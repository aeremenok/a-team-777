#ifndef _CTEXTINSQUARE_H
#define _CTEXTINSQUARE_H
//////////////////////////////////////////////////////////////////////////
#include "CSquare.h"
#include "CText.h"


//////////////////////////////////////////////////////////////////////////
class ostream;
//////////////////////////////////////////////////////////////////////////
class CTextInSquare : public CSquare, public CText
{

private:
	
	static KOLtextInSquare;
        
protected:

	int IDtextInSquare;
   
public:

	CTextInSquare(CPoint c1,CPoint c2, COLORREF aColor,CString content);
	CTextInSquare():IDtextInSquare(KOLtextInSquare){KOLtextInSquare++;};

	virtual ~CTextInSquare();

	void Draw(CDC* pDC, CElement* pElement, int l=0);
	WORD getType(){return CSHAPE_TEXT_IN_SQUARE;};
	virtual void Serialize(CArchive& ar);

    //вычисляет площадь фигуры
    virtual double S() const;
	virtual double P() const;
};
#endif 
