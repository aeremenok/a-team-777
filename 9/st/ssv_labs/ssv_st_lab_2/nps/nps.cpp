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

int main(int argc, char** argv)
{
	BOOL fConnected;
	LPTSTR lpszPipename = "\\\\.\\pipe\\SsvPipe";	// ��� ������
	CHAR chRequest[BUFSIZE];
	DWORD cbBytesRead;
	BOOL fSuccess;
	HANDLE hPipe;

	// ������� �����
	hPipe = CreateNamedPipe ( 
		lpszPipename,				// ��� ������
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
        printf("ERROR: Failed to create pipe!\n");
		return 1;
    }

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
