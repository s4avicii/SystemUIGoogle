package com.android.systemui.unfold.util;

import android.animation.ValueAnimator;
import android.database.ContentObserver;
import android.os.Handler;
import java.util.Objects;

/* renamed from: com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider$animatorDurationScaleObserver$1 */
/* compiled from: ScaleAwareTransitionProgressProvider.kt */
public final class C1698x746b6035 extends ContentObserver {
    public final /* synthetic */ ScaleAwareTransitionProgressProvider this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1698x746b6035(ScaleAwareTransitionProgressProvider scaleAwareTransitionProgressProvider) {
        super((Handler) null);
        this.this$0 = scaleAwareTransitionProgressProvider;
    }

    public final void onChange(boolean z) {
        ScaleAwareTransitionProgressProvider scaleAwareTransitionProgressProvider = this.this$0;
        Objects.requireNonNull(scaleAwareTransitionProgressProvider);
        scaleAwareTransitionProgressProvider.scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(ValueAnimator.areAnimatorsEnabled());
    }
}
