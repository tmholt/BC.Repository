using OpenPop.Pop3;
using SpamFilter.Data;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace SpamFilter {
	/// <summary>
	/// Interaction logic for MainWindow.xaml
	/// </summary>
	public partial class MainWindow : Window, INotifyPropertyChanged {

		private EmailAccount mActiveEmailAccount;
		private ObservableCollection<MessageHeaderInfo> maMessageHeaders;
		private FilterSet mFilterSet;
		private Pop3Client mClient;
		private int mnEmailCount;
		private string msStatusInfo;


		/// <summary>
		/// ctor
		/// </summary>
		public MainWindow() {
			InitializeComponent();
		}

		~MainWindow() {
			try {
				if ( mClient != null ) {
					mClient.Disconnect();
					mClient.Dispose();
				}
			}
			catch {
			}
		}

		#region public properties (bound)

		private IEnumerable<EmailAccount> EmailAccounts {
			get;
			set;
		}

		public EmailAccount ActiveEmailAccount {
			get { return mActiveEmailAccount;  }
			set {
				if ( mActiveEmailAccount != value ) {
					mActiveEmailAccount = value;
					tbBtnRefreshClick(this, null);
				}
			}
		}

		public int EmailCount {
			get { 
				return mnEmailCount; 
			}
			set {
				if ( mnEmailCount != value ) {
					mnEmailCount = value;
					Notify("EmailCount");
				}
			}
		}

		public string StatusInfo {
			get {
				return msStatusInfo;
			}
			set {
				if ( msStatusInfo != value ) {
					msStatusInfo = value;
					Notify("StatusInfo");
				}
			}
		}

		#endregion


		private async void Initialize() {

			EmailAccounts = new List<EmailAccount>(new [] {
				new EmailAccount() {
					AccountName = "BengalCore / tmholt",
					Server = "mail.bengalcore.com",
					Username = "tmholt@bengalcore.com",
					Password = "Squawk12!",
					Port = 110,
					UseSSL = false
				},
				new EmailAccount() {
					AccountName = "Plus-LS / thomas.holt",
					Server = "pop.gmail.com", // "mail.plus-ls.com"
					Username = "thomas.holt@plus-ls.com",
					Password = "quack12!",
					Port = 995,
					UseSSL = true
				}
			});

			mActiveEmailAccount = EmailAccounts.First();

			bool connected = await ConnectToEmailServer();
			if ( !connected ) {
				// ack!
				MessageBox.Show("Could not connect to email server!");
				return;
			}

			mFilterSet = FilterSet.Load();
			dgFilters.ItemsSource = mFilterSet.Criteria;
			tbcboAccounts.ItemsSource = EmailAccounts;
			this.DataContext = this;

			maMessageHeaders = new ObservableCollection<MessageHeaderInfo>();
			dgHeaders.ItemsSource = maMessageHeaders;

			/* test test test
			mFilterSet.Add(new FilterCriteria() {
				Action = FilterAction.Delete,
				Field = FilterField.SubjectContains,
				Text = "Rachael Ray"
			});
			mFilterSet.Add(new FilterCriteria() {
				Action = FilterAction.Keep,
				Field = FilterField.FromName,
				Text = "Christine Rather"
			});
			*/

			await GetMessagesAndEvaluate();
			dgHeaders.Rebind();
			StatusInfo = "Load complete";
		}



		private async Task<bool> ConnectToEmailServer() {
			
			if ( mActiveEmailAccount == null ) return false;

			var func = new Func<bool>(() => {
				try {
					mClient = new Pop3Client();
					mClient.Connect(mActiveEmailAccount.Server, mActiveEmailAccount.Port, mActiveEmailAccount.UseSSL);
					mClient.Authenticate(mActiveEmailAccount.Username, mActiveEmailAccount.Password);
					return true;
				}
				catch {
					return false;
				}
			});
			return await Task.Factory.StartNew<bool>(func);
		}

		private async Task GetMessagesAndEvaluate() {
			await GetMessagesInBackground();
			await mFilterSet.Evaluate(maMessageHeaders);
		}

		private async Task GetMessagesInBackground() {
			this.Cursor = Cursors.Wait;
			maMessageHeaders.Clear();

			await Task.Factory.StartNew(GetMessages);

			this.Cursor = Cursors.Arrow;
		}

		private void GetMessages() {

			int count = mClient.GetMessageCount();
			EmailCount = count;

			// note that GetMessage(i) is 1-based
			for ( int i = count-1; i >= 0; i-- ) {

				int index = i+1;

				var hinfo = new MessageHeaderInfo();
				hinfo.ID = index;
				hinfo.ImageUrl = "Res\\item-unk.png";

				OpenPop.Mime.Header.MessageHeader header = mClient.GetMessageHeaders(index);
				hinfo.Size = mClient.GetMessageSize(index);
				hinfo.Date = header.DateSent;

				// to
				var sb = new StringBuilder();
				if ( header.To != null && header.To.Count > 0 ) {
					foreach ( var toAddr in header.To ) {
						sb.AppendFormat("{0} ({1}), ", toAddr.DisplayName, toAddr.Address);
					}
					sb.Length = sb.Length - 2;
					hinfo.To = sb.ToString();
				}

				// cc
				sb = new StringBuilder();
				if ( header.Cc != null && header.Cc.Count > 0 ) {
					foreach ( var toAddr in header.Cc ) {
						sb.AppendFormat("{0} ({1}), ", toAddr.DisplayName, toAddr.Address);
					}
					sb.Length = sb.Length - 2;
					hinfo.Cc = sb.ToString();
				}


                if ( header.From == null )
                {
                    hinfo.FromAddress = "Unknown";
                    hinfo.FromName = "Unknown";
                    hinfo.IsValidAddress = false;
                }
                else
                {
                    hinfo.FromAddress = header.From.Address;
                    hinfo.FromName = header.From.DisplayName;
                    hinfo.IsValidAddress = header.From.HasValidMailAddress;
                }

				hinfo.Subject = header.Subject;

				maMessageHeaders.Add(hinfo);
				StatusInfo = "Loaded " + (count-i) + " of " + count;
			}
		}

		private string GetInputText(string description, string startingText) {
			var win = new InputTextWindow(description, startingText);
			win.Owner = this;
			win.ShowDialog();
			if ( win.Result ) {
				return win.FinalText;
			}
			return null;
		}

		private async void ReevaluateMessages() {
			this.Cursor = Cursors.Wait;

			// we re-evaluate to hit anything that now meets this criteria
			await mFilterSet.Evaluate(maMessageHeaders);

			dgHeaders.Rebind();
			this.Cursor = Cursors.Arrow;
		}

		private void DeleteFlaggedMessages() {
			// async?
			this.Cursor = Cursors.Wait;

			int listcount = 0;
			try {
				var list = maMessageHeaders.Where((hinfo) => hinfo.Action == FilterAction.Delete).ToList();
				listcount = list.Count();
				list.ForEach((hinfo) => {
					mClient.DeleteMessage(hinfo.ID);
					maMessageHeaders.Remove(hinfo);
				});
			}
			catch ( System.Exception ex ) {
				StatusInfo = "Error deleting! " + ex.Message;
				return;
			}
			finally {
				this.Cursor = Cursors.Arrow;
				EmailCount = maMessageHeaders.Count;
			}
			
			StatusInfo = listcount + " spam emails deleted from server";
		}

		public void DeleteOneMessage(MessageHeaderInfo hinfo) {
			mClient.DeleteMessage(hinfo.ID);
			maMessageHeaders.Remove(hinfo);
		}

		private void DeleteSelectedFilters() {
			var templist = dgFilters.SelectedItems.ToList();
			foreach ( FilterCriteria filter in templist ) {
				mFilterSet.Remove(filter);
			}

			ReevaluateMessages();
		}

		private void ShowMessageBody(MessageHeaderInfo hinfo) {
			try {
				var msg = mClient.GetMessage(hinfo.ID);
				var msgform = new EmailDisplayWindow(this, hinfo, msg);
				msgform.Owner = this;
				msgform.ShowDialog();
			}
			catch ( System.Exception ex ) {
				MessageBox.Show("Error getting email message from server.\r\n" + ex.Message);
			}

		}

		#region Control events

		private void Window_Loaded(object sender, RoutedEventArgs e) {
			Initialize();
		}

		// delete selected message now. does not set/change filters
		private void ctxtmnuDelete_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			// async?
			this.Cursor = Cursors.Wait;

			int listcount = 0;
			try {
				var list = dgHeaders.SelectedItems.ToList();
				listcount = list.Count();
				list.ForEach((hinfo) => {
					var hinfo2 = hinfo as MessageHeaderInfo;
					mClient.DeleteMessage(hinfo2.ID);
					maMessageHeaders.Remove(hinfo2);
				});
			}
			catch ( System.Exception ex ) {
				StatusInfo = "Error deleting! " + ex.Message;
				return;
			}
			finally {
				this.Cursor = Cursors.Arrow;
				EmailCount = maMessageHeaders.Count;
			}
			
			StatusInfo = listcount + " emails deleted from server";
		}

		private void tbBtnDeleteFlagged_Click(object sender, RoutedEventArgs e) {
			if ( dgHeaders.Visibility == System.Windows.Visibility.Visible ) {
				DeleteFlaggedMessages();
			}
			else {
				DeleteSelectedFilters();
			}
		}

		private async void tbBtnRefreshClick(object sender, RoutedEventArgs e) {

			// NOTE: this is a hard refresh from scratch. It might be better to use this
			// List<string> uids = mClient.GetMessageUids();
			// and then compare with the current set of UIDs.

			this.Cursor = Cursors.Wait;
			maMessageHeaders.Clear();

			try { 
				mClient.Disconnect();
				mClient.Dispose();
			}
			catch {
			}

			bool connected = await ConnectToEmailServer();
			if ( !connected ) {
				MessageBox.Show("Could not connect to email server!");
				this.Cursor = Cursors.Arrow;
				return;
			}

			this.Cursor = Cursors.Arrow;


			await GetMessagesAndEvaluate();
			dgHeaders.Rebind();
			StatusInfo = "Reload complete";
		}

		private void ctxtmnuFilterBySenderAddress_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {
				var filter = new FilterCriteria() {
					Action = FilterAction.Delete,
					Field = FilterField.FromAddress,
					Text = hinfo.FromAddress
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

		private void ctxtmnuFilterByDomain_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {

				string domain = FilterSet.GetDomainFromAddress(hinfo.FromAddress);
				if ( string.IsNullOrEmpty(domain) ) continue;

				var filter = new FilterCriteria() {
					Action = FilterAction.Delete,
					Field = FilterField.FromDomain,
					Text = domain
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

		private void ctxtmnuFilterBySenderName_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {
				var filter = new FilterCriteria() {
					Action = FilterAction.Delete,
					Field = FilterField.FromName,
					Text = hinfo.FromName
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

	    private void ctxtmnuFilterBySenderContaining_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
	        if ( !dgHeaders.SelectedItems.Any() ) return;

	        var hinfo = (MessageHeaderInfo)dgHeaders.SelectedItems.First();
	        string filtertext = GetInputText("Enter the text you would like to filter senders by.", hinfo.FromName);
	        if ( string.IsNullOrEmpty(filtertext) ) return;


	        var filter = new FilterCriteria() {
	            Action = FilterAction.Delete,
	            Field = FilterField.FromNameContains,
	            Text = filtertext
	        };
	        mFilterSet.Add(filter);

	        ReevaluateMessages();
	    }

        private void ctxtmnuFilterBySubjectContaining_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			var hinfo = (MessageHeaderInfo)dgHeaders.SelectedItems.First();
			string filtertext = GetInputText("Enter the text you would like to filter all subjects by.", hinfo.Subject);
			if ( string.IsNullOrEmpty(filtertext) ) return;


			var filter = new FilterCriteria() {
				Action = FilterAction.Delete,
				Field = FilterField.SubjectContains,
				Text = filtertext
			};
			mFilterSet.Add(filter);

			ReevaluateMessages();
		}

		private void ctxtmnuFilterBySubject_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {
				var filter = new FilterCriteria() {
					Action = FilterAction.Delete,
					Field = FilterField.Subject,
					Text = hinfo.Subject
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();

		}

		private void ctxtmnuKeepBySenderAddress_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {
				var filter = new FilterCriteria() {
					Action = FilterAction.Keep,
					Field = FilterField.FromAddress,
					Text = hinfo.FromAddress
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

		private void ctxtmnuKeepByDomain_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {

				string domain = FilterSet.GetDomainFromAddress(hinfo.FromAddress);
				if ( string.IsNullOrEmpty(domain) ) continue;

				var filter = new FilterCriteria() {
					Action = FilterAction.Keep,
					Field = FilterField.FromDomain,
					Text = domain
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

		private void ctxtmnuKeepBySenderName_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			foreach ( MessageHeaderInfo hinfo in dgHeaders.SelectedItems ) {
				var filter = new FilterCriteria() {
					Action = FilterAction.Keep,
					Field = FilterField.FromName,
					Text = hinfo.FromName
				};
				mFilterSet.Add(filter);
			}

			ReevaluateMessages();
		}

		private void ctxtmnuViewMatchingRules_Click(object sender, Telerik.Windows.RadRoutedEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;
			var hinfo = (MessageHeaderInfo)dgHeaders.SelectedItems.First();
			var matching = new List<FilterCriteria>();
			mFilterSet.Evaluate(hinfo, matching);
			var form = new ViewMatchingRulesWindow(matching);
			form.Owner = this;
			form.ShowDialog();
		}


		private void tbrbViewMessages_Checked(object sender, RoutedEventArgs e) {
			if ( dgFilters == null || dgHeaders == null ) return;

			dgFilters.Visibility = System.Windows.Visibility.Hidden;
			dgHeaders.Visibility = System.Windows.Visibility.Visible;
		}

		private void tbrbViewFilters_Checked(object sender, RoutedEventArgs e) {
			if ( dgFilters == null || dgHeaders == null ) return;

			dgFilters.Visibility = System.Windows.Visibility.Visible;
			dgHeaders.Visibility = System.Windows.Visibility.Hidden;
		}

		private void dgHeaders_MouseDoubleClick(object sender, MouseButtonEventArgs e) {
			if ( !dgHeaders.SelectedItems.Any() ) return;

			var hinfo = dgHeaders.SelectedItems.First() as MessageHeaderInfo;
			if ( hinfo == null ) return;

			ShowMessageBody(hinfo);
		}

		private void dgHeaders_KeyDown(object sender, KeyEventArgs e) {
			switch ( e.Key ) {
				case Key.D:
                case Key.Delete:
					ctxtmnuDelete_Click(dgHeaders, null);
					break;
				case Key.K:
					ctxtmnuKeepBySenderAddress_Click(dgHeaders, null);
					break;
				case Key.F:
					ctxtmnuFilterBySenderName_Click(dgHeaders, null);
					break;
                case Key.O:
                case Key.Space:
                    dgHeaders_MouseDoubleClick(dgHeaders, null);
                    break;


            }
		}

		private void dgHeaders_SelectionChanged(object sender, Telerik.Windows.Controls.SelectionChangeEventArgs e) {
			StatusInfo = dgHeaders.SelectedItems.Count + " items selected";
		}



		#endregion


		#region INotifyPropertyChanged

		public event PropertyChangedEventHandler PropertyChanged;

		private void Notify(string sProp) {
			if ( PropertyChanged != null ) {
				PropertyChanged(this, new PropertyChangedEventArgs(sProp));
			}
		}

		#endregion

	}
}
