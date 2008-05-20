/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DockWidget.h
 * \brief заголовок класса CDockWidget
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CDockWidget_H_F42FF8FC_77CD_40B7_8E03_5C02EC10C31F_INCLUDED_
#define _CDockWidget_H_F42FF8FC_77CD_40B7_8E03_5C02EC10C31F_INCLUDED_

#include <QtGui/QDockWidget>
/*!
 * \brief
 */
template<typename T>
class CDockWidget: public QDockWidget
{
  T m_form; 
public:
  CDockWidget( QWidget * parent = 0, Qt::WindowFlags flags = 0 ):QDockWidget(parent,flags),m_form(this)
  {
    setWidget(&m_form);
    setFloating(false);
    setFeatures(QDockWidget::DockWidgetMovable | QDockWidget::DockWidgetFloatable);
  }
  
  const T& getWidget() const
  {
    return m_form;
  }
  
  T& getWidget()
  {
    return m_form;
  }

  ~CDockWidget()
  {
  }
};//class CDockWidget

#endif //_CDockWidget_H_F42FF8FC_77CD_40B7_8E03_5C02EC10C31F_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
