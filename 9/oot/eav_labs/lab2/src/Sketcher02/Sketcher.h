// Sketcher.h : main header file for the SKETCHER application
//

#if !defined(AFX_SKETCHER_H__623441A3_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHER_H__623441A3_57EA_11D0_9257_00201834E2A3__INCLUDED_

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CSketcherApp:
// See Sketcher.cpp for the implementation of this class
//

#include "OurConstants.h"

class CSketcherApp : public CWinApp
{
public:
	CSketcherApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CSketcherApp)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHER_H__623441A3_57EA_11D0_9257_00201834E2A3__INCLUDED_)
