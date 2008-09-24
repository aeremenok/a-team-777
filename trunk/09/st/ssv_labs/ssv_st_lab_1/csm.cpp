/*
============================================================================
Name        : csm.cpp
Author      : ssv
Version     : 1.2
Copyright   : copyleft
Description : TCP client-server implementation
============================================================================
*/

#include "stdafx.h" 

////////////////////////////////////////////////////////////////////////////////

#define     PACKET_NUM  ((DWORD)100) 		// ����� �������
#define     DATA_SIZE   ((DWORD)0x10000) 	// ������ �������
#define     TIME_LIMIT  ((DWORD)1000) 		// �������� ������
#define     SERV_TIME   ((DWORD)100) 		// ����� ���������

////////////////////////////////////////////////////////////////////////////////

char		*SERVER_NAME;			// ��� �������
USHORT		SERVER_PORT;			// ���� �������
unsigned	CLIENT_TIMEOUT;			// �������� ����� ��������� �� ������� �������
unsigned	QUEUE_SIZE;				// ������ �������
bool		SERVER_ROLE = false;	// ����
unsigned	g_success = 0;			// ����� ������� ������������ ��������

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ��������� �� ������ WINSOCK
int PrintError(const char *msg = 0)
{
    printf("Fatal error in %s, WSAError=%d\n", msg, WSAGetLastError());
    ExitThread(-1);
    return -1;
}

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ���������� � ���������� ������� ���������
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tServer role: csm -S port queue_size\n");
    printf("\tClient role: csm -C server_name port client_timeout\nExample:");
    printf("\n\tcsm -S 3000 5");
    printf("\n\tcsm -C myserver 3000 50\n");
    ExitProcess(0);
}

////////////////////////////////////////////////////////////////////////////////

// ������ ���������� ��������� ������
void clparse(int c, char** v)
{
    if (c == 4)
    {
        if (!strcmp(v[1], "-S"))
        {
            // ������ ��������� ��� �������
            QUEUE_SIZE = atoi( v[3] ), SERVER_PORT = atoi(v[2]), SERVER_ROLE = true;
            printf("Started as SERVER, port=%d\n", SERVER_PORT);
            return;
        }
    }

    if (c == 5)
    {
        if (!strcmp(v[1], "-C"))
        {
            // ������ ��������� ��� �������
            CLIENT_TIMEOUT = atoi(v[4]), SERVER_PORT=atoi(v[3]), SERVER_NAME=v[2], printf(
                "Started as CLIENT, SERVER=%s, port=%d\n", SERVER_NAME,
                SERVER_PORT);
            return;
        }
    }

    // ����������� �������� ��������� - �������� ������� �� ������ � ����������
    PrintUsage();
}

////////////////////////////////////////////////////////////////////////////////

// ����������� ������ �����
void resaddr(sockaddr_in *addr)
{
    hostent *he;

    ZeroMemory(addr, sizeof(sockaddr_in));

    // ������ ������ ����� �� ��� �����
    !(he = gethostbyname(SERVER_NAME)) && PrintError("gethostname");
    CopyMemory(&addr->sin_addr,he->h_addr,sizeof(sockaddr_in));
    addr->sin_family = AF_INET, addr->sin_port = htons(SERVER_PORT);

    printf("host=%s, IP=%s\n", SERVER_NAME, inet_ntoa(addr->sin_addr));
}

////////////////////////////////////////////////////////////////////////////////

