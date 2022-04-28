package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.columbus.actions.Action;
import dagger.Lazy;
import java.util.Objects;
import java.util.Set;

/* compiled from: SetupWizard.kt */
public final class SetupWizard extends Gate {
    public final SetupWizard$actionListener$1 actionListener = new SetupWizard$actionListener$1(this);
    public boolean exceptionActive;
    public final Set<Action> exceptions;
    public final Lazy<DeviceProvisionedController> provisionedController;
    public final SetupWizard$provisionedListener$1 provisionedListener = new SetupWizard$provisionedListener$1(this);
    public boolean setupComplete;

    public final void onActivate() {
        this.exceptionActive = false;
        for (Action action : this.exceptions) {
            SetupWizard$actionListener$1 setupWizard$actionListener$1 = this.actionListener;
            Objects.requireNonNull(action);
            action.listeners.add(setupWizard$actionListener$1);
            this.exceptionActive = action.isAvailable | this.exceptionActive;
        }
        this.setupComplete = isSetupComplete();
        this.provisionedController.get().addCallback(this.provisionedListener);
        updateBlocking();
    }

    public final boolean isSetupComplete() {
        if (!this.provisionedController.get().isDeviceProvisioned() || !this.provisionedController.get().isCurrentUserSetup()) {
            return false;
        }
        return true;
    }

    public final void onDeactivate() {
        this.provisionedController.get().removeCallback(this.provisionedListener);
    }

    public final String toString() {
        return super.toString() + " [isDeviceProvisioned -> " + this.provisionedController.get().isDeviceProvisioned() + "; isCurrentUserSetup -> " + this.provisionedController.get().isCurrentUserSetup() + ']';
    }

    public final void updateBlocking() {
        boolean z;
        if (this.exceptionActive || this.setupComplete) {
            z = false;
        } else {
            z = true;
        }
        setBlocking(z);
    }

    public SetupWizard(Context context, Set<Action> set, Lazy<DeviceProvisionedController> lazy) {
        super(context);
        this.exceptions = set;
        this.provisionedController = lazy;
    }
}
