package com.android.systemui.unfold;

import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;

/* compiled from: UnfoldProgressProvider.kt */
public final class UnfoldProgressProvider$addListener$1$onTransitionFinished$1 implements Runnable {
    public final /* synthetic */ ShellUnfoldProgressProvider.UnfoldListener $listener;

    public UnfoldProgressProvider$addListener$1$onTransitionFinished$1(ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.$listener = unfoldListener;
    }

    public final void run() {
        this.$listener.onStateChangeFinished();
    }
}
