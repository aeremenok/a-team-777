// OleDriver.h: interface for the OleDriver class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OLEDRIVER_H__E4F4B416_7A31_40B3_B98B_8F8DF901F380__INCLUDED_)
#define AFX_OLEDRIVER_H__E4F4B416_7A31_40B3_B98B_8F8DF901F380__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class OleDriver : public COleDispatchDriver  
{
public:
	OleDriver();
	virtual ~OleDriver();

	BOOL deleteElement(LPCTSTR key);
	void drawLine(float x1, float y1, float x2, float y2, LPCTSTR key);
	void showWindow();
	short getHashSize();
};

#endif // !defined(AFX_OLEDRIVER_H__E4F4B416_7A31_40B3_B98B_8F8DF901F380__INCLUDED_)
