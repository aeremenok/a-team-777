#ifndef Elements_h
#define Elements_h

#include <ostream>

// Generic element class
//##ModelId=473EDD6D032D
class CElement: public CObject
{
	DECLARE_SERIAL(CElement)
protected:
    // Color of an element
	//##ModelId=473EDD6D032F
    COLORREF m_Color;                 

    // Rectangle enclosing an element
	//##ModelId=473EDD6D0330
    CRect m_EnclosingRect;            

    // Pen width
	//##ModelId=473EDD6D033C
    int m_Pen;                         
public:
	//изменяет размер фигуры
	//##ModelId=474DC10E006D
    virtual void resize(CPoint Start, CPoint End);

	//определен для совместимости с фигурой по указателю
	//##ModelId=4741F1B1004E
    virtual bool operator==(const CElement& rhs) const;

    // Virtual destructor
	//##ModelId=473EDD6D033D
    virtual ~CElement(){}

    // Virtual draw operation
	//##ModelId=473EDD6D033F
    virtual void Draw(CDC* pDC, CElement* pElement=0, bool isIdVisible = true){};

    // Get the bounding rectangle for an element
	//##ModelId=473EDD6D0342
    CRect GetBoundRect();

	//##ModelId=4741F10F009C
    virtual void Move(CSize& aSize);
protected:
    // Default constructor
	//##ModelId=473EDD6D0343
    CElement(){}                               
	//{{AFX_VIRTUAL(CElement)
	public:
	//##ModelId=475168BF033C
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

};

// Class defining a line object
//##ModelId=473EDD6D034B
class CLine: public CElement
{
public:
	//изменяет размер фигуры
	//##ModelId=474DC535009C
	virtual void resize(CPoint Start, CPoint End);

    // Function to display a line
	//##ModelId=473EDD6D034D
	virtual void Draw(CDC* pDC, CElement* pElement=0, bool isIdVisible = true);

	//##ModelId=4741F10F00FB
	void Move(CSize& aSize);

    // Constructor for a line object
	//##ModelId=473EDD6D0350
    CLine(CPoint Start, CPoint End, COLORREF aColor);
protected:
    // Start point of line
	//##ModelId=473EDD6D035B
    CPoint m_StartPoint;          

    // End point of line
	//##ModelId=473EDD6D035C
    CPoint m_EndPoint;            

    // Default constructor - should not be used
	//##ModelId=473EDD6D035D
    CLine(){}             
};

// Class defining a rectangle object
//##ModelId=473EDD6D035E
class CRectangle: public CElement
{
public:
    // Function to display a rectangle
	//##ModelId=473EDD6D0360
	virtual void Draw(CDC* pDC, CElement* pElement=0);  

	//##ModelId=4741F10F010A
	void Move(CSize& aSize);

    // Constructor for a rectangle object
	//##ModelId=473EDD6D036D
    CRectangle(CPoint Start, CPoint End, COLORREF aColor);
protected:
    // Default constructor - should not be used
	//##ModelId=473EDD6D0371
    CRectangle(){}        
};
#endif
