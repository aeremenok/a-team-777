// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "TextInOval.h"

#include <ostream.h>
#include <math.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20600D4
list<TextInOval*> TextInOval::_textsInOvals;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20600F1
TextInOval* TextInOval::create( float firstRad /*= NULL*/, float secondRad /*= NULL*/,   std::string content /*= ""*/,   float x /*= NULL*/, float y /*= NULL  */ )
{
    // проверяем, нет ли уже такого текста в овале
    using namespace std;
    list<TextInOval*>::iterator iter;
    for (iter = _textsInOvals.begin(); iter != _textsInOvals.end(); iter++)
    {
        TextInOval* textInOval = *iter;
        if (
            textInOval->_rad1 == firstRad &&
            textInOval->_rad2 == secondRad &&
            textInOval->_x == x &&
            textInOval->_y == y &&
            textInOval->_content.compare(content) == 0
            )
        {   // такой есть
            return textInOval;
        }
    }
    // не нашли - создаем новый
    TextInOval* textInOval = new TextInOval(firstRad, secondRad, content, x, y);
    _textsInOvals.push_back(textInOval);
    return textInOval;
}

//##ModelId=4770E20600F8
TextInOval* TextInOval::create( CPoint Start, CPoint End, COLORREF aColor )
{
    float r1, r2;
    // определяем радиусы овала
    r1 = fabs(Start.x - End.x);
    r2 = fabs(Start.y - End.y);

    // получаем последний введенный пользователем текст
    CString cs = *(TextRequest::Text());
    std::string str((LPCSTR)cs);
    TextInOval* textInOval = create(r1, r2, str, 0, 0);
    
    textInOval->m_Pen = 1;
    textInOval->m_Color = aColor;
    textInOval->resize(Start, End);

    return textInOval;    
}

//##ModelId=4770E2060105
TextInOval::~TextInOval()
{
    _textsInOvals.remove(this);
	cout<<"[text in oval] text in oval destroyed"<<endl;
}

//##ModelId=4770E2060109
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

//##ModelId=4770E20600B5
TextInOval::TextInOval( float firstRad, float secondRad, std::string content, float x, float y ):
Text(content, 0, 0), Oval(firstRad, secondRad, 0, 0), Shape(x, y)
{
    cout<<"[text_in_oval] text in oval created"<<endl;
}

//##ModelId=4770E2060107
float TextInOval::Area() const
{
    return Oval::Area();
}

//##ModelId=4770E20600D8
void TextInOval::Draw( CDC* pDC, CElement* pElement, bool isIdVisible )
{
    Oval::Draw(pDC, pElement, isIdVisible);
    Text::Draw(pDC, pElement, isIdVisible);
}

//##ModelId=4770E20600E6
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

//##ModelId=4770E20600E3
int TextInOval::getType() const
{
    return TEXT_IN_OVAL;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E20600E5
TextInOval::TextInOval()
{
	// ToDo: Add your specialized code here and/or call the base class
}
