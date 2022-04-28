package com.google.android.systemui.columbus.feedback;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import com.android.systemui.assist.AssistManager;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: AssistInvocationEffect.kt */
public final class AssistInvocationEffect implements FeedbackEffect {
    public ValueAnimator animation;
    public final AssistInvocationEffect$animatorListener$1 animatorListener;
    public final AssistInvocationEffect$animatorUpdateListener$1 animatorUpdateListener;
    public final AssistManagerGoogle assistManager;
    public float progress;

    public final void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        ValueAnimator valueAnimator;
        boolean z;
        ValueAnimator valueAnimator2;
        boolean z2 = false;
        if (i == 0) {
            ValueAnimator valueAnimator3 = this.animation;
            if (valueAnimator3 != null && valueAnimator3.isRunning()) {
                z2 = true;
            }
            if (z2 && (valueAnimator = this.animation) != null) {
                valueAnimator.cancel();
            }
            this.animation = null;
        } else if (i == 1) {
            ValueAnimator valueAnimator4 = this.animation;
            if (valueAnimator4 != null && valueAnimator4.isRunning()) {
                z = true;
            } else {
                z = false;
            }
            if (z && (valueAnimator2 = this.animation) != null) {
                valueAnimator2.cancel();
            }
            this.animation = null;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.progress, 1.0f});
            ofFloat.setDuration(200);
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.addUpdateListener(this.animatorUpdateListener);
            ofFloat.addListener(this.animatorListener);
            this.animation = ofFloat;
            ofFloat.start();
        }
    }

    public AssistInvocationEffect(AssistManager assistManager2) {
        AssistManagerGoogle assistManagerGoogle;
        if (assistManager2 instanceof AssistManagerGoogle) {
            assistManagerGoogle = (AssistManagerGoogle) assistManager2;
        } else {
            assistManagerGoogle = null;
        }
        this.assistManager = assistManagerGoogle;
        this.animatorUpdateListener = new AssistInvocationEffect$animatorUpdateListener$1(this);
        this.animatorListener = new AssistInvocationEffect$animatorListener$1(this);
    }
}
