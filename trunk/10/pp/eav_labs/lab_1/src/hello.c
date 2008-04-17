#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char* argv[]){

    char* buf;
    int bufsize = 0;
    int i;

	if (argc < 2)
	{
	    printf("use > 2 args\n");
	    return -1;
	}

	printf("argc %d\n", argc);

	for (i = 1; i < argc; i++)
	{
	    bufsize+= strlen(argv[i]) + 1;
	}

	printf("bufsize %d\n", bufsize);
	buf = (char*)malloc( sizeof(char) * bufsize);
	printf("malloc\n");
	strcpy(buf, "");

	printf("buf %s", buf);

	for (i = 1; i < argc; i++)
	{
	    strcat(buf, argv[i]);
	    strcat(buf, " ");
	}

	printf ("running %s\n", buf);

	system(buf);

	return 0;
} 
