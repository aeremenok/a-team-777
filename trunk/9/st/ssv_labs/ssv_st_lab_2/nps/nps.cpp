/*
    ���������� ������� ����������� �������
*/
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE			1024	// ������ ������ ��� ���������
#define PIPE_TIMEOUT	5000	// ����� ��������

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ���������� � ���������� ������� ���������
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnps pipe_name\nExample:");
    printf("\n\tnps ssv");
}

////////////////////////////////////////////////////////////////////////////////

int main(int argc, char** argv)
{
	BOOL fConnected;
	CHAR chRequest[BUFSIZE];
	DWORD cbBytesRead;
	BOOL fSuccess;
	HANDLE hPipe;
    char szPipeNamePrefix[] = "\\\\.\\pipe\\";
    char szPipeName[256];

    // ������������ ���������� ���������� ?
    if(argc != 2)
    {
        PrintUsage();
        return 1;
    }

    // ���������� ��� ������
    strcpy(szPipeName, szPipeNamePrefix);
    strcat(szPipeName, argv[1] );

	// ������� �����
	hPipe = CreateNamedPipe ( 
        szPipeName,				    // ��� ������
		PIPE_ACCESS_DUPLEX,			// ������ �� ������/������
		PIPE_TYPE_MESSAGE |			// ����� ������: ���������
			PIPE_READMODE_MESSAGE |	//		����� ������ ���������
			PIPE_WAIT,				//		����� ����������: ��������
		PIPE_UNLIMITED_INSTANCES,	// ������������ ���������� �����������
		BUFSIZE,					// ������ ��������� ������
		BUFSIZE,					// ������ �������� ������
		PIPE_TIMEOUT,				// ����� ��������
		NULL);						// ������������: ��� ������������

	// �������� ������ �� ������� ?
	if (hPipe == INVALID_HANDLE_VALUE)
    {
        // ����� � �������
        printf("ERROR: Failed to create pipe \'%s\'!\n", szPipeName);
		return 1;
    }

    printf("\nNamed Pipe server started successfully with pipe \'%s\'\n\n", szPipeName);

	// ����������� ����
	for (;;)
	{
		// �Ĩ� ����������� �������
		fConnected = ConnectNamedPipe(hPipe, NULL) ? TRUE : (GetLastError() == ERROR_PIPE_CONNECTED);

		// ����������� ��������� ?
		if (fConnected)
		{
			// ������ ��������� �������
			fSuccess = ReadFile (
                hPipe,		    // ��������� �� �����
				chRequest,		// ������� ����� ���������
				BUFSIZE,		// ������ ������
				&cbBytesRead,	// ���������� ����, ������� ������� �������
				NULL);			// �� �����٨���� ����/�����

			// �������� ���������� ���������
			chRequest[cbBytesRead] = '\0';
            printf("Data Received: %s\n", chRequest);

			// ������ ������ ?
			if ( !fSuccess || cbBytesRead == 0)
			{
				// ����� �� �����
				break;
			}

			// ������� ������ ������
			FlushFileBuffers(hPipe);

			// ����������� �� ������
			DisconnectNamedPipe(hPipe);
		}
		else
		{
			// ������ �� ����������� - ��������� �����
			CloseHandle(hPipe);
		}
	}

	// ��������� �����
	CloseHandle(hPipe);

	// ������ �������� ������, ��� ��������� =)
	// ����� � �������
	return 1;
}

////////////////////////////////////////////////////////////////////////////////
