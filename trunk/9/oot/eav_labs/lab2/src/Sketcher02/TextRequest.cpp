// TextRequest.cpp : implementation file
//
//////////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"
#include "TextRequest.h"
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// TextRequest dialog
CString* TextRequest::m_Text = new CString();
//////////////////////////////////////////////////////////////////////////
TextRequest::TextRequest(CWnd* pParent /*=NULL*/)
	: CDialog(TextRequest::IDD, pParent)
{
    m_Result = 1;
    
	//{{AFX_DATA_INIT(TextRequest)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void TextRequest::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(TextRequest)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(TextRequest, CDialog)
	//{{AFX_MSG_MAP(TextRequest)
	ON_EN_UPDATE(IDC_EDIT1, OnUpdateEdit1)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// TextRequest message handlers

void TextRequest::OnUpdateEdit1() 
{
}

void TextRequest::OnOK() 
{
    CString* const cs = new CString();
    GetDlgItemText(IDC_EDIT1, *cs);
    Text(cs);
	
    m_Result = 0;

	CDialog::OnOK();
}
//////////////////////////////////////////////////////////////////////////
CString* TextRequest::getTextToShow()
{
    bool showFlag = TRUE;
    CString* cs = NULL;
    TextRequest* dialog = new TextRequest();
    while (dialog->Result())
    {
        int res = dialog->DoModal();
        
        cs = TextRequest::Text();
    }	
    return cs;    
}
//////////////////////////////////////////////////////////////////////////