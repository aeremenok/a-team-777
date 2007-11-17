// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "TextInOval.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40223
list<TextInOval*> TextInOval::_textsInOvals;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40232
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

//##ModelId=473EDDF40245
TextInOval::~TextInOval()
{
    _textsInOvals.remove(this);
	cout<<"[text in oval] text in oval destroyed"<<endl;
}

//##ModelId=473EDDF40254
ostream& TextInOval::speak(ostream& os) const
{
    return 
        Shape::speak(os)
        <<"[text_in_oval] oval chords: ("
        <<_rad1<<", "<<_rad2<<")"<<endl
        <<"[text_in_oval] text content: "
        <<_content.c_str()<<endl
        ;
}

//##ModelId=473EDDF401E7
TextInOval::TextInOval( float rad1, float rad2, std::string content, float x, float y ):
Text(content, 0, 0), Oval(rad1, rad2, 0, 0), Shape(x, y)
{
    cout<<"[text_in_oval] text in oval created"<<endl;
}

//##ModelId=473EDDF40252
float TextInOval::Area() const
{
    return Oval::Area();
}

//##ModelId=473EF4F500DA
void TextInOval::Draw( CDC* pDC )
{
    Oval::Draw(pDC);
    Text::Draw(pDC);
}
//////////////////////////////////////////////////////////////////////////
