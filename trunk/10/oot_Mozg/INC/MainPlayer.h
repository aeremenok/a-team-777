// **************************************************************************
//! \file   MainPlayer.h
//! \brief  ����� ����������� ������ ������������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#ifndef __MAINPLAYER_H
#define __MAINPLAYER_H

#include <string>
#include "Player.h"

// ==========================================================================
namespace Game 
{
class MainPlayer : public Player 
{
  public:
      bool ifMain () const; //!< ���������� true
};
}//end of namespace Game
// ==========================================================================
#endif
