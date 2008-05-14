/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file EdgeParameters.cpp
 * \brief реализация класса CEdgeParameters
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "EdgeParameters.h"

CEdgeParameters::CEdgeParameters()
{
}

bool CEdgeParameters::isAvail(CEdgeParameters::LinkType type) const
{
  for(size_t it=0;it<m_links.size();++it)
    if(m_links[it].getType()==type)
      return true;
  return false;
}

void CEdgeParameters::addLink(const CEdgeParameters::CLink& link)
{
  m_links.push_back(link);
}

size_t CEdgeParameters::linkCount() const
{
  return m_links.size();
}

const CEdgeParameters::CLink& CEdgeParameters::getLink(LinkType type) const
{
  for(size_t it=0;it<m_links.size();++it)
    if(m_links[it].getType()==type)
      return m_links[it];
  
  throw;
}

const CEdgeParameters::CLink& CEdgeParameters::getLink(size_t index) const
{
  return m_links[index];
}

CEdgeParameters::~CEdgeParameters()
{

}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
