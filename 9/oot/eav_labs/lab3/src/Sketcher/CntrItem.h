// CntrItem.h : interface of the CSketcherCntrItem class
//

#if !defined(AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_)
#define AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSketcherDoc;
class CSketcherView;

class CSketcherCntrItem : public COleDocObjectItem
{
	DECLARE_SERIAL(CSketcherCntrItem)

// Constructors
public:
	CSketcherCntrItem(CSketcherDoc* pContainer = NULL);
		// Note: pContainer is allowed to be NULL to enable IMPLEMENT_SERIALIZE.
		//  IMPLEMENT_SERIALIZE requires the class have a constructor with
		//  zero arguments.  Normally, OLE items are constructed with a
		//  non-NULL document pointer.

// Attributes
public:
	CRect m_rect;   // position within the document
	CSketcherDoc* GetDocument()
		{ return (CSketcherDoc*)COleDocObjectItem::GetDocument(); }
	CSketcherView* GetActiveView()
		{ return (CSketcherView*)COleDocObjectItem::GetActiveView(); }

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherCntrItem)
	public:
	virtual void OnChange(OLE_NOTIFICATION wNotification, DWORD dwParam);
	virtual void OnActivate();
	protected:
	//##ModelId=476D9BD600A4
	virtual void OnGetItemPosition(CRect& rPosition);
	//##ModelId=476D9BD600AB
	virtual void OnDeactivateUI(BOOL bUndoable);
	virtual BOOL OnChangeItemPosition(const CRect& rectPos);
	virtual BOOL CanActivate();
	//}}AFX_VIRTUAL
// Operations
public:
	void InvalidateItem();
	void UpdateFromServerExtent();

// Implementation
public:
	~CSketcherCntrItem();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif
	virtual void Serialize(CArchive& ar);
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_)
