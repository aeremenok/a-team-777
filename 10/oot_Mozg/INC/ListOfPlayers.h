// **************************************************************************
//! \file   ListOfPlayers.h
//! \brief  Список игроков
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

      //! Добавляет игрока в список.
      //! Возвращает true в случае удачи, в противном случае false.
      bool addPlayer (Player* player);
     
      //! Удаляет игрока из списка.
      //! Возвращает true в случае удачи, в противном случае false.
      bool deletePlayer (std::string nameForDelete);

      //! Возвращает число игроков в списке
      int getCountOfPlayers ();

      //! Возвращает игрока, который в данный момент активен.
      Player* getActivePlayer ();

      //! Преход к следующему игроку по списку.
      void goNext ();
  
  private:
     // ~~~~~~~~~~~~~~~~~~~~~~~~ Предикат поиска имени ~~~~~~~~~~~~~~~~~~~~~~
     class FindName_eq
        :public std::unary_function<Player* , bool> 
     {
        //! Имя передаваемое через параметр в предикат
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
      const int maxPlayers; //!< Максимально число игроков
      //! Список игроков. Не может содержать больше чем maxPlayers игроков 
      std::list<Player*> listPl; 
      //! Текущий игрок
      std::list<Player*>::const_iterator currPlayer; 
};

}//end of namespace Game
// ==========================================================================
#endif
