/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file DeliveryNetwork.h
 * \brief заголовок класса CDeliveryNetwork
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_
#define _CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_

#include "Graph.h"
#include "City.h"
#include "EdgeParameters.h"

/*!
 * \brief Описание Мира 
 * Набор существующих городов и дорог
 */
class CDeliveryNetwork
{

public:
  typedef CPath<CCity,CDefaultLink> Path;

private:
  typedef CGraph<CCity, CEdgeParameters> Graph;
  
  Graph m_graph; //!< описание мира (: города, дороги и больше ничего

  CDeliveryNetwork();

  /*!
   * \brief проверка существования прямого маршрута
   */
  bool isEdgeAvail(const CCity& from, const CCity& to) const;

  const Graph::edge& getEdge(const CCity& from, const CCity& to) const;

  /*!
   * \brief рекурсивный поиск всех путей до destination
   */
  void findPossiblesRoutes(const CCity& destination, Path& current, std::list<Path> &res);

public:

  const Graph& getContainer() const
  {
    return m_graph;
  }

  /*!
   * \brief получить полный список всех городов
   */
  std::set<CCity> getCitys() const;

  const CCity& getCity(const std::string& name);

  /*!
   * \brief получить список достижимых городов
   */
  std::set<CCity> getAccessibleCity(const CCity& from) const;

  /*!
   * \brief добавить город
   */
  void addCity(const CCity& city);
  
  /*!
   * \brief добавить маршрут из города в город, с заданным типом и стоимостью
   */
  void addRoute(const CCity& from, const CCity& to, CDefaultLink* link);

  /*!
   * \brief получить список всех возможных маршрутов
   */
  std::list<Path> getPossibleRoutes(const CCity& from, const CCity &to);

  static CDeliveryNetwork& getInstance();
  
  ~CDeliveryNetwork();
};//class CDeliveryNetwork

#endif //_CDeliveryNetwork_H_DCA26B2A_255F_438C_A3E4_D624EA85279C_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
