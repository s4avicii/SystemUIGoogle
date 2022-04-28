package com.android.p012wm.shell.transition;

import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.Transitions;

/* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneShotRemoteHandler$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SurfaceControl.Transaction f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;
    public final /* synthetic */ WindowContainerTransaction f$3;

    public /* synthetic */ OneShotRemoteHandler$1$$ExternalSyntheticLambda0(SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = transaction;
        this.f$1 = transaction2;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }

    public final void run() {
        SurfaceControl.Transaction transaction = this.f$0;
        SurfaceControl.Transaction transaction2 = this.f$1;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
        WindowContainerTransaction windowContainerTransaction = this.f$3;
        if (transaction != null) {
            transaction2.merge(transaction);
        }
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
    }
}
