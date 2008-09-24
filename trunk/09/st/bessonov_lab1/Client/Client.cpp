// Client.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <WinSock2.h>
#include <iostream>
#include <stdlib.h>
void PrintTime()
{
	SYSTEMTIME tm;
	GetLocalTime(&tm);
	std::cout<<tm.wHour<<":"<<tm.wMinute<<":"<<tm.wSecond<<":"<<tm.wMilliseconds<<" ";
}

int _tmain(int argc, _TCHAR* argv[])
{
	WSADATA wsad;
	WSAStartup(MAKEWORD(2,0),&wsad); //Configuretion of socket
	int nInterval=3000; // Interval of sending datagrams (Second parameter of command line)
	int nCount=2; // Count of datagrams (First parameter of command line)
	bool debug = true; // (Third parameter of command line)
	int port=3400;//default port
	unsigned long int ip;//=INADDR_LOOPBACK;

	switch(argc) {
	case 6:	
		if (strcmp(argv[5],"debug")==0)/*equal*/debug =true;
	case 5:
		port=atoi(argv[4]);
	case 4:
		ip = inet_addr(argv[3]);
	case 3:
		nInterval=atoi(argv[2]);
	case 2:
		nCount=atoi(argv[1]);
		break;
	}
	SOCKET s=socket(AF_INET,SOCK_DGRAM,0);
	if (s<0)
	{
		if(debug){PrintTime();std::cout<<"Fault to create socket\n";}
		return 0;
	}
	sockaddr_in addr;
	addr.sin_family=AF_INET;
	addr.sin_port=htons(port);
	if(argc >= 4)
	{
		addr.sin_addr.s_addr=ip;//htonl(INADDR_LOOPBACK);
		if(debug){std::cout<<(ip)<<":"<<port<<"\n";}
	}
	else
		addr.sin_addr.s_addr=htonl(INADDR_LOOPBACK);

	char buffer[1024];
	gethostname(buffer,1024);
	hostent *he = gethostbyname(buffer);
	struct in_addr ipp;
	CopyMemory(&ipp,he->h_addr,sizeof(in_addr));
	if(debug){std::cout<<"IP:"<<inet_ntoa(ipp)<<"\n";}
	if(debug){std::cout<<"Host:"<<buffer<<"\n";}
	char msg[]="Message";
	char msg1[10];
	char msg2[3];

	for (int i=0;i<nCount;i++)
	{
		Sleep(nInterval);
		PrintTime();
		strcpy(msg1,msg);
		itoa(i+1,msg2,10);
		strcat(msg1,msg2);
		std::cout<<"Datagram has been sent\n";
		sendto(s,msg1,sizeof(msg1),0,(struct sockaddr *)&addr,sizeof(addr));
		
	}
	closesocket(s);
	WSACleanup();
	return 0;
}

