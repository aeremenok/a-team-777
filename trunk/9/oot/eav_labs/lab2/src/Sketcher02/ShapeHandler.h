// ShapeHandler.h: interface for the ShapeHandler class.
//
//////////////////////////////////////////////////////////////////////
#if !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
#define AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_
//////////////////////////////////////////////////////////////////////////
#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//////////////////////////////////////////////////////////////////////////
#include "container/ExternalGraphIterator.h"
#include "container/Ribble.h"

#include "Elements.h"
#include "SketcherDoc.h"
//////////////////////////////////////////////////////////////////////////
class ShapeHandler  
{
    CScrollView* _view;

    // First point recorded for an element
    CPoint m_FirstPoint;       
    // Second point recorded for an element
    CPoint m_SecondPoint;
    // Pointer to temporary element
    CElement* m_pTempElement;
    // Currently selected element
    CElement* m_pSelected;
    
    // Move element flag
    BOOL m_MoveMode;
    // Cursor position
    CPoint m_CursorPos;
    // Original position in a move    
    CPoint m_FirstPos;
    //////////////////////////////////////////////////////////////////////////
    // отображается ли служебная информация
    bool _isGraphVisible;
    // масштаб отображения
    int m_Scale;
    //////////////////////////////////////////////////////////////////////////
    //итератор по инцидентным ребрам текущей вершины m_pSelected
    ExternalGraphIterator<CElement>* _lastNearestRibbles; 
    // ребро для подсветки
    Ribble<CElement>* _ribble;
    // начальная вершина
    CElement* _firstVertex;
public:
    ShapeHandler(CScrollView* view);
	virtual ~ShapeHandler();
    //////////////////////////////////////////////////////////////////////////
    int Scale() const { return m_Scale; }
    void Scale(int val) { m_Scale = val; }

    bool IsGraphVisible() const { return _isGraphVisible; }
    void IsGraphVisible(bool val) { _isGraphVisible = val; }

    CSketcherDoc* GetDocument();

    CElement* Selected() const { return m_pSelected; }
    //////////////////////////////////////////////////////////////////////////
    // Create a new element on the heap
    CElement* CreateElement();
    // Select an element
    CElement* SelectElement(CPoint aPoint, CClientDC& aDC);
    // Move an element
    void MoveElement(CClientDC& aDC, CPoint& point);
    void onMove();
    void onDelete();
    //////////////////////////////////////////////////////////////////////////
    // отрисовывает содержимое
    void onDraw( CDC* pDC );
    void onLBDown( CClientDC &aDC, CPoint &point );
    void onLBUp( CClientDC &aDC, CPoint& point );
    void onMMove( CPoint& point, bool flag);
    void onRBDown( CPoint &point );
    void onRBUp( CPoint &point );
    //////////////////////////////////////////////////////////////////////////
    // отрисовать ребро по указателю на ребро
    void drawRibble( Ribble<CElement>* ribble, COLORREF aColor );
    // отрисовать "ребро" по 2м точкам
    void drawRibble( CElement* start, CElement* end);
    // заданная фигура будет отмечена для подсветки
    void markHighlighted( CElement* pCurrentSelection);
    //////////////////////////////////////////////////////////////////////////
    // можно ли двигаться дальше по графу
    bool canProceed();
    // освежить список инцидентных ребер
    ExternalGraphIterator<CElement>* refreshNearestRibbles();
    // сменить текущее ребро
    void changeRibble(bool isNext);
    // сменить текущую вершину
    void changeVertex();
};

#endif // !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
