// **************************************************************************
//! \file   Data.h
//! \brief  ���������� ������ ��� ������� ���������
//! \author Bessonov A.V.
//! \date   17.May.2008 - 17.May.2008
// **************************************************************************

#ifndef __DATA_H
#define __DATA_H

#include "petrinet.h"
#include "ifaces.h"

// ==========================================================================
namespace tdata //!< namespace Data
{
   class Data: private PetriNet<bool>,
               public iface::iDrawable,
               public iface::iSerializable,
               public iface::iNetworkStruct
   {
   public:
      Data();
      virtual ~Data();
      
      //********************* ��������� iNetworkStruct **********************
      virtual void generate (int newNumberOfHoles, int newNumberOfCell);
      virtual bool makeStep (int numberOfHole);	
      virtual void clean ();

      //********************* ��������� iDrawable ***************************
      virtual void SetHdc(HDC hDC);
      virtual void GetHdc(HDC hDC);
      virtual void Redraw() const;

      //********************* ��������� iSerializable ***********************
      virtual bool PutIntoFile(std::string& filename);
      virtual bool GetFromFile(std::string& filename);

   };

}// end of namespace tdata
// ==========================================================================
#endif // __DATA_H