/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DefaultLink.h
 * \brief заголовок класса CDefaultLink
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CDefaultLink_H_FCD576C9_8F01_4362_9AF7_E979C4E9B248_INCLUDED_
#define _CDefaultLink_H_FCD576C9_8F01_4362_9AF7_E979C4E9B248_INCLUDED_

#include <vector>

// Serialization
#include <boost/serialization/nvp.hpp>
#include <boost/serialization/utility.hpp>
#include <boost/serialization/list.hpp>
#include <boost/serialization/vector.hpp>
#include <boost/serialization/string.hpp>
#include <boost/serialization/version.hpp>
#include <boost/serialization/split_member.hpp>
#include <boost/archive/tmpdir.hpp>
#include <boost/archive/xml_iarchive.hpp>
#include <boost/archive/xml_oarchive.hpp>
#include <boost/serialization/export.hpp>	// must be in the end of serializatrion headers list

class CDefaultLink
{
public:
  enum LinkType
  {
    AIRLINES=0, //!< Воздушное сообщение
    TRUCK,    //!< Грузовое
    TRAIN,    //!< Поезда
    SHIP,     //!< Кораблики
    UNKNOWN   //!< Все остальное
  };
private:
  LinkType m_type; // тип соединения
  unsigned long m_cost; // стоимость 
  unsigned long m_time; // время

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(m_type);
    ar & BOOST_SERIALIZATION_NVP(m_cost);
    ar & BOOST_SERIALIZATION_NVP(m_time);
  }

protected:

  CDefaultLink(LinkType type, unsigned long cost, unsigned long time):m_type(type), m_cost(cost), m_time(time)
  {}

  
public:
  CDefaultLink():m_type(UNKNOWN), m_cost(0), m_time(0)
  {}

  virtual LinkType getType() const
  {
    return m_type;
  }

  virtual unsigned long getCost() const
  {
    return m_cost;
  }

  virtual unsigned long getTime() const
  {
    return m_time;
  }

  virtual std::string getDescription() const
  {
    return "неизвестный перевозчик";
  }

  bool operator<(const CDefaultLink& link) const
  {
    return m_type<link.m_type;
  }

  virtual ~CDefaultLink()
  {

  }
};

class CStupidLink: public CDefaultLink
{
  std::string m_name;

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_BASE_OBJECT_NVP(CDefaultLink);
    ar & BOOST_SERIALIZATION_NVP(m_name);
  }

public:
  CStupidLink()
  {
  }
  CStupidLink(const std::string &s, unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::UNKNOWN, cost, time), m_name(s)
  {

  }

  std::string getDescription() const
  {
    return m_name;
  }
};

#endif //_CDefaultLink_H_FCD576C9_8F01_4362_9AF7_E979C4E9B248_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
