// ChildFrm.h : interface of the CChildFrame class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CHILDFRM_H__623441A9_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_CHILDFRM_H__623441A9_57EA_11D0_9257_00201834E2A3__INCLUDED_

//##ModelId=473EDD6D0372
class CChildFrame : public CMDIChildWnd
{
	DECLARE_DYNCREATE(CChildFrame)
public:
	//##ModelId=473EDD6D037A
	CChildFrame();

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CChildFrame)
	//##ModelId=473EDD6D037B
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=473EDD6D037E
	virtual ~CChildFrame();
#ifdef _DEBUG
	//##ModelId=473EDD6D0380
	virtual void AssertValid() const;
	//##ModelId=473EDD6D0382
	virtual void Dump(CDumpContext& dc) const;
#endif

// Generated message map functions
protected:
	//{{AFX_MSG(CChildFrame)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CHILDFRM_H__623441A9_57EA_11D0_9257_00201834E2A3__INCLUDED_)
