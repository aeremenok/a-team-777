// **************************************************************************
//! \file   Data.cpp
//! \brief  ���������� ������ ��� ������� ���������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************


#include "stdafx.h"
#include "Data.h"

// ==========================================================================
namespace tdata //!< Namespace tdata
{
   Data::Data()
      :PetriNet<bool>()
   {

   }
   Data::~Data()
   {

   }

   //********************* ��������� iNetworkStruct **********************
   void Data::generate (int newNumberOfHoles, int newNumberOfCell)
   {

   }
   bool Data::makeStep (int numberOfHole)
   {
      return true;
   }
   void Data::clean ()
   {
   }

   //********************* ��������� iDrawable ***************************
   void Data::SetHdc(HDC hDC)
   {

   }
   void Data::GetHdc(HDC hDC)
   {

   }
   void Data::Redraw() const
   {

   }

   //********************* ��������� iSerializable ***********************
   bool Data::PutIntoFile(std::wstring& filename)
   {
      return true;
   }
   bool Data::GetFromFile(std::wstring& filename)
   {
      return true;
   }
}// end of namespace tdata
// ==========================================================================
