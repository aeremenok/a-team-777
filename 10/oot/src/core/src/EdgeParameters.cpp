/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file EdgeParameters.cpp
 * \brief реализация класса CEdgeParameters
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "EdgeParameters.h"
#include "Exception.h"
CEdgeParameters::CEdgeParameters()
{
}

bool CEdgeParameters::isAvail(CDefaultLink::LinkType type) const
{
  for(size_t it=0;it<m_links.size();++it)
    if(m_links[it]->getType()==type)
      return true;
  return false;
}

void CEdgeParameters::addLink(CDefaultLink *link)
{
  m_links.push_back(link);
}

size_t CEdgeParameters::linkCount() const
{
  return m_links.size();
}

const CDefaultLink* CEdgeParameters::getLink(CDefaultLink::LinkType type) const
{
  for(size_t it=0;it<m_links.size();++it)
    if(m_links[it]->getType()==type)
      return m_links[it];
  
  NOT_IMPLEMENTED("ОШИБКА! Запрошено не существующее ребро");
}

const CDefaultLink* CEdgeParameters::getLink(size_t index) const
{
  return m_links[index];
}

CDefaultLink* CEdgeParameters::getLink(size_t index)
{
  return m_links[index];
}

    CEdgeParameters::~CEdgeParameters()
{
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
