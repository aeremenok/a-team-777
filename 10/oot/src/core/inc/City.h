/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file City.h
 * \brief заголовок класса CCity
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_
#define _CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_

#include <string>

/*!
 * \brief Город
 */
class CCity
{
  std::string m_name; //!< Имя города
  unsigned long m_people; //!< численность
public:

  CCity(const std::string& name, unsigned long people);

  const std::string& getName() const;

  unsigned long getPeople() const;

  bool operator ==(const CCity& b) const;
  
  bool operator <(const CCity& b) const;
  ~CCity();
};//class CCity

#endif //_CCity_H_1DD094D2_C71A_4562_820F_DCD38A04292E_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
