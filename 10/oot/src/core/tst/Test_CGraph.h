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
   * \brief тесты для класса CGraph
   */
  class CGraphTest : public CppUnit::TestFixture
  {
    CPPUNIT_TEST_SUITE(CGraphTest);

    CPPUNIT_TEST(testEmptyInt);
    CPPUNIT_TEST(testEmptyString);
    CPPUNIT_TEST(testAddVertexEdges);
    CPPUNIT_TEST(testRemoveVertices);
    CPPUNIT_TEST(testSerialize);

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
    
    void testIterator()
    {
      CGraph<int> g;
      g.add(1);
      g.add(2);
      for(class CGraph<int>::vertex_iterator it=g.vertex_begin();it!=g.vertex_end();++it)
      {

      }

      std::list<int> l;
      l.push_back(2);
      l.push_back(2);
      l.push_back(2);
      l.push_back(2);
      CPPUNIT_ASSERT(l.size()==4);
    }
    
    void testSerialize()
    {
    	
      std::ofstream	ofs("test.xml");
      boost::archive::xml_oarchive oarchive(ofs);
      CGraph<int,int> savedGraph;
      try	
      {
        oarchive << boost::serialization::make_nvp("Graph", savedGraph);
      }
      catch(...){
        CPPUNIT_ASSERT(false);
      }
      ofs.close();

      std::ifstream	ifs("test.xml");
      boost::archive::xml_iarchive iarchive(ifs);

      CGraph<int,int> loadedGraph;

      try	
      {
        iarchive >> boost::serialization::make_nvp("Graph", loadedGraph);
      }
      catch(...){
        CPPUNIT_ASSERT(false);
      }

      ifs.close();

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
