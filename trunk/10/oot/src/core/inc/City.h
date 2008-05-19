/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file City.h
 * \brief заголовок класса CCity
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_
#define _CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_

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

/*!
 * \brief Город
 */
class CCity
{
  std::string m_name; //!< Имя города
  unsigned long m_people; //!< численность

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(m_name);
    ar & BOOST_SERIALIZATION_NVP(m_people);
  }
  
public:

  CCity(){}

  CCity(const std::string& name, unsigned long people);

  const std::string& getName() const;

  unsigned long getPeople() const;

  bool operator ==(const CCity& b) const;
  
  bool operator <(const CCity& b) const;
  ~CCity();
};//class CCity

#endif //_CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
