/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewCargoDialog.cpp
 * \brief реализация класса CNewCargoDialog
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <iostream>
#include "NewCargoDialog.h"

CNewCargoDialog::CNewCargoDialog(QWidget *parent, Qt::WindowFlags f): QDialog(parent,f)
{
  m_form.setupUi(this);
  std::set<CCity> list = CDeliveryNetwork::getInstance().getCitys();
  int i=0;
  for(std::set<CCity>::iterator it=list.begin();it!=list.end();++it)
  {
    m_comboBoxCity.insert(std::pair<int,CCity>(i,*it));
    m_form.m_from->insertItem(i,tr(it->getName().c_str()));
    ++i;
  }

  connect(m_form.m_from, SIGNAL(activated(int)), SLOT(itemActivated(int)));
}

void CNewCargoDialog::itemActivated(int index)
{
  m_form.m_destLabel->setEnabled(true);
  m_form.m_dest->setEnabled(true);
  m_form.m_dest->clear();
  m_form.m_speed->setEnabled(true);
  CCity from = m_comboBoxCity.find(index)->second;
  std::set<CCity> list = CDeliveryNetwork::getInstance().getAccessibleCity(from);
  int i=0;
  for(std::set<CCity>::iterator it=list.begin();it!=list.end();++it)
  {
    m_form.m_dest->insertItem(i,tr(it->getName().c_str()));
    ++i;
  }
}



/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
