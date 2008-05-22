// **************************************************************************
//! \file   Data.cpp
//! \brief  Реализация класса для игровой структуры
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
//! \author Lysenko A.A.
//! \date   20.May.2008 - 21.May.2008
// **************************************************************************

#include "stdafx.h"
#include "Data.h"

using namespace tdata;

// ==========================================================================
#define CLR_POSITION            RGB(68, 230, 60)   //!< Цвет позиции
#define CLR_TRANSITION_ACTIVE   RGB(254, 38, 33)   //!< Цвет активного перехода
#define CLR_TRANSITION_INACTIVE RGB(119, 196, 215) //!< Цвет неактивного перехода
#define CLR_INPUT               RGB(28, 22, 248)   //!< Цвет ребер входной функции
#define CLR_OUTPUT              RGB(12, 236, 6)    //!< Цвет ребер выходной функции
#define CLR_TOKEN               RGB(0, 0, 0)       //!< Цвет фишки
#define CLR_PEN                 RGB(0, 0, 0)       //!< Цвет пера

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
   bool maxReached = FireAllTransitions(this);

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
void Data::Redraw() const
{
   Game::Controller::Instance()->Redraw();
}

void Data::Draw(CPaintDC& dc)
{
   // НАРИСОВАТЬ ФОН
   DrawBackground(dc);

   if ( GetPositionsNumber() <= 0 || GetTransitionsNumber() <= 0 )
      return;

   // ВЫЧИСЛИТЬ ПРЯМОУГОЛЬНИК РИСОВАНИЯ
   RECT rect = CalcDrawingRect(dc);

   // РАССЧИТАТЬ КООРДИНАТЫ ПОЗИЦИЙ И ПЕРЕХОДОВ
   CalcVertexes(rect);

   // ОТОБРАЗИТЬ РЕБРА
   DrawTransitionsInput(dc);
   DrawTransitionsOutput(dc);

   // ОТОБРАЗИТЬ ПОЗИЦИИ
   DrawPositions(dc);

   // ОТОБРАЗИТЬ ПЕРЕХОДЫ
   DrawTransitions(dc);
}

//********************* интерфейс iSerializable ***********************
int Data::PutIntoArchive(ser::Archive& archive)
{
   ser::writer wr(archive);

   wr << GetPositionsNumber() << GetTransitionsNumber();

   for ( TRANSITION t = 0; t < GetTransitionsNumber(); t++ )
   {
      POSITION szi = m_Input[t].size();
      wr << szi;
      for ( POSITION pi = 0; pi < szi; pi++ )
         wr << m_Input[t][pi];

      POSITION szo = m_Output[t].size();
      wr << szo;
      for ( POSITION po = 0; po < szo; po++ )
         wr << m_Output[t][po];
   }

   for ( POSITION p = 0; p < GetPositionsNumber(); p++ )
   {
      bool val = m_Marking[p];
      wr << val;
   }

   return wr.id();
}

void Data::GetFromArchive(ser::Archive& archive, int id)
{
   ser::reader rd(archive, id);

   POSITION poss = 0;
   TRANSITION trs = 0;
   
   rd >> poss >> trs;

   SetPositionsNumber(poss);
   SetTransitionsNumber(trs);

   for ( TRANSITION t = 0; t < trs; t++ )
   {
      POSITION szi = 0;
      rd >> szi;
      m_Input[t].resize(szi, NONE);
      for ( POSITION pi = 0; pi < szi; pi++ )
         rd >> m_Input[t][pi];

      POSITION szo = 0;
      rd >> szo;
      m_Output[t].resize(szo, NONE);
      for ( POSITION po = 0; po < szo; po++ )
         rd >> m_Output[t][po];
   }

   for ( POSITION p = 0; p < poss; p++ )
   {
      bool val = false;
      rd >> val;
      m_Marking[p] = val;
   }
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
   // ПОЛУЧИТЬ КЛИЕНТСКУЮ ОБЛАСТЬ ОКНА
   RECT clirect;
   ::GetClientRect(dc.m_hWnd, &clirect);

   // ВВЕСТИ НЕБОЛЬШОЙ ОТСТУП
   clirect.top += 8;
   clirect.bottom -= 8;
   clirect.left += 8;
   clirect.right -= 8;

   return clirect;
}

