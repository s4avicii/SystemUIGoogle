package com.android.p012wm.shell.pip.phone;

import android.content.ComponentName;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionState;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ Rect f$1;
    public final /* synthetic */ SurfaceControl f$2;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda3(ComponentName componentName, Rect rect, SurfaceControl surfaceControl) {
        this.f$1 = rect;
        this.f$2 = surfaceControl;
    }

    public final void accept(Object obj) {
        Rect rect = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        PipController pipController = (PipController) obj;
        Objects.requireNonNull(pipController);
        PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
        Objects.requireNonNull(pipTaskOrganizer);
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        if (pipTransitionState.mInSwipePipToHomeTransition) {
            pipTaskOrganizer.mPipBoundsState.setBounds(rect);
            pipTaskOrganizer.mSwipePipToHomeOverlay = surfaceControl;
        }
    }
}
