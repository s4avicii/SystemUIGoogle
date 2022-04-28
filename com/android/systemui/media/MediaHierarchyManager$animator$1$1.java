package com.android.systemui.media;

import android.animation.ValueAnimator;
import android.util.MathUtils;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager$animator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ValueAnimator $this_apply;
    public final /* synthetic */ MediaHierarchyManager this$0;

    public MediaHierarchyManager$animator$1$1(MediaHierarchyManager mediaHierarchyManager, ValueAnimator valueAnimator) {
        this.this$0 = mediaHierarchyManager;
        this.$this_apply = valueAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        float f2;
        this.this$0.updateTargetState();
        float animatedFraction = this.$this_apply.getAnimatedFraction();
        MediaHierarchyManager mediaHierarchyManager = this.this$0;
        if (mediaHierarchyManager.isCrossFadeAnimatorRunning) {
            mediaHierarchyManager.animationCrossFadeProgress = MathUtils.lerp(mediaHierarchyManager.animationStartCrossFadeProgress, 1.0f, this.$this_apply.getAnimatedFraction());
            float f3 = this.this$0.animationCrossFadeProgress;
            int i = (f3 > 0.5f ? 1 : (f3 == 0.5f ? 0 : -1));
            if (i < 0) {
                f2 = 0.0f;
            } else {
                f2 = 1.0f;
            }
            if (i <= 0) {
                f = 1.0f - (f3 / 0.5f);
            } else {
                f = (f3 - 0.5f) / 0.5f;
            }
            animatedFraction = f2;
        } else {
            f = MathUtils.lerp(mediaHierarchyManager.animationStartAlpha, 1.0f, this.$this_apply.getAnimatedFraction());
        }
        MediaHierarchyManager mediaHierarchyManager2 = this.this$0;
        MediaHierarchyManager.interpolateBounds(mediaHierarchyManager2.animationStartBounds, mediaHierarchyManager2.targetBounds, animatedFraction, mediaHierarchyManager2.currentBounds);
        MediaHierarchyManager mediaHierarchyManager3 = this.this$0;
        mediaHierarchyManager3.applyState(mediaHierarchyManager3.currentBounds, f, false);
    }
}
