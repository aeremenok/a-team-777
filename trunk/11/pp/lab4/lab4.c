#include <stdio.h>
#include <string.h>

#include <sys/root.h>
#include <sys/link.h>

#define DATA 1000

LinkCB_t    *nextLink;
LinkCB_t    *prevLink;
int         myProcId;
int         nextProcId;
int         prevProcId;
int         lastProcId;
char        text[DATA];
int         dataSize;
int         error;
int         statusMessage;
int         commonLinkId;

char *fName = "lab4";

const int M_EXIT = 0;
const int M_EMPTY = 1;
const int M_ALLEMPTY = 2;
const int M_PUSH = 3;
const int M_POP = 4;
const int M_UPDATE = 5;
const int M_UPDATEPUSH = 6;


int initProc() {
    int nProcs = GET_ROOT()->ProcRoot->nProcs;
    myProcId = GET_ROOT()->ProcRoot->MyProcID;
    
    lastProcId = nProcs - 1;
    nextProcId = (myProcId + 1) % nProcs;
    prevProcId = (myProcId == 0) ? lastProcId : (myProcId - 1);
    
    nextLink = NULL;
    prevLink = NULL;
    
    dataSize = 0;
    
    commonLinkId = 1234;
    
    return nProcs;
}


int initRing() {
    if (myProcId == 0) {
        nextLink = MakeLink(nextProcId, commonLinkId, &error);
        if (nextLink == NULL) {
            LogError(EC_FATAL, fName, "failed to make link: %d", error);
            return 1;
        }
        
        prevLink = GetLink(prevProcId, commonLinkId, &error);
        if (prevLink == NULL) {
            LogError(EC_FATAL, fName, "failed to make link: %d", error);
            return 1;
        }
    } else {
        prevLink = GetLink(prevProcId, commonLinkId, &error);
        if (prevLink == NULL) {
            LogError(EC_FATAL, fName, "failed to make link: %d", error);
            return 1;
        }

        nextLink = MakeLink(nextProcId, commonLinkId, &error);
        if (nextLink == NULL) {
            LogError(EC_FATAL, fName, "failed to make link: %d", error);
            return 1;
        }
    }
    
    return 0;
}


int isEmpty() {
    int recvSize = 0;
    int result = 0;
    
    if (myProcId != 0) {
        LogError(EC_FATAL, "wrong processor element!", 0);
    }
    
    SendLink(nextLink, (void *)&M_ALLEMPTY, sizeof(M_ALLEMPTY));
    
    recvSize = RecvLink(prevLink, &statusMessage, sizeof(statusMessage));
    
    if (recvSize != sizeof(statusMessage)) {
        LogError(EC_FATAL, fName, "possible data corruption!", 0);
    } else {
        result = statusMessage;
    }
    
    return result;
}


int isFull() {
    if (dataSize == 0) {
        return 0;
    } else {
        return 1;
    }
}


void die() {
    SendLink(nextLink, (void *)&M_EXIT, sizeof(M_EXIT));
    RecvLink(prevLink, &statusMessage, sizeof(statusMessage));
}


int push(char *dataToPush, int size) {
    int recvSize = 0;
    
    if (isFull() == 1) {
        return 1;
    }
    
    if (size > DATA) {
        return 1;
    }
    
    SendLink(nextLink, (void *)&M_EMPTY, sizeof(M_EMPTY));
    recvSize = RecvLink(nextLink, &statusMessage, sizeof(statusMessage));
    if (recvSize != sizeof(statusMessage)) {
        LogError(EC_FATAL, fName, "possible data corruption", 0);
        return 1;
    }
    
    if (statusMessage == 1) {
        SendLink(nextLink, (void *)&M_PUSH, sizeof(M_PUSH));
        SendLink(nextLink, dataToPush, size);
    } else {
        memcpy(&text, dataToPush, size);
        dataSize = size;
    }
    
    return 0;
}


int pop(void *buffer, int *size) {
    int recvSize = 0;
    
    if (isEmpty() == 1) {
        return 1;
    }
    
    SendLink(nextLink, (void *)&M_POP, sizeof(M_POP));
    recvSize = RecvLink(prevLink, buffer, DATA);
    *size = recvSize;
    
    if (isFull() == 1) {
        SendLink(nextLink, (void *)&M_UPDATEPUSH, sizeof(M_UPDATEPUSH));
        
        SendLink(nextLink, &text, dataSize);
        dataSize = 0;
    } else {
        SendLink(nextLink, (void *)&M_UPDATE, sizeof(M_UPDATE));
    }
    
    return 0;
}


