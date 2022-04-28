package com.android.keyguard;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardHostViewController$$ExternalSyntheticLambda0 implements View.OnKeyListener {
    public final /* synthetic */ KeyguardHostViewController f$0;

    public /* synthetic */ KeyguardHostViewController$$ExternalSyntheticLambda0(KeyguardHostViewController keyguardHostViewController) {
        this.f$0 = keyguardHostViewController;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        KeyguardHostViewController keyguardHostViewController = this.f$0;
        Objects.requireNonNull(keyguardHostViewController);
        return keyguardHostViewController.interceptMediaKey(keyEvent);
    }
}
