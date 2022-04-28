package com.google.android.systemui.columbus.gates;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;

/* compiled from: SetupWizard.kt */
public final class SetupWizard$provisionedListener$1 implements DeviceProvisionedController.DeviceProvisionedListener {
    public final /* synthetic */ SetupWizard this$0;

    public SetupWizard$provisionedListener$1(SetupWizard setupWizard) {
        this.this$0 = setupWizard;
    }

    public final void onDeviceProvisionedChanged() {
        SetupWizard setupWizard = this.this$0;
        setupWizard.setupComplete = setupWizard.isSetupComplete();
        this.this$0.updateBlocking();
    }

    public final void onUserSetupChanged() {
        SetupWizard setupWizard = this.this$0;
        setupWizard.setupComplete = setupWizard.isSetupComplete();
        this.this$0.updateBlocking();
    }
}
