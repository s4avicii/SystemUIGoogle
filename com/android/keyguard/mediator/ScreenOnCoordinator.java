package com.android.keyguard.mediator;

import android.animation.ValueAnimator;
import android.os.Trace;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ScreenOnCoordinator.kt */
public final class ScreenOnCoordinator implements ScreenLifecycle.Observer {
    public final Execution execution;
    public final FoldAodAnimationController foldAodAnimationController;
    public final PendingTasksContainer pendingTasks = new PendingTasksContainer();
    public final UnfoldLightRevealOverlayAnimation unfoldLightRevealAnimation;
    public boolean wakeAndUnlocking;
    public PendingTasksContainer$registerTask$1 wakeAndUnlockingTask;

    public final void onScreenTurnedOff() {
        this.wakeAndUnlockingTask = null;
    }

    public final void onScreenTurnedOn() {
        this.execution.assertIsMainThread();
        FoldAodAnimationController foldAodAnimationController2 = this.foldAodAnimationController;
        if (foldAodAnimationController2 != null && foldAodAnimationController2.shouldPlayAnimation) {
            foldAodAnimationController2.handler.removeCallbacks(foldAodAnimationController2.startAnimationRunnable);
            foldAodAnimationController2.handler.post(foldAodAnimationController2.startAnimationRunnable);
            foldAodAnimationController2.shouldPlayAnimation = false;
        }
        PendingTasksContainer pendingTasksContainer = this.pendingTasks;
        Objects.requireNonNull(pendingTasksContainer);
        pendingTasksContainer.completionCallback = new AtomicReference<>();
        pendingTasksContainer.pendingTasksCount = new AtomicInteger(0);
    }

    public final void onScreenTurningOn(Runnable runnable) {
        Runnable andSet;
        this.execution.assertIsMainThread();
        Trace.beginSection("ScreenOnCoordinator#onScreenTurningOn");
        PendingTasksContainer pendingTasksContainer = this.pendingTasks;
        Objects.requireNonNull(pendingTasksContainer);
        pendingTasksContainer.completionCallback = new AtomicReference<>();
        pendingTasksContainer.pendingTasksCount = new AtomicInteger(0);
        UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = this.unfoldLightRevealAnimation;
        if (unfoldLightRevealOverlayAnimation != null) {
            PendingTasksContainer pendingTasksContainer2 = this.pendingTasks;
            Objects.requireNonNull(pendingTasksContainer2);
            pendingTasksContainer2.pendingTasksCount.incrementAndGet();
            PendingTasksContainer$registerTask$1 pendingTasksContainer$registerTask$1 = new PendingTasksContainer$registerTask$1(pendingTasksContainer2);
            Trace.beginSection("UnfoldLightRevealOverlayAnimation#onScreenTurningOn");
            try {
                if (unfoldLightRevealOverlayAnimation.isFolded || unfoldLightRevealOverlayAnimation.isUnfoldHandled || !ValueAnimator.areAnimatorsEnabled()) {
                    unfoldLightRevealOverlayAnimation.ensureOverlayRemoved();
                    pendingTasksContainer$registerTask$1.run();
                } else {
                    unfoldLightRevealOverlayAnimation.addView(pendingTasksContainer$registerTask$1);
                    unfoldLightRevealOverlayAnimation.isUnfoldHandled = true;
                }
            } finally {
                Trace.endSection();
            }
        }
        FoldAodAnimationController foldAodAnimationController2 = this.foldAodAnimationController;
        if (foldAodAnimationController2 != null) {
            PendingTasksContainer pendingTasksContainer3 = this.pendingTasks;
            Objects.requireNonNull(pendingTasksContainer3);
            pendingTasksContainer3.pendingTasksCount.incrementAndGet();
            PendingTasksContainer$registerTask$1 pendingTasksContainer$registerTask$12 = new PendingTasksContainer$registerTask$1(pendingTasksContainer3);
            if (!foldAodAnimationController2.shouldPlayAnimation) {
                pendingTasksContainer$registerTask$12.run();
            } else if (foldAodAnimationController2.isScrimOpaque) {
                pendingTasksContainer$registerTask$12.run();
            } else {
                foldAodAnimationController2.pendingScrimReadyCallback = pendingTasksContainer$registerTask$12;
            }
        }
        if (this.wakeAndUnlocking) {
            PendingTasksContainer pendingTasksContainer4 = this.pendingTasks;
            Objects.requireNonNull(pendingTasksContainer4);
            pendingTasksContainer4.pendingTasksCount.incrementAndGet();
            this.wakeAndUnlockingTask = new PendingTasksContainer$registerTask$1(pendingTasksContainer4);
        }
        PendingTasksContainer pendingTasksContainer5 = this.pendingTasks;
        ScreenOnCoordinator$onScreenTurningOn$1 screenOnCoordinator$onScreenTurningOn$1 = new ScreenOnCoordinator$onScreenTurningOn$1(runnable);
        Objects.requireNonNull(pendingTasksContainer5);
        pendingTasksContainer5.completionCallback.set(screenOnCoordinator$onScreenTurningOn$1);
        if (pendingTasksContainer5.pendingTasksCount.get() == 0 && (andSet = pendingTasksContainer5.completionCallback.getAndSet((Object) null)) != null) {
            andSet.run();
        }
        Trace.endSection();
    }

    public final void setWakeAndUnlocking(boolean z) {
        if (!z && this.wakeAndUnlocking) {
            PendingTasksContainer$registerTask$1 pendingTasksContainer$registerTask$1 = this.wakeAndUnlockingTask;
            if (pendingTasksContainer$registerTask$1 != null) {
                pendingTasksContainer$registerTask$1.run();
            }
            this.wakeAndUnlockingTask = null;
        }
        this.wakeAndUnlocking = z;
    }

    public ScreenOnCoordinator(ScreenLifecycle screenLifecycle, Optional<SysUIUnfoldComponent> optional, Execution execution2) {
        this.execution = execution2;
        this.unfoldLightRevealAnimation = (UnfoldLightRevealOverlayAnimation) optional.map(ScreenOnCoordinator$unfoldLightRevealAnimation$1.INSTANCE).orElse((Object) null);
        this.foldAodAnimationController = (FoldAodAnimationController) optional.map(ScreenOnCoordinator$foldAodAnimationController$1.INSTANCE).orElse((Object) null);
        screenLifecycle.mObservers.add(this);
    }
}
