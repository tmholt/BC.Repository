package mil.don.devices.platform_nighthawk;


import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.devices.DeviceEntity;
import mil.don.common.interfaces.IDeviceCamera;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
 * What are the things that a device needs to do, and what does it need in order to do that job?
 * 1. open communications with the actual device.
 *    custom ports, usually UDP
 *    custom inbound / outbound protocol
 *    turn on receiving as needed
 *    receive configuration from device or configure device as needed
 **  NEED: URIs
 * 2. convert the specific device messages into a common format and pass along
 *    need details for where to send messages
 *    need to connect to inbound commands (specifically targeted to this device)
 * * NEED: details for transport (rabbitmq, exchanges, whatever)
 * 3. connect to video stream as do whatever with it
 ** NEED: URIs
 * 4. send heartbeat / status messages out
 * 5. let device manager know that it is available as a device in the system
 * 6. set up destructors to close comms on shutdown
 */
@Component("NighthawkDevice")
@Scope("prototype")
public class Nighthawk
    extends DeviceEntity
    implements IDeviceCamera
{

    DeviceConfiguration _config;

    public Nighthawk() {
    }

    public Nighthawk(DeviceConfiguration config) {
        _config = config;
    }

    @Override
    public void run() {

    }


}
