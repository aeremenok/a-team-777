//============================================================================
// Name        : eav_lab_2.cpp
// Author      : eav 3351
// Version     :
// Copyright   : Your copyright notice
//============================================================================
#include "StdAfx.h"
//////////////////////////////////////////////////////////////////////////
#include <winsock2.h>

#include <stdio.h>
#include <stdlib.h>
//////////////////////////////////////////////////////////////////////////
#define ICMP_ECHO ((USHORT)8) // ��� ��������� icmp-echo
#define ICMP_MAXBUF ((DWORD)0x1000) // ������������ ������ ������ icmp
#define ICMP_DATA ((DWORD)32) // ������ ������ icmp
#define ICMP_TIMEOUT ((DWORD)1000) // ��������
// ������ ������������� ��������
#define constructor() { ZeroMemory(this, sizeof(*this)); }
//////////////////////////////////////////////////////////////////////////
// ��������� ��������� ������ IP
typedef struct tagIP_header
{
        tagIP_header()constructor()
        ;
        unsigned char h_len : 4; // ����� ���������
        unsigned char version : 4; // ������ ���������
        unsigned char tos; // ��� �������
        unsigned short total_len; // ����� ����� ������
        unsigned short ident; // ������������� ������
        unsigned short frag_and_flags; // �����
        unsigned char ttl; // ����� �����
        unsigned char proto; // ��� ���������
        unsigned short checksum; // ����������� �����
        unsigned int sourceIP; // �������� �����
        unsigned int destIP; // �������� �����
} IP_HEADER, *PIP_HEADER;

// �������� ��������� ������ ICMP
typedef struct tagICMP_MSG
{
        tagICMP_MSG()constructor()
        ;
        BYTE i_type; // ��� ���������
        BYTE i_code; // ����������� ��� ���������
        USHORT i_chksum; // ����������� �����
        USHORT i_id; // ������������� ����������
        USHORT i_seq; // ����� � ������������������
        // ������� � ������� ���.������
        DWORD timestamp; // ��������� �����
} ICMP_MSG, *PICMP_MSG;
//////////////////////////////////////////////////////////////////////////
USHORT g_appid; // xxx � ��� ���?

/**
 * ����� �� ����� ������ winsock � ���������� ������
 */
bool _sc(int wsacode, const char* msg = 0)
{
    bool res = false;
    if (wsacode == SOCKET_ERROR)
    {
        printf("wsaerror=%d,%s\n ", WSAGetLastError(), (msg) ? (msg) : (""));
        res = true;
    }
    return res;
}

USHORT checksum(USHORT* buffer, int size)
{
    unsigned long chksum = 0;

    while (size > 1)
    {
        chksum += *buffer++;
        size -= sizeof(USHORT);
    }

    if (size)
    {
        chksum += *(UCHAR*)buffer;
    }

    chksum = (chksum >> 16) + (chksum&0xffff); // xxx ��� ���?
    chksum += (chksum >> 16);

    return (USHORT)(~chksum); // xxx %)
}

/**
 * ������ ���������� �������
 */
bool icmp_parse(IP_HEADER *iph, ICMP_MSG *icmph, sockaddr_in* from, int len)
{
    if (icmph->i_id != g_appid)
    {
        printf("(:\n");
        return false;
    }

    printf("Reply from %s bytes = %d TTL = %d time = %d ms\n",
            inet_ntoa(from->sin_addr), len, iph->ttl, GetTickCount()
                    -icmph->timestamp);

    return true;
}

/**
 * ���� �������/�������� ���������
 */
void echo(const char* hostname)
{
    hostent* he;
    SOCKET sock;
    WSADATA wsad;

    sockaddr_in dest;
    unsigned size;
    char buf[ICMP_MAXBUF];
    PIP_HEADER iph = (PIP_HEADER)buf;

    // ������������� winsock
    if (_sc(WSAStartup(MAKEWORD(2,1), &wsad)))
    {
        ExitProcess(-1);
    }
    // �������� ������ icmp
    sock = WSASocket(AF_INET, SOCK_RAW, IPPROTO_ICMP,0,0,0);

    printf("Pinging host \\\\%s\n", hostname);

    // ��������� ������ ����� �� ��� �����
    if ((he = gethostbyname(hostname))==0)
    {
        _sc(SOCKET_ERROR);
        ExitProcess(-1);
    }

    ZeroMemory(&dest, sizeof(dest));
    CopyMemory(&(dest.sin_addr), he->h_addr, he->h_length);
    dest.sin_family = he->h_addrtype;
    printf("Host IP %s\n", inet_ntoa(dest.sin_addr));

    for (;;)
    {
        ICMP_MSG data_out;
        int len = sizeof(dest);
        size = sizeof(ICMP_MSG)+ICMP_DATA; // ����� ������ ������

        // ���������� ���� �������������� ������ ������
        memset((char*)(buf+sizeof(ICMP_MSG)), '#', ICMP_DATA);
        // ���������� ��������� ������
        data_out.timestamp = GetTickCount(); // ����� �����������
        data_out.i_type = ICMP_ECHO; // ���  
        data_out.i_id = g_appid; // 
        data_out.i_chksum = checksum((USHORT*)&data_out, size); // ���������� �����
        
        // �������� ������ � �������� ICMP_ECHO
        _sc(sendto(sock, (char*)&data_out, size, 0, (sockaddr*)&dest, len));
        
        // ��������� ������ � ������ ������
        do 
            _sc(recvfrom(sock, buf, ICMP_MAXBUF, 0, (sockaddr*)&dest, &len));
        while (
                !icmp_parse(
                (IP_HEADER*)buf,
                (ICMP_MSG*)(buf + iph->h_len*4),
                &dest,
                ICMP_DATA)
               );
        
        // ����� ����� ����������� ��������
        Sleep(ICMP_TIMEOUT);
    }
}

int main(int argc, char* argv[])
{
    if (argc != 2)
    {
        printf("Usage: ping.exe [hostname]\n");
    }
    else
    {
        g_appid = (USHORT)GetCurrentProcessId();
        echo(argv[1]);
    }
    return 0;
}
//////////////////////////////////////////////////////////////////////////

