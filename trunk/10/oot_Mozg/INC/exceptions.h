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

} // endof namespace excptns
// ==========================================================================
#endif // __EXCEPTIONS_H