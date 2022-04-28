package com.android.p012wm.shell.transition;

import android.util.Pair;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ TransitionFilter f$0;
    public final /* synthetic */ RemoteTransition f$1;

    public /* synthetic */ Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(TransitionFilter transitionFilter, RemoteTransition remoteTransition) {
        this.f$0 = transitionFilter;
        this.f$1 = remoteTransition;
    }

    public final void accept(Object obj) {
        TransitionFilter transitionFilter = this.f$0;
        RemoteTransition remoteTransition = this.f$1;
        RemoteTransitionHandler remoteTransitionHandler = ((Transitions) obj).mRemoteTransitionHandler;
        Objects.requireNonNull(remoteTransitionHandler);
        remoteTransitionHandler.handleDeath(remoteTransition.asBinder(), (Transitions$$ExternalSyntheticLambda1) null);
        remoteTransitionHandler.mFilters.add(new Pair(transitionFilter, remoteTransition));
    }
}
