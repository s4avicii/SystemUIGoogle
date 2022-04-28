package com.android.keyguard;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController) {
        this.f$0 = keyguardPinBasedInputViewController;
    }

    public final boolean onLongClick(View view) {
        KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
        Objects.requireNonNull(keyguardPinBasedInputViewController);
        if (keyguardPinBasedInputViewController.mPasswordEntry.isEnabled()) {
            ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).resetPasswordText(true, true);
        }
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView;
        Objects.requireNonNull(keyguardPinBasedInputView);
        keyguardPinBasedInputView.performHapticFeedback(1, 3);
        return true;
    }
}
