//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include <winsock2.h>
#include <iostream>
#include <cstdlib>
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
// число пакетов
#define PACKET_NUM ((DWORD)100)
// размер запроса
#define DATA_SIZE ((DWORD)0x10000)
// интервал
#define CLIENT_TIMEOUT ((DWORD)50)
// ожидание ответа
#define TIME_LIMIT ((DWORD)50)
//////////////////////////////////////////////////////////////////////////
/************************************************************************/
// размер очереди
int QUEUE_SIZE = 5;
// время обработки
int SERV_TIME = 50;
/************************************************************************/
// имя сервера
char* SERVER_NAME;
// порт сервера
short SERVER_PORT;
// роль
bool SERVER_ROLE = false;
// число успешно обработанных запросов
unsigned g_success = 0;
//////////////////////////////////////////////////////////////////////////
/**
 * вывод на экран информации о параметрах командной строки
 */
void printUsage()
{
    printf("program usage:\n");
    printf("\tserver role: csm --S port serv_time queue_depth\n");
    printf("\tclient role: csm --C server_name port\nExample:\n");
    printf("\tcsm --S 3000 50 5\n");
    printf("\tcsm --C myserver 3000\n");
    // завершаем работу
    ExitProcess(0);
}
//////////////////////////////////////////////////////////////////////////
/**
 * разбирает поданные на вход аргументы
 */
void clparse(int c, char** v)
{
    if (c==5)
        if (!strcmp(v[1], "--S"))
    {
        SERVER_PORT = atoi(v[2]);
        SERVER_ROLE = true;
        /************************************************************************/
        SERV_TIME = atoi(v[3]);
        QUEUE_SIZE = atoi(v[4]);
        /************************************************************************/
        printf("started as server. port = %d\n", SERVER_PORT);
        return;
    }
    if (c==4)
        if (!strcmp(v[1], "--C"))
    {
        SERVER_PORT = atoi(v[3]);
        SERVER_NAME = v[2];

        printf("started as client. server = %s, server port = %d", SERVER_NAME, SERVER_PORT);
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
    char hostName[BUFSIZ];

    sockaddr_in addr;
    SOCKET sock;
    // счетчик клиентов
    int c = 0;
    // получаем имя компьютера, где запущено
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    // получаем параметры хоста по имени
    resaddr(&addr);

    // открытие серверного сокета
    SOCKET_ERROR==(sock = socket(AF_INET,SOCK_DGRAM,
        /************************************************************************/
        IPPROTO_UDP
        /************************************************************************/
        )) && printError("socket");
    // привязка сокета к локальному адресу
    SOCKET_ERROR==bind(sock, (sockaddr*) &addr, sizeof(addr)) && printError("bind");
    
    int sz = sizeof(addr);
    for(;;)
    {
        char buf[DATA_SIZE];
        printf("accepted = %d\n", c++);

        // прием данных запроса от клиента
        if (recvfrom(sock, buf, BUFSIZ, 0, (sockaddr*)&addr, &sz) == -1)
        {
            printError("server<<recvfrom");
        }
        
        // задержка на время обработки
        Sleep(SERV_TIME);
        // посылка клиенту результатов обработки запроса
        if ( sendto(sock, buf, BUFSIZ, 0, (sockaddr*)&addr, sz) == -1)
        {
            printError("server<<sendto");
        }
    }
    closesocket(sock);
}
//////////////////////////////////////////////////////////////////////////
/*
 * реализация клиента
 */
DWORD WINAPI client(void*)
{
    SOCKET sock;
    sockaddr_in addr;
    char buf[DATA_SIZE];
    static int i = 0;
    DWORD id = GetCurrentThreadId();

    // получаем параметры хоста по имени
    resaddr(&addr);

    printf("thread_id = %04X\n",i++);

    // инициализируем сокет клиента
    SOCKET_ERROR == (sock = socket(AF_INET,SOCK_DGRAM,
        /************************************************************************/
        IPPROTO_UDP
        /************************************************************************/
        )) && printError("socket");
    // соединяемся с сервером
    connect(sock,(sockaddr*)&addr,sizeof(sockaddr_in)) && printError("connect");

    *((DWORD*)buf)=id;
    int sz = sizeof(addr);
    // отправляем запрос на сервер
    if (sendto(sock, buf, BUFSIZ, 0, (sockaddr*)&addr, sz) == -1)
    {
        printError("client<<sendto");
    }

    // получаем ответ
    if (recvfrom(sock, buf, BUFSIZ, 0, (sockaddr*)&addr, &sz) == -1)
    {
        printError("client<<recvfrom");
    }

    *((DWORD*)buf) == id && printf("thead %40X success\n", *((DWORD*)buf)),g_success++;

    // закрываем сокетное соединение (сервер закрывает сокет автоматически)
    closesocket(sock);

    return 0;
}
//////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{
    // разбираем входные данные
    clparse(argc,argv);

    // инициализируем сокеты windows
    WSADATA wsad;
    if ( WSAStartup( MAKEWORD(2,0), &wsad ) ) 
        printError("WSAStartup");

    if (SERVER_ROLE) // запущен сервер
        server();
    else             // запущен клиент
        for (int i=0; i < PACKET_NUM; i++) 
            CreateThread(0,0,client,0,0,0), Sleep(CLIENT_TIMEOUT);

    // ждем ответа
    Sleep (TIME_LIMIT);

    // выводим статистику
    printf(
        "Sent %d, received %d, total = %2.1f%%\n",
        PACKET_NUM,
        g_success,
        ((float)g_success*100)/PACKET_NUM
        );

    return 0;
}
//////////////////////////////////////////////////////////////////////////

