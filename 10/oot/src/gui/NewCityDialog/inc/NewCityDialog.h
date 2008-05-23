/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewCityDialog.h
 * \brief заголовок класса CNewCityDialog
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CNewCityDialog_H_788D762F_767E_4608_8785_1E0D6962969F_INCLUDED_
#define _CNewCityDialog_H_788D762F_767E_4608_8785_1E0D6962969F_INCLUDED_

#include <QtGui/QDialog>
#include "ui_NewCityDialog.h"
/*!
 * \brief
 */
class CNewCityDialog: public QDialog
{
  Q_OBJECT

  Ui::CNewCityDialog m_form;
protected slots:
  
  void accept();

public:
  CNewCityDialog(QWidget * parent = 0, Qt::WindowFlags f = 0);
  
};//class CNewCityDialog

#endif //_CNewCityDialog_H_788D762F_767E_4608_8785_1E0D6962969F_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
