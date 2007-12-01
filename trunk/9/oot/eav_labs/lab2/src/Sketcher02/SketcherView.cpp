// SketcherView.cpp : implementation of the CSketcherView class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"

#include "Elements.h"

#include "shapes/Rectangle.h"
#include "shapes/Text.h"
#include "shapes/Oval.h"
#include "shapes/TextInOval.h"

#include "container/Iterator.h"

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
    m_pSelected = 0;                    // No element selected initially
    m_MoveMode = FALSE;                 // Set move mode off
    m_CursorPos = CPoint(0,0);          // Initialize as zero
    m_FirstPos = CPoint(0,0);           // Initialize as zero
    isGraphVisible = false;
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
    
    CElement* pElement = NULL;
    Iterator<CElement>* iter = pDoc->getGraphIterator();

    CPoint* start = NULL;
    CPoint* end = NULL;

    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();

        pElement = ribble->get__vertex1();
        if(pDC->RectVisible(pElement->GetBoundRect()))
        {
            pElement->Draw(pDC, m_pSelected);
            start = &(pElement->GetBoundRect().CenterPoint());
        }

        pElement = ribble->get__vertex2();
        if(pDC->RectVisible(pElement->GetBoundRect()))
        {
            pElement->Draw(pDC, m_pSelected);
            end = &(pElement->GetBoundRect().CenterPoint());
        }

        drawRibble(start, end, pDC);
    }
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
   aDC.DPtoLP(&point);                 // convert point to Logical

   if(m_MoveMode)
   {
      // In moving mode, so drop the element
      m_MoveMode = FALSE;                 // Kill move mode
      m_pSelected = 0;                    // De-select the element
      GetDocument()->UpdateAllViews(0);   // Redraw all the views
   }
   else
   {
      m_FirstPoint = point;               // Record the cursor position
      SetCapture();                       // Capture subsequent mouse messages
   }
}

//##ModelId=473EDD6D0291
void CSketcherView::OnLButtonUp(UINT nFlags, CPoint point) 
{
   if(this == GetCapture())
      ReleaseCapture();        // Stop capturing mouse messages

   // If there is an element, add it to the document
   if(m_pTempElement)
   {  
      CClientDC aDC(this);
      OnPrepareDC(&aDC);

      CElement* firstElement = GetDocument()->AddElement(m_pTempElement);
      drawRibble(firstElement, m_pTempElement, &aDC);

      GetDocument()->UpdateAllViews(0,0,m_pTempElement);  // Tell all the views
      m_pTempElement = 0;        // Reset the element pointer
   }
}

//##ModelId=473EDD6D029F
void CSketcherView::OnMouseMove(UINT nFlags, CPoint point) 
{
   // Define a Device Context object for the view
   CClientDC aDC(this);
   OnPrepareDC(&aDC);            // Get origin adjusted

   if(m_MoveMode)
   { // передвигаем фигуру
      aDC.DPtoLP(&point);        // Convert to logical coordinatess
      MoveElement(aDC, point);   // Move the element
   }
   else if((nFlags & MK_LBUTTON) && (this == GetCapture()))
   { // рисуем фигуру
      aDC.DPtoLP(&point);
      m_SecondPoint = point;     // Save the current cursor position

      if(m_pTempElement)
      { // фигура есть
         aDC.SetROP2(R2_NOTXORPEN);      // Set drawing mode
         m_pTempElement->Draw(&aDC);  
         m_pTempElement->resize(m_FirstPoint, m_SecondPoint);
      }
      else
      { // фигуры нет
         m_pTempElement = CreateElement();
      }
      m_pTempElement->Draw(&aDC);  
   }
   else
   { // ничего не двигаем и не рисуем, только подсвечиваем
      CRect aRect;
      CElement* pCurrentSelection = SelectElement(point);

      if(pCurrentSelection != m_pSelected)
      {
         if(m_pSelected)             // Old elemented selected?
         {                           // Yes, so draw it unselected
            aRect = m_pSelected->GetBoundRect(); // Get bounding rectangle
            aDC.LPtoDP(aRect);                   // Conv to device coords
            aRect.NormalizeRect();               // Normalize
            InvalidateRect(aRect, FALSE);        // Invalidate area
         }
         m_pSelected = pCurrentSelection;        // Save elem under cursor
         if(m_pSelected)                         // Is there one?
         {                                       // Yes, so get it redrawn
            aRect = m_pSelected->GetBoundRect(); // Get bounding rectangle
            aDC.LPtoDP(aRect);                   // Conv to device coords
            aRect.NormalizeRect();               // Normalize
            InvalidateRect(aRect, FALSE);        // Invalidate area
         }
      }
   }
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
            return Rectangle2::create(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());
      
        case TEXT:
            return Text::create(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());

        case OVAL:
            return Oval::create(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());

        case TEXT_IN_OVAL:
            return TextInOval::create(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());

        //////////////////////////////////////////////////////////////////////////
		case LINE:                  
			return new CLine(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());

		default:
			//	Something's gone wrong
			AfxMessageBox("Bad Element code", MB_OK);
			AfxAbort();
			return NULL;
	}
}

