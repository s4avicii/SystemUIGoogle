package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.CallbackController;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatusBarLocationPublisher.kt */
public final class StatusBarLocationPublisher implements CallbackController<StatusBarMarginUpdatedListener> {
    public final LinkedHashSet listeners = new LinkedHashSet();
    public int marginLeft;
    public int marginRight;

    public final void addCallback(Object obj) {
        this.listeners.add(new WeakReference((StatusBarMarginUpdatedListener) obj));
    }

    public final void removeCallback(Object obj) {
        StatusBarMarginUpdatedListener statusBarMarginUpdatedListener = (StatusBarMarginUpdatedListener) obj;
        WeakReference weakReference = null;
        for (WeakReference weakReference2 : this.listeners) {
            if (Intrinsics.areEqual(weakReference2.get(), statusBarMarginUpdatedListener)) {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            this.listeners.remove(weakReference);
        }
    }
}
