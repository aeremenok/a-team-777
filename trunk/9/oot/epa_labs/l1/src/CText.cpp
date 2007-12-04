#include "stdafx.h"
#include "CText.h"
#include <ostream.h>

int CText::KOLtext = 0;

class ostream;
CText::CText( std::string content, float x, float y )
: CShape(x, y), IDtext(KOLtext)
{
	KOLtext++;
    _content = content;
    cout<<"[CText] created. ID="<<getID()<<"; TextID="<<this->IDtext<<"."<<endl;
}


CText::~CText()
{
	cout<<"[CText] Destroyed ID="<<getID()<<"; TextID="<<this->IDtext<<"."<<endl;
}

ostream& CText::printInfo(ostream& os) const
{
    return CShape::printInfo(os)
        <<"[CText] ID="<<getID()<<"; TextID="<<this->IDtext<<"; Text content: "
        <<_content.c_str()<<endl;
}


const std::string& CText::get__content() const
{
    return _content;
}


void CText::set__content(std::string& value)
{
    _content = value;
    return;
}


double CText::S() const
{
    return -1;
}
double CText::P() const
{
    return -1;
}

//////////////////////////////////////////////////////////////////////////
