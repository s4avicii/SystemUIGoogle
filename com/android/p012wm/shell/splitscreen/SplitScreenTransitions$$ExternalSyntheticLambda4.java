package com.android.p012wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ SplitScreenTransitions f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Rect f$3;
    public final /* synthetic */ ValueAnimator f$4;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda4(SplitScreenTransitions splitScreenTransitions, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, ValueAnimator valueAnimator) {
        this.f$0 = splitScreenTransitions;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = rect;
        this.f$4 = valueAnimator;
    }

    public final void run() {
        SplitScreenTransitions splitScreenTransitions = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        Rect rect = this.f$3;
        ValueAnimator valueAnimator = this.f$4;
        Objects.requireNonNull(splitScreenTransitions);
        transaction.setWindowCrop(surfaceControl, 0, 0);
        transaction.setPosition(surfaceControl, (float) rect.left, (float) rect.top);
        transaction.apply();
        splitScreenTransitions.mTransactionPool.release(transaction);
        Transitions transitions = splitScreenTransitions.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mMainExecutor.execute(new QuickAccessWalletTile$$ExternalSyntheticLambda0(splitScreenTransitions, valueAnimator, 5));
    }
}
