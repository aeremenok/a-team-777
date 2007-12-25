// IpFrame.h : interface of the CInPlaceFrame class
//

#if !defined(AFX_IPFRAME_H__97B92561_B318_48BC_9DD6_6A332931DB3E__INCLUDED_)
#define AFX_IPFRAME_H__97B92561_B318_48BC_9DD6_6A332931DB3E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//##ModelId=4770E20800B3
class CInPlaceFrame : public COleDocIPFrameWnd
{
	DECLARE_DYNCREATE(CInPlaceFrame)
public:
	//##ModelId=4770E20800B5
	CInPlaceFrame();

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CInPlaceFrame)
	//##ModelId=4770E20800B6
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=4770E20800C4
	virtual ~CInPlaceFrame();
#ifdef _DEBUG
	//##ModelId=4770E20800C6
	virtual void AssertValid() const;
	//##ModelId=4770E20800C8
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	//##ModelId=4770E20800D3
	COleDropTarget	m_dropTarget;
	//##ModelId=4770E20800D8
	COleResizeBar   m_wndResizeBar;

// Generated message map functions
protected:
	//{{AFX_MSG(CInPlaceFrame)
	//##ModelId=4770E20800E1
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_IPFRAME_H__97B92561_B318_48BC_9DD6_6A332931DB3E__INCLUDED_)
