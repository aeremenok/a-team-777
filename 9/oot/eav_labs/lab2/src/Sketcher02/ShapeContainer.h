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
// обертка контейнера для переносимости
class ShapeContainer
{
    // Current drawing color
    COLORREF m_Color;
    // Current element type
    WORD m_Element;
    // граф фигур документа
    Graph<CElement>* _container;
    //////////////////////////////////////////////////////////////////////////
    // сериализует элементы контейнера
    void serializeContainer( CArchive& ar );
    // восстанавливает из файла фигуру и ее положение в контейнере
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
    // получить новый итератор, указавющий на начало контейнера
    Iterator<CElement>* getNewIterator() const { return _container->getIterator(); }
    // получить список ребер, инцидентных выбранной вершине
    ExternalGraphIterator<CElement>* getNearestRibbles(CElement* selected);
    //////////////////////////////////////////////////////////////////////////
    void serialize(CArchive& ar);
};

#endif // !defined(AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_)
