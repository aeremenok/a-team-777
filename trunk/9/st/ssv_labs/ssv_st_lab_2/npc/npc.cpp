
#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

int main()
{
    HANDLE hFile;
    BOOL flg;
    DWORD dwWrite;
    char szPipeUpdate[200];

	// ������� ����
    hFile = CreateFile(
        "\\\\.\\pipe\\SsvPipe", // ��� ������
        GENERIC_WRITE,          // ��� �������: ������
        0,                      // ���������� �������: ���
        NULL,                   // ������������: ���
        OPEN_EXISTING,          // ��������� ������������ ����
        0,                      // ����� � ��������: ���
        NULL);                  // ������: ���

	// �������� ������ ��� ��������
    strcpy(szPipeUpdate, "!!!Hello, Pipes!!!");

	// ����� �� ���������� ?
    if(hFile == INVALID_HANDLE_VALUE)
    {
		printf("ERROR: Named Pipe Client: No such pipe exists!\n" );
    }
    else
    {
		// ������ ������ � �����
        flg = WriteFile(hFile, szPipeUpdate, strlen(szPipeUpdate), &dwWrite, NULL);
        
		// ������ �� ������� ?
        if (FALSE == flg)
        {
			printf("ERROR: Named Pipe Client: Cannot write to pipe!\n");
        }
        else
        {
			printf("INFO:  Named Pipe Client: Successfull write into pipe!\n");
        }
        
		// ��������� �����
        CloseHandle(hFile);
    }

	// ��������� ������
	return 0;
}