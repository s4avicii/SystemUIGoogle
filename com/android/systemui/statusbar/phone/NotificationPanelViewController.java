package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.leanback.R$raw;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardClockSwitchController;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.keyguard.LockIconView;
import com.android.keyguard.LockIconViewController;
import com.android.keyguard.dagger.KeyguardQsUserSwitchComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.keyguard.dagger.KeyguardUserSwitcherComponent;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0;
import com.android.systemui.DejankUtils;
import com.android.systemui.R$anim;
import com.android.systemui.R$array;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsKeyguardViewController;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.communal.CommunalHostView;
import com.android.systemui.communal.CommunalHostViewController;
import com.android.systemui.communal.CommunalHostViewPositionAlgorithm;
import com.android.systemui.communal.CommunalSource;
import com.android.systemui.communal.CommunalSourceMonitor;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.communal.dagger.CommunalViewComponent;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.p006qs.HeaderPrivacyIconsController;
import com.android.systemui.p006qs.tiles.CastTile$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.ClockPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.QsFrameTranslateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSource;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.phone.KeyguardAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.PanelViewController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManagerKt;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherScrim;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda6;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.jvm.internal.Intrinsics;

public final class NotificationPanelViewController extends PanelViewController implements NotifPanelEventSource {
    public static final long ANIMATION_DELAY_ICON_FADE_IN = 82;
    public static final Rect EMPTY_RECT = new Rect();
    public static final Rect M_DUMMY_DIRTY_RECT = new Rect(0, 0, 1, 1);
    public C14741 mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityManager mActivityManager;
    public boolean mAffordanceHasPreview;
    public KeyguardAffordanceHelper mAffordanceHelper;
    public boolean mAllowExpandForSmallExpansion;
    public int mAmbientIndicationBottomPadding;
    public final C14918 mAnimateKeyguardBottomAreaInvisibleEndRunnable;
    public boolean mAnimateNextNotificationBounds;
    public boolean mAnimateNextPositionUpdate;
    public boolean mAnimatingQS;
    public final AuthController mAuthController;
    public int mBarState;
    public boolean mBlockTouches;
    public boolean mBlockingExpansionForCurrentTouch;
    public float mBottomAreaShadeAlpha;
    public final ValueAnimator mBottomAreaShadeAlphaAnimator;
    public boolean mBouncerShowing;
    public final KeyguardClockPositionAlgorithm mClockPositionAlgorithm;
    public final KeyguardClockPositionAlgorithm.Result mClockPositionResult;
    public boolean mClosingWithAlphaFadeOut;
    public boolean mCollapsedOnDown;
    public final CommandQueue mCommandQueue;
    public final CommunalHostViewPositionAlgorithm mCommunalPositionAlgorithm;
    public final CommunalHostViewPositionAlgorithm.Result mCommunalPositionResult;
    public WeakReference<CommunalSource> mCommunalSource;
    public final CommunalSourceMonitor mCommunalSourceMonitor;
    public final NotificationPanelViewController$$ExternalSyntheticLambda2 mCommunalSourceMonitorCallback;
    public final C14852 mCommunalStateCallback;
    public final CommunalStateController mCommunalStateController;
    public CommunalHostView mCommunalView;
    public final CommunalViewComponent.Factory mCommunalViewComponentFactory;
    public CommunalHostViewController mCommunalViewController;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationListener mConfigurationListener;
    public boolean mConflictingQsExpansionGesture;
    public final ContentResolver mContentResolver;
    public final ControlsComponent mControlsComponent;
    public final ConversationNotificationManager mConversationNotificationManager;
    public int mCurrentPanelState;
    public NotificationShadeDepthController mDepthController;
    public int mDisplayId;
    public int mDisplayRightInset;
    public int mDisplayTopInset;
    public int mDistanceForQSFullShadeTransition;
    public float mDownX;
    public float mDownY;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public boolean mDozingOnDown;
    public final NotificationEntryManager mEntryManager;
    public Runnable mExpandAfterLayoutRunnable;
    public boolean mExpandingFromHeadsUp;
    public boolean mExpectingSynthesizedDown;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final C14863 mFalsingTapListener;
    public final FeatureFlags mFeatureFlags;
    public FlingAnimationUtils mFlingAnimationUtils;
    public final Provider<FlingAnimationUtils.Builder> mFlingAnimationUtilsBuilder;
    public final C147914 mFragmentListener;
    public final FragmentService mFragmentService;
    public NotificationGroupManagerLegacy mGroupManager;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public ScreenDecorations$$ExternalSyntheticLambda4 mHeadsUpExistenceChangedRunnable;
    public int mHeadsUpInset;
    public boolean mHeadsUpPinnedMode;
    public HeadsUpTouchHelper mHeadsUpTouchHelper;
    public final HeightListener mHeightListener;
    public Runnable mHideExpandedRunnable;
    public boolean mHideIconsDuringLaunchAnimation;
    public int mIndicationBottomPadding;
    public float mInitialHeightOnTouch;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public float mInterpolatedDarkAmount;
    public boolean mIsExpanding;
    public boolean mIsFullWidth;
    public boolean mIsGestureNavigation;
    public boolean mIsLaunchTransitionFinished;
    public boolean mIsLaunchTransitionRunning;
    public boolean mIsPanelCollapseOnQQS;
    public boolean mIsPulseExpansionResetAnimator;
    public boolean mIsQsTranslationResetAnimator;
    public final KeyguardAffordanceHelperCallback mKeyguardAffordanceHelperCallback;
    public final KeyguardBypassController mKeyguardBypassController;
    public KeyguardIndicationController mKeyguardIndicationController;
    public KeyguardMediaController mKeyguardMediaController;
    public float mKeyguardNotificationBottomPadding;
    public float mKeyguardOnlyContentAlpha;
    public final KeyguardQsUserSwitchComponent.Factory mKeyguardQsUserSwitchComponentFactory;
    public KeyguardQsUserSwitchController mKeyguardQsUserSwitchController;
    public boolean mKeyguardQsUserSwitchEnabled;
    public boolean mKeyguardShowing;
    public final Rect mKeyguardStatusAreaClipBounds;
    public KeyguardStatusBarView mKeyguardStatusBar;
    public final KeyguardStatusBarViewComponent.Factory mKeyguardStatusBarViewComponentFactory;
    public KeyguardStatusBarViewController mKeyguardStatusBarViewController;
    public final KeyguardStatusViewComponent.Factory mKeyguardStatusViewComponentFactory;
    public KeyguardStatusViewController mKeyguardStatusViewController;
    public Optional<KeyguardUnfoldTransition> mKeyguardUnfoldTransition;
    public final KeyguardUserSwitcherComponent.Factory mKeyguardUserSwitcherComponentFactory;
    public KeyguardUserSwitcherController mKeyguardUserSwitcherController;
    public boolean mKeyguardUserSwitcherEnabled;
    public String mLastCameraLaunchSource;
    public boolean mLastEventSynthesizedDown;
    public float mLastOverscroll;
    public Runnable mLaunchAnimationEndRunnable;
    public boolean mLaunchingAffordance;
    public final LayoutInflater mLayoutInflater;
    public float mLinearDarkAmount;
    public boolean mListenForHeadsUp;
    public LockIconViewController mLockIconViewController;
    public int mLockscreenNotificationQSPadding;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public int mMaxAllowedKeyguardNotifications;
    public final int mMaxKeyguardNotifications;
    public int mMaxOverscrollAmountForPulse;
    public final C148116 mMaybeHideExpandedRunnable;
    public final MediaDataManager mMediaDataManager;
    public final MediaHierarchyManager mMediaHierarchyManager;
    public final MetricsLogger mMetricsLogger;
    public float mMinFraction;
    public int mNavigationBarBottomHeight;
    public final ListenerSet<NotifPanelEventSource.Callbacks> mNotifEventSourceCallbacks;
    public long mNotificationBoundsAnimationDelay;
    public long mNotificationBoundsAnimationDuration;
    public NotificationsQuickSettingsContainer mNotificationContainerParent;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public Optional<NotificationPanelUnfoldAnimationController> mNotificationPanelUnfoldAnimationController;
    public final C148419 mNotificationPanelViewStateProvider;
    public NotificationShelfController mNotificationShelfController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public NotificationsQSContainerController mNotificationsQSContainerController;
    public int mOldLayoutDirection;
    public final OnClickListener mOnClickListener = new OnClickListener();
    public final MyOnHeadsUpChangedListener mOnHeadsUpChangedListener;
    public boolean mOnlyAffordanceInThisMotion;
    public float mOverStretchAmount;
    public int mPanelAlpha;
    public final AnimatableProperty.C12216 mPanelAlphaAnimator;
    public Runnable mPanelAlphaEndAction;
    public final AnimationProperties mPanelAlphaInPropertiesAnimator;
    public final AnimationProperties mPanelAlphaOutPropertiesAnimator;
    public boolean mPanelExpanded;
    public final PowerManager mPowerManager;
    public ViewGroup mPreviewContainer;
    public final PrivacyDotViewController mPrivacyDotViewController;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public boolean mPulsing;
    public final QRCodeScannerController mQRCodeScannerController;
    public boolean mQSAnimatingHiddenFromCollapsed;
    @VisibleForTesting
    public C0961QS mQs;
    public boolean mQsAnimatorExpand;
    public int mQsClipBottom;
    public int mQsClipTop;
    public ValueAnimator mQsClippingAnimation;
    public final Rect mQsClippingAnimationEndBounds;
    public boolean mQsExpandImmediate;
    public boolean mQsExpanded;
    public boolean mQsExpandedWhenExpandingStarted;
    public ValueAnimator mQsExpansionAnimator;
    public boolean mQsExpansionEnabledAmbient;
    public boolean mQsExpansionEnabledPolicy;
    public boolean mQsExpansionFromOverscroll;
    public float mQsExpansionHeight;
    public int mQsFalsingThreshold;
    public FrameLayout mQsFrame;
    public QsFrameTranslateController mQsFrameTranslateController;
    public boolean mQsFullyExpanded;
    public final Region mQsInterceptRegion;
    public int mQsMaxExpansionHeight;
    public int mQsMinExpansionHeight;
    public int mQsPeekHeight;
    public boolean mQsScrimEnabled;
    public ValueAnimator mQsSizeChangeAnimator;
    public boolean mQsTouchAboveFalsingThreshold;
    public boolean mQsTracking;
    public float mQsTranslationForFullShadeTransition;
    public VelocityTracker mQsVelocityTracker;
    public boolean mQsVisible;
    public final QuickAccessWalletController mQuickAccessWalletController;
    public float mQuickQsOffsetHeight;
    public final RecordingController mRecordingController;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public int mScreenCornerRadius;
    public ScreenOffAnimationController mScreenOffAnimationController;
    public final ScrimController mScrimController;
    public int mScrimCornerRadius;
    public final C147813 mScrollListener;
    public final SecureSettings mSecureSettings;
    public final SettingsChangeObserver mSettingsChangeObserver;
    public boolean mShouldUseSplitNotificationShade;
    public boolean mShowIconsWhenExpanded;
    public final SplitShadeHeaderController mSplitShadeHeaderController;
    public int mSplitShadeNotificationsScrimMarginBottom;
    public int mSplitShadeStatusBarHeight;
    public int mStackScrollerMeasuringPass;
    public boolean mStackScrollerOverscrolling;
    public int mStatusBarHeaderHeightKeyguard;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    @VisibleForTesting
    public final StatusBarStateListener mStatusBarStateListener;
    public final C148318 mStatusBarViewTouchEventHandler;
    public boolean mStatusViewCentered;
    public final SysUiState mSysUiState;
    public final TapAgainViewController mTapAgainViewController;
    public ExpandableNotificationRow mTrackedHeadsUpNotification;
    public final ArrayList<Consumer<ExpandableNotificationRow>> mTrackingHeadsUpListeners;
    public int mTrackingPointer;
    public int mTransitionToFullShadeQSPosition;
    public float mTransitioningToFullShadeProgress;
    public boolean mTwoFingerQsExpandPossible;
    public float mUdfpsMaxYBurnInOffset;
    public final Executor mUiExecutor;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final boolean mUseCombinedQSHeaders;
    public final UserManager mUserManager;
    public boolean mUserSetupComplete;
    public final VibratorHelper mVibratorHelper;
    public final NotificationPanelView mView;

    public class ConfigurationListener implements ConfigurationController.ConfigurationListener {
        public ConfigurationListener() {
        }

        public final void onDensityOrFontScaleChanged() {
            NotificationPanelViewController.m240$$Nest$mreInflateViews(NotificationPanelViewController.this);
        }

        public final void onSmallestScreenWidthChanged() {
            Trace.beginSection("onSmallestScreenWidthChanged");
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            boolean z = notificationPanelViewController.mKeyguardUserSwitcherEnabled;
            boolean z2 = notificationPanelViewController.mKeyguardQsUserSwitchEnabled;
            notificationPanelViewController.updateUserSwitcherFlags();
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            if (!(z == notificationPanelViewController2.mKeyguardUserSwitcherEnabled && z2 == notificationPanelViewController2.mKeyguardQsUserSwitchEnabled)) {
                NotificationPanelViewController.m240$$Nest$mreInflateViews(notificationPanelViewController2);
            }
            Trace.endSection();
        }

