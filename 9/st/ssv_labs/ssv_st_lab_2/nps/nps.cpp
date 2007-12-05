/*
    пеюкхгюжхъ яепбепю хлемнбюммшу йюмюкнб
*/
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

#include <windows.h>
#include <stdio.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE			1024	// пюглеп астепю дкъ яннаыемхъ
#define PIPE_TIMEOUT	5000	// бпелъ нфхдюмхъ

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм хмтнплюжхх н оюпюлерпюу гюосяйю опнцпюллш
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

    // меопюбхкэмне йнкхвеярбн оюпюлерпнб ?
    if(argc != 2)
    {
        PrintUsage();
        return 1;
    }

    // янярюбкъел хлъ йюмюкю
    strcpy(szPipeName, szPipeNamePrefix);
    strcat(szPipeName, argv[1] );

	// янгдю╗л йюмюк
	hPipe = CreateNamedPipe ( 
        szPipeName,				    // хлъ йюмюкю
		PIPE_ACCESS_DUPLEX,			// днярсо мю времхе/гюохяэ
		PIPE_TYPE_MESSAGE |			// пефхл йюмюкю: яннаыемхъ
			PIPE_READMODE_MESSAGE |	//		пефхл времхъ яннаыемхи
			PIPE_WAIT,				//		пефхл акнйхпнбйх: нфхдюмхе
		PIPE_UNLIMITED_INSTANCES,	// люйяхлюкэмне йнкхвеярбн щйгелокъпнб
		BUFSIZE,					// пюглеп бшундмнцн астепю
		BUFSIZE,					// пюглеп бундмнцн астепю
		PIPE_TIMEOUT,				// бпелъ нфхдюмхъ
		NULL);						// аегноюямнярэ: аег аегноюямнярх

	// янгдюмхе йюмюкю ме сдюкняэ ?
	if (hPipe == INVALID_HANDLE_VALUE)
    {
        // бшунд я ньхайни
        printf("ERROR: Failed to create pipe \'%s\'!\n", szPipeName);
		return 1;
    }

    printf("\nNamed Pipe server started successfully with pipe \'%s\'\n\n", szPipeName);

	// аеяйнмевмши жхйк
	for (;;)
	{
		// фд╗л ондйкчвемхъ йкхемрю
		fConnected = ConnectNamedPipe(hPipe, NULL) ? TRUE : (GetLastError() == ERROR_PIPE_CONNECTED);

		// ондйкчвемхе опнхгнькн ?
		if (fConnected)
		{
			// вхрюел яннаыемхе йкхемрю
			fSuccess = ReadFile (
                hPipe,		    // сйюгюрекэ мю йюмюк
				chRequest,		// бундмни астеп яннаыемхи
				BUFSIZE,		// пюглеп астепю
				&cbBytesRead,	// йнкхвеярбн аюир, йнрнпше сдюкняэ явхрюрэ
				NULL);			// ме янблеы╗ммши ббнд/бшбнд

			// оевюрюел онксвеммне яннаыемхе
			chRequest[cbBytesRead] = '\0';
            printf("Data Received: %s\n", chRequest);

			// вхрюрэ мевецн ?
			if ( !fSuccess || cbBytesRead == 0)
			{
				// бшунд хг жхйкю
				break;
			}

			// нвхыюел астепю йюмюкю
			FlushFileBuffers(hPipe);

			// нрйкчвюеляъ нр йюмюкю
			DisconnectNamedPipe(hPipe);
		}
		else
		{
			// йкхемр ме ондйкчвхкяъ - гюйпшбюел йюмюк
			CloseHandle(hPipe);
		}
	}

	// гюйпшбюел йюмюк
	CloseHandle(hPipe);

	// яепбеп гюбепьхк пюанрс, врн мерхохвмн =)
	// бшунд я ньхайни
	return 1;
}

////////////////////////////////////////////////////////////////////////////////
