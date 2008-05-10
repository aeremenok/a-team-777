#!/bin/sh
# $Id: derive.tst,v 1.2 2006/01/13 09:00:29 dumb Exp $
#-----------------------------------------------------------------------------------

if [ -z "$1" ]; then echo 'Имя пакета для теста:'; read PACKAGE; else PACKAGE=$1; fi;
if [ -z "$2" ]; then echo 'Имя класса для теста:'; read CLASS; else CLASS=$2; fi;

UUID=`uuidgen | sed -e s/-/_/g -e y/abcdef/ABCDEF/`
NAME=TEST_${CLASS}_H
GUID=_${NAME}_${UUID}_INCLUDED_
YEAR=`date +%Y`

#echo $GUID
#echo $NAME

FSUITE="TestSuite_"$PACKAGE".cpp"
FTESTCPP="Test_"$CLASS".cpp"
FTEST="Test_"$CLASS".h"
REPL_PKG='-e s/$(PACKAGE)/'$PACKAGE'/g'
REPL_CLASS='-e s/$(CLASS)/'$CLASS'/g'
REPL_GUID='-e s/$(GUID)/'$GUID'/g'
REPL_FSUITE='-e s/$(FSUITE)/'$FSUITE'/g'
REPL_FTEST='-e s/$(FTEST)/'$FTEST'/g'
REPL_YEAR='-e s/$(YEAR)/'$YEAR'/g'

#echo $REPL_PKG
#echo $REPL_CLASS
#echo $REPL_GUID
#echo $REPL_FSUITE
#echo $REPL_FTEST

cmd="sed $REPL_PKG $REPL_CLASS $REPL_GUID $REPL_FSUITE $REPL_FTEST $REPL_YEAR"
${cmd} <TestSuite.cpp.t >$FSUITE 
#${cmd} <Test_CClass.cpp.t >$FTEST 
${cmd} <Test_CClass.h.t >$FTEST 
