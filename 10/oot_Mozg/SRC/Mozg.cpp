// **************************************************************************
//! \file   Mozg.cpp
//! \brief  main source file for Mozg.exe
//! \author Bessonov A.V.
//! \date   17.May.2008 - 19.May.2008
// **************************************************************************


#include "stdafx.h"
#include <new.h>

CAppModule _Module;

// ==========================================================================
int Mozg_new_handler( size_t )
{
   CONFIRM(!"Оператор new вернул NULL!!!");
   throw std::bad_alloc();
   return 0;
}

// ==========================================================================
int Run(LPTSTR /*lpstrCmdLine*/ = NULL, int nCmdShow = SW_SHOWDEFAULT)
{
   // БРОСАТЬ ИСКЛЮЧЕНИЕ, ЕСЛИ new ВЕРНУЛ NULL
   _set_new_handler( Mozg_new_handler );
   _set_new_mode(1); // ТО ЖЕ, ДЛЯ malloc

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

   //! Создаем объкт контролирующий ход игры
   Game::Controller* g_control = Game::Controller::Instance(dlgMain);
   //Game::Controller* g_control = new Game::Controller(dlgMain);

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
