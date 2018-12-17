package mil.don.common.devices;

import io.reactivex.Observable;

public interface IDeviceDetector extends IDevice {

    Observable<DetectionMessage> getDetectionsStream();
}
