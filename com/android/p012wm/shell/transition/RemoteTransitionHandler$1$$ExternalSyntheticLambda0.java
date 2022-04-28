package com.android.p012wm.shell.transition;

import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.RemoteTransitionHandler;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RemoteTransitionHandler$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ RemoteTransitionHandler.C19411 f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$4;
    public final /* synthetic */ WindowContainerTransaction f$5;

    public /* synthetic */ RemoteTransitionHandler$1$$ExternalSyntheticLambda0(RemoteTransitionHandler.C19411 r1, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = r1;
        this.f$1 = transaction;
        this.f$2 = transaction2;
        this.f$3 = iBinder;
        this.f$4 = transitionFinishCallback;
        this.f$5 = windowContainerTransaction;
    }

    public final void run() {
        RemoteTransitionHandler.C19411 r0 = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl.Transaction transaction2 = this.f$2;
        IBinder iBinder = this.f$3;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$4;
        WindowContainerTransaction windowContainerTransaction = this.f$5;
        if (transaction != null) {
            Objects.requireNonNull(r0);
            transaction2.merge(transaction);
        }
        RemoteTransitionHandler.this.mRequestedRemotes.remove(iBinder);
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
    }
}
