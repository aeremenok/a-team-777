// **************************************************************************
//! \file   ListOfPlayers.h
//! \brief  ������ �������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#ifndef __LISTOFPLAYERS_H
#define __LISTOFPLAYERS_H

#include "Player.h"
#include <list>
//#include <functional>

// ==========================================================================
namespace Game
{
class ListOfPlayers 
{
  public:
      ListOfPlayers(int max = 6);
      ~ListOfPlayers();

      //! ��������� ������ � ������.
      //! ���������� true � ������ �����, � ��������� ������ false.
      bool addPlayer (Player* player);
     
      //! ������� ������ �� ������.
      //! ���������� true � ������ �����, � ��������� ������ false.
      bool deletePlayer (std::string nameForDelete);

      //! ���������� ����� ������� � ������
      int getCountOfPlayers ();

      //! ���������� ������, ������� � ������ ������ �������.
      Player* getActivePlayer ();

      //! ������ � ���������� ������ �� ������.
      void goNext ();
  
  private:
     // ~~~~~~~~~~~~~~~~~~~~~~~~ �������� ������ ����� ~~~~~~~~~~~~~~~~~~~~~~
     class FindName_eq
        :public std::unary_function<Player* , bool> 
     {
        //! ��� ������������ ����� �������� � ��������
        const std::string name; 
     public:
        explicit FindName_eq(const std::string& str): name(str){}
        bool operator()(Player *const pl) const 
        {
           if(pl == 0)
              return false;
           return pl->getName() == name;
        }
     };
     // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  private: 
      const int maxPlayers; //!< ����������� ����� �������
      //! ������ �������. �� ����� ��������� ������ ��� maxPlayers ������� 
      std::list<Player*> listPl; 
      //! ������� �����
      std::list<Player*>::const_iterator currPlayer; 
};

}//end of namespace Game
// ==========================================================================
#endif
