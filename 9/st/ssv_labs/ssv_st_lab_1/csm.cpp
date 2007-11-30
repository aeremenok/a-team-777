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

#define     PACKET_NUM  ((DWORD)100) 		// вхякн оюйернб
#define     DATA_SIZE   ((DWORD)0x10000) 	// пюглеп гюопняю
#define     TIME_LIMIT  ((DWORD)1000) 		// нфхдюмхе нрберю
#define     SERV_TIME   ((DWORD)100) 		// бпелъ напюанрйх

////////////////////////////////////////////////////////////////////////////////

char		*SERVER_NAME;			// хлъ яепбепю
USHORT		SERVER_PORT;			// онпр яепбепю
unsigned	CLIENT_TIMEOUT;			// хмрепбюк лефдс гюопняюлх ян ярнпнмш йкхемрю
unsigned	QUEUE_SIZE;				// пюглеп нвепедх
bool		SERVER_ROLE = false;	// пнкэ
unsigned	g_success = 0;			// вхякн сяоеьмн напюанрюммшу гюопнянб

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм яннаыемхъ на ньхайе WINSOCK
int PrintError(const char *msg = 0)
{
    printf("Fatal error in %s, WSAError=%d\n", msg, WSAGetLastError());
    ExitThread(-1);
    return -1;
}

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм хмтнплюжхх н оюпюлерпюу гюосяйю опнцпюллш
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

// пюганп оюпюлерпнб йнлюмдмни ярпнйх
void clparse(int c, char** v)
{
    if (c == 4)
    {
        if (!strcmp(v[1], "-S"))
        {
            // гюосяй опнцпюллш йюй яепбепю
            QUEUE_SIZE = atoi( v[3] ), SERVER_PORT = atoi(v[2]), SERVER_ROLE = true;
            printf("Started as SERVER, port=%d\n", SERVER_PORT);
            return;
        }
    }

    if (c == 5)
    {
        if (!strcmp(v[1], "-C"))
        {
            // гюосяй опнцпюллш йюй йкхемрю
            CLIENT_TIMEOUT = atoi(v[4]), SERVER_PORT=atoi(v[3]), SERVER_NAME=v[2], printf(
                "Started as CLIENT, SERVER=%s, port=%d\n", SERVER_NAME,
                SERVER_PORT);
            return;
        }
    }

    // меопюбхкэмн оепедюмш оюпюлерпш - оевюрюел яопюбйс он пюанре я опнцпюллни
    PrintUsage();
}

////////////////////////////////////////////////////////////////////////////////

// нопедекемхе юдпеяю унярю
void resaddr(sockaddr_in *addr)
{
    hostent *he;

    ZeroMemory(addr, sizeof(sockaddr_in));

    // гюопня юдпеяю унярю он ецн хлемх
    !(he = gethostbyname(SERVER_NAME)) && PrintError("gethostname");
    CopyMemory(&addr->sin_addr,he->h_addr,sizeof(sockaddr_in));
    addr->sin_family = AF_INET, addr->sin_port = htons(SERVER_PORT);

    printf("host=%s, IP=%s\n", SERVER_NAME, inet_ntoa(addr->sin_addr));
}

////////////////////////////////////////////////////////////////////////////////

