#include <stdio.h> 
#include <string.h> 
#include <malloc.h>
#include <stdlib.h> 

int main(int argc, char* argv[]) 
{
    char* buf; 
    int bufsize = 0; 
    int i;

    if (argc < 2) return 0; 

    for (i = 1; i < argc; i++ ) 
    {
        bufsize += strlen(argv[i]); 
    }

    buf = (char*) malloc( sizeof(char) * ( bufsize + (argc-1) ) ); 
    strcpy(buf, ""); 

    for ( i = 1; i < argc; i++ )
    {
        strcat(buf, argv[i]);
        strcat(buf, " ");
    }

    printf("running: %s\n", buf); 

    system( buf );

    return 0;
}
