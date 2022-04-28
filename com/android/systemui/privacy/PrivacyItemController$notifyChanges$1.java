package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyItemController;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$notifyChanges$1 implements Runnable {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$notifyChanges$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    public final void run() {
        List<T> list;
        PrivacyItemController privacyItemController = this.this$0;
        Objects.requireNonNull(privacyItemController);
        synchronized (privacyItemController) {
            list = CollectionsKt___CollectionsKt.toList(privacyItemController.privacyList);
        }
        Iterator it = this.this$0.callbacks.iterator();
        while (it.hasNext()) {
            PrivacyItemController.Callback callback = (PrivacyItemController.Callback) ((WeakReference) it.next()).get();
            if (callback != null) {
                callback.onPrivacyItemsChanged(list);
            }
        }
    }
}
