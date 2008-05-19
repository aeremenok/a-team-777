/* $Id: TestSuite.cpp.t,v 1.2 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file TestSuite_core.cpp
 * \brief Test suite for core package
 *
 * PROJ: oot (Test set)
 *
 * ------------------------------------------------------------------------ */

#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/ui/text/TestRunner.h>

#include "Test_CGraph.h"
#include "Test_Serialization.h"

int main()
{
  CppUnit::TextUi::TestRunner runner;
  CppUnit::TestFactoryRegistry &registry=CppUnit::TestFactoryRegistry::getRegistry();
  runner.addTest(registry.makeTest());
  return !runner.run();
}

/* ===[ End of file $Source: /cvs/decisions/templates/TestSuite.cpp.t,v $ ]=== */
