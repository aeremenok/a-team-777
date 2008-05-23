/* $Id: Streamable.h,v 1.2 2007/04/21 13:38:18 dumb Exp $ */
/*!
 * \file Streamable.h
 * \brief ��������� ������ IStreamable
 *
 * ------------------------------------------------------------------------ */


#ifndef _CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_
#define _CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_

#include <iostream>

/*!
 * \brief ��������� ���������� � ����� �������
 */
class IStreamable
{
public:
  virtual ~IStreamable()
  {
  }
  
  /*!
   * \brief ������� ������ � �����
   * \param stm: [in] ����� ��� ������
   * \return stm ����� ������
   */
  virtual std::ostream& outToStream(std::ostream& stm) const=0;
}; // class IStreamable

/*!
 * \brief �������� ������ ������� � �����
 */
inline std::ostream& operator << (std::ostream& stm,const IStreamable& obj)
{
  return obj.outToStream(stm);
}

#endif //_CStreamable_H_1BBACD52_4FAC_4F9B_9C15_4AFE84F1D35F_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/streaming/inc/Streamable.h,v $ ]=== */
