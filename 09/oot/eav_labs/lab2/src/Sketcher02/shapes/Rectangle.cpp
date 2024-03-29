// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Rectangle.h"

#include "..\OurConstants.h"

#include <math.h>
//////////////////////////////////////////////////////////////////////////
#include <iostream>
using namespace std;
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
    // ����, ��� �� ��� �������������� � ������ �����������
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
        {   // ����� ����
            return rectangle;
        }
    }
    // �� ����� - ������� �����
    Rectangle2* rectangle = new Rectangle2(length, width, x, y);
    _rectangles.push_back(rectangle);
    return rectangle;
}

//##ModelId=474055EF0280
Rectangle2* Rectangle2::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float l, w;
    // ���������� ����� � ������
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
std::ostream& Rectangle2::speak(std::ostream& os) const
{
    return Shape::speak(os)
        <<"[rectangle] rectangle size: ("
        <<_length<<", "<<_width<<")\n";
}

//##ModelId=473EDDF403AA
float Rectangle2::Area() const
{
    return _length * _width;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4754603F0290
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

//##ModelId=4751692C0119
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

//##ModelId=4751CD2403C8
int Rectangle2::getType() const
{
    return RECTANGLE;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4751AC870261
Rectangle2::Rectangle2()
{
	// ToDo: Add your specialized code here and/or call the base class
}
