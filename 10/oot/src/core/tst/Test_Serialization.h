/* $Id: Test_CClass.h.t,v 1.4 2006/01/13 09:04:42 dumb Exp $ */
/*!
 * \file Test_Serialization.h
 * \brief Test suite for Serialization class
 *
 * PROJ: oot (Test set)
 *
 * ------------------------------------------------------------------------ */


#if !defined(_TEST_Serialization_H_59676FA5_0539_451E_B0E6_4CD3A6E9B79F_INCLUDED_)
#define _TEST_Serialization_H_59676FA5_0539_451E_B0E6_4CD3A6E9B79F_INCLUDED_

#include <cppunit/extensions/HelperMacros.h>
#include "AircraftLink.h"

namespace Test
{
  /*!
   * \brief тесты для класса Serialization
   */
  class SerializationTest : public CppUnit::TestFixture
  {
    CPPUNIT_TEST_SUITE(SerializationTest);

    CPPUNIT_TEST(testSample);

    CPPUNIT_TEST_SUITE_END();
    
  protected:

    /*!
     * \brief 
     */
    void testSample()
    {
      std::ofstream	ofs("test.xml");
      boost::archive::xml_oarchive oarchive(ofs);
      CEdgeParameters p;
      p.addLink(new CStupidLink());
      p.addLink(new CStupidLink());
      p.addLink(new CStupidLink());
      try	
      {
        oarchive << boost::serialization::make_nvp("Graph", p);
      }
      catch(...){
        CPPUNIT_ASSERT(false);
      }
      ofs.close();
            
    }

  public:
    void setUp()
    {
    }

    void tearDown()
    {
    }
  };//class SerializationTest

  CPPUNIT_TEST_SUITE_REGISTRATION(SerializationTest);

}//namespace Test

#endif // _TEST_Serialization_H_59676FA5_0539_451E_B0E6_4CD3A6E9B79F_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/Test_CClass.h.t,v $ ]=== */
