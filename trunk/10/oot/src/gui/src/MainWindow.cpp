/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file MainWindow.cpp
 * \brief реализация класса CMainWindow
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */
#include <iostream>
#include "MainWindow.h"
#include "UserCookie.h"
#include "LoginDialog.h"

CMainWindow::CMainWindow(QWidget * parent, Qt::WindowFlags f): QMainWindow(parent,f)
{
  m_form.setupUi(this);
}


void CMainWindow::showEvent(QShowEvent *event)
{
  const CUser& user = CUserCookie::getInstance().getUser();
  if(user.getType()==CUser::UNKNOWN)
  {
    CLoginDialog dialog(this);
    if(dialog.exec()==QDialog::Rejected)
      exit(0);
  }
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
