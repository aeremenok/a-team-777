// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"

#include "Text.h"
#include "..\resource.h"
#include "..\TextRequest.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060132
list<Text*> Text::_texts;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060136
Text::Text( std::string content, float x, float y )
: Shape(x, y)
{
    _content = content;
    cout<<"[text] text created"<<endl;
}

//##ModelId=4770E206016F
Text* Text::create(std::string content, float x, float y)
{
    // проверяем, нет ли уже такого текста
    using namespace std;
    list<Text*>::iterator iter;
    for (iter = _texts.begin(); iter != _texts.end(); iter++)
    {
        Text* text = *iter;
        if (
            text->_x == x &&
            text->_y == y &&
            text->_content.compare(content) == 0
            )
        {   // такой есть
            return text;
        }
    }
    // не нашли - создаем новый
    Text* text = new Text(content, x, y);
    _texts.push_back(text);
    return text;
}

//##ModelId=4770E2060174
Text* Text::create( CPoint Start, CPoint End, COLORREF aColor )
{
    // получаем последний введенный пользователем текст
    CString cs = *(TextRequest::Text());
    std::string str((LPCSTR)cs);
    Text* text = create(str, 0, 0);

    // задаем параметры отрисовки
    text->m_Pen = 1;
    text->m_Color = aColor;
    text->resize(Start, End);

    return text;
}

//##ModelId=4770E2060182
Text::~Text()
{
    _texts.remove(this);
	cout<<"[text] text destroyed"<<endl;
}

//##ModelId=4770E2060147
std::ostream& Text::speak(std::ostream& os) const
{
    return Shape::speak(os)
        <<"[text] text content: "
        <<_content.c_str()<<"\n";
}

//##ModelId=4770E2060164
const std::string& Text::get__content() const
{
    return _content;
}

//##ModelId=4770E2060166
void Text::set__content(std::string& value)
{
    _content = value;
    return;
}

//##ModelId=4770E2060184
float Text::Area() const
{
    return -1;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060151
void Text::Draw( CDC* pDC, CElement* pElement, bool isIdVisible )
{
    COLORREF aColor = m_Color;
    if (this == pElement)
    {
        aColor = SELECT_COLOR;
    }
    
    // Now draw the text
    pDC->SetTextColor(aColor);
    pDC->TextOut(_x, _y, _content.c_str());
    drawID(pDC, isIdVisible);
}

//##ModelId=4770E2060161
void Text::Serialize(CArchive& ar)
{
    Shape::Serialize(ar);
    
    if (ar.IsStoring())
    {  // storing code
        CString cs = _content.c_str();
        ar << cs;
    }
    else
    { // loading code
        CString cs = "";
        ar >> cs;
        _content = (LPCSTR)cs;
    }
}

//##ModelId=4770E206015E
int Text::getType() const
{
    return TEXT;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=4770E2060160
Text::Text()
{
	// ToDo: Add your specialized code here and/or call the base class
}
