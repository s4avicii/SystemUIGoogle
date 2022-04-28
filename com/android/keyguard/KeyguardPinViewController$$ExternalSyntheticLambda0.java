package com.android.keyguard;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPinViewController$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ KeyguardPinViewController f$0;

    public /* synthetic */ KeyguardPinViewController$$ExternalSyntheticLambda0(KeyguardPinViewController keyguardPinViewController) {
        this.f$0 = keyguardPinViewController;
    }

    public final void onClick(View view) {
        KeyguardPinViewController keyguardPinViewController = this.f$0;
        Objects.requireNonNull(keyguardPinViewController);
        keyguardPinViewController.getKeyguardSecurityCallback().reset();
        keyguardPinViewController.getKeyguardSecurityCallback().onCancelClicked();
    }
}
