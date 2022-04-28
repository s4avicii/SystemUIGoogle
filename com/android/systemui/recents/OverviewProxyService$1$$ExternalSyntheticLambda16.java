package com.android.systemui.recents;

import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda16 implements Consumer {
    public static final /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda16 INSTANCE = new OverviewProxyService$1$$ExternalSyntheticLambda16(0);
    public static final /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda16 INSTANCE$1 = new OverviewProxyService$1$$ExternalSyntheticLambda16(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda16(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StatusBar statusBar = (StatusBar) obj;
                Objects.requireNonNull(statusBar);
                statusBar.mCommandQueueCallbacks.togglePanel();
                return;
            default:
                int i = OneHandedController.IOneHandedImpl.$r8$clinit;
                ((OneHandedController) obj).stopOneHanded();
                return;
        }
    }
}
