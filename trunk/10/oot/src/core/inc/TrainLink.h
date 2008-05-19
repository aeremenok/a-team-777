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

#include "EdgeParameters.h"

class CTrainLink: public CDefaultLink
{
  
public:
  CTrainLink(unsigned long cost, unsigned long time);
  
  virtual std::string getDescription() const;

  virtual ~CTrainLink()
  {
  }
};

class CRZDLink: public CTrainLink
{
public:
  CRZDLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;
};

class CASDLink: public CTrainLink
{
public:
  CASDLink(unsigned long cost, unsigned long time);
  
  std::string getDescription() const;
};

#endif //_CTrainLink_H_6FB7D836_EA20_4459_8857_DB5F89CAEFA7_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
