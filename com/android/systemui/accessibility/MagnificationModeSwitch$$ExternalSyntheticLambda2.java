package com.android.systemui.accessibility;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MagnificationModeSwitch$$ExternalSyntheticLambda2 implements View.OnTouchListener {
    public final /* synthetic */ MagnificationModeSwitch f$0;

    public /* synthetic */ MagnificationModeSwitch$$ExternalSyntheticLambda2(MagnificationModeSwitch magnificationModeSwitch) {
        this.f$0 = magnificationModeSwitch;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        MagnificationModeSwitch magnificationModeSwitch = this.f$0;
        Objects.requireNonNull(magnificationModeSwitch);
        if (!magnificationModeSwitch.mIsVisible) {
            return false;
        }
        return magnificationModeSwitch.mGestureDetector.onTouch(motionEvent);
    }
}
