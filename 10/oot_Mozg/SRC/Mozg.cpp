// Mozg.cpp : main source file for Mozg.exe
//

#include "stdafx.h"
#include "MainDlg.h"
#include "Controller.h"

CAppModule _Module;

int Run(LPTSTR /*lpstrCmdLine*/ = NULL, int nCmdShow = SW_SHOWDEFAULT)
{
	CMessageLoop theLoop;
	_Module.AddMessageLoop(&theLoop);

   //CMainDlg* dlgMain;
   Game::CMainDlg* dlgMain = new Game::CMainDlg;

   //if(dlgMain.Create(NULL) == NULL)
	if(dlgMain->Create(NULL) == NULL)
	{
		ATLTRACE(_T("Main dialog creation failed!\n"));
		return 0;
	}
   //dlgMain.ShowWindow(nCmdShow);
	dlgMain->ShowWindow(nCmdShow);

   //! ������� ����� �������������� ��� ����
   Game::Controller* g_control = new Game::Controller(dlgMain);

	int nRet = theLoop.Run();

	_Module.RemoveMessageLoop();

   delete dlgMain;
   delete g_control;
	return nRet;
}

int WINAPI _tWinMain(HINSTANCE hInstance, HINSTANCE /*hPrevInstance*/, LPTSTR lpstrCmdLine, int nCmdShow)
{
	HRESULT hRes = ::CoInitialize(NULL);
// If you are running on NT 4.0 or higher you can use the following call instead to 
// make the EXE free threaded. This means that calls come in on a random RPC thread.
//	HRESULT hRes = ::CoInitializeEx(NULL, COINIT_MULTITHREADED);
	ATLASSERT(SUCCEEDED(hRes));

	// this resolves ATL window thunking problem when Microsoft Layer for Unicode (MSLU) is used
	::DefWindowProc(NULL, 0, 0, 0L);

	AtlInitCommonControls(ICC_BAR_CLASSES);	// add flags to support other controls

	hRes = _Module.Init(NULL, hInstance);
	ATLASSERT(SUCCEEDED(hRes));

	int nRet = Run(lpstrCmdLine, nCmdShow);

	_Module.Term();
	::CoUninitialize();

	return nRet;
}
