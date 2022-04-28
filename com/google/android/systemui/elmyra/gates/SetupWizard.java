package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.elmyra.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SetupWizard extends Gate {
    public final ArrayList mExceptions;
    public final DeviceProvisionedController mProvisionedController;
    public final C22591 mProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        public final void onDeviceProvisionedChanged() {
            boolean z;
            SetupWizard setupWizard = SetupWizard.this;
            Objects.requireNonNull(setupWizard);
            if (!setupWizard.mProvisionedController.isDeviceProvisioned() || !setupWizard.mProvisionedController.isCurrentUserSetup()) {
                z = false;
            } else {
                z = true;
            }
            SetupWizard setupWizard2 = SetupWizard.this;
            if (z != setupWizard2.mSetupComplete) {
                setupWizard2.mSetupComplete = z;
                setupWizard2.notifyListener();
            }
        }

        public final void onUserSetupChanged() {
            boolean z;
            SetupWizard setupWizard = SetupWizard.this;
            Objects.requireNonNull(setupWizard);
            if (!setupWizard.mProvisionedController.isDeviceProvisioned() || !setupWizard.mProvisionedController.isCurrentUserSetup()) {
                z = false;
            } else {
                z = true;
            }
            SetupWizard setupWizard2 = SetupWizard.this;
            if (z != setupWizard2.mSetupComplete) {
                setupWizard2.mSetupComplete = z;
                setupWizard2.notifyListener();
            }
        }
    };
    public boolean mSetupComplete;

    public final boolean isBlocked() {
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (((Action) this.mExceptions.get(i)).isAvailable()) {
                return false;
            }
        }
        return !this.mSetupComplete;
    }

    public final void onActivate() {
        boolean z;
        if (!this.mProvisionedController.isDeviceProvisioned() || !this.mProvisionedController.isCurrentUserSetup()) {
            z = false;
        } else {
            z = true;
        }
        this.mSetupComplete = z;
        this.mProvisionedController.addCallback(this.mProvisionedListener);
    }

    public final void onDeactivate() {
        this.mProvisionedController.removeCallback(this.mProvisionedListener);
    }

    public final String toString() {
        return super.toString() + " [isDeviceProvisioned -> " + this.mProvisionedController.isDeviceProvisioned() + "; isCurrentUserSetup -> " + this.mProvisionedController.isCurrentUserSetup() + "]";
    }

    public SetupWizard(Context context, List<Action> list) {
        super(context);
        this.mExceptions = new ArrayList(list);
        this.mProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    }
}
