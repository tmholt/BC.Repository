using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ErgGenerator
{
    public class PowerZones : ObservableCollection<PowerZone>, INotifyPropertyChanged
    {

        private uint mnFtp;
        private uint mnFthr;

        public event PropertyChangedEventHandler PowerZonesPropertyChanged;


        public PowerZones()
        {
            Initialize();

        }

        public uint FTP
        {
            get { return mnFtp; }
            set
            {
                mnFtp = value;
                OnPropertyChanged(new PropertyChangedEventArgs(nameof(FTP)));
                PowerZonesPropertyChanged(this, new PropertyChangedEventArgs(nameof(FTP)));
                SetThresholds();
            }
        }

        public uint FTHR
        {
            get { return mnFthr; }
            set
            {
                mnFthr = value;
                OnPropertyChanged(new PropertyChangedEventArgs(nameof(FTHR)));
                PowerZonesPropertyChanged(this, new PropertyChangedEventArgs(nameof(FTHR)));
                SetThresholds();
            }
        }

        internal string GenerateRangesString(uint percentageOfFtpMin, uint percentageOfFtpMax)
        {
            var lowZone = this.FirstOrDefault(z => z.WattsPercentageLow != null && z.WattsPercentageHigh != null && z.WattsPercentageLow <= percentageOfFtpMin && z.WattsPercentageHigh >= percentageOfFtpMin);
            var highZone = this.FirstOrDefault(z => z.WattsPercentageLow != null && z.WattsPercentageHigh != null && z.WattsPercentageLow <= percentageOfFtpMax && z.WattsPercentageHigh >= percentageOfFtpMax);

            if ( lowZone == null || highZone == null ) return "UNK";

            double top =  percentageOfFtpMin - (uint)lowZone.WattsPercentageLow;
            double bottom = (uint)lowZone.WattsPercentageHigh - (uint)lowZone.WattsPercentageLow;
            double lowValue = lowZone.ZoneNumber + Math.Round(top/bottom, 1);

            top =  percentageOfFtpMax - (uint)highZone.WattsPercentageLow;
            bottom = (uint)highZone.WattsPercentageHigh - (uint)highZone.WattsPercentageLow;
            double highValue = highZone.ZoneNumber + Math.Round(top/bottom, 1);

            return string.Format("{0:0.0} ({1:000}) - {2:0.0} ({3:000})", 
                lowValue, FTP * ((double)percentageOfFtpMin/100.0), highValue, FTP * ((double)percentageOfFtpMax/100.0));
        }



        private void Initialize()
        {
            Add(new PowerZone(1, "Active Recovery", 0, 59, 0, 64, "\"Easy spinning\" or \"light pedal pressure\", i.e., very low level exercise, too low in and of itself to induce significant physiological adaptations. Minimal sensation of leg effort/fatigue. Requires no concentration to maintain pace, and continuous conversation possible. Typically used for active recovery after strenuous training days (or races), between interval efforts, or for socializing."));
            Add(new PowerZone(2, "Endurance", 60, 79, 65, 84, "All day pace, or classic long slow distance (LSD) training. Sensation of leg effort/fatigue generally low, but may rise periodically to higher levels (e.g., when climbing). Concentration generally required to maintain effort only at highest end of range and/or during longer training sessions. Breathing is more regular than at level 1, but continuous conversation still possible. Frequent (daily) training sessions of moderate duration (e.g., 2 h) at level 2 possible (provided dietary carbohydrate intake is adequate), but complete recovery from very long workouts may take more than 24 hs."));
            Add(new PowerZone(3, "Tempo", 80, 90, 85, 91, "Typical intensity of fartlek workout, 'spirited' group ride, or briskly moving paceline. More frequent/greater sensation of leg effort/fatigue than at level 2. Requires concentration to maintain alone, especially at upper end of range, to prevent effort from falling back to level 2. Breathing deeper and more rhythmic than level 2, such that any conversation must be somewhat halting, but not as difficult as at level 4. Recovery from level 3 training sessions more difficult than after level 2 workouts, but consecutive days of level 3 training still possible if duration is not excessive and dietary carbohydrate intake is adequate."));
            Add(new PowerZone(4, "Lactate Threshold", 91, 104, 92, 100, "Just below to just above TT effort, taking into account duration, current fitness, environmental conditions, etc. Essentially continuous sensation of moderate or even greater leg effort/fatigue. Continuous conversation difficult at best, due to depth/frequency of breathing. Effort sufficiently high that sustained exercise at this level is mentally very taxing – therefore typically performed in training as multiple 'repeats', 'modules', or 'blocks' of 10-30 min duration. Consecutive days of training at level 4 possible, but such workouts generally only performed when sufficiently rested/recovered from prior training so as to be able to maintain intensity."));
            Add(new PowerZone(5, "VO2 Max", 105, 120, 101, 110, "Typical intensity of longer (3-8 min) intervals intended to increase VO2max. Strong to severe sensations of leg effort/fatigue, such that completion of more than 30-40 min total training time is difficult at best. Conversation not possible due to often 'ragged' breathing. Should generally be attempted only when adequately recovered from prior training – consecutive days of level 5 work not necessarily desirable even if possible. Note: At this level, the average heart rate may not be due to slowness of heart rate response and/or ceiling imposed by maximum heart rate)"));
            Add(new PowerZone(6, "Anaerobic Capacity", 111, 150, null, null, "Short (30 s to 3 min), high intensity intervals designed to increase anaerobic capacity. Heart rate generally not useful as guide to intensity due to non-steady-state nature of effort. Severe sensation of leg effort/fatigue, and conversation impossible. Consecutive days of extended level 6 training usually not attempted."));
            Add(new PowerZone(7, "Neuromuscular Power", null, null, null, null, "Very short, very high intensity efforts (e.g., jumps, standing starts, short sprints) that generally place greater stress on musculoskeletal rather than metabolic systems. Power useful as guide, but only in reference to prior similar efforts, not TT pace."));
        }

        private void SetThresholds()
        {
            foreach ( var zone in this )
            {
                zone.SetThresholds(FTP, FTHR);
            }
        }





    }

    public class PowerZone : INotifyPropertyChanged
    {

        public PowerZone(uint number, string name, uint? wlow, uint? whigh, uint? hlow, uint? hhigh, string description)
        {
            ZoneNumber = number;
            WattsPercentageLow = wlow;
            WattsPercentageHigh = whigh;
            HeartRatePercentageLow = hlow;
            HeartRatePercentageHigh = hhigh;
            Name = name;
            Description = description;
        }

        public uint ZoneNumber { get; private set; }
        public uint? WattsPercentageLow { get; private set; }
        public uint? WattsPercentageHigh { get; private set; }

        public uint? HeartRatePercentageLow { get; private set; }
        public uint? HeartRatePercentageHigh { get; private set; }


        public string Name { get; private set; }

        public string Description { get; private set; }

        public string RangesString { get; private set; }


        internal void SetThresholds(uint ftp, uint fthr)
        {
            string wattslow = ( WattsPercentageLow == null ) ? "n/a" : Math.Round(ftp * ((double)WattsPercentageLow/100.0)).ToString("000");
            string wattshigh = ( WattsPercentageHigh == null ) ? "n/a" : Math.Round(ftp * ((double)WattsPercentageHigh/100.0)).ToString("000");

            string hrlow = ( HeartRatePercentageLow == null ) ? "n/a" : Math.Round(fthr * ((double)HeartRatePercentageLow/100.0)).ToString("000");
            string hrhigh = ( HeartRatePercentageHigh == null ) ? "n/a" : Math.Round(fthr * ((double)HeartRatePercentageHigh/100.0)).ToString("000");

            RangesString = string.Format("Watts: {0} - {1}; Heartrate: {2} - {3}",
                wattslow, wattshigh, hrlow, hrhigh);

            Notify(nameof(RangesString));
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
