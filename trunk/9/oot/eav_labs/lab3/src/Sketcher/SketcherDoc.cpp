// SketcherDoc.cpp : implementation of the CSketcherDoc class
//
#include "stdafx.h"
#include "Sketcher.h"
#include "resource.h"
#include "SketcherDoc.h"
#include "SketcherView.h"

#include "CntrItem.h"
#include "SrvrItem.h"

#include "TextRequest.h"
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc

IMPLEMENT_DYNCREATE(CSketcherDoc, COleServerDoc)

BEGIN_MESSAGE_MAP(CSketcherDoc, COleServerDoc)
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
	// Enable default OLE container implementation
	ON_UPDATE_COMMAND_UI(ID_EDIT_PASTE, COleServerDoc::OnUpdatePasteMenu)
	ON_UPDATE_COMMAND_UI(ID_EDIT_PASTE_LINK, COleServerDoc::OnUpdatePasteLinkMenu)
	ON_UPDATE_COMMAND_UI(ID_OLE_EDIT_CONVERT, COleServerDoc::OnUpdateObjectVerbMenu)
	ON_COMMAND(ID_OLE_EDIT_CONVERT, COleServerDoc::OnEditConvert)
	ON_UPDATE_COMMAND_UI(ID_OLE_EDIT_LINKS, COleServerDoc::OnUpdateEditLinksMenu)
	ON_COMMAND(ID_OLE_EDIT_LINKS, COleServerDoc::OnEditLinks)
	ON_UPDATE_COMMAND_UI_RANGE(ID_OLE_VERB_FIRST, ID_OLE_VERB_LAST, COleServerDoc::OnUpdateObjectVerbMenu)
	ON_COMMAND(ID_FILE_SEND_MAIL, OnFileSendMail)
	ON_UPDATE_COMMAND_UI(ID_FILE_SEND_MAIL, OnUpdateFileSendMail)
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(CSketcherDoc, COleServerDoc)
	//{{AFX_DISPATCH_MAP(CSketcherDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//      DO NOT EDIT what you see in these blocks of generated code!
	DISP_FUNCTION(CSketcherDoc, "deleteElement", deleteElement, VT_BOOL, VTS_BSTR)
	DISP_FUNCTION(CSketcherDoc, "showWindow", showWindow, VT_EMPTY, VTS_NONE)
	//DISP_FUNCTION(CSketcherDoc, "DrawLine", DrawLine, VT_EMPTY, VTS_R4 VTS_R4 VTS_R4 VTS_R4 VTS_BSTR)
	//}}AFX_DISPATCH_MAP
END_DISPATCH_MAP()

// Note: we add support for IID_ISketcher to support typesafe binding
//  from VBA.  This IID must match the GUID that is attached to the 
//  dispinterface in the .ODL file.

// {209B83CE-2FA3-4A8C-9C22-869FF0BA2652}
static const IID IID_ISketcher =
{ 0x209b83ce, 0x2fa3, 0x4a8c, { 0x9c, 0x22, 0x86, 0x9f, 0xf0, 0xba, 0x26, 0x52 } };

BEGIN_INTERFACE_MAP(CSketcherDoc, COleServerDoc)
	INTERFACE_PART(CSketcherDoc, IID_ISketcher, Dispatch)
END_INTERFACE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc construction/destruction

//##ModelId=4770E2060354
CSketcherDoc::CSketcherDoc()
{
	// Use OLE compound files
	EnableCompoundFile();
	
    m_DocSize = CSize(800,600);  // Set initial document size 30x30 inches
	// TODO: add one-time construction code here
    _shapeContainer = new ShapeContainer();
	EnableAutomation();

	AfxOleLockApp();
}

//##ModelId=4770E2060391
CSketcherDoc::~CSketcherDoc()
{
    delete _shapeContainer;
	AfxOleUnlockApp();
}

