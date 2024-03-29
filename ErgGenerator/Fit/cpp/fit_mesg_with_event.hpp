////////////////////////////////////////////////////////////////////////////////
// The following FIT Protocol software provided may be used with FIT protocol
// devices only and remains the copyrighted property of Dynastream Innovations Inc.
// The software is being provided on an "as-is" basis and as an accommodation,
// and therefore all warranties, representations, or guarantees of any kind
// (whether express, implied or statutory) including, without limitation,
// warranties of merchantability, non-infringement, or fitness for a particular
// purpose, are specifically disclaimed.
//
// Copyright 2018 Dynastream Innovations Inc.
////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 20.58Release
// Tag = production/akw/20.58.10-0-g645e93c
////////////////////////////////////////////////////////////////////////////////


#if !defined(FIT_MESG_WITH_EVENT_HPP)
#define FIT_MESG_WITH_EVENT_HPP

#include "fit.hpp"
#include "fit_profile.hpp"

namespace fit
{

class MesgWithEvent
{
public:
    virtual ~MesgWithEvent() {}
    virtual FIT_DATE_TIME GetTimestamp(void) const = 0;
    virtual void SetTimestamp(FIT_DATE_TIME timestamp) = 0;
    virtual FIT_EVENT GetEvent() const = 0;
    virtual void SetEvent(FIT_EVENT event) = 0;
    virtual FIT_EVENT_TYPE GetEventType() const = 0;
    virtual void SetEventType(FIT_EVENT_TYPE type) = 0;
    virtual FIT_UINT8 GetEventGroup() const = 0;
    virtual void SetEventGroup(FIT_UINT8 group) = 0;
};

} // namespace fit

#endif // !defined(FIT_MESG_WITH_EVENT_HPP)
