package com.android.systemui.shared.rotation;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RotationButtonController$$ExternalSyntheticLambda0 implements View.OnHoverListener {
    public final /* synthetic */ RotationButtonController f$0;

    public /* synthetic */ RotationButtonController$$ExternalSyntheticLambda0(RotationButtonController rotationButtonController) {
        this.f$0 = rotationButtonController;
    }

    public final boolean onHover(View view, MotionEvent motionEvent) {
        boolean z;
        RotationButtonController rotationButtonController = this.f$0;
        Objects.requireNonNull(rotationButtonController);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9 || actionMasked == 7) {
            z = true;
        } else {
            z = false;
        }
        rotationButtonController.mHoveringRotationSuggestion = z;
        rotationButtonController.rescheduleRotationTimeout(true);
        return false;
    }
}
