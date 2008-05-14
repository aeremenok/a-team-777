/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file EdgeParameters.h
 * \brief ��������� ������ CEdgeParameters
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_
#define _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

#include <vector>
/*!
 * \brief ����� ���������� ��������
 */
class CEdgeParameters
{
public:
  enum LinkType
  {
    AIRLINES=0, //!< ��������� ���������
    TRUCK,    //!< ��������
    TRAIN,    //!< ������
    SHIP,     //!< ���������
    UNKNOWN   //!< ��� ���������
  };
 
  class CLink
  {
    LinkType m_type; // ��� ����������
    unsigned long m_cost; // ��������� 
    unsigned long m_time; // �����
   
  public:
    CLink():m_type(UNKNOWN), m_cost(0), m_time(0)
    {}

    CLink(LinkType type, unsigned long cost, unsigned long time):m_type(type), m_cost(cost), m_time(time)
    {}
    
    LinkType getType() const
    {
      return m_type;
    }

    unsigned long getCost() const
    {
      return m_cost;
    }

    unsigned long getTime() const
    {
      return m_time;
    }

    bool operator<(const CLink& link) const
    {
      return m_type<link.m_type;
    }
  };

private:

  std::vector<CLink> m_links; // ����� ������������ �����

public:
  CEdgeParameters();

  bool isAvail(LinkType type) const;

  void addLink(const CLink&);

  size_t linkCount() const;

  const CLink& getLink(LinkType type) const;

  const CLink& getLink(size_t index) const;
  
  ~CEdgeParameters();
};//class CEdgeParameters

#endif //_CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
