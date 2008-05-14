/* $Id: ConstMap.h,v 1.14 2006/09/22 08:14:30 autoreleaser Exp $ */
/*!
 * \file ConstMap.h
 * \brief ��������� ������ CConstMap
 *
 * ------------------------------------------------------------------------ */


#ifndef _CConstMap_H_B4C15E68_6CFD_4F2E_A0A0_92C493E6DA61_INCLUDED_
#define _CConstMap_H_B4C15E68_6CFD_4F2E_A0A0_92C493E6DA61_INCLUDED_

#include "toolDefs.h"
#include <map>

/*!
 * \brief ��������� � ������ ������ std::map
 *
 * ��������� ��������� const T& [](const Key&) const
 * ���������� ����������� ������ ������, �������� �������� �������� ��� ��������
 * ���� � ����� ���������� setDefault
 */
template<typename _Key,
  typename _Value,
  typename _Compare=std::less<_Key>,
#if __GNUC_PREREQ(3,2)
  typename _Allocator=std::allocator<std::pair<const _Key,_Value> > >
#else
  typename _Allocator=std::allocator<_Value> >
#endif
class CConstMap: public std::map<_Key,_Value,_Compare,_Allocator>
{
  typedef std::map<_Key,_Value,_Compare,_Allocator> Super;
  _Value m_default;
  
public:
  typedef _Key Key;
  typedef _Value Value;
  
  typedef typename Super::key_type key_type;
  typedef typename Super::mapped_type mapped_type;
  typedef typename Super::value_type value_type;
  typedef typename Super::key_compare key_compare;
  
  typedef typename Super::allocator_type allocator_type;
  
  typedef typename Super::reference reference;
  typedef typename Super::const_reference const_reference;
  
  typedef typename Super::iterator iterator;
  typedef typename Super::const_iterator const_iterator;
  
  typedef typename Super::size_type size_type;
  typedef typename Super::difference_type difference_type;
  
  typedef typename Super::pointer pointer;
  typedef typename Super::const_pointer const_pointer;

  typedef typename Super::reverse_iterator reverse_iterator;
  typedef typename Super::const_reverse_iterator const_reverse_iterator;

  typedef typename Super::value_compare value_compare;

  /*!
   * \brief ����������� �� ���������
   */
  CConstMap(): m_default()
  {
  }

  /*!
   * \brief ����������� �� ��������� ��-���������
   * \param def: [in] �������� ��-���������
   */
  explicit
  CConstMap(const Value& def,const _Compare& cmp=_Compare(),const _Allocator& a=_Allocator()):
    Super(cmp,a),m_default(def)
  {
  }

  /*!
   * \brief ����������� �� ���� ����������
   */
  template<typename InputIterator>
  CConstMap(InputIterator first,InputIterator last): Super(first,last),m_default()
  {
  }

  /*!
   * \brief ����������� �� ���� ����������
   * \param def: [in] �������� ��-���������
   */
  template<typename InputIterator,typename V>
  CConstMap(InputIterator first,InputIterator last,const V& def,
    const _Compare& cmp=_Compare(),const _Allocator& a=_Allocator()):
      Super(first,last,cmp,a),m_default(def)
  {
  }

  /*!
   * \brief ����������� �� �������
   * \param r: [in] ������ �� ���������� ��� �������������
   * \param def: [in] �������� ��-���������
   */
  template<typename T,int SZ>
  explicit
  CConstMap(const T (&r)[SZ],const Value& def=Value(),
    const _Compare& cmp=_Compare(),const _Allocator& a=_Allocator()):
      Super(r,r+SZ,cmp,a),m_default(def)
  {
  }

  /*!
   * \brief ���������� �������� ��-���������
   */
  void setDefault(const Value& def)
  {
    m_default=def;
  }

  Value& operator [](const Key& key)
  {
    return static_cast<Super&>(*this)[key];
  }
  
  /*!
   * \brief �������� ����������� ������ �� ������������ ������� ����
   * ��� �� �������� ��-���������, ���� ������������ ����� ���
   */
  const Value& operator [](const Key& key) const
  {
    const_iterator it=find(key);
    if(it==this->end())
    {
      return m_default;
    }
    else
      return it->second;
  }
  
  /*!
   * \brief ������ []
   */
  Value& get(const Key& key)
  {
    return (*this)[key];
  }

  /*!
   * \brief �������� ����������� ������ �� ������������ ������� ����
   * ��� �� �������� ��-���������, ���� ������������ ����� ���
   */
  const Value& get(const Key& key) const
  {
    return (*this)[key];
  }

  const Value& get(const Key& key,const Value& def) const
  {
    const_iterator it=find(key);
    if(it==this->end())
    {
      return def;
    }
    else
      return it->second;
  }

  /*!
   * \brief �������� ���������� � ������ CConstMap
   * \note �������� ��-��������� ���� ������������
   */
  void swap(CConstMap& obj)
  {
    static_cast<Super&>(*this).swap(static_cast<Super&>(obj));
    swap(m_default,obj.m_default);
  }
};//class CConstMap

template<typename Key,
  typename Value,
  typename Compare,
  typename Allocator>
void swap(CConstMap<Key,Value,Compare,Allocator>& a,
  CConstMap<Key,Value,Compare,Allocator>& b)
{
  a.swap(b);
}

#endif //_CConstMap_H_B4C15E68_6CFD_4F2E_A0A0_92C493E6DA61_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/src/common/inc/ConstMap.h,v $ ]=== */
