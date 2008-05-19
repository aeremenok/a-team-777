// **************************************************************************
//! \file   SlavePlayer.h
//! \brief  ����� ����������� �������� ������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#ifndef __SLAVEPLAYER_H
#define __SLAVEPLAYER_H

#include <string>
#include "Player.h"

// ==========================================================================
namespace Game 
{
class SlavePlayer : public Player 
{
  public:
      bool ifMain () const; //!< ���������� false

};
}//end of namespace Game
// ==========================================================================

#endif
