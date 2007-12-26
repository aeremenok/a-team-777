#ifndef Elements_h
#define Elements_h
//////////////////////////////////////////////////////////////////////////
#include "OurConstants.h"
//////////////////////////////////////////////////////////////////////////
// Generic element class
//##ModelId=4770E20802C8
class CElement: public CObject
{
	DECLARE_SERIAL(CElement)
protected:
    // Color of an element
	//##ModelId=4770E20802D6
    COLORREF m_Color;                 

    // Rectangle enclosing an element
	//##ModelId=4770E20802D7
    CRect m_EnclosingRect;            

    // Pen width
	//##ModelId=4770E20802E5
    int m_Pen;                         
public:
    COLORREF Color() const { return m_Color; }
    void Color(COLORREF val) { m_Color = val; }

	//изменяет размер фигуры
	//##ModelId=4770E20802E6
    virtual void resize(CPoint Start, CPoint End);

	//определен для совместимости с фигурой по указателю
	//##ModelId=4770E20802EA
    virtual bool operator==(const CElement& rhs) const;

    // Virtual destructor
	//##ModelId=4770E20802F7
    virtual ~CElement(){}

    // Virtual draw operation
	//##ModelId=4770E20802F9
    virtual void Draw(CDC* pDC, CElement* pElement=0, bool isIdVisible = true){};

    // Get the bounding rectangle for an element
	//##ModelId=4770E20802FE
    CRect GetBoundRect();

	//##ModelId=4770E2080304
    virtual void Move(CSize& aSize);
protected:
    // Default constructor
	//##ModelId=4770E2080307
    CElement(){m_Color = BLACK;}
	//{{AFX_VIRTUAL(CElement)
	public:
	//##ModelId=4770E2080308
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

};

// Class defining a line object
//##ModelId=4770E2080314
class CLine: public CElement
{
public:
	//изменяет размер фигуры
	//##ModelId=4770E2080324
	virtual void resize(CPoint Start, CPoint End);

    // Function to display a line
	//##ModelId=4770E2080328
	virtual void Draw(CDC* pDC, CElement* pElement=0, bool isIdVisible = true);

	//##ModelId=4770E208032D
	void Move(CSize& aSize);

    // Constructor for a line object
	//##ModelId=4770E2080334
    CLine(CPoint Start, CPoint End, COLORREF aColor);
protected:
    // Start point of line
	//##ModelId=4770E2080338
    CPoint m_StartPoint;          

    // End point of line
	//##ModelId=4770E2080339
    CPoint m_EndPoint;            

    // Default constructor - should not be used
	//##ModelId=4770E2080343
    CLine(){}             
};

// Class defining a rectangle object
//##ModelId=4770E2080344
class CRectangle: public CElement
{
public:
    // Function to display a rectangle
	//##ModelId=4770E2080353
	virtual void Draw(CDC* pDC, CElement* pElement=0);  

	//##ModelId=4770E2080357
	void Move(CSize& aSize);

    // Constructor for a rectangle object
	//##ModelId=4770E2080363
    CRectangle(CPoint Start, CPoint End, COLORREF aColor);
protected:
    // Default constructor - should not be used
	//##ModelId=4770E2080367
    CRectangle(){}        
};
#endif
