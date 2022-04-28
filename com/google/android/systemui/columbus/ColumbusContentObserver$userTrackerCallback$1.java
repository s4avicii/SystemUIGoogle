package com.google.android.systemui.columbus;

import com.android.systemui.settings.UserTracker;

/* compiled from: ColumbusContentObserver.kt */
public final class ColumbusContentObserver$userTrackerCallback$1 implements UserTracker.Callback {
    public final /* synthetic */ ColumbusContentObserver this$0;

    public ColumbusContentObserver$userTrackerCallback$1(ColumbusContentObserver columbusContentObserver) {
        this.this$0 = columbusContentObserver;
    }

    public final void onUserChanged(int i) {
        this.this$0.updateContentObserver();
        ColumbusContentObserver columbusContentObserver = this.this$0;
        columbusContentObserver.callback.invoke(columbusContentObserver.settingsUri);
    }
}
