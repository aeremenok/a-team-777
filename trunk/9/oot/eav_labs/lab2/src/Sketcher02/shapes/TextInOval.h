// Copyright (C) 1991 - 1999 Rational Software Corporation
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include "Text.h"

#include <list>
//////////////////////////////////////////////////////////////////////////
class ostream;
using std::list;
//////////////////////////////////////////////////////////////////////////
//текст в овале
//##ModelId=473EDDF401E4
class TextInOval 
: public virtual Oval
, public virtual Text
{
private:
    //##ModelId=473EDDF401E7
    TextInOval(float rad1, float rad2, std::string content, float x, float y);

    //указатели на созданные тексты в овалах
    //##ModelId=473EDDF40223
    static list<TextInOval*> _textsInOvals;
public:
	//идентификатор типа объекта
	//##ModelId=4751CD2D01C5
	virtual int getType() const;

	//##ModelId=4751AC8C030D
	TextInOval();

	//##ModelId=4751693802BF
	virtual void Serialize(CArchive& ar);

    //выдает указатель на уже существующий текст в овале, если он имеет заданные 
    //параметры
    //если такого не существует - создает новый
    //##ModelId=473EDDF40232
    static TextInOval* create(
        float rad1 = NULL, float rad2 = NULL, 
        std::string content = NULL, 
        float x = NULL, float y = NULL
        );

	//##ModelId=474055EF00AB
    static TextInOval* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=473EDDF40245
	virtual ~TextInOval();
    
    //вычисляет площадь фигуры
    //##ModelId=473EDDF40252
    virtual float Area() const;

    //Virtual draw operation
    //##ModelId=473EF4F500DA
    virtual void Draw(CDC* pDC, CElement* pElement=0);
protected:
    //вывести состояние текста в овале в поток
    //##ModelId=473EDDF40254
    virtual std::ostream& speak(std::ostream& os) const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
