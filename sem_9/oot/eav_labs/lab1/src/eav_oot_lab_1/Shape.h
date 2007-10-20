// Copyright (C) 1991 - 1999 Rational Software Corporation

#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_SHAPE_46F50BDC00BB_INCLUDED
#define _INC_SHAPE_46F50BDC00BB_INCLUDED
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//���������� ������
//##ModelId=46F50BDC00BB
class Shape 
{
protected:
    /************************************************************************/
    /* ��������                                                             */
    /************************************************************************/
    //�������� ������. ��� ������ �� ��������� ���������� � ������ ���������
    //##ModelId=46F50D19006D
    float _x;
    
    //�������� ������. ��� ������ �� ��������� ���������� � ������ ���������
    //##ModelId=46F50D1D00AB
	float _y;

public:
    /************************************************************************/
    /* ������������ � ����������                                            */
    /************************************************************************/
    //##ModelId=4708DDC303C8
    Shape();
    
    //##ModelId=4708DDC4001F
    virtual ~Shape();

    /************************************************************************/
    /* �������/�������                                                      */
    /************************************************************************/
    //�������� �������� ������
    //##ModelId=46F675DE02AF
    const float get__y() const;
    
    //������ �������� ������
    //##ModelId=46F675DF010B
    void set__y(float value);
    
    //�������� �������� ������
    //##ModelId=46F675E401C7
    const float get__x() const;
    
    //������ ������� ������
    //##ModelId=46F675E403AB
	void set__x(float value);

    /************************************************************************/
    /* ���������                                                            */
    /************************************************************************/
    //�������� ������ � �����
    //##ModelId=46F6755D0025
	friend ostream& operator<<(ostream& o, const Shape& rhs);

    /************************************************************************/
    /* ������ ������                                                        */
    /************************************************************************/    
	//��������� ������ � �������� �����
	//##ModelId=46F50D80038A
	void moveToPoint(float x_pos  , float y_pos);

protected:
    //������� ��������� ������ � �����
    //##ModelId=4712170B0271
    virtual ostream& speak(ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_SHAPE_46F50BDC00BB_INCLUDED */
