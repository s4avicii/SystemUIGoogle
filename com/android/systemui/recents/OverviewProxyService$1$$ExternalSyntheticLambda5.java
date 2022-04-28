package com.android.systemui.recents;

import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda5(OverviewProxyService.C10571 r1, float f) {
        this.f$0 = r1;
        this.f$1 = f;
    }

    public final void run() {
        OverviewProxyService.C10571 r0 = this.f$0;
        float f = this.f$1;
        Objects.requireNonNull(r0);
        OverviewProxyService overviewProxyService = OverviewProxyService.this;
        Objects.requireNonNull(overviewProxyService);
        int size = overviewProxyService.mConnectionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((OverviewProxyService.OverviewProxyListener) overviewProxyService.mConnectionCallbacks.get(size)).onAssistantProgress(f);
            } else {
                return;
            }
        }
    }
}
