using SpamFilter.Data;
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
	/// Interaction logic for ViewMatchingRulesWindow.xaml
	/// </summary>
	public partial class ViewMatchingRulesWindow : Window {
		public ViewMatchingRulesWindow(IEnumerable<FilterCriteria> filters) {
			InitializeComponent();
			dgFilters.ItemsSource = filters;
		}
	}
}
