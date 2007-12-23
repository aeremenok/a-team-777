// SketcherView.h : interface of the CSketcherView class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERVIEW_H__C4ED1ACD_C234_4E67_B1BB_FC106DCB5C19__INCLUDED_)
#define AFX_SKETCHERVIEW_H__C4ED1ACD_C234_4E67_B1BB_FC106DCB5C19__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CSketcherCntrItem;

class CSketcherView : public CView
{
protected: // create from serialization only
	CSketcherView();
	DECLARE_DYNCREATE(CSketcherView)

// Attributes
public:
	CSketcherDoc* GetDocument();
	// m_pSelection holds the selection to the current CSketcherCntrItem.
	// For many applications, such a member variable isn't adequate to
	//  represent a selection, such as a multiple selection or a selection
	//  of objects that are not CSketcherCntrItem objects.  This selection
	//  mechanism is provided just to help you get started.

	// TODO: replace this selection mechanism with one appropriate to your app.
	CSketcherCntrItem* m_pSelection;

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual void OnInitialUpdate(); // called first time after construct
	virtual BOOL IsSelected(const CObject* pDocItem) const;// Container support
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CSketcherView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherView)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	afx_msg void OnDestroy();
	afx_msg void OnSetFocus(CWnd* pOldWnd);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnInsertObject();
	afx_msg void OnCancelEditCntr();
	afx_msg void OnCancelEditSrvr();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in SketcherView.cpp
inline CSketcherDoc* CSketcherView::GetDocument()
   { return (CSketcherDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERVIEW_H__C4ED1ACD_C234_4E67_B1BB_FC106DCB5C19__INCLUDED_)
