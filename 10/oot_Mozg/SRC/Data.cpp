// **************************************************************************
//! \file   Data.cpp
//! \brief  ���������� ������ ��� ������� ���������
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
   // ������� ���������
   case 0:
      // 13 �������
      SetPositionsNumber(13);
      // 9 ���������
      SetTransitionsNumber(9);

      // ������ ������� �������
      // 1-� �������
      AddPositionToInput(0, 0);
      AddPositionToInput(0, 1);
      AddPositionToInput(0, 2);
      // 2-� �������
      AddPositionToInput(1, 2);
      // 3-� �������
      AddPositionToInput(2, 2);
      AddPositionToInput(2, 3);
      // 4-� �������
      AddPositionToInput(3, 3);
      AddPositionToInput(3, 4);
      // 5-� �������
      AddPositionToInput(4, 4);
      AddPositionToInput(4, 5);
      // 6-� �������
      AddPositionToInput(5, 6);
      AddPositionToInput(5, 7);
      // 7-� �������
      AddPositionToInput(6, 8);
      // 8-� �������
      AddPositionToInput(7, 8);
      AddPositionToInput(7, 9);
      // 9-� �������
      AddPositionToInput(8, 10);
      AddPositionToInput(8, 11);

      // ������ �������� �������
      // 1-� �������
      AddPositionToOutput(0, 6);
      // 2-� �������
      AddPositionToOutput(1, 6);
      AddPositionToOutput(1, 7);
      // 3-� �������
      AddPositionToOutput(2, 8);
      // 4-� �������
      AddPositionToOutput(3, 8);
      // 5-� �������
      AddPositionToOutput(4, 9);
      // 6-� �������
      AddPositionToOutput(5, 10);
      // 7-� �������
      AddPositionToOutput(6, 10);
      // 8-� �������
      AddPositionToOutput(7, 11);
      // 9-� �������
      AddPositionToOutput(8, 12);

      break;
   // ������� ���������
   case 1:
      // 18 �������
      SetPositionsNumber(18);
      // 12 ���������
      SetTransitionsNumber(12);

      // ������ ������� �������
      // 1-� �������
      AddPositionToInput(0, 0);
      AddPositionToInput(0, 1);
      // 2-� �������
      AddPositionToInput(1, 1);
      // 3-� �������
      AddPositionToInput(2, 1);
      AddPositionToInput(2, 2);
      AddPositionToInput(2, 3);
      AddPositionToInput(2, 4);
      // 4-� �������
      AddPositionToInput(3, 4);
      // 5-� �������
      AddPositionToInput(4, 4);
      AddPositionToInput(4, 5);
      // 6-� �������
      AddPositionToInput(5, 6);
      AddPositionToInput(5, 7);
      // 7-� �������
      AddPositionToInput(6, 8);
      // 8-� �������
      AddPositionToInput(7, 9);
      // 9-� �������
      AddPositionToInput(8, 10);
      AddPositionToInput(8, 11);
      // 10-� �������
      AddPositionToInput(9, 12);
      // 11-� �������
      AddPositionToInput(10, 12);
      AddPositionToInput(10, 13);
      // 12-� �������
      AddPositionToInput(11, 13);

      // ������ �������� �������
      // 1-� �������
      AddPositionToOutput(0, 6);
      // 2-� �������
      AddPositionToOutput(1, 6);
      // 3-� �������
      AddPositionToOutput(2, 7);
      AddPositionToOutput(2, 8);
      // 4-� �������
      AddPositionToOutput(3, 9);
      // 5-� �������
      AddPositionToOutput(4, 9);
      // 6-� �������
      AddPositionToOutput(5, 10);
      // 7-� �������
      AddPositionToOutput(6, 10);
      AddPositionToOutput(6, 11);
      // 8-� �������
      AddPositionToOutput(7, 11);
      // 9-� �������
      AddPositionToOutput(8, 12);
      AddPositionToOutput(8, 13);
      // 10-� �������
      AddPositionToOutput(9, 14);
      // 11-� �������
      AddPositionToOutput(10, 15);
      // 12-� �������
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
   // ��������������, ��� ������� ���������
   if ( !IsPositionAvailable(numberOfHole) )
      // ������ ����� � ������������ ������� - ������ ������
      /* �.�. ����� ������ ������� ���� ���, �� ��������� ������ ���������
         ����� �������� � �� ��������� �� */
      return true;

   SetToken(numberOfHole, true);
   bool maxReached = FireAllTransitions();

   // TODO: ������� �� ������������ ����. ����� ���������

   // ��������� ����������� ���������� ����
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
int Data::PutIntoArchive(ser::Archive& archive)
{
   return 0;
}

void Data::GetFromArchive(ser::Archive& archive, int id)
{
   return;
}
// ==========================================================================
