// SrvrItem.cpp : implementation of the CSketcherSrvrItem class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "SketcherDoc.h"
#include "SrvrItem.h"
#include "CntrItem.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherSrvrItem implementation

IMPLEMENT_DYNAMIC(CSketcherSrvrItem, CDocObjectServerItem)

//##ModelId=4770E206019E
CSketcherSrvrItem::CSketcherSrvrItem(CSketcherDoc* pContainerDoc)
	: CDocObjectServerItem(pContainerDoc, TRUE)
{
    isInitialized = false;
	// TODO: add one-time construction code here
	//  (eg, adding additional clipboard formats to the item's data source)
}

//##ModelId=4770E20601B1
CSketcherSrvrItem::~CSketcherSrvrItem()
{
	// TODO: add cleanup code here
}

//##ModelId=4770E20601B7
void CSketcherSrvrItem::Serialize(CArchive& ar)
{
	// CSketcherSrvrItem::Serialize will be called by the framework if
	//  the item is copied to the clipboard.  This can happen automatically
	//  through the OLE callback OnGetClipboardData.  A good default for
	//  the embedded item is simply to delegate to the document's Serialize
	//  function.  If you support links, then you will want to serialize
	//  just a portion of the document.

	if (!IsLinkedItem())
	{
		CSketcherDoc* pDoc = GetDocument();
		ASSERT_VALID(pDoc);
		pDoc->Serialize(ar);
	}
}

//##ModelId=4770E20601AD
BOOL CSketcherSrvrItem::OnGetExtent(DVASPECT dwDrawAspect, CSize& rSize)
{
	// Most applications, like this one, only handle drawing the content
	//  aspect of the item.  If you wish to support other aspects, such
	//  as DVASPECT_THUMBNAIL (by overriding OnDrawEx), then this
	//  implementation of OnGetExtent should be modified to handle the
	//  additional aspect(s).

	if (dwDrawAspect != DVASPECT_CONTENT)
		return CDocObjectServerItem::OnGetExtent(dwDrawAspect, rSize);

	// CSketcherSrvrItem::OnGetExtent is called to get the extent in
	//  HIMETRIC units of the entire item.  The default implementation
	//  here simply returns a hard-coded number of units.

	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	// TODO: replace this arbitrary size
	rSize = pDoc->GetDocSize();
	CClientDC dc(NULL);

        // use a mapping mode based on logical units
	//  (we can't use MM_LOENGLISH because MM_LOENGLISH uses physical inches)
	dc.SetMapMode(MM_ANISOTROPIC);
	dc.SetViewportExt(dc.GetDeviceCaps(LOGPIXELSX), dc.GetDeviceCaps(LOGPIXELSY));
	dc.SetWindowExt(100, 100);
	dc.LPtoHIMETRIC(&rSize);

	return TRUE;
}

//##ModelId=4770E20601A2
BOOL CSketcherSrvrItem::OnDraw(CDC* pDC, CSize& rSize)
{
	// Remove this if you use rSize
	UNREFERENCED_PARAMETER(rSize);

	CSketcherDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDC->SetMapMode(MM_ANISOTROPIC);
    CSize sizeDoc = pDoc->GetDocSize();
    //sizeDoc.cy = -sizeDoc.cy;
    pDC->SetWindowOrg(0,0);
	pDC->SetWindowExt(sizeDoc);
    isInitialized = true;


	// draw the OLE items from the list
	POSITION pos = pDoc->GetStartPosition();
	while (pos != NULL)
	{
		CSketcherCntrItem* pItem = (CSketcherCntrItem*)pDoc->GetNextItem(pos);
		pItem->Draw(pDC, pItem->m_rect);
	}

    Iterator<CElement>* iter = pDoc->getShapeContainer()->getNewIterator();
    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();
        ribble->get__vertex1()->Draw(pDC);
        ribble->get__vertex2()->Draw(pDC);

        CPoint start = ribble->get__vertex1()->GetBoundRect().CenterPoint();
        CPoint end = ribble->get__vertex2()->GetBoundRect().CenterPoint();
        
        CLine* visibleRibble = new CLine(start, end, GREEN);
        visibleRibble->Draw(pDC);        
    }
 
    return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherSrvrItem diagnostics

#ifdef _DEBUG
//##ModelId=4770E20601B2
void CSketcherSrvrItem::AssertValid() const
{
	CDocObjectServerItem::AssertValid();
}

//##ModelId=4770E20601B4
void CSketcherSrvrItem::Dump(CDumpContext& dc) const
{
	CDocObjectServerItem::Dump(dc);
}
#endif

/////////////////////////////////////////////////////////////////////////////
