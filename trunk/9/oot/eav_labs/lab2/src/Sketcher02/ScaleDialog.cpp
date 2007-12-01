// ScaleDialog.cpp : implementation file
//

#include "stdafx.h"
#include "Sketcher.h"
#include "ScaleDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CScaleDialog dialog


//##ModelId=4751685902A1
CScaleDialog::CScaleDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CScaleDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CScaleDialog)
	m_Scale = 0;
	//}}AFX_DATA_INIT
}


//##ModelId=4751685902B0
void CScaleDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CScaleDialog)
	DDX_Text(pDX, IDC_SCALE, m_Scale);
	DDV_MinMaxInt(pDX, m_Scale, 1, 8);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CScaleDialog, CDialog)
	//{{AFX_MSG_MAP(CScaleDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CScaleDialog message handlers

//##ModelId=4751685902B3
BOOL CScaleDialog::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
    // First get a pointer to the spin control
    CSpinButtonCtrl* pSpin;
    pSpin = (CSpinButtonCtrl*)GetDlgItem(IDC_SPIN_SCALE);

    // If you have not checked the auto buddy option in
    // the spin control's properties, set the buddy control here

    // Set the spin control range
    pSpin->SetRange(1, 8);
	
    return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}
