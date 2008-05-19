/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file User.cpp
 * \brief реализация класса CUser
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "User.h"

CUser::CUser(): m_name("Неизвестный пользователь"), m_type(CUser::UNKNOWN)
{

}

CUser::CUser(const std::string& name, CUser::Type type): m_name(name), m_type(type)
{

}

CUser::Type CUser::getType() const
{
  return m_type;
}


const std::string& CUser::getName() const
{
  return m_name;
}

CUser::path_iterator CUser::begin()
{
  return m_cargo.begin();
}

CUser::path_const_iterator CUser::begin() const
{
  return m_cargo.begin();
}

CUser::path_iterator CUser::end()
{
  return m_cargo.end();
}
CUser::path_const_iterator CUser::end() const
{
  return m_cargo.end();
}

void CUser::addCargo(const CPath<CCity, CDefaultLink>& path)
{
  m_cargo.push_back(path);
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
