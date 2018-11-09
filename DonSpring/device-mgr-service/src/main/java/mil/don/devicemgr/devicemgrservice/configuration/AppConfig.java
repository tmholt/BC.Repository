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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.don.common.configuration.Device;


// this is our configuration values loaded for this application
@Component
@ConfigurationProperties
public class AppConfig
{
    private final Map<String, String> _exchanges = new HashMap<>();
    private final List<mil.don.common.configuration.Device> _devices = new ArrayList<>();


    public AppConfig() {
        System.out.println("ctor for AppConfig");
    }

    public Map<String, String> getExchanges()
    {
        return _exchanges;
    }
    public List<mil.don.common.configuration.Device> getDevices()
    {
        return _devices;
    }

    @Override
    public String toString() {
        int optionsSum = _devices.stream().map(Device::getOptions).mapToInt(Map::size).sum();
        return String.format("%d exchanges, %d devices, %d options", _exchanges.size(), _devices.size(), optionsSum);
    }


}

