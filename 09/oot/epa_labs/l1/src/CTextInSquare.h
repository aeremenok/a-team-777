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

	int idTextInSquare;

    //������� ��������� ������ � ����� � �����
    virtual ostream& printInfo(ostream& os) const;

public:
    CTextInSquare(std::string content, double x, double y,  double l);

	virtual ~CTextInSquare();
    
    //��������� ������� ������
    virtual double S() const;
	virtual double P() const;
};
#endif 
