package com.android.systemui.statusbar.policy;

import com.android.systemui.settings.UserTracker;

/* compiled from: DeviceProvisionedControllerImpl.kt */
public final class DeviceProvisionedControllerImpl$userChangedCallback$1 implements UserTracker.Callback {
    public final /* synthetic */ DeviceProvisionedControllerImpl this$0;

    public final void onProfilesChanged() {
    }

    public DeviceProvisionedControllerImpl$userChangedCallback$1(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        this.this$0 = deviceProvisionedControllerImpl;
    }

    public final void onUserChanged(int i) {
        this.this$0.updateValues(false, i);
        this.this$0.onUserSwitched();
    }
}
