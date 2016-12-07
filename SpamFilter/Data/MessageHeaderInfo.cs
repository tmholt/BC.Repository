using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

namespace SpamFilter.Data {
	public class MessageHeaderInfo {

		private FilterAction meAction = FilterAction.Undetermined;


		public int ID { get; set; }
		public string FromAddress { get; set; }
		public string FromName { get; set; }
		public string To { get; set; }
		public string Cc { get; set; }
		public string Subject { get; set; }
		public DateTime Date { get; set; }
		public bool IsValidAddress { get; set; }
		public int Size { get; set; }
		public string ImageUrl { get; set; }
		
		public FilterAction Action {
			get { return meAction;  }
			set {
				if ( meAction != value ) {
					meAction = value;
					SetImageUrlFromAction();
					// notify?
				}
			}
		}

		private void SetImageUrlFromAction() {
			switch ( meAction ) {
				case FilterAction.Delete:
					ImageUrl = "res\\item-bad.png";
					break;
				case FilterAction.Keep:
					ImageUrl = "res\\item-good.png";
					break;
				case FilterAction.Undetermined:
					ImageUrl = "res\\item-unk.png";
					break;
			}
		}

	}
}