// ���������� �������
void server()
{
    char    hostname[BUFSIZ];
    sockaddr_in addr;
    SOCKET  sock;
    int     c = 0;              // �ר���� ��������
    float   util;               // ���������� �������
	DWORD   time1;              // ����� ������� ���������
    DWORD   time2;              // ����� ���������� ���������
	DWORD   timeBusy = 0;       // ����� ��������� ������� (������ ��������� ��������)
    DWORD   timeUp = 0;         // ����� ������������� ������� � ������ ��������� ������� �������
    DWORD   timeStarted = 0;    // ����� ������ ��������� ������� �������
    bool    hadPacket = false;  // ��������� ������� ������� ������ (������ ���ר�� ����������)

    // ��������� ����� ����������, �� ������� �������� ���������
    gethostname(hostname, BUFSIZ) && PrintError("gethostname");
    SERVER_NAME = new char[strlen(hostname)+1];
    strcpy(SERVER_NAME, hostname);
    resaddr(&addr);

    // �������� ���������� ������
    SOCKET_ERROR == (sock = socket(AF_INET, SOCK_STREAM, 0)) && PrintError("socket");

    // �������� ������ � ���������� ������
    SOCKET_ERROR == bind(sock, (sockaddr*)&addr, sizeof(addr)) && PrintError("bind");

    // ��������� ������������� ����
    SOCKET_ERROR == listen(sock, QUEUE_SIZE) && PrintError("listen");

    for (;;)
    {
        char buf[DATA_SIZE];
        sockaddr_in addr;
        int sz = sizeof(addr);

        // �������� ���������� � ��������, ���������� ������
        SOCKET s = accept(sock, (sockaddr*)&addr, &sz);
        printf("accepted=%d\n", c++);

        //������� ������ ������� �� �������
        recv(s, buf, DATA_SIZE, 0);

        // ���� ������ ��������� ������� ������� - �������� ����� ������
        if ( !hadPacket )
        {
            hadPacket = true;
            timeStarted = GetTickCount();
        }
        
        // ������� ������� ����� ���������� �������
        time1 = GetTickCount();

        // �������� �� ��������� ����������
        Sleep(SERV_TIME);

        // ������� ������� ����� ��������� �������
        time2 = GetTickCount();

        // ����� ��������� �������
        timeBusy += time2 - time1;

        // ����� ������ �������
        timeUp = time2 - timeStarted;

        // ���ר� ���������� �������
        util = (float) timeBusy / (float) timeUp;
        util *= 100;

        printf("\nserver average utilization is %2.1f%%\n", util);

        // ������� ������� ����������� ��������� �������
        send(s, buf, DATA_SIZE, 0);
    }
}

////////////////////////////////////////////////////////////////////////////////

// ���������� �������
DWORD WINAPI client(void *)
{
    SOCKET sock;
    sockaddr_in addr;
    char buf[DATA_SIZE];
    static int i = 0;
    DWORD id = GetCurrentThreadId();

    resaddr(&addr);

    printf("thread_id=%04X\n", i++);

    // �������� ����������� ������
    SOCKET_ERROR == (sock = socket(AF_INET,SOCK_STREAM, 0 )) && PrintError("socket");

    // ���������� � ��������
    connect(sock, (sockaddr*) &addr, sizeof(sockaddr_in))
        && PrintError("connect");
    *((DWORD*)buf) = id;

    // ������� ������ ������� �������
    !send(sock, buf, DATA_SIZE, 0) && PrintError("send");

    // �������� ����������� ��������� �������
    !recv(sock, buf, DATA_SIZE, 0) && PrintError("recv");
    *((DWORD*)buf) == id && printf("thread %04X success\n", *((DWORD*)buf)),
        g_success++;

    // �������� ����������� ������ (�� ������� �������� ��������������)
    closesocket(sock);

    return 0;
}

////////////////////////////////////////////////////////////////////////////////

// ����� �����
int main(int argc, char* argv[])
{
    WSADATA wsad;

    // ������ ���������� ��������� ������
    clparse(argc, argv);

    // ������������� ���������� WINSOCK
    if (WSAStartup(MAKEWORD(2,0), &wsad))
    {
        PrintError("WSAStartup");
    }

    if (SERVER_ROLE)
    {
        // ���� ��������� �������� ��� ������
        server();
    }
    else
    {
        // ����� ������� ������ ����� ���������� �������
        for( int i = 0; i < PACKET_NUM; i++ )
        {
            CreateThread(0,0,client,0,0,0),Sleep(CLIENT_TIMEOUT);
        }
    }

    // �������� ��������� ���������
    Sleep(TIME_LIMIT);

    // ����� ����������
    printf("Sent %d, received %d, total=%2.1f%%\n",PACKET_NUM, g_success,((float)g_success*100)/PACKET_NUM);

    return 0;
}
