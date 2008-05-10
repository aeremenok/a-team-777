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

#include "LoginDialog.h"


CLoginDialog::CLoginDialog(QWidget *parent, Qt::WindowFlags f): QDialog(parent, f)
{
  m_form.setupUi(this);
}
/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
