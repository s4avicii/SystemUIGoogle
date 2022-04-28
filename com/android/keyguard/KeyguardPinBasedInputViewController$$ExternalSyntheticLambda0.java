package com.android.keyguard;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0 implements View.OnKeyListener {
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController) {
        this.f$0 = keyguardPinBasedInputViewController;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
        Objects.requireNonNull(keyguardPinBasedInputViewController);
        if (keyEvent.getAction() == 0) {
            return ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).onKeyDown(i, keyEvent);
        }
        return false;
    }
}
