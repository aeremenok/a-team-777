/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file MainWindow.h
 * \brief заголовок класса CMainWindow
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CMainWindow_H_9C2577A5_7BA4_4083_B4CE_EDC8691B163D_INCLUDED_
#define _CMainWindow_H_9C2577A5_7BA4_4083_B4CE_EDC8691B163D_INCLUDED_

#include <QtGui/QMainWindow>
#include "ui_MainWindow.h"

/*!
 * \brief
 */
class CMainWindow: public QMainWindow
{
  
  Q_OBJECT
  
  CMainWindow(const CMainWindow& obj);
  CMainWindow& operator=(const CMainWindow& obj);
 
  Ui::CMainWindow m_form;
protected:
  virtual void showEvent(QShowEvent *event);

protected slots:
  void addCargo();
public:
  CMainWindow(QWidget * parent = 0, Qt::WindowFlags f = 0);
  
  ~CMainWindow()
  {
  }
};//class CMainWindow

#endif //_CMainWindow_H_9C2577A5_7BA4_4083_B4CE_EDC8691B163D_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
