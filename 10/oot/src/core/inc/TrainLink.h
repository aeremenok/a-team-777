/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file TrainLink.h
 * \brief заголовок класса CTrainLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CTrainLink_H_6FB7D836_EA20_4459_8857_DB5F89CAEFA7_INCLUDED_
#define _CTrainLink_H_6FB7D836_EA20_4459_8857_DB5F89CAEFA7_INCLUDED_

#include "DefaultLink.h"

class CTrainLink: public CDefaultLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CDefaultLink);
  }
  
public:
  CTrainLink(unsigned long cost=0, unsigned long time=0);
  
  virtual std::string getDescription() const;

  virtual ~CTrainLink()
  {
  }
};

class CRZDLink: public CTrainLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CTrainLink);
  }
public:
  CRZDLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;
};

class CASDLink: public CTrainLink
{
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CTrainLink);
  }
public:
  CASDLink(unsigned long cost=0, unsigned long time=0);
  
  std::string getDescription() const;
};

#endif //_CTrainLink_H_6FB7D836_EA20_4459_8857_DB5F89CAEFA7_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
