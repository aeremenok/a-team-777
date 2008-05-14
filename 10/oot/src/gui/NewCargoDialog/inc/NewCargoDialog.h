/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewCargoDialog.h
 * \brief заголовок класса CNewCargoDialog
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CNewCargoDialog_H_A755C317_18D7_4C32_BAF8_7EEEBA09BF51_INCLUDED_
#define _CNewCargoDialog_H_A755C317_18D7_4C32_BAF8_7EEEBA09BF51_INCLUDED_

#include <map>
#include <QtGui/QDialog>
#include "ui_NewCargoDialog.h"
#include "DeliveryNetwork.h"

/*!
 * \brief
 */
class CNewCargoDialog: public QDialog
{
  Q_OBJECT

  CNewCargoDialog(const CNewCargoDialog& obj);
  CNewCargoDialog& operator=(const CNewCargoDialog& obj);
  
  Ui::CNewCargoDialog m_form;

  std::map<int, CCity> m_comboBoxCity;

protected slots:
  void itemActivated(int);

  void itemChecked();

public:
  CNewCargoDialog(QWidget * parent = 0, Qt::WindowFlags f = 0);
  

};//class CNewCargoDialog

#endif //_CNewCargoDialog_H_A755C317_18D7_4C32_BAF8_7EEEBA09BF51_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
