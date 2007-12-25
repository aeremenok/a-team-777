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
//##ModelId=4770E206021A
class CSketcherView : public CScrollView
{
protected: // create from serialization only
	//##ModelId=4770E206021C
	CSketcherView();
	DECLARE_DYNCREATE(CSketcherView)

// Attributes
public:
	//##ModelId=4770E206022A
	CSketcherDoc* GetDocument();
	// m_pSelection holds the selection to the current CSketcherCntrItem.
	// For many applications, such a member variable isn't adequate to
	//  represent a selection, such as a multiple selection or a selection
	//  of objects that are not CSketcherCntrItem objects.  This selection
	//  mechanism is provided just to help you get started.

	// TODO: replace this selection mechanism with one appropriate to your app.
	//##ModelId=4770E206022C
	CSketcherCntrItem* m_pSelection;
protected:

	//##ModelId=4770E2060231
    ShapeHandler* _handler;
// Operations
public:
	//##ModelId=4770E2060239
	CSketcherCntrItem* HitTestItems(CPoint point);
	//##ModelId=4770E206023B
	void SetSelection(CSketcherCntrItem* pItem);
	//##ModelId=4770E206023D
	void SetupTracker(CSketcherCntrItem* pItem, CRectTracker* pTracker);
    // вызывает диалог задания масштаба
	//##ModelId=4770E206024A
    void requestScale();
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	//##ModelId=4770E206024B
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	//##ModelId=4770E206024E
    virtual void OnPrepareDC(CDC* pDC, CPrintInfo* pInfo = NULL);
	//##ModelId=4770E2060259
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	//##ModelId=4770E206025C
	virtual void OnInitialUpdate(); // called first time after construct
	//##ModelId=4770E206025E
	virtual BOOL IsSelected(const CObject* pDocItem) const;// Container support
	//##ModelId=4770E206026A
	virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=4770E2060279
	void ResetScrollSizes();
	//##ModelId=4770E206027A
	virtual ~CSketcherView();
#ifdef _DEBUG
	//##ModelId=4770E206027C
	virtual void AssertValid() const;
	//##ModelId=4770E206027E
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherView)
	afx_msg void OnDestroy();
	afx_msg void OnSetFocus(CWnd* pOldWnd);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnInsertObject();
	afx_msg void OnCancelEditCntr();
	afx_msg void OnCancelEditSrvr();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDblClk(UINT nFlags, CPoint point);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
	afx_msg void OnEditCopy();
	afx_msg void OnUpdateEditCopy(CCmdUI* pCmdUI);
	afx_msg void OnEditPaste();
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMove();
	afx_msg void OnSendtoback();
	afx_msg void OnDelete();
	afx_msg void OnElementDrawribbles();
	afx_msg void OnUpdateElementDrawribbles(CCmdUI* pCmdUI);
	afx_msg void OnNoelementScale();
	afx_msg void OnUpdateNoelementScale(CCmdUI* pCmdUI);
	afx_msg void OnElementScale();
	afx_msg void OnUpdateElementScale(CCmdUI* pCmdUI);
	afx_msg void OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags);
	afx_msg void OnDeleteByKey();
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
