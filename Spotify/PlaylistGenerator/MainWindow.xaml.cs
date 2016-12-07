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

namespace PlaylistGenerator {
	/// <summary>
	/// Interaction logic for MainWindow.xaml
	/// </summary>
	public partial class MainWindow : Window {

		private List<PlaylistItem> maWebPlaylist;
		private List<PlaylistItem> maExisting;
		private StationInfoCollection maStations;

		public MainWindow() {
			InitializeComponent();
		}


		private void Window_Loaded(object sender, RoutedEventArgs e) {
			maStations = new StationInfoCollection();
			maStations.Add(new StationInfo() {
				Name = "Lightning 100 Nashville",
				OnlinePlaylistUrl = "http://wrlt.tunegenie.com/",
				SpotifyPlaylistName = "LIGHTNING"
			});
			maStations.Add(new StationInfo() {
				Name = "107.7 The End Seattle",
				OnlinePlaylistUrl = "http://kndd.tunegenie.com/",
				SpotifyPlaylistName = "END1077"
			});
			maStations.Add(new StationInfo() {
				Name = "92.5 The River Boston",
				OnlinePlaylistUrl = "http://wxrv.tunegenie.com/",
				SpotifyPlaylistName = "RIVER925"
			});
			maStations.Add(new StationInfo() {
				Name = "Kink 101.9 Portland",
				OnlinePlaylistUrl = "http://kink.tunegenie.com/",
				SpotifyPlaylistName = "KINK101"
			});
			maStations.Add(new StationInfo() {
				Name = "WTTS 92.3 Indianapolis",
				OnlinePlaylistUrl = "http://wtts.tunegenie.com/",
				SpotifyPlaylistName = "WTTS923"
			});
			maStations.Add(new StationInfo() {
				Name = "WWCT 99.9 Peoria",
				OnlinePlaylistUrl = "http://wwct.tunegenie.com/",
				SpotifyPlaylistName = "WWCT999"
			});
			maStations.Add(new StationInfo() {
				Name = "91X Sandiego",
				OnlinePlaylistUrl = "http://xetra.tunegenie.com/",
				SpotifyPlaylistName = "91X-SD"
			});
			maStations.Add(new StationInfo() {
				Name = "WROX 96X Norfolk",
				OnlinePlaylistUrl = "http://wrox.tunegenie.com/",
				SpotifyPlaylistName = "WROX96X"
			});
			maStations.Add(new StationInfo() {
				Name = "KATT 100 OK",
				OnlinePlaylistUrl = "http://katt.tunegenie.com/",
				SpotifyPlaylistName = "KATT100"
			});
			maStations.Add(new StationInfo() {
				Name = "Z-ROCK 1067 CA",
				OnlinePlaylistUrl = "http://listen.zrockfm.com/",
				SpotifyPlaylistName = "ZROCK106"
			});
			maStations.Add(new StationInfo() {
				Name = "KLOO 106 Classic Rock Oregon",
				OnlinePlaylistUrl = "http://kloo.tunegenie.com/",
				SpotifyPlaylistName = "KLOO106"
			});		
			maStations.Add(new StationInfo() {
				Name = "Radio 105.7 Atlanta",
				OnlinePlaylistUrl = "http://www.radio1057.com/music/playlist/",
				SpotifyPlaylistName = "RADIO1057",
				Format = StationPlaylistFormat.IHeartRadio
			});
			maStations.Add(new StationInfo() {
				Name = "Philly 104.5 Alternative",
				OnlinePlaylistUrl = "http://www.radio1045.com/playlist/",
				SpotifyPlaylistName = "PHILLY1045",
				Format = StationPlaylistFormat.IHeartRadio
			});

			maStations.Add(new StationInfo() {
				Name = "Rolling Stones - Greatest Hits",
				PlaylistLink = "https://open.spotify.com/user/128103936/playlist/0Y0Q2zBIHq8Ood3XSLCv5D",
				SpotifyURI = "spotify:user:128103936:playlist:0Y0Q2zBIHq8Ood3XSLCv5D"
			});
			maStations.Add(new StationInfo() {
				Name = "Hard Rock Covers",
				PlaylistLink = "https://open.spotify.com/user/gash73/playlist/4XSB9v2mJOTbKgAeK5AELc",
				SpotifyURI = "spotify:user:gash73:playlist:4XSB9v2mJOTbKgAeK5AELc"

			});
			maStations.Add(new StationInfo() {
				Name = "Spotify Sessions",
				PlaylistLink = "https://open.spotify.com/user/spotifysessions/playlist/69enGygSne9ziF6PxQEWHg",
				SpotifyURI = "spotify:user:spotifysessions:playlist:69enGygSne9ziF6PxQEWHg"
			});
			maStations.Add(new StationInfo() {
				Name = "dope",
				PlaylistLink = "https://open.spotify.com/user/1213741851/playlist/1XpidGJptJpk2TAOes5pvG",
				SpotifyURI = "spotify:user:1213741851:playlist:1XpidGJptJpk2TAOes5pvG"
			});
			maStations.Add(new StationInfo() {
				Name = "Left of my dial",
				PlaylistLink = "https://open.spotify.com/user/corvar/playlist/3GxB0byPkz502cBFpa86kG",
				SpotifyURI = "spotify:user:corvar:playlist:3GxB0byPkz502cBFpa86kG"
			});


			cboStations.ItemsSource = maStations;

		}


