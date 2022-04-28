package com.android.systemui.statusbar.dagger;

import com.android.systemui.animation.ActivityLaunchAnimator;
import dagger.internal.Factory;

public final class StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory implements Factory<ActivityLaunchAnimator> {

    public static final class InstanceHolder {
        public static final StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory INSTANCE = new StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory();
    }

    public final Object get() {
        return new ActivityLaunchAnimator();
    }
}
