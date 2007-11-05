//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include <winsock2.h>
#include <iostream>
#include <cstdlib>
#include <queue>
//////////////////////////////////////////////////////////////////////////
using namespace std;


//////////////////////////////////////////////////////////////////////////
// число пакетов
#define PACKET_NUM ((DWORD)10)
// размер очереди
#define QUEUE_SIZE ((DWORD) 5)
// интервал
#define CLIENT_TIMEOUT ((DWORD)50)
// ожидание ответа
#define TIME_LIMIT ((DWORD)1000
)


// время обработки  - изменяемое
int SERV_TIME = 10;
// размер запроса
int DATA_SIZE  = 10000; //изменяемое


// имя сервера
char* SERVER_NAME;
// порт сервера
short SERVER_PORT;
// роль
bool SERVER_ROLE = false;


// число успешно обработанных запросов
unsigned g_success = 0;

//////////////////////////////////////////////////////////////////////////
// счетчик клиентов
int c = 0;
// параметры рабочего сокета

int sz = 0;





//////////////////////////////////////////////////////////////////////////
/**
 * вывод на экран информации о параметрах командной строки
 */
void printUsage()
{
    printf("program usage:\n");
    printf("\tserver role: csm --S port serv_time\n");
    printf("\tclient role: csm --C server_name port data_size\nExample:\n");
    printf("\tcsm --S 3000 50\n");
    printf("\tcsm --C myserver 3000 1000\n");
    // завершаем работу
    ExitProcess(0);
}

/////////////////////////////////////////////////////////////////////////
/**
 * разбирает поданные на вход аргументы
 */
void clparse(int c, char** v)
{
	printf("entered %d params:", c);
    if (c==4)
        if (!strcmp(v[1], "--S"))
		{
			SERVER_ROLE = true;
			SERVER_PORT = atoi(v[2]);        
			SERV_TIME = atoi(v[3]);
			
			printf("started as server. port = %d, SERV_TIME= %d\n", SERVER_PORT, SERV_TIME);
			return;
		}
		if (c==5)			
			if (!strcmp(v[1], "--C"))
			{
				SERVER_NAME = v[2];
				SERVER_PORT = atoi(v[3]);
				DATA_SIZE = atoi(v[4]);
				

				printf("started as client. server = %s, server port = %d, data size is %d", 
					SERVER_NAME, SERVER_PORT, DATA_SIZE);

				return;
			}
		// если ничего подходящего не ввели - выводим инструкции и завершаем работу
		printUsage();
}
//////////////////////////////////////////////////////////////////////////
/**
 * выводит сообщение об ошибке
 */
int printError(const char* msg = 0)
{
    printf("fatal error in %s, WSAError %d", msg, WSAGetLastError());
    // завершаем поток
    ExitThread(-1);
    return -1;
}


//////////////////////////////////////////////////////////////////////////
/**
 * определяет адрес хоста
 */
void resaddr(sockaddr_in *addr)
{
   hostent *he;
   ZeroMemory(addr, sizeof(sockaddr_in));
   // получение хоста по его имени
   !(he = gethostbyname(SERVER_NAME)) && printError("gethostbyname");

   CopyMemory(
        &addr->sin_addr,
        he->h_addr, 
        sizeof(sockaddr_in)
        );
   addr->sin_family = AF_INET;
   addr->sin_port=htons(SERVER_PORT);

   printf(
       "host=%s, IP=%s\n",
       SERVER_NAME,
       inet_ntoa(addr->sin_addr)
       );
}

//////////////////////////////////////////////////////////////////////////
/**
 * реализация сервера
 */
void server()
{
//    char* buf;
	char hostName[BUFSIZ];
	SOCKET sock;
	sockaddr_in addr;
	int c = 0; //количество клиентов


    // получаем имя компьютера, где запущено
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    // получаем параметры хоста по имени
    resaddr(&addr);

    // открытие серверного сокета
    SOCKET_ERROR==(sock = socket(AF_INET, SOCK_STREAM, 0)) 
        && printError("socket");
    // привязка сокета к локальному адресу
    SOCKET_ERROR==bind(sock, (sockaddr*) &addr, sizeof(addr)) 
        && printError("bind");
    
   
	
	SOCKET_ERROR==listen(sock, QUEUE_SIZE) 
		&& printError("listen");
	
	//sockaddr_in addr;
	int sz = sizeof(addr);
    
    for(;;)
    {
		char* buf = new char[DATA_SIZE];



        
        SOCKET s = accept(sock,(sockaddr*)&addr, &sz);
		printf("accepted=%d\n", c++);

		//принять от клиента
		recv(s, buf, DATA_SIZE, 0);

		//задержка - имитируем обработку запроса
		Sleep(SERV_TIME);

		//отсылаем резулт
		send(s,buf,DATA_SIZE,0);

				
    }
    //closesocket(sock);
}

//////////////////////////////////////////////////////////////////////////
/*
 * реализация клиента
 */
DWORD WINAPI client(void*)
{
    SOCKET sock;
    sockaddr_in serv_addr;
    char* buf = new char[DATA_SIZE];
	static int i =0 ;
    DWORD id = GetCurrentThreadId();


    //DWORD receivedID;
    
    // получаем параметры хоста по имени
    resaddr(&serv_addr);

    printf("thread_id = %d\n",id);

    // инициализируем сокет клиента
    SOCKET_ERROR == (sock = socket(AF_INET, SOCK_STREAM, 0)) 
        && printError("socket");
    // соединяемся с сервером
    connect(sock,(sockaddr*)&serv_addr,sizeof(sockaddr_in)) && printError("clinet<< connect");

	*((DWORD*)buf) = id;

	!send(sock, buf, DATA_SIZE, 0) && printError("client<< send");

	!recv(sock, buf, DATA_SIZE, 0) && printError("client<< recv");

	*((DWORD*)buf)==id && printf("thread %4X success\n", *((DWORD*)buf));

	g_success++;

    // закрываем сокетное соединение
    closesocket(sock);

    return 0;
}


//////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{
    int timeLimit;

    // разбираем входные данные
    clparse(argc,argv);

    // инициализируем сокеты windows
    WSADATA wsad;
    if ( WSAStartup( MAKEWORD(2,0), &wsad ) ) 
        printError("WSAStartup");

    if (SERVER_ROLE) // запущен сервер
	{
		//CreateThread(0,0,handleQueue,0,0,0);
        
		server();
	}
    else
    {   // запущен клиент
        for (int i=0; i < PACKET_NUM; i++) 
            CreateThread(0,0,client,0,0,0), Sleep( CLIENT_TIMEOUT );

        // ждем ответа
        timeLimit = 2 * SERV_TIME * PACKET_NUM + TIME_LIMIT;
        printf("sleeping for %d\n", timeLimit);
        Sleep (timeLimit);

        // выводим статистику
        printf(
            "Sent %d, received %d, total = %2.1f%%\n",
            PACKET_NUM,
            g_success,
            ((float)g_success*100)/PACKET_NUM
            );
    }
    return 0;
}
//////////////////////////////////////////////////////////////////////////

