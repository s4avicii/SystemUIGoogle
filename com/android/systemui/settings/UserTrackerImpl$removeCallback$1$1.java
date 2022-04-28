package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.util.function.Predicate;

/* compiled from: UserTrackerImpl.kt */
public final class UserTrackerImpl$removeCallback$1$1<T> implements Predicate {
    public final /* synthetic */ UserTracker.Callback $callback;

    public UserTrackerImpl$removeCallback$1$1(UserTracker.Callback callback) {
        this.$callback = callback;
    }

    public final boolean test(Object obj) {
        UserTracker.Callback callback = this.$callback;
        UserTracker.Callback callback2 = ((DataItem) obj).callback.get();
        if (callback2 == null) {
            return true;
        }
        return callback2.equals(callback);
    }
}
