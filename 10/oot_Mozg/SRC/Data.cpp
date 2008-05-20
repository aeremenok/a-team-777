// **************************************************************************
//! \file   Data.cpp
//! \brief  Реализация класса для игровой структуры
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

   //********************* интерфейс iNetworkStruct **********************
   void Data::generate (int newIDofGame)
   {

   }
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

   //********************* интерфейс iDrawable ***************************
   void Data::SetHdc(HDC hDC)
   {

   }
   void Data::GetHdc(HDC hDC)
   {

   }
   void Data::Redraw() const
   {

   }

   //********************* интерфейс iSerializable ***********************
   bool Data::PutIntoFile(std::string& filename)
   {
      return true;
   }
   bool Data::GetFromFile(std::string& filename)
   {
      return true;
   }
}// end of namespace tdata
// ==========================================================================
