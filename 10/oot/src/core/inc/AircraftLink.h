/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file AircraftLink.h
 * \brief заголовок класса CAircraftLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CAircraftLink_H_69991021_ED52_4A8B_81CB_F525E04509D5_INCLUDED_
#define _CAircraftLink_H_69991021_ED52_4A8B_81CB_F525E04509D5_INCLUDED_


#include "EdgeParameters.h"

/*!
 * \brief Воздушная трасса
 */
class CAircraftLink: public CDefaultLink
{
  
public:
  CAircraftLink(unsigned long cost, unsigned long time);
  
  virtual std::string getDescription() const;

  virtual ~CAircraftLink()
  {
  }
};//class CAircraftLink


class CS7Link: public CAircraftLink
{

public:
  CS7Link(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

class CAeroflotLink: public CAircraftLink
{

public:
  CAeroflotLink(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

class CPulkovoLink: public CAircraftLink
{

public:
  CPulkovoLink(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

#endif //_CAircraftLink_H_69991021_ED52_4A8B_81CB_F525E04509D5_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