		private void cboStations_SelectionChanged(object sender, SelectionChangedEventArgs e) {

			SetStatus("Station selection change");

			dgPlaylist.ItemsSource = null;
			dgExisting.ItemsSource = null;


			if ( cboStations.SelectedIndex < 0 || cboStations.SelectedIndex >= maStations.Count ) {
				//??
				MessageBox.Show("WTF?");
				return;
			}

			maStations.SelectedIndex = cboStations.SelectedIndex;
		}

		//private void tbBtnGetToken_Click(object sender, RoutedEventArgs e) {
		//	// actually gets access code (not token)
		//	var lookup = new SpotifyLookup();
		//	string token = lookup.GetAuthorizationToken();
		//}

		//private void tbBtnCode2Token_Click(object sender, RoutedEventArgs e) {
		//	string error;
		//	var lookup = new SpotifyLookup();
		//	bool bgot = lookup.ConvertAuthCodeToToken(out error);
		//}

		private void tbBtnExisting_Click(object sender, RoutedEventArgs e) {
			var lookup = new SpotifyLookup();
			string error;

			var user = lookup.GetUserId(out error);
			if ( user == null ) {
				SetStatus("Error in user lookup: " + error);
				return;
			}

			this.Cursor = Cursors.Wait;

			var spotifyPlaylist = lookup.GetPlaylist(user.Id, maStations.Selected.SpotifyPlaylistName, true, out error);
			if ( spotifyPlaylist == null ) {
				SetStatus("Error in looking up playlist " + error);
				this.Cursor = Cursors.Arrow;
				return;
			}

			maExisting = new List<PlaylistItem>();
			foreach ( var track in spotifyPlaylist.Tracks ) {
				var item = new PlaylistItem() {
					Track = track.Name,
					Artist = track.Artists.First().Name,
					GotTrackInfo = true,
					SpotifyPlaylistEntry = track.SpotifyTrackUri,
					SpotifyTrack = track
				};
				maExisting.Add(item);
			}

			dgExisting.ItemsSource = maExisting;
			SetStatus("Get existing complete: " + spotifyPlaylist.Tracks.Length + " tracks");
			this.Cursor = Cursors.Arrow;
		}

		private async void tbBtnGetLightningPlaylist_Click(object sender, RoutedEventArgs e) {
			//GetLightningPlaylist();
			this.Cursor = Cursors.Wait;
			await StartGettingAndCompare();
			this.Cursor = Cursors.Arrow;
		}

		private async void tbBtnFindTempo_Click(object sender, RoutedEventArgs e) {
			this.Cursor = Cursors.Wait;
			var matching = await GetMatchingSongsByTempo(int.Parse(txtTempo.Text));
			dgExisting.ItemsSource = matching;
			SetStatus("Get tempo matching complete: " + matching.Count + " tracks");

			this.Cursor = Cursors.Arrow;
		}



		private void tbBtnBuild_Click(object sender, RoutedEventArgs e) {
			this.Cursor = Cursors.Wait;
			AddPlaylistTracksToSpotify();
			this.Cursor = Cursors.Arrow;
		}

		private void SetStatus(string s) {
			lblStatus.Content = s;
		}

		private void AddPlaylistTracksToSpotify() {
			var lookup = new SpotifyLookup();
			string error;

			var user = lookup.GetUserId(out error);
			if ( user == null ) {
				SetStatus("Error in user lookup: " + error);
				return;
			}

			var spotifyPlaylist = lookup.GetPlaylist(user.Id, maStations.Selected.SpotifyPlaylistName, false, out error);
			if ( spotifyPlaylist == null ) {
				SetStatus("Error in looking up playlist " + error);
				return;
			}

			int addedCount;
			bool added = lookup.AddTracksToPlaylist(user.Id, spotifyPlaylist, maWebPlaylist, out addedCount, out error);

			if ( added ) {
				SetStatus("Add to playlist complete: Added " + addedCount + " tracks");
			}
			else {
				SetStatus("Add to playlist failed: " + error);
			}
		}

