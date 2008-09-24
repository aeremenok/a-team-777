#include "stdafx.h"
#include "iostream.h"
#include "math.h"
#include "CDiamond.h"

int CDiamond::KOLdiamond = 0;

//constructors & desctuctors
CDiamond::~CDiamond(){
	cout << "[CDiamond] Deleting. ID="<<getID()<<"; DiamondID="<<this->idDiamond<<"; x="<<this->x<<"; y="<<this->y<<"; l="<<this->l<<endl;
};
//constructor
CDiamond::CDiamond(double newx, double newy, double newl, double newU):CShape(newx,newy),l(newl),u(newU),idDiamond(KOLdiamond)
{
	KOLdiamond++;
	cout << "[CDiamond] Created. ID="<<getID()<<"; DiamondID="<<this->idDiamond<<" x="<<this->x<<"; y="<<this->y<<"; l="<<this->l<<"; U="<<this->u<<endl;
};

//re-defined function
 ostream& CDiamond::printInfo(ostream&os) const{
	 return CShape::printInfo(os) << ";{CDiamond} DiamondID="<<this->idDiamond<<"; l="<<this->l<<"; U="<<this->u<<endl;
};

//getters
const double CDiamond::getL()const {
	return this->l;
};
const double CDiamond::getU()const {
	return this->u;
};
//setters
void CDiamond::setL(double newL){
	this->l = newL;
};
void CDiamond::setU(double newU){
	this->u = newU;
};
//pure virtual function
double CDiamond::S()const{
	return this->l * this->l * sin(this->u);
};

double CDiamond::P()const{
	return 4 * this->l;
};