package com.android.keyguard;

import android.view.KeyEvent;
import android.widget.TextView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPasswordViewController$$ExternalSyntheticLambda0 implements TextView.OnEditorActionListener {
    public final /* synthetic */ KeyguardPasswordViewController f$0;

    public /* synthetic */ KeyguardPasswordViewController$$ExternalSyntheticLambda0(KeyguardPasswordViewController keyguardPasswordViewController) {
        this.f$0 = keyguardPasswordViewController;
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
        Objects.requireNonNull(keyguardPasswordViewController);
        if (keyEvent == null && (i == 0 || i == 6 || i == 5)) {
            z = true;
        } else {
            z = false;
        }
        if (keyEvent == null || !KeyEvent.isConfirmKey(keyEvent.getKeyCode()) || keyEvent.getAction() != 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z && !z2) {
            return false;
        }
        keyguardPasswordViewController.verifyPasswordAndUnlock();
        return true;
    }
}
