// npc.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

/*
int main(int argc, char* argv[])
{
	printf("Hello World!\n");
	return 0;
}
*/

#include <windows.h>
#include <stdio.h>

#define BUFSIZE 1024
#define PIPE_TIMEOUT 5000

int main()
{
    HANDLE hFile;
    BOOL flg;
    DWORD dwWrite;
    char szPipeUpdate[200];
    hFile = CreateFile("\\\\.\\pipe\\SamplePipe", GENERIC_WRITE,
        0, NULL, OPEN_EXISTING,
        0, NULL);

    strcpy(szPipeUpdate,"Data from Named Pipe client for createnamedpipe");

    if(hFile == INVALID_HANDLE_VALUE)
    {
        printf("CreateFile failed for Named Pipe client\n" );
    }
    else
    {
        flg = WriteFile(hFile, szPipeUpdate, strlen(szPipeUpdate), &dwWrite, NULL);
        
        if (FALSE == flg)
        {
            printf("WriteFile failed for Named Pipe client\n");
        }
        else
        {
            printf("WriteFile succeeded for Named Pipe client\n");
        }
        
        CloseHandle(hFile);
    }

	return 0;
}