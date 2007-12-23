// SketcherDoc.cpp : implementation of the CSketcherDoc class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"
#include "resource.h"
#include "SketcherDoc.h"

#include "TextRequest.h"
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
    _shapeContainer = new ShapeContainer();
    m_DocSize = CSize(3000,3000);  // Set initial document size 30x30 inches
}

//##ModelId=473EDD6D02D6
CSketcherDoc::~CSketcherDoc()
{
    delete _shapeContainer;
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
        ar << m_DocSize;             // and the current document size
        _shapeContainer->serialize(ar);
    }
    else
    {
        ar >> m_DocSize;             // and the current document size
        _shapeContainer->serialize(ar);
    }
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
   _shapeContainer->SetElementColor(BLACK);
}

//##ModelId=473EDD6D02E4
void CSketcherDoc::OnColorRed() 
{
    _shapeContainer->SetElementColor(RED);
}

//##ModelId=473EDD6D02E6
void CSketcherDoc::OnElementLine() 
{
   _shapeContainer->SetElementType(RIBBLE);
}

//##ModelId=473EDD6D02E8
void CSketcherDoc::OnElementRectangle() 
{
   _shapeContainer->SetElementType(RECTANGLE);
}

//##ModelId=473EDD6D02EA
void CSketcherDoc::OnUpdateColorBlack(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is black
   pCmdUI->SetCheck(_shapeContainer->GetElementColor()==BLACK);
}

//##ModelId=473EDD6D02F0
void CSketcherDoc::OnUpdateColorRed(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is red
   pCmdUI->SetCheck(_shapeContainer->GetElementColor()==RED);
}

//##ModelId=473EDD6D02F3
void CSketcherDoc::OnUpdateElementLine(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a line
   pCmdUI->SetCheck(_shapeContainer->GetElementType()==RIBBLE);
}

//##ModelId=473EDD6D02F6
void CSketcherDoc::OnUpdateElementRectangle(CCmdUI* pCmdUI) 
{
   pCmdUI->SetCheck(_shapeContainer->GetElementType()==RECTANGLE);
}

//##ModelId=474055EF0203
void CSketcherDoc::OnElementOval() 
{
	_shapeContainer->SetElementType(OVAL);
}

//##ModelId=474055EF0214
void CSketcherDoc::OnUpdateElementOval(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(_shapeContainer->GetElementType()==OVAL);
}

//##ModelId=474055EF0217
void CSketcherDoc::OnUpdateElementText(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(_shapeContainer->GetElementType()==TEXT);
}

//##ModelId=474055EF0223
void CSketcherDoc::OnElementText() 
{
    TextRequest::getTextToShow();
    _shapeContainer->SetElementType(TEXT);
}

//##ModelId=474055EF0225
void CSketcherDoc::OnElementTextInOval() 
{
    TextRequest::getTextToShow();
    _shapeContainer->SetElementType(TEXT_IN_OVAL);
}

//##ModelId=474055EF0227
void CSketcherDoc::OnUpdateElementTextInOval(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(_shapeContainer->GetElementType()==TEXT_IN_OVAL);
}

//##ModelId=475A821C002E
void CSketcherDoc::OnElementRibble() 
{
    _shapeContainer->SetElementType(RIBBLE);
}

//##ModelId=475A821C003F
void CSketcherDoc::OnUpdateElementRibble(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(_shapeContainer->GetElementType()==RIBBLE);
}


