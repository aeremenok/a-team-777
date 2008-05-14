
#include <QtCore/QStringList>
#include "TreeItem.h"


TreeItem* TreeItem::getSubTree(const CDeliveryNetwork::CPath &path)
{
  std::list<CCity> list = path.getVertices();
  std::list<CCostType> costs = path.getCost();

  TreeItem *parent = new TreeItem(std::pair<CCity,CCity>(list.front(), list.back()), path.getSummaryCost(), CCostType::UNKNOWN);

  std::list<CCostType>::iterator cost = costs.begin();

  for(std::list<CCity>::iterator it=list.begin();it!=list.end();++it,++cost)
  {
    std::list<CCity>::iterator next = it;  next++;
    parent->appendChild(new TreeItem(std::pair<CCity,CCity>(*it,*next), *cost, parent));
  }

  return parent;
}

TreeItem::TreeItem(const std::pair<CCity,CCity> &pair, const CCostType &cost, CCostType::Type type, TreeItem *parent)
{
  m_pair = pair;
  m_type = type;
  m_cost = cost;
  parentItem = parent;
}

TreeItem::~TreeItem()
{
  qDeleteAll(childItems);
}

void TreeItem::appendChild(TreeItem *item)
{
  childItems.append(item);
}

TreeItem *TreeItem::child(int row)
{
  return childItems.value(row);
}

int TreeItem::childCount() const
{
  return childItems.count();
}

int TreeItem::columnCount() const
{
  return 4;
}

QVariant TreeItem::data(int column) const
{
  switch(column)
  {
    case 0:
      return QVariant(QObject:(m_pair.first.getName().c_str()));
    case 1:
      return QVariant(QObject:tr(m_pair.second.getName().c_str()));
    case 2:
      return QVariant(m_type);
    case 3:
      return QVariant(m_cost.getCost(m_type));
  }
}

TreeItem *TreeItem::parent()
{
  return parentItem;
}

int TreeItem::row() const
{
  if (parentItem)
    return parentItem->childItems.indexOf(const_cast<TreeItem*>(this));

  return 0;
}
