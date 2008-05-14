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
#include "TreeItem.h"

CRoutesView::CRoutesView(QWidget *parent, Qt::WindowFlags f): QWidget(parent, f)
{
  m_form.setupUi(this);
  m_form.m_view->setModel(&m_model);
}

bool isValid(const CDeliveryNetwork::Path& path, const std::set<CEdgeParameters::LinkType>& validTypes)
{
  for(CDeliveryNetwork::Path::link_const_iterator it=path.link_begin(); it!=path.link_end(); ++it)
    if(validTypes.count(it->getType())==0)
      return false;
  return true;  
}

void CRoutesView::updateModel(const CCity& from, const CCity& to, const std::set<CEdgeParameters::LinkType>& validTypes)
{
  std::list<CDeliveryNetwork::Path> routes = CDeliveryNetwork::getInstance().getPossibleRoutes(from,to);
  
  for(std::list<CDeliveryNetwork::Path>::iterator it=routes.begin();it!=routes.end();++it)
  {
    if(isValid(*it,validTypes))
    {
      // добавить в модель виджета
       m_model.addRow( new CTreeItem(*it));
    }
  }
}


/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
