// SketcherDoc.h : interface of the CSketcherDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_
//////////////////////////////////////////////////////////////////////////
#include "ShapeContainer.h"
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDD6D02BF
class CSketcherDoc : public CDocument
{
protected: // create from serialization only
	//##ModelId=473EDD6D02C1
	CSketcherDoc();
	DECLARE_DYNCREATE(CSketcherDoc)

// Attributes
public:

protected:
    ShapeContainer* _shapeContainer;
    // Document size
	//##ModelId=475168590251
	CSize m_DocSize;
// Operations
public:
    // Retrieve the document size
    //##ModelId=475168590261
    CSize GetDocSize(){ return m_DocSize; }
    ShapeContainer* getShapeContainer() const { return _shapeContainer; }
protected:
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherDoc)
	public:
	//##ModelId=473EDD6D02D1
	virtual BOOL OnNewDocument();
	//##ModelId=473EDD6D02D3
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL
// Implementation
public:
	//##ModelId=473EDD6D02D6
	virtual ~CSketcherDoc();
#ifdef _DEBUG
	//##ModelId=473EDD6D02D8
	virtual void AssertValid() const;
	//##ModelId=473EDD6D02DF
	virtual void Dump(CDumpContext& dc) const;
#endif
protected:
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
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
