# $Id: Makefile.boot,v 1.2.2.5.2.2 2008/01/11 10:58:40 dumb Exp $
#
# скопировать в ${PROJECT}/src/Makefile
# выполняет загрузку остальных make-скриптов
#

include project.conf
-include .make/Makefile.main

define make_boot
rm -rf .make makefile Makefile.inc && \
cvs export -r ${make_LABEL} -d .make decisions/.makeNormal && \
ln -s .make/Makefile.inc Makefile.inc
endef

boot:
	@echo 'bootstrapping make'
	@${make_boot}

ifndef MAKEFILE_MAIN
ifneq (,$(filter-out boot,${MAKECMDGOALS}))

%: boot
	@${MAKE} -f .make/Makefile.main $(filter-out boot,$@)

endif
endif
