package com.google.android.systemui.assist;

import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AssistManagerGoogle$$ExternalSyntheticLambda0 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ AssistManagerGoogle f$0;

    public /* synthetic */ AssistManagerGoogle$$ExternalSyntheticLambda0(AssistManagerGoogle assistManagerGoogle) {
        this.f$0 = assistManagerGoogle;
    }

    public final void onNavigationModeChanged(int i) {
        AssistManagerGoogle assistManagerGoogle = this.f$0;
        Objects.requireNonNull(assistManagerGoogle);
        assistManagerGoogle.mNavigationMode = i;
    }
}
