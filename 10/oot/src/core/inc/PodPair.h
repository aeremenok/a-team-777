/* $Id: PodPair.h,v 1.2 2005/09/16 06:36:01 dumb Exp $ */
/*!
 * \file PodPair.h
 * \brief заголовок класса CPodPair
 * ------------------------------------------------------------------------ */


#ifndef _CPodPair_H_63F6D912_FEDC_4FCE_B9E6_6325F672CE81_INCLUDED_
#define _CPodPair_H_63F6D912_FEDC_4FCE_B9E6_6325F672CE81_INCLUDED_

#include <utility>

/*!
 * \brief пара объектов POD для статической инициализации
 */
template<typename T1,typename T2>
struct CPodPair
{
  T1 first;
  T2 second;
  
  typedef T1 first_type;
  typedef T2 second_type;
  
  template<typename A,typename B>
  operator std::pair<A,B>() const
  {
    return std::pair<A,B>(first,second);
  }
};

#endif //_CPodPair_H_63F6D912_FEDC_4FCE_B9E6_6325F672CE81_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/common/inc/PodPair.h,v $ ]=== */
