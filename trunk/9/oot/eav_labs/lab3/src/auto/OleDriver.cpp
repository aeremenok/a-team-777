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

BOOL OleDriver::deleteElement(LPCTSTR key){
	const BYTE params[] = VTS_BSTR;
	InvokeHelper(0x1, DISPATCH_METHOD, VT_EMPTY, NULL, params, key);
	return true;
}

void OleDriver::showWindow(){
	InvokeHelper(0x2, DISPATCH_METHOD, VT_EMPTY, NULL, NULL);
}

void OleDriver::drawLine(float x1, float y1, float x2, float y2, LPCTSTR key){
	const BYTE params[] = VTS_R4 VTS_R4 VTS_R4 VTS_R4 VTS_BSTR;
	InvokeHelper(0x3, DISPATCH_METHOD, VT_EMPTY, NULL, params, x1, y1, x2, y2, key);
}

short OleDriver::getHashSize(){
	short ret;
	InvokeHelper(0x4, DISPATCH_METHOD, VT_I2, (void*)&ret, NULL);
	return ret;
}
