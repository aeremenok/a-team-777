#ifndef Elements_h
#define Elements_h

// Generic element class
//##ModelId=473EDD6D032D
class CElement: public CObject
{
protected:
	//##ModelId=473EDD6D032F
   COLORREF m_Color;                          // Color of an element
	//##ModelId=473EDD6D0330
   CRect m_EnclosingRect;             // Rectangle enclosing an element
	//##ModelId=473EDD6D033C
   int m_Pen;                         // Pen width

public:
	//##ModelId=473EDD6D033D
   virtual ~CElement(){}                      // Virtual destructor
	//##ModelId=473EDD6D033F
   virtual void Draw(CDC* pDC, CElement* pElement=0) {}             // Virtual draw operation

	//##ModelId=473EDD6D0342
   CRect GetBoundRect();   // Get the bounding rectangle for an element
   virtual void Move(CSize& aSize) {}
protected:
	//##ModelId=473EDD6D0343
   CElement(){}                               // Default constructor
};

// Class defining a line object
//##ModelId=473EDD6D034B
class CLine: public CElement
{
public:
	//##ModelId=473EDD6D034D
	virtual void Draw(CDC* pDC, CElement* pElement=0);  // Function to display a line
	void Move(CSize& aSize);
   // Constructor for a line object
	//##ModelId=473EDD6D0350
   CLine(CPoint Start, CPoint End, COLORREF aColor);

protected:
	//##ModelId=473EDD6D035B
   CPoint m_StartPoint;          // Start point of line
	//##ModelId=473EDD6D035C
   CPoint m_EndPoint;            // End point of line

	//##ModelId=473EDD6D035D
   CLine(){}             // Default constructor - should not be used
};

// Class defining a rectangle object
//##ModelId=473EDD6D035E
class CRectangle: public CElement
{
public:
	//##ModelId=473EDD6D0360
	virtual void Draw(CDC* pDC, CElement* pElement=0);  // Function to display a rectangle
	void Move(CSize& aSize);
   // Constructor for a rectangle object
	//##ModelId=473EDD6D036D
   CRectangle(CPoint Start, CPoint End, COLORREF aColor);

protected:
	//##ModelId=473EDD6D0371
   CRectangle(){}        // Default constructor - should not be used
};


#endif
