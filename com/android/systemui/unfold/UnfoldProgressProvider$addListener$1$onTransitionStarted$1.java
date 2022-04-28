package com.android.systemui.unfold;

import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import java.util.Objects;

/* compiled from: UnfoldProgressProvider.kt */
public final class UnfoldProgressProvider$addListener$1$onTransitionStarted$1 implements Runnable {
    public final /* synthetic */ ShellUnfoldProgressProvider.UnfoldListener $listener;

    public UnfoldProgressProvider$addListener$1$onTransitionStarted$1(ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.$listener = unfoldListener;
    }

    public final void run() {
        Objects.requireNonNull(this.$listener);
    }
}
