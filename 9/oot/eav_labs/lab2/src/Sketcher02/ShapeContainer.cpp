// ShapeContainer.cpp: implementation of the ShapeContainer class.
//
//////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"
#include "ShapeContainer.h"
//////////////////////////////////////////////////////////////////////////
#include "shapes/Shape.h"
#include "shapes/Rectangle.h"
#include "shapes/Oval.h"
#include "shapes/Text.h"
#include "shapes/TextInOval.h"

#include <map>
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

ShapeContainer::ShapeContainer()
{
    m_Element = RECTANGLE;   // Set initial element type
    m_Color = BLACK;    // Set initial drawing color
   _container = new Graph<CElement>();
}

ShapeContainer::~ShapeContainer()
{
    _container->clear();
    _container = NULL;
}

void ShapeContainer::serializeContainer( CArchive& ar )
{
    if (ar.IsStoring())
    {
        ar << _container->getRibbleCount();
        
        Iterator<CElement>* iter = getNewIterator();
        while (iter->hasNext())
        {
            Ribble<CElement>* current = iter->next();
            // на NULL не проверяем, т.к. при нулевых вершинах
            //  восстановление графа чрезвычайно сложно. 
            //  считаем такую ситуацию недопустимой!
            Shape* 
                vertex = (Shape*)current->get__vertex1(); 
            
            ar << vertex->get__id();
            ar << vertex->getType();
            vertex->Serialize(ar);
            
            vertex = (Shape*)current->get__vertex2();
            
            ar << vertex->get__id();
            ar << vertex->getType();
            vertex->Serialize(ar);
        }
    } 
    else
    {
        _container->clear();
        int count;
        using namespace std;
        map<int, Shape*> shapes;
        // получаем количество ребер
        ar >> count;
        while (count--)
        {
            try
            {
                _container->addRibble(
                    readShape(ar, shapes),
                    readShape(ar, shapes)
                    );
            }
            catch (CException* e1) 
            {
                e1->ReportError();
            }
            catch (GraphException* e)
            {
                AfxMessageBox(e->getException().c_str());
            }
        }
    }
}

Shape* ShapeContainer::readShape( CArchive &ar, map<int, Shape*> &shapes )
{
    int id;
    ar >> id;
    
    int shapeType;
    ar >> shapeType;
    
    // проверяем, нет ли уже такой фигуры в контейнере
    Shape* toAdd = shapes[id];
    
    if (toAdd == NULL)
    { // фигуры нет, создаем новую
        switch(shapeType)
        {
        case RECTANGLE:
            toAdd = Rectangle2::create();
            break;
        case OVAL:
            toAdd = Oval::create();
            break;
        case TEXT:
            toAdd = Text::create();
            break;
        case TEXT_IN_OVAL:
            toAdd = TextInOval::create();
            break;
        default:
            AfxMessageBox("Cannot read shape type, bad source file!");
            return NULL;
            break;
        }
        toAdd->Serialize(ar);
        // запоминаем созданную фигуру по идентификатору,
        //  чтобы, если потребуется, создать еще ребра с ней
        shapes[id] = toAdd;
    }
    else
    {
        toAdd->Serialize(ar);
    }
    return toAdd;
}

void ShapeContainer::serialize( CArchive& ar )
{
    if (ar.IsStoring())
    {
        ar << m_Color                // Store the current color
           << m_Element;              // the current element type,    
    } 
    else
    {
        ar >> m_Color                // Retrieve the current color
           >> m_Element;              // the current element type,
    }
    serializeContainer(ar);
}

CElement* ShapeContainer::AddElement( CElement* m_pElement )
{
    if (GetElementType()!=RIBBLE)
    {
        try
        {
            _container->addRibble(m_pElement, m_pElement);
        }
        catch (GraphException* e)
        {
            AfxMessageBox(e->getException().c_str());
        }
    }
    // todo убрать
    return NULL;
}

void ShapeContainer::SendToBack( CElement* pElement )
{
    if(pElement)
    {
        _container->removeVertex(pElement);
        _container->addVertex(pElement);
    }
}

void ShapeContainer::DeleteElement( CElement* m_pSelected )
{
    try
    {
        _container->removeVertex(m_pSelected);
    }
    catch (GraphException* e)
    {
        AfxMessageBox(e->getException().c_str());
    }
}

void ShapeContainer::linkElements( CElement* element1, CElement* element2 )
{
    try
    {
        _container->linkVertices(element1, element2);
    }
    catch (GraphException* e)
    {
        try{
            AfxMessageBox(e->getException().c_str());
        } catch (...){}
    }
}

ExternalGraphIterator<CElement>* ShapeContainer::getNearestRibbles( CElement* selected )
{
    try
    {
        return _container->getNearestRibbles(selected); 
    }
    catch (GraphException* e)
    {
        AfxMessageBox(e->getException().c_str());
    }
}