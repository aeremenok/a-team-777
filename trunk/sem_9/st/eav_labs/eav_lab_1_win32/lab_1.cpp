//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include <winsock2.h>
#include <iostream>
#include <cstdlib>
#include <queue>
//////////////////////////////////////////////////////////////////////////
using namespace std;
//////////////////////////////////////////////////////////////////////////
// ����� �������
#define PACKET_NUM ((DWORD)100)
// ������ �������
#define DATA_SIZE BUFSIZ
// ��������
#define CLIENT_TIMEOUT ((DWORD)50)
// �������� ������
#define TIME_LIMIT ((DWORD)50)
//////////////////////////////////////////////////////////////////////////
// ������ �������
int QUEUE_SIZE = 5;
// ����� ���������
int SERV_TIME = 50;
// ��� �������
char* SERVER_NAME;
// ���� �������
short SERVER_PORT;
// ����
bool SERVER_ROLE = false;
// ����� ������� ������������ ��������
unsigned g_success = 0;
//////////////////////////////////////////////////////////////////////////
// ������� ���������
queue<char*> _client_messages;
// ������� �������
queue<sockaddr_in*> _client_addresses;

// ������� ��������
int c = 0;
// ��������� �������� ������
SOCKET sock;
sockaddr_in addr;
int sz = 0;
// ����������� ������ ��� ��������� �������
CRITICAL_SECTION cr;
//////////////////////////////////////////////////////////////////////////
/**
 * ����� �� ����� ���������� � ���������� ��������� ������
 */
void printUsage()
{
    printf("program usage:\n");
    printf("\tserver role: csm --S port serv_time queue_depth\n");
    printf("\tclient role: csm --C server_name port\nExample:\n");
    printf("\tcsm --S 3000 50 5\n");
    printf("\tcsm --C myserver 3000\n");
    // ��������� ������
    ExitProcess(0);
}
//////////////////////////////////////////////////////////////////////////
/**
 * ��������� �������� �� ���� ���������
 */
void clparse(int c, char** v)
{
    if (c==5)
        if (!strcmp(v[1], "--S"))
    {
        SERVER_PORT = atoi(v[2]);
        SERVER_ROLE = true;
        SERV_TIME = atoi(v[3]);
        QUEUE_SIZE = atoi(v[4]);
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
    // ���� ������ ����������� �� ����� - ������� ���������� � ��������� ������
    printUsage();
}
//////////////////////////////////////////////////////////////////////////
/**
 * ������� ��������� �� ������
 */
int printError(const char* msg = 0)
{
    printf("fatal error in %s, WSAError %d", msg, WSAGetLastError());
    // ��������� �����
    ExitThread(-1);
    return -1;
}
//////////////////////////////////////////////////////////////////////////
/**
 * ���������� ����� �����
 */
void resaddr(sockaddr_in *addr)
{
   hostent *he;
   ZeroMemory(addr, sizeof(sockaddr_in));
   // ��������� ����� �� ��� �����
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
 * �������������� �����
 */
void initSocket()
{
    char hostName[BUFSIZ];
    // �������� ��� ����������, ��� ��������
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    // �������� ��������� ����� �� �����
    resaddr(&addr);

    // �������� ���������� ������
    SOCKET_ERROR==(sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) 
        && printError("socket");
    // �������� ������ � ���������� ������
    SOCKET_ERROR==bind(sock, (sockaddr*) &addr, sizeof(addr)) 
        && printError("bind");
    
    sz = sizeof(addr);
}
//////////////////////////////////////////////////////////////////////////
/**
 * ���������� �������
 */
void server()
{
    char* buf;
    for(;;)
    {
		EnterCriticalSection(&cr);
        
        if ( _client_addresses.size() > 0 )
        {   // ���� ������� - ������������
            buf = _client_messages.front();
			_client_messages.pop();

            addr = *(_client_addresses.front());
            _client_addresses.pop();

            // �������� �� ����� ���������
            Sleep(SERV_TIME);
            
            // ������� ������� ����������� ��������� �������
            if ( sendto(sock, buf, DATA_SIZE, 0, (sockaddr*)&addr, sz) == -1)
            {
                printError("server << sendto");
            } 
		}

		LeaveCriticalSection(&cr);
    }
    closesocket(sock);
}
//////////////////////////////////////////////////////////////////////////
DWORD WINAPI handleQueue(void*)
{
    char buf[DATA_SIZE];
    for(;;)
	{
		EnterCriticalSection(&cr);

		if (recvfrom(sock, buf, DATA_SIZE, 0, (sockaddr*)&addr, &sz) == SOCKET_ERROR)
		{
			printError("handleQueue<< recvfrom");
		}
		else if (_client_addresses.size() < QUEUE_SIZE)
		{   // ������� �� ��������� - ���������� �� ���������
			_client_messages.push(buf);
			_client_addresses.push(&addr);

			printf("accepted = %d\n", ++c);
		}

		LeaveCriticalSection(&cr);
	}
}
//////////////////////////////////////////////////////////////////////////
/*
 * ���������� �������
 */
DWORD WINAPI client(void*)
{
    SOCKET sock;
    sockaddr_in serv_addr;
    char buf[DATA_SIZE];
    DWORD id = GetCurrentThreadId();
    DWORD receivedID;
    
    // �������� ��������� ����� �� �����
    resaddr(&serv_addr);

    printf("thread_id = %d\n",id);

    // �������������� ����� �������
    SOCKET_ERROR == (sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) 
        && printError("socket");
    // ����������� � ��������
    connect(sock,(sockaddr*)&serv_addr,sizeof(sockaddr_in)) && printError("clinet<< connect");

    sprintf(buf, "%d", id);
    int sz = sizeof(serv_addr);
    // ���������� ������ �� ������
    if (sendto(sock, buf, DATA_SIZE, 0, (sockaddr*)&serv_addr, sz) == SOCKET_ERROR)
    {
        printError("client<< sendto");
    }

    // �������� �����
    if (recvfrom(sock, buf, DATA_SIZE, 0, (sockaddr*)&serv_addr, &sz) == SOCKET_ERROR)
    {
        printError("client<< recvfrom");
    }

    receivedID = atof(buf);
    if (receivedID==id)
    {
        printf("thead %d success\n", id);
        g_success++; 
    } 

    // ��������� �������� ����������
    closesocket(sock);

    return 0;
}
//////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{
    int timeLimit;

    // ��������� ������� ������
    clparse(argc,argv);

    // �������������� ������ windows
    WSADATA wsad;
    if ( WSAStartup( MAKEWORD(2,0), &wsad ) ) 
        printError("WSAStartup");

    if (SERVER_ROLE) // ������� ������
	{
		initSocket();

		InitializeCriticalSection(&cr);
		CreateThread(0,0,handleQueue,0,0,0);
        server();
	}
    else
    {   // ������� ������
        for (int i=0; i < PACKET_NUM; i++) 
            CreateThread(0,0,client,0,0,0), Sleep( CLIENT_TIMEOUT );

        // ���� ������
        timeLimit = 2 * SERV_TIME * PACKET_NUM + TIME_LIMIT;
        printf("sleeping for %d\n", timeLimit);
        Sleep (timeLimit);

        // ������� ����������
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

