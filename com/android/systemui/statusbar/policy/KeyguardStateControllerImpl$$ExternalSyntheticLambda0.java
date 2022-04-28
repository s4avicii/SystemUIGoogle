package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStateControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public static final /* synthetic */ KeyguardStateControllerImpl$$ExternalSyntheticLambda0 INSTANCE = new KeyguardStateControllerImpl$$ExternalSyntheticLambda0();

    public final void accept(Object obj) {
        ((KeyguardStateController.Callback) obj).onKeyguardShowingChanged();
    }
}
