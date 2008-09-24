// SketcherView.cpp : implementation of the CSketcherView class
//

#include "stdafx.h"
#include "Sketcher.h"


#include "ChildFrm.h"
#include "ScaleDialog.h"
#include "Elements.h"

#include "other\CSquare.h"
#include "other\CDiamond.h"
#include "other\CText.h"
#include "other\CTextInSquare.h"
#include "Insert_Text.h"
#include "other\TStack.h"

#include "SketcherDoc.h"
#include "SketcherView.h"



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
	ON_COMMAND(ID_VIEW_SCALE, OnViewScale)
	ON_COMMAND(ID_INS_TEXT1, OnInsertText)
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

CSketcherView::CSketcherView()
{
    m_FirstPoint = CPoint(0,0);         // Set 1st recorded point to 0,0
    m_SecondPoint = CPoint(0,0);        // Set 2nd recorded point to 0,0
    m_pTempElement = 0;                 // Set temporary element pointer to 0
    m_pSelected = 0;                    // No element selected initially
    m_MoveMode = FALSE;                 // Set move mode off
    m_CursorPos = CPoint(0,0);          // Initialize as zero
    m_FirstPos = CPoint(0,0);           // Initialize as zero
    m_Scale = 1;                          // Set scale to 1:1
    SetScrollSizes(MM_TEXT, CSize(0,0));  // Set arbitrary scrollers
	EnteredText = "Text not entered!";
	s_Pen = 1;
	selectedEl = 0;
}

CSketcherView::~CSketcherView()
{
}

BOOL CSketcherView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView drawing