// Find the element at the cursor
//##ModelId=4741F10E0213
CElement* CSketcherView::SelectElement(CPoint aPoint)
{
    // Convert parameter aPoint to logical coordinates
    CClientDC aDC(this);
    OnPrepareDC(&aDC);
    aDC.DPtoLP(&aPoint);

    CSketcherDoc* pDoc=GetDocument();      // Get a pointer to the document
    CElement* pElement = NULL;             // Store an element pointer
    CRect aRect(0,0,0,0);                  // Store a rectangle

    Iterator<CElement>* iter = pDoc->getGraphIterator();
    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();

        pElement = ribble->get__vertex1();
        aRect = pElement->GetBoundRect();
        if(aRect.PtInRect(aPoint))
            return pElement;
        pElement = ribble->get__vertex2();
        aRect = pElement->GetBoundRect();
        if(aRect.PtInRect(aPoint))
            return pElement;
    }

    return NULL;                              // No element found
}

//##ModelId=4741F10E0215
void CSketcherView::MoveElement(CClientDC& aDC, CPoint& point)
{
   CSize Distance = point - m_CursorPos;   // Get move distance
   m_CursorPos = point;          // Set current point as 1st for next time

   // If there is an element, selected, move it
   if(m_pSelected)
   {
      aDC.SetROP2(R2_NOTXORPEN);
      m_pSelected->Draw(&aDC,m_pSelected); // Draw the element to erase it
      m_pSelected->Move(Distance);         // Now move the element
      m_pSelected->Draw(&aDC,m_pSelected); // Draw the moved element
   }
}

//##ModelId=4741F10E0234
void CSketcherView::OnRButtonDown(UINT nFlags, CPoint point) 
{
   if(m_MoveMode)
   {
      // In moving mode, so drop element back in original position
      CClientDC aDC(this);
      OnPrepareDC(&aDC);                  // Get origin adjusted
      MoveElement(aDC, m_FirstPos);       // Move element to orig position
      m_MoveMode = FALSE;                 // Kill move mode
      m_pSelected = 0;                    // De-select element
      GetDocument()->UpdateAllViews(0);   // Redraw all the views
      return;                             // We are done
   }
}


//##ModelId=4741F10E0244
void CSketcherView::OnRButtonUp(UINT nFlags, CPoint point) 
{
// Create the cursor menu
   CMenu aMenu;
   aMenu.LoadMenu(IDR_CURSOR_MENU);    // Load the cursor menu
   ClientToScreen(&point);             // Convert to screen coordinates

   // Display the pop-up at the cursor position
   if(m_pSelected)
   {
      aMenu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON,
                                                  point.x, point.y, this);
   }
   else
   {
      // Check color menu items
      COLORREF Color = GetDocument()->GetElementColor();
      aMenu.CheckMenuItem(ID_COLOR_BLACK,
                     (BLACK==Color?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
      aMenu.CheckMenuItem(ID_COLOR_RED,
                       (RED==Color?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);

      // Check element menu items
      WORD ElementType = GetDocument()->GetElementType();
      aMenu.CheckMenuItem(ID_ELEMENT_LINE,
                (LINE==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
      aMenu.CheckMenuItem(ID_ELEMENT_RECTANGLE,
           (RECTANGLE==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);

      // Display the context pop-up
      aMenu.GetSubMenu(1)->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON, point.x, point.y, this);
   }
   
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
   CScrollView::OnInitialUpdate();
	
   // Define document size as 30x30ins in MM_LOENGLISH
   CSize DocSize(3000,3000);

   // Set mapping mode and document size.
   SetScrollSizes(MM_LOENGLISH, DocSize);
}

//##ModelId=4741F10E0251
void CSketcherView::OnMove() 
{
   CClientDC aDC(this);
   OnPrepareDC(&aDC);              // Set up the device context
   GetCursorPos(&m_CursorPos);     // Get cursor position in screen coords
   ScreenToClient(&m_CursorPos);   // Convert to client coords
   aDC.DPtoLP(&m_CursorPos);       // Convert to logical
   m_FirstPos = m_CursorPos;       // Remember first position
   m_MoveMode = TRUE;              // Start move mode
	
}

//##ModelId=4741F10E0253
void CSketcherView::OnSendtoback() 
{
   GetDocument()->SendToBack(m_pSelected);  // Move element in list	
}

//##ModelId=4741F10E0255
void CSketcherView::OnDelete() 
{
   if(m_pSelected)
   {
      CSketcherDoc* pDoc = GetDocument();  // Get the document pointer
      pDoc->DeleteElement(m_pSelected);    // Delete the element
      pDoc->UpdateAllViews(0);             // Redraw all the views
      m_pSelected = 0;                     // Reset selected element ptr
   }	
}

//##ModelId=47511BBE02EE
void CSketcherView::drawRibble( CPoint* start, CPoint* end, CDC* pDC )
{
    if ( isGraphVisible && start != NULL && end != NULL)
    {   // рисуем ребро
        CLine* visibleRibble = new CLine(*start, *end, GREEN);
        visibleRibble->Draw(pDC);
        start = NULL;
        end = NULL;
    }
}

//##ModelId=47511BBE02FF
void CSketcherView::drawRibble( CElement* start, CElement* end, CDC* pDC )
{
    drawRibble(
        &(start->GetBoundRect().CenterPoint()),
        &(end->GetBoundRect().CenterPoint()),
        pDC
        );
}

//##ModelId=47511BBE037A
void CSketcherView::OnElementDrawribbles() 
{
	isGraphVisible = !isGraphVisible;
    Invalidate();
}

//##ModelId=47511BBE037C
void CSketcherView::OnUpdateElementDrawribbles(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(isGraphVisible==true);
}
