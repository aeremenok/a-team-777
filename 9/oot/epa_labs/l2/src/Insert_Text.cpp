// Insert_Text.cpp : implementation file
//

#include "stdafx.h"
#include "Sketcher.h"
#include "Insert_Text.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Insert_Text dialog


Insert_Text::Insert_Text(CWnd* pParent /*=NULL*/)
	: CDialog(Insert_Text::IDD, pParent)
{
	//{{AFX_DATA_INIT(Insert_Text)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void Insert_Text::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(Insert_Text)
	DDX_Text(pDX, IDC_TEXT, insert_text);
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(Insert_Text, CDialog)
	//{{AFX_MSG_MAP(Insert_Text)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Insert_Text message handlers

void Insert_Text::OnOK() 
{
	// TODO: Add extra validation here
	
	CDialog::OnOK();
}

void Insert_Text::OnCancel() 
{
	// TODO: Add extra cleanup here
	
	CDialog::OnCancel();
}
void Insert_Text::setText(CString str)
{
/*	CEdit * pSpin;
    pSpin = (CEdit*)GetDlgItem(IDC_TEXT);

	pSpin->SetDlgItemText(IDC_TEXT,str);
	*/
}

CString Insert_Text::getText(){
	/*CEdit * pSpin;
    pSpin = (CEdit*)GetDlgItem(IDC_TEXT);

	char* s = new char[20];
	pSpin->GetDlgItemText(IDC_TEXT,s,20);
	return s;
	*/
	return insert_text;
}

BOOL Insert_Text::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	// TODO: Add extra initialization here
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}
