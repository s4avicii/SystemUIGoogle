package com.android.systemui.statusbar.phone;

import android.view.View;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda5(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    public final Object apply(Object obj) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        View view = (View) obj;
        Objects.requireNonNull(keyguardStatusBarViewController);
        return Float.valueOf(keyguardStatusBarViewController.mKeyguardHeadsUpShowingAmount);
    }
}
