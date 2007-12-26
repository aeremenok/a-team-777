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
//##ModelId=4770E207019D
class ShapeContainer
{
    // Current drawing color
	//##ModelId=4770E207019E
    COLORREF m_Color;
    // Current element type
	//##ModelId=4770E207019F
    WORD m_Element;
    // граф фигур документа
	//##ModelId=4770E20701B0
    Graph<CElement>* _container;
    //////////////////////////////////////////////////////////////////////////
    // сериализует элементы контейнера
	//##ModelId=4770E20701BC
    void serializeContainer( CArchive& ar );
    // восстанавливает из файла фигуру и ее положение в контейнере
	//##ModelId=4770E20701BE
    Shape* readShape(CArchive &ar, map<int, Shape*> &shapes);
public:
	//##ModelId=4770E20701CD
	ShapeContainer();
	//##ModelId=4770E20701CE
	virtual ~ShapeContainer();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E20701D0
    WORD GetElementType(){return m_Element;}
	//##ModelId=4770E20701D1
    void SetElementType(WORD type){m_Element = type;}

	//##ModelId=4770E20701D3
	COLORREF GetElementColor(){return m_Color;}
	//##ModelId=4770E20701DB
    void SetElementColor(COLORREF color){m_Color = color;}
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E20701DD
    CElement* AddElement(CElement* m_pElement);
	//##ModelId=4770E20701DF
    void SendToBack(CElement* pElement);
	//##ModelId=4770E20701EB
    void DeleteElement(CElement* m_pSelected);
	//##ModelId=47728C0001F4
    CElement* getElementById(int id);
	//##ModelId=4770E20701EF
    void linkElements(CElement* element1, CElement* element2);
	//##ModelId=47728C000213
    void removeRibble(CElement* element1, CElement* element2);
    //////////////////////////////////////////////////////////////////////////
    // получить новый итератор, указывающий на начало контейнера
	//##ModelId=4770E20701FB
    Iterator<CElement>* getNewIterator() const { return _container->getIterator(); }
    // получить список ребер, инцидентных выбранной вершине
	//##ModelId=4770E20701FD
    ExternalGraphIterator<CElement>* getNearestRibbles(CElement* selected);
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=4770E20701FF
    void serialize(CArchive& ar);
};

#endif // !defined(AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_)
