// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"

#include <ostream.h>
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
ostream& Oval::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[oval] oval chords: ("
        <<_rad1<<", "<<_rad2<<")"<<endl;
}

//##ModelId=473EDDF5000F
float Oval::Area() const
{
    return M_PI * _rad1 * _rad2;
}

//////////////////////////////////////////////////////////////////////////

