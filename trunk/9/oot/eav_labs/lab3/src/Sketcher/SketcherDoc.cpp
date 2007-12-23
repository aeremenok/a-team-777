// SketcherDoc.cpp : implementation of the CSketcherDoc class
//

#include "stdafx.h"
#include "Sketcher.h"

#include "SketcherDoc.h"
#include "CntrItem.h"
#include "SrvrItem.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc

IMPLEMENT_DYNCREATE(CSketcherDoc, COleServerDoc)

BEGIN_MESSAGE_MAP(CSketcherDoc, COleServerDoc)
	//{{AFX_MSG_MAP(CSketcherDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
	// Enable default OLE container implementation
	ON_UPDATE_COMMAND_UI(ID_EDIT_PASTE, COleServerDoc::OnUpdatePasteMenu)
	ON_UPDATE_COMMAND_UI(ID_EDIT_PASTE_LINK, COleServerDoc::OnUpdatePasteLinkMenu)
	ON_UPDATE_COMMAND_UI(ID_OLE_EDIT_CONVERT, COleServerDoc::OnUpdateObjectVerbMenu)
	ON_COMMAND(ID_OLE_EDIT_CONVERT, COleServerDoc::OnEditConvert)
	ON_UPDATE_COMMAND_UI(ID_OLE_EDIT_LINKS, COleServerDoc::OnUpdateEditLinksMenu)
	ON_COMMAND(ID_OLE_EDIT_LINKS, COleServerDoc::OnEditLinks)
	ON_UPDATE_COMMAND_UI_RANGE(ID_OLE_VERB_FIRST, ID_OLE_VERB_LAST, COleServerDoc::OnUpdateObjectVerbMenu)
	ON_COMMAND(ID_FILE_SEND_MAIL, OnFileSendMail)
	ON_UPDATE_COMMAND_UI(ID_FILE_SEND_MAIL, OnUpdateFileSendMail)
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(CSketcherDoc, COleServerDoc)
	//{{AFX_DISPATCH_MAP(CSketcherDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//      DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_DISPATCH_MAP
END_DISPATCH_MAP()

// Note: we add support for IID_ISketcher to support typesafe binding
//  from VBA.  This IID must match the GUID that is attached to the 
//  dispinterface in the .ODL file.

// {209B83CE-2FA3-4A8C-9C22-869FF0BA2652}
static const IID IID_ISketcher =
{ 0x209b83ce, 0x2fa3, 0x4a8c, { 0x9c, 0x22, 0x86, 0x9f, 0xf0, 0xba, 0x26, 0x52 } };

BEGIN_INTERFACE_MAP(CSketcherDoc, COleServerDoc)
	INTERFACE_PART(CSketcherDoc, IID_ISketcher, Dispatch)
END_INTERFACE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc construction/destruction

CSketcherDoc::CSketcherDoc()
{
	// Use OLE compound files
	EnableCompoundFile();

	// TODO: add one-time construction code here

	EnableAutomation();

	AfxOleLockApp();
}

CSketcherDoc::~CSketcherDoc()
{
	AfxOleUnlockApp();
}

BOOL CSketcherDoc::OnNewDocument()
{
	if (!COleServerDoc::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc server implementation

COleServerItem* CSketcherDoc::OnGetEmbeddedItem()
{
	// OnGetEmbeddedItem is called by the framework to get the COleServerItem
	//  that is associated with the document.  It is only called when necessary.

	CSketcherSrvrItem* pItem = new CSketcherSrvrItem(this);
	ASSERT_VALID(pItem);
	return pItem;
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc Active Document server implementation

CDocObjectServer *CSketcherDoc::GetDocObjectServer(LPOLEDOCUMENTSITE pDocSite)
{
	return new CDocObjectServer(this, pDocSite);
}



/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc serialization

void CSketcherDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}

	// Calling the base class COleServerDoc enables serialization
	//  of the container document's COleClientItem objects.
	COleServerDoc::Serialize(ar);
}

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc diagnostics

#ifdef _DEBUG
void CSketcherDoc::AssertValid() const
{
	COleServerDoc::AssertValid();
}

void CSketcherDoc::Dump(CDumpContext& dc) const
{
	COleServerDoc::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CSketcherDoc commands