void CSketcherView::OnDraw(CDC* pDC)
{
   CSketcherDoc* pDoc = GetDocument();
   ASSERT_VALID(pDoc);

    IIterator<CElement>* iter = pDoc->getIterator();
    int i = 0;
	CElement* prev = 0; 
    while (iter->hasNext())
    {        
        CElement* current = iter->next();
        current->Draw(pDC, m_pSelected, i==selectedEl  );
		if(prev!=0){
			
			CPen aPen;
			
			if(!aPen.CreatePen(PS_SOLID, s_Pen, RGB(0,0,255)))
			{  
			  // Pen creation failed. Abort the program.
			  AfxMessageBox("Pen creation failed drawing a line", MB_OK);
			  AfxAbort();
			}

			CPen* pOldPen = pDC->SelectObject(&aPen);  // Select the pen

			// Now draw the line
			pDC->MoveTo(prev->GetBoundRect().CenterPoint());
			CPoint p = CPoint(current->GetBoundRect().TopLeft().x, current->GetBoundRect().CenterPoint().y);
			pDC->LineTo(p);

			

			CRect ovalrect = CRect(current->GetBoundRect().TopLeft().x-20, current->GetBoundRect().CenterPoint().y+10,
									current->GetBoundRect().TopLeft().x, current->GetBoundRect().CenterPoint().y-10);

			pDC->Ellipse(ovalrect);
			pDC->SelectObject(pOldPen);                // Restore the old pen

		}
		i++;
		prev = current;
    }
    


   /*
   POSITION aPos = pDoc->GetListHeadPosition();
   CElement* pElement = 0;                  // Store for an element pointer
   while(aPos)                              // Loop while aPos is not null
   {
      pElement = pDoc->GetNext(aPos);       // Get the current element pointer
      // If the element is visible...
      if(pDC->RectVisible(pElement->GetBoundRect()))
         pElement->Draw(pDC, m_pSelected);  // ...draw it
   }
   */
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView printing

BOOL CSketcherView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CSketcherView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CSketcherView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherView diagnostics

#ifdef _DEBUG
void CSketcherView::AssertValid() const
{
	CView::AssertValid();
}

void CSketcherView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CSketcherDoc* CSketcherView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CSketcherDoc)));
	return (CSketcherDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherView message handlers

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

void CSketcherView::OnLButtonUp(UINT nFlags, CPoint point) 
{
   if(this == GetCapture())
      ReleaseCapture();        // Stop capturing mouse messages

   // If there is an element, add it to the document
   if(m_pTempElement)
   {  
      GetDocument()->AddElement(m_pTempElement);
      //GetDocument()->UpdateAllViews(0,0,m_pTempElement);  // Tell all the views
      GetDocument()->UpdateAllViews(0);  
	  m_pTempElement = 0;        // Reset the element pointer
   }
}

void CSketcherView::OnMouseMove(UINT nFlags, CPoint point) 
{
	HCURSOR hCursor;
	hCursor = AfxGetApp()->LoadCursor(IDC_POINTER);
	::SetCursor(hCursor); 
	::SetClassLong(this->m_hWnd, GCL_HCURSOR, (long) hCursor);

   // Define a Device Context object for the view
   CClientDC aDC(this);
   OnPrepareDC(&aDC);            // Get origin adjusted

   // If we are in move mode, move the selected element and return
   if(m_MoveMode)
   {
      aDC.DPtoLP(&point);        // Convert to logical coordinatess
      MoveElement(aDC, point);   // Move the element
      return;
   }
   
   if((nFlags & MK_LBUTTON) && (this == GetCapture()))
   {
      aDC.DPtoLP(&point);        // convert point to Logical
      m_SecondPoint = point;     // Save the current cursor position

      if(m_pTempElement)
      {
         aDC.SetROP2(R2_NOTXORPEN);      // Set drawing mode

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
   else        // We are not drawing an element...
   {           // ...so do highlighting
      CRect aRect;
      CElement* pCurrentSelection = SelectElement(point);

      if(pCurrentSelection!=m_pSelected)
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



CElement* CSketcherView::CreateElement()
{
	// Get a pointer to the document for this view
	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);                  // Verify the pointer is good

	// Now select the element using the type stored in the document
	switch(pDoc->GetElementType())
	{
		case CSHAPE_SQUARE:
		{									
			return new CSquare(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());
		}
		case CSHAPE_TEXT:    
		{   
			return new CText(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor(), EnteredText);
		}
		case CSHAPE_TEXT_IN_SQUARE:          
		{
			return new CTextInSquare(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor(), EnteredText);
		}
		case CSHAPE_DIAMOND:                  
		{
			return new CDiamond(m_FirstPoint, m_SecondPoint, pDoc->GetElementColor());
		}

		default:
			//	Something's gone wrong
			AfxMessageBox("Bad Element code", MB_OK);
			AfxAbort();
			return NULL;
	}
}

// Find the element at the cursor
CElement* CSketcherView::SelectElement(CPoint aPoint)
{
   // Convert parameter aPoint to logical coordinates
   CClientDC aDC(this);
   OnPrepareDC(&aDC);
   aDC.DPtoLP(&aPoint);

   CSketcherDoc* pDoc=GetDocument();      // Get a pointer to the document
   CElement* pElement = 0;                // Store an element pointer
   CRect aRect(0,0,0,0);                  // Store a rectangle
   CElement* foundElement = 0; 
   

   IIterator<CElement>* iter = pDoc->getIterator();
    int i = 0;
    while (iter->hasNext())
    {        
        pElement = iter->next();
		aRect = pElement->GetBoundRect();
		// Select the first element that appears under the cursor
        if(aRect.PtInRect(aPoint)) foundElement =  pElement;
    }
   /*
   POSITION aPos = pDoc->GetListTailPosition();  // Get last element posn

   while(aPos)                            // Iterate through the list
   {
      pElement = pDoc->GetPrev(aPos);
      aRect = pElement->GetBoundRect();
      // Select the first element that appears under the cursor
      if(aRect.PtInRect(aPoint))
         return pElement;
   }
   */
   return foundElement;                              // No element found
}

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
      
	
	  aMenu.CheckMenuItem(ID_SQUARE,
                (((CSHAPE_SQUARE==ElementType)?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND));
      aMenu.CheckMenuItem(ID_TEXT,
                (CSHAPE_TEXT==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
      aMenu.CheckMenuItem(ID_SQUARE_TEXT,
                (CSHAPE_TEXT_IN_SQUARE==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
      aMenu.CheckMenuItem(ID_DIAMOND,
                (CSHAPE_DIAMOND==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
	  
	  // Display the context pop-up
      aMenu.GetSubMenu(1)->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON, point.x, point.y, this);
   }
   
}

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

void CSketcherView::OnInitialUpdate() 
{
   ResetScrollSizes();               // Set up the scrollbars
   CScrollView::OnInitialUpdate();
}

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

void CSketcherView::OnSendtoback() 
{
   GetDocument()->SendToBack(m_pSelected);  // Move element in list	
}

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


void CSketcherView::OnInsertText()
{
   Insert_Text aDlg;            // Create a dialog object
   aDlg.setText(EnteredText);  
   if(aDlg.DoModal() == IDOK)
   {
      EnteredText = aDlg.getText();    // Get the new value      
   }
}

void CSketcherView::OnViewScale() 
{
   CScaleDialog aDlg;            // Create a dialog object
   aDlg.m_Scale = m_Scale;       // Pass the view scale to the dialog
   if(aDlg.DoModal() == IDOK)
   {
      m_Scale = aDlg.m_Scale;    // Get the new scale

      // Get the frame window for this view
      CChildFrame* viewFrame = (CChildFrame*)GetParentFrame();

      // Build the message string
      CString StatusMsg("View Scale:");
      StatusMsg += (char)('0' + m_Scale);

      // Write the string to the status bar
      viewFrame->m_StatusBar.GetStatusBarCtrl().SetText(StatusMsg, 0, 0);
      ResetScrollSizes();        // Adjust scrolling to the new scale
      InvalidateRect(0);         // Invalidate the whole window
   }
}

void CSketcherView::ResetScrollSizes()
{
   CClientDC aDC(this);
   OnPrepareDC(&aDC);                            // Set up the device context
   CSize DocSize = GetDocument()->GetDocSize();  // Get the document size
   aDC.LPtoDP(&DocSize);                         // Get the size in pixels
   SetScrollSizes(MM_TEXT, DocSize);             // Set up the scrollbars
}

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
   long xExtent = (long)DocSize.cx*m_Scale*xLogPixels/100L;
   long yExtent = (long)DocSize.cy*m_Scale*yLogPixels/100L;

   pDC->SetViewportExt((int)xExtent, (int)-yExtent); // Set viewport extent
}


//##ModelId=47532663035B
void CSketcherView::OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
    switch(nChar)
    {
        case 38: // up
                
			{selectedEl++;
			CSketcherDoc* pDoc = GetDocument();
			IIterator<CElement>* iter = pDoc->getIterator();
			if(selectedEl>=iter->getCount())selectedEl = iter->getCount()-1;
			GetDocument()->UpdateAllViews(0);

            break;
			}
        case 40:{ // down
                
			selectedEl--;
			if(selectedEl < 0)selectedEl=0;
			GetDocument()->UpdateAllViews(0);
            break;
				}
        case 37: // left
            break;
        case 39: // right
                 
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
