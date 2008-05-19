/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file ShipLink.h
 * \brief заголовок класса CShipLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CShipLink_H_2C04E306_B068_4D17_BAE7_A34828E9A266_INCLUDED_
#define _CShipLink_H_2C04E306_B068_4D17_BAE7_A34828E9A266_INCLUDED_

#include "EdgeParameters.h"

/*!
 * \brief
 */
class CShipLink: public CDefaultLink
{
  
public:
  CShipLink(unsigned long cost, unsigned long time);
  
  virtual std::string getDescription() const;

  virtual ~CShipLink()
  {
  }
};

class CLimcoLink: public CShipLink
{

public:
  CLimcoLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;

};

class CAlfaLink: public CShipLink
{

public:
  CAlfaLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;

};
#endif //_CShipLink_H_2C04E306_B068_4D17_BAE7_A34828E9A266_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
