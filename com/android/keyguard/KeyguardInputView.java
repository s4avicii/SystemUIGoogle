package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;

public abstract class KeyguardInputView extends LinearLayout {
    public Runnable mOnFinishImeAnimationRunnable;

    public KeyguardInputView(Context context) {
        super(context);
    }

    public boolean disallowInterceptTouch(MotionEvent motionEvent) {
        return false;
    }

    public abstract String getTitle();

    public void startAppearAnimation() {
    }

    public boolean startDisappearAnimation(QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0) {
        return false;
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
