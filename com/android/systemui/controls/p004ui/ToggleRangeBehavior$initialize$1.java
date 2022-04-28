package com.android.systemui.controls.p004ui;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.controls.p004ui.ToggleRangeBehavior;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ToggleRangeBehavior$initialize$1 */
/* compiled from: ToggleRangeBehavior.kt */
public final class ToggleRangeBehavior$initialize$1 implements View.OnTouchListener {
    public final /* synthetic */ GestureDetector $gestureDetector;
    public final /* synthetic */ ToggleRangeBehavior.ToggleRangeGestureListener $gestureListener;
    public final /* synthetic */ ToggleRangeBehavior this$0;

    public ToggleRangeBehavior$initialize$1(GestureDetector gestureDetector, ToggleRangeBehavior.ToggleRangeGestureListener toggleRangeGestureListener, ToggleRangeBehavior toggleRangeBehavior) {
        this.$gestureDetector = gestureDetector;
        this.$gestureListener = toggleRangeGestureListener;
        this.this$0 = toggleRangeBehavior;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.$gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
            ToggleRangeBehavior.ToggleRangeGestureListener toggleRangeGestureListener = this.$gestureListener;
            Objects.requireNonNull(toggleRangeGestureListener);
            if (toggleRangeGestureListener.isDragging) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
                ToggleRangeBehavior.ToggleRangeGestureListener toggleRangeGestureListener2 = this.$gestureListener;
                Objects.requireNonNull(toggleRangeGestureListener2);
                toggleRangeGestureListener2.isDragging = false;
                this.this$0.endUpdateRange();
            }
        }
        return false;
    }
}
