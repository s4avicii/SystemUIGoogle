package com.android.systemui.accessibility;

import android.view.Choreographer;
import android.view.WindowManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MagnificationModeSwitch$$ExternalSyntheticLambda0 implements Choreographer.FrameCallback {
    public final /* synthetic */ MagnificationModeSwitch f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ MagnificationModeSwitch$$ExternalSyntheticLambda0(MagnificationModeSwitch magnificationModeSwitch, float f, float f2) {
        this.f$0 = magnificationModeSwitch;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void doFrame(long j) {
        MagnificationModeSwitch magnificationModeSwitch = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        Objects.requireNonNull(magnificationModeSwitch);
        WindowManager.LayoutParams layoutParams = magnificationModeSwitch.mParams;
        layoutParams.x = (int) (((float) layoutParams.x) + f);
        layoutParams.y = (int) (((float) layoutParams.y) + f2);
        magnificationModeSwitch.updateButtonViewLayoutIfNeeded();
    }
}
