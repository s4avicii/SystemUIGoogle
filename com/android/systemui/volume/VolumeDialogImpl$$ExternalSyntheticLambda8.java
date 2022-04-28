package com.android.systemui.volume;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda8 implements View.OnHoverListener {
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda8(VolumeDialogImpl volumeDialogImpl) {
        this.f$0 = volumeDialogImpl;
    }

    public final boolean onHover(View view, MotionEvent motionEvent) {
        boolean z;
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        Objects.requireNonNull(volumeDialogImpl);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9 || actionMasked == 7) {
            z = true;
        } else {
            z = false;
        }
        volumeDialogImpl.mHovering = z;
        volumeDialogImpl.rescheduleTimeoutH();
        return true;
    }
}
