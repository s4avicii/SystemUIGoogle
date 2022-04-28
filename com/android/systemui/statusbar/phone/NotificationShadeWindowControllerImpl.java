package com.android.systemui.statusbar.phone;

import android.app.IActivityManager;
import android.content.Context;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Trace;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.p006qs.QSAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda27;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class NotificationShadeWindowControllerImpl implements NotificationShadeWindowController, Dumpable, ConfigurationController.ConfigurationListener {
    public final IActivityManager mActivityManager;
    public final AuthController mAuthController;
    public final ArrayList<WeakReference<StatusBarWindowCallback>> mCallbacks = new ArrayList<>();
    public final SysuiColorExtractor mColorExtractor;
    public final Context mContext;
    public final State mCurrentState = new State();
    public int mDeferWindowLayoutParams;
    public final DozeParameters mDozeParameters;
    public float mFaceAuthDisplayBrightness;
    public final HashSet mForceOpenTokens;
    public NotificationShadeWindowController.ForcePluginOpenListener mForcePluginOpenListener;
    public boolean mHasTopUi;
    public boolean mHasTopUiChanged;
    public final KeyguardBypassController mKeyguardBypassController;
    public final float mKeyguardMaxRefreshRate;
    public final float mKeyguardPreferredRefreshRate;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public NotificationShadeWindowController.OtherwisedCollapsedListener mListener;
    public final long mLockScreenDisplayTimeout;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public ViewGroup mNotificationShadeView;
    public float mScreenBrightnessDoze;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public Consumer<Integer> mScrimsVisibilityListener;
    public final C14931 mStateListener;
    public final WindowManager mWindowManager;

    public static class State {
        public boolean mBackdropShowing;
        public int mBackgroundBlurRadius;
        public boolean mBouncerShowing;
        public HashSet mComponentsForcingTopUi = new HashSet();
        public boolean mDozing;
        public boolean mForceCollapsed;
        public boolean mForceDozeBrightness;
        public boolean mForcePluginOpen;
        public boolean mHeadsUpShowing;
        public boolean mKeyguardFadingAway;
        public boolean mKeyguardGoingAway;
        public boolean mKeyguardNeedsInput;
        public boolean mKeyguardOccluded;
        public boolean mKeyguardShowing;
        public boolean mLaunchingActivity;
        public boolean mLightRevealScrimOpaque;
        public boolean mNotTouchable;
        public boolean mNotificationShadeFocusable;
        public boolean mPanelExpanded;
        public boolean mPanelVisible;
        public boolean mQsExpanded;
        public boolean mRemoteInputActive;
        public int mScrimsVisibility;
        public int mStatusBarState;

        public final String toString() {
            StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("Window State {", "\n");
            for (Field field : State.class.getDeclaredFields()) {
                m.append("  ");
                try {
                    m.append(field.getName());
                    m.append(": ");
                    m.append(field.get(this));
                } catch (IllegalAccessException unused) {
                }
                m.append("\n");
            }
            m.append("}");
            return m.toString();
        }

        /* renamed from: -$$Nest$misKeyguardShowingAndNotOccluded  reason: not valid java name */
        public static boolean m243$$Nest$misKeyguardShowingAndNotOccluded(State state) {
            Objects.requireNonNull(state);
            if (!state.mKeyguardShowing || state.mKeyguardOccluded) {
                return false;
            }
            return true;
        }
    }

    public final void registerCallback(StatusBarWindowCallback statusBarWindowCallback) {
        int i = 0;
        while (i < this.mCallbacks.size()) {
            if (this.mCallbacks.get(i).get() != statusBarWindowCallback) {
                i++;
            } else {
                return;
            }
        }
        this.mCallbacks.add(new WeakReference(statusBarWindowCallback));
    }

    public final void setDozeScreenBrightness(int i) {
        this.mScreenBrightnessDoze = ((float) i) / 255.0f;
    }

    public final void unregisterCallback(StatusBarWindowCallback statusBarWindowCallback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).get() == statusBarWindowCallback) {
                this.mCallbacks.remove(i);
                return;
            }
        }
    }

    public static boolean isExpanded(State state) {
        if ((state.mForceCollapsed || (!State.m243$$Nest$misKeyguardShowingAndNotOccluded(state) && !state.mPanelVisible && !state.mKeyguardFadingAway && !state.mBouncerShowing && !state.mHeadsUpShowing && state.mScrimsVisibility == 0)) && state.mBackgroundBlurRadius <= 0 && !state.mLaunchingActivity) {
            return false;
        }
        return true;
    }

    public final void apply(State state) {
        boolean z;
        boolean z2;
        boolean z3;
        WindowManager.LayoutParams layoutParams;
        long j;
        boolean z4;
        boolean z5;
        boolean z6 = false;
        if (state.mScrimsVisibility == 2 || state.mLightRevealScrimOpaque) {
            z = true;
        } else {
            z = false;
        }
        if (state.mKeyguardShowing || (state.mDozing && this.mDozeParameters.getAlwaysOn())) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((!z2 || state.mBackdropShowing || z) && !this.mKeyguardViewMediator.isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
            this.mLpChanged.flags &= -1048577;
        } else {
            this.mLpChanged.flags |= 1048576;
        }
        if (state.mDozing) {
            this.mLpChanged.privateFlags |= 524288;
        } else {
            this.mLpChanged.privateFlags &= -524289;
        }
        if (this.mKeyguardPreferredRefreshRate > 0.0f) {
            if (state.mStatusBarState != 1 || state.mKeyguardFadingAway || state.mKeyguardGoingAway) {
                z5 = false;
            } else {
                z5 = true;
            }
            if (!z5 || !this.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser())) {
                this.mLpChanged.preferredMaxDisplayRefreshRate = 0.0f;
            } else {
                this.mLpChanged.preferredMaxDisplayRefreshRate = this.mKeyguardPreferredRefreshRate;
            }
            Trace.setCounter("display_set_preferred_refresh_rate", (long) this.mKeyguardPreferredRefreshRate);
        } else if (this.mKeyguardMaxRefreshRate > 0.0f) {
            if (!this.mKeyguardBypassController.getBypassEnabled() || state.mStatusBarState != 1 || state.mKeyguardFadingAway || state.mKeyguardGoingAway) {
                z4 = false;
            } else {
                z4 = true;
            }
            if (state.mDozing || z4) {
                this.mLpChanged.preferredMaxDisplayRefreshRate = this.mKeyguardMaxRefreshRate;
            } else {
                this.mLpChanged.preferredMaxDisplayRefreshRate = 0.0f;
            }
            Trace.setCounter("display_max_refresh_rate", (long) this.mLpChanged.preferredMaxDisplayRefreshRate);
        }
        if (!state.mNotificationShadeFocusable || !state.mPanelExpanded) {
            z3 = false;
        } else {
            z3 = true;
        }
        if ((state.mBouncerShowing && (state.mKeyguardOccluded || state.mKeyguardNeedsInput)) || ((NotificationRemoteInputManager.ENABLE_REMOTE_INPUT && state.mRemoteInputActive) || this.mScreenOffAnimationController.shouldIgnoreKeyguardTouches())) {
            WindowManager.LayoutParams layoutParams2 = this.mLpChanged;
            layoutParams2.flags = layoutParams2.flags & -9 & -131073;
        } else if (State.m243$$Nest$misKeyguardShowingAndNotOccluded(state) || z3) {
            this.mLpChanged.flags &= -9;
            if (!state.mKeyguardNeedsInput || !State.m243$$Nest$misKeyguardShowingAndNotOccluded(state)) {
                this.mLpChanged.flags |= 131072;
            } else {
                this.mLpChanged.flags &= -131073;
            }
        } else {
            WindowManager.LayoutParams layoutParams3 = this.mLpChanged;
            layoutParams3.flags = (layoutParams3.flags | 8) & -131073;
        }
        WindowManager.LayoutParams layoutParams4 = this.mLpChanged;
        layoutParams4.softInputMode = 16;
        if (state.mPanelExpanded || state.mBouncerShowing || (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT && state.mRemoteInputActive)) {
            layoutParams4.privateFlags |= 8388608;
        } else {
            layoutParams4.privateFlags &= -8388609;
        }
        if (!State.m243$$Nest$misKeyguardShowingAndNotOccluded(state) && !state.mDozing) {
            this.mLpChanged.screenOrientation = -1;
        } else if (this.mKeyguardStateController.isKeyguardScreenRotationAllowed()) {
            this.mLpChanged.screenOrientation = 2;
        } else {
            this.mLpChanged.screenOrientation = 5;
        }
        boolean isExpanded = isExpanded(state);
        if (state.mForcePluginOpen) {
            NotificationShadeWindowController.OtherwisedCollapsedListener otherwisedCollapsedListener = this.mListener;
            if (otherwisedCollapsedListener != null) {
                StatusBar.C15432.Callback callback = (StatusBar.C15432.Callback) ((StatusBar$2$Callback$$ExternalSyntheticLambda0) otherwisedCollapsedListener).f$0;
                Objects.requireNonNull(callback);
                StatusBar.C15432.this.mOverlays.forEach(new StatusBar$2$Callback$$ExternalSyntheticLambda1(isExpanded));
            }
            isExpanded = true;
        }
        if (isExpanded) {
            this.mNotificationShadeView.setVisibility(0);
        } else {
            this.mNotificationShadeView.setVisibility(4);
        }
        if (!State.m243$$Nest$misKeyguardShowingAndNotOccluded(state) || state.mStatusBarState != 1 || state.mQsExpanded) {
            this.mLpChanged.userActivityTimeout = -1;
        } else {
            WindowManager.LayoutParams layoutParams5 = this.mLpChanged;
            if (state.mBouncerShowing) {
                j = 10000;
            } else {
                j = this.mLockScreenDisplayTimeout;
            }
            layoutParams5.userActivityTimeout = j;
        }
        if (!State.m243$$Nest$misKeyguardShowingAndNotOccluded(state) || state.mStatusBarState != 1 || state.mQsExpanded) {
            this.mLpChanged.inputFeatures &= -5;
        } else {
            WindowManager.LayoutParams layoutParams6 = this.mLpChanged;
            layoutParams6.inputFeatures = 4 | layoutParams6.inputFeatures;
        }
        boolean z7 = !State.m243$$Nest$misKeyguardShowingAndNotOccluded(state);
        ViewGroup viewGroup = this.mNotificationShadeView;
        if (!(viewGroup == null || viewGroup.getFitsSystemWindows() == z7)) {
            this.mNotificationShadeView.setFitsSystemWindows(z7);
            this.mNotificationShadeView.requestApplyInsets();
        }
        if (state.mHeadsUpShowing) {
            this.mLpChanged.flags |= 32;
        } else {
            this.mLpChanged.flags &= -33;
        }
        if (state.mForceDozeBrightness) {
            this.mLpChanged.screenBrightness = this.mScreenBrightnessDoze;
        } else {
            this.mLpChanged.screenBrightness = this.mFaceAuthDisplayBrightness;
        }
        if (!state.mComponentsForcingTopUi.isEmpty() || isExpanded(state)) {
            z6 = true;
        }
        this.mHasTopUiChanged = z6;
        if (state.mNotTouchable) {
            this.mLpChanged.flags |= 16;
        } else {
            this.mLpChanged.flags &= -17;
        }
        if (!isExpanded(state)) {
            this.mLpChanged.privateFlags |= 16777216;
        } else {
            this.mLpChanged.privateFlags &= -16777217;
        }
        if (!(this.mDeferWindowLayoutParams != 0 || (layoutParams = this.mLp) == null || layoutParams.copyFrom(this.mLpChanged) == 0)) {
            this.mWindowManager.updateViewLayout(this.mNotificationShadeView, this.mLp);
        }
        if (this.mHasTopUi != this.mHasTopUiChanged) {
            DejankUtils.whitelistIpcs((Runnable) new QSAnimator$$ExternalSyntheticLambda0(this, 5));
        }
        notifyStateChangedCallbacks();
    }

    public final void attach$1() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2040, -2138832824, -3);
        this.mLp = layoutParams;
        layoutParams.token = new Binder();
        WindowManager.LayoutParams layoutParams2 = this.mLp;
        layoutParams2.gravity = 48;
        boolean z = false;
        layoutParams2.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams3 = this.mLp;
        layoutParams3.softInputMode = 16;
        layoutParams3.setTitle("NotificationShade");
        this.mLp.packageName = this.mContext.getPackageName();
        WindowManager.LayoutParams layoutParams4 = this.mLp;
        layoutParams4.layoutInDisplayCutoutMode = 3;
        layoutParams4.privateFlags |= 134217728;
        layoutParams4.insetsFlags.behavior = 2;
        this.mWindowManager.addView(this.mNotificationShadeView, layoutParams4);
        this.mLpChanged.copyFrom(this.mLp);
        onThemeChanged();
        KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
        Objects.requireNonNull(keyguardViewMediator);
        if (keyguardViewMediator.mShowing && !keyguardViewMediator.mOccluded) {
            z = true;
        }
        if (z) {
            setKeyguardShowing(true);
        }
    }

    public final void batchApplyWindowLayoutParams(Runnable runnable) {
        WindowManager.LayoutParams layoutParams;
        this.mDeferWindowLayoutParams++;
        runnable.run();
        int i = this.mDeferWindowLayoutParams - 1;
        this.mDeferWindowLayoutParams = i;
        if (i == 0 && (layoutParams = this.mLp) != null && layoutParams.copyFrom(this.mLpChanged) != 0) {
            this.mWindowManager.updateViewLayout(this.mNotificationShadeView, this.mLp);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "NotificationShadeWindowController:", "  mKeyguardMaxRefreshRate=");
        m.append(this.mKeyguardMaxRefreshRate);
        printWriter.println(m.toString());
        printWriter.println("  mKeyguardPreferredRefreshRate=" + this.mKeyguardPreferredRefreshRate);
        printWriter.println("  mDeferWindowLayoutParams=" + this.mDeferWindowLayoutParams);
        printWriter.println(this.mCurrentState);
        ViewGroup viewGroup = this.mNotificationShadeView;
        if (viewGroup != null && viewGroup.getViewRootImpl() != null) {
            this.mNotificationShadeView.getViewRootImpl().dump("  ", printWriter);
        }
    }

    public final boolean getForcePluginOpen() {
        return this.mCurrentState.mForcePluginOpen;
    }

    public final boolean getPanelExpanded() {
        return this.mCurrentState.mPanelExpanded;
    }

    public final boolean isLaunchingActivity() {
        return this.mCurrentState.mLaunchingActivity;
    }

    public final boolean isShowingWallpaper() {
        return !this.mCurrentState.mBackdropShowing;
    }

    public final void notifyStateChangedCallbacks() {
        for (StatusBarWindowCallback onStateChanged : (List) this.mCallbacks.stream().map(NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0.INSTANCE).filter(WifiPickerTracker$$ExternalSyntheticLambda27.INSTANCE$2).collect(Collectors.toList())) {
            State state = this.mCurrentState;
            onStateChanged.onStateChanged(state.mKeyguardShowing, state.mKeyguardOccluded, state.mBouncerShowing, state.mDozing);
        }
    }

    public final void onRemoteInputActive(boolean z) {
        State state = this.mCurrentState;
        state.mRemoteInputActive = z;
        apply(state);
    }

    public final void onThemeChanged() {
        ColorExtractor.GradientColors gradientColors;
        int i;
        if (this.mNotificationShadeView != null) {
            SysuiColorExtractor sysuiColorExtractor = this.mColorExtractor;
            Objects.requireNonNull(sysuiColorExtractor);
            if (sysuiColorExtractor.mHasMediaArtwork) {
                gradientColors = sysuiColorExtractor.mBackdropColors;
            } else {
                gradientColors = sysuiColorExtractor.mNeutralColorsLock;
            }
            boolean supportsDarkText = gradientColors.supportsDarkText();
            int systemUiVisibility = this.mNotificationShadeView.getSystemUiVisibility();
            if (supportsDarkText) {
                i = systemUiVisibility | 16 | 8192;
            } else {
                i = systemUiVisibility & -17 & -8193;
            }
            this.mNotificationShadeView.setSystemUiVisibility(i);
        }
    }

    public final void setBackdropShowing(boolean z) {
        State state = this.mCurrentState;
        state.mBackdropShowing = z;
        apply(state);
    }

    public final void setBackgroundBlurRadius(int i) {
        State state = this.mCurrentState;
        if (state.mBackgroundBlurRadius != i) {
            state.mBackgroundBlurRadius = i;
            apply(state);
        }
    }

    public final void setBouncerShowing(boolean z) {
        State state = this.mCurrentState;
        state.mBouncerShowing = z;
        apply(state);
    }

    public final void setForceDozeBrightness(boolean z) {
        State state = this.mCurrentState;
        state.mForceDozeBrightness = z;
        apply(state);
    }

    public final void setForcePluginOpen(boolean z, Object obj) {
        if (z) {
            this.mForceOpenTokens.add(obj);
        } else {
            this.mForceOpenTokens.remove(obj);
        }
        State state = this.mCurrentState;
        boolean z2 = state.mForcePluginOpen;
        state.mForcePluginOpen = !this.mForceOpenTokens.isEmpty();
        State state2 = this.mCurrentState;
        if (z2 != state2.mForcePluginOpen) {
            apply(state2);
            NotificationShadeWindowController.ForcePluginOpenListener forcePluginOpenListener = this.mForcePluginOpenListener;
            if (forcePluginOpenListener != null) {
                boolean z3 = this.mCurrentState.mForcePluginOpen;
                StatusBarTouchableRegionManager statusBarTouchableRegionManager = ((StatusBarTouchableRegionManager$$ExternalSyntheticLambda0) forcePluginOpenListener).f$0;
                Objects.requireNonNull(statusBarTouchableRegionManager);
                statusBarTouchableRegionManager.updateTouchableRegion();
            }
        }
    }

    public final void setForceWindowCollapsed(boolean z) {
        State state = this.mCurrentState;
        state.mForceCollapsed = z;
        apply(state);
    }

    public final void setHeadsUpShowing(boolean z) {
        State state = this.mCurrentState;
        state.mHeadsUpShowing = z;
        apply(state);
    }

    public final void setKeyguardFadingAway(boolean z) {
        State state = this.mCurrentState;
        state.mKeyguardFadingAway = z;
        apply(state);
    }

    public final void setKeyguardGoingAway(boolean z) {
        State state = this.mCurrentState;
        state.mKeyguardGoingAway = z;
        apply(state);
    }

    public final void setKeyguardNeedsInput(boolean z) {
        State state = this.mCurrentState;
        state.mKeyguardNeedsInput = z;
        apply(state);
    }

    public final void setKeyguardOccluded(boolean z) {
        State state = this.mCurrentState;
        state.mKeyguardOccluded = z;
        apply(state);
    }

    public final void setKeyguardShowing(boolean z) {
        State state = this.mCurrentState;
        state.mKeyguardShowing = z;
        apply(state);
    }

    public final void setLaunchingActivity(boolean z) {
        State state = this.mCurrentState;
        state.mLaunchingActivity = z;
        apply(state);
    }

    public final void setLightRevealScrimOpaque(boolean z) {
        State state = this.mCurrentState;
        if (state.mLightRevealScrimOpaque != z) {
            state.mLightRevealScrimOpaque = z;
            apply(state);
        }
    }

    public final void setNotTouchable() {
        State state = this.mCurrentState;
        state.mNotTouchable = false;
        apply(state);
    }

    public final void setNotificationShadeFocusable(boolean z) {
        State state = this.mCurrentState;
        state.mNotificationShadeFocusable = z;
        apply(state);
    }

    public final void setPanelExpanded(boolean z) {
        State state = this.mCurrentState;
        state.mPanelExpanded = z;
        apply(state);
    }

    public final void setPanelVisible(boolean z) {
        State state = this.mCurrentState;
        state.mPanelVisible = z;
        state.mNotificationShadeFocusable = z;
        apply(state);
    }

    public final void setQsExpanded(boolean z) {
        State state = this.mCurrentState;
        state.mQsExpanded = z;
        apply(state);
    }

    public final void setRequestTopUi(boolean z, String str) {
        if (z) {
            this.mCurrentState.mComponentsForcingTopUi.add(str);
        } else {
            this.mCurrentState.mComponentsForcingTopUi.remove(str);
        }
        apply(this.mCurrentState);
    }

    public final void setScrimsVisibility(int i) {
        State state = this.mCurrentState;
        state.mScrimsVisibility = i;
        apply(state);
        this.mScrimsVisibilityListener.accept(Integer.valueOf(i));
    }

    public final void setScrimsVisibilityListener(NotificationShadeDepthController.C11721 r2) {
        if (this.mScrimsVisibilityListener != r2) {
            this.mScrimsVisibilityListener = r2;
        }
    }

    public final void setWallpaperSupportsAmbientMode() {
        Objects.requireNonNull(this.mCurrentState);
        apply(this.mCurrentState);
    }

    public NotificationShadeWindowControllerImpl(Context context, WindowManager windowManager, IActivityManager iActivityManager, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, ConfigurationController configurationController, KeyguardViewMediator keyguardViewMediator, KeyguardBypassController keyguardBypassController, SysuiColorExtractor sysuiColorExtractor, DumpManager dumpManager, KeyguardStateController keyguardStateController, ScreenOffAnimationController screenOffAnimationController, AuthController authController) {
        float f = -1.0f;
        this.mFaceAuthDisplayBrightness = -1.0f;
        this.mForceOpenTokens = new HashSet();
        C14931 r1 = new StatusBarStateController.StateListener() {
            public final void onDozingChanged(boolean z) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                Objects.requireNonNull(notificationShadeWindowControllerImpl);
                State state = notificationShadeWindowControllerImpl.mCurrentState;
                state.mDozing = z;
                notificationShadeWindowControllerImpl.apply(state);
            }

            public final void onStateChanged(int i) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                Objects.requireNonNull(notificationShadeWindowControllerImpl);
                State state = notificationShadeWindowControllerImpl.mCurrentState;
                state.mStatusBarState = i;
                notificationShadeWindowControllerImpl.apply(state);
            }
        };
        this.mStateListener = r1;
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mActivityManager = iActivityManager;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardStateController = keyguardStateController;
        Objects.requireNonNull(dozeParameters);
        this.mScreenBrightnessDoze = ((float) dozeParameters.mResources.getInteger(17694919)) / 255.0f;
        this.mLpChanged = new WindowManager.LayoutParams();
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mColorExtractor = sysuiColorExtractor;
        this.mScreenOffAnimationController = screenOffAnimationController;
        dumpManager.registerDumpable(NotificationShadeWindowControllerImpl.class.getName(), this);
        this.mAuthController = authController;
        this.mLockScreenDisplayTimeout = (long) context.getResources().getInteger(C1777R.integer.config_lockScreenDisplayTimeout);
        ((SysuiStatusBarStateController) statusBarStateController).addCallback(r1, 1);
        configurationController.addCallback(this);
        float integer = (float) context.getResources().getInteger(C1777R.integer.config_keyguardRefreshRate);
        if (integer > -1.0f) {
            Display.Mode[] supportedModes = context.getDisplay().getSupportedModes();
            int length = supportedModes.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Display.Mode mode = supportedModes[i];
                if (((double) Math.abs(mode.getRefreshRate() - integer)) <= 0.1d) {
                    f = mode.getRefreshRate();
                    break;
                }
                i++;
            }
        }
        this.mKeyguardPreferredRefreshRate = f;
        this.mKeyguardMaxRefreshRate = (float) context.getResources().getInteger(C1777R.integer.config_keyguardMaxRefreshRate);
    }

    public final void setForcePluginOpenListener(StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 statusBarTouchableRegionManager$$ExternalSyntheticLambda0) {
        this.mForcePluginOpenListener = statusBarTouchableRegionManager$$ExternalSyntheticLambda0;
    }

    public final void setNotificationShadeView(NotificationShadeWindowView notificationShadeWindowView) {
        this.mNotificationShadeView = notificationShadeWindowView;
    }

    public final void setStateListener(StatusBar$2$Callback$$ExternalSyntheticLambda0 statusBar$2$Callback$$ExternalSyntheticLambda0) {
        this.mListener = statusBar$2$Callback$$ExternalSyntheticLambda0;
    }

    public final ViewGroup getNotificationShadeView() {
        return this.mNotificationShadeView;
    }
}
