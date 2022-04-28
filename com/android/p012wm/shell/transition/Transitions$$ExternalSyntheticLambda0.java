package com.android.p012wm.shell.transition;

import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda0 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ Transitions f$0;
    public final /* synthetic */ Transitions.ActiveTransition f$1;

    public /* synthetic */ Transitions$$ExternalSyntheticLambda0(Transitions transitions, Transitions.ActiveTransition activeTransition) {
        this.f$0 = transitions;
        this.f$1 = activeTransition;
    }

    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
        Transitions transitions = this.f$0;
        Transitions.ActiveTransition activeTransition = this.f$1;
        Objects.requireNonNull(transitions);
        transitions.onFinish(activeTransition.mToken, windowContainerTransaction, false);
    }
}
