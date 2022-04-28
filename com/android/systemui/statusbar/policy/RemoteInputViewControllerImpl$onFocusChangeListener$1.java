package com.android.systemui.statusbar.policy;

import android.view.View;
import java.util.Objects;

/* compiled from: RemoteInputViewController.kt */
public final class RemoteInputViewControllerImpl$onFocusChangeListener$1 implements View.OnFocusChangeListener {
    public final /* synthetic */ RemoteInputViewControllerImpl this$0;

    public RemoteInputViewControllerImpl$onFocusChangeListener$1(RemoteInputViewControllerImpl remoteInputViewControllerImpl) {
        this.this$0 = remoteInputViewControllerImpl;
    }

    public final void onFocusChange(View view, boolean z) {
        RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler = this.this$0.remoteInputQuickSettingsDisabler;
        Objects.requireNonNull(remoteInputQuickSettingsDisabler);
        if (remoteInputQuickSettingsDisabler.remoteInputActive != z) {
            remoteInputQuickSettingsDisabler.remoteInputActive = z;
            remoteInputQuickSettingsDisabler.commandQueue.recomputeDisableFlags(remoteInputQuickSettingsDisabler.context.getDisplayId(), true);
        }
    }
}
