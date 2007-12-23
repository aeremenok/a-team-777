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
#include "SketcherView.h"

class CSketcherView;
//////////////////////////////////////////////////////////////////////////
class ShapeHandler  
{
    CSketcherView* _view;

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
    //////////////////////////////////////////////////////////////////////////
    // Create a new element on the heap
    CElement* CreateElement();
    // Select an element
    CElement* SelectElement(CPoint aPoint);
    // Move an element
    void MoveElement(CPoint& point);
    //////////////////////////////////////////////////////////////////////////
    // отрисовать ребро по указателю на ребро
    void drawRibble( Ribble<CElement>* ribble, COLORREF aColor );
    // заданная фигура будет отмечена для подсветки
    void markHighlighted( CElement* pCurrentSelection);
    //////////////////////////////////////////////////////////////////////////
    // можно ли двигаться дальше по графу
    bool canProceed();
    // освежить список инцидентных ребер
    ExternalGraphIterator<CElement>* refreshNearestRibbles();
    //////////////////////////////////////////////////////////////////////////
    CSketcherDoc* GetDocument();
public:
    ShapeHandler(CSketcherView* view);
	virtual ~ShapeHandler();
    //////////////////////////////////////////////////////////////////////////
    int Scale() const { return m_Scale; }
    void Scale(int val) { m_Scale = val; }

    bool IsGraphVisible() const { return _isGraphVisible; }
    void IsGraphVisible(bool val) { _isGraphVisible = val; }

    CElement* Selected() const { return m_pSelected; }
    //////////////////////////////////////////////////////////////////////////
    void onMove();
    void onDelete();
    void onDraw( CDC* pDC );

    void onLBDown( CPoint &point );
    void onLBUp( CPoint& point );
    void onRBDown( CPoint &point );
    void onRBUp( CPoint &point );

    void onMMove( CPoint& point, bool flag);
    //////////////////////////////////////////////////////////////////////////
    // сменить текущее ребро
    void changeRibble(bool isNext);
    // сменить текущую вершину
    void changeVertex();
};

#endif // !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
