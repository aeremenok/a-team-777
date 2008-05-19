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

#include <iostream>
#include "RoutesView.h"

#include "ConstMap.h"
#include "PodPair.h"

const char * getFriendlyName(CDefaultLink::LinkType type)
{
  static CPodPair<CDefaultLink::LinkType, const char *> _nn[]=
  {
    {CDefaultLink::AIRLINES, "Авиалинии"},
    {CDefaultLink::SHIP, "Морские линии"},
    {CDefaultLink::TRAIN, "Железнодорожный транспорт"},
    {CDefaultLink::TRUCK, "Автотранспорт"},
    {CDefaultLink::UNKNOWN, ""},
  };
  static const CConstMap<CDefaultLink::LinkType, const char *> n(_nn,_nn+COUNT(_nn));

  return n[type];
}

void CRoutesView::parse(const CDeliveryNetwork::Path path)
{ 
  if(path.vertex_front()==path.vertex_back())
    return;
  std::pair<CCity,CCity> m_pair(path.vertex_front(), path.vertex_back());
  CStupidLink m_link("весь путь", path.getCost(),0);
  
  QList<QStandardItem*> list;
  QStandardItem* a = new QStandardItem(QObject::tr(m_pair.first.getName().c_str()));
  QStandardItem* b = new QStandardItem(QObject::tr(m_pair.second.getName().c_str()));
  QStandardItem* c = new QStandardItem(QObject::tr(getFriendlyName(m_link.getType())));
  QStandardItem* d = new QStandardItem(QString("%1").arg(m_link.getCost()));
  list.push_back(a);
  list.push_back(b);
  list.push_back(c);
  list.push_back(d);
  m_path[a]=path;
  m_path[b]=path;
  m_path[c]=path;
  m_path[d]=path;

  m_model.appendRow(list);

  CDeliveryNetwork::Path::link_const_iterator link = path.link_begin(); link++;
  CDeliveryNetwork::Path::vertex_const_iterator it = path.vertex_begin();
  for(;it!=path.vertex_end();++it,++link)
  {
    CDeliveryNetwork::Path::vertex_const_iterator next = it;  next++;
    if(next==path.vertex_end())
      break;

    QList<QStandardItem*> list;
    QStandardItem* e = new QStandardItem(QObject::tr(it->getName().c_str()));
    QStandardItem* f = new QStandardItem(QObject::tr(next->getName().c_str()));
    QStandardItem* g = new QStandardItem(QObject::tr(getFriendlyName((*link)->getType())));
    QStandardItem* h = new QStandardItem(QString("%1").arg((*link)->getCost()));
    list.push_back(e);
    list.push_back(f);
    list.push_back(g);
    list.push_back(h);
    m_path[e]=path;
    m_path[f]=path;
    m_path[g]=path;
    m_path[h]=path;

    a->appendRow(list);
  }
}


CRoutesView::CRoutesView(QWidget *parent, Qt::WindowFlags f): QWidget(parent, f)
{
  m_form.setupUi(this);
  m_form.m_view->setModel(&m_model);
}

bool CRoutesView::isValid(const CDeliveryNetwork::Path& path, const std::set<CDefaultLink::LinkType>& validTypes)
{
  for(CDeliveryNetwork::Path::link_const_iterator it=path.link_begin(); it!=path.link_end(); ++it)
  {
    if(validTypes.count((*it)->getType())==0)
      return false;
  }
  return true;  
}

void CRoutesView::updateModel(const CCity& from, const CCity& to, const std::set<CDefaultLink::LinkType>& validTypes)
{
  m_model.clear();
  m_model.setColumnCount(4);
  QStandardItem *item;
  item = new QStandardItem(QObject::tr("Пункт отправления"));
  m_model.setHorizontalHeaderItem(0, item);
  
  item= new QStandardItem(QObject::tr("Пункт назначения"));
  m_model.setHorizontalHeaderItem(1,item);

  item = new QStandardItem(QObject::tr("Вид транспорта"));
  m_model.setHorizontalHeaderItem(2,item);

  item =  new QStandardItem(QObject::tr("Стоимость"));
  m_model.setHorizontalHeaderItem(3,item);

  std::list<CDeliveryNetwork::Path> routes = CDeliveryNetwork::getInstance().getPossibleRoutes(from,to);
  
  for(std::list<CDeliveryNetwork::Path>::iterator it=routes.begin();it!=routes.end();++it)
  {
    if(isValid(*it,validTypes))
    {
      // добавить в модель виджета
      parse(*it);
    }
  }
}

void CRoutesView::updateModel(const CUser& user)
{
  m_model.clear();
  m_model.setColumnCount(4);
  m_model.setHorizontalHeaderItem(0, new QStandardItem(QObject::tr("Пункт отправления")));
  m_model.setHorizontalHeaderItem(1, new QStandardItem(QObject::tr("Пункт назначения")));
  m_model.setHorizontalHeaderItem(2, new QStandardItem(QObject::tr("Вид транспорта")));
  m_model.setHorizontalHeaderItem(3, new QStandardItem(QObject::tr("Стоимость")));
  for(CUser::path_const_iterator it=user.begin(); it!=user.end(); ++it)
  {
    parse(*it);   
  }
}

CPath<CCity, CDefaultLink> CRoutesView::currentPath() const
{
  return m_path.find(m_model.itemFromIndex(m_form.m_view->currentIndex()))->second;
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
