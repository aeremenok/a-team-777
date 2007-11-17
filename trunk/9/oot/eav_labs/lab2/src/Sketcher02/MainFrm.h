// MainFrm.h : interface of the CMainFrame class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_MAINFRM_H__623441A7_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_MAINFRM_H__623441A7_57EA_11D0_9257_00201834E2A3__INCLUDED_

//##ModelId=473EDD6D030D
class CMainFrame : public CMDIFrameWnd
{
	DECLARE_DYNAMIC(CMainFrame)
public:
	//##ModelId=473EDD6D030F
	CMainFrame();

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMainFrame)
	//##ModelId=473EDD6D0310
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=473EDD6D0313
	virtual ~CMainFrame();
#ifdef _DEBUG
	//##ModelId=473EDD6D0315
	virtual void AssertValid() const;
	//##ModelId=473EDD6D0317
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:  // control bar embedded members
	//##ModelId=473EDD6D031D
	CStatusBar  m_wndStatusBar;
	//##ModelId=473EDD6D0322
	CToolBar    m_wndToolBar;

// Generated message map functions
protected:
	//{{AFX_MSG(CMainFrame)
	//##ModelId=473EDD6D0326
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MAINFRM_H__623441A7_57EA_11D0_9257_00201834E2A3__INCLUDED_)
