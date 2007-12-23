// SketcherView.cpp : implementation of the CSketcherView class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"

#include "ChildFrm.h"
#include "ScaleDialog.h"

#include "SketcherDoc.h"
#include "SketcherView.h"
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherView

IMPLEMENT_DYNCREATE(CSketcherView, CScrollView)

BEGIN_MESSAGE_MAP(CSketcherView, CScrollView)
	//{{AFX_MSG_MAP(CSketcherView)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	ON_WM_RBUTTONDOWN()
	ON_WM_RBUTTONUP()
	ON_COMMAND(ID_MOVE, OnMove)
	ON_COMMAND(ID_SENDTOBACK, OnSendtoback)
	ON_COMMAND(ID_DELETE, OnDelete)
	ON_COMMAND(ID_ELEMENT_DRAWRIBBLES, OnElementDrawribbles)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_DRAWRIBBLES, OnUpdateElementDrawribbles)
	ON_COMMAND(ID_NOELEMENT_SCALE, OnNoelementScale)
	ON_UPDATE_COMMAND_UI(ID_NOELEMENT_SCALE, OnUpdateNoelementScale)
	ON_COMMAND(ID_ELEMENT_SCALE, OnElementScale)
	ON_UPDATE_COMMAND_UI(ID_ELEMENT_SCALE, OnUpdateElementScale)
	ON_WM_KEYDOWN()
	ON_WM_KEYUP()
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
    _handler = new ShapeHandler(this);
    SetScrollSizes(MM_TEXT, CSize(0,0));  // Set arbitrary scrollers
}

//##ModelId=473EDD6D0273
CSketcherView::~CSketcherView()
{
    delete _handler;
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
    _handler->onDraw(pDC);
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
    CClientDC aDC(this);                // Create a device context
    OnPrepareDC(&aDC);                  // Get origin adjusted
    
    _handler->onLBDown(aDC, point);
}

//##ModelId=473EDD6D0291
void CSketcherView::OnLButtonUp(UINT nFlags, CPoint point) 
{
    if(this == GetCapture())
        ReleaseCapture();        // Stop capturing mouse messages

    CClientDC aDC(this);
    OnPrepareDC(&aDC);
    _handler->onLBUp(aDC, point);
}

//##ModelId=473EDD6D029F
void CSketcherView::OnMouseMove(UINT nFlags, CPoint point) 
{
    _handler->onMMove(point, (nFlags & MK_LBUTTON) );
}

//##ModelId=4741F10E0234
void CSketcherView::OnRButtonDown(UINT nFlags, CPoint point) 
{
    _handler->onRBDown(point);
}

//##ModelId=4741F10E0244
void CSketcherView::OnRButtonUp(UINT nFlags, CPoint point) 
{
    _handler->onRBUp(point);
}

//##ModelId=4741F10E0225
void CSketcherView::OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint) 
{
    // Invalidate the area corresponding to the element pointed to
    // if there is one, otherwise invalidate the whole client area
    if(pHint)
    {
        CClientDC aDC(this);            // Create a device context
        OnPrepareDC(&aDC);              // Get origin adjusted
        
        // Get the enclosing rectangle and convert to client coordinates
        CRect aRect=((CElement*)pHint)->GetBoundRect();
        aDC.LPtoDP(aRect);
        aRect.NormalizeRect();
        InvalidateRect(aRect);          // Get the area redrawn
    }
    else
        InvalidateRect(0);
}

//##ModelId=4741F10E0223
void CSketcherView::OnInitialUpdate() 
{
    ResetScrollSizes();
    CScrollView::OnInitialUpdate();
}

//##ModelId=4741F10E0251
void CSketcherView::OnMove() 
{
    _handler->onMove();
}

//##ModelId=4741F10E0253
void CSketcherView::OnSendtoback() 
{
   // Move element in list
   GetDocument()->getShapeContainer()->SendToBack(_handler->Selected());
   Invalidate();
}

//##ModelId=4741F10E0255
void CSketcherView::OnDelete() 
{
   _handler->onDelete();
}

