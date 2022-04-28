package com.android.systemui.recents;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda4(Object obj, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService.C10571 r0 = (OverviewProxyService.C10571) this.f$0;
                boolean z = this.f$1;
                Objects.requireNonNull(r0);
                OverviewProxyService.this.mHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda9(r0, z));
                return;
            default:
                LegacySplitScreenController legacySplitScreenController = (LegacySplitScreenController) this.f$0;
                boolean z2 = this.f$1;
                Objects.requireNonNull(legacySplitScreenController);
                if (legacySplitScreenController.mVisible) {
                    legacySplitScreenController.setHomeMinimized(z2);
                    return;
                }
                return;
        }
    }
}
