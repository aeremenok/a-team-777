// **************************************************************************
//! \file   petrinet.inl
//! \brief  Inline implementation
//! \author Lysenko A.A.
//! \date   20.May.2008 - 20.May.2008
// **************************************************************************

// ==========================================================================
// PETRINET
// ==========================================================================
template <typename MT>
inline PetriNet<MT>::PetriNet(TRANSITION maxFired)
   : m_maxFired(maxFired), m_positions(0), m_transitions(0) {}

// ==========================================================================
//                            Î×ÈÑÒÈÒÜ ÑÎÄÅĞÆÈÌÎÅ
template <typename MT>
inline void PetriNet<MT>::Clear()
{
   CONFIRM ( m_positions >= 0 && m_transitions >= 0 );

   m_positions = 0;
   m_transitions = 0;
   m_Input.clear();
   m_Output.clear();
   m_Marking.clear();

   CONFIRM ( m_positions == 0 && m_transitions == 0 &&
             m_Input.size() == 0 && m_Output.size() == 0 &&
             m_Marking.size() == 0 );
}

// ==========================================================================
//                           ÇÀÄÀÒÜ ×ÈÑËÎ ÏÎÇÈÖÈÉ
template <typename MT>
inline void PetriNet<MT>::SetPositionsNumber(POSITION number)
{
   CONFIRM ( m_positions >= 0 && m_transitions >= 0 );

   // ÅÑËÈ ×ÈÑËÎ ÏÎÇÈÖÈÉ ÏĞÅÆÍÅÅ, ÍÈ×ÅÃÎ ÍÅ ÄÅËÀÒÜ
   if ( GetPositionsNumber() == number )
      return;

   // ÒÀÊ ÊÀÊ ÌÅÍßÅÒÑß ×ÈÑËÎ ÏÎÇÈÖÈÉ, ÑÁĞÎÑÈÒÜ ÂÑÅ ÑÂßÇÀÍÍÛÅ ÄÀÍÍÛÅ
   m_Input.clear();
   m_Output.clear();
   m_Marking.clear();

   m_positions = number;
   m_Marking.resize(number, false);
   m_Input.resize(GetTransitionsNumber());
   m_Output.resize(GetTransitionsNumber());

   CONFIRM ( m_positions == number && m_transitions >= 0 &&
             m_Input.size() == m_transitions &&
             m_Output.size() == m_transitions &&
             m_Marking.size() == m_positions );
}

// ==========================================================================
//                        ÇÀÄÀÒÜ ×ÈÑËÎ ÏÅĞÅÕÎÄÎÂ
template <typename MT>
inline void PetriNet<MT>::SetTransitionsNumber(TRANSITION number)
{
   CONFIRM ( m_positions >= 0 && m_transitions >= 0 );

   // ÅÑËÈ ×ÈÑËÎ ÏÅĞÅÕÎÄÎÂ ÏĞÅÆÍÅÅ, ÍÈ×ÅÃÎ ÍÅ ÄÅËÀÒÜ
   if ( GetTransitionsNumber() == number )
      return;

   // ÒÀÊ ÊÀÊ ÌÅÍßÅÒÑß ×ÈÑËÎ ÏÅĞÅÕÎÄÎÂ, ÑÁĞÎÑÈÒÜ ÂÑÅ ÑÂßÇÀÍÍÛÅ ÄÀÍÍÛÅ
   m_Input.clear();
   m_Output.clear();

   m_transitions = number;
   m_Input.resize(number);
   m_Output.resize(number);

   CONFIRM ( m_positions >= 0 && m_transitions == number &&
             m_Input.size() == number &&
             m_Output.size() == number );
}

// ==========================================================================
//             ÄÎÁÀÂÈÒÜ ÏÎÇÈÖÈŞ ÂÎ ÂÕÎÄÍÓŞ ÔÓÍÊÖÈŞ ÏÅĞÅÕÎÄÀ
template <typename MT>
inline void PetriNet<MT>::AddPositionToInput(TRANSITION tr, POSITION pos)
{
   if ( tr >= 0 && tr < GetTransitionsNumber() )
      // ÑÂßÇÈ ÊĞÀÒÍÎÑÒÈ > 1 ÍÅ ÇÀÏĞÅÙÅÍÛ, ÏÎİÒÎÌÓ ÍÅ ÏĞÎÂÅĞßÅÌ ÍÀËÈ×ÈÅ ÏÎÇÈÖÈÈ
      m_Input[tr].push_back(pos);
}

