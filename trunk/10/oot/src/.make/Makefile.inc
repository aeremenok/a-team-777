# $Id: Makefile.inc,v 1.2.2.13 2007/06/25 08:03:26 dumb Exp $

ROOT ?= ..
METAROOT ?= ${ROOT}/../..

include ${ROOT}/.make/Makefile.defs
-include ${METAROOT}/arch.inc


.SUFFIXES:

OBJDIR ?= ./${CFG}

#
# это определение создает каталог объектников безотносительно к зависимостям
#
OBJ_MAGIC := $(shell mkdir ${OBJDIR} >/dev/null 2>&1)
DEP_MAGIC := $(shell find ${OBJDIR} -name '*.d' -empty -exec rm -f \{} \; )

___CPPFLAGS ?= -Wall -g3

release_DEFS += -DNDEBUG
release_CPPFLAGS += -O2
release_CFLAGS += -O2
release_CXXFLAGS += -O2

debug_DEFS += -D_DEBUG
debug_CPPFLAGS += -O0
debug_CFLAGS += -O0
debug_CXXFLAGS += -O0


#CFG GOAL MACRO

__FULL = $($(1))_$($(2))_$(3)
__GOAL = $($(2))_$(3)
__CFG  = $($(1))_$(3)

_FULL = $(if ${${__FULL}},${${__FULL}},${_GOAL})
_GOAL = $(if ${${__GOAL}},${${__GOAL}},${_CFG})
_CFG  = $(if ${${__CFG}},${${__CFG}},$($(3)))

_COMBINE = ${$(3)} ${${__GOAL}} ${${__CFG}} ${${__FULL}}
_OVERRIDE = _FULL


target = $(call _FULL,CFG,GOAL,TARGET)
srcs = $(call _FULL,CFG,GOAL,SRCS)
_srcs = ${srcs}
objs = $(call _FULL,CFG,GOAL,OBJS)

uis = $(call _FULL,CFG,GOAL,UIS)
uics = $(call _FULL,CFG,GOAL,UICS)
mocs = $(call _FULL,CFG,GOAL,MOCS)

cppflags = ${___CPPFLAGS} $(call _COMBINE,CFG,GOAL,CPPFLAGS)
cxxflags = $(call _COMBINE,CFG,GOAL,CXXFLAGS)
cflags   = $(call _COMBINE,CFG,GOAL,CFLAGS)

incs = $(call _COMBINE,CFG,GOAL,INC)
defs = $(call _COMBINE,CFG,GOAL,DEFS)

ldflags = $(call _COMBINE,CFG,GOAL,LDFLAGS)
ldadd = $(call _COMBINE,CFG,GOAL,LDADD)
libadd = $(call _COMBINE,CFG,GOAL,LIBADD)


drvd_objs = $(addprefix ${OBJDIR}/,$(notdir $(addsuffix .o,$(basename $(_srcs)))))
_objs = ${objs} ${drvd_objs}

deps = $(call _FULL,CFG,GOAL,DEPS)
drvd_deps = $(addprefix ${OBJDIR}/,$(notdir $(addsuffix .d,$(basename $(_srcs)))))
_deps = ${deps} ${drvd_deps}

#
#
#


define make_cc
echo 'Compiling ' $<
${CC} ${defs} ${incs} ${cppflags} ${cflags} -c $< -o $@
endef

define make_cdep
#echo 'Dep-making' $<
${CC} ${defs} ${incs} ${cppflags} -M -c $< | sed 's!\([^:]*\):!${OBJDIR}/\1 $@ : !' > $@ && [ -s $@ ]
endef

${OBJDIR}/%.o : %.c
	@${make_cc}

${OBJDIR}/%.d : %.c
	@${make_cdep}



define make_cxx
echo 'Compiling ' $<
${CXX} ${defs} ${incs} ${cppflags} ${cxxflags} -c $< -o $@
endef

define make_cxxdep
#echo 'Dep-making' $<
${CXX} ${defs} ${incs} ${cppflags} -M -c $< | sed 's!\([^:]*\):!${OBJDIR}/\1 $@ : !' > $@ && [ -s $@ ]
endef

${OBJDIR}/%.o : %.cpp
	@${make_cxx}

${OBJDIR}/%.d : %.cpp
	@${make_cxxdep}


#
#
#

-include ${ROOT}/extlib.inc

include ${ROOT}/.make/Makefile.subdir
include ${ROOT}/.make/Makefile.ui
include ${ROOT}/.make/Makefile.qt4
include ${ROOT}/.make/Makefile.clean
include ${ROOT}/.make/Makefile.exe
include ${ROOT}/.make/Makefile.test
include ${ROOT}/.make/Makefile.lib
include ${ROOT}/.make/Makefile.so
include ${ROOT}/.make/Makefile.release
include ${ROOT}/.make/Makefile.static
include ${ROOT}/.make/Makefile.compile
