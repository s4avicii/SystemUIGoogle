package com.android.systemui.statusbar.phone;

import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import androidx.leanback.R$color;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardMessageArea;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda6;
import com.android.systemui.DejankUtils;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.biometrics.UdfpsKeyguardView;
import com.android.systemui.biometrics.UdfpsKeyguardViewController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.DismissCallbackWrapper;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.ViewGroupFadeHelper;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.util.Assert;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class StatusBarKeyguardViewManager implements RemoteInputController.Callback, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, PanelExpansionListener, NavigationModeController.ModeChangedListener, KeyguardViewController, FoldAodAnimationController.FoldAodAnimationStatus {
    public ActivityStarter.OnDismissAction mAfterKeyguardGoneAction;
    public final ArrayList<Runnable> mAfterKeyguardGoneRunnables = new ArrayList<>();
    public AlternateAuthInterceptor mAlternateAuthInterceptor;
    public BiometricUnlockController mBiometricUnlockController;
    public KeyguardBouncer mBouncer;
    public KeyguardBypassController mBypassController;
    public final ConfigurationController mConfigurationController;
    public boolean mDismissActionWillAnimateOnKeyguard;
    public final C15592 mDockEventListener = new DockManager.DockEventListener() {
        public final void onEvent(int i) {
            boolean isDocked = StatusBarKeyguardViewManager.this.mDockManager.isDocked();
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            if (isDocked != statusBarKeyguardViewManager.mIsDocked) {
                statusBarKeyguardViewManager.mIsDocked = isDocked;
                statusBarKeyguardViewManager.updateStates();
            }
        }
    };
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final C15581 mExpansionCallback = new KeyguardBouncer.BouncerExpansionCallback() {
        public final void onFullyHidden() {
        }

        public final void onExpansionChanged(float f) {
            AlternateAuthInterceptor alternateAuthInterceptor = StatusBarKeyguardViewManager.this.mAlternateAuthInterceptor;
            if (alternateAuthInterceptor != null) {
                UdfpsKeyguardViewController.C07092 r0 = (UdfpsKeyguardViewController.C07092) alternateAuthInterceptor;
                UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
                udfpsKeyguardViewController.mInputBouncerHiddenAmount = f;
                udfpsKeyguardViewController.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
            StatusBarKeyguardViewManager.this.updateStates();
        }

        public final void onFullyShown() {
            StatusBarKeyguardViewManager.this.updateStates();
            StatusBarKeyguardViewManager.this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), StatusBarKeyguardViewManager.this.mStatusBar.getBouncerContainer(), "BOUNCER_VISIBLE");
        }

        public final void onStartingToHide() {
            StatusBarKeyguardViewManager.this.updateStates();
        }

        public final void onStartingToShow() {
            StatusBarKeyguardViewManager.this.updateStates();
        }

        public final void onVisibilityChanged(boolean z) {
            if (!z) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                Objects.requireNonNull(statusBarKeyguardViewManager);
                if (!statusBarKeyguardViewManager.bouncerIsOrWillBeShowing()) {
                    statusBarKeyguardViewManager.mAfterKeyguardGoneAction = null;
                    statusBarKeyguardViewManager.mDismissActionWillAnimateOnKeyguard = false;
                    Runnable runnable = statusBarKeyguardViewManager.mKeyguardGoneCancelAction;
                    if (runnable != null) {
                        runnable.run();
                        statusBarKeyguardViewManager.mKeyguardGoneCancelAction = null;
                    }
                }
            }
            AlternateAuthInterceptor alternateAuthInterceptor = StatusBarKeyguardViewManager.this.mAlternateAuthInterceptor;
            if (alternateAuthInterceptor != null) {
                UdfpsKeyguardViewController.C07092 r2 = (UdfpsKeyguardViewController.C07092) alternateAuthInterceptor;
                UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
                udfpsKeyguardViewController.mIsBouncerVisible = udfpsKeyguardViewController.mKeyguardViewManager.isBouncerShowing();
                UdfpsKeyguardViewController udfpsKeyguardViewController2 = UdfpsKeyguardViewController.this;
                if (!udfpsKeyguardViewController2.mIsBouncerVisible) {
                    udfpsKeyguardViewController2.mInputBouncerHiddenAmount = 1.0f;
                } else if (udfpsKeyguardViewController2.mKeyguardViewManager.isBouncerShowing()) {
                    UdfpsKeyguardViewController.this.mInputBouncerHiddenAmount = 0.0f;
                }
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
        }
    };
    public boolean mFirstUpdate = true;
    public final FoldAodAnimationController mFoldAodAnimationController;
    public boolean mGesturalNav;
    public boolean mGlobalActionsVisible = false;
    public boolean mIsDocked;
    public final KeyguardBouncer.Factory mKeyguardBouncerFactory;
    public Runnable mKeyguardGoneCancelAction;
    public KeyguardMessageAreaController mKeyguardMessageAreaController;
    public final KeyguardMessageAreaController.Factory mKeyguardMessageAreaFactory;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public int mLastBiometricMode;
    public boolean mLastBouncerDismissible;
    public boolean mLastBouncerIsOrWillBeShowing;
    public boolean mLastBouncerShowing;
    public boolean mLastDozing;
    public boolean mLastGesturalNav;
    public boolean mLastGlobalActionsVisible = false;
    public boolean mLastIsDocked;
    public boolean mLastOccluded;
    public boolean mLastPulsing;
    public boolean mLastRemoteInputActive;
    public boolean mLastScreenOffAnimationPlaying;
    public boolean mLastShowing;
    public final LatencyTracker mLatencyTracker;
    public C15647 mMakeNavigationBarVisibleRunnable = new Runnable() {
        public final void run() {
            NavigationBarView navigationBarView = StatusBarKeyguardViewManager.this.mStatusBar.getNavigationBarView();
            if (navigationBarView != null) {
                navigationBarView.setVisibility(0);
            }
            StatusBar statusBar = StatusBarKeyguardViewManager.this.mStatusBar;
            Objects.requireNonNull(statusBar);
            statusBar.mNotificationShadeWindowView.getWindowInsetsController().show(WindowInsets.Type.navigationBars());
        }
    };
    public final NotificationMediaManager mMediaManager;
    public final NavigationModeController mNavigationModeController;
    public View mNotificationContainer;
    public NotificationPanelViewController mNotificationPanelViewController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public boolean mOccluded;
    public DismissWithActionRequest mPendingWakeupAction;
    public boolean mPulsing;
    public boolean mQsExpanded;
    public boolean mRemoteInputActive;
    public boolean mScreenOffAnimationPlaying;
    public final Lazy<ShadeController> mShadeController;
    public boolean mShowing;
    public StatusBar mStatusBar;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final C15603 mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onEmergencyCallAction() {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            if (statusBarKeyguardViewManager.mOccluded) {
                statusBarKeyguardViewManager.reset(true);
            }
        }
    };
    public ViewMediatorCallback mViewMediatorCallback;

    public interface AlternateAuthInterceptor {
    }

    public StatusBarKeyguardViewManager(ViewMediatorCallback viewMediatorCallback, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, NotificationMediaManager notificationMediaManager, KeyguardBouncer.Factory factory, KeyguardMessageAreaController.Factory factory2, Optional optional, Lazy lazy, LatencyTracker latencyTracker) {
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mConfigurationController = configurationController;
        this.mNavigationModeController = navigationModeController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mMediaManager = notificationMediaManager;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDockManager = dockManager;
        this.mKeyguardBouncerFactory = factory;
        this.mKeyguardMessageAreaFactory = factory2;
        this.mShadeController = lazy;
        this.mLatencyTracker = latencyTracker;
        this.mFoldAodAnimationController = (FoldAodAnimationController) optional.map(PeopleSpaceWidgetManager$$ExternalSyntheticLambda6.INSTANCE$3).orElse((Object) null);
    }

    public final void onCancelClicked() {
    }

    public final void onDensityOrFontScaleChanged() {
        hideBouncer(true);
    }

    public final void showBouncer(boolean z) {
        resetAlternateAuth(false);
        if (this.mShowing && !this.mBouncer.isShowing()) {
            this.mBouncer.show(false, z);
        }
        updateStates();
    }

    public static class DismissWithActionRequest {
        public final boolean afterKeyguardGone;
        public final Runnable cancelAction;
        public final ActivityStarter.OnDismissAction dismissAction;
        public final String message;

        public DismissWithActionRequest(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
            this.dismissAction = onDismissAction;
            this.cancelAction = runnable;
            this.afterKeyguardGone = z;
            this.message = str;
        }
    }

    public final void blockPanelExpansionFromCurrentTouch() {
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mBlockingExpansionForCurrentTouch = notificationPanelViewController.mTracking;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean bouncerNeedsScrimming() {
        /*
            r4 = this;
            boolean r0 = r4.mOccluded
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0015
            com.android.systemui.dreams.DreamOverlayStateController r0 = r4.mDreamOverlayStateController
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mState
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0012
            r0 = r1
            goto L_0x0013
        L_0x0012:
            r0 = r2
        L_0x0013:
            if (r0 == 0) goto L_0x0061
        L_0x0015:
            com.android.systemui.statusbar.phone.KeyguardBouncer r0 = r4.mBouncer
            java.util.Objects.requireNonNull(r0)
            com.android.keyguard.KeyguardHostViewController r0 = r0.mKeyguardViewController
            if (r0 == 0) goto L_0x002e
            com.android.systemui.plugins.ActivityStarter$OnDismissAction r3 = r0.mDismissAction
            if (r3 != 0) goto L_0x0029
            java.lang.Runnable r0 = r0.mCancelAction
            if (r0 == 0) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r0 = r2
            goto L_0x002a
        L_0x0029:
            r0 = r1
        L_0x002a:
            if (r0 == 0) goto L_0x002e
            r0 = r1
            goto L_0x002f
        L_0x002e:
            r0 = r2
        L_0x002f:
            if (r0 != 0) goto L_0x0061
            com.android.systemui.statusbar.phone.KeyguardBouncer r0 = r4.mBouncer
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x0042
            com.android.systemui.statusbar.phone.KeyguardBouncer r0 = r4.mBouncer
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsScrimmed
            if (r0 != 0) goto L_0x0061
        L_0x0042:
            com.android.systemui.statusbar.phone.KeyguardBouncer r4 = r4.mBouncer
            java.util.Objects.requireNonNull(r4)
            com.android.keyguard.KeyguardHostViewController r4 = r4.mKeyguardViewController
            if (r4 == 0) goto L_0x005c
            com.android.keyguard.KeyguardSecurityContainerController r4 = r4.mKeyguardSecurityContainerController
            java.util.Objects.requireNonNull(r4)
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r4 = r4.mCurrentSecurityMode
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r0 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPin
            if (r4 == r0) goto L_0x005a
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r0 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPuk
            if (r4 != r0) goto L_0x005c
        L_0x005a:
            r4 = r1
            goto L_0x005d
        L_0x005c:
            r4 = r2
        L_0x005d:
            if (r4 == 0) goto L_0x0060
            goto L_0x0061
        L_0x0060:
            r1 = r2
        L_0x0061:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.bouncerNeedsScrimming():boolean");
    }

    public final void dismissAndCollapse() {
        this.mStatusBar.executeRunnableDismissingKeyguard((Runnable) null, true, false, true);
    }

    public final void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        boolean z2;
        boolean z3;
        boolean z4;
        Runnable runnable2;
        if (this.mShowing) {
            DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
            this.mPendingWakeupAction = null;
            if (!(dismissWithActionRequest == null || (runnable2 = dismissWithActionRequest.cancelAction) == null)) {
                runnable2.run();
            }
            if (this.mDozing) {
                BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
                Objects.requireNonNull(biometricUnlockController);
                int i = biometricUnlockController.mMode;
                if (i == 1 || i == 2) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (!z4) {
                    this.mPendingWakeupAction = new DismissWithActionRequest(onDismissAction, runnable, z, str);
                    return;
                }
            }
            this.mAfterKeyguardGoneAction = onDismissAction;
            this.mKeyguardGoneCancelAction = runnable;
            if (onDismissAction == null || !onDismissAction.willRunAnimationOnKeyguard()) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.mDismissActionWillAnimateOnKeyguard = z2;
            if (this.mAlternateAuthInterceptor == null || !this.mKeyguardUpdateManager.isUnlockingWithBiometricAllowed(true)) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (z3) {
                if (!z) {
                    KeyguardBouncer keyguardBouncer = this.mBouncer;
                    ActivityStarter.OnDismissAction onDismissAction2 = this.mAfterKeyguardGoneAction;
                    Runnable runnable3 = this.mKeyguardGoneCancelAction;
                    Objects.requireNonNull(keyguardBouncer);
                    KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
                    Objects.requireNonNull(keyguardHostViewController);
                    Runnable runnable4 = keyguardHostViewController.mCancelAction;
                    if (runnable4 != null) {
                        runnable4.run();
                        keyguardHostViewController.mCancelAction = null;
                    }
                    keyguardHostViewController.mDismissAction = onDismissAction2;
                    keyguardHostViewController.mCancelAction = runnable3;
                    this.mAfterKeyguardGoneAction = null;
                    this.mKeyguardGoneCancelAction = null;
                }
                UdfpsKeyguardViewController.C07092 r6 = (UdfpsKeyguardViewController.C07092) this.mAlternateAuthInterceptor;
                Objects.requireNonNull(r6);
                updateAlternateAuthShowing(UdfpsKeyguardViewController.m170$$Nest$mshowUdfpsBouncer(UdfpsKeyguardViewController.this, true));
                return;
            } else if (z) {
                KeyguardBouncer keyguardBouncer2 = this.mBouncer;
                Objects.requireNonNull(keyguardBouncer2);
                keyguardBouncer2.show(false, true);
            } else {
                KeyguardBouncer keyguardBouncer3 = this.mBouncer;
                ActivityStarter.OnDismissAction onDismissAction3 = this.mAfterKeyguardGoneAction;
                Runnable runnable5 = this.mKeyguardGoneCancelAction;
                Objects.requireNonNull(keyguardBouncer3);
                keyguardBouncer3.ensureView();
                KeyguardHostViewController keyguardHostViewController2 = keyguardBouncer3.mKeyguardViewController;
                Objects.requireNonNull(keyguardHostViewController2);
                Runnable runnable6 = keyguardHostViewController2.mCancelAction;
                if (runnable6 != null) {
                    runnable6.run();
                    keyguardHostViewController2.mCancelAction = null;
                }
                keyguardHostViewController2.mDismissAction = onDismissAction3;
                keyguardHostViewController2.mCancelAction = runnable5;
                keyguardBouncer3.show(false, true);
                this.mAfterKeyguardGoneAction = null;
                this.mKeyguardGoneCancelAction = null;
            }
        }
        updateStates();
    }

    public final void executeAfterKeyguardGoneAction() {
        ActivityStarter.OnDismissAction onDismissAction = this.mAfterKeyguardGoneAction;
        if (onDismissAction != null) {
            onDismissAction.onDismiss();
            this.mAfterKeyguardGoneAction = null;
        }
        this.mKeyguardGoneCancelAction = null;
        this.mDismissActionWillAnimateOnKeyguard = false;
        for (int i = 0; i < this.mAfterKeyguardGoneRunnables.size(); i++) {
            this.mAfterKeyguardGoneRunnables.get(i).run();
        }
        this.mAfterKeyguardGoneRunnables.clear();
    }

    public final ViewRootImpl getViewRootImpl() {
        return this.mNotificationShadeWindowController.getNotificationShadeView().getViewRootImpl();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ed  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void hide(long r28, long r30) {
        /*
            r27 = this;
            r0 = r27
            java.lang.String r1 = "StatusBarKeyguardViewManager#hide"
            android.os.Trace.beginSection(r1)
            r1 = 0
            r0.mShowing = r1
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r0.mKeyguardStateController
            boolean r3 = r2.isOccluded()
            r2.notifyKeyguardState(r1, r3)
            r27.launchPendingWakeupAction()
            com.android.keyguard.KeyguardUpdateMonitor r2 = r0.mKeyguardUpdateManager
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mNeedsSlowUnlockTransition
            if (r2 == 0) goto L_0x0022
            r2 = 2000(0x7d0, double:9.88E-321)
            goto L_0x0024
        L_0x0022:
            r2 = r30
        L_0x0024:
            long r4 = android.os.SystemClock.uptimeMillis()
            r6 = -48
            long r6 = r28 + r6
            long r6 = r6 - r4
            r4 = 0
            long r6 = java.lang.Math.max(r4, r6)
            com.android.systemui.statusbar.phone.StatusBar r8 = r0.mStatusBar
            boolean r8 = r8.isInLaunchTransition()
            r9 = 1
            if (r8 != 0) goto L_0x0151
            com.android.systemui.statusbar.policy.KeyguardStateController r8 = r0.mKeyguardStateController
            boolean r8 = r8.isFlingingToDismissKeyguard()
            if (r8 == 0) goto L_0x0046
            goto L_0x0151
        L_0x0046:
            r27.executeAfterKeyguardGoneAction()
            com.android.systemui.statusbar.phone.BiometricUnlockController r8 = r0.mBiometricUnlockController
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.mMode
            r10 = 2
            if (r8 != r10) goto L_0x0055
            r8 = r9
            goto L_0x0056
        L_0x0055:
            r8 = r1
        L_0x0056:
            boolean r17 = r27.needsBypassFading()
            if (r17 == 0) goto L_0x005f
            r2 = 67
            goto L_0x0063
        L_0x005f:
            if (r8 == 0) goto L_0x0065
            r2 = 240(0xf0, double:1.186E-321)
        L_0x0063:
            r12 = r4
            goto L_0x0066
        L_0x0065:
            r12 = r6
        L_0x0066:
            com.android.systemui.statusbar.phone.StatusBar r6 = r0.mStatusBar
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.CommandQueue r7 = r6.mCommandQueue
            int r11 = r6.mDisplayId
            long r14 = r28 + r2
            r25 = 120(0x78, double:5.93E-322)
            long r20 = r14 - r25
            r22 = 120(0x78, double:5.93E-322)
            r24 = 1
            r18 = r7
            r19 = r11
            r18.appTransitionStarting(r19, r20, r22, r24)
            com.android.systemui.statusbar.CommandQueue r7 = r6.mCommandQueue
            int r11 = r6.mDisplayId
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x008a
            r4 = r9
            goto L_0x008b
        L_0x008a:
            r4 = r1
        L_0x008b:
            r7.recomputeDisableFlags(r11, r4)
            com.android.systemui.statusbar.CommandQueue r4 = r6.mCommandQueue
            int r5 = r6.mDisplayId
            long r20 = r28 - r25
            r22 = 120(0x78, double:5.93E-322)
            r24 = 1
            r18 = r4
            r19 = r5
            r18.appTransitionStarting(r19, r20, r22, r24)
            com.android.systemui.statusbar.policy.KeyguardStateController r11 = r6.mKeyguardStateController
            r14 = r2
            r16 = r17
            r11.notifyKeyguardFadingAway(r12, r14, r16)
            com.android.systemui.statusbar.phone.BiometricUnlockController r4 = r0.mBiometricUnlockController
            java.util.Objects.requireNonNull(r4)
            android.os.Handler r5 = r4.mHandler
            com.android.systemui.statusbar.phone.BiometricUnlockController$2 r6 = new com.android.systemui.statusbar.phone.BiometricUnlockController$2
            r6.<init>()
            r11 = 96
            r5.postDelayed(r6, r11)
            r0.hideBouncer(r9)
            if (r8 == 0) goto L_0x00ed
            if (r17 == 0) goto L_0x00d1
            com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = r0.mNotificationPanelViewController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.phone.PanelView r4 = r4.mView
            android.view.View r5 = r0.mNotificationContainer
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8 r6 = new com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8
            r6.<init>(r0, r10)
            com.android.systemui.statusbar.notification.ViewGroupFadeHelper.fadeOutAllChildrenExcept(r4, r5, r2, r6)
            goto L_0x00e9
        L_0x00d1:
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r3 = r2.mNotificationPanelViewController
            com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0 r8 = new com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0
            r4 = 7
            r8.<init>(r2, r4)
            r4 = 0
            r6 = 96
            android.view.ViewPropertyAnimator r2 = r3.fadeOut(r4, r6, r8)
            r2.start()
        L_0x00e9:
            r27.wakeAndUnlockDejank()
            goto L_0x0143
        L_0x00ed:
            com.android.systemui.statusbar.SysuiStatusBarStateController r4 = r0.mStatusBarStateController
            boolean r4 = r4.leaveOpenOnKeyguardHide()
            if (r4 != 0) goto L_0x011d
            com.android.systemui.statusbar.NotificationShadeWindowController r4 = r0.mNotificationShadeWindowController
            r4.setKeyguardFadingAway(r9)
            if (r17 == 0) goto L_0x010f
            com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = r0.mNotificationPanelViewController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.phone.PanelView r4 = r4.mView
            android.view.View r5 = r0.mNotificationContainer
            com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19 r6 = new com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19
            r7 = 5
            r6.<init>(r0, r7)
            com.android.systemui.statusbar.notification.ViewGroupFadeHelper.fadeOutAllChildrenExcept(r4, r5, r2, r6)
            goto L_0x0114
        L_0x010f:
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            r2.hideKeyguard()
        L_0x0114:
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            r2.updateScrimController()
            r27.wakeAndUnlockDejank()
            goto L_0x0143
        L_0x011d:
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            r2.hideKeyguard()
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.policy.KeyguardStateController r3 = r2.mKeyguardStateController
            r3.notifyKeyguardDoneFading()
            com.android.systemui.statusbar.phone.ScrimController r2 = r2.mScrimController
            java.util.Objects.requireNonNull(r2)
            r2.mExpansionAffectsAlpha = r9
            com.android.systemui.statusbar.phone.BiometricUnlockController r2 = r0.mBiometricUnlockController
            java.util.Objects.requireNonNull(r2)
            boolean r3 = r2.isWakeAndUnlock()
            if (r3 == 0) goto L_0x0140
            r2.mFadedAwayAfterWakeAndUnlock = r9
        L_0x0140:
            r2.resetMode()
        L_0x0143:
            r27.updateStates()
            com.android.systemui.statusbar.NotificationShadeWindowController r2 = r0.mNotificationShadeWindowController
            r2.setKeyguardShowing(r1)
            com.android.keyguard.ViewMediatorCallback r0 = r0.mViewMediatorCallback
            r0.keyguardGone()
            goto L_0x0166
        L_0x0151:
            com.android.systemui.statusbar.policy.KeyguardStateController r1 = r0.mKeyguardStateController
            boolean r1 = r1.isFlingingToDismissKeyguard()
            com.android.systemui.statusbar.phone.StatusBar r2 = r0.mStatusBar
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$5 r3 = new com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$5
            r3.<init>()
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$6 r4 = new com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$6
            r4.<init>(r1)
            r2.fadeKeyguardAfterLaunchTransition(r3, r4)
        L_0x0166:
            r0 = 62
            com.android.systemui.shared.system.SysUiStatsLog.write(r0, r9)
            android.os.Trace.endSection()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.hide(long, long):void");
    }

    public void hideBouncer(boolean z) {
        Runnable runnable;
        if (this.mBouncer != null) {
            if (this.mShowing && !bouncerIsOrWillBeShowing()) {
                this.mAfterKeyguardGoneAction = null;
                this.mDismissActionWillAnimateOnKeyguard = false;
                Runnable runnable2 = this.mKeyguardGoneCancelAction;
                if (runnable2 != null) {
                    runnable2.run();
                    this.mKeyguardGoneCancelAction = null;
                }
            }
            KeyguardBouncer keyguardBouncer = this.mBouncer;
            Objects.requireNonNull(keyguardBouncer);
            if (keyguardBouncer.isShowing()) {
                SysUiStatsLog.write(63, 1);
                DismissCallbackRegistry dismissCallbackRegistry = keyguardBouncer.mDismissCallbackRegistry;
                Objects.requireNonNull(dismissCallbackRegistry);
                int size = dismissCallbackRegistry.mDismissCallbacks.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    DismissCallbackWrapper dismissCallbackWrapper = dismissCallbackRegistry.mDismissCallbacks.get(size);
                    Executor executor = dismissCallbackRegistry.mUiBgExecutor;
                    Objects.requireNonNull(dismissCallbackWrapper);
                    executor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(dismissCallbackWrapper, 4));
                }
                dismissCallbackRegistry.mDismissCallbacks.clear();
            }
            keyguardBouncer.mIsScrimmed = false;
            keyguardBouncer.mFalsingCollector.onBouncerHidden();
            keyguardBouncer.mCallback.onBouncerVisiblityChanged(false);
            KeyguardBouncer.C14392 r3 = keyguardBouncer.mShowRunnable;
            boolean z2 = DejankUtils.STRICT_MODE_ENABLED;
            Assert.isMainThread();
            DejankUtils.sPendingRunnables.remove(r3);
            DejankUtils.sHandler.removeCallbacks(r3);
            keyguardBouncer.mHandler.removeCallbacks(keyguardBouncer.mShowRunnable);
            keyguardBouncer.mShowingSoon = false;
            KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
            if (keyguardHostViewController != null) {
                Runnable runnable3 = keyguardHostViewController.mCancelAction;
                if (runnable3 != null) {
                    runnable3.run();
                    keyguardHostViewController.mCancelAction = null;
                }
                keyguardHostViewController.mDismissAction = null;
                keyguardHostViewController.mCancelAction = null;
                KeyguardHostViewController keyguardHostViewController2 = keyguardBouncer.mKeyguardViewController;
                Objects.requireNonNull(keyguardHostViewController2);
                keyguardHostViewController2.mKeyguardSecurityContainerController.onPause();
            }
            keyguardBouncer.mIsAnimatingAway = false;
            keyguardBouncer.setVisibility(4);
            if (z) {
                keyguardBouncer.mHandler.postDelayed(keyguardBouncer.mRemoveViewRunnable, 50);
            }
            DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
            this.mPendingWakeupAction = null;
            if (dismissWithActionRequest != null && (runnable = dismissWithActionRequest.cancelAction) != null) {
                runnable.run();
            }
        }
    }

    public final boolean isBouncerShowing() {
        if (this.mBouncer.isShowing() || isShowingAlternateAuth()) {
            return true;
        }
        return false;
    }

    public final boolean isGoingToNotificationShade() {
        return this.mStatusBarStateController.leaveOpenOnKeyguardHide();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isNavBarVisible() {
        /*
            r5 = this;
            com.android.systemui.statusbar.phone.BiometricUnlockController r0 = r5.mBiometricUnlockController
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0010
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMode
            r3 = 2
            if (r0 != r3) goto L_0x0010
            r0 = r1
            goto L_0x0011
        L_0x0010:
            r0 = r2
        L_0x0011:
            boolean r3 = r5.mShowing
            if (r3 == 0) goto L_0x001b
            boolean r3 = r5.mOccluded
            if (r3 != 0) goto L_0x001b
            r3 = r1
            goto L_0x001c
        L_0x001b:
            r3 = r2
        L_0x001c:
            boolean r4 = r5.mDozing
            if (r4 == 0) goto L_0x0024
            if (r0 != 0) goto L_0x0024
            r0 = r1
            goto L_0x0025
        L_0x0024:
            r0 = r2
        L_0x0025:
            if (r3 == 0) goto L_0x0029
            if (r4 == 0) goto L_0x0031
        L_0x0029:
            boolean r4 = r5.mPulsing
            if (r4 == 0) goto L_0x0037
            boolean r4 = r5.mIsDocked
            if (r4 != 0) goto L_0x0037
        L_0x0031:
            boolean r4 = r5.mGesturalNav
            if (r4 == 0) goto L_0x0037
            r4 = r1
            goto L_0x0038
        L_0x0037:
            r4 = r2
        L_0x0038:
            if (r3 != 0) goto L_0x0040
            if (r0 != 0) goto L_0x0040
            boolean r0 = r5.mScreenOffAnimationPlaying
            if (r0 == 0) goto L_0x0054
        L_0x0040:
            com.android.systemui.statusbar.phone.KeyguardBouncer r0 = r5.mBouncer
            boolean r0 = r0.isShowing()
            if (r0 != 0) goto L_0x0054
            boolean r0 = r5.mRemoteInputActive
            if (r0 != 0) goto L_0x0054
            if (r4 != 0) goto L_0x0054
            boolean r5 = r5.mGlobalActionsVisible
            if (r5 == 0) goto L_0x0053
            goto L_0x0054
        L_0x0053:
            r1 = r2
        L_0x0054:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.isNavBarVisible():boolean");
    }

    public final boolean isShowingAlternateAuth() {
        AlternateAuthInterceptor alternateAuthInterceptor = this.mAlternateAuthInterceptor;
        if (alternateAuthInterceptor != null) {
            UdfpsKeyguardViewController.C07092 r0 = (UdfpsKeyguardViewController.C07092) alternateAuthInterceptor;
            Objects.requireNonNull(r0);
            if (UdfpsKeyguardViewController.this.mShowingUdfpsBouncer) {
                return true;
            }
        }
        return false;
    }

    public final boolean isShowingAlternateAuthOrAnimating() {
        AlternateAuthInterceptor alternateAuthInterceptor = this.mAlternateAuthInterceptor;
        if (alternateAuthInterceptor != null) {
            UdfpsKeyguardViewController.C07092 r0 = (UdfpsKeyguardViewController.C07092) alternateAuthInterceptor;
            Objects.requireNonNull(r0);
            if (UdfpsKeyguardViewController.this.mShowingUdfpsBouncer) {
                return true;
            }
            Objects.requireNonNull(this.mAlternateAuthInterceptor);
        }
        return false;
    }

    public final boolean isUnlockWithWallpaper() {
        return this.mNotificationShadeWindowController.isShowingWallpaper();
    }

    public final void keyguardGoingAway() {
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mKeyguardStateController.notifyKeyguardGoingAway();
        CommandQueue commandQueue = statusBar.mCommandQueue;
        int i = statusBar.mDisplayId;
        Objects.requireNonNull(commandQueue);
        synchronized (commandQueue.mLock) {
            commandQueue.mHandler.obtainMessage(1245184, i, 1).sendToTarget();
        }
        statusBar.updateScrimController();
    }

    public final void launchPendingWakeupAction() {
        DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (dismissWithActionRequest == null) {
            return;
        }
        if (this.mShowing) {
            dismissWithAction(dismissWithActionRequest.dismissAction, dismissWithActionRequest.cancelAction, dismissWithActionRequest.afterKeyguardGone, dismissWithActionRequest.message);
            return;
        }
        ActivityStarter.OnDismissAction onDismissAction = dismissWithActionRequest.dismissAction;
        if (onDismissAction != null) {
            onDismissAction.onDismiss();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001c, code lost:
        if (r0.mMode == 1) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean needsBypassFading() {
        /*
            r3 = this;
            com.android.systemui.statusbar.phone.BiometricUnlockController r0 = r3.mBiometricUnlockController
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMode
            r1 = 7
            r2 = 1
            if (r0 == r1) goto L_0x001e
            com.android.systemui.statusbar.phone.BiometricUnlockController r0 = r3.mBiometricUnlockController
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMode
            r1 = 2
            if (r0 == r1) goto L_0x001e
            com.android.systemui.statusbar.phone.BiometricUnlockController r0 = r3.mBiometricUnlockController
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMode
            if (r0 != r2) goto L_0x0027
        L_0x001e:
            com.android.systemui.statusbar.phone.KeyguardBypassController r3 = r3.mBypassController
            boolean r3 = r3.getBypassEnabled()
            if (r3 == 0) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r2 = 0
        L_0x0028:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.needsBypassFading():boolean");
    }

    public final void notifyKeyguardAuthenticated() {
        KeyguardBouncer keyguardBouncer = this.mBouncer;
        Objects.requireNonNull(keyguardBouncer);
        keyguardBouncer.ensureView();
        KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        Objects.requireNonNull(keyguardHostViewController);
        keyguardHostViewController.mSecurityCallback.finish(false, currentUser);
        if (this.mAlternateAuthInterceptor != null && isShowingAlternateAuthOrAnimating()) {
            resetAlternateAuth(false);
            executeAfterKeyguardGoneAction();
        }
    }

    public final void onDozingChanged(boolean z) {
        if (this.mDozing != z) {
            this.mDozing = z;
            if (z || this.mBouncer.needsFullscreenBouncer() || this.mOccluded) {
                reset(z);
            }
            updateStates();
            if (!z) {
                launchPendingWakeupAction();
            }
        }
    }

    public final void onFinishedGoingToSleep() {
        KeyguardBouncer keyguardBouncer = this.mBouncer;
        Objects.requireNonNull(keyguardBouncer);
        if (keyguardBouncer.mKeyguardViewController != null && keyguardBouncer.mContainer.getVisibility() == 0) {
            KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
            Objects.requireNonNull(keyguardHostViewController);
            if (KeyguardHostViewController.DEBUG) {
                Log.d("KeyguardViewBase", String.format("screen off, instance %s at %s", new Object[]{Integer.toHexString(keyguardHostViewController.hashCode()), Long.valueOf(SystemClock.uptimeMillis())}));
            }
            keyguardHostViewController.mKeyguardSecurityContainerController.showPrimarySecurityScreen(true);
            keyguardHostViewController.mKeyguardSecurityContainerController.onPause();
            ((KeyguardHostView) keyguardHostViewController.mView).clearFocus();
        }
    }

    public final void onFoldToAodAnimationChanged() {
        FoldAodAnimationController foldAodAnimationController = this.mFoldAodAnimationController;
        if (foldAodAnimationController != null) {
            Objects.requireNonNull(foldAodAnimationController);
            this.mScreenOffAnimationPlaying = foldAodAnimationController.shouldPlayAnimation;
        }
    }

    public final void onKeyguardFadedAway() {
        this.mNotificationContainer.postDelayed(new TaskView$$ExternalSyntheticLambda6(this, 5), 100);
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        ViewGroupFadeHelper.reset(notificationPanelViewController.mView);
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mKeyguardStateController.notifyKeyguardDoneFading();
        ScrimController scrimController = statusBar.mScrimController;
        Objects.requireNonNull(scrimController);
        scrimController.mExpansionAffectsAlpha = true;
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        Objects.requireNonNull(biometricUnlockController);
        if (biometricUnlockController.isWakeAndUnlock()) {
            biometricUnlockController.mFadedAwayAfterWakeAndUnlock = true;
        }
        biometricUnlockController.resetMode();
        WindowManagerGlobal.getInstance().trimMemory(20);
    }

    public final void onPanelExpansionChanged(float f, boolean z, boolean z2) {
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        if (notificationPanelViewController.mHintAnimationRunning) {
            this.mBouncer.setExpansion(1.0f);
        } else if (this.mStatusBarStateController.getState() == 2 && this.mKeyguardUpdateManager.isUdfpsEnrolled()) {
        } else {
            if (bouncerNeedsScrimming()) {
                this.mBouncer.setExpansion(0.0f);
            } else if (this.mShowing) {
                BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
                Objects.requireNonNull(biometricUnlockController);
                int i = biometricUnlockController.mMode;
                boolean z3 = true;
                if (!(i == 1 || i == 2)) {
                    z3 = false;
                }
                if (!z3 && !this.mStatusBar.isInLaunchTransition()) {
                    this.mBouncer.setExpansion(f);
                }
                if (f != 1.0f && z2 && !this.mKeyguardStateController.canDismissLockScreen() && !this.mBouncer.isShowing()) {
                    KeyguardBouncer keyguardBouncer = this.mBouncer;
                    Objects.requireNonNull(keyguardBouncer);
                    if (!keyguardBouncer.mIsAnimatingAway) {
                        this.mBouncer.show(false, false);
                    }
                }
            } else if (this.mPulsing && f == 0.0f) {
                this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), this.mStatusBar.getBouncerContainer(), "BOUNCER_VISIBLE");
            }
        }
    }

    public final void onRemoteInputActive(boolean z) {
        this.mRemoteInputActive = z;
        updateStates();
    }

    public final void onStartedGoingToSleep() {
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mNotificationShadeWindowView.getWindowInsetsController().setAnimationsDisabled(true);
        NavigationBarView navigationBarView = this.mStatusBar.getNavigationBarView();
        if (navigationBarView != null) {
            View view = navigationBarView.mVertical;
            if (view != null) {
                view.animate().alpha(0.0f).setDuration(125).start();
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                view2.animate().alpha(0.0f).setDuration(125).start();
            }
        }
    }

    public final void onStartedWakingUp() {
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mNotificationShadeWindowView.getWindowInsetsController().setAnimationsDisabled(false);
        NavigationBarView navigationBarView = this.mStatusBar.getNavigationBarView();
        if (navigationBarView != null) {
            View view = navigationBarView.mVertical;
            if (view != null) {
                view.animate().alpha(1.0f).setDuration(125).start();
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                view2.animate().alpha(1.0f).setDuration(125).start();
            }
        }
    }

    public final void onThemeChanged() {
        boolean isShowing = this.mBouncer.isShowing();
        KeyguardBouncer keyguardBouncer = this.mBouncer;
        Objects.requireNonNull(keyguardBouncer);
        boolean z = keyguardBouncer.mIsScrimmed;
        hideBouncer(true);
        KeyguardBouncer keyguardBouncer2 = this.mBouncer;
        Objects.requireNonNull(keyguardBouncer2);
        boolean z2 = keyguardBouncer2.mInitialized;
        keyguardBouncer2.ensureView();
        if (z2) {
            KeyguardHostViewController keyguardHostViewController = keyguardBouncer2.mKeyguardViewController;
            if (KeyguardHostViewController.DEBUG) {
                Objects.requireNonNull(keyguardHostViewController);
                Log.d("KeyguardViewBase", "show()");
            }
            keyguardHostViewController.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
        }
        keyguardBouncer2.mBouncerPromptReason = keyguardBouncer2.mCallback.getBouncerPromptReason();
        if (isShowing) {
            showBouncer(z);
        }
    }

    public final void requestFp(boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        Objects.requireNonNull(keyguardUpdateMonitor);
        keyguardUpdateMonitor.mOccludingAppRequestingFp = z;
        keyguardUpdateMonitor.updateFingerprintListeningState(2);
        AlternateAuthInterceptor alternateAuthInterceptor = this.mAlternateAuthInterceptor;
        if (alternateAuthInterceptor != null) {
            UdfpsKeyguardViewController.C07092 r2 = (UdfpsKeyguardViewController.C07092) alternateAuthInterceptor;
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mUdfpsRequested = z;
            UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) udfpsKeyguardViewController.mView;
            Objects.requireNonNull(udfpsKeyguardView);
            udfpsKeyguardView.mUdfpsRequested = z;
            UdfpsKeyguardViewController.this.updateAlpha();
            UdfpsKeyguardViewController.this.updatePauseAuth();
        }
    }

    public final void reset(boolean z) {
        if (this.mShowing) {
            this.mNotificationPanelViewController.resetViews(true);
            if (!this.mOccluded || this.mDozing) {
                if (!this.mBouncer.needsFullscreenBouncer() || this.mDozing) {
                    StatusBar statusBar = this.mStatusBar;
                    Objects.requireNonNull(statusBar);
                    statusBar.mStatusBarStateController.setKeyguardRequested(true);
                    statusBar.mStatusBarStateController.setLeaveOpenOnKeyguardHide(false);
                    statusBar.updateIsKeyguard();
                    AssistManager assistManager = statusBar.mAssistManagerLazy.get();
                    Objects.requireNonNull(assistManager);
                    AsyncTask.execute(new Runnable() {
                        public final void run(
/*
Method generation error in method: com.android.systemui.assist.AssistManager.4.run():void, dex: classes.dex
                        jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.assist.AssistManager.4.run():void, class status: UNLOADED
                        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        
*/
                    });
                    if (z) {
                        hideBouncer(false);
                        KeyguardBouncer keyguardBouncer = this.mBouncer;
                        Objects.requireNonNull(keyguardBouncer);
                        boolean z2 = keyguardBouncer.mInitialized;
                        keyguardBouncer.ensureView();
                        if (z2) {
                            KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
                            if (KeyguardHostViewController.DEBUG) {
                                Objects.requireNonNull(keyguardHostViewController);
                                Log.d("KeyguardViewBase", "show()");
                            }
                            keyguardHostViewController.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
                        }
                        keyguardBouncer.mBouncerPromptReason = keyguardBouncer.mCallback.getBouncerPromptReason();
                    }
                } else {
                    this.mStatusBar.hideKeyguard();
                    KeyguardBouncer keyguardBouncer2 = this.mBouncer;
                    Objects.requireNonNull(keyguardBouncer2);
                    keyguardBouncer2.show(true, true);
                }
                updateStates();
            } else {
                this.mStatusBar.hideKeyguard();
                if (z || this.mBouncer.needsFullscreenBouncer()) {
                    hideBouncer(false);
                }
            }
            resetAlternateAuth(false);
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
            Objects.requireNonNull(keyguardUpdateMonitor);
            keyguardUpdateMonitor.mHandler.obtainMessage(312).sendToTarget();
            updateStates();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0010, code lost:
        if (com.android.systemui.biometrics.UdfpsKeyguardViewController.m170$$Nest$mshowUdfpsBouncer(r0.this$0, false) == false) goto L_0x0012;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0012, code lost:
        if (r3 != false) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0015, code lost:
        updateAlternateAuthShowing(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resetAlternateAuth(boolean r3) {
        /*
            r2 = this;
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$AlternateAuthInterceptor r0 = r2.mAlternateAuthInterceptor
            r1 = 0
            if (r0 == 0) goto L_0x0012
            com.android.systemui.biometrics.UdfpsKeyguardViewController$2 r0 = (com.android.systemui.biometrics.UdfpsKeyguardViewController.C07092) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.biometrics.UdfpsKeyguardViewController r0 = com.android.systemui.biometrics.UdfpsKeyguardViewController.this
            boolean r0 = com.android.systemui.biometrics.UdfpsKeyguardViewController.m170$$Nest$mshowUdfpsBouncer(r0, r1)
            if (r0 != 0) goto L_0x0014
        L_0x0012:
            if (r3 == 0) goto L_0x0015
        L_0x0014:
            r1 = 1
        L_0x0015:
            r2.updateAlternateAuthShowing(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.resetAlternateAuth(boolean):void");
    }

    public final void setKeyguardGoingAwayState(boolean z) {
        this.mNotificationShadeWindowController.setKeyguardGoingAway(z);
    }

    public final void setNeedsInput(boolean z) {
        this.mNotificationShadeWindowController.setKeyguardNeedsInput(z);
    }

    public final void setOccluded(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mIsOccluded = z;
        StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = statusBar.mStatusBarHideIconsForBouncerManager;
        Objects.requireNonNull(statusBarHideIconsForBouncerManager);
        statusBarHideIconsForBouncerManager.isOccluded = z;
        statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
        ScrimController scrimController = statusBar.mScrimController;
        Objects.requireNonNull(scrimController);
        scrimController.mKeyguardOccluded = z;
        scrimController.updateScrims();
        if (z && !this.mOccluded && this.mShowing) {
            SysUiStatsLog.write(62, 3);
            if (this.mStatusBar.isInLaunchTransition()) {
                this.mOccluded = true;
                updateStates();
                this.mStatusBar.fadeKeyguardAfterLaunchTransition((C15625) null, new Runnable() {
                    public final void run() {
                        StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                        statusBarKeyguardViewManager.mNotificationShadeWindowController.setKeyguardOccluded(statusBarKeyguardViewManager.mOccluded);
                        StatusBarKeyguardViewManager.this.reset(true);
                    }
                });
                return;
            }
            StatusBar statusBar2 = this.mStatusBar;
            Objects.requireNonNull(statusBar2);
            if (statusBar2.mIsLaunchingActivityOverLockscreen) {
                this.mOccluded = true;
                updateStates();
                this.mShadeController.get().addPostCollapseAction(new CarrierTextManager$$ExternalSyntheticLambda0(this, 6));
                return;
            }
        } else if (!z && this.mOccluded && this.mShowing) {
            SysUiStatsLog.write(62, 2);
        }
        if (this.mOccluded || !z) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.mOccluded = z;
        updateStates();
        if (this.mShowing) {
            NotificationMediaManager notificationMediaManager = this.mMediaManager;
            if (!z2 || z) {
                z4 = false;
            } else {
                z4 = true;
            }
            notificationMediaManager.updateMediaMetaData(false, z4);
        }
        this.mNotificationShadeWindowController.setKeyguardOccluded(z);
        if (!this.mDozing) {
            reset(z3);
        }
        if (z2 && !z && this.mShowing && !this.mBouncer.isShowing()) {
            StatusBar statusBar3 = this.mStatusBar;
            Objects.requireNonNull(statusBar3);
            NotificationPanelViewController notificationPanelViewController = statusBar3.mNotificationPanelViewController;
            Objects.requireNonNull(notificationPanelViewController);
            notificationPanelViewController.setExpandedHeightInternal(((float) notificationPanelViewController.getMaxPanelHeight()) * 0.0f);
            statusBar3.mCommandQueueCallbacks.animateExpandNotificationsPanel();
            ScrimController scrimController2 = statusBar3.mScrimController;
            Objects.requireNonNull(scrimController2);
            scrimController2.mUnOcclusionAnimationRunning = true;
        }
    }

    public final boolean shouldDisableWindowAnimationsForUnlock() {
        return this.mStatusBar.isInLaunchTransition();
    }

    public final void show$2() {
        Trace.beginSection("StatusBarKeyguardViewManager#show");
        this.mShowing = true;
        this.mNotificationShadeWindowController.setKeyguardShowing(true);
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        keyguardStateController.notifyKeyguardState(this.mShowing, keyguardStateController.isOccluded());
        reset(true);
        SysUiStatsLog.write(62, 2);
        Trace.endSection();
    }

    public final void showGenericBouncer() {
        boolean z;
        if (this.mAlternateAuthInterceptor == null || !this.mKeyguardUpdateManager.isUnlockingWithBiometricAllowed(true)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            UdfpsKeyguardViewController.C07092 r0 = (UdfpsKeyguardViewController.C07092) this.mAlternateAuthInterceptor;
            Objects.requireNonNull(r0);
            updateAlternateAuthShowing(UdfpsKeyguardViewController.m170$$Nest$mshowUdfpsBouncer(UdfpsKeyguardViewController.this, true));
            return;
        }
        showBouncer(true);
    }

    public final void startPreHideAnimation(QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0) {
        boolean z;
        if (this.mBouncer.isShowing()) {
            KeyguardBouncer keyguardBouncer = this.mBouncer;
            Objects.requireNonNull(keyguardBouncer);
            keyguardBouncer.mIsAnimatingAway = true;
            KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
            if (keyguardHostViewController != null) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController.mKeyguardSecurityContainerController;
                Objects.requireNonNull(keyguardSecurityContainerController);
                KeyguardSecurityModel.SecurityMode securityMode = keyguardSecurityContainerController.mCurrentSecurityMode;
                if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
                    KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecurityContainerController.mView;
                    Objects.requireNonNull(keyguardSecurityContainer);
                    keyguardSecurityContainer.mDisappearAnimRunning = true;
                    keyguardSecurityContainer.mViewMode.startDisappearAnimation(securityMode);
                    z = keyguardSecurityContainerController.getCurrentSecurityController().startDisappearAnimation(qSTileImpl$$ExternalSyntheticLambda0);
                } else {
                    z = false;
                }
                if (!z && qSTileImpl$$ExternalSyntheticLambda0 != null) {
                    qSTileImpl$$ExternalSyntheticLambda0.run();
                }
            } else if (qSTileImpl$$ExternalSyntheticLambda0 != null) {
                qSTileImpl$$ExternalSyntheticLambda0.run();
            }
            StatusBar statusBar = this.mStatusBar;
            Objects.requireNonNull(statusBar);
            NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
            Objects.requireNonNull(notificationPanelViewController);
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController.mKeyguardQsUserSwitchController;
            if (keyguardQsUserSwitchController != null) {
                int i = notificationPanelViewController.mBarState;
                keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i, true, false, i);
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController.mKeyguardUserSwitcherController;
            if (keyguardUserSwitcherController != null) {
                int i2 = notificationPanelViewController.mBarState;
                keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i2, true, false, i2);
            }
            if (this.mDismissActionWillAnimateOnKeyguard) {
                updateStates();
            }
        } else if (qSTileImpl$$ExternalSyntheticLambda0 != null) {
            qSTileImpl$$ExternalSyntheticLambda0.run();
        }
        NotificationPanelViewController notificationPanelViewController2 = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController2);
        notificationPanelViewController2.mBlockingExpansionForCurrentTouch = notificationPanelViewController2.mTracking;
    }

    public final void updateAlternateAuthShowing(boolean z) {
        KeyguardMessageAreaController keyguardMessageAreaController = this.mKeyguardMessageAreaController;
        if (keyguardMessageAreaController != null) {
            boolean isShowingAlternateAuth = isShowingAlternateAuth();
            Objects.requireNonNull(keyguardMessageAreaController);
            KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) keyguardMessageAreaController.mView;
            Objects.requireNonNull(keyguardMessageArea);
            if (keyguardMessageArea.mAltBouncerShowing != isShowingAlternateAuth) {
                keyguardMessageArea.mAltBouncerShowing = isShowingAlternateAuth;
                keyguardMessageArea.update();
            }
        }
        KeyguardBypassController keyguardBypassController = this.mBypassController;
        boolean isShowingAlternateAuth2 = isShowingAlternateAuth();
        Objects.requireNonNull(keyguardBypassController);
        keyguardBypassController.altBouncerShowing = isShowingAlternateAuth2;
        if (z) {
            this.mStatusBar.updateScrimController();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0252  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0177  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateStates() {
        /*
            r20 = this;
            r0 = r20
            boolean r1 = r0.mShowing
            boolean r2 = r0.mOccluded
            com.android.systemui.statusbar.phone.KeyguardBouncer r3 = r0.mBouncer
            boolean r3 = r3.isShowing()
            boolean r4 = r20.bouncerIsOrWillBeShowing()
            com.android.systemui.statusbar.phone.KeyguardBouncer r5 = r0.mBouncer
            java.util.Objects.requireNonNull(r5)
            com.android.keyguard.KeyguardHostViewController r5 = r5.mKeyguardViewController
            r6 = 0
            r7 = 1
            if (r5 == 0) goto L_0x002c
            com.android.keyguard.KeyguardSecurityContainerController r5 = r5.mKeyguardSecurityContainerController
            java.util.Objects.requireNonNull(r5)
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r5 = r5.mCurrentSecurityMode
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r8 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPin
            if (r5 == r8) goto L_0x002a
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r8 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPuk
            if (r5 != r8) goto L_0x002c
        L_0x002a:
            r5 = r7
            goto L_0x002d
        L_0x002c:
            r5 = r6
        L_0x002d:
            r5 = r5 ^ r7
            boolean r8 = r0.mRemoteInputActive
            if (r5 != 0) goto L_0x0039
            if (r1 == 0) goto L_0x0039
            if (r8 == 0) goto L_0x0037
            goto L_0x0039
        L_0x0037:
            r9 = r6
            goto L_0x003a
        L_0x0039:
            r9 = r7
        L_0x003a:
            boolean r10 = r0.mLastBouncerDismissible
            if (r10 != 0) goto L_0x0049
            boolean r10 = r0.mLastShowing
            if (r10 == 0) goto L_0x0049
            boolean r10 = r0.mLastRemoteInputActive
            if (r10 == 0) goto L_0x0047
            goto L_0x0049
        L_0x0047:
            r10 = r6
            goto L_0x004a
        L_0x0049:
            r10 = r7
        L_0x004a:
            if (r9 != r10) goto L_0x0050
            boolean r9 = r0.mFirstUpdate
            if (r9 == 0) goto L_0x007f
        L_0x0050:
            if (r5 != 0) goto L_0x006b
            if (r1 == 0) goto L_0x006b
            if (r8 == 0) goto L_0x0057
            goto L_0x006b
        L_0x0057:
            com.android.systemui.statusbar.phone.KeyguardBouncer r9 = r0.mBouncer
            java.util.Objects.requireNonNull(r9)
            android.view.ViewGroup r10 = r9.mContainer
            int r10 = r10.getSystemUiVisibility()
            r11 = 4194304(0x400000, float:5.877472E-39)
            r10 = r10 | r11
            android.view.ViewGroup r9 = r9.mContainer
            r9.setSystemUiVisibility(r10)
            goto L_0x007f
        L_0x006b:
            com.android.systemui.statusbar.phone.KeyguardBouncer r9 = r0.mBouncer
            java.util.Objects.requireNonNull(r9)
            android.view.ViewGroup r10 = r9.mContainer
            int r10 = r10.getSystemUiVisibility()
            r11 = -4194305(0xffffffffffbfffff, float:NaN)
            r10 = r10 & r11
            android.view.ViewGroup r9 = r9.mContainer
            r9.setSystemUiVisibility(r10)
        L_0x007f:
            boolean r9 = r20.isNavBarVisible()
            boolean r10 = r0.mLastShowing
            if (r10 == 0) goto L_0x008d
            boolean r10 = r0.mLastOccluded
            if (r10 != 0) goto L_0x008d
            r10 = r7
            goto L_0x008e
        L_0x008d:
            r10 = r6
        L_0x008e:
            boolean r11 = r0.mLastDozing
            r12 = 2
            if (r11 == 0) goto L_0x0099
            int r13 = r0.mLastBiometricMode
            if (r13 == r12) goto L_0x0099
            r13 = r7
            goto L_0x009a
        L_0x0099:
            r13 = r6
        L_0x009a:
            if (r10 == 0) goto L_0x009e
            if (r11 == 0) goto L_0x00a6
        L_0x009e:
            boolean r11 = r0.mLastPulsing
            if (r11 == 0) goto L_0x00ac
            boolean r11 = r0.mLastIsDocked
            if (r11 != 0) goto L_0x00ac
        L_0x00a6:
            boolean r11 = r0.mLastGesturalNav
            if (r11 == 0) goto L_0x00ac
            r11 = r7
            goto L_0x00ad
        L_0x00ac:
            r11 = r6
        L_0x00ad:
            if (r10 != 0) goto L_0x00b5
            if (r13 != 0) goto L_0x00b5
            boolean r10 = r0.mLastScreenOffAnimationPlaying
            if (r10 == 0) goto L_0x00c6
        L_0x00b5:
            boolean r10 = r0.mLastBouncerShowing
            if (r10 != 0) goto L_0x00c6
            boolean r10 = r0.mLastRemoteInputActive
            if (r10 != 0) goto L_0x00c6
            if (r11 != 0) goto L_0x00c6
            boolean r10 = r0.mLastGlobalActionsVisible
            if (r10 == 0) goto L_0x00c4
            goto L_0x00c6
        L_0x00c4:
            r10 = r6
            goto L_0x00c7
        L_0x00c6:
            r10 = r7
        L_0x00c7:
            if (r9 != r10) goto L_0x00cd
            boolean r10 = r0.mFirstUpdate
            if (r10 == 0) goto L_0x011f
        L_0x00cd:
            com.android.systemui.statusbar.phone.StatusBar r10 = r0.mStatusBar
            com.android.systemui.navigationbar.NavigationBarView r10 = r10.getNavigationBarView()
            if (r10 == 0) goto L_0x011f
            if (r9 == 0) goto L_0x0106
            com.android.systemui.statusbar.policy.KeyguardStateController r9 = r0.mKeyguardStateController
            boolean r9 = r9.isKeyguardFadingAway()
            r10 = 0
            if (r9 == 0) goto L_0x00e8
            com.android.systemui.statusbar.policy.KeyguardStateController r9 = r0.mKeyguardStateController
            long r13 = r9.getKeyguardFadingAwayDelay()
            goto L_0x00f4
        L_0x00e8:
            com.android.systemui.statusbar.phone.KeyguardBouncer r9 = r0.mBouncer
            boolean r9 = r9.isShowing()
            if (r9 == 0) goto L_0x00f3
            r13 = 320(0x140, double:1.58E-321)
            goto L_0x00f4
        L_0x00f3:
            r13 = r10
        L_0x00f4:
            int r9 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r9 != 0) goto L_0x00fe
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$7 r9 = r0.mMakeNavigationBarVisibleRunnable
            r9.run()
            goto L_0x011f
        L_0x00fe:
            android.view.View r9 = r0.mNotificationContainer
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$7 r10 = r0.mMakeNavigationBarVisibleRunnable
            r9.postOnAnimationDelayed(r10, r13)
            goto L_0x011f
        L_0x0106:
            android.view.View r9 = r0.mNotificationContainer
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$7 r10 = r0.mMakeNavigationBarVisibleRunnable
            r9.removeCallbacks(r10)
            com.android.systemui.statusbar.phone.StatusBar r9 = r0.mStatusBar
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.phone.NotificationShadeWindowView r9 = r9.mNotificationShadeWindowView
            android.view.WindowInsetsController r9 = r9.getWindowInsetsController()
            int r10 = android.view.WindowInsets.Type.navigationBars()
            r9.hide(r10)
        L_0x011f:
            boolean r9 = r0.mLastBouncerShowing
            if (r3 != r9) goto L_0x0127
            boolean r9 = r0.mFirstUpdate
            if (r9 == 0) goto L_0x015f
        L_0x0127:
            com.android.systemui.statusbar.NotificationShadeWindowController r9 = r0.mNotificationShadeWindowController
            r9.setBouncerShowing(r3)
            com.android.systemui.statusbar.phone.StatusBar r9 = r0.mStatusBar
            java.util.Objects.requireNonNull(r9)
            r9.mBouncerShowing = r3
            com.android.systemui.statusbar.phone.KeyguardBypassController r10 = r9.mKeyguardBypassController
            java.util.Objects.requireNonNull(r10)
            r10.bouncerShowing = r3
            com.android.systemui.statusbar.PulseExpansionHandler r10 = r9.mPulseExpansionHandler
            java.util.Objects.requireNonNull(r10)
            r10.bouncerShowing = r3
            r9.setBouncerShowingForStatusBarComponents(r3)
            com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager r10 = r9.mStatusBarHideIconsForBouncerManager
            java.util.Objects.requireNonNull(r10)
            r10.bouncerShowing = r3
            r10.updateHideIconsForBouncer(r7)
            com.android.systemui.statusbar.CommandQueue r10 = r9.mCommandQueue
            int r11 = r9.mDisplayId
            r10.recomputeDisableFlags(r11, r7)
            r9.updateScrimController()
            boolean r10 = r9.mBouncerShowing
            if (r10 != 0) goto L_0x015f
            r9.updatePanelExpansionForKeyguard()
        L_0x015f:
            boolean r9 = r0.mLastOccluded
            java.lang.String r10 = "KeyguardUpdateMonitor"
            java.lang.String r11 = ")"
            if (r2 != r9) goto L_0x016b
            boolean r9 = r0.mFirstUpdate
            if (r9 == 0) goto L_0x01ad
        L_0x016b:
            com.android.keyguard.KeyguardUpdateMonitor r9 = r0.mKeyguardUpdateManager
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.util.Assert.isMainThread()
            boolean r13 = com.android.keyguard.KeyguardUpdateMonitor.DEBUG
            if (r13 == 0) goto L_0x018e
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "onKeyguardOccludedChanged("
            r13.append(r14)
            r13.append(r2)
            r13.append(r11)
            java.lang.String r13 = r13.toString()
            android.util.Log.d(r10, r13)
        L_0x018e:
            r13 = r6
        L_0x018f:
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r14 = r9.mCallbacks
            int r14 = r14.size()
            if (r13 >= r14) goto L_0x01ad
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r14 = r9.mCallbacks
            java.lang.Object r14 = r14.get(r13)
            java.lang.ref.WeakReference r14 = (java.lang.ref.WeakReference) r14
            java.lang.Object r14 = r14.get()
            com.android.keyguard.KeyguardUpdateMonitorCallback r14 = (com.android.keyguard.KeyguardUpdateMonitorCallback) r14
            if (r14 == 0) goto L_0x01aa
            r14.onKeyguardOccludedChanged(r2)
        L_0x01aa:
            int r13 = r13 + 1
            goto L_0x018f
        L_0x01ad:
            if (r1 == 0) goto L_0x01b3
            if (r2 != 0) goto L_0x01b3
            r9 = r7
            goto L_0x01b4
        L_0x01b3:
            r9 = r6
        L_0x01b4:
            boolean r13 = r0.mLastShowing
            if (r13 == 0) goto L_0x01be
            boolean r13 = r0.mLastOccluded
            if (r13 != 0) goto L_0x01be
            r13 = r7
            goto L_0x01bf
        L_0x01be:
            r13 = r6
        L_0x01bf:
            if (r9 != r13) goto L_0x01c5
            boolean r9 = r0.mFirstUpdate
            if (r9 == 0) goto L_0x0241
        L_0x01c5:
            com.android.keyguard.KeyguardUpdateMonitor r9 = r0.mKeyguardUpdateManager
            if (r1 == 0) goto L_0x01cc
            if (r2 != 0) goto L_0x01cc
            goto L_0x01cd
        L_0x01cc:
            r7 = r6
        L_0x01cd:
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.util.Assert.isMainThread()
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "onKeyguardVisibilityChanged("
            r13.append(r14)
            r13.append(r7)
            r13.append(r11)
            java.lang.String r13 = r13.toString()
            android.util.Log.d(r10, r13)
            r9.mKeyguardIsVisible = r7
            if (r7 == 0) goto L_0x01f0
            r9.mSecureCameraLaunched = r6
        L_0x01f0:
            com.android.systemui.statusbar.phone.KeyguardBypassController r13 = r9.mKeyguardBypassController
            if (r13 == 0) goto L_0x01f6
            r13.userHasDeviceEntryIntent = r6
        L_0x01f6:
            r13 = r6
        L_0x01f7:
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r14 = r9.mCallbacks
            int r14 = r14.size()
            if (r13 >= r14) goto L_0x023d
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r14 = r9.mCallbacks
            java.lang.Object r14 = r14.get(r13)
            java.lang.ref.WeakReference r14 = (java.lang.ref.WeakReference) r14
            java.lang.Object r14 = r14.get()
            com.android.keyguard.KeyguardUpdateMonitorCallback r14 = (com.android.keyguard.KeyguardUpdateMonitorCallback) r14
            if (r14 == 0) goto L_0x0234
            r16 = r13
            long r12 = android.os.SystemClock.elapsedRealtime()
            boolean r15 = r14.mShowing
            if (r7 != r15) goto L_0x022b
            r17 = r7
            long r6 = r14.mVisibilityChangedCalled
            long r6 = r12 - r6
            r18 = 1000(0x3e8, double:4.94E-321)
            int r6 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1))
            if (r6 >= 0) goto L_0x0228
            r6 = r17
            goto L_0x0237
        L_0x0228:
            r6 = r17
            goto L_0x022c
        L_0x022b:
            r6 = r7
        L_0x022c:
            r14.onKeyguardVisibilityChanged(r6)
            r14.mVisibilityChangedCalled = r12
            r14.mShowing = r6
            goto L_0x0237
        L_0x0234:
            r6 = r7
            r16 = r13
        L_0x0237:
            int r13 = r16 + 1
            r7 = r6
            r6 = 0
            r12 = 2
            goto L_0x01f7
        L_0x023d:
            r7 = r12
            r9.updateBiometricListeningState(r7)
        L_0x0241:
            boolean r6 = r0.mLastBouncerIsOrWillBeShowing
            if (r4 != r6) goto L_0x024c
            boolean r6 = r0.mFirstUpdate
            if (r6 == 0) goto L_0x024a
            goto L_0x024c
        L_0x024a:
            r6 = 0
            goto L_0x027b
        L_0x024c:
            com.android.keyguard.KeyguardUpdateMonitor r6 = r0.mKeyguardUpdateManager
            boolean r7 = com.android.keyguard.KeyguardUpdateMonitor.DEBUG
            if (r7 == 0) goto L_0x026d
            java.util.Objects.requireNonNull(r6)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "sendKeyguardBouncerChanged("
            r7.append(r9)
            r7.append(r4)
            r7.append(r11)
            java.lang.String r7 = r7.toString()
            android.util.Log.d(r10, r7)
        L_0x026d:
            com.android.keyguard.KeyguardUpdateMonitor$14 r6 = r6.mHandler
            r7 = 322(0x142, float:4.51E-43)
            android.os.Message r6 = r6.obtainMessage(r7)
            r6.arg1 = r4
            r6.sendToTarget()
            goto L_0x024a
        L_0x027b:
            r0.mFirstUpdate = r6
            r0.mLastShowing = r1
            boolean r1 = r0.mGlobalActionsVisible
            r0.mLastGlobalActionsVisible = r1
            r0.mLastOccluded = r2
            r0.mLastBouncerShowing = r3
            r0.mLastBouncerIsOrWillBeShowing = r4
            r0.mLastBouncerDismissible = r5
            r0.mLastRemoteInputActive = r8
            boolean r1 = r0.mDozing
            r0.mLastDozing = r1
            boolean r1 = r0.mPulsing
            r0.mLastPulsing = r1
            boolean r1 = r0.mScreenOffAnimationPlaying
            r0.mLastScreenOffAnimationPlaying = r1
            com.android.systemui.statusbar.phone.BiometricUnlockController r1 = r0.mBiometricUnlockController
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mMode
            r0.mLastBiometricMode = r1
            boolean r1 = r0.mGesturalNav
            r0.mLastGesturalNav = r1
            boolean r1 = r0.mIsDocked
            r0.mLastIsDocked = r1
            com.android.systemui.statusbar.phone.StatusBar r0 = r0.mStatusBar
            java.util.Objects.requireNonNull(r0)
            r0.logStateToEventlog()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.updateStates():void");
    }

    public final void wakeAndUnlockDejank() {
        int i;
        if (this.mBiometricUnlockController.isWakeAndUnlock() && this.mLatencyTracker.isEnabled()) {
            BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
            Objects.requireNonNull(biometricUnlockController);
            BiometricSourceType biometricSourceType = biometricUnlockController.mBiometricType;
            LatencyTracker latencyTracker = this.mLatencyTracker;
            if (biometricSourceType == BiometricSourceType.FACE) {
                i = 7;
            } else {
                i = 2;
            }
            latencyTracker.onActionEnd(i);
        }
    }

    public final boolean bouncerIsOrWillBeShowing() {
        if (!isBouncerShowing()) {
            KeyguardBouncer keyguardBouncer = this.mBouncer;
            Objects.requireNonNull(keyguardBouncer);
            if (keyguardBouncer.mShowingSoon) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final void onNavigationModeChanged(int i) {
        boolean isGesturalMode = R$color.isGesturalMode(i);
        if (isGesturalMode != this.mGesturalNav) {
            this.mGesturalNav = isGesturalMode;
            updateStates();
        }
    }

    public final boolean shouldSubtleWindowAnimationsForUnlock() {
        return needsBypassFading();
    }

    public final void showBouncerMessage(String str, ColorStateList colorStateList) {
        if (isShowingAlternateAuth()) {
            KeyguardMessageAreaController keyguardMessageAreaController = this.mKeyguardMessageAreaController;
            if (keyguardMessageAreaController != null) {
                keyguardMessageAreaController.setMessage((CharSequence) str);
                return;
            }
            return;
        }
        KeyguardBouncer keyguardBouncer = this.mBouncer;
        Objects.requireNonNull(keyguardBouncer);
        KeyguardHostViewController keyguardHostViewController = keyguardBouncer.mKeyguardViewController;
        if (keyguardHostViewController != null) {
            KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController.mKeyguardSecurityContainerController;
            Objects.requireNonNull(keyguardSecurityContainerController);
            if (keyguardSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                keyguardSecurityContainerController.getCurrentSecurityController().showMessage(str, colorStateList);
                return;
            }
            return;
        }
        Log.w("KeyguardBouncer", "Trying to show message on empty bouncer");
    }

    public final boolean isShowing() {
        return this.mShowing;
    }
}