        public final void onThemeChanged() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mView.getContext().getThemeResId();
            Objects.requireNonNull(notificationPanelViewController);
            NotificationPanelViewController.m240$$Nest$mreInflateViews(NotificationPanelViewController.this);
        }
    }

    public class DynamicPrivacyControlListener implements DynamicPrivacyController.Listener {
        public DynamicPrivacyControlListener() {
        }

        public final void onDynamicPrivacyChanged() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.mLinearDarkAmount == 0.0f) {
                notificationPanelViewController.mAnimateNextPositionUpdate = true;
            }
        }
    }

    public class HeightListener implements C0961QS.HeightListener {
        public HeightListener() {
        }

        public final void onQsHeightChanged() {
            int i;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            C0961QS qs = notificationPanelViewController.mQs;
            if (qs != null) {
                i = qs.getDesiredHeight();
            } else {
                i = 0;
            }
            notificationPanelViewController.mQsMaxExpansionHeight = i;
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            if (notificationPanelViewController2.mQsExpanded && notificationPanelViewController2.mQsFullyExpanded) {
                notificationPanelViewController2.mQsExpansionHeight = (float) notificationPanelViewController2.mQsMaxExpansionHeight;
                notificationPanelViewController2.requestScrollerTopPaddingUpdate(false);
                NotificationPanelViewController.this.requestPanelHeightUpdate();
            }
            if (NotificationPanelViewController.this.mAccessibilityManager.isEnabled()) {
                NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                notificationPanelViewController3.mView.setAccessibilityPaneTitle(notificationPanelViewController3.determineAccessibilityPaneTitle());
            }
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController4.mNotificationStackScrollLayoutController;
            int i2 = notificationPanelViewController4.mQsMaxExpansionHeight;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mMaxTopPadding = i2;
        }
    }

    public class KeyguardAffordanceHelperCallback implements KeyguardAffordanceHelper.Callback {
        public KeyguardAffordanceHelperCallback() {
        }

        public final KeyguardAffordanceView getLeftIcon() {
            if (NotificationPanelViewController.this.mView.getLayoutDirection() == 1) {
                KeyguardBottomAreaView keyguardBottomAreaView = NotificationPanelViewController.this.mKeyguardBottomArea;
                Objects.requireNonNull(keyguardBottomAreaView);
                return keyguardBottomAreaView.mRightAffordanceView;
            }
            KeyguardBottomAreaView keyguardBottomAreaView2 = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView2);
            return keyguardBottomAreaView2.mLeftAffordanceView;
        }

        public final float getMaxTranslationDistance() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController);
            return (float) Math.hypot((double) NotificationPanelViewController.this.mView.getWidth(), (double) notificationPanelViewController.mView.getHeight());
        }

        public final KeyguardAffordanceView getRightIcon() {
            if (NotificationPanelViewController.this.mView.getLayoutDirection() == 1) {
                KeyguardBottomAreaView keyguardBottomAreaView = NotificationPanelViewController.this.mKeyguardBottomArea;
                Objects.requireNonNull(keyguardBottomAreaView);
                return keyguardBottomAreaView.mLeftAffordanceView;
            }
            KeyguardBottomAreaView keyguardBottomAreaView2 = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView2);
            return keyguardBottomAreaView2.mRightAffordanceView;
        }

        public final void onAnimationToSideStarted(boolean z, float f, float f2) {
            if (NotificationPanelViewController.this.mView.getLayoutDirection() != 1) {
                if (!z) {
                    z = true;
                } else {
                    z = false;
                }
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mIsLaunchTransitionRunning = true;
            notificationPanelViewController.mLaunchAnimationEndRunnable = null;
            StatusBar statusBar = notificationPanelViewController.mStatusBar;
            Objects.requireNonNull(statusBar);
            float f3 = statusBar.mDisplayMetrics.density;
            int abs = Math.abs((int) (f / f3));
            int abs2 = Math.abs((int) (f2 / f3));
            if (z) {
                NotificationPanelViewController.this.mLockscreenGestureLogger.write(190, abs, abs2);
                LockscreenGestureLogger lockscreenGestureLogger = NotificationPanelViewController.this.mLockscreenGestureLogger;
                LockscreenGestureLogger.LockscreenUiEvent lockscreenUiEvent = LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_DIALER;
                Objects.requireNonNull(lockscreenGestureLogger);
                LockscreenGestureLogger.log(lockscreenUiEvent);
                NotificationPanelViewController.this.mFalsingCollector.onLeftAffordanceOn();
                NotificationPanelViewController.this.mFalsingCollector.shouldEnforceBouncer();
                NotificationPanelViewController.this.mKeyguardBottomArea.launchLeftAffordance();
            } else {
                if ("lockscreen_affordance".equals(NotificationPanelViewController.this.mLastCameraLaunchSource)) {
                    NotificationPanelViewController.this.mLockscreenGestureLogger.write(189, abs, abs2);
                    LockscreenGestureLogger lockscreenGestureLogger2 = NotificationPanelViewController.this.mLockscreenGestureLogger;
                    LockscreenGestureLogger.LockscreenUiEvent lockscreenUiEvent2 = LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_CAMERA;
                    Objects.requireNonNull(lockscreenGestureLogger2);
                    LockscreenGestureLogger.log(lockscreenUiEvent2);
                }
                NotificationPanelViewController.this.mFalsingCollector.onCameraOn();
                NotificationPanelViewController.this.mFalsingCollector.shouldEnforceBouncer();
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.mKeyguardBottomArea.launchCamera(notificationPanelViewController2.mLastCameraLaunchSource);
            }
            StatusBar statusBar2 = NotificationPanelViewController.this.mStatusBar;
            Objects.requireNonNull(statusBar2);
            statusBar2.mMessageRouter.sendMessageDelayed(1003, 5000);
            NotificationPanelViewController.this.mBlockTouches = true;
        }
    }

    public class MyOnHeadsUpChangedListener implements OnHeadsUpChangedListener {
        public MyOnHeadsUpChangedListener() {
        }

        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            if (!NotificationPanelViewController.this.isOnKeyguard()) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationStackScrollLayoutController.mView.generateHeadsUpAnimation(expandableNotificationRow, true);
            }
        }

        public final void onHeadsUpPinnedModeChanged(boolean z) {
            if (z) {
                NotificationPanelViewController.this.mHeadsUpExistenceChangedRunnable.run();
                NotificationPanelViewController.this.updateNotificationTranslucency();
            } else {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                Objects.requireNonNull(notificationPanelViewController);
                notificationPanelViewController.mHeadsUpAnimatingAway = true;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                Objects.requireNonNull(notificationStackScrollLayout);
                notificationStackScrollLayout.mHeadsUpAnimatingAway = true;
                notificationStackScrollLayout.updateClipping();
                notificationPanelViewController.updateVisibility();
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController2.mNotificationStackScrollLayoutController;
                ScreenDecorations$$ExternalSyntheticLambda4 screenDecorations$$ExternalSyntheticLambda4 = notificationPanelViewController2.mHeadsUpExistenceChangedRunnable;
                Objects.requireNonNull(notificationStackScrollLayoutController2);
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                Objects.requireNonNull(notificationStackScrollLayout2);
                notificationStackScrollLayout2.mAnimationFinishedRunnables.add(screenDecorations$$ExternalSyntheticLambda4);
            }
            NotificationPanelViewController.this.updateGestureExclusionRect();
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mHeadsUpPinnedMode = z;
            notificationPanelViewController3.updateVisibility();
            NotificationPanelViewController.this.mKeyguardStatusBarViewController.updateForHeadsUp();
        }

        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            if (NotificationPanelViewController.this.isFullyCollapsed() && notificationEntry.isRowHeadsUp() && !NotificationPanelViewController.this.isOnKeyguard()) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationStackScrollLayoutController.mView.generateHeadsUpAnimation(expandableNotificationRow, false);
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if (expandableNotificationRow2 != null) {
                    expandableNotificationRow2.mMustStayOnScreen = false;
                }
            }
        }
    }

    public interface NotificationPanelViewStateProvider {
    }

    public class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public OnApplyWindowInsetsListener() {
        }

        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mDisplayTopInset = insetsIgnoringVisibility.top;
            notificationPanelViewController.mDisplayRightInset = insetsIgnoringVisibility.right;
            notificationPanelViewController.mNavigationBarBottomHeight = windowInsets.getStableInsetBottom();
            NotificationPanelViewController.m241$$Nest$mupdateMaxHeadsUpTranslation(NotificationPanelViewController.this);
            return windowInsets;
        }
    }

    public class OnAttachStateChangeListener implements View.OnAttachStateChangeListener {
        public OnAttachStateChangeListener() {
        }

        public final void onViewAttachedToWindow(View view) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mFragmentService.getFragmentHostManager(notificationPanelViewController.mView).addTagListener(C0961QS.TAG, NotificationPanelViewController.this.mFragmentListener);
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            notificationPanelViewController2.mStatusBarStateController.addCallback(notificationPanelViewController2.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mConfigurationController.addCallback(notificationPanelViewController3.mConfigurationListener);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            CommunalSourceMonitor communalSourceMonitor = notificationPanelViewController4.mCommunalSourceMonitor;
            NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = notificationPanelViewController4.mCommunalSourceMonitorCallback;
            Objects.requireNonNull(communalSourceMonitor);
            communalSourceMonitor.mExecutor.execute(new CastTile$$ExternalSyntheticLambda1(communalSourceMonitor, notificationPanelViewController$$ExternalSyntheticLambda2, 2));
            NotificationPanelViewController.this.mConfigurationListener.onThemeChanged();
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mFalsingManager.addTapListener(notificationPanelViewController5.mFalsingTapListener);
            NotificationPanelViewController.this.mKeyguardIndicationController.init();
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController6);
            notificationPanelViewController6.mContentResolver.registerContentObserver(Settings.Global.getUriFor("user_switcher_enabled"), false, notificationPanelViewController6.mSettingsChangeObserver);
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            notificationPanelViewController7.mCommunalStateController.addCallback((CommunalStateController.Callback) notificationPanelViewController7.mCommunalStateCallback);
        }

        public final void onViewDetachedFromWindow(View view) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController);
            notificationPanelViewController.mContentResolver.unregisterContentObserver(notificationPanelViewController.mSettingsChangeObserver);
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController2.mFragmentService.getFragmentHostManager(notificationPanelViewController2.mView);
            C147914 r0 = NotificationPanelViewController.this.mFragmentListener;
            Objects.requireNonNull(fragmentHostManager);
            ArrayList arrayList = fragmentHostManager.mListeners.get(C0961QS.TAG);
            if (arrayList != null && arrayList.remove(r0) && arrayList.size() == 0) {
                fragmentHostManager.mListeners.remove(C0961QS.TAG);
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mStatusBarStateController.removeCallback(notificationPanelViewController3.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            notificationPanelViewController4.mConfigurationController.removeCallback(notificationPanelViewController4.mConfigurationListener);
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            CommunalSourceMonitor communalSourceMonitor = notificationPanelViewController5.mCommunalSourceMonitor;
            NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = notificationPanelViewController5.mCommunalSourceMonitorCallback;
            Objects.requireNonNull(communalSourceMonitor);
            communalSourceMonitor.mExecutor.execute(new ScrimView$$ExternalSyntheticLambda1(communalSourceMonitor, notificationPanelViewController$$ExternalSyntheticLambda2, 1));
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            notificationPanelViewController6.mFalsingManager.removeTapListener(notificationPanelViewController6.mFalsingTapListener);
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            CommunalStateController communalStateController = notificationPanelViewController7.mCommunalStateController;
            C14852 r4 = notificationPanelViewController7.mCommunalStateCallback;
            Objects.requireNonNull(communalStateController);
            Objects.requireNonNull(r4, "Callback must not be null. b/128895449");
            communalStateController.mCallbacks.remove(r4);
        }
    }

    public class OnClickListener implements View.OnClickListener {
        public OnClickListener() {
        }

        public final void onClick(View view) {
            NotificationPanelViewController.this.onQsExpansionStarted();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.mQsExpanded) {
                notificationPanelViewController.flingSettings(0.0f, 1, (WMShell$7$$ExternalSyntheticLambda1) null, true);
            } else if (notificationPanelViewController.isQsExpansionEnabled()) {
                NotificationPanelViewController.this.mLockscreenGestureLogger.write(195, 0, 0);
                NotificationPanelViewController.this.flingSettings(0.0f, 0, (WMShell$7$$ExternalSyntheticLambda1) null, true);
            }
        }
    }

    public class OnConfigurationChangedListener extends PanelViewController.OnConfigurationChangedListener {
        public OnConfigurationChangedListener() {
            super();
        }
    }

    public class OnEmptySpaceClickListener implements NotificationStackScrollLayout.OnEmptySpaceClickListener {
        public OnEmptySpaceClickListener() {
        }
    }

    public class OnHeightChangedListener implements ExpandableView.OnHeightChangedListener {
        public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
        }

        public OnHeightChangedListener() {
        }

        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            ExpandableNotificationRow expandableNotificationRow;
            if (expandableView != null || !NotificationPanelViewController.this.mQsExpanded) {
                if (z) {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    if (notificationPanelViewController.mInterpolatedDarkAmount == 0.0f) {
                        notificationPanelViewController.mAnimateNextPositionUpdate = true;
                    }
                }
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                ExpandableView firstChildNotGone = notificationStackScrollLayoutController.mView.getFirstChildNotGone();
                if (firstChildNotGone instanceof ExpandableNotificationRow) {
                    expandableNotificationRow = (ExpandableNotificationRow) firstChildNotGone;
                } else {
                    expandableNotificationRow = null;
                }
                if (expandableNotificationRow != null && (expandableView == expandableNotificationRow || expandableNotificationRow.mNotificationParent == expandableNotificationRow)) {
                    NotificationPanelViewController.this.requestScrollerTopPaddingUpdate(false);
                }
                NotificationPanelViewController.this.requestPanelHeightUpdate();
            }
        }
    }

    public class OnLayoutChangeListener extends PanelViewController.OnLayoutChangeListener {
        public OnLayoutChangeListener() {
            super();
        }

        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            boolean z;
            C0961QS qs;
            DejankUtils.startDetectingBlockingIpcs("NVP#onLayout");
            super.onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
            NotificationPanelViewController.this.updateMaxDisplayedNotifications(true);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            if (((float) notificationStackScrollLayoutController.mView.getWidth()) == ((float) NotificationPanelViewController.this.mView.getWidth())) {
                z = true;
            } else {
                z = false;
            }
            notificationPanelViewController.mIsFullWidth = z;
            ScrimController scrimController = notificationPanelViewController.mScrimController;
            Objects.requireNonNull(scrimController);
            if (z != scrimController.mClipsQsScrim) {
                scrimController.mClipsQsScrim = z;
                for (ScrimState scrimState : ScrimState.values()) {
                    boolean z2 = scrimController.mClipsQsScrim;
                    Objects.requireNonNull(scrimState);
                    scrimState.mClipQsScrim = z2;
                }
                ScrimView scrimView = scrimController.mScrimBehind;
                if (scrimView != null) {
                    scrimView.enableBottomEdgeConcave(scrimController.mClipsQsScrim);
                }
                ScrimState scrimState2 = scrimController.mState;
                if (scrimState2 != ScrimState.UNINITIALIZED) {
                    scrimState2.prepare(scrimState2);
                    scrimController.applyAndDispatchState();
                }
            }
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            Objects.requireNonNull(notificationStackScrollLayout.mAmbientState);
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController2.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController);
            ((KeyguardStatusView) keyguardStatusViewController.mView).setPivotX((float) (notificationPanelViewController2.mView.getWidth() / 2));
            KeyguardStatusViewController keyguardStatusViewController2 = NotificationPanelViewController.this.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController2);
            KeyguardClockSwitchController keyguardClockSwitchController = keyguardStatusViewController2.mKeyguardClockSwitchController;
            Objects.requireNonNull(keyguardClockSwitchController);
            KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardClockSwitchController.mView;
            Objects.requireNonNull(keyguardClockSwitch);
            ((KeyguardStatusView) keyguardStatusViewController2.mView).setPivotY(keyguardClockSwitch.mClockView.getTextSize() * 0.34521484f);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            int i9 = notificationPanelViewController3.mQsMaxExpansionHeight;
            if (notificationPanelViewController3.mQs != null) {
                NotificationPanelViewController.m242$$Nest$mupdateQSMinHeight(notificationPanelViewController3);
                NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                notificationPanelViewController4.mQsMaxExpansionHeight = notificationPanelViewController4.mQs.getDesiredHeight();
                NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationPanelViewController5.mNotificationStackScrollLayoutController;
                int i10 = notificationPanelViewController5.mQsMaxExpansionHeight;
                Objects.requireNonNull(notificationStackScrollLayoutController3);
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController3.mView;
                Objects.requireNonNull(notificationStackScrollLayout2);
                notificationStackScrollLayout2.mMaxTopPadding = i10;
            }
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController6);
            notificationPanelViewController6.positionClockAndNotifications(false);
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            boolean z3 = notificationPanelViewController7.mQsExpanded;
            if (z3 && notificationPanelViewController7.mQsFullyExpanded) {
                notificationPanelViewController7.mQsExpansionHeight = (float) notificationPanelViewController7.mQsMaxExpansionHeight;
                notificationPanelViewController7.requestScrollerTopPaddingUpdate(false);
                NotificationPanelViewController.this.requestPanelHeightUpdate();
                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                int i11 = notificationPanelViewController8.mQsMaxExpansionHeight;
                if (i11 != i9) {
                    ValueAnimator valueAnimator = notificationPanelViewController8.mQsSizeChangeAnimator;
                    if (valueAnimator != null) {
                        i9 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        notificationPanelViewController8.mQsSizeChangeAnimator.cancel();
                    }
                    ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i9, i11});
                    notificationPanelViewController8.mQsSizeChangeAnimator = ofInt;
                    ofInt.setDuration(300);
                    notificationPanelViewController8.mQsSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    notificationPanelViewController8.mQsSizeChangeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            NotificationPanelViewController.this.requestScrollerTopPaddingUpdate(false);
                            NotificationPanelViewController.this.requestPanelHeightUpdate();
                            NotificationPanelViewController.this.mQs.setHeightOverride(((Integer) NotificationPanelViewController.this.mQsSizeChangeAnimator.getAnimatedValue()).intValue());
                        }
                    });
                    notificationPanelViewController8.mQsSizeChangeAnimator.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            NotificationPanelViewController.this.mQsSizeChangeAnimator = null;
                        }
                    });
                    notificationPanelViewController8.mQsSizeChangeAnimator.start();
                }
            } else if (!z3 && notificationPanelViewController7.mQsExpansionAnimator == null) {
                notificationPanelViewController7.setQsExpansion(((float) notificationPanelViewController7.mQsMinExpansionHeight) + notificationPanelViewController7.mLastOverscroll);
            }
            NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController9);
            notificationPanelViewController9.updateExpandedHeight(notificationPanelViewController9.mExpandedHeight);
            NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController10);
            if (notificationPanelViewController10.mBarState == 1) {
                notificationPanelViewController10.mKeyguardStatusBarViewController.updateViewState();
            }
            notificationPanelViewController10.updateQsExpansion$1();
            NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
            if (notificationPanelViewController11.mQsSizeChangeAnimator == null && (qs = notificationPanelViewController11.mQs) != null) {
                qs.setHeightOverride(qs.getDesiredHeight());
            }
            NotificationPanelViewController.m241$$Nest$mupdateMaxHeadsUpTranslation(NotificationPanelViewController.this);
            NotificationPanelViewController.this.updateGestureExclusionRect();
            Runnable runnable = NotificationPanelViewController.this.mExpandAfterLayoutRunnable;
            if (runnable != null) {
                runnable.run();
                NotificationPanelViewController.this.mExpandAfterLayoutRunnable = null;
            }
            DejankUtils.stopDetectingBlockingIpcs("NVP#onLayout");
        }
    }

    public class OnOverscrollTopChangedListener implements NotificationStackScrollLayout.OnOverscrollTopChangedListener {
        public OnOverscrollTopChangedListener() {
        }
    }

    public class SettingsChangeObserver extends ContentObserver {
        public SettingsChangeObserver(Handler handler) {
            super(handler);
        }

        public final void onChange(boolean z) {
            NotificationPanelViewController.m240$$Nest$mreInflateViews(NotificationPanelViewController.this);
        }
    }

    public class StatusBarStateListener implements StatusBarStateController.StateListener {
        public StatusBarStateListener() {
        }

        public final void onDozeAmountChanged(float f, float f2) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mInterpolatedDarkAmount = f2;
            notificationPanelViewController.mLinearDarkAmount = f;
            KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController);
            KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController.mView;
            Objects.requireNonNull(keyguardStatusView);
            if (keyguardStatusView.mDarkAmount != f2) {
                keyguardStatusView.mDarkAmount = f2;
                KeyguardClockSwitch keyguardClockSwitch = keyguardStatusView.mClockView;
                Objects.requireNonNull(keyguardClockSwitch);
                keyguardClockSwitch.mDarkAmount = f2;
                ClockPlugin clockPlugin = keyguardClockSwitch.mClockPlugin;
                if (clockPlugin != null) {
                    clockPlugin.setDarkAmount(f2);
                }
                R$raw.fadeOut(keyguardStatusView.mMediaHostContainer, f2, true);
                keyguardStatusView.updateDark();
            }
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            KeyguardBottomAreaView keyguardBottomAreaView = notificationPanelViewController2.mKeyguardBottomArea;
            float f3 = notificationPanelViewController2.mInterpolatedDarkAmount;
            Objects.requireNonNull(keyguardBottomAreaView);
            if (f3 != keyguardBottomAreaView.mDarkAmount) {
                keyguardBottomAreaView.mDarkAmount = f3;
                keyguardBottomAreaView.dozeTimeTick();
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController3);
            notificationPanelViewController3.positionClockAndNotifications(false);
        }

        public final void onStateChanged(int i) {
            boolean z;
            boolean z2;
            C0961QS qs;
            int i2;
            long j;
            long j2;
            boolean goingToFullShade = NotificationPanelViewController.this.mStatusBarStateController.goingToFullShade();
            boolean isKeyguardFadingAway = NotificationPanelViewController.this.mKeyguardStateController.isKeyguardFadingAway();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            int i3 = notificationPanelViewController.mBarState;
            if (i == 1) {
                z = true;
            } else {
                z = false;
            }
            if (notificationPanelViewController.mDozeParameters.shouldDelayKeyguardShow() && i3 == 0 && i == 1) {
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController2.mKeyguardStatusViewController;
                KeyguardClockPositionAlgorithm.Result result = notificationPanelViewController2.mClockPositionResult;
                keyguardStatusViewController.updatePosition(result.clockX, result.clockYFullyDozing, result.clockScale, false);
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            KeyguardStatusViewController keyguardStatusViewController2 = notificationPanelViewController3.mKeyguardStatusViewController;
            int i4 = notificationPanelViewController3.mBarState;
            Objects.requireNonNull(keyguardStatusViewController2);
            keyguardStatusViewController2.mKeyguardVisibilityHelper.setViewVisibility(i, isKeyguardFadingAway, goingToFullShade, i4);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            CommunalHostViewController communalHostViewController = notificationPanelViewController4.mCommunalViewController;
            if (communalHostViewController != null) {
                communalHostViewController.mKeyguardVisibilityHelper.setViewVisibility(i, isKeyguardFadingAway, goingToFullShade, notificationPanelViewController4.mBarState);
            }
            NotificationPanelViewController.this.setKeyguardBottomAreaVisibility(i, goingToFullShade);
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mBarState = i;
            notificationPanelViewController5.mKeyguardShowing = z;
            if (i3 == 1 && (goingToFullShade || i == 2)) {
                if (notificationPanelViewController5.mKeyguardStateController.isKeyguardFadingAway()) {
                    j2 = NotificationPanelViewController.this.mKeyguardStateController.getKeyguardFadingAwayDelay();
                    j = NotificationPanelViewController.this.mKeyguardStateController.getShortenedFadingAwayDuration();
                } else {
                    j2 = 0;
                    j = 360;
                }
                KeyguardStatusBarViewController keyguardStatusBarViewController = NotificationPanelViewController.this.mKeyguardStatusBarViewController;
                Objects.requireNonNull(keyguardStatusBarViewController);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{((KeyguardStatusBarView) keyguardStatusBarViewController.mView).getAlpha(), 0.0f});
                ofFloat.addUpdateListener(keyguardStatusBarViewController.mAnimatorUpdateListener);
                ofFloat.setStartDelay(j2);
                ofFloat.setDuration(j);
                ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.7.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.7.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
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
                ofFloat.start();
                NotificationPanelViewController.m242$$Nest$mupdateQSMinHeight(NotificationPanelViewController.this);
            } else if (i3 == 2 && i == 1) {
                notificationPanelViewController5.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                Objects.requireNonNull(notificationStackScrollLayout);
                notificationStackScrollLayout.mScroller.abortAnimation();
                notificationStackScrollLayout.setOwnScrollY(0, false);
                NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                if (!notificationPanelViewController6.mQsExpanded && notificationPanelViewController6.mShouldUseSplitNotificationShade) {
                    notificationPanelViewController6.mQs.animateHeaderSlidingOut();
                }
            } else {
                if (i3 == 0 && i == 1 && notificationPanelViewController5.mScreenOffAnimationController.isKeyguardShowDelayed()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    KeyguardStatusBarViewController keyguardStatusBarViewController2 = NotificationPanelViewController.this.mKeyguardStatusBarViewController;
                    if (z) {
                        i2 = 0;
                    } else {
                        i2 = 4;
                    }
                    Objects.requireNonNull(keyguardStatusBarViewController2);
                    ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setAlpha(1.0f);
                    ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setVisibility(i2);
                }
                if (z) {
                    NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                    if (!(i3 == notificationPanelViewController7.mBarState || (qs = notificationPanelViewController7.mQs) == null)) {
                        qs.hideImmediately();
                    }
                }
            }
            NotificationPanelViewController.this.mKeyguardStatusBarViewController.updateForHeadsUp();
            if (z) {
                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                Objects.requireNonNull(notificationPanelViewController8);
                notificationPanelViewController8.mKeyguardBottomArea.setDozing$2(notificationPanelViewController8.mDozing, false);
                boolean z3 = notificationPanelViewController8.mDozing;
            }
            NotificationPanelViewController.this.updateMaxDisplayedNotifications(false);
            NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
            Objects.requireNonNull(notificationPanelViewController9);
            notificationPanelViewController9.mBottomAreaShadeAlphaAnimator.cancel();
            if (notificationPanelViewController9.mBarState == 2) {
                notificationPanelViewController9.mBottomAreaShadeAlphaAnimator.setFloatValues(new float[]{notificationPanelViewController9.mBottomAreaShadeAlpha, 0.0f});
                notificationPanelViewController9.mBottomAreaShadeAlphaAnimator.start();
            } else {
                notificationPanelViewController9.mBottomAreaShadeAlpha = 1.0f;
            }
            NotificationPanelViewController.this.updateQsState();
        }
    }

    public final void collapsePanel(boolean z, boolean z2, float f) {
        boolean z3;
        if (!z || isFullyCollapsed()) {
            resetViews(false);
            setExpandedHeightInternal(((float) getMaxPanelHeight()) * 0.0f);
            z3 = false;
        } else {
            collapse(z2, f);
            z3 = true;
        }
        if (!z3) {
            PanelExpansionStateManager panelExpansionStateManager = this.mPanelExpansionStateManager;
            Objects.requireNonNull(panelExpansionStateManager);
            PanelExpansionStateManagerKt.access$stateToString(panelExpansionStateManager.state);
            PanelExpansionStateManagerKt.access$stateToString(0);
            if (panelExpansionStateManager.state != 0) {
                panelExpansionStateManager.updateStateInternal(0);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void flingSettings(float r9, int r10, final com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1 r11, boolean r12) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            if (r10 == 0) goto L_0x0012
            if (r10 == r1) goto L_0x000f
            com.android.systemui.plugins.qs.QS r2 = r8.mQs
            if (r2 == 0) goto L_0x000d
            r2.closeDetail()
        L_0x000d:
            r2 = r0
            goto L_0x0015
        L_0x000f:
            int r2 = r8.mQsMinExpansionHeight
            goto L_0x0014
        L_0x0012:
            int r2 = r8.mQsMaxExpansionHeight
        L_0x0014:
            float r2 = (float) r2
        L_0x0015:
            float r3 = r8.mQsExpansionHeight
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            r5 = 0
            if (r4 != 0) goto L_0x0029
            if (r11 == 0) goto L_0x0021
            r11.run()
        L_0x0021:
            if (r10 == 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r1 = r5
        L_0x0025:
            r8.traceQsJank(r5, r1)
            return
        L_0x0029:
            if (r10 != 0) goto L_0x002d
            r10 = r1
            goto L_0x002e
        L_0x002d:
            r10 = r5
        L_0x002e:
            int r4 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r4 <= 0) goto L_0x0034
            if (r10 == 0) goto L_0x003a
        L_0x0034:
            int r4 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r4 >= 0) goto L_0x003d
            if (r10 == 0) goto L_0x003d
        L_0x003a:
            r9 = r0
            r4 = r1
            goto L_0x003e
        L_0x003d:
            r4 = r5
        L_0x003e:
            r6 = 2
            float[] r6 = new float[r6]
            r6[r5] = r3
            r6[r1] = r2
            android.animation.ValueAnimator r3 = android.animation.ValueAnimator.ofFloat(r6)
            if (r12 == 0) goto L_0x0056
            android.view.animation.PathInterpolator r9 = com.android.systemui.animation.Interpolators.TOUCH_RESPONSE
            r3.setInterpolator(r9)
            r6 = 368(0x170, double:1.82E-321)
            r3.setDuration(r6)
            goto L_0x005d
        L_0x0056:
            com.android.wm.shell.animation.FlingAnimationUtils r12 = r8.mFlingAnimationUtils
            float r6 = r8.mQsExpansionHeight
            r12.apply(r3, r6, r2, r9)
        L_0x005d:
            if (r4 == 0) goto L_0x0064
            r6 = 350(0x15e, double:1.73E-321)
            r3.setDuration(r6)
        L_0x0064:
            com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda1 r9 = new com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda1
            r9.<init>(r8, r1)
            r3.addUpdateListener(r9)
            com.android.systemui.statusbar.phone.NotificationPanelViewController$10 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$10
            r9.<init>(r11)
            r3.addListener(r9)
            r8.mAnimatingQS = r1
            r3.start()
            r8.mQsExpansionAnimator = r3
            r8.mQsAnimatorExpand = r10
            float r9 = r8.computeQsExpansionFraction()
            int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r9 != 0) goto L_0x008a
            int r9 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r9 != 0) goto L_0x008a
            goto L_0x008b
        L_0x008a:
            r1 = r5
        L_0x008b:
            r8.mQSAnimatingHiddenFromCollapsed = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.flingSettings(float, int, com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1, boolean):void");
    }

    public final void launchCamera(boolean z, int i) {
        boolean z2;
        boolean z3;
        KeyguardAffordanceView keyguardAffordanceView;
        KeyguardAffordanceView keyguardAffordanceView2;
        float f;
        if (i == 1) {
            this.mLastCameraLaunchSource = "power_double_tap";
        } else if (i == 0) {
            this.mLastCameraLaunchSource = "wiggle_gesture";
        } else if (i == 2) {
            this.mLastCameraLaunchSource = "lift_to_launch_ml";
        } else {
            this.mLastCameraLaunchSource = "lockscreen_affordance";
        }
        if (!isFullyCollapsed()) {
            setLaunchingAffordance(true);
        } else {
            z = false;
        }
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        Objects.requireNonNull(keyguardBottomAreaView);
        if (keyguardBottomAreaView.mCameraPreview != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mAffordanceHasPreview = z2;
        KeyguardAffordanceHelper keyguardAffordanceHelper = this.mAffordanceHelper;
        if (this.mView.getLayoutDirection() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        Objects.requireNonNull(keyguardAffordanceHelper);
        if (!keyguardAffordanceHelper.mSwipingInProgress) {
            if (z3) {
                keyguardAffordanceView = keyguardAffordanceHelper.mLeftIcon;
            } else {
                keyguardAffordanceView = keyguardAffordanceHelper.mRightIcon;
            }
            if (z3) {
                keyguardAffordanceView2 = keyguardAffordanceHelper.mRightIcon;
            } else {
                keyguardAffordanceView2 = keyguardAffordanceHelper.mLeftIcon;
            }
            KeyguardAffordanceView keyguardAffordanceView3 = keyguardAffordanceView2;
            keyguardAffordanceHelper.startSwiping(keyguardAffordanceView);
            if (keyguardAffordanceView.getVisibility() != 0) {
                z = false;
            }
            if (z) {
                keyguardAffordanceHelper.fling(0.0f, false, !z3);
                KeyguardAffordanceHelper.updateIcon(keyguardAffordanceView3, 0.0f, 0.0f, true, false, true, false);
                return;
            }
            ((KeyguardAffordanceHelperCallback) keyguardAffordanceHelper.mCallback).onAnimationToSideStarted(!z3, keyguardAffordanceHelper.mTranslation, 0.0f);
            if (z3) {
                f = ((KeyguardAffordanceHelperCallback) keyguardAffordanceHelper.mCallback).getMaxTranslationDistance();
            } else {
                f = ((KeyguardAffordanceHelperCallback) keyguardAffordanceHelper.mCallback).getMaxTranslationDistance();
            }
            keyguardAffordanceHelper.mTranslation = f;
            KeyguardAffordanceHelper.updateIcon(keyguardAffordanceView3, 0.0f, 0.0f, false, false, true, false);
            KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView.mPreviewClipper);
            View view = keyguardAffordanceView.mPreviewView;
            if (view != null) {
                view.setClipBounds((Rect) null);
                keyguardAffordanceView.mPreviewView.setVisibility(0);
            }
            keyguardAffordanceView.mCircleRadius = keyguardAffordanceView.getMaxCircleSize();
            keyguardAffordanceView.setImageAlpha(0.0f, false);
            keyguardAffordanceView.invalidate();
            keyguardAffordanceHelper.mFlingEndListener.onAnimationEnd((Animator) null);
            keyguardAffordanceHelper.mAnimationEndRunnable.run();
        }
    }

    public final void onFlingEnd(boolean z) {
        setOverExpansionInternal(0.0f, false);
        setAnimator((ValueAnimator) null);
        this.mKeyguardStateController.notifyPanelFlingEnd();
        if (!z) {
            if (this.mInteractionJankMonitor != null) {
                InteractionJankMonitor.getInstance().end(0);
            }
            notifyExpandingFinished();
        } else if (this.mInteractionJankMonitor != null) {
            InteractionJankMonitor.getInstance().cancel(0);
        }
        updatePanelExpansionAndVisibility();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mIsFlinging = false;
    }

    public final void resetViews(boolean z) {
        this.mIsLaunchTransitionFinished = false;
        this.mBlockTouches = false;
        if (!this.mLaunchingAffordance) {
            this.mAffordanceHelper.reset(false);
            this.mLastCameraLaunchSource = "lockscreen_affordance";
        }
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mGutsManager.closeAndSaveGuts(true, true, true, true);
        if (!z || isFullyCollapsed()) {
            ValueAnimator valueAnimator = this.mQsExpansionAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            setQsExpansion((float) this.mQsMinExpansionHeight);
        } else {
            ValueAnimator valueAnimator2 = this.mQsExpansionAnimator;
            if (valueAnimator2 != null) {
                if (this.mQsAnimatorExpand) {
                    float f = this.mQsExpansionHeight;
                    valueAnimator2.cancel();
                    setQsExpansion(f);
                }
            }
            flingSettings(0.0f, 2, (WMShell$7$$ExternalSyntheticLambda1) null, false);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        notificationStackScrollLayoutController.mView.setOverScrollAmount(0.0f, true, z, !z);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mScroller.abortAnimation();
        notificationStackScrollLayout.setOwnScrollY(0, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setTransitionToFullShadeAmount(float r6, boolean r7, long r8) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            r2 = 0
            if (r7 == 0) goto L_0x001c
            boolean r7 = r5.mIsFullWidth
            if (r7 == 0) goto L_0x001c
            r3 = 448(0x1c0, double:2.213E-321)
            r5.mAnimateNextNotificationBounds = r0
            r5.mNotificationBoundsAnimationDuration = r3
            r5.mNotificationBoundsAnimationDelay = r8
            float r7 = r5.mQsTranslationForFullShadeTransition
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x0019
            r7 = r0
            goto L_0x001a
        L_0x0019:
            r7 = r1
        L_0x001a:
            r5.mIsQsTranslationResetAnimator = r7
        L_0x001c:
            int r7 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x0090
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = r5.mNotificationStackScrollLayoutController
            int r7 = r7.getVisibleNotificationCount()
            if (r7 != 0) goto L_0x0053
            com.android.systemui.media.MediaDataManager r7 = r5.mMediaDataManager
            boolean r7 = r7.hasActiveMedia()
            if (r7 != 0) goto L_0x0053
            com.android.systemui.plugins.qs.QS r7 = r5.mQs
            if (r7 == 0) goto L_0x0090
            float r7 = r5.getQSEdgePosition()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = r5.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.mTopPadding
            float r8 = (float) r8
            float r7 = r7 - r8
            com.android.systemui.plugins.qs.QS r8 = r5.mQs
            android.view.View r8 = r8.getHeader()
            int r8 = r8.getHeight()
            float r8 = (float) r8
            float r7 = r7 + r8
            goto L_0x0091
        L_0x0053:
            float r7 = r5.getQSEdgePosition()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = r5.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.media.KeyguardMediaController r9 = r8.mKeyguardMediaController
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.MediaContainerView r9 = r9.singlePaneContainer
            if (r9 == 0) goto L_0x0083
            int r3 = r9.getHeight()
            if (r3 == 0) goto L_0x0083
            com.android.systemui.statusbar.SysuiStatusBarStateController r3 = r8.mStatusBarStateController
            int r3 = r3.getState()
            if (r3 == r0) goto L_0x0074
            goto L_0x0083
        L_0x0074:
            int r9 = r9.getHeight()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
            java.util.Objects.requireNonNull(r8)
            int r1 = r8.mGapHeight
            int r8 = r8.mPaddingBetweenElements
            int r1 = r1 + r8
            int r1 = r1 + r9
        L_0x0083:
            float r8 = (float) r1
            float r7 = r7 + r8
            boolean r8 = r5.isOnKeyguard()
            if (r8 == 0) goto L_0x0091
            int r8 = r5.mLockscreenNotificationQSPadding
            float r8 = (float) r8
            float r7 = r7 - r8
            goto L_0x0091
        L_0x0090:
            r7 = r2
        L_0x0091:
            android.view.animation.PathInterpolator r8 = com.android.systemui.animation.Interpolators.FAST_OUT_SLOW_IN
            int r9 = r5.mDistanceForQSFullShadeTransition
            float r9 = (float) r9
            float r6 = r6 / r9
            float r6 = android.util.MathUtils.saturate(r6)
            float r6 = r8.getInterpolation(r6)
            r5.mTransitioningToFullShadeProgress = r6
            float r6 = android.util.MathUtils.lerp(r2, r7, r6)
            int r6 = (int) r6
            float r7 = r5.mTransitioningToFullShadeProgress
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x00b0
            int r6 = java.lang.Math.max(r0, r6)
        L_0x00b0:
            r5.mTransitionToFullShadeQSPosition = r6
            r5.updateQsExpansion$1()
            com.android.systemui.communal.CommunalHostViewController r6 = r5.mCommunalViewController
            if (r6 == 0) goto L_0x00c0
            float r5 = r5.mTransitioningToFullShadeProgress
            r6.mShadeExpansion = r5
            r6.updateCommunalViewOccluded()
        L_0x00c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.setTransitionToFullShadeAmount(float, boolean, long):void");
    }

    public final void showAodUi() {
        setDozing$1(true, false);
        this.mStatusBarStateController.setUpcomingState();
        this.mEntryManager.updateNotifications("showAodUi");
        this.mStatusBarStateListener.onStateChanged(1);
        this.mStatusBarStateListener.onDozeAmountChanged(1.0f, 1.0f);
        setExpandedHeightInternal(((float) getMaxPanelHeight()) * 1.0f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:90:0x017c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateMaxDisplayedNotifications(boolean r15) {
        /*
            r14 = this;
            r0 = 0
            r1 = -1
            if (r15 == 0) goto L_0x0186
            java.lang.ref.WeakReference<com.android.systemui.communal.CommunalSource> r15 = r14.mCommunalSource
            r2 = 1
            if (r15 == 0) goto L_0x0012
            java.lang.Object r15 = r15.get()
            if (r15 == 0) goto L_0x0012
            r9 = r0
            goto L_0x0180
        L_0x0012:
            com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm r15 = r14.mClockPositionAlgorithm
            java.util.Objects.requireNonNull(r15)
            boolean r3 = r15.mBypassEnabled
            if (r3 == 0) goto L_0x001f
            int r15 = r15.mUnlockedStackScrollerPadding
            float r15 = (float) r15
            goto L_0x002e
        L_0x001f:
            boolean r3 = r15.mIsSplitShade
            if (r3 == 0) goto L_0x0028
            int r3 = r15.mSplitShadeTargetTopMargin
            int r15 = r15.mUserSwitchHeight
            goto L_0x002c
        L_0x0028:
            int r3 = r15.mMinTopMargin
            int r15 = r15.mKeyguardStatusHeight
        L_0x002c:
            int r3 = r3 + r15
            float r15 = (float) r3
        L_0x002e:
            android.content.res.Resources r3 = r14.mResources
            r4 = 2131166628(0x7f0705a4, float:1.7947507E38)
            int r3 = r3.getDimensionPixelSize(r4)
            int r3 = java.lang.Math.max(r2, r3)
            com.android.systemui.statusbar.NotificationShelfController r4 = r14.mNotificationShelfController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.NotificationShelf r4 = r4.mView
            int r4 = r4.getVisibility()
            r5 = 8
            r6 = 0
            if (r4 != r5) goto L_0x004d
            r4 = r6
            goto L_0x005d
        L_0x004d:
            com.android.systemui.statusbar.NotificationShelfController r4 = r14.mNotificationShelfController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.NotificationShelf r4 = r4.mView
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.getHeight()
            int r4 = r4 + r3
            float r4 = (float) r4
        L_0x005d:
            com.android.keyguard.LockIconViewController r7 = r14.mLockIconViewController
            java.util.Objects.requireNonNull(r7)
            T r7 = r7.mView
            com.android.keyguard.LockIconView r7 = (com.android.keyguard.LockIconView) r7
            java.util.Objects.requireNonNull(r7)
            android.graphics.PointF r8 = r7.mLockIconCenter
            float r8 = r8.y
            int r7 = r7.mRadius
            float r7 = (float) r7
            float r8 = r8 - r7
            int r7 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r7 == 0) goto L_0x00a0
            com.android.systemui.statusbar.phone.StatusBar r7 = r14.mStatusBar
            java.util.Objects.requireNonNull(r7)
            android.util.DisplayMetrics r7 = r7.mDisplayMetrics
            int r7 = r7.heightPixels
            float r7 = (float) r7
            com.android.keyguard.LockIconViewController r8 = r14.mLockIconViewController
            java.util.Objects.requireNonNull(r8)
            T r8 = r8.mView
            com.android.keyguard.LockIconView r8 = (com.android.keyguard.LockIconView) r8
            java.util.Objects.requireNonNull(r8)
            android.graphics.PointF r9 = r8.mLockIconCenter
            float r9 = r9.y
            int r8 = r8.mRadius
            float r8 = (float) r8
            float r9 = r9 - r8
            float r7 = r7 - r9
            android.content.res.Resources r8 = r14.mResources
            r9 = 2131166364(0x7f07049c, float:1.7946971E38)
            int r8 = r8.getDimensionPixelSize(r9)
            float r8 = (float) r8
            float r7 = r7 + r8
            goto L_0x00a1
        L_0x00a0:
            r7 = r6
        L_0x00a1:
            int r8 = r14.mIndicationBottomPadding
            int r9 = r14.mAmbientIndicationBottomPadding
            int r8 = java.lang.Math.max(r8, r9)
            float r8 = (float) r8
            float r7 = java.lang.Math.max(r7, r8)
            r14.mKeyguardNotificationBottomPadding = r7
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = r14.mNotificationStackScrollLayoutController
            int r8 = r8.getHeight()
            float r8 = (float) r8
            float r8 = r8 - r15
            float r8 = r8 - r4
            float r8 = r8 - r7
            r15 = 0
            r7 = r0
            r9 = r7
        L_0x00bd:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r10 = r14.mNotificationStackScrollLayoutController
            int r10 = r10.getChildCount()
            if (r7 >= r10) goto L_0x0180
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r10 = r14.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r10 = r10.mView
            android.view.View r10 = r10.getChildAt(r7)
            com.android.systemui.statusbar.notification.row.ExpandableView r10 = (com.android.systemui.statusbar.notification.row.ExpandableView) r10
            boolean r11 = r10 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r11 == 0) goto L_0x0105
            r11 = r10
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r11
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy r12 = r14.mGroupManager
            if (r12 == 0) goto L_0x00ef
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r13 = r11.mEntry
            java.util.Objects.requireNonNull(r13)
            android.service.notification.StatusBarNotification r13 = r13.mSbn
            boolean r12 = r12.isSummaryOfSuppressedGroup(r13)
            if (r12 == 0) goto L_0x00ef
            r12 = r2
            goto L_0x00f0
        L_0x00ef:
            r12 = r0
        L_0x00f0:
            if (r12 == 0) goto L_0x00f4
            goto L_0x017c
        L_0x00f4:
            boolean r12 = r14.canShowViewOnLockscreen(r10)
            if (r12 != 0) goto L_0x00fc
            goto L_0x017c
        L_0x00fc:
            java.util.Objects.requireNonNull(r11)
            boolean r11 = r11.mRemoved
            if (r11 == 0) goto L_0x0119
            goto L_0x017c
        L_0x0105:
            boolean r11 = r10 instanceof com.android.systemui.statusbar.notification.stack.MediaContainerView
            if (r11 == 0) goto L_0x017c
            int r11 = r10.getVisibility()
            if (r11 != r5) goto L_0x0111
            goto L_0x017c
        L_0x0111:
            int r11 = r10.getIntrinsicHeight()
            if (r11 != 0) goto L_0x0119
            goto L_0x017c
        L_0x0119:
            int r11 = r10.getMinHeight(r2)
            float r11 = (float) r11
            float r8 = r8 - r11
            if (r9 != 0) goto L_0x0123
            r11 = r6
            goto L_0x0124
        L_0x0123:
            float r11 = (float) r3
        L_0x0124:
            float r8 = r8 - r11
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r11 = r14.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r11 = r11.mView
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm r12 = r11.mStackScrollAlgorithm
            com.android.systemui.statusbar.notification.stack.NotificationSectionsManager r11 = r11.mSectionsManager
            java.util.Objects.requireNonNull(r12)
            boolean r15 = com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm.childNeedsGapHeight(r11, r9, r10, r15)
            if (r15 == 0) goto L_0x0140
            int r15 = r12.mGapHeight
            float r15 = (float) r15
            goto L_0x0141
        L_0x0140:
            r15 = r6
        L_0x0141:
            float r8 = r8 - r15
            int r15 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r15 < 0) goto L_0x0150
            int r15 = r14.mMaxKeyguardNotifications
            if (r15 == r1) goto L_0x014c
            if (r9 >= r15) goto L_0x0150
        L_0x014c:
            int r9 = r9 + 1
            r15 = r10
            goto L_0x017c
        L_0x0150:
            float r15 = -r4
            int r15 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
            if (r15 <= 0) goto L_0x0180
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = r14.mNotificationStackScrollLayoutController
            int r15 = r15.getChildCount()
            int r7 = r7 + r2
        L_0x015c:
            if (r7 >= r15) goto L_0x0179
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r3 = r14.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r3.mView
            android.view.View r3 = r3.getChildAt(r7)
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            boolean r4 = r3 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r4 == 0) goto L_0x0176
            boolean r3 = r14.canShowViewOnLockscreen(r3)
            if (r3 == 0) goto L_0x0176
            goto L_0x0180
        L_0x0176:
            int r7 = r7 + 1
            goto L_0x015c
        L_0x0179:
            int r9 = r9 + 1
            goto L_0x0180
        L_0x017c:
            int r7 = r7 + 1
            goto L_0x00bd
        L_0x0180:
            int r15 = java.lang.Math.max(r9, r2)
            r14.mMaxAllowedKeyguardNotifications = r15
        L_0x0186:
            boolean r15 = r14.mKeyguardShowing
            if (r15 == 0) goto L_0x01c2
            com.android.systemui.statusbar.phone.KeyguardBypassController r15 = r14.mKeyguardBypassController
            boolean r15 = r15.getBypassEnabled()
            if (r15 != 0) goto L_0x01c2
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = r14.mNotificationStackScrollLayoutController
            int r1 = r14.mMaxAllowedKeyguardNotifications
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl r15 = r15.mNotificationListContainer
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r15 = r15.mView
            java.util.Objects.requireNonNull(r15)
            int r2 = r15.mMaxDisplayedNotifications
            if (r2 == r1) goto L_0x01b3
            r15.mMaxDisplayedNotifications = r1
            r15.updateContentHeight()
            com.android.systemui.statusbar.NotificationShelf r1 = r15.mShelf
            r15.notifyHeightChangeListener(r1, r0)
        L_0x01b3:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = r14.mNotificationStackScrollLayoutController
            float r14 = r14.mKeyguardNotificationBottomPadding
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r15 = r15.mView
            java.util.Objects.requireNonNull(r15)
            r15.mKeyguardBottomPadding = r14
            goto L_0x01ef
        L_0x01c2:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = r14.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl r15 = r15.mNotificationListContainer
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r15 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r15 = r15.mView
            java.util.Objects.requireNonNull(r15)
            int r2 = r15.mMaxDisplayedNotifications
            if (r2 == r1) goto L_0x01e1
            r15.mMaxDisplayedNotifications = r1
            r15.updateContentHeight()
            com.android.systemui.statusbar.NotificationShelf r1 = r15.mShelf
            r15.notifyHeightChangeListener(r1, r0)
        L_0x01e1:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r14 = r14.mNotificationStackScrollLayoutController
            r15 = -1082130432(0xffffffffbf800000, float:-1.0)
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r14 = r14.mView
            java.util.Objects.requireNonNull(r14)
            r14.mKeyguardBottomPadding = r15
        L_0x01ef:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.updateMaxDisplayedNotifications(boolean):void");
    }

    static {
        LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0381  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x044b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0469  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x04cd  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x04d8  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0516  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x052e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x056b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x056e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x060b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x06d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NotificationPanelViewController(com.android.systemui.statusbar.phone.NotificationPanelView r18, android.content.res.Resources r19, android.os.Handler r20, android.view.LayoutInflater r21, com.android.systemui.flags.FeatureFlags r22, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r23, com.android.systemui.statusbar.PulseExpansionHandler r24, com.android.systemui.statusbar.notification.DynamicPrivacyController r25, com.android.systemui.statusbar.phone.KeyguardBypassController r26, com.android.systemui.plugins.FalsingManager r27, com.android.systemui.classifier.FalsingCollector r28, com.android.systemui.statusbar.NotificationLockscreenUserManager r29, com.android.systemui.statusbar.notification.NotificationEntryManager r30, com.android.systemui.communal.CommunalStateController r31, com.android.systemui.statusbar.policy.KeyguardStateController r32, com.android.systemui.plugins.statusbar.StatusBarStateController r33, com.android.systemui.statusbar.window.StatusBarWindowStateController r34, com.android.systemui.statusbar.NotificationShadeWindowController r35, com.android.systemui.doze.DozeLog r36, com.android.systemui.statusbar.phone.DozeParameters r37, com.android.systemui.statusbar.CommandQueue r38, com.android.systemui.statusbar.VibratorHelper r39, com.android.internal.util.LatencyTracker r40, android.os.PowerManager r41, android.view.accessibility.AccessibilityManager r42, int r43, com.android.keyguard.KeyguardUpdateMonitor r44, com.android.systemui.communal.CommunalSourceMonitor r45, com.android.internal.logging.MetricsLogger r46, android.app.ActivityManager r47, com.android.systemui.statusbar.policy.ConfigurationController r48, javax.inject.Provider<com.android.p012wm.shell.animation.FlingAnimationUtils.Builder> r49, com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager r50, com.android.systemui.statusbar.notification.ConversationNotificationManager r51, com.android.systemui.media.MediaHierarchyManager r52, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r53, com.android.systemui.statusbar.phone.NotificationsQSContainerController r54, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r55, com.android.keyguard.dagger.KeyguardStatusViewComponent.Factory r56, com.android.keyguard.dagger.KeyguardQsUserSwitchComponent.Factory r57, com.android.keyguard.dagger.KeyguardUserSwitcherComponent.Factory r58, com.android.keyguard.dagger.KeyguardStatusBarViewComponent.Factory r59, com.android.systemui.communal.dagger.CommunalViewComponent.Factory r60, com.android.systemui.statusbar.LockscreenShadeTransitionController r61, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy r62, com.android.systemui.statusbar.phone.NotificationIconAreaController r63, com.android.systemui.biometrics.AuthController r64, com.android.systemui.statusbar.phone.ScrimController r65, android.os.UserManager r66, com.android.systemui.media.MediaDataManager r67, com.android.systemui.statusbar.NotificationShadeDepthController r68, com.android.systemui.statusbar.notification.stack.AmbientState r69, com.android.keyguard.LockIconViewController r70, com.android.systemui.media.KeyguardMediaController r71, com.android.systemui.statusbar.events.PrivacyDotViewController r72, com.android.systemui.statusbar.phone.TapAgainViewController r73, com.android.systemui.navigationbar.NavigationModeController r74, com.android.systemui.fragments.FragmentService r75, android.content.ContentResolver r76, com.android.systemui.wallet.controller.QuickAccessWalletController r77, com.android.systemui.qrcodescanner.controller.QRCodeScannerController r78, com.android.systemui.screenrecord.RecordingController r79, java.util.concurrent.Executor r80, com.android.systemui.util.settings.SecureSettings r81, com.android.systemui.statusbar.phone.SplitShadeHeaderController r82, com.android.systemui.statusbar.phone.ScreenOffAnimationController r83, com.android.systemui.statusbar.phone.LockscreenGestureLogger r84, com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager r85, com.android.systemui.statusbar.NotificationRemoteInputManager r86, java.util.Optional<com.android.systemui.unfold.SysUIUnfoldComponent> r87, com.android.systemui.controls.dagger.ControlsComponent r88, com.android.internal.jank.InteractionJankMonitor r89, com.android.systemui.statusbar.QsFrameTranslateController r90, com.android.systemui.model.SysUiState r91, com.android.systemui.keyguard.KeyguardUnlockAnimationController r92) {
        /*
            r17 = this;
            r15 = r17
            r14 = r18
            r13 = r22
            r12 = r23
            r11 = r24
            r10 = r55
            r9 = r59
            r8 = r60
            r7 = r61
            r6 = r87
            r5 = r33
            com.android.systemui.statusbar.SysuiStatusBarStateController r5 = (com.android.systemui.statusbar.SysuiStatusBarStateController) r5
            java.lang.Object r0 = r49.get()
            r16 = r0
            com.android.wm.shell.animation.FlingAnimationUtils$Builder r16 = (com.android.p012wm.shell.animation.FlingAnimationUtils.Builder) r16
            r0 = r17
            r1 = r18
            r2 = r27
            r3 = r36
            r4 = r32
            r6 = r35
            r7 = r39
            r8 = r53
            r9 = r40
            r10 = r16
            r11 = r50
            r12 = r84
            r13 = r85
            r14 = r69
            r15 = r89
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnHeightChangedListener r0 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnHeightChangedListener
            r1 = r17
            r0.<init>()
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnClickListener r2 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnClickListener
            r2.<init>()
            r1.mOnClickListener = r2
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnOverscrollTopChangedListener r2 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnOverscrollTopChangedListener
            r2.<init>()
            com.android.systemui.statusbar.phone.NotificationPanelViewController$KeyguardAffordanceHelperCallback r3 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$KeyguardAffordanceHelperCallback
            r3.<init>()
            r1.mKeyguardAffordanceHelperCallback = r3
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnEmptySpaceClickListener r3 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnEmptySpaceClickListener
            r3.<init>()
            com.android.systemui.statusbar.phone.NotificationPanelViewController$MyOnHeadsUpChangedListener r4 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$MyOnHeadsUpChangedListener
            r4.<init>()
            r1.mOnHeadsUpChangedListener = r4
            com.android.systemui.statusbar.phone.NotificationPanelViewController$HeightListener r4 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$HeightListener
            r4.<init>()
            r1.mHeightListener = r4
            com.android.systemui.statusbar.phone.NotificationPanelViewController$ConfigurationListener r4 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$ConfigurationListener
            r4.<init>()
            r1.mConfigurationListener = r4
            com.android.systemui.statusbar.phone.NotificationPanelViewController$StatusBarStateListener r4 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$StatusBarStateListener
            r4.<init>()
            r1.mStatusBarStateListener = r4
            r4 = 1
            r1.mQsExpansionEnabledPolicy = r4
            r1.mQsExpansionEnabledAmbient = r4
            r5 = 0
            r1.mDisplayTopInset = r5
            r1.mDisplayRightInset = r5
            com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm r6 = new com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
            r6.<init>()
            r1.mClockPositionAlgorithm = r6
            com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm$Result r6 = new com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm$Result
            r6.<init>()
            r1.mClockPositionResult = r6
            com.android.systemui.communal.CommunalHostViewPositionAlgorithm r6 = new com.android.systemui.communal.CommunalHostViewPositionAlgorithm
            r6.<init>()
            r1.mCommunalPositionAlgorithm = r6
            com.android.systemui.communal.CommunalHostViewPositionAlgorithm$Result r6 = new com.android.systemui.communal.CommunalHostViewPositionAlgorithm$Result
            r6.<init>()
            r1.mCommunalPositionResult = r6
            r1.mQsScrimEnabled = r4
            java.lang.String r6 = "lockscreen_affordance"
            r1.mLastCameraLaunchSource = r6
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4 r6 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4
            r7 = 2
            r6.<init>(r1, r7)
            r1.mHeadsUpExistenceChangedRunnable = r6
            r1.mHideIconsDuringLaunchAnimation = r4
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r1.mTrackingHeadsUpListeners = r6
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda6 r6 = com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda6.INSTANCE
            com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6 r8 = com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6.INSTANCE$2
            com.android.systemui.statusbar.notification.AnimatableProperty$7 r9 = com.android.systemui.statusbar.notification.AnimatableProperty.f80Y
            com.android.systemui.statusbar.notification.AnimatableProperty$5 r9 = new com.android.systemui.statusbar.notification.AnimatableProperty$5
            java.lang.String r10 = "panelAlpha"
            r9.<init>(r10, r8, r6)
            com.android.systemui.statusbar.notification.AnimatableProperty$6 r6 = new com.android.systemui.statusbar.notification.AnimatableProperty$6
            r8 = 2131428558(0x7f0b04ce, float:1.8478764E38)
            r10 = 2131428557(0x7f0b04cd, float:1.8478762E38)
            r11 = 2131428559(0x7f0b04cf, float:1.8478766E38)
            r6.<init>(r8, r10, r11, r9)
            r1.mPanelAlphaAnimator = r6
            com.android.systemui.statusbar.notification.stack.AnimationProperties r6 = new com.android.systemui.statusbar.notification.stack.AnimationProperties
            r6.<init>()
            r10 = 150(0x96, double:7.4E-322)
            r6.duration = r10
            android.view.animation.PathInterpolator r8 = com.android.systemui.animation.Interpolators.ALPHA_OUT
            r6.setCustomInterpolator(r9, r8)
            r1.mPanelAlphaOutPropertiesAnimator = r6
            com.android.systemui.statusbar.notification.stack.AnimationProperties r6 = new com.android.systemui.statusbar.notification.stack.AnimationProperties
            r6.<init>()
            r10 = 200(0xc8, double:9.9E-322)
            r6.duration = r10
            com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13 r10 = new com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13
            r10.<init>(r1, r4)
            r6.mAnimationEndAction = r10
            android.view.animation.PathInterpolator r10 = com.android.systemui.animation.Interpolators.ALPHA_IN
            r6.setCustomInterpolator(r9, r10)
            r1.mPanelAlphaInPropertiesAnimator = r6
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r1.mQsClippingAnimationEndBounds = r6
            r6 = 0
            r1.mQsClippingAnimation = r6
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>()
            r1.mKeyguardStatusAreaClipBounds = r9
            android.graphics.Region r9 = new android.graphics.Region
            r9.<init>()
            r1.mQsInterceptRegion = r9
            r9 = 1065353216(0x3f800000, float:1.0)
            r1.mKeyguardOnlyContentAlpha = r9
            r1.mStatusViewCentered = r4
            com.android.systemui.util.ListenerSet r9 = new com.android.systemui.util.ListenerSet
            r9.<init>()
            r1.mNotifEventSourceCallbacks = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$1 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$1
            r9.<init>()
            r1.mAccessibilityDelegate = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$2 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$2
            r9.<init>()
            r1.mCommunalStateCallback = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$3 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$3
            r9.<init>()
            r1.mFalsingTapListener = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$8 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$8
            r9.<init>()
            r1.mAnimateKeyguardBottomAreaInvisibleEndRunnable = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$13 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$13
            r9.<init>()
            r1.mScrollListener = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$14 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$14
            r9.<init>()
            r1.mFragmentListener = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$16 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$16
            r9.<init>()
            r1.mMaybeHideExpandedRunnable = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$18 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$18
            r9.<init>()
            r1.mStatusBarViewTouchEventHandler = r9
            com.android.systemui.statusbar.phone.NotificationPanelViewController$19 r9 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$19
            r9.<init>()
            r1.mNotificationPanelViewStateProvider = r9
            r1.mCurrentPanelState = r5
            r10 = r18
            r1.mView = r10
            r11 = r39
            r1.mVibratorHelper = r11
            r11 = r71
            r1.mKeyguardMediaController = r11
            r11 = r72
            r1.mPrivacyDotViewController = r11
            r11 = r77
            r1.mQuickAccessWalletController = r11
            r11 = r78
            r1.mQRCodeScannerController = r11
            r11 = r88
            r1.mControlsComponent = r11
            r11 = r46
            r1.mMetricsLogger = r11
            r11 = r47
            r1.mActivityManager = r11
            r11 = r48
            r1.mConfigurationController = r11
            r11 = r49
            r1.mFlingAnimationUtilsBuilder = r11
            r11 = r52
            r1.mMediaHierarchyManager = r11
            r11 = r53
            r1.mStatusBarKeyguardViewManager = r11
            r11 = r54
            r1.mNotificationsQSContainerController = r11
            r54.init()
            r11 = r55
            r1.mNotificationStackScrollLayoutController = r11
            r12 = r62
            r1.mGroupManager = r12
            r12 = r63
            r1.mNotificationIconAreaController = r12
            r12 = r31
            r1.mCommunalStateController = r12
            r12 = r60
            r1.mCommunalViewComponentFactory = r12
            r13 = r56
            r1.mKeyguardStatusViewComponentFactory = r13
            r13 = r59
            r1.mKeyguardStatusBarViewComponentFactory = r13
            r14 = r68
            r1.mDepthController = r14
            r14 = r76
            r1.mContentResolver = r14
            r14 = r57
            r1.mKeyguardQsUserSwitchComponentFactory = r14
            r14 = r58
            r1.mKeyguardUserSwitcherComponentFactory = r14
            r14 = r75
            r1.mFragmentService = r14
            com.android.systemui.statusbar.phone.NotificationPanelViewController$SettingsChangeObserver r14 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$SettingsChangeObserver
            r15 = r20
            r14.<init>(r15)
            r1.mSettingsChangeObserver = r14
            android.content.res.Resources r14 = r1.mResources
            boolean r14 = com.android.systemui.util.Utils.shouldUseSplitNotificationShade(r14)
            r1.mShouldUseSplitNotificationShade = r14
            r10.setWillNotDraw(r4)
            r14 = r82
            r1.mSplitShadeHeaderController = r14
            r14 = r21
            r1.mLayoutInflater = r14
            r14 = r22
            r1.mFeatureFlags = r14
            r15 = r27
            r1.mFalsingManager = r15
            r15 = r28
            r1.mFalsingCollector = r15
            r15 = r41
            r1.mPowerManager = r15
            r15 = r42
            r1.mAccessibilityManager = r15
            java.lang.String r15 = r17.determineAccessibilityPaneTitle()
            r10.setAccessibilityPaneTitle(r15)
            r15 = 255(0xff, float:3.57E-43)
            r1.setPanelAlpha(r15, r5)
            r15 = r38
            r1.mCommandQueue = r15
            r15 = r79
            r1.mRecordingController = r15
            r15 = r43
            r1.mDisplayId = r15
            r15 = r24
            r1.mPulseExpansionHandler = r15
            r6 = r37
            r1.mDozeParameters = r6
            r6 = r65
            r1.mScrimController = r6
            r6 = r66
            r1.mUserManager = r6
            r4 = r67
            r1.mMediaDataManager = r4
            r4 = r73
            r1.mTapAgainViewController = r4
            r5 = r80
            r1.mUiExecutor = r5
            r5 = r81
            r1.mSecureSettings = r5
            r5 = r89
            r1.mInteractionJankMonitor = r5
            r5 = r91
            r1.mSysUiState = r5
            com.android.wm.shell.TaskView$$ExternalSyntheticLambda6 r5 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda6
            r7 = 3
            r5.<init>(r1, r7)
            java.util.Objects.requireNonNull(r24)
            r15.pulseExpandAbortListener = r5
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda5 r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda5
            r5.<init>(r1)
            java.util.Objects.requireNonNull(r34)
            r7 = r34
            java.util.HashSet r7 = r7.listeners
            r7.add(r5)
            android.content.Context r5 = r18.getContext()
            r5.getThemeResId()
            r5 = r26
            r1.mKeyguardBypassController = r5
            r5 = r44
            r1.mUpdateMonitor = r5
            r5 = r45
            r1.mCommunalSourceMonitor = r5
            r5 = r61
            r1.mLockscreenShadeTransitionController = r5
            java.util.Objects.requireNonNull(r61)
            r5.notificationPanelController = r1
            com.android.systemui.statusbar.phone.NotificationPanelViewController$DynamicPrivacyControlListener r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$DynamicPrivacyControlListener
            r5.<init>()
            java.util.Objects.requireNonNull(r25)
            r7 = r25
            android.util.ArraySet<com.android.systemui.statusbar.notification.DynamicPrivacyController$Listener> r7 = r7.mListeners
            r7.add(r5)
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda4 r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda4
            r5.<init>(r1)
            java.util.Objects.requireNonNull(r85)
            r7 = r85
            java.util.ArrayList r7 = r7.stateListeners
            r7.add(r5)
            r5 = 2
            float[] r7 = new float[r5]
            r7 = {1065353216, 0} // fill-array
            android.animation.ValueAnimator r5 = android.animation.ValueAnimator.ofFloat(r7)
            r1.mBottomAreaShadeAlphaAnimator = r5
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda0 r7 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda0
            r7.<init>(r1)
            r5.addUpdateListener(r7)
            r6 = 160(0xa0, double:7.9E-322)
            r5.setDuration(r6)
            r5.setInterpolator(r8)
            r5 = r29
            r1.mLockscreenUserManager = r5
            r5 = r30
            r1.mEntryManager = r5
            r5 = r51
            r1.mConversationNotificationManager = r5
            r5 = r64
            r1.mAuthController = r5
            r5 = r70
            r1.mLockIconViewController = r5
            r5 = r83
            r1.mScreenOffAnimationController = r5
            r5 = r86
            r1.mRemoteInputManager = r5
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda3 r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda3
            r5.<init>(r1)
            r6 = r74
            int r5 = r6.addListener(r5)
            boolean r5 = androidx.leanback.R$color.isGesturalMode(r5)
            r1.mIsGestureNavigation = r5
            r5 = 0
            r10.setBackgroundColor(r5)
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnAttachStateChangeListener r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnAttachStateChangeListener
            r5.<init>()
            r10.addOnAttachStateChangeListener(r5)
            boolean r6 = r18.isAttachedToWindow()
            if (r6 == 0) goto L_0x02f0
            r5.onViewAttachedToWindow(r10)
        L_0x02f0:
            com.android.systemui.statusbar.phone.NotificationPanelViewController$OnApplyWindowInsetsListener r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$OnApplyWindowInsetsListener
            r5.<init>()
            r10.setOnApplyWindowInsetsListener(r5)
            r5 = 2131492943(0x7f0c004f, float:1.8609352E38)
            r6 = r19
            int r5 = r6.getInteger(r5)
            r1.mMaxKeyguardNotifications = r5
            com.android.systemui.statusbar.phone.DozeParameters$$ExternalSyntheticLambda0 r5 = com.android.systemui.statusbar.phone.DozeParameters$$ExternalSyntheticLambda0.INSTANCE$3
            r6 = r87
            java.util.Optional r5 = r6.map(r5)
            r1.mKeyguardUnfoldTransition = r5
            com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4 r5 = com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4.INSTANCE$3
            java.util.Optional r5 = r6.map(r5)
            r1.mNotificationPanelUnfoldAnimationController = r5
            com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda2 r5 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$$ExternalSyntheticLambda2
            r5.<init>(r1)
            r1.mCommunalSourceMonitorCallback = r5
            r5 = r90
            r1.mQsFrameTranslateController = r5
            r17.updateUserSwitcherFlags()
            r17.loadDimens()
            r5 = 2131428173(0x7f0b034d, float:1.8477983E38)
            android.view.View r5 = r10.findViewById(r5)
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r5 = (com.android.systemui.statusbar.phone.KeyguardStatusBarView) r5
            r1.mKeyguardStatusBar = r5
            r5 = 2131427723(0x7f0b018b, float:1.847707E38)
            android.view.View r5 = r10.findViewById(r5)
            com.android.systemui.communal.CommunalHostView r5 = (com.android.systemui.communal.CommunalHostView) r5
            r1.mCommunalView = r5
            boolean r5 = r1.mKeyguardUserSwitcherEnabled
            if (r5 == 0) goto L_0x036c
            boolean r5 = r66.isUserSwitcherEnabled()
            if (r5 == 0) goto L_0x036c
            boolean r5 = r1.mKeyguardQsUserSwitchEnabled
            if (r5 == 0) goto L_0x035c
            r5 = 2131428186(0x7f0b035a, float:1.847801E38)
            android.view.View r5 = r10.findViewById(r5)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            android.view.View r5 = r5.inflate()
            android.widget.FrameLayout r5 = (android.widget.FrameLayout) r5
            r6 = r5
            r5 = 0
            goto L_0x036e
        L_0x035c:
            r5 = 2131428197(0x7f0b0365, float:1.8478032E38)
            android.view.View r5 = r10.findViewById(r5)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            android.view.View r5 = r5.inflate()
            com.android.systemui.statusbar.policy.KeyguardUserSwitcherView r5 = (com.android.systemui.statusbar.policy.KeyguardUserSwitcherView) r5
            goto L_0x036d
        L_0x036c:
            r5 = 0
        L_0x036d:
            r6 = 0
        L_0x036e:
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r7 = r1.mKeyguardStatusBar
            com.android.keyguard.dagger.KeyguardStatusBarViewComponent r7 = r13.build(r7, r9)
            com.android.systemui.statusbar.phone.KeyguardStatusBarViewController r7 = r7.getKeyguardStatusBarViewController()
            r1.mKeyguardStatusBarViewController = r7
            r7.init()
            com.android.systemui.communal.CommunalHostView r7 = r1.mCommunalView
            if (r7 == 0) goto L_0x038e
            com.android.systemui.communal.dagger.CommunalViewComponent r7 = r12.build(r7)
            com.android.systemui.communal.CommunalHostViewController r7 = r7.getCommunalHostViewController()
            r1.mCommunalViewController = r7
            r7.init()
        L_0x038e:
            r7 = 2131428509(0x7f0b049d, float:1.8478664E38)
            android.view.View r7 = r10.findViewById(r7)
            com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer r7 = (com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer) r7
            r1.mNotificationContainerParent = r7
            r7 = 2131428195(0x7f0b0363, float:1.8478028E38)
            android.view.View r7 = r10.findViewById(r7)
            com.android.keyguard.KeyguardStatusView r7 = (com.android.keyguard.KeyguardStatusView) r7
            r1.updateViewControllers(r7, r6, r5)
            r5 = 2131428521(0x7f0b04a9, float:1.8478689E38)
            android.view.View r5 = r10.findViewById(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout) r5
            java.util.Objects.requireNonNull(r55)
            r11.mView = r5
            com.android.systemui.statusbar.notification.stack.StackStateLogger r6 = r11.mStackStateLogger
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.StackStateAnimator r5 = r5.mStateAnimator
            java.util.Objects.requireNonNull(r5)
            r5.mLogger = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            java.util.Objects.requireNonNull(r5)
            r5.mController = r11
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r6 = r11.mNotificationRoundnessManager
            java.util.HashSet<com.android.systemui.statusbar.notification.row.ExpandableView> r5 = r5.mChildrenToAddAnimated
            java.util.Objects.requireNonNull(r6)
            r6.mAnimatedChildren = r5
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger r6 = r11.mLogger
            java.util.Objects.requireNonNull(r5)
            r5.mLogger = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$TouchHandler r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$TouchHandler
            r6.<init>()
            java.util.Objects.requireNonNull(r5)
            r5.mTouchHandler = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.phone.StatusBar r6 = r11.mStatusBar
            java.util.Objects.requireNonNull(r5)
            r5.mStatusBar = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3 r6 = new com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            r5.mClearAllAnimationListener = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0 r6 = new com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            r5.mClearAllListener = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda2 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda2
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            r5.mFooterClearAllListener = r6
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.NotificationRemoteInputManager r6 = r11.mRemoteInputManager
            boolean r6 = r6.isRemoteInputActive()
            java.util.Objects.requireNonNull(r5)
            r5.mIsRemoteInputActive = r6
            r5.updateFooter()
            com.android.systemui.statusbar.NotificationRemoteInputManager r5 = r11.mRemoteInputManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11
            r6.<init>()
            r5.addControllerCallback(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.phone.ShadeController r6 = r11.mShadeController
            java.util.Objects.requireNonNull(r5)
            r5.mShadeController = r6
            com.android.systemui.statusbar.phone.KeyguardBypassController r5 = r11.mKeyguardBypassController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda5
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            java.util.ArrayList r7 = r5.listeners
            boolean r7 = r7.isEmpty()
            java.util.ArrayList r8 = r5.listeners
            r8.add(r6)
            if (r7 == 0) goto L_0x0452
            com.android.systemui.statusbar.policy.KeyguardStateController r6 = r5.mKeyguardStateController
            com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1 r5 = r5.faceAuthEnabledChangedCallback
            r6.addCallback(r5)
        L_0x0452:
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r5 = r11.mNotificationRoundnessManager
            com.android.systemui.statusbar.phone.KeyguardBypassController r6 = r11.mKeyguardBypassController
            boolean r6 = r6.getBypassEnabled()
            r7 = 1
            r6 = r6 ^ r7
            java.util.Objects.requireNonNull(r5)
            r5.mRoundForPulsingViews = r6
            com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController r5 = r11.mFgFeatureController
            boolean r5 = r5.isForegroundServiceDismissalEnabled()
            if (r5 == 0) goto L_0x0499
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController r6 = r11.mFgServicesSectionController
            android.view.LayoutInflater r7 = r11.mLayoutInflater
            java.util.Objects.requireNonNull(r6)
            r8 = 2131624101(0x7f0e00a5, float:1.8875372E38)
            r9 = 0
            android.view.View r7 = r7.inflate(r8, r9)
            r6.entriesView = r7
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            r8 = 8
            r7.setVisibility(r8)
            android.view.View r6 = r6.entriesView
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            com.android.systemui.statusbar.notification.row.ForegroundServiceDungeonView r6 = (com.android.systemui.statusbar.notification.row.ForegroundServiceDungeonView) r6
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.row.ForegroundServiceDungeonView r7 = r5.mFgsSectionView
            if (r7 == 0) goto L_0x0493
            goto L_0x0499
        L_0x0493:
            r5.mFgsSectionView = r6
            r7 = -1
            r5.addView(r6, r7)
        L_0x0499:
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$Builder r5 = r11.mNotificationSwipeHelperBuilder
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r6 = r11.mNotificationCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$6 r7 = r11.mMenuEventListener
            r5.mOnMenuEventListener = r7
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r8 = new com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper
            android.content.res.Resources r9 = r5.mResources
            android.view.ViewConfiguration r12 = r5.mViewConfiguration
            com.android.systemui.plugins.FalsingManager r13 = r5.mFalsingManager
            com.android.systemui.flags.FeatureFlags r5 = r5.mFeatureFlags
            r16 = 0
            r25 = r8
            r26 = r9
            r27 = r12
            r28 = r13
            r29 = r5
            r30 = r16
            r31 = r6
            r32 = r7
            r25.<init>(r26, r27, r28, r29, r30, r31, r32)
            r11.mSwipeHelper = r8
            com.android.systemui.statusbar.notification.NotifPipelineFlags r5 = r11.mNotifPipelineFlags
            boolean r5 = r5.isNewPipelineEnabled()
            if (r5 == 0) goto L_0x04d8
            com.android.systemui.statusbar.notification.collection.NotifPipeline r5 = r11.mNotifPipeline
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$12 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$12
            r6.<init>()
            r5.addCollectionListener(r6)
            goto L_0x04e2
        L_0x04d8:
            com.android.systemui.statusbar.notification.NotificationEntryManager r5 = r11.mNotificationEntryManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$13 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$13
            r6.<init>()
            r5.addNotificationEntryListener(r6)
        L_0x04e2:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            android.content.Context r6 = r5.getContext()
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r7 = r11.mSwipeHelper
            r5.initView(r6, r7)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.phone.KeyguardBypassController r6 = r11.mKeyguardBypassController
            boolean r6 = r6.getBypassEnabled()
            java.util.Objects.requireNonNull(r5)
            r5.mKeyguardBypassEnabled = r6
            com.android.systemui.statusbar.phone.KeyguardBypassController r5 = r11.mKeyguardBypassController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 r7 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4
            r7.<init>(r6)
            java.util.Objects.requireNonNull(r5)
            java.util.ArrayList r6 = r5.listeners
            boolean r6 = r6.isEmpty()
            java.util.ArrayList r8 = r5.listeners
            r8.add(r7)
            if (r6 == 0) goto L_0x051d
            com.android.systemui.statusbar.policy.KeyguardStateController r6 = r5.mKeyguardStateController
            com.android.systemui.statusbar.phone.KeyguardBypassController$faceAuthEnabledChangedCallback$1 r5 = r5.faceAuthEnabledChangedCallback
            r6.addCallback(r5)
        L_0x051d:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.tuner.RadioListPreference$$ExternalSyntheticLambda0 r6 = new com.android.systemui.tuner.RadioListPreference$$ExternalSyntheticLambda0
            r7 = 2
            r6.<init>(r11, r7)
            java.util.Objects.requireNonNull(r5)
            r5.mManageButtonClickListener = r6
            com.android.systemui.statusbar.notification.row.FooterView r5 = r5.mFooterView
            if (r5 == 0) goto L_0x0533
            com.android.systemui.statusbar.notification.row.FooterViewButton r5 = r5.mManageButton
            r5.setOnClickListener(r6)
        L_0x0533:
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r5 = r11.mHeadsUpManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$8 r6 = r11.mOnHeadsUpChangedListener
            r5.addListener(r6)
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r5 = r11.mHeadsUpManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda3 r7 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda3
            r7.<init>(r6)
            java.util.Objects.requireNonNull(r5)
            r5.mAnimationStateHandler = r7
            com.android.systemui.statusbar.notification.DynamicPrivacyController r5 = r11.mDynamicPrivacyController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0 r6 = r11.mDynamicPrivacyControllerListener
            java.util.Objects.requireNonNull(r5)
            android.util.ArraySet<com.android.systemui.statusbar.notification.DynamicPrivacyController$Listener> r5 = r5.mListeners
            r5.add(r6)
            com.android.systemui.statusbar.phone.ScrimController r5 = r11.mScrimController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            java.util.Objects.requireNonNull(r6)
            com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0 r7 = new com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0
            r8 = 6
            r7.<init>(r6, r8)
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.scrim.ScrimView r6 = r5.mScrimBehind
            if (r6 != 0) goto L_0x056e
            r5.mScrimBehindChangeRunnable = r7
            goto L_0x0574
        L_0x056e:
            java.util.concurrent.Executor r5 = r5.mMainExecutor
            r6.mChangeRunnable = r7
            r6.mChangeRunnableExecutor = r5
        L_0x0574:
            com.android.systemui.statusbar.LockscreenShadeTransitionController r5 = r11.mLockscreenShadeTransitionController
            java.util.Objects.requireNonNull(r5)
            r5.nsslController = r11
            com.android.systemui.statusbar.DragDownHelper r6 = r5.touchHelper
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r7 = r11.mView
            java.util.Objects.requireNonNull(r6)
            r6.host = r7
            com.android.systemui.statusbar.DragDownHelper r5 = r5.touchHelper
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$11 r6 = r6.mExpandHelperCallback
            java.util.Objects.requireNonNull(r5)
            r5.expandCallback = r6
            com.android.systemui.statusbar.NotificationLockscreenUserManager r5 = r11.mLockscreenUserManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$5 r6 = r11.mLockscreenUserChangeListener
            r5.addUserChangedListener(r6)
            android.content.res.Resources r5 = r11.mResources
            r6 = 2131034137(0x7f050019, float:1.7678783E38)
            boolean r5 = r5.getBoolean(r6)
            r11.mFadeNotificationsOnDismiss = r5
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r5 = r11.mNotificationRoundnessManager
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            java.util.Objects.requireNonNull(r6)
            com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19 r7 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19
            r7.<init>(r6, r8)
            java.util.Objects.requireNonNull(r5)
            r5.mRoundingChangedCallback = r7
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r6 = r11.mNotificationRoundnessManager
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 r7 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6
            r7.<init>(r6)
            java.util.Objects.requireNonNull(r5)
            java.util.ArrayList<java.util.function.BiConsumer<java.lang.Float, java.lang.Float>> r5 = r5.mExpandedHeightListeners
            r5.add(r7)
            com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager r5 = r11.mVisualStabilityManager
            com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0 r6 = new com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            r5.mVisibilityLocationProvider = r6
            com.android.systemui.tuner.TunerService r5 = r11.mTunerService
            com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$$ExternalSyntheticLambda0 r6 = new com.android.systemui.statusbar.phone.NotificationShadeWindowViewController$$ExternalSyntheticLambda0
            r7 = 1
            r6.<init>(r11, r7)
            java.lang.String r7 = "high_priority"
            java.lang.String r8 = "notification_history_enabled"
            java.lang.String[] r7 = new java.lang.String[]{r7, r8}
            r5.addTunable(r6, r7)
            com.android.systemui.media.KeyguardMediaController r5 = r11.mKeyguardMediaController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7 r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7
            r6.<init>(r11)
            java.util.Objects.requireNonNull(r5)
            r5.visibilityChangedListener = r6
            com.android.systemui.statusbar.policy.DeviceProvisionedController r5 = r11.mDeviceProvisionedController
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$2 r6 = r11.mDeviceProvisionedListener
            r5.addCallback(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$2 r5 = r11.mDeviceProvisionedListener
            java.util.Objects.requireNonNull(r5)
            r5.updateCurrentUserIsSetup()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            boolean r5 = r5.isAttachedToWindow()
            if (r5 == 0) goto L_0x0612
            android.view.View$OnAttachStateChangeListener r5 = r11.mOnAttachStateChangeListener
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r11.mView
            r5.onViewAttachedToWindow(r6)
        L_0x0612:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            android.view.View$OnAttachStateChangeListener r6 = r11.mOnAttachStateChangeListener
            r5.addOnAttachStateChangeListener(r6)
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r5 = r11.mSilentHeaderController
            com.google.android.systemui.assist.uihints.IconController$$ExternalSyntheticLambda0 r6 = new com.google.android.systemui.assist.uihints.IconController$$ExternalSyntheticLambda0
            r7 = 1
            r6.<init>(r11, r7)
            r5.setOnClearSectionClickListener(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r11.mView
            java.util.Objects.requireNonNull(r5)
            r5.mOnHeightChangedListener = r0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r11.mView
            java.util.Objects.requireNonNull(r0)
            r0.mOverscrollTopChangedListener = r2
            com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2 r0 = new com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2
            r2 = 5
            r0.<init>(r1, r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r11.mView
            java.util.Objects.requireNonNull(r2)
            r2.mScrollListener = r0
            com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda32 r0 = new com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda32
            r2 = 3
            r0.<init>(r1, r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r11.mView
            java.util.Objects.requireNonNull(r2)
            r2.mOnStackYChanged = r0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r11.mView
            java.util.Objects.requireNonNull(r0)
            r0.mOnEmptySpaceClickListener = r3
            com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda2 r0 = new com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda2
            r2 = 2
            r0.<init>(r11, r2)
            java.util.ArrayList<java.util.function.Consumer<com.android.systemui.statusbar.notification.row.ExpandableNotificationRow>> r2 = r1.mTrackingHeadsUpListeners
            r2.add(r0)
            r0 = 2131428166(0x7f0b0346, float:1.8477969E38)
            android.view.View r0 = r10.findViewById(r0)
            com.android.systemui.statusbar.phone.KeyguardBottomAreaView r0 = (com.android.systemui.statusbar.phone.KeyguardBottomAreaView) r0
            r1.mKeyguardBottomArea = r0
            r0 = 2131428609(0x7f0b0501, float:1.8478867E38)
            android.view.View r0 = r10.findViewById(r0)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            r1.mPreviewContainer = r0
            com.android.systemui.statusbar.phone.KeyguardBottomAreaView r2 = r1.mKeyguardBottomArea
            java.util.Objects.requireNonNull(r2)
            r2.mPreviewContainer = r0
            r2.inflateCameraPreview()
            r2.updateLeftAffordance()
            android.content.res.Resources r0 = r1.mResources
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.orientation
            r17.initBottomArea()
            java.util.Objects.requireNonNull(r23)
            r0 = r23
            r0.mStackScrollerController = r11
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r11.mView
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r2.mAmbientState
            boolean r2 = r2.isPulseExpanding()
            r0.pulseExpanding = r2
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1 r2 = new com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1
            r2.<init>(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r11.mView
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r3.mAmbientState
            java.util.Objects.requireNonNull(r3)
            r3.mOnPulseHeightChangedListener = r2
            r2 = 2131428651(0x7f0b052b, float:1.8478953E38)
            android.view.View r2 = r10.findViewById(r2)
            android.widget.FrameLayout r2 = (android.widget.FrameLayout) r2
            r1.mQsFrame = r2
            r15.stackScrollerController = r11
            com.android.systemui.statusbar.phone.NotificationPanelViewController$5 r2 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$5
            r2.<init>()
            java.util.ArrayList<com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$WakeUpListener> r0 = r0.wakeUpListeners
            r0.add(r2)
            com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda3 r0 = new com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda3
            r0.<init>(r1)
            r10.mRtlChangeListener = r0
            com.android.systemui.statusbar.phone.NotificationPanelViewController$1 r0 = r1.mAccessibilityDelegate
            r10.setAccessibilityDelegate(r0)
            boolean r0 = r1.mShouldUseSplitNotificationShade
            if (r0 == 0) goto L_0x06db
            r17.updateResources()
        L_0x06db:
            r73.init()
            java.util.Optional<com.android.keyguard.KeyguardUnfoldTransition> r0 = r1.mKeyguardUnfoldTransition
            com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1 r2 = new com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1
            r3 = 2
            r2.<init>(r1, r3)
            r0.ifPresent(r2)
            java.util.Optional<com.android.systemui.statusbar.phone.NotificationPanelUnfoldAnimationController> r0 = r1.mNotificationPanelUnfoldAnimationController
            com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4 r2 = new com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4
            r3 = 1
            r2.<init>(r1, r3)
            r0.ifPresent(r2)
            com.android.systemui.flags.BooleanFlag r0 = com.android.systemui.flags.Flags.COMBINED_QS_HEADERS
            boolean r0 = r14.isEnabled((com.android.systemui.flags.BooleanFlag) r0)
            r1.mUseCombinedQSHeaders = r0
            com.android.systemui.statusbar.phone.NotificationPanelViewController$4 r0 = new com.android.systemui.statusbar.phone.NotificationPanelViewController$4
            r0.<init>()
            java.util.Objects.requireNonNull(r92)
            r1 = r92
            java.util.ArrayList<com.android.systemui.keyguard.KeyguardUnlockAnimationController$KeyguardUnlockAnimationListener> r1 = r1.listeners
            r1.add(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.<init>(com.android.systemui.statusbar.phone.NotificationPanelView, android.content.res.Resources, android.os.Handler, android.view.LayoutInflater, com.android.systemui.flags.FeatureFlags, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator, com.android.systemui.statusbar.PulseExpansionHandler, com.android.systemui.statusbar.notification.DynamicPrivacyController, com.android.systemui.statusbar.phone.KeyguardBypassController, com.android.systemui.plugins.FalsingManager, com.android.systemui.classifier.FalsingCollector, com.android.systemui.statusbar.NotificationLockscreenUserManager, com.android.systemui.statusbar.notification.NotificationEntryManager, com.android.systemui.communal.CommunalStateController, com.android.systemui.statusbar.policy.KeyguardStateController, com.android.systemui.plugins.statusbar.StatusBarStateController, com.android.systemui.statusbar.window.StatusBarWindowStateController, com.android.systemui.statusbar.NotificationShadeWindowController, com.android.systemui.doze.DozeLog, com.android.systemui.statusbar.phone.DozeParameters, com.android.systemui.statusbar.CommandQueue, com.android.systemui.statusbar.VibratorHelper, com.android.internal.util.LatencyTracker, android.os.PowerManager, android.view.accessibility.AccessibilityManager, int, com.android.keyguard.KeyguardUpdateMonitor, com.android.systemui.communal.CommunalSourceMonitor, com.android.internal.logging.MetricsLogger, android.app.ActivityManager, com.android.systemui.statusbar.policy.ConfigurationController, javax.inject.Provider, com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager, com.android.systemui.statusbar.notification.ConversationNotificationManager, com.android.systemui.media.MediaHierarchyManager, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.phone.NotificationsQSContainerController, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController, com.android.keyguard.dagger.KeyguardStatusViewComponent$Factory, com.android.keyguard.dagger.KeyguardQsUserSwitchComponent$Factory, com.android.keyguard.dagger.KeyguardUserSwitcherComponent$Factory, com.android.keyguard.dagger.KeyguardStatusBarViewComponent$Factory, com.android.systemui.communal.dagger.CommunalViewComponent$Factory, com.android.systemui.statusbar.LockscreenShadeTransitionController, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy, com.android.systemui.statusbar.phone.NotificationIconAreaController, com.android.systemui.biometrics.AuthController, com.android.systemui.statusbar.phone.ScrimController, android.os.UserManager, com.android.systemui.media.MediaDataManager, com.android.systemui.statusbar.NotificationShadeDepthController, com.android.systemui.statusbar.notification.stack.AmbientState, com.android.keyguard.LockIconViewController, com.android.systemui.media.KeyguardMediaController, com.android.systemui.statusbar.events.PrivacyDotViewController, com.android.systemui.statusbar.phone.TapAgainViewController, com.android.systemui.navigationbar.NavigationModeController, com.android.systemui.fragments.FragmentService, android.content.ContentResolver, com.android.systemui.wallet.controller.QuickAccessWalletController, com.android.systemui.qrcodescanner.controller.QRCodeScannerController, com.android.systemui.screenrecord.RecordingController, java.util.concurrent.Executor, com.android.systemui.util.settings.SecureSettings, com.android.systemui.statusbar.phone.SplitShadeHeaderController, com.android.systemui.statusbar.phone.ScreenOffAnimationController, com.android.systemui.statusbar.phone.LockscreenGestureLogger, com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager, com.android.systemui.statusbar.NotificationRemoteInputManager, java.util.Optional, com.android.systemui.controls.dagger.ControlsComponent, com.android.internal.jank.InteractionJankMonitor, com.android.systemui.statusbar.QsFrameTranslateController, com.android.systemui.model.SysUiState, com.android.systemui.keyguard.KeyguardUnlockAnimationController):void");
    }

    public final void animateToFullShade(long j) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mGoToFullShadeNeedsAnimation = true;
        notificationStackScrollLayout.mGoToFullShadeDelay = j;
        notificationStackScrollLayout.mNeedsAnimation = true;
        notificationStackScrollLayout.requestChildrenUpdate();
        this.mView.requestLayout();
        this.mAnimateNextPositionUpdate = true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyQSClippingImmediately(int r17, int r18, int r19, int r20, boolean r21) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            int r6 = r0.mScrimCornerRadius
            boolean r7 = r0.mIsFullWidth
            r8 = 0
            r9 = 0
            if (r7 == 0) goto L_0x0038
            android.graphics.Rect r6 = r0.mKeyguardStatusAreaClipBounds
            r6.set(r1, r2, r3, r4)
            com.android.systemui.screenrecord.RecordingController r6 = r0.mRecordingController
            boolean r6 = r6.isRecording()
            if (r6 == 0) goto L_0x0023
            r6 = r9
            goto L_0x0026
        L_0x0023:
            int r6 = r0.mScreenCornerRadius
            float r6 = (float) r6
        L_0x0026:
            int r7 = r0.mScrimCornerRadius
            float r7 = (float) r7
            float r10 = (float) r2
            float r10 = r10 / r7
            r11 = 1065353216(0x3f800000, float:1.0)
            float r10 = java.lang.Math.min(r10, r11)
            float r6 = android.util.MathUtils.lerp(r6, r7, r10)
            int r6 = (int) r6
            r7 = r5
            goto L_0x0039
        L_0x0038:
            r7 = r8
        L_0x0039:
            com.android.systemui.plugins.qs.QS r10 = r0.mQs
            if (r10 == 0) goto L_0x00b2
            com.android.systemui.statusbar.PulseExpansionHandler r10 = r0.mPulseExpansionHandler
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.isExpanding
            float r12 = r0.mTransitioningToFullShadeProgress
            int r12 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r12 > 0) goto L_0x0058
            if (r10 != 0) goto L_0x0058
            android.animation.ValueAnimator r12 = r0.mQsClippingAnimation
            if (r12 == 0) goto L_0x0075
            boolean r12 = r0.mIsQsTranslationResetAnimator
            if (r12 != 0) goto L_0x0058
            boolean r12 = r0.mIsPulseExpansionResetAnimator
            if (r12 == 0) goto L_0x0075
        L_0x0058:
            if (r10 != 0) goto L_0x0077
            boolean r10 = r0.mIsPulseExpansionResetAnimator
            if (r10 == 0) goto L_0x005f
            goto L_0x0077
        L_0x005f:
            boolean r10 = r0.mShouldUseSplitNotificationShade
            if (r10 != 0) goto L_0x0075
            com.android.systemui.plugins.qs.QS r10 = r0.mQs
            android.view.View r10 = r10.getHeader()
            int r10 = r10.getHeight()
            int r10 = r2 - r10
            float r10 = (float) r10
            r12 = 1043542835(0x3e333333, float:0.175)
            float r10 = r10 * r12
            goto L_0x008b
        L_0x0075:
            r10 = r9
            goto L_0x008b
        L_0x0077:
            com.android.systemui.plugins.qs.QS r10 = r0.mQs
            android.view.View r10 = r10.getHeader()
            int r10 = r10.getHeight()
            int r10 = r2 - r10
            float r10 = (float) r10
            r12 = 1073741824(0x40000000, float:2.0)
            float r10 = r10 / r12
            float r10 = java.lang.Math.max(r9, r10)
        L_0x008b:
            r0.mQsTranslationForFullShadeTransition = r10
            com.android.systemui.statusbar.QsFrameTranslateController r10 = r0.mQsFrameTranslateController
            r10.translateQsFrame()
            android.widget.FrameLayout r10 = r0.mQsFrame
            float r10 = r10.getTranslationY()
            float r12 = (float) r2
            float r12 = r12 - r10
            int r12 = (int) r12
            r0.mQsClipTop = r12
            float r13 = (float) r4
            float r13 = r13 - r10
            int r10 = (int) r13
            r0.mQsClipBottom = r10
            r0.mQsVisible = r5
            com.android.systemui.plugins.qs.QS r13 = r0.mQs
            if (r5 == 0) goto L_0x00ae
            boolean r14 = r0.mShouldUseSplitNotificationShade
            if (r14 != 0) goto L_0x00ae
            r14 = 1
            goto L_0x00af
        L_0x00ae:
            r14 = r8
        L_0x00af:
            r13.setFancyClipping(r12, r10, r6, r14)
        L_0x00b2:
            com.android.keyguard.KeyguardStatusViewController r10 = r0.mKeyguardStatusViewController
            r12 = 0
            if (r7 == 0) goto L_0x00ba
            android.graphics.Rect r7 = r0.mKeyguardStatusAreaClipBounds
            goto L_0x00bb
        L_0x00ba:
            r7 = r12
        L_0x00bb:
            java.util.Objects.requireNonNull(r10)
            if (r7 == 0) goto L_0x00ed
            android.graphics.Rect r12 = r10.mClipBounds
            int r13 = r7.left
            int r14 = r7.top
            float r14 = (float) r14
            T r15 = r10.mView
            com.android.keyguard.KeyguardStatusView r15 = (com.android.keyguard.KeyguardStatusView) r15
            float r15 = r15.getY()
            float r14 = r14 - r15
            int r14 = (int) r14
            int r15 = r7.right
            int r7 = r7.bottom
            float r7 = (float) r7
            T r11 = r10.mView
            com.android.keyguard.KeyguardStatusView r11 = (com.android.keyguard.KeyguardStatusView) r11
            float r11 = r11.getY()
            float r7 = r7 - r11
            int r7 = (int) r7
            r12.set(r13, r14, r15, r7)
            T r7 = r10.mView
            com.android.keyguard.KeyguardStatusView r7 = (com.android.keyguard.KeyguardStatusView) r7
            android.graphics.Rect r10 = r10.mClipBounds
            r7.setClipBounds(r10)
            goto L_0x00f4
        L_0x00ed:
            T r7 = r10.mView
            com.android.keyguard.KeyguardStatusView r7 = (com.android.keyguard.KeyguardStatusView) r7
            r7.setClipBounds(r12)
        L_0x00f4:
            if (r5 != 0) goto L_0x0100
            boolean r5 = r0.mShouldUseSplitNotificationShade
            if (r5 == 0) goto L_0x0100
            com.android.systemui.statusbar.phone.ScrimController r5 = r0.mScrimController
            r5.setNotificationsBounds(r9, r9, r9, r9)
            goto L_0x0109
        L_0x0100:
            com.android.systemui.statusbar.phone.ScrimController r5 = r0.mScrimController
            float r7 = (float) r1
            float r9 = (float) r2
            float r10 = (float) r3
            float r11 = (float) r4
            r5.setNotificationsBounds(r7, r9, r10, r11)
        L_0x0109:
            boolean r5 = r0.mShouldUseSplitNotificationShade
            if (r5 == 0) goto L_0x0132
            com.android.systemui.statusbar.phone.KeyguardStatusBarViewController r5 = r0.mKeyguardStatusBarViewController
            java.util.Objects.requireNonNull(r5)
            T r5 = r5.mView
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r5 = (com.android.systemui.statusbar.phone.KeyguardStatusBarView) r5
            java.util.Objects.requireNonNull(r5)
            int r7 = r5.mTopClipping
            if (r7 == 0) goto L_0x0159
            r5.mTopClipping = r8
            android.graphics.Rect r7 = r5.mClipRect
            int r9 = r5.getWidth()
            int r10 = r5.getHeight()
            r7.set(r8, r8, r9, r10)
            android.graphics.Rect r7 = r5.mClipRect
            r5.setClipBounds(r7)
            goto L_0x0159
        L_0x0132:
            com.android.systemui.statusbar.phone.KeyguardStatusBarViewController r5 = r0.mKeyguardStatusBarViewController
            java.util.Objects.requireNonNull(r5)
            T r5 = r5.mView
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r5 = (com.android.systemui.statusbar.phone.KeyguardStatusBarView) r5
            int r7 = r5.getTop()
            int r7 = r2 - r7
            int r9 = r5.mTopClipping
            if (r7 == r9) goto L_0x0159
            r5.mTopClipping = r7
            android.graphics.Rect r9 = r5.mClipRect
            int r10 = r5.getWidth()
            int r11 = r5.getHeight()
            r9.set(r8, r7, r10, r11)
            android.graphics.Rect r7 = r5.mClipRect
            r5.setClipBounds(r7)
        L_0x0159:
            com.android.systemui.statusbar.phone.ScrimController r5 = r0.mScrimController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.scrim.ScrimView r7 = r5.mScrimBehind
            if (r7 == 0) goto L_0x016f
            com.android.systemui.scrim.ScrimView r9 = r5.mNotificationsScrim
            if (r9 != 0) goto L_0x0167
            goto L_0x016f
        L_0x0167:
            r7.setCornerRadius(r6)
            com.android.systemui.scrim.ScrimView r5 = r5.mNotificationsScrim
            r5.setCornerRadius(r6)
        L_0x016f:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
            int r5 = r5.getLeft()
            int r1 = r1 - r5
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
            int r5 = r5.getLeft()
            int r3 = r3 - r5
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
            int r5 = r5.getTop()
            int r2 = r2 - r5
            boolean r5 = r0.mShouldUseSplitNotificationShade
            if (r5 == 0) goto L_0x0199
            r5 = r6
            goto L_0x019a
        L_0x0199:
            r5 = r8
        L_0x019a:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            java.util.Objects.requireNonNull(r0)
            int r7 = r0.mRoundedRectClippingLeft
            r9 = 5
            if (r7 != r1) goto L_0x01c6
            int r7 = r0.mRoundedRectClippingRight
            if (r7 != r3) goto L_0x01c6
            int r7 = r0.mRoundedRectClippingBottom
            if (r7 != r4) goto L_0x01c6
            int r7 = r0.mRoundedRectClippingTop
            if (r7 != r2) goto L_0x01c6
            float[] r7 = r0.mBgCornerRadii
            r10 = r7[r8]
            float r11 = (float) r6
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 != 0) goto L_0x01c6
            r7 = r7[r9]
            float r10 = (float) r5
            int r7 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r7 != 0) goto L_0x01c6
            goto L_0x0201
        L_0x01c6:
            r0.mRoundedRectClippingLeft = r1
            r0.mRoundedRectClippingTop = r2
            r0.mRoundedRectClippingBottom = r4
            r0.mRoundedRectClippingRight = r3
            float[] r7 = r0.mBgCornerRadii
            float r6 = (float) r6
            r7[r8] = r6
            r8 = 1
            r7[r8] = r6
            r8 = 2
            r7[r8] = r6
            r8 = 3
            r7[r8] = r6
            r6 = 4
            float r5 = (float) r5
            r7[r6] = r5
            r7[r9] = r5
            r6 = 6
            r7[r6] = r5
            r6 = 7
            r7[r6] = r5
            android.graphics.Path r5 = r0.mRoundedClipPath
            r5.reset()
            android.graphics.Path r6 = r0.mRoundedClipPath
            float r7 = (float) r1
            float r8 = (float) r2
            float r9 = (float) r3
            float r10 = (float) r4
            float[] r11 = r0.mBgCornerRadii
            android.graphics.Path$Direction r12 = android.graphics.Path.Direction.CW
            r6.addRoundRect(r7, r8, r9, r10, r11, r12)
            boolean r1 = r0.mShouldUseRoundedRectClipping
            if (r1 == 0) goto L_0x0201
            r0.invalidate()
        L_0x0201:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.applyQSClippingImmediately(int, int, int, int, boolean):void");
    }

    public final int calculatePanelHeightQsExpanded() {
        int i;
        int height = this.mNotificationStackScrollLayoutController.getHeight();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        int emptyBottomMargin = height - notificationStackScrollLayoutController.mView.getEmptyBottomMargin();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        float f = (float) (emptyBottomMargin - notificationStackScrollLayout.mTopPadding);
        if (this.mNotificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController3);
            if (notificationStackScrollLayoutController3.mShowEmptyShadeView) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = this.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController4);
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController4.mView;
                Objects.requireNonNull(notificationStackScrollLayout2);
                f = (float) notificationStackScrollLayout2.mEmptyShadeView.getHeight();
            }
        }
        int i2 = this.mQsMaxExpansionHeight;
        ValueAnimator valueAnimator = this.mQsSizeChangeAnimator;
        if (valueAnimator != null) {
            i2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        }
        if (this.mBarState == 1) {
            i = this.mClockPositionResult.stackScrollerPadding;
        } else {
            i = 0;
        }
        float max = ((float) Math.max(i2, i)) + f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController5 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController5);
        NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController5.mView;
        Objects.requireNonNull(notificationStackScrollLayout3);
        float f2 = max + notificationStackScrollLayout3.mTopPaddingOverflow;
        if (f2 > ((float) this.mNotificationStackScrollLayoutController.getHeight())) {
            float f3 = (float) i2;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController6 = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController6);
            f2 = Math.max(((float) notificationStackScrollLayoutController6.mView.getLayoutMinHeight()) + f3, (float) this.mNotificationStackScrollLayoutController.getHeight());
        }
        return (int) f2;
    }

    public final int calculatePanelHeightShade() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        int height = this.mNotificationStackScrollLayoutController.getHeight() - notificationStackScrollLayoutController.mView.getEmptyBottomMargin();
        if (this.mBarState != 1) {
            return height;
        }
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
        Objects.requireNonNull(keyguardClockPositionAlgorithm);
        int i = keyguardClockPositionAlgorithm.mKeyguardStatusHeight;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        return Math.max(height, i + notificationStackScrollLayout.mIntrinsicContentHeight);
    }

    public final int calculateQsBottomPosition(float f) {
        if (this.mTransitioningToFullShadeProgress > 0.0f) {
            return this.mTransitionToFullShadeQSPosition;
        }
        int qsMinExpansionHeight = this.mQs.getQsMinExpansionHeight() + ((int) getHeaderTranslation());
        if (((double) f) != 0.0d) {
            return (int) MathUtils.lerp(qsMinExpansionHeight, this.mQs.getDesiredHeight(), f);
        }
        return qsMinExpansionHeight;
    }

    public final float computeQsExpansionFraction() {
        if (this.mQSAnimatingHiddenFromCollapsed) {
            return 0.0f;
        }
        float f = this.mQsExpansionHeight;
        int i = this.mQsMinExpansionHeight;
        return Math.min(1.0f, (f - ((float) i)) / ((float) (this.mQsMaxExpansionHeight - i)));
    }

    public final String determineAccessibilityPaneTitle() {
        C0961QS qs = this.mQs;
        if (qs != null && qs.isCustomizing()) {
            return this.mResources.getString(C1777R.string.accessibility_desc_quick_settings_edit);
        }
        if (this.mQsExpansionHeight != 0.0f && this.mQsFullyExpanded) {
            return this.mResources.getString(C1777R.string.accessibility_desc_quick_settings);
        }
        if (this.mBarState == 1) {
            return this.mResources.getString(C1777R.string.accessibility_desc_lock_screen);
        }
        return this.mResources.getString(C1777R.string.accessibility_desc_notification_shade);
    }

    public final ViewPropertyAnimator fadeOut(long j, long j2, Runnable runnable) {
        return this.mView.animate().alpha(0.0f).setStartDelay(j).setDuration(j2).setInterpolator(Interpolators.ALPHA_OUT).withLayer().withEndAction(runnable);
    }

    public final float getFadeoutAlpha() {
        int i = this.mQsMinExpansionHeight;
        if (i == 0) {
            return 1.0f;
        }
        return (float) Math.pow((double) Math.max(0.0f, Math.min(this.mExpandedHeight / ((float) i), 1.0f)), 0.75d);
    }

    public final float getHeaderTranslation() {
        if (this.mBarState == 1 && !this.mKeyguardBypassController.getBypassEnabled()) {
            return (float) (-this.mQs.getQsMinExpansionHeight());
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        float f = this.mExpandedHeight;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        float appearEndPosition = notificationStackScrollLayout.getAppearEndPosition();
        float appearStartPosition = notificationStackScrollLayout.getAppearStartPosition();
        float f2 = (f - appearStartPosition) / (appearEndPosition - appearStartPosition);
        float f3 = -this.mQsExpansionHeight;
        if (!this.mShouldUseSplitNotificationShade && this.mBarState == 0) {
            f3 *= 0.175f;
        }
        if (this.mKeyguardBypassController.getBypassEnabled() && isOnKeyguard()) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
            Objects.requireNonNull(ambientState);
            float f4 = ambientState.mPulseHeight;
            if (f4 == 100000.0f) {
                f4 = 0.0f;
            }
            f2 = MathUtils.smoothStep(0.0f, (float) notificationStackScrollLayout2.mIntrinsicPadding, f4);
            f3 = (float) (-this.mQs.getQsMinExpansionHeight());
        }
        return Math.min(0.0f, MathUtils.lerp(f3, 0.0f, Math.min(1.0f, f2)));
    }

    public final int getKeyguardNotificationStaticPadding() {
        if (!this.mKeyguardShowing) {
            return 0;
        }
        if (!this.mKeyguardBypassController.getBypassEnabled()) {
            return this.mClockPositionResult.stackScrollerPadding;
        }
        int i = this.mHeadsUpInset;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        if (!notificationStackScrollLayout.mAmbientState.isPulseExpanding()) {
            return i;
        }
        int i2 = this.mClockPositionResult.stackScrollerPadding;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
        Objects.requireNonNull(ambientState);
        float f = ambientState.mPulseHeight;
        if (f == 100000.0f) {
            f = 0.0f;
        }
        return (int) MathUtils.lerp(i, i2, MathUtils.smoothStep(0.0f, (float) notificationStackScrollLayout2.mIntrinsicPadding, f));
    }

    public final int getMaxPanelHeight() {
        int i;
        int i2 = this.mStatusBarMinHeight;
        if (this.mBarState != 1 && this.mNotificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            i2 = Math.max(i2, this.mQsMinExpansionHeight);
        }
        if (this.mQsExpandImmediate || this.mQsExpanded || ((this.mIsExpanding && this.mQsExpandedWhenExpandingStarted) || this.mPulsing)) {
            i = calculatePanelHeightQsExpanded();
        } else {
            i = calculatePanelHeightShade();
        }
        int max = Math.max(i2, i);
        if (max == 0 || Float.isNaN((float) max)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("maxPanelHeight is invalid. mOverExpansion: ");
            m.append(this.mOverExpansion);
            m.append(", calculatePanelHeightQsExpanded: ");
            m.append(calculatePanelHeightQsExpanded());
            m.append(", calculatePanelHeightShade: ");
            m.append(calculatePanelHeightShade());
            m.append(", mStatusBarMinHeight = ");
            m.append(this.mStatusBarMinHeight);
            m.append(", mQsMinExpansionHeight = ");
            m.append(this.mQsMinExpansionHeight);
            Log.wtf("PanelView", m.toString());
        }
        return max;
    }

    public final float getQSEdgePosition() {
        float f = this.mQuickQsOffsetHeight;
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        float f2 = f * ambientState.mExpansionFraction;
        AmbientState ambientState2 = this.mAmbientState;
        Objects.requireNonNull(ambientState2);
        float f3 = ambientState2.mStackY;
        AmbientState ambientState3 = this.mAmbientState;
        Objects.requireNonNull(ambientState3);
        return Math.max(f2, f3 - ((float) ambientState3.mScrollY));
    }

    public final boolean hideStatusBarIconsWhenExpanded() {
        if (this.mIsLaunchAnimationRunning) {
            return this.mHideIconsDuringLaunchAnimation;
        }
        HeadsUpAppearanceController headsUpAppearanceController = this.mHeadsUpAppearanceController;
        if (headsUpAppearanceController != null && headsUpAppearanceController.shouldBeVisible()) {
            return false;
        }
        if (!this.mIsFullWidth || !this.mShowIconsWhenExpanded) {
            return true;
        }
        return false;
    }

    public final void initBottomArea() {
        KeyguardAffordanceHelper keyguardAffordanceHelper = new KeyguardAffordanceHelper(this.mKeyguardAffordanceHelperCallback, this.mView.getContext(), this.mFalsingManager);
        this.mAffordanceHelper = keyguardAffordanceHelper;
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        Objects.requireNonNull(keyguardBottomAreaView);
        keyguardBottomAreaView.mAffordanceHelper = keyguardAffordanceHelper;
        KeyguardBottomAreaView keyguardBottomAreaView2 = this.mKeyguardBottomArea;
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(keyguardBottomAreaView2);
        keyguardBottomAreaView2.mStatusBar = statusBar;
        keyguardBottomAreaView2.updateCameraVisibility();
        KeyguardBottomAreaView keyguardBottomAreaView3 = this.mKeyguardBottomArea;
        boolean z = this.mUserSetupComplete;
        Objects.requireNonNull(keyguardBottomAreaView3);
        keyguardBottomAreaView3.mUserSetupComplete = z;
        keyguardBottomAreaView3.updateCameraVisibility();
        keyguardBottomAreaView3.updateLeftAffordanceIcon();
        KeyguardBottomAreaView keyguardBottomAreaView4 = this.mKeyguardBottomArea;
        FalsingManager falsingManager = this.mFalsingManager;
        Objects.requireNonNull(keyguardBottomAreaView4);
        keyguardBottomAreaView4.mFalsingManager = falsingManager;
        KeyguardBottomAreaView keyguardBottomAreaView5 = this.mKeyguardBottomArea;
        QuickAccessWalletController quickAccessWalletController = this.mQuickAccessWalletController;
        Objects.requireNonNull(keyguardBottomAreaView5);
        keyguardBottomAreaView5.mQuickAccessWalletController = quickAccessWalletController;
        quickAccessWalletController.setupWalletChangeObservers(keyguardBottomAreaView5.mCardRetriever, QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
        keyguardBottomAreaView5.mQuickAccessWalletController.updateWalletPreference();
        keyguardBottomAreaView5.mQuickAccessWalletController.queryWalletCards(keyguardBottomAreaView5.mCardRetriever);
        keyguardBottomAreaView5.updateWalletVisibility();
        keyguardBottomAreaView5.updateAffordanceColors();
        KeyguardBottomAreaView keyguardBottomAreaView6 = this.mKeyguardBottomArea;
        ControlsComponent controlsComponent = this.mControlsComponent;
        Objects.requireNonNull(keyguardBottomAreaView6);
        keyguardBottomAreaView6.mControlsComponent = controlsComponent;
        controlsComponent.getControlsListingController().ifPresent(new WMShell$$ExternalSyntheticLambda6(keyguardBottomAreaView6, 2));
        keyguardBottomAreaView6.updateAffordanceColors();
        KeyguardBottomAreaView keyguardBottomAreaView7 = this.mKeyguardBottomArea;
        QRCodeScannerController qRCodeScannerController = this.mQRCodeScannerController;
        Objects.requireNonNull(keyguardBottomAreaView7);
        keyguardBottomAreaView7.mQRCodeScannerController = qRCodeScannerController;
        qRCodeScannerController.registerQRCodeScannerChangeObservers(0, 1);
        keyguardBottomAreaView7.updateQRCodeButtonVisibility();
        keyguardBottomAreaView7.updateAffordanceColors();
    }

    public final boolean isOnKeyguard() {
        if (this.mBarState == 1) {
            return true;
        }
        return false;
    }

    public final boolean isQsExpansionEnabled() {
        if (!this.mQsExpansionEnabledPolicy || !this.mQsExpansionEnabledAmbient || this.mRemoteInputManager.isRemoteInputActive()) {
            return false;
        }
        return true;
    }

    public final void loadDimens() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mView.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mHintDistance = this.mResources.getDimension(C1777R.dimen.hint_move_distance);
        this.mPanelFlingOvershootAmount = this.mResources.getDimension(C1777R.dimen.panel_overshoot_amount);
        this.mUnlockFalsingThreshold = this.mResources.getDimensionPixelSize(C1777R.dimen.unlock_falsing_threshold);
        FlingAnimationUtils.Builder builder = this.mFlingAnimationUtilsBuilder.get();
        Objects.requireNonNull(builder);
        builder.mMaxLengthSeconds = 0.4f;
        this.mFlingAnimationUtils = builder.build();
        this.mStatusBarMinHeight = SystemBarUtils.getStatusBarHeight(this.mView.getContext());
        this.mStatusBarHeaderHeightKeyguard = Utils.getStatusBarHeaderHeightKeyguard(this.mView.getContext());
        this.mQsPeekHeight = this.mResources.getDimensionPixelSize(C1777R.dimen.qs_peek_height);
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
        Resources resources = this.mResources;
        Objects.requireNonNull(keyguardClockPositionAlgorithm);
        keyguardClockPositionAlgorithm.mStatusViewBottomMargin = resources.getDimensionPixelSize(C1777R.dimen.keyguard_status_view_bottom_margin);
        keyguardClockPositionAlgorithm.mSplitShadeTopNotificationsMargin = resources.getDimensionPixelSize(C1777R.dimen.split_shade_header_height);
        keyguardClockPositionAlgorithm.mSplitShadeTargetTopMargin = resources.getDimensionPixelSize(C1777R.dimen.keyguard_split_shade_top_margin);
        keyguardClockPositionAlgorithm.mContainerTopPadding = resources.getDimensionPixelSize(C1777R.dimen.keyguard_clock_top_margin);
        keyguardClockPositionAlgorithm.mBurnInPreventionOffsetX = resources.getDimensionPixelSize(C1777R.dimen.burn_in_prevention_offset_x);
        keyguardClockPositionAlgorithm.mBurnInPreventionOffsetYClock = resources.getDimensionPixelSize(C1777R.dimen.burn_in_prevention_offset_y_clock);
        this.mQsFalsingThreshold = this.mResources.getDimensionPixelSize(C1777R.dimen.qs_falsing_threshold);
        this.mResources.getDimensionPixelSize(C1777R.dimen.notification_panel_min_side_margin);
        this.mIndicationBottomPadding = this.mResources.getDimensionPixelSize(C1777R.dimen.keyguard_indication_bottom_padding);
        this.mResources.getDimensionPixelSize(C1777R.dimen.notification_shelf_height);
        this.mResources.getDimensionPixelSize(C1777R.dimen.status_bar_icon_drawing_size_dark);
        this.mHeadsUpInset = this.mResources.getDimensionPixelSize(C1777R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(this.mView.getContext());
        this.mDistanceForQSFullShadeTransition = this.mResources.getDimensionPixelSize(C1777R.dimen.lockscreen_shade_qs_transition_distance);
        this.mMaxOverscrollAmountForPulse = this.mResources.getDimensionPixelSize(C1777R.dimen.pulse_expansion_max_top_overshoot);
        this.mScrimCornerRadius = this.mResources.getDimensionPixelSize(C1777R.dimen.notification_scrim_corner_radius);
        this.mScreenCornerRadius = (int) ScreenDecorationsUtils.getWindowCornerRadius(this.mView.getContext());
        this.mLockscreenNotificationQSPadding = this.mResources.getDimensionPixelSize(C1777R.dimen.notification_side_paddings);
        this.mUdfpsMaxYBurnInOffset = (float) this.mResources.getDimensionPixelSize(C1777R.dimen.udfps_burn_in_offset_y);
    }

    public final void onExpandingStarted() {
        boolean z;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mIsExpansionChanging = true;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mExpansionChanging = true;
        notificationStackScrollLayoutController.checkSnoozeLeavebehind();
        this.mIsExpanding = true;
        boolean z2 = this.mQsFullyExpanded;
        this.mQsExpandedWhenExpandingStarted = z2;
        MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
        if (!z2 || this.mAnimatingQS) {
            z = false;
        } else {
            z = true;
        }
        Objects.requireNonNull(mediaHierarchyManager);
        if (mediaHierarchyManager.collapsingShadeFromQS != z) {
            mediaHierarchyManager.collapsingShadeFromQS = z;
            MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
        }
        if (this.mQsExpanded) {
            onQsExpansionStarted();
        }
        C0961QS qs = this.mQs;
        if (qs != null) {
            qs.setHeaderListening(true);
        }
    }

    public final void onQsExpansionStarted() {
        ValueAnimator valueAnimator = this.mQsExpansionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        cancelHeightAnimator();
        float f = this.mQsExpansionHeight - ((float) 0);
        setQsExpansion(f);
        requestPanelHeightUpdate();
        this.mNotificationStackScrollLayoutController.checkSnoozeLeavebehind();
        if (f == 0.0f) {
            StatusBar statusBar = this.mStatusBar;
            Objects.requireNonNull(statusBar);
            if (!statusBar.mKeyguardStateController.canDismissLockScreen()) {
                statusBar.mKeyguardUpdateMonitor.requestFaceAuth(false);
            }
        }
    }

    public final void onTrackingStarted() {
        FalsingCollector falsingCollector = this.mFalsingCollector;
        this.mKeyguardStateController.canDismissLockScreen();
        falsingCollector.onTrackingStarted();
        endClosing();
        this.mTracking = true;
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mShadeController.runPostCollapseRunnables();
        notifyExpandingStarted();
        updatePanelExpansionAndVisibility();
        ScrimController scrimController = this.mScrimController;
        Objects.requireNonNull(scrimController);
        scrimController.mTracking = true;
        scrimController.mDarkenWhileDragging = !scrimController.mKeyguardStateController.canDismissLockScreen();
        if (this.mQsFullyExpanded) {
            this.mQsExpandImmediate = true;
            if (!this.mShouldUseSplitNotificationShade) {
                this.mNotificationStackScrollLayoutController.setShouldShowShelfOnly(true);
            }
        }
        int i = this.mBarState;
        if (i == 1 || i == 2) {
            KeyguardAffordanceHelper keyguardAffordanceHelper = this.mAffordanceHelper;
            Objects.requireNonNull(keyguardAffordanceHelper);
            Animator animator = keyguardAffordanceHelper.mSwipeAnimator;
            if (animator != null) {
                animator.cancel();
            }
            KeyguardAffordanceHelper.updateIcon(keyguardAffordanceHelper.mRightIcon, 0.0f, 0.0f, true, false, false, false);
            KeyguardAffordanceHelper.updateIcon(keyguardAffordanceHelper.mLeftIcon, 0.0f, 0.0f, true, false, false, false);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mPanelTracking = true;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mPanelTracking = true;
        notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView(true, true);
        this.mView.removeCallbacks(this.mMaybeHideExpandedRunnable);
    }

    public final void onTrackingStopped(boolean z) {
        int i;
        this.mFalsingCollector.onTrackingStopped();
        this.mTracking = false;
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        int i2 = statusBar.mState;
        if ((i2 == 1 || i2 == 2) && !z && !statusBar.mKeyguardStateController.canDismissLockScreen()) {
            statusBar.mStatusBarKeyguardViewManager.showBouncer(false);
        }
        updatePanelExpansionAndVisibility();
        if (z) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.setOverScrollAmount(0.0f, true, true, true);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        notificationStackScrollLayout2.mPanelTracking = false;
        AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mPanelTracking = false;
        if (z && (((i = this.mBarState) == 1 || i == 2) && !this.mHintAnimationRunning)) {
            this.mAffordanceHelper.reset(true);
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        Objects.requireNonNull(notificationShadeDepthController);
        if (notificationShadeDepthController.blursDisabledForUnlock) {
            notificationShadeDepthController.blursDisabledForUnlock = false;
            notificationShadeDepthController.scheduleUpdate((View) null);
        }
    }

    public final void positionClockAndNotifications(boolean z) {
        boolean z2;
        boolean z3;
        int i;
        int i2;
        boolean z4;
        boolean z5;
        boolean z6;
        int i3;
        float f;
        float f2;
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z7;
        int i8;
        int i9;
        boolean z8;
        int i10;
        int i11;
        int i12;
        float f3;
        boolean z9;
        boolean isAddOrRemoveAnimationPending = this.mNotificationStackScrollLayoutController.isAddOrRemoveAnimationPending();
        boolean isOnKeyguard = isOnKeyguard();
        if (isOnKeyguard && this.mCommunalViewController != null) {
            if (this.mScreenOffAnimationController.shouldExpandNotifications()) {
                f3 = 1.0f;
            } else {
                f3 = this.mExpandedFraction;
            }
            CommunalHostViewPositionAlgorithm communalHostViewPositionAlgorithm = this.mCommunalPositionAlgorithm;
            int height = this.mCommunalView.getHeight();
            if (CommunalHostViewPositionAlgorithm.DEBUG) {
                Objects.requireNonNull(communalHostViewPositionAlgorithm);
                Log.d("CommunalPositionAlg", "setup. panelExpansion:" + f3);
            }
            communalHostViewPositionAlgorithm.mPanelExpansion = f3;
            communalHostViewPositionAlgorithm.mCommunalHeight = height;
            CommunalHostViewPositionAlgorithm communalHostViewPositionAlgorithm2 = this.mCommunalPositionAlgorithm;
            CommunalHostViewPositionAlgorithm.Result result = this.mCommunalPositionResult;
            Objects.requireNonNull(communalHostViewPositionAlgorithm2);
            result.communalY = (int) ((1.0f - communalHostViewPositionAlgorithm2.mPanelExpansion) * ((float) (-communalHostViewPositionAlgorithm2.mCommunalHeight)));
            if (this.mNotificationStackScrollLayoutController.isAddOrRemoveAnimationPending() || this.mAnimateNextPositionUpdate) {
                z9 = true;
            } else {
                z9 = false;
            }
            CommunalHostViewController communalHostViewController = this.mCommunalViewController;
            int i13 = this.mCommunalPositionResult.communalY;
            Objects.requireNonNull(communalHostViewController);
            PropertyAnimator.setProperty((CommunalHostView) communalHostViewController.mView, AnimatableProperty.f80Y, (float) i13, CommunalHostViewController.COMMUNAL_ANIMATION_PROPERTIES, z9);
        }
        if (isOnKeyguard || z) {
            int i14 = this.mStatusBarHeaderHeightKeyguard;
            boolean bypassEnabled = this.mKeyguardBypassController.getBypassEnabled();
            if (this.mNotificationStackScrollLayoutController.getVisibleNotificationCount() != 0 || this.mMediaDataManager.hasActiveMedia()) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (!this.mShouldUseSplitNotificationShade || !this.mMediaDataManager.hasActiveMedia()) {
                z5 = false;
            } else {
                z5 = true;
            }
            ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
            Objects.requireNonNull(screenOffAnimationController);
            ArrayList arrayList = screenOffAnimationController.animations;
            if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!((ScreenOffAnimation) it.next()).shouldAnimateClockChange()) {
                            z6 = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            z6 = true;
            if ((!z4 || this.mShouldUseSplitNotificationShade) && (!z5 || this.mDozing)) {
                KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
                Objects.requireNonNull(keyguardStatusViewController);
                keyguardStatusViewController.mKeyguardClockSwitchController.displayClock(0, z6);
            } else {
                KeyguardStatusViewController keyguardStatusViewController2 = this.mKeyguardStatusViewController;
                Objects.requireNonNull(keyguardStatusViewController2);
                keyguardStatusViewController2.mKeyguardClockSwitchController.displayClock(1, z6);
            }
            updateKeyguardStatusViewAlignment(true);
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
            if (keyguardQsUserSwitchController != null) {
                i3 = keyguardQsUserSwitchController.mUserAvatarView.getHeight();
            } else {
                i3 = 0;
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
            if (keyguardUserSwitcherController != null) {
                i3 = keyguardUserSwitcherController.mListView.getHeight();
            }
            if (this.mScreenOffAnimationController.shouldExpandNotifications()) {
                f = 1.0f;
            } else {
                f = this.mExpandedFraction;
            }
            if (this.mScreenOffAnimationController.shouldExpandNotifications()) {
                f2 = 1.0f;
            } else {
                f2 = this.mInterpolatedDarkAmount;
            }
            float f4 = -1.0f;
            if (this.mUpdateMonitor.isUdfpsEnrolled()) {
                AuthController authController = this.mAuthController;
                Objects.requireNonNull(authController);
                if (authController.mUdfpsProps.size() > 0) {
                    AuthController authController2 = this.mAuthController;
                    Objects.requireNonNull(authController2);
                    SensorLocationInternal location = ((FingerprintSensorPropertiesInternal) authController2.mUdfpsProps.get(0)).getLocation();
                    f4 = ((float) (location.sensorLocationY - location.sensorRadius)) - this.mUdfpsMaxYBurnInOffset;
                }
            }
            KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
            int i15 = this.mStatusBarHeaderHeightKeyguard;
            KeyguardStatusViewController keyguardStatusViewController3 = this.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController3);
            int height2 = ((KeyguardStatusView) keyguardStatusViewController3.mView).getHeight();
            KeyguardClockSwitchController keyguardClockSwitchController = keyguardStatusViewController3.mKeyguardClockSwitchController;
            Objects.requireNonNull(keyguardClockSwitchController);
            NotificationIconAreaController notificationIconAreaController = keyguardClockSwitchController.mNotificationIconAreaController;
            Objects.requireNonNull(notificationIconAreaController);
            NotificationIconContainer notificationIconContainer = notificationIconAreaController.mAodIcons;
            if (notificationIconContainer == null) {
                i4 = 0;
            } else {
                i4 = notificationIconContainer.getHeight();
            }
            int i16 = height2 - i4;
            float f5 = this.mOverStretchAmount;
            C0961QS qs = this.mQs;
            if (qs != null) {
                i5 = qs.getHeader().getHeight();
            } else {
                i5 = 0;
            }
            int i17 = i5 + this.mQsPeekHeight;
            float computeQsExpansionFraction = computeQsExpansionFraction();
            z3 = isAddOrRemoveAnimationPending;
            int i18 = this.mDisplayTopInset;
            z2 = isOnKeyguard;
            boolean z10 = this.mShouldUseSplitNotificationShade;
            boolean z11 = z6;
            KeyguardStatusViewController keyguardStatusViewController4 = this.mKeyguardStatusViewController;
            float f6 = f4;
            int i19 = this.mStatusBarHeaderHeightKeyguard;
            Objects.requireNonNull(keyguardStatusViewController4);
            KeyguardClockSwitchController keyguardClockSwitchController2 = keyguardStatusViewController4.mKeyguardClockSwitchController;
            Objects.requireNonNull(keyguardClockSwitchController2);
            boolean z12 = z10;
            if (keyguardClockSwitchController2.mLargeClockFrame.getVisibility() == 0) {
                i7 = (keyguardClockSwitchController2.mLargeClockFrame.findViewById(C1777R.C1779id.animatable_clock_view_large).getHeight() / 2) + (keyguardClockSwitchController2.mLargeClockFrame.getHeight() / 2);
                i6 = i18;
            } else {
                i6 = i18;
                i7 = keyguardClockSwitchController2.mKeyguardClockTopMargin + keyguardClockSwitchController2.mClockFrame.findViewById(C1777R.C1779id.animatable_clock_view).getHeight() + i19;
            }
            float f7 = (float) i7;
            KeyguardStatusViewController keyguardStatusViewController5 = this.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController5);
            KeyguardClockSwitchController keyguardClockSwitchController3 = keyguardStatusViewController5.mKeyguardClockSwitchController;
            Objects.requireNonNull(keyguardClockSwitchController3);
            if (keyguardClockSwitchController3.mLargeClockFrame.getVisibility() != 0) {
                z7 = true;
            } else {
                z7 = false;
            }
            Objects.requireNonNull(keyguardClockPositionAlgorithm);
            keyguardClockPositionAlgorithm.mMinTopMargin = Math.max(keyguardClockPositionAlgorithm.mContainerTopPadding, i3) + i15;
            keyguardClockPositionAlgorithm.mPanelExpansion = f;
            keyguardClockPositionAlgorithm.mKeyguardStatusHeight = keyguardClockPositionAlgorithm.mStatusViewBottomMargin + i16;
            keyguardClockPositionAlgorithm.mUserSwitchHeight = i3;
            keyguardClockPositionAlgorithm.mUserSwitchPreferredY = i14;
            keyguardClockPositionAlgorithm.mDarkAmount = f2;
            keyguardClockPositionAlgorithm.mOverStretchAmount = f5;
            keyguardClockPositionAlgorithm.mBypassEnabled = bypassEnabled;
            keyguardClockPositionAlgorithm.mUnlockedStackScrollerPadding = i17;
            keyguardClockPositionAlgorithm.mQsExpansion = computeQsExpansionFraction;
            keyguardClockPositionAlgorithm.mCutoutTopInset = i6;
            keyguardClockPositionAlgorithm.mIsSplitShade = z12;
            keyguardClockPositionAlgorithm.mUdfpsTop = f6;
            keyguardClockPositionAlgorithm.mClockBottom = f7;
            keyguardClockPositionAlgorithm.mIsClockTopAligned = z7;
            KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm2 = this.mClockPositionAlgorithm;
            KeyguardClockPositionAlgorithm.Result result2 = this.mClockPositionResult;
            Objects.requireNonNull(keyguardClockPositionAlgorithm2);
            int clockY = keyguardClockPositionAlgorithm2.getClockY(keyguardClockPositionAlgorithm2.mPanelExpansion, keyguardClockPositionAlgorithm2.mDarkAmount);
            result2.clockY = clockY;
            result2.userSwitchY = (int) (MathUtils.lerp((float) ((-keyguardClockPositionAlgorithm2.mKeyguardStatusHeight) - keyguardClockPositionAlgorithm2.mUserSwitchHeight), (float) keyguardClockPositionAlgorithm2.mUserSwitchPreferredY, Interpolators.FAST_OUT_LINEAR_IN.getInterpolation(keyguardClockPositionAlgorithm2.mPanelExpansion)) + keyguardClockPositionAlgorithm2.mOverStretchAmount);
            result2.clockYFullyDozing = keyguardClockPositionAlgorithm2.getClockY(1.0f, 1.0f);
            result2.clockAlpha = MathUtils.lerp(Interpolators.ACCELERATE.getInterpolation((1.0f - MathUtils.saturate(keyguardClockPositionAlgorithm2.mQsExpansion / 0.3f)) * Math.max(0.0f, ((float) clockY) / Math.max(1.0f, (float) keyguardClockPositionAlgorithm2.getClockY(1.0f, keyguardClockPositionAlgorithm2.mDarkAmount)))), 1.0f, keyguardClockPositionAlgorithm2.mDarkAmount);
            boolean z13 = keyguardClockPositionAlgorithm2.mBypassEnabled;
            if (z13) {
                i8 = (int) (((float) keyguardClockPositionAlgorithm2.mUnlockedStackScrollerPadding) + keyguardClockPositionAlgorithm2.mOverStretchAmount);
            } else {
                if (keyguardClockPositionAlgorithm2.mIsSplitShade) {
                    clockY -= keyguardClockPositionAlgorithm2.mSplitShadeTopNotificationsMargin;
                    i12 = keyguardClockPositionAlgorithm2.mUserSwitchHeight;
                } else {
                    i12 = keyguardClockPositionAlgorithm2.mKeyguardStatusHeight;
                }
                i8 = clockY + i12;
            }
            result2.stackScrollerPadding = i8;
            if (z13) {
                i9 = keyguardClockPositionAlgorithm2.mUnlockedStackScrollerPadding;
            } else {
                if (keyguardClockPositionAlgorithm2.mIsSplitShade) {
                    i11 = keyguardClockPositionAlgorithm2.getClockY(1.0f, keyguardClockPositionAlgorithm2.mDarkAmount);
                    i10 = keyguardClockPositionAlgorithm2.mUserSwitchHeight;
                } else {
                    i11 = keyguardClockPositionAlgorithm2.getClockY(1.0f, keyguardClockPositionAlgorithm2.mDarkAmount);
                    i10 = keyguardClockPositionAlgorithm2.mKeyguardStatusHeight;
                }
                i9 = i11 + i10;
            }
            result2.stackScrollerPaddingExpanded = i9;
            result2.clockX = (int) R$array.interpolate(0.0f, (float) R$anim.getBurnInOffset(keyguardClockPositionAlgorithm2.mBurnInPreventionOffsetX, true), keyguardClockPositionAlgorithm2.mDarkAmount);
            result2.clockScale = R$array.interpolate(R$anim.zigzag(((float) System.currentTimeMillis()) / 60000.0f, 0.2f, 181.0f) + 0.8f, 1.0f, 1.0f - keyguardClockPositionAlgorithm2.mDarkAmount);
            if ((this.mNotificationStackScrollLayoutController.isAddOrRemoveAnimationPending() || this.mAnimateNextPositionUpdate) && z11) {
                z8 = true;
            } else {
                z8 = false;
            }
            KeyguardStatusViewController keyguardStatusViewController6 = this.mKeyguardStatusViewController;
            KeyguardClockPositionAlgorithm.Result result3 = this.mClockPositionResult;
            keyguardStatusViewController6.updatePosition(result3.clockX, result3.clockY, result3.clockScale, z8);
            KeyguardQsUserSwitchController keyguardQsUserSwitchController2 = this.mKeyguardQsUserSwitchController;
            if (keyguardQsUserSwitchController2 != null) {
                KeyguardClockPositionAlgorithm.Result result4 = this.mClockPositionResult;
                int i20 = result4.clockX;
                int i21 = result4.userSwitchY;
                AnimationProperties animationProperties = KeyguardQsUserSwitchController.ANIMATION_PROPERTIES;
                PropertyAnimator.setProperty((FrameLayout) keyguardQsUserSwitchController2.mView, AnimatableProperty.f80Y, (float) i21, animationProperties, z8);
                PropertyAnimator.setProperty((FrameLayout) keyguardQsUserSwitchController2.mView, AnimatableProperty.TRANSLATION_X, (float) (-Math.abs(i20)), animationProperties, z8);
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController2 = this.mKeyguardUserSwitcherController;
            if (keyguardUserSwitcherController2 != null) {
                KeyguardClockPositionAlgorithm.Result result5 = this.mClockPositionResult;
                int i22 = result5.clockX;
                int i23 = result5.userSwitchY;
                AnimationProperties animationProperties2 = KeyguardUserSwitcherController.ANIMATION_PROPERTIES;
                PropertyAnimator.setProperty(keyguardUserSwitcherController2.mListView, AnimatableProperty.f80Y, (float) i23, animationProperties2, z8);
                PropertyAnimator.setProperty(keyguardUserSwitcherController2.mListView, AnimatableProperty.TRANSLATION_X, (float) (-Math.abs(i22)), animationProperties2, z8);
                Rect rect = new Rect();
                keyguardUserSwitcherController2.mListView.getDrawingRect(rect);
                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).offsetDescendantRectToMyCoords(keyguardUserSwitcherController2.mListView, rect);
                KeyguardUserSwitcherScrim keyguardUserSwitcherScrim = keyguardUserSwitcherController2.mBackground;
                Objects.requireNonNull(keyguardUserSwitcherScrim);
                keyguardUserSwitcherScrim.mCircleX = (int) (keyguardUserSwitcherController2.mListView.getTranslationX() + ((float) rect.left) + ((float) (rect.width() / 2)));
                keyguardUserSwitcherScrim.mCircleY = (int) (keyguardUserSwitcherController2.mListView.getTranslationY() + ((float) rect.top) + ((float) (rect.height() / 2)));
                keyguardUserSwitcherScrim.updatePaint();
            }
            updateNotificationTranslucency();
            updateClock();
        } else {
            z3 = isAddOrRemoveAnimationPending;
            z2 = isOnKeyguard;
        }
        if (z2) {
            i = this.mClockPositionResult.stackScrollerPaddingExpanded;
        } else if (this.mShouldUseSplitNotificationShade) {
            i = 0;
        } else {
            C0961QS qs2 = this.mQs;
            if (qs2 != null) {
                i2 = qs2.getHeader().getHeight();
            } else {
                i2 = 0;
            }
            i = i2 + this.mQsPeekHeight;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mIntrinsicPadding = i;
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        int i24 = this.mClockPositionResult.clockX;
        Objects.requireNonNull(keyguardBottomAreaView);
        if (keyguardBottomAreaView.mBurnInXOffset != i24) {
            keyguardBottomAreaView.mBurnInXOffset = i24;
            float f8 = (float) i24;
            keyguardBottomAreaView.mIndicationArea.setTranslationX(f8);
            View view = keyguardBottomAreaView.mAmbientIndicationArea;
            if (view != null) {
                view.setTranslationX(f8);
            }
        }
        this.mStackScrollerMeasuringPass++;
        requestScrollerTopPaddingUpdate(z3);
        this.mStackScrollerMeasuringPass = 0;
        this.mAnimateNextPositionUpdate = false;
    }

    public final View reInflateStub(int i, int i2, int i3, boolean z) {
        View findViewById = this.mView.findViewById(i);
        if (findViewById != null) {
            int indexOfChild = this.mView.indexOfChild(findViewById);
            this.mView.removeView(findViewById);
            if (z) {
                View inflate = this.mLayoutInflater.inflate(i3, this.mView, false);
                this.mView.addView(inflate, indexOfChild);
                return inflate;
            }
            ViewStub viewStub = new ViewStub(this.mView.getContext(), i3);
            viewStub.setId(i2);
            this.mView.addView(viewStub, indexOfChild);
            return null;
        } else if (z) {
            return ((ViewStub) this.mView.findViewById(i2)).inflate();
        } else {
            return findViewById;
        }
    }

    public final void registerCallbacks(NotifPanelEventSource.Callbacks callbacks) {
        this.mNotifEventSourceCallbacks.addIfAbsent(callbacks);
    }

    public final void requestScrollerTopPaddingUpdate(boolean z) {
        float f;
        boolean z2;
        boolean z3;
        int i;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!this.mShouldUseSplitNotificationShade || this.mKeyguardShowing) {
            boolean z4 = this.mKeyguardShowing;
            if (!z4 || (!this.mQsExpandImmediate && (!this.mIsExpanding || !this.mQsExpandedWhenExpandingStarted))) {
                ValueAnimator valueAnimator = this.mQsSizeChangeAnimator;
                if (valueAnimator != null) {
                    i = Math.max(((Integer) valueAnimator.getAnimatedValue()).intValue(), getKeyguardNotificationStaticPadding());
                } else if (z4) {
                    f = MathUtils.lerp((float) getKeyguardNotificationStaticPadding(), (float) this.mQsMaxExpansionHeight, computeQsExpansionFraction());
                } else {
                    f = this.mQsFrameTranslateController.getNotificationsTopPadding(this.mQsExpansionHeight);
                }
            } else {
                int keyguardNotificationStaticPadding = getKeyguardNotificationStaticPadding();
                int i2 = this.mQsMaxExpansionHeight;
                if (this.mBarState == 1) {
                    i2 = Math.max(keyguardNotificationStaticPadding, i2);
                }
                i = (int) MathUtils.lerp((float) this.mQsMinExpansionHeight, (float) i2, this.mExpandedFraction);
            }
            f = (float) i;
        } else {
            f = 0.0f;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        int i3 = (int) f;
        int layoutMinHeight = notificationStackScrollLayout.getLayoutMinHeight() + i3;
        if (layoutMinHeight > notificationStackScrollLayout.getHeight()) {
            notificationStackScrollLayout.mTopPaddingOverflow = (float) (layoutMinHeight - notificationStackScrollLayout.getHeight());
        } else {
            notificationStackScrollLayout.mTopPaddingOverflow = 0.0f;
        }
        if (!z || notificationStackScrollLayout.mKeyguardBypassEnabled) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (notificationStackScrollLayout.mTopPadding != i3) {
            if (z2 || notificationStackScrollLayout.mAnimateNextTopPaddingChange) {
                z3 = true;
            } else {
                z3 = false;
            }
            notificationStackScrollLayout.mTopPadding = i3;
            notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
            notificationStackScrollLayout.updateContentHeight();
            if (z3 && notificationStackScrollLayout.mAnimationsEnabled && notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.mTopPaddingNeedsAnimation = true;
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener((ExpandableView) null, z3);
            notificationStackScrollLayout.mAnimateNextTopPaddingChange = false;
        }
        notificationStackScrollLayout.setExpandedHeight(notificationStackScrollLayout.mExpandedHeight);
        if (this.mKeyguardShowing && this.mKeyguardBypassController.getBypassEnabled()) {
            updateQsExpansion$1();
        }
    }

    public final void setDozing$1(boolean z, boolean z2) {
        float f;
        if (z != this.mDozing) {
            NotificationPanelView notificationPanelView = this.mView;
            Objects.requireNonNull(notificationPanelView);
            notificationPanelView.mDozing = z;
            this.mDozing = z;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState);
            if (ambientState.mDozing != z) {
                AmbientState ambientState2 = notificationStackScrollLayout.mAmbientState;
                Objects.requireNonNull(ambientState2);
                ambientState2.mDozing = z;
                notificationStackScrollLayout.requestChildrenUpdate();
                notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
            }
            this.mKeyguardBottomArea.setDozing$2(this.mDozing, z2);
            KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
            boolean z3 = this.mDozing;
            Objects.requireNonNull(keyguardStatusBarViewController);
            keyguardStatusBarViewController.mDozing = z3;
            if (z) {
                this.mBottomAreaShadeAlphaAnimator.cancel();
            }
            int i = this.mBarState;
            if (i == 1 || i == 2) {
                this.mKeyguardBottomArea.setDozing$2(this.mDozing, z2);
                if (!this.mDozing && z2) {
                    this.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
                }
            }
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            this.mStatusBarStateController.setAndInstrumentDozeAmount(this.mView, f, z2);
        }
    }

    public final void setIsClosing(boolean z) {
        boolean z2 = this.mClosing;
        this.mClosing = z;
        if (z2 != z) {
            Iterator<NotifPanelEventSource.Callbacks> it = this.mNotifEventSourceCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onPanelCollapsingChanged(z);
            }
        }
    }

    public final void setKeyguardBottomAreaVisibility(int i, boolean z) {
        this.mKeyguardBottomArea.animate().cancel();
        if (z) {
            this.mKeyguardBottomArea.animate().alpha(0.0f).setStartDelay(this.mKeyguardStateController.getKeyguardFadingAwayDelay()).setDuration(this.mKeyguardStateController.getShortenedFadingAwayDuration()).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardBottomAreaInvisibleEndRunnable).start();
        } else if (i == 1 || i == 2) {
            this.mKeyguardBottomArea.setVisibility(0);
            this.mKeyguardBottomArea.setAlpha(1.0f);
        } else {
            this.mKeyguardBottomArea.setVisibility(8);
        }
    }

    public final void setLaunchingAffordance(boolean z) {
        this.mLaunchingAffordance = z;
        KeyguardAffordanceView leftIcon = this.mKeyguardAffordanceHelperCallback.getLeftIcon();
        Objects.requireNonNull(leftIcon);
        leftIcon.mLaunchingAffordance = z;
        KeyguardAffordanceView rightIcon = this.mKeyguardAffordanceHelperCallback.getRightIcon();
        Objects.requireNonNull(rightIcon);
        rightIcon.mLaunchingAffordance = z;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        Objects.requireNonNull(keyguardBypassController);
        keyguardBypassController.launchingAffordance = z;
    }

    public final void setListening(boolean z) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        Objects.requireNonNull(keyguardStatusBarViewController);
        if (z != keyguardStatusBarViewController.mBatteryListening) {
            keyguardStatusBarViewController.mBatteryListening = z;
            if (z) {
                keyguardStatusBarViewController.mBatteryController.addCallback(keyguardStatusBarViewController.mBatteryStateChangeCallback);
            } else {
                keyguardStatusBarViewController.mBatteryController.removeCallback(keyguardStatusBarViewController.mBatteryStateChangeCallback);
            }
        }
        C0961QS qs = this.mQs;
        if (qs != null) {
            qs.setListening(z);
        }
    }

    public final void setOverExpansion(float f) {
        if (f != this.mOverExpansion) {
            this.mOverExpansion = f;
            this.mQsFrameTranslateController.translateQsFrame();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState);
            ambientState.mOverExpansion = f;
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
        }
    }

    public final void setPanelAlpha(int i, boolean z) {
        AnimationProperties animationProperties;
        if (this.mPanelAlpha != i) {
            this.mPanelAlpha = i;
            NotificationPanelView notificationPanelView = this.mView;
            AnimatableProperty.C12216 r1 = this.mPanelAlphaAnimator;
            float f = (float) i;
            if (i == 255) {
                animationProperties = this.mPanelAlphaInPropertiesAnimator;
            } else {
                animationProperties = this.mPanelAlphaOutPropertiesAnimator;
            }
            PropertyAnimator.setProperty(notificationPanelView, r1, f, animationProperties, z);
        }
    }

    public final void setPanelScrimMinFraction(float f) {
        this.mMinFraction = f;
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        Objects.requireNonNull(notificationShadeDepthController);
        notificationShadeDepthController.panelPullDownMinFraction = f;
        ScrimController scrimController = this.mScrimController;
        float f2 = this.mMinFraction;
        Objects.requireNonNull(scrimController);
        if (!Float.isNaN(f2)) {
            scrimController.mPanelScrimMinFraction = f2;
            scrimController.calculateAndUpdatePanelExpansion();
            return;
        }
        throw new IllegalArgumentException("minFraction should not be NaN");
    }

    @VisibleForTesting
    public void setQsExpanded(boolean z) {
        boolean z2;
        int i;
        Object obj;
        boolean z3 = z;
        boolean z4 = true;
        if (this.mQsExpanded != z3) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.mQsExpanded = z3;
            updateQsState();
            requestPanelHeightUpdate();
            this.mFalsingCollector.setQsExpanded(z3);
            StatusBar statusBar = this.mStatusBar;
            Objects.requireNonNull(statusBar);
            statusBar.mNotificationShadeWindowController.setQsExpanded(z3);
            NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
            if (z3) {
                i = 4;
            } else {
                i = 0;
            }
            Objects.requireNonNull(notificationPanelViewController);
            KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
            Objects.requireNonNull(keyguardStatusViewController);
            ((KeyguardStatusView) keyguardStatusViewController.mView).setImportantForAccessibility(i);
            statusBar.mNotificationPanelViewController.updateSystemUiStateFlags();
            if (statusBar.getNavigationBarView() != null) {
                NavigationBarView navigationBarView = statusBar.getNavigationBarView();
                Objects.requireNonNull(navigationBarView);
                navigationBarView.updateSlippery();
            }
            NotificationsQSContainerController notificationsQSContainerController = this.mNotificationsQSContainerController;
            Objects.requireNonNull(notificationsQSContainerController);
            if (notificationsQSContainerController.qsExpanded != z3) {
                notificationsQSContainerController.qsExpanded = z3;
                ((NotificationsQuickSettingsContainer) notificationsQSContainerController.mView).invalidate();
            }
            PulseExpansionHandler pulseExpansionHandler = this.mPulseExpansionHandler;
            Objects.requireNonNull(pulseExpansionHandler);
            pulseExpansionHandler.qsExpanded = z3;
            KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
            Objects.requireNonNull(keyguardBypassController);
            if (keyguardBypassController.qSExpanded == z3) {
                z4 = false;
            }
            keyguardBypassController.qSExpanded = z3;
            if (z4 && !z3) {
                keyguardBypassController.maybePerformPendingUnlock();
            }
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            statusBarKeyguardViewManager.mQsExpanded = z3;
            StatusBarKeyguardViewManager.AlternateAuthInterceptor alternateAuthInterceptor = statusBarKeyguardViewManager.mAlternateAuthInterceptor;
            if (alternateAuthInterceptor != null) {
                UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
                udfpsKeyguardViewController.mQsExpanded = z3;
                udfpsKeyguardViewController.updatePauseAuth();
            }
            LockIconViewController lockIconViewController = this.mLockIconViewController;
            Objects.requireNonNull(lockIconViewController);
            lockIconViewController.mQsExpanded = z3;
            lockIconViewController.updateVisibility();
            PrivacyDotViewController privacyDotViewController = this.mPrivacyDotViewController;
            Objects.requireNonNull(privacyDotViewController);
            Intrinsics.stringPlus("setQsExpanded ", Boolean.valueOf(z));
            Object obj2 = privacyDotViewController.lock;
            synchronized (obj2) {
                try {
                    obj = obj2;
                    try {
                        privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, z, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, (String) null, 16375));
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                    throw th;
                }
            }
        }
    }

    public final void setQsExpansion(float f) {
        boolean z;
        float min = Math.min(Math.max(f, (float) this.mQsMinExpansionHeight), (float) this.mQsMaxExpansionHeight);
        int i = this.mQsMaxExpansionHeight;
        if (min != ((float) i) || i == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mQsFullyExpanded = z;
        float f2 = (float) this.mQsMinExpansionHeight;
        if (min > f2 && !this.mQsExpanded && !this.mStackScrollerOverscrolling && !this.mDozing) {
            setQsExpanded(true);
        } else if (min <= f2 && this.mQsExpanded) {
            setQsExpanded(false);
        }
        this.mQsExpansionHeight = min;
        updateQsExpansion$1();
        requestScrollerTopPaddingUpdate(false);
        this.mKeyguardStatusBarViewController.updateViewState();
        int i2 = this.mBarState;
        if (i2 == 2 || i2 == 1) {
            updateKeyguardBottomAreaAlpha();
            positionClockAndNotifications(false);
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mView.setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        }
        if (!this.mFalsingManager.isUnlockingDisabled() && this.mQsFullyExpanded) {
            this.mFalsingCollector.shouldEnforceBouncer();
        }
    }

    public final boolean touchXOutsideOfQs(float f) {
        if (f < this.mQsFrame.getX() || f > this.mQsFrame.getX() + ((float) this.mQsFrame.getWidth())) {
            return true;
        }
        return false;
    }

    public final void traceQsJank(boolean z, boolean z2) {
        InteractionJankMonitor interactionJankMonitor = this.mInteractionJankMonitor;
        if (interactionJankMonitor != null) {
            if (z) {
                interactionJankMonitor.begin(this.mView, 5);
            } else if (z2) {
                interactionJankMonitor.cancel(5);
            } else {
                interactionJankMonitor.end(5);
            }
        }
    }

    public final void trackMovement(MotionEvent motionEvent) {
        VelocityTracker velocityTracker = this.mQsVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
    }

    public final void unregisterCallbacks(NotifPanelEventSource.Callbacks callbacks) {
        this.mNotifEventSourceCallbacks.remove(callbacks);
    }

    public final void updateClock() {
        float f = this.mClockPositionResult.clockAlpha * this.mKeyguardOnlyContentAlpha;
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        Objects.requireNonNull(keyguardStatusViewController);
        KeyguardVisibilityHelper keyguardVisibilityHelper = keyguardStatusViewController.mKeyguardVisibilityHelper;
        Objects.requireNonNull(keyguardVisibilityHelper);
        if (!keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            KeyguardUnlockAnimationController keyguardUnlockAnimationController = keyguardStatusViewController.mKeyguardUnlockAnimationController;
            Objects.requireNonNull(keyguardUnlockAnimationController);
            if (keyguardUnlockAnimationController.unlockingWithSmartspaceTransition) {
                ((KeyguardStatusView) keyguardStatusViewController.mView).setChildrenAlphaExcludingClockView(f);
                keyguardStatusViewController.mKeyguardClockSwitchController.setChildrenAlphaExcludingSmartspace(f);
            } else {
                KeyguardVisibilityHelper keyguardVisibilityHelper2 = keyguardStatusViewController.mKeyguardVisibilityHelper;
                Objects.requireNonNull(keyguardVisibilityHelper2);
                if (!keyguardVisibilityHelper2.mKeyguardViewVisibilityAnimating) {
                    ((KeyguardStatusView) keyguardStatusViewController.mView).setAlpha(f);
                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController.mView;
                    Objects.requireNonNull(keyguardStatusView);
                    if (keyguardStatusView.mChildrenAlphaExcludingSmartSpace < 1.0f) {
                        ((KeyguardStatusView) keyguardStatusViewController.mView).setChildrenAlphaExcludingClockView(1.0f);
                        keyguardStatusViewController.mKeyguardClockSwitchController.setChildrenAlphaExcludingSmartspace(1.0f);
                    }
                }
            }
        }
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            Objects.requireNonNull(keyguardQsUserSwitchController);
            KeyguardVisibilityHelper keyguardVisibilityHelper3 = keyguardQsUserSwitchController.mKeyguardVisibilityHelper;
            Objects.requireNonNull(keyguardVisibilityHelper3);
            if (!keyguardVisibilityHelper3.mKeyguardViewVisibilityAnimating) {
                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(f);
            }
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            Objects.requireNonNull(keyguardUserSwitcherController);
            KeyguardVisibilityHelper keyguardVisibilityHelper4 = keyguardUserSwitcherController.mKeyguardVisibilityHelper;
            Objects.requireNonNull(keyguardVisibilityHelper4);
            if (!keyguardVisibilityHelper4.mKeyguardViewVisibilityAnimating) {
                ((KeyguardUserSwitcherView) keyguardUserSwitcherController.mView).setAlpha(f);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0080, code lost:
        if (r6 < r2) goto L_0x0084;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateExpandedHeight(float r6) {
        /*
            r5 = this;
            boolean r0 = r5.mTracking
            if (r0 == 0) goto L_0x0022
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r5.mNotificationStackScrollLayoutController
            android.view.VelocityTracker r1 = r5.mVelocityTracker
            r2 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r2)
            android.view.VelocityTracker r1 = r5.mVelocityTracker
            float r1 = r1.getYVelocity()
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.AmbientState r0 = r0.mAmbientState
            java.util.Objects.requireNonNull(r0)
            r0.mExpandingVelocity = r1
        L_0x0022:
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r5.mKeyguardBypassController
            boolean r0 = r0.getBypassEnabled()
            if (r0 == 0) goto L_0x0035
            boolean r0 = r5.isOnKeyguard()
            if (r0 == 0) goto L_0x0035
            int r6 = r5.getMaxPanelHeight()
            float r6 = (float) r6
        L_0x0035:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r5.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            r0.setExpandedHeight(r6)
            r5.updateKeyguardBottomAreaAlpha()
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r6 = r5.mHeadsUpManager
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mHasPinnedNotification
            r0 = 1
            r1 = 0
            if (r6 != 0) goto L_0x0051
            boolean r6 = r5.mHeadsUpAnimatingAway
            if (r6 == 0) goto L_0x0057
        L_0x0051:
            int r6 = r5.mBarState
            if (r6 != 0) goto L_0x0057
            r6 = r0
            goto L_0x0058
        L_0x0057:
            r6 = r1
        L_0x0058:
            if (r6 != 0) goto L_0x005e
            boolean r6 = r5.mIsFullWidth
            if (r6 == 0) goto L_0x0083
        L_0x005e:
            float r6 = r5.mExpandedHeight
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = r5.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r2.mView
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.EmptyShadeView r3 = r2.mEmptyShadeView
            int r3 = r3.getVisibility()
            r4 = 8
            if (r3 != r4) goto L_0x007a
            int r2 = r2.getMinExpansionHeight()
            float r2 = (float) r2
            goto L_0x007e
        L_0x007a:
            float r2 = r2.getAppearEndPosition()
        L_0x007e:
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x0083
            goto L_0x0084
        L_0x0083:
            r0 = r1
        L_0x0084:
            if (r0 == 0) goto L_0x008d
            boolean r6 = r5.isOnKeyguard()
            if (r6 == 0) goto L_0x008d
            r0 = r1
        L_0x008d:
            boolean r6 = r5.mShowIconsWhenExpanded
            if (r0 == r6) goto L_0x009a
            r5.mShowIconsWhenExpanded = r0
            com.android.systemui.statusbar.CommandQueue r6 = r5.mCommandQueue
            int r5 = r5.mDisplayId
            r6.recomputeDisableFlags(r5, r1)
        L_0x009a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.updateExpandedHeight(float):void");
    }

    public final void updateGestureExclusionRect() {
        Rect rect;
        List list;
        Region calculateTouchableRegion = this.mStatusBarTouchableRegionManager.calculateTouchableRegion();
        if (!isFullyCollapsed() || calculateTouchableRegion == null) {
            rect = null;
        } else {
            rect = calculateTouchableRegion.getBounds();
        }
        if (rect == null) {
            rect = EMPTY_RECT;
        }
        NotificationPanelView notificationPanelView = this.mView;
        if (rect.isEmpty()) {
            list = Collections.EMPTY_LIST;
        } else {
            list = Collections.singletonList(rect);
        }
        notificationPanelView.setSystemGestureExclusionRects(list);
    }

    public final void updateKeyguardBottomAreaAlpha() {
        float f;
        int i;
        if (this.mHintAnimationRunning) {
            f = 0.0f;
        } else {
            f = 0.95f;
        }
        float min = Math.min(MathUtils.map(f, 1.0f, 0.0f, 1.0f, this.mExpandedFraction), 1.0f - computeQsExpansionFraction()) * this.mBottomAreaShadeAlpha;
        this.mKeyguardBottomArea.setAffordanceAlpha(min);
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        if (min == 0.0f) {
            i = 4;
        } else {
            i = 0;
        }
        keyguardBottomAreaView.setImportantForAccessibility(i);
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        View view = statusBar.mAmbientIndicationContainer;
        if (view != null) {
            view.setAlpha(min);
        }
        LockIconViewController lockIconViewController = this.mLockIconViewController;
        Objects.requireNonNull(lockIconViewController);
        ((LockIconView) lockIconViewController.mView).setAlpha(min);
    }

    public final void updateKeyguardStatusViewAlignment(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        int i = 0;
        if (this.mNotificationStackScrollLayoutController.getVisibleNotificationCount() != 0 || this.mMediaDataManager.hasActiveMedia()) {
            z2 = true;
        } else {
            z2 = false;
        }
        WeakReference<CommunalSource> weakReference = this.mCommunalSource;
        if (weakReference == null || weakReference.get() == null) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (!this.mShouldUseSplitNotificationShade || ((!z2 && !z3) || this.mDozing)) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (this.mStatusViewCentered != z4) {
            this.mStatusViewCentered = z4;
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.mNotificationContainerParent);
            if (!z4) {
                i = C1777R.C1779id.qs_edge_guideline;
            }
            constraintSet.connect(C1777R.C1779id.keyguard_status_view, 7, i, 7);
            if (z) {
                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                changeBounds.setDuration(360);
                TransitionManager.beginDelayedTransition(this.mNotificationContainerParent, changeBounds);
            }
            constraintSet.applyTo(this.mNotificationContainerParent);
        }
        this.mKeyguardUnfoldTransition.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda3(this, 1));
    }

    public final void updateNotificationTranslucency() {
        float f;
        if (this.mClosingWithAlphaFadeOut && !this.mExpandingFromHeadsUp) {
            HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
            Objects.requireNonNull(headsUpManagerPhone);
            if (!headsUpManagerPhone.mHasPinnedNotification) {
                f = getFadeoutAlpha();
                if (this.mBarState == 1 && !this.mHintAnimationRunning && !this.mKeyguardBypassController.getBypassEnabled()) {
                    f *= this.mClockPositionResult.clockAlpha;
                }
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationStackScrollLayoutController.mView.setAlpha(f);
            }
        }
        f = 1.0f;
        f *= this.mClockPositionResult.clockAlpha;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        notificationStackScrollLayoutController2.mView.setAlpha(f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateQSExpansionEnabledAmbient() {
        /*
            r2 = this;
            com.android.systemui.statusbar.notification.stack.AmbientState r0 = r2.mAmbientState
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mTopPadding
            float r0 = (float) r0
            float r1 = r2.mQuickQsOffsetHeight
            float r0 = r0 - r1
            boolean r1 = r2.mShouldUseSplitNotificationShade
            if (r1 != 0) goto L_0x001e
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r2.mAmbientState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mScrollY
            float r1 = (float) r1
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x001c
            goto L_0x001e
        L_0x001c:
            r0 = 0
            goto L_0x001f
        L_0x001e:
            r0 = 1
        L_0x001f:
            r2.mQsExpansionEnabledAmbient = r0
            com.android.systemui.plugins.qs.QS r0 = r2.mQs
            if (r0 != 0) goto L_0x0026
            goto L_0x002d
        L_0x0026:
            boolean r2 = r2.isQsExpansionEnabled()
            r0.setHeaderClickable(r2)
        L_0x002d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.updateQSExpansionEnabledAmbient():void");
    }

    public final void updateQsExpansion$1() {
        float f;
        float f2;
        boolean z;
        float f3;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (this.mQs != null) {
            if (this.mQsExpandImmediate || this.mQsExpanded) {
                f = 1.0f;
            } else {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
                Objects.requireNonNull(lockscreenShadeTransitionController);
                if (lockscreenShadeTransitionController.qSDragProgress > 0.0f) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.mLockscreenShadeTransitionController;
                    Objects.requireNonNull(lockscreenShadeTransitionController2);
                    f = lockscreenShadeTransitionController2.qSDragProgress;
                } else {
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    Objects.requireNonNull(notificationStackScrollLayout);
                    StackScrollAlgorithm stackScrollAlgorithm = notificationStackScrollLayout.mStackScrollAlgorithm;
                    AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
                    Objects.requireNonNull(stackScrollAlgorithm);
                    f = stackScrollAlgorithm.getExpansionFractionWithoutShelf(stackScrollAlgorithm.mTempAlgorithmState, ambientState);
                }
            }
            float computeQsExpansionFraction = computeQsExpansionFraction();
            if (this.mShouldUseSplitNotificationShade) {
                f2 = 1.0f;
            } else {
                f2 = computeQsExpansionFraction();
            }
            this.mQs.setQsExpansion(f2, this.mExpandedFraction, getHeaderTranslation(), f);
            MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
            Objects.requireNonNull(mediaHierarchyManager);
            boolean z7 = true;
            if (mediaHierarchyManager.qsExpansion == computeQsExpansionFraction) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                mediaHierarchyManager.qsExpansion = computeQsExpansionFraction;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                if (mediaHierarchyManager.getQSTransformationProgress() >= 0.0f) {
                    mediaHierarchyManager.updateTargetState();
                    mediaHierarchyManager.applyTargetStateIfNotAnimating();
                }
            }
            int calculateQsBottomPosition = calculateQsBottomPosition(computeQsExpansionFraction);
            ScrimController scrimController = this.mScrimController;
            Objects.requireNonNull(scrimController);
            if (!Float.isNaN(computeQsExpansionFraction)) {
                float notificationScrimAlpha = ShadeInterpolation.getNotificationScrimAlpha(computeQsExpansionFraction);
                if (calculateQsBottomPosition > 0) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                if (!(scrimController.mQsExpansion == notificationScrimAlpha && scrimController.mQsBottomVisible == z5)) {
                    scrimController.mQsExpansion = notificationScrimAlpha;
                    scrimController.mQsBottomVisible = z5;
                    ScrimState scrimState = scrimController.mState;
                    if (scrimState == ScrimState.SHADE_LOCKED || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.PULSING) {
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                    if (z6 && scrimController.mExpansionAffectsAlpha) {
                        scrimController.applyAndDispatchState();
                    }
                }
            }
            setQSClippingBounds();
            if (!this.mShouldUseSplitNotificationShade) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController2);
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                Objects.requireNonNull(notificationStackScrollLayout2);
                float f4 = notificationStackScrollLayout2.mQsExpansionFraction;
                if (f4 == computeQsExpansionFraction || !(f4 == 1.0f || computeQsExpansionFraction == 1.0f)) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                notificationStackScrollLayout2.mQsExpansionFraction = computeQsExpansionFraction;
                notificationStackScrollLayout2.updateUseRoundedRectClipping();
                int i = notificationStackScrollLayout2.mOwnScrollY;
                if (i > 0) {
                    notificationStackScrollLayout2.setOwnScrollY((int) MathUtils.lerp(i, 0, notificationStackScrollLayout2.mQsExpansionFraction), false);
                }
                if (z4) {
                    notificationStackScrollLayout2.updateFooter();
                }
            }
            NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
            Objects.requireNonNull(notificationShadeDepthController);
            if (Float.isNaN(computeQsExpansionFraction)) {
                Log.w("DepthController", "Invalid qs expansion");
            } else {
                if (notificationShadeDepthController.qsPanelExpansion == computeQsExpansionFraction) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    notificationShadeDepthController.qsPanelExpansion = computeQsExpansionFraction;
                    notificationShadeDepthController.scheduleUpdate((View) null);
                }
            }
            if (this.mTransitioningToFullShadeProgress > 0.0f) {
                LockscreenShadeTransitionController lockscreenShadeTransitionController3 = this.mLockscreenShadeTransitionController;
                Objects.requireNonNull(lockscreenShadeTransitionController3);
                f3 = lockscreenShadeTransitionController3.qSDragProgress;
            } else {
                f3 = this.mExpandedFraction;
            }
            SplitShadeHeaderController splitShadeHeaderController = this.mSplitShadeHeaderController;
            Objects.requireNonNull(splitShadeHeaderController);
            if (splitShadeHeaderController.visible) {
                if (splitShadeHeaderController.shadeExpandedFraction == f3) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    splitShadeHeaderController.statusBar.setAlpha(ShadeInterpolation.getContentAlpha(f3));
                    splitShadeHeaderController.shadeExpandedFraction = f3;
                }
            }
            SplitShadeHeaderController splitShadeHeaderController2 = this.mSplitShadeHeaderController;
            Objects.requireNonNull(splitShadeHeaderController2);
            if (splitShadeHeaderController2.visible) {
                if (splitShadeHeaderController2.qsExpandedFraction != computeQsExpansionFraction) {
                    z7 = false;
                }
                if (!z7) {
                    splitShadeHeaderController2.qsExpandedFraction = computeQsExpansionFraction;
                    splitShadeHeaderController2.updateVisibility();
                    splitShadeHeaderController2.updatePosition$3();
                }
            }
            SplitShadeHeaderController splitShadeHeaderController3 = this.mSplitShadeHeaderController;
            boolean z8 = this.mQsVisible;
            Objects.requireNonNull(splitShadeHeaderController3);
            if (splitShadeHeaderController3.shadeExpanded != z8) {
                splitShadeHeaderController3.shadeExpanded = z8;
                if (z8) {
                    splitShadeHeaderController3.privacyIconsController.startListening();
                } else {
                    HeaderPrivacyIconsController headerPrivacyIconsController = splitShadeHeaderController3.privacyIconsController;
                    Objects.requireNonNull(headerPrivacyIconsController);
                    headerPrivacyIconsController.listening = false;
                    headerPrivacyIconsController.privacyItemController.removeCallback(headerPrivacyIconsController.picCallback);
                    headerPrivacyIconsController.privacyChipLogged = false;
                }
                splitShadeHeaderController3.updateVisibility();
                splitShadeHeaderController3.updatePosition$3();
            }
            CommunalHostViewController communalHostViewController = this.mCommunalViewController;
            if (communalHostViewController != null) {
                communalHostViewController.mQsExpansion = computeQsExpansionFraction;
                communalHostViewController.updateCommunalViewOccluded();
            }
        }
    }

    public final void updateQsState() {
        boolean z;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        boolean z2 = this.mQsExpanded;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mQsExpanded = z2;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
        boolean z3 = false;
        if (notificationStackScrollLayout.mQsExpanded || notificationStackScrollLayout.getScrollRange() <= 0) {
            z = false;
        } else {
            z = true;
        }
        if (z != notificationStackScrollLayout.mScrollable) {
            notificationStackScrollLayout.mScrollable = z;
            notificationStackScrollLayout.setFocusable(z);
            notificationStackScrollLayout.updateForwardAndBackwardScrollability();
        }
        notificationStackScrollLayoutController.updateShowEmptyShadeView();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        if (this.mBarState != 1 && (!this.mQsExpanded || this.mQsExpansionFromOverscroll || this.mShouldUseSplitNotificationShade)) {
            z3 = true;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        notificationStackScrollLayout2.mScrollingEnabled = z3;
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null && this.mQsExpanded && !this.mStackScrollerOverscrolling) {
            keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
        }
        C0961QS qs = this.mQs;
        if (qs != null) {
            qs.setExpanded(this.mQsExpanded);
        }
    }

    public final void updateResources() {
        int i;
        this.mQuickQsOffsetHeight = (float) SystemBarUtils.getQuickQsOffsetHeight(this.mView.getContext());
        this.mSplitShadeStatusBarHeight = SystemBarUtils.getQuickQsOffsetHeight(this.mView.getContext());
        this.mSplitShadeNotificationsScrimMarginBottom = this.mResources.getDimensionPixelSize(C1777R.dimen.split_shade_notifications_scrim_margin_bottom);
        int dimensionPixelSize = this.mResources.getDimensionPixelSize(C1777R.dimen.qs_panel_width);
        int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(C1777R.dimen.notification_panel_width);
        boolean shouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(this.mResources);
        this.mShouldUseSplitNotificationShade = shouldUseSplitNotificationShade;
        C0961QS qs = this.mQs;
        if (qs != null) {
            qs.setInSplitShade(shouldUseSplitNotificationShade);
        }
        int dimensionPixelSize3 = this.mResources.getDimensionPixelSize(C1777R.dimen.notification_panel_margin_bottom);
        if (this.mShouldUseSplitNotificationShade) {
            i = this.mSplitShadeStatusBarHeight;
        } else {
            i = this.mResources.getDimensionPixelSize(C1777R.dimen.notification_panel_margin_top);
        }
        SplitShadeHeaderController splitShadeHeaderController = this.mSplitShadeHeaderController;
        boolean z = this.mShouldUseSplitNotificationShade;
        Objects.requireNonNull(splitShadeHeaderController);
        if (splitShadeHeaderController.splitShadeMode != z) {
            splitShadeHeaderController.splitShadeMode = z;
            if (z || splitShadeHeaderController.combinedHeaders) {
                splitShadeHeaderController.privacyIconsController.onParentVisible();
            } else {
                HeaderPrivacyIconsController headerPrivacyIconsController = splitShadeHeaderController.privacyIconsController;
                Objects.requireNonNull(headerPrivacyIconsController);
                headerPrivacyIconsController.chipVisibilityListener = null;
                headerPrivacyIconsController.privacyChip.setOnClickListener((View.OnClickListener) null);
            }
            splitShadeHeaderController.updateVisibility();
            splitShadeHeaderController.updateConstraints();
        }
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = this.mNotificationContainerParent;
        for (int i2 = 0; i2 < notificationsQuickSettingsContainer.getChildCount(); i2++) {
            View childAt = notificationsQuickSettingsContainer.getChildAt(i2);
            if (childAt.getId() == -1) {
                childAt.setId(View.generateViewId());
            }
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mNotificationContainerParent);
        if (this.mShouldUseSplitNotificationShade) {
            constraintSet.connect(C1777R.C1779id.qs_frame, 7, C1777R.C1779id.qs_edge_guideline, 7);
            constraintSet.connect(C1777R.C1779id.notification_stack_scroller, 6, C1777R.C1779id.qs_edge_guideline, 6);
            constraintSet.get(C1777R.C1779id.split_shade_status_bar).layout.mHeight = this.mSplitShadeStatusBarHeight;
            dimensionPixelSize = 0;
            dimensionPixelSize2 = 0;
        } else {
            constraintSet.connect(C1777R.C1779id.qs_frame, 7, 0, 7);
            constraintSet.connect(C1777R.C1779id.notification_stack_scroller, 6, 0, 6);
            if (this.mUseCombinedQSHeaders) {
                constraintSet.get(C1777R.C1779id.split_shade_status_bar).layout.mHeight = -2;
            }
        }
        constraintSet.getConstraint(C1777R.C1779id.notification_stack_scroller).layout.mWidth = dimensionPixelSize2;
        constraintSet.getConstraint(C1777R.C1779id.qs_frame).layout.mWidth = dimensionPixelSize;
        constraintSet.setMargin(C1777R.C1779id.notification_stack_scroller, 3, i);
        constraintSet.setMargin(C1777R.C1779id.notification_stack_scroller, 4, dimensionPixelSize3);
        constraintSet.setMargin(C1777R.C1779id.qs_frame, 3, i);
        constraintSet.applyTo(this.mNotificationContainerParent);
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mStackTopMargin = i;
        this.mNotificationsQSContainerController.updateMargins$2();
        NotificationsQSContainerController notificationsQSContainerController = this.mNotificationsQSContainerController;
        boolean z2 = this.mShouldUseSplitNotificationShade;
        Objects.requireNonNull(notificationsQSContainerController);
        if (notificationsQSContainerController.splitShadeEnabled != z2) {
            notificationsQSContainerController.splitShadeEnabled = z2;
            notificationsQSContainerController.updateBottomSpacing();
        }
        updateKeyguardStatusViewAlignment(false);
        this.mKeyguardMediaController.refreshMediaPosition();
    }

    public final void updateSystemUiStateFlags() {
        boolean z;
        SysUiState sysUiState = this.mSysUiState;
        if (!isFullyExpanded() || this.mQsExpanded) {
            z = false;
        } else {
            z = true;
        }
        sysUiState.setFlag(4, z);
        sysUiState.setFlag(2048, this.mQsExpanded);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    public final void updateUserSwitcherFlags() {
        boolean z;
        boolean z2 = this.mResources.getBoolean(17891680);
        this.mKeyguardUserSwitcherEnabled = z2;
        if (!z2 || !this.mFeatureFlags.isEnabled(Flags.QS_USER_DETAIL_SHORTCUT)) {
            z = false;
        } else {
            z = true;
        }
        this.mKeyguardQsUserSwitchEnabled = z;
    }

    public final void updateViewControllers(KeyguardStatusView keyguardStatusView, FrameLayout frameLayout, KeyguardUserSwitcherView keyguardUserSwitcherView) {
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewComponentFactory.build(keyguardStatusView).getKeyguardStatusViewController();
        this.mKeyguardStatusViewController = keyguardStatusViewController;
        keyguardStatusViewController.init();
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
        }
        this.mKeyguardQsUserSwitchController = null;
        this.mKeyguardUserSwitcherController = null;
        if (frameLayout != null) {
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchComponentFactory.build(frameLayout).getKeyguardQsUserSwitchController();
            this.mKeyguardQsUserSwitchController = keyguardQsUserSwitchController;
            keyguardQsUserSwitchController.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else if (keyguardUserSwitcherView != null) {
            KeyguardUserSwitcherController keyguardUserSwitcherController2 = this.mKeyguardUserSwitcherComponentFactory.build(keyguardUserSwitcherView).getKeyguardUserSwitcherController();
            this.mKeyguardUserSwitcherController = keyguardUserSwitcherController2;
            keyguardUserSwitcherController2.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else {
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0088  */
    /* renamed from: -$$Nest$minitDownStates  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m239$$Nest$minitDownStates(com.android.systemui.statusbar.phone.NotificationPanelViewController r6, android.view.MotionEvent r7) {
        /*
            java.util.Objects.requireNonNull(r6)
            int r0 = r7.getActionMasked()
            r1 = 0
            if (r0 != 0) goto L_0x008b
            r6.mOnlyAffordanceInThisMotion = r1
            boolean r0 = r6.mQsFullyExpanded
            r6.mQsTouchAboveFalsingThreshold = r0
            boolean r0 = r6.mDozing
            r6.mDozingOnDown = r0
            float r0 = r7.getX()
            r6.mDownX = r0
            float r7 = r7.getY()
            r6.mDownY = r7
            boolean r7 = r6.isFullyCollapsed()
            r6.mCollapsedOnDown = r7
            float r0 = r6.mDownX
            float r2 = r6.mDownY
            r3 = 1
            if (r7 != 0) goto L_0x0068
            boolean r7 = r6.mKeyguardShowing
            if (r7 != 0) goto L_0x0068
            boolean r7 = r6.mQsExpanded
            if (r7 == 0) goto L_0x0036
            goto L_0x0068
        L_0x0036:
            com.android.systemui.plugins.qs.QS r7 = r6.mQs
            if (r7 != 0) goto L_0x003d
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r7 = r6.mKeyguardStatusBar
            goto L_0x0041
        L_0x003d:
            android.view.View r7 = r7.getHeader()
        L_0x0041:
            android.widget.FrameLayout r4 = r6.mQsFrame
            float r4 = r4.getX()
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 < 0) goto L_0x0068
            android.widget.FrameLayout r4 = r6.mQsFrame
            float r4 = r4.getX()
            android.widget.FrameLayout r5 = r6.mQsFrame
            int r5 = r5.getWidth()
            float r5 = (float) r5
            float r4 = r4 + r5
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x0068
            int r7 = r7.getBottom()
            float r7 = (float) r7
            int r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r7 > 0) goto L_0x0068
            r7 = r3
            goto L_0x0069
        L_0x0068:
            r7 = r1
        L_0x0069:
            r6.mIsPanelCollapseOnQQS = r7
            boolean r7 = r6.mCollapsedOnDown
            if (r7 == 0) goto L_0x007a
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r7 = r6.mHeadsUpManager
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mHasPinnedNotification
            if (r7 == 0) goto L_0x007a
            r7 = r3
            goto L_0x007b
        L_0x007a:
            r7 = r1
        L_0x007b:
            r6.mListenForHeadsUp = r7
            boolean r7 = r6.mExpectingSynthesizedDown
            r6.mAllowExpandForSmallExpansion = r7
            r6.mTouchSlopExceededBeforeDown = r7
            if (r7 == 0) goto L_0x0088
            r6.mLastEventSynthesizedDown = r3
            goto L_0x008d
        L_0x0088:
            r6.mLastEventSynthesizedDown = r1
            goto L_0x008d
        L_0x008b:
            r6.mLastEventSynthesizedDown = r1
        L_0x008d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.m239$$Nest$minitDownStates(com.android.systemui.statusbar.phone.NotificationPanelViewController, android.view.MotionEvent):void");
    }

    /* renamed from: -$$Nest$mreInflateViews  reason: not valid java name */
    public static void m240$$Nest$mreInflateViews(NotificationPanelViewController notificationPanelViewController) {
        boolean z;
        boolean z2;
        Objects.requireNonNull(notificationPanelViewController);
        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) notificationPanelViewController.mNotificationContainerParent.findViewById(C1777R.C1779id.keyguard_status_view);
        int indexOfChild = notificationPanelViewController.mNotificationContainerParent.indexOfChild(keyguardStatusView);
        notificationPanelViewController.mNotificationContainerParent.removeView(keyguardStatusView);
        KeyguardStatusView keyguardStatusView2 = (KeyguardStatusView) notificationPanelViewController.mLayoutInflater.inflate(C1777R.layout.keyguard_status_view, notificationPanelViewController.mNotificationContainerParent, false);
        notificationPanelViewController.mNotificationContainerParent.addView(keyguardStatusView2, indexOfChild);
        notificationPanelViewController.mStatusViewCentered = true;
        KeyguardMediaController keyguardMediaController = notificationPanelViewController.mKeyguardMediaController;
        Objects.requireNonNull(keyguardMediaController);
        keyguardMediaController.splitShadeContainer = (FrameLayout) keyguardStatusView2.findViewById(C1777R.C1779id.status_view_media_container);
        keyguardMediaController.reattachHostView();
        keyguardMediaController.refreshMediaPosition();
        notificationPanelViewController.updateResources();
        notificationPanelViewController.updateUserSwitcherFlags();
        boolean isUserSwitcherEnabled = notificationPanelViewController.mUserManager.isUserSwitcherEnabled();
        boolean z3 = notificationPanelViewController.mKeyguardQsUserSwitchEnabled;
        if (!z3 || !isUserSwitcherEnabled) {
            z = false;
        } else {
            z = true;
        }
        if (z3 || !notificationPanelViewController.mKeyguardUserSwitcherEnabled || !isUserSwitcherEnabled) {
            z2 = false;
        } else {
            z2 = true;
        }
        notificationPanelViewController.updateViewControllers((KeyguardStatusView) notificationPanelViewController.mView.findViewById(C1777R.C1779id.keyguard_status_view), (FrameLayout) notificationPanelViewController.reInflateStub(C1777R.C1779id.keyguard_qs_user_switch_view, C1777R.C1779id.keyguard_qs_user_switch_stub, C1777R.layout.keyguard_qs_user_switch, z), (KeyguardUserSwitcherView) notificationPanelViewController.reInflateStub(C1777R.C1779id.keyguard_user_switcher_view, C1777R.C1779id.keyguard_user_switcher_stub, C1777R.layout.keyguard_user_switcher, z2));
        int indexOfChild2 = notificationPanelViewController.mView.indexOfChild(notificationPanelViewController.mKeyguardBottomArea);
        notificationPanelViewController.mView.removeView(notificationPanelViewController.mKeyguardBottomArea);
        KeyguardBottomAreaView keyguardBottomAreaView = notificationPanelViewController.mKeyguardBottomArea;
        KeyguardBottomAreaView keyguardBottomAreaView2 = (KeyguardBottomAreaView) notificationPanelViewController.mLayoutInflater.inflate(C1777R.layout.keyguard_bottom_area, notificationPanelViewController.mView, false);
        notificationPanelViewController.mKeyguardBottomArea = keyguardBottomAreaView2;
        Objects.requireNonNull(keyguardBottomAreaView2);
        keyguardBottomAreaView2.mStatusBar = keyguardBottomAreaView.mStatusBar;
        keyguardBottomAreaView2.updateCameraVisibility();
        if (keyguardBottomAreaView2.mAmbientIndicationArea != null) {
            View findViewById = keyguardBottomAreaView.findViewById(C1777R.C1779id.ambient_indication_container);
            ((ViewGroup) findViewById.getParent()).removeView(findViewById);
            ViewGroup viewGroup = (ViewGroup) keyguardBottomAreaView2.mAmbientIndicationArea.getParent();
            int indexOfChild3 = viewGroup.indexOfChild(keyguardBottomAreaView2.mAmbientIndicationArea);
            viewGroup.removeView(keyguardBottomAreaView2.mAmbientIndicationArea);
            viewGroup.addView(findViewById, indexOfChild3);
            keyguardBottomAreaView2.mAmbientIndicationArea = findViewById;
            keyguardBottomAreaView2.dozeTimeTick();
        }
        KeyguardBottomAreaView keyguardBottomAreaView3 = notificationPanelViewController.mKeyguardBottomArea;
        ViewGroup viewGroup2 = notificationPanelViewController.mPreviewContainer;
        Objects.requireNonNull(keyguardBottomAreaView3);
        keyguardBottomAreaView3.mPreviewContainer = viewGroup2;
        keyguardBottomAreaView3.inflateCameraPreview();
        keyguardBottomAreaView3.updateLeftAffordance();
        notificationPanelViewController.mView.addView(notificationPanelViewController.mKeyguardBottomArea, indexOfChild2);
        notificationPanelViewController.initBottomArea();
        notificationPanelViewController.mKeyguardIndicationController.setIndicationArea(notificationPanelViewController.mKeyguardBottomArea);
        notificationPanelViewController.mStatusBarStateListener.onDozeAmountChanged(notificationPanelViewController.mStatusBarStateController.getDozeAmount(), notificationPanelViewController.mStatusBarStateController.getInterpolatedDozeAmount());
        KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
        int i = notificationPanelViewController.mBarState;
        Objects.requireNonNull(keyguardStatusViewController);
        keyguardStatusViewController.mKeyguardVisibilityHelper.setViewVisibility(i, false, false, i);
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            int i2 = notificationPanelViewController.mBarState;
            keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i2, false, false, i2);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            int i3 = notificationPanelViewController.mBarState;
            keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i3, false, false, i3);
        }
        notificationPanelViewController.setKeyguardBottomAreaVisibility(notificationPanelViewController.mBarState, false);
        notificationPanelViewController.mKeyguardUnfoldTransition.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda0(notificationPanelViewController, 1));
        notificationPanelViewController.mNotificationPanelUnfoldAnimationController.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda1(notificationPanelViewController, 2));
    }

    /* renamed from: -$$Nest$mupdateMaxHeadsUpTranslation  reason: not valid java name */
    public static void m241$$Nest$mupdateMaxHeadsUpTranslation(NotificationPanelViewController notificationPanelViewController) {
        Objects.requireNonNull(notificationPanelViewController);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
        int height = notificationPanelViewController.mView.getHeight();
        int i = notificationPanelViewController.mNavigationBarBottomHeight;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mMaxHeadsUpTranslation = (float) (height - i);
        StackStateAnimator stackStateAnimator = notificationStackScrollLayout.mStateAnimator;
        Objects.requireNonNull(stackStateAnimator);
        stackStateAnimator.mHeadsUpAppearHeightBottom = height;
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    /* renamed from: -$$Nest$mupdateQSMinHeight  reason: not valid java name */
    public static void m242$$Nest$mupdateQSMinHeight(NotificationPanelViewController notificationPanelViewController) {
        int i;
        Objects.requireNonNull(notificationPanelViewController);
        float f = (float) notificationPanelViewController.mQsMinExpansionHeight;
        if (notificationPanelViewController.mKeyguardShowing) {
            i = 0;
        } else {
            i = notificationPanelViewController.mQs.getQsMinExpansionHeight();
        }
        notificationPanelViewController.mQsMinExpansionHeight = i;
        if (notificationPanelViewController.mQsExpansionHeight == f) {
            notificationPanelViewController.mQsExpansionHeight = (float) i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003c A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean canShowViewOnLockscreen(com.android.systemui.statusbar.notification.row.ExpandableView r6) {
        /*
            r5 = this;
            java.util.Objects.requireNonNull(r6)
            boolean r0 = r6 instanceof com.android.systemui.statusbar.NotificationShelf
            r1 = 0
            if (r0 == 0) goto L_0x0009
            return r1
        L_0x0009:
            boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            r2 = 1
            if (r0 == 0) goto L_0x003d
            r0 = r6
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r0
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy r3 = r5.mGroupManager
            if (r3 == 0) goto L_0x0024
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r0.mEntry
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            boolean r3 = r3.isSummaryOfSuppressedGroup(r4)
            if (r3 == 0) goto L_0x0024
            r3 = r2
            goto L_0x0025
        L_0x0024:
            r3 = r1
        L_0x0025:
            if (r3 == 0) goto L_0x0029
        L_0x0027:
            r5 = r1
            goto L_0x003a
        L_0x0029:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r5 = r5.mLockscreenUserManager
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r0.mEntry
            boolean r5 = r5.shouldShowOnKeyguard(r3)
            if (r5 != 0) goto L_0x0034
            goto L_0x0027
        L_0x0034:
            boolean r5 = r0.mRemoved
            if (r5 == 0) goto L_0x0039
            goto L_0x0027
        L_0x0039:
            r5 = r2
        L_0x003a:
            if (r5 != 0) goto L_0x003d
            return r1
        L_0x003d:
            int r5 = r6.getVisibility()
            r6 = 8
            if (r5 != r6) goto L_0x0046
            return r1
        L_0x0046:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.canShowViewOnLockscreen(com.android.systemui.statusbar.notification.row.ExpandableView):boolean");
    }

    public final void collapse(boolean z, float f) {
        if (canPanelBeCollapsed()) {
            if (this.mQsExpanded) {
                this.mQsExpandImmediate = true;
                this.mNotificationStackScrollLayoutController.setShouldShowShelfOnly(true);
            }
            if (canPanelBeCollapsed()) {
                cancelHeightAnimator();
                notifyExpandingStarted();
                setIsClosing(true);
                if (z) {
                    this.mNextCollapseSpeedUpFactor = f;
                    this.mView.postDelayed(this.mFlingCollapseRunnable, 120);
                    return;
                }
                fling(0.0f, false, f, false);
            }
        }
    }

    public final void expand(boolean z) {
        if (isFullyCollapsed() || isCollapsing()) {
            this.mInstantExpanding = true;
            this.mAnimateAfterExpanding = z;
            this.mUpdateFlingOnLayout = false;
            cancelHeightAnimator();
            this.mView.removeCallbacks(this.mFlingCollapseRunnable);
            if (this.mTracking) {
                onTrackingStopped(true);
            }
            if (this.mExpanding) {
                notifyExpandingFinished();
            }
            updatePanelExpansionAndVisibility();
            this.mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public final void onGlobalLayout(
/*
Method generation error in method: com.android.systemui.statusbar.phone.PanelViewController.6.onGlobalLayout():void, dex: classes.dex
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.statusbar.phone.PanelViewController.6.onGlobalLayout():void, class status: UNLOADED
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
            this.mView.requestLayout();
        }
        setListening(true);
    }

    public final void expandWithQs() {
        if (isQsExpansionEnabled()) {
            this.mQsExpandImmediate = true;
            this.mNotificationStackScrollLayoutController.setShouldShowShelfOnly(true);
        }
        if (isFullyCollapsed()) {
            expand(true);
            return;
        }
        traceQsJank(true, false);
        flingSettings(0.0f, 0, (WMShell$7$$ExternalSyntheticLambda1) null, false);
    }

    public final void setQSClippingBounds() {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int calculateQsBottomPosition = calculateQsBottomPosition(computeQsExpansionFraction());
        if (computeQsExpansionFraction() > 0.0f || calculateQsBottomPosition > 0) {
            z = true;
        } else {
            z = false;
        }
        if (this.mShouldUseSplitNotificationShade) {
            i = Math.min(calculateQsBottomPosition, this.mSplitShadeStatusBarHeight);
        } else {
            if (this.mTransitioningToFullShadeProgress > 0.0f) {
                calculateQsBottomPosition = this.mTransitionToFullShadeQSPosition;
            } else {
                float qSEdgePosition = getQSEdgePosition();
                if (!isOnKeyguard()) {
                    calculateQsBottomPosition = (int) qSEdgePosition;
                } else if (!this.mKeyguardBypassController.getBypassEnabled()) {
                    calculateQsBottomPosition = (int) Math.min((float) calculateQsBottomPosition, qSEdgePosition);
                }
            }
            i = (int) (((float) calculateQsBottomPosition) + this.mOverStretchAmount);
            float f = this.mMinFraction;
            if (f > 0.0f && f < 1.0f) {
                i = (int) (MathUtils.saturate(((this.mExpandedFraction - f) / (1.0f - f)) / f) * ((float) i));
            }
        }
        if (this.mShouldUseSplitNotificationShade) {
            i2 = (this.mNotificationStackScrollLayoutController.getHeight() + i) - this.mSplitShadeNotificationsScrimMarginBottom;
        } else {
            i2 = this.mView.getBottom();
        }
        int i5 = i2;
        if (this.mIsFullWidth) {
            i3 = 0;
        } else {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            i3 = notificationStackScrollLayoutController.mView.getLeft();
        }
        if (this.mIsFullWidth) {
            i4 = this.mView.getRight() + this.mDisplayRightInset;
        } else {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            i4 = notificationStackScrollLayoutController2.mView.getRight();
        }
        int i6 = i4;
        int min = Math.min(i, i5);
        if (this.mAnimateNextNotificationBounds && !this.mKeyguardStatusAreaClipBounds.isEmpty()) {
            this.mQsClippingAnimationEndBounds.set(i3, min, i6, i5);
            Rect rect = this.mKeyguardStatusAreaClipBounds;
            int i7 = rect.left;
            int i8 = rect.top;
            int i9 = rect.right;
            int i10 = rect.bottom;
            ValueAnimator valueAnimator = this.mQsClippingAnimation;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mQsClippingAnimation = ofFloat;
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            this.mQsClippingAnimation.setDuration(this.mNotificationBoundsAnimationDuration);
            this.mQsClippingAnimation.setStartDelay(this.mNotificationBoundsAnimationDelay);
            this.mQsClippingAnimation.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i7, i8, i9, i10, z));
            this.mQsClippingAnimation.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    notificationPanelViewController.mQsClippingAnimation = null;
                    notificationPanelViewController.mIsQsTranslationResetAnimator = false;
                    notificationPanelViewController.mIsPulseExpansionResetAnimator = false;
                }
            });
            this.mQsClippingAnimation.start();
        } else if (this.mQsClippingAnimation != null) {
            this.mQsClippingAnimationEndBounds.set(i3, min, i6, i5);
        } else {
            applyQSClippingImmediately(i3, min, i6, i5, z);
        }
        this.mAnimateNextNotificationBounds = false;
        this.mNotificationBoundsAnimationDelay = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean shouldQuickSettingsIntercept(float r8, float r9, float r10) {
        /*
            r7 = this;
            boolean r0 = r7.isQsExpansionEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x00e4
            boolean r0 = r7.mCollapsedOnDown
            if (r0 != 0) goto L_0x00e4
            boolean r0 = r7.mKeyguardShowing
            if (r0 == 0) goto L_0x0017
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r7.mKeyguardBypassController
            boolean r0 = r0.getBypassEnabled()
            if (r0 != 0) goto L_0x00e4
        L_0x0017:
            boolean r0 = r7.mKeyguardShowing
            if (r0 == 0) goto L_0x0021
            boolean r2 = r7.mShouldUseSplitNotificationShade
            if (r2 == 0) goto L_0x0021
            goto L_0x00e4
        L_0x0021:
            if (r0 != 0) goto L_0x002d
            com.android.systemui.plugins.qs.QS r0 = r7.mQs
            if (r0 != 0) goto L_0x0028
            goto L_0x002d
        L_0x0028:
            android.view.View r0 = r0.getHeader()
            goto L_0x002f
        L_0x002d:
            com.android.systemui.statusbar.phone.KeyguardStatusBarView r0 = r7.mKeyguardStatusBar
        L_0x002f:
            android.graphics.Region r2 = r7.mQsInterceptRegion
            android.widget.FrameLayout r3 = r7.mQsFrame
            float r3 = r3.getX()
            int r3 = (int) r3
            int r4 = r0.getTop()
            android.widget.FrameLayout r5 = r7.mQsFrame
            float r5 = r5.getX()
            int r5 = (int) r5
            android.widget.FrameLayout r6 = r7.mQsFrame
            int r6 = r6.getWidth()
            int r6 = r6 + r5
            int r0 = r0.getBottom()
            r2.set(r3, r4, r6, r0)
            com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager r0 = r7.mStatusBarTouchableRegionManager
            android.graphics.Region r2 = r7.mQsInterceptRegion
            r0.updateRegionForNotch(r2)
            android.graphics.Region r0 = r7.mQsInterceptRegion
            int r2 = (int) r8
            int r3 = (int) r9
            boolean r0 = r0.contains(r2, r3)
            boolean r2 = r7.mQsExpanded
            if (r2 == 0) goto L_0x00e3
            r2 = 1
            if (r0 != 0) goto L_0x00e1
            r0 = 0
            int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r10 >= 0) goto L_0x00e2
            boolean r8 = r7.touchXOutsideOfQs(r8)
            if (r8 == 0) goto L_0x0073
            goto L_0x00dc
        L_0x0073:
            boolean r8 = r7.mIsGestureNavigation
            if (r8 == 0) goto L_0x0086
            com.android.systemui.statusbar.phone.NotificationPanelView r8 = r7.mView
            int r8 = r8.getHeight()
            int r10 = r7.mNavigationBarBottomHeight
            int r8 = r8 - r10
            float r8 = (float) r8
            int r8 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0086
            goto L_0x00dc
        L_0x0086:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = r7.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
            java.util.Objects.requireNonNull(r8)
            int r10 = r8.getChildCount()
            r3 = r1
        L_0x0095:
            if (r3 >= r10) goto L_0x00ba
            android.view.View r4 = r8.getChildAt(r3)
            com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
            int r5 = r4.getVisibility()
            r6 = 8
            if (r5 != r6) goto L_0x00a6
            goto L_0x00b7
        L_0x00a6:
            float r5 = r4.getTranslationY()
            int r6 = r4.mActualHeight
            float r6 = (float) r6
            float r5 = r5 + r6
            int r4 = r4.mClipBottomAmount
            float r4 = (float) r4
            float r5 = r5 - r4
            int r4 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r4 <= 0) goto L_0x00b7
            r0 = r5
        L_0x00b7:
            int r3 = r3 + 1
            goto L_0x0095
        L_0x00ba:
            float r8 = r8.mStackTranslation
            float r0 = r0 + r8
            int r8 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r8 <= 0) goto L_0x00de
            com.android.systemui.plugins.qs.QS r8 = r7.mQs
            android.view.View r8 = r8.getView()
            float r8 = r8.getY()
            com.android.systemui.plugins.qs.QS r7 = r7.mQs
            android.view.View r7 = r7.getView()
            int r7 = r7.getHeight()
            float r7 = (float) r7
            float r8 = r8 + r7
            int r7 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r7 > 0) goto L_0x00dc
            goto L_0x00de
        L_0x00dc:
            r7 = r1
            goto L_0x00df
        L_0x00de:
            r7 = r2
        L_0x00df:
            if (r7 == 0) goto L_0x00e2
        L_0x00e1:
            r1 = r2
        L_0x00e2:
            return r1
        L_0x00e3:
            return r0
        L_0x00e4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.shouldQuickSettingsIntercept(float, float, float):boolean");
    }

    public final void updatePanelExpanded() {
        boolean z;
        C0961QS qs;
        if (!isFullyCollapsed() || this.mExpectingSynthesizedDown) {
            z = true;
        } else {
            z = false;
        }
        if (this.mPanelExpanded != z) {
            HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
            Objects.requireNonNull(headsUpManagerPhone);
            if (z != headsUpManagerPhone.mIsExpanded) {
                headsUpManagerPhone.mIsExpanded = z;
                if (z) {
                    headsUpManagerPhone.mHeadsUpGoingAway = false;
                }
            }
            StatusBarTouchableRegionManager statusBarTouchableRegionManager = this.mStatusBarTouchableRegionManager;
            Objects.requireNonNull(statusBarTouchableRegionManager);
            if (z != statusBarTouchableRegionManager.mIsStatusBarExpanded) {
                statusBarTouchableRegionManager.mIsStatusBarExpanded = z;
                if (z) {
                    statusBarTouchableRegionManager.mForceCollapsedUntilLayout = false;
                }
                statusBarTouchableRegionManager.updateTouchableRegion();
            }
            StatusBar statusBar = this.mStatusBar;
            Objects.requireNonNull(statusBar);
            if (statusBar.mPanelExpanded != z) {
                NotificationLogger notificationLogger = statusBar.mNotificationLogger;
                Objects.requireNonNull(notificationLogger);
                notificationLogger.mPanelExpanded = Boolean.valueOf(z);
                synchronized (notificationLogger.mDozingLock) {
                    notificationLogger.maybeUpdateLoggingStatus();
                }
            }
            statusBar.mPanelExpanded = z;
            StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = statusBar.mStatusBarHideIconsForBouncerManager;
            Objects.requireNonNull(statusBarHideIconsForBouncerManager);
            statusBarHideIconsForBouncerManager.panelExpanded = z;
            statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
            statusBar.mNotificationShadeWindowController.setPanelExpanded(z);
            statusBar.mStatusBarStateController.setPanelExpanded(z);
            if (z && statusBar.mStatusBarStateController.getState() != 1) {
                try {
                    statusBar.mBarService.clearNotificationEffects();
                } catch (RemoteException unused) {
                }
            }
            if (!z) {
                NotificationRemoteInputManager notificationRemoteInputManager = statusBar.mRemoteInputManager;
                Objects.requireNonNull(notificationRemoteInputManager);
                NotificationRemoteInputManager.RemoteInputListener remoteInputListener = notificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputListener != null) {
                    remoteInputListener.onPanelCollapsed();
                }
            }
            this.mPanelExpanded = z;
            if (!z && (qs = this.mQs) != null && qs.isCustomizing()) {
                this.mQs.closeCustomizer();
            }
        }
    }
}
