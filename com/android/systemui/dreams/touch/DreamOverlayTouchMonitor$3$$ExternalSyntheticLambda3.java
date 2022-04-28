package com.android.systemui.dreams.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.google.android.systemui.smartspace.InterceptingViewPager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3 implements DreamOverlayTouchMonitor.Evaluator, InterceptingViewPager.EventProxy {
    public final /* synthetic */ Object f$0;

    public final boolean delegateEvent(MotionEvent motionEvent) {
        return Objects.requireNonNull((InterceptingViewPager) this.f$0);
    }

    public /* synthetic */ DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3(Object obj) {
        this.f$0 = obj;
    }

    public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
        return onGestureListener.onSingleTapUp((MotionEvent) this.f$0);
    }
}
