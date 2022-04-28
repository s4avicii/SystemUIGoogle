package com.android.keyguard;

import android.content.res.Resources;
import android.database.ContentObserver;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.keyguard.clock.ClockManager;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.plugins.ClockPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public final class KeyguardClockSwitchController extends ViewController<KeyguardClockSwitch> {
    public final BatteryController mBatteryController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mCanShowDoubleLineClock = true;
    public final KeyguardClockSwitchController$$ExternalSyntheticLambda1 mClockChangedListener = new KeyguardClockSwitchController$$ExternalSyntheticLambda1(this);
    public FrameLayout mClockFrame;
    public final ClockManager mClockManager;
    public AnimatableClockController mClockViewController;
    public final SysuiColorExtractor mColorExtractor;
    public final KeyguardClockSwitchController$$ExternalSyntheticLambda0 mColorsListener = new KeyguardClockSwitchController$$ExternalSyntheticLambda0(this);
    public int mCurrentClockSize = 1;
    public C04921 mDoubleLineClockObserver = new ContentObserver() {
        public final void onChange(boolean z) {
            KeyguardClockSwitchController.this.updateDoubleLineClock();
        }
    };
    public int mKeyguardClockTopMargin = 0;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public FrameLayout mLargeClockFrame;
    public AnimatableClockController mLargeClockViewController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public boolean mOnlyClock = false;
    public final Resources mResources;
    public final SecureSettings mSecureSettings;
    public final LockscreenSmartspaceController mSmartspaceController;
    public View mSmartspaceView;
    public ViewGroup mStatusArea;
    public final StatusBarStateController mStatusBarStateController;
    public Executor mUiExecutor;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardClockSwitchController(KeyguardClockSwitch keyguardClockSwitch, StatusBarStateController statusBarStateController, SysuiColorExtractor sysuiColorExtractor, ClockManager clockManager, KeyguardSliceViewController keyguardSliceViewController, NotificationIconAreaController notificationIconAreaController, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, SecureSettings secureSettings, Executor executor, Resources resources) {
        super(keyguardClockSwitch);
        KeyguardUnlockAnimationController keyguardUnlockAnimationController2 = keyguardUnlockAnimationController;
        this.mStatusBarStateController = statusBarStateController;
        this.mColorExtractor = sysuiColorExtractor;
        this.mClockManager = clockManager;
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBatteryController = batteryController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSmartspaceController = lockscreenSmartspaceController;
        this.mResources = resources;
        this.mSecureSettings = secureSettings;
        this.mUiExecutor = executor;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController2;
        C04932 r2 = new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() {
            public final void onSmartspaceSharedElementTransitionStarted() {
                KeyguardClockSwitchController keyguardClockSwitchController = KeyguardClockSwitchController.this;
                Objects.requireNonNull(keyguardClockSwitchController);
                ((KeyguardClockSwitch) keyguardClockSwitchController.mView).setClipChildren(false);
                ViewGroup viewGroup = keyguardClockSwitchController.mStatusArea;
                if (viewGroup != null) {
                    viewGroup.setClipChildren(false);
                }
            }

            public final void onUnlockAnimationFinished() {
                KeyguardClockSwitchController keyguardClockSwitchController = KeyguardClockSwitchController.this;
                Objects.requireNonNull(keyguardClockSwitchController);
                ((KeyguardClockSwitch) keyguardClockSwitchController.mView).setClipChildren(true);
                ViewGroup viewGroup = keyguardClockSwitchController.mStatusArea;
                if (viewGroup != null) {
                    viewGroup.setClipChildren(true);
                }
            }
        };
        Objects.requireNonNull(keyguardUnlockAnimationController);
        keyguardUnlockAnimationController2.listeners.add(r2);
    }

    public final void displayClock(int i, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        if (this.mCanShowDoubleLineClock || i != 0) {
            this.mCurrentClockSize = i;
            KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) this.mView;
            Objects.requireNonNull(keyguardClockSwitch);
            Integer num = keyguardClockSwitch.mDisplayedClockSize;
            boolean z5 = true;
            if (num == null || i != num.intValue()) {
                if (keyguardClockSwitch.mChildrenAreLaidOut) {
                    if (i == 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    keyguardClockSwitch.updateClockViews(z4, z);
                }
                keyguardClockSwitch.mDisplayedClockSize = Integer.valueOf(i);
                z2 = true;
            } else {
                z2 = false;
            }
            if (z && z2 && i == 0) {
                AnimatableClockController animatableClockController = this.mLargeClockViewController;
                Objects.requireNonNull(animatableClockController);
                if (!animatableClockController.mIsDozing) {
                    AnimatableClockView animatableClockView = (AnimatableClockView) animatableClockController.mView;
                    Objects.requireNonNull(animatableClockView);
                    if (animatableClockView.textAnimator != null) {
                        if (animatableClockView.getResources().getConfiguration().fontWeightAdjustment > 100) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        int i2 = animatableClockView.dozingWeightInternal;
                        if (z3) {
                            i2 += 100;
                        }
                        animatableClockView.setTextStyle(i2, Integer.valueOf(animatableClockView.lockScreenColor), false, 0, 0, (AnimatableClockView$animateCharge$startAnimPhase2$1) null);
                        if (animatableClockView.getResources().getConfiguration().fontWeightAdjustment <= 100) {
                            z5 = false;
                        }
                        int i3 = animatableClockView.lockScreenWeightInternal;
                        if (z5) {
                            i3 += 100;
                        }
                        animatableClockView.setTextStyle(i3, Integer.valueOf(animatableClockView.lockScreenColor), true, 350, 0, (AnimatableClockView$animateCharge$startAnimPhase2$1) null);
                    }
                }
            }
        }
    }

    public final void onInit() {
        this.mKeyguardSliceViewController.init();
        this.mClockFrame = (FrameLayout) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.lockscreen_clock_view);
        this.mLargeClockFrame = (FrameLayout) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.lockscreen_clock_view_large);
        AnimatableClockController animatableClockController = new AnimatableClockController((AnimatableClockView) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.animatable_clock_view), this.mStatusBarStateController, this.mBroadcastDispatcher, this.mBatteryController, this.mKeyguardUpdateMonitor, this.mResources);
        this.mClockViewController = animatableClockController;
        animatableClockController.init();
        AnimatableClockController animatableClockController2 = new AnimatableClockController((AnimatableClockView) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.animatable_clock_view_large), this.mStatusBarStateController, this.mBroadcastDispatcher, this.mBatteryController, this.mKeyguardUpdateMonitor, this.mResources);
        this.mLargeClockViewController = animatableClockController2;
        animatableClockController2.init();
    }

    public final void onViewAttached() {
        boolean z;
        ClockManager clockManager = this.mClockManager;
        KeyguardClockSwitchController$$ExternalSyntheticLambda1 keyguardClockSwitchController$$ExternalSyntheticLambda1 = this.mClockChangedListener;
        Objects.requireNonNull(clockManager);
        Class<ClockPlugin> cls = ClockPlugin.class;
        if (clockManager.mListeners.isEmpty()) {
            clockManager.mPluginManager.addPluginListener(clockManager.mPreviewClocks, cls, true);
            clockManager.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("lock_screen_custom_clock_face"), false, clockManager.mContentObserver, -1);
            clockManager.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("docked_clock_face"), false, clockManager.mContentObserver, -1);
            clockManager.mCurrentUserObservable.getCurrentUser().observeForever(clockManager.mCurrentUserObserver);
            DockManager dockManager = clockManager.mDockManager;
            if (dockManager != null) {
                dockManager.addListener(clockManager.mDockEventListener);
            }
        }
        ClockManager.AvailableClocks availableClocks = new ClockManager.AvailableClocks();
        for (int i = 0; i < clockManager.mBuiltinClocks.size(); i++) {
            availableClocks.addClockPlugin((ClockPlugin) ((Supplier) clockManager.mBuiltinClocks.get(i)).get());
        }
        clockManager.mListeners.put(keyguardClockSwitchController$$ExternalSyntheticLambda1, availableClocks);
        clockManager.mPluginManager.addPluginListener(availableClocks, cls, true);
        clockManager.reload();
        this.mColorExtractor.addOnColorsChangedListener(this.mColorsListener);
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) this.mView;
        ColorExtractor.GradientColors colors = this.mColorExtractor.getColors(2);
        Objects.requireNonNull(keyguardClockSwitch);
        keyguardClockSwitch.mSupportsDarkText = colors.supportsDarkText();
        int[] colorPalette = colors.getColorPalette();
        keyguardClockSwitch.mColorPalette = colorPalette;
        ClockPlugin clockPlugin = keyguardClockSwitch.mClockPlugin;
        if (clockPlugin != null) {
            clockPlugin.setColorPalette(keyguardClockSwitch.mSupportsDarkText, colorPalette);
        }
        this.mKeyguardClockTopMargin = ((KeyguardClockSwitch) this.mView).getResources().getDimensionPixelSize(C1777R.dimen.keyguard_clock_top_margin);
        if (this.mOnlyClock) {
            ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.keyguard_slice_view).setVisibility(8);
            ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.left_aligned_notification_icon_container).setVisibility(8);
            return;
        }
        NotificationIconContainer notificationIconContainer = (NotificationIconContainer) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.left_aligned_notification_icon_container);
        NotificationIconAreaController notificationIconAreaController = this.mNotificationIconAreaController;
        Objects.requireNonNull(notificationIconAreaController);
        NotificationIconContainer notificationIconContainer2 = notificationIconAreaController.mAodIcons;
        if (notificationIconContainer2 == null || notificationIconContainer == notificationIconContainer2) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            notificationIconContainer2.setAnimationsEnabled(false);
            notificationIconAreaController.mAodIcons.removeAllViews();
        }
        notificationIconAreaController.mAodIcons = notificationIconContainer;
        Objects.requireNonNull(notificationIconContainer);
        notificationIconContainer.mOnLockScreen = true;
        notificationIconAreaController.updateAodIconsVisibility(false, z);
        notificationIconAreaController.updateAnimations();
        if (z) {
            notificationIconAreaController.updateAodNotificationIcons();
        }
        notificationIconAreaController.updateIconLayoutParams(notificationIconAreaController.mContext);
        this.mStatusArea = (ViewGroup) ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.keyguard_status_area);
        if (this.mSmartspaceController.isEnabled()) {
            this.mSmartspaceView = this.mSmartspaceController.buildAndConnectView((ViewGroup) this.mView);
            View findViewById = ((KeyguardClockSwitch) this.mView).findViewById(C1777R.C1779id.keyguard_slice_view);
            int indexOfChild = this.mStatusArea.indexOfChild(findViewById);
            findViewById.setVisibility(8);
            this.mStatusArea.addView(this.mSmartspaceView, indexOfChild, new LinearLayout.LayoutParams(-1, -2));
            this.mSmartspaceView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(C1777R.dimen.below_clock_padding_start), 0, getContext().getResources().getDimensionPixelSize(C1777R.dimen.below_clock_padding_end), 0);
            updateClockLayout();
            KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.mKeyguardUnlockAnimationController;
            View view = this.mSmartspaceView;
            Objects.requireNonNull(keyguardUnlockAnimationController);
            keyguardUnlockAnimationController.lockscreenSmartspace = view;
        }
        this.mSecureSettings.registerContentObserver(Settings.Secure.getUriFor("lockscreen_use_double_line_clock"), false, this.mDoubleLineClockObserver);
        updateDoubleLineClock();
    }

    public final void onViewDetached() {
        ClockManager clockManager = this.mClockManager;
        KeyguardClockSwitchController$$ExternalSyntheticLambda1 keyguardClockSwitchController$$ExternalSyntheticLambda1 = this.mClockChangedListener;
        Objects.requireNonNull(clockManager);
        clockManager.mPluginManager.removePluginListener((ClockManager.AvailableClocks) clockManager.mListeners.remove(keyguardClockSwitchController$$ExternalSyntheticLambda1));
        if (clockManager.mListeners.isEmpty()) {
            clockManager.mPluginManager.removePluginListener(clockManager.mPreviewClocks);
            clockManager.mContentResolver.unregisterContentObserver(clockManager.mContentObserver);
            clockManager.mCurrentUserObservable.getCurrentUser().removeObserver(clockManager.mCurrentUserObserver);
            DockManager dockManager = clockManager.mDockManager;
            if (dockManager != null) {
                dockManager.removeListener(clockManager.mDockEventListener);
            }
        }
        this.mColorExtractor.removeOnColorsChangedListener(this.mColorsListener);
        this.mStatusBarStateController.getState();
        ((KeyguardClockSwitch) this.mView).setClockPlugin((ClockPlugin) null);
        this.mSecureSettings.unregisterContentObserver(this.mDoubleLineClockObserver);
    }

    public final void setChildrenAlphaExcludingSmartspace(float f) {
        HashSet hashSet = new HashSet();
        if (this.mSmartspaceView != null) {
            hashSet.add(this.mStatusArea);
        }
        if (this.mCurrentClockSize == 0) {
            hashSet.add(this.mClockFrame);
        } else {
            hashSet.add(this.mLargeClockFrame);
        }
        for (int i = 0; i < ((KeyguardClockSwitch) this.mView).getChildCount(); i++) {
            View childAt = ((KeyguardClockSwitch) this.mView).getChildAt(i);
            if (!hashSet.contains(childAt)) {
                childAt.setAlpha(f);
            }
        }
    }

    public final void updateDoubleLineClock() {
        boolean z = true;
        if (this.mSecureSettings.getInt("lockscreen_use_double_line_clock", 1) == 0) {
            z = false;
        }
        this.mCanShowDoubleLineClock = z;
        if (!z) {
            this.mUiExecutor.execute(new AccessPoint$$ExternalSyntheticLambda0(this, 2));
        }
    }

    public final void updateClockLayout() {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.keyguard_large_clock_top_margin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.topMargin = dimensionPixelSize;
        this.mLargeClockFrame.setLayoutParams(layoutParams);
    }
}
