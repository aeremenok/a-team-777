; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CInPlaceFrame
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "Sketcher.h"
ODLFile=Sketcher.odl
LastPage=0

ClassCount=7
Class1=CSketcherApp
Class2=CSketcherDoc
Class3=CSketcherView
Class4=CMainFrame
Class5=CInPlaceFrame
Class7=CAboutDlg

ResourceCount=15
Resource1=IDD_ABOUTBOX
Resource2=IDR_MAINFRAME
Resource3=IDR_SKETCHTYPE
Resource4=IDR_SKETCHTYPE_CNTR_IP
Resource5=IDR_SKETCHTYPE_SRVR_IP
Resource6=IDR_SKETCHTYPE_SRVR_EMB
Resource8=IDR_SKETCHTYPE (English (U.S.))
Resource9=IDR_SKETCHTYPE_CNTR_IP (English (U.S.))
Resource10=IDR_MAINFRAME (English (U.S.))
Resource11=IDD_ABOUTBOX (English (U.S.))
Resource7=IDR_SKETCHTYPE_SRVR_IP (English (U.S.))
Resource12=IDD_SCALE_DLG
Resource13=IDR_CURSOR_MENU (English (U.S.))
Resource14=IDD_TEXTREQUEST_DIALOG
Class6=CChildFrame
Resource15=IDR_SKETCHTYPE_SRVR_EMB (English (U.S.))

[CLS:CSketcherApp]
Type=0
HeaderFile=Sketcher.h
ImplementationFile=Sketcher.cpp
Filter=N

[CLS:CSketcherDoc]
Type=0
HeaderFile=SketcherDoc.h
ImplementationFile=SketcherDoc.cpp
Filter=N
BaseClass=COleServerDoc
VirtualFilter=vLODC
LastObject=CSketcherDoc

[CLS:CSketcherView]
Type=0
HeaderFile=SketcherView.h
ImplementationFile=SketcherView.cpp
Filter=C
LastObject=CSketcherView
BaseClass=CScrollView
VirtualFilter=VWC


[CLS:CMainFrame]
Type=0
HeaderFile=MainFrm.h
ImplementationFile=MainFrm.cpp
Filter=T

[CLS:CInPlaceFrame]
Type=0
HeaderFile=IpFrame.h
ImplementationFile=IpFrame.cpp
Filter=T
LastObject=CInPlaceFrame

[CLS:CChildFrame]
Type=0
HeaderFile=ChildFrm.h
ImplementationFile=ChildFrm.cpp
Filter=M
LastObject=CChildFrame


[CLS:CAboutDlg]
Type=0
HeaderFile=Sketcher.cpp
ImplementationFile=Sketcher.cpp
Filter=D
LastObject=CAboutDlg

[DLG:IDD_ABOUTBOX]
Type=1
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308352
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889
Class=CAboutDlg

