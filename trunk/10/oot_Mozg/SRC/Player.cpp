// **************************************************************************
//! \file   Player.cpp
//! \brief  Класс реализующий игрока 
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#include "stdafx.h"
#include "Player.h"

// ==========================================================================
namespace Game 
{

void Player::setName (std::wstring newName)
{
   name = newName;
}

std::wstring Player::getName () const
{
   return name;
}

}//end of namespace Game
// ==========================================================================


