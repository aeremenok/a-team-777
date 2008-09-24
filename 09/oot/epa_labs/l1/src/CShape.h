#ifndef _CSHAPE_H
#define _CSHAPE_H

#include "iostream.h"

//фигура
class CShape{
private:

	static int KOL;

	const int id;
	
protected:

	
		
	double x;
	double y;

	//constructors & desctuctors
	CShape(double x, double y);

	//re-defined function
	virtual ostream& printInfo(ostream&os) const;

public:
	//constructors & desctuctors
	virtual ~CShape();

	//getters	
	const double getX() const;	
	const double getY() const;
	const int getID() const;

	//setters	
	void setX(double newX);
	void setY(double newY);

	//re-defined
	friend ostream& operator<<(ostream& o, const CShape& rhs);
	virtual bool operator==(const CShape& rhs) const;
	
	//some user-functions
	void moveX(double deltaX);
	void moveY(double deltaY);

	//pure virtual function
	virtual double S()const = 0;
	virtual double P()const = 0;
};
#endif
