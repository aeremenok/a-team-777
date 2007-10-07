// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED

class ostream;

//���������� ������
//##ModelId=46F50BDC00BB
class Shape 
{
private:


public:
    //��������� ������� ������
    //##ModelId=4708E2B00186
    virtual int Area()=0;

    //##ModelId=4708DDC303C8
    Shape();

    //##ModelId=4708DDC4001F
    virtual ~Shape();

	//�������� ������ � �����
	//##ModelId=46F6755D0025
	friend ostream& operator<<(ostream& o, const Shape& rhs) ;

	//�������� �������� ������
	//##ModelId=46F675DE02AF
	const int getY() const;

	//������ �������� ������
	//##ModelId=46F675DF010B
	void setY(int value);

	//�������� �������� ������
	//##ModelId=46F675E401C7
	const int getX() const;

	//������ ������� ������
	//##ModelId=46F675E403AB
	void setX(int value);

	//��������� ������ � �������� �����. �� ��������� - � 
	//������ ���������
	//##ModelId=46F50D80038A
	void moveToPoint(int x_pos = 0, int y_pos = 0);

protected:
	//�������� ������. ��� ������ �� ��������� ���������� � 
	//������ ���������
	//##ModelId=46F50D19006D
	int _x;

	//�������� ������. ��� ������ �� ��������� ���������� � 
	//������ ���������
	//##ModelId=46F50D1D00AB
	int _y;

};

#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
