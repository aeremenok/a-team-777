// **************************************************************************
//! \file   SlavePlayer.h
//! \brief  Класс реализующий ведомого игрока
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
      bool ifMain () const; //!< Возвращает false

};
}//end of namespace Game
// ==========================================================================

#endif
