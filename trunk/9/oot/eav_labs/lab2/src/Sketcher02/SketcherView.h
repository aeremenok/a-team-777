// SketcherView.h : interface of the CSketcherView class
/////////////////////////////////////////////////////////////////////////////
#if !defined(AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_
//////////////////////////////////////////////////////////////////////////
#include "ShapeHandler.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDD6D01B9
class CSketcherView : public CScrollView
{
protected: // create from serialization only
	//##ModelId=473EDD6D01C6
	CSketcherView();
	DECLARE_DYNCREATE(CSketcherView)

// Attributes
public:
	//##ModelId=473EDD6D01D4
	CSketcherDoc* GetDocument();
protected:
    ShapeHandler* _handler;
// Operations
public:
protected:
	//##ModelId=4751685901F4
    void ResetScrollSizes();
    // вызывает диалог задания масштаба
	//##ModelId=4751685901F5
    void requestScale();
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	//##ModelId=473EDD6D0243
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	//##ModelId=4751685901F6
    virtual void OnPrepareDC(CDC* pDC, CPrintInfo* pInfo = NULL);
	//##ModelId=473EDD6D0246
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//##ModelId=4741F10E0223
	virtual void OnInitialUpdate();
	protected:
	//##ModelId=473EDD6D0253
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	//##ModelId=473EDD6D0261
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	//##ModelId=473EDD6D0265
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//##ModelId=4741F10E0225
	virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=473EDD6D0273
	virtual ~CSketcherView();
#ifdef _DEBUG
	//##ModelId=473EDD6D0275
	virtual void AssertValid() const;
	//##ModelId=473EDD6D0280
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherView)
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
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_)
