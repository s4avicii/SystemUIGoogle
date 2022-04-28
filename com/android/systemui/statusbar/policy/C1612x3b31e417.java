package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import android.util.Log;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1612x3b31e417 implements DeviceStateManager.DeviceStateCallback {
    public final /* synthetic */ DeviceStateRotationLockSettingController f$0;

    public /* synthetic */ C1612x3b31e417(DeviceStateRotationLockSettingController deviceStateRotationLockSettingController) {
        this.f$0 = deviceStateRotationLockSettingController;
    }

    public final void onStateChanged(int i) {
        DeviceStateRotationLockSettingController deviceStateRotationLockSettingController = this.f$0;
        Objects.requireNonNull(deviceStateRotationLockSettingController);
        Log.v("DSRotateLockSettingCon", "updateDeviceState [state=" + i + "]");
        Trace.beginSection("updateDeviceState [state=" + i + "]");
        try {
            if (deviceStateRotationLockSettingController.mDeviceState != i) {
                deviceStateRotationLockSettingController.readPersistedSetting(i);
            }
        } finally {
            Trace.endSection();
        }
    }
}
