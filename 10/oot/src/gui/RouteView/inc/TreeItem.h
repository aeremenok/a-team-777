#ifndef TREEITEM_H
#define TREEITEM_H

#include <QtCore/QList>
#include <QtCore/QVariant>
#include "City.h"
#include "CostType.h"
#include "DeliveryNetwork.h"

class TreeItem
{
    QList<TreeItem*> childItems;

    std::pair<CCity, CCity> m_pair;
    CCostType               m_cost;
    CCostType::Type         m_type;
    TreeItem *              parentItem;

    TreeItem(const std::pair<CCity,CCity> & pair, const CCostType& cost, CCostType::Type type, TreeItem* parent =0);

public:
    
    TreeItem(){}

    TreeItem* getSubTree(const CDeliveryNetwork::CPath &path);

    void appendChild(TreeItem *child);

    TreeItem *child(int row);
    int childCount() const;
    int columnCount() const;
    QVariant data(int column) const;
    int row() const;
    TreeItem *parent();
    
    ~TreeItem();
};

#endif
