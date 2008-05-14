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

bool CEdgeParameters::isAvail(CEdgeParameters::Type type) const
{
  return m_links.count(type) > 0;
}

void CEdgeParameters::addLink(const CEdgeParameters::CLink& link)
{
  m_links.insert(link);
}

size_t CEdgeParameters::linkCount() const
{
  return m_links.size();
}

const CLink& getLink(LinkType type) const
{
  return m_links.find(type);
}

CEdgeParameters::~CEdgeParameters()
{

}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
