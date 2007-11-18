#if !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
#define AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextRequest.h : header file
/////////////////////////////////////////////////////////////////////////////
// TextRequest dialog
class TextRequest : public CDialog
{
// Construction
public:
	TextRequest(CWnd* pParent = NULL);   // standard constructor

    static CString* Text() { return m_Text; }
    static void Text(CString* val) { m_Text = val; }

    int Result() const { return m_Result; }
    void Result(int val) { m_Result = val; }

    static CString* getTextToShow();
// Dialog Data
	//{{AFX_DATA(TextRequest)
	enum { IDD = IDD_TEXTREQUEST_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(TextRequest)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
    static CString* m_Text;
    int m_Result;
	// Generated message map functions
	//{{AFX_MSG(TextRequest)
	afx_msg void OnUpdateEdit1();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
