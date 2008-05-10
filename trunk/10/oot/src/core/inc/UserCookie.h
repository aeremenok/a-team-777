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
 
  CUser m_user;

  CUserCookie()
  {}
  
public:
  
  ~CUserCookie()
  {
  }
 
  void findUser(const std::string& name, const std::string& password);

  const CUser& getUser() const;

  static CUserCookie& getInstance();
};//class CUserCookie

#endif //_CUserCookie_H_9CEA896E_087C_44D4_ACC3_6C5DA569964A_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
