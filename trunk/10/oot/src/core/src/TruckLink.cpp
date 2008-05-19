/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file TruckLink.cpp
 * \brief реализация класса CTruckLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "TruckLink.h"

CTruckLink::CTruckLink(unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::TRUCK,cost,time)
{

}

std::string CTruckLink::getDescription() const
{
  return "неизвестный дорожный перевозчик";
}

CCargoLink::CCargoLink(unsigned long cost, unsigned long time):CTruckLink(cost,time)
{

}

std::string CCargoLink::getDescription() const
{
  return "Cargo";
}

CVasjaLink::CVasjaLink(unsigned long cost, unsigned long time):CTruckLink(cost,time)
{

}

std::string CVasjaLink::getDescription() const
{
  return "ООО Василий и сыновья";
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
