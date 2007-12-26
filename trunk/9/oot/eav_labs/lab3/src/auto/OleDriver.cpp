// OleDriver.cpp: implementation of the OleDriver class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "lab3driv.h"
#include "OleDriver.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
OleDriver::OleDriver()
{
}

OleDriver::~OleDriver()
{
}

BOOL OleDriver::deleteElement(LPCTSTR key)
{
	const BYTE params[] = VTS_BSTR;
	InvokeHelper(0x1, DISPATCH_METHOD, VT_EMPTY, NULL, params, key);
	return true;
}

void OleDriver::showWindow()
{
	InvokeHelper(0x2, DISPATCH_METHOD, VT_EMPTY, NULL, NULL);
}

void OleDriver::drawTextInOval( float x, float y, LPCTSTR content, float r1, float r2 )
{
    const BYTE params[] = VTS_R4 VTS_R4 VTS_BSTR VTS_R4 VTS_R4;
    InvokeHelper(0x3, DISPATCH_METHOD, VT_EMPTY, NULL, params, x, y, content, r1, r2);
}

void OleDriver::drawText( float x, float y, LPCTSTR content )
{
    const BYTE params[] = VTS_R4 VTS_R4 VTS_BSTR;
    InvokeHelper(0x4, DISPATCH_METHOD, VT_EMPTY, NULL, params, x, y, content);    
}

void OleDriver::drawRectangle( float x, float y, float height, float width )
{
    const BYTE params[] = VTS_R4 VTS_R4 VTS_R4 VTS_R4;
    InvokeHelper(0x5, DISPATCH_METHOD, VT_EMPTY, NULL, params, x, y, height, width);    
}

void OleDriver::drawOval( float x, float y, float r1, float r2 )
{
    const BYTE params[] = VTS_R4 VTS_R4 VTS_R4 VTS_R4;
    InvokeHelper(0x6, DISPATCH_METHOD, VT_EMPTY, NULL, params, x, y, r1, r2);
}

void OleDriver::addRibble( LPCTSTR id1, LPCTSTR id2 )
{
    AfxMessageBox(id1);
    AfxMessageBox(id2);
    const BYTE params[] = VTS_BSTR VTS_BSTR;
    InvokeHelper(0x7, DISPATCH_METHOD, VT_EMPTY, NULL, params, id1, id2);
}

void OleDriver::removeRibble( LPCTSTR id1, LPCTSTR id2 )
{
    const BYTE params[] = VTS_BSTR VTS_BSTR;
    InvokeHelper(0x8, DISPATCH_METHOD, VT_EMPTY, NULL, params, id1, id2);    
}
