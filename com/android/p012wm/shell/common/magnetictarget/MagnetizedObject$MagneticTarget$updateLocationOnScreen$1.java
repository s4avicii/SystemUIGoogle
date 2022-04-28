package com.android.p012wm.shell.common.magnetictarget;

import android.graphics.PointF;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1 */
/* compiled from: MagnetizedObject.kt */
public final class MagnetizedObject$MagneticTarget$updateLocationOnScreen$1 implements Runnable {
    public final /* synthetic */ MagnetizedObject.MagneticTarget this$0;

    public MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(MagnetizedObject.MagneticTarget magneticTarget) {
        this.this$0 = magneticTarget;
    }

    public final void run() {
        MagnetizedObject.MagneticTarget magneticTarget = this.this$0;
        Objects.requireNonNull(magneticTarget);
        magneticTarget.targetView.getLocationOnScreen(this.this$0.tempLoc);
        MagnetizedObject.MagneticTarget magneticTarget2 = this.this$0;
        Objects.requireNonNull(magneticTarget2);
        PointF pointF = magneticTarget2.centerOnScreen;
        MagnetizedObject.MagneticTarget magneticTarget3 = this.this$0;
        Objects.requireNonNull(magneticTarget3);
        float width = (((float) magneticTarget3.targetView.getWidth()) / 2.0f) + ((float) magneticTarget3.tempLoc[0]);
        MagnetizedObject.MagneticTarget magneticTarget4 = this.this$0;
        Objects.requireNonNull(magneticTarget4);
        float translationX = width - magneticTarget4.targetView.getTranslationX();
        MagnetizedObject.MagneticTarget magneticTarget5 = this.this$0;
        Objects.requireNonNull(magneticTarget5);
        float height = ((float) magneticTarget5.targetView.getHeight()) / 2.0f;
        MagnetizedObject.MagneticTarget magneticTarget6 = this.this$0;
        Objects.requireNonNull(magneticTarget6);
        pointF.set(translationX, (height + ((float) magneticTarget5.tempLoc[1])) - magneticTarget6.targetView.getTranslationY());
    }
}
