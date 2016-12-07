using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace SpamFilter {
	/// <summary>
	/// Interaction logic for InputTextWindow.xaml
	/// </summary>
	public partial class InputTextWindow : Window {

		public bool Result = false;
		public string FinalText = string.Empty;

		public InputTextWindow(string description, string startingTextValue) {
			InitializeComponent();

			lblDesc.Content = description;
			txtInput.Text = startingTextValue;
		}

		private void btnOk_Click(object sender, RoutedEventArgs e) {
			Result = true;
			FinalText = txtInput.Text;
			this.Close();
		}

		private void btnCancel_Click(object sender, RoutedEventArgs e) {
			Result = false;
			FinalText = string.Empty;
			this.Close();
		}
	}
}
