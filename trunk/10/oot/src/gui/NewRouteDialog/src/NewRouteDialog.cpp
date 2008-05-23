/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewRouteDialog.cpp
 * \brief реализация класса CNewRouteDialog
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "NewRouteDialog.h"
#include "DeliveryNetwork.h"

CNewRouteDialog::CNewRouteDialog(QWidget *parent, Qt::WindowFlags f):QDialog(parent,f)
{
  m_form.setupUi(this);
  init();
  connect(m_form.m_linkType, SIGNAL(activated(const QString&)), SLOT(linkTypeSelected(const QString&)));
}

void CNewRouteDialog::init()
{
  std::set<CCity> city = CDeliveryNetwork::getInstance().getCitys();
  for(std::set<CCity>::iterator it=city.begin(); it!=city.end(); ++it)
  {
    m_form.m_source->addItem(tr(it->getName().c_str()));
    m_form.m_dest->addItem(tr(it->getName().c_str()));
  }

}

void CNewRouteDialog::linkTypeSelected(const QString& str)
{
  m_form.m_link->clear();
  m_form.m_link->setEnabled(true);
  m_form.m_linkLabel->setEnabled(true);
  if(str==tr("Авиатранспорт"))
  {
    m_form.m_link->addItem(tr("Пулково"));
    m_form.m_link->addItem(tr("Аэрофлот"));
    m_form.m_link->addItem(tr("S7"));
  }
  if(str==tr("Железнодорожный транспорт"))
  {
    m_form.m_link->addItem(tr("РЖД"));
    m_form.m_link->addItem(tr("ASD"));
  }
  if(str==tr("Дорожный транспорт"))
  {
    m_form.m_link->addItem(tr("Cargo"));
    m_form.m_link->addItem(tr("Вася и сыновья"));
  }
  if(str==tr("Морской  транспорт"))
  {
    m_form.m_link->addItem(tr("Limco"));
    m_form.m_link->addItem(tr("Альфа"));
  }
}

void CNewRouteDialog::accept()
{
  CDefaultLink * link;

  if(m_form.m_link->currentText()==tr("Пулково"))
    link= new CPulkovoLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("Аэрофлот"))
    link= new CAeroflotLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("S7"))
    link= new CS7Link(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("Limco"))
    link= new CLimcoLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("Альфа"))
    link= new CAlfaLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("РЖД"))
    link= new CRZDLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("ASD"))
    link= new CASDLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("Cargo"))
    link= new CCargoLink(m_form.m_cost->value(), m_form.m_cost->value());
  if(m_form.m_link->currentText()==tr("Вася и сыновья"))
    link= new CVasjaLink(m_form.m_cost->value(), m_form.m_cost->value());

  CDeliveryNetwork &net = CDeliveryNetwork::getInstance();

  net.addRoute(net.getCity(m_form.m_source->currentText().toLocal8Bit().data()),
               net.getCity(m_form.m_dest->currentText().toLocal8Bit().data()),
               link);

  QDialog::accept();
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
