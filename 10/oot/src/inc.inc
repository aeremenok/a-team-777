# $Id: inc.inc,v 1.8 2007/03/01 12:22:13 dumb Exp $

INC += -I./inc -I${ROOT}/inc -I${ROOT}/../inc \
       -I${ROOT}/core/inc 

LIBADD += ${ROOT}/oot.a
LDADD +=

DEFS += -D_GNU_SOURCE=1