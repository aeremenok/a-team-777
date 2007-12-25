// ChildFrm.h : interface of the CChildFrame class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CHILDFRM_H__5A606600_C10B_4FB0_87C6_C1C31FCF8CBB__INCLUDED_)
#define AFX_CHILDFRM_H__5A606600_C10B_4FB0_87C6_C1C31FCF8CBB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


//##ModelId=4770E20803CF
class CChildFrame : public CMDIChildWnd
{
	DECLARE_DYNCREATE(CChildFrame)
public:
	//##ModelId=4770E20803D1
	CChildFrame();

// Attributes
public:
	//##ModelId=4770E20803D3
   CStatusBar m_StatusBar;    // Status bar object

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CChildFrame)
	//##ModelId=4770E20803DF
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=4770E20803E2
	virtual ~CChildFrame();
#ifdef _DEBUG
	//##ModelId=4770E20803E4
	virtual void AssertValid() const;
	//##ModelId=4770E20803E6
	virtual void Dump(CDumpContext& dc) const;
#endif

// Generated message map functions
protected:
	//{{AFX_MSG(CChildFrame)
	//##ModelId=4770E2090009
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CHILDFRM_H__5A606600_C10B_4FB0_87C6_C1C31FCF8CBB__INCLUDED_)
