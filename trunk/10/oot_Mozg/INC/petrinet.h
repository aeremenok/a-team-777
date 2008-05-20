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
#include <algorithm>

// ==========================================================================
namespace tdata //!< Template Data
{
   // =======================================================================
   typedef __int32 POSITION;      //!< ������������� �������
   typedef __int32 TRANSITION;    //!< ������������� ��������
   
   enum { NONE = -1 };            //!< ������������ �������� �������

   // =======================================================================
   //! ������� � �������� ������� ���������
   typedef std::vector<POSITION> IOFunc;

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
      typedef position_iterator<MarkingType> iposition;
      typedef transition_iterator<MarkingType> itransition;
      typedef transition_input_it<MarkingType> itransition_input;
      typedef transition_output_it<MarkingType> itransition_output;

      friend class iposition;
      friend class itransition;
      friend class itransition_input;
      friend class itransition_output;

      // --------------------------------------------------------------------
      PetriNet(TRANSITION maxFired = 100);

      //! �������� ����������
      void Clear();

      //! ������ ����� �������
      void SetPositionsNumber(POSITION number);

      //! ������ ����� ���������
      void SetTransitionsNumber(TRANSITION number);

      //! �������� ������� �� ������� ������� ��������
      //! \param tr - �������
      //! \param pos - ����������� �������
      void AddPositionToInput(TRANSITION tr, POSITION pos);

      //! �������� ������� � �������� ������� ��������
      //! \param tr - �������
      //! \param pos - ����������� �������
      void AddPositionToOutput(TRANSITION tr, POSITION pos);

      //! �������� ����������
      void SetMarking(const std::vector<MarkingType>& marking);

      //! �������� ���������� �� ������
      void ResetMarking();

      //! �������� ������� �����(��) � �������
      //! \return true - ���� ���������� ���������, false - �����
      bool SetToken(POSITION pos, MarkingType token);

      //! ��������� ��� ���������� ������������ ���������
      //! \param drawable - ��������� �� ������, ����������� ��������� ���������
      //! \return true - ���� ��� ���������� �������� ���������
      //! \return false - ���� ��������� ����������� ���������� ���-�� ���������
      bool FireAllTransitions(iface::iDrawable* drawable = NULL);

      // --------------------------------------------------------------------
      //! ���������, ����� �� �������� ����� � �������
      bool IsPositionAvailable(POSITION pos) const;

      //! ���������, ������� �� �������
      bool IsTransitionActive(TRANSITION tr) const;

      //! �������� ���������� �������
      POSITION GetPositionsNumber() const;

      //! �������� ���������� ���������
      TRANSITION GetTransitionsNumber() const;

      //! �������� ������� ����������
      const std::vector<MarkingType>& GetCurrentMarking() const;

      //! �������� ����������� ���������� ��� ������������ ����� ���������
      //! � ������ ������ ������� ����
      TRANSITION GetMaxFired() const; 

      // --------------------------------------------------------------------
      //! �������� �������� ��� ������ �������
      iposition GetPositions() const;
      //! �������� �������� ��� ������ ���������
      itransition GetTransitions() const;
      //! �������� �������� ��� ������ ������� ������� ��������
      itransition_input GetTransitionInput(TRANSITION tr) const;
      //! �������� �������� ��� ������ �������� ������� ��������
      itransition_output GetTransitionOutput(TRANSITION tr) const;

   private:
      //! ��������� ������������ ��������� ��������
      void FireTransition(TRANSITION tr);

   private:
      POSITION                 m_positions;  //!< �������
      TRANSITION               m_transitions;//!< ��������
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
      position_iterator(const PetriNet<MarkingType>* net, POSITION id);
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
      transition_iterator(const PetriNet<MarkingType>* net, TRANSITION id);
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
      transition_input_it(const PetriNet<MarkingType>* net, TRANSITION tr);
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
      transition_output_it(const PetriNet<MarkingType>* net, TRANSITION tr);
      const PetriNet<MarkingType>* m_net;
      TRANSITION m_tr;
      POSITION m_pos;
   };

// ==========================================================================
#include "petrinet.inl"
// ==========================================================================
} // end of namespace tdata

// ==========================================================================
#endif // __PETRINET_H