/*!
 * \file Graph.h
 * \brief заголовок класса CGraph
 * 
 * 
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#ifndef _CGraph_H_11012290_C04A_495C_8913_6C0A0D5FD41D_INCLUDED_
#define _CGraph_H_11012290_C04A_495C_8913_6C0A0D5FD41D_INCLUDED_

#include <utility>
#include <iterator>
#include <set>
#include <list>
#include <algorithm> 

#include <boost/scoped_ptr.hpp>

//foreach
#include <boost/foreach.hpp>

#ifndef foreach
#define foreach BOOST_FOREACH
#endif

// Serialization
#include <boost/serialization/nvp.hpp>
#include <boost/serialization/utility.hpp>
#include <boost/serialization/list.hpp>
#include <boost/serialization/string.hpp>
#include <boost/serialization/version.hpp>
#include <boost/serialization/split_member.hpp>
#include <boost/archive/tmpdir.hpp>
#include <boost/archive/xml_iarchive.hpp>
#include <boost/archive/xml_oarchive.hpp>

#include <boost/serialization/export.hpp>	// must be in the end of serializatrion headers list

#include "DefaultLink.h"
#include "AircraftLink.h"
#include "ShipLink.h"
#include "TrainLink.h"
#include "TruckLink.h"
#include "City.h"

/*!
 * \brief путь
 */
template <typename VertexType, typename LinkType>
class CPath 
{
  typedef VertexType vertex;
  
  std::list<vertex>  vertices_list; //!< список вершин
  std::list<LinkType*>    link_list; //!< список типов перевозки между вершинами

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(vertices_list);
    ar & BOOST_SERIALIZATION_NVP(link_list);
  }

public:
  typedef typename std::list<LinkType*> Linklist;
  typedef typename std::list<VertexType> Vertexlist;
  typedef typename Vertexlist::iterator vertex_iterator;
  typedef typename Vertexlist::const_iterator vertex_const_iterator;
  typedef typename Linklist::iterator link_iterator;
  typedef typename Linklist::const_iterator link_const_iterator;

  vertex_iterator vertex_begin() { return vertices_list.begin(); }
  vertex_iterator vertex_end() { return vertices_list.end(); }

  vertex_const_iterator vertex_begin() const{ return vertices_list.begin(); }
  vertex_const_iterator vertex_end() const { return vertices_list.end(); }
  
  link_iterator link_begin() { return link_list.begin(); }
  link_iterator link_end() { return link_list.end(); }
  
  link_const_iterator link_begin() const { return link_list.begin(); }
  link_const_iterator link_end() const { return link_list.end(); }

  CPath()
  {
  }

  bool operator < (const CPath& other) const 
  {
    if (getCost() == other.getCost()) 
      return vertices_list.size() < vertices_list.size();
    else 
      return (getCost() < other.getCost());
  }

  const vertex& vertex_back() const
  {
    return vertices_list.back();
  }
  
  const vertex& vertex_front() const
  {
    return vertices_list.front();
  }

  void push_back(const vertex& v, const LinkType *c)
  {
    vertices_list.push_back(v);
    link_list.push_back(c);
  }
  
  void push_back(const vertex& v, LinkType *c)
  {
    vertices_list.push_back(v);
    link_list.push_back(c);
  }
  
  void pop_back()
  {
    vertices_list.pop_back();
    link_list.pop_back();
  }

  unsigned long getCost() const
  {
    unsigned long cost=0;
    for(typename std::list<LinkType*>::const_iterator it=link_list.begin();it!=link_list.end();++it)
      cost += (*it)->getCost();
    return cost;
  }
  
  bool exist(const vertex& v) const
  {
    for(typename std::list<vertex>::const_iterator it=vertices_list.begin(); it!=vertices_list.end(); it++)
    {
      if((*it)==v)
        return true;
    }
    return false;
  }

};

template<>
template<class Archive>
void CPath<CCity, CDefaultLink>::serialize(Archive & ar, const unsigned int version)
{
    ar.register_type(static_cast<CStupidLink *>(NULL));
    ar.register_type(static_cast<CDefaultLink *>(NULL));
    ar.register_type(static_cast<CAircraftLink *>(NULL));
    ar.register_type(static_cast<CS7Link *>(NULL));
    ar.register_type(static_cast<CPulkovoLink *>(NULL));
    ar.register_type(static_cast<CAeroflotLink *>(NULL));
    ar.register_type(static_cast<CShipLink *>(NULL));
    ar.register_type(static_cast<CLimcoLink *>(NULL));
    ar.register_type(static_cast<CAlfaLink *>(NULL));
    ar.register_type(static_cast<CTrainLink *>(NULL));
    ar.register_type(static_cast<CRZDLink *>(NULL));
    ar.register_type(static_cast<CASDLink *>(NULL));
    ar.register_type(static_cast<CTruckLink *>(NULL));
    ar.register_type(static_cast<CCargoLink *>(NULL));
    ar.register_type(static_cast<CVasjaLink *>(NULL));
    ar & BOOST_SERIALIZATION_NVP(vertices_list);
    ar & BOOST_SERIALIZATION_NVP(link_list);
}

