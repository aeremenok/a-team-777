#include "stdafx.h"
#include "afxwin.h"
#include "..\stdafx.h"


#include "iostream.h"
#include "math.h"


#include "..\OurConstants.h"
#include "..\Elements.h"

#include "CDiamond.h"



int CDiamond::KOLdiamond = 0;

//getters
const double CDiamond::getL()const {
	return this->l;
};
const double CDiamond::getU()const {
	return this->u;
};
//setters
void CDiamond::setL(int newL){
	this->l = newL;
};
void CDiamond::setU(double newU){
	this->u = newU;
};


//pure virtual function
double CDiamond::S()const{
	return this->l * this->l * sin(this->u);
};

double CDiamond::P()const{
	return 4 * this->l;
};

CDiamond::CDiamond(CPoint p1, CPoint p2, COLORREF aColor) : CShape( p1, p2,  aColor), IDdiamond(KOLdiamond) {
	KOLdiamond++;

	t1 = p1;
	t2 = p2;
    
	int l1 = abs(abs(this->m_EnclosingRect.TopLeft().x) - abs(this->m_EnclosingRect.BottomRight().x));
	int l2 = abs(abs(this->m_EnclosingRect.TopLeft().y) - abs(this->m_EnclosingRect.BottomRight().y));

    m_EnclosingRect = new CRect(p1.x,     p1.y,
						        p1.x + l1, p1.y - l2);
	

    double h = abs(abs(m_EnclosingRect.TopLeft().y) - abs(m_EnclosingRect.BottomRight().y ));
    double l = abs(abs(m_EnclosingRect.TopLeft().x) - abs(m_EnclosingRect.BottomRight().x ));
	double a = sqrt(h*h+l*l);


    if(l==0 || a==0){
		t3 = CPoint(m_EnclosingRect.TopLeft());
		t4 = CPoint(m_EnclosingRect.BottomRight());
		m_EnclosingRect.NormalizeRect();
		return;
	}
	if(h > l){
		t2 = CPoint(m_EnclosingRect.BottomRight().x, m_EnclosingRect.TopLeft().y-l);
		t3 = CPoint(m_EnclosingRect.BottomRight().x, m_EnclosingRect.TopLeft().y);
		t4 = CPoint(m_EnclosingRect.TopLeft().x, m_EnclosingRect.TopLeft().y-l);
		m_EnclosingRect.NormalizeRect();
		return;
	}

	

    double u = acos(l/a) ;    
	double n = a / (2 * (l / a));

    t3 = CPoint(m_EnclosingRect.TopLeft().x+n,m_EnclosingRect.TopLeft().y);
	t4 = CPoint(m_EnclosingRect.BottomRight().x-n,m_EnclosingRect.BottomRight().y);
	t2 = CPoint(m_EnclosingRect.BottomRight().x,m_EnclosingRect.BottomRight().y);
	m_EnclosingRect.NormalizeRect();

}


void CDiamond::Move(CSize& aSize)
{
	m_EnclosingRect += aSize;          // Move the rectangle
    t1 += aSize;
	t2 += aSize;
    t3 += aSize;
	t4 += aSize;	
}

CDiamond::~CDiamond() {
	
}


// Draw a CDiamond object
void CDiamond::Draw(CDC* pDC, CElement* pElement, int l)
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
   text += convertIntToChar(this->IDdiamond) ;
   text +="(";
   text += convertIntToChar(this->getID()) ;
   text +=")";

   pDC->TextOut(this->m_EnclosingRect.TopLeft().x, this->m_EnclosingRect.BottomRight().y , text);


   //pDC->Rectangle(m_EnclosingRect);

   


   pDC->MoveTo(t1);
   pDC->LineTo(t3);
   pDC->LineTo(t2);
   pDC->LineTo(t4);
   pDC->LineTo(t1);
   

   pDC->SelectObject(pOldPen);                // Restore the old pen
}

void CDiamond::Serialize(CArchive& ar)
{
	CShape::Serialize(ar);
	if (ar.IsStoring())
	{		
		ar <<l<<t1.x<<t1.y<<t2.x<<t2.y<<t3.x<<t3.y<<t4.x<<t4.y;
	} else {
		ar >>l>>t1.x>>t1.y>>t2.x>>t2.y>>t3.x>>t3.y>>t4.x>>t4.y;
	}
};
