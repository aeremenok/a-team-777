/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file TrainLink.cpp
 * \brief реализация класса CTrainLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "TrainLink.h"

CTrainLink::CTrainLink(unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::TRAIN,cost,time)
{

}

std::string CTrainLink::getDescription() const
{
  return "неизвестный железнодорожный перевозчик";
}

CRZDLink::CRZDLink(unsigned long cost, unsigned long time):CTrainLink(cost,time)
{

}

std::string CRZDLink::getDescription() const
{
  return "РЖД";
}

CASDLink::CASDLink(unsigned long cost, unsigned long time):CTrainLink(cost,time)
{

}

std::string CASDLink::getDescription() const
{
  return "АСД";
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
