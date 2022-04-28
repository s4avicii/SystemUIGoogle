package com.android.p012wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.Transitions;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda2 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ SplitScreenTransitions f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda2(SplitScreenTransitions splitScreenTransitions) {
        this.f$0 = splitScreenTransitions;
    }

    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
        this.f$0.onFinish(windowContainerTransaction);
    }
}
