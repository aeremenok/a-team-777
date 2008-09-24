// Implementations of the element classes
#include "stdafx.h"

#include "OurConstants.h"
#include "Elements.h"

#include <math.h>





// Add definitions for member functions here

// CLine class constructor
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
void CLine::Draw(CDC* pDC, CElement* pElement, int l)
{
   // Create a pen for this object and
   // initialize it to the object color and line width of 1 pixel
   CPen aPen;
   COLORREF aColor = m_Color;             // Initialize with element color
   if(this == pElement)                   // This element selected?
      aColor = SELECT_COLOR;              // Set highlight color
   if(!aPen.CreatePen(PS_SOLID, m_Pen, aColor))
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
CRect CElement::GetBoundRect()
{
   CRect BoundingRect;              // Object to store bounding rectangle
   BoundingRect = m_EnclosingRect;  // Store the enclosing rectangle

   // Increase the rectangle by the pen width
   BoundingRect.InflateRect(m_Pen, m_Pen);
   return BoundingRect;             // Return the bounding rectangle
}

// CRectangle class constructor
CRectangle:: CRectangle(CPoint Start, CPoint End, COLORREF aColor)
{
   m_Color = aColor;          // Set rectangle color
   m_Pen = 1;                 // Set pen width

   // Define the enclosing rectangle 
   m_EnclosingRect = CRect(Start, End);
   m_EnclosingRect.NormalizeRect();
}

// Draw a CRectangle object
void CRectangle::Draw(CDC* pDC, CElement* pElement, int l)
{
   // Create a pen for this object and
   // initialize it to the object color and line width of 1 pixel
   CPen aPen;
   COLORREF aColor = m_Color;             // Initialize with element color
   if(this == pElement)                   // This element selected?
      aColor = SELECT_COLOR;              // Set highlight color
   if(!aPen.CreatePen(PS_SOLID, m_Pen, aColor))
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

void CLine::Move(CSize& aSize)
{
   m_StartPoint += aSize;            // Move the start point
   m_EndPoint += aSize;              // and the end point
   m_EnclosingRect += aSize;         // Move the enclosing rectangle
}

void CRectangle::Move(CSize& aSize)
{
   m_EnclosingRect+= aSize;          // Move the rectangle
}

void CElement::Serialize(CArchive& ar)
{
   CObject::Serialize(ar);         // Call the base class function

   if (ar.IsStoring())
   {
      ar << m_Color                // Store the color,
         << m_EnclosingRect        // and the enclosing rectangle,
         << m_Pen;                 // and the pen width
   }
   else
   {
      ar >> m_Color                // Retrieve the color,
         >> m_EnclosingRect        // and the enclosing rectangle,
         >> m_Pen;                 // and the pen width
   }
};

void CLine::Serialize(CArchive& ar)
{
   CElement::Serialize(ar);        // Call the base class function

   if (ar.IsStoring())
   {
      ar << m_StartPoint           // Store the line start point,
         << m_EndPoint;            // and the end point
   }
   else
   {
      ar >> m_StartPoint           // Retrieve the line start point,
         >> m_EndPoint;            // and the end point
   }
};

void CRectangle::Serialize(CArchive& ar)
{
   CElement::Serialize(ar);        // Call the base class function
};
