// **************************************************************************
//! \file   Data.h
//! \brief  ќбъ€вление класса дл€ игровой структуры
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
      virtual ~Data() {};
      
      //********************* интерфейс iNetworkStruct **********************
      virtual void generate (int newIDofGame);
      virtual void generate (int newNumberOfHoles, int newNumberOfCell);
      virtual bool makeStep (int numberOfHole);	
      virtual void clean ();

      //********************* интерфейс iDrawable ***************************
      virtual void SetHdc(HDC hDC);
      virtual void GetHdc(HDC hDC);
      virtual void Redraw() const;

      //********************* интерфейс iSerializable ***********************
      virtual int PutIntoArchive(ser::Archive& archive);
      virtual void GetFromArchive(ser::Archive& archive, int id);

   };

}// end of namespace tdata
// ==========================================================================
#endif // __DATA_H