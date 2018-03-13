using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ErgGenerator
{
    public class DataWrapper
    {
        public PowerZones Zones { get; set; }
        public Intervals Intervals { get; set; }

        public DataWrapper()
        {
            Zones = new PowerZones();
            Intervals = new Intervals(Zones);
        }

        public static DataWrapper Load(string file)
        {
            try
            {
                var reader = new StreamReader(file);
                var serializer = new DataContractSerializer(typeof(DataWrapper));
                var dataset = (DataWrapper)serializer.ReadObject(reader, true);
                reader.Close();
                return dataset;
            }
            catch
            {
                return new DataWrapper();
            }
        }

        private void Save(string file)
        {
            var fs = new FileStream(file, FileMode.Create);
            var serializer = new DataContractSerializer(typeof(DataWrapper));
            serializer.WriteObject(fs, this);
            fs.Close();
        }
    }
}
