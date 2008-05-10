/* $Id: Test_CClass.h.t,v 1.4 2006/01/13 09:04:42 dumb Exp $ */
/*!
 * \file $(FTEST)
 * \brief Test suite for $(CLASS) class
 *
 * PROJ: oot (Test set)
 *
 * ------------------------------------------------------------------------ */


#if !defined($(GUID))
#define $(GUID)

#include <cppunit/extensions/HelperMacros.h>

namespace Test
{
  /*!
   * \brief тесты для класса $(CLASS)
   */
  class $(CLASS)Test : public CppUnit::TestFixture
  {
    CPPUNIT_TEST_SUITE($(CLASS)Test);

    CPPUNIT_TEST(testSample);

    CPPUNIT_TEST_SUITE_END();
    
  protected:

    /*!
     * \brief 
     */
    void testSample()
    {
      
    }

  public:
    void setUp()
    {
    }

    void tearDown()
    {
    }
  };//class $(CLASS)Test

  CPPUNIT_TEST_SUITE_REGISTRATION($(CLASS)Test);

}//namespace Test

#endif // $(GUID)

/* ===[ End of file $Source: /cvs/decisions/templates/Test_CClass.h.t,v $ ]=== */