[MNU:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command4=ID_APP_EXIT
Command5=ID_VIEW_STATUS_BAR
CommandCount=6

CommandCount=6
Command3=ID_FILE_MRU_FILE1
Command6=ID_APP_ABOUT

[MNU:IDR_SKETCHTYPE]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_SAVE
Command5=ID_FILE_SAVE_AS
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_EDIT_PASTE_SPECIAL
Command13=ID_OLE_INSERT_NEW
Command14=ID_OLE_EDIT_LINKS
Command15=ID_VIEW_STATUS_BAR
Command16=ID_WINDOW_NEW
Command17=ID_WINDOW_CASCADE
CommandCount=20
Command6=ID_FILE_MRU_FILE1
Command7=ID_APP_EXIT
Command8=ID_EDIT_UNDO
Command18=ID_WINDOW_TILE_HORZ
Command19=ID_WINDOW_ARRANGE
Command20=ID_APP_ABOUT

[MNU:IDR_SKETCHTYPE_CNTR_IP]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_SAVE
Command5=ID_FILE_SAVE_AS
Command9=ID_WINDOW_CASCADE
Command10=ID_WINDOW_TILE_HORZ
Command11=ID_WINDOW_ARRANGE
CommandCount=11
Command6=ID_FILE_MRU_FILE1
Command7=ID_APP_EXIT
Command8=ID_WINDOW_NEW

[MNU:IDR_SKETCHTYPE_SRVR_EMB]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_UPDATE
Command5=ID_FILE_SAVE_COPY_AS
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_EDIT_PASTE_SPECIAL
Command13=ID_OLE_INSERT_NEW
Command14=ID_OLE_EDIT_LINKS
Command15=ID_VIEW_STATUS_BAR
Command16=ID_WINDOW_NEW
Command17=ID_WINDOW_CASCADE
CommandCount=20
Command6=ID_FILE_MRU_FILE1
Command7=ID_APP_EXIT
Command8=ID_EDIT_UNDO
Command18=ID_WINDOW_TILE_HORZ
Command19=ID_WINDOW_ARRANGE
Command20=ID_APP_ABOUT

[MNU:IDR_SKETCHTYPE_SRVR_IP]
Type=1
Class=CSketcherView
Command1=ID_EDIT_UNDO
Command2=ID_EDIT_CUT
Command3=ID_EDIT_COPY
Command4=ID_EDIT_PASTE
CommandCount=8
Command5=ID_EDIT_PASTE_SPECIAL
Command6=ID_OLE_INSERT_NEW
Command7=ID_OLE_EDIT_LINKS
Command8=ID_APP_ABOUT

[ACL:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command5=ID_EDIT_CUT
Command6=ID_EDIT_COPY
Command7=ID_EDIT_PASTE
Command8=ID_EDIT_UNDO
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_NEXT_PANE
CommandCount=13
Command4=ID_EDIT_UNDO
Command13=ID_PREV_PANE

[ACL:IDR_SKETCHTYPE_CNTR_IP]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command5=ID_PREV_PANE
CommandCount=5
Command4=ID_NEXT_PANE

[ACL:IDR_SKETCHTYPE_SRVR_IP]
Type=1
Class=CSketcherView
Command1=ID_EDIT_UNDO
Command2=ID_EDIT_CUT
Command3=ID_EDIT_COPY
Command4=ID_EDIT_PASTE
Command5=ID_EDIT_UNDO
Command6=ID_EDIT_CUT
Command7=ID_EDIT_COPY
Command8=ID_EDIT_PASTE
CommandCount=8

[ACL:IDR_SKETCHTYPE_SRVR_EMB]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_UPDATE
Command5=ID_EDIT_CUT
Command6=ID_EDIT_COPY
Command7=ID_EDIT_PASTE
Command8=ID_EDIT_UNDO
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
CommandCount=13
Command4=ID_EDIT_UNDO
Command12=ID_NEXT_PANE
Command13=ID_PREV_PANE


[MNU:IDR_MAINFRAME (English (U.S.))]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_MRU_FILE1
Command4=ID_APP_EXIT
Command5=ID_VIEW_STATUS_BAR
Command6=ID_APP_ABOUT
CommandCount=6

[MNU:IDR_SKETCHTYPE (English (U.S.))]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_SAVE
Command5=ID_FILE_SAVE_AS
Command6=ID_FILE_SEND_MAIL
Command7=ID_FILE_MRU_FILE1
Command8=ID_APP_EXIT
Command9=ID_EDIT_UNDO
Command10=ID_EDIT_CUT
Command11=ID_EDIT_COPY
Command12=ID_EDIT_PASTE
Command13=ID_EDIT_PASTE_SPECIAL
Command14=ID_OLE_INSERT_NEW
Command15=ID_OLE_EDIT_LINKS
Command16=ID_OLE_VERB_FIRST
Command17=ID_VIEW_STATUS_BAR
Command18=ID_ELEMENT_RECTANGLE
Command19=ID_ELEMENT_OVAL
Command20=ID_ELEMENT_TEXT
Command21=ID_ELEMENT_TEXT_IN_OVAL
Command22=ID_ELEMENT_RIBBLE
Command23=ID_ELEMENT_DRAWRIBBLES
Command24=ID_ELEMENT_SCALE
Command25=ID_COLOR_BLACK
Command26=ID_COLOR_RED
Command27=ID_WINDOW_NEW
Command28=ID_WINDOW_CASCADE
Command29=ID_WINDOW_TILE_HORZ
Command30=ID_WINDOW_ARRANGE
Command31=ID_APP_ABOUT
CommandCount=31

[MNU:IDR_SKETCHTYPE_CNTR_IP (English (U.S.))]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_SAVE
Command5=ID_FILE_SAVE_AS
Command6=ID_FILE_SEND_MAIL
Command7=ID_FILE_MRU_FILE1
Command8=ID_APP_EXIT
Command9=ID_WINDOW_NEW
Command10=ID_WINDOW_CASCADE
Command11=ID_WINDOW_TILE_HORZ
Command12=ID_WINDOW_ARRANGE
CommandCount=12

[MNU:IDR_SKETCHTYPE_SRVR_EMB (English (U.S.))]
Type=1
Class=CSketcherView
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_CLOSE
Command4=ID_FILE_UPDATE
Command5=ID_FILE_SAVE_COPY_AS
Command6=ID_FILE_SEND_MAIL
Command7=ID_FILE_MRU_FILE1
Command8=ID_APP_EXIT
Command9=ID_EDIT_UNDO
Command10=ID_EDIT_CUT
Command11=ID_EDIT_COPY
Command12=ID_EDIT_PASTE
Command13=ID_EDIT_PASTE_SPECIAL
Command14=ID_OLE_INSERT_NEW
Command15=ID_OLE_EDIT_LINKS
Command16=ID_OLE_VERB_FIRST
Command17=ID_VIEW_STATUS_BAR
Command18=ID_WINDOW_NEW
Command19=ID_WINDOW_CASCADE
Command20=ID_WINDOW_TILE_HORZ
Command21=ID_WINDOW_ARRANGE
Command22=ID_APP_ABOUT
CommandCount=22

[MNU:IDR_SKETCHTYPE_SRVR_IP (English (U.S.))]
Type=1
Class=?
Command1=ID_EDIT_UNDO
Command2=ID_EDIT_CUT
Command3=ID_EDIT_COPY
Command4=ID_EDIT_PASTE
Command5=ID_EDIT_PASTE_SPECIAL
Command6=ID_OLE_INSERT_NEW
Command7=ID_OLE_EDIT_LINKS
Command8=ID_OLE_VERB_FIRST
Command9=ID_APP_ABOUT
CommandCount=9

[ACL:IDR_MAINFRAME (English (U.S.))]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_EDIT_UNDO
Command5=ID_EDIT_CUT
Command6=ID_EDIT_COPY
Command7=ID_EDIT_PASTE
Command8=ID_EDIT_UNDO
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_NEXT_PANE
Command13=ID_PREV_PANE
Command14=ID_CANCEL_EDIT_CNTR
CommandCount=14

[ACL:IDR_SKETCHTYPE_CNTR_IP (English (U.S.))]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_NEXT_PANE
Command5=ID_PREV_PANE
Command6=ID_CANCEL_EDIT_CNTR
CommandCount=6

[ACL:IDR_SKETCHTYPE_SRVR_IP (English (U.S.))]
Type=1
Class=?
Command1=ID_EDIT_UNDO
Command2=ID_EDIT_CUT
Command3=ID_EDIT_COPY
Command4=ID_EDIT_PASTE
Command5=ID_EDIT_UNDO
Command6=ID_EDIT_CUT
Command7=ID_EDIT_COPY
Command8=ID_EDIT_PASTE
Command9=ID_CANCEL_EDIT_SRVR
CommandCount=9

[ACL:IDR_SKETCHTYPE_SRVR_EMB (English (U.S.))]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_UPDATE
Command4=ID_EDIT_UNDO
Command5=ID_EDIT_CUT
Command6=ID_EDIT_COPY
Command7=ID_EDIT_PASTE
Command8=ID_EDIT_UNDO
Command9=ID_EDIT_CUT
Command10=ID_EDIT_COPY
Command11=ID_EDIT_PASTE
Command12=ID_NEXT_PANE
Command13=ID_PREV_PANE
Command14=ID_CANCEL_EDIT_CNTR
CommandCount=14

[DLG:IDD_ABOUTBOX (English (U.S.))]
Type=1
Class=CAboutDlg
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:IDD_TEXTREQUEST_DIALOG]
Type=1
Class=?
ControlCount=4
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308865
Control4=IDC_EDIT1,edit,1350631552

[DLG:IDD_SCALE_DLG]
Type=1
Class=?
ControlCount=4
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_SCALE,edit,1350639744
Control4=IDC_SPIN_SCALE,msctls_updown32,1342177330

[MNU:IDR_CURSOR_MENU (English (U.S.))]
Type=1
Class=?
Command1=ID_MOVE
Command2=ID_DELETE
Command3=ID_SENDTOBACK
Command4=ID_NOELEMENT_SCALE
Command5=ID_COLOR_BLACK
Command6=ID_COLOR_RED
Command7=ID_ELEMENT_RECTANGLE
Command8=ID_ELEMENT_OVAL
Command9=ID_ELEMENT_TEXT
Command10=ID_ELEMENT_TEXT_IN_OVAL
Command11=ID_ELEMENT_RIBBLE
CommandCount=11

