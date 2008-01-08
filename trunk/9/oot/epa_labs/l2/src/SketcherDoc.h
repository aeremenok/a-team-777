// SketcherDoc.h : interface of the CSketcherDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
#define AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_

#include "other\TStack.h"

class CSketcherDoc : public CDocument
{
protected: // create from serialization only
	CSketcherDoc();
	DECLARE_DYNCREATE(CSketcherDoc)

// Attributes
public:

protected:
	COLORREF m_Color;		// Current drawing color
	WORD m_Element;			// Current element type
    TStack<CElement> m_ElementList;  // Element list
    CSize m_DocSize;        // Document size

// Operations
public:
   WORD GetElementType()                     // Get the element type
      { return m_Element; }
   COLORREF GetElementColor()                // Get the element color
      { return m_Color; }
   void AddElement(CElement* pElement);       // Add an element to the list
     
   IIterator<CElement>* getIterator(){return m_ElementList.getIterator();}
   //POSITION GetListHeadPosition()            // Return list head POSITION value
   //   { return m_ElementList.getFirst(); }
   //CElement* GetNext(POSITION& aPos)         // Return current element pointer
   //   { return m_ElementList.GetNext(aPos); }
   //POSITION GetListTailPosition()            // Return list tail POSITION value
   //   { return m_ElementList.GetTailPosition(); }
   //CElement* GetPrev(POSITION& aPos)         // Return current element pointer
   //   { return m_ElementList.GetPrev(aPos); }
   CSize GetDocSize()                        // Retrieve the document size
	  { return m_DocSize; }
   void DeleteElement(CElement* pElement);   // Delete an element
   void SendToBack(CElement* pElement);      // Send element to start of list

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSketcherDoc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CSketcherDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CSketcherDoc)
	afx_msg void OnColorBlack();
	afx_msg void OnColorRed();

	afx_msg void OnElementSquare();
	afx_msg void OnElementText();
	afx_msg void OnElementSquareText();
	afx_msg void OnElementDiamond();
	
	afx_msg void OnUpdateColorBlack(CCmdUI* pCmdUI);
	afx_msg void OnUpdateColorRed(CCmdUI* pCmdUI);

	afx_msg void OnUpdateElementSquare(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementText(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementSquareText(CCmdUI* pCmdUI);
	afx_msg void OnUpdateElementDiamond(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SKETCHERDOC_H__623441AB_57EA_11D0_9257_00201834E2A3__INCLUDED_)
