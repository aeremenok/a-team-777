/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file EdgeParameters.h
 * \brief заголовок класса CEdgeParameters
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_
#define _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

#include <vector>

// Serialization
#include <boost/serialization/nvp.hpp>
#include <boost/serialization/utility.hpp>
#include <boost/serialization/list.hpp>
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
public:
  CStupidLink(const std::string &s, unsigned long cost, unsigned long time):CDefaultLink(CDefaultLink::UNKNOWN, cost, time), m_name(s)
  {

  }

  std::string getDescription() const
  {
    return m_name;
  }
};

/*!
 * \brief набор стоимостей доставки
 */
class CEdgeParameters
{

  std::vector<CDefaultLink *> m_links; // набор существующих ребер

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(m_links);
  }
public:
  CEdgeParameters();

  bool isAvail(CDefaultLink::LinkType type) const;

  void addLink(CDefaultLink*);

  size_t linkCount() const;

  const CDefaultLink* getLink(CDefaultLink::LinkType type) const;

  const CDefaultLink* getLink(size_t index) const;
  CDefaultLink* getLink(size_t index);
  
  ~CEdgeParameters();
};//class CEdgeParameters

#endif //_CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
