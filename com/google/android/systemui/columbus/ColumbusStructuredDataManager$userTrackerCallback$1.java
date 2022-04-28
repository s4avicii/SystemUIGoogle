package com.google.android.systemui.columbus;

import com.android.systemui.settings.UserTracker;

/* compiled from: ColumbusStructuredDataManager.kt */
public final class ColumbusStructuredDataManager$userTrackerCallback$1 implements UserTracker.Callback {
    public final /* synthetic */ ColumbusStructuredDataManager this$0;

    public ColumbusStructuredDataManager$userTrackerCallback$1(ColumbusStructuredDataManager columbusStructuredDataManager) {
        this.this$0 = columbusStructuredDataManager;
    }

    public final void onUserChanged(int i) {
        ColumbusStructuredDataManager columbusStructuredDataManager = this.this$0;
        synchronized (columbusStructuredDataManager.lock) {
            columbusStructuredDataManager.packageStats = columbusStructuredDataManager.fetchPackageStats();
        }
    }
}
