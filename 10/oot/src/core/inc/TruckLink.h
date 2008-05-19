/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file TruckLink.h
 * \brief заголовок класса CTruckLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CTruckLink_H_50B3E148_419E_495D_A2E6_428B2FE917B7_INCLUDED_
#define _CTruckLink_H_50B3E148_419E_495D_A2E6_428B2FE917B7_INCLUDED_

#include "EdgeParameters.h"

class CTruckLink: public CDefaultLink
{
  
public:
  CTruckLink(unsigned long cost, unsigned long time);
  
  virtual std::string getDescription() const;

  virtual ~CTruckLink()
  {
  }
};

class CCargoLink: public CTruckLink
{
public:
  CCargoLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;
};

class CVasjaLink: public CTruckLink
{
public:
  CVasjaLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;
};

#endif //_CTruckLink_H_50B3E148_419E_495D_A2E6_428B2FE917B7_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
