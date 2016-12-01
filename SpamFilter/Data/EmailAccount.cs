﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace SpamFilter.Data {
	
	[DataContract]
	public class EmailAccount {

		/// <summary>
		/// Descriptive text
		/// </summary>
		public string AccountName { get; set; }

		public string Username { get; set; }
		public string Password { get; set; }
		public string Server { get; set; }
		public int Port { get; set; }
		public bool UseSSL { get; set; }

	}
}
