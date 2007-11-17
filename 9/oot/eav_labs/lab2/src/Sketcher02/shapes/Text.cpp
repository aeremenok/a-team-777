// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "StdAfx.h"
#include "Text.h"

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
