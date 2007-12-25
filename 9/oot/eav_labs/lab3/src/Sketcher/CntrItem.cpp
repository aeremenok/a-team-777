// CntrItem.cpp : implementation of the CSketcherCntrItem class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "SketcherDoc.h"
#include "SketcherView.h"
#include "CntrItem.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherCntrItem implementation

IMPLEMENT_SERIAL(CSketcherCntrItem, COleClientItem, 0)

//##ModelId=4770E2080383
CSketcherCntrItem::CSketcherCntrItem(CSketcherDoc* pContainer)
	: COleClientItem(pContainer)
{
	// TODO: add one-time construction code here
	m_rect.SetRect(10, 10, 300, 300);
}

//##ModelId=4770E20803B5
CSketcherCntrItem::~CSketcherCntrItem()
{
	// TODO: add cleanup code here
	
}

//##ModelId=4770E20803B3
void CSketcherCntrItem::InvalidateItem()
{
	GetDocument()->UpdateAllViews(NULL, HINT_UPDATE_ITEM, this);
}

//##ModelId=4770E20803B4
void CSketcherCntrItem::UpdateFromServerExtent()
{
	CSize size;
	if (GetCachedExtent(&size))
	{
		// OLE returns the extent in HIMETRIC units -- we need pixels
		CClientDC dc(NULL);
		
		//size.cy = -size.cy;
		dc.HIMETRICtoDP(&size);

		// Only invalidate if it has actually changed, and also
		// only if it is not in-place active.  When in-place active, the
		// container item size should sync with the "window size" of the
		// object.  Only when not in-place active should the container 
		// item size should sync with the natural size of the object.

		if ((size != m_rect.Size()) && !IsInPlaceActive())
		{
			// invalidate old, update, invalidate new
			InvalidateItem();
			m_rect.bottom = m_rect.top + size.cy;
			m_rect.right = m_rect.left + size.cx;
			InvalidateItem();

			// mark document as modified
			GetDocument()->SetModifiedFlag();
		}
	}
}

//##ModelId=4770E2080393
void CSketcherCntrItem::OnChange(OLE_NOTIFICATION nCode, DWORD dwParam)
{
	ASSERT_VALID(this);

	COleClientItem::OnChange(nCode, dwParam);

	// When an item is being edited (either in-place or fully open)
	//  it sends OnChange notifications for changes in the state of the
	//  item or visual appearance of its content.
	switch (nCode)
	{
	case OLE_CHANGED:
		InvalidateItem();
		UpdateFromServerExtent();
		break;
	case OLE_CHANGED_STATE:
	case OLE_CHANGED_ASPECT:
		InvalidateItem();
		break;
	}

	// TODO: invalidate the item by calling UpdateAllViews
	//  (with hints appropriate to your application)
	GetDocument()->NotifyChanged();
}

//##ModelId=4770E20803A6
BOOL CSketcherCntrItem::OnChangeItemPosition(const CRect& rectPos)
{
	ASSERT_VALID(this);

	// During in-place activation CSketcherCntrItem::OnChangeItemPosition
	//  is called by the server to change the position of the in-place
	//  window.  Usually, this is a result of the data in the server
	//  document changing such that the extent has changed or as a result
	//  of in-place resizing.
	//
	// The default here is to call the base class, which will call
	//  COleDocObjectItem::SetItemRects to move the item
	//  to the new position.

	if (!COleClientItem::OnChangeItemPosition(rectPos))
		return FALSE;
	InvalidateItem();
	m_rect = rectPos;
	InvalidateItem();

	// mark document as dirty
	GetDocument()->SetModifiedFlag();

	return TRUE;
}

//##ModelId=4770E2080399
void CSketcherCntrItem::OnGetItemPosition(CRect& rPosition)
{
	ASSERT_VALID(this);
	// return rect relative to client area of view
	rPosition = m_rect;
}

//##ModelId=4770E2080397
void CSketcherCntrItem::OnActivate()
{
    // Allow only one inplace activate item per frame
    CSketcherView* pView = GetActiveView();
    ASSERT_VALID(pView);
    COleClientItem* pItem = GetDocument()->GetInPlaceActiveItem(pView);
    if (pItem != NULL && pItem != this)
        pItem->Close();
    
    CSketcherCntrItem::OnActivate();
}

//##ModelId=4770E20803A3
void CSketcherCntrItem::OnDeactivateUI(BOOL bUndoable)
{
	COleClientItem::OnDeactivateUI(bUndoable);

    // Hide the object if it is not an outside-in object
    DWORD dwMisc = 0;
    m_lpObject->GetMiscStatus(GetDrawAspect(), &dwMisc);
    if (dwMisc & OLEMISC_INSIDEOUT)
        DoVerb(OLEIVERB_HIDE, NULL);
}

//##ModelId=4770E20803C3
void CSketcherCntrItem::Serialize(CArchive& ar)
{
	ASSERT_VALID(this);

	// Call base class first to read in COleDocObjectItem data.
	// Since this sets up the m_pDocument pointer returned from
	//  CSketcherCntrItem::GetDocument, it is a good idea to call
	//  the base class Serialize first.
	COleClientItem::Serialize(ar);

	// now store/retrieve data specific to CSketcherCntrItem
	if (ar.IsStoring())
	{
		// TODO: add storing code here
		ar << m_rect;
	}
	else
	{
		// TODO: add loading code here
		ar >> m_rect;
	}
}

//##ModelId=4770E20803B1
BOOL CSketcherCntrItem::CanActivate()
{
	// Editing in-place while the server itself is being edited in-place
	//  does not work and is not supported.  So, disable in-place
	//  activation in this case.
	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	ASSERT(pDoc->IsKindOf(RUNTIME_CLASS(COleServerDoc)));
	if (pDoc->IsInPlaceActive())
		return FALSE;

	// otherwise, rely on default behavior
	return COleClientItem::CanActivate();
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherCntrItem diagnostics

#ifdef _DEBUG
//##ModelId=4770E20803B6
void CSketcherCntrItem::AssertValid() const
{
	COleClientItem::AssertValid();
}

//##ModelId=4770E20803C0
void CSketcherCntrItem::Dump(CDumpContext& dc) const
{
	COleClientItem::Dump(dc);
}
#endif

/////////////////////////////////////////////////////////////////////////////
