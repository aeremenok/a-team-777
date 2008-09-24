#ifndef _CTEXT_H
#define _CTEXT_H

#include "CShape.h"
#include <string>
//�����
class CText : public virtual CShape
{
private:
	static KOLtext;   
        
protected:
    
	int IDtext;

    

    //���������� ������
    std::string _content;

    //������� ��������� �������������� � �����
    virtual ostream& printInfo(ostream& os) const;
public:

	CText(std::string content, float x, float y);
    
    const std::string& get__content() const;
	void set__content(std::string& value);

    //##ModelId=46F676990213
	virtual ~CText();

    //��������� ������� ������    
    virtual double S() const;
	virtual double P() const;
};
#endif 
