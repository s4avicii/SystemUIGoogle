package com.android.systemui.unfold;

import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import java.util.concurrent.Executor;

/* compiled from: UnfoldProgressProvider.kt */
public final class UnfoldProgressProvider implements ShellUnfoldProgressProvider {
    public final UnfoldTransitionProgressProvider unfoldProgressProvider;

    public final void addListener(Executor executor, ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.unfoldProgressProvider.addCallback(new UnfoldProgressProvider$addListener$1(executor, unfoldListener));
    }

    public UnfoldProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.unfoldProgressProvider = unfoldTransitionProgressProvider;
    }
}
