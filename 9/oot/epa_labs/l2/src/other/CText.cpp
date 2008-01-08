#include "stdafx.h"
#include "CText.h"
#include <ostream.h>


int CText::KOLtext = 0;

class ostream;
CText::CText(CPoint c1,CPoint c2, COLORREF aColor,CString content ):
		CShape(c1,c2, aColor), IDtext(KOLtext), p1(c1),p2(c2)
{
	KOLtext++;
    _content = content;
}


CText::~CText()
{

}



const CString CText::get__content() const
{
    return _content;
}


void CText::set__content(CString value)
{
    _content = value;
    return;
}


double CText::S() const
{
    return -1;
}
double CText::P() const
{
    return -1;
}


// Draw a CText object
void CText::Draw(CDC* pDC, CElement* pElement, int l)
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
}

//////////////////////////////////////////////////////////////////////////
void CText::Serialize(CArchive& ar)
{
	CShape::Serialize(ar);
	if (ar.IsStoring())
	{
		
		ar<<p1.x<<p1.y<<p2.x<<p2.y<<_content;
	}else{
		ar >> p1.x>>p1.y>>p2.x>>p2.y>> _content;
	}
};

