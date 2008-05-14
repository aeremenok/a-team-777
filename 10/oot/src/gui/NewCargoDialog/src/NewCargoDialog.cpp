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
#include "ConstMap.h"
#include "PodPair.h"

CNewCargoDialog::CNewCargoDialog(QWidget *parent, Qt::WindowFlags f): QDialog(parent,f)
{
  m_form.setupUi(this);
  std::set<CCity> list = CDeliveryNetwork::getInstance().getCitys();
  for(std::set<CCity>::iterator it=list.begin();it!=list.end();++it)
  {
    m_comboBoxCity.insert(std::pair<QString,CCity>(tr(it->getName().c_str()),*it));
    m_form.m_from->addItem(tr(it->getName().c_str()));
  }

  connect(m_form.m_from, SIGNAL(activated(int)), SLOT(itemActivated(int)));
  connect(m_form.m_dest, SIGNAL(activated(int)), SLOT(itemChecked()));
  connect(m_form.m_airplane, SIGNAL(clicked()), SLOT(itemChecked()));
  connect(m_form.m_train, SIGNAL(clicked()), SLOT(itemChecked()));
  connect(m_form.m_ship, SIGNAL(clicked()), SLOT(itemChecked()));
  connect(m_form.m_truck, SIGNAL(clicked()), SLOT(itemChecked()));

}

void CNewCargoDialog::itemChecked()
{
  std::set<CEdgeParameters::LinkType> validTypes;

  static CPodPair<QCheckBox*, CEdgeParameters::LinkType> _nn[]=
  {
    {m_form.m_airplane, CEdgeParameters::AIRLINES},
    {m_form.m_ship, CEdgeParameters::SHIP},
    {m_form.m_train, CEdgeParameters::TRAIN},
    {m_form.m_truck, CEdgeParameters::TRUCK},
  };
  static const CConstMap<QCheckBox*, CEdgeParameters::LinkType> n(_nn,_nn+COUNT(_nn));
  
  for(CConstMap<QCheckBox*, CEdgeParameters::LinkType>::const_iterator it = n.begin(); it!=n.end(); ++it)
    if(it->first->isChecked())
      validTypes.insert(it->second);
  
  validTypes.insert(CEdgeParameters::UNKNOWN);
  
  m_form.m_routes->updateModel(m_comboBoxCity.find(m_form.m_from->currentText())->second,
                               m_comboBoxCity.find(m_form.m_dest->currentText())->second,
                                                   validTypes);

}

void CNewCargoDialog::itemActivated(int index)
{
  m_form.m_destLabel->setEnabled(true);
  m_form.m_dest->setEnabled(true);
  m_form.m_dest->clear();
  m_form.m_speed->setEnabled(true);
  m_form.m_routes->setEnabled(true);
  CCity from = m_comboBoxCity.find(m_form.m_from->currentText())->second;
  std::set<CCity> list = CDeliveryNetwork::getInstance().getAccessibleCity(from);
  int i=0;
  for(std::set<CCity>::iterator it=list.begin();it!=list.end();++it)
  {
    m_form.m_dest->insertItem(i,tr(it->getName().c_str()));
    ++i;
  }
  itemChecked();
}



/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
