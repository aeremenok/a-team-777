// ChildFrm.cpp : implementation of the CChildFrame class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "ChildFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CChildFrame

IMPLEMENT_DYNCREATE(CChildFrame, CMDIChildWnd)

BEGIN_MESSAGE_MAP(CChildFrame, CMDIChildWnd)
	//{{AFX_MSG_MAP(CChildFrame)
	ON_WM_CREATE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CChildFrame construction/destruction

//##ModelId=473EDD6D037A
CChildFrame::CChildFrame()
{
	// TODO: add member initialization code here
	
}

//##ModelId=473EDD6D037E
CChildFrame::~CChildFrame()
{
}

//##ModelId=473EDD6D037B
BOOL CChildFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CMDIChildWnd::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CChildFrame diagnostics

#ifdef _DEBUG
//##ModelId=473EDD6D0380
void CChildFrame::AssertValid() const
{
	CMDIChildWnd::AssertValid();
}

//##ModelId=473EDD6D0382
void CChildFrame::Dump(CDumpContext& dc) const
{
	CMDIChildWnd::Dump(dc);
}

#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CChildFrame message handlers

//##ModelId=4751685903D8
int CChildFrame::OnCreate(LPCREATESTRUCT lpCreateStruct) 
{
	if (CMDIChildWnd::OnCreate(lpCreateStruct) == -1)
		return -1;
	
   // Create the status bar
   m_StatusBar.Create(this);

   // Work out the width of the text we want to display
   CRect textRect;
   CClientDC aDC(&m_StatusBar);
   aDC.SelectObject(m_StatusBar.GetFont());
   aDC.DrawText("View Scale:99", -1, textRect, DT_SINGLELINE|DT_CALCRECT);

   // Setup a part big enough to take the text
   int width = textRect.Width();
   m_StatusBar.GetStatusBarCtrl().SetParts(1, &width);

   // Initialize the text for the status bar
   m_StatusBar.GetStatusBarCtrl().SetText("View Scale:1", 0, 0);
	
	return 0;
}
