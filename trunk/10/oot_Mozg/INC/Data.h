// **************************************************************************
//! \file   Data.h
//! \brief  ���������� ������ ��� ������� ���������
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
      
      //********************* ��������� iNetworkStruct **********************
      virtual void generate (int newIDofGame);
      virtual void generate (int newNumberOfHoles, int newNumberOfCell);
      virtual bool makeStep (int numberOfHole);	
      virtual void clean ();

      //********************* ��������� iDrawable ***************************
      virtual void Redraw() const;
      virtual void Draw(CPaintDC& dc);

      //********************* ��������� iSerializable ***********************
      virtual int PutIntoArchive(ser::Archive& archive);
      virtual void GetFromArchive(ser::Archive& archive, int id);

   private:
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~ ��������� ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      //! ������ ���
      void DrawBackground(CPaintDC& dc) const;
      //! ��������� ������� ��� ���������
      RECT CalcDrawingRect(CPaintDC& dc) const;
      //! ���������� ���������� ������� � ���������
      void CalcVertexes(const RECT& rect);
      //! ���������� �������
      void DrawPositions(CPaintDC& dc) const;
      //! ���������� ��������
      void DrawTransitions(CPaintDC& dc) const;
      //! ���������� ������� ����� ���������
      void DrawTransitionsInput(CPaintDC& dc) const;
      //! ���������� �������� ����� ���������
      void DrawTransitionsOutput(CPaintDC& dc) const;

      typedef std::vector<POINT> POINTS;
      POINTS m_posDots;                //!< ���������� �������
      POINTS  m_trDots;                //!< ���������� ���������
      long   m_objSize;                //!< ������ ������� (������� / ������)

   };

}// end of namespace tdata
// ==========================================================================
#endif // __DATA_H