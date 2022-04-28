package com.android.keyguard;

import android.view.MotionEvent;
import com.android.systemui.Gefingerpoken;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSecurityContainer$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ MotionEvent f$0;

    public /* synthetic */ KeyguardSecurityContainer$$ExternalSyntheticLambda0(MotionEvent motionEvent) {
        this.f$0 = motionEvent;
    }

    public final boolean test(Object obj) {
        MotionEvent motionEvent = this.f$0;
        int i = KeyguardSecurityContainer.$r8$clinit;
        return ((Gefingerpoken) obj).onInterceptTouchEvent(motionEvent);
    }
}
