// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "Shape.h"
// перекрывает ostream, подключенный в shape.h. нужен для cout
#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E207021C
int Shape::_counter = 0;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2070239
Shape::Shape(float x, float y) : _id(_counter++)
{
    _x = x;
    _y = y;
    cout<<"[shape] shape created"<<endl;
}

//##ModelId=4770E2070279
void Shape::moveToPoint(float x_pos, float y_pos)
{
    _x = x_pos;
    _y = y_pos;
}

//##ModelId=4770E2070261
const float Shape::get__y() const
{
	return _y;
}

//##ModelId=4770E2070269
void Shape::set__y(float value)
{
	_y = value;
	return;
}

//##ModelId=4770E207026B
const float Shape::get__x() const
{
	return _x;
}

//##ModelId=4770E207026D
void Shape::set__x(float value)
{
	_x = value;
	return;
}

//##ModelId=4770E207025F
Shape::~Shape()
{
    cout<<"[shape] shape destroyed"<<endl;
}

//##ModelId=4770E207023C
std::ostream& Shape::speak(std::ostream& os) const
{
    return os<<"[shape] id="<<get__id()<<", shape center coordinates: ("
             <<get__x()<<", "<<get__y()<<"), "
             <<"area = "<<Area()<<"\n";
}

//##ModelId=4770E207026F
bool Shape::operator==(const Shape& rhs) const
{
    // учитывая проверку при создании объектов,
    //  достаточно сравнить идентификаторы
    //  на CElement не обращаем внимания - мы уже все проверили
    return (get__id() == rhs.get__id());
}

//##ModelId=4770E207025C
void Shape::Move( CSize& aSize )
{
    moveToPoint(_x + aSize.cx, _y + aSize.cy);
    CElement::Move(aSize);
}

//##ModelId=4770E207024F
void Shape::drawID( CDC* pDC, bool isIdVisible ) const
{
    if (isIdVisible)
    {
        char id[3];
        itoa(_id, id, 10);
        pDC->SetTextColor(GREEN);
        pDC->TextOut(m_EnclosingRect.BottomRight().x, m_EnclosingRect.BottomRight().y, id);
    }
}

//##ModelId=4770E2070258
void Shape::resize(CPoint Start, CPoint End)
{
    // определяем координаты центра
    set__x( (End.x + Start.x) / 2 );
    set__y( (End.y + Start.y) / 2 );

    CElement::resize(Start, End);
}

//##ModelId=4770E207024C
void Shape::Serialize(CArchive& ar)
{
    CElement::Serialize(ar);
    
    if (ar.IsStoring())
    {  // storing code
        ar //<< _id
           << get__x()
           << get__y();
    }
    else
    { // loading code
        float x, y;
        ar //>> _id
           >> x
           >> y;
        set__x(x);
        set__y(y);
    }
}

//##ModelId=4770E2070249
const int Shape::get__id() const
{
    return _id;
}
//////////////////////////////////////////////////////////////////////////
std::ostream& operator<<( std::ostream& o, const Shape& rhs )
{
    return rhs.speak(o);
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E207024B
Shape::Shape()
{
	// ToDo: Add your specialized code here and/or call the base class
}




