// Copyright (C) 1991 - 1999 Rational Software Corporation
//////////////////////////////////////////////////////////////////////////
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXT_46F50C7401C5_INCLUDED
#define _INC_TEXT_46F50C7401C5_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Shape.h"

// диалог
#include "../resource.h"
#include "../TextRequest.h"

#include <string>
//////////////////////////////////////////////////////////////////////////
//текст
//##ModelId=4770E2060120
class Text 
: public virtual Shape
{
private:
    //указатели на созданные тексты
    //##ModelId=4770E2060132
    static list<Text*> _texts;
protected:
    //##ModelId=4770E2060136
    Text(std::string content, float x, float y);

    //содержимое текста
    //##ModelId=4770E2060143
    std::string _content;

    //вывести состояние прямоугольника в поток
    //##ModelId=4770E2060147
    virtual std::ostream& speak(std::ostream& os) const;
public:
	// Virtual draw operation
	//##ModelId=4770E2060151
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//идентификатор типа объекта
	//##ModelId=4770E206015E
	virtual int getType() const;

	//##ModelId=4770E2060160
	Text();

	//##ModelId=4770E2060161
	virtual void Serialize(CArchive& ar);

    //##ModelId=4770E2060164
    const std::string& get__content() const;

    //##ModelId=4770E2060166
    void set__content(std::string& value);

    //выдает указатель на уже существующий текст, если он имеет заданные параметры
    //если такого не существует - создает новый
    //##ModelId=4770E206016F
    static Text* create(std::string content = "", float x = NULL, float y = NULL);

	//##ModelId=4770E2060174
    static Text* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=4770E2060182
	virtual ~Text();

    //вычисляет площадь фигуры
    //##ModelId=4770E2060184
    virtual float Area() const;
};
//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXT_46F50C7401C5_INCLUDED */
