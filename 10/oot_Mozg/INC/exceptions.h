// **************************************************************************
//! \file   exceptions.h
//! \brief  Система классов-исключений
//! \author Lysenko A.A.
//! \date   22.May.2008 - 22.May.2008
// **************************************************************************

#ifndef __EXCEPTIONS_H
#define __EXCEPTIONS_H

// ==========================================================================
namespace excptns    //! Исключения
{
// ==========================================================================
//! Исключение, генерируемое при зацикливании сети
/** Зацикливание определяется фактом срабатывания максимально допустимого 
    числа переходов при очередном ходе игрока.
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
   bool endOfGame;      //!< Флаг того, что игра завершена (победа текущего игрока)
};

// ==========================================================================
//! Исключение, генеририруемое при ошибке восстановления данных
class SerRestoreException
{
public:
   enum errCode                  //!< Коды ошибок
   {
      ERR_CODE_NONE = -1,        //!< Неопределенная ошибка
      ERR_CODE_NOFILE,           //!< Файл не найден или не удалось открыть
      ERR_CODE_NOTENOUGHDATA,    //!< Файл испорчен (недостаточно данных)
      ERR_CODE_MOREDATA,         //!< Лишние данные в файле, возможно искажение
      ERR_CODE_NOTVALID          //!< Невалидные данные
   };

   SerRestoreException() : code(ERR_CODE_NONE) {};
   SerRestoreException(errCode cd) : code(cd) {};
   SerRestoreException(const SerRestoreException& e) : code(e.code) {};  

   errCode getCode() const
   {
      return code;
   }

private:
   errCode code;                 //!< Код ошибки
};

// ==========================================================================
//! Исключение, генеририруемое при ошибках в работе списка игроков
class PlayerListException
{
public:
   enum errCode                  //!< Коды ошибок
   {
      ERR_CODE_NONE = -1,        //!< Неопределенная ошибка
      ERR_CODE_NOMEMORY,         //!< Недостаточно памяти
      ERR_CODE_NAMECONFLICT,     //!< Игрок с таким именем уже существует
      ERR_CODE_MAXCOUNT          //!< Достигнуто максимальное число игроков
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
   errCode code;                 //!< Код ошибки
   std::string name;             //!< Ошибочное имя
};

} // endof namespace excptns
// ==========================================================================
#endif // __EXCEPTIONS_H