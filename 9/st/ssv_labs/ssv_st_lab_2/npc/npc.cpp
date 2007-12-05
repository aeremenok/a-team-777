/*
    ���������� ������� ����������� �������
*/
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

////////////////////////////////////////////////////////////////////////////////

// ����� �� ����� ���������� � ���������� ������� ���������
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnpc pipe_name message\nExample:");
    printf("\n\tnpc ssv \"!!!hello pipes!!!\"");
}

////////////////////////////////////////////////////////////////////////////////

// ����� �����
int main( int argc, char** argv )
{
    HANDLE hFile;
    BOOL flg;
    DWORD dwWrite;
    char szPipeNamePrefix[] = "\\\\.\\pipe\\";
    char szPipeName[256];
    char szMessage[200];

    // ������������ ���������� ���������� ?
    if(argc != 3)
    {
        PrintUsage();
        return 1;
    }

    // ���������� ��� ������
    strcpy(szPipeName, szPipeNamePrefix);
    strcat(szPipeName, argv[1] );

	// ������� ����
    hFile = CreateFile(
        szPipeName,     // ��� ������
        GENERIC_WRITE,  // ��� �������: ������
        0,              // ���������� �������: ���
        NULL,           // ������������: ���
        OPEN_EXISTING,  // ��������� ������������ ����
        0,              // ����� � ��������: ���
        NULL);          // ������: ���

	// �������� ������ ��� ��������
    strcpy(szMessage, argv[2]);

	// ����� �� ���������� ?
    if(hFile == INVALID_HANDLE_VALUE)
    {
        printf("ERROR: Pipe \'%s\' doesn't exist!\n", szPipeName );
    }
    else
    {
		// ������ ������ � �����
        flg = WriteFile(hFile, szMessage, strlen(szMessage), &dwWrite, NULL);
        
		// ������ �� ������� ?
        if (FALSE == flg)
        {
            printf("ERROR: Cannot write to pipe \'%s\'!\n", szPipeName);
        }
        else
        {
            printf("\nSuccessfull write \'%s\' into pipe \'%s\'!\n", szMessage, szPipeName);
        }
        
		// ��������� �����
        CloseHandle(hFile);
    }

	// ��������� ������
	return 0;
}

////////////////////////////////////////////////////////////////////////////////
