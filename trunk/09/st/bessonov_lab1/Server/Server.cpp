// Server.cpp : Defines the entry point for the console application.
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
	int t_job=1000; // Time of working at one task (First argument)
	int nQueue=1; // Length of Queue (Second argument)
	int port=3400;//default port

	bool debug = false; //(Third argument)
	switch(argc) {
	case 5:
		if (strcmp(argv[4],"debug")==0)/*equal*/debug =true;
	case 4:
		port=atoi(argv[3]);
	case 3:	
		nQueue = atoi(argv[2]);
	case 2:
		t_job=atoi(argv[1]);
		break;
	}

	/**Creating of socket*/
	SOCKET s=socket(AF_INET,SOCK_DGRAM,0);
	if (s<0) {if(debug){PrintTime();std::cout<<"Fault to create socket\n";}	return 0;}
	
	/**Binding socket to port*/
	sockaddr_in addr;
	addr.sin_family=AF_INET;
	addr.sin_port=htons(port);
	addr.sin_addr.s_addr=htonl(INADDR_ANY);
	if (bind(s,(struct sockaddr *)&addr,sizeof(addr))<0)
	{if(debug){PrintTime();std::cout<<"Fault to bind socket\n";}	return 0;}


	//listen(s,nQueue);
	int bytes_read;
	DWORD timeTicStart,timeTic1,timeTic2;//
	DWORD timeProcessing=0,timeWorking;
	
	timeTicStart=GetTickCount();
	
	/** Changing size of input queue*/
	int len = sizeof(int); 
	int sizeOfInputQueue; 
	getsockopt(s,SOL_SOCKET,SO_RCVBUF,(char*)(&sizeOfInputQueue),&len); // Get current size
	if(debug){std::cout<<"Current buffer="<<sizeOfInputQueue<<"\n";}
	sizeOfInputQueue=nQueue;
	setsockopt(s,SOL_SOCKET,SO_RCVBUF,(char*)(&sizeOfInputQueue),len); // Set new size
	getsockopt(s,SOL_SOCKET,SO_RCVBUF,(char*)(&sizeOfInputQueue),&len); // Get size for check out
	if(debug){std::cout<<"Current buffer="<<sizeOfInputQueue<<"\n";}
	
	/**Get Ip-adress of interface*/
	char buffer[1024];
	gethostname(buffer,1024);
	hostent *he = gethostbyname(buffer);
	struct in_addr ipp;
	CopyMemory(&ipp,he->h_addr,sizeof(sockaddr_in));
	if(debug){std::cout<<"IP:"<<inet_ntoa(ipp)<<"\n";}
	if(debug){std::cout<<"Host:"<<buffer<<"\n";}
	if(debug){PrintTime(); std::cout<<"Start listen port "<<ntohs(addr.sin_port)<<"\n";}
	
	double util; // Average utilization of Server
	const int sizebuf=10;
	char buf[sizebuf];
	/**Start of receiving packages*/
	while (true)
	{
		bytes_read=recvfrom(s,buf,sizebuf,0,NULL,NULL);
		timeTic1=GetTickCount();
		if(debug){PrintTime(); std::cout<<"Received: "<<buf<<"\n"<<"Starting processing \n";}
		Sleep(t_job);
		timeTic2=GetTickCount();
		timeProcessing+=timeTic2-timeTic1;
		timeWorking=timeTic2-timeTicStart;
		util=(double)timeProcessing/(double)timeWorking;
		util*=100;
		std::cout<<"Utilization = "<<util<<"%\n";

	}
	closesocket(s);
	WSACleanup();
	return 0;
}