//##ModelId=47511BBE037A
void CSketcherView::OnElementDrawribbles() 
{
	_handler->IsGraphVisible(!_handler->IsGraphVisible());
    Invalidate();
}

//##ModelId=47511BBE037C
void CSketcherView::OnUpdateElementDrawribbles(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(_handler->IsGraphVisible() == true);
}

//##ModelId=475168590203
void CSketcherView::OnNoelementScale() 
{
    requestScale();
}

//##ModelId=475168590213
void CSketcherView::OnUpdateNoelementScale(CCmdUI* pCmdUI){}

//##ModelId=475168590216
void CSketcherView::OnElementScale() 
{
	requestScale();
}

//##ModelId=475168590218
void CSketcherView::OnUpdateElementScale(CCmdUI* pCmdUI)
{
}

//##ModelId=4751685901F4
void CSketcherView::ResetScrollSizes()
{
    CClientDC aDC(this);
    OnPrepareDC(&aDC);                            // Set up the device context
    CSize DocSize = GetDocument()->GetDocSize();  // Get the document size
    aDC.LPtoDP(&DocSize);                         // Get the size in pixels
    SetScrollSizes(MM_TEXT, DocSize);             // Set up the scrollbars
}

//##ModelId=4751685901F5
void CSketcherView::requestScale()
{
    CScaleDialog aDlg;            // Create a dialog object
    aDlg.m_Scale = _handler->Scale();
    if(aDlg.DoModal() ==  IDOK)
    {
        _handler->Scale(aDlg.m_Scale);    // Get the new scale

        // Get the frame window for this view
        CChildFrame* viewFrame = (CChildFrame*)GetParentFrame();

        // Build the message string
        CString StatusMsg("View Scale:");
        StatusMsg += (char)('0' + _handler->Scale());

        // Write the string to the status bar
        viewFrame->m_StatusBar.GetStatusBarCtrl().SetText(StatusMsg, 0, 0);
        ResetScrollSizes();        // Adjust scrolling to the new scale
        InvalidateRect(0);         // Invalidate the whole window
    }
}

//##ModelId=4751685901F6
void CSketcherView::OnPrepareDC(CDC* pDC, CPrintInfo* pInfo)
{
    CScrollView::OnPrepareDC(pDC, pInfo);
    CSketcherDoc* pDoc = GetDocument();
    pDC->SetMapMode(MM_ANISOTROPIC);           // Set the map mode
    CSize DocSize = pDoc->GetDocSize();        // Get the document size

    // y extent must be negative because we want MM_LOENGLISH
    DocSize.cy = -DocSize.cy;                  // Change sign of y
    pDC->SetWindowExt(DocSize);                // Now set the window extent

    // Get the number of pixels per inch in x and y
    int xLogPixels = pDC->GetDeviceCaps(LOGPIXELSX);
    int yLogPixels = pDC->GetDeviceCaps(LOGPIXELSY);

    // Calculate the viewport extent in x and y
    long xExtent = (long)DocSize.cx * _handler->Scale() * xLogPixels/100L;
    long yExtent = (long)DocSize.cy * _handler->Scale() * yLogPixels/100L;

    pDC->SetViewportExt((int)xExtent, (int)-yExtent); // Set viewport extent
}

//##ModelId=47532663035B
void CSketcherView::OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
    switch(nChar)
    {
        case 38: // up
                 // бегаем по ребрам вперед
            _handler->changeRibble(true);
            break;
        case 40: // down
                 // бегаем по ребрам назад
            _handler->changeRibble(false);
            break;
        case 37: // left
            break;
        case 39: // right
                 // перемещаемся на новую вершину
            _handler->changeVertex();
            break;
        default:
            break;
    }
    CScrollView::OnKeyDown(nChar, nRepCnt, nFlags);
}

//##ModelId=47532663036B
void CSketcherView::OnKeyUp(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
	CScrollView::OnKeyUp(nChar, nRepCnt, nFlags);
}
