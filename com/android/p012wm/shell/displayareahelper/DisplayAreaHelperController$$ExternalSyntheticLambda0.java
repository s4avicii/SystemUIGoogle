package com.android.p012wm.shell.displayareahelper;

import android.view.SurfaceControl;
import com.android.p012wm.shell.RootDisplayAreaOrganizer;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$init$1;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.displayareahelper.DisplayAreaHelperController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DisplayAreaHelperController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DisplayAreaHelperController f$0;
    public final /* synthetic */ int f$1 = 0;
    public final /* synthetic */ SurfaceControl.Builder f$2;
    public final /* synthetic */ Consumer f$3;

    public /* synthetic */ DisplayAreaHelperController$$ExternalSyntheticLambda0(DisplayAreaHelperController displayAreaHelperController, SurfaceControl.Builder builder, UnfoldLightRevealOverlayAnimation$init$1 unfoldLightRevealOverlayAnimation$init$1) {
        this.f$0 = displayAreaHelperController;
        this.f$2 = builder;
        this.f$3 = unfoldLightRevealOverlayAnimation$init$1;
    }

    public final void run() {
        DisplayAreaHelperController displayAreaHelperController = this.f$0;
        int i = this.f$1;
        SurfaceControl.Builder builder = this.f$2;
        Consumer consumer = this.f$3;
        Objects.requireNonNull(displayAreaHelperController);
        RootDisplayAreaOrganizer rootDisplayAreaOrganizer = displayAreaHelperController.mRootDisplayAreaOrganizer;
        Objects.requireNonNull(rootDisplayAreaOrganizer);
        SurfaceControl surfaceControl = rootDisplayAreaOrganizer.mLeashes.get(i);
        if (surfaceControl != null) {
            builder.setParent(surfaceControl);
        }
        consumer.accept(builder);
    }
}
