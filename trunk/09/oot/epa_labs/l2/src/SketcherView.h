// SketcherView.h : interface of the CSketcherView class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_

#include "other\TStack.h"

class CSketcherView : public CScrollView
{
protected: // create from serialization only
	CSketcherView();
	int s_Pen;
	DECLARE_DYNCREATE(CSketcherView)

// Attributes
public:
	CSketcherDoc* GetDocument();

protected:
    CPoint m_FirstPoint;       // First point recorded for an element
    CPoint m_SecondPoint;      // Second point recorded for an element
    CElement* m_pTempElement;  // Pointer to temporary element
    CElement* m_pSelected;     // Currently selected element
    BOOL m_MoveMode;           // Move element flag
    CPoint m_CursorPos;        // Cursor position
    CPoint m_FirstPos;         // Original position in a move
    int m_Scale;               // Current view scale
	CString EnteredText;       // Text for CText
	int selectedEl;

// Operations
public:


protected:
    CElement* CreateElement(); // Create a new element on the heap
    CElement* SelectElement(CPoint aPoint);           // Select an element
    void MoveElement(CClientDC& aDC, CPoint& point);  // Move an element

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	virtual void OnInitialUpdate();
	virtual void OnPrepareDC(CDC* pDC, CPrintInfo* pInfo = NULL);
	protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
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
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMove();
	afx_msg void OnSendtoback();
	afx_msg void OnDelete();
	afx_msg void OnViewScale();
	afx_msg void OnInsertText();
	afx_msg void OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags);	
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
