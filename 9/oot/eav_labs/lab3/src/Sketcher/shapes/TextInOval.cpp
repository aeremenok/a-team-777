// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "TextInOval.h"

#include "../OurConstants.h"
#include "../resource.h"
#include "../TextRequest.h"

#include <math.h>
//////////////////////////////////////////////////////////////////////////
#include <iostream>
using namespace std;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40223
list<TextInOval*> TextInOval::_textsInOvals;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40232
/*
TextInOval* TextInOval::create(float rad1, float rad2, std::string content, float x, float y)
{
    // проверяем, нет ли уже такого текста в овале
    using namespace std;
    list<TextInOval*>::iterator iter;
    for (iter = _textsInOvals.begin(); iter != _textsInOvals.end(); iter++)
    {
        TextInOval* textInOval = *iter;
        if (
            textInOval->_rad1 == rad1 &&
            textInOval->_rad2 == rad2 &&
            textInOval->_x == x &&
            textInOval->_y == y &&
            textInOval->_content.compare(content) == 0
            )
        {   // такой есть
            return textInOval;
        }
    }
    // не нашли - создаем новый
    TextInOval* textInOval = new TextInOval(rad1, rad2, content, x, y);
    _textsInOvals.push_back(textInOval);
    return textInOval;
}
*/
//##ModelId=474055EF00AB
TextInOval* TextInOval::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float r1, r2;
    // определяем радиусы овала
    r1 = fabs(Start.x - End.x);
    r2 = fabs(Start.y - End.y);

    // получаем последний введенный пользователем текст
    CString cs = *(TextRequest::Text());
    std::string str((LPCSTR)cs);
    TextInOval* textInOval = new TextInOval();//create(r1, r2, str, 0, 0);
    
    textInOval->m_Pen = 1;
    textInOval->m_Color = aColor;
    textInOval->resize(Start, End);

    return textInOval;    
}

//##ModelId=473EDDF40245
TextInOval::~TextInOval()
{
    _textsInOvals.remove(this);
	cout<<"[text in oval] text in oval destroyed"<<endl;
}

//##ModelId=473EDDF40254
std::ostream& TextInOval::speak(std::ostream& os) const
{
    return 
        Shape::speak(os)
        <<"[text_in_oval] oval chords: ("
        <<getRad1()<<", "<<getRad2()<<")"<<"\n"
        <<"[text_in_oval] text content: "
        <<get__content().c_str()<<"\n"
        ;
}

//##ModelId=473EDDF401E7
// TextInOval::TextInOval( float rad1, float rad2, std::string content, float x, float y ):
// Text(content, 0, 0), Oval(rad1, rad2, 0, 0), Shape(x, y)
// {
//     cout<<"[text_in_oval] text in oval created"<<endl;
// }

//##ModelId=473EDDF40252
float TextInOval::Area() const
{
    return Oval::Area();
}

//##ModelId=4754604A0000
void TextInOval::Draw( CDC* pDC, CElement* pElement, bool isIdVisible )
{
    Oval::Draw(pDC, pElement, isIdVisible);
    Text::Draw(pDC, pElement, isIdVisible);
}

//##ModelId=4751693802BF
void TextInOval::Serialize(CArchive& ar)
{
    Shape::Serialize(ar);
    
    if (ar.IsStoring())
    {  // storing code
        CString cs = _content.c_str();
        ar << cs
           << _rad1
           << _rad2;
    }
    else
    { // loading code
        CString cs;
        ar >> cs
           >> _rad1
           >> _rad2;
        _content = (LPCSTR)cs;
    }
}

//##ModelId=4751CD2D01C5
int TextInOval::getType() const
{
    return TEXT_IN_OVAL;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4751AC8C030D
TextInOval::TextInOval()
{
	// ToDo: Add your specialized code here and/or call the base class
}



