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

#include <string>

/*!
 * \brief Описание пользователя
 */
class CUser
{

public:

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

private:
  std::string m_name;
  Type  m_type;
};//class CUser

#endif //_CUser_H_C8E128F3_4C67_41C7_A74F_6BBCEC505BA6_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
