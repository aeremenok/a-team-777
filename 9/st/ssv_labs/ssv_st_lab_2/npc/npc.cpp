
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
            printf( "Could not open pipe..." ); 
            return 0;
        }

        // ����� �����, �����Ĩ� 10 ������ � ��������� �٨

        if ( !WaitNamedPipe( pipeName, 10000 ) ) 
        { 
            printf( "Could not open pipe..." ); 
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
        printf( "SetNamedPipeHandleState failed" ); 
        return 0;
    }

    // �������� ������� ���
    printf( "Enter Client name: " );
    scanf( "%s", name );

    fSuccess = WriteFile( 
        hPipe,                  // �����
        name,					// ���������
        ( strlen( name ) + 1 ) * sizeof( char ), // ����� ���������
        &cbWritten,             // ���������� ����
        NULL );                 // ��� ���������
    if ( !fSuccess ) 
    {
        printf( "WriteFile failed" ); 
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

    // �Ĩ� ������� �������
    getch();

    // ��������� �����
    CloseHandle(hPipe); 

    return 0; 
}

////////////////////////////////////////////////////////////////////////////////
