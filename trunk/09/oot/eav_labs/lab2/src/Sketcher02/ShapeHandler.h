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
//##ModelId=476EA08C037A
class ShapeHandler  
{
	//##ModelId=476EA08C037C
    CSketcherView* _view;

    // First point recorded for an element
	//##ModelId=476EA08C0380
    CPoint m_FirstPoint;       
    // Second point recorded for an element
	//##ModelId=476EA08C03C8
    CPoint m_SecondPoint;
    // Pointer to temporary element
	//##ModelId=476EA08C03CA
    CElement* m_pTempElement;
    // Currently selected element
	//##ModelId=476EA08C03D9
    CElement* m_pSelected;
    
    // Move element flag
	//##ModelId=476EA08C03DD
    BOOL m_MoveMode;
    // Cursor position
	//##ModelId=476EA08C03DE
    CPoint m_CursorPos;
    // Original position in a move    
	//##ModelId=476EA08D0000
    CPoint m_FirstPos;
    //////////////////////////////////////////////////////////////////////////
    // отображается ли служебная информация
	//##ModelId=476EA08D0001
    bool _isGraphVisible;
    // масштаб отображения
	//##ModelId=476EA08D0002
    int m_Scale;
    //////////////////////////////////////////////////////////////////////////
    //итератор по инцидентным ребрам текущей вершины m_pSelected
	//##ModelId=476EA08D0010
    ExternalGraphIterator<CElement>* _lastNearestRibbles; 
    // ребро для подсветки
	//##ModelId=476EA08D0015
    Ribble<CElement>* _ribble;
    // начальная вершина
	//##ModelId=476EA08D0020
    CElement* _firstVertex;
    //////////////////////////////////////////////////////////////////////////
    // Create a new element on the heap
	//##ModelId=476EA08D0024
    CElement* CreateElement();
    // Select an element
	//##ModelId=476EA08D0025
    CElement* SelectElement(CPoint aPoint);
    // Move an element
	//##ModelId=476EA08D002E
    void MoveElement(CPoint& point);
    //////////////////////////////////////////////////////////////////////////
    // отрисовать ребро по указателю на ребро
	//##ModelId=476EA08D0030
    void drawRibble( Ribble<CElement>* ribble, COLORREF aColor );
    // заданная фигура будет отмечена для подсветки
	//##ModelId=476EA08D0033
    void markHighlighted( CElement* pCurrentSelection);
    //////////////////////////////////////////////////////////////////////////
    // можно ли двигаться дальше по графу
	//##ModelId=476EA08D003F
    bool canProceed();
    // освежить список инцидентных ребер
	//##ModelId=476EA08D0040
    ExternalGraphIterator<CElement>* refreshNearestRibbles();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D0041
    CSketcherDoc* GetDocument();
public:
	//##ModelId=476EA08D0042
    ShapeHandler(CSketcherView* view);
	//##ModelId=476EA08D0044
	virtual ~ShapeHandler();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D004E
    int Scale() const { return m_Scale; }
	//##ModelId=476EA08D0050
    void Scale(int val) { m_Scale = val; }

	//##ModelId=476EA08D0052
    bool IsGraphVisible() const { return _isGraphVisible; }
	//##ModelId=476EA08D0054
    void IsGraphVisible(bool val) { _isGraphVisible = val; }

	//##ModelId=476EA08D0056
    CElement* Selected() const { return m_pSelected; }
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D0058
    void onMove();
	//##ModelId=476EA08D005D
    void onDelete();
	//##ModelId=476EA08D005E
    void onDraw( CDC* pDC );

	//##ModelId=476EA08D0060
    void onLBDown( CPoint &point );
	//##ModelId=476EA08D0062
    void onLBUp( CPoint& point );
	//##ModelId=476EA08D0064
    void onRBDown( CPoint &point );
	//##ModelId=476EA08D006D
    void onRBUp( CPoint &point );

	//##ModelId=476EA08D006F
    void onMMove( CPoint& point, bool flag);
    //////////////////////////////////////////////////////////////////////////
    // сменить текущее ребро
	//##ModelId=476EA08D0072
    void changeRibble(bool isNext);
    // сменить текущую вершину
	//##ModelId=476EA08D007D
    void changeVertex();
};

#endif // !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
