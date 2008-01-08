#include "stdafx.h"
#include "iostream.h"
#include "CShape.h"
#include "CSquare.h"
#include "CDiamond.h"
#include "CText.h"
#include "CTextInSquare.h"

int CShape::KOL = 0;

const CPoint CShape::getTopLeft()const {
	return this->m_EnclosingRect.TopLeft();
};

const CPoint CShape::getBottomRight()const {
	return this->m_EnclosingRect.BottomRight();
};

const int CShape::getID()const {
	return this->id;
};

void CShape::setTopLeft(CPoint newX){
	this->m_EnclosingRect.SetRect(newX,this->m_EnclosingRect.BottomRight() ) ;
};

void CShape::setBottomRight(CPoint newY){
	this->m_EnclosingRect.SetRect( this->m_EnclosingRect.TopLeft(), newY) ;
};


CShape::CShape(CPoint x, CPoint y, COLORREF aColor):id(KOL){	
	m_EnclosingRect = CRect(x, y);
    m_EnclosingRect.NormalizeRect();
	m_Color = aColor;
	m_Pen = 1;
	KOL++;
	
};

CShape::CShape(CRect rect, COLORREF aColor):id(KOL){
	m_EnclosingRect  = rect;
	m_EnclosingRect.NormalizeRect();
	m_Color = aColor;
	m_Pen = 1;
	KOL++;
	
};



CShape::~CShape()
{

};


char* CShape::convertIntToChar(int i)
{
	char* chars = new char[10];
    itoa(i, chars, 10);
	return chars;
}


bool CShape::operator==(const CShape& rhs) const
{
    // учитывая проверку при создании объектов,
    // достаточно сравнить идентификаторы
    return (this->id == rhs.id);
};


ostream& operator<<( ostream& o, const CShape& rhs )
{
    //return rhs.printInfo(o);
	return o;
}

void CShape::Move(CSize& aSize)
{
   m_EnclosingRect += aSize;          // Move the rectangle
}


void CShape::Serialize(CArchive& ar)
{   
   if (ar.IsStoring())//only saving
   {
      ar << this->getType() 
		 << m_Color                // Store the color,
         << m_EnclosingRect        // and the enclosing rectangle,
         << m_Pen                  // and the pen width
		 ; 
   }  else {
	   ar >> m_Color                // Store the color,
         >> m_EnclosingRect        // and the enclosing rectangle,
         >> m_Pen; 
   }
}


 CElement* CShape::getFromArch(CArchive& ar){
	
	if (! ar.IsStoring())//loading
	{			
		WORD n_Type;
	
		ar >> n_Type; 

		CElement* newEl = NULL;

		switch(n_Type)
		{
			case CSHAPE_SQUARE:
			{					
				newEl = new CSquare();
				break;
			}
			case CSHAPE_TEXT:    
			{   
				newEl =  new CText();
				break;
			}
			case CSHAPE_TEXT_IN_SQUARE:          
			{
				newEl =  new CTextInSquare();
				break;
			}
			case CSHAPE_DIAMOND:                  
			{
				newEl =  new CDiamond();
				break;
			}
		}
		if(newEl == NULL) return NULL;

		newEl->Serialize(ar);
		return newEl;
    
	}
	return NULL;
};


