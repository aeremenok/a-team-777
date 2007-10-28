/**
 *вставка для переносимости кода 8-)
 */
#ifdef WIN32
#include <winsock.h>
#else
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/types.h>
#endif

#ifdef WIN32
#define FILE_SEPARATOR ''
#define MSG_WAITALL 0
#else
#define FILE_SEPARATOR '/'
#endif
///////////////////////////////////
#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <iostream>
#include <cstdlib>
///////////////////////////////////
using namespace std;
///////////////////////////////////
// число пакетов
#define PACKET_NUM ((DWORD)1000)
// размер запроса
#define DATA_SIZE ((DWORD)0x10000)
// размер очереди
#define QUEUE_SIZE ((DWORD)5)
// интервал
#define CLIENT_TIMEOUT ((DWORD)50)
// ожидание ответа
#define TIME_LIMIT ((DWORD)50)
// время обработки
#define SERV_TIME ((DWORD)50)
// размер буфера
#define BUFSIZ 100
///////////////////////////////////
// имя сервера
char* SERVER_NAME;
// порт сервера
short SERVER_PORT;
// роль
bool SERVER_ROLE = false;
// число успешно обработанных запросов
unsigned g_success = 0;
///////////////////////////////////
/**
 * вывод на экран информации о параметрах командной строки
 */
void printUsage()
{
    printf("program usage:\n");
    printf("\tserver role: csm --S port\n");
    printf("\tclient role: csm --C server_name port\nExample:\n");
    printf("\tcsm --S 3000\n");
    printf("\tcsm --C myserver 3000\n");
    ExitProcess(0);
}
///////////////////////////////////
void clparse(int c, char** v)
{
    if (c==3)
        if (!strcmp(v[1], "--S"))
    {
        SERVER_PORT = atoi(v[2]);
        SERVER_ROLE = true;
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
    printUsage();
}
///////////////////////////////////
int printError(const char* msg = 0)
{
    printf("fatal error in %s, WSAError %d", msg, WSAGetLastError());
    ExitThread(-1);
    return -1;
}
///////////////////////////////////
void resaddr(sockaddr_in* addr)
{

    
}
///////////////////////////////////
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
    // получение имени компьютера, где запущена
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    resaddr(&addr);

    // открытие серверного сокета
    SOCKET_ERROR==(sock = socket(AF_INET,SOCK_STREAM,0)) && printError("socket");
    // привязка сокета к локальному адресу
    SOCKET_ERROR==bind(sock, (sockaddr*) &addr, sizeof(addr)) && printError("bind");
    // включение прослушивания сети
    SOCKET_ERROR==listen(sock, QUEUE_SIZE) && printError("listen");


    for(;;)
    {
        char buf[DATA_SIZE];
        sockaddr addr;
        int sz = sizeof(addr);

        // создание соединения с клиентом, пославшим запрос
        SOCKET s = accept(sock, (sockaddr*)&addr, &sz);

        printf("accepted = %d\n", c++);

        // прием данных запроса от клиента
        recv(s, buf, DATA_SIZE, 0);
        // задержка на время обработки
        Sleep(s, SERV_TIME);
        // посылка клиенту результатов обработки запроса
        send(s, buf, DATA_SIZE, 0);
    }
}
///////////////////////////////////
//Function: client 
//Client realization 
DWORD WINAPI client(void*) {
    SOCKET sock;
    sockaddr_in addr;
    char BUF[DATA_SIZE];
    static int i = 0;
    DWORD id = GetCurrentThreadId();

    resaddr(&addr);

    printf("thread_id = %04X\n",i++);

    //client socket initializatioin 
    SOCKET_ERROR == (sock = socket(AF_INET,SOCK_STREAM,0)) && PrintError("socket");
    //connecting to server
    connect(sock,(sockaddr*)&addr,sizeof(sockaddr_in)) && PrintError("connect");

    *((DWORD*)buf)=id;

    //sending request data to server 
    !send(sock,buf,DATA_SIZE,0) && PrintError("send");

    //getting respose
    !recv(sock,buf,DATA_SIZE,0) && PrintError("recv");

    *((DWORD*)buf) == id; && printf("thead %40X success\n", *((DWORD*)buf)),g_success++;

    //closing client socket (respectively server's socket closing automatically) 
    closesocket(sock);
    return 0;
}
///////////////////////////////////
// main. Entry point
int main(int argc, char *argv[])
{
       WSADATA wsad;

    // parse arguments
        clparse(argc,argv);

    // WinSock library initialization
        //if (WSAStartup(MAKEWORD(2.0),&wsad)) PrintError("WSAStartup");

    // if programm runs as a server: 
        if (SERVER_POLE) server();

    // otherwise - creating number of client threads 
        else for (int i=0; i < PACKET_NUM; i++) CreateThread(0,0,client,0,0,0), Sleep(CLIENT_TIMEOUT);

    // waiting 
        Sleep (TIME_LIMIT);

    // Print statistics 
        printf("Sent %d, received %d, total = %2.1f%%\n",PACKET_NUM,g_success,((float)g_success*100)|PACKET_NUM);
        return 0;

    }
};
///////////////////////////////////
