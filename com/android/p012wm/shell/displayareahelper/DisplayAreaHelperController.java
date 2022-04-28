package com.android.p012wm.shell.displayareahelper;

import android.view.SurfaceControl;
import com.android.p012wm.shell.RootDisplayAreaOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$init$1;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.displayareahelper.DisplayAreaHelperController */
public final class DisplayAreaHelperController implements DisplayAreaHelper {
    public final Executor mExecutor;
    public final RootDisplayAreaOrganizer mRootDisplayAreaOrganizer;

    public final void attachToRootDisplayArea(SurfaceControl.Builder builder, UnfoldLightRevealOverlayAnimation$init$1 unfoldLightRevealOverlayAnimation$init$1) {
        this.mExecutor.execute(new DisplayAreaHelperController$$ExternalSyntheticLambda0(this, builder, unfoldLightRevealOverlayAnimation$init$1));
    }

    public DisplayAreaHelperController(ShellExecutor shellExecutor, RootDisplayAreaOrganizer rootDisplayAreaOrganizer) {
        this.mExecutor = shellExecutor;
        this.mRootDisplayAreaOrganizer = rootDisplayAreaOrganizer;
    }
}
