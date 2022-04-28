package com.android.systemui.recents;

import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda7(OverviewProxyService.C10571 r1, int i) {
        this.f$0 = r1;
        this.f$1 = i;
    }

    public final void run() {
        OverviewProxyService.C10571 r0 = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(r0);
        OverviewProxyService overviewProxyService = OverviewProxyService.this;
        Objects.requireNonNull(overviewProxyService);
        int size = overviewProxyService.mConnectionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((OverviewProxyService.OverviewProxyListener) overviewProxyService.mConnectionCallbacks.get(size)).onPrioritizedRotation(i);
            } else {
                return;
            }
        }
    }
}
