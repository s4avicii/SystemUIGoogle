package com.android.systemui.unfold.util;

import android.content.Context;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Objects;

/* compiled from: NaturalRotationUnfoldProgressProvider.kt */
public final class NaturalRotationUnfoldProgressProvider implements UnfoldTransitionProgressProvider {
    public final Context context;
    public boolean isNaturalRotation;
    public final RotationWatcher rotationWatcher = new RotationWatcher();
    public final ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider;
    public final IWindowManager windowManagerInterface;

    /* compiled from: NaturalRotationUnfoldProgressProvider.kt */
    public final class RotationWatcher extends IRotationWatcher.Stub {
        public RotationWatcher() {
        }

        public final void onRotationChanged(int i) {
            boolean z;
            NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = NaturalRotationUnfoldProgressProvider.this;
            Objects.requireNonNull(naturalRotationUnfoldProgressProvider);
            if (i == 0 || i == 2) {
                z = true;
            } else {
                z = false;
            }
            if (naturalRotationUnfoldProgressProvider.isNaturalRotation != z) {
                naturalRotationUnfoldProgressProvider.isNaturalRotation = z;
                naturalRotationUnfoldProgressProvider.scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(z);
            }
        }
    }

    public final void addCallback(Object obj) {
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider2 = this.scopedUnfoldTransitionProgressProvider;
        Objects.requireNonNull(scopedUnfoldTransitionProgressProvider2);
        scopedUnfoldTransitionProgressProvider2.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void removeCallback(Object obj) {
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider2 = this.scopedUnfoldTransitionProgressProvider;
        Objects.requireNonNull(scopedUnfoldTransitionProgressProvider2);
        scopedUnfoldTransitionProgressProvider2.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public NaturalRotationUnfoldProgressProvider(Context context2, IWindowManager iWindowManager, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.context = context2;
        this.windowManagerInterface = iWindowManager;
        this.scopedUnfoldTransitionProgressProvider = new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider);
    }
}
