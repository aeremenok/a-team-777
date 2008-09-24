#ifndef _CTEXT_H
#define _CTEXT_H

#include "CShape.h"
#include <string>
//текст
class CText : public virtual CShape
{

private:
	static int KOLtext;   
        
protected:

	int IDtext;
	CPoint p1;
	CPoint p2;

    //содержимое текста
    CString _content;


public:
	CText(CPoint c1, CPoint c2, COLORREF aColor,CString content);
	CText():IDtext(KOLtext){KOLtext++;}

    const CString get__content() const;
	void set__content(CString value);

    //##ModelId=46F676990213
	~CText();

	void Draw(CDC* pDC, CElement* pElement, int l=0);
	WORD getType(){return CSHAPE_TEXT;};
	virtual void Serialize(CArchive& ar);

    //вычисляет площадь фигуры    
    virtual double S() const;
	virtual double P() const;
};
#endif 
