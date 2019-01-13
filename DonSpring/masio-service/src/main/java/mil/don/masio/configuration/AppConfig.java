

package mil.don.masio.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import mil.don.common.configuration.DeviceConfiguration;


// this contains our custom configuration values loaded for the mas-io service
// @Component - this works, or defining it in application header property (happening now). can't do both.
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfig
{

    private String _masInUri;
    private String _masOutUriThreats;
    private String _masOutUriTracks;
    private int _masPublishRate;
    private final Map<String, String> _exchanges = new HashMap<>();


    public AppConfig() {
      System.out.println("MasIo::AppConfig ctor");
    }

    public int getMasPublishRate() { return _masPublishRate; }
    public AppConfig setMasPublishRate(int masPublishRate) { _masPublishRate = masPublishRate; return this; }

    public String getMasInUri() { return _masInUri; }
    public AppConfig setMasInUri(String masInUri) { _masInUri = masInUri; return this; }

    public String getMasOutUriThreats() { return _masOutUriThreats; }
    public AppConfig setMasOutUriThreats(String masOutUriThreats) { _masOutUriThreats = masOutUriThreats; return this; }

    public String getMasOutUriTracks() { return _masOutUriTracks; }
    public AppConfig setMasOutUriTracks(String masOutUriTracks) { _masOutUriTracks = masOutUriTracks; return this; }


    // TODO: not using, should move to global
    public Map<String, String> getExchanges() { return _exchanges; }

    @Override
    public String toString() {
        return String.format("MasIoConfig:: %d exchanges, in: %s, out: %s, out: %s",
            _exchanges.size(),
            _masInUri,
            _masOutUriThreats,
            _masOutUriTracks);
    }


}

