/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file LoginDialog.h
 * \brief заголовок класса CLoginDialog
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CLoginDialog_H_522DFE86_849B_4470_9611_78F09BA05AF0_INCLUDED_
#define _CLoginDialog_H_522DFE86_849B_4470_9611_78F09BA05AF0_INCLUDED_

#include <QtGui/QDialog>
#include "ui_LoginDialog.h"

/*!
 * \brief
 */
class CLoginDialog: public QDialog
{
  Q_OBJECT

  CLoginDialog(const CLoginDialog& obj);
  CLoginDialog& operator=(const CLoginDialog& obj);

  Ui::CLoginDialog m_form;

  virtual void accept();
public:
  CLoginDialog(QWidget * parent = 0, Qt::WindowFlags f = 0);
  
  ~CLoginDialog()
  {
  }
};//class CLoginDialog

#endif //_CLoginDialog_H_522DFE86_849B_4470_9611_78F09BA05AF0_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
