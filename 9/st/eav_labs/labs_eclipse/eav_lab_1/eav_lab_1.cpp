//============================================================================
// Name        : eav_lab_2.cpp
// Author      : eav 3351
// Version     :
// Copyright   : Your copyright notice
//============================================================================

#include <stdio.h>
#include <stdlib.h>

#define ICMP_ECHO ((USHORT)8) // код сообщения icmp-echo
#define ICMP_MAXBUF ((DWORD)0x1000) // максимальный размер пакета icmp
#define ICMP_DATA ((DWORD)32) // размер данных icmp
#define ICMP_TIMEOUT ((DWORD)1000) // интервал
// макрос инициализации структур
#define constructor() { ZeroMemory(this, sizeof(*this)) }
// структура заголовка пакета
typedef struct tagIP_header
{
	tagIP_header() constructor();
	unsigned char h_len: 4; // длина загловка
	unsigned char version: 4; // версия протокола
}

int main(void)
{
	puts("Hello World!!!");
	return EXIT_SUCCESS;
}
