// SketcherDoc.h : interface of the CSketcherDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_


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
	//##ModelId=473EDD6D02C2
	COLORREF m_Color;		// Current drawing color
	//##ModelId=473EDD6D02CE
	WORD m_Element;			// Current element type

// Operations
public:
	//##ModelId=473EDD6D02CF
	WORD GetElementType()        // Get the element type
	{
		return m_Element;
	}
   
	//##ModelId=473EDD6D02D0
	COLORREF GetElementColor()   // Get the element color
	{
		return m_Color;
	}

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
	afx_msg void OnColorBlack();
	afx_msg void OnColorRed();
	afx_msg void OnElementLine();
	afx_msg void OnElementRectangle();
	afx_msg void OnUpdateColorBlack(CCmdUI* pCmdUI);
	afx_msg void OnUpdateColorRed(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementLine(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementRectangle(CCmdUI* pCmdUI);
	afx_msg void OnElementOval();
	afx_msg void OnUpdateElementOval(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementText(CCmdUI* pCmdUI);
	afx_msg void OnElementText();
	afx_msg void OnElementTextInOval();
	afx_msg void OnUpdateElementTextInOval(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
