#include "stdafx.h"
#include "iostream.h"
#include "CSquare.h"
#include <string>


int CSquare::KOLsquare = 0;

//constructors & desctuctors
CSquare::~CSquare(){
};

//getters
const double CSquare::getL()const {
	return this->l;
};

//setters
void CSquare::setL(double newL){
	this->l = newL;
};

//pure virtual function
double CSquare::S()const{
	return this->l * this->l;
};

double CSquare::P()const{
	return 4 * this->l;
};


CSquare::CSquare(CPoint p1, CPoint p2, COLORREF aColor):IDsquare(KOLsquare), CShape( p1,p2,  aColor){
	KOLsquare++;
		
	int l1 = abs(abs(this->m_EnclosingRect.TopLeft().x) - abs(this->m_EnclosingRect.BottomRight().x));
	int l2 = abs(abs(this->m_EnclosingRect.TopLeft().y) - abs(this->m_EnclosingRect.BottomRight().y));

	if(l1 < l2) l = l1; else l = l2;
	

   /*int x1 = this->m_EnclosingRect.BottomRight().x;
   int y1 = this->m_EnclosingRect.TopLeft().y;

   if(x1 > this->m_EnclosingRect.TopLeft().x+l) x1 = this->m_EnclosingRect.TopLeft().x+l;
   if(x1 < this->m_EnclosingRect.TopLeft().x-l) x1 = this->m_EnclosingRect.TopLeft().x-l;

   if(y1 > this->m_EnclosingRect.BottomRight().y + l) y1 = this->m_EnclosingRect.BottomRight().y + l;
   if(y1 < this->m_EnclosingRect.BottomRight().y - l) y1 = this->m_EnclosingRect.BottomRight().y - l;

   
	m_EnclosingRect = new CRect(this->m_EnclosingRect.TopLeft().x,   y1,
						         x1, this->m_EnclosingRect.BottomRight().y     );
								 */

    m_EnclosingRect = new CRect(p1.x,     p1.y,
						        p1.x + l, p1.y - l);
	m_EnclosingRect.NormalizeRect();

	
}


// Draw a CSquare object
void CSquare::Draw(CDC* pDC, CElement* pElement, int l)
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

   // Now draw the line

   pDC->SetTextColor(RGB(255, 255, 0));
   pDC->SetTextAlign(TA_BOTTOM | TA_NOUPDATECP);

   CString text = "";
   text += convertIntToChar(this->IDsquare) ;
   text +="(";
   text += convertIntToChar(this->getID()) ;
   text +=")";

   pDC->TextOut(this->m_EnclosingRect.TopLeft().x, this->m_EnclosingRect.BottomRight().y , text);

   pDC->Rectangle(m_EnclosingRect);

   pDC->SelectObject(pOldPen);                // Restore the old pen
}

void CSquare::Serialize(CArchive& ar)
{
	CShape::Serialize(ar);
	if (ar.IsStoring())
	{		
		ar << l;
	}else{
		ar >> l;
	}
};