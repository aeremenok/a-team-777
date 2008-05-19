/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file UserCookie.cpp
 * \brief реализация класса CUserCookie
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <unistd.h>
#include <iostream>
#include <fstream>
#include "UserCookie.h"


CUserCookie& CUserCookie::getInstance()
{
  static CUserCookie cookie;
  return cookie;
}

CUserCookie::CUserCookie()
{

  try	
  {
    std::ifstream	ifs("test.xml");
    boost::archive::xml_iarchive iarchive(ifs);
    //iarchive >> boost::serialization::make_nvp("UserCookie", *this);
    ifs.close();
  }
  catch(...)
  { 
  }
}

CUserCookie::~CUserCookie()
{
  std::ofstream	ofs("test.xml");
  boost::archive::xml_oarchive oarchive(ofs);
  try	
  {
    oarchive << boost::serialization::make_nvp("UserCookie", *this);
    ofs.flush();
  }
  catch(...)
  {
  }
  ofs.close();
}
  
static CUser unknownUser("unknown", CUser::UNKNOWN);

const CUser& CUserCookie::getUser() const
{
  for(std::list<CUser>::const_iterator i=m_user.begin();i!=m_user.end();++i)
    if(i->getType() == m_current)
      return *i;
  return unknownUser;
}

CUser& CUserCookie::getUser()
{
  for(std::list<CUser>::iterator i=m_user.begin();i!=m_user.end();++i)
    if(i->getType() == m_current)
      return *i;
  return unknownUser;
}

bool CUserCookie::findUser(const std::string& name, const std::string &password)
{
  // здесь по идее надо куда-то лезть, и искать пользователя в базе, определив его тип.
  // но это все в теории.... (:
  if(name=="client"&&password=="client")
  {
    m_current = CUser::CLIENT;
    for(std::list<CUser>::iterator i=m_user.begin();i!=m_user.end();++i)
      if(i->getType() == m_current)
        return true;
    m_user.push_back(CUser("client",CUser::CLIENT));
    return true;
  }

  if(name=="seller"&&password=="seller")
  {
    m_current = CUser::SELLER;
    for(std::list<CUser>::iterator i=m_user.begin();i!=m_user.end();++i)
      if(i->getType() == m_current)
        return true;
    m_user.push_back(CUser("seller",CUser::SELLER));
    return true;
  }

  if(name=="manager"&&password=="manager")
  {
    m_current = CUser::MANAGER;
    for(std::list<CUser>::iterator i=m_user.begin();i!=m_user.end();++i)
      if(i->getType() == m_current)
        return true;
    m_user.push_back(CUser("manager",CUser::MANAGER));
    return true;
  }
  
  m_current = CUser::UNKNOWN;
  return false;
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
