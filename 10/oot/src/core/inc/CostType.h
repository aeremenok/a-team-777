/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file CostType.h
 * \brief заголовок класса CCostType
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CCostType_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_
#define _CCostType_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_


#include <map>
/*!
 * \brief набор стоимостей доставки
 * 
 */
class CCostType
{
public:
  enum Type
  {
    AIRLINES=0, //!< Воздушное сообщение
    TRUCK,    //!< Грузовое
    TRAIN,    //!< Поезда
    SHIP,     //!< Кораблики
    UNKNOWN   //!< Все остальное
  };
 
private:
  std::map<Type, unsigned long> m_costs; //!< стоимости
  std::map<Type, bool>          m_avail; //!< наличие вида сообщения
public:
  CCostType();

  void setCost(Type type, unsigned long cost);
  unsigned long getCost(Type type) const;

  bool isAvail(Type type) const;
  
  ~CCostType();
};//class CCostType

#endif //_CCostType_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
