#ifndef _CCDIAMOND_H
#define _CCDIAMOND_H

#include "CShape.h"

//фигура
class CDiamond: public CShape{

private:
	static int KOLdiamond;

	
protected:

	const int idDiamond;

	
	//длина стороны
	double l;
	double u; //угол
	
	//re-defined function
	virtual ostream& printInfo(ostream&os) const;

public:
	//constructors & desctuctors
	CDiamond(double x, double y, double l, double u);

	//constructors & desctuctors
	~CDiamond();

	//getters
	const double getL() const;
	const double getU() const;

	//setters
	void setL(double newL);
	void setU(double newU);

	
	//pure virtual function
	double S()const;
	double P()const;
};
#endif
