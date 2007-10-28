/**
 *������� ��� ������������� ���� 8-)
 */
//#ifdef WIN32
#include "StdAfx.h"
#include <winsock.h>
// #elseif
// #include <netdb.h>
// #include <arpa/inet.h>
// #include <netinet/in.h>
// #include <sys/socket.h>
// #include <sys/types.h>
// #endif

// #ifdef WIN32
// #define FILE_SEPARATOR ''
// #define MSG_WAITALL 0
// #else
// #define FILE_SEPARATOR '/'
// #endif
///////////////////////////////////
// #ifdef HAVE_CONFIG_H
// #include <config.h>
// #endif

#include <iostream>
#include <cstdlib>
///////////////////////////////////
using namespace std;
///////////////////////////////////
// ����� �������
#define PACKET_NUM ((DWORD)1000)
// ������ �������
#define DATA_SIZE ((DWORD)0x10000)
// ������ �������
#define QUEUE_SIZE ((DWORD)5)
// ��������
#define CLIENT_TIMEOUT ((DWORD)50)
// �������� ������
#define TIME_LIMIT ((DWORD)50)
// ����� ���������
#define SERV_TIME ((DWORD)50)
// ������ ������
//#define BUFSIZ 100
///////////////////////////////////
// ��� �������
char* SERVER_NAME;
// ���� �������
short SERVER_PORT;
// ����
bool SERVER_ROLE = false;
// ����� ������� ������������ ��������
unsigned g_success = 0;
///////////////////////////////////
/**
 * ����� �� ����� ���������� � ���������� ��������� ������
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
//Fucntion: resaddr
//Host address determination
void resaddr(sockaddr_in *addr)
{
    hostent *he;

    ZeroMemory(addr, sizeof(sockaddr_in));

    // Host address request by name
    !(he = gethostbyname(SERVER_NAME)) && printError("gethostbyname");

    CopyMemory(&addr->sin_addr,he->h_addr /*���� ��� ������ ���� ������-��*/,sizeof(sockaddr_in));
    addr->sin_family = AF_INET, addr->sin_port=htons(SERVER_PORT);

    printf("host=%s, IP=%s\n",SERVER_NAME,inet_ntoa(addr->sin_addr));
}
///////////////////////////////////
/**
 * ���������� �������
 */
void server()
{
    char hostName[BUFSIZ];

    sockaddr_in addr;
    SOCKET sock;
    // ������� ��������
    int c = 0;
    // ��������� ����� ����������, ��� ��������
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    resaddr(&addr);

    // �������� ���������� ������
    SOCKET_ERROR==(sock = socket(AF_INET,SOCK_STREAM,0)) && printError("socket");
    // �������� ������ � ���������� ������
    SOCKET_ERROR==bind(sock, (sockaddr*) &addr, sizeof(addr)) && printError("bind");
    // ��������� ������������� ����
    SOCKET_ERROR==listen(sock, QUEUE_SIZE) && printError("listen");


    for(;;)
    {
        char buf[DATA_SIZE];
        sockaddr addr;
        int sz = sizeof(addr);

        // �������� ���������� � ��������, ��������� ������
        SOCKET s = accept(sock, (sockaddr*)&addr, &sz);

        printf("accepted = %d\n", c++);

        // ����� ������ ������� �� �������
        recv(s, buf, DATA_SIZE, 0);
        // �������� �� ����� ���������
        Sleep(SERV_TIME);
        // ������� ������� ����������� ��������� �������
        send(s, buf, DATA_SIZE, 0);
    }
}
///////////////////////////////////
//Function: client 
//Client realization 
DWORD WINAPI client(void*) {
    SOCKET sock;
    sockaddr_in addr;
    char buf[DATA_SIZE];
    static int i = 0;
    DWORD id = GetCurrentThreadId();

    resaddr(&addr);

    printf("thread_id = %04X\n",i++);

    //client socket initializatioin 
    SOCKET_ERROR == (sock = socket(AF_INET,SOCK_STREAM,0)) && printError("socket");
    //connecting to server
    connect(sock,(sockaddr*)&addr,sizeof(sockaddr_in)) && printError("connect");

    *((DWORD*)buf)=id;

    //sending request data to server 
    !send(sock,buf,DATA_SIZE,0) && printError("send");

    //getting respose
    !recv(sock,buf,DATA_SIZE,0) && printError("recv");

    *((DWORD*)buf) == id && printf("thead %40X success\n", *((DWORD*)buf)),g_success++;

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
    if ( WSAStartup( MAKEWORD(2,0), &wsad ) ) 
        printError("WSAStartup");

    // if programm runs as a server: 
    if (SERVER_ROLE) server();

    // otherwise - creating number of client threads 
    else for (int i=0; i < PACKET_NUM; i++) CreateThread(0,0,client,0,0,0), Sleep(CLIENT_TIMEOUT);

    // waiting 
    Sleep (TIME_LIMIT);

    // Print statistics 
    printf("Sent %d, received %d, total = %2.1f%%\n",PACKET_NUM,g_success,((float)g_success*100)/PACKET_NUM);
    return 0;
}
///////////////////////////////////

