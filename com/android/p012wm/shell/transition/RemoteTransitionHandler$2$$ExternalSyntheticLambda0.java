package com.android.p012wm.shell.transition;

import android.os.IBinder;
import android.util.Log;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.RemoteTransitionHandler;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$2$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RemoteTransitionHandler$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ RemoteTransitionHandler.C19422 f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;
    public final /* synthetic */ WindowContainerTransaction f$3;

    public /* synthetic */ RemoteTransitionHandler$2$$ExternalSyntheticLambda0(RemoteTransitionHandler.C19422 r1, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = r1;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }

    public final void run() {
        RemoteTransitionHandler.C19422 r0 = this.f$0;
        IBinder iBinder = this.f$1;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
        WindowContainerTransaction windowContainerTransaction = this.f$3;
        Objects.requireNonNull(r0);
        if (!RemoteTransitionHandler.this.mRequestedRemotes.containsKey(iBinder)) {
            Log.e("RemoteTransitionHandler", "Merged transition finished after it's mergeTarget (the transition it was supposed to merge into). This usually means that the mergeTarget's RemoteTransition impl erroneously accepted/ran the merge request after finishing the mergeTarget");
        }
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
    }
}
