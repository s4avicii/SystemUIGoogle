package com.android.systemui.recents;

import com.android.p012wm.shell.pip.Pip;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda15 implements Consumer {
    public static final /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda15 INSTANCE = new OverviewProxyService$1$$ExternalSyntheticLambda15(0);
    public static final /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda15 INSTANCE$1 = new OverviewProxyService$1$$ExternalSyntheticLambda15(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda15(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = OverviewProxyService.C10571.$r8$clinit;
                ((Pip) obj).setPinnedStackAnimationType();
                return;
            default:
                C0961QS qs = (C0961QS) obj;
                int i2 = NotificationsQuickSettingsContainer.$r8$clinit;
                return;
        }
    }
}
