#ifndef Elements_h
#define Elements_h

// Generic element class
class CElement: public CObject
{
protected:
   COLORREF m_Color;                          // Color of an element
   CRect m_EnclosingRect;             // Rectangle enclosing an element
   int m_Pen;                         // Pen width

public:
   virtual ~CElement(){}                      // Virtual destructor
   virtual void Draw(CDC* pDC) {}             // Virtual draw operation

   CRect GetBoundRect();   // Get the bounding rectangle for an element

protected:
   CElement(){}                               // Default constructor
};

// Class defining a line object
class CLine: public CElement
{
public:
   virtual void Draw(CDC* pDC);  // Function to display a line

   // Constructor for a line object
   CLine(CPoint Start, CPoint End, COLORREF aColor);

protected:
   CPoint m_StartPoint;          // Start point of line
   CPoint m_EndPoint;            // End point of line

   CLine(){}             // Default constructor - should not be used
};

// Class defining a rectangle object
class CRectangle: public CElement
{
public:
   virtual void Draw(CDC* pDC);  // Function to display a rectangle

   // Constructor for a rectangle object
   CRectangle(CPoint Start, CPoint End, COLORREF aColor);

protected:
   CRectangle(){}        // Default constructor - should not be used
};


#endif
