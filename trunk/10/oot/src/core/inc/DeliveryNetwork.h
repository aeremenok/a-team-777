/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DeliveryNetwork.h
 * \brief ��������� ������ CDeliveryNetwork
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_
#define _CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_

#include "Graph.h"
#include "City.h"
#include "CostType.h"

/*!
 * \brief �������� ���� 
 * ����� ������������ ������� � �����
 */
class CDeliveryNetwork
{

  typedef CGraph<CCity, CCostType> Graph;
  
  Graph m_graph; //!< �������� ���� (: ������, ������ � ������ ������

  CDeliveryNetwork(const CDeliveryNetwork& obj);
  CDeliveryNetwork& operator=(const CDeliveryNetwork& obj);
  
  CDeliveryNetwork();

  /*!
   * \brief �������� ������������� ������� ��������
   */
  bool isEdgeAvail(const CCity& from, const CCity& to) const;

  const Graph::edge& getEdge(const CCity& from, const CCity& to) const;

public:
  /*!
   * \brief �������� ������ ������ ���� �������
   */
  std::set<CCity> getCitys() const;

  /*!
   * \brief �������� ������ ���������� �������
   */
  std::set<CCity> getAccessibleCity(const CCity& from) const;

  /*!
   * \brief �������� �����
   */
  void addCity(const CCity& city);
  
  /*!
   * \brief �������� ������� �� ������ � �����, � �������� ����� � ����������
   */
  void addRoute(const CCity& from, const CCity& to, CCostType::Type type, unsigned long cost);

  static CDeliveryNetwork& getInstance();
  
  ~CDeliveryNetwork();
};//class CDeliveryNetwork

#endif //_CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
