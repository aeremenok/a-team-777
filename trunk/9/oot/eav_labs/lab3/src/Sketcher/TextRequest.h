#if !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
#define AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextRequest.h : header file
/////////////////////////////////////////////////////////////////////////////
// TextRequest dialog
//##ModelId=4770E2060027
class TextRequest : public CDialog
{
// Construction
public:
	//##ModelId=4770E2060037
	TextRequest(CWnd* pParent = NULL);   // standard constructor

	//##ModelId=4770E2060039
    static CString* Text() { return m_Text; }
	//##ModelId=4770E206003B
    static void Text(CString* val) { m_Text = val; }

	//##ModelId=4770E206003E
    int Result() const { return m_Result; }
	//##ModelId=4770E2060045
    void Result(int val) { m_Result = val; }

	//##ModelId=4770E2060047
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
	//##ModelId=4770E2060049
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//##ModelId=4770E206004C
    static CString* m_Text;
	//##ModelId=4770E2060093
    int m_Result;
	// Generated message map functions
	//{{AFX_MSG(TextRequest)
	//##ModelId=4770E2060094
	afx_msg void OnUpdateEdit1();
	//##ModelId=4770E2060096
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTREQUEST_H__9D22BF47_678D_48D0_810A_B5D9273D7192__INCLUDED_)
