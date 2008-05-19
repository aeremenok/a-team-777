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

// ==========================================================================
namespace tdata //!< Template Data
{
   // =======================================================================
   typedef __int32 POSITION;      //!< Идентификатор позиции
   typedef __int32 TRANSITION;    //!< Идентификатор перехода
   
   enum { NONE = -1 };            //!< Некорректное значение индекса

   // =======================================================================
   //! Структура для задания входной и выходной функций переходов
   struct IOFunc
   {
      TRANSITION Transition;     //!< Переход
      POSITION   Position;       //!< Связанная с ним позиция
   };

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

      //! Задать число позиций
      void SetPositionsNumber(POSITION number){}

      //! Задать число переходов
      void SetTransitionsNumber(TRANSITION number){}

      //! Добавить позицию во входную функцию перехода
      //! \param tr - переход
      //! \param pos - добавляемая позиция
      void AddPositionToInput(TRANSITION tr, POSITION pos){}

      //! Добавить позицию в выходную функцию перехода
      //! \param tr - переход
      //! \param pos - добавляемая позиция
      void AddPositionToOutput(TRANSITION tr, POSITION pos){}

      //! Изменить маркировку
      void SetMarking(const std::vector<MarkingType>& marking){}

      //! Изменить наличие фишки(ек) в позиции
      //! \return true - если добавление произошло, false - иначе
      bool SetToken(POSITION pos, MarkingType token){return 0}

      //! Запустить все допустимые срабатывания переходов
      //! \param drawable - указатель на объект, реализующий интерфейс рисования
      //! \return true - если все допустимые переходы сработали
      //! \return false - если сработало максимально допустимое кол-во переходов
      bool FireAllTransitions(iface::iDrawable* drawable = NULL){return 0}

      // --------------------------------------------------------------------
      //! Проверить, можно ли добавить фишку в позицию
      bool IsPositionAvailable(POSITION pos) const {return 0}

      //! Проверить, активен ли переход
      bool IsTransitionActive(TRANSITION tr) const {return 0}

      //! Получить количество позиций
      POSITION GetPositionsNumber() const {return 0}

      //! Получить количество переходов
      TRANSITION GetTransitionsNumber() const {return 0}

      //! Получить текущую маркировку
      const std::vector<MarkingType>& GetCurrentMarking() const {return 0}

      //! Поулчить максимально допустимое для срабатывания число переходов
      //! в рамках одного запуска сети
      TRANSITION GetMaxFired() const {return 0} 

      // --------------------------------------------------------------------
      //! Получить итератор для обхода позиций
      position_iterator GetPositions() const {return 0}
      //! Получить итератор для обхода переходов
      transition_iterator GetTransitions() const {return 0}
      //! Получить итератор для обхода входных позиций перехода
      transition_input_it GetTransitionInput(TRANSITION tr) const {return 0}
      //! Получить итератор для обхода выходных позиций перехода
      transition_output_it GetTransitionOutput(TRANSITION tr) const {return 0}

   private:
      //! Запустить срабатывание заданного перехода
      void FireTransition(TRANSITION tr);

   protected:
      std::vector<POSITION>    m_positions;  //!< Позиции
      std::vector<TRANSITION>  m_transitions;//!< Переходы
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
      position_iterator(const PetriNet<MarkingType>* parts, POSITION id);
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
      transition_iterator(const PetriNet<MarkingType>* parts, TRANSITION id);
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
      transition_input_it(const PetriNet<MarkingType>* parts, TRANSITION tr);
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
      transition_output_it(const PetriNet<MarkingType>* parts, TRANSITION tr);
      const PetriNet<MarkingType>* m_net;
      TRANSITION m_tr;
      POSITION m_pos;
   };

} // end of namespace tdata

// ==========================================================================
#endif // __PETRINET_H