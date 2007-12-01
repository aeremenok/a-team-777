// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Rectangle.h"

#include "..\OurConstants.h"

#include <ostream.h>
#include <math.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40370
list<Rectangle2*> Rectangle2::_rectangles;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF4038A
Rectangle2::Rectangle2( float l, float w, float x, float y )
: Shape(x, y)
{
    _length = l;
    _width = w;
    cout<<"[rectangle] rectangle created"<<endl;
}

//##ModelId=473EDDF4038F
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

//##ModelId=474055EF0280
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

//##ModelId=473EDDF4039B
Rectangle2::~Rectangle2()
{
    _rectangles.remove(this);
	cout<<"[rectangle] rectangle destroyed"<<endl;
}

//##ModelId=473EDDF4039D
const float Rectangle2::get__length() const
{
	return _length;
}

//##ModelId=473EDDF4039F
void Rectangle2::set__length(float value)
{
	_length = value;
}

//##ModelId=473EDDF403A1
const float Rectangle2::get__width() const
{
	return _width;
}

//##ModelId=473EDDF403A3
void Rectangle2::set__width(float value)
{
	_width = value;
}

//##ModelId=473EDDF403AC
ostream& Rectangle2::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")"<<endl;
}

//##ModelId=473EDDF403AA
float Rectangle2::Area() const
{
    return _length * _width;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EF26702EE
void Rectangle2::Draw( CDC* pDC, CElement* pElement/*=0*/ )
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
    drawID(pDC);
    
    pDC->SelectObject(pOldBrush);              // Restore the old brush
    pDC->SelectObject(pOldPen);                // Restore the old pen    
}
//////////////////////////////////////////////////////////////////////////

