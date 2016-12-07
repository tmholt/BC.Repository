using OpenPop.Mime;
using OpenPop.Pop3;
using SpamFilter.Data;
using System;

using System.Diagnostics;
using System.Linq;

using System.Windows;


namespace SpamFilter {
	/// <summary>
	/// Interaction logic for EmailResponseyWindow.xaml
	/// </summary>
	public partial class EmailResponseWindow : Window {


		private MainWindow mParent;
		private MessageHeaderInfo mHeaderInfo;


		public EmailResponseWindow(MainWindow parent, MessageHeaderInfo hinfo, OpenPop.Mime.Message msg) {
			InitializeComponent();

			mParent = parent;
			mHeaderInfo = hinfo;

			var vm = new HeaderVM() {
				FromText = string.Format("{0} ({1})", hinfo.FromName, hinfo.FromAddress),
				ToText = hinfo.To,
				CcText = hinfo.Cc,
				SizeText = hinfo.Size / 1024 + " KB",
				SubjectText = hinfo.Subject
			};
			gridHeaders.DataContext = vm;

			MessagePart plainText = msg.FindFirstPlainTextVersion();
			if ( plainText != null ) {
				txtBody.Text = plainText.GetBodyAsText();
			}

			MessagePart htmlText = msg.FindFirstHtmlVersion();
			if ( htmlText != null ) {
				string html = htmlText.GetBodyAsText();
				webBody.NavigateToString(html);
				tbrbViewHtml.IsChecked = true;
			}
		}

		private void tbrbViewText_Checked(object sender, RoutedEventArgs e) {
			if ( txtBody == null || webBody == null ) return;
			txtBody.Visibility = System.Windows.Visibility.Visible;
			webBody.Visibility = System.Windows.Visibility.Hidden;
		}

		private void tbrbViewHtml_Checked(object sender, RoutedEventArgs e) {
			if ( txtBody == null || webBody == null ) return;
			txtBody.Visibility = System.Windows.Visibility.Hidden;
			webBody.Visibility = System.Windows.Visibility.Visible;
		}

		private void tbBtnDelete_Click(object sender, RoutedEventArgs e) {
			mParent.DeleteOneMessage(mHeaderInfo);
			this.Close();
		}

		private void tbBtnSend_Click(object sender, RoutedEventArgs e) {

		}


		private void webBody_LoadCompleted(object sender, System.Windows.Navigation.NavigationEventArgs e) {
			var doc = (mshtml.IHTMLDocument2)webBody.Document;
			foreach ( mshtml.IHTMLElement link in doc.links ) {
				mshtml.HTMLAnchorElement anchor = link as mshtml.HTMLAnchorElement;
				if ( anchor != null ) {
					mshtml.HTMLAnchorEvents_Event handler = anchor as mshtml.HTMLAnchorEvents_Event;
					if ( handler != null ) {
						handler.onclick += new mshtml.HTMLAnchorEvents_onclickEventHandler(delegate() {
							Process.Start(anchor.href);
							return true;
						});
					}
				}
			}
		}



	}
}
