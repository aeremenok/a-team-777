// lab3drivDlg.cpp : implementation file
//

#include "stdafx.h"
#include "lab3driv.h"
#include "lab3drivDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif
//////////////////////////////////////////////////////////////////////////
enum {RECTANGLE, OVAL, TEXT, TEXT_IN_OVAL};
/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CLab3drivDlg dialog

CLab3drivDlg::CLab3drivDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CLab3drivDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CLab3drivDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CLab3drivDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CLab3drivDlg)
	DDX_Control(pDX, IDC_EDIT7, m_Text);
	DDX_Control(pDX, IDC_EDIT5, m_Width);
	DDX_Control(pDX, IDC_EDIT3, m_Height);
	DDX_Control(pDX, IDC_EDIT4, m_Y);
	DDX_Control(pDX, IDC_EDIT1, m_X);
	DDX_Control(pDX, IDC_EDIT6, m_sizeControl);
	DDX_Control(pDX, IDC_EDIT2, m_EditControl);
    DDX_Control(pDX, IDC_COMBO_TYPE, _typeSelect);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CLab3drivDlg, CDialog)
	//{{AFX_MSG_MAP(CLab3drivDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_BUTTON1, OnButton1)
	ON_BN_CLICKED(IDC_BUTTON2, OnButton2)
	ON_BN_CLICKED(IDC_BUTTON3, OnButton3)
	ON_BN_CLICKED(IDC_BUTTON4, OnButton4)
	ON_CBN_SELENDOK(IDC_COMBO_TYPE, OnSelendokComboType)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CLab3drivDlg message handlers

BOOL CLab3drivDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	m_initialized = false;

	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CLab3drivDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CLab3drivDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CLab3drivDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CLab3drivDlg::OnButton1() 
{
	if (!m_initialized)
		return;
	CString strText;
	m_EditControl.GetWindowText(strText);
	m_oleDriver.deleteElement(strText);
}

void CLab3drivDlg::OnButton2() 
{
	if (!m_initialized)
		return;
	short size = m_oleDriver.getHashSize();
	char c[5];
	itoa(size, c, 10);
	m_sizeControl.SetWindowText(c);
}

void CLab3drivDlg::OnButton3() 
{
	if (!m_initialized)
		return;
	CString x1s, y1s, x2s, y2s, key;
	float x1, y1, x2, y2;
	m_X.GetWindowText(x1s);
	m_Width.GetWindowText(x2s);
	m_Y.GetWindowText(y1s);
	m_Width.GetWindowText(y2s);
	m_Text.GetWindowText(key);
// 	x1 = atof(x1s);
// 	x2 = atof(x2s);
// 	y1 = atof(y1s);
// 	y2 = atof(y2s);
	m_oleDriver.drawLine(x1, y1, x2, y2, key);
}

void CLab3drivDlg::OnButton4() 
{
	// TODO: Add your control notification handler code here

	if (!m_oleDriver.CreateDispatch(_T("Sketcher.Document")))
	{
		AfxMessageBox("Cannot create OLEApp.Document");
		return;  // fail
	}
	m_oleDriver.showWindow();
	m_initialized = true;
}

void CLab3drivDlg::OnSelendokComboType() 
{
	_type = _typeSelect.GetCurSel();
    switch(_type)
    {
    case RECTANGLE:
        m_X.SetReadOnly(false);
        m_Y.SetReadOnly(false);
        m_Width.SetReadOnly(false);
        m_Height.SetReadOnly(false);

        m_Text.SetReadOnly(true);
    	break;
    case OVAL:
        m_X.SetReadOnly(false);
        m_Y.SetReadOnly(false);
        m_Width.SetReadOnly(false);
        m_Height.SetReadOnly(false);

        m_Text.SetReadOnly(true);
    	break;
    case TEXT:
        m_X.SetReadOnly(false);
        m_Y.SetReadOnly(false);
        m_Width.SetReadOnly(true);
        m_Height.SetReadOnly(true);

        m_Text.SetReadOnly(false);
        break;
    case TEXT_IN_OVAL:
        m_X.SetReadOnly(false);
        m_Y.SetReadOnly(false);
        m_Width.SetReadOnly(false);
        m_Height.SetReadOnly(false);

        m_Text.SetReadOnly(false);
        break;
    default:
        break;
    }
}
