/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * file DeliveryNetwork.cpp
 * \brief ���������� CDeliveryNetwork::getPossibleRoutes
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <vector>
#include <set>
#include "DeliveryNetwork.h"

/*!
 * ��� ��� ������ � ���� ������ �������, � ����� ���������� ����� ����� ���� ��������� ��������� �� ���� ��� ������ � ��������� ��� 
 * �� ��������� �� ���� ���������
 */
//id CDeliveryNetwork::makePossiblesRoutes(CDeliveryNetwork::Graph::path& current, std::list<CDeliveryNetwork::Graph::path> &res)
//{
   
//}


void CDeliveryNetwork::findPossiblesRoutes(const CCity& destination, CDeliveryNetwork::Graph::path& current, std::list<CDeliveryNetwork::Graph::path> &res)
{
  CCity last = current.lastVertex();
  if(last==destination) // ������
  {
//    makePossiblesRoutes(current,res);
    return;
  }
  std::list<CDeliveryNetwork::Graph::edge> adjacent = m_graph.adjacent_edges(last);

  for(std::list<CDeliveryNetwork::Graph::edge>::iterator it=adjacent.begin();it!=adjacent.end();++it)
  {
    if(!current.exist(it->vertex_pair.second)) // �� ������� �� ��� �� ������
    {
      current.addVertex(it->vertex_pair.second, it->cost);
      findPossiblesRoutes(destination,current,res);
    }
  }
}

std::list<CDeliveryNetwork::Graph::path> CDeliveryNetwork::getPossibleRoutes(const CCity& from, const CCity& to)
{
  CDeliveryNetwork::Graph::path current(from);
  std::list<CDeliveryNetwork::Graph::path> res;
  findPossiblesRoutes(to, current, res);
  return res;
}

