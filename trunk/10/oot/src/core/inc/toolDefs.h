/* $Id: toolDefs.h,v 1.5 2006/10/30 12:18:07 dumb Exp $ */
/*!
 * \file toolDefs.h
 * \brief ��������� ����� �����������
 *
 * ------------------------------------------------------------------------ */


#ifndef _CToolDefs_H_29F3F553_913D_4F33_82DF_1CBAC1B9F2B9_INCLUDED_
#define _CToolDefs_H_29F3F553_913D_4F33_82DF_1CBAC1B9F2B9_INCLUDED_

#include <limits.h>

#ifndef C_ASSERT
  /*!
   * \brief assert ������� ����������
   * \param a: [safe] ����������� ��������� ����� ���������� ���������� � bool
   *
   * ��� ��������� ������� ��������� ������ "������������� ������"
   */
  #define C_ASSERT(a) typedef int __C_ASSERT__[(a)?1:-1]
#endif

#ifndef PACKED
  #if PRAGMAPACK
    #define PACKED
  #else
    #define PACKED __attribute__((packed))
  #endif
#endif

#ifndef __GNUC_PREREQ
  #if defined(__GNUC__) && defined(__GNUC_MINOR__)
    /*!
     * \brief ������ gcc �� ������ ���������
     * \param maj: [safe] ������� ����� ������
     * \param min: [safe] ������� ����� ������
     */
    #define __GNUC_PREREQ(maj, min) \
      ((__GNUC__ << 16) + __GNUC_MINOR__ >= ((maj) << 16) + (min))
  #else
    #define __GNUC_PREREQ(maj, min) 0
  #endif
#endif


#ifndef __GLIBC_PREREQ
  #if defined(__GLIBC__) && defined(__GLIBC_MINOR__)
    /*!
     * \brief ������ glibc �� ������ ���������
     * \param maj: [safe] ������� ����� ������
     * \param min: [safe] ������� ����� ������
     */
    #define __GLIBC_PREREQ(maj, min) \
      ((__GLIBC__ << 16) + __GLIBC_MINOR__ >= ((maj) << 16) + (min))
  #else
    #define __GLIBC_PREREQ(maj, min) 0
  #endif
#endif


typedef unsigned int uint;
typedef unsigned char uint8;
typedef unsigned long uint32;
typedef unsigned short uint16;

typedef signed char int8;
typedef signed long int32;
typedef signed short int16;

#define __VERIFY_INT_BIT_COUNT(name,count) C_ASSERT(sizeof(name##count)*CHAR_BIT==(count))
__VERIFY_INT_BIT_COUNT(uint,8);
__VERIFY_INT_BIT_COUNT(uint,16);
__VERIFY_INT_BIT_COUNT(uint,32);
__VERIFY_INT_BIT_COUNT(int,8);
__VERIFY_INT_BIT_COUNT(int,16);
__VERIFY_INT_BIT_COUNT(int,32);
#undef __VERIFY_INT_BIT_COUNT

#ifndef COUNT
  /*!
   * \brief ���������� ��������� ������� �������������� �������
   * \param a: [unsafe] ��� �������
   */
  #define COUNT(a) (sizeof(a)/sizeof(*(a)))
#endif

#ifndef SIZEOF_RANGE
  /*!
   * \brief ���������� ���� �� ������ ���� a �� ��������� ���� b
   * \param a: [safe] ��������� ����
   * \param b: [unsafe] �������� ����
   */
  #define SIZEOF_RANGE(a,b) (((char*)&(b))-((char*)&(a))+sizeof(b))
#endif

#endif //_CToolDefs_H_29F3F553_913D_4F33_82DF_1CBAC1B9F2B9_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/common/inc/toolDefs.h,v $ ]=== */
