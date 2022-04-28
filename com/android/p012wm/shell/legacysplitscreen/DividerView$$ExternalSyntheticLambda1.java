package com.android.p012wm.shell.legacysplitscreen;

import android.view.SurfaceControl;
import android.view.animation.PathInterpolator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.DividerView$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DividerView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ DividerView f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ DividerView$$ExternalSyntheticLambda1(DividerView dividerView, boolean z) {
        this.f$0 = dividerView;
        this.f$1 = z;
    }

    public final void run() {
        DividerView dividerView = this.f$0;
        boolean z = this.f$1;
        PathInterpolator pathInterpolator = DividerView.IME_ADJUST_INTERPOLATOR;
        Objects.requireNonNull(dividerView);
        SurfaceControl viewSurface = dividerView.mWindowManager.mSystemWindows.getViewSurface(dividerView);
        if (viewSurface != null) {
            SurfaceControl.Transaction transaction = dividerView.mTiles.getTransaction();
            if (z) {
                transaction.hide(viewSurface);
            } else {
                transaction.show(viewSurface);
            }
            dividerView.mImeController.setDimsHidden(transaction, z);
            transaction.apply();
            dividerView.mTiles.releaseTransaction(transaction);
        }
    }
}
