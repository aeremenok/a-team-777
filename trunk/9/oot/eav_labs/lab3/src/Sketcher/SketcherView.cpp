// SketcherView.cpp : implementation of the CSketcherView class
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"

#include "ChildFrm.h"
#include "ScaleDialog.h"

#include "SketcherDoc.h"
#include "CntrItem.h"
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
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	ON_WM_DESTROY()
	ON_WM_SETFOCUS()
	ON_WM_SIZE()
	ON_COMMAND(ID_OLE_INSERT_NEW, OnInsertObject)
	ON_COMMAND(ID_CANCEL_EDIT_CNTR, OnCancelEditCntr)
	ON_COMMAND(ID_CANCEL_EDIT_SRVR, OnCancelEditSrvr)
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
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherView construction/destruction

CSketcherView::CSketcherView()
{
	m_pSelection = NULL;
	// TODO: add construction code here
    _handler = new ShapeHandler(this);
    SetScrollSizes(MM_TEXT, CSize(0,0));  // Set arbitrary scrollers
}

CSketcherView::~CSketcherView()
{
    delete _handler;
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
	// TODO: add draw code for native data here
	_handler->onDraw(pDC);
}

void CSketcherView::OnInitialUpdate()
{
	ResetScrollSizes();               // Set up the scrollbars
	CView::OnInitialUpdate();


	// TODO: remove this code when final selection model code is written
	m_pSelection = NULL;    // initialize selection

	//Active documents should always be activated
	COleDocument* pDoc = (COleDocument*) GetDocument();
	if (pDoc != NULL)
	{
		// activate the first one
		POSITION posItem = pDoc->GetStartPosition();
		if (posItem != NULL)
		{
			CDocItem* pItem = pDoc->GetNextItem(posItem);

			// only if it's an Active document
			COleDocObjectItem *pDocObjectItem =
				DYNAMIC_DOWNCAST(COleDocObjectItem, pItem);

			if (pDocObjectItem != NULL)
			{
				pDocObjectItem->DoVerb(OLEIVERB_SHOW, this);
			}
		}
	}
}

void CSketcherView::OnDestroy()
{
	// Deactivate the item on destruction; this is important
	// when a splitter view is being used.
   CView::OnDestroy();
   COleClientItem* pActiveItem = GetDocument()->GetInPlaceActiveItem(this);
   if (pActiveItem != NULL && pActiveItem->GetActiveView() == this)
   {
      pActiveItem->Deactivate();
      ASSERT(GetDocument()->GetInPlaceActiveItem(this) == NULL);
   }
}


/////////////////////////////////////////////////////////////////////////////
// OLE Client support and commands

BOOL CSketcherView::IsSelected(const CObject* pDocItem) const
{
	// The implementation below is adequate if your selection consists of
	//  only CSketcherCntrItem objects.  To handle different selection
	//  mechanisms, the implementation here should be replaced.

	// TODO: implement this function that tests for a selected OLE client item

	return pDocItem == m_pSelection;
}

void CSketcherView::OnInsertObject()
{
	// Invoke the standard Insert Object dialog box to obtain information
	//  for new CSketcherCntrItem object.
	COleInsertDialog dlg;
	if (dlg.DoModal(COleInsertDialog::DocObjectsOnly) != IDOK)
		return;

	BeginWaitCursor();

	CSketcherCntrItem* pItem = NULL;
	TRY
	{
		// Create new item connected to this document.
		CSketcherDoc* pDoc = GetDocument();
		ASSERT_VALID(pDoc);
		pItem = new CSketcherCntrItem(pDoc);
		ASSERT_VALID(pItem);

		// Initialize the item from the dialog data.
		if (!dlg.CreateItem(pItem))
			AfxThrowMemoryException();  // any exception will do
		ASSERT_VALID(pItem);
		pItem->UpdateLink();
		pItem->UpdateFromServerExtent();
		
		// If item created from class list (not from file) then launch
		//  the server to edit the item.
        if (dlg.GetSelectionType() == COleInsertDialog::createNewItem)
			pItem->DoVerb(OLEIVERB_SHOW, this);

		ASSERT_VALID(pItem);

		// As an arbitrary user interface design, this sets the selection
		//  to the last item inserted.

		// TODO: reimplement selection as appropriate for your application

		SetSelection(pItem);
		pItem->InvalidateItem();
	}
	CATCH(CException, e)
	{
		if (pItem != NULL)
		{
			ASSERT_VALID(pItem);
			pItem->Delete();
		}
		AfxMessageBox(IDP_FAILED_TO_CREATE);
	}
	END_CATCH

	EndWaitCursor();
	GetDocument()->NotifyChanged();
}

