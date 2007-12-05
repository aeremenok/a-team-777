// nps.cpp : Defines the entry point for the console application.
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
    BOOL fConnected;
    LPTSTR lpszPipename = "\\\\.\\pipe\\SamplePipe";
    CHAR chRequest[BUFSIZE];
    DWORD cbBytesRead;
    BOOL fSuccess;
    HANDLE hPipe;

    hPipe = CreateNamedPipe ( lpszPipename,
        PIPE_ACCESS_DUPLEX, // read/write access
        PIPE_TYPE_MESSAGE | // message type pipe
        PIPE_READMODE_MESSAGE | // message-read mode
        PIPE_WAIT, // blocking mode
        PIPE_UNLIMITED_INSTANCES, // max. instances
        BUFSIZE, // output buffer size
        BUFSIZE, // input buffer size
        PIPE_TIMEOUT, // client time-out
        NULL); // no security attribute

    if (hPipe == INVALID_HANDLE_VALUE)
    return true;

    for (;;)
    {
        // Trying connectnamedpipe in sample for CreateNamedPipe
        // Wait for the client to connect; if it succeeds,
        // the function returns a nonzero value. If the function returns
        // zero, GetLastError returns ERROR_PIPE_CONNECTED.

        fConnected = ConnectNamedPipe(hPipe, NULL) ?
        TRUE : (GetLastError() == ERROR_PIPE_CONNECTED);

        if (fConnected)
        {
            fSuccess = ReadFile (hPipe, // handle to pipe
            chRequest, // buffer to receive data
            BUFSIZE, // size of buffer
            &cbBytesRead, // number of bytes read
            NULL); // not overlapped I/O

            chRequest[cbBytesRead] = '\0';
            printf("Data Received: %s\n",chRequest);

            if (! fSuccess || cbBytesRead == 0)
            break;

            FlushFileBuffers(hPipe);
            DisconnectNamedPipe(hPipe);
        }
        else

        // The client could not connect in the CreateNamedPipe sample, so close the pipe.
        CloseHandle(hPipe);
    }

    CloseHandle(hPipe);

    return 1;
}