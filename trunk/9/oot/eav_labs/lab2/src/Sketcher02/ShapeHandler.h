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
    // ������������ �� ��������� ����������
    bool _isGraphVisible;
    // ������� �����������
    int m_Scale;
    //////////////////////////////////////////////////////////////////////////
    //�������� �� ����������� ������ ������� ������� m_pSelected
    ExternalGraphIterator<CElement>* _lastNearestRibbles; 
    // ����� ��� ���������
    Ribble<CElement>* _ribble;
    // ��������� �������
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
    // ������������ ����������
    void onDraw( CDC* pDC );
    void onLBDown( CClientDC &aDC, CPoint &point );
    void onLBUp( CClientDC &aDC, CPoint& point );
    void onMMove( CPoint& point, bool flag);
    void onRBDown( CPoint &point );
    void onRBUp( CPoint &point );
    //////////////////////////////////////////////////////////////////////////
    // ���������� ����� �� ��������� �� �����
    void drawRibble( Ribble<CElement>* ribble, COLORREF aColor );
    // ���������� "�����" �� 2� ������
    void drawRibble( CElement* start, CElement* end);
    // �������� ������ ����� �������� ��� ���������
    void markHighlighted( CElement* pCurrentSelection);
    //////////////////////////////////////////////////////////////////////////
    // ����� �� ��������� ������ �� �����
    bool canProceed();
    // �������� ������ ����������� �����
    ExternalGraphIterator<CElement>* refreshNearestRibbles();
    // ������� ������� �����
    void changeRibble(bool isNext);
    // ������� ������� �������
    void changeVertex();
};

#endif // !defined(AFX_SHAPEHANDLER_H__2CA88070_8010_4B95_A1E8_ABBB038685E4__INCLUDED_)
