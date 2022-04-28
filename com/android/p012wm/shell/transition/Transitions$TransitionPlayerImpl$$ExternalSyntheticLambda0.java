package com.android.p012wm.shell.transition;

import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Transitions.TransitionPlayerImpl f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ TransitionInfo f$2;
    public final /* synthetic */ SurfaceControl.Transaction f$3;
    public final /* synthetic */ SurfaceControl.Transaction f$4;

    public /* synthetic */ Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda0(Transitions.TransitionPlayerImpl transitionPlayerImpl, IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        this.f$0 = transitionPlayerImpl;
        this.f$1 = iBinder;
        this.f$2 = transitionInfo;
        this.f$3 = transaction;
        this.f$4 = transaction2;
    }

    public final void run() {
        Transitions.TransitionPlayerImpl transitionPlayerImpl = this.f$0;
        IBinder iBinder = this.f$1;
        TransitionInfo transitionInfo = this.f$2;
        SurfaceControl.Transaction transaction = this.f$3;
        SurfaceControl.Transaction transaction2 = this.f$4;
        Objects.requireNonNull(transitionPlayerImpl);
        Transitions.this.onTransitionReady(iBinder, transitionInfo, transaction, transaction2);
    }
}
