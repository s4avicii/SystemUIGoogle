package com.android.systemui.p006qs;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import java.util.Collection;

/* renamed from: com.android.systemui.qs.AutoAddTracker$contentObserver$1 */
/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker$contentObserver$1 extends ContentObserver {
    public final /* synthetic */ AutoAddTracker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AutoAddTracker$contentObserver$1(AutoAddTracker autoAddTracker, Handler handler) {
        super(handler);
        this.this$0 = autoAddTracker;
    }

    public final void onChange(boolean z, Collection<? extends Uri> collection, int i, int i2) {
        AutoAddTracker autoAddTracker = this.this$0;
        if (i2 == autoAddTracker.userId) {
            autoAddTracker.loadTiles();
        }
    }
}
