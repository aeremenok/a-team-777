/* $Id: Test_CClass.h.t,v 1.4 2006/01/13 09:04:42 dumb Exp $ */
/*!
 * \file Test_CGraph.h
 * \brief Test suite for CGraph class
 *
 * PROJ: oot (Test set)
 *
 * ------------------------------------------------------------------------ */


#if !defined(_TEST_CGraph_H_0F90BF6D_21B6_449E_B019_CC195F69A38E_INCLUDED_)
#define _TEST_CGraph_H_0F90BF6D_21B6_449E_B019_CC195F69A38E_INCLUDED_

#include <cppunit/extensions/HelperMacros.h>
#include "Graph.h"
#include <string>

namespace Test
{
  /*!
   * \brief ����� ��� ������ CGraph
   */
  class CGraphTest : public CppUnit::TestFixture
  {
    CPPUNIT_TEST_SUITE(CGraphTest);

    CPPUNIT_TEST(testEmptyInt);
    CPPUNIT_TEST(testEmptyString);
    CPPUNIT_TEST(testAddVertexEdges);
    CPPUNIT_TEST(testRemoveVertices);

    CPPUNIT_TEST_SUITE_END();
    
  protected:

    /*!
     * \brief 
     */
    void testEmptyInt()
    {
      CGraph<int> g;
      CPPUNIT_ASSERT(g.edge_begin()==g.edge_end());
      CPPUNIT_ASSERT(g.edges_count()==0);
      CPPUNIT_ASSERT(g.vertices_count()==0);
    }

    void testEmptyString()
    {
      CGraph<std::string> g;
      CPPUNIT_ASSERT(g.edge_begin()==g.edge_end());
      CPPUNIT_ASSERT(g.edges_count()==0);
      CPPUNIT_ASSERT(g.vertices_count()==0);
    }

    void testAddVertexEdges()
    {
      CGraph<std::string> g;
      g.add("Saint-Petersburg");
      g.add("Moscow");
      CPPUNIT_ASSERT(g.edges_count()==0);
      CPPUNIT_ASSERT(g.vertices_count()==2);
      g.add(CGraph<std::string>::pair("Saint-Petersburg","Moscow"),10);
      CPPUNIT_ASSERT(g.edges_count()==1);
      CPPUNIT_ASSERT(g.vertices_count()==2);
      g.add(CGraph<std::string>::pair("Saint-Petersburg","Helsinki"),5);
      CPPUNIT_ASSERT(g.edges_count()==2);
      CPPUNIT_ASSERT(g.vertices_count()==3);
    }
    
    void testRemoveVertices()
    {
      CGraph<std::string> g;
      g.add("Saint-Petersburg");
      g.add("Moscow");
      g.add("Babruisk");
      g.add(CGraph<std::string>::pair("Saint-Petersburg","Moscow"),10);
      g.add(CGraph<std::string>::pair("Saint-Petersburg","Helsinki"),5);
      CPPUNIT_ASSERT(g.edges_count()==2);
      CPPUNIT_ASSERT(g.vertices_count()==4);
      g.remove("Babruisk");
      CPPUNIT_ASSERT(g.edges_count()==2);
      CPPUNIT_ASSERT(g.vertices_count()==3);
      g.remove("Saint-Petersburg");
      CPPUNIT_ASSERT(g.edges_count()==0);
      CPPUNIT_ASSERT(g.vertices_count()==2);
    }

  public:
    void setUp()
    {
    }

    void tearDown()
    {
    }
  };//class CGraphTest

  CPPUNIT_TEST_SUITE_REGISTRATION(CGraphTest);

}//namespace Test

#endif // _TEST_CGraph_H_0F90BF6D_21B6_449E_B019_CC195F69A38E_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/Test_CClass.h.t,v $ ]=== */
