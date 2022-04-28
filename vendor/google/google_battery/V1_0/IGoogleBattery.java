package vendor.google.google_battery.V1_0;

import android.os.IHwInterface;
import android.os.RemoteException;
import com.google.android.systemui.adaptivecharging.AdaptiveChargingManager;

public interface IGoogleBattery extends IHwInterface {
    void getChargingStageAndDeadline(AdaptiveChargingManager.C21592 r1) throws RemoteException;

    byte setChargingDeadline() throws RemoteException;
}
