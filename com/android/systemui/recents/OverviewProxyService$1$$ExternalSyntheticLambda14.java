package com.android.systemui.recents;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda14(boolean z) {
        this.f$0 = z;
    }

    public final void accept(Object obj) {
        ((LegacySplitScreen) obj).setMinimized(this.f$0);
    }
}
