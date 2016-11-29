using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PlaylistGenerator {
	
	public class PlaylistItem {

		public DateTime Added {
			get; set;
		}

		public string Artist {
			get; set;
		}

		public string Track {
			get;
			set;
		}

		public string ImageUrl {
			get;
			set;
		}

		public float Tempo {
			get;
			set;
		}


		public string SpotifyRequestUrl {
			get;
			set;
		}

		public string SpotifyPlaylistEntry {
			get;
			set;
		}

		public Spotify.TrackFull SpotifyTrack {
			get;
			set;
		}

		public bool GotTrackInfo {
			get;
			set;
		}

	}
}
