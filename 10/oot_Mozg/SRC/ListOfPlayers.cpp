// **************************************************************************
//! \file   ListOfPlayers.cpp
//! \brief  ������ �������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 20.May.2008
// **************************************************************************


#include "stdafx.h"
#include "ListOfPlayers.h"
#include "MainPlayer.h"
#include "SlavePlayer.h"
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
int ListOfPlayers::PutIntoArchive(ser::Archive& archive)
{
   //! ������� writter, ����� ������� ����� ������ ������
   ser::writer wr(archive);
   //! �������� ����� �������� ������, ��������� � �������
   std::list<Player*>::const_iterator curr = listPl.begin();
   int i = 1;
   while (curr != currPlayer)
   {
      i++;
      curr++;
   }
   //! ���������� ���������� ������� � ����� ���������
   wr << getCountOfPlayers() << i;
   
   //! ���������� ����� � �����
   for (curr = listPl.begin(); curr != listPl.end(); curr++)
   {
      wr << (*curr)->getName();
   }
   return wr.id();
}

void ListOfPlayers::GetFromArchive(ser::Archive& archive, int id)
{
   //! ������� reader, �� �������� ����� ������ ������
   ser::reader rd(archive, id);
   int numPl;        //!< ���������� �������
   int numActivePl;  //!< �������� �����
   //! ������� ������ � ����������
   rd >> numPl >> numActivePl;

   if (numPl == 0)   //! ���� �� �������� �� ������ ������. �������� 
      return;        //! ��������� �����

   std::string name; //!< ��� ���������� ����
   rd >> name;       //!< ��������� ��� �������� ������ 
   Player* pl = new MainPlayer;
   pl->setName(name);
   addPlayer(pl);  //!< ��������� �������� ������ ������

   //! ��������� ��������� �������
   for (int i = 1; i < numPl ; i++)
   {
      rd >> name;
      pl = new SlavePlayer;
      pl->setName(name);
      addPlayer(pl);
   }

   //! ������������� �������� ������
   for (int i = 1; i < numActivePl; i++)
   {
      goNext();
   }
}

}//end of namespace Game
// ==========================================================================