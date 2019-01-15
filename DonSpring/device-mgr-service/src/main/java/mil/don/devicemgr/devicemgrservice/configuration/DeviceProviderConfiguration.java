/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.devicemgr.devicemgrservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import mil.don.common.devices.DeviceBase;
import mil.don.devices.platform_ctc.Ctc;
import mil.don.devices.platform_duke5.Duke5;
import mil.don.devices.platform_nighthawk.Nighthawk;
import mil.don.proxies.LoggingProxy;

// NOTE: this seems to be the only way that I can get to the device components in their
// respective jars. It would be nice if they just got picked up by spring.
@Configuration
public class DeviceProviderConfiguration
{
    // the proxy to our logging service
    @Autowired
    private LoggingProxy _logging;


    @Bean
    @Scope("prototype")
    public DeviceBase createNighthawk() {
        return new Nighthawk(_logging);
    }

    @Bean
    @Scope("prototype")
    public DeviceBase createDuke5() {
        return new Duke5(_logging);
    }
	
    @Bean
    @Scope("prototype")
    public DeviceBase createCtc() {
        return new Ctc(_logging);
    }	
}
