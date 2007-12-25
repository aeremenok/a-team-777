#if !defined(AFX_SCALEDIALOG_H__17891A67_636E_4CF2_A1B9_7A0E92605F7E__INCLUDED_)
#define AFX_SCALEDIALOG_H__17891A67_636E_4CF2_A1B9_7A0E92605F7E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ScaleDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CScaleDialog dialog

//##ModelId=4770E2070289
class CScaleDialog : public CDialog
{
// Construction
public:
	//##ModelId=4770E2070297
	CScaleDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CScaleDialog)
	enum { IDD = IDD_SCALE_DLG };
	//##ModelId=4770E2070299
	int		m_Scale;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CScaleDialog)
	protected:
	//##ModelId=4770E207029A
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CScaleDialog)
	//##ModelId=4770E207029D
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SCALEDIALOG_H__17891A67_636E_4CF2_A1B9_7A0E92605F7E__INCLUDED_)
