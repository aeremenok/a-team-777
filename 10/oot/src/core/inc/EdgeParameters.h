/* $Id: template.h,v 1.3 2006/01/13 09:00:29 dumb Exp $ */
/*!
 * \file EdgeParameters.h
 * \brief заголовок класса CEdgeParameters
 *
 * PROJ: oot
 *
 * ------------------------------------------------------------------------ */


#ifndef _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_
#define _CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

#include <vector>

// Serialization
#include <boost/serialization/nvp.hpp>
#include <boost/serialization/utility.hpp>
#include <boost/serialization/list.hpp>
#include <boost/serialization/vector.hpp>
#include <boost/serialization/string.hpp>
#include <boost/serialization/version.hpp>
#include <boost/serialization/split_member.hpp>
#include <boost/archive/tmpdir.hpp>
#include <boost/archive/xml_iarchive.hpp>
#include <boost/archive/xml_oarchive.hpp>
#include <boost/serialization/export.hpp>	// must be in the end of serializatrion headers list

#include "DefaultLink.h"
#include "AircraftLink.h"
#include "ShipLink.h"
#include "TrainLink.h"
#include "TruckLink.h"

/*!
 * \brief набор стоимостей доставки
 */
class CEdgeParameters
{

  std::vector<CDefaultLink *> m_links; // набор существующих ребер

  friend class boost::serialization::access;
  template<class Archive>
  void serialize(Archive & ar, const unsigned int version)
  {
    ar.register_type(static_cast<CStupidLink *>(NULL));
    ar.register_type(static_cast<CDefaultLink *>(NULL));
    ar.register_type(static_cast<CAircraftLink *>(NULL));
    ar.register_type(static_cast<CS7Link *>(NULL));
    ar.register_type(static_cast<CPulkovoLink *>(NULL));
    ar.register_type(static_cast<CAeroflotLink *>(NULL));
    ar.register_type(static_cast<CShipLink *>(NULL));
    ar.register_type(static_cast<CLimcoLink *>(NULL));
    ar.register_type(static_cast<CAlfaLink *>(NULL));
    ar.register_type(static_cast<CTrainLink *>(NULL));
    ar.register_type(static_cast<CRZDLink *>(NULL));
    ar.register_type(static_cast<CASDLink *>(NULL));
    ar.register_type(static_cast<CTruckLink *>(NULL));
    ar.register_type(static_cast<CCargoLink *>(NULL));
    ar.register_type(static_cast<CVasjaLink *>(NULL));
    ar & BOOST_SERIALIZATION_NVP(m_links);
  }
public:
  CEdgeParameters();

  bool isAvail(CDefaultLink::LinkType type) const;

  void addLink(CDefaultLink*);

  size_t linkCount() const;

  const CDefaultLink* getLink(CDefaultLink::LinkType type) const;

  const CDefaultLink* getLink(size_t index) const;
  CDefaultLink* getLink(size_t index);
  
  ~CEdgeParameters();
};//class CEdgeParameters

#endif //_CEdgeParameters_H_FAB24717_0B58_433C_8E8F_C5C07EB55AB3_INCLUDED_

/* ===[ End of file $Source: /cvs/decisions/templates/template.h,v $ ]=== */
