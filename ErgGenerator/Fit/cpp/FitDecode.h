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


#if !defined(FIT_DECODE_H)
#define FIT_DECODE_H

#include "fit_profile.hpp"
#include "fit_mesg_listener.hpp"
#include "fit_mesg_definition_listener.hpp"

typedef enum
{
    STATE_FILE_HDR,
    STATE_RECORD,
    STATE_RESERVED1,
    STATE_ARCH,
    STATE_MESG_NUM_0,
    STATE_MESG_NUM_1,
    STATE_NUM_FIELDS,
    STATE_FIELD_NUM,
    STATE_FIELD_SIZE,
    STATE_FIELD_TYPE,
    STATE_NUM_DEV_FIELDS,
    STATE_DEV_FIELD_NUM,
    STATE_DEV_FIELD_SIZE,
    STATE_DEV_FIELD_INDEX,
    STATE_FIELD_DATA,
    STATE_DEV_FIELD_DATA,
    STATE_FILE_CRC_HIGH,
    STATES
} STATE;

typedef enum
{
    RETURN_CONTINUE,
    RETURN_MESG,
    RETURN_MESG_DEF,
    RETURN_END_OF_FILE,
    RETURN_ERROR,
    RETURNS
} RETURN;

@interface FitDecode : NSObject
- (void)InitRead:(FILE *) file;
- (void)InitRead:(FILE *) file startOfFile:(FIT_BOOL) start;
- (FIT_BOOL)IsFit:(FILE *) file;
- (FIT_BOOL)CheckIntegrity:(FILE *) file;
- (void) SkipHeader;
- (void) IncompleteStream;
- (void) Pause;
- (FIT_BOOL) Resume;
- (FIT_BOOL) GetInvalidDataSize;
- (void) SetInvalidDataSize:(FIT_BOOL) value;
- (FIT_BOOL) Read:(FILE *)file withMesgListener:(fit::MesgListener *) listener;
- (FIT_BOOL) Read:(FILE *)file withListener:(fit::MesgListener *) listener andDefListener:(fit::MesgDefinitionListener *) defListener;
- (RETURN) ReadByte:(FIT_UINT8) data;
- (void) ExpandComponents:(fit::Field *) containingField fromComponents:(const fit::Profile::FIELD_COMPONENT*) components numComponents: (FIT_UINT16) numComponents;
@end

#endif /* FIT_DECODE_H */
