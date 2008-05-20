// **************************************************************************
//! \file   petrinet.h
//! \brief  Шаблон полиморфного контейнера, реализующего сеть Петри
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
   typedef __int32 POSITION;      //!< Идентификатор позиции
   typedef __int32 TRANSITION;    //!< Идентификатор перехода
   
   enum { NONE = -1 };            //!< Некорректное значение индекса

   // =======================================================================
   //! Входная и выходная функция переходов
   typedef std::vector<POSITION> IOFunc;

   // =======================================================================
   // FORWARD DECLARATION
   template <typename MarkingType>
   class position_iterator;      //!< Итератор для обхода позиций
   template <typename MarkingType>
   class transition_iterator;    //!< Итератор для обхода переходов
   template <typename MarkingType>
   class transition_input_it;    //!< Итератор для обхода входных позиций перехода
   template <typename MarkingType>
   class transition_output_it;   //!< Итератор для обхода выходных позиций перехода

   // =======================================================================
   //! Сеть петри
   //! MarkingType - определяет ограничение на кол-во фишек в одной позиции
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

      //! Очистить содержимое
      void Clear();

      //! Задать число позиций
      void SetPositionsNumber(POSITION number);

      //! Задать число переходов
      void SetTransitionsNumber(TRANSITION number);

      //! Добавить позицию во входную функцию перехода
      //! \param tr - переход
      //! \param pos - добавляемая позиция
      void AddPositionToInput(TRANSITION tr, POSITION pos);

      //! Добавить позицию в выходную функцию перехода
      //! \param tr - переход
      //! \param pos - добавляемая позиция
      void AddPositionToOutput(TRANSITION tr, POSITION pos);

      //! Изменить маркировку
      void SetMarking(const std::vector<MarkingType>& marking);

      //! Сбросить маркировку на пустую
      void ResetMarking();

      //! Изменить наличие фишки(ек) в позиции
      //! \return true - если добавление произошло, false - иначе
      bool SetToken(POSITION pos, MarkingType token);

      //! Запустить все допустимые срабатывания переходов
      //! \param drawable - указатель на объект, реализующий интерфейс рисования
      //! \return true - если все допустимые переходы сработали
      //! \return false - если сработало максимально допустимое кол-во переходов
      bool FireAllTransitions(iface::iDrawable* drawable = NULL);

      // --------------------------------------------------------------------
      //! Проверить, можно ли добавить фишку в позицию
      bool IsPositionAvailable(POSITION pos) const;

      //! Проверить, активен ли переход
      bool IsTransitionActive(TRANSITION tr) const;

      //! Получить количество позиций
      POSITION GetPositionsNumber() const;

      //! Получить количество переходов
      TRANSITION GetTransitionsNumber() const;

      //! Получить текущую маркировку
      const std::vector<MarkingType>& GetCurrentMarking() const;

      //! Поулчить максимально допустимое для срабатывания число переходов
      //! в рамках одного запуска сети
      TRANSITION GetMaxFired() const; 

      // --------------------------------------------------------------------
      //! Получить итератор для обхода позиций
      iposition GetPositions() const;
      //! Получить итератор для обхода переходов
      itransition GetTransitions() const;
      //! Получить итератор для обхода входных позиций перехода
      itransition_input GetTransitionInput(TRANSITION tr) const;
      //! Получить итератор для обхода выходных позиций перехода
      itransition_output GetTransitionOutput(TRANSITION tr) const;

   private:
      //! Запустить срабатывание заданного перехода
      void FireTransition(TRANSITION tr);

   private:
      POSITION                 m_positions;  //!< Позиции
      TRANSITION               m_transitions;//!< Переходы
      std::vector<IOFunc>      m_Input;      //!< Множество входных функций
      std::vector<IOFunc>      m_Output;     //!< Множество выходных функций
      std::vector<MarkingType> m_Marking;    //!< Текущая маркировка сети
      //! Максимально допустимое кол-во переходов для срабатывания в рамках одного запуска
      TRANSITION               m_maxFired;
   };

   // =======================================================================
   //! Итератор для обхода позиций
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
   //! Итератор для обхода переходов
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
   //! Итератор для обхода входных позиций перехода
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
   //! Итератор для обхода выходных позиций перехода
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