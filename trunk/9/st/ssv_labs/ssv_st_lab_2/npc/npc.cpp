
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

////////////////////////////////////////////////////////////////////////////////

#include <windows.h> 
#include <stdio.h>
#include <conio.h>
#include <tchar.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE     512 // ������ ������
#define MAXSIZE     15  // ������������ ������ �����

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ���������� � ���������� ������� ���������
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnpc pipe_name\nExample:");
    printf("\n\tnpc test");
    ExitProcess(0);
}

////////////////////////////////////////////////////////////////////////////////

// ����� �����
int main( int argc, char** argv ) 
{ 
    HANDLE hPipe; 
    char chBuf[BUFSIZE]; 
    BOOL fSuccess; 
    DWORD cbRead, cbWritten, dwMode; 
    char pipeName[200] = "\\\\.\\pipe\\"; 

    char name[MAXSIZE];  // ��� ��� ������� �� ������

    // ��������� ��������� ��������� ������
    if ( argc != 2 )
    {
        PrintUsage();
    }
    
    strcat( pipeName, argv[1] );

    // �������� ������� �����, �Ĩ� ��� �������������

    while ( TRUE ) 
    { 
        hPipe = CreateFile( 
            pipeName,   // ��� ������
            GENERIC_READ |  // ������ �� ������ � ������
                GENERIC_WRITE, 
            0,              // ��� ����������
            NULL,           // ��� ��������� ������������
            OPEN_EXISTING,  // ��������� ������������ ����� 
            0,              // �������� �� ���������
            NULL);          // �� ������������ ������

        // ���� ������� ����� ������� - ����� ����������, ����� ����� ��������� � ����� ������

        if ( hPipe != INVALID_HANDLE_VALUE ) 
        {
            break; 
        }

        // ���� ��������� ������, � ��� �� ������ ��������� - �������

        if ( GetLastError() != ERROR_PIPE_BUSY ) 
        {
            printf( "Could not open pipe...\n" ); 
            return 0;
        }

        // ����� �����, �����Ĩ� 10 ������ � ��������� �٨

        if ( !WaitNamedPipe( pipeName, 10000 ) ) 
        { 
            printf( "Could not open pipe...\n" ); 
            return 0;
        } 
    } 

    // ����������� � ������ �����������, ������������� ����� ������

    dwMode = PIPE_READMODE_MESSAGE; 
    fSuccess = SetNamedPipeHandleState( 
        hPipe,    // �����
        &dwMode,  // ����� �������
        NULL,     // ��� ������ ����
        NULL );   // ��� ������ �������
    
    // ���� �� ������� ������� ����� ������ - �������

    if ( !fSuccess ) 
    {
        printf( "SetNamedPipeHandleState failed\n" ); 
        return 0;
    }

    printf( "Type in a command to send. Type \'quit\' to stop client.\n" );

    // ����������� ����
    for(;;)
    {
        // �������� ������� ���
        printf( ">" );
        scanf( "%s", name );

        // ������� ��� ������� ������, �� ����� ����� =)
        if ( !strcmp(name, "quit" ) )
        {
            break;
        }

        fSuccess = WriteFile( 
            hPipe,                  // �����
            name,					// ���������
            ( strlen( name ) + 1 ) * sizeof( char ), // ����� ���������
            &cbWritten,             // ���������� ����
            NULL );                 // ��� ���������
        if ( !fSuccess ) 
        {
            printf( "WriteFile failed.\n" ); 
            return 0;
        }

        do 
        { 
            // ������ �� ������ ����� �������

            fSuccess = ReadFile( 
                hPipe,    // �����
                chBuf,    // ���������
                BUFSIZE * sizeof( char ),  // ������ ���������
                &cbRead,  // ���������� ����
                NULL );    // ��� ���������

            if ( !fSuccess && GetLastError() != ERROR_MORE_DATA ) 
            {
                break; 
            }

            printf( "Server answered \'%s\'\n", chBuf ); 
        } 
        while ( !fSuccess );  // ��������� ���� ERROR_MORE_DATA 
    }

    // ��������� �����
    CloseHandle(hPipe); 

    return 0; 
}

////////////////////////////////////////////////////////////////////////////////
