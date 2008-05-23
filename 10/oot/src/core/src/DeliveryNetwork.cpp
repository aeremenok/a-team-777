/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DeliveryNetwork.cpp
 * \brief реализация класса CDeliveryNetwork
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */
#include <fstream>
#include "DeliveryNetwork.h"
#include "Exception.h"
CDeliveryNetwork::CDeliveryNetwork()
{
  try	
  {
    std::ifstream	ifs("map.xml");
    boost::archive::xml_iarchive iarchive(ifs);
    iarchive >> boost::serialization::make_nvp("Map", *this);
    ifs.close();
  }
  catch(...)
  { 
  }
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

  NOT_IMPLEMENTED("ребро не нашел");
}

void CDeliveryNetwork::addRoute(const CCity& from, const CCity& to, CDefaultLink *link)
{
  CDeliveryNetwork::Graph::pair p(from,to);
  
  CEdgeParameters parameters;
  parameters.addLink(link);

  if(isEdgeAvail(from,to))
  {
    parameters = getEdge(from,to).cost;
    parameters.addLink(link);
  }
  m_graph.add(p,parameters);
}

CDeliveryNetwork& CDeliveryNetwork::getInstance()
{
  static CDeliveryNetwork w;
  return w;
}

const CCity& CDeliveryNetwork::getCity(const std::string& name) 
{
  for(CDeliveryNetwork::Graph::vertex_iterator it=m_graph.vertex_begin();it!=m_graph.vertex_end();++it)
    if(it->getName() == name)
      return *it;

  NOT_IMPLEMENTED("Запрошен не существующий город");
}

CDeliveryNetwork::~CDeliveryNetwork()
{
  std::ofstream	ofs("map.xml");
  boost::archive::xml_oarchive oarchive(ofs);
  try	
  {
    oarchive << boost::serialization::make_nvp("Map", *this);
    ofs.flush();
  }
  catch(...)
  {
  }
  ofs.close();
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
