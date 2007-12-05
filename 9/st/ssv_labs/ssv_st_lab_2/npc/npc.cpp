
#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

int main()
{
    HANDLE hFile;
    BOOL flg;
    DWORD dwWrite;
    char szPipeUpdate[200];

	// янгдю╗л тюик
    hFile = CreateFile(
        "\\\\.\\pipe\\SsvPipe", // хлъ йюмюкю
        GENERIC_WRITE,          // рхо днярсою: гюохяэ
        0,                      // пюгдекемхе днярсою: мер
        NULL,                   // аегноюямнярэ: мер
        OPEN_EXISTING,          // нрйпшбюрэ ясыеярбсчыхи тюик
        0,                      // ткюцх х юрпхасрш: мер
        NULL);                  // ьюакнм: мер

	// йнохпсел ярпнйс дкъ оепедювх
    strcpy(szPipeUpdate, "!!!Hello, Pipes!!!");

	// йюмюк ме ясыеярбсер ?
    if(hFile == INVALID_HANDLE_VALUE)
    {
		printf("ERROR: Named Pipe Client: No such pipe exists!\n" );
    }
    else
    {
		// гюохяэ ярпнйх б йюмюк
        flg = WriteFile(hFile, szPipeUpdate, strlen(szPipeUpdate), &dwWrite, NULL);
        
		// гюохяэ ме сдюкюяэ ?
        if (FALSE == flg)
        {
			printf("ERROR: Named Pipe Client: Cannot write to pipe!\n");
        }
        else
        {
			printf("INFO:  Named Pipe Client: Successfull write into pipe!\n");
        }
        
		// гюйпшбюел йюмюк
        CloseHandle(hFile);
    }

	// гюбепьюел пюанрс
	return 0;
}