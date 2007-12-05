/*
    пеюкхгюжхъ йкхемрю хлемнбюммшу йюмюкнб
*/
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм хмтнплюжхх н оюпюлерпюу гюосяйю опнцпюллш
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnpc pipe_name\nExample:");
    printf("\n\tnpc ssv\"");
}

////////////////////////////////////////////////////////////////////////////////

// рнвйю бундю
int main( int argc, char** argv )
{
    HANDLE hFile;
    BOOL flg;
    DWORD dwWrite;
    char szPipeNamePrefix[] = "\\\\.\\pipe\\";
    char szPipeName[256];

    // меопюбхкэмне йнкхвеярбн оюпюлерпнб ?
    if(argc != 2)
    {
        PrintUsage();
        return 1;
    }

    // янярюбкъел хлъ йюмюкю
    strcpy(szPipeName, szPipeNamePrefix);
    strcat(szPipeName, argv[1] );

	// янгдю╗л тюик
    hFile = CreateFile(
        szPipeName,     // хлъ йюмюкю
        GENERIC_WRITE,  // рхо днярсою: гюохяэ
        0,              // пюгдекемхе днярсою: мер
        NULL,           // аегноюямнярэ: мер
        OPEN_EXISTING,  // нрйпшбюрэ ясыеярбсчыхи тюик
        0,              // ткюцх х юрпхасрш: мер
        NULL);          // ьюакнм: мер

	// йнохпсел ярпнйс дкъ оепедювх
    //strcpy(szMessage, argv[2]);

	// йюмюк ме ясыеярбсер ?
    if(hFile == INVALID_HANDLE_VALUE)
    {
        printf("ERROR: Pipe \'%s\' doesn't exist!\n", szPipeName );
    }
    else
    {
        printf("Connected to pipe \'%s\'...\n", szPipeName );
        printf("Type a message and press ENTER to send it or press Ctrl-C to quit.\n", szPipeName );

        // аеяйнмевмши жхйк
        for(;;)
        {
            char szMessage[200];    // рейяр яннаыемхъ

            printf(">");

            // вхрюел я йнмянкх яннаыемхе дкъ оепедювх 
            scanf("%s", szMessage);

		    // гюохяэ ярпнйх б йюмюк
            flg = WriteFile(hFile, szMessage, strlen(szMessage), &dwWrite, NULL);
            
		    // гюохяэ ме сдюкюяэ ?
            if (FALSE == flg)
            {
                printf("ERROR: Cannot write to pipe \'%s\'!\n", szPipeName);
            }
            else
            {
                printf("\nSuccessfull write \'%s\' into pipe \'%s\'!\n\n", szMessage, szPipeName);
            }
        }

	    // гюйпшбюел йюмюк
        CloseHandle(hFile);
    }

	// гюбепьюел пюанрс
	return 0;
}

////////////////////////////////////////////////////////////////////////////////
