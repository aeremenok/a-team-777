
#include <QtCore/QStringList>
#include "TreeItem.h"


CTreeItem::CTreeItem(const CDeliveryNetwork::Path &path):parentItem(NULL)
{ 
  m_pair = std::pair<CCity,CCity>(path.vertex_front(), path.vertex_back());
  m_link = CEdgeParameters::CLink(CEdgeParameters::UNKNOWN, path.getCost(),0);
  CDeliveryNetwork::Path::link_const_iterator link = path.link_begin();
  CDeliveryNetwork::Path::vertex_const_iterator it = path.vertex_begin();
  for(;it!=path.vertex_end();++it,++link)
  {
    CDeliveryNetwork::Path::vertex_const_iterator next = it;  next++;
    appendChild(new CTreeItem(std::pair<CCity,CCity>(*it,*next), *link, this));
  }
}

CTreeItem::CTreeItem(const std::pair<CCity,CCity> &pair, const CEdgeParameters::CLink &link, CTreeItem *parent)
{
  m_pair = pair;
  m_link = link;
  parentItem = parent;
}

CTreeItem::~CTreeItem()
{
  qDeleteAll(childItems);
}

void CTreeItem::appendChild(CTreeItem *item)
{
  childItems.append(item);
}

CTreeItem *CTreeItem::child(int row)
{
  return childItems.value(row);
}

int CTreeItem::childCount() const
{
  return childItems.count();
}

int CTreeItem::columnCount() const
{
  return 4;
}

QVariant CTreeItem::data(int column) const
{
  switch(column)
  {
    case 0:
      return QVariant(QObject::tr(m_pair.first.getName().c_str()));
    case 1:
      return QVariant(QObject::tr(m_pair.second.getName().c_str()));
    case 2:
      return QVariant(m_link.getType());
    case 3:
      return QVariant((unsigned int)(m_link.getCost()));
  }
  return QVariant();
}

CTreeItem *CTreeItem::parent()
{
  return parentItem;
}

int CTreeItem::row() const
{
  if (parentItem)
    return parentItem->childItems.indexOf(const_cast<CTreeItem*>(this));

  return 0;
}
