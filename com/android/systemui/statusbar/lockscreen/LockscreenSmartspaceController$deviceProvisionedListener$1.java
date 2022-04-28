package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$deviceProvisionedListener$1 implements DeviceProvisionedController.DeviceProvisionedListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$deviceProvisionedListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void onDeviceProvisionedChanged() {
        this.this$0.connectSession();
    }

    public final void onUserSetupChanged() {
        this.this$0.connectSession();
    }
}
