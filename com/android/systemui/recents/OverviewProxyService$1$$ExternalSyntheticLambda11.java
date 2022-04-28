package com.android.systemui.recents;

import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda11(OverviewProxyService.C10571 r1, boolean z, boolean z2) {
        this.f$0 = r1;
        this.f$1 = z;
        this.f$2 = z2;
    }

    public final void run() {
        OverviewProxyService.C10571 r0 = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        Objects.requireNonNull(r0);
        OverviewProxyService overviewProxyService = OverviewProxyService.this;
        Objects.requireNonNull(overviewProxyService);
        int size = overviewProxyService.mConnectionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((OverviewProxyService.OverviewProxyListener) overviewProxyService.mConnectionCallbacks.get(size)).onTaskbarStatusUpdated(z, z2);
            } else {
                return;
            }
        }
    }
}
