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
#define PACKET_NUM ((DWORD)10)
// ������ �������
#define QUEUE_SIZE ((DWORD) 5)
// ��������
#define CLIENT_TIMEOUT ((DWORD)50)
// �������� ������
#define TIME_LIMIT ((DWORD)1000
)


// ����� ���������  - ����������
int SERV_TIME = 10;
// ������ �������
int DATA_SIZE  = 10000; //����������


// ��� �������
char* SERVER_NAME;
// ���� �������
short SERVER_PORT;
// ����
bool SERVER_ROLE = false;


// ����� ������� ������������ ��������
unsigned g_success = 0;

//////////////////////////////////////////////////////////////////////////
// ������� ��������
int c = 0;
// ��������� �������� ������

int sz = 0;





//////////////////////////////////////////////////////////////////////////
/**
 * ����� �� ����� ���������� � ���������� ��������� ������
 */
void printUsage()
{
    printf("program usage:\n");
    printf("\tserver role: csm --S port serv_time\n");
    printf("\tclient role: csm --C server_name port data_size\nExample:\n");
    printf("\tcsm --S 3000 50\n");
    printf("\tcsm --C myserver 3000 1000\n");
    // ��������� ������
    ExitProcess(0);
}

/////////////////////////////////////////////////////////////////////////
/**
 * ��������� �������� �� ���� ���������
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
 * ���������� �������
 */
void server()
{
//    char* buf;
	char hostName[BUFSIZ];
	SOCKET sock;
	sockaddr_in addr;
	int c = 0; //���������� ��������


    // �������� ��� ����������, ��� ��������
    gethostname(hostName, BUFSIZ) && printError("gethostname");
    
    SERVER_NAME = new char[strlen(hostName)+1];
    strcpy(SERVER_NAME, hostName);
    // �������� ��������� ����� �� �����
    resaddr(&addr);

    // �������� ���������� ������
    SOCKET_ERROR==(sock = socket(AF_INET, SOCK_STREAM, 0)) 
        && printError("socket");
    // �������� ������ � ���������� ������
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

		//������� �� �������
		recv(s, buf, DATA_SIZE, 0);

		//�������� - ��������� ��������� �������
		Sleep(SERV_TIME);

		//�������� ������
		send(s,buf,DATA_SIZE,0);

				
    }
    //closesocket(sock);
}

//////////////////////////////////////////////////////////////////////////
/*
 * ���������� �������
 */
DWORD WINAPI client(void*)
{
    SOCKET sock;
    sockaddr_in serv_addr;
    char* buf = new char[DATA_SIZE];
	static int i =0 ;
    DWORD id = GetCurrentThreadId();


    //DWORD receivedID;
    
    // �������� ��������� ����� �� �����
    resaddr(&serv_addr);

    printf("thread_id = %d\n",id);

    // �������������� ����� �������
    SOCKET_ERROR == (sock = socket(AF_INET, SOCK_STREAM, 0)) 
        && printError("socket");
    // ����������� � ��������
    connect(sock,(sockaddr*)&serv_addr,sizeof(sockaddr_in)) && printError("clinet<< connect");

	*((DWORD*)buf) = id;

	!send(sock, buf, DATA_SIZE, 0) && printError("client<< send");

	!recv(sock, buf, DATA_SIZE, 0) && printError("client<< recv");

	*((DWORD*)buf)==id && printf("thread %4X success\n", *((DWORD*)buf));

	g_success++;

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
		//CreateThread(0,0,handleQueue,0,0,0);
        
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

