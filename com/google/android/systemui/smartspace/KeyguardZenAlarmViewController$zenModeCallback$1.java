package com.google.android.systemui.smartspace;

import com.android.systemui.statusbar.policy.ZenModeController;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$zenModeCallback$1 implements ZenModeController.Callback {
    public final /* synthetic */ KeyguardZenAlarmViewController this$0;

    public KeyguardZenAlarmViewController$zenModeCallback$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        this.this$0 = keyguardZenAlarmViewController;
    }

    public final void onZenChanged(int i) {
        this.this$0.updateDnd();
    }
}
