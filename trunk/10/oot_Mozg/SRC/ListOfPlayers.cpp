// **************************************************************************
//! \file   ListOfPlayers.cpp
//! \brief  ������ �������
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
   //! �� ������� ������� ���� �������
   std::list<Player*>::iterator itr = listPl.begin();
   while(itr != listPl.end())
   {
      delete *itr; 
      itr++;
   }
   //! ���������� ������
   listPl.clear();
}

bool ListOfPlayers::addPlayer (Player* player)
{
   //! ���������, ��� � ������ ������ ���  maxPlayers � ��������� ������
   //! ���� ������� ������ ���������� false
   if(getCountOfPlayers () == maxPlayers) // ������ ��������� ������
      return false;

   //! ���� ����� � ����� ������ ��� ���� � ������ ���������� false
   std::list<Player*>::const_iterator itr = find_if(listPl.begin(), listPl.end(), 
      FindName_eq(player->getName()));
   if(itr != listPl.end()) // ����� ����� ��� ���� � ������ 
      return false;
   
   //! ��������� ������
   try
   {
      listPl.push_back(player);
   }
   catch (std::bad_alloc&)
   {
      ::MessageBox(0,"������������ ������", "Error", MB_OK | MB_ICONERROR);
      currPlayer = listPl.begin();
      return false;
   }
   currPlayer = listPl.begin();
   return true;
}

bool ListOfPlayers::deletePlayer (std::string nameForDelete)
{
   //! ���� ������� ���, ������ �� ������
   //! ������� ������ � ����� ������
   std::list<Player*>::iterator itr;
   if ((itr = find_if(listPl.begin(), listPl.end(), 
         FindName_eq(nameForDelete))) != listPl.end())
   {
      delete *itr; //!< ������� ������, �� �������� ��������� ��������
      listPl.erase(itr); //!< ��������� ������� �� ������ 
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
   //! ���� ������ ���� ���������� NULL
   if(getCountOfPlayers () == 0)
	   return 0;

   //! ���� ������ �� ����, �� currPlayer == NULL, ���������� ������� � ������
   if(currPlayer == listPl.end())
      currPlayer = listPl.begin(); //!< ������������� ������� �������

   //! ���������� �������� ������
   return *currPlayer;
      
}

void ListOfPlayers::goNext ()
{
   //! ���� ����� ���� ��� �� ������, �� ������ �� ������
   if(getCountOfPlayers () < 2)
      return;

   //! � ��������� ������ ��������� � ����������, ���� ����� �� ����������,
   //! ��������� � �������
   if (++currPlayer == listPl.end())
      currPlayer == listPl.begin();
}

}//end of namespace Game
// ==========================================================================