using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpamFilter.Data {

	/// <summary>
	/// Viewmodel for a message header
	/// </summary>
	internal class HeaderVM {
		public string FromText { get; set; }
		public string ToText { get; set; }
		public string CcText { get; set; }
		public string SubjectText { get; set; }
		public string SizeText { get; set; }
	}
}
