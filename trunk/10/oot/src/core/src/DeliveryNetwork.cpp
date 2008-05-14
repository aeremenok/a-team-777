/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DeliveryNetwork.cpp
 * \brief реализация класса CDeliveryNetwork
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "DeliveryNetwork.h"

CDeliveryNetwork::CDeliveryNetwork()
{

}

std::set<CCity> CDeliveryNetwork::getCitys() const
{
  std::set<CCity> result;
  std::list<CCity> list = m_graph.vertices_list();
  for(std::list<CCity>::iterator it=list.begin();it!=list.end();++it)
    result.insert(*it);
  return result;
}

void CDeliveryNetwork::addCity(const CCity& city)
{
  m_graph.add(city);
}

bool CDeliveryNetwork::isEdgeAvail(const CCity& from, const CCity& to) const
{
  CDeliveryNetwork::Graph::pair edge(from,to);
  for(CDeliveryNetwork::Graph::edge_const_iterator it=m_graph.edge_begin();it!=m_graph.edge_end(); ++it)
    if((*it)==edge)
      return true;

  return false;
}

const CDeliveryNetwork::Graph::edge& CDeliveryNetwork::getEdge(const CCity& from, const CCity& to) const
{
  CDeliveryNetwork::Graph::pair edge(from,to);
  for(CDeliveryNetwork::Graph::edge_const_iterator it=m_graph.edge_begin();it!=m_graph.edge_end(); ++it)
    if((*it)==edge)
      return *it;

//\todo вставить исключение "ребро не нашел"
}

void CDeliveryNetwork::addRoute(const CCity& from, const CCity& to, CCostType::Type type, unsigned long cost, unsigned long time)
{
  CDeliveryNetwork::Graph::pair p(from,to);
  
  CCostType costType;
  costType.setCost(type,cost,time);

  if(isEdgeAvail(from,to))
  {
    costType = getEdge(from,to).cost;
    costType.setCost(type,cost,time);
  }
  m_graph.add(p,costType);
}

CDeliveryNetwork& CDeliveryNetwork::getInstance()
{
  static CDeliveryNetwork w;
  return w;
}

CDeliveryNetwork::~CDeliveryNetwork()
{

}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
