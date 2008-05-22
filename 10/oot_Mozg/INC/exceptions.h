// **************************************************************************
//! \file   exceptions.h
//! \brief  ������� �������-����������
//! \author Lysenko A.A.
//! \date   22.May.2008 - 22.May.2008
// **************************************************************************

#ifndef __EXCEPTIONS_H
#define __EXCEPTIONS_H

// ==========================================================================
namespace excptns    //! ����������
{
// ==========================================================================
//! ����������, ������������ ��� ������������ ����
/** ������������ ������������ ������ ������������ ����������� ����������� 
    ����� ��������� ��� ��������� ���� ������.
 */
class MaxFiredException
{
public:
   MaxFiredException() : endOfGame(false) {};
   MaxFiredException(bool end) : endOfGame(end) {};
   MaxFiredException(const MaxFiredException& e) : endOfGame(e.endOfGame) {};

   bool isEndOfGame() const
   {
      return endOfGame;
   }

private:
   bool endOfGame;      //!< ���� ����, ��� ���� ��������� (������ �������� ������)
};

// ==========================================================================
//! ����������, �������������� ��� ������ �������������� ������
class SerRestoreException
{
public:
   enum errCode                  //!< ���� ������
   {
      ERR_CODE_NONE = -1,        //!< �������������� ������
      ERR_CODE_NOFILE,           //!< ���� �� ������ ��� �� ������� �������
      ERR_CODE_NOTENOUGHDATA,    //!< ���� �������� (������������ ������)
      ERR_CODE_MOREDATA,         //!< ������ ������ � �����, �������� ���������
      ERR_CODE_NOTVALID          //!< ���������� ������
   };

   SerRestoreException() : code(ERR_CODE_NONE) {};
   SerRestoreException(errCode cd) : code(cd) {};
   SerRestoreException(const SerRestoreException& e) : code(e.code) {};  

   errCode getCode() const
   {
      return code;
   }

private:
   errCode code;                 //!< ��� ������
};

// ==========================================================================
//! ����������, �������������� ��� ������� � ������ ������ �������
class PlayerListException
{
public:
   enum errCode                  //!< ���� ������
   {
      ERR_CODE_NONE = -1,        //!< �������������� ������
      ERR_CODE_NOMEMORY,         //!< ������������ ������
      ERR_CODE_NAMECONFLICT,     //!< ����� � ����� ������ ��� ����������
      ERR_CODE_MAXCOUNT          //!< ���������� ������������ ����� �������
   };

   PlayerListException() : code(ERR_CODE_NONE), name("") {};
   PlayerListException(errCode cd) : code(cd), name("") {};
   PlayerListException(LPCSTR str) : code(ERR_CODE_NAMECONFLICT), name(str) {};
   PlayerListException(const PlayerListException& e) : code(e.code), name(e.name) {};  

   errCode getCode() const
   {
      return code;
   }

   LPCSTR getName() const
   {
      return name.c_str();
   }

private:
   errCode code;                 //!< ��� ������
   std::string name;             //!< ��������� ���
};

} // endof namespace excptns
// ==========================================================================
#endif // __EXCEPTIONS_H