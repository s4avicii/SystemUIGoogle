package com.android.p012wm.shell.transition;

import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ TransitionInfo.Change f$1;
    public final /* synthetic */ Animation f$2;
    public final /* synthetic */ SurfaceControl.Transaction f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda5(DefaultTransitionHandler defaultTransitionHandler, TransitionInfo.Change change, Animation animation, SurfaceControl.Transaction transaction) {
        this.f$0 = defaultTransitionHandler;
        this.f$1 = change;
        this.f$2 = animation;
        this.f$3 = transaction;
    }

    public final void accept(Object obj) {
        DefaultTransitionHandler defaultTransitionHandler = this.f$0;
        TransitionInfo.Change change = this.f$1;
        Animation animation = this.f$2;
        SurfaceControl.Transaction transaction = this.f$3;
        Objects.requireNonNull(defaultTransitionHandler);
        DefaultTransitionHandler.edgeExtendWindow(change, animation, (SurfaceControl.Transaction) obj, transaction);
    }
}
