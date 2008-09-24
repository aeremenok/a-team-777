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
//##ModelId=476EA08D008C
class ShapeContainer
{
    // Current drawing color
	//##ModelId=476EA08D008D
    COLORREF m_Color;
    // Current element type
	//##ModelId=476EA08D008E
    WORD m_Element;
    // граф фигур документа
	//##ModelId=476EA08D009D
    Graph<CElement>* _container;
    //////////////////////////////////////////////////////////////////////////
    // сериализует элементы контейнера
	//##ModelId=476EA08D00A1
    void serializeContainer( CArchive& ar );
    // восстанавливает из файла фигуру и ее положение в контейнере
	//##ModelId=476EA08D00A3
    Shape* readShape(CArchive &ar, map<int, Shape*> &shapes);
public:
	//##ModelId=476EA08D00AC
	ShapeContainer();
	//##ModelId=476EA08D00AD
	virtual ~ShapeContainer();
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D00AF
    WORD GetElementType(){return m_Element;}
	//##ModelId=476EA08D00B0
    void SetElementType(WORD type){m_Element = type;}

	//##ModelId=476EA08D00B2
	COLORREF GetElementColor(){return m_Color;}
	//##ModelId=476EA08D00B3
    void SetElementColor(COLORREF color){m_Color = color;}
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D00B5
    CElement* AddElement(CElement* m_pElement);
	//##ModelId=476EA08D00BB
    void SendToBack(CElement* pElement);
	//##ModelId=476EA08D00BD
    void DeleteElement(CElement* m_pSelected);

	//##ModelId=476EA08D00BF
    void linkElements(CElement* element1, CElement* element2);
    //////////////////////////////////////////////////////////////////////////
    // получить новый итератор, указавющий на начало контейнера
	//##ModelId=476EA08D00C2
    Iterator<CElement>* getNewIterator() const { return _container->getIterator(); }
    // получить список ребер, инцидентных выбранной вершине
	//##ModelId=476EA08D00C4
    ExternalGraphIterator<CElement>* getNearestRibbles(CElement* selected);
    //////////////////////////////////////////////////////////////////////////
	//##ModelId=476EA08D00CB
    void serialize(CArchive& ar);
};

#endif // !defined(AFX_SHAPECONTAINER_H__7D5486FF_8023_4CB2_B9FA_823898A1951F__INCLUDED_)
