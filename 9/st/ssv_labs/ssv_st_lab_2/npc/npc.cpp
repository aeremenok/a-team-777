
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

////////////////////////////////////////////////////////////////////////////////

#include <windows.h> 
#include <stdio.h>
#include <conio.h>
#include <tchar.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE     512 // пюглеп астепю
#define MAXSIZE     15  // люйяхлюкэмши пюглеп хлемх

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм хмтнплюжхх н оюпюлерпюу гюосяйю опнцпюллш
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnpc pipe_name\nExample:");
    printf("\n\tnpc test");
    ExitProcess(0);
}

////////////////////////////////////////////////////////////////////////////////

// рнвйю бундю
int main( int argc, char** argv ) 
{ 
    HANDLE hPipe; 
    char chBuf[BUFSIZE]; 
    BOOL fSuccess; 
    DWORD cbRead, cbWritten, dwMode; 
    char pipeName[200] = "\\\\.\\pipe\\"; 

    char name[MAXSIZE];  // хлъ дкъ оняшкйх мю яепбеп

    // пюгахпюел оюпюлерпш йнлюмдмни ярпнйх
    if ( argc != 2 )
    {
        PrintUsage();
    }
    
    strcat( pipeName, argv[1] );

    // ошрюеляъ нрйпшрэ йюмюк, фд╗л опх менаундхлнярх

    while ( TRUE ) 
    { 
        hPipe = CreateFile( 
            pipeName,   // хлъ йюмюкю
            GENERIC_READ |  // днярсо мю времхе х гюохяэ
                GENERIC_WRITE, 
            0,              // аег пюгдекемхъ
            NULL,           // аег юрпхасрнб аегноюямнярх
            OPEN_EXISTING,  // нрйпшбюрэ ясыеярбсчыхи йюмюк 
            0,              // юрпхасрш он слнквюмхч
            NULL);          // ме хяонкэгнбюрэ ьюакнм

        // еякх янгдюрэ йюмюк сдюкняэ - асдел опнднкфюрэ, хмюве асдел йпсрхрэяъ б жхйке дюкэье

        if ( hPipe != INVALID_HANDLE_VALUE ) 
        {
            break; 
        }

        // еякх опнхгнькю ньхайю, х щрн ме ньхайю гюмърнярх - бшундхл

        if ( GetLastError() != ERROR_PIPE_BUSY ) 
        {
            printf( "Could not open pipe..." ); 
            return 0;
        }

        // йюмюк гюмър, онднфд╗л 10 яейсмд х онопнасел еы╗

        if ( !WaitNamedPipe( pipeName, 10000 ) ) 
        { 
            printf( "Could not open pipe..." ); 
            return 0;
        } 
    } 

    // ондйкчвемхе й йюмюкс опнхгбедемн, сярюмюбкхбюел пефхл времхъ

    dwMode = PIPE_READMODE_MESSAGE; 
    fSuccess = SetNamedPipeHandleState( 
        hPipe,    // йюмюк
        &dwMode,  // пефхл днярсою
        NULL,     // мер кхлхрю аюир
        NULL );   // мер кхлхрю бпелемх
    
    // еякх ме сдюкняэ ялемхрэ пефхл йюмюкю - бшундхл

    if ( !fSuccess ) 
    {
        printf( "SetNamedPipeHandleState failed" ); 
        return 0;
    }

    // оняшкюел яепбепс хлъ
    printf( "Enter Client name: " );
    scanf( "%s", name );

    fSuccess = WriteFile( 
        hPipe,                  // йюмюк
        name,					// яннаыемхе
        ( strlen( name ) + 1 ) * sizeof( char ), // дкхмю яннаыемхъ
        &cbWritten,             // йнкхвеярбн аюир
        NULL );                 // аег мюкнфемхъ
    if ( !fSuccess ) 
    {
        printf( "WriteFile failed" ); 
        return 0;
    }

    do 
    { 
        // вхрюел хг йюмюкю нрбер яепбепю

        fSuccess = ReadFile( 
            hPipe,    // йюмюк
            chBuf,    // яннаыемхе
            BUFSIZE * sizeof( char ),  // пюглеп яннаыемхъ
            &cbRead,  // йнкхвеярбн аюир
            NULL );    // аег мюкнфемхъ

        if ( !fSuccess && GetLastError() != ERROR_MORE_DATA ) 
        {
            break; 
        }

        printf( "Server answered \'%s\'\n", chBuf ); 
    } 
    while ( !fSuccess );  // онбрнпъел онйю ERROR_MORE_DATA 

    // фд╗л мюфюрхъ йкюбхьх
    getch();

    // гюйпшбюел йюмюк
    CloseHandle(hPipe); 

    return 0; 
}

////////////////////////////////////////////////////////////////////////////////
