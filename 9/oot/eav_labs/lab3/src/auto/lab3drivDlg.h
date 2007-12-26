// lab3drivDlg.h : header file
//

#if !defined(AFX_LAB3DRIVDLG_H__864CD75E_DD5C_48C6_A01A_BC456979FED6__INCLUDED_)
#define AFX_LAB3DRIVDLG_H__864CD75E_DD5C_48C6_A01A_BC456979FED6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "OleDriver.h"

/////////////////////////////////////////////////////////////////////////////
// CLab3drivDlg dialog

class CLab3drivDlg : public CDialog
{
private:
	OleDriver m_oleDriver;
	int m_initialized;
    int _type;
// Construction
public:
	CLab3drivDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CLab3drivDlg)
	enum { IDD = IDD_LAB3DRIV_DIALOG };
	CEdit	m_id2;
	CEdit	m_id1;
	CEdit	m_Text;
	CEdit	m_Width;
	CEdit	m_Height;
	CEdit	m_Y;
	CEdit	m_X;
	CEdit	ID_to_delete;
    CComboBox _typeSelect;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLab3drivDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CLab3drivDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnButton1();
	afx_msg void OnButton3();
	afx_msg void OnButton4();
	afx_msg void OnSelendokComboType();
	afx_msg void OnLink();
	afx_msg void OnUnLink();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_LAB3DRIVDLG_H__864CD75E_DD5C_48C6_A01A_BC456979FED6__INCLUDED_)
