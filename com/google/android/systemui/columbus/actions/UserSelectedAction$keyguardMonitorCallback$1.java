package com.google.android.systemui.columbus.actions;

import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$keyguardMonitorCallback$1 implements KeyguardStateController.Callback {
    public final /* synthetic */ UserSelectedAction this$0;

    public UserSelectedAction$keyguardMonitorCallback$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    public final void onKeyguardShowingChanged() {
        this.this$0.updateAvailable();
    }
}
