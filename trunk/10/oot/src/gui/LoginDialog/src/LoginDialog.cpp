/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file LoginDialog.cpp
 * \brief реализация класса CLoginDialog
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <iostream>
#include "LoginDialog.h"
#include "UserCookie.h"

CLoginDialog::CLoginDialog(QWidget *parent, Qt::WindowFlags f): QDialog(parent, f)
{
  m_form.setupUi(this);
}

void CLoginDialog::accept()
{
  if(CUserCookie::getInstance().findUser(m_form.m_name->text().toLocal8Bit().data(),m_form.m_pass->text().toLocal8Bit().data()))
    QDialog::accept();
  else
    reject();
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
