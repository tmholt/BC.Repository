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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ErgGenerator
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        private Intervals maIntervals;
        private PowerZones maZones;

        public MainWindow()
        {
            InitializeComponent();

            maZones = new PowerZones();
            maIntervals = new Intervals(maZones);

            this.DataContext = maZones;
            dgIntervals.ItemsSource = maIntervals;
            dgZones.ItemsSource = maZones;

        }

        private void mnuAddRow_Click(object sender, Telerik.Windows.RadRoutedEventArgs e)
        {
            maIntervals.Add();
        }

        private void mnuRemoveRow_Click(object sender, Telerik.Windows.RadRoutedEventArgs e)
        {

        }

        private void mnuMoveRowUp_Click(object sender, Telerik.Windows.RadRoutedEventArgs e)
        {

        }

        private void mnuMoveRowDown_Click(object sender, Telerik.Windows.RadRoutedEventArgs e)
        {

        }
    }
}
