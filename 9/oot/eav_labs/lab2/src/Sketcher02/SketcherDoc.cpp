// SketcherDoc.cpp : implementation of the CSketcherDoc class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"

#include "resource.h"
#include "TextRequest.h"

#include "SketcherDoc.h"
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
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc construction/destruction

//##ModelId=473EDD6D02C1
CSketcherDoc::CSketcherDoc()
{
	// TODO: add one-time construction code here
   m_Element = LINE;   // Set initial element type
   m_Color = BLACK;    // Set initial drawing color
}

//##ModelId=473EDD6D02D6
CSketcherDoc::~CSketcherDoc()
{
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
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
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
   m_Element = LINE;       // Set element type as a line	
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
   pCmdUI->SetCheck(m_Element==LINE);
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
