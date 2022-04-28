package com.android.keyguard;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3 implements View.OnTouchListener {
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController) {
        this.f$0 = keyguardPinBasedInputViewController;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
        Objects.requireNonNull(keyguardPinBasedInputViewController);
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView;
        Objects.requireNonNull(keyguardPinBasedInputView);
        keyguardPinBasedInputView.performHapticFeedback(1, 3);
        return false;
    }
}
