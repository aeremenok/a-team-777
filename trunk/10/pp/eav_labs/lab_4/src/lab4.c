#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include "sys/root.h"
#include "message.h"

int main(void) {
    char *FName = "main";
    int MyProcID;
    int nProcs;
    LinkCB_t *PrevLink, *NextLink;
    int error;
    int empty = 1;
    char body[1024];
    int bodylen;
    int lastId;
    MyProcID = GET_ROOT()->ProcRoot->MyProcID;
    nProcs=GET_ROOT()->ProcRoot->nProcs;
    int dimX = GET_ROOT()->ProcRoot->DimX;
    int dimY = GET_ROOT()->ProcRoot->DimY;
    int myY = GET_ROOT()->ProcRoot->MyY;
    int myX = GET_ROOT()->ProcRoot->MyX;
    int chet = myY%2==0;
    int nextId;
    int prevId;

    if(nProcs<2) {
       printf("Your network is too small.\n");
       AbortServer(1);
    }

    /*set up links */
    if(MyProcID==0) {
        nextId = MyProcID+1;
        NextLink=MakeLink(nextId, 1234, &error);
        if(NextLink==NULL) {
            LogError(EC_FATAL, FName, "Failed to make link to next processor, error %d.", error);
            AbortServer(1);
        }
        if (dimY%2 != 0) {
            prevId=nProcs-1;
        }
        else{
            prevId=nProcs-dimX;
        }
        PrevLink=GetLink(prevId, 1234, &error);
        if(PrevLink==NULL) {
            LogError(EC_FATAL, FName, "Failed to get link from previous processor, error %d.", error);
            AbortServer(1);
        }
    }
    else {
        lastId = nProcs - 1 - (dimY%2 == 0 )*(dimX-1);
        if(myX==0 && myY%2 ==0 || myX == dimX-1 && myY%2 != 0){
            prevId = MyProcID - dimX;
        }
        else{
            prevId = (MyProcID + 1 - 2*(myY%2==0))%nProcs;
        }
        PrevLink=GetLink(prevId, 1234, &error);
        if(PrevLink==NULL) {
            LogError(EC_FATAL, FName, "Failed to get link from previous processor, error %d.", error);
            AbortServer(1);
        }
        nextId = 0;
        if(myX == 0 && myY%2 !=0 || myX == dimX-1 && myY%2==0){ /*крайний */
            nextId = MyProcID + dimX;
            if(nextId>=nProcs) nextId = 0;
        }
        else{
            nextId = MyProcID + 1 - 2*(myY%2!=0);
        }
        NextLink=MakeLink(nextId, 1234, &error);
        if(NextLink==NULL) {
            LogError(EC_FATAL, FName, "Failed to make link to next processor, error %d.", error);
            AbortServer(1);
        }
    }
    printf("MyId=%d, prevId is %d, nextId is %d.\n", MyProcID, prevId, nextId);

    /*process message */
    {
        if(MyProcID==0) {
            int length;
            char original[1024], result[1024];
            memset(original, 0, 1024);
            memset(result, 0, 1024);
            memset(body, 0, 1024);
            int cont = 1;
            
            while (cont)
            {
                printf("Input message text : \n");
                scanf("%s", original);
                length=strlen(original);
                
                if(empty == 1){
                    sprintf(body, original);
                    printf("MyId=%d. empty: %s.\n",
                    MyProcID, body);
                    empty = 0;
                } 
                else{
                    printf("MyId=%d. not empty: %s.\n",MyProcID, original);
                    printf("MyId=%d. Sending message: %s.\n",MyProcID, body);
                    printf("MyId=%d. My body is: %s.\n",MyProcID, original);
                    SendLink(NextLink, body, length);
                    sprintf(body, original);
                    length = RecvLink(PrevLink, result, 1024);
                    result[length]=(char)0;
                    printf("MyId=%d. Status message received: %s.\n",MyProcID, result);
                    
                    if(stricmp(result, "FIFO is not complete ") != 0){ 
                        printf("MyId=%d, sending status message:%s\n", MyProcID, result);
                        SendLink(NextLink, result, length);
                        cont = 0;
                    }
                }
            }
        }
        else {
            int length;
            char buffer[1024];
            memset(buffer, 0, 1024);
            int cont = 1;
            while (cont)
            {
                length=RecvLink(PrevLink, buffer, 1024);
                buffer[length] = 0;
                printf("MyId=%d, got message: %s\n",MyProcID, buffer );
                if(stricmp(buffer, "FIFO is not complete ") == 0){
                    printf("MyId=%d, sending status message %s\n", MyProcID,    buffer);
                    SendLink(NextLink, buffer, length);
                }else if(stricmp(buffer, " FIFO is complete ")==0){
                    cont = 0;
                    printf("MyId=%d, sending status message %s\n", MyProcID, buffer);
                    if(MyProcID != lastId) SendLink(NextLink, buffer, length);
                }
                else{
                    if(empty == 1) {
                        sprintf(body, buffer);
                        bodylen = length;
                        body[bodylen] = 0;
                        printf("MyId=%d. I was empty!\n",MyProcID);
                        printf("MyId=%d. My body is: %s.\n",MyProcID,body);
                        empty = 0;
                        if(MyProcID == lastId){
                            char* mes = " FIFO is complete\0";
                            SendLink(NextLink, mes, 15);
                        }
                        else{
                            char* mes = " FIFO is not complete \0";
                            printf("MyId=%d. Sending status message:%s\n", MyProcID, mes);
                            SendLink(NextLink, mes, strlen(mes));
                        }
                    }
                    else{
                        printf("MyId=%d. I was not empty, my body was:%s\n",
                        MyProcID, body);
                        printf("MyId=%d. Sending message: %s.\n",MyProcID, body);
                        printf("MyId=%d. My body is: %s.\n",
                        MyProcID, buffer);
                        SendLink(NextLink, body, bodylen);
                        sprintf(body, buffer);
                        bodylen = length;
                        body[bodylen] = 0;
                    }
                }
            }
        }
    }
    exit(0);
}