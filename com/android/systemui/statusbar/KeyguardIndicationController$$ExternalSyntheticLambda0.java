package com.android.systemui.statusbar;

import com.android.systemui.dock.DockManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda0 implements DockManager.AlignmentStateListener {
    public final /* synthetic */ KeyguardIndicationController f$0;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda0(KeyguardIndicationController keyguardIndicationController) {
        this.f$0 = keyguardIndicationController;
    }

    public final void onAlignmentStateChanged(int i) {
        KeyguardIndicationController keyguardIndicationController = this.f$0;
        Objects.requireNonNull(keyguardIndicationController);
        keyguardIndicationController.mHandler.post(new KeyguardIndicationController$$ExternalSyntheticLambda1(keyguardIndicationController, i, 0));
    }
}
