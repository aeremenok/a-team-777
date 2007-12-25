// SketcherView.h : interface of the CSketcherView class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERVIEW_H__C4ED1ACD_C234_4E67_B1BB_FC106DCB5C19__INCLUDED_)
#define AFX_SKETCHERVIEW_H__C4ED1ACD_C234_4E67_B1BB_FC106DCB5C19__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//////////////////////////////////////////////////////////////////////////
#include "ShapeHandler.h"

class ShapeHandler;
class CSketcherCntrItem;
//////////////////////////////////////////////////////////////////////////
class CSketcherView : public CScrollView
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
protected:

    ShapeHandler* _handler;
// Operations
public:
	CSketcherCntrItem* HitTestItems(CPoint point);
	void SetSelection(CSketcherCntrItem* pItem);
	void SetupTracker(CSketcherCntrItem* pItem, CRectTracker* pTracker);
    // вызывает диалог задания масштаба
    void requestScale();
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
    virtual void OnPrepareDC(CDC* pDC, CPrintInfo* pInfo = NULL);
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual void OnInitialUpdate(); // called first time after construct
	virtual BOOL IsSelected(const CObject* pDocItem) const;// Container support
	virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
	//}}AFX_VIRTUAL

// Implementation
public:
	void ResetScrollSizes();
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
	//##ModelId=473EDD6D0283
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	//##ModelId=473EDD6D0291
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	//##ModelId=473EDD6D029F
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	//##ModelId=4741F10E0234
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	//##ModelId=4741F10E0244
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	//##ModelId=4741F10E0251
	afx_msg void OnMove();
	//##ModelId=4741F10E0253
	afx_msg void OnSendtoback();
	//##ModelId=4741F10E0255
	afx_msg void OnDelete();
	//##ModelId=47511BBE037A
	afx_msg void OnElementDrawribbles();
	//##ModelId=47511BBE037C
	afx_msg void OnUpdateElementDrawribbles(CCmdUI* pCmdUI);
	//##ModelId=475168590203
	afx_msg void OnNoelementScale();
	//##ModelId=475168590213
	afx_msg void OnUpdateNoelementScale(CCmdUI* pCmdUI);
	//##ModelId=475168590216
	afx_msg void OnElementScale();
	//##ModelId=475168590218
	afx_msg void OnUpdateElementScale(CCmdUI* pCmdUI);
	//##ModelId=47532663035B
	afx_msg void OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags);
	//##ModelId=47532663036B
	afx_msg void OnKeyUp(UINT nChar, UINT nRepCnt, UINT nFlags);
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
