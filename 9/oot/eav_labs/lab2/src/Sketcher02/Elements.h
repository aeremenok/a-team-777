#ifndef Elements_h
#define Elements_h

class ostream;

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
	//определен для совместимости с фигурой по указателю
	//##ModelId=4741F1B1004E
    virtual bool operator==(const CElement& rhs) const;

	//##ModelId=473EDD6D033D
    virtual ~CElement(){}                      // Virtual destructor
	//##ModelId=473EDD6D033F
    virtual void Draw(CDC* pDC, CElement* pElement=0) {}             // Virtual draw operation

	//##ModelId=473EDD6D0342
    CRect GetBoundRect();   // Get the bounding rectangle for an element
	//##ModelId=4741F10F009C
    virtual void Move(CSize& aSize);
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
	//##ModelId=4741F10F00FB
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
	//##ModelId=4741F10F010A
	void Move(CSize& aSize);
    // Constructor for a rectangle object
	//##ModelId=473EDD6D036D
    CRectangle(CPoint Start, CPoint End, COLORREF aColor);

protected:
	//##ModelId=473EDD6D0371
    CRectangle(){}        // Default constructor - should not be used
};
#endif