// ==========================================================================
void Data::CalcVertexes(const RECT& rect)
{
   // ЧИСЛО "СТРОК" ОБЪЕКТОВ
   POINT zero = {0, 0};
   int rows = 0;
   POSITION posNum = GetPositionsNumber();
   TRANSITION trNum  = GetTransitionsNumber();
   m_posDots.resize(posNum, zero);
   m_trDots.resize(trNum, zero);
   
   int i = posNum,   // ОСТАВШИЕСЯ ПОЗИЦИИ
       j = 6,        // КОЛИЧЕСТВО ПОЗИЦИЙ В ТЕКУЩЕЙ СТРОКЕ
       sign = -1;    // НАПРАВЛЕНИЕ ДВИЖЕНИЯ ПО СТРУКТУРЕ СЕТИ
   /* Направление - ориентация вершины тек. треугольника - вверх(+) или вниз(-)
      Работаем со следующей структурой сети:

      * * * * * *
       - - - - -
        * * * *
         - - -                   *  -- позиция
          * *                    -  -- переход
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
      // ЕСЛИ ДОШЛИ ДО ГРАНИЦЫ, ПОМЕНЯТЬ НАПРАВЛЕНИЕ
      if ( j == 0 || j > 6 )
      {
         sign *= -1;
         if ( j == 0 )
            j = 2;
         else
            j = 4;
      }
      
      i -= j;
      rows += 2;     // СРАЗУ УЧИТЫВАЕМ ПЕРЕХОДЫ      

      // ИЗМЕНИТЬ КОЛ-ВО ПОЗИЦИЙ В ОЧЕРЕДНОЙ СТРОКЕ      
      j = j + sign*2;
   }

   /* В rows - количество строк в структуре + 1 */

   int nRowsBetween = 1;      // ЧИСЛО "СТРОК" МЕЖДУ ОБЪЕКТАМИ
   int nRows = (rows - 2) * nRowsBetween + rows + 1;
   /*          строки между             объекты   верхняя граница */

   // РАЗМЕР ОБЪЕКТА - ВЫСОТА СТРОКИ
   m_objSize = (rect.bottom - rect.top) / nRows;

   // 7 СТОЛБЦОВ (2 КРАЙНИХ И 5 МЕЖДУ ОБЪЕКТАМИ)
   int nColumnWidth = (rect.right - rect.left) / 7;

   // КООРДИНАТЫ ПОЗИЦИЙ
   POINT posStart = {rect.left + nColumnWidth, rect.top + m_objSize + m_objSize / 2};
   int curRow = 0;
   i = posNum;
   j = 6;
   sign = -1;
   while ( i > 0 )
   {
      // ЕСЛИ ДОШЛИ ДО ГРАНИЦЫ, ПОМЕНЯТЬ НАПРАВЛЕНИЕ
      if ( j == 0 || j > 6 )
      {
         sign *= -1;
         if ( j == 0 )
            j = 2;
         else
            j = 4;
      }
      
      POSITION firstInRow = posNum - i;
      m_posDots[firstInRow].y = posStart.y + m_objSize * (curRow*(nRowsBetween + 1));
      m_posDots[firstInRow].x = posStart.x + nColumnWidth * ((6 - j) / 2);

      for (int k = 1; (k < j) && (firstInRow + k < posNum); ++k )
      {
         m_posDots[firstInRow + k].y = m_posDots[firstInRow].y;
         m_posDots[firstInRow + k].x = m_posDots[firstInRow].x + nColumnWidth * k;
      }

      // ВЫЧИСЛИТЬ КОЛ-ВО ПОЗИЦИЙ В ОЧЕРЕДНОЙ СТРОКЕ И ПЕРЕЙТИ ДАЛЬШЕ
      i -= j;
      curRow += 2;
      j = j + sign*2;
   }

   // КООРДИНАТЫ ПЕРЕХОДОВ
   POINT trStart = {posStart.x + nColumnWidth / 2, posStart.y + m_objSize*(nRowsBetween + 1)};
   curRow = 0; // ТЕКУЩИЙ РЯД ПЕРЕХОДОВ, А НЕ ВОБЩЕМ
   i = trNum;
   j = 5;
   sign = -1;
   while ( i > 0 )
   {
      // ЕСЛИ ДОШЛИ ДО ГРАНИЦЫ, ПОМЕНЯТЬ НАПРАВЛЕНИЕ
      if ( j < 0 || j > 5 )
      {
         sign *= -1;
         j = 3;
      }

      TRANSITION firstInRow = trNum - i;
      m_trDots[firstInRow].y = trStart.y + m_objSize * (curRow*(nRowsBetween + 1));
      m_trDots[firstInRow].x = trStart.x + nColumnWidth * ((5 - j) / 2);

      for (int k = 1; (k < j) && (firstInRow + k < trNum); ++k )
      {
         m_trDots[firstInRow + k].y = m_trDots[firstInRow].y;
         m_trDots[firstInRow + k].x = m_trDots[firstInRow].x + nColumnWidth * k;
      }

      // ВЫЧИСЛИТЬ КОЛ-ВО ПОЗИЦИЙ В ОЧЕРЕДНОЙ СТРОКЕ И ПЕРЕЙТИ ДАЛЬШЕ
      i -= j;
      curRow += 2;
      j = j + sign*2;
   }
}

