/* $Id: Trace.h,v 1.7 2007/10/01 07:32:18 dumb Exp $ */
/*!
 * \file Trace.h
 * \brief �������������� ����������� ������ ����� � ������ �� �������� ������
 *
 * ------------------------------------------------------------------------ */


#ifndef _CTrace_H_A6C1533E_B988_4DB6_94D7_2217A89CEC1A_INCLUDED_
#define _CTrace_H_A6C1533E_B988_4DB6_94D7_2217A89CEC1A_INCLUDED_

#include <vector>
#include <string>
#include <iostream>
#include <cstddef>

/*!
 * \brief ������ �� ����� � ����������
 */
class CSource
{
public:
  /*! ������ */
  long m_line;
  /*! ��� ����� */
  const char *m_file;
  /*! ������ ��� ������� */
  const char *m_function;

  CSource(int line,const char *file,const char *function) : m_line(line), m_file(file), m_function(function)
  {
  }

  /*!
   * \brief ������ ����������� ����� � ����������
   * \return std::string ���� file:line:function
   */
  std::string getDescription() const;

  /*!
   * \brief ����� ������ �� ����� � ���������� � �����
   */
  friend std::ostream& operator << (std::ostream &stm,const CSource &a)
  {
    return stm << a.m_file << ":" << a.m_line << ":" << a.m_function;
  }
};

/*!
 * \brief ������� CSource ����������� �� ������� �������
 */
#define SRC() CSource(__LINE__,__FILE__,__PRETTY_FUNCTION__)

/*!
 * \brief ����� ����������� �����
 *
 * ����� ��������� ���������� STRACE() ��������� ���� ����
 * � ������� ������ ����� � ���� ������ �� ��������.
 *
 * � ����� ������ ������������ �������������� ���� ����������,
 * ������� ������ ����� ���������� � ��������� ���������
 * ������ ����� ������.
 * 
 * thread-safe
 */
class CStackTrace
{
  /*! ������ ���������������� ������ */
  typedef std::vector<CSource> CTraceList;
  CTraceList m_trace;

public:

  /*!
   * \brief �������� c��� �������� ������
   */
  static const CStackTrace& getInstance();
  
  /*!
   * \brief ��������� ���� �� ������� �����
   * \param src: [in] ������ �� ���� ����� � ���������
   */
  static void push(const CSource& src);
  
  /*!
   * \brief ����� ������� ���� �����
   */
  static void pop();
  
  /*!
   * \brief �������� ������ �����
   * \return ������ ������ ����� �� �������� ������ �����������
   */
  const CTraceList& getTrace() const
  {
    return m_trace;
  }
};

/*!
 * \brief ����� ������ ����� � �����
 * \todo �������� ����� ������ ���������� ������������� ��������� ��������
 */
std::ostream& operator << (std::ostream& stm,const CStackTrace& a);

/*!
 * \brief ����� ����� �����
 */
class CStackLocation
{
public:
  CStackLocation(const CSource &src)
  {
    CStackTrace::push(src);
  }

  ~CStackLocation()
  {
    CStackTrace::pop();
  }
};

/*!
 * \brief �������� ������������ ���� �����
 */
#define STRACE() CStackLocation __stack_trace_location__(SRC())

#endif //_CTrace_H_A6C1533E_B988_4DB6_94D7_2217A89CEC1A_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/exception/inc/Trace.h,v $ ]=== */
