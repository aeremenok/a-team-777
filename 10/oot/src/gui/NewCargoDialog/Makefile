# $Id: Makefile,v 1.1 2007/11/14 11:12:50 bea Exp $

ROOT ?= ../..

vpath %.cpp ./src ./tst
vpath %.h   ./inc ./src
vpath %.ui  ./src

MAKE_FLAGS += qt4

TARGET := NewCargoDialog

INCDIRS = RouteView

INC += -I./ -I./ui -I../inc -I../ui $(addprefix -I../,$(addsuffix /inc,${INCDIRS})) $(addprefix -I../,$(addsuffix /ui,${INCDIRS}))

DEFS += -DQT_THREAD_SUPPORT


UIS := NewCargoDialog.ui
UICS := NewCargoDialog.cpp

default: compile lib

include ${ROOT}/inc.inc
include ${ROOT}/Makefile.inc
