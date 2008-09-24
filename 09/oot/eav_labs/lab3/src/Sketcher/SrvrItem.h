// SrvrItem.h : interface of the CSketcherSrvrItem class
//

#if !defined(AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_)
#define AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//##ModelId=4770E206018D
class CSketcherSrvrItem : public CDocObjectServerItem
{
	DECLARE_DYNAMIC(CSketcherSrvrItem)

	//##ModelId=47728BFF00BB
    bool isInitialized;

// Constructors
public:
	//##ModelId=4770E206019E
	CSketcherSrvrItem(CSketcherDoc* pContainerDoc);

// Attributes
	//##ModelId=4770E20601A0
	CSketcherDoc* GetDocument() const
		{ return (CSketcherDoc*)CDocObjectServerItem::GetDocument(); }

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherSrvrItem)
	public:
	//##ModelId=4770E20601A2
	virtual BOOL OnDraw(CDC* pDC, CSize& rSize);
	//##ModelId=4770E20601AD
	virtual BOOL OnGetExtent(DVASPECT dwDrawAspect, CSize& rSize);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=4770E20601B1
	~CSketcherSrvrItem();
#ifdef _DEBUG
	//##ModelId=4770E20601B2
	virtual void AssertValid() const;
	//##ModelId=4770E20601B4
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	//##ModelId=4770E20601B7
	virtual void Serialize(CArchive& ar);   // overridden for document i/o
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SRVRITEM_H__A5B40173_5467_4AB0_BF3C_968F412F8534__INCLUDED_)
