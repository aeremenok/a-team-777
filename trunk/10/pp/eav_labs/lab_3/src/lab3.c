#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <sys/link.h>
#include <sys/root.h>
#include <sys/sys_rpc.h>
#include <sys/thread.h>

const int STACK_SIZE = 4096;
int myprocid;
    
int harry(void)
{
    char answer[100];
    int error;
    LinkCB_t* TonyLink, *MaryLink;
    int  i;
    char* question[] = {
        "how are you?",
        "what do you think of parix?"
    };

    TonyLink = MakeLink( myprocid, 1234, &error);
    if (!TonyLink)    
    { 
        LogError(EC_ERROR, "harry", "errcode %d", error );
        printf("tony link error");
        exit(1);
    }

    MaryLink = MakeLink( myprocid, 1234, &error);
    if (!MaryLink)    
    { 
        LogError(EC_ERROR, "mary", "errcode %d", error );
        printf("mary link error");
        exit(1);
    }

    for (i = 0; i < 2; i++){
        SendLink( TonyLink, (byte*)question[i], strlen(question[i])+1);
        SendLink( MaryLink, (byte*)question[i], strlen(question[i])+1);
        printf("harry said: %s\n",question[i]);

        RecvLink( MaryLink, (byte*)answer, 1024 );
        printf("mary said: %s\n",answer);
        RecvLink( TonyLink, (byte*)answer, 1024 );
        printf("tony said: %s\n",answer);
    }

    return 0;
}

int tony(void)
{
    char answer[100];
    int error;
    LinkCB_t* HarryLink;
    int  i;
    char* question[] = {
        "fine",
        "it works"
    };

    printf(" tony working  %d \r\n", myprocid);
    HarryLink = GetLink(myprocid, 1234, &error);

    if (!HarryLink)
    { 
        LogError(EC_ERROR, "harry", "errcode %d", error );
        exit(1);
    }

    for (i = 0; i < 2; i++){
        RecvLink( HarryLink, (byte*)answer, 1024 );
        SendLink( HarryLink, (byte*)question[i], strlen(question[i])+1 );
    }

    return 0;
}

int mary(void)
{
    char answer[100];
    int error;
    LinkCB_t* HarryLink;
    int  i;
    char* question[] = {
        "could be better",
        "it's too buggy"
    };

    printf(" mary working  %d \r\n", myprocid);
    HarryLink = GetLink(myprocid, 1234, &error);

    if (!HarryLink)
    { 
        LogError(EC_ERROR, "harry", "errcode %d", error );
        exit(1);
    }

    for (i = 0; i < 2; i++){
        RecvLink( HarryLink, (byte*)answer, 1024 );
        SendLink( HarryLink, (byte*)question[i], strlen(question[i])+1 );
    }

    return 0;
}

int main(int argc, char* argv[])
{
    Thread_t* tHarry;
    Thread_t* tTony;
    Thread_t* tMary;
    int error, result;

    myprocid = GET_ROOT()->ProcRoot->MyProcID;

    tHarry = StartThread(  harry, STACK_SIZE, &error, 0 );
    if (tHarry==NULL)
    { 
        LogError(EC_ERROR, "harry", "errcode %d", error );
        printf("harry error");
        exit(1);
    }

    tTony = StartThread( tony, STACK_SIZE, &error, 0 );
    if (tTony == NULL)
    { 
        LogError(EC_ERROR, "tony", "errcode %d", error );
        exit(1);
    }

    tMary = StartThread( mary, STACK_SIZE, &error, 0 );
    if (tTony == NULL)
    { 
        LogError(EC_ERROR, "mary", "errcode %d", error );
        exit(1);
    }

    WaitThread(tHarry, &result);
    WaitThread(tTony, &result);
    WaitThread(tMary, &result);

    return 0;
}
