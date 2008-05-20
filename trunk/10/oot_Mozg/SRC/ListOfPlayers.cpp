// **************************************************************************
//! \file   ListOfPlayers.cpp
//! \brief  Список игроков
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************


#include "stdafx.h"
#include "ListOfPlayers.h"
#include <algorithm>
 

// ==========================================================================
namespace Game
{
ListOfPlayers::ListOfPlayers(int max)
   :maxPlayers(max)
{
   currPlayer = listPl.end();
}

ListOfPlayers::~ListOfPlayers()
{
   //! По очереди удаляем всех игроков
   std::list<Player*>::iterator itr = listPl.begin();
   while(itr != listPl.end())
   {
      delete *itr; 
      itr++;
   }
   //! Опустошаем список
   listPl.clear();
}

bool ListOfPlayers::addPlayer (Player* player)
{
   //! Проверяет, что в списке меньше чем  maxPlayers и добавляет нового
   //! Если игроков меньше возвращает false
   if(getCountOfPlayers () == maxPlayers) // Больше добавлять нельзя
      return false;

   //! Если игрок с такми именем уже есть в списке возвращает false
   std::list<Player*>::const_iterator itr = find_if(listPl.begin(), listPl.end(), 
      FindName_eq(player->getName()));
   if(itr != listPl.end()) // Такой игрок уже есть в списке 
      return false;
   
   //! Добавляем игрока
   try
   {
      listPl.push_back(player);
   }
   catch (std::bad_alloc&)
   {
      ::MessageBox(0,"Недостаточно памяти", "Error", MB_OK | MB_ICONERROR);
      currPlayer = listPl.begin();
      return false;
   }
   currPlayer = listPl.begin();
   return true;
}

bool ListOfPlayers::deletePlayer (std::string nameForDelete)
{
   //! Если игроков нет, ничего не делаем
   //! Удаляем игрока с таким именем
   std::list<Player*>::iterator itr;
   if ((itr = find_if(listPl.begin(), listPl.end(), 
         FindName_eq(nameForDelete))) != listPl.end())
   {
      delete *itr; //!< Удаляет игрока, на которого указывает итератор
      listPl.erase(itr); //!< Исклячает позицию из списка 
      currPlayer = listPl.begin();
      return true;
   }
	return false;
}

int ListOfPlayers::getCountOfPlayers ()
{
	return listPl.size();
}

Player* ListOfPlayers::getActivePlayer ()
{
   //! Если список пуст возвращает NULL
   if(getCountOfPlayers () == 0)
	   return 0;

   //! Если список не пуст, но currPlayer == NULL, возвращает первого в списке
   if(currPlayer == listPl.end())
      currPlayer = listPl.begin(); //!< Устанавливаем первого текущим

   //! Возвращает текущего игрока
   return *currPlayer;
      
}

void ListOfPlayers::goNext ()
{
   //! Если игрок один или не одного, то ничего не делаем
   if(getCountOfPlayers () < 2)
      return;

   //! В противном случае переходим к следующему, если вышли за последнего,
   //! переходим к первому
   if (++currPlayer == listPl.end())
      currPlayer == listPl.begin();
}

}//end of namespace Game
// ==========================================================================