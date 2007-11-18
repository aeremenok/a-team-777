// SketcherView.h : interface of the CSketcherView class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERVIEW_H__623441AD_57EA_11D0_9257_00201834E2A3__INCLUDED_

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
    CPoint m_FirstPoint;       // First point recorded for an element
    CPoint m_SecondPoint;      // Second point recorded for an element
    CElement* m_pTempElement;  // Pointer to temporary element
    CElement* m_pSelected;     // Currently selected element
    BOOL m_MoveMode;           // Move element flag
    CPoint m_CursorPos;        // Cursor position
    CPoint m_FirstPos;         // Original position in a move


// Operations
public:

protected:
	//##ModelId=473EDD6D0242
    CElement* CreateElement(); // Create a new element on the heap
    CElement* SelectElement(CPoint aPoint);           // Select an element
    void MoveElement(CClientDC& aDC, CPoint& point);  // Move an element

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherView)
	public:
	//##ModelId=473EDD6D0243
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	//##ModelId=473EDD6D0246
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	virtual void OnInitialUpdate();
	protected:
	//##ModelId=473EDD6D0253
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	//##ModelId=473EDD6D0261
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	//##ModelId=473EDD6D0265
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
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
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMove();
	afx_msg void OnSendtoback();
	afx_msg void OnDelete();
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
