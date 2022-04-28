package com.android.p012wm.shell.pip;

import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.Transitions;
import com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTransition$$ExternalSyntheticLambda1 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ PipTransition f$0;
    public final /* synthetic */ TransitionInfo.Change f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;

    public /* synthetic */ PipTransition$$ExternalSyntheticLambda1(PipTransition pipTransition, TransitionInfo.Change change, Transitions$$ExternalSyntheticLambda1 transitions$$ExternalSyntheticLambda1) {
        this.f$0 = pipTransition;
        this.f$1 = change;
        this.f$2 = transitions$$ExternalSyntheticLambda1;
    }

    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
        PipTransition pipTransition = this.f$0;
        TransitionInfo.Change change = this.f$1;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
        Objects.requireNonNull(pipTransition);
        pipTransition.mPipOrganizer.onExitPipFinished(change.getTaskInfo());
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
    }
}
