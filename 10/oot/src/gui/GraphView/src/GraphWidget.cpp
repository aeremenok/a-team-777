#include <iostream>
#include "GraphWidget.h"
#include "Edge.h"
#include "Node.h"

#include <QtCore/QDebug>
#include <QtGui/QGraphicsScene>
#include <QtGui/QWheelEvent>

#include <math.h>

#include "DeliveryNetwork.h"

GraphWidget::GraphWidget(QWidget * parent): QGraphicsView(parent), timerId(0)
{
    QGraphicsScene *scene = new QGraphicsScene(this);
    scene->setItemIndexMethod(QGraphicsScene::NoIndex);
    scene->setSceneRect(-1000, -1000, 1000, 1000);
    setScene(scene);
    setCacheMode(CacheBackground);
    setRenderHint(QPainter::Antialiasing);
    setTransformationAnchor(AnchorUnderMouse);
    setResizeAnchor(AnchorViewCenter);
}

void GraphWidget::setGraph(CGraph<CCity, CEdgeParameters> graph)
{
  typedef CGraph<CCity, CEdgeParameters> Super;
//  m_nodes.clear();
//  m_edges.clear();

  size_t i=0;
  for(Super::vertex_iterator v=graph.vertex_begin();v!=graph.vertex_end(); ++v)
  {
    if(m_nodes.count(*v)==0)
    {
      Node *n = new Node(this, *v);
      m_nodes.insert(std::pair<CCity,Node*>(*v,n));
      scene()->addItem(n);  

      n->setPos(-500 + 300*cos(i*M_PI/180),-500 + 300*sin(i*M_PI/180));
    }
    i += 360/graph.vertices_count();
  }
  
  for(Super::edge_iterator v=graph.edge_begin();v!=graph.edge_end(); ++v)
  {
    if(m_edges.count(v->vertex_pair)==0)
    {
      Edge *e = new Edge(m_nodes.find(v->vertex_pair.first)->second, m_nodes.find(v->vertex_pair.second)->second);
      scene()->addItem(e);
      m_edges.insert(std::pair<Super::pair ,Edge*>(v->vertex_pair,e));
    }
  }

  centerNode = m_nodes.begin()->second;
//  centerNode->setSticky(true);
  setMinimumSize(400, 400);
}

void GraphWidget::itemMoved()
{
    if (!timerId)
        timerId = startTimer(1000 / 25);
}

void GraphWidget::keyPressEvent(QKeyEvent *event)
{
    switch (event->key()) {
    case Qt::Key_Up:
        centerNode->moveBy(0, -20);
        break;
    case Qt::Key_Down:
        centerNode->moveBy(0, 20);
        break;
    case Qt::Key_Left:
        centerNode->moveBy(-20, 0);
        break;
    case Qt::Key_Right:
        centerNode->moveBy(20, 0);
        break;
    case Qt::Key_Plus:
        scaleView(1.2);
        break;
    case Qt::Key_Minus:
        scaleView(1 / 1.2);
        break;
    case Qt::Key_Space:
    case Qt::Key_Enter:
        foreach (QGraphicsItem *item, scene()->items()) {
            if (qgraphicsitem_cast<Node *>(item))
                item->setPos(-150 + qrand() % 300, -150 + qrand() % 300);
        }
        break;
    default:
        QGraphicsView::keyPressEvent(event);
    }
}

void GraphWidget::timerEvent(QTimerEvent *event)
{
    Q_UNUSED(event);

    QList<Node *> nodes;
    foreach (QGraphicsItem *item, scene()->items()) {
        if (Node *node = qgraphicsitem_cast<Node *>(item))
            nodes << node;
    }

    foreach (Node *node, nodes)
        node->calculateForces();

    bool itemsMoved = false;
    foreach (Node *node, nodes) {
        if (node->advance())
            itemsMoved = true;
    }

    if (!itemsMoved) {
        killTimer(timerId);
        timerId = 0;
    }
}

void GraphWidget::wheelEvent(QWheelEvent *event)
{
    scaleView(pow((double)2, -event->delta() / 240.0));
}

void GraphWidget::drawBackground(QPainter *painter, const QRectF &rect)
{
    Q_UNUSED(rect);

    // Shadow
    QRectF sceneRect = this->sceneRect();
    QRectF rightShadow(sceneRect.right(), sceneRect.top() + 5, 5, sceneRect.height());
    QRectF bottomShadow(sceneRect.left() + 5, sceneRect.bottom(), sceneRect.width(), 5);
    if (rightShadow.intersects(rect) || rightShadow.contains(rect))
    	painter->fillRect(rightShadow, Qt::darkGray);
    if (bottomShadow.intersects(rect) || bottomShadow.contains(rect))
    	painter->fillRect(bottomShadow, Qt::darkGray);

    // Fill
    QLinearGradient gradient(sceneRect.topLeft(), sceneRect.bottomRight());
    gradient.setColorAt(0, Qt::white);
    gradient.setColorAt(1, Qt::lightGray);
    painter->fillRect(rect.intersect(sceneRect), gradient);
    painter->setBrush(Qt::NoBrush);
    painter->drawRect(sceneRect);
}

void GraphWidget::scaleView(qreal scaleFactor)
{
    qreal factor = matrix().scale(scaleFactor, scaleFactor).mapRect(QRectF(0, 0, 1, 1)).width();
    if (factor < 0.07 || factor > 100)
        return;

    scale(scaleFactor, scaleFactor);
}

void GraphWidget::selectPath(CPath<CCity, CDefaultLink> path)
{
  for(std::map<CGraph<CCity, CEdgeParameters>::pair, Edge*>::iterator it=m_edges.begin(); it!=m_edges.end(); ++it)
  {
    it->second->setActive(false);
    it->second->update();
  }
  typedef CPath<CCity, CDefaultLink> Super;
  Super::vertex_iterator next=path.vertex_begin();++next;
  for(Super::vertex_iterator it=path.vertex_begin();next!=path.vertex_end();++it,next++)
  {
    m_edges.find(std::pair<CCity,CCity>(*it,*next))->second->setActive(true); 
    m_edges.find(std::pair<CCity,CCity>(*it,*next))->second->update(); 
  }

  update();
}

void GraphWidget::setFreeze(bool fl)
{
  for(std::map<CCity, Node*>::iterator it=m_nodes.begin(); it!=m_nodes.end();++it)
  {
    it->second->setSticky(fl);
  }
  if(!fl)
    centerNode->moveBy(1, 0);
}
