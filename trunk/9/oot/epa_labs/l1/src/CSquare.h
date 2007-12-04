#ifndef _CSQUARE_H
#define _CSQUARE_H

#include "CShape.h"

//������
class CSquare: public virtual CShape{

private:
	static int KOLsquare;

	
protected:

	const int idSquare;

	//����� �������
	double l;
	
	//re-defined function
	virtual ostream& printInfo(ostream&os) const;

public:
	//constructors & desctuctors
	CSquare(double x, double y, double l);

	//constructors & desctuctors
	~CSquare();

	//getters
	const double getL() const;

	//setters
	void setL(double newL);

	
	//pure virtual function
	double S()const;
	double P()const;
};
#endif
