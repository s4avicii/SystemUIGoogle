package com.android.p012wm.shell.legacysplitscreen;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1869x586845aa implements Runnable {
    public final /* synthetic */ LegacySplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ C1869x586845aa(LegacySplitScreenController.SplitScreenImpl splitScreenImpl, boolean z) {
        this.f$0 = splitScreenImpl;
        this.f$1 = z;
    }

    public final void run() {
        DividerView dividerView;
        LegacySplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(splitScreenImpl);
        LegacySplitScreenController legacySplitScreenController = LegacySplitScreenController.this;
        Objects.requireNonNull(legacySplitScreenController);
        if (legacySplitScreenController.isSplitActive() && (dividerView = legacySplitScreenController.mView) != null) {
            if (dividerView.mSurfaceHidden != z) {
                dividerView.mSurfaceHidden = z;
                dividerView.post(new DividerView$$ExternalSyntheticLambda1(dividerView, z));
            }
            legacySplitScreenController.mIsKeyguardShowing = z;
        }
    }
}
