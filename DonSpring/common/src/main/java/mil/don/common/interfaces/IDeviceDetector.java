package mil.don.common.interfaces;

import io.reactivex.Observable;
import mil.don.common.devices.DetectionMessage;

public interface IDeviceDetector extends IDevice {

    Observable<DetectionMessage> getDetectionsStream();
}
