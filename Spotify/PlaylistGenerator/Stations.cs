using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace PlaylistGenerator {

	public enum StationPlaylistFormat {
		TuneGenie,
		IHeartRadio
	}

	public class StationInfoCollection : ObservableCollection<StationInfo> {

		private int mnSelecedIndex = 0;

		public int SelectedIndex {
			get { return mnSelecedIndex;  }
			set { mnSelecedIndex = value; }
		}

		public StationInfo Selected {
			get { return this[mnSelecedIndex];  }
		}

		#region Serialization / Deserialization

		const string FILE_PATH = "settings.txt";

		/// <summary>
		/// Save our settings infor to isolated storage.
		/// </summary>
		public bool Serialize(out string results) {
			results = string.Empty;

			try {
				var store = IsolatedStorageFile.GetStore(
					IsolatedStorageScope.User|IsolatedStorageScope.Assembly|IsolatedStorageScope.Domain,
					null, null);
				using ( var strm = new IsolatedStorageFileStream(FILE_PATH, FileMode.Create, store) ) {
					var szr = new DataContractSerializer(typeof(StationInfoCollection));
					szr.WriteObject(strm, this);
				}
				store.Dispose();
				return true;
			}
			catch ( System.Exception ex ) {
				results = "Error saving to iso: " + ex.Message;
				return false;
			}
		}

		// used when changed *once*
		public static void RemoveStorage() {
			var store = IsolatedStorageFile.GetStore(
				IsolatedStorageScope.User|IsolatedStorageScope.Assembly|IsolatedStorageScope.Domain,
				null, null);
			store.Remove();
		}

		public static StationInfoCollection Deserialize(out string results) {
			StationInfoCollection stations;
			results = string.Empty;

			try {
				var store = IsolatedStorageFile.GetStore(
					IsolatedStorageScope.User|IsolatedStorageScope.Assembly|IsolatedStorageScope.Domain,
					null, null);
				try {
					using ( var strm = new IsolatedStorageFileStream(FILE_PATH, FileMode.Open, store) ) {
						var szr = new DataContractSerializer(typeof(StationInfoCollection));
						stations = szr.ReadObject(strm) as StationInfoCollection;
						results = "Settings loaded successfully";
					}
				}
				catch ( System.IO.FileNotFoundException ) {
					// file does not exist
					results = "No existing stations file";
					stations = new StationInfoCollection();
				}
				store.Dispose();
			}
			catch ( System.Exception ex ) {
				results = ex.Message;
				stations = new StationInfoCollection();
			}

			return stations;
		}

		#endregion

	}

	[DataContract]
	public class StationInfo {

		[DataMember]
		public string Name { get; set; }

		[DataMember]
		public string OnlinePlaylistUrl { get; set; }

		[DataMember]
		public string SpotifyPlaylistName { get; set; }

		[DataMember]
		public StationPlaylistFormat Format { get; set; }

		[DataMember]
		public string PlaylistLink { get; set; }

		[DataMember]
		public string SpotifyURI { get; set; }

	}
}
