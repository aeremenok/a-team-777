/*!
 * \file Graph.h
 * \brief ��������� ������ CGraph
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


/*!
 * \brief ����
 */
template<typename VertexType, typename CostType = int> 
class CGraph 
{
public:
  typedef VertexType                vertex; //!< �������
  typedef std::pair<vertex, vertex> pair;   //!< ����

  /*!
   * \brief �����
   */
  struct edge 
  {
    pair    vertex_pair; //!< ����������� �������
    CostType  cost;      //!< ��� �����

    edge(const pair& e, const CostType& c): vertex_pair(e), cost(c) 
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
  };
  
  /*!
   * \brief ����
   */
  struct path 
  {
    std::list<vertex>  vertices_list; //!< ������ ������
    CostType      cost;               //!< ���������

    bool operator < (const path& other) const 
    {
      if (cost == other.cost) 
        return vertices_list.size() < vertices_list.size();
      else 
        return (cost < other.cost);
    }
  };

private:
  /*!
   * \brief ���������� ������������� �����  
   */
  std::list<edge>        edges;  //!< ����� �����
  std::list<vertex>      vertices; //!< ����� ������
public:
  CGraph()
  {
  }

  /*! 
   * \brief �������� �������. 
   * ���� ����� ������� ��� ����, �� ������ ��� ��� �� �����������
   */
  void add(const vertex& v) 
  {
    typename std::list<vertex>::iterator it = std::find(vertices.begin(), vertices.end(), v);
    if(it == vertices.end()) 
      vertices.push_back(v);
  }

  /*!
   * \brief �������� �����. 
   * ���� ����� ����� ��� ����, �� ��� ���������� ���������
   */
  void add(const pair& p, const CostType& c) 
  {
    remove(p);

    edges.push_back(edge(p, c));

    add(p.first);
    add(p.second);
  }

  /*!
   * \brief ������� �����
   */
  void remove(const pair& p) 
  {
    typename std::list<edge>::iterator it = std::find(edges.begin(), edges.end(), p);
    if(it != edges.end()) {
      edges.erase(it);
    }
  }

  /*!
   * \brief ������� �������
   */
  void remove(const vertex& v) 
  {
    typename std::list<vertex>::iterator vertex_it = std::find(vertices.begin(), vertices.end(), v);
    if (vertex_it == vertices.end()) 
      return;
    else
      vertices.erase(vertex_it);

    // ������� �����, ��������� � ���� ��������
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
   * \brief �������� ������ ������� �����
   * \param v ������� ��� ������� ��������� ������ �����
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
   * \brief �������� ������ ������� ������
   * \param v ������� ��� ������� ��������� ������ ������
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
   * \brief �������� �� ��������
   * � ���� ������� ������������� �������� �� ������� ������
   */
  class vertex_iterator : public std::iterator<std::bidirectional_iterator_tag, VertexType>  
  {
    typedef class CGraph<VertexType, CostType>      _Owner;
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

