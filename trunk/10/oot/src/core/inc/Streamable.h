/* $Id: Streamable.h,v 1.2 2007/04/21 13:38:18 dumb Exp $ */
/*!
 * \file Streamable.h
 * \brief заголовок класса IStreamable
 *
 * ------------------------------------------------------------------------ */


#ifndef _CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_
#define _CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_

#include <iostream>

/*!
 * \brief интерфейс выводимого в поток объекта
 */
class IStreamable
{
public:
  virtual ~IStreamable()
  {
  }
  
  /*!
   * \brief вывести объект в поток
   * \param stm: [in] поток для вывода
   * \return stm после вывода
   */
  virtual std::ostream& outToStream(std::ostream& stm) const=0;

  virtual std::istream& inFromStream(std::istream& stm) const=0;
}; // class IStreamable

/*!
 * \brief оператор вывода объекта в поток
 */
inline std::ostream& operator << (std::ostream& stm,const IStreamable& obj)
{
  return obj.outToStream(stm);
}

inline std::istream& operator >> (std::istream& stm, const IStreamable& obj)
{
  return obj.inFromStream(stm);
}

#endif //_CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/streaming/inc/Streamable.h,v $ ]=== */
