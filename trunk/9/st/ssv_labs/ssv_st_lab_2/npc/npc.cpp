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
    printf("\tnpc pipe_name\nExample:");
    printf("\n\tnpc ssv\"");
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

    // ������������ ���������� ���������� ?
    if(argc != 2)
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
    //strcpy(szMessage, argv[2]);

	// ����� �� ���������� ?
    if(hFile == INVALID_HANDLE_VALUE)
    {
        printf("ERROR: Pipe \'%s\' doesn't exist!\n", szPipeName );
    }
    else
    {
        printf("Connected to pipe \'%s\'...\n", szPipeName );
        printf("Type a message and press ENTER to send it or press Ctrl-C to quit.\n", szPipeName );

        // ����������� ����
        for(;;)
        {
            char szMessage[200];    // ����� ���������

            printf(">");

            // ������ � ������� ��������� ��� �������� 
            scanf("%s", szMessage);

		    // ������ ������ � �����
            flg = WriteFile(hFile, szMessage, strlen(szMessage), &dwWrite, NULL);
            
		    // ������ �� ������� ?
            if (FALSE == flg)
            {
                printf("ERROR: Cannot write to pipe \'%s\'!\n", szPipeName);
            }
            else
            {
                printf("\nSuccessfull write \'%s\' into pipe \'%s\'!\n\n", szMessage, szPipeName);
            }
        }

	    // ��������� �����
        CloseHandle(hFile);
    }

	// ��������� ������
	return 0;
}

////////////////////////////////////////////////////////////////////////////////
