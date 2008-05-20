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

#include "DefaultLink.h"

/*!
 * \brief
 */
class CShipLink: public CDefaultLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CDefaultLink);
  }
  
public:
  CShipLink(unsigned long cost=0, unsigned long time=0);
  
  virtual std::string getDescription() const;

  virtual ~CShipLink()
  {
  }
};

class CLimcoLink: public CShipLink
{
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CShipLink);
  }

public:
  CLimcoLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;

};

class CAlfaLink: public CShipLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CShipLink);
  }

public:
  CAlfaLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;

};
#endif //_CShipLink_H_2C04E306_B068_4D17_BAE7_A34828E9A266_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
