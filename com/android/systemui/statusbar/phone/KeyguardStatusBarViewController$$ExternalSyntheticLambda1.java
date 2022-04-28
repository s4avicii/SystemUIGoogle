package com.android.systemui.statusbar.phone;

import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda1 implements View.OnApplyWindowInsetsListener {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda1(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        Objects.requireNonNull(keyguardStatusBarViewController);
        return ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).updateWindowInsets(windowInsets, keyguardStatusBarViewController.mInsetsProvider);
    }
}
