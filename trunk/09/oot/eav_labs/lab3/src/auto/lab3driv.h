// lab3driv.h : main header file for the LAB3DRIV application
//

#if !defined(AFX_LAB3DRIV_H__C24CB940_113C_4765_ADFF_B59BFCCA6071__INCLUDED_)
#define AFX_LAB3DRIV_H__C24CB940_113C_4765_ADFF_B59BFCCA6071__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CLab3drivApp:
// See lab3driv.cpp for the implementation of this class
//

class CLab3drivApp : public CWinApp
{
public:
	CLab3drivApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLab3drivApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CLab3drivApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_LAB3DRIV_H__C24CB940_113C_4765_ADFF_B59BFCCA6071__INCLUDED_)
