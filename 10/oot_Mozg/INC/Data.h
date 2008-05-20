// **************************************************************************
//! \file   Data.h
//! \brief  Объявление класса для игровой структуры
//! \author Bessonov A.V.
//! \date   17.May.2008 - 20.May.2008
//! \author Lysenko A.A.
//! \date   20.May.2008 - 21.May.2008
// **************************************************************************

#ifndef __DATA_H
#define __DATA_H

#include "petrinet.h"
#include "ifaces.h"

// ==========================================================================
namespace tdata //!< namespace Data
{
   class Data: private PetriNet<bool>,
               public iface::iDrawable,
               public iface::iSerializable,
               public iface::iNetworkStruct
   {
   public:
      Data();
      virtual ~Data() {};
      
      //********************* интерфейс iNetworkStruct **********************
      virtual void generate (int newIDofGame);
      virtual void generate (int newNumberOfHoles, int newNumberOfCell);
      virtual bool makeStep (int numberOfHole);	
      virtual void clean ();

      //********************* интерфейс iDrawable ***************************
      virtual void Redraw() const;
      virtual void Draw(CPaintDC& dc);

      //********************* интерфейс iSerializable ***********************
      virtual int PutIntoArchive(ser::Archive& archive);
      virtual void GetFromArchive(ser::Archive& archive, int id);

   private:
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~ РИСОВАНИЕ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      //! Залить фон
      void DrawBackground(CPaintDC& dc) const;
      //! Вычислить область для рисования
      RECT CalcDrawingRect(CPaintDC& dc) const;
      //! Рассчитать координаты позиций и переходов
      void CalcVertexes(const RECT& rect);
      //! Нарисовать позиции
      void DrawPositions(CPaintDC& dc) const;
      //! Нарисовать переходы
      void DrawTransitions(CPaintDC& dc) const;
      //! Нарисовать входные ребра переходов
      void DrawTransitionsInput(CPaintDC& dc) const;
      //! Нарисовать выходные ребра переходов
      void DrawTransitionsOutput(CPaintDC& dc) const;

      typedef std::vector<POINT> POINTS;
      POINTS m_posDots;                //!< Координаты позиций
      POINTS  m_trDots;                //!< Координаты переходов
      long   m_objSize;                //!< Размер объекта (диаметр / ширина)

   };

}// end of namespace tdata
// ==========================================================================
#endif // __DATA_H