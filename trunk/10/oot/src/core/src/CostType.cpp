/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file CostType.cpp
 * \brief реализация класса CCostType
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "CostType.h"

CCostType::CCostType()
{
  for(int i=AIRLINES; i<UNKNOWN; ++i)
  {
    m_costs[(Type)i]=0;
    m_avail[(Type)i]=false;
  }
}

void CCostType::setCost(CCostType::Type type, unsigned long cost)
{
  m_costs[type] = cost;
  m_avail[type] = true;
}

unsigned long CCostType::getCost(CCostType::Type type) const
{
  return m_costs.find(type)->second;
}

bool CCostType::isAvail(CCostType::Type type) const
{
  return m_avail.find(type)->second;
}

CCostType::~CCostType()
{

}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
