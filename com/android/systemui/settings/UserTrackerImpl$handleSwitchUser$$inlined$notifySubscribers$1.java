package com.android.systemui.settings;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import java.util.List;
import java.util.Objects;

/* compiled from: UserTrackerImpl.kt */
public final class UserTrackerImpl$handleSwitchUser$$inlined$notifySubscribers$1 implements Runnable {
    public final /* synthetic */ Context $ctx$inlined;
    public final /* synthetic */ DataItem $it;
    public final /* synthetic */ int $newUser$inlined;
    public final /* synthetic */ List $profiles$inlined;

    public UserTrackerImpl$handleSwitchUser$$inlined$notifySubscribers$1(DataItem dataItem, int i, Context context, List list) {
        this.$it = dataItem;
        this.$newUser$inlined = i;
        this.$ctx$inlined = context;
        this.$profiles$inlined = list;
    }

    public final void run() {
        DataItem dataItem = this.$it;
        Objects.requireNonNull(dataItem);
        UserTracker.Callback callback = dataItem.callback.get();
        if (callback != null) {
            callback.onUserChanged(this.$newUser$inlined);
            callback.onProfilesChanged();
        }
    }
}
