#include "stdafx.h"
#include "CTextInSquare.h"
#include <ostream.h>


int CTextInSquare::KOLtextInSquare = 0;

CTextInSquare::~CTextInSquare()
{
	
}



CTextInSquare::CTextInSquare( CPoint c1,CPoint c2, COLORREF aColor,CString content):
	CShape(c1,c2, aColor),
	CSquare(c1,c2, aColor),
	CText(c1,c2, aColor, content), 
	IDtextInSquare(KOLtextInSquare)
{
	KOLtextInSquare++;
}


double CTextInSquare::S() const
{
    return CSquare::S();
}


double CTextInSquare::P() const
{
    return CSquare::P();
}


// Draw a CTextInSquare object
void CTextInSquare::Draw(CDC* pDC, CElement* pElement, int l)
{
   // Create a pen for this object and
   // initialize it to the object color and line width of 1 pixel
   CPen aPen;
   COLORREF aColor = m_Color;             // Initialize with element color
   if(this == pElement || l==1)                   // This element selected?
      aColor = SELECT_COLOR;              // Set highlight color
   if(!aPen.CreatePen(PS_SOLID, m_Pen, aColor))
   {  
	  // Pen creation failed. Abort the program.
      AfxMessageBox("Pen creation failed drawing a line", MB_OK);
      AfxAbort();
   }

   CPen* pOldPen = pDC->SelectObject(&aPen);  // Select the pen

   pDC->Rectangle(m_EnclosingRect);
   pDC->SetTextColor(aColor);

   pDC->SetTextAlign(TA_BOTTOM | TA_NOUPDATECP);

   int x =// p1.x;
	   this->m_EnclosingRect.TopLeft().x ;
	   //+ (abs(abs(this->m_EnclosingRect.TopLeft().x) - abs(this->m_EnclosingRect.BottomRight().x))/2);
   int y = this->m_EnclosingRect.TopLeft().y + (abs(abs(this->m_EnclosingRect.TopLeft().y) - abs(this->m_EnclosingRect.BottomRight().y))/2);
   pDC->TextOut( x, y, _content);


   pDC->SetTextColor(RGB(255, 255, 0));
   pDC->SetTextAlign(TA_BOTTOM | TA_NOUPDATECP);

   CString text = "";
   text += convertIntToChar(this->IDtext) ;
   text += "(";
   text += convertIntToChar(this->getID()) ;
   text += ")";

   pDC->TextOut(this->m_EnclosingRect.TopLeft().x, this->m_EnclosingRect.BottomRight().y , text);

   pDC->SelectObject(pOldPen);                // Restore the old pen
};



void CTextInSquare::Serialize(CArchive& ar)		
{
	CShape::Serialize(ar);
	if (ar.IsStoring())
	{		
		ar<<l<<p1.x<<p1.y<<p2.x<<p2.y<<_content;
	}else{
		ar>>l>>p1.x>>p1.y>>p2.x>>p2.y>>_content;
	}
};