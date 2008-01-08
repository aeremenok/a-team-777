#if !defined(AFX_INSERT_TEXT_H__7F50FAF2_323E_41CE_80AE_24BFD42EBF8B__INCLUDED_)
#define AFX_INSERT_TEXT_H__7F50FAF2_323E_41CE_80AE_24BFD42EBF8B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// Insert_Text.h : header file
//
#include "iostream.h"
#include "afxwin.h"
#include <string>
/////////////////////////////////////////////////////////////////////////////
// Insert_Text dialog

class Insert_Text : public CDialog
{
// Construction
public:
	Insert_Text(CWnd* pParent = NULL);   // standard constructor
	void setText(CString str);
	CString getText();
	CString insert_text;
// Dialog Data
	//{{AFX_DATA(Insert_Text)
	enum { IDD = IDD_DIALOG_TEXT };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(Insert_Text)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(Insert_Text)
	virtual void OnOK();
	virtual void OnCancel();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_INSERT_TEXT_H__7F50FAF2_323E_41CE_80AE_24BFD42EBF8B__INCLUDED_)
