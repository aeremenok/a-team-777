# $Id: Makefile,v 1.1 2007/11/14 11:12:50 bea Exp $

ROOT ?= ../..

vpath %.cpp ./src ./tst
vpath %.h   ./inc ./src
vpath %.ui  ./src

MAKE_FLAGS += qt4

TARGET := LoginDialog

DEFS += -DQT_THREAD_SUPPORT

INC += -I./ -I./ui

UIS := LoginDialog.ui
UICS := LoginDialog.cpp

default: compile lib

exe : default

include ${ROOT}/inc.inc
include ${ROOT}/Makefile.inc
