/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file RoutesView.cpp
 * \brief реализация класса CRoutesView
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "RoutesView.h"
#include "DeliveryNetwork.h"

CRoutesView::CRoutesView(QWidget *parent, Qt::WindowFlags f): QWidget(parent, f)
{
  m_form.setupUi(this);
  m_form.m_view->setModel(&m_model);
}

void CRoutesView::updateModel(const CCity& from, const CCity& to, const std::set<CCostType::Type>& validTypes)
{
  m_model.clear();
  m_model.setColumnCount(3);
  std::list<CDeliveryNetwork::CPath> routes = CDeliveryNetwork::getInstance().getPossibleRoutes(from,to);
  
  for(std::list<CDeliveryNetwork::CPath>::iterator it=routes.begin();it!=routes.end();++it)
  {
    if(it->getSummaryCost().isValidFor(validTypes))
    {
      // добавить в модель виджета
      m_model.appendRow(new CItem(*it));

    }
  }
}


/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
