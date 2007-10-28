/**
 *вставка для переносимости кода 8-)
 */
#ifdef WIN32
#include <winsock.h>
#else
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/types.h>
#endif

#ifdef WIN32
#define FILE_SEPARATOR ''
#define MSG_WAITALL 0
#else
#define FILE_SEPARATOR '/'
#endif
///////////////////////////////////
#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <iostream>
#include <cstdlib>
///////////////////////////////////
using namespace std;
///////////////////////////////////
// число пакетов
#define PACKET_NUM ((DWORD)1000)
// размер запроса
#define DATA_SIZE ((DWORD)0x10000)
// размер очереди
#define QUEUE_SIZE ((DWORD)5)
// интервал
#define CLIENT_TIMEOUT ((DWORD)50)
// ожидание ответа
#define TIME_LIMIT ((DWORD)50)
// время обработки
#define SERV_TIME ((DWORD)50)
// размер буфера
#define BUFSIZ 100
///////////////////////////////////
// имя сервера
char* SERVER_NAME;
// порт сервера
short SERVER_PORT;
// роль
bool SERVER_ROLE = false;
// число успешно обработанных запросов
unsigned g_success = 0;
///////////////////////////////////
/**
 * вывод на экран информации о параметрах командной строки
 */
void printUsage()
{
    
}
///////////////////////////////////
/**
 * реализация сервера
 */
void server()
{
    char hostName[BUFSIZ];
}
///////////////////////////////////
int main(int argc, char *argv[])
{
    cout << "Hello, world!" << endl;

    return EXIT_SUCCESS;
}
///////////////////////////////////
