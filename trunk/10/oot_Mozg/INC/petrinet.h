// **************************************************************************
//! \file   petrinet.h
//! \brief  ������ ������������ ����������, ������������ ���� �����
//! \author Lysenko A.A.
//! \date   14.May.2008 - 14.May.2008
// **************************************************************************

#ifndef __PETRINET_H
#define __PETRINET_H

#include "ifaces.h"
#include <vector>

// ==========================================================================
namespace tdata //!< Template Data
{
   // =======================================================================
   typedef __int32 POSITION;      //!< ������������� �������
   typedef __int32 TRANSITION;    //!< ������������� ��������
   
   enum { NONE = -1 };            //!< ������������ �������� �������

   // =======================================================================
   //! ��������� ��� ������� ������� � �������� ������� ���������
   struct IOFunc
   {
      TRANSITION Transition;     //!< �������
      POSITION   Position;       //!< ��������� � ��� �������
   };

   // =======================================================================
   // FORWARD DECLARATION
   template <typename MarkingType>
   class position_iterator;      //!< �������� ��� ������ �������
   template <typename MarkingType>
   class transition_iterator;    //!< �������� ��� ������ ���������
   template <typename MarkingType>
   class transition_input_it;    //!< �������� ��� ������ ������� ������� ��������
   template <typename MarkingType>
   class transition_output_it;   //!< �������� ��� ������ �������� ������� ��������

   // =======================================================================
   //! ���� �����
   //! MarkingType - ���������� ����������� �� ���-�� ����� � ����� �������
   template <typename MarkingType>
   class PetriNet
   {
   public:
      // --------------------------------------------------------------------
      typedef position_iterator<MarkingType> position_iterator;
      typedef transition_iterator<MarkingType> transition_iterator;
      typedef transition_input_it<MarkingType> transition_input_it;
      typedef transition_output_it<MarkingType> transition_output_it;

      friend class position_iterator;
      friend class transition_iterator;
      friend class transition_input_it;
      friend class transition_output_it;

      // --------------------------------------------------------------------
      PetriNet(TRANSITION maxFired = 100){}

      //! ������ ����� �������
      void SetPositionsNumber(POSITION number){}

      //! ������ ����� ���������
      void SetTransitionsNumber(TRANSITION number){}

      //! �������� ������� �� ������� ������� ��������
      //! \param tr - �������
      //! \param pos - ����������� �������
      void AddPositionToInput(TRANSITION tr, POSITION pos){}

      //! �������� ������� � �������� ������� ��������
      //! \param tr - �������
      //! \param pos - ����������� �������
      void AddPositionToOutput(TRANSITION tr, POSITION pos){}

      //! �������� ����������
      void SetMarking(const std::vector<MarkingType>& marking){}

      //! �������� ������� �����(��) � �������
      //! \return true - ���� ���������� ���������, false - �����
      bool SetToken(POSITION pos, MarkingType token){return 0}

      //! ��������� ��� ���������� ������������ ���������
      //! \param drawable - ��������� �� ������, ����������� ��������� ���������
      //! \return true - ���� ��� ���������� �������� ���������
      //! \return false - ���� ��������� ����������� ���������� ���-�� ���������
      bool FireAllTransitions(iface::iDrawable* drawable = NULL){return 0}

      // --------------------------------------------------------------------
      //! ���������, ����� �� �������� ����� � �������
      bool IsPositionAvailable(POSITION pos) const {return 0}

      //! ���������, ������� �� �������
      bool IsTransitionActive(TRANSITION tr) const {return 0}

      //! �������� ���������� �������
      POSITION GetPositionsNumber() const {return 0}

      //! �������� ���������� ���������
      TRANSITION GetTransitionsNumber() const {return 0}

      //! �������� ������� ����������
      const std::vector<MarkingType>& GetCurrentMarking() const {return 0}

      //! �������� ����������� ���������� ��� ������������ ����� ���������
      //! � ������ ������ ������� ����
      TRANSITION GetMaxFired() const {return 0} 

      // --------------------------------------------------------------------
      //! �������� �������� ��� ������ �������
      position_iterator GetPositions() const {return 0}
      //! �������� �������� ��� ������ ���������
      transition_iterator GetTransitions() const {return 0}
      //! �������� �������� ��� ������ ������� ������� ��������
      transition_input_it GetTransitionInput(TRANSITION tr) const {return 0}
      //! �������� �������� ��� ������ �������� ������� ��������
      transition_output_it GetTransitionOutput(TRANSITION tr) const {return 0}

   private:
      //! ��������� ������������ ��������� ��������
      void FireTransition(TRANSITION tr);

   protected:
      std::vector<POSITION>    m_positions;  //!< �������
      std::vector<TRANSITION>  m_transitions;//!< ��������
      std::vector<IOFunc>      m_Input;      //!< ��������� ������� �������
      std::vector<IOFunc>      m_Output;     //!< ��������� �������� �������
      std::vector<MarkingType> m_Marking;    //!< ������� ���������� ����
      //! ����������� ���������� ���-�� ��������� ��� ������������ � ������ ������ �������
      TRANSITION               m_maxFired;
   };

   // =======================================================================
   //! �������� ��� ������ �������
   template <typename MarkingType>
   class position_iterator
   {
   public:
      position_iterator();
      position_iterator(const position_iterator& rv);
      position_iterator& operator=(const position_iterator& rv);

      bool end() const;
      position_iterator& operator++();

      POSITION position() const;
      
      friend class PetriNet<MarkingType>;
   private:
      position_iterator(const PetriNet<MarkingType>* parts, POSITION id);
      const PetriNet<MarkingType>* m_net;
      POSITION m_pos;
   };


   // =======================================================================
   //! �������� ��� ������ ���������
   template <typename MarkingType>
   class transition_iterator
   {
   public:
      transition_iterator();
      transition_iterator(const transition_iterator& rv);
      transition_iterator& operator=(const transition_iterator& rv);

      bool end() const;
      transition_iterator& operator++();

      TRANSITION transition() const;

      friend class PetriNet<MarkingType>;
   private:
      transition_iterator(const PetriNet<MarkingType>* parts, TRANSITION id);
      const PetriNet<MarkingType>* m_net;
      TRANSITION m_tr;
   };

   // =======================================================================
   //! �������� ��� ������ ������� ������� ��������
   template <typename MarkingType>
   class transition_input_it
   {
   public:
      transition_input_it();
      transition_input_it(const transition_input_it& rv);
      transition_input_it& operator=(const transition_input_it& rv);

      bool end() const;
      transition_input_it& operator++();

      POSITION position() const;

      friend class PetriNet<MarkingType>;
   private:
      transition_input_it(const PetriNet<MarkingType>* parts, TRANSITION tr);
      const PetriNet<MarkingType>* m_net;
      TRANSITION m_tr;
      POSITION m_pos;
   };

   // =======================================================================
   //! �������� ��� ������ �������� ������� ��������
   template <typename MarkingType>
   class transition_output_it
   {
   public:
      transition_output_it();
      transition_output_it(const transition_output_it& rv);
      transition_output_it& operator=(const transition_output_it& rv);

      bool end() const;
      transition_output_it& operator++();

      POSITION position() const;

      friend class PetriNet<MarkingType>;
   private:
      transition_output_it(const PetriNet<MarkingType>* parts, TRANSITION tr);
      const PetriNet<MarkingType>* m_net;
      TRANSITION m_tr;
      POSITION m_pos;
   };

} // end of namespace tdata

// ==========================================================================
#endif // __PETRINET_H