package com.android.systemui.recents;

import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda8(OverviewProxyService.C10571 r1, boolean z) {
        this.f$0 = r1;
        this.f$1 = z;
    }

    public final void run() {
        OverviewProxyService.C10571 r2 = this.f$0;
        Objects.requireNonNull(r2);
        int size = OverviewProxyService.this.mConnectionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((OverviewProxyService.OverviewProxyListener) OverviewProxyService.this.mConnectionCallbacks.get(size)).onOverviewShown();
            } else {
                return;
            }
        }
    }
}
