// SketcherDoc.cpp : implementation of the CSketcherDoc class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "Elements.h"
#include "SketcherDoc.h"
#include "other\TStack.h"

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
	ON_COMMAND(ID_COLOR_RED,   OnColorRed)

	ON_COMMAND(ID_SQUARE,      OnElementSquare)
	ON_COMMAND(ID_TEXT,        OnElementText)
	ON_COMMAND(ID_SQUARE_TEXT, OnElementSquareText)
	ON_COMMAND(ID_DIAMOND,     OnElementDiamond)
	
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLACK, OnUpdateColorBlack)
	ON_UPDATE_COMMAND_UI(ID_COLOR_RED,   OnUpdateColorRed)

	ON_UPDATE_COMMAND_UI(ID_SQUARE,      OnUpdateElementSquare)
	ON_UPDATE_COMMAND_UI(ID_TEXT,        OnUpdateElementText)
	ON_UPDATE_COMMAND_UI(ID_SQUARE_TEXT, OnUpdateElementSquareText)
	ON_UPDATE_COMMAND_UI(ID_DIAMOND,     OnUpdateElementDiamond)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc construction/destruction

CSketcherDoc::CSketcherDoc()
{
	// TODO: add one-time construction code here
   m_Element = CSHAPE_SQUARE;   // Set initial element type
   m_Color = BLACK;    // Set initial drawing color
   m_DocSize = CSize(3000,3000);  // Set initial document size 30x30 inches
}

CSketcherDoc::~CSketcherDoc()
{
}

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
	m_ElementList.serialize(ar);    // Serialize the element list
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc diagnostics

#ifdef _DEBUG
void CSketcherDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CSketcherDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc commands
void CSketcherDoc::OnColorBlack() 
{
   m_Color = BLACK;        // Set the drawing color to black
}
void CSketcherDoc::OnColorRed() 
{
   m_Color = RED;          // Set the drawing color to red	
}
/////////////////////////////////////////////////////////////////////////////
void CSketcherDoc::OnElementSquare() 
{
   m_Element = CSHAPE_SQUARE;       // Set element type as a line	
}

void CSketcherDoc::OnElementText() 
{
   m_Element = CSHAPE_TEXT;  // Set element type as a rectangle	
}

void CSketcherDoc::OnElementSquareText() 
{
   m_Element = CSHAPE_TEXT_IN_SQUARE;       // Set element type as a line	
}

void CSketcherDoc::OnElementDiamond() 
{
   m_Element = CSHAPE_DIAMOND;  // Set element type as a rectangle	
}


/////////////////////////////////////////////////////////////////////////////
void CSketcherDoc::OnUpdateColorBlack(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is black
   pCmdUI->SetCheck(m_Color==BLACK);
}

void CSketcherDoc::OnUpdateColorRed(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is red
   pCmdUI->SetCheck(m_Color==RED);
}


/////////////////////////////////////////////////////////////////////////////
void CSketcherDoc::OnUpdateElementSquare(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a line
   pCmdUI->SetCheck(m_Element==CSHAPE_SQUARE);
}

void CSketcherDoc::OnUpdateElementText(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a rectangle
   pCmdUI->SetCheck(m_Element==CSHAPE_TEXT);
}
void CSketcherDoc::OnUpdateElementSquareText(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a line
   pCmdUI->SetCheck(m_Element==CSHAPE_TEXT_IN_SQUARE);
}

void CSketcherDoc::OnUpdateElementDiamond(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a rectangle
   pCmdUI->SetCheck(m_Element==CSHAPE_DIAMOND);
}


/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////
void CSketcherDoc::DeleteElement(CElement* pElement)
{
   if(pElement)
   {		  
		try
		{
			 m_ElementList.pop();
		}
		catch (StackEmptyException* e)
		{					
			AfxMessageBox("Got StackEmptyException", MB_OK);
		}
      // If the element pointer is valid,
      // find the pointer in the list and delete it
	  //POSITION aPosition = m_ElementList.Find(pElement);
      //m_ElementList.RemoveAt(aPosition);
      //delete pElement;           // Delete the element from the heap
   }
}

void CSketcherDoc::AddElement(CElement* pElement)
 { 
	try
	{
		m_ElementList.push(pElement); 
	}
	catch (StackFullException* e)
	{					
		AfxMessageBox("Stack is full!", MB_OK);
	}
}

void CSketcherDoc::SendToBack(CElement* pElement)
{
   if(pElement)
   {
	   AfxMessageBox("Not implemented for stack! :(", MB_OK);
      // If the element pointer is valid,
      // find the pointer in the list and remove the element
      //POSITION aPosition = m_ElementList.Find(pElement);
      //m_ElementList.RemoveAt(aPosition);

      //m_ElementList.AddHead(pElement);  // Put it back to the beginning
   }
}
