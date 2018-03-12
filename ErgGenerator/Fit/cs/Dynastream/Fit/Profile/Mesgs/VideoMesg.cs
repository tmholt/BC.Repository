#region Copyright
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

#endregion

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.IO;
using System.Linq;

namespace Dynastream.Fit
{
    /// <summary>
    /// Implements the Video profile message.
    /// </summary>
    public class VideoMesg : Mesg
    {
        #region Fields
        #endregion

        /// <summary>
        /// Field Numbers for <see cref="VideoMesg"/>
        /// </summary>
        public sealed class FieldDefNum
        {
            public const byte Url = 0;
            public const byte HostingProvider = 1;
            public const byte Duration = 2;
            public const byte Invalid = Fit.FieldNumInvalid;
        }

        #region Constructors
        public VideoMesg() : base(Profile.GetMesg(MesgNum.Video))
        {
        }

        public VideoMesg(Mesg mesg) : base(mesg)
        {
        }
        #endregion // Constructors

        #region Methods
        ///<summary>
        /// Retrieves the Url field</summary>
        /// <returns>Returns byte[] representing the Url field</returns>
        public byte[] GetUrl()
        {
            byte[] data = (byte[])GetFieldValue(0, 0, Fit.SubfieldIndexMainField);
            return data.Take(data.Length - 1).ToArray();
        }

        ///<summary>
        /// Retrieves the Url field</summary>
        /// <returns>Returns String representing the Url field</returns>
        public String GetUrlAsString()
        {
            byte[] data = (byte[])GetFieldValue(0, 0, Fit.SubfieldIndexMainField);
            return data != null ? Encoding.UTF8.GetString(data, 0, data.Length - 1) : null;
        }

        ///<summary>
        /// Set Url field</summary>
        /// <param name="url_"> field value to be set</param>
        public void SetUrl(String url_)
        {
            byte[] data = Encoding.UTF8.GetBytes(url_);
            byte[] zdata = new byte[data.Length + 1];
            data.CopyTo(zdata, 0);
            SetFieldValue(0, 0, zdata, Fit.SubfieldIndexMainField);
        }

        
        /// <summary>
        /// Set Url field</summary>
        /// <param name="url_">field value to be set</param>
        public void SetUrl(byte[] url_)
        {
            SetFieldValue(0, 0, url_, Fit.SubfieldIndexMainField);
        }
        
        ///<summary>
        /// Retrieves the HostingProvider field</summary>
        /// <returns>Returns byte[] representing the HostingProvider field</returns>
        public byte[] GetHostingProvider()
        {
            byte[] data = (byte[])GetFieldValue(1, 0, Fit.SubfieldIndexMainField);
            return data.Take(data.Length - 1).ToArray();
        }

        ///<summary>
        /// Retrieves the HostingProvider field</summary>
        /// <returns>Returns String representing the HostingProvider field</returns>
        public String GetHostingProviderAsString()
        {
            byte[] data = (byte[])GetFieldValue(1, 0, Fit.SubfieldIndexMainField);
            return data != null ? Encoding.UTF8.GetString(data, 0, data.Length - 1) : null;
        }

        ///<summary>
        /// Set HostingProvider field</summary>
        /// <param name="hostingProvider_"> field value to be set</param>
        public void SetHostingProvider(String hostingProvider_)
        {
            byte[] data = Encoding.UTF8.GetBytes(hostingProvider_);
            byte[] zdata = new byte[data.Length + 1];
            data.CopyTo(zdata, 0);
            SetFieldValue(1, 0, zdata, Fit.SubfieldIndexMainField);
        }

        
        /// <summary>
        /// Set HostingProvider field</summary>
        /// <param name="hostingProvider_">field value to be set</param>
        public void SetHostingProvider(byte[] hostingProvider_)
        {
            SetFieldValue(1, 0, hostingProvider_, Fit.SubfieldIndexMainField);
        }
        
        ///<summary>
        /// Retrieves the Duration field
        /// Units: ms
        /// Comment: Playback time of video</summary>
        /// <returns>Returns nullable uint representing the Duration field</returns>
        public uint? GetDuration()
        {
            Object val = GetFieldValue(2, 0, Fit.SubfieldIndexMainField);
            if(val == null)
            {
                return null;
            }

            return (Convert.ToUInt32(val));
            
        }

        /// <summary>
        /// Set Duration field
        /// Units: ms
        /// Comment: Playback time of video</summary>
        /// <param name="duration_">Nullable field value to be set</param>
        public void SetDuration(uint? duration_)
        {
            SetFieldValue(2, 0, duration_, Fit.SubfieldIndexMainField);
        }
        
        #endregion // Methods
    } // Class
} // namespace