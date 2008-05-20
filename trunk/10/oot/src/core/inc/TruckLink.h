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

#include "DefaultLink.h"

class CTruckLink: public CDefaultLink
{
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CDefaultLink);
  }
  
public:
  CTruckLink(unsigned long cost=0, unsigned long time=0);
  
  virtual std::string getDescription() const;

  virtual ~CTruckLink()
  {
  }
};

class CCargoLink: public CTruckLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CTruckLink);
  }
public:
  CCargoLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;
};

class CVasjaLink: public CTruckLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CTruckLink);
  }
public:
  CVasjaLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;
};

#endif //_CTruckLink_H_50B3E148_419E_495D_A2E6_428B2FE917B7_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