// ==========================================================================
//              ÄÎÁÀÂÈÒÜ ÏÎÇÈÖÈŞ Â ÂÛÕÎÄÍÓŞ ÔÓÍÊÖÈŞ ÏÅĞÅÕÎÄÀ
template <typename MT>
inline void PetriNet<MT>::AddPositionToOutput(TRANSITION tr, POSITION pos)
{
   if ( tr >= 0 && tr < GetTransitionsNumber() )
      // ÑÂßÇÈ ÊĞÀÒÍÎÑÒÈ > 1 ÍÅ ÇÀÏĞÅÙÅÍÛ, ÏÎİÒÎÌÓ ÍÅ ÏĞÎÂÅĞßÅÌ ÍÀËÈ×ÈÅ ÏÎÇÈÖÈÈ
      m_Output[tr].push_back(pos);
}

// ==========================================================================
//                         ÈÇÌÅÍÈÒÜ ÌÀĞÊÈĞÎÂÊÓ
template <typename MT>
inline void PetriNet<MT>::SetMarking(const std::vector<MT>& marking)
{
   if ( marking.size() == m_Marking.size() )
      m_Marking = marking;
}

// ==========================================================================
//                      ÑÁĞÎÑÈÒÜ ÌÀĞÊÈĞÎÂÊÓ ÍÀ ÏÓÑÒÓŞ
template <typename MT>
inline void PetriNet<MT>::ResetMarking()
{
   m_Marking.assign(GetPositionsNumber(), false);
}

// ==========================================================================
//                ÈÇÌÅÍÈÒÜ ÍÀËÈ×ÈÅ ÔÈØÊÈ(ÅÊ) Â ÏÎÇÈÖÈÈ
template <typename MT>
inline bool PetriNet<MT>::SetToken(POSITION pos, MT token)
{
   if ( pos >= 0 && pos < GetPositionsNumber() )
      // ÄÎÁÀÂËßÅÌ ÔÈØÊÓ È ÏÎÇÈÖÈß ÍÅÄÎÑÒÓÏÍÀ
      if ( token && !IsPositionAvailable(pos) )
         return false;
      else
      {
         m_Marking[pos] = token;
         return true;
      }

   return false;
}

// ==========================================================================
//           ÇÀÏÓÑÒÈÒÜ ÂÑÅ ÄÎÏÓÑÒÈÌÛÅ ÑĞÀÁÀÒÛÂÀÍÈß ÏÅĞÅÕÎÄÎÂ
template <typename MT>
inline bool PetriNet<MT>::FireAllTransitions(iface::iDrawable* drawable = NULL)
{
   itransition itF = GetTransitions();    // Èñõîäíûé èòåğàòîğ
   TRANSITION fired = 0;                  // Êîë-âî ñğàáîòàâøèõ ïåğåõîäîâ

   itransition it = itF;
   while ( !it.end() && (fired < m_maxFired) )
      // ÏÅĞÅÕÎÄ ÀÊÒÈÂÅÍ
      if ( IsTransitionActive(it.transition()) )
      {
         // ÇÀÏÓÑÒÈÒÜ ÑĞÀÁÀÒÛÂÀÍÈÅ
         FireTransition(it.transition());
         // ÓÂÅËÈ×ÈÒÜ Ñ×ÅÒ×ÈÊ
         fired++;
         // ÂÅĞÍÓÒÑß Â ÍÀ×ÀËÎ ÎÁÕÎÄÀ
         it = itF;
         // ÂÛÇÂÀÒÜ ÏÅĞÅĞÈÑÎÂÊÓ
         if ( drawable != NULL )
            drawable->Redraw();
      }
      // ÏÅĞÅÕÎÄ ÍÅÀÊÒÈÂÅÍ
      else
         ++it;

   if ( fired == m_maxFired )
      return false;
   else
      return true;
}

