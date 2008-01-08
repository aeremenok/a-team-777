#ifndef _CSHAPE_H
#define _CSHAPE_H
#include "..\stdafx.h"
#include "..\OurConstants.h"
#include <math.h>
#include "iostream.h"
#include "afxwin.h"
#include "..\Elements.h"

//фигура
class CShape: public CElement{

private:

	static int KOL;
	const int id;

	
	
protected:

	//constructors & desctuctors
	CShape(CPoint x, CPoint y, COLORREF aColor);
	CShape(CRect rect, COLORREF aColor);
	CShape():id(KOL){KOL++;};

	char* convertIntToChar(int i);
	
public:
	//constructors & desctuctors	
	virtual ~CShape();

	void Move(CSize& aSize);

	

	//getters	
	const CPoint getTopLeft() const;	
	const CPoint getBottomRight() const;
	const int getID() const;

	//setters	
	void setTopLeft(CPoint newX);
	void setBottomRight(CPoint newY);

	//re-defined
	virtual bool operator==(const CShape& rhs) const;
	
	WORD getType(){return OTHER;};
	virtual void Serialize(CArchive& ar);
	static CElement* getFromArch(CArchive& ar);
	
	//pure virtual function
	virtual double S()const = 0;
	virtual double P()const = 0;
};
#endif
