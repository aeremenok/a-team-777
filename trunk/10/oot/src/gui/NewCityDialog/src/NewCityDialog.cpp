/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file NewCityDialog.cpp
 * \brief реализация класса CNewCityDialog
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include <iostream>
#include "NewCityDialog.h"
#include "DeliveryNetwork.h"

CNewCityDialog::CNewCityDialog(QWidget *parent, Qt::WindowFlags f): QDialog(parent,f)
{
  m_form.setupUi(this);
}

void CNewCityDialog::accept()
{
  CDeliveryNetwork::getInstance().addCity(CCity(m_form.m_name->text().toLocal8Bit().data(), m_form.m_people->value()));
  QDialog::accept();
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
