using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ErgGenerator
{

    public class Intervals : ObservableCollection<Interval>
    {
        private PowerZones maZones;
        private uint mnIndex = 0;


        public Intervals(PowerZones zones)
        {
            maZones = zones;

            maZones.PowerZonesPropertyChanged += (sender, args) =>
            {
                switch ( args.PropertyName )
                {
                    case "FTP":
                        foreach ( var interval in this )
                        {
                            interval.ZonesRangeString = maZones.GenerateRangesString(interval.PercentageOfFtpMin, interval.PercentageOfFtpMax);
                        }
                        break;

                    case "FTHR":
                        break;
                }
            };
        }

        public Interval Add()
        {
            uint previous = (uint)this.Sum(ntrvl => ntrvl.EndingMinutes);
            var interval = new Interval(++mnIndex, previous);
            interval.PropertyChanged += Interval_PropertyChanged;

            this.Add(interval);
            return interval;
        }

        private void Interval_PropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            var interval = sender as Interval;
            switch ( e.PropertyName )
            {
                case "DurationMinutes":
                    // recalc everything below
                    UpdateStartingEnding();
                    break;

                case "PercentageOfFtpMin":
                case "PercentageOfFtpMax":
                    interval.ZonesRangeString = maZones.GenerateRangesString(interval.PercentageOfFtpMin, interval.PercentageOfFtpMax);
                    break;                    


            }
        }

        private void UpdateStartingEnding()
        {
            uint starting = 0;
            uint ending = 0;
            for ( int i = 0; i < Count; i++ )
            {
                var ntrvl = this[i];
                ntrvl.SetStarting(starting);
                starting += ntrvl.DurationMinutes;
            }
        }
    }

    public interface IInterval
    {
        uint Index { get; }

        uint StartingMinutes { get; }


        uint DurationMinutes { get; set; }

        uint EndingMinutes { get; }

        uint PercentageOfFtpMin { get; set; }

        uint PercentageOfFtpMax { get; set; }

        uint CadenceMin { get; set; }

        uint CadenceMax { get; set; }

        string ZonesRangeString { get; }

        string Description { get; set; }
    }

    /*
                <rad:GridViewDataColumn Header="Starting" IsReadOnly="False" Width="75" DataMemberBinding="{Binding StartingMinutes}"/>
                <rad:GridViewDataColumn Header="Duration" IsReadOnly="False" Width="75" DataMemberBinding="{Binding DurationMinutes}"/>
                <rad:GridViewDataColumn Header="Ending" IsReadOnly="True" Width="75" DataMemberBinding="{Binding EndingMinutes}"/>
                <rad:GridViewDataColumn Header="FTP % Low" IsReadOnly="False" Width="105" DataMemberBinding="{Binding PercentageOfFtpMin}" DataFormatString="{}{0:0.0}"/>
                <rad:GridViewDataColumn Header="FTP % High" IsReadOnly="False" Width="105" DataMemberBinding="{Binding PercentageOfFtpMax}"  DataFormatString="{}{0:0.0}"/>
                <rad:GridViewDataColumn Header="Zones" IsReadOnly="True" Width="105" DataMemberBinding="{Binding ZonesRangeString}"/>
                <rad:GridViewDataColumn Header="Description" IsReadOnly="False" Width="*" DataMemberBinding="{Binding Description}"/>
      
     */
    public class Interval : IInterval, INotifyPropertyChanged
    {

        private uint mnIndex;
        private uint mnStartingMinutes;
        private uint mnDuration;
        private string msZonesRanges;
        private uint mnPercentageOfFtpMin;
        private uint mnPercentageOfFtpMax;

        public Interval(uint index, uint startingMinutes)
        {
            mnIndex = index;
            mnStartingMinutes = startingMinutes;
        }

        public uint Index => mnIndex;

        public uint StartingMinutes => mnStartingMinutes; 
        

        public uint DurationMinutes
        {
            get { return mnDuration; }
            set
            {
                mnDuration = value;
                Notify(nameof(DurationMinutes));
                Notify(nameof(EndingMinutes));
            }
        }

        public uint EndingMinutes => mnStartingMinutes + mnDuration;

        public uint PercentageOfFtpMin {
            get { return mnPercentageOfFtpMin; }
            set
            {
                mnPercentageOfFtpMin = value;
                Notify(nameof(PercentageOfFtpMin));
            }
        }

        public uint PercentageOfFtpMax
        {
            get { return mnPercentageOfFtpMax; }
            set
            {
                mnPercentageOfFtpMax = value;
                Notify(nameof(PercentageOfFtpMax));
            }
        }


        public uint CadenceMin { get; set; }

        public uint CadenceMax { get; set; }

        public string ZonesRangeString {
            get { return msZonesRanges; }
            set
            {
                msZonesRanges = value;
                Notify(nameof(ZonesRangeString));
            }

        }

        public string Description { get; set; }

        // only access from parent

        internal void SetStarting(uint starting)
        {
            mnStartingMinutes = starting;
            Notify(nameof(StartingMinutes));
            Notify(nameof(EndingMinutes));
        }


        #region INotifyPropertyChanged

        public event PropertyChangedEventHandler PropertyChanged;

        protected void Notify(string prop)
        {
            if ( PropertyChanged != null )
            {
                PropertyChanged(this, new PropertyChangedEventArgs(prop));
            }
        }

        #endregion
    }
}
