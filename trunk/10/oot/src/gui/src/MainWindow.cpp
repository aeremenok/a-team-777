/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file MainWindow.cpp
 * \brief ���������� ������ CMainWindow
 * \todo ��������������
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */
#include <iostream>
#include "MainWindow.h"
#include "UserCookie.h"
#include "LoginDialog.h"
#include "NewCargoDialog.h"
#include "GraphWidget.h"
#include "UserCookie.h"

CMainWindow::CMainWindow(QWidget * parent, Qt::WindowFlags f): QMainWindow(parent,f)
{
  m_form.setupUi(this);
  GraphWidget * w = new GraphWidget(this);  
  w->setGraph(CDeliveryNetwork::getInstance().getContainer());
  setCentralWidget(w);
  connect(m_form.m_newCargo,SIGNAL(triggered()),SLOT(addCargo()));
  m_dock = new CDockWidget<CRoutesView>(this);
  addDockWidget(Qt::RightDockWidgetArea, m_dock);
  connect(&m_dock->getWidget(), SIGNAL(selectPath(CPath<CCity,CDefaultLink>)),w , SLOT(selectPath(CPath<CCity,CDefaultLink>)));

}


void CMainWindow::showEvent(QShowEvent *event)
{
  const CUser& user = CUserCookie::getInstance().getUser();
  if(user.getType()==CUser::UNKNOWN)
  {
    CLoginDialog dialog(this);
    if(dialog.exec()==QDialog::Rejected)
      exit(0);
    m_dock->getWidget().updateModel(CUserCookie::getInstance().getUser());
  }
}


void CMainWindow::addCargo()
{
  CNewCargoDialog dialog(this);
  dialog.exec();
  m_dock->getWidget().updateModel(CUserCookie::getInstance().getUser());
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */