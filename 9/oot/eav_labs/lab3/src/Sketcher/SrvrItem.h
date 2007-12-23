// SrvrItem.h : interface of the CSketcherSrvrItem class
//

#if !defined(AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_)
#define AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSketcherSrvrItem : public CDocObjectServerItem
{
	DECLARE_DYNAMIC(CSketcherSrvrItem)

// Constructors
public:
	CSketcherSrvrItem(CSketcherDoc* pContainerDoc);

// Attributes
	CSketcherDoc* GetDocument() const
		{ return (CSketcherDoc*)CDocObjectServerItem::GetDocument(); }

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherSrvrItem)
	public:
	virtual BOOL OnDraw(CDC* pDC, CSize& rSize);
	virtual BOOL OnGetExtent(DVASPECT dwDrawAspect, CSize& rSize);
	//}}AFX_VIRTUAL

// Implementation
public:
	~CSketcherSrvrItem();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	virtual void Serialize(CArchive& ar);   // overridden for document i/o
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_)
