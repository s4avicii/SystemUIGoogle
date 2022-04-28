package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0831x58d1e4b6 implements Consumer {
    public static final /* synthetic */ C0831x58d1e4b6 INSTANCE = new C0831x58d1e4b6();

    public final void accept(Object obj) {
        StatusBar statusBar = (StatusBar) obj;
        int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(statusBar);
        statusBar.mCommandQueueCallbacks.animateExpandNotificationsPanel();
    }
}
