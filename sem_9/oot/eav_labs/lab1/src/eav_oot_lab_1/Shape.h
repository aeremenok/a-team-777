// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED

class ostream;

//обобщенная фигура
//##ModelId=46F50BDC00BB
class Shape 
{
private:


public:
    //вычисляет площадь фигуры
    //##ModelId=4708E2B00186
    virtual int Area()=0;

    //##ModelId=4708DDC303C8
    Shape();

    //##ModelId=4708DDC4001F
    virtual ~Shape();

	//оператор вывода в поток
	//##ModelId=46F6755D0025
	friend ostream& operator<<(ostream& o, const Shape& rhs) ;

	//получить ординату фигуры
	//##ModelId=46F675DE02AF
	const int getY() const;

	//задать ординату фигуры
	//##ModelId=46F675DF010B
	void setY(int value);

	//получить абсциссу фигуры
	//##ModelId=46F675E401C7
	const int getX() const;

	//задать абциссу фигуры
	//##ModelId=46F675E403AB
	void setX(int value);

	//перенести фигуру в заданную точку. по умолчанию - в 
	//начало координат
	//##ModelId=46F50D80038A
	void moveToPoint(int x_pos = 0, int y_pos = 0);

protected:
	//абсцисса фигуры. все фигуры по умолчанию появляются в 
	//начале координат
	//##ModelId=46F50D19006D
	int _x;

	//ордината фигуры. все фигуры по умолчанию появляются в 
	//начале координат
	//##ModelId=46F50D1D00AB
	int _y;

};

#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
