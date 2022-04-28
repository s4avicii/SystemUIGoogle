package com.android.systemui.recents;

import android.graphics.Rect;
import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda18 implements Supplier {
    public final /* synthetic */ OverviewProxyService.C10571 f$0;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda18(OverviewProxyService.C10571 r1) {
        this.f$0 = r1;
    }

    public final Object get() {
        OverviewProxyService.C10571 r1 = this.f$0;
        Objects.requireNonNull(r1);
        return (Rect) OverviewProxyService.this.mLegacySplitScreenOptional.map(OverviewProxyService$1$$ExternalSyntheticLambda17.INSTANCE).orElse((Object) null);
    }
}
