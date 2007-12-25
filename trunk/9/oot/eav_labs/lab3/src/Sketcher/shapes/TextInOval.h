// Copyright (C) 1991 - 1999 Rational Software Corporation
#if defined (_MSC_VER) && (_MSC_VER >= 1000)
#pragma once
#endif
#ifndef _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
#define _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED
//////////////////////////////////////////////////////////////////////////
#include "Oval.h"
#include "Text.h"
//////////////////////////////////////////////////////////////////////////
//текст в овале
//##ModelId=4770E20600A3
class TextInOval 
: public virtual Oval
, public virtual Text
{
private:
    //##ModelId=4770E20600B5
    TextInOval(float firstRad, float secondRad, std::string content, float x, float y);

    //указатели на созданные тексты в овалах
    //##ModelId=4770E20600D4
    static list<TextInOval*> _textsInOvals;
public:
	// Virtual draw operation
	//##ModelId=4770E20600D8
	virtual void Draw(CDC* pDC, CElement* pElement = 0, bool isIdVisible = true);

	//идентификатор типа объекта
	//##ModelId=4770E20600E3
	virtual int getType() const;

	//##ModelId=4770E20600E5
	TextInOval();

	//##ModelId=4770E20600E6
	virtual void Serialize(CArchive& ar);

    //выдает указатель на уже существующий текст в овале, если он имеет заданные 
    //параметры
    //если такого не существует - создает новый
    //##ModelId=4770E20600F1
    static TextInOval* create(  float firstRad = NULL, float secondRad = NULL,   std::string content = "",   float x = NULL, float y = NULL  );

	//##ModelId=4770E20600F8
    static TextInOval* create(CPoint Start, CPoint End, COLORREF aColor);

	//##ModelId=4770E2060105
	virtual ~TextInOval();
    
    //вычисляет площадь фигуры
    //##ModelId=4770E2060107
    virtual float Area() const;
protected:
    //вывести состояние текста в овале в поток
    //##ModelId=4770E2060109
    virtual std::ostream& speak(std::ostream& os) const;
};

//##ModelId=4754604A0000

//////////////////////////////////////////////////////////////////////////
#endif /* _INC_TEXTINOVAL_46F50C7B01D4_INCLUDED */
