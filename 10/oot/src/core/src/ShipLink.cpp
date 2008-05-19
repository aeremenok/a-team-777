/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file ShipLink.cpp
 * \brief реализация класса CShipLink
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "ShipLink.h"

CShipLink::CShipLink(unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::SHIP,cost,time)
{

}

std::string CShipLink::getDescription() const
{
  return "неизвестный морской перевозчик";
}

CLimcoLink::CLimcoLink(unsigned long cost, unsigned long time):CShipLink(cost,time)
{

}

std::string CLimcoLink::getDescription() const
{
  return "Лимко Логистикс";
}

CAlfaLink::CAlfaLink(unsigned long cost, unsigned long time):CShipLink(cost,time)
{

}

std::string CAlfaLink::getDescription() const
{
  return "АльфаЛайнер";
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
