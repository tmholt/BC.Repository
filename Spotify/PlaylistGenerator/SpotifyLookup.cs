using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Security;
using System.Net.Sockets;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Windows.Navigation;

namespace PlaylistGenerator {
	public class SpotifyLookup {

		const string APP_NAME = "BC.Spotify.PlaylistGenerator";
		const string SPOTIFY_CLIENT_ID = "9dbd74f7853f499dba524dbaaef5b20d";
		const string SPOTIFY_CLIENT_SECRET = "b9bd98dabfb141b1b0878ef1b3223592";
		const string REDIRECT_URI = "http://bengalcore.com";
		const string STATE_COOKIE = "suckit";

		// go here to generate a token
		// http://lab.possan.se/playlistcreator-example/

		//const string APP_AUTH_CODE = "AQB3_lcENlV1FuIAv-z-SQUIgZq-BklcqlZwQL1Dn03p4fSu4GEx8QA8ZWrZ3ZNXUvuihcPO1A7Zp2BG1AzPcGXAQaCxdNL87YirPZH-oW5qoC3OztJP9_UZrP1W37Zad3I6RWos90ACxg_gfc96crGLGP0V7wldEJccmfBqq9aMo66R4mPmyh-FTICzYSuRpKxCaqjiH-6KM-FqTKmwoBjPFA";
		
		const string STATIC_TOKEN = "BQDyOuOiof79u0O47nnMbvvdGZcADcitWzNeVds-oN6zydd-EVoFTR3o5fn6zSP51RCcz4WVACWt_fzg-aqU2__MwrkTs4bcUZeEsRU2mFA7XAFr3aNpzzszIR7m_WYBTn0HxMEgstBpLA0OKZvI06Akkg3nGicTVdKa39Vg7w5bWSE7CO7TB80psEaO-f_XWDVd57g2i3Efhv8LzWrSATU";

        private Spotify.TokenReposnse mToken = null;

#if false
		public string GetAuthorizationToken() {
			const string BASE = @"https://accounts.spotify.com/authorize?client_id={0}&response_type=token&show_dialog=true&scope=playlist-modify-public&redirect_uri={1}&state={2}";
			string url = string.Format(BASE, SPOTIFY_CLIENT_ID, 
				HttpUtility.UrlEncode(REDIRECT_URI), STATE_COOKIE);

			var authForm = new AuthForm();
			
			//authForm.Browser.Navigate(url);
			//authForm.Browser.Navigated += (object sender, NavigationEventArgs e) => {
			//	Console.WriteLine(e.Uri.ToString());
			//};


			authForm.ShowDialog();

			/*
			var request = WebRequest.Create(url) as HttpWebRequest;
			// don't need a token to get a token

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						return null;
					}
					// pull token from reponse (I hope)
					using ( StreamReader rs = new StreamReader(response.GetResponseStream()) ) {
						string s = rs.ReadToEnd();
					}
				}
			}
			catch {
			}
			 */
			return null;

		}
#endif

		private string GetToken(out string error) {
			error = null;
			return STATIC_TOKEN;

			if ( mToken == null ) {
				bool success = SetAccessToken(out error);
				if ( !success || mToken == null ) {
					return null;
				}
			}
			return mToken.AccessToken;
		}

