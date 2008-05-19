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
// �������� ����������
template <typename MT>
inline void PetriNet<MT>::Clear()
{
}

// ==========================================================================
// ������ ����� �������
template <typename MT>
inline void PetriNet<MT>::SetPositionsNumber(POSITION number)
{
}

// ==========================================================================
// ������ ����� ���������
template <typename MT>
inline void PetriNet<MT>::SetTransitionsNumber(TRANSITION number)
{
}

// ==========================================================================
// �������� ������� �� ������� ������� ��������
// \param tr - �������
// \param pos - ����������� �������
template <typename MT>
inline void PetriNet<MT>::AddPositionToInput(TRANSITION tr, POSITION pos)
{
}

// ==========================================================================
// �������� ������� � �������� ������� ��������
// \param tr - �������
// \param pos - ����������� �������
template <typename MT>
inline void PetriNet<MT>::AddPositionToOutput(TRANSITION tr, POSITION pos)
{
}

// ==========================================================================
// �������� ����������
template <typename MT>
inline void PetriNet<MT>::SetMarking(const std::vector<MT>& marking)
{
}

// ==========================================================================
// �������� ������� �����(��) � �������
// \return true - ���� ���������� ���������, false - �����
template <typename MT>
inline bool PetriNet<MT>::SetToken(POSITION pos, MT token)
{
}

// ==========================================================================
// ��������� ��� ���������� ������������ ���������
// \param drawable - ��������� �� ������, ����������� ��������� ���������
// \return true - ���� ��� ���������� �������� ���������
// \return false - ���� ��������� ����������� ���������� ���-�� ���������
template <typename MT>
inline bool PetriNet<MT>::FireAllTransitions(iface::iDrawable* drawable = NULL)
{
}

// ==========================================================================
// ���������, ����� �� �������� ����� � �������
template <typename MT>
inline bool PetriNet<MT>::IsPositionAvailable(POSITION pos) const
{
}

// ==========================================================================
// ���������, ������� �� �������
template <typename MT>
inline bool PetriNet<MT>::IsTransitionActive(TRANSITION tr) const
{
}

// ==========================================================================
// �������� ���������� �������
template <typename MT>
inline POSITION PetriNet<MT>::GetPositionsNumber() const
{
}

// ==========================================================================
// �������� ���������� ���������
template <typename MT>
inline TRANSITION PetriNet<MT>::GetTransitionsNumber() const
{
}

// ==========================================================================
// �������� ������� ����������
template <typename MT>
inline const std::vector<MT>& PetriNet<MT>::GetCurrentMarking() const
{
}

// ==========================================================================
// �������� ����������� ���������� ��� ������������ ����� ���������
// � ������ ������ ������� ����
template <typename MT>
inline TRANSITION PetriNet<MT>::GetMaxFired() const
{
}

// ==========================================================================
// �������� �������� ��� ������ �������
template <typename MT>
inline position_iterator<MT> PetriNet<MT>::GetPositions() const
{
}

// ==========================================================================
// �������� �������� ��� ������ ���������
template <typename MT>
inline transition_iterator<MT> PetriNet<MT>::GetTransitions() const
{
}

// ==========================================================================
// �������� �������� ��� ������ ������� ������� ��������
template <typename MT>
inline transition_input_it<MT> PetriNet<MT>::GetTransitionInput(TRANSITION tr) const
{
}

// ==========================================================================
// �������� �������� ��� ������ �������� ������� ��������
template <typename MT>
inline transition_output_it<MT> PetriNet<MT>::GetTransitionOutput(TRANSITION tr) const
{
}

// ==========================================================================
// ��������� ������������ ��������� ��������
template <typename MT>
inline void PetriNet<MT>::FireTransition(TRANSITION tr)
{
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
inline position_iterator<MT>::position_iterator(const PetriNet<MT>* net, POSITION id);

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
      IOFunc::iterator it = 
         std::find(m_net->m_Input[tr].begin(), m_net->m_Input[tr].end(), m_pos);
      it++;
      if ( it != m_net->m_Input[tr].end() )
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
      IOFunc::iterator it = 
         std::find(m_net->m_Output[tr].begin(), m_net->m_Output[tr].end(), m_pos);
      it++;
      if ( it != m_net->m_Output[tr].end() )
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