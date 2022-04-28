package com.android.systemui.unfold.util;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.provider.Settings;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Objects;

/* compiled from: ScaleAwareTransitionProgressProvider.kt */
public final class ScaleAwareTransitionProgressProvider implements UnfoldTransitionProgressProvider {
    public final ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider;

    /* compiled from: ScaleAwareTransitionProgressProvider.kt */
    public interface Factory {
        ScaleAwareTransitionProgressProvider wrap(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider);
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

    public ScaleAwareTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ContentResolver contentResolver) {
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider2 = new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider);
        this.scopedUnfoldTransitionProgressProvider = scopedUnfoldTransitionProgressProvider2;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("animator_duration_scale"), false, new C1698x746b6035(this));
        scopedUnfoldTransitionProgressProvider2.setReadyToHandleTransition(ValueAnimator.areAnimatorsEnabled());
    }
}
