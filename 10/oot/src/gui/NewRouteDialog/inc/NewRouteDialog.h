/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewRouteDialog.h
 * \brief заголовок класса CNewRouteDialog
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CNewRouteDialog_H_DEE5EA06_D229_479D_BCAE_829D6E0CFFFF_INCLUDED_
#define _CNewRouteDialog_H_DEE5EA06_D229_479D_BCAE_829D6E0CFFFF_INCLUDED_

#include <QtGui/QDialog>
#include "ui_NewRouteDialog.h"

/*!
 * \brief
 */

class CNewRouteDialog: public QDialog
{
  Q_OBJECT

  Ui::CNewRouteDialog m_form;

  void init();

protected slots:
  void linkTypeSelected(const QString&);
  void accept();

public:
  CNewRouteDialog(QWidget *parent=0, Qt::WindowFlags f=0);

  
};//class CNewRouteDialog

#endif //_CNewRouteDialog_H_DEE5EA06_D229_479D_BCAE_829D6E0CFFFF_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
