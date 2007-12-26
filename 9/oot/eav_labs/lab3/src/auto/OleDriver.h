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
	void showWindow();
    void drawTextInOval(float x, float y, LPCTSTR content, float r1, float r2);
    void drawText(float x, float y, LPCTSTR content);
    void drawRectangle(float x, float y, float height, float width);
    void drawOval(float x, float y, float r1, float r2);
    void addRibble(short id1, short id2);
    void removeRibble(short id1, short id2);
};

#endif // !defined(AFX_OLEDRIVER_H__E4F4B416_7A31_40B3_B98B_8F8DF901F380__INCLUDED_)
