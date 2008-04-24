#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <sys/link.h>
#include <sys/root.h>
#include <sys/sys_rpc.h>

int main(int argc, char* argv[])
{
       RootProc_t* root;
       int MyProcId;
       int myx, myy, myz;
       int dimx, dimy, dimz;
       int next;

       printf("started\n");

       root = GET_ROOT()->ProcRoot;
       MyProcId = root->MyProcID;

       myx=root->MyX;
       myy=root->MyY;
       myz=root->MyZ;

       printf("[%d] my{%d %d %d}\n", MyProcId, myx,myy,myz);

       dimx = root->DimX;
       dimy = root->DimY;
       dimz = root->DimZ;

       printf("[%d] dim{%d %d %d}\n", MyProcId, dimx,dimy,dimz);

       if (myy < (dimy-1))
       {
               next =
                   ( myz * dimz * dimy ) +
                   ( ( myy + 1 ) * dimx ) +
                   myx;
               printf("[%d] up %d\n", MyProcId, next);
       }
       else
       {
               printf("[%d] no up\n", MyProcId);
       }


       if (myx < (dimx-1))
       {
               next =
                   ( myz * dimz * dimx ) +
                   ( myy * dimx ) +
                   ( myx + 1 );
               printf("[%d] right %d\n", MyProcId, next);
       }
       else
       {
               printf("[%d] no right\n", MyProcId);
       }

       if (myx > 1)
       {
               next =
                   ( myz * dimz * dimx ) +
                   ( myy * dimx ) +
                   ( myx - 1 );
               printf("[%d] left %d\n", MyProcId, next);
       }
       else
       {
               printf("[%d] no left\n", MyProcId);
       }

       if (myy > 1)
       {
               next =
                   ( myz * dimz * dimx ) +
                   ( ( myy - 1 ) * dimx ) +
                   myx;
               printf("[%d] down %d\n", MyProcId, next);
       }
       else
       {
               printf("[%d] no down\n", MyProcId);
       }

       printf("[%d] finished\n", MyProcId);
       return 0;
}