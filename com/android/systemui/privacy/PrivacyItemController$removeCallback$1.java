package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyItemController;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$removeCallback$1<T> implements Predicate {
    public final /* synthetic */ WeakReference<PrivacyItemController.Callback> $callback;

    public PrivacyItemController$removeCallback$1(WeakReference<PrivacyItemController.Callback> weakReference) {
        this.$callback = weakReference;
    }

    public final boolean test(Object obj) {
        PrivacyItemController.Callback callback = (PrivacyItemController.Callback) ((WeakReference) obj).get();
        if (callback == null) {
            return true;
        }
        return callback.equals(this.$callback.get());
    }
}