// ==========================================================================
//             ÏĞÎÂÅĞÈÒÜ, ÌÎÆÍÎ ËÈ ÄÎÁÀÂÈÒÜ ÔÈØÊÓ Â ÏÎÇÈÖÈŞ
/* Òàê êàê ïîâåäåíèå çàâèñèò îò òèïà ìàğêèğîâêè, ôóíêöèÿ
   ñïåöèôèöèğóåòñÿ îòäåëüíî äëÿ áóëåâîãî è ÷èñëîâûõ òèïîâ */
inline bool PetriNet<bool>::IsPositionAvailable(POSITION pos) const
{
   if ( pos >= 0 && pos < GetPositionsNumber() )
      if ( m_Marking[pos] )
         return false;
      else
         return true;

   return false;
}

// ==========================================================================
//                     ÏĞÎÂÅĞÈÒÜ, ÀÊÒÈÂÅÍ ËÈ ÏÅĞÅÕÎÄ
/* Òàê êàê ïîâåäåíèå çàâèñèò îò òèïà ìàğêèğîâêè, ôóíêöèÿ
   ñïåöèôèöèğóåòñÿ îòäåëüíî äëÿ áóëåâîãî è ÷èñëîâûõ òèïîâ */
inline bool PetriNet<bool>::IsTransitionActive(TRANSITION tr) const
{
   if ( tr < 0 || tr >= GetTransitionsNumber() )
      return false;

   bool ret = true;

   // ÏĞÎÂÅĞÈÒÜ, ×ÒÎ ÂÎ ÂÑÅÕ ÂÕÎÄÍÛÕ ÏÎÇÈÖÈßÕ ÅÑÒÜ ÔÈØÊÈ
   itransition_input input = GetTransitionInput(tr);
   for ( ; !input.end(); ++input )
      ret = ret && m_Marking[input.position()];

   // ÏĞÎÂÅĞÈÒÜ, ×ÒÎ ÑĞÅÄÈ ÂÛÕÎÄÍÛÕ ÏÎÇÈÖÈÉ ÅÑÒÜ ÕÎÒÜ ÎÄÍÀ ÑÂÎÁÎÄÍÀß
   bool out_ready = false;
   itransition_output output = GetTransitionOutput(tr);
   for ( ; !output.end(); ++output )
      out_ready = out_ready || !m_Marking[output.position()];

   return ret && out_ready;
}

// ==========================================================================
//                        ÏÎËÓ×ÈÒÜ ÊÎËÈ×ÅÑÒÂÎ ÏÎÇÈÖÈÉ
template <typename MT>
inline POSITION PetriNet<MT>::GetPositionsNumber() const
{
   return m_positions;
}

// ==========================================================================
//                     ÏÎËÓ×ÈÒÜ ÊÎËÈ×ÅÑÒÂÎ ÏÅĞÅÕÎÄÎÂ
template <typename MT>
inline TRANSITION PetriNet<MT>::GetTransitionsNumber() const
{
   return m_transitions;
}

// ==========================================================================
//                      ÏÎËÓ×ÈÒÜ ÒÅÊÓÙÓŞ ÌÀĞÊÈĞÎÂÊÓ
template <typename MT>
inline const std::vector<MT>& PetriNet<MT>::GetCurrentMarking() const
{
   return m_Marking;
}

// ==========================================================================
template <typename MT>
inline TRANSITION PetriNet<MT>::GetMaxFired() const
{
   return m_maxFired;
}

// ==========================================================================
//                 ÏÎËÓ×ÈÒÜ ÈÒÅĞÀÒÎĞ ÄËß ÎÁÕÎÄÀ ÏÎÇÈÖÈÉ
template <typename MT>
inline position_iterator<MT> PetriNet<MT>::GetPositions() const
{
   if ( GetPositionsNumber() > 0 )
      return iposition(this, 0);
   else
      return iposition();
}

// ==========================================================================
//                ÏÎËÓ×ÈÒÜ ÈÒÅĞÀÒÎĞ ÄËß ÎÁÕÎÄÀ ÏÅĞÅÕÎÄÎÂ
template <typename MT>
inline transition_iterator<MT> PetriNet<MT>::GetTransitions() const
{
   if ( GetTransitionsNumber() > 0 )
      return itransition(this, 0);
   else
      return itransition();
}

