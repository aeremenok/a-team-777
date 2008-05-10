/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file User.cpp
 * \brief ���������� ������ CUser
 * \todo ��������������
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "User.h"

CUser::CUser(): m_name("����������� ������������"), m_type(CUser::UNKNOWN)
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

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
