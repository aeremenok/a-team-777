// **************************************************************************
//! \file   Player.h
//! \brief  ����� ����������� ������ 
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
      //! ������ ��� ������
      void setName (std::string newName);

      //! ������ ��� ������
      std::string getName () const;

      //! ���������� true, ���� �����-�����������.
      virtual bool ifMain () const =0;

  private: 
   
      std::string name; //!< ��� ������
};
}//end of namespace Game
// ==========================================================================
#endif
