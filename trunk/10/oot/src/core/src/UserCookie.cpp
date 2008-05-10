/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file UserCookie.cpp
 * \brief ���������� ������ CUserCookie
 * \todo ��������������
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "UserCookie.h"

CUserCookie& CUserCookie::getInstance()
{
  static CUserCookie cookie;
  return cookie;
}

const CUser& CUserCookie::getUser() const
{
  return m_user;
}

bool CUserCookie::findUser(const std::string& name, const std::string &password)
{
  // ����� �� ���� ���� ����-�� �����, � ������ ������������ � ����, ��������� ��� ���.
  // �� ��� ��� � ������.... (:
  if(name=="client"&&password=="client")
  {
    m_user=CUser("������",CUser::CLIENT);
    return true;
  }

  if(name=="seller"&&password=="seller")
  {
    m_user=CUser("��������",CUser::SELLER);
    return true;
  }

  if(name=="manager"&&password=="manager")
  {
    m_user=CUser("��������-�����������",CUser::MANAGER);
    return true;
  }
  
  m_user=CUser();
  return false;
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
