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
#include "NewCityDialog.h"
#include "NewRouteDialog.h"

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

  connect(m_form.add_city,SIGNAL(triggered()),SLOT(addCity()));
  connect(m_form.add_route,SIGNAL(triggered()),SLOT(addRoute()));
  connect(m_form.freeze,SIGNAL(toggled(bool)),w,SLOT(setFreeze(bool)));
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
    initGui(CUserCookie::getInstance().getUser());
  }

}

void CMainWindow::initGui(const CUser& user)
{
  m_form.add_city->setVisible(user.getType()==CUser::MANAGER);
  m_form.add_route->setVisible(user.getType()==CUser::MANAGER);
  m_form.m_newCargo->setVisible(user.getType()==CUser::CLIENT);
}

void CMainWindow::addCargo()
{
  CNewCargoDialog dialog(this);
  dialog.exec();
  m_dock->getWidget().updateModel(CUserCookie::getInstance().getUser());
}

void CMainWindow::addCity()
{
  CNewCityDialog dialog(this);
  dialog.exec();
  dynamic_cast<GraphWidget*>(centralWidget())->setGraph(CDeliveryNetwork::getInstance().getContainer());
}

void CMainWindow::addRoute()
{
  CNewRouteDialog dialog(this);
  dialog.exec();
  dynamic_cast<GraphWidget*>(centralWidget())->setGraph(CDeliveryNetwork::getInstance().getContainer());
}

/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