// The following command handler provides the standard keyboard
//  user interface to cancel an in-place editing session.  Here,
//  the container (not the server) causes the deactivation.
void CSketcherView::OnCancelEditCntr()
{
	// Close any in-place active item on this view.
	COleClientItem* pActiveItem = GetDocument()->GetInPlaceActiveItem(this);
	if (pActiveItem != NULL)
	{
		pActiveItem->Close();
	}
	ASSERT(GetDocument()->GetInPlaceActiveItem(this) == NULL);
	GetDocument()->NotifyChanged();
}

// Special handling of OnSetFocus and OnSize are required for a container
//  when an object is being edited in-place.
void CSketcherView::OnSetFocus(CWnd* pOldWnd)
{
	COleClientItem* pActiveItem = GetDocument()->GetInPlaceActiveItem(this);
	if (pActiveItem != NULL &&
		pActiveItem->GetItemState() == COleClientItem::activeUIState)
	{
		// need to set focus to this item if it is in the same view
		CWnd* pWnd = pActiveItem->GetInPlaceWindow();
		if (pWnd != NULL)
		{
			pWnd->SetFocus();   // don't call the base class
			return;
		}
	}

	CView::OnSetFocus(pOldWnd);
}

void CSketcherView::OnSize(UINT nType, int cx, int cy)
{
	ResetScrollSizes();
	CView::OnSize(nType, cx, cy);
	COleClientItem* pActiveItem = GetDocument()->GetInPlaceActiveItem(this);
	if (pActiveItem != NULL)
		pActiveItem->SetItemRects();
}

/////////////////////////////////////////////////////////////////////////////
// OLE Server support

// The following command handler provides the standard keyboard
//  user interface to cancel an in-place editing session.  Here,
//  the server (not the container) causes the deactivation.
void CSketcherView::OnCancelEditSrvr()
{
	GetDocument()->OnDeactivateUI(FALSE);
	GetDocument()->NotifyChanged();
}

CSketcherCntrItem* CSketcherView::HitTestItems(CPoint point)
{
	CSketcherDoc* pDoc = GetDocument();
	CSketcherCntrItem* pItemHit = NULL;
	POSITION pos = pDoc->GetStartPosition();
	while (pos != NULL)
	{
		CSketcherCntrItem* pItem = (CSketcherCntrItem*)pDoc->GetNextItem(pos);
		if (pItem->m_rect.PtInRect(point))
			pItemHit = pItem;
	}
	return pItemHit;    // return top item at point
}

void CSketcherView::SetSelection(CSketcherCntrItem* pItem)
{
	// close in-place active item
	if (pItem == NULL || m_pSelection != pItem)
	{
		COleClientItem* pActiveItem = GetDocument()->GetInPlaceActiveItem(this);
		if (pActiveItem != NULL && pActiveItem != pItem)
			pActiveItem->Close();
	}
	// update view to new selection
	if (m_pSelection != pItem)
	{
		if (m_pSelection != NULL)
			OnUpdate(NULL, HINT_UPDATE_ITEM, m_pSelection);

		m_pSelection = pItem;
		if (m_pSelection != NULL)
			OnUpdate(NULL, HINT_UPDATE_ITEM, m_pSelection);
	}
}

void CSketcherView::SetupTracker(CSketcherCntrItem* pItem, CRectTracker* pTracker)
{
	pTracker->m_rect = pItem->m_rect;

	if (pItem == m_pSelection)
		pTracker->m_nStyle |= CRectTracker::resizeInside;

	if (pItem->GetType() == OT_LINK)
		pTracker->m_nStyle |= CRectTracker::dottedLine;
	else
		pTracker->m_nStyle |= CRectTracker::solidLine;

	if (pItem->GetItemState() == COleClientItem::openState ||
		pItem->GetItemState() == COleClientItem::activeUIState)
	{
		pTracker->m_nStyle |= CRectTracker::hatchInside;
    }
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
    _handler->onLBDown(point);
}

//##ModelId=473EDD6D0291
void CSketcherView::OnLButtonUp(UINT nFlags, CPoint point) 
{
    _handler->onLBUp(point);
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
	Invalidate();
	GetDocument()->NotifyChanged();
}

void CSketcherView::OnMove() 
{
    _handler->onMove();
}


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
void CSketcherView::ResetScrollSizes()
{
    CClientDC aDC(this);
    OnPrepareDC(&aDC);                            // Set up the device context
    CSize DocSize = GetDocument()->GetDocSize();  // Get the document size
    aDC.LPtoDP(&DocSize);                         // Get the size in pixels
    SetScrollSizes(MM_TEXT, DocSize);             // Set up the scrollbars
}

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
void CSketcherView::OnKeyUp(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
	CScrollView::OnKeyUp(nChar, nRepCnt, nFlags);
}

