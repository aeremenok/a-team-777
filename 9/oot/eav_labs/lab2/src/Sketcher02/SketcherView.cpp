// SketcherView.cpp : implementation of the CSketcherView class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "Elements.h"
#include "SketcherDoc.h"
#include "SketcherView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherView

IMPLEMENT_DYNCREATE(CSketcherView, CView)

BEGIN_MESSAGE_MAP(CSketcherView, CView)
	//{{AFX_MSG_MAP(CSketcherView)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherView construction/destruction

//##ModelId=473EDD6D01C6
CSketcherView::CSketcherView()
{
    m_FirstPoint = CPoint(0,0);         // Set 1st recorded point to 0,0
    m_SecondPoint = CPoint(0,0);        // Set 2nd recorded point to 0,0
    m_pTempElement = 0;                 // Set temporary element pointer to 0
}

//##ModelId=473EDD6D0273
CSketcherView::~CSketcherView()
{
}

//##ModelId=473EDD6D0246
BOOL CSketcherView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView drawing

//##ModelId=473EDD6D0243
void CSketcherView::OnDraw(CDC* pDC)
{
	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	// TODO: add draw code for native data here
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView printing

//##ModelId=473EDD6D0253
BOOL CSketcherView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

//##ModelId=473EDD6D0261
void CSketcherView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

//##ModelId=473EDD6D0265
void CSketcherView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView diagnostics

#ifdef _DEBUG
//##ModelId=473EDD6D0275
void CSketcherView::AssertValid() const
{
	CView::AssertValid();
}

//##ModelId=473EDD6D0280
void CSketcherView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

//##ModelId=473EDD6D01D4
CSketcherDoc* CSketcherView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CSketcherDoc)));
	return (CSketcherDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherView message handlers

//##ModelId=473EDD6D0283
void CSketcherView::OnLButtonDown(UINT nFlags, CPoint point) 
{
	m_FirstPoint = point;               // Record the cursor position
    SetCapture();                       // Capture subsequent mouse messages
	CView::OnLButtonDown(nFlags, point);
}

//##ModelId=473EDD6D0291
void CSketcherView::OnLButtonUp(UINT nFlags, CPoint point) 
{
	if(this == GetCapture())
		ReleaseCapture();        // Stop capturing mouse messages

	// Make sure there is an element
	if(m_pTempElement)
	{  
		// Call a document class function to store the element
		// pointed to by m_pTempElement in the document object

		delete m_pTempElement;   // This code is temporary
		m_pTempElement = 0;      // Reset the element pointer
	}
	CView::OnLButtonUp(nFlags, point);
}

//##ModelId=473EDD6D029F
void CSketcherView::OnMouseMove(UINT nFlags, CPoint point) 
{
	// Define a Device Context object for the view
	CClientDC aDC(this);
	aDC.SetROP2(R2_NOTXORPEN);  // Set the drawing mode
	if((nFlags & MK_LBUTTON) && (this == GetCapture()))
	{
		m_SecondPoint = point;     // Save the current cursor position

		if(m_pTempElement)
		{
			// Redraw the old element so it disappears from the view
			m_pTempElement->Draw(&aDC);
			delete m_pTempElement;        // Delete the old element
			m_pTempElement = 0;           // Reset the pointer to 0
		}

		// Create a temporary element of the type and color that
		// is recorded in the document object, and draw it
		m_pTempElement = CreateElement();  // Create a new element
		m_pTempElement->Draw(&aDC);        // Draw the element
	}
	CView::OnMouseMove(nFlags, point);
}

//##ModelId=473EDD6D0242
CElement* CSketcherView::CreateElement()
{
	// Get a pointer to the document for this view
	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);                  // Verify the pointer is good

	// Now select the element using the type stored in the document
	switch(pDoc->GetElementType())
	{
		case RECTANGLE:
			return new CRectangle(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());
      
		case LINE:                  
			return new CLine(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());

		default:
			//	Something's gone wrong
			AfxMessageBox("Bad Element code", MB_OK);
			AfxAbort();
			return NULL;
	}
}
