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


#if !defined(FIT_VIDEO_TITLE_MESG_LISTENER_HPP)
#define FIT_VIDEO_TITLE_MESG_LISTENER_HPP

#include "fit_video_title_mesg.hpp"

namespace fit
{

class VideoTitleMesgListener
{
public:
    virtual ~VideoTitleMesgListener() {}
    virtual void OnMesg(VideoTitleMesg& mesg) = 0;
};

} // namespace fit

#endif // !defined(FIT_VIDEO_TITLE_MESG_LISTENER_HPP)
