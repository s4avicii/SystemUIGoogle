package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ RemoteAnimationTarget f$0;
    public final /* synthetic */ SyncRtSurfaceTransactionApplier f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda0(RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier) {
        this.f$0 = remoteAnimationTarget;
        this.f$1 = syncRtSurfaceTransactionApplier;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        RemoteAnimationTarget remoteAnimationTarget = this.f$0;
        SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.f$1;
        boolean z = KeyguardViewMediator.DEBUG;
        syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
    }
}
