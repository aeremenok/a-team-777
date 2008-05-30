#include <stdio.h>
#include <stdlib.h>

int dimX;
int dimY;

int myX;
int myY;

int nProc;

int getProcId(int x, int y)
{
    int id = y*dimX+x;
    //printf("[getProcId] x=%d, y=%d -> id=%d\n", x, y, id);
    return id;
}

int getXId(int id)
{
    int x = id % dimX;
    //printf("[getXId] id=%d -> x=%d \n", id, x );
    return x;
}

int getYId(int id)
{
    int y = id / dimX;
    //printf("[getYId] id=%d -> y=%d \n", id, y );
    return y;
}

int getLeft()
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (  dimX <= 1 && dimY <= 1  )
    {
        printf( " Error: must run at more than one processor!" );
        return 0;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if ( dimX == 1)
    {
        if ( myY == 0) return dimY - 1;
        if ( myY == dimY - 1 ) return myY - 1;
        return myY - 1;
    }
    if ( dimY == 1)
    {
        if ( myX == 0) return dimX - 1;
        //if( myX == dimX - 1 ) return 0;
        return myX-1;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////

    if ( dimY == 2)
    {
        if ( myY == 0 )
        {
            if ( myX == 0 ) return getProcId( myX, myY+1 );
            if ( myX == dimX - 1 ) return  getProcId( myX - 1, myY );
            return getProcId( myX-1, myY );
        }
        else
            if ( myY == 1 )
            {
                if ( myX == 0 ) return getProcId( myX+1, myY );
                if ( myX == dimX - 1 ) return  getProcId( myX, myY-1 );
                return getProcId( myX+1, myY );
            }
        printf("BUG! dimY==2 and myY!=0/1!");
        return 0;
    }

    if ( dimX == 2)
    {
        if ( myX == 0 )
        {
            if ( myY == 0 ) return getProcId( myX, myY+1 );
            if ( myY == dimY - 1 ) return  getProcId( myX+1, myY );
            return getProcId( myX, myY+1 );
        }
        else
            if ( myX == 1 )
            {
                if ( myY == 0 ) return getProcId( myX-1, myY );
                if ( myY == dimY - 1 ) return  getProcId( myX, myY-1 );
                return getProcId( myX, myY-1 );
            }
        printf("BUG! dimX==2 and myX!=0/1!");
        return 0;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int id = 0;
    if ( dimX % 2 == 0)
    {

        //==================================================================

        //1.
        if ( myY == 0)
        {
            //first line
            if ( myX == 0 ) return getProcId( myX, myY+1 );
            if ( myX == dimX - 1 ) return getProcId( myX-1, myY );
            return getProcId( myX-1, myY );
        }
        if ( myY == 1)
        {
            //second line
            if ( myX == 0 ) return getProcId( myX, myY+1 );
            if ( myX == dimX - 1 ) return getProcId( myX, myY-1 );

            return (myX % 2 == 0)? getProcId( myX, myY+1 )
                   : getProcId( myX+1, myY );
        }
        if ( myY == dimY - 1 )
        {
            //last upper line
            return (myX % 2 == 0)? getProcId( myX+1, myY )
                   : getProcId( myX, myY-1 );
        }
        return (myX % 2 == 0)? getProcId( myX, myY+1 )
               : getProcId( myX, myY-1 );

        //==================================================================

    }
    else if (dimY % 2 == 0)
    {

        //==================================================================

        //2.
        if ( myX == 0)
        {
            //first line
            if ( myY == 0 ) return getProcId( myX, myY+1 );
            if ( myY == dimY - 1 ) return getProcId( myX+1, myY );
            return getProcId( myX, myY+1 );
        }
        if ( myX == 1)
        {
            //second line
            if ( myY == 0 ) return getProcId( myX-1, myY );
            if ( myY == dimY - 1 ) return getProcId( myX+1, myY );

            return (myY % 2 == 0)? getProcId( myX, myY-1 )
                   : getProcId( myX+1, myY );
        }
        if ( myX == dimX - 1)
        {
            //last upper line
            return (myY % 2 == 0)? getProcId( myX-1, myY )
                   : getProcId( myX, myY-1 );
        }
        return (myY % 2 == 0)? getProcId( myX-1, myY )
               : getProcId( myX+1, myY );

        //==================================================================

    }
    else
    {
        //very bad. since 3*3... 5*5; 7*7; ...
        //==================================================================

        //3.
        if ( myY == 0)
        {
            //first line
            if ( myX == 0 ) return getProcId( myX+1, myY+1 );
            //if ( myX == dimX - 1 ) return getProcId( myX-1, myY );
            return getProcId( myX-1, myY );
        }
        if ( myX > 1)
        {
            if ( myY == 1)
            {
                //second line
                //if ( myX == 0 ) return getProcId( myX, myY-1 );
                if ( myX == dimX - 1 ) return getProcId( myX, myY-1 );

                return (myX % 2 == 0)? getProcId( myX+1, myY )
                       : getProcId( myX, myY+1 );
            }
            if ( myY == dimY - 1)
            {
                //last upper line
                return (myX % 2 == 0)? getProcId( myX, myY-1 )
                       : getProcId( myX+1, myY );
            }
            return (myX % 2 == 0)? getProcId( myX, myY-1 )
                   : getProcId( myX, myY+1);
        }
        if (myX == 1 && myY == 1) return getProcId( myX-1, myY );
        if (myX == 1 && myY == dimY - 1) return getProcId( myX+1, myY );

        if (myX == 0)
            return (myY % 2 == 0)? getProcId( myX+1, myY )
                   : getProcId( myX, myY+1);
        else
            return (myY % 2 == 0)? getProcId( myX, myY+1 )
                   : getProcId( myX-1, myY );

        //==================================================================
    }

    printf("BUG! noting found\n");

    return id;
}

int getRight()
{
////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (  dimX <= 1 && dimY <= 1  )
    {
        printf( " Error: must run at more than one processor!" );
        return 0;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if ( dimX == 1)
    {
        if ( myY == 0) return 1;
        if ( myY == dimY - 1 ) return 0;
        return myY + 1;
    }
    if ( dimY == 1)
    {
        if ( myX == 0) return 1;
        if ( myX == dimX - 1 ) return 0;
        return myX+1;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////

    if ( dimY == 2)
    {
        if ( myY == 0 )
        {
            if ( myX == 0 ) return getProcId( myX+1, myY );
            if ( myX == dimX - 1 ) return  getProcId( myX, myY+1 );
            return getProcId( myX+1, myY );
        }
        else
            if ( myY == 1 )
            {
                if ( myX == 0 ) return getProcId( myX, myY-1 );
                if ( myX == dimX - 1 ) return  getProcId( myX-1, myY );
                return getProcId( myX-1, myY );
            }
        printf("BUG! dimY==2 and myY!=0/1!");
        return 0;
    }

    if ( dimX == 2)
    {
        if ( myX == 0 )
        {
            if ( myY == 0 ) return getProcId( myX+1, myY );
            if ( myY == dimY - 1 ) return  getProcId( myX, myY-1 );
            return getProcId( myX, myY-1 );
        }
        else
            if ( myX == 1 )
            {
                if ( myY == 0 ) return getProcId( myX, myY+1 );
                if ( myY == dimY - 1 ) return  getProcId( myX-1, myY );
                return getProcId( myX, myY+1 );
            }
        printf("BUG! dimX==2 and myX!=0/1!");
        return 0;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int id = 0;
    if ( dimX % 2 == 0)
    {

        //==================================================================

        //1.
        if ( myY == 0)
        {
            //first line
            if ( myX == 0 ) return getProcId( myX+1, myY );
            if ( myX == dimX - 1 ) return getProcId( myX, myY+1 );
            return getProcId( myX+1, myY );
        }
        if ( myY == 1)
        {
            //second line
            if ( myX == 0 ) return getProcId( myX, myY-1 );
            if ( myX == dimX - 1 ) return getProcId( myX, myY+1 );

            return (myX % 2 == 0)? getProcId( myX-1, myY )
                   : getProcId( myX, myY+1 );
        }
        if ( myY == dimY - 1)
        {
            //last upper line
            return (myX % 2 == 0)? getProcId( myX, myY-1 )
                   : getProcId( myX-1, myY );
        }
        return (myX % 2 == 0)? getProcId( myX, myY-1 )
               : getProcId( myX, myY+1);

        //==================================================================

    }
    else if (dimY % 2 == 0)
    {

        //==================================================================

        //2.
        if ( myX == 0)
        {
            //first line
            if ( myY == 0 ) return getProcId( myX+1, myY );
            if ( myY == dimY - 1 ) return getProcId( myX, myY-1 );
            return getProcId( myX, myY-1 );
        }
        if ( myX == 1)
        {
            //second line
            if ( myY == 0 ) return getProcId( myX+1, myY );
            if ( myY == dimY - 1 ) return getProcId( myX-1, myY );

            return (myY % 2 == 0)? getProcId( myX+1, myY )
                   : getProcId( myX, myY+1 );
        }
        if ( myX == dimX - 1)
        {
            //last upper line
            return (myY % 2 == 0)? getProcId( myX, myY+1 )
                   : getProcId( myX-1, myY );
        }
        return (myY % 2 == 0)? getProcId( myX+1, myY )
               : getProcId( myX-1, myY );

        //==================================================================

    }
    else
    {
        //very bad. since 3*3... 5*5; 7*7; ...
        //==================================================================

        //3.
        if ( myY == 0)
        {
            //first line
            if ( myX == 0 ) return getProcId( myX+1, myY );
            if ( myX == dimX - 1 ) return getProcId( myX, myY+1 );
            return getProcId( myX+1, myY );
        }
        if ( myX > 1)
        {
            if ( myY == 1)
            {
                //second line
                //if ( myX == 0 ) return getProcId( myX, myY-1 );
                if ( myX == dimX - 1 ) return getProcId( myX, myY+1 );

                return (myX % 2 == 0)? getProcId( myX, myY+1 )
                       : getProcId( myX-1, myY );
            }
            if ( myY == dimY - 1)
            {
                //last upper line
                return (myX % 2 == 0)? getProcId( myX-1, myY )
                       : getProcId( myX, myY-1 );
            }
            return (myX % 2 == 0)? getProcId( myX, myY+1 )
                   : getProcId( myX, myY-1);
        }
        if (myX == 1 && myY == 1) return 0;
        if (myX == 1 && myY == dimY - 1) return getProcId( myX-1, myY );

        if (myX == 0)
            return (myY % 2 == 0)? getProcId( myX, myY-1 )
                   : getProcId( myX+1, myY);
        else
            return (myY % 2 == 0)? getProcId( myX-1, myY )
                   : getProcId( myX, myY-1);

        //==================================================================
    }


    return id;
}

int getNext(int right)
{
    if (right)
        return getRight();
    else
        return getLeft();
}

void makeChain(int right)
{
    int dead = 0;
    int i = 0;

    myX = 0;
    myY = 0;

    printf("\n\n===== CHAIN RIGHT=%d =====\n\n", right);

    i = getNext(right);
    printf("START: 0 -> %d -> ", i);

    myX = getXId(i);
    myY = getYId(i);

    while ( i != 0 && dead < 2*nProc )
    {
        i = getNext(right);

        myX = getXId(i);
        myY = getYId(i);
        printf("%d -> ", i);
        dead ++;
    }
    if (dead == 2*nProc )  printf("\n\nInfinity while!\n");
    printf("END\n");
}

int main()
{
    printf("START\n");

    //init my* dim*
    myX = 0;
    myY = 0;
    dimX = 3;
    dimY = 3;


    if ((dimX <= 1) && (dimY <= 1))
    {
        printf(" Error: must run at more than one processor!");
        return 0;
    }

    printf("dimX=%d dimY=%d \n\n", dimX, dimY );

    printf("\n\n===== TEST =====\n\n");

    nProc = dimX * dimY;
    int i = 0;
    for (; i < nProc; i++)
    {
        myX = getXId(i);
        myY = getYId(i);

        int left = getLeft();
        int right = getRight();

        printf("[%d] myX=%d myY=%d  AND left=%d right=%d \n", i, myX, myY, left, right);
    }

    makeChain(1);
    makeChain(0);

    return 0;
}
