// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "stdAfx.h"

#include "Oval.h"

#include <ostream.h>
#include <math.h>
//////////////////////////////////////////////////////////////////////////
#define M_PI 3.1415926
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20703D2
list<Oval*> Oval::_ovals;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF403C8
Oval::Oval(float firstRad, float secondRad, float x, float y)
: Shape(x, y)
{
    _rad1 = firstRad;
    _rad2 = secondRad;
    cout<<"[oval] oval created"<<endl;
}

//##ModelId=4770E208002B
Oval* Oval::create(float firstRad, float secondRad, float x, float y)
{
    using namespace std;
    // ищем, нет ли уже овала с такими параметрами
    list<Oval*>::iterator iter;
    for (iter = _ovals.begin(); iter != _ovals.end(); iter++)
    {
        Oval* oval = *iter;
        if (
            oval->_rad1 == firstRad &&
            oval->_rad2 == secondRad &&
            oval->_x == x &&
            oval->_y == y
            )
        {   // такой есть
            return oval;
        }
    }
    // не нашли - создаем новый
    Oval* oval = new Oval(firstRad, secondRad, x, y);
    _ovals.push_back(oval);
    return oval;
}

//##ModelId=4770E2080038
Oval* Oval::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float r1, r2;
    // определяем радиусы
    r1 = fabs(Start.x - End.x) / 2;
    r2 = fabs(Start.y - End.y) / 2;
    
    Oval* oval = create(r1, r2, 0, 0);
    
    oval->m_Pen = 1;
    oval->m_Color = aColor;
    oval->resize(Start, End);

    return oval;
}

//##ModelId=4770E208003D
Oval::~Oval()
{
    _ovals.remove(this);
	cout<<"[oval] oval destroyed"<<endl;
}

//##ModelId=4770E208003F
const float Oval::getRad1() const
{
	return _rad1;
}

//##ModelId=4770E2080046
void Oval::setRad1(float value)
{
	_rad1 = value;
}

//##ModelId=4770E2080048
const float Oval::getRad2() const
{
	return _rad2;
}

//##ModelId=4770E208004A
void Oval::setRad2(float value)
{
	_rad2 = value;
}

//##ModelId=4770E2080009
std::ostream& Oval::speak(std::ostream& os) const
{
    return Shape::speak(os)
        <<"[oval] oval chords: ("
        <<getRad1()<<", "<<getRad2()<<")\n";
}

//##ModelId=4770E2080056
float Oval::Area() const
{
    return M_PI * _rad1 * _rad2;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2080018
void Oval::Draw( CDC* pDC, CElement* pElement, bool isIdVisible )
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
        AfxMessageBox("Pen creation failed drawing an oval", MB_OK);
        AfxAbort();
    }
    
    // Select the pen
    CPen* pOldPen = pDC->SelectObject(&aPen);   
    // Select the brush
    CBrush* pOldBrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH); 
    
    // Now draw the rectangle
    pDC->Ellipse(m_EnclosingRect);
    drawID(pDC, isIdVisible);
    
    pDC->SelectObject(pOldBrush);              // Restore the old brush
    pDC->SelectObject(pOldPen);                // Restore the old pen
}

//##ModelId=4770E2080028
void Oval::Serialize(CArchive& ar)
{
    Shape::Serialize(ar);
   
    if (ar.IsStoring())
    {  // storing code
        ar << _rad1
            << _rad2;
    }
    else
    { // loading code
        ar >> _rad1
            >> _rad2;
    }
}

//##ModelId=4770E2080026
int Oval::getType() const
{
    return OVAL;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2080017
Oval::Oval()
{
	// ToDo: Add your specialized code here and/or call the base class
}
