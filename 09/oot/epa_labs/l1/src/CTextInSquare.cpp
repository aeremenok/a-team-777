#include "stdafx.h"
#include "CTextInSquare.h"
#include <ostream.h>

int CTextInSquare::KOLtextInSquare = 0;

CTextInSquare::~CTextInSquare()
{
	cout<<"[Text In Square] Destroyed. ID="<<getID()<<"; TextID="<<this->IDtext<<"."<<endl;
}


ostream& CTextInSquare::printInfo(ostream& os) const
{
    return 
        CShape::printInfo(os)
        <<"*[Text In Square] ID="<<getID()<<"; TextID="<<this->IDtext<<". Square len="
        <<CSquare::l
        <<"; Text: "
        <<_content.c_str()<<endl;
}


CTextInSquare::CTextInSquare( std::string content, double x, double y,  double l):
CText(content, 0, 0), CSquare(0, 0, l), CShape(x, y), idTextInSquare(KOLtextInSquare)
{
	KOLtextInSquare++;
    cout<<"[Text In Square] Created. ID="<<getID()<<"; TextID="<<this->IDtext<<"."<<endl;
}


double CTextInSquare::S() const
{
    return CSquare::S();
}


double CTextInSquare::P() const
{
    return CSquare::P();
}