// ==========================================================================
void Data::DrawPositions(CPaintDC& dc) const
{
   CPen pen;
   pen.CreatePen(PS_SOLID, 0, CLR_PEN);
   CBrush brushPos, brushTkn;
   brushPos.CreateSolidBrush(CLR_POSITION);
   brushTkn.CreateSolidBrush(CLR_TOKEN);

   HPEN oldPen = dc.SelectPen(pen);
   HBRUSH oldBrush = dc.SelectBrush(brushPos);

   for ( iposition it = GetPositions(); !it.end(); ++it )
   {
      RECT posRect;
      posRect.left = m_posDots[it.position()].x - m_objSize / 2;
      posRect.right = m_posDots[it.position()].x + m_objSize / 2;
      posRect.top = m_posDots[it.position()].y - m_objSize / 2;
      posRect.bottom = m_posDots[it.position()].y + m_objSize / 2;

      RECT tknRect;
      tknRect.left = m_posDots[it.position()].x - m_objSize / 4;
      tknRect.right = m_posDots[it.position()].x + m_objSize / 4;
      tknRect.top = m_posDots[it.position()].y - m_objSize / 4;
      tknRect.bottom = m_posDots[it.position()].y + m_objSize / 4;

      dc.Ellipse(&posRect);
      if ( !IsPositionAvailable(it.position()) )
      {
         dc.SelectBrush(brushTkn);
         dc.Ellipse(&tknRect);
         dc.SelectBrush(brushPos);
      }
   }

   dc.SelectPen(oldPen);
   dc.SelectBrush(oldBrush);
}

// ==========================================================================
void Data::DrawTransitions(CPaintDC& dc) const
{
   CPen pen;
   pen.CreatePen(PS_SOLID, 0, CLR_PEN);
   CBrush brushAct, brushTr;
   brushAct.CreateSolidBrush(CLR_TRANSITION_ACTIVE);
   brushTr.CreateSolidBrush(CLR_TRANSITION_INACTIVE);

   HPEN oldPen = dc.SelectPen(pen);
   HBRUSH oldBrush = dc.SelectBrush(brushTr);

   for ( itransition it = GetTransitions(); !it.end(); ++it )
   {
      RECT trRect;
      trRect.left = m_trDots[it.transition()].x - m_objSize / 2;
      trRect.right = m_trDots[it.transition()].x + m_objSize / 2;
      trRect.top = m_trDots[it.transition()].y - m_objSize / 4;
      trRect.bottom = m_trDots[it.transition()].y + m_objSize / 4;

      if ( IsTransitionActive(it.transition()) )
         dc.SelectBrush(brushAct);
      else
         dc.SelectBrush(brushTr);

      dc.Rectangle(&trRect);
   }

   dc.SelectPen(oldPen);
   dc.SelectBrush(oldBrush);
}

// ==========================================================================
void Data::DrawTransitionsInput(CPaintDC& dc) const
{
   CPen inputPen;
   inputPen.CreatePen(PS_SOLID, 2, CLR_INPUT);
   HPEN oldPen = dc.SelectPen(inputPen);

   for ( itransition it = GetTransitions(); !it.end(); ++it )
      for ( itransition_input inp = GetTransitionInput(it.transition()); !inp.end(); ++inp )
      {
         dc.MoveTo(m_trDots[it.transition()]);
         dc.LineTo(m_posDots[inp.position()]);
      }

   dc.SelectPen(oldPen);
}

// ==========================================================================
void Data::DrawTransitionsOutput(CPaintDC& dc) const
{
   CPen outputPen;
   outputPen.CreatePen(PS_SOLID, 2, CLR_OUTPUT);
   HPEN oldPen = dc.SelectPen(outputPen);

   for ( itransition it = GetTransitions(); !it.end(); ++it )
      for ( itransition_output outp = GetTransitionOutput(it.transition()); !outp.end(); ++outp )
      {
         dc.MoveTo(m_trDots[it.transition()]);
         dc.LineTo(m_posDots[outp.position()]);
      }

      dc.SelectPen(oldPen);
}

// ==========================================================================
