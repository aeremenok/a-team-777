// Implementations of the element classes
#include "stdafx.h"
//////////////////////////////////////////////////////////////////////////
#include "OurConstants.h"
#include "Elements.h"

#include <math.h>
//////////////////////////////////////////////////////////////////////////
// Add definitions for member functions here

// CLine class constructor
//##ModelId=473EDD6D0350
CLine::CLine(CPoint Start, CPoint End, COLORREF aColor)
{
   m_StartPoint = Start;      // Set line start point
   m_EndPoint = End;          // Set line end point
   m_Color = aColor;          // Set line color
   m_Pen = 1;                 // Set pen width

   // Define the enclosing rectangle
   m_EnclosingRect = CRect(Start, End);
   m_EnclosingRect.NormalizeRect();
}

// Draw a CLine object
//##ModelId=473EDD6D034D
void CLine::Draw(CDC* pDC, CElement* pElement)
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

   // Define the enclosing rectangle 
   m_EnclosingRect = CRect(Start, End);
   m_EnclosingRect.NormalizeRect();
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
