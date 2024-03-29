// **************************************************************************
//! \file   ifaces.h
//! \brief  ���������� ������������ �����������
//! \author Lysenko A.A.
//! \date   14.May.2008 - 20.May.2008
// **************************************************************************

#ifndef __IFACES_H
#define __IFACES_H

#include "Archive.h"

// ==========================================================================
namespace iface   //!< ����� ����������, ����������� � ���������
{
   // =======================================================================
   //! ���������, ����������� ����������� ����������� ������
   class iDrawable
   {
   public:
      //! ������������
      virtual void Redraw() const = 0;

      //! ������� �����������
      virtual void Draw(CPaintDC& dc) = 0;

      virtual ~iDrawable() {};
   };

   // =======================================================================
   //! ��������, ����������� ����������� ������������ ������
   class iSerializable
   {
   public:
      //! �������� ������ � ����
      //! \return ������������� ������� ������� � ������
      virtual int PutIntoArchive(ser::Archive& archive) = 0;

      //! �������� ������ �� �����
      //! \param id - ������������� ������� ������� � ������
      virtual void GetFromArchive(ser::Archive& archive, int id) = 0;

      virtual ~iSerializable() {};

   };
   class iNetworkStruct 
   {
   public:

      //! ���������� ��������� �� ������ ID
      virtual void generate (int newIDofGame) = 0;

      //! ���������� ����� ���������� ��������� �� ������ �������� ����������
      virtual void generate (int newNumberOfHoles, int newNumberOfCell) = 0;

      //! ������ ����� � �����. ���� ������ ������ ������ ���������� false
      //! � ��������� true
      //! numberOfHole - ����� �����, � ������� �������� �����
      virtual bool makeStep (int numberOfHole) = 0;

      //! ���������, ������������� �� �������
      //! \param i - ������������� ��������� ������������ ������� ([0..5])
      virtual bool isPositionBlocked(int i) const = 0;

      //! �������� ��������� �� �����
      virtual void clear () = 0;

      virtual ~iNetworkStruct(){}
   };


} // end of namespace iface

// ==========================================================================
#endif // __IFACES_H