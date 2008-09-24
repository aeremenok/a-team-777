#ifndef _CSQUARE_H
#define _CSQUARE_H

#include "afxwin.h"
#include "CShape.h"


//фигура
class CSquare: public virtual CShape{

private:
	static int KOLsquare;

	
protected:

	const int IDsquare;

	//длина стороны
	double l;

	
public:
	//constructors & desctuctors
	CSquare(CPoint p1, CPoint c2, COLORREF aColor);
	CSquare():IDsquare(KOLsquare){KOLsquare++;};


	void Draw(CDC* pDC, CElement* pElement, int l=0);

	//constructors & desctuctors
	virtual ~CSquare();

	//getters
	const double getL() const;
	WORD getType(){return CSHAPE_SQUARE;};
	virtual void Serialize(CArchive& ar);

	//setters
	void setL(double newL);

	
	//pure virtual function
	double S()const;
	double P()const;
};
#endif
