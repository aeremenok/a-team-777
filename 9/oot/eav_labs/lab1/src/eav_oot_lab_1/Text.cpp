// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#include "Text.h"

#include <ostream.h>
//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
//##ModelId=4736C55C01C5
list<Text*> Text::_texts;
//////////////////////////////////////////////////////////////////////////
//##ModelId=472DF1EC01B5
Text::Text( std::string content, float x, float y )
: Shape(x, y)
{
    _content = content;
    cout<<"[text] text created"<<endl;
}

//##ModelId=4736C4CA000F
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

//##ModelId=46F676990213
Text::~Text()
{
    _texts.remove(this);
	cout<<"[text] text destroyed"<<endl;
}

//##ModelId=471218CE00DA
ostream& Text::speak(ostream& os) const
{
    return Shape::speak(os)
        <<"[text] text content: "
        <<_content.c_str()<<endl;
}

//##ModelId=4736C4B9037A
const std::string& Text::get__content() const
{
    return _content;
}

//##ModelId=4736C4BA02D0
void Text::set__content(std::string& value)
{
    _content = value;
    return;
}

//##ModelId=472DF2970261
float Text::Area() const
{
    return -1;
}

//////////////////////////////////////////////////////////////////////////
