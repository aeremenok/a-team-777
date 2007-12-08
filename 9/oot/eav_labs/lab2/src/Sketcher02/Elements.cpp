// Implementations of the element classes
#include "stdafx.h"
//////////////////////////////////////////////////////////////////////////
#include "OurConstants.h"
#include "Elements.h"

#include <math.h>
//////////////////////////////////////////////////////////////////////////
IMPLEMENT_SERIAL(CElement, CObject, VERSION_NUMBER)
//////////////////////////////////////////////////////////////////////////
// Add definitions for member functions here
// CLine class constructor
//##ModelId=473EDD6D0350
CLine::CLine(CPoint Start, CPoint End, COLORREF aColor)
{
   m_Color = aColor;          // Set line color
   m_Pen = 1;                 // Set pen width

   resize(Start, End);
}

// Draw a CLine object
//##ModelId=473EDD6D034D
void CLine::Draw(CDC* pDC, CElement* pElement, bool isIdVisible)
{
   // Create a pen for this object and
   // initialize it to the object color and line width of 1 pixel
   CPen aPen;
   if(!aPen.CreatePen(PS_SOLID, m_Pen, m_Color))
   {  
	  // Pen creation failed. Abort the program.
      AfxMessageBox("Pen creation failed drawing a line", MB_OK);
      AfxAbort();
   }

   CPen* pOldPen = pDC->SelectObject(&aPen);  // Select the pen

   // Now draw the line
   pDC->MoveTo(m_StartPoint);
   pDC->LineTo(m_EndPoint);

   pDC->SelectObject(pOldPen);                // Restore the old pen
}

//##ModelId=4741F10F00FB
void CLine::Move( CSize& aSize )
{
    m_StartPoint += aSize;            // Move the start point
    m_EndPoint += aSize;              // and the end point
    CElement::Move(aSize);
}

// Get the bounding rectangle for an element
//##ModelId=473EDD6D0342
CRect CElement::GetBoundRect()
{
   CRect BoundingRect;              // Object to store bounding rectangle
   BoundingRect = m_EnclosingRect;  // Store the enclosing rectangle

   // Increase the rectangle by the pen width
   BoundingRect.InflateRect(m_Pen, m_Pen);
   return BoundingRect;             // Return the bounding rectangle
}

// CRectangle class constructor
//##ModelId=473EDD6D036D
CRectangle::CRectangle(CPoint Start, CPoint End, COLORREF aColor)
{
   m_Color = aColor;          // Set rectangle color
   m_Pen = 1;                 // Set pen width

   CElement::resize(Start, End);
}

// Draw a CRectangle object
//##ModelId=473EDD6D0360
void CRectangle::Draw(CDC* pDC, CElement* pElement)
{
   // Create a pen for this object and
   // initialize it to the object color and line width of 1 pixel
   CPen aPen; 
   if(!aPen.CreatePen(PS_SOLID, m_Pen, m_Color))
   {                                           // Pen creation failed
      AfxMessageBox("Pen creation failed drawing a rectangle", MB_OK);
      AfxAbort();
   }

   // Select the pen
   CPen* pOldPen = pDC->SelectObject(&aPen);   
   // Select the brush
   CBrush* pOldBrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH); 

   // Now draw the rectangle
   pDC->Rectangle(m_EnclosingRect);

   pDC->SelectObject(pOldBrush);              // Restore the old brush
   pDC->SelectObject(pOldPen);                // Restore the old pen
}

//##ModelId=4741F10F010A
void CRectangle::Move( CSize& aSize )
{
    CElement::Move( aSize );
}

//##ModelId=4741F1B1004E
bool CElement::operator==(const CElement& rhs) const
{
    return 
        this->m_Color == rhs.m_Color &&
        this->m_EnclosingRect == rhs.m_EnclosingRect &&
        this->m_Pen == rhs.m_Pen;
}

//##ModelId=4741F10F009C
void CElement::Move( CSize& aSize )
{
    m_EnclosingRect += aSize;    
}

//##ModelId=474DC10E006D
void CElement::resize( CPoint Start, CPoint End )
{
    m_EnclosingRect = CRect(Start, End);
    m_EnclosingRect.NormalizeRect();	
}

//##ModelId=474DC535009C
void CLine::resize(CPoint Start, CPoint End)
{
	m_StartPoint = Start;
    m_EndPoint = End;
	
	CElement::resize(Start, End);
}

//##ModelId=475168BF033C
void CElement::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{  // storing code
        ar << m_Color
           << m_EnclosingRect
           << m_Pen;
	}
	else
	{ // loading code
        ar >> m_Color
           >> m_EnclosingRect
           >> m_Pen;
	}
}


