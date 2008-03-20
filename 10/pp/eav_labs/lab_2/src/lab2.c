#include <stdio.h> 
#include <stdlib.h> 

typedef struct RootProc_t;

int main(int argc, char *argv[])
{
    RootProc_t* root;
    int myProcId;
    int neighbourId;

    printf("hello!\n");

    root = GETROOT()->ProcRoot;
    myProcId = root->MyProcId;
    
    neighbourId = getNeighbourId(root);
    printf("upper neighbour %s\n", neighbourId);
    
    return 0;
}

int getNeighbourId(RootProc_t root){
    int res = -1;
    if (root->MyY < (root->DimY - 1))
    {
        res =  root->MyZ * root->DimX * root->DimY +
               ((root->MyY+1) * root->DimX) +
               root->MyX
    }
    else
    {
        printf("no upper neighbours by Y\n");
    }
    return res;
}
