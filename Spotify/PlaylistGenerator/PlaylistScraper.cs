using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace PlaylistGenerator {
	
	public class PlaylistScraper {

		public List<PlaylistItem> Scrape(StationPlaylistFormat format, string url) {
			switch ( format ) {
				case StationPlaylistFormat.IHeartRadio:
					return ScrapeIHeartRadio(url);
				case StationPlaylistFormat.TuneGenie:
				default:
					return ScrapeTuneGenie(url);
			}
		}

		/// <summary>
		/// Note that this scrape method is specific to i-heart-radio format.
		/// Note also: this is scraping the top songs of the week, not the most recently played list. That 
		/// list appears to be hosted elsewhere.
		/// </summary>
		private List<PlaylistItem> ScrapeIHeartRadio(string url) {
			var items = new List<PlaylistItem>();

			var web = new HtmlWeb();
			var doc = web.Load(url);
			var artists = doc.DocumentNode.SelectNodes("//a[contains(concat(' ', normalize-space(@class), ' '), ' subtitle ')]");
			var songs = doc.DocumentNode.SelectNodes("//a[contains(concat(' ', normalize-space(@class), ' '), ' title ')]");

			if ( artists == null || songs == null ) {
				return null;
			}

			for ( int i = 0; i < artists.Count; i++ ) {
				var artistnode = artists[i].ChildNodes[0];
				var songnode = songs[i].ChildNodes[0];

				string song = HttpUtility.HtmlDecode(songnode.InnerText);
				string artist = HttpUtility.HtmlDecode(artistnode.InnerText);

				song = song.Trim();
				artist = artist.Trim();

				song = song.Replace("?", null);
				song = song.Replace("!", null);
				song = song.Replace("@", null);
				song = song.Replace("#", null);
				song = song.Replace("&", null);
				artist = artist.Replace("?", null);
				artist = artist.Replace("!", null);
				artist = artist.Replace("@", null);
				artist = artist.Replace("#", null);
				artist = artist.Replace("&", null);


				Console.WriteLine(song + " / " + artist);
				items.Add(new PlaylistItem() {
					Added = DateTime.Now,
					Artist = artist.Trim(),
					Track = song.Trim()
				});
			}

			return items;
		}

		/// <summary>
		/// Note that this scrape method is specific to tunegenie.com format
		/// </summary>
		private List<PlaylistItem> ScrapeTuneGenie(string url) {

			var items = new List<PlaylistItem>();

			var web = new HtmlWeb();
			var doc = web.Load(url);
			var results = doc.DocumentNode.SelectNodes("//div[contains(concat(' ', normalize-space(@class), ' '), ' song ')]");

			for ( int i = 0; i < results.Count; i+= 2 ) { // every other
				var result = results[i];
				string song = HttpUtility.HtmlDecode(result.InnerText);
				string artist = HttpUtility.HtmlDecode(result.NextSibling.NextSibling.InnerText);

				song = song.Trim();
				artist = artist.Trim();

				song = song.Replace("?", null);
				song = song.Replace("!", null);
				song = song.Replace("@", null);
				song = song.Replace("#", null);
				song = song.Replace("&", null);
				artist = artist.Replace("?", null);
				artist = artist.Replace("!", null);
				artist = artist.Replace("@", null);
				artist = artist.Replace("#", null);
				artist = artist.Replace("&", null);


				Console.WriteLine(song + " / " + artist);
				items.Add(new PlaylistItem() {
					Added = DateTime.Now,
					Artist = artist.Trim(),
					Track = song.Trim()
				});
			}
			return items;
		}

	}
}
