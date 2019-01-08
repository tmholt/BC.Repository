package mil.don.common.devices;

import io.reactivex.Observable;
import mil.don.common.messages.tcut30.DataMessage;

public interface IDeviceDetector extends IDevice {

    Observable<DataMessage> getDetectionsStream();
}
