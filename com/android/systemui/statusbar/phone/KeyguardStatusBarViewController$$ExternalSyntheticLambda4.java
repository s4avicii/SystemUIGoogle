package com.android.systemui.statusbar.phone;

import android.view.View;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda4 implements BiConsumer {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda4(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    public final void accept(Object obj, Object obj2) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        View view = (View) obj;
        Objects.requireNonNull(keyguardStatusBarViewController);
        keyguardStatusBarViewController.mKeyguardHeadsUpShowingAmount = ((Float) obj2).floatValue();
        keyguardStatusBarViewController.updateViewState();
    }
}