/*!
 * \brief граф
 */
template<typename VertexType, typename EdgeType = int> 
class CGraph 
{
public:
  typedef VertexType                vertex; //!< вершина
  typedef std::pair<vertex, vertex> pair;   //!< пара

  /*!
   * \brief ребро
   */
  struct edge 
  {
    pair    vertex_pair; //!< соединяемые вершины
    EdgeType  cost;      //!< вес ребра

    edge(const pair& e, const EdgeType& c): vertex_pair(e), cost(c) 
    {
    }

    bool operator == (const pair& other) const 
    {
      return vertex_pair.first == other.first &&
             vertex_pair.second == other.second;
    }

    bool operator == (const edge& other) const 
    {
      return vertex_pair.first == other.vertex_pair.first &&
             vertex_pair.second == other.vertex_pair.second;
    }
    
    bool operator < (const edge& other) const 
    {
      if(vertex_pair.first!=other.vertex_pair.first)
        return vertex_pair.first < other.vertex_pair.first;
      return vertex_pair.second < other.vertex_pair.second;
    }
    
    bool operator != (const edge& other) const 
    {
      return vertex_pair.first != other.vertex_pair.first &&
             vertex_pair.second != other.vertex_pair.second;
    }

  private: 
    edge()
    {}
    
    friend class boost::serialization::access;
    template<class Archive>
    void serialize(Archive & ar, const unsigned int version)
    {
      ar & BOOST_SERIALIZATION_NVP(vertex_pair);
      ar & BOOST_SERIALIZATION_NVP(cost);
    }
  };
  
private:
  /*!
   * \brief Внутреннее представление графа  
   */
  std::list<edge>        edges;  //!< набор ребер
  std::list<vertex>      vertices; //!< набор вершин
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar.register_type(static_cast<CStupidLink *>(NULL));
    ar.register_type(static_cast<CDefaultLink *>(NULL));
    ar.register_type(static_cast<CAircraftLink *>(NULL));
    ar.register_type(static_cast<CS7Link *>(NULL));
    ar.register_type(static_cast<CPulkovoLink *>(NULL));
    ar.register_type(static_cast<CAeroflotLink *>(NULL));
    ar.register_type(static_cast<CShipLink *>(NULL));
    ar.register_type(static_cast<CLimcoLink *>(NULL));
    ar.register_type(static_cast<CAlfaLink *>(NULL));
    ar.register_type(static_cast<CTrainLink *>(NULL));
    ar.register_type(static_cast<CRZDLink *>(NULL));
    ar.register_type(static_cast<CASDLink *>(NULL));
    ar.register_type(static_cast<CTruckLink *>(NULL));
    ar.register_type(static_cast<CCargoLink *>(NULL));
    ar.register_type(static_cast<CVasjaLink *>(NULL));
    ar & BOOST_SERIALIZATION_NVP(edges);
    ar & BOOST_SERIALIZATION_NVP(vertices);
  }

