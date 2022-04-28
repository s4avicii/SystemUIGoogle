package com.android.keyguard;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPatternViewController$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    public final /* synthetic */ KeyguardPatternViewController f$0;

    public /* synthetic */ KeyguardPatternViewController$$ExternalSyntheticLambda1(KeyguardPatternViewController keyguardPatternViewController) {
        this.f$0 = keyguardPatternViewController;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        KeyguardPatternViewController keyguardPatternViewController = this.f$0;
        Objects.requireNonNull(keyguardPatternViewController);
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        keyguardPatternViewController.mFalsingCollector.avoidGesture();
        return false;
    }
}
