package mil.don.common.devices;

import java.util.ArrayList;
import java.util.List;

public class DeviceEntity {

    private String _id;
    private String _name;
    private double _latitude;
    private double _longitude;
    private double _altitude;
    private ArrayList<DeviceType> _capabilities;


    public DeviceEntity() {
    }

    public DeviceEntity(String id, String name, double lat, double lon, double alt) {
        _capabilities = new ArrayList<>();
        _id = id;
        _name = name;
        _latitude = lat;
        _longitude = lon;
        _altitude = alt;
    }


    public String getId() {
        return _id;
    }
    public DeviceEntity setId(String id) { _id = id; return this; }

    public String getName() {
        return _name;
    }
    public DeviceEntity setName(String name) { _name = name; return this; }

    public double getLatitude() {
        return _latitude;
    }
    public DeviceEntity setLatitude(double latitude) { _latitude = latitude; return this; }

    public double getLongitude() {
        return _longitude;
    }
    public DeviceEntity setLongitude(double longitude) { _longitude = longitude; return this; }

    public List<DeviceType> getCapabilities() {
        return _capabilities;
    }
    public DeviceEntity setCapabilities(List<DeviceType> caps) { _capabilities = new ArrayList<>(caps); return this; }
}