// ==========================================================================
//           ÏÎËÓ×ÈÒÜ ÈÒÅĞÀÒÎĞ ÄËß ÎÁÕÎÄÀ ÂÕÎÄÍÛÕ ÏÎÇÈÖÈÉ ÏÅĞÅÕÎÄÀ
template <typename MT>
inline transition_input_it<MT> PetriNet<MT>::GetTransitionInput(TRANSITION tr) const
{
   if ( tr >= 0 && tr < GetTransitionsNumber() )
      return itransition_input(this, tr);
   else
      return itransition_input();
}

// ==========================================================================
//          ÏÎËÓ×ÈÒÜ ÈÒÅĞÀÒÎĞ ÄËß ÎÁÕÎÄÀ ÂÛÕÎÄÍÛÕ ÏÎÇÈÖÈÉ ÏÅĞÅÕÎÄÀ
template <typename MT>
inline transition_output_it<MT> PetriNet<MT>::GetTransitionOutput(TRANSITION tr) const
{
   if ( tr >= 0 && tr < GetTransitionsNumber() )
      return itransition_output(this, tr);
   else
      return itransition_output();
}

// ==========================================================================
//               ÇÀÏÓÑÒÈÒÜ ÑĞÀÁÀÒÛÂÀÍÈÅ ÇÀÄÀÍÍÎÃÎ ÏÅĞÅÕÎÄÀ
/* Òàê êàê ïîâåäåíèå çàâèñèò îò òèïà ìàğêèğîâêè, ôóíêöèÿ
   ñïåöèôèöèğóåòñÿ îòäåëüíî äëÿ áóëåâîãî è ÷èñëîâûõ òèïîâ */
inline void PetriNet<bool>::FireTransition(TRANSITION tr)
{
   if ( !IsTransitionActive(tr) )
      return;

   // ÇÀÁÀĞÀÒÜ ÔÈØÊÈ ÈÇ ÂÕÎÄÍÛÕ ÏÎÇÈÖÈÉ
   itransition_input input = GetTransitionInput(tr);
   for ( ; !input.end(); ++input )
      SetToken(input.position(), false);

   // ÏÅĞÊÈÍÓÒÜ Â ÂÛÕÎÄÍÛÅ
   itransition_output output = GetTransitionOutput(tr);
   for ( ; !output.end(); ++output )
      SetToken(output.position(), true);
}

// ==========================================================================
// POSITION_ITERATOR
// ==========================================================================
template <typename MT>
inline position_iterator<MT>::position_iterator()
   : m_net(NULL), m_pos(NONE) {}

template <typename MT>
inline position_iterator<MT>::position_iterator(const position_iterator& rv)
   : m_net(rv.m_net), m_pos(rv.m_pos) {}

template <typename MT>
inline position_iterator<MT>& position_iterator<MT>::operator=(const position_iterator<MT>& rv)
{
   if (&rv == this)
      return *this;

   m_net = rv.m_net;
   m_pos = rv.m_pos;

   return *this;
}

template <typename MT>
inline bool position_iterator<MT>::end() const

{
   return !m_net || (m_pos < 0);
}

template <typename MT>
inline position_iterator<MT>& position_iterator<MT>::operator++()
{
   if ( !end() )
      if ( m_pos != m_net->m_positions - 1 )
         m_pos++;
      else
         m_pos = NONE;

   return *this;
}

template <typename MT>
inline POSITION position_iterator<MT>::position() const
{
   return m_pos;
}

template <typename MT>
inline position_iterator<MT>::position_iterator(const PetriNet<MT>* net, POSITION id)
   : m_net(net), m_pos(id) {}

// ==========================================================================
// TRANSITION_ITERATOR
// ==========================================================================
template <typename MT>
inline transition_iterator<MT>::transition_iterator()
   : m_net(NULL), m_tr(NONE) {}

template <typename MT>
inline transition_iterator<MT>::transition_iterator(const transition_iterator<MT>& rv)
   : m_net(rv.m_net), m_tr(rv.m_tr) {}

template <typename MT>
inline transition_iterator<MT>& transition_iterator<MT>::operator=(const transition_iterator<MT>& rv)
{
   if (&rv == this)
      return *this;

   m_net = rv.m_net;
   m_tr = rv.m_tr;

   return *this;
}


