

package mil.don.masio.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import mil.don.common.configuration.DeviceConfiguration;


// this is our configuration values loaded for this application
@Component
@ConfigurationProperties
public class AppConfig
{

    /*
    mas-network-config:
      mas-in-uri: 127.0.0.1:30053
      mas-out-uri-threats: 127.0.0.1:14508
      mas-out-uri-tracks: 127.0.0.1:14506
     */
    public class MasNetworkConfig {

      private String _masInUri;
      private String _masOutUriThreats;
      private String _masOutUriTracks;

      public String getMasInUri() { return _masInUri; }
      public void setMasInUri(String masInUri) { this._masInUri = masInUri; }

      public String getMasOutUriThreats() { return _masOutUriThreats; }
      public void setMasOutUriThreats(String masOutUriThreats) { this._masOutUriThreats = masOutUriThreats; }

      public String getMasOutUriTracks() { return _masOutUriTracks; }
      public void setMasOutUriTracks(String masOutUriTracks) { this._masOutUriTracks = masOutUriTracks; }
    }

    private final Map<String, String> _exchanges = new HashMap<>();
    private final MasNetworkConfig _masNetworkConfig = new MasNetworkConfig();

    public AppConfig() {
        System.out.println("MasIo::AppConfig ctor");
    }

    // TODO: not using, should move to global
    public Map<String, String> getExchanges() { return _exchanges; }
    public MasNetworkConfig getMasNetworkConfig() { return _masNetworkConfig; }




  @Override
    public String toString() {
        return String.format("MasIoConfig:: %d exchanges, in: %s, out: %s, out: %s",
            _exchanges.size(),
            _masNetworkConfig._masInUri,
            _masNetworkConfig._masOutUriThreats,
            _masNetworkConfig.getMasOutUriTracks());
    }


}

