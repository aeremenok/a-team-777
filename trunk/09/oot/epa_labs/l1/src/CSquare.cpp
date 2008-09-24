#include "stdafx.h"
#include "iostream.h"
#include "CSquare.h"

int CSquare::KOLsquare = 0;

//constructors & desctuctors
CSquare::~CSquare(){
	cout << "[CSquare] Deleting. ID="<<getID()<<"; SquareID=" << this->idSquare << 
		"; x=" << this->x<<"; y="<<this->y<<"; l="<<this->l<<endl;
};
//constructor
CSquare::CSquare(double newx, double newy, double newl):CShape(newx,newy),l(newl),idSquare(KOLsquare)
{
	KOLsquare++;
	cout << "[CSquare] Created. ID=" << getID() << "; SquareID=" << this->idSquare <<
		" x="<< this->x << "; y=" << this->y << "; l=" << this->l << endl;
};

//re-defined function
 ostream& CSquare::printInfo(ostream&os) const{
	 return CShape::printInfo(os) << ";{Square} SquareID=" << this->idSquare << "; l=" << this->l << endl;
};

//getters
const double CSquare::getL()const {
	return this->l;
};

//setters
void CSquare::setL(double newL){
	this->l = newL;
};

//pure virtual function
double CSquare::S()const{
	return this->l * this->l;
};

double CSquare::P()const{
	return 4 * this->l;
};