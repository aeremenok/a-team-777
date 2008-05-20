/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file RoutesView.h
 * \brief заголовок класса CRoutesView
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
#include "User.h"
#include "DeliveryNetwork.h"

/*!
 * \brief Отображение иска маршрутов
 */
class CRoutesView: public QWidget
{
  Q_OBJECT

  Ui::CRoutesView m_form;
  
  QStandardItemModel m_model;

  std::map<QStandardItem *, CPath<CCity, CDefaultLink> > m_path;

  bool isValid(const CDeliveryNetwork::Path& path, const std::set<CDefaultLink::LinkType>& validTypes);
  
  void parse(CDeliveryNetwork::Path path);

public:
  CRoutesView(QWidget * parent = 0, Qt::WindowFlags f = 0);

  void updateModel(const CCity& from, const CCity& to, const std::set<CDefaultLink::LinkType>& validTypes);
  
  void updateModel(const CUser& user);

  CPath<CCity, CDefaultLink> currentPath() const;
};//class CRoutesView

#endif //_CRoutesView_H_2BC4B721_E1C3_4FEB_9D91_88D7EAD9CEF8_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