// пеюкхгюжхъ яепбепю
void server()
{
    char    hostname[BUFSIZ];
    sockaddr_in addr;
    SOCKET  sock;
    int     c = 0;              // яв╗рвхй йкхемрнб
    float   util;               // срхкхгюжхъ яепбепю
	DWORD   time1;              // бпелъ гюосяйю напюанрйх
    DWORD   time2;              // бпелъ гюбепьемхъ напюанрйх
	DWORD   timeBusy = 0;       // бпелъ гюмърнярх яепбепю (вхярюъ напюанрйю гюопнянб)
    DWORD   timeUp = 0;         // бпелъ ясыеярбнбюмхъ яепбепю я мювюкю напюанрйх оепбнцн гюопняю
    DWORD   timeStarted = 0;    // бпелъ мювюкю напюанрйх оепбнцн гюопняю
    bool    hadPacket = false;  // хмдхйюрнп опхундю оепбнцн оюйерю (мювюкн нряв╗рю срхкхгюжхх)

    // онксвемхе хлемх йнлоэчрепю, мю йнрнпнл гюосыемю опнцпюллю
    gethostname(hostname, BUFSIZ) && PrintError("gethostname");
    SERVER_NAME = new char[strlen(hostname)+1];
    strcpy(SERVER_NAME, hostname);
    resaddr(&addr);

    // нрйпшрхе яепбепмнцн янйерю
    SOCKET_ERROR == (sock = socket(AF_INET, SOCK_STREAM, 0)) && PrintError("socket");

    // опхбъгйю янйерю й кнйюкэмнлс юдпеяс
    SOCKET_ERROR == bind(sock, (sockaddr*)&addr, sizeof(addr)) && PrintError("bind");

    // бйкчвемхъ опняксьхбюмхъ яерх
    SOCKET_ERROR == listen(sock, QUEUE_SIZE) && PrintError("listen");

    for (;;)
    {
        char buf[DATA_SIZE];
        sockaddr_in addr;
        int sz = sizeof(addr);

        // янгдюмхе янедхмемхъ я йкхемрнл, опхякюбьхл гюопня
        SOCKET s = accept(sock, (sockaddr*)&addr, &sz);
        printf("accepted=%d\n", c++);

        //опхмърэ дюммше гюопняю нр йкхемрю
        recv(s, buf, DATA_SIZE, 0);

        // еякх мювюрю напюанрйю оепбнцн гюопняю - гюяейюел бпелъ ярюпрю
        if ( !hadPacket )
        {
            hadPacket = true;
            timeStarted = GetTickCount();
        }
        
        // нряевйю бпелемх оепед напюанрйни гюопняю
        time1 = GetTickCount();

        // гюдепфйю мю напюанрйс хмтнплюжхх
        Sleep(SERV_TIME);

        // нряевйю бпелемх оняке напюанрйх гюопняю
        time2 = GetTickCount();

        // бпелъ гюмърнярх яепбепю
        timeBusy += time2 - time1;

        // бпелъ пюанрш яепбепю
        timeUp = time2 - timeStarted;

        // пюяв╗р срхкхгюжхх яепбепю
        util = (float) timeBusy / (float) timeUp;
        util *= 100;

        printf("\nserver average utilization is %2.1f%%\n", util);

        // оняшкйю йкхемрс пегскэрюрнб напюанрйх гюопняю
        send(s, buf, DATA_SIZE, 0);
    }
}

////////////////////////////////////////////////////////////////////////////////

// пеюкхгюжхъ йкхемрю
DWORD WINAPI client(void *)
{
    SOCKET sock;
    sockaddr_in addr;
    char buf[DATA_SIZE];
    static int i = 0;
    DWORD id = GetCurrentThreadId();

    resaddr(&addr);

    printf("thread_id=%04X\n", i++);

    // янгдюмхе йкхемряйнцн янйерю
    SOCKET_ERROR == (sock = socket(AF_INET,SOCK_STREAM, 0 )) && PrintError("socket");

    // янедхмемхе я яепбепнл
    connect(sock, (sockaddr*) &addr, sizeof(sockaddr_in))
        && PrintError("connect");
    *((DWORD*)buf) = id;

    // оняшкйю дюммшу гюопняю яепбепс
    !send(sock, buf, DATA_SIZE, 0) && PrintError("send");

    // опхмърхе пегскэрюрнб напюанрйх гюопняю
    !recv(sock, buf, DATA_SIZE, 0) && PrintError("recv");
    *((DWORD*)buf) == id && printf("thread %04X success\n", *((DWORD*)buf)),
        g_success++;

    // гюйпшрхе йкхемряйнцн янйерю (мю яепбепе гюйпшрхе юбрнлюрхвеяйне)
    closesocket(sock);

    return 0;
}

////////////////////////////////////////////////////////////////////////////////

// рнвйю бундю
int main(int argc, char* argv[])
{
    WSADATA wsad;

    // пюганп оюпюлерпнб йнлюмдмни ярпнйх
    clparse(argc, argv);

    // хмхжхюкхгюжхъ ахакхнрейх WINSOCK
    if (WSAStartup(MAKEWORD(2,0), &wsad))
    {
        PrintError("WSAStartup");
    }

    if (SERVER_ROLE)
    {
        // еякх опнцпюллю гюосыемю йюй яепбеп
        server();
    }
    else
    {
        // хмюве янгдюрэ мсфмне вхякн йкхемряйху онрнйнб
        for( int i = 0; i < PACKET_NUM; i++ )
        {
            CreateThread(0,0,client,0,0,0),Sleep(CLIENT_TIMEOUT);
        }
    }

    // нфхдюмхе нйнмвюмхъ напюанрйх
    Sleep(TIME_LIMIT);

    // бшбнд ярюрхярхйх
    printf("Sent %d, received %d, total=%2.1f%%\n",PACKET_NUM, g_success,((float)g_success*100)/PACKET_NUM);

    return 0;
}
