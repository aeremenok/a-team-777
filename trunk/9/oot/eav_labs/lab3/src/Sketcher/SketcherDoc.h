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
class CSketcherDoc : public COleServerDoc
{
protected: // create from serialization only
	CSketcherDoc();
	DECLARE_DYNCREATE(CSketcherDoc)

// Attributes
public:
	CSketcherSrvrItem* GetEmbeddedItem()
		{ return (CSketcherSrvrItem*)COleServerDoc::GetEmbeddedItem(); }

protected:
	//##ModelId=476EA08C030E
    ShapeContainer* _shapeContainer;
    // Document size
	//##ModelId=475168590251
	CSize m_DocSize;
// Operations
public:
    // Retrieve the document size
    //##ModelId=475168590261
    CSize GetDocSize(){ return m_DocSize; }
	//##ModelId=476EA08C0312
    ShapeContainer* getShapeContainer() const { return _shapeContainer; }
    CRect GetDocExtent();                     // Get the bounding rectangle for the whole document

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherDoc)
	protected:
	virtual COleServerItem* OnGetEmbeddedItem();
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	virtual BOOL OnUpdateDocument();
	virtual void OnSetItemRects(LPCRECT lpPosRect, LPCRECT lpClipRect);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CSketcherDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	virtual CDocObjectServer* GetDocObjectServer(LPOLEDOCUMENTSITE pDocSite);

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherDoc)
	//##ModelId=473EDD6D02E2
	afx_msg void OnColorBlack();
	//##ModelId=473EDD6D02E4
	afx_msg void OnColorRed();
	//##ModelId=473EDD6D02E6
	afx_msg void OnElementLine();
	//##ModelId=473EDD6D02E8
	afx_msg void OnElementRectangle();
	//##ModelId=473EDD6D02EA
	afx_msg void OnUpdateColorBlack(CCmdUI* pCmdUI);
	//##ModelId=473EDD6D02F0
	afx_msg void OnUpdateColorRed(CCmdUI* pCmdUI);
	//##ModelId=473EDD6D02F3
	afx_msg void OnUpdateElementLine(CCmdUI* pCmdUI);
	//##ModelId=473EDD6D02F6
	afx_msg void OnUpdateElementRectangle(CCmdUI* pCmdUI);
	//##ModelId=474055EF0203
	afx_msg void OnElementOval();
	//##ModelId=474055EF0214
	afx_msg void OnUpdateElementOval(CCmdUI* pCmdUI);
	//##ModelId=474055EF0217
	afx_msg void OnUpdateElementText(CCmdUI* pCmdUI);
	//##ModelId=474055EF0223
	afx_msg void OnElementText();
	//##ModelId=474055EF0225
	afx_msg void OnElementTextInOval();
	//##ModelId=474055EF0227
	afx_msg void OnUpdateElementTextInOval(CCmdUI* pCmdUI);
	//##ModelId=475A821C002E
	afx_msg void OnElementRibble();
	//##ModelId=475A821C003F
	afx_msg void OnUpdateElementRibble(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

	// Generated OLE dispatch map functions
	//{{AFX_DISPATCH(CSketcherDoc)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	afx_msg BOOL deleteElement(LPCTSTR key);
	//##ModelId=476D9BD50150
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
