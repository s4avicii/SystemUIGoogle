package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0832x58d1e4b7 implements Consumer {
    public static final /* synthetic */ C0832x58d1e4b7 INSTANCE = new C0832x58d1e4b7();

    public final void accept(Object obj) {
        StatusBar statusBar = (StatusBar) obj;
        int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(statusBar);
        statusBar.mCommandQueueCallbacks.animateExpandSettingsPanel((String) null);
    }
}
