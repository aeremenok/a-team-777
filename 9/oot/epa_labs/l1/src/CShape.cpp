#include "stdafx.h"
#include "iostream.h"
#include "CShape.h"

int CShape::KOL = 0;

const double CShape::getX()const {
	return this->x;
};

const double CShape::getY()const {
	return this->y;
};

const int CShape::getID()const {
	return this->id;
};

void CShape::setX(double newX){
	this->x = newX;
};

void CShape::setY(double newY){
	this->y = newY;
};

void CShape::moveX(double deltaX){
	this->x += deltaX;
};

void CShape::moveY(double deltaY){
	this->y += deltaY;
};

CShape::CShape(double x, double y):id(KOL){
	this->x = x;
	this->y = y;
	KOL++;
	cout<<"[CShape] Created "<< id << " (" << x <<", "<< y <<") "<<endl;
};

CShape::~CShape()
{
	cout  <<"[CShape] " << id << " IS DESTROYED "<<endl;
};

ostream& CShape::printInfo(ostream& os) const
{
    return os << "[CShape] ID=" << id <<
		"(" << x << ", " << y << ") " <<
		"S=" << S() << "; P=" << P();
};

bool CShape::operator==(const CShape& rhs) const
{
    // учитывая проверку при создании объектов,
    // достаточно сравнить идентификаторы
    return (this->id == rhs.id);
};


ostream& operator<<( ostream& o, const CShape& rhs )
{
    return rhs.printInfo(o);
}
