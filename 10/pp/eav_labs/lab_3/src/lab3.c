#include <stdio.h>
#include <string.h>
#include <stdlib.h>
 
#include <sys/link.h>
#include <sys/root.h>
#include <sys/sys_rpc.h>
#include <sys/thread.h>
 
const int STACK_SIZE = 4096;
char answer[100];
RootProc_t* root;
    
int harry(void)
{
    int myprocid, error;
    LinkCB_t* TonyLink;
    int  i;
    char* question[] = {
        "how r u?",
        "what do u think of parix?"
    };
    
    myprocid = root->MyProcID;
    TonyLink = MakeLink( myprocid, 1234, &error);
    if (!TonyLink)    
    {  
        LogError(EC_ERROR, "harry", "errcode %d", error );
        exit(1);
    }
    
    for (i = 0; i < 2; i++){
        printf("talk to tony %s\n", question[i]);
        SendLink( TonyLink, (byte*)question[i], strlen(question[i]) );
        RecvLink( TonyLink, (byte*)answer, 1024 );
    }

    return 0;
}
 
int tony(void)
{
    int myprocid, error;
    LinkCB_t* HarryLink;
    int  i;
    char* question[] = {
        "how r u?",
        "what do u think of parix?"
    };
 
    myprocid = root->MyProcID;
    HarryLink = MakeLink(myprocid, 1234, &error);
    if (!HarryLink)
    {  
        LogError(EC_ERROR, "harry", "errcode %d", error );
        exit(1);
    }
    
    for (i = 0; i < 2; i++){
        printf("talk to harry %s\n", question[i]);
        SendLink( HarryLink, (byte*)question[i], strlen(question[i]) );
        RecvLink( HarryLink, (byte*)answer, 1024 );
    }

    return 0;
}
 
int main(int argc, char* argv[])
{
    Thread_t* tHarry;
    Thread_t* tTony;
    int error, result;

    root = GET_ROOT()->ProcRoot;

    tHarry = StartThread(  harry, STACK_SIZE, &error, 0 );
    if (!tHarry)
    {  
        LogError(EC_ERROR, "harry", "errcode %d", error );
        exit(1);
    }

    tTony = StartThread( tony, STACK_SIZE, &error, 0 );
    if (!tTony)
    {  
        LogError(EC_ERROR, "tony", "errcode %d", error );
        exit(1);
    }

    WaitThread(tHarry, &result);
    WaitThread(tTony, &result);
    
    return 0;
}