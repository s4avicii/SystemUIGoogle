package com.android.p012wm.shell.transition;

import android.os.IBinder;
import android.util.Log;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneShotRemoteHandler$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ OneShotRemoteHandler f$0;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$1;

    public /* synthetic */ OneShotRemoteHandler$$ExternalSyntheticLambda0(OneShotRemoteHandler oneShotRemoteHandler, Transitions.TransitionFinishCallback transitionFinishCallback) {
        this.f$0 = oneShotRemoteHandler;
        this.f$1 = transitionFinishCallback;
    }

    public final void binderDied() {
        OneShotRemoteHandler oneShotRemoteHandler = this.f$0;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$1;
        Objects.requireNonNull(oneShotRemoteHandler);
        Log.e("ShellTransitions", "Remote transition died, finishing");
        oneShotRemoteHandler.mMainExecutor.execute(new PowerUI$$ExternalSyntheticLambda0(transitionFinishCallback, 6));
    }
}
