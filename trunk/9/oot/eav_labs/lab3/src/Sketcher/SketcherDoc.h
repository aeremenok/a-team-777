// SketcherDoc.h : interface of the CSketcherDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERDOC_H__7645EBAE_C2DE_4989_9BCD_03B688FC673F__INCLUDED_)
#define AFX_SKETCHERDOC_H__7645EBAE_C2DE_4989_9BCD_03B688FC673F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
//////////////////////////////////////////////////////////////////////////
#include "ShapeContainer.h"
//////////////////////////////////////////////////////////////////////////
#define HINT_UPDATE_WINDOW  0
#define HINT_UPDATE_ITEM    1
//////////////////////////////////////////////////////////////////////////
class CSketcherSrvrItem;
//class CSketcherView;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060352
class CSketcherDoc : public COleServerDoc
{
protected: // create from serialization only
	//##ModelId=4770E2060354
	CSketcherDoc();
	DECLARE_DYNCREATE(CSketcherDoc)

// Attributes
public:
	//##ModelId=4770E2060355
	CSketcherSrvrItem* GetEmbeddedItem()
		{ return (CSketcherSrvrItem*)COleServerDoc::GetEmbeddedItem(); }

protected:
	//##ModelId=4770E2060363
    ShapeContainer* _shapeContainer;
    // Document size
	//##ModelId=4770E2060367
	CSize m_DocSize;
// Operations
public:
    // Retrieve the document size
    //##ModelId=4770E2060368
    CSize GetDocSize(){ return m_DocSize; }
	//##ModelId=4770E2060372
    ShapeContainer* getShapeContainer() const { return _shapeContainer; }
	//##ModelId=4770E2060374
    CRect GetDocExtent();                     // Get the bounding rectangle for the whole document

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherDoc)
	public:
	//##ModelId=4770E2060375
	virtual BOOL OnNewDocument();
	//##ModelId=4770E2060377
	virtual void Serialize(CArchive& ar);
	//##ModelId=4770E2060381
	virtual BOOL OnUpdateDocument();
	//##ModelId=4770E2060383
	virtual void OnSetItemRects(LPCRECT lpPosRect, LPCRECT lpClipRect);
	protected:
	//##ModelId=4770E2060387
	virtual COleServerItem* OnGetEmbeddedItem();
	//}}AFX_VIRTUAL

// Implementation
public:
	//##ModelId=4770E2060391
	virtual ~CSketcherDoc();
#ifdef _DEBUG
	//##ModelId=4770E2060393
	virtual void AssertValid() const;
	//##ModelId=4770E2060395
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	//##ModelId=4770E2060398
	virtual CDocObjectServer* GetDocObjectServer(LPOLEDOCUMENTSITE pDocSite);

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherDoc)
	//##ModelId=4770E20603A1
	afx_msg void OnColorBlack();
	//##ModelId=4770E20603A3
	afx_msg void OnColorRed();
	//##ModelId=4770E20603A5
	afx_msg void OnElementLine();
	//##ModelId=4770E20603A7
	afx_msg void OnElementRectangle();
	//##ModelId=4770E20603B1
	afx_msg void OnUpdateColorBlack(CCmdUI* pCmdUI);
	//##ModelId=4770E20603B4
	afx_msg void OnUpdateColorRed(CCmdUI* pCmdUI);
	//##ModelId=4770E20603C0
	afx_msg void OnUpdateElementLine(CCmdUI* pCmdUI);
	//##ModelId=4770E20603C3
	afx_msg void OnUpdateElementRectangle(CCmdUI* pCmdUI);
	//##ModelId=4770E20603CF
	afx_msg void OnElementOval();
	//##ModelId=4770E20603D1
	afx_msg void OnUpdateElementOval(CCmdUI* pCmdUI);
	//##ModelId=4770E20603D4
	afx_msg void OnUpdateElementText(CCmdUI* pCmdUI);
	//##ModelId=4770E20603DF
	afx_msg void OnElementText();
	//##ModelId=4770E20603E1
	afx_msg void OnElementTextInOval();
	//##ModelId=4770E20603E3
	afx_msg void OnUpdateElementTextInOval(CCmdUI* pCmdUI);
	//##ModelId=4770E2070007
	afx_msg void OnElementRibble();
	//##ModelId=4770E2070009
	afx_msg void OnUpdateElementRibble(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

	// Generated OLE dispatch map functions
	//{{AFX_DISPATCH(CSketcherDoc)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//##ModelId=4770E207000C
	afx_msg BOOL deleteElement(LPCTSTR key);
	//##ModelId=4770E2070017
	afx_msg void showWindow();
	//##ModelId=476D9BD50158
	//afx_msg void DrawLine(float x1, float y1, float x2, float y2, LPCTSTR text);
	//}}AFX_DISPATCH
	DECLARE_DISPATCH_MAP()
	DECLARE_INTERFACE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERDOC_H__7645EBAE_C2DE_4989_9BCD_03B688FC673F__INCLUDED_)
