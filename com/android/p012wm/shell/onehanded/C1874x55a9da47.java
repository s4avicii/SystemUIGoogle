package com.android.p012wm.shell.onehanded;

import android.view.SurfaceControl;
import com.android.p012wm.shell.onehanded.OneHandedAnimationController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationController$OneHandedTransitionAnimator$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1874x55a9da47 implements Consumer {
    public final /* synthetic */ OneHandedAnimationController.OneHandedTransitionAnimator f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;

    public /* synthetic */ C1874x55a9da47(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator, SurfaceControl.Transaction transaction) {
        this.f$0 = oneHandedTransitionAnimator;
        this.f$1 = transaction;
    }

    public final void accept(Object obj) {
        OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = this.f$0;
        Objects.requireNonNull(oneHandedTransitionAnimator);
        ((OneHandedAnimationCallback) obj).onOneHandedAnimationEnd(oneHandedTransitionAnimator);
    }
}