		private Task<List<PlaylistItem>> GetMatchingSongsByTempo(float tempo) {

			return Task.Factory.StartNew<List<PlaylistItem>>(() => {

				if ( maExisting == null || maExisting.Count == 0 ) return null;
				var lookup = new SpotifyLookup();
				string error;

				const int MAX_CALL_COUNT = 100;
				int callcount = 0;
				var sb = new StringBuilder();
				var audioList = new List<Spotify.AudioFeatures>();

				foreach ( PlaylistItem item in maExisting ) {
					sb.Append(item.SpotifyTrack.Id);
					callcount++;

					if ( callcount >= MAX_CALL_COUNT ) {
						var audio = lookup.GetAudioFeaturesForTracks(sb.ToString(), out error);
						audioList.AddRange(audio.Items);
						sb.Clear();
						callcount = 0;
					}
					else {
						sb.Append(",");
					}
				}

				// pick up remaining
				if ( callcount > 0 ) {
					sb.Remove(sb.Length-1, 1); // trailing comma
					var audio = lookup.GetAudioFeaturesForTracks(sb.ToString(), out error);
					audioList.AddRange(audio.Items);
				}

				// now match and add tempo value
				foreach ( PlaylistItem item in maExisting ) {
					var matching = audioList.FirstOrDefault(audio => audio.Id == item.SpotifyTrack.Id);
					if ( matching != null ) {
						item.Tempo = matching.Tempo;
					}
				}

				// finally find tempo within 3 beats
				return maExisting.Where(item => 
					(item.Tempo > (tempo-3) && item.Tempo < (tempo+3))).ToList();
			});

		}

		/// <summary>
		/// Run as a task
		/// </summary>
		private async Task StartGettingAndCompare() {

			maWebPlaylist = await GetPlaylistFromWeb();
			if ( maWebPlaylist == null ) {
				MessageBox.Show("Got not records from web playlist");
				return;
			}

			int originalCount = maWebPlaylist.Count;
			int gotCount = maWebPlaylist.Count(item => item.GotTrackInfo == true);

			maWebPlaylist.RemoveAll(item => item.GotTrackInfo == false);
			int remainingCount = maWebPlaylist.Count;

			for ( int i = remainingCount-1; i >= 0; i-- ) {
				var item = maWebPlaylist[i];
				var existing = maExisting.FirstOrDefault((current) => current.SpotifyTrack.SpotifyTrackUri == item.SpotifyTrack.SpotifyTrackUri);
				if ( existing != null ) {
					maWebPlaylist.RemoveAt(i);
				}
				else {
					// doublecheck - i'm getting duplicates for some reason
					string artist = item.Artist;
					string song = item.Track;

					var existing2 = maExisting.FirstOrDefault((current) => 
						current.Track == song && current.Artist == artist);

					if ( existing2 != null ) {
						// WTF??
						Console.WriteLine("Matching for {0}/{1}!", artist, song);
					}

				}
			}

			int uniqueCount = maWebPlaylist.Count;

			SetStatus(string.Format("Pulled {0} tracks, matched {1} tracks, removed {2}, found {3} unique",
				originalCount, gotCount, remainingCount-uniqueCount, uniqueCount));
			dgPlaylist.ItemsSource = maWebPlaylist;


		}


		private async void GetLightningPlaylist() {
			this.Cursor = Cursors.Wait;
			maWebPlaylist = await GetPlaylistFromWeb();
			this.Cursor = Cursors.Arrow;

			int gotCount = maWebPlaylist.Count(item => item.GotTrackInfo == true);
			SetStatus("Got " + gotCount + " of " + maWebPlaylist.Count + " tracks");
			dgPlaylist.ItemsSource = maWebPlaylist;
		}

		private Task<List<PlaylistItem>> GetPlaylistFromWeb() {
			return Task.Factory.StartNew<List<PlaylistItem>>(() => {
				var scraper = new PlaylistScraper();
				var lookup = new SpotifyLookup();

				string error;
				var playlist = scraper.Scrape(maStations.Selected.Format, maStations.Selected.OnlinePlaylistUrl);
				if ( playlist != null ) {
          foreach ( var item in playlist ) {
  					bool found = lookup.LookupTrack(item, out error);
  					// if found
  				}
        }
				return playlist;
			});
		}

	}
}
