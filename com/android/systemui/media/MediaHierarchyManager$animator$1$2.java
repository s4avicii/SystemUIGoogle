package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager$animator$1$2 extends AnimatorListenerAdapter {
    public boolean cancelled;
    public final /* synthetic */ MediaHierarchyManager this$0;

    public final void onAnimationCancel(Animator animator) {
        this.cancelled = true;
        MediaHierarchyManager mediaHierarchyManager = this.this$0;
        mediaHierarchyManager.animationPending = false;
        View view = mediaHierarchyManager.rootView;
        if (view != null) {
            view.removeCallbacks(mediaHierarchyManager.startAnimation);
        }
    }

    public final void onAnimationStart(Animator animator) {
        this.cancelled = false;
        this.this$0.animationPending = false;
    }

    public MediaHierarchyManager$animator$1$2(MediaHierarchyManager mediaHierarchyManager) {
        this.this$0 = mediaHierarchyManager;
    }

    public final void onAnimationEnd(Animator animator) {
        MediaHierarchyManager mediaHierarchyManager = this.this$0;
        mediaHierarchyManager.isCrossFadeAnimatorRunning = false;
        if (!this.cancelled) {
            mediaHierarchyManager.applyTargetStateIfNotAnimating();
        }
    }
}
