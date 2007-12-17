// SketcherDoc.cpp : implementation of the CSketcherDoc class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"
#include "resource.h"
#include "SketcherDoc.h"

#include "TextRequest.h"

#include "shapes/Shape.h"
#include "shapes/Rectangle.h"
#include "shapes/Oval.h"
#include "shapes/Text.h"
#include "shapes/TextInOval.h"

#include <map>
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc

IMPLEMENT_DYNCREATE(CSketcherDoc, CDocument)

BEGIN_MESSAGE_MAP(CSketcherDoc, CDocument)
	//{{AFX_MSG_MAP(CSketcherDoc)
	ON_COMMAND(ID_COLOR_BLACK, OnColorBlack)
	ON_COMMAND(ID_COLOR_RED, OnColorRed)
	ON_COMMAND(ID_ELEMENT_LINE, OnElementLine)
	ON_COMMAND(ID_ELEMENT_RECTANGLE, OnElementRectangle)
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLACK, OnUpdateColorBlack)
	ON_UPDATE_COMMAND_UI(ID_COLOR_RED, OnUpdateColorRed)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_LINE, OnUpdateElementLine)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_RECTANGLE, OnUpdateElementRectangle)
	ON_COMMAND(ID_ELEMENT_OVAL, OnElementOval)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_OVAL, OnUpdateElementOval)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_TEXT, OnUpdateElementText)
	ON_COMMAND(ID_ELEMENT_TEXT, OnElementText)
	ON_COMMAND(ID_ELEMENT_TEXT_IN_OVAL, OnElementTextInOval)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_TEXT_IN_OVAL, OnUpdateElementTextInOval)
	ON_COMMAND(ID_ELEMENT_RIBBLE, OnElementRibble)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_RIBBLE, OnUpdateElementRibble)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc construction/destruction

//##ModelId=473EDD6D02C1
CSketcherDoc::CSketcherDoc()
{
	// TODO: add one-time construction code here
   m_Element = RECTANGLE;   // Set initial element type
   m_Color = BLACK;    // Set initial drawing color
   _container = new Graph<CElement>();
   m_DocSize = CSize(3000,3000);  // Set initial document size 30x30 inches
}

//##ModelId=473EDD6D02D6
CSketcherDoc::~CSketcherDoc()
{
    _container->clear();
    _container = NULL;
}

//##ModelId=473EDD6D02D1
BOOL CSketcherDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc serialization
//##ModelId=473EDD6D02D3
void CSketcherDoc::Serialize(CArchive& ar)
{
    if (ar.IsStoring())
    {
      ar << m_Color                // Store the current color
         << m_Element              // the current element type,
         << m_DocSize;             // and the current document size
    }
    else
    {
      ar >> m_Color                // Retrieve the current color
         >> m_Element              // the current element type,
         >> m_DocSize;             // and the current document size
    }
    serializeContainer(ar);
}

//##ModelId=4751AAD80232
void CSketcherDoc::serializeContainer( CArchive& ar )
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

//##ModelId=47527CD90213
Shape* CSketcherDoc::readShape( CArchive &ar, map<int, Shape*> &shapes )
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
/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc diagnostics

#ifdef _DEBUG
//##ModelId=473EDD6D02D8
void CSketcherDoc::AssertValid() const
{
	CDocument::AssertValid();
}

//##ModelId=473EDD6D02DF
void CSketcherDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc commands

//##ModelId=473EDD6D02E2
void CSketcherDoc::OnColorBlack() 
{
   m_Color = BLACK;        // Set the drawing color to black
}

//##ModelId=473EDD6D02E4
void CSketcherDoc::OnColorRed() 
{
   m_Color = RED;          // Set the drawing color to red	
}

//##ModelId=473EDD6D02E6
void CSketcherDoc::OnElementLine() 
{
   m_Element = RIBBLE;       // Set element type as a line	
}

//##ModelId=473EDD6D02E8
void CSketcherDoc::OnElementRectangle() 
{
   m_Element = RECTANGLE;  // Set element type as a rectangle	
}

//##ModelId=473EDD6D02EA
void CSketcherDoc::OnUpdateColorBlack(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is black
   pCmdUI->SetCheck(m_Color==BLACK);
}

//##ModelId=473EDD6D02F0
void CSketcherDoc::OnUpdateColorRed(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is red
   pCmdUI->SetCheck(m_Color==RED);
}

//##ModelId=473EDD6D02F3
void CSketcherDoc::OnUpdateElementLine(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a line
   pCmdUI->SetCheck(m_Element==RIBBLE);
}

//##ModelId=473EDD6D02F6
void CSketcherDoc::OnUpdateElementRectangle(CCmdUI* pCmdUI) 
{
   pCmdUI->SetCheck(m_Element==RECTANGLE);
}

//##ModelId=474055EF0203
void CSketcherDoc::OnElementOval() 
{
	m_Element = OVAL;
}

//##ModelId=474055EF0214
void CSketcherDoc::OnUpdateElementOval(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(m_Element==OVAL);
}

//##ModelId=474055EF0217
void CSketcherDoc::OnUpdateElementText(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(m_Element==TEXT);
}

//##ModelId=474055EF0223
void CSketcherDoc::OnElementText() 
{
    TextRequest::getTextToShow();
	m_Element = TEXT;
}

//##ModelId=474055EF0225
void CSketcherDoc::OnElementTextInOval() 
{
    TextRequest::getTextToShow();
	m_Element = TEXT_IN_OVAL;
}

//##ModelId=474055EF0227
void CSketcherDoc::OnUpdateElementTextInOval(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_Element==TEXT_IN_OVAL);
}

//##ModelId=4741F10E029F
CElement* CSketcherDoc::AddElement( CElement* m_pElement )
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

//##ModelId=4741F10E02A1
void CSketcherDoc::SendToBack(CElement* pElement)
{
    if(pElement)
    {
        _container->removeVertex(pElement);
        _container->addVertex(pElement);
    }    
}

//##ModelId=4741F10E02A3
void CSketcherDoc::DeleteElement( CElement* m_pSelected )
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

//##ModelId=475A821C002E
void CSketcherDoc::OnElementRibble() 
{
    m_Element = RIBBLE;
}

//##ModelId=475A821C003F
void CSketcherDoc::OnUpdateElementRibble(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_Element == RIBBLE);
}

//##ModelId=475A8BA1032C
void CSketcherDoc::linkElements( CElement* element1, CElement* element2 )
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

//##ModelId=475AD65302CE
ExternalGraphIterator<CElement>* CSketcherDoc::getNearestRibbles( CElement* selected )
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