		/// <summary>
		/// Bummer. This works, but logs into a program, not into a user account.
		/// So, this cannot lookup user info, like playlists.
		/// </summary>
		/// <param name="error"></param>
		/// <returns></returns>
		public bool SetAccessToken(out string error) {
			error = null;

			const string BASE = @"https://accounts.spotify.com/api/token";
			var request = WebRequest.Create(BASE) as HttpWebRequest;
			request.Method = "POST";
			request.Accept = "application/json";
			request.ContentType = "application/x-www-form-urlencoded";
			request.ServicePoint.Expect100Continue = false;

			// generate header
			string authHeader = string.Format("{0}:{1}", SPOTIFY_CLIENT_ID, SPOTIFY_CLIENT_SECRET);
			byte[] xaAuthHeader = Encoding.ASCII.GetBytes(authHeader);
			string authHeaderEncoded = Convert.ToBase64String(xaAuthHeader);
			request.Headers.Add(HttpRequestHeader.Authorization, "Basic  " + authHeaderEncoded);

			// generate POST body data
			string body = "grant_type=client_credentials";
			//string body = string.Format("grant_type=authorization_code&code={0}&redirect_uri={1}",
			//	APP_AUTH_CODE,
			//	HttpUtility.UrlEncode(REDIRECT_URI));

			byte[] xaData = Encoding.ASCII.GetBytes(body);

			request.ContentLength = xaData.Length;
			using ( var stream = request.GetRequestStream() ) {
				stream.Write(xaData, 0, xaData.Length);
				stream.Close();
			}

			// ship it to the server
			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "Bad status returned: " + response.StatusCode;
						return false;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.TokenReposnse));
					var result = szr.ReadObject(response.GetResponseStream());
					mToken = result as Spotify.TokenReposnse;
					return true;
				}
			}
			catch ( System.Exception ex ) {
				error = "ERROR: " + ex.Message;
			}
			return false;

		}



		public Spotify.UserPrivate GetUserId(out string error) {
			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return null;

			const string BASE = @"https://api.spotify.com/v1/me";
			var request = WebRequest.Create(BASE) as HttpWebRequest;
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "Bad status returned: " + response.StatusCode;
						return null;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.UserPrivate));
					var result = szr.ReadObject(response.GetResponseStream());
					return result as Spotify.UserPrivate;
				}
			}
			catch ( System.Exception ex ) {
				error = "ERROR: " + ex.Message;
			}
			return null;
		}

		public Spotify.PlaylistSimple GetPlaylistByName(string userId, 
			string playlistName, 
			bool getTracks, 
			out string error) {

			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return null;


			const string BASE = @"https://api.spotify.com/v1/users/{0}/playlists";
			string url = string.Format(BASE, userId);
			var request = WebRequest.Create(url) as HttpWebRequest;
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "Bad status returned: " + response.StatusCode;
						return null;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.PlaylistsList));
					var result = szr.ReadObject(response.GetResponseStream());
					var playlists = result as Spotify.PlaylistsList;

					foreach ( var playlist in playlists.Items ) {
						if ( playlist.Name == playlistName ) {

							if ( getTracks ) {
								GetTracksForPlaylist(userId, playlist, out error);
							}

							return playlist;
						}
					}
				}
			}
			catch ( System.Exception ex ) {
				error = "GetPlaylist ERROR: " + ex.Message;
			}
			return null;
		}

		private int GetPlaylistTrackCount(string userId, Spotify.PlaylistSimple playlist, out string error) {
			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return 0;

			const string BASE = @"https://api.spotify.com/v1/users/{0}/playlists/{1}/tracks?fields=total,limit";
			string url = string.Format(BASE, userId, playlist.Id);
			var request = WebRequest.Create(url) as HttpWebRequest;
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "AddTracksToPlaylist: Bad status returned: " + response.StatusCode;
						return 0;
					}
					var reader = new StreamReader(response.GetResponseStream());
					string s = reader.ReadToEnd();
					reader.Dispose();

					int ndx1 = s.IndexOf("total");
					int ndx2 = s.LastIndexOf("}");
					string stotal = s.Substring(ndx1+9, ndx2-ndx1-10);

					return int.Parse(stotal);
				}
			}
			catch ( System.Exception ex ) {
				error = "GetPlaylistTrackCount ERROR: " + ex.Message;
			}
			return 0;
		}

		/// <summary>
		/// NOTE: max 100 IDs at once
		/// </summary>
		public Spotify.AudioFeaturesList GetAudioFeaturesForTracks(string trackIds, out string error) {
			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return null;

			const string BASE = @"https://api.spotify.com/v1/audio-features?ids={0}";
			string url = string.Format(BASE, trackIds);

			var request = WebRequest.Create(url) as HttpWebRequest;
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "GetAudioFeaturesForTracks: Bad status returned: " + response.StatusCode;
						return null;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.AudioFeaturesList));
					var result = szr.ReadObject(response.GetResponseStream());
					var audiolist = result as Spotify.AudioFeaturesList;
					return audiolist;
				}
			}
			catch ( System.Exception ex ) {
				error = "GetAudioFeaturesForTracks ERROR: " + ex.Message;
			}

			return null;
		}

		public Spotify.AudioFeatures GetAudioFeaturesForTrack(string trackId, out string error) {
			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return null;

			const string BASE = @"https://api.spotify.com/v1/audio-features/{0}";
			string url = string.Format(BASE, trackId);

			var request = WebRequest.Create(url) as HttpWebRequest;
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "GetAudioFeaturesForTrack: Bad status returned: " + response.StatusCode;
						return null;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.AudioFeatures));
					var result = szr.ReadObject(response.GetResponseStream());
					var audio = result as Spotify.AudioFeatures;
					return audio;
				}
			}
			catch ( System.Exception ex ) {
				error = "GetAudioFeaturesForTrack ERROR: " + ex.Message;
			}

			return null;
		}


		public bool GetTracksForPlaylist(string userId, Spotify.PlaylistSimple playlist, out string error) {
			error = null;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return false;

			int playlistCount = GetPlaylistTrackCount(userId, playlist, out error);
			if ( playlistCount == 0 ) return false;
			int offset = 0;

			playlist.Tracks = new Spotify.TrackFull[playlistCount];
			int i = 0;

			const string BASE = @"https://api.spotify.com/v1/users/{0}/playlists/{1}/tracks?offset={2}";

			for ( ; ; ) {
				string url = string.Format(BASE, userId, playlist.Id, offset);
				var request = WebRequest.Create(url) as HttpWebRequest;
				request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);

				try {
					using ( var response = request.GetResponse() as HttpWebResponse ) {
						if ( response.StatusCode != HttpStatusCode.OK ) {
							error = "AddTracksToPlaylist: Bad status returned: " + response.StatusCode;
							return false;
						}
						var szr = new DataContractJsonSerializer(typeof(Spotify.GetTracksResults));
						var result = szr.ReadObject(response.GetResponseStream());
						var tracks = result as Spotify.GetTracksResults;

						foreach ( var track in tracks.Items ) {
							playlist.Tracks[i++] = track.Track;
						}
					}
				}
				catch ( System.Exception ex ) {
					error = "AddTracksToPlaylist ERROR: " + ex.Message;
				}

				offset += 100;
				if ( offset >= playlistCount ) break;
			}

			return true;
		}

		public bool AddTracksToPlaylist(string userId, Spotify.PlaylistSimple playlist, 
			IEnumerable<PlaylistItem> newItems,
			out int tracksAdded,
			out string error) {

			error = null;
			tracksAdded = 0;

			string TOKEN = GetToken(out error);
			if ( TOKEN == null ) return false;

			if ( string.IsNullOrWhiteSpace(userId) ) {
				error = "Invalid inbound userid";
				return false;
			}

			if ( playlist == null ) {
				error = "Invalid inbound playlist";
				return false;
			}

			if ( newItems == null ) {
				error = "Invalid inbound items list";
				return false;
			}

			if ( newItems.Count() == 0 ) {
				error = "Zero-length inbound items list";
				return false;
			}

			StringBuilder sb = new StringBuilder();
			foreach ( var item in newItems ) {
				if ( !item.GotTrackInfo ) continue;
				tracksAdded++;
				sb.Append(HttpUtility.UrlEncode(item.SpotifyTrack.SpotifyTrackUri));
				sb.Append(",");
			}
			sb.Length = sb.Length - 1;


			const string BASE = @"https://api.spotify.com/v1/users/{0}/playlists/{1}/tracks?uris={2}";
			string url = string.Format(BASE, userId, playlist.Id, sb.ToString());
			var request = WebRequest.Create(url) as HttpWebRequest;
			request.Method = "POST";
			request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);
			request.Accept = "application/json";

			try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.Created ) {
						error = "Bad status returned: " + response.StatusCode;
						return false;
					}
				}
			}
			catch ( System.Exception ex ) {
				error = "ERROR: " + ex.Message;
				return false;
			}
			return true;
		}

		public bool LookupTrack(PlaylistItem item, out string error) {
			error = null;

            string TOKEN = GetToken(out error);
            if ( TOKEN == null ) return false;


            const string BASE = @"https://api.spotify.com/v1/search?type=track&limit=1&q=";
			string url = string.Format("{0}track:{1}+artist:{2}",
				BASE, HttpUtility.UrlEncode(item.Track), HttpUtility.UrlEncode(item.Artist));

			item.SpotifyRequestUrl = url;

			var request = WebRequest.Create(url) as HttpWebRequest;
            request.Method = "GET";
            request.Headers.Add(HttpRequestHeader.Authorization, "Bearer " + TOKEN);
            request.Accept = "application/json";

            try {
				using ( var response = request.GetResponse() as HttpWebResponse ) {
					if ( response.StatusCode != HttpStatusCode.OK ) {
						error = "Bad status returned: " + response.StatusCode;
						return false;
					}
					var szr = new DataContractJsonSerializer(typeof(Spotify.TracksResults));
					var result = szr.ReadObject(response.GetResponseStream());
					var tracks = result as Spotify.TracksResults;
					if ( tracks == null ) return false;
					if ( tracks.Results.Items.Count() == 0 ) {
						error = "Song not available on spotify";
						return false; // this can happen if this song is not available on spotify
					}

					var track = tracks.Results.Items[0];
					item.SpotifyPlaylistEntry = track.SpotifyTrackUri;
					item.SpotifyTrack = track;
					item.GotTrackInfo = true;
				}
				return true;
			}
			catch ( System.Exception ex ) {
				error = "ERROR: " + ex.Message;
				return false;
			}
		}

		// from http://tiku.io/questions/7712/net-httpwebrequest-getresponse-raises-exception-when-http-status-code-400-ba
		private void SendPostDataNoWebRequest(string urlEndpoint, string bodyContent) {

			TcpClient client = new TcpClient(urlEndpoint, 443);
			Stream netStream = client.GetStream();
			SslStream sslStream = new SslStream(netStream);
			sslStream.AuthenticateAsClient(urlEndpoint);

			{
				byte[] contentAsBytes = Encoding.ASCII.GetBytes(bodyContent.ToString());

				StringBuilder msg = new StringBuilder();
				msg.AppendLine("POST /o/oauth2/token HTTP/1.1");
				msg.AppendLine("Host: accounts.google.com");
				msg.AppendLine("Content-Type: application/x-www-form-urlencoded");
				msg.AppendLine("Content-Length: " + contentAsBytes.Length.ToString());
				msg.AppendLine("");
				Debug.WriteLine("Request");
				Debug.WriteLine(msg.ToString());
				Debug.WriteLine(bodyContent.ToString());

				byte[] headerAsBytes = Encoding.ASCII.GetBytes(msg.ToString());
				sslStream.Write(headerAsBytes);
				sslStream.Write(contentAsBytes);
			}

			Debug.WriteLine("Response");

			StreamReader reader = new StreamReader(sslStream);
			while ( true ) {  // Print the response line by line to the debug stream for inspection.
				string line = reader.ReadLine();
				if ( line == null ) break;
				Debug.WriteLine(line);
			}

		}

	}
}
