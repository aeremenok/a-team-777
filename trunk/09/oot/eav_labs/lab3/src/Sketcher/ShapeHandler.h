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

#include "Elements.h"
#include "SketcherDoc.h"
#include "SketcherView.h"

class CSketcherView;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070074
class ShapeHandler  
{
	//##ModelId=4770E2070084
    CSketcherView* _view;

    // First point recorded for an element
	//##ModelId=4770E2070088
    CPoint m_FirstPoint;       
    // Second point recorded for an element
	//##ModelId=4770E2070089
    CPoint m_SecondPoint;
    // Pointer to temporary element
	//##ModelId=4770E2070094
    CElement* m_pTempElement;
    // Currently selected element
	//##ModelId=4770E2070099
    CElement* m_pSelected;
    
    // Move element flag
	//##ModelId=4770E20700A3
    BOOL m_MoveMode;
    // Cursor position
	//##ModelId=4770E20700A4
    CPoint m_CursorPos;
    // Original position in a move    
	//##ModelId=4770E20700A5
    CPoint m_FirstPos;
    //////////////////////////////////////////////////////////////////////////
    // отображается ли служебная информация
	//##ModelId=4770E20700B3
    bool _isGraphVisible;
    // масштаб отображения
	//##ModelId=4770E20700B4
    int m_Scale;
    //////////////////////////////////////////////////////////////////////////
    //итератор по инцидентным ребрам текущей вершины m_pSelected
	//##ModelId=4770E20700C5
    ExternalGraphIterator<CElement>* _lastNearestRibbles; 
    // ребро для подсветки
	//##ModelId=4770E20700E3
    Ribble<CElement>* _ribble;
    // начальная вершина
	//##ModelId=4770E20700E8
    CElement* _firstVertex;
    //////////////////////////////////////////////////////////////////////////
    // Create a new element on the heap
	//##ModelId=4770E20700F1
    CElement* CreateElement();
    // Select an element
	//##ModelId=4770E20700F2
    CElement* SelectElement(CPoint aPoint);
    // Move an element
	//##ModelId=4770E20700F4
    void MoveElement(CPoint& point);
    //////////////////////////////////////////////////////////////////////////
    // отрисовать ребро по указателю на ребро
	//##ModelId=4770E2070102
    void drawRibble( Ribble<CElement>* ribble, COLORREF aColor );
    // заданная фигура будет отмечена для подсветки
	//##ModelId=4770E2070110
    void markHighlighted( CElement* pCurrentSelection);
    //////////////////////////////////////////////////////////////////////////
    // можно ли двигаться дальше по графу
	//##ModelId=4770E2070112
    bool canProceed();
    // освежить список инцидентных ребер
	//##ModelId=4770E2070113
    ExternalGraphIterator<CElement>* refreshNearestRibbles();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E2070120
    CSketcherDoc* GetDocument();
public:
	//##ModelId=4770E2070121
    ShapeHandler(CSketcherView* view);
	//##ModelId=4770E2070123
	virtual ~ShapeHandler();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E2070125
    int Scale() const { return m_Scale; }
	//##ModelId=4770E2070130
    void Scale(int val) { m_Scale = val; }

	//##ModelId=4770E2070132
    bool IsGraphVisible() const { return _isGraphVisible; }
	//##ModelId=4770E2070134
    void IsGraphVisible(bool val) { _isGraphVisible = val; }

	//##ModelId=4770E2070136
    CElement* Selected() const { return m_pSelected; }
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E2070140
    void onMove();
	//##ModelId=4770E2070141
    void onDelete();
	//##ModelId=4770E2070142
    void onDraw( CDC* pDC );

	//##ModelId=4770E207015E
    void onLBDown( CPoint &point );
	//##ModelId=4770E2070160
    void onLBUp( CPoint& point );
	//##ModelId=4770E2070162
    void onRBDown( CPoint &point );
	//##ModelId=4770E207016E
    void onRBUp( CPoint &point );

	//##ModelId=4770E2070170
    void onMMove( CPoint& point, bool flag);
    //////////////////////////////////////////////////////////////////////////
    // сменить текущее ребро
	//##ModelId=4770E207017E
    void changeRibble(bool isNext);
    // сменить текущую вершину
	//##ModelId=4770E2070180
    void changeVertex();
};

#endif // !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
