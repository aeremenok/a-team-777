// **************************************************************************
//! \file   Data.cpp
//! \brief  Реализация класса для игровой структуры
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
//! \author Lysenko A.A.
//! \date   20.May.2008 - 20.May.2008
// **************************************************************************

#include "stdafx.h"
#include "Data.h"

using namespace tdata;

// ==========================================================================
Data::Data() {}

// ==========================================================================
void Data::generate (int newIDofGame)
{
   switch (newIDofGame)
   {
   // ПРОСТАЯ СТРУКТУРА
   case 0:
      // 13 позиций
      SetPositionsNumber(13);
      // 9 переходов
      SetTransitionsNumber(9);

      // ЗАДАТЬ ВХОДНУЮ ФУНКЦИЮ
      // 1-й переход
      AddPositionToInput(0, 0);
      AddPositionToInput(0, 1);
      AddPositionToInput(0, 2);
      // 2-й переход
      AddPositionToInput(1, 2);
      // 3-й переход
      AddPositionToInput(2, 2);
      AddPositionToInput(2, 3);
      // 4-й переход
      AddPositionToInput(3, 3);
      AddPositionToInput(3, 4);
      // 5-й переход
      AddPositionToInput(4, 4);
      AddPositionToInput(4, 5);
      // 6-й переход
      AddPositionToInput(5, 6);
      AddPositionToInput(5, 7);
      // 7-й переход
      AddPositionToInput(6, 8);
      // 8-й переход
      AddPositionToInput(7, 8);
      AddPositionToInput(7, 9);
      // 9-й переход
      AddPositionToInput(8, 10);
      AddPositionToInput(8, 11);

      // ЗАДАТЬ ВЫХОДНУЮ ФУНКЦИЮ
      // 1-й переход
      AddPositionToOutput(0, 6);
      // 2-й переход
      AddPositionToOutput(1, 6);
      AddPositionToOutput(1, 7);
      // 3-й переход
      AddPositionToOutput(2, 8);
      // 4-й переход
      AddPositionToOutput(3, 8);
      // 5-й переход
      AddPositionToOutput(4, 9);
      // 6-й переход
      AddPositionToOutput(5, 10);
      // 7-й переход
      AddPositionToOutput(6, 10);
      // 8-й переход
      AddPositionToOutput(7, 11);
      // 9-й переход
      AddPositionToOutput(8, 12);

      break;
   // СЛОЖНАЯ СТРУКТУРА
   case 1:
      // 18 позиций
      SetPositionsNumber(18);
      // 12 переходов
      SetTransitionsNumber(12);

      // ЗАДАТЬ ВХОДНУЮ ФУНКЦИЮ
      // 1-й переход
      AddPositionToInput(0, 0);
      AddPositionToInput(0, 1);
      // 2-й переход
      AddPositionToInput(1, 1);
      // 3-й переход
      AddPositionToInput(2, 1);
      AddPositionToInput(2, 2);
      AddPositionToInput(2, 3);
      AddPositionToInput(2, 4);
      // 4-й переход
      AddPositionToInput(3, 4);
      // 5-й переход
      AddPositionToInput(4, 4);
      AddPositionToInput(4, 5);
      // 6-й переход
      AddPositionToInput(5, 6);
      AddPositionToInput(5, 7);
      // 7-й переход
      AddPositionToInput(6, 8);
      // 8-й переход
      AddPositionToInput(7, 9);
      // 9-й переход
      AddPositionToInput(8, 10);
      AddPositionToInput(8, 11);
      // 10-й переход
      AddPositionToInput(9, 12);
      // 11-й переход
      AddPositionToInput(10, 12);
      AddPositionToInput(10, 13);
      // 12-й переход
      AddPositionToInput(11, 13);

      // ЗАДАТЬ ВЫХОДНУЮ ФУНКЦИЮ
      // 1-й переход
      AddPositionToOutput(0, 6);
      // 2-й переход
      AddPositionToOutput(1, 6);
      // 3-й переход
      AddPositionToOutput(2, 7);
      AddPositionToOutput(2, 8);
      // 4-й переход
      AddPositionToOutput(3, 9);
      // 5-й переход
      AddPositionToOutput(4, 9);
      // 6-й переход
      AddPositionToOutput(5, 10);
      // 7-й переход
      AddPositionToOutput(6, 10);
      AddPositionToOutput(6, 11);
      // 8-й переход
      AddPositionToOutput(7, 11);
      // 9-й переход
      AddPositionToOutput(8, 12);
      AddPositionToOutput(8, 13);
      // 10-й переход
      AddPositionToOutput(9, 14);
      // 11-й переход
      AddPositionToOutput(10, 15);
      // 12-й переход
      AddPositionToOutput(11, 16);
      AddPositionToOutput(11, 17);
   	break;
   }
}

// ==========================================================================
void Data::generate (int newNumberOfHoles, int newNumberOfCell)
{
   SetPositionsNumber(newNumberOfHoles);
   SetTransitionsNumber(newNumberOfCell);
}

// ==========================================================================
bool Data::makeStep (int numberOfHole)
{
   // УДОСТОВЕРИТЬСЯ, ЧТО ПОЗИЦИЯ СВОБОДНАЯ
   if ( !IsPositionAvailable(numberOfHole) )
      // КИНУЛИ ФИШКУ В НЕДОПУСТИМУЮ ПОЗИЦИЮ - ИГРАЕМ ДАЛЬШЕ
      /* Т.е. игрок просто потерял свой ход, но интерфейс должен проверять
         такие ситуации и не допускать их */
      return true;

   SetToken(numberOfHole, true);
   bool maxReached = FireAllTransitions();

   // TODO: Реакция на срабатывание макс. числа переходов

   // ПРОВЕРИТЬ ВОЗМОЖНОСТЬ СЛЕДУЮЩЕГО ХОДА
   bool ret = false;
   for ( iposition ip = GetPositions(); !ip.end() && ip.position() < 6; ++ip )
      ret = ret || IsPositionAvailable(ip.position());

   return ret;
}

// ==========================================================================
void Data::clean ()
{
   PetriNet<bool>::Clear();
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
int Data::PutIntoArchive(ser::Archive& archive)
{
   return 0;
}

void Data::GetFromArchive(ser::Archive& archive, int id)
{
   return;
}
// ==========================================================================
