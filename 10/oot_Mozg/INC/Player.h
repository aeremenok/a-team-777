// **************************************************************************
//! \file   Player.h
//! \brief  Класс реализующий игрока 
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#ifndef __PLAYER_H
#define __PLAYER_H

#include <string>

// ==========================================================================
namespace Game 
{
class Player 
{
  public:
      //! Задает имя игрока
      void setName (std::wstring newName);

      //! Задает имя игрока
      std::wstring getName () const;

      //! Возвращает true, если игрок-организатор.
      virtual bool ifMain () const =0;

  private: 
   
      std::wstring name; //!< Имя игрока
};
}//end of namespace Game
// ==========================================================================
#endif
