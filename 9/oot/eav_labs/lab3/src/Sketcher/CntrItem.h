// CntrItem.h : interface of the CSketcherCntrItem class
//

#if !defined(AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_)
#define AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSketcherDoc;
class CSketcherView;

//##ModelId=4770E2080381
class CSketcherCntrItem : public COleClientItem
{
	DECLARE_SERIAL(CSketcherCntrItem)

// Constructors
public:
	//##ModelId=4770E2080383
	CSketcherCntrItem(CSketcherDoc* pContainer = NULL);
		// Note: pContainer is allowed to be NULL to enable IMPLEMENT_SERIALIZE.
		//  IMPLEMENT_SERIALIZE requires the class have a constructor with
		//  zero arguments.  Normally, OLE items are constructed with a
		//  non-NULL document pointer.

// Attributes
public:
	//##ModelId=4770E2080385
	CRect m_rect;   // position within the document
	//##ModelId=4770E2080391
	CSketcherDoc* GetDocument()
		{ return (CSketcherDoc*)COleClientItem::GetDocument(); }
	//##ModelId=4770E2080392
	CSketcherView* GetActiveView()
		{ return (CSketcherView*)COleClientItem::GetActiveView(); }

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherCntrItem)
	public:
	//##ModelId=4770E2080393
	virtual void OnChange(OLE_NOTIFICATION wNotification, DWORD dwParam);
	//##ModelId=4770E2080397
	virtual void OnActivate();
	protected:
	//##ModelId=4770E2080399
	virtual void OnGetItemPosition(CRect& rPosition);
	//##ModelId=4770E20803A3
	virtual void OnDeactivateUI(BOOL bUndoable);
	//##ModelId=4770E20803A6
	virtual BOOL OnChangeItemPosition(const CRect& rectPos);
	//##ModelId=4770E20803B1
	virtual BOOL CanActivate();
	//}}AFX_VIRTUAL
// Operations
public:
	//##ModelId=4770E20803B3
	void InvalidateItem();
	//##ModelId=4770E20803B4
	void UpdateFromServerExtent();

// Implementation
public:
	//##ModelId=4770E20803B5
	~CSketcherCntrItem();
#ifdef _DEBUG
	//##ModelId=4770E20803B6
	virtual void AssertValid() const;
	//##ModelId=4770E20803C0
	virtual void Dump(CDumpContext& dc) const;
#endif
	//##ModelId=4770E20803C3
	virtual void Serialize(CArchive& ar);
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CNTRITEM_H__61EA282B_414F_411A_AFDC_7FA9E437FA26__INCLUDED_)
