# Microsoft Developer Studio Project File - Name="Sketcher" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Application" 0x0101

CFG=Sketcher - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "Sketcher.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "Sketcher.mak" CFG="Sketcher - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "Sketcher - Win32 Release" (based on "Win32 (x86) Application")
!MESSAGE "Sketcher - Win32 Debug" (based on "Win32 (x86) Application")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Sketcher - Win32 Release"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /Yu"stdafx.h" /FD /c
# ADD CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /FD /c
# ADD BASE MTL /nologo /D "NDEBUG" /mktyplib203 /win32
# ADD MTL /nologo /D "NDEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0x419 /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0x419 /d "NDEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /machine:I386
# ADD LINK32 /nologo /subsystem:windows /machine:I386

!ELSEIF  "$(CFG)" == "Sketcher - Win32 Debug"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /Yu"stdafx.h" /FD /GZ /c
# ADD CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /FD /GZ /c
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0x419 /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0x419 /d "_DEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /debug /machine:I386 /pdbtype:sept
# ADD LINK32 /nologo /subsystem:windows /debug /machine:I386 /pdbtype:sept

!ENDIF 

# Begin Target

# Name "Sketcher - Win32 Release"
# Name "Sketcher - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\ChildFrm.cpp
# End Source File
# Begin Source File

SOURCE=.\CntrItem.cpp
# End Source File
# Begin Source File

SOURCE=.\Elements.cpp
# End Source File
# Begin Source File

SOURCE=.\exceptions\GraphException.cpp
# End Source File
# Begin Source File

SOURCE=.\IpFrame.cpp
# End Source File
# Begin Source File

SOURCE=.\MainFrm.cpp
# End Source File
# Begin Source File

SOURCE=.\shapes\Oval.cpp
# End Source File
# Begin Source File

SOURCE=.\shapes\Rectangle.cpp
# End Source File
# Begin Source File

SOURCE=.\exceptions\RibbleExistsException.cpp
# End Source File
# Begin Source File

SOURCE=.\exceptions\RibbleNotFoundException.cpp
# End Source File
# Begin Source File

SOURCE=.\ScaleDialog.cpp
# End Source File
# Begin Source File

SOURCE=.\shapes\Shape.cpp
# End Source File
# Begin Source File

SOURCE=.\ShapeContainer.cpp
# End Source File
# Begin Source File

SOURCE=.\ShapeHandler.cpp
# End Source File
# Begin Source File

SOURCE=.\Sketcher.cpp
# End Source File
# Begin Source File

SOURCE=.\Sketcher.odl
# End Source File
# Begin Source File

SOURCE=.\Sketcher.rc
# End Source File
# Begin Source File

SOURCE=.\SketcherDoc.cpp
# End Source File
# Begin Source File

SOURCE=.\SketcherView.cpp
# End Source File
# Begin Source File

SOURCE=.\SrvrItem.cpp
# End Source File
# Begin Source File

SOURCE=.\StdAfx.cpp
# ADD CPP /Yc"stdafx.h"
# End Source File
# Begin Source File

SOURCE=.\shapes\Text.cpp
# End Source File
# Begin Source File

SOURCE=.\shapes\TextInOval.cpp
# End Source File
# Begin Source File

SOURCE=.\TextRequest.cpp
# End Source File
# Begin Source File

SOURCE=.\exceptions\VertexNotFoundException.cpp
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\ChildFrm.h
# End Source File
# Begin Source File

SOURCE=.\CntrItem.h
# End Source File
# Begin Source File

SOURCE=.\Elements.h
# End Source File
# Begin Source File

SOURCE=.\container\ExternalGraphIterator.h
# End Source File
# Begin Source File

SOURCE=.\container\Graph.h
# End Source File
# Begin Source File

SOURCE=.\exceptions\GraphException.h
# End Source File
# Begin Source File

SOURCE=.\IpFrame.h
# End Source File
# Begin Source File

SOURCE=.\container\Iterator.h
# End Source File
# Begin Source File

SOURCE=.\MainFrm.h
# End Source File
# Begin Source File

SOURCE=.\OurConstants.h
# End Source File
# Begin Source File

SOURCE=.\shapes\Oval.h
# End Source File
# Begin Source File

SOURCE=.\shapes\Rectangle.h
# End Source File
# Begin Source File

SOURCE=.\Resource.h
# End Source File
# Begin Source File

SOURCE=.\container\Ribble.h
# End Source File
# Begin Source File

SOURCE=.\exceptions\RibbleExistsException.h
# End Source File
# Begin Source File

SOURCE=.\exceptions\RibbleNotFoundException.h
# End Source File
# Begin Source File

SOURCE=.\ScaleDialog.h
# End Source File
# Begin Source File

SOURCE=.\shapes\Shape.h
# End Source File
# Begin Source File

SOURCE=.\ShapeContainer.h
# End Source File
# Begin Source File

SOURCE=.\ShapeHandler.h
# End Source File
# Begin Source File

SOURCE=.\Sketcher.h
# End Source File
# Begin Source File

SOURCE=.\SketcherDoc.h
# End Source File
# Begin Source File

SOURCE=.\SketcherView.h
# End Source File
# Begin Source File

SOURCE=.\SrvrItem.h
# End Source File
# Begin Source File

SOURCE=.\StdAfx.h
# End Source File
# Begin Source File

SOURCE=.\shapes\Text.h
# End Source File
# Begin Source File

SOURCE=.\shapes\TextInOval.h
# End Source File
# Begin Source File

SOURCE=.\TextRequest.h
# End Source File
# Begin Source File

SOURCE=.\exceptions\VertexNotFoundException.h
# End Source File
# End Group
# Begin Group "Resource Files"

# PROP Default_Filter "ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe"
# Begin Source File

SOURCE=.\res\Sketcher.ico
# End Source File
# Begin Source File

SOURCE=.\res\Sketcher.rc2
# End Source File
# Begin Source File

SOURCE=.\res\SketcherDoc.ico
# End Source File
# End Group
# Begin Source File

SOURCE=.\ReadMe.txt
# End Source File
# Begin Source File

SOURCE=.\Sketcher.reg
# End Source File
# End Target
# End Project
