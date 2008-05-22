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

} // endof namespace excptns
// ==========================================================================
#endif // __EXCEPTIONS_H