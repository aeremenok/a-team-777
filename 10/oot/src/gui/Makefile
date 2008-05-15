# $Id: Makefile,v 1.26 2008/03/17 07:33:01 zps Exp $

MAKE_FLAGS += recursive qt4 

SUBUI = ${SUBDIRS}
SUBDIRS = LoginDialog NewCargoDialog RouteView GraphView

ROOT ?= ..

#.PHONY: ${SUBUI}

vpath %.cpp ./src ./tst $(addsuffix /src,${SUBUI})
vpath %.h   ./inc ./src ./ui $(addsuffix /inc,${SUBUI})
vpath %.ui  ./src $(addsuffix /src,${SUBUI})

# target name & sources list
TARGET := gui

UIS := MainWindow.ui
UICS := MainWindow.cpp


exe_SRCS := main.cpp
exe_LDFLAGS += -pthread
exe_LDADD += -lQtCore -lQtGui


DEFS += -DQT_THREAD_SUPPORT

INC += -I./ -I./ui $(addprefix -I,$(addsuffix /inc,${SUBUI})) $(addprefix -I,$(addsuffix /ui,${SUBUI}))

default: ui compile lib exe

include ${ROOT}/inc.inc
include ${ROOT}/Makefile.inc
