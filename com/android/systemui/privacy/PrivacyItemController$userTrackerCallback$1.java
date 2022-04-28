package com.android.systemui.privacy;

import com.android.systemui.settings.UserTracker;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$userTrackerCallback$1 implements UserTracker.Callback {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$userTrackerCallback$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    public final void onProfilesChanged() {
        this.this$0.update(true);
    }

    public final void onUserChanged(int i) {
        this.this$0.update(true);
    }
}