template <typename MT>
inline bool transition_iterator<MT>::end() const
{
   return !m_net || (m_tr < 0);
}

template <typename MT>
inline transition_iterator<MT>& transition_iterator<MT>::operator++()
{
   if ( !end() )
      if ( m_tr != m_net->m_transitions - 1 )
         m_tr++;
      else
         m_tr = NONE;

   return *this;
}

template <typename MT>
inline TRANSITION transition_iterator<MT>::transition() const
{
   return m_tr;
}

template <typename MT>
inline transition_iterator<MT>::transition_iterator(const PetriNet<MT>* net, TRANSITION id)
   : m_net(net), m_tr(id) {}

// ==========================================================================
// TRANSITION_INPUT_IT
// ==========================================================================
template <typename MT>
inline transition_input_it<MT>::transition_input_it()
   : m_net(NULL), m_tr(NONE), m_pos(NONE) {}

template <typename MT>
inline transition_input_it<MT>::transition_input_it(const transition_input_it<MT>& rv)
   : m_net(rv.m_net), m_tr(rv.m_tr), m_pos(rv.m_pos) {}

template <typename MT>
inline transition_input_it<MT>& transition_input_it<MT>::operator=(const transition_input_it<MT>& rv)
{
   if (&rv == this)
      return *this;

   m_net = rv.m_net;
   m_tr = rv.m_tr;
   m_pos = rv.m_pos;

   return *this;
}

template <typename MT>
inline bool transition_input_it<MT>::end() const
{
   return !m_net || (m_tr < 0) || (m_pos < 0);
}

template <typename MT>
inline transition_input_it<MT>& transition_input_it<MT>::operator++()
{
   if ( !end() )
   {
      IOFunc::const_iterator it = 
         std::find(m_net->m_Input[m_tr].begin(), m_net->m_Input[m_tr].end(), m_pos);
      it++;
      if ( it != m_net->m_Input[m_tr].end() )
         m_pos = *it;
      else
         m_pos = NONE;
   }

   return *this;
}

template <typename MT>
inline POSITION transition_input_it<MT>::position() const
{
   return m_pos;
}

template <typename MT>
inline transition_input_it<MT>::transition_input_it(const PetriNet<MT>* net, TRANSITION tr)
   : m_net(net), m_tr(tr)
{
   if ( m_net->m_Input[tr].size() > 0 )
      m_pos = m_net->m_Input[tr].front();
   else
      m_pos = NONE;
}

// ==========================================================================
// TRANSITION_OUTPUT_IT
// ==========================================================================
template <typename MT>
inline transition_output_it<MT>::transition_output_it()
   : m_net(NULL), m_tr(NONE), m_pos(NONE) {}

template <typename MT>
inline transition_output_it<MT>::transition_output_it(const transition_output_it<MT>& rv)
   : m_net(rv.m_net), m_tr(rv.m_tr), m_pos(rv.m_pos) {}

template <typename MT>
inline transition_output_it<MT>& transition_output_it<MT>::operator=(const transition_output_it<MT>& rv)
{
   if (&rv == this)
      return *this;

   m_net = rv.m_net;
   m_tr = rv.m_tr;
   m_pos = rv.m_pos;

   return *this;
}

template <typename MT>
inline bool transition_output_it<MT>::end() const
{
   return !m_net || (m_tr < 0) || (m_pos < 0);
}

template <typename MT>
inline transition_output_it<MT>& transition_output_it<MT>::operator++()
{
   if ( !end() )
   {
      IOFunc::const_iterator it = 
         std::find(m_net->m_Output[m_tr].begin(), m_net->m_Output[m_tr].end(), m_pos);
      it++;
      if ( it != m_net->m_Output[m_tr].end() )
         m_pos = *it;
      else
         m_pos = NONE;
   }

   return *this;
}

template <typename MT>
inline POSITION transition_output_it<MT>::position() const
{
   return m_pos;
}

template <typename MT>
inline transition_output_it<MT>::transition_output_it(const PetriNet<MT>* net, TRANSITION tr)
   : m_net(net), m_tr(tr)
{
   if ( m_net->m_Output[tr].size() > 0 )
      m_pos = m_net->m_Output[tr].front();
   else
      m_pos = NONE;
}

// ==========================================================================