package com.android.systemui.statusbar.policy;

import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1613x3b31e418 implements DeviceStateRotationLockSettingsManager.DeviceStateRotationLockSettingsListener {
    public final /* synthetic */ DeviceStateRotationLockSettingController f$0;

    public /* synthetic */ C1613x3b31e418(DeviceStateRotationLockSettingController deviceStateRotationLockSettingController) {
        this.f$0 = deviceStateRotationLockSettingController;
    }

    public final void onSettingsChanged() {
        DeviceStateRotationLockSettingController deviceStateRotationLockSettingController = this.f$0;
        Objects.requireNonNull(deviceStateRotationLockSettingController);
        deviceStateRotationLockSettingController.readPersistedSetting(deviceStateRotationLockSettingController.mDeviceState);
    }
}