public:
  CGraph()
  {
  }

  /*! 
   * \brief Добавить вершину. 
   * Если такая вершина уже есть, то второй раз она не добавляется
   */
  void add(const vertex& v) 
  {
    typename std::list<vertex>::iterator it = std::find(vertices.begin(), vertices.end(), v);
    if(it == vertices.end()) 
      vertices.push_back(v);
  }

  /*!
   * \brief Добавить ребро. 
   * Если такое ребро уже есть, то оно замещается указанным
   */
  void add(const pair& p, const EdgeType& c) 
  {
    remove(p);

    edges.push_back(edge(p, c));

    add(p.first);
    add(p.second);
  }

  /*!
   * \brief удалить ребро
   */
  void remove(const pair& p) 
  {
    typename std::list<edge>::iterator it = std::find(edges.begin(), edges.end(), p);
    if(it != edges.end()) {
      edges.erase(it);
    }
  }

  /*!
   * \brief удалить вершину
   */
  void remove(const vertex& v) 
  {
    typename std::list<vertex>::iterator vertex_it = std::find(vertices.begin(), vertices.end(), v);
    if (vertex_it == vertices.end()) 
      return;
    else
      vertices.erase(vertex_it);

    // удаляем ребра, связанные с этой вершиной
    typename std::list<edge>::iterator edge_it = edges.begin();
    while(edge_it != edges.end()) 
    {
      if (edge_it->vertex_pair.first == v) 
      {
        edge_it = edges.erase(edge_it);
      } 
      else if (edge_it->vertex_pair.second == v) 
      {
        edge_it = edges.erase(edge_it);
      } 
      else 
      {
        ++edge_it;
      }
    }
  }

  int edges_count() const 
  {
    return static_cast<int>(edges.size());
  }

  int vertices_count() const 
  {
    return static_cast<int>(vertices.size());
  }

  std::list<edge> edges_list() const 
  {
    return edges;
  }

  std::list<vertex>  vertices_list() const 
  {
    return vertices;
  }

  typedef typename std::list<edge>::iterator      edge_iterator;
  typedef typename std::list<edge>::const_iterator  edge_const_iterator;

  edge_iterator edge_begin() 
  {
    return edges.begin();
  }

  edge_iterator edge_end() 
  {
    return edges.end();
  }

  edge_const_iterator edge_begin() const 
  {
    return edges.begin();
  }

  edge_const_iterator edge_end() const 
  {
    return edges.end();
  }

  /*!
   * \brief получить список смежных ребер
   * \param v вершина для которой требуется список ребер
   */
  std::list<edge>  adjacent_edges(const VertexType& v) const 
  {
    std::list<edge> result;

    foreach(const edge& e, edges) {
      if (e.vertex_pair.first == v) {
        result.push_back(e);
      }
    }

    return result;
  }

  /*!
   * \brief получить список смежных вершин
   * \param v вершина для которой требуется список вершин
   */
  std::list<vertex>  adjacent_vertices(const VertexType& v) const 
  {
    std::list<vertex> result;

    foreach(const edge& e, edges) {
      if (e.vertex_pair.first == v) {
        result.push_back(e.vertex_pair.second);
      }
    }

    return result;
  }

  /*!
   * \brief итератор по вершинам
   * в свою очередь предоставляет итератор по смежным ребрам
   */
  class vertex_iterator : public std::iterator<std::bidirectional_iterator_tag, VertexType>  
  {
    typedef class CGraph<VertexType, EdgeType>      _Owner;
    typedef VertexType&                  reference;
    typedef VertexType*                  pointer;
    typedef typename std::list<VertexType>::iterator  _NodePtr;

    _Owner*              owner;
    std::auto_ptr<std::list<edge> > adjacent_edge;

    _NodePtr            node;
  public:
    vertex_iterator() : owner(0)
    {

    }

    vertex_iterator(const vertex_iterator&v)
    {
      owner = v.owner;
      node = v.node;
      adjacent_edge.reset(new std::list<edge>(*v.adjacent_edge));
    }

    vertex_iterator(_Owner* pOwner, _NodePtr _node) : owner(pOwner), node(_node)
    {

    }

    reference operator*() const 
    {
      return *node;
    }

    pointer operator->() const 
    {
      return &(*node);
    }

    vertex_iterator& operator++(int) 
    {
      vertex_iterator _temp = (*this);
      node++;

      return _temp;
    }

    vertex_iterator& operator++() 
    {
      node++;
      return (*this);
    }

    vertex_iterator& operator--(int) 
    {
      vertex_iterator _temp = (*this);
      node--;

      return _temp;
    }

    vertex_iterator& operator--() 
    {
      node--;
      return (*this);
    }


    inline bool operator ==(const vertex_iterator& other) const 
    {
      return (other.owner == owner) && (other.node == node);
    }

    inline bool operator !=(const vertex_iterator& other) const 
    {
      return !(operator==(other));
    }

    edge_iterator edge_begin() 
    {
      _check_adjacent_edge();
      return adjacent_edge->begin();
    }

    edge_iterator edge_end() 
    {
      _check_adjacent_edge();
      return adjacent_edge->end();
    }

    edge_const_iterator edge_begin() const 
    {
      _check_adjacent_edge();
      return adjacent_edge->begin();
    }

    edge_const_iterator edge_end() const 
    {
      _check_adjacent_edge();
      return adjacent_edge->end();
    }

  private:
    void _check_adjacent_edge() 
    {
      if(!adjacent_edge.get())
        adjacent_edge.reset(new std::list<edge>(owner->adjacent_edges(*node)));
    }
  };

  vertex_iterator vertex_begin() 
  {
    return vertex_iterator(this, vertices.begin());
  }

  vertex_iterator vertex_end() 
  {
    return vertex_iterator(this, vertices.end());
  }
};

#endif

