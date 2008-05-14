/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file RoutesView.h
 * \brief ��������� ������ CRoutesView
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CRoutesView_H_2BC4B721_E1C3_4FEB_9D91_88D7EAD9CEF8_INCLUDED_
#define _CRoutesView_H_2BC4B721_E1C3_4FEB_9D91_88D7EAD9CEF8_INCLUDED_

#include <set>
#include <QtGui/QWidget>
#include <QtGui/QStandardItemModel>
#include "ui_RoutesView.h"
#include "City.h"
#include "EdgeParameters.h"
#include "TreeModel.h"
/*!
 * \brief ����������� ���� ���������
 */
class CRoutesView: public QWidget
{
  Q_OBJECT

  CRoutesView(const CRoutesView& obj);
  CRoutesView& operator=(const CRoutesView& obj);
  
  Ui::CRoutesView m_form;
  
  CTreeModel m_model;

public:
  CRoutesView(QWidget * parent = 0, Qt::WindowFlags f = 0);

  void updateModel(const CCity& from, const CCity& to, const std::set<CEdgeParameters::LinkType>& validTypes);
  
};//class CRoutesView

#endif //_CRoutesView_H_2BC4B721_E1C3_4FEB_9D91_88D7EAD9CEF8_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
