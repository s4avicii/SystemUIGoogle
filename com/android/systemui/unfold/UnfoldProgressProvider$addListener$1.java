package com.android.systemui.unfold;

import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.concurrent.Executor;

/* compiled from: UnfoldProgressProvider.kt */
public final class UnfoldProgressProvider$addListener$1 implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final /* synthetic */ Executor $executor;
    public final /* synthetic */ ShellUnfoldProgressProvider.UnfoldListener $listener;

    public UnfoldProgressProvider$addListener$1(Executor executor, ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.$executor = executor;
        this.$listener = unfoldListener;
    }

    public final void onTransitionFinished() {
        this.$executor.execute(new UnfoldProgressProvider$addListener$1$onTransitionFinished$1(this.$listener));
    }

    public final void onTransitionProgress(float f) {
        this.$executor.execute(new UnfoldProgressProvider$addListener$1$onTransitionProgress$1(this.$listener, f));
    }

    public final void onTransitionStarted() {
        this.$executor.execute(new UnfoldProgressProvider$addListener$1$onTransitionStarted$1(this.$listener));
    }
}
