#if !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
#define AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextRequest.h : header file
/////////////////////////////////////////////////////////////////////////////
// TextRequest dialog
//##ModelId=474055EE03C8
class TextRequest : public CDialog
{
// Construction
public:
	//##ModelId=474055EE03D8
	TextRequest(CWnd* pParent = NULL);   // standard constructor

	//##ModelId=474055EE03DA
    static CString* Text() { return m_Text; }
	//##ModelId=474055EE03DC
    static void Text(CString* val) { m_Text = val; }

	//##ModelId=474055EF0000
    int Result() const { return m_Result; }
	//##ModelId=474055EF0002
    void Result(int val) { m_Result = val; }

	//##ModelId=474055EF0004
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
	//##ModelId=474055EF0006
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//##ModelId=474055EF0011
    static CString* m_Text;
	//##ModelId=474055EF005D
    int m_Result;
	// Generated message map functions
	//{{AFX_MSG(TextRequest)
	//##ModelId=474055EF005E
	afx_msg void OnUpdateEdit1();
	//##ModelId=474055EF006D
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
