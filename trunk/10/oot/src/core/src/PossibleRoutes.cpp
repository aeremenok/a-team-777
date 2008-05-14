/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * file DeliveryNetwork.cpp
 * \brief реализация CDeliveryNetwork::getPossibleRoutes
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <vector>
#include <set>
#include "DeliveryNetwork.h"

void CDeliveryNetwork::findPossiblesRoutes(const CCity& destination, CDeliveryNetwork::Path& current, std::list<CDeliveryNetwork::Path> &res)
{
  CCity last = current.vertex_back();
  if(last==destination) // пришли
  {
    res.push_back(current);
    return;
  }
  std::list<CDeliveryNetwork::Graph::edge> adjacent = m_graph.adjacent_edges(last);

  for(std::list<CDeliveryNetwork::Graph::edge>::iterator it=adjacent.begin();it!=adjacent.end();++it)
  {
    if(!current.exist(it->vertex_pair.second)) // по вершине мы еще не ходили
    {
      for(size_t i=0; i< it->cost.linkCount(); ++i)
      {
        CEdgeParameters::CLink link = it->cost.getLink(i);
        current.add(it->vertex_pair.second, link);
        findPossiblesRoutes(destination,current,res);
      }
    }
  }
}

std::list<CDeliveryNetwork::Path> CDeliveryNetwork::getPossibleRoutes(const CCity& from, const CCity& to)
{
  CDeliveryNetwork::Path current;
  current.add(from, CEdgeParameters::CLink());

  std::list<CDeliveryNetwork::Path> res;
  findPossiblesRoutes(to, current, res);
  return res;
}

