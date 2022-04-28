package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController$Stub;
import com.android.systemui.shared.system.smartspace.SmartspaceState;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController extends ISysuiUnlockAnimationController$Stub implements KeyguardStateController.Callback {
    public boolean attemptedSmartSpaceTransitionForThisSwipe;
    public final Lazy<BiometricUnlockController> biometricUnlockControllerLazy;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Lazy<KeyguardViewMediator> keyguardViewMediator;
    public SmartspaceState launcherSmartspaceState;
    public ILauncherUnlockAnimationController launcherUnlockController;
    public final ArrayList<KeyguardUnlockAnimationListener> listeners = new ArrayList<>();
    public View lockscreenSmartspace;
    public boolean playingCannedUnlockAnimation;
    public float roundedCornerRadius;
    public ValueAnimator smartspaceAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
    public final Rect smartspaceDestBounds;
    public final Rect smartspaceOriginBounds;
    public float smartspaceUnlockProgress;
    public float surfaceBehindAlpha = 1.0f;
    public ValueAnimator surfaceBehindAlphaAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
    public final ValueAnimator surfaceBehindEntryAnimator;
    public final Matrix surfaceBehindMatrix = new Matrix();
    public SyncRtSurfaceTransactionApplier.SurfaceParams surfaceBehindParams;
    public long surfaceBehindRemoteAnimationStartTime;
    public RemoteAnimationTarget surfaceBehindRemoteAnimationTarget;
    public SyncRtSurfaceTransactionApplier surfaceTransactionApplier;
    public boolean unlockingToLauncherWithInWindowAnimations;
    public boolean unlockingWithSmartspaceTransition;

    /* compiled from: KeyguardUnlockAnimationController.kt */
    public static final class Companion {
        public static boolean isNexusLauncherUnderneath() {
            ComponentName componentName;
            String className;
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null || (componentName = runningTask.topActivity) == null || (className = componentName.getClassName()) == null) {
                return false;
            }
            return className.equals("com.google.android.apps.nexuslauncher.NexusLauncherActivity");
        }
    }

    /* compiled from: KeyguardUnlockAnimationController.kt */
    public interface KeyguardUnlockAnimationListener {
        void onSmartspaceSharedElementTransitionStarted() {
        }

        void onUnlockAnimationFinished() {
        }

        void onUnlockAnimationStarted(boolean z, boolean z2) {
        }
    }

    public static /* synthetic */ void getSurfaceBehindEntryAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceTransactionApplier$annotations() {
    }

    public final void finishKeyguardExitRemoteAnimationIfReachThreshold() {
        if (KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation && this.keyguardViewController.isShowing()) {
            KeyguardViewMediator keyguardViewMediator2 = this.keyguardViewMediator.get();
            Objects.requireNonNull(keyguardViewMediator2);
            if (keyguardViewMediator2.mSurfaceBehindRemoteAnimationRequested && this.keyguardViewMediator.get().isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
                float dismissAmount = this.keyguardStateController.getDismissAmount();
                if (dismissAmount >= 1.0f || (this.keyguardStateController.isDismissingFromSwipe() && !this.keyguardStateController.isFlingingToDismissKeyguardDuringSwipeGesture() && dismissAmount >= 0.4f)) {
                    setSurfaceBehindAppearAmount(1.0f);
                    this.keyguardViewMediator.get().onKeyguardExitRemoteAnimationFinished(false);
                }
            }
        }
    }

    public final void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget remoteAnimationTarget, long j, boolean z) {
        boolean z2;
        int i;
        if (this.surfaceTransactionApplier == null) {
            this.surfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(this.keyguardViewController.getViewRootImpl().getView());
        }
        this.surfaceBehindParams = null;
        this.surfaceBehindRemoteAnimationTarget = remoteAnimationTarget;
        this.surfaceBehindRemoteAnimationStartTime = j;
        if (!z) {
            this.playingCannedUnlockAnimation = true;
            if (!Companion.isNexusLauncherUnderneath() || this.launcherUnlockController == null) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                this.unlockingToLauncherWithInWindowAnimations = true;
                if (prepareForSmartspaceTransition()) {
                    this.smartspaceAnimator.start();
                    Iterator<KeyguardUnlockAnimationListener> it = this.listeners.iterator();
                    while (it.hasNext()) {
                        it.next().onSmartspaceSharedElementTransitionStarted();
                    }
                }
                ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
                if (iLauncherUnlockAnimationController != null) {
                    boolean z3 = this.unlockingWithSmartspaceTransition;
                    BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) this.lockscreenSmartspace;
                    if (smartspaceView == null) {
                        i = -1;
                    } else {
                        i = smartspaceView.getSelectedPage();
                    }
                    iLauncherUnlockAnimationController.prepareForUnlock(z3, i);
                }
                ILauncherUnlockAnimationController iLauncherUnlockAnimationController2 = this.launcherUnlockController;
                if (iLauncherUnlockAnimationController2 != null) {
                    iLauncherUnlockAnimationController2.playUnlockAnimation();
                }
                if (!this.unlockingWithSmartspaceTransition) {
                    this.handler.postDelayed(new C0849xe53b14a5(this), 200);
                }
                setSurfaceBehindAppearAmount(1.0f);
            } else if (!this.biometricUnlockControllerLazy.get().isWakeAndUnlock()) {
                this.surfaceBehindEntryAnimator.start();
            } else {
                setSurfaceBehindAppearAmount(1.0f);
                this.keyguardViewMediator.get().onKeyguardExitRemoteAnimationFinished(false);
            }
            if (this.biometricUnlockControllerLazy.get().isWakeAndUnlock()) {
                this.keyguardViewController.hide(this.surfaceBehindRemoteAnimationStartTime, 350);
            }
        }
        Iterator<KeyguardUnlockAnimationListener> it2 = this.listeners.iterator();
        while (it2.hasNext()) {
            it2.next().onUnlockAnimationStarted(this.playingCannedUnlockAnimation, this.biometricUnlockControllerLazy.get().isWakeAndUnlock());
        }
        finishKeyguardExitRemoteAnimationIfReachThreshold();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0143, code lost:
        if (r2 != false) goto L_0x0145;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onKeyguardDismissAmountChanged() {
        /*
            r6 = this;
            boolean r0 = com.android.systemui.keyguard.KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.android.keyguard.KeyguardViewController r0 = r6.keyguardViewController
            boolean r0 = r0.isShowing()
            r1 = 0
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x00d5
            com.android.systemui.flags.FeatureFlags r0 = r6.featureFlags
            com.android.systemui.flags.BooleanFlag r4 = com.android.systemui.flags.Flags.NEW_UNLOCK_SWIPE_ANIMATION
            boolean r0 = r0.isEnabled((com.android.systemui.flags.BooleanFlag) r4)
            if (r0 != 0) goto L_0x001c
            goto L_0x00b1
        L_0x001c:
            boolean r0 = r6.playingCannedUnlockAnimation
            if (r0 == 0) goto L_0x0022
            goto L_0x00b1
        L_0x0022:
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r6.keyguardStateController
            float r0 = r0.getDismissAmount()
            r4 = 1048576000(0x3e800000, float:0.25)
            int r5 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r5 < 0) goto L_0x007d
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r5 = r6.keyguardViewMediator
            java.lang.Object r5 = r5.get()
            com.android.systemui.keyguard.KeyguardViewMediator r5 = (com.android.systemui.keyguard.KeyguardViewMediator) r5
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mSurfaceBehindRemoteAnimationRequested
            if (r5 != 0) goto L_0x007d
            boolean r0 = com.android.systemui.keyguard.KeyguardUnlockAnimationController.Companion.isNexusLauncherUnderneath()
            if (r0 == 0) goto L_0x0049
            com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController r0 = r6.launcherUnlockController
            if (r0 == 0) goto L_0x0049
            r0 = r2
            goto L_0x004a
        L_0x0049:
            r0 = r3
        L_0x004a:
            if (r0 == 0) goto L_0x0056
            com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController r0 = r6.launcherUnlockController
            if (r0 != 0) goto L_0x0051
            goto L_0x0054
        L_0x0051:
            r0.setUnlockAmount(r1)
        L_0x0054:
            r6.unlockingToLauncherWithInWindowAnimations = r2
        L_0x0056:
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r0 = r6.keyguardViewMediator
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            java.util.Objects.requireNonNull(r0)
            r0.mSurfaceBehindRemoteAnimationRequested = r2
            android.app.IActivityTaskManager r4 = android.app.ActivityTaskManager.getService()     // Catch:{ RemoteException -> 0x006c }
            r5 = 6
            r4.keyguardGoingAway(r5)     // Catch:{ RemoteException -> 0x006c }
            goto L_0x0072
        L_0x006c:
            r4 = move-exception
            r0.mSurfaceBehindRemoteAnimationRequested = r3
            r4.printStackTrace()
        L_0x0072:
            android.animation.ValueAnimator r0 = r6.surfaceBehindAlphaAnimator
            r0.cancel()
            android.animation.ValueAnimator r0 = r6.surfaceBehindAlphaAnimator
            r0.start()
            goto L_0x00ae
        L_0x007d:
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 >= 0) goto L_0x00ae
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r0 = r6.keyguardViewMediator
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mSurfaceBehindRemoteAnimationRequested
            if (r0 == 0) goto L_0x00ae
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r0 = r6.keyguardViewMediator
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            java.util.Objects.requireNonNull(r0)
            r0.mSurfaceBehindRemoteAnimationRequested = r3
            boolean r4 = r0.mShowing
            if (r4 == 0) goto L_0x00a4
            r0.setShowingLocked(r2, r2)
        L_0x00a4:
            android.animation.ValueAnimator r0 = r6.surfaceBehindAlphaAnimator
            r0.cancel()
            android.animation.ValueAnimator r0 = r6.surfaceBehindAlphaAnimator
            r0.reverse()
        L_0x00ae:
            r6.finishKeyguardExitRemoteAnimationIfReachThreshold()
        L_0x00b1:
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r0 = r6.keyguardViewMediator
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mSurfaceBehindRemoteAnimationRequested
            if (r0 != 0) goto L_0x00ce
            dagger.Lazy<com.android.systemui.keyguard.KeyguardViewMediator> r0 = r6.keyguardViewMediator
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            boolean r0 = r0.isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()
            if (r0 == 0) goto L_0x00d5
        L_0x00ce:
            boolean r0 = r6.playingCannedUnlockAnimation
            if (r0 != 0) goto L_0x00d5
            r6.updateSurfaceBehindAppearAmount()
        L_0x00d5:
            com.android.systemui.flags.FeatureFlags r0 = r6.featureFlags
            com.android.systemui.flags.BooleanFlag r4 = com.android.systemui.flags.Flags.SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED
            boolean r0 = r0.isEnabled((com.android.systemui.flags.BooleanFlag) r4)
            if (r0 != 0) goto L_0x00e1
            goto L_0x0164
        L_0x00e1:
            boolean r0 = r6.playingCannedUnlockAnimation
            if (r0 == 0) goto L_0x00e7
            goto L_0x0164
        L_0x00e7:
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r6.keyguardStateController
            float r0 = r0.getDismissAmount()
            boolean r4 = r6.attemptedSmartSpaceTransitionForThisSwipe
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r4 != 0) goto L_0x0130
            com.android.keyguard.KeyguardViewController r4 = r6.keyguardViewController
            boolean r4 = r4.isShowing()
            if (r4 == 0) goto L_0x0130
            int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r4 <= 0) goto L_0x0130
            int r4 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r4 >= 0) goto L_0x0130
            r6.attemptedSmartSpaceTransitionForThisSwipe = r2
            boolean r0 = r6.prepareForSmartspaceTransition()
            if (r0 == 0) goto L_0x0151
            r6.unlockingWithSmartspaceTransition = r2
            com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController r0 = r6.launcherUnlockController
            if (r0 != 0) goto L_0x0112
            goto L_0x0116
        L_0x0112:
            r1 = 4
            r0.setSmartspaceVisibility(r1)
        L_0x0116:
            boolean r0 = r6.unlockingWithSmartspaceTransition
            if (r0 == 0) goto L_0x0151
            java.util.ArrayList<com.android.systemui.keyguard.KeyguardUnlockAnimationController$KeyguardUnlockAnimationListener> r0 = r6.listeners
            java.util.Iterator r0 = r0.iterator()
        L_0x0120:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0151
            java.lang.Object r1 = r0.next()
            com.android.systemui.keyguard.KeyguardUnlockAnimationController$KeyguardUnlockAnimationListener r1 = (com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener) r1
            r1.onSmartspaceSharedElementTransitionStarted()
            goto L_0x0120
        L_0x0130:
            boolean r4 = r6.attemptedSmartSpaceTransitionForThisSwipe
            if (r4 == 0) goto L_0x0151
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x013a
            r1 = r2
            goto L_0x013b
        L_0x013a:
            r1 = r3
        L_0x013b:
            if (r1 != 0) goto L_0x0145
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x0142
            goto L_0x0143
        L_0x0142:
            r2 = r3
        L_0x0143:
            if (r2 == 0) goto L_0x0151
        L_0x0145:
            r6.attemptedSmartSpaceTransitionForThisSwipe = r3
            r6.unlockingWithSmartspaceTransition = r3
            com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController r0 = r6.launcherUnlockController
            if (r0 != 0) goto L_0x014e
            goto L_0x0151
        L_0x014e:
            r0.setSmartspaceVisibility(r3)
        L_0x0151:
            boolean r0 = r6.unlockingWithSmartspaceTransition
            if (r0 == 0) goto L_0x0164
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r6.keyguardStateController
            float r0 = r0.getDismissAmount()
            r1 = 1053609165(0x3ecccccd, float:0.4)
            float r0 = r0 / r1
            r6.smartspaceUnlockProgress = r0
            r6.setSmartspaceProgressToDestinationBounds(r0)
        L_0x0164:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardUnlockAnimationController.onKeyguardDismissAmountChanged():void");
    }

    public final boolean prepareForSmartspaceTransition() {
        SmartspaceState smartspaceState;
        boolean z;
        boolean z2;
        int i;
        if (!this.featureFlags.isEnabled(Flags.SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED) || this.launcherUnlockController == null || this.lockscreenSmartspace == null || (smartspaceState = this.launcherSmartspaceState) == null) {
            return false;
        }
        if (smartspaceState.visibleOnScreen) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !Companion.isNexusLauncherUnderneath() || this.biometricUnlockControllerLazy.get().isWakeAndUnlock()) {
            return false;
        }
        if (!this.keyguardStateController.canDismissLockScreen()) {
            BiometricUnlockController biometricUnlockController = this.biometricUnlockControllerLazy.get();
            Objects.requireNonNull(biometricUnlockController);
            if (biometricUnlockController.isWakeAndUnlock() || (i = biometricUnlockController.mMode) == 5 || i == 7) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                return false;
            }
        }
        this.unlockingWithSmartspaceTransition = true;
        this.smartspaceDestBounds.setEmpty();
        View view = this.lockscreenSmartspace;
        Intrinsics.checkNotNull(view);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        view.getBoundsOnScreen(this.smartspaceOriginBounds);
        Rect rect = this.smartspaceDestBounds;
        SmartspaceState smartspaceState2 = this.launcherSmartspaceState;
        Intrinsics.checkNotNull(smartspaceState2);
        rect.set(smartspaceState2.boundsOnScreen);
        View view2 = this.lockscreenSmartspace;
        Intrinsics.checkNotNull(view2);
        View view3 = this.lockscreenSmartspace;
        Intrinsics.checkNotNull(view3);
        rect.offset(-view2.getPaddingLeft(), -view3.getPaddingTop());
        return true;
    }

    public final void setSmartspaceProgressToDestinationBounds(float f) {
        if (!this.smartspaceDestBounds.isEmpty()) {
            float min = Math.min(1.0f, f);
            Rect rect = this.smartspaceDestBounds;
            int i = rect.left;
            Rect rect2 = this.smartspaceOriginBounds;
            float f2 = ((float) (i - rect2.left)) * min;
            float f3 = ((float) (rect.top - rect2.top)) * min;
            Rect rect3 = new Rect();
            View view = this.lockscreenSmartspace;
            Intrinsics.checkNotNull(view);
            view.getBoundsOnScreen(rect3);
            Rect rect4 = this.smartspaceOriginBounds;
            float f4 = (((float) rect4.left) + f2) - ((float) rect3.left);
            float f5 = (((float) rect4.top) + f3) - ((float) rect3.top);
            View view2 = this.lockscreenSmartspace;
            Intrinsics.checkNotNull(view2);
            view2.setTranslationX(view2.getTranslationX() + f4);
            view2.setTranslationY(view2.getTranslationY() + f5);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setSurfaceBehindAppearAmount(float r10) {
        /*
            r9 = this;
            android.view.RemoteAnimationTarget r0 = r9.surfaceBehindRemoteAnimationTarget
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            boolean r1 = r9.unlockingToLauncherWithInWindowAnimations
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0051
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams r0 = r9.surfaceBehindParams
            if (r0 != 0) goto L_0x0012
            goto L_0x001f
        L_0x0012:
            float r0 = r0.alpha
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x001a
            r0 = r4
            goto L_0x001b
        L_0x001a:
            r0 = r3
        L_0x001b:
            if (r0 != 0) goto L_0x001f
            r0 = r4
            goto L_0x0020
        L_0x001f:
            r0 = r3
        L_0x0020:
            if (r0 != 0) goto L_0x0044
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r0 = new android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder
            android.view.RemoteAnimationTarget r1 = r9.surfaceBehindRemoteAnimationTarget
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            android.view.SurfaceControl r1 = r1.leash
            r0.<init>(r1)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r0 = r0.withAlpha(r2)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams r0 = r0.build()
            android.view.SyncRtSurfaceTransactionApplier r1 = r9.surfaceTransactionApplier
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams[] r2 = new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[r4]
            r2[r3] = r0
            r1.scheduleApply(r2)
            r9.surfaceBehindParams = r0
        L_0x0044:
            boolean r0 = r9.playingCannedUnlockAnimation
            if (r0 != 0) goto L_0x00c1
            com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController r9 = r9.launcherUnlockController
            if (r9 != 0) goto L_0x004d
            goto L_0x00c1
        L_0x004d:
            r9.setUnlockAmount(r10)
            goto L_0x00c1
        L_0x0051:
            android.graphics.Rect r0 = r0.screenSpaceBounds
            int r0 = r0.height()
            r1 = 1064514355(0x3f733333, float:0.95)
            r5 = 1028443344(0x3d4cccd0, float:0.050000012)
            r6 = 0
            float r7 = kotlinx.atomicfu.AtomicFU.clamp((float) r10, (float) r6, (float) r2)
            float r7 = r7 * r5
            float r7 = r7 + r1
            android.graphics.Matrix r1 = r9.surfaceBehindMatrix
            android.view.RemoteAnimationTarget r5 = r9.surfaceBehindRemoteAnimationTarget
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            android.graphics.Rect r5 = r5.screenSpaceBounds
            int r5 = r5.width()
            float r5 = (float) r5
            r8 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r8
            float r0 = (float) r0
            r8 = 1059648963(0x3f28f5c3, float:0.66)
            float r8 = r8 * r0
            r1.setScale(r7, r7, r5, r8)
            android.graphics.Matrix r1 = r9.surfaceBehindMatrix
            r5 = 1028443341(0x3d4ccccd, float:0.05)
            float r0 = r0 * r5
            float r2 = r2 - r10
            float r2 = r2 * r0
            r1.postTranslate(r6, r2)
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r9.keyguardStateController
            boolean r0 = r0.isSnappingKeyguardBackAfterSwipe()
            if (r0 == 0) goto L_0x0091
            goto L_0x0093
        L_0x0091:
            float r10 = r9.surfaceBehindAlpha
        L_0x0093:
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r0 = new android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder
            android.view.RemoteAnimationTarget r1 = r9.surfaceBehindRemoteAnimationTarget
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            android.view.SurfaceControl r1 = r1.leash
            r0.<init>(r1)
            android.graphics.Matrix r1 = r9.surfaceBehindMatrix
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r0 = r0.withMatrix(r1)
            float r1 = r9.roundedCornerRadius
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r0 = r0.withCornerRadius(r1)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder r10 = r0.withAlpha(r10)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams r10 = r10.build()
            android.view.SyncRtSurfaceTransactionApplier r0 = r9.surfaceTransactionApplier
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            android.view.SyncRtSurfaceTransactionApplier$SurfaceParams[] r1 = new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[r4]
            r1[r3] = r10
            r0.scheduleApply(r1)
            r9.surfaceBehindParams = r10
        L_0x00c1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardUnlockAnimationController.setSurfaceBehindAppearAmount(float):void");
    }

    public final void updateSurfaceBehindAppearAmount() {
        if (this.surfaceBehindRemoteAnimationTarget == null || this.playingCannedUnlockAnimation) {
            return;
        }
        if (this.keyguardStateController.isFlingingToDismissKeyguard()) {
            setSurfaceBehindAppearAmount(this.keyguardStateController.getDismissAmount());
        } else if (this.keyguardStateController.isDismissingFromSwipe() || this.keyguardStateController.isSnappingKeyguardBackAfterSwipe()) {
            setSurfaceBehindAppearAmount((this.keyguardStateController.getDismissAmount() - 0.25f) / 0.15f);
        }
    }

    public KeyguardUnlockAnimationController(Context context, KeyguardStateController keyguardStateController2, Lazy<KeyguardViewMediator> lazy, KeyguardViewController keyguardViewController2, FeatureFlags featureFlags2, Lazy<BiometricUnlockController> lazy2) {
        this.keyguardStateController = keyguardStateController2;
        this.keyguardViewMediator = lazy;
        this.keyguardViewController = keyguardViewController2;
        this.featureFlags = featureFlags2;
        this.biometricUnlockControllerLazy = lazy2;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.surfaceBehindEntryAnimator = ofFloat;
        this.smartspaceOriginBounds = new Rect();
        this.smartspaceDestBounds = new Rect();
        this.handler = new Handler();
        ValueAnimator valueAnimator = this.surfaceBehindAlphaAnimator;
        valueAnimator.setDuration(150);
        PathInterpolator pathInterpolator = Interpolators.TOUCH_RESPONSE;
        valueAnimator.setInterpolator(pathInterpolator);
        valueAnimator.addUpdateListener(new KeyguardUnlockAnimationController$1$1(this));
        valueAnimator.addListener(new KeyguardUnlockAnimationController$1$2(this));
        ofFloat.setDuration(200);
        ofFloat.setStartDelay(75);
        ofFloat.setInterpolator(pathInterpolator);
        ofFloat.addUpdateListener(new KeyguardUnlockAnimationController$2$1(this));
        ofFloat.addListener(new KeyguardUnlockAnimationController$2$2(this));
        ValueAnimator valueAnimator2 = this.smartspaceAnimator;
        valueAnimator2.setDuration(200);
        valueAnimator2.setInterpolator(pathInterpolator);
        valueAnimator2.addUpdateListener(new KeyguardUnlockAnimationController$3$1(this));
        valueAnimator2.addListener(new KeyguardUnlockAnimationController$3$2(this));
        keyguardStateController2.addCallback(this);
        this.roundedCornerRadius = (float) context.getResources().getDimensionPixelSize(17105512);
    }
}
