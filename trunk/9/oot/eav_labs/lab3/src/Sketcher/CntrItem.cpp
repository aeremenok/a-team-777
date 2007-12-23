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

IMPLEMENT_SERIAL(CSketcherCntrItem, COleDocObjectItem, 0)

CSketcherCntrItem::CSketcherCntrItem(CSketcherDoc* pContainer)
	: COleDocObjectItem(pContainer)
{
	// TODO: add one-time construction code here
	
}

CSketcherCntrItem::~CSketcherCntrItem()
{
	// TODO: add cleanup code here
	
}

void CSketcherCntrItem::OnChange(OLE_NOTIFICATION nCode, DWORD dwParam)
{
	ASSERT_VALID(this);

	COleDocObjectItem::OnChange(nCode, dwParam);

	// When an item is being edited (either in-place or fully open)
	//  it sends OnChange notifications for changes in the state of the
	//  item or visual appearance of its content.

	// TODO: invalidate the item by calling UpdateAllViews
	//  (with hints appropriate to your application)

	GetDocument()->UpdateAllViews(NULL);
		// for now just update ALL views/no hints
}

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

	if (!COleDocObjectItem::OnChangeItemPosition(rectPos))
		return FALSE;

	// TODO: update any cache you may have of the item's rectangle/extent

	return TRUE;
}


void CSketcherCntrItem::OnActivate()
{
}

void CSketcherCntrItem::OnDeactivateUI(BOOL bUndoable)
{
	COleDocObjectItem::OnDeactivateUI(bUndoable);

    // Hide the object if it is not an outside-in object
    DWORD dwMisc = 0;
    m_lpObject->GetMiscStatus(GetDrawAspect(), &dwMisc);
    if (dwMisc & OLEMISC_INSIDEOUT)
        DoVerb(OLEIVERB_HIDE, NULL);
}

void CSketcherCntrItem::Serialize(CArchive& ar)
{
	ASSERT_VALID(this);

	// Call base class first to read in COleDocObjectItem data.
	// Since this sets up the m_pDocument pointer returned from
	//  CSketcherCntrItem::GetDocument, it is a good idea to call
	//  the base class Serialize first.
	COleDocObjectItem::Serialize(ar);

	// now store/retrieve data specific to CSketcherCntrItem
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

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
void CSketcherCntrItem::AssertValid() const
{
	COleDocObjectItem::AssertValid();
}

void CSketcherCntrItem::Dump(CDumpContext& dc) const
{
	COleDocObjectItem::Dump(dc);
}
#endif

/////////////////////////////////////////////////////////////////////////////
