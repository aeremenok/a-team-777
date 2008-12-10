#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("need 2 argument of type 'int'\n");
        return 1;
    }
    
    int dimX = atoi(argv[1]);
    int dimY = atoi(argv[2]);

    int nProcs = dimX * dimY;
    int path[nProcs];
    int k;
    int curX, curY;
    int next;
    
    for (k = 0; k < nProcs; k++) {
        path[k] = 0;
    }
    
    for (k = 0; k < nProcs; k++) {
        curX = k % dimX;
        curY = k / dimX;
        if (dimX == 1 || dimY == 1) {
            // trivial 'row' or 'column' solution
            path[k] = (k + 1) % nProcs;
        } else {
            if (curY == 0) {
                // the very bottom row
                if (curX != dimX - 1) {
                    next = k + 1;
                } else {
                    next = k + dimX;
                }
            } else if (curX == dimX - 1) {
                // the very right column
                if (curY != dimY - 1) {
                    next = k + dimX;
                } else {
                    next = k - 1;
                }
            } else {
                if (dimX % 2 == 0) {
                    // simple solution
                    if (curY == dimY - 1) {
                        // top row
                        if (dimY == 2 && curX != 0) {
                            next = k - 1;
                        } else if (curX % 2 != 0) {
                            next = k - 1;
                        } else {
                            next = k - dimX;
                        }
                    } else if (curY == 1) {
                        // row above bottom row
                        if (curX == 0) {
                            next = k - dimX;
                        } else if (curX % 2 == 0) {
                            next = k - 1;
                        } else {
                            next = k + dimX;
                        }
                    } else {
                        // any other cell
                        if (curX % 2 == 0) {
                            next = k - dimX;
                        } else {
                            next = k + dimX;
                        }
                    }
                } else {
                    // dances with a tambourine =)
                    if (curX > 1) {
                        // so called 'standard' path
                        if (curY == dimY - 1) {
                            // top row
                            if (dimY == 2 && curX != 0) {
                                next = k - 1;
                            } else if (curX % 2 != 0) {
                                next = k - dimX;
                            } else {
                                next = k - 1;
                            }
                        } else if (curY == 1) {
                            // row above bottom row
                            if (curX % 2 == 0) {
                                next = k + dimX;
                            } else {
                                next = k - 1;
                            }
                        } else {
                            // any other cell
                            if (curX % 2 == 0) {
                                next = k + dimX;
                            } else {
                                next = k - dimX;
                            }
                        }
                    } else {
                        // two very left columns with non-standard path
                        if (dimY % 2 == 0) {
                            // path will be optimal
                            if (curX == 0) {
                                // first column
                                if (curY % 2 == 0) {
                                    next = k + 1;
                                } else {
                                    next = k - dimX;
                                }
                            } else {
                                // second column
                                if (curY % 2 == 0) {
                                    next = k - dimX;
                                } else {
                                    next = k - 1;
                                }
                            }
                        } else {
                            // path is not optimal
                            if (curX == 0) {
                                // first column
                                if (curY % 2 == 0) {
                                    next = k - dimX;
                                } else {
                                    next = k + 1;
                                }
                            } else {
                                // second column
                                if (curY == 1) {
                                    next = 0;
                                } else if (curY % 2 == 0) {
                                    next = k - 1;
                                } else {
                                    next = k - dimX;
                                }
                            }
                        }
                    }
                }
            }
            path[k] = next;
        }
    }
    
    printf("0");
    int go = path[0];
    for (k = 0; k < nProcs; k++) {
        printf(" ==> %d", go);
        go = path[go];
    }
    printf("\n");
}