package com.android.systemui.statusbar.events;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler implements CallbackController<SystemStatusAnimationCallback>, Dumpable {
    public int animationState;
    public final SystemEventChipAnimationController chipAnimationController;
    public final SystemStatusAnimationScheduler$chipUpdateListener$1 chipUpdateListener;
    public final SystemEventCoordinator coordinator;
    public final DelayableExecutor executor;
    public boolean hasPersistentDot;
    public final LinkedHashSet listeners = new LinkedHashSet();
    public StatusEvent scheduledEvent;
    public final StatusBarWindowController statusBarWindowController;
    public final SystemStatusAnimationScheduler$systemAnimatorAdapter$1 systemAnimatorAdapter;
    public final SystemClock systemClock;
    public final SystemStatusAnimationScheduler$systemUpdateListener$1 systemUpdateListener;

    /* compiled from: SystemStatusAnimationScheduler.kt */
    public final class ChipAnimatorAdapter extends AnimatorListenerAdapter {
        public final int endState;
        public final Function1<Context, View> viewCreator;

        public ChipAnimatorAdapter(int i, Function1<? super Context, ? extends View> function1) {
            this.endState = i;
            this.viewCreator = function1;
        }

        public final void onAnimationEnd(Animator animator) {
            int i;
            SystemStatusAnimationScheduler systemStatusAnimationScheduler = SystemStatusAnimationScheduler.this;
            SystemEventChipAnimationController systemEventChipAnimationController = systemStatusAnimationScheduler.chipAnimationController;
            Objects.requireNonNull(systemStatusAnimationScheduler);
            int i2 = systemStatusAnimationScheduler.animationState;
            Objects.requireNonNull(systemEventChipAnimationController);
            if (i2 == 1) {
                View view = systemEventChipAnimationController.currentAnimatedView;
                if (view != null) {
                    view.setTranslationX(0.0f);
                    view.setAlpha(1.0f);
                }
            } else {
                View view2 = systemEventChipAnimationController.currentAnimatedView;
                if (view2 != null) {
                    view2.setVisibility(4);
                }
                FrameLayout frameLayout = systemEventChipAnimationController.animationWindowView;
                if (frameLayout == null) {
                    frameLayout = null;
                }
                frameLayout.removeView(systemEventChipAnimationController.currentAnimatedView);
            }
            SystemStatusAnimationScheduler systemStatusAnimationScheduler2 = SystemStatusAnimationScheduler.this;
            if (this.endState == 4) {
                Objects.requireNonNull(systemStatusAnimationScheduler2);
                if (!systemStatusAnimationScheduler2.hasPersistentDot) {
                    i = 0;
                    systemStatusAnimationScheduler2.animationState = i;
                }
            }
            i = this.endState;
            systemStatusAnimationScheduler2.animationState = i;
        }

        public final void onAnimationStart(Animator animator) {
            int i;
            SystemStatusAnimationScheduler systemStatusAnimationScheduler = SystemStatusAnimationScheduler.this;
            SystemEventChipAnimationController systemEventChipAnimationController = systemStatusAnimationScheduler.chipAnimationController;
            Function1<Context, View> function1 = this.viewCreator;
            int i2 = systemStatusAnimationScheduler.animationState;
            Objects.requireNonNull(systemEventChipAnimationController);
            FrameLayout frameLayout = null;
            if (!systemEventChipAnimationController.initialized) {
                systemEventChipAnimationController.initialized = true;
                View inflate = LayoutInflater.from(systemEventChipAnimationController.context).inflate(C1777R.layout.system_event_animation_window, (ViewGroup) null);
                Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.widget.FrameLayout");
                FrameLayout frameLayout2 = (FrameLayout) inflate;
                systemEventChipAnimationController.animationWindowView = frameLayout2;
                systemEventChipAnimationController.animationDotView = frameLayout2.findViewById(C1777R.C1779id.dot_view);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                layoutParams.gravity = 8388629;
                StatusBarWindowController statusBarWindowController = systemEventChipAnimationController.statusBarWindowController;
                FrameLayout frameLayout3 = systemEventChipAnimationController.animationWindowView;
                if (frameLayout3 == null) {
                    frameLayout3 = null;
                }
                Objects.requireNonNull(statusBarWindowController);
                statusBarWindowController.mStatusBarWindowView.addView(frameLayout3, layoutParams);
            }
            if (i2 == 1) {
                View invoke = function1.invoke(systemEventChipAnimationController.context);
                systemEventChipAnimationController.currentAnimatedView = invoke;
                FrameLayout frameLayout4 = systemEventChipAnimationController.animationWindowView;
                if (frameLayout4 == null) {
                    frameLayout4 = null;
                }
                FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
                layoutParams2.gravity = 8388629;
                FrameLayout frameLayout5 = systemEventChipAnimationController.animationWindowView;
                if (frameLayout5 != null) {
                    frameLayout = frameLayout5;
                }
                if (frameLayout.isLayoutRtl()) {
                    StatusBarLocationPublisher statusBarLocationPublisher = systemEventChipAnimationController.locationPublisher;
                    Objects.requireNonNull(statusBarLocationPublisher);
                    i = statusBarLocationPublisher.marginRight;
                } else {
                    StatusBarLocationPublisher statusBarLocationPublisher2 = systemEventChipAnimationController.locationPublisher;
                    Objects.requireNonNull(statusBarLocationPublisher2);
                    i = statusBarLocationPublisher2.marginLeft;
                }
                layoutParams2.setMarginStart(i);
                frameLayout4.addView(invoke, layoutParams2);
                View view = systemEventChipAnimationController.currentAnimatedView;
                if (view != null) {
                    float width = (float) view.getWidth();
                    if (view.isLayoutRtl()) {
                        width = -width;
                    }
                    view.setTranslationX(width);
                    view.setAlpha(0.0f);
                    view.setVisibility(0);
                    StatusBarLocationPublisher statusBarLocationPublisher3 = systemEventChipAnimationController.locationPublisher;
                    Objects.requireNonNull(statusBarLocationPublisher3);
                    int i3 = statusBarLocationPublisher3.marginLeft;
                    StatusBarLocationPublisher statusBarLocationPublisher4 = systemEventChipAnimationController.locationPublisher;
                    Objects.requireNonNull(statusBarLocationPublisher4);
                    view.setPadding(i3, 0, statusBarLocationPublisher4.marginRight, 0);
                    return;
                }
                return;
            }
            View view2 = systemEventChipAnimationController.currentAnimatedView;
            if (view2 != null) {
                view2.setTranslationX(0.0f);
                view2.setAlpha(1.0f);
            }
        }
    }

    public final void addCallback(SystemStatusAnimationCallback systemStatusAnimationCallback) {
        Assert.isMainThread();
        if (this.listeners.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            Objects.requireNonNull(systemEventCoordinator);
            systemEventCoordinator.privacyController.addCallback(systemEventCoordinator.privacyStateListener);
        }
        this.listeners.add(systemStatusAnimationCallback);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("Scheduled event: ", this.scheduledEvent));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.hasPersistentDot, "Has persistent privacy dot: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("Animation state: ", Integer.valueOf(this.animationState)));
        printWriter.println("Listeners:");
        if (this.listeners.isEmpty()) {
            printWriter.println("(none)");
            return;
        }
        for (SystemStatusAnimationCallback stringPlus : this.listeners) {
            printWriter.println(Intrinsics.stringPlus("  ", stringPlus));
        }
    }

    public final AnimatorSet notifyTransitionToPersistentDot() {
        LinkedHashSet linkedHashSet = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = linkedHashSet.iterator();
        while (true) {
            String str = null;
            if (!it.hasNext()) {
                break;
            }
            SystemStatusAnimationCallback systemStatusAnimationCallback = (SystemStatusAnimationCallback) it.next();
            StatusEvent statusEvent = this.scheduledEvent;
            if (statusEvent != null) {
                str = statusEvent.getContentDescription();
            }
            systemStatusAnimationCallback.onSystemStatusAnimationTransitionToPersistentDot(str);
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    public final void removeCallback(SystemStatusAnimationCallback systemStatusAnimationCallback) {
        Assert.isMainThread();
        this.listeners.remove(systemStatusAnimationCallback);
        if (this.listeners.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            Objects.requireNonNull(systemEventCoordinator);
            systemEventCoordinator.privacyController.removeCallback(systemEventCoordinator.privacyStateListener);
        }
    }

    public SystemStatusAnimationScheduler(SystemEventCoordinator systemEventCoordinator, SystemEventChipAnimationController systemEventChipAnimationController, StatusBarWindowController statusBarWindowController2, DumpManager dumpManager, SystemClock systemClock2, DelayableExecutor delayableExecutor) {
        this.coordinator = systemEventCoordinator;
        this.chipAnimationController = systemEventChipAnimationController;
        this.statusBarWindowController = statusBarWindowController2;
        this.systemClock = systemClock2;
        this.executor = delayableExecutor;
        Objects.requireNonNull(systemEventCoordinator);
        systemEventCoordinator.scheduler = this;
        dumpManager.registerDumpable("SystemStatusAnimationScheduler", this);
        this.systemUpdateListener = new SystemStatusAnimationScheduler$systemUpdateListener$1(this);
        this.systemAnimatorAdapter = new SystemStatusAnimationScheduler$systemAnimatorAdapter$1(this);
        this.chipUpdateListener = new SystemStatusAnimationScheduler$chipUpdateListener$1(this);
    }
}
