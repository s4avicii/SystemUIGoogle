package com.android.systemui.accessibility;

import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemActions$$ExternalSyntheticLambda0 implements StatusBarWindowCallback {
    public final /* synthetic */ SystemActions f$0;

    public /* synthetic */ SystemActions$$ExternalSyntheticLambda0(SystemActions systemActions) {
        this.f$0 = systemActions;
    }

    public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4) {
        SystemActions systemActions = this.f$0;
        Objects.requireNonNull(systemActions);
        systemActions.registerOrUnregisterDismissNotificationShadeAction();
    }
}
