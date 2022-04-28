package com.android.p012wm.shell.animation;

import android.util.DisplayMetrics;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.animation.FlingAnimationUtils_Builder_Factory */
public final class FlingAnimationUtils_Builder_Factory implements Factory<FlingAnimationUtils.Builder> {
    public final Provider<DisplayMetrics> displayMetricsProvider;

    public final Object get() {
        return new FlingAnimationUtils.Builder(this.displayMetricsProvider.get());
    }

    public FlingAnimationUtils_Builder_Factory(Provider<DisplayMetrics> provider) {
        this.displayMetricsProvider = provider;
    }
}
