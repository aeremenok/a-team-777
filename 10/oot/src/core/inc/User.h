/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file User.h
 * \brief заголовок класса CUser
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CUser_H_C8E128F3_4C67_41C7_A74F_6BBCEC505BA6_INCLUDED_
#define _CUser_H_C8E128F3_4C67_41C7_A74F_6BBCEC505BA6_INCLUDED_

#include <list>
#include <string>
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

#include "Graph.h"
#include "City.h"
#include "EdgeParameters.h"

/*!
 * \brief Описание пользователя
 */
class CUser
{

public:
  
  typedef std::list<CPath<CCity, CDefaultLink> > Path;
  typedef Path::iterator path_iterator;
  typedef Path::const_iterator path_const_iterator;

  enum Type
  {
    CLIENT,
    SELLER,
    MANAGER,
    BOSS,
    DRIVER,
    UNKNOWN
  };

  CUser();

  CUser(const std::string& name, Type type);
  
  ~CUser()
  {
  }

  Type getType() const;
  void setType(Type);

  const std::string& getName() const;
  void setName(const std::string& name);

  path_iterator begin();
  path_const_iterator begin() const;

  path_iterator end();
  path_const_iterator end() const;

  void addCargo(const CPath<CCity, CDefaultLink> &path);

private:
  std::string m_name;
  Type  m_type;
  
  std::list<CPath<CCity, CDefaultLink> > m_cargo; //!< список заказов пользователя

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(m_name);
    ar & BOOST_SERIALIZATION_NVP(m_type);
    ar & BOOST_SERIALIZATION_NVP(m_cargo);
  }
};//class CUser

#endif //_CUser_H_C8E128F3_4C67_41C7_A74F_6BBCEC505BA6_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
