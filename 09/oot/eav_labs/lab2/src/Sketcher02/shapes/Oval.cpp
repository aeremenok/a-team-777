// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Oval.h"

#include "..\OurConstants.h"

#include <math.h>
//////////////////////////////////////////////////////////////////////////
#include <iostream>
using namespace std;
//////////////////////////////////////////////////////////////////////////
#define M_PI 3.1415926
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF403BE
list<Oval*> Oval::_ovals;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF403C8
Oval::Oval(float rad1, float rad2, float x, float y)
: Shape(x, y)
{
    _rad1 = rad1;
    _rad2 = rad2;
    cout<<"[oval] oval created"<<endl;
}

//##ModelId=473EDDF403DB
Oval* Oval::create(float rad1, float rad2, float x, float y)
{
    using namespace std;
    // ����, ��� �� ��� ����� � ������ �����������
    list<Oval*>::iterator iter;
    for (iter = _ovals.begin(); iter != _ovals.end(); iter++)
    {
        Oval* oval = *iter;
        if (
            oval->_rad1 == rad1 &&
            oval->_rad2 == rad2 &&
            oval->_x == x &&
            oval->_y == y
            )
        {   // ����� ����
            return oval;
        }
    }
    // �� ����� - ������� �����
    Oval* oval = new Oval(rad1, rad2, x, y);
    _ovals.push_back(oval);
    return oval;
}

//##ModelId=474055EF02FD
Oval* Oval::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float r1, r2;
    // ���������� �������
    r1 = fabs(Start.x - End.x) / 2;
    r2 = fabs(Start.y - End.y) / 2;
    
    Oval* oval = create(r1, r2, 0, 0);
    
    oval->m_Pen = 1;
    oval->m_Color = aColor;
    oval->resize(Start, End);

    return oval;
}

//##ModelId=473EDDF50001
Oval::~Oval()
{
    _ovals.remove(this);
	cout<<"[oval] oval destroyed"<<endl;
}

//##ModelId=473EDDF50003
const float Oval::getRad1() const
{
	return _rad1;
}

//##ModelId=473EDDF50005
void Oval::setRad1(float value)
{
	_rad1 = value;
}

//##ModelId=473EDDF50007
const float Oval::getRad2() const
{
	return _rad2;
}

//##ModelId=473EDDF50009
void Oval::setRad2(float value)
{
	_rad2 = value;
}

//##ModelId=473EDDF403D8
std::ostream& Oval::speak(std::ostream& os) const
{
    return Shape::speak(os)
        <<"[oval] oval chords: ("
        <<getRad1()<<", "<<getRad2()<<")\n";
}

//##ModelId=473EDDF5000F
float Oval::Area() const
{
    return M_PI * _rad1 * _rad2;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4754601601A5
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

//##ModelId=475168E301A5
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

//##ModelId=4751CD0F0196
int Oval::getType() const
{
    return OVAL;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4751AC7A02BF
Oval::Oval()
{
	// ToDo: Add your specialized code here and/or call the base class
}
