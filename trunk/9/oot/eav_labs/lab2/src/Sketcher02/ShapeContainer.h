// ShapeContainer.h: interface for the ShapeContainer class.
//
//////////////////////////////////////////////////////////////////////
#if !defined(AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_)
#define AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//////////////////////////////////////////////////////////////////////////
#include "container/Graph.h"
#include "shapes/Shape.h"
#include "Elements.h"

#include <map>
//////////////////////////////////////////////////////////////////////////
// ������� ���������� ��� �������������
class ShapeContainer
{
    // Current drawing color
    COLORREF m_Color;
    // Current element type
    WORD m_Element;
    // ���� ����� ���������
    Graph<CElement>* _container;
    //////////////////////////////////////////////////////////////////////////
    // ����������� �������� ����������
    void serializeContainer( CArchive& ar );
    // ��������������� �� ����� ������ � �� ��������� � ����������
    Shape* readShape(CArchive &ar, map<int, Shape*> &shapes);
public:
	ShapeContainer();
	virtual ~ShapeContainer();
    //////////////////////////////////////////////////////////////////////////
    WORD GetElementType(){return m_Element;}
    void SetElementType(WORD type){m_Element = type;}

	COLORREF GetElementColor(){return m_Color;}
    void SetElementColor(COLORREF color){m_Color = color;}
    //////////////////////////////////////////////////////////////////////////
    CElement* AddElement(CElement* m_pElement);
    void SendToBack(CElement* pElement);
    void DeleteElement(CElement* m_pSelected);

    void linkElements(CElement* element1, CElement* element2);
    //////////////////////////////////////////////////////////////////////////
    // �������� ����� ��������, ���������� �� ������ ����������
    Iterator<CElement>* getNewIterator() const { return _container->getIterator(); }
    // �������� ������ �����, ����������� ��������� �������
    ExternalGraphIterator<CElement>* getNearestRibbles(CElement* selected);
    //////////////////////////////////////////////////////////////////////////
    void serialize(CArchive& ar);
};

#endif // !defined(AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_)