//##ModelId=4770E2060375
BOOL CSketcherDoc::OnNewDocument()
{
	if (!COleServerDoc::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc server implementation
//##ModelId=4770E2060387
COleServerItem* CSketcherDoc::OnGetEmbeddedItem()
{
	// OnGetEmbeddedItem is called by the framework to get the COleServerItem
	//  that is associated with the document.  It is only called when necessary.

	CSketcherSrvrItem* pItem = new CSketcherSrvrItem(this);
	ASSERT_VALID(pItem);
	return pItem;
}
/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc Active Document server implementation

//##ModelId=4770E2060398
CDocObjectServer *CSketcherDoc::GetDocObjectServer(LPOLEDOCUMENTSITE pDocSite)
{
	return new CDocObjectServer(this, pDocSite);
}


/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc serialization

//##ModelId=4770E2060377
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

	// Calling the base class COleServerDoc enables serialization
	//  of the container document's COleClientItem objects.
	COleServerDoc::Serialize(ar);
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc diagnostics

#ifdef _DEBUG
//##ModelId=4770E2060393
void CSketcherDoc::AssertValid() const
{
	COleServerDoc::AssertValid();
}

//##ModelId=4770E2060395
void CSketcherDoc::Dump(CDumpContext& dc) const
{
	COleServerDoc::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc commands

//##ModelId=4770E20603A1
void CSketcherDoc::OnColorBlack() 
{
   _shapeContainer->SetElementColor(BLACK);
}

//##ModelId=4770E20603A3
void CSketcherDoc::OnColorRed() 
{
    _shapeContainer->SetElementColor(RED);
}

//##ModelId=4770E20603A5
void CSketcherDoc::OnElementLine() 
{
   _shapeContainer->SetElementType(RIBBLE);
}

//##ModelId=4770E20603A7
void CSketcherDoc::OnElementRectangle() 
{
   _shapeContainer->SetElementType(RECTANGLE);
}

//##ModelId=4770E20603B1
void CSketcherDoc::OnUpdateColorBlack(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is black
   pCmdUI->SetCheck(_shapeContainer->GetElementColor()==BLACK);
}

//##ModelId=4770E20603B4
void CSketcherDoc::OnUpdateColorRed(CCmdUI* pCmdUI) 
{
   // Set menu item Checked if the current color is red
   pCmdUI->SetCheck(_shapeContainer->GetElementColor()==RED);
}

//##ModelId=4770E20603C0
void CSketcherDoc::OnUpdateElementLine(CCmdUI* pCmdUI) 
{
   // Set Checked if the current element is a line
   pCmdUI->SetCheck(_shapeContainer->GetElementType()==RIBBLE);
}

//##ModelId=4770E20603C3
void CSketcherDoc::OnUpdateElementRectangle(CCmdUI* pCmdUI) 
{
   pCmdUI->SetCheck(_shapeContainer->GetElementType()==RECTANGLE);
}

//##ModelId=4770E20603CF
void CSketcherDoc::OnElementOval() 
{
	_shapeContainer->SetElementType(OVAL);
}

//##ModelId=4770E20603D1
void CSketcherDoc::OnUpdateElementOval(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(_shapeContainer->GetElementType()==OVAL);
}

//##ModelId=4770E20603D4
void CSketcherDoc::OnUpdateElementText(CCmdUI* pCmdUI) 
{
    pCmdUI->SetCheck(_shapeContainer->GetElementType()==TEXT);
}


// Get the rectangle enclosing the entire document
//##ModelId=476D9BD500FD
CRect CSketcherDoc::GetDocExtent()
{
	CRect DocExtent(0,0,1,1);    // Initial document extent
	CRect ElementBound(0,0,0,0); // Space for element bounding rectangle

    Iterator<CElement>* iter = getShapeContainer()->getNewIterator();
    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();
        // Get the bounding rectangle for the element
        ElementBound = ribble->get__vertex1()->GetBoundRect();
        // Make coordinates of document extent the outer limits
		DocExtent.UnionRect(DocExtent, ElementBound);

        // Get the bounding rectangle for the element
        ElementBound = ribble->get__vertex2()->GetBoundRect();
        // Make coordinates of document extent the outer limits
		DocExtent.UnionRect(DocExtent, ElementBound);
    }

	DocExtent.NormalizeRect();
	return DocExtent;
}

//##ModelId=4770E2060381
BOOL CSketcherDoc::OnUpdateDocument() 
{
	// TODO: Add your specialized code here and/or call the base class
	return COleServerDoc::OnUpdateDocument();
}

//##ModelId=4770E207000C
BOOL CSketcherDoc::deleteElement(LPCTSTR key) 
{
	// TODO: Add your dispatch handler code here
    getShapeContainer()->DeleteElement(key);
	UpdateAllViews(NULL);
	SetModifiedFlag();
	return TRUE;
}

//##ModelId=4770E2070017
void CSketcherDoc::showWindow() 
{
	POSITION pos = GetFirstViewPosition();
	CView* pView = GetNextView(pos);
	if (pView != NULL)
	{
		CFrameWnd* pFrameWnd = pView->GetParentFrame();
		pFrameWnd->ActivateFrame(SW_SHOW);
		pFrameWnd = pFrameWnd->GetParentFrame();
		if (pFrameWnd != NULL)
			pFrameWnd->ActivateFrame(SW_SHOW);
	}
}

/*
void CSketcherDoc::DrawLine(float x1, float y1, float x2, float y2, LPCTSTR key) 
{
	CElement* line = new CLine(CPoint(x1, y1), CPoint(x2, y2), GetElementColor());
	AddElement(line, key);
	UpdateAllViews(NULL);
	SetModifiedFlag();
}
*/

//##ModelId=4770E2060383
void CSketcherDoc::OnSetItemRects(LPCRECT lpPosRect, LPCRECT lpClipRect) 
{
	COleServerDoc::OnSetItemRects(lpPosRect, lpClipRect);

	// notify first view that scroll info should change
	POSITION pos = GetFirstViewPosition();
	CSketcherView* v = (CSketcherView*)GetNextView(pos);
	v->ResetScrollSizes();
}


//##ModelId=4770E20603DF
void CSketcherDoc::OnElementText() 
{
    TextRequest::getTextToShow();
    _shapeContainer->SetElementType(TEXT);
}

//##ModelId=4770E20603E1
void CSketcherDoc::OnElementTextInOval() 
{
    TextRequest::getTextToShow();
    _shapeContainer->SetElementType(TEXT_IN_OVAL);
}

//##ModelId=4770E20603E3
void CSketcherDoc::OnUpdateElementTextInOval(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(_shapeContainer->GetElementType()==TEXT_IN_OVAL);
}

//##ModelId=4770E2070007
void CSketcherDoc::OnElementRibble() 
{
    _shapeContainer->SetElementType(RIBBLE);
}

//##ModelId=4770E2070009
void CSketcherDoc::OnUpdateElementRibble(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(_shapeContainer->GetElementType()==RIBBLE);
}


