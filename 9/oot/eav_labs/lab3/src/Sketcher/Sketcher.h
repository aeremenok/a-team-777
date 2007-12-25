// Sketcher.h : main header file for the SKETCHER application
//

#if !defined(AFX_SKETCHER_H__BB98257F_3521_46F9_BF9F_F9478781124B__INCLUDED_)
#define AFX_SKETCHER_H__BB98257F_3521_46F9_BF9F_F9478781124B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CSketcherApp:
// See Sketcher.cpp for the implementation of this class
//

//##ModelId=4770E2070026
class CSketcherApp : public CWinApp
{
public:
	//##ModelId=4770E2070028
	CSketcherApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherApp)
	public:
	//##ModelId=4770E2070029
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//##ModelId=4770E2070037
	COleTemplateServer m_server;
		// Server object for document creation
	//{{AFX_MSG(CSketcherApp)
	//##ModelId=4770E207003B
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHER_H__BB98257F_3521_46F9_BF9F_F9478781124B__INCLUDED_)
