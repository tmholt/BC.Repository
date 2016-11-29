using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace PlaylistGenerator.Spotify {

	[DataContract]
	public class ExternalUrl {

		[DataMember]
		public string Key { get; set; }

		[DataMember(Name="value")]
		public string Value { get; set; }
	}

	[DataContract]
	public class ImageRef {

		[DataMember(Name="height")]
		public int? Height { get; set; }


		[DataMember(Name="width")]
		public int? Width { get; set; }

		[DataMember(Name="url")]
		public string Url { get; set; }
	}


	[DataContract]
	public class AlbumSimplified {

		[DataMember(Name="album_type")]
		public string AlbumType { get; set; }

		[DataMember(Name="available_markets")]
		public string[] AvailableMarkets { get; set; }

		//[DataMember(Name="external_urls")]
		//public ExternalUrl ExternalUrls { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="images")]
		public ImageRef[] Images { get; set; }

		[DataMember(Name="name")]
		public string Name { get; set; }

		[DataMember(Name="type")]
		public string Type{ get; set; }
		
		[DataMember(Name="uri")]
		public string SpotifyAlbumUri { get; set; }	

	}

	[DataContract]
	public class ArtistSimplified {

		//[DataMember(Name="external_urls")]
		//public ExternalUrl ExternalUrls { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="name")]
		public string Name { get; set; }

		[DataMember(Name="type")]
		public string Type { get; set; }

		[DataMember(Name="uri")]
		public string SpotifyArtistUri { get; set; }	

	}


	[DataContract]
	public class TracksResults {

		[DataMember(Name="tracks")]
		public TracksList Results { get; set; }
	}

	[DataContract]
	public class TracksList {

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="total")]
		public int Total { get; set; }

		[DataMember(Name="limit")]
		public int Limit { get; set; }

		[DataMember(Name="items")]
		public TrackFull[] Items { get; set; }
	}

	[DataContract]
	public class GetTracksResults {
		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="items")]
		public GetTrackResultItem[] Items { get; set; }
	}

	[DataContract]
	public class GetTrackResultItem {
		[DataMember(Name="added_at")]
		public string AddedAtDate { get; set; }

		[DataMember(Name="track")]
		public TrackFull Track { get; set; }
	}

	[DataContract]
	public class AudioFeaturesList {

		[DataMember(Name = "audio_features")]
		public AudioFeatures[] Items { get; set; }
	}


	[DataContract]
	public class AudioFeatures {

		[DataMember(Name = "acousticness")]
		public float Acousticness { get; set; }

		[DataMember(Name = "analysis_url")]
		public string AnalysisUrl { get; set; }

		[DataMember(Name = "danceability")]
		public float Danceability { get; set; }

		[DataMember(Name = "duration_ms")]
		public int DurationMs { get; set; }

		[DataMember(Name = "energy")]
		public float Energy { get; set; }

		[DataMember(Name = "id")]
		public string Id { get; set; }

		[DataMember(Name = "instrumentalness")]
		public float Instrumentalness { get; set; }

		[DataMember(Name = "key")]
		public int Key { get; set; }

		[DataMember(Name = "liveness")]
		public float liveness { get; set; }

		[DataMember(Name = "loudness")]
		public float Loudness { get; set; }

		[DataMember(Name = "mode")]
		public int Mode { get; set; }

		[DataMember(Name = "speechiness")]
		public float Speechiness { get; set; }

		[DataMember(Name = "tempo")]
		public float Tempo { get; set; }

		[DataMember(Name = "time_signature")]
		public int TimeSignature { get; set; }

		[DataMember(Name = "track_href")]
		public string TrackHref { get; set; }

		[DataMember(Name = "type")]
		public string Type { get; set; }

		[DataMember(Name = "uri")]
		public string Uri { get; set; }

		[DataMember(Name = "valence")]
		public float Valence { get; set; }
	}


	[DataContract]
	public class TrackFull {

		[DataMember(Name="album")]
		public AlbumSimplified Album { get; set; }

		[DataMember(Name="artists")]
		public ArtistSimplified[] Artists { get; set; }

		[DataMember(Name="available_markets")]
		public string[] Markets { get; set; }

		[DataMember(Name="disc_number")]
		public int DiscNumber { get; set; }

		[DataMember(Name="duration_ms")]
		public int Duration { get; set; }

		[DataMember(Name="explicit")]
		public bool Explicit { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="name")]
		public string Name { get; set; }

		[DataMember(Name="popularity")]
		public int Popularity { get; set; }

		[DataMember(Name="track_number")]
		public int TrackNumber { get; set; }

		[DataMember(Name="uri")]
		public string SpotifyTrackUri { get; set; }

		public TrackSimple ToSimple() {
			var simple = new TrackSimple() {
				//Album = this.Album,
				Artists = this.Artists,
				Markets = this.Markets,
				DiscNumber = this.DiscNumber,
				Explicit = this.Explicit,
				Href = this.Href,
				Id = this.Id,
				Name = this.Name,
				//Popularity = this.Popularity,
				TrackNumber = this.TrackNumber,
				SpotifyTrackUri = this.SpotifyTrackUri
			};
			return simple;
		}

	}


	[DataContract]
	public class TrackSimple {

		[DataMember(Name="artists")]
		public ArtistSimplified[] Artists { get; set; }

		[DataMember(Name="available_markets")]
		public string[] Markets { get; set; }

		[DataMember(Name="disc_number")]
		public int DiscNumber { get; set; }

		[DataMember(Name="duration_ms")]
		public int Duration { get; set; }

		[DataMember(Name="explicit")]
		public bool Explicit { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="name")]
		public string Name { get; set; }

		[DataMember(Name="track_number")]
		public int TrackNumber { get; set; }

		[DataMember(Name="uri")]
		public string SpotifyTrackUri { get; set; }

		public TrackFull ToFull() {
			var full = new TrackFull() {
				//Album = this.Album,
				Artists = this.Artists,
				Markets = this.Markets,
				DiscNumber = this.DiscNumber,
				Explicit = this.Explicit,
				Href = this.Href,
				Id = this.Id,
				Name = this.Name,
				//Popularity = this.Popularity,
				TrackNumber = this.TrackNumber,
				SpotifyTrackUri = this.SpotifyTrackUri
			};
			return full;
		}


	}

	[DataContract]
	public class FollowersRef {

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="total")]
		public int Total { get; set; }
	}

	[DataContract]
	public class UserPrivate {

		[DataMember(Name="country")]
		public string Country { get; set; }

		[DataMember(Name="display_name")]
		public string DisplayName { get; set; }

		[DataMember(Name="email")]
		public string Email { get; set; }

		[DataMember(Name="external_url")]
		public ExternalUrl Urls { get; set; }

		[DataMember(Name="followers")]
		public FollowersRef Followers { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="images")]
		public ImageRef[] Images { get; set; }

		[DataMember(Name="product")]
		public string Product { get; set; }

		[DataMember(Name="type")]
		public string Type { get; set; }

		[DataMember(Name="uri")]
		public string SpotifyUserUri { get; set; }

	}


	[DataContract]
	public class TokenReposnse {

		[DataMember(Name="access_token")]
		public string AccessToken { get; set; }

		[DataMember(Name="token_type")]
		public string TokenType { get; set; }

		[DataMember(Name="expires_in")]
		public int Expires { get; set; }

		[DataMember(Name="refresh_token")]
		public string RefreshToken { get; set; }
	}

	[DataContract]
	public class PlaylistsList {

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="total")]
		public int Total { get; set; }

		[DataMember(Name="limit")]
		public int Limit { get; set; }

		[DataMember(Name="items")]
		public PlaylistSimple[] Items { get; set; }
	}


	[DataContract]
	public class PlaylistSimple {

		[DataMember(Name="collaborative")]
		public bool Collaborative { get; set; }

		[DataMember(Name="external_urls")]
		public ExternalUrl Urls { get; set; }

		[DataMember(Name="href")]
		public string Href { get; set; }

		[DataMember(Name="id")]
		public string Id { get; set; }

		[DataMember(Name="images")]
		public ImageRef[] Images { get; set; }


		[DataMember(Name="name")]
		public string Name { get; set; }


		[DataMember(Name="owner")]
		public UserPrivate Owner { get; set; }


		[DataMember(Name="public")]
		public bool IsPublic { get; set; }


		[DataMember(Name="tracks")]
		public TrackFull[] Tracks { get; set; }


		[DataMember(Name="type")]
		public string Type { get; set; }

		[DataMember(Name="uri")]
		public string SpotifyPlaylistUri { get; set; }

	}

}
