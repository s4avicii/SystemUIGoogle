package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.settings.UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1 */
/* compiled from: UserTrackerImpl.kt */
public final class C1097xc58954eb implements Runnable {
    public final /* synthetic */ DataItem $it;
    public final /* synthetic */ List $profiles$inlined;

    public C1097xc58954eb(DataItem dataItem, List list) {
        this.$it = dataItem;
        this.$profiles$inlined = list;
    }

    public final void run() {
        DataItem dataItem = this.$it;
        Objects.requireNonNull(dataItem);
        UserTracker.Callback callback = dataItem.callback.get();
        if (callback != null) {
            callback.onProfilesChanged();
        }
    }
}
