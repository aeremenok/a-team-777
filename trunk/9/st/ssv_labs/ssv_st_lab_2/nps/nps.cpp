
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

////////////////////////////////////////////////////////////////////////////////

#include <windows.h> 
#include <stdio.h> 
#include <tchar.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE 4096

////////////////////////////////////////////////////////////////////////////////

DWORD WINAPI InstanceThread( LPVOID ); 
VOID GetAnswerToRequest( LPSTR, LPSTR, LPDWORD ); 

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ���������� � ���������� ������� ���������
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnps pipe_name\nExample:");
    printf("\n\tnps test");
    ExitProcess(0);
}

////////////////////////////////////////////////////////////////////////////////

int main( int argc, char** argv ) 
{ 
    BOOL fConnected; 
    DWORD dwThreadId; 
    HANDLE hPipe, hThread; 
    char pipeName[200] = "\\\\.\\pipe\\"; 

    // ��������� ��������� ��������� ������
    if ( argc != 2 )
    {
        PrintUsage();
    }

    strcat( pipeName, argv[1] );

    // � ����� ��������� ����� � ��������� ����������� �������.
    // ��� ����������� ������� ��������� ����� ��� ������� � ���,
    // � ���� �����������.

    for (;;) 
    { 
        // ������� �����
        hPipe = CreateNamedPipe( 
            pipeName,             // ��� ������
            PIPE_ACCESS_DUPLEX,       // ������ �� ������ � ������
            PIPE_TYPE_MESSAGE |       // ����� ��� ���������
                PIPE_READMODE_MESSAGE |   // ����� ������ ���������
                PIPE_WAIT,                // ����� ����������
            PIPE_UNLIMITED_INSTANCES, // ������������ ���������� �����������
            BUFSIZE,                  // ������ ��������� ������
            BUFSIZE,                  // ������ �������� ������
            NMPWAIT_USE_DEFAULT_WAIT, // �������� �������
            NULL );                    // ������������

        // ������� ����� �� ������� - �������
        if ( hPipe == INVALID_HANDLE_VALUE ) 
        {
            printf( "CreatePipe failed..." ); 
            return 0;
        }

        // ������� ����������� �������. ��� ������ ������� ���������� 
        // ��������� ��������. ���� ������� ������� ����, GetLastError
        // ���ͨ� ERROR_PIPE_CONNECTED.

        fConnected = ConnectNamedPipe( hPipe, NULL ) ? TRUE : ( GetLastError() == ERROR_PIPE_CONNECTED ); 

        if ( fConnected ) 
        { 
            // ������� ����� ��� ������� �������
            hThread = CreateThread( 
                NULL,              // ������������
                0,                 // ������ ����� �� ���������
                InstanceThread,    // ������� ������
                (LPVOID) hPipe,    // �������� ������
                0,                 // �� ����� =)
                &dwThreadId );     // ������������� ������

            if ( hThread == NULL ) 
            {
                printf( "CreateThread failed..." ); 
                return 0;
            }
            else 
            {
                CloseHandle( hThread ); 
            }
        } 
        else 
        {
            // ������ �� ���ب� - ��������� �����
            CloseHandle( hPipe ); 
        }
    } 

    return 1; 
} 

////////////////////////////////////////////////////////////////////////////////

DWORD WINAPI InstanceThread(LPVOID lpvParam) 
{ 
    char chRequest[BUFSIZE]; 
    char chReply[BUFSIZE]; 
    DWORD cbBytesRead, cbReplyBytes, cbWritten; 
    BOOL fSuccess; 
    HANDLE hPipe; 

    // �������� ������ - ��������� �� �����
    hPipe = (HANDLE) lpvParam; 

    while ( TRUE ) 
    { 
        // ������ ������ ������� �� ������
        fSuccess = ReadFile( 
            hPipe,        // �����
            chRequest,    // �����
            BUFSIZE * sizeof( char ), // ������ ������
            &cbBytesRead, // ����� ����
            NULL);        // ��� ����������

        if ( !fSuccess || cbBytesRead == 0 ) 
        {
            break; 
        }

        // �������� ����� ��� �������
        GetAnswerToRequest( chRequest, chReply, &cbReplyBytes ); 

        // ����� ����� � ����� 
        fSuccess = WriteFile( 
            hPipe,        
            chReply,      
            cbReplyBytes, 
            &cbWritten,   
            NULL );       

        if ( !fSuccess || cbReplyBytes != cbWritten )
        {
            break; 
        }
    } 

    // ������ ����� ������, ����� ��������� ������� ��������� ��� ���������� 
    // ����� �����������. ����� ������������� � ��������� �����.

    FlushFileBuffers( hPipe ); 
    DisconnectNamedPipe( hPipe ); 
    CloseHandle( hPipe ); 

    return 1;
}

////////////////////////////////////////////////////////////////////////////////

VOID GetAnswerToRequest( LPSTR chRequest, LPSTR chReply, LPDWORD pchBytes )
{
    printf( "Client named \'%s\' connected...\n", chRequest ) ;
    sprintf( chReply, "Welcome to the server, %s!", chRequest );

    *pchBytes = ( lstrlen( chReply ) + 1 ) * sizeof( char );
}

////////////////////////////////////////////////////////////////////////////////
