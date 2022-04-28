package com.android.systemui.unfold.util;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ScopedUnfoldTransitionProgressProvider.kt */
public final class ScopedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, UnfoldTransitionProgressProvider.TransitionProgressListener {
    public boolean isReadyToHandleTransition;
    public boolean isTransitionRunning;
    public float lastTransitionProgress;
    public final ArrayList listeners;
    public UnfoldTransitionProgressProvider source;

    public ScopedUnfoldTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.listeners = new ArrayList();
        this.lastTransitionProgress = -1.0f;
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider2 = this.source;
        if (unfoldTransitionProgressProvider2 != null) {
            unfoldTransitionProgressProvider2.removeCallback(this);
        }
        if (unfoldTransitionProgressProvider != null) {
            this.source = unfoldTransitionProgressProvider;
            unfoldTransitionProgressProvider.addCallback(this);
            return;
        }
        this.source = null;
    }

    public final void onTransitionStarted() {
        this.isTransitionRunning = true;
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
        }
    }

    public final void addCallback(Object obj) {
        this.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void onTransitionFinished() {
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
        }
        this.isTransitionRunning = false;
        this.lastTransitionProgress = -1.0f;
    }

    public final void onTransitionProgress(float f) {
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
            }
        }
        this.lastTransitionProgress = f;
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void setReadyToHandleTransition(boolean z) {
        if (this.isTransitionRunning) {
            boolean z2 = false;
            if (z) {
                Iterator it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
                }
                if (this.lastTransitionProgress == -1.0f) {
                    z2 = true;
                }
                if (!z2) {
                    Iterator it2 = this.listeners.iterator();
                    while (it2.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it2.next()).onTransitionProgress(this.lastTransitionProgress);
                    }
                }
            } else {
                this.isTransitionRunning = false;
                Iterator it3 = this.listeners.iterator();
                while (it3.hasNext()) {
                    ((UnfoldTransitionProgressProvider.TransitionProgressListener) it3.next()).onTransitionFinished();
                }
            }
        }
        this.isReadyToHandleTransition = z;
    }

    public ScopedUnfoldTransitionProgressProvider() {
        this((UnfoldTransitionProgressProvider) null);
    }
}
