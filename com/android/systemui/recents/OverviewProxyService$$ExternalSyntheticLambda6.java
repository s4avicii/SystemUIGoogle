package com.android.systemui.recents;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda6 implements Consumer {
    public static final /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda6 INSTANCE = new OverviewProxyService$$ExternalSyntheticLambda6(0);
    public static final /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda6 INSTANCE$1 = new OverviewProxyService$$ExternalSyntheticLambda6(1);
    public static final /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda6 INSTANCE$2 = new OverviewProxyService$$ExternalSyntheticLambda6(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((LegacySplitScreen) obj).setMinimized(false);
                return;
            case 1:
                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                return;
            default:
                ((PhonePipMenuController.Listener) obj).onPipDismiss();
                return;
        }
    }
}
