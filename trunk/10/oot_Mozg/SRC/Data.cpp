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
   bool maxReached = FireAllTransitions(this);

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
void Data::Redraw() const
{
   Game::Controller::Instance()->Redraw();
}

void Data::Draw(CPaintDC& dc)
{
   // ���������� ���
   DrawBackground(dc);

   if ( GetPositionsNumber() <= 0 || GetTransitionsNumber() <= 0 )
      return;

   // ��������� ������������� ���������
   RECT rect = CalcDrawingRect(dc);

   // ���������� ���������� ������� � ���������
   CalcVertexes(rect);

   // ���������� �������
   DrawPositions(dc);

   // ���������� ��������
   DrawTransitions(dc);

   // ���������� �����
   DrawTransitionsInput(dc);
   DrawTransitionsOutput(dc);
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
void Data::DrawBackground(CPaintDC& dc) const
{
   CBrush oldbrush = dc.GetCurrentBrush();
   dc.SelectStockBrush(WHITE_BRUSH);
   RECT clirect;
   ::GetClientRect(dc.m_hWnd, &clirect);
   dc.Rectangle(&clirect);
   dc.SelectBrush(oldbrush);
}

// ==========================================================================
RECT Data::CalcDrawingRect(CPaintDC& dc) const
{
   // �������� ���������� ������� ����
   RECT clirect;
   ::GetClientRect(dc.m_hWnd, &clirect);

   // ������ ��������� ������
   clirect.top += 8;
   clirect.bottom -= 8;
   clirect.left += 8;
   clirect.right -= 8;

   return clirect;
}

// ==========================================================================
void Data::CalcVertexes(const RECT& rect)
{
   // ����� "�����" ��������
   int rows = 0;
   int posNum = GetPositionsNumber(),
       trNum  = GetTransitionsNumber();
   
   int i = posNum,   // ���������� �������
       j = 6,        // ���������� ������� � ������� ������
       sign = -1;    // ����������� �������� �� ��������� ����
   /* ����������� - ���������� ������� ���. ������������ - �����(+) ��� ����(-)
      �������� �� ��������� ���������� ����:

      * * * * * *
       - - - - -
        * * * *
         - - -                   *  -- �������
          * *                    -  -- �������
           -
          * *
         - - -
        * * * *
       - - - - -
      * * * * * *
       - - - - -
        * * * *
         - - -
          * *
           -
          * *
         - - -
        * * * *
       - - - - -
      * * * * * *

   */
   while ( i > 0 )
   {
      // ���� ����� �� �������, �������� �����������
      if ( j == 0 || j > 6 )
      {
         sign *= -1;
         if ( j == 0 )
            j = 2;
         else
            j = 4;
      }
      
      i -= j;
      rows += 2;     // ����� ��������� ��������      

      // �������� ���-�� ������� � ��������� ������      
      j = j + sign*2;
   }

   /* � rows - ���������� ����� � ��������� + 1 */
}

// ==========================================================================
void Data::DrawPositions(CPaintDC& dc) const
{
}

// ==========================================================================
void Data::DrawTransitions(CPaintDC& dc) const
{
}

// ==========================================================================
void Data::DrawTransitionsInput(CPaintDC& dc) const
{
}

// ==========================================================================
void Data::DrawTransitionsOutput(CPaintDC& dc) const
{
}

// ==========================================================================
