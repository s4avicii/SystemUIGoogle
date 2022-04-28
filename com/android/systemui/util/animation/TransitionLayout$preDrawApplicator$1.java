package com.android.systemui.util.animation;

import android.view.ViewTreeObserver;

/* compiled from: TransitionLayout.kt */
public final class TransitionLayout$preDrawApplicator$1 implements ViewTreeObserver.OnPreDrawListener {
    public final /* synthetic */ TransitionLayout this$0;

    public TransitionLayout$preDrawApplicator$1(TransitionLayout transitionLayout) {
        this.this$0 = transitionLayout;
    }

    public final boolean onPreDraw() {
        TransitionLayout transitionLayout = this.this$0;
        transitionLayout.updateScheduled = false;
        transitionLayout.getViewTreeObserver().removeOnPreDrawListener(this);
        TransitionLayout transitionLayout2 = this.this$0;
        transitionLayout2.isPreDrawApplicatorRegistered = false;
        transitionLayout2.applyCurrentState();
        return true;
    }
}
