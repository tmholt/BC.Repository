﻿using System;
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

namespace PlaylistGenerator {
	/// <summary>
	/// Interaction logic for AuthForm.xaml
	/// </summary>
	public partial class AuthForm : Window {
		public AuthForm() {
			InitializeComponent();
			CefSharp.Cef.Initialize();
		}

		//public WebBrowser Browser {
		//	get { return webCtl; }
		//}
	}
}
