package com.android.systemui.util.wakelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.animation.Animation;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.util.Assert;

public final class KeepAwakeAnimationListener extends AnimatorListenerAdapter implements Animation.AnimationListener {
    @VisibleForTesting
    public static WakeLock sWakeLock;

    public final void onAnimationEnd(Animator animator) {
        Assert.isMainThread();
        sWakeLock.release("KeepAwakeAnimListener");
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationStart(Animator animator) {
        Assert.isMainThread();
        sWakeLock.acquire("KeepAwakeAnimListener");
    }

    public KeepAwakeAnimationListener(Context context) {
        Assert.isMainThread();
        if (sWakeLock == null) {
            sWakeLock = WakeLock.createPartial(context, "animation");
        }
    }

    public final void onAnimationEnd(Animation animation) {
        Assert.isMainThread();
        sWakeLock.release("KeepAwakeAnimListener");
    }

    public final void onAnimationStart(Animation animation) {
        Assert.isMainThread();
        sWakeLock.acquire("KeepAwakeAnimListener");
    }
}
