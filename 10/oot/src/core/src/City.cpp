/* $Id: template.cpp,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file City.cpp
 * \brief реализация класса CCity
 * \todo комментировать
 *
 * File description
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */

#include "City.h"
#include <QtCore/QString>
#include <QtCore/QObject>
CCity::CCity(const std::string& name, unsigned long people): m_name(name), m_people(people)
{

}

const std::string& CCity::getName() const
{
  return m_name;
}

unsigned long CCity::getPeople() const
{
  return m_people;
}

CCity::~CCity()
{

}

bool CCity::operator ==(const CCity& b) const
{
  return getName()==b.getName();
}

bool CCity::operator <(const CCity& b) const
{
  if(getName()!=b.getName())
    return QString(QObject::tr(getName().c_str()))<QString(QObject::tr(b.getName().c_str()));

  return getPeople()<b.getPeople();
}


/* ===[ End of file $Source: /cvs/decisions/templates/template.cpp,v $ ]=== */
