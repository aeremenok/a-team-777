//============================================================================
// Name        : eav_lab_2.cpp
// Author      : eav 3351
// Version     :
// Copyright   : Your copyright notice
//============================================================================

#include <winsock2.h>

#include <stdio.h>
#include <stdlib.h>

#define ICMP_ECHO ((USHORT)8) // код сообщения icmp-echo
#define ICMP_MAXBUF ((DWORD)0x1000) // максимальный размер пакета icmp
#define ICMP_DATA ((DWORD)32) // размер данных icmp
#define ICMP_TIMEOUT ((DWORD)1000) // интервал
// макрос инициализации структур
#define constructor() { ZeroMemory(this, sizeof(*this)) }

// структура заголовка пакета IP
typedef struct tagIP_header
{
        tagIP_header()constructor()
        ;
        unsigned char h_len : 4; // длина заголовка
        unsigned char version : 4; // версия протокола
        unsigned char tos; // тип сервиса
        unsigned short total_len; // общая длина пакета
        unsigned short ident; // идентификатор пакета
        unsigned short frag_and_flags; // флаги
        unsigned char ttl; // время жизни
        unsigned char proto; // код протокола
        unsigned short checksum; // контрольная сумма
        unsigned int sourceIP; // исходный адрес
        unsigned int destIP; // конечный адрес
} IP_HEADER, *PIP_HEADER;

// структур заголовка пакета ICMP
typedef struct tagICMP_MSG
{
        tagICMP_MSG()constructor()
        ;
        BYTE i_type; // тип сообщения
        BYTE i_code; // расширенный код сообщения
        USHORT i_chksum; // контрольная сумма
        USHORT i_id; // идентификатор приложения
        USHORT i_seq; // номер в последовательности
// атрибут в области доп.данных
        DWORD timestamp; // временная метка
} ICPM_MSG, *PICMP_MSG;

USHORT g_appid; // xxx а это что?

/**
 * вывод на экран ошибки winsock и завершение работы
 */
bool _sc(int wsacode, const char* msg = 0)
{
    bool res = false;
    if (wsacode == SOCKET_ERROR)
    {
        printf("wsaerror=%d,%s\n ", WSAGetLastError(), (msg)?(msg):(""));
        res = true;
    }
    return res;
}

int main(void)
{
    puts("Hello World!!!");
    return EXIT_SUCCESS;
}
