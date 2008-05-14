/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * file DeliveryNetwork.cpp
 * \brief ���������� CDeliveryNetwork::getAccessibleCity
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <vector>
#include <set>
#include "DeliveryNetwork.h"

std::set<CCity> CDeliveryNetwork::getAccessibleCity(const CCity& from) const
{
  std::set<CCity> list;
  std::list<CCity> queue; //!< очередь вершин на обход
 
  queue.push_back(from);
  list.insert(from);
 
  while(queue.size())
  {
    CCity current = queue.front();
    std::list<CCity> adjacent = m_graph.adjacent_vertices(current);
    for(std::list<CCity>::iterator it=adjacent.begin();it!=adjacent.end();++it)
    {
      if(list.count(*it)==0) // в этой вершине не были
      {
        list.insert(*it);
        queue.push_back(*it);
      }
    }
    queue.pop_front();
  }
  return list;
}

