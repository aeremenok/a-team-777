# $Id: Makefile,v 1.1 2007/01/25 13:49:02 dumb Exp $

vpath %.cpp ./src ./tst

ROOT ?= ..

# target name & sources list
TARGET ?= core

SRCS = Graph.cpp User.cpp UserCookie.cpp City.cpp EdgeParameters.cpp DeliveryNetwork.cpp \
       AccessibleCitySearch.cpp PossibleRoutes.cpp AircraftLink.cpp ShipLink.cpp TrainLink.cpp \
       TruckLink.cpp


test_SRCS := TestSuite_core.cpp
test_LDADD += -lcppunit -lboost_serialization

default: lib

include ${ROOT}/inc.inc
include ${ROOT}/Makefile.inc
