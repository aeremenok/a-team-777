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

} // endof namespace excptns
// ==========================================================================
#endif // __EXCEPTIONS_H