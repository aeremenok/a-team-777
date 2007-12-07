
////////////////////////////////////////////////////////////////////////////////

#include "stdafx.h"

////////////////////////////////////////////////////////////////////////////////

#include <windows.h> 
#include <stdio.h> 
#include <tchar.h>

////////////////////////////////////////////////////////////////////////////////

#define BUFSIZE 4096

////////////////////////////////////////////////////////////////////////////////

DWORD WINAPI InstanceThread( LPVOID ); 
VOID GetAnswerToRequest( LPSTR, LPSTR, LPDWORD ); 

////////////////////////////////////////////////////////////////////////////////

// бшбнд мю щйпюм хмтнплюжхх н оюпюлерпюу гюосяйю опнцпюллш
void PrintUsage()
{
    printf("Program usage:\n");
    printf("\tnps pipe_name\nExample:");
    printf("\n\tnps test");
    ExitProcess(0);
}

////////////////////////////////////////////////////////////////////////////////

int main( int argc, char** argv ) 
{ 
    BOOL fConnected; 
    DWORD dwThreadId; 
    HANDLE hPipe, hThread; 
    char pipeName[200] = "\\\\.\\pipe\\"; 

    // пюгахпюел оюпюлерпш йнлюмдмни ярпнйх
    if ( argc != 2 )
    {
        PrintUsage();
    }

    strcat( pipeName, argv[1] );

    printf( "server started\n" );

    // б жхйке янгдю╗ряъ йюмюк х нфхдюеряъ ондйкчвемхе йкхемрю.
    // опх ондйкчвемхх йкхемрю янгдю╗ряъ онрнй дкъ наыемхъ я мхл,
    // х жхйк онбрнпъеряъ.

    for (;;) 
    { 
        printf( "CreateNamedPipe... " );
        // янгдю╗л йюмюк
        hPipe = CreateNamedPipe( 
            pipeName,             // хлъ йюмюкю
            PIPE_ACCESS_DUPLEX,       // днярсо мю времхе х гюохяэ
            PIPE_TYPE_MESSAGE |       // йюмюк дкъ яннаыемхи
                PIPE_READMODE_MESSAGE |   // пефхл времхъ яннаыемхи
                PIPE_WAIT,                // пефхл акнйхпнбйх
            PIPE_UNLIMITED_INSTANCES, // люйяхлюкэмне йнкхвеярбн щйгелокъпнб
            BUFSIZE,                  // пюглеп бшундмнцн астепю
            BUFSIZE,                  // пюглеп бундмнцн астепю
            NMPWAIT_USE_DEFAULT_WAIT, // гюдепфйю йкхемрю
            NULL );                   // аегноюямнярэ
        printf( "Done.\n" );

        // янгдюрэ йюмюк ме сдюкняэ - бшундхл
        if ( hPipe == INVALID_HANDLE_VALUE ) 
        {
            printf( "CreatePipe failed...\n" ); 
            return 0;
        }

        // нфхдюел ондйкчвемхъ йкхемрю. опх сяоеуе тсмйжхъ бнгбпюыюер 
        // мемскебне гмювемхе. еякх тсмйжхъ бепмскю мнкэ, GetLastError
        // бепм╗р ERROR_PIPE_CONNECTED.

        printf( "Waiting for connection..." );
        fConnected = ConnectNamedPipe( hPipe, NULL ) ? TRUE : ( GetLastError() == ERROR_PIPE_CONNECTED ); 
        printf( " Done.\n" );

        if ( fConnected ) 
        { 
            // янгдю╗л онрнй дкъ дюммнцн йкхемрю
            hThread = CreateThread( 
                NULL,              // аегноюямнярэ
                0,                 // пюглеп ярейю он слнквюмхч
                InstanceThread,    // тсмйжхъ онрнйю
                (LPVOID) hPipe,    // оюпюлерп онрнйю
                0,                 // ме яоюрэ =)
                &dwThreadId );     // хдемрхтхйюрнп онрнйю

            if ( hThread == NULL ) 
            {
                printf( "CreateThread failed...\n" ); 
                return 0;
            }
            else 
            {
                CloseHandle( hThread ); 
            }
        } 
        else 
        {
            // йкхемр ме опхь╗к - гюйпшбюел йюмюк
            CloseHandle( hPipe ); 
        }
    } 

    return 1; 
} 

////////////////////////////////////////////////////////////////////////////////

DWORD WINAPI InstanceThread(LPVOID lpvParam) 
{ 
    char chRequest[BUFSIZE]; 
    char chReply[BUFSIZE]; 
    DWORD cbBytesRead, cbReplyBytes, cbWritten; 
    BOOL fSuccess; 
    HANDLE hPipe; 

    // оюпюлерп онрнйю - сйюгюрекэ мю йюмюк
    hPipe = (HANDLE) lpvParam; 

    while ( TRUE ) 
    { 
        // вхрюел гюопня йкхемрю хг йюмюкю
        fSuccess = ReadFile( 
            hPipe,        // йюмюк
            chRequest,    // астеп
            BUFSIZE * sizeof( char ), // пюглеп астепю
            &cbBytesRead, // вхякн аюир
            NULL);        // аег оепейпшрхъ

        if ( !fSuccess || cbBytesRead == 0 ) 
        {
            break; 
        }

        // онксвюел нрбер дкъ гюопняю
        GetAnswerToRequest( chRequest, chReply, &cbReplyBytes ); 

        // охьел нрбер б астеп 
        fSuccess = WriteFile( 
            hPipe,        
            chReply,      
            cbReplyBytes, 
            &cbWritten,   
            NULL );       

        if ( !fSuccess || cbReplyBytes != cbWritten )
        {
            break; 
        }
    } 

    // декюел яапня йюмюкю, врнаш онгбнкхрэ йкхемрс опнвхрюрэ ецн яндепфхлне 
    // оепед нрйкчвемхел. оняке нрянедхмъеляъ х гюйпшбюел йюмюк.

    FlushFileBuffers( hPipe ); 
    DisconnectNamedPipe( hPipe ); 
    CloseHandle( hPipe ); 

    return 1;
}

////////////////////////////////////////////////////////////////////////////////

VOID GetAnswerToRequest( LPSTR chRequest, LPSTR chReply, LPDWORD pchBytes )
{
    printf( "Client message \'%s\' received...\n", chRequest ) ;
    sprintf( chReply, "Message \'%s\' received successfully!", chRequest );

    *pchBytes = ( lstrlen( chReply ) + 1 ) * sizeof( char );
}

////////////////////////////////////////////////////////////////////////////////
