#ifndef TREEITEM_H
#define TREEITEM_H

#include <QtCore/QList>
#include <QtCore/QVariant>
#include "City.h"
#include "EdgeParameters.h"
#include "DeliveryNetwork.h"

class CTreeItem
{
    QList<CTreeItem*> childItems;

    std::pair<CCity, CCity> m_pair;
    CEdgeParameters::CLink  m_link;
    CTreeItem             * parentItem;

    CTreeItem(const std::pair<CCity,CCity> & pair, const CEdgeParameters::CLink&, CTreeItem* parent =0);
    
public:
   
    CTreeItem() 
    {}
    CTreeItem(const CDeliveryNetwork::Path &path);

    void appendChild(CTreeItem *child);

    CTreeItem *child(int row);
    int childCount() const;
    int columnCount() const;
    QVariant data(int column) const;
    int row() const;
    CTreeItem *parent();
    
    ~CTreeItem();
};

#endif
