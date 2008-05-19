/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file UserCookie.h
 * \brief заголовок класса CUserCookie
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CUserCookie_H_9CEA896E_087C_44D4_ACC3_6C5DA569964A_INCLUDED_
#define _CUserCookie_H_9CEA896E_087C_44D4_ACC3_6C5DA569964A_INCLUDED_

#include "User.h"

/*!
 * \brief описание настроек пользователя
 */
class CUserCookie
{
  CUserCookie(const CUserCookie& obj);
  CUserCookie& operator=(const CUserCookie& obj);
 
  std::list<CUser> m_user;
  CUser::Type m_current;

  CUserCookie();
  
  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar & BOOST_SERIALIZATION_NVP(m_user);
    ar & BOOST_SERIALIZATION_NVP(m_current);
  }
  
public:
  
  ~CUserCookie();
 
  bool findUser(const std::string& name, const std::string& password);

  const CUser& getUser() const;
  CUser& getUser();

  static CUserCookie& getInstance();
};//class CUserCookie

#endif //_CUserCookie_H_9CEA896E_087C_44D4_ACC3_6C5DA569964A_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
