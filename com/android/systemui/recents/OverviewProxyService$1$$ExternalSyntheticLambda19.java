package com.android.systemui.recents;

import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda19 implements Supplier {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda19(OverviewProxyService.C10571 r1, Runnable runnable) {
        this.f$0 = r1;
        this.f$1 = runnable;
    }

    public final Object get() {
        OverviewProxyService.C10571 r0 = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(r0);
        return Boolean.valueOf(OverviewProxyService.this.mHandler.post(runnable));
    }
}
