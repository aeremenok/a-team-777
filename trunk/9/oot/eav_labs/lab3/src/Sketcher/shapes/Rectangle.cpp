// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "Rectangle.h"

#include <ostream.h>
#include <math.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070346
list<Rectangle2*> Rectangle2::_rectangles;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070362
Rectangle2::Rectangle2( float l, float w, float x, float y )
: Shape(x, y)
{
    _length = l;
    _width = w;
    cout<<"[rectangle] rectangle created"<<endl;
}

//##ModelId=4770E2070383
Rectangle2* Rectangle2::create(float length, float width, float x, float y)
{
    // ищем, нет ли уже прямоугольника с такими параметрами
    using namespace std;
    list<Rectangle2*>::iterator iter;
    for (iter = _rectangles.begin(); iter != _rectangles.end(); iter++)
    {
        Rectangle2* rectangle = *iter;
        if (
            rectangle->_length == length &&
            rectangle->_width == width &&
            rectangle->_x == x &&
            rectangle->_y == y
            )
        {   // такой есть
            return rectangle;
        }
    }
    // не нашли - создаем новый
    Rectangle2* rectangle = new Rectangle2(length, width, x, y);
    _rectangles.push_back(rectangle);
    return rectangle;
}

//##ModelId=4770E2070391
Rectangle2* Rectangle2::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float l, w;
    // определяем длину и ширину
    w = fabs(Start.x - End.x);
    l = fabs(Start.y - End.y);

    Rectangle2* rect = create(l, w, 0, 0);

    rect->m_Pen = 1;
    rect->m_Color = aColor;
    rect->resize(Start, End);

    return rect;
}

//##ModelId=4770E2070396
Rectangle2::~Rectangle2()
{
    _rectangles.remove(this);
	cout<<"[rectangle] rectangle destroyed"<<endl;
}

//##ModelId=4770E2070398
const float Rectangle2::get__length() const
{
	return _length;
}

//##ModelId=4770E20703A1
void Rectangle2::set__length(float value)
{
	_length = value;
}

//##ModelId=4770E20703A3
const float Rectangle2::get__width() const
{
	return _width;
}

//##ModelId=4770E20703A5
void Rectangle2::set__width(float value)
{
	_width = value;
}

//##ModelId=4770E20703B0
std::ostream& Rectangle2::speak(std::ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")\n";
}

//##ModelId=4770E20703A7
float Rectangle2::Area() const
{
    return _length * _width;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070372
void Rectangle2::Draw( CDC* pDC, CElement* pElement, bool isIdVisible )
{
    // Create a pen for this object and
    // initialize it to the object color and line width of 1 pixel
    CPen aPen;
    COLORREF aColor = m_Color;
    if (this == pElement)
    {
        aColor = SELECT_COLOR;
    }
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
    drawID(pDC, isIdVisible);
    
    pDC->SelectObject(pOldBrush);              // Restore the old brush
    pDC->SelectObject(pOldPen);                // Restore the old pen    
}

//##ModelId=4770E207037A
void Rectangle2::Serialize(CArchive& ar)
{
    Shape::Serialize(ar);

    if (ar.IsStoring())
    {  // storing code
        ar << _length
           << _width;
    }
    else
    { // loading code
        ar >> _length
           >> _width;
    }
}

//##ModelId=4770E2070377
int Rectangle2::getType() const
{
    return RECTANGLE;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070379
Rectangle2::Rectangle2()
{
	// ToDo: Add your specialized code here and/or call the base class
}