int processMessage() {
    int loop = 1;
    int recvSize = 0;
    int response = 0;
    
    while (loop == 1) {
        recvSize = RecvLink(prevLink, &statusMessage, sizeof(statusMessage));
        if (recvSize != sizeof(statusMessage)) {
            LogError(EC_FATAL, fName, "possible data corruption!", 0);
            return 1;
        }
        
        switch (statusMessage) {
            case 0:
                loop = 0;
                SendLink(nextLink, (void *)&M_EXIT, sizeof(M_EXIT));
                break;
            
            case 1:
                if (dataSize == 0) {
                    response = 1;
                } else {
                    response = 0;
                }
                SendLink(prevLink, &response, sizeof(response));
                break;
            
            case 2:
                if (myProcId != lastProcId) {
                    SendLink(nextLink, (void *)&M_ALLEMPTY, sizeof(M_ALLEMPTY));
                } else {
                    if (dataSize == 0) {
                        response = 1;
                    } else {
                        response = 0;
                    }
                    SendLink(nextLink, &response, sizeof(response));
                }
                break;
                
            case 3:
                dataSize = RecvLink(prevLink, &text, DATA);
                
                if (myProcId != lastProcId) {
                    SendLink(nextLink, (void *)&M_EMPTY, sizeof(M_EMPTY));
                    recvSize = RecvLink(nextLink, &statusMessage, sizeof(statusMessage));
                    if (recvSize != sizeof (statusMessage)) {
                        LogError(EC_FATAL, fName, "possible data corruption!", 0);
                        return 1;
                    }
                    if (statusMessage == 1) {
                        SendLink(nextLink, (void *)&M_PUSH, sizeof(M_PUSH));
                        SendLink(nextLink, &text, dataSize);
                        dataSize = 0;
                    }
                }
                break;
                
            case 4:
                if (myProcId == lastProcId) {
                    SendLink(nextLink, &text, dataSize);
                    dataSize = 0;
                } else {
                    SendLink(nextLink, (void *)&M_POP, sizeof(M_POP));
                }
                break;
                
            case 5:
                if (myProcId != lastProcId && dataSize == 0) {
                    SendLink(nextLink, (void *)&M_UPDATE, sizeof(M_UPDATE));
                }
                
                if (dataSize > 0) {
                    if (myProcId == lastProcId) {
                        return 1;
                    }
                    
                    SendLink(nextLink, (void *)&M_UPDATEPUSH, sizeof(M_UPDATEPUSH));
                    SendLink(nextLink, &text, dataSize);
                    dataSize = 0;
                }
                break;
                
            case 6:
                if (myProcId == lastProcId) {
                    dataSize = RecvLink(prevLink, &text, DATA);
                } else {
                    SendLink(nextLink, (void *)&M_UPDATEPUSH, sizeof(M_UPDATEPUSH));
                    SendLink(nextLink, &text, dataSize);
                    dataSize = RecvLink(prevLink, &text, DATA);
                }
                break;
                
            default:
                printf("\nUNKNOWN COMMAND: %d\n", statusMessage);
                LogError(EC_ERROR, fName, "unknown command!", 0);
                return 1;
                break;
        }
    }
    
    return 0;
}


int doTest(int nProcs) {
    char* testArray[] = {
        "test message 1",
        "test message 2",
        "test message 3",
        "test message 4"
    };
    
    char* userMsgs[] = {
        "Queue is Empty!\n",
        "Queue is Full!\n",
        "Queue is not Empty!\n",
        "Message \'%s\' pushed in Queue!\n",
        "Message \'%s\' popped from Queue!\n",
        "\nTest Failed!!!\n"
    };
    
    int EMPTY = 0;
    int FULL = 1;
    int NOT_EMPTY = 2;
    int PUSHED = 3;
    int POPPED = 4;
    int FAILED = 5;
    
    int i = 0;
    int curData = 0;
    int recvSize = 0;
    char recvBuffer[DATA];
    
    if (isEmpty()) {
        printf(userMsgs[EMPTY]);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    if (push(testArray[curData], strlen(testArray[curData]) + 1) == 0) {
        printf(userMsgs[PUSHED], testArray[curData]);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    ++curData; curData %= 4;

    if (isEmpty()) {
        printf(userMsgs[FAILED]);
    } else {
        printf(userMsgs[NOT_EMPTY]);
    }
    
    if (pop(&recvBuffer, &recvSize) == 0) {
        printf(userMsgs[POPPED], recvBuffer);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    if (isEmpty()) {
        printf(userMsgs[EMPTY]);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    for (i = 0; i < nProcs; i++) {
        if (push(testArray[curData], strlen(testArray[curData]) + 1) == 0) {
            printf(userMsgs[PUSHED], testArray[curData]);
        } else {
            printf(userMsgs[FAILED]);
        }
        ++curData; curData %= 4;
    }
    
    if (isFull() == 1) {
        printf(userMsgs[FULL]);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    for (i = 0; i < nProcs; i++) {
        if (pop(&recvBuffer, &recvSize) == 0) {
            printf(userMsgs[POPPED], recvBuffer);
        } else {
            printf(userMsgs[FAILED]);
        }
    }
    
    if (isEmpty()) {
        printf(userMsgs[EMPTY]);
    } else {
        printf(userMsgs[FAILED]);
    }
    
    return 0;
}


int main(void) {
    int nProcs = initProc();
    
    if (nProcs < 2) {
        printf("not interesting, try at least 2 processors");
        return 1;
    }
    
    initRing();
    
    if (myProcId == 0) {
        if (doTest(nProcs) != 0) {
            printf("\nTest failed, and the world goes down!\n");
            AbortServer(1);
        }
        
        die();
    } else {
        if (processMessage() != 0) {
            printf("\nTest failed, and the world goes down!\n");
            AbortServer(1);
        }
    }
    
    return 0;
}
