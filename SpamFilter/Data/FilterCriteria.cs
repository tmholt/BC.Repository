using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Xml;

namespace SpamFilter.Data {

	public enum FilterField {
		FromAddress,
		FromDomain,
		FromName,
		FromNameContains,
		Subject,
		SubjectContains
	}

	public enum FilterAction {
		Keep,
		Delete,
		Undetermined
	}
	
	[DataContract]
	public class FilterCriteria {

		[DataMember]
		public FilterField Field { get; set; }


		[DataMember]
		public FilterAction Action { get; set; }

		[DataMember]
		public string Text { get; set; }

	}

	[DataContract]
	public class FilterSet : IDisposable {

		private const string DATA_FILE_PATH = @"C:\temp\spam-email-filter-criteria.dat";

		private ObservableCollection<FilterCriteria> maCriteria;

		private FilterSet() {
			maCriteria = new ObservableCollection<FilterCriteria>();
		}

		~FilterSet() {
			Dispose();
		}

		/// <summary>
		/// Only deserialization should call set
		/// </summary>
		[DataMember]
		public ObservableCollection<FilterCriteria> Criteria {
			get { return maCriteria;  }
			set { maCriteria = value; }
		}

		public static string GetDomainFromAddress(string address) {
			int atindex = address.IndexOf('@');
			if ( atindex > 0 ) {
				return address.Substring(atindex+1);
			}
			return null;
		}

		public FilterAction Evaluate(MessageHeaderInfo hinfo, List<FilterCriteria> matchingFilters = null) {

			FilterAction action = FilterAction.Undetermined;

			foreach ( var filter in maCriteria ) {

				string text2eval = null;
				bool useContains = false;

				switch ( filter.Field ) {
					case FilterField.FromAddress:
						text2eval = hinfo.FromAddress;
						break;
					case FilterField.FromDomain:
						text2eval = GetDomainFromAddress(hinfo.FromAddress);
						break;
					case FilterField.FromName:
						text2eval = hinfo.FromName;
						break;
					case FilterField.FromNameContains:
						text2eval = hinfo.FromName;
						useContains = true;
						break;
					case FilterField.Subject:
						text2eval = hinfo.Subject;
						break;
					case FilterField.SubjectContains:
						text2eval = hinfo.Subject;
						useContains = true;
						break;

				}

				if ( string.IsNullOrEmpty(text2eval) ) {
					continue;
				}

				if ( useContains ) {
					if ( Regex.IsMatch(text2eval, filter.Text, RegexOptions.IgnoreCase) ) {
						switch ( action ) { 
							case FilterAction.Undetermined:
							case FilterAction.Delete:
								action = filter.Action;
								break;
						}
						if ( matchingFilters != null ) {
							matchingFilters.Add(filter);
						}
					}
				}
				else {
					if ( string.Equals(text2eval, filter.Text, StringComparison.CurrentCultureIgnoreCase) ) {
						switch ( action ) {
							case FilterAction.Undetermined:
							case FilterAction.Delete:
								action = filter.Action;
								break;
						}
						if ( matchingFilters != null ) {
							matchingFilters.Add(filter);
						}
					}
				}

			}
			return action;
		}

		public async Task Evaluate(IEnumerable<MessageHeaderInfo> list) {
			await Task.Factory.StartNew(() => {
				foreach ( var hinfo in list ) {
					hinfo.Action = Evaluate(hinfo);
				}
			});
		}

		public void Add(FilterCriteria criteria) {
			maCriteria.Add(criteria);
		}

		public void Remove(FilterCriteria criteria) {
			maCriteria.Remove(criteria);
		}

		public static FilterSet Load() {
			try {
				var fs = new FileStream(DATA_FILE_PATH, FileMode.Open);
				var reader = XmlDictionaryReader.CreateTextReader(fs, new XmlDictionaryReaderQuotas());
				var serializer = new DataContractSerializer(typeof(FilterSet));
				var filterset = (FilterSet)serializer.ReadObject(reader, true);
				reader.Close();
				fs.Close();
				return filterset;
			}
			catch {
				return new FilterSet();
			}
		}

		private void Save() {
			var fs = new FileStream(DATA_FILE_PATH, FileMode.Create);
			var serializer = new DataContractSerializer(typeof(FilterSet));
			serializer.WriteObject(fs, this);
			fs.Close();
		}

		private bool mbDisposed = false;

		public void Dispose() {
			if ( mbDisposed ) return;
			Save();
			maCriteria.Clear();
			mbDisposed = true;
		}
	}
}
