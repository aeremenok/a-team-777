#ifndef GRAPHWIDGET_H
#define GRAPHWIDGET_H

#include <map>
#include <QtGui/QGraphicsView>
#include "City.h"
#include "EdgeParameters.h"
#include "Graph.h"
class Node;
class Edge;

class GraphWidget : public QGraphicsView
{
    Q_OBJECT

public:
    GraphWidget(QWidget * parent = 0);

    void itemMoved();

    void setGraph(CGraph<CCity,CEdgeParameters> graph);

public slots:

    void selectPath(CPath<CCity, CDefaultLink>);
    
    void setFreeze(bool fl);
protected:
    void keyPressEvent(QKeyEvent *event);
    void timerEvent(QTimerEvent *event);
    void wheelEvent(QWheelEvent *event);
    void drawBackground(QPainter *painter, const QRectF &rect);

    void scaleView(qreal scaleFactor);

private:
    int timerId;
    Node *centerNode;
    std::map<CCity, Node*> m_nodes;
    std::map<CGraph<CCity, CEdgeParameters>::pair, Edge*> m_edges;
};

#endif
