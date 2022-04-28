package com.android.systemui.statusbar.events;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$scheduleEvent$1 implements Runnable {
    public final /* synthetic */ SystemStatusAnimationScheduler this$0;

    public SystemStatusAnimationScheduler$scheduleEvent$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public final void run() {
        Objects.requireNonNull(this.this$0);
        SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
        systemStatusAnimationScheduler.animationState = 1;
        StatusBarWindowController statusBarWindowController = systemStatusAnimationScheduler.statusBarWindowController;
        Objects.requireNonNull(statusBarWindowController);
        StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
        state.mForceStatusBarVisible = true;
        statusBarWindowController.apply(state);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat.setDuration(250);
        ofFloat.addListener(this.this$0.systemAnimatorAdapter);
        ofFloat.addUpdateListener(this.this$0.systemUpdateListener);
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat2.setDuration(250);
        SystemStatusAnimationScheduler systemStatusAnimationScheduler2 = this.this$0;
        StatusEvent statusEvent = systemStatusAnimationScheduler2.scheduledEvent;
        Intrinsics.checkNotNull(statusEvent);
        ofFloat2.addListener(new SystemStatusAnimationScheduler.ChipAnimatorAdapter(2, statusEvent.getViewCreator()));
        ofFloat2.addUpdateListener(this.this$0.chipUpdateListener);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{ofFloat, ofFloat2});
        animatorSet.start();
        final SystemStatusAnimationScheduler systemStatusAnimationScheduler3 = this.this$0;
        systemStatusAnimationScheduler3.executor.executeDelayed(new Runnable() {
            public final void run() {
                int i;
                AnimatorSet notifyTransitionToPersistentDot;
                systemStatusAnimationScheduler3.animationState = 3;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setDuration(250);
                ofFloat.addListener(systemStatusAnimationScheduler3.systemAnimatorAdapter);
                ofFloat.addUpdateListener(systemStatusAnimationScheduler3.systemUpdateListener);
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                ofFloat2.setDuration(250);
                SystemStatusAnimationScheduler systemStatusAnimationScheduler = systemStatusAnimationScheduler3;
                Objects.requireNonNull(systemStatusAnimationScheduler);
                if (systemStatusAnimationScheduler.hasPersistentDot) {
                    i = 4;
                } else {
                    i = 0;
                }
                SystemStatusAnimationScheduler systemStatusAnimationScheduler2 = systemStatusAnimationScheduler3;
                StatusEvent statusEvent = systemStatusAnimationScheduler2.scheduledEvent;
                Intrinsics.checkNotNull(statusEvent);
                ofFloat2.addListener(new SystemStatusAnimationScheduler.ChipAnimatorAdapter(i, statusEvent.getViewCreator()));
                ofFloat2.addUpdateListener(systemStatusAnimationScheduler3.chipUpdateListener);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(ofFloat2).before(ofFloat);
                SystemStatusAnimationScheduler systemStatusAnimationScheduler3 = systemStatusAnimationScheduler3;
                Objects.requireNonNull(systemStatusAnimationScheduler3);
                if (systemStatusAnimationScheduler3.hasPersistentDot && (notifyTransitionToPersistentDot = systemStatusAnimationScheduler3.notifyTransitionToPersistentDot()) != null) {
                    animatorSet.playTogether(new Animator[]{ofFloat, notifyTransitionToPersistentDot});
                }
                animatorSet.start();
                StatusBarWindowController statusBarWindowController = systemStatusAnimationScheduler3.statusBarWindowController;
                Objects.requireNonNull(statusBarWindowController);
                StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
                state.mForceStatusBarVisible = false;
                statusBarWindowController.apply(state);
                systemStatusAnimationScheduler3.scheduledEvent = null;
            }
        }, 1500);
    }
}
