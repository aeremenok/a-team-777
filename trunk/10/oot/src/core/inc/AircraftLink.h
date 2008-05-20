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


#include "DefaultLink.h"

/*!
 * \brief Воздушная трасса
 */
class CAircraftLink: public CDefaultLink
{
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CDefaultLink);
  }

public:

  CAircraftLink();

  CAircraftLink(unsigned long cost, unsigned long time);
  
  virtual std::string getDescription() const;

  virtual ~CAircraftLink()
  {
  }
};//class CAircraftLink


class CS7Link: public CAircraftLink
{

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CAircraftLink);
  }

public:
  CS7Link();

  CS7Link(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

class CAeroflotLink: public CAircraftLink
{

public:
  CAeroflotLink();

  CAeroflotLink(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

class CPulkovoLink: public CAircraftLink
{
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CAircraftLink);
  }
public:
  CPulkovoLink();

  CPulkovoLink(unsigned long cost, unsigned long time);

  std::string getDescription() const;
};

#endif //_CAircraftLink_H_69991021_ED52_4A8B_81CB_F525E04509D5_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
