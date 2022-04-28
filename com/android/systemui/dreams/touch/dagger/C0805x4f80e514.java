package com.android.systemui.dreams.touch.dagger;

import com.android.p012wm.shell.animation.FlingAnimationUtils;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory */
public final class C0805x4f80e514 implements Factory<FlingAnimationUtils> {
    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsClosing(Provider<FlingAnimationUtils.Builder> provider) {
        FlingAnimationUtils.Builder builder = provider.get();
        builder.reset();
        builder.mMaxLengthSeconds = 0.6f;
        builder.mSpeedUpFactor = 0.6f;
        return builder.build();
    }
}
