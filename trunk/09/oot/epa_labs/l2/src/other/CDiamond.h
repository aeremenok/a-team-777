#ifndef _CCDIAMOND_H
#define _CCDIAMOND_H

#include "CShape.h"

//фигура
class CDiamond: public CShape{

private:

	static int KOLdiamond;

protected:

	const int IDdiamond;

	//длина стороны
	int l;
	double u; //угол
	CPoint t1;
	CPoint t2;
	CPoint t3;
	CPoint t4;
	
public:

	//constructors & desctuctors
	CDiamond(CPoint c1, CPoint c2, COLORREF aColor);
	CDiamond():IDdiamond(KOLdiamond){KOLdiamond++;};


	//constructors & desctuctors
	~CDiamond();

	void Draw(CDC* pDC, CElement* pElement, int l=0);
	void Move(CSize& aSize);

	//getters
	const double getL() const;
	const double getU() const;
	WORD getType(){return CSHAPE_DIAMOND;};
	virtual void Serialize(CArchive& ar);

	//setters
	void setL(int newL);
	void setU(double newU);

	
	//pure virtual function
	double S()const;
	double P()const;
};
#endif
