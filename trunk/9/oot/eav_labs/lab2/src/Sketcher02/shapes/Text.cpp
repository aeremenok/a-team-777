// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Text.h"

#include "..\OurConstants.h"
#include "..\resource.h"
#include "..\TextRequest.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40281
list<Text*> Text::_texts;
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EDDF40285
Text::Text( std::string content, float x, float y )
: Shape(x, y)
{
    _content = content;
    cout<<"[text] text created"<<endl;
}

//##ModelId=473EDDF402BF
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

//##ModelId=474055EF0167
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

//##ModelId=473EDDF402CF
Text::~Text()
{
    _texts.remove(this);
	cout<<"[text] text destroyed"<<endl;
}

//##ModelId=473EDDF402A4
ostream& Text::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[text] text content: "
        <<_content.c_str()<<endl;
}

//##ModelId=473EDDF402B0
const std::string& Text::get__content() const
{
    return _content;
}

//##ModelId=473EDDF402B2
void Text::set__content(std::string& value)
{
    _content = value;
    return;
}

//##ModelId=473EDDF402D1
float Text::Area() const
{
    return -1;
}
//////////////////////////////////////////////////////////////////////////
//##ModelId=473EF26003D8
void Text::Draw( CDC* pDC, CElement* pElement/*=0*/ )
{
    // Create a pen for this object and
    // initialize it to the object color and line width of 1 pixel
//    CPen aPen;
    COLORREF aColor = m_Color;
    if (this == pElement)
    {
        aColor = SELECT_COLOR;
    }
//     if(!aPen.CreatePen(PS_SOLID, m_Pen, m_Color))
//     {                                           // Pen creation failed
//         AfxMessageBox("Pen creation failed drawing a text", MB_OK);
//         AfxAbort();
//     }
//     
//     // Select the pen
//     CPen* pOldPen = pDC->SelectObject(&aPen);   
//     // Select the brush
//     CBrush* pOldBrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH); 
    
    // Now draw the text
    pDC->SetTextColor(aColor);
    pDC->TextOut(_x, _y, _content.c_str());
    drawID(pDC);
    
//     pDC->SelectObject(pOldBrush);              // Restore the old brush
//     pDC->SelectObject(pOldPen);                // Restore the old pen
}
//////////////////////////////////////////////////////////////////////////
