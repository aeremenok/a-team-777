/* $Id: Exception.h,v 1.5 2006/08/28 11:35:16 dumb Exp $ */
/*!
 * \file Exception.h
 * ------------------------------------------------------------------------ */


#ifndef _CException_H_71DA1F16_33DC_432E_8F07_BBEBB4297269_INCLUDED_
#define _CException_H_71DA1F16_33DC_432E_8F07_BBEBB4297269_INCLUDED_

#include <string>
#include <iostream>
#include "Streamable.h"
#include "Trace.h"

/*!
  \class CException
  \brief ������� ����� ��� ���� ���������� � ���������� CLib
*/
class CException: public IStreamable
{
  unsigned short m_errcode;  /*!< ���������� ������������� ������ */
  unsigned char  m_package;  /*!< ������������� ������ - ��������� ���������� */

public:

  /*! ����������� ����������
    \param  errorcode ������������� ������
    \param  package_id ������������� ������-��������� ����������
  */
  CException(unsigned short errorcode, unsigned char package_id)
  {
    m_errcode=errorcode;
    m_package=package_id;
  }

  /*! ����������� ���������� */
  virtual ~CException()
  {
  }

  /*! �������� ��� ������
    \return ��� ������, ������������ � \ref errcodes.h
  */
  const unsigned short& getErrorCode() const
  {
    return m_errcode;
  }

  /*! �������� ������������� ������- ��������� ������
    \return ������������� ������-��������� ����������
  */
  const unsigned char& getPackage() const
  {
    return m_package;
  }

  /*! ������������ ���������� ���������� ������

    ���������� ���������� ������ ������������ ����� ����������� ����� ���������� ���
    ������ � ������������� ������-���������. ����� �������������� � �������� ����� � ����������
    ������ ��� ��� ������ � �������.
    \code
    ������

    31         23         15          7         0
    |==3 ����==|==2 ����==|==1 ����==|==0 ����==|
    |----------+----------+----------+----------|
    | package  | reserved |    error code       |
    |----------+----------+----------+----------|
    \endcode

    \return ���������� ���������� ������
  */
  unsigned long getErrorHandle() const
  {
   return (((unsigned long)m_package) << 24 ) + m_errcode;
  }

  virtual std::ostream& outToStream(std::ostream& stm) const;

  /*!
   * \brief ������� � ����� ��������� �������� ����������.
   *
   * �������� �� �������� ������� ��������� ���������� ������������ �������������.
   * ������� ������������ ��� �������� ������, �����������, ��� ������ ���������.
   *
   * \param stm: ����� ��� ������ ��������
   * \return ����� ����� ������
   */
  virtual std::ostream& outDescription(std::ostream& stm) const
  {
    return stm;
  }

  /*!
   * \brief �������� ��������� �������� ����������.
   *
   * �������� �� �������� ������� ��������� ���������� ������������ �������������.
   * ������� ������������ ��� �������� ������, �����������, ��� ������ ���������.
   *
   */
  virtual std::string getDescription() const;

}; // class CException


//=======================[ class CExceptionSource ]======================================


/*!
  \class CExceptionSource
  \brief ����� ���������� � ����������� � ����� ���������� � �������� �� �������� �����
*/
class CExceptionSource : public CException
{
  CSource m_src;  /*!< ������ �� �������� ���������� */
  CStackTrace m_stack;

public:

  /*!
    ����������� �������������
    \param errorcode ��� ������, ������������ � ����� errcodes.h
    \param source ��������� ����� ������������� ���������� (��. �������� \ref SRC, \ref CSource)
    \param package_id ������������� ������
  */
  CExceptionSource(unsigned short errorcode, const CSource& src,unsigned char package_id):
    CException(errorcode,package_id), m_src(src), m_stack(CStackTrace::getInstance())
  {
  }

  /*! �������� ������ � ������ �����
    \return ������ � ������ �����, � ������� ������������� ����������
  */
  std::string getSource() const { return m_src.m_file; }

  /*! �������� ��� �������, �������������� ����������
  */
  std::string getSourceFunction() const { return m_src.m_function; }

  /*! �������� ����� ������ � ������� ������������� ����������
    \return ����� ������-��������� ����������
  */
  unsigned int getLine() const { return m_src.m_line; }

  /*!
   * ����� ������� � �����
   * \param stm: [in] ����� ��� ������
   * \return ����� stm ����� ������
   */
  virtual std::ostream& outToStream(std::ostream& stm) const;
}; // class CExceptionSource


//=======================[ class CNotImplementedException ]======================================

/*!
  \class CNotImplementedException
  \brief ���������� ���������� ����������

  ������ ��� ���������� ������������ ��� �������� ������ ��������� �
  ������� ������� � �������� ��� ������������� �����������
*/
class CNotImplementedException: public CExceptionSource
{
  std::string m_description;
  
public:
  /*!
   * \brief ����������� �������������
   * \param source: [in] ��������� ����� ������������� ���������� (��. �������� \ref SRC, \ref CSource)
   * \param description: [in] ��������
   */
  CNotImplementedException(const CSource& source,const std::string& description):
    CExceptionSource(0,source,0),m_description(description)
  {
  }

  virtual std::string getDescription() const;
};

#ifndef NOT_IMPLEMENTED
/*!
 * \brief ��������� ���������� � ��������������� ����������������
 * \param description: [safe] ��������
 */
#define NOT_IMPLEMENTED(description) throw CNotImplementedException(SRC(),(description))
#endif

#endif //_CException_H_71DA1F16_33DC_432E_8F07_BBEBB4297269_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/exception/inc/Exception.h,v $ ]=== */
