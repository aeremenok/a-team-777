/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file AircraftLink.cpp
 * \brief реализация класса CAircraftLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "AircraftLink.h"

CAircraftLink::CAircraftLink(unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::AIRLINES,cost,time)
{

}

std::string CAircraftLink::getDescription() const
{
  return "неизвестный авиаперевозчик";
}

CS7Link::CS7Link(unsigned long cost, unsigned long time):CAircraftLink(cost,time)
{
}

std::string CS7Link::getDescription() const
{
  return "S7";
}

CAeroflotLink::CAeroflotLink(unsigned long cost, unsigned long time):CAircraftLink(cost,time)
{
}

std::string CAeroflotLink::getDescription() const
{
  return "Аэрофлот";
}

CPulkovoLink::CPulkovoLink(unsigned long cost, unsigned long time):CAircraftLink(cost,time)
{
}

std::string CPulkovoLink::getDescription() const
{
  return "Пулково";
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
