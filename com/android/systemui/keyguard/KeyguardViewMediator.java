package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.biometrics.BiometricSourceType;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.leanback.R$color;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardConstants;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda10;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda0;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

public class KeyguardViewMediator extends CoreStartable implements StatusBarStateController.StateListener {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public Lazy<ActivityLaunchAnimator> mActivityLaunchAnimator;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public AudioManager mAudioManager;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C08608 mBroadcastReceiver;
    public CharSequence mCustomMessage;
    public final C08597 mDelayedLockBroadcastReceiver;
    public int mDelayedProfileShowingSequence;
    public int mDelayedShowingSequence;
    public DeviceConfigProxy mDeviceConfig;
    public boolean mDeviceInteractive;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public boolean mDreamOverlayShowing;
    public final C08542 mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mExternallyEnabled = true;
    public final FalsingCollector mFalsingCollector;
    public boolean mGoingToSleep;
    public C08619 mHandler;
    public Animation mHideAnimation;
    public final QSTileImpl$$ExternalSyntheticLambda0 mHideAnimationFinishedRunnable;
    public boolean mHideAnimationRun = false;
    public boolean mHideAnimationRunning = false;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public boolean mKeyguardDonePending = false;
    public IRemoteAnimationRunner mKeyguardExitAnimationRunner;
    public final C085110 mKeyguardGoingAwayRunnable;
    public final ArrayList<IKeyguardStateCallback> mKeyguardStateCallbacks = new ArrayList<>();
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy<KeyguardUnlockAnimationController> mKeyguardUnlockAnimationControllerLazy;
    public final Lazy<KeyguardViewController> mKeyguardViewControllerLazy;
    public final SparseIntArray mLastSimStates = new SparseIntArray();
    public boolean mLockLater;
    public final LockPatternUtils mLockPatternUtils;
    public int mLockSoundId;
    public int mLockSoundStreamId;
    public float mLockSoundVolume;
    public SoundPool mLockSounds;
    public boolean mNeedToReshowWhenReenabled = false;
    public final Lazy<NotificationShadeDepthController> mNotificationShadeDepthController;
    public final Lazy<NotificationShadeWindowController> mNotificationShadeWindowControllerLazy;
    public final C08575 mOccludeAnimationController;
    public ActivityLaunchRemoteAnimationRunner mOccludeAnimationRunner;
    public boolean mOccluded = false;
    public final C08501 mOnPropertiesChangedListener;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock = false;
    public boolean mPendingReset;
    public String mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;
    public final float mPowerButtonY;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public ScreenOnCoordinator mScreenOnCoordinator;
    public boolean mShowHomeOverLockscreen;
    public PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public final SparseBooleanArray mSimWasLocked = new SparseBooleanArray();
    public StatusBarManager mStatusBarManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public IRemoteAnimationFinishedCallback mSurfaceBehindRemoteAnimationFinishedCallback;
    public boolean mSurfaceBehindRemoteAnimationRequested = false;
    public boolean mSurfaceBehindRemoteAnimationRunning;
    public boolean mSystemReady;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public int mUiSoundsStreamType;
    public int mUnlockSoundId;
    public final C08586 mUnoccludeAnimationController;
    public ActivityLaunchRemoteAnimationRunner mUnoccludeAnimationRunner;
    public C08553 mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public C08564 mViewMediatorCallback;
    public boolean mWaitingUntilKeyguardVisible = false;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;

    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public final ActivityLaunchAnimator.Controller mActivityLaunchController;
        public ActivityLaunchAnimator.Runner mRunner;

        public ActivityLaunchRemoteAnimationRunner(ActivityLaunchAnimator.Controller controller) {
            this.mActivityLaunchController = controller;
        }

        public final void onAnimationCancelled() throws RemoteException {
            ActivityLaunchAnimator.Runner runner = this.mRunner;
            if (runner != null) {
                runner.onAnimationCancelled();
            }
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            ActivityLaunchAnimator.Runner createRunner = KeyguardViewMediator.this.mActivityLaunchAnimator.get().createRunner(this.mActivityLaunchController);
            this.mRunner = createRunner;
            createRunner.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardViewMediator(Context context, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy<KeyguardViewController> lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy<KeyguardUnlockAnimationController> lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy<NotificationShadeDepthController> lazy3, ScreenOnCoordinator screenOnCoordinator, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy<NotificationShadeWindowController> lazy4, Lazy<ActivityLaunchAnimator> lazy5) {
        super(context);
        SysuiStatusBarStateController sysuiStatusBarStateController2 = sysuiStatusBarStateController;
        C08501 r3 = new DeviceConfig.OnPropertiesChangedListener() {
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    KeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mOnPropertiesChangedListener = r3;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() {
            public final void onStateChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                DreamOverlayStateController dreamOverlayStateController = keyguardViewMediator.mDreamOverlayStateController;
                Objects.requireNonNull(dreamOverlayStateController);
                boolean z = true;
                if ((dreamOverlayStateController.mState & 1) == 0) {
                    z = false;
                }
                keyguardViewMediator.mDreamOverlayShowing = z;
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() {
            public final void onUserInfoChanged() {
            }

            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(i)) {
                    KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulBiometricAttempt(i);
                }
            }

            public final void onClockVisibilityChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z = KeyguardViewMediator.DEBUG;
                Objects.requireNonNull(keyguardViewMediator);
                keyguardViewMediator.adjustStatusBarLocked(false, false);
            }

            public final void onDeviceProvisioned() {
                boolean z;
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z2 = KeyguardViewMediator.DEBUG;
                keyguardViewMediator.sendUserPresentBroadcast();
                synchronized (KeyguardViewMediator.this) {
                    Objects.requireNonNull(KeyguardViewMediator.this);
                    if (!UserManager.isSplitSystemUser() || KeyguardUpdateMonitor.getCurrentUser() != 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        KeyguardViewMediator.this.doKeyguardLocked((Bundle) null);
                    }
                }
            }

            public final void onKeyguardVisibilityChanged(boolean z) {
                synchronized (KeyguardViewMediator.this) {
                    if (!z) {
                        if (KeyguardViewMediator.this.mPendingPinLock) {
                            Log.i("KeyguardViewMediator", "PIN lock requested, starting keyguard");
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            keyguardViewMediator.mPendingPinLock = false;
                            keyguardViewMediator.doKeyguardLocked((Bundle) null);
                        }
                    }
                }
            }

            public final void onSimStateChanged(int i, int i2, int i3) {
                boolean z;
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("onSimStateChanged(subId=", i, ", slotId=", i2, ",state=");
                m.append(i3);
                m.append(")");
                Log.d("KeyguardViewMediator", m.toString());
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean isSimPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i4 = size - 1; i4 >= 0; i4--) {
                    try {
                        KeyguardViewMediator.this.mKeyguardStateCallbacks.get(i4).onSimSecureStateChanged(isSimPinSecure);
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            KeyguardViewMediator.this.mKeyguardStateCallbacks.remove(i4);
                        }
                    }
                }
                synchronized (KeyguardViewMediator.this) {
                    int i5 = KeyguardViewMediator.this.mLastSimStates.get(i2);
                    if (i5 != 2) {
                        if (i5 != 3) {
                            z = false;
                            KeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                        }
                    }
                    z = true;
                    KeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                }
                if (i3 != 1) {
                    if (i3 == 2 || i3 == 3) {
                        synchronized (KeyguardViewMediator.this) {
                            KeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            if (!keyguardViewMediator.mShowing) {
                                Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                KeyguardViewMediator.this.doKeyguardLocked((Bundle) null);
                            } else {
                                keyguardViewMediator.mPendingPinLock = true;
                                keyguardViewMediator.resetStateLocked();
                            }
                        }
                        return;
                    } else if (i3 == 5) {
                        synchronized (KeyguardViewMediator.this) {
                            Log.d("KeyguardViewMediator", "READY, reset state? " + KeyguardViewMediator.this.mShowing);
                            KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                            if (keyguardViewMediator2.mShowing && keyguardViewMediator2.mSimWasLocked.get(i2, false)) {
                                Log.d("KeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                                KeyguardViewMediator.this.resetStateLocked();
                            }
                        }
                        return;
                    } else if (i3 != 6) {
                        if (i3 != 7) {
                            Log.v("KeyguardViewMediator", "Unspecific state: " + i3);
                            return;
                        }
                        synchronized (KeyguardViewMediator.this) {
                            if (!KeyguardViewMediator.this.mShowing) {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                KeyguardViewMediator.this.doKeyguardLocked((Bundle) null);
                            } else {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                KeyguardViewMediator.this.resetStateLocked();
                            }
                        }
                        return;
                    }
                }
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.shouldWaitForProvisioning()) {
                        KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                        if (!keyguardViewMediator3.mShowing) {
                            Log.d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                            KeyguardViewMediator.this.doKeyguardLocked((Bundle) null);
                        } else {
                            keyguardViewMediator3.resetStateLocked();
                        }
                    }
                    if (i3 == 1) {
                        if (z) {
                            Log.d("KeyguardViewMediator", "SIM moved to ABSENT when the previous state was locked. Reset the state.");
                            KeyguardViewMediator.this.resetStateLocked();
                        }
                        KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                    }
                }
            }

            public final void onUserSwitchComplete(int i) {
                UserInfo userInfo;
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", String.format("onUserSwitchComplete %d", new Object[]{Integer.valueOf(i)}));
                }
                if (i != 0 && (userInfo = UserManager.get(KeyguardViewMediator.this.mContext).getUserInfo(i)) != null && !KeyguardViewMediator.this.mLockPatternUtils.isSecure(i)) {
                    if (userInfo.isGuest() || userInfo.isDemo()) {
                        KeyguardViewMediator.this.dismiss((IKeyguardDismissCallback) null, (CharSequence) null);
                    }
                }
            }

            public final void onUserSwitching(int i) {
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", String.format("onUserSwitching %d", new Object[]{Integer.valueOf(i)}));
                }
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator.this.resetKeyguardDonePendingLocked();
                    if (KeyguardViewMediator.this.mLockPatternUtils.isLockScreenDisabled(i)) {
                        KeyguardViewMediator.this.dismiss((IKeyguardDismissCallback) null, (CharSequence) null);
                    } else {
                        KeyguardViewMediator.this.resetStateLocked();
                    }
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    Objects.requireNonNull(keyguardViewMediator);
                    keyguardViewMediator.adjustStatusBarLocked(false, false);
                }
            }

            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(currentUser)) {
                    KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(currentUser);
                }
            }

            public final void onTrustChanged(int i) {
                if (i == KeyguardUpdateMonitor.getCurrentUser()) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.notifyTrustedChangedLocked(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
                    }
                }
            }
        };
        this.mViewMediatorCallback = new ViewMediatorCallback() {
            public final CharSequence consumeCustomMessage() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                CharSequence charSequence = keyguardViewMediator.mCustomMessage;
                keyguardViewMediator.mCustomMessage = null;
                return charSequence;
            }

            public final boolean isScreenOn() {
                return KeyguardViewMediator.this.mDeviceInteractive;
            }

            public final void keyguardDoneDrawing() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDoneDrawing");
                KeyguardViewMediator.this.mHandler.sendEmptyMessage(8);
                Trace.endSection();
            }

            public final void keyguardDonePending(int i) {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDonePending");
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "keyguardDonePending");
                }
                if (i != ActivityManager.getCurrentUser()) {
                    Trace.endSection();
                    return;
                }
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mKeyguardDonePending = true;
                keyguardViewMediator.mHideAnimationRun = true;
                keyguardViewMediator.mHideAnimationRunning = true;
                keyguardViewMediator.mKeyguardViewControllerLazy.get().startPreHideAnimation(KeyguardViewMediator.this.mHideAnimationFinishedRunnable);
                KeyguardViewMediator.this.mHandler.sendEmptyMessageDelayed(13, 3000);
                Trace.endSection();
            }

            public final void keyguardGone() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardGone");
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "keyguardGone");
                }
                KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().setKeyguardGoingAwayState(false);
                KeyguardDisplayManager keyguardDisplayManager = KeyguardViewMediator.this.mKeyguardDisplayManager;
                Objects.requireNonNull(keyguardDisplayManager);
                if (keyguardDisplayManager.mShowing) {
                    if (KeyguardDisplayManager.DEBUG) {
                        Log.v("KeyguardDisplayManager", "hide");
                    }
                    MediaRouter mediaRouter = keyguardDisplayManager.mMediaRouter;
                    if (mediaRouter != null) {
                        mediaRouter.removeCallback(keyguardDisplayManager.mMediaRouterCallback);
                    }
                    keyguardDisplayManager.updateDisplays(false);
                }
                keyguardDisplayManager.mShowing = false;
                Trace.endSection();
            }

            public final void onBouncerVisiblityChanged(boolean z) {
                synchronized (KeyguardViewMediator.this) {
                    if (z) {
                        KeyguardViewMediator.this.mPendingPinLock = false;
                    }
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    boolean z2 = KeyguardViewMediator.DEBUG;
                    keyguardViewMediator.adjustStatusBarLocked(z, false);
                }
            }

            public final void onCancelClicked() {
                KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().onCancelClicked();
            }

            public final void playTrustedSound() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z = KeyguardViewMediator.DEBUG;
                Objects.requireNonNull(keyguardViewMediator);
                keyguardViewMediator.playSound(keyguardViewMediator.mTrustedSoundId);
            }

            public final void readyForKeyguardDone() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mKeyguardDonePending) {
                    keyguardViewMediator.mKeyguardDonePending = false;
                    keyguardViewMediator.tryKeyguardDone();
                }
                Trace.endSection();
            }

            public final void resetKeyguard() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z = KeyguardViewMediator.DEBUG;
                keyguardViewMediator.resetStateLocked();
            }

            public final void setNeedsInput(boolean z) {
                KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().setNeedsInput(z);
            }

            public final void userActivity() {
                KeyguardViewMediator.this.userActivity();
            }

            public final int getBouncerPromptReason() {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardViewMediator.this.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                boolean z5 = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(currentUser);
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardViewMediator.this.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor2);
                boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardUpdateMonitor$$ExternalSyntheticLambda10(keyguardUpdateMonitor2, currentUser))).booleanValue();
                keyguardUpdateMonitor2.mIsFaceEnrolled = booleanValue;
                boolean z6 = true;
                if (!booleanValue || keyguardUpdateMonitor2.isFaceDisabled(currentUser)) {
                    z = false;
                } else {
                    z = true;
                }
                if (z || keyguardUpdateMonitor2.isUnlockWithFingerprintPossible(currentUser)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z5 || z2) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardViewMediator.this.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor3);
                KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor3.mStrongAuthTracker;
                int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(currentUser);
                if (z3) {
                    if ((strongAuthTracker.getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()) & 1) == 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    if (!z4) {
                        return 1;
                    }
                }
                if (z3 && (strongAuthForUser & 16) != 0) {
                    return 2;
                }
                if ((strongAuthForUser & 2) != 0) {
                    return 3;
                }
                if (z5 && (strongAuthForUser & 4) != 0) {
                    return 4;
                }
                if (z3) {
                    if ((strongAuthForUser & 8) != 0) {
                        return 5;
                    }
                    KeyguardUpdateMonitor keyguardUpdateMonitor4 = KeyguardViewMediator.this.mUpdateMonitor;
                    Objects.requireNonNull(keyguardUpdateMonitor4);
                    if (!keyguardUpdateMonitor4.mFingerprintLockedOut && !keyguardUpdateMonitor4.mFingerprintLockedOutPermanent) {
                        z6 = false;
                    }
                    if (z6) {
                        return 5;
                    }
                }
                if (z3 && (strongAuthForUser & 64) != 0) {
                    return 6;
                }
                if (!z3 || (strongAuthForUser & 128) == 0) {
                    return 0;
                }
                return 7;
            }

            public final void keyguardDone(int i) {
                if (i == ActivityManager.getCurrentUser()) {
                    if (KeyguardViewMediator.DEBUG) {
                        Log.d("KeyguardViewMediator", "keyguardDone");
                    }
                    KeyguardViewMediator.this.tryKeyguardDone();
                }
            }
        };
        C08575 r4 = new ActivityLaunchAnimator.Controller() {
            public final ViewGroup getLaunchContainer() {
                return (ViewGroup) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().getViewRootImpl().getView();
            }

            public final void onLaunchAnimationCancelled() {
                KeyguardViewMediator.this.setOccluded(true, false);
            }

            public final void onLaunchAnimationStart(boolean z) {
                KeyguardViewMediator.this.setOccluded(true, false);
            }

            public final void setLaunchContainer(ViewGroup viewGroup) {
                Log.wtf("KeyguardViewMediator", "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.");
            }

            public final LaunchAnimator.State createAnimatorState() {
                int width = getLaunchContainer().getWidth();
                int height = getLaunchContainer().getHeight();
                float f = ((float) height) / 3.0f;
                float f2 = (float) width;
                float f3 = f2 / 3.0f;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardViewMediator.this.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                if (keyguardUpdateMonitor.mSecureCameraLaunched) {
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    float f4 = keyguardViewMediator.mPowerButtonY;
                    float f5 = f / 2.0f;
                    int i = (int) (f2 - f3);
                    float f6 = keyguardViewMediator.mWindowCornerRadius;
                    return new LaunchAnimator.State((int) (f4 - f5), (int) (f4 + f5), i, width, f6, f6);
                }
                int i2 = height / 2;
                int i3 = width / 2;
                float f7 = KeyguardViewMediator.this.mWindowCornerRadius;
                return new LaunchAnimator.State(i2, i2, i3, i3, f7, f7);
            }
        };
        this.mOccludeAnimationController = r4;
        C08586 r5 = new ActivityLaunchAnimator.Controller() {
            public final ViewGroup getLaunchContainer() {
                return (ViewGroup) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().getViewRootImpl().getView();
            }

            public final void onLaunchAnimationCancelled() {
                KeyguardViewMediator.this.setOccluded(false, false);
            }

            public final void onLaunchAnimationEnd(boolean z) {
                KeyguardViewMediator.this.setOccluded(false, false);
            }

            public final void setLaunchContainer(ViewGroup viewGroup) {
                Log.wtf("KeyguardViewMediator", "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.");
            }

            public final LaunchAnimator.State createAnimatorState() {
                int width = getLaunchContainer().getWidth();
                int height = getLaunchContainer().getHeight();
                float f = KeyguardViewMediator.this.mWindowCornerRadius;
                return new LaunchAnimator.State(0, height, 0, width, f, f);
            }
        };
        this.mUnoccludeAnimationController = r5;
        this.mOccludeAnimationRunner = new ActivityLaunchRemoteAnimationRunner(r4);
        this.mUnoccludeAnimationRunner = new ActivityLaunchRemoteAnimationRunner(r5);
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("seq", 0);
                    if (KeyguardViewMediator.DEBUG) {
                        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(ExifInterface$$ExternalSyntheticOutline0.m13m("received DELAYED_KEYGUARD_ACTION with seq = ", intExtra, ", mDelayedShowingSequence = "), KeyguardViewMediator.this.mDelayedShowingSequence, "KeyguardViewMediator");
                    }
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        if (keyguardViewMediator.mDelayedShowingSequence == intExtra) {
                            keyguardViewMediator.doKeyguardLocked((Bundle) null);
                        }
                    }
                } else if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("seq", 0);
                    int intExtra3 = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                    if (intExtra3 != 0) {
                        synchronized (KeyguardViewMediator.this) {
                            KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                            if (keyguardViewMediator2.mDelayedProfileShowingSequence == intExtra2) {
                                keyguardViewMediator2.mTrustManager.setDeviceLockedForUser(intExtra3, true);
                            }
                        }
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) {
            /* JADX WARNING: Code restructure failed: missing block: B:124:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:86:0x021c, code lost:
                r6 = r5.mKeyguardDisplayManager;
                java.util.Objects.requireNonNull(r6);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:87:0x0223, code lost:
                if (r6.mShowing != false) goto L_0x0246;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:89:0x0229, code lost:
                if (com.android.keyguard.KeyguardDisplayManager.DEBUG == false) goto L_0x0231;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:90:0x022b, code lost:
                android.util.Log.v("KeyguardDisplayManager", "show");
             */
            /* JADX WARNING: Code restructure failed: missing block: B:91:0x0231, code lost:
                r0 = r6.mMediaRouter;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:92:0x0233, code lost:
                if (r0 == null) goto L_0x023e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:93:0x0235, code lost:
                r0.addCallback(4, r6.mMediaRouterCallback, 8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:94:0x023e, code lost:
                android.util.Log.w("KeyguardDisplayManager", "MediaRouter not yet initialized");
             */
            /* JADX WARNING: Code restructure failed: missing block: B:95:0x0243, code lost:
                r6.updateDisplays(true);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:96:0x0246, code lost:
                r6.mShowing = true;
                r5.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser());
                android.os.Trace.endSection();
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void handleMessage(android.os.Message r6) {
                /*
                    r5 = this;
                    int r0 = r6.what
                    r1 = 1
                    r2 = 0
                    switch(r0) {
                        case 1: goto L_0x0198;
                        case 2: goto L_0x018f;
                        case 3: goto L_0x016b;
                        case 4: goto L_0x0134;
                        case 5: goto L_0x012d;
                        case 6: goto L_0x0007;
                        case 7: goto L_0x011c;
                        case 8: goto L_0x010d;
                        case 9: goto L_0x00f1;
                        case 10: goto L_0x00c3;
                        case 11: goto L_0x007c;
                        case 12: goto L_0x005c;
                        case 13: goto L_0x004b;
                        case 14: goto L_0x003c;
                        case 15: goto L_0x0007;
                        case 16: goto L_0x0007;
                        case 17: goto L_0x0035;
                        case 18: goto L_0x002e;
                        case 19: goto L_0x0009;
                        default: goto L_0x0007;
                    }
                L_0x0007:
                    goto L_0x0258
                L_0x0009:
                    java.lang.String r6 = "KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM"
                    android.os.Trace.beginSection(r6)
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    java.util.Objects.requireNonNull(r5)
                    r5.mSurfaceBehindRemoteAnimationRequested = r1
                    android.app.IActivityTaskManager r6 = android.app.ActivityTaskManager.getService()     // Catch:{ RemoteException -> 0x0020 }
                    r0 = 6
                    r6.keyguardGoingAway(r0)     // Catch:{ RemoteException -> 0x0020 }
                    goto L_0x0026
                L_0x0020:
                    r6 = move-exception
                    r5.mSurfaceBehindRemoteAnimationRequested = r2
                    r6.printStackTrace()
                L_0x0026:
                    r5.onKeyguardExitRemoteAnimationFinished(r1)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x002e:
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.systemui.keyguard.KeyguardViewMediator.m207$$Nest$mhandleSystemReady(r5)
                    goto L_0x0258
                L_0x0035:
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.systemui.keyguard.KeyguardViewMediator.m204$$Nest$mhandleNotifyStartedGoingToSleep(r5)
                    goto L_0x0258
                L_0x003c:
                    java.lang.String r6 = "KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP"
                    android.os.Trace.beginSection(r6)
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.systemui.keyguard.KeyguardViewMediator.m205$$Nest$mhandleNotifyStartedWakingUp(r5)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x004b:
                    java.lang.String r5 = "KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT"
                    android.os.Trace.beginSection(r5)
                    java.lang.String r5 = "KeyguardViewMediator"
                    java.lang.String r6 = "Timeout while waiting for activity drawn!"
                    android.util.Log.w(r5, r6)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x005c:
                    java.lang.String r0 = "KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM"
                    android.os.Trace.beginSection(r0)
                    java.lang.Object r6 = r6.obj
                    com.android.systemui.keyguard.KeyguardViewMediator$StartKeyguardExitAnimParams r6 = (com.android.systemui.keyguard.KeyguardViewMediator.StartKeyguardExitAnimParams) r6
                    com.android.systemui.keyguard.KeyguardViewMediator r0 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.systemui.statusbar.NotificationShadeWindowController> r0 = r0.mNotificationShadeWindowControllerLazy
                    java.lang.Object r0 = r0.get()
                    com.android.systemui.statusbar.NotificationShadeWindowController r0 = (com.android.systemui.statusbar.NotificationShadeWindowController) r0
                    com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda0 r1 = new com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda0
                    r1.<init>(r5, r6, r2)
                    r0.batchApplyWindowLayoutParams(r1)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x007c:
                    java.lang.Object r6 = r6.obj
                    com.android.systemui.keyguard.KeyguardViewMediator$DismissMessage r6 = (com.android.systemui.keyguard.KeyguardViewMediator.DismissMessage) r6
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    java.util.Objects.requireNonNull(r6)
                    com.android.internal.policy.IKeyguardDismissCallback r0 = r6.mCallback
                    java.lang.CharSequence r6 = r6.mMessage
                    boolean r1 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    java.util.Objects.requireNonNull(r5)
                    boolean r1 = r5.mShowing
                    if (r1 == 0) goto L_0x00b2
                    if (r0 == 0) goto L_0x00a3
                    com.android.systemui.keyguard.DismissCallbackRegistry r1 = r5.mDismissCallbackRegistry
                    java.util.Objects.requireNonNull(r1)
                    java.util.ArrayList<com.android.systemui.keyguard.DismissCallbackWrapper> r1 = r1.mDismissCallbacks
                    com.android.systemui.keyguard.DismissCallbackWrapper r2 = new com.android.systemui.keyguard.DismissCallbackWrapper
                    r2.<init>(r0)
                    r1.add(r2)
                L_0x00a3:
                    r5.mCustomMessage = r6
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r5 = r5.mKeyguardViewControllerLazy
                    java.lang.Object r5 = r5.get()
                    com.android.keyguard.KeyguardViewController r5 = (com.android.keyguard.KeyguardViewController) r5
                    r5.dismissAndCollapse()
                    goto L_0x0258
                L_0x00b2:
                    if (r0 == 0) goto L_0x0258
                    r0.onDismissError()     // Catch:{ RemoteException -> 0x00b9 }
                    goto L_0x0258
                L_0x00b9:
                    r5 = move-exception
                    java.lang.String r6 = "DismissCallbackWrapper"
                    java.lang.String r0 = "Failed to call callback"
                    android.util.Log.i(r6, r0, r5)
                    goto L_0x0258
                L_0x00c3:
                    com.android.systemui.keyguard.KeyguardViewMediator r0 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    monitor-enter(r0)
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this     // Catch:{ all -> 0x00ee }
                    java.lang.Object r6 = r6.obj     // Catch:{ all -> 0x00ee }
                    android.os.Bundle r6 = (android.os.Bundle) r6     // Catch:{ all -> 0x00ee }
                    boolean r2 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x00ee }
                    r1.doKeyguardLocked(r6)     // Catch:{ all -> 0x00ee }
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this     // Catch:{ all -> 0x00ee }
                    boolean r6 = r5.mShowing     // Catch:{ all -> 0x00ee }
                    com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5 r1 = new com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5     // Catch:{ all -> 0x00ee }
                    r1.<init>(r5, r6)     // Catch:{ all -> 0x00ee }
                    com.android.systemui.DejankUtils.whitelistIpcs((java.lang.Runnable) r1)     // Catch:{ all -> 0x00ee }
                    r5.updateInputRestrictedLocked()     // Catch:{ all -> 0x00ee }
                    java.util.concurrent.Executor r6 = r5.mUiBgExecutor     // Catch:{ all -> 0x00ee }
                    com.android.wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0 r1 = new com.android.wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0     // Catch:{ all -> 0x00ee }
                    r2 = 3
                    r1.<init>(r5, r2)     // Catch:{ all -> 0x00ee }
                    r6.execute(r1)     // Catch:{ all -> 0x00ee }
                    monitor-exit(r0)     // Catch:{ all -> 0x00ee }
                    goto L_0x0258
                L_0x00ee:
                    r5 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x00ee }
                    throw r5
                L_0x00f1:
                    java.lang.String r0 = "KeyguardViewMediator#handleMessage SET_OCCLUDED"
                    android.os.Trace.beginSection(r0)
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    int r0 = r6.arg1
                    if (r0 == 0) goto L_0x00fe
                    r0 = r1
                    goto L_0x00ff
                L_0x00fe:
                    r0 = r2
                L_0x00ff:
                    int r6 = r6.arg2
                    if (r6 == 0) goto L_0x0104
                    goto L_0x0105
                L_0x0104:
                    r1 = r2
                L_0x0105:
                    com.android.systemui.keyguard.KeyguardViewMediator.m206$$Nest$mhandleSetOccluded(r5, r0, r1)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x010d:
                    java.lang.String r6 = "KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING"
                    android.os.Trace.beginSection(r6)
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.systemui.keyguard.KeyguardViewMediator.m202$$Nest$mhandleKeyguardDoneDrawing(r5)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x011c:
                    java.lang.String r6 = "KeyguardViewMediator#handleMessage KEYGUARD_DONE"
                    android.os.Trace.beginSection(r6)
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    r5.handleKeyguardDone()
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x012d:
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.systemui.keyguard.KeyguardViewMediator.m203$$Nest$mhandleNotifyFinishedGoingToSleep(r5)
                    goto L_0x0258
                L_0x0134:
                    java.lang.String r6 = "KeyguardViewMediator#handleMessage VERIFY_UNLOCK"
                    android.os.Trace.beginSection(r6)
                    com.android.systemui.keyguard.KeyguardViewMediator r6 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r5 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    java.util.Objects.requireNonNull(r6)
                    java.lang.String r5 = "KeyguardViewMediator#handleVerifyUnlock"
                    android.os.Trace.beginSection(r5)
                    monitor-enter(r6)
                    boolean r5 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0168 }
                    if (r5 == 0) goto L_0x0151
                    java.lang.String r5 = "KeyguardViewMediator"
                    java.lang.String r0 = "handleVerifyUnlock"
                    android.util.Log.d(r5, r0)     // Catch:{ all -> 0x0168 }
                L_0x0151:
                    r6.setShowingLocked(r1, r2)     // Catch:{ all -> 0x0168 }
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r5 = r6.mKeyguardViewControllerLazy     // Catch:{ all -> 0x0168 }
                    java.lang.Object r5 = r5.get()     // Catch:{ all -> 0x0168 }
                    com.android.keyguard.KeyguardViewController r5 = (com.android.keyguard.KeyguardViewController) r5     // Catch:{ all -> 0x0168 }
                    r5.dismissAndCollapse()     // Catch:{ all -> 0x0168 }
                    monitor-exit(r6)     // Catch:{ all -> 0x0168 }
                    android.os.Trace.endSection()
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x0168:
                    r5 = move-exception
                    monitor-exit(r6)     // Catch:{ all -> 0x0168 }
                    throw r5
                L_0x016b:
                    com.android.systemui.keyguard.KeyguardViewMediator r6 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r5 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    java.util.Objects.requireNonNull(r6)
                    monitor-enter(r6)
                    boolean r5 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x018c }
                    if (r5 == 0) goto L_0x017e
                    java.lang.String r5 = "KeyguardViewMediator"
                    java.lang.String r0 = "handleReset"
                    android.util.Log.d(r5, r0)     // Catch:{ all -> 0x018c }
                L_0x017e:
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r5 = r6.mKeyguardViewControllerLazy     // Catch:{ all -> 0x018c }
                    java.lang.Object r5 = r5.get()     // Catch:{ all -> 0x018c }
                    com.android.keyguard.KeyguardViewController r5 = (com.android.keyguard.KeyguardViewController) r5     // Catch:{ all -> 0x018c }
                    r5.reset(r1)     // Catch:{ all -> 0x018c }
                    monitor-exit(r6)     // Catch:{ all -> 0x018c }
                    goto L_0x0258
                L_0x018c:
                    r5 = move-exception
                    monitor-exit(r6)     // Catch:{ all -> 0x018c }
                    throw r5
                L_0x018f:
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    r5.handleHide()
                    goto L_0x0258
                L_0x0198:
                    com.android.systemui.keyguard.KeyguardViewMediator r5 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    java.lang.Object r6 = r6.obj
                    android.os.Bundle r6 = (android.os.Bundle) r6
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    java.util.Objects.requireNonNull(r5)
                    java.lang.String r6 = "KeyguardViewMediator#handleShow"
                    android.os.Trace.beginSection(r6)
                    int r6 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
                    com.android.internal.widget.LockPatternUtils r0 = r5.mLockPatternUtils
                    boolean r0 = r0.isSecure(r6)
                    if (r0 == 0) goto L_0x01bd
                    com.android.internal.widget.LockPatternUtils r0 = r5.mLockPatternUtils
                    android.app.admin.DevicePolicyManager r0 = r0.getDevicePolicyManager()
                    r0.reportKeyguardSecured(r6)
                L_0x01bd:
                    monitor-enter(r5)
                    boolean r6 = r5.mSystemReady     // Catch:{ all -> 0x0255 }
                    if (r6 != 0) goto L_0x01d0
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0255 }
                    if (r6 == 0) goto L_0x01cd
                    java.lang.String r6 = "KeyguardViewMediator"
                    java.lang.String r0 = "ignoring handleShow because system is not ready."
                    android.util.Log.d(r6, r0)     // Catch:{ all -> 0x0255 }
                L_0x01cd:
                    monitor-exit(r5)     // Catch:{ all -> 0x0255 }
                    goto L_0x0258
                L_0x01d0:
                    boolean r6 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0255 }
                    if (r6 == 0) goto L_0x01db
                    java.lang.String r6 = "KeyguardViewMediator"
                    java.lang.String r0 = "handleShow"
                    android.util.Log.d(r6, r0)     // Catch:{ all -> 0x0255 }
                L_0x01db:
                    r5.mHiding = r2     // Catch:{ all -> 0x0255 }
                    r6 = 0
                    r5.mKeyguardExitAnimationRunner = r6     // Catch:{ all -> 0x0255 }
                    com.android.keyguard.mediator.ScreenOnCoordinator r6 = r5.mScreenOnCoordinator     // Catch:{ all -> 0x0255 }
                    r6.setWakeAndUnlocking(r2)     // Catch:{ all -> 0x0255 }
                    r5.mPendingLock = r2     // Catch:{ all -> 0x0255 }
                    r5.setShowingLocked(r1, r2)     // Catch:{ all -> 0x0255 }
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r6 = r5.mKeyguardViewControllerLazy     // Catch:{ all -> 0x0255 }
                    java.lang.Object r6 = r6.get()     // Catch:{ all -> 0x0255 }
                    com.android.keyguard.KeyguardViewController r6 = (com.android.keyguard.KeyguardViewController) r6     // Catch:{ all -> 0x0255 }
                    r6.show$2()     // Catch:{ all -> 0x0255 }
                    r5.resetKeyguardDonePendingLocked()     // Catch:{ all -> 0x0255 }
                    r5.mHideAnimationRun = r2     // Catch:{ all -> 0x0255 }
                    r5.adjustStatusBarLocked(r2, r2)     // Catch:{ all -> 0x0255 }
                    r5.userActivity()     // Catch:{ all -> 0x0255 }
                    com.android.keyguard.KeyguardUpdateMonitor r6 = r5.mUpdateMonitor     // Catch:{ all -> 0x0255 }
                    java.util.Objects.requireNonNull(r6)     // Catch:{ all -> 0x0255 }
                    r6.mKeyguardGoingAway = r2     // Catch:{ all -> 0x0255 }
                    r0 = 2
                    r6.updateBiometricListeningState(r0)     // Catch:{ all -> 0x0255 }
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r6 = r5.mKeyguardViewControllerLazy     // Catch:{ all -> 0x0255 }
                    java.lang.Object r6 = r6.get()     // Catch:{ all -> 0x0255 }
                    com.android.keyguard.KeyguardViewController r6 = (com.android.keyguard.KeyguardViewController) r6     // Catch:{ all -> 0x0255 }
                    r6.setKeyguardGoingAwayState(r2)     // Catch:{ all -> 0x0255 }
                    android.os.PowerManager$WakeLock r6 = r5.mShowKeyguardWakeLock     // Catch:{ all -> 0x0255 }
                    r6.release()     // Catch:{ all -> 0x0255 }
                    monitor-exit(r5)     // Catch:{ all -> 0x0255 }
                    com.android.keyguard.KeyguardDisplayManager r6 = r5.mKeyguardDisplayManager
                    java.util.Objects.requireNonNull(r6)
                    boolean r0 = r6.mShowing
                    if (r0 != 0) goto L_0x0246
                    boolean r0 = com.android.keyguard.KeyguardDisplayManager.DEBUG
                    java.lang.String r2 = "KeyguardDisplayManager"
                    if (r0 == 0) goto L_0x0231
                    java.lang.String r0 = "show"
                    android.util.Log.v(r2, r0)
                L_0x0231:
                    android.media.MediaRouter r0 = r6.mMediaRouter
                    if (r0 == 0) goto L_0x023e
                    r2 = 4
                    com.android.keyguard.KeyguardDisplayManager$2 r3 = r6.mMediaRouterCallback
                    r4 = 8
                    r0.addCallback(r2, r3, r4)
                    goto L_0x0243
                L_0x023e:
                    java.lang.String r0 = "MediaRouter not yet initialized"
                    android.util.Log.w(r2, r0)
                L_0x0243:
                    r6.updateDisplays(r1)
                L_0x0246:
                    r6.mShowing = r1
                    com.android.internal.widget.LockPatternUtils r5 = r5.mLockPatternUtils
                    int r6 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
                    r5.scheduleNonStrongBiometricIdleTimeout(r6)
                    android.os.Trace.endSection()
                    goto L_0x0258
                L_0x0255:
                    r6 = move-exception
                    monitor-exit(r5)     // Catch:{ all -> 0x0255 }
                    throw r6
                L_0x0258:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.C08619.handleMessage(android.os.Message):void");
            }
        };
        this.mKeyguardGoingAwayRunnable = new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0060, code lost:
                if (r4.this$0.mWallpaperSupportsAmbientMode != false) goto L_0x0062;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:8:0x003e, code lost:
                if (r4.this$0.mWallpaperSupportsAmbientMode == false) goto L_0x0040;
             */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0051  */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0074  */
            /* JADX WARNING: Removed duplicated region for block: B:22:0x0086  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r4 = this;
                    java.lang.String r0 = "KeyguardViewMediator.mKeyGuardGoingAwayRunnable"
                    android.os.Trace.beginSection(r0)
                    boolean r0 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                    if (r0 == 0) goto L_0x0010
                    java.lang.String r0 = "KeyguardViewMediator"
                    java.lang.String r1 = "keyguardGoingAway"
                    android.util.Log.d(r0, r1)
                L_0x0010:
                    com.android.systemui.keyguard.KeyguardViewMediator r0 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r0 = r0.mKeyguardViewControllerLazy
                    java.lang.Object r0 = r0.get()
                    com.android.keyguard.KeyguardViewController r0 = (com.android.keyguard.KeyguardViewController) r0
                    r0.keyguardGoingAway()
                    r0 = 0
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r1 = r1.mKeyguardViewControllerLazy
                    java.lang.Object r1 = r1.get()
                    com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                    boolean r1 = r1.shouldDisableWindowAnimationsForUnlock()
                    r2 = 2
                    if (r1 != 0) goto L_0x0040
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.keyguard.mediator.ScreenOnCoordinator r1 = r1.mScreenOnCoordinator
                    java.util.Objects.requireNonNull(r1)
                    boolean r1 = r1.wakeAndUnlocking
                    if (r1 == 0) goto L_0x0041
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r1 = r1.mWallpaperSupportsAmbientMode
                    if (r1 != 0) goto L_0x0041
                L_0x0040:
                    r0 = r2
                L_0x0041:
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r1 = r1.mKeyguardViewControllerLazy
                    java.lang.Object r1 = r1.get()
                    com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                    boolean r1 = r1.isGoingToNotificationShade()
                    if (r1 != 0) goto L_0x0062
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.keyguard.mediator.ScreenOnCoordinator r1 = r1.mScreenOnCoordinator
                    java.util.Objects.requireNonNull(r1)
                    boolean r1 = r1.wakeAndUnlocking
                    if (r1 == 0) goto L_0x0064
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    boolean r1 = r1.mWallpaperSupportsAmbientMode
                    if (r1 == 0) goto L_0x0064
                L_0x0062:
                    r0 = r0 | 1
                L_0x0064:
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r1 = r1.mKeyguardViewControllerLazy
                    java.lang.Object r1 = r1.get()
                    com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                    boolean r1 = r1.isUnlockWithWallpaper()
                    if (r1 == 0) goto L_0x0076
                    r0 = r0 | 4
                L_0x0076:
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r1 = r1.mKeyguardViewControllerLazy
                    java.lang.Object r1 = r1.get()
                    com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                    boolean r1 = r1.shouldSubtleWindowAnimationsForUnlock()
                    if (r1 == 0) goto L_0x0088
                    r0 = r0 | 8
                L_0x0088:
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    com.android.keyguard.KeyguardUpdateMonitor r1 = r1.mUpdateMonitor
                    java.util.Objects.requireNonNull(r1)
                    r3 = 1
                    r1.mKeyguardGoingAway = r3
                    r1.updateBiometricListeningState(r2)
                    com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    dagger.Lazy<com.android.keyguard.KeyguardViewController> r1 = r1.mKeyguardViewControllerLazy
                    java.lang.Object r1 = r1.get()
                    com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                    r1.setKeyguardGoingAwayState(r3)
                    com.android.systemui.keyguard.KeyguardViewMediator r4 = com.android.systemui.keyguard.KeyguardViewMediator.this
                    java.util.concurrent.Executor r4 = r4.mUiBgExecutor
                    com.android.systemui.keyguard.KeyguardViewMediator$10$$ExternalSyntheticLambda0 r1 = new com.android.systemui.keyguard.KeyguardViewMediator$10$$ExternalSyntheticLambda0
                    r1.<init>(r0)
                    r4.execute(r1)
                    android.os.Trace.endSection()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.C085110.run():void");
            }
        };
        this.mHideAnimationFinishedRunnable = new QSTileImpl$$ExternalSyntheticLambda0(this, 2);
        this.mFalsingCollector = falsingCollector;
        this.mLockPatternUtils = lockPatternUtils;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardViewControllerLazy = lazy;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mNotificationShadeDepthController = lazy3;
        this.mUiBgExecutor = executor;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mPM = powerManager;
        this.mTrustManager = trustManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        DumpManager dumpManager2 = dumpManager;
        dumpManager.registerDumpable(getClass().getName(), this);
        this.mDeviceConfig = deviceConfigProxy;
        this.mScreenOnCoordinator = screenOnCoordinator;
        this.mNotificationShadeWindowControllerLazy = lazy4;
        Objects.requireNonNull(deviceConfigProxy);
        this.mShowHomeOverLockscreen = DeviceConfig.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        DeviceConfigProxy deviceConfigProxy2 = this.mDeviceConfig;
        C08619 r52 = this.mHandler;
        Objects.requireNonNull(r52);
        MediaRoute2Provider$$ExternalSyntheticLambda0 mediaRoute2Provider$$ExternalSyntheticLambda0 = new MediaRoute2Provider$$ExternalSyntheticLambda0(r52);
        Objects.requireNonNull(deviceConfigProxy2);
        DeviceConfig.addOnPropertiesChangedListener("systemui", mediaRoute2Provider$$ExternalSyntheticLambda0, r3);
        this.mInGestureNavigationMode = R$color.isGesturalMode(navigationModeController.addListener(new KeyguardViewMediator$$ExternalSyntheticLambda1(this)));
        this.mDozeParameters = dozeParameters;
        this.mStatusBarStateController = sysuiStatusBarStateController2;
        sysuiStatusBarStateController2.addCallback(this);
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUnlockAnimationControllerLazy = lazy2;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mActivityLaunchAnimator = lazy5;
        this.mPowerButtonY = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.physical_power_button_center_screen_location_y);
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
    }

    public final void handleStartKeyguardExitAnimation(long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        long j3 = j;
        long j4 = j2;
        RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr;
        final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
        Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j4);
        }
        synchronized (this) {
            if (this.mHiding || this.mSurfaceBehindRemoteAnimationRequested || this.mKeyguardStateController.isFlingingToDismissKeyguardDuringSwipeGesture()) {
                this.mHiding = false;
                IRemoteAnimationRunner iRemoteAnimationRunner = this.mKeyguardExitAnimationRunner;
                this.mKeyguardExitAnimationRunner = null;
                ScreenOnCoordinator screenOnCoordinator = this.mScreenOnCoordinator;
                Objects.requireNonNull(screenOnCoordinator);
                if (screenOnCoordinator.wakeAndUnlocking) {
                    this.mKeyguardViewControllerLazy.get().getViewRootImpl().setReportNextDraw();
                    this.mScreenOnCoordinator.setWakeAndUnlocking(false);
                }
                LatencyTracker.getInstance(this.mContext).onActionEnd(11);
                boolean z = KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation;
                if (z && iRemoteAnimationRunner != null && iRemoteAnimationFinishedCallback2 != null) {
                    C085211 r7 = new IRemoteAnimationFinishedCallback() {
                        public final IBinder asBinder() {
                            return iRemoteAnimationFinishedCallback2.asBinder();
                        }

                        public final void onAnimationFinished() throws RemoteException {
                            try {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            } catch (RemoteException e) {
                                Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e);
                            }
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            boolean z = KeyguardViewMediator.DEBUG;
                            keyguardViewMediator.onKeyguardExitFinished();
                            KeyguardViewMediator.this.mKeyguardViewControllerLazy.get().hide(0, 0);
                            KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                        }
                    };
                    try {
                        this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf("RunRemoteAnimation"));
                        iRemoteAnimationRunner.onAnimationStart(7, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, r7);
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onAnimationStart", e);
                    }
                } else if (!z || this.mStatusBarStateController.leaveOpenOnKeyguardHide() || remoteAnimationTargetArr4 == null || remoteAnimationTargetArr4.length <= 0) {
                    this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf("RemoteAnimationDisabled"));
                    this.mKeyguardViewControllerLazy.get().hide(j, j4);
                    this.mContext.getMainExecutor().execute(new ScreenDecorations$$ExternalSyntheticLambda0(this, iRemoteAnimationFinishedCallback2, remoteAnimationTargetArr4, 1));
                    onKeyguardExitFinished();
                } else {
                    this.mSurfaceBehindRemoteAnimationFinishedCallback = iRemoteAnimationFinishedCallback2;
                    this.mSurfaceBehindRemoteAnimationRunning = true;
                    this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf("DismissPanel"));
                    this.mKeyguardUnlockAnimationControllerLazy.get().notifyStartSurfaceBehindRemoteAnimation(remoteAnimationTargetArr4[0], j, this.mSurfaceBehindRemoteAnimationRequested);
                }
            } else {
                if (iRemoteAnimationFinishedCallback2 != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e2) {
                        Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e2);
                    }
                }
                setShowingLocked(this.mShowing, true);
                return;
            }
        }
        Trace.endSection();
    }

    public final void onBootCompleted() {
        synchronized (this) {
            if (this.mContext.getResources().getBoolean(17891665)) {
                UserSwitcherController userSwitcherController = this.mUserSwitcherController;
                Objects.requireNonNull(userSwitcherController);
                if (userSwitcherController.isDeviceAllowedToAddGuest()) {
                    userSwitcherController.guaranteeGuestPresent();
                } else {
                    userSwitcherController.mDeviceProvisionedController.addCallback(userSwitcherController.mGuaranteeGuestPresentAfterProvisioned);
                }
            }
            this.mBootCompleted = true;
            adjustStatusBarLocked(false, true);
            if (this.mBootSendUserPresent) {
                sendUserPresentBroadcast();
            }
        }
    }

    public final void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    public final void sendUserPresentBroadcast() {
        synchronized (this) {
            if (this.mBootCompleted) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda4(this, (UserManager) this.mContext.getSystemService("user"), new UserHandle(currentUser), currentUser));
            } else {
                this.mBootSendUserPresent = true;
            }
        }
    }

    public final void start() {
        synchronized (this) {
            setupLocked();
        }
    }

    public final void startKeyguardExitAnimation(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(12, new StartKeyguardExitAnimParams(j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback)));
        Trace.endSection();
    }

    public static class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }
    }

    public static class StartKeyguardExitAnimParams {
        public long fadeoutDuration;
        public RemoteAnimationTarget[] mApps;
        public IRemoteAnimationFinishedCallback mFinishedCallback;
        public RemoteAnimationTarget[] mNonApps;
        public RemoteAnimationTarget[] mWallpapers;
        public long startTime;

        public StartKeyguardExitAnimParams(long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.startTime = j;
            this.fadeoutDuration = j2;
            this.mApps = remoteAnimationTargetArr;
            this.mWallpapers = remoteAnimationTargetArr2;
            this.mNonApps = remoteAnimationTargetArr3;
            this.mFinishedCallback = iRemoteAnimationFinishedCallback;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0030, code lost:
        if (r5 != false) goto L_0x0032;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void adjustStatusBarLocked(boolean r4, boolean r5) {
        /*
            r3 = this;
            android.app.StatusBarManager r0 = r3.mStatusBarManager
            if (r0 != 0) goto L_0x0011
            android.content.Context r0 = r3.mContext
            java.lang.String r1 = "statusbar"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.app.StatusBarManager r0 = (android.app.StatusBarManager) r0
            r3.mStatusBarManager = r0
        L_0x0011:
            android.app.StatusBarManager r0 = r3.mStatusBarManager
            java.lang.String r1 = "KeyguardViewMediator"
            if (r0 != 0) goto L_0x001d
            java.lang.String r3 = "Could not get status bar manager"
            android.util.Log.w(r1, r3)
            goto L_0x0084
        L_0x001d:
            r2 = 0
            if (r5 == 0) goto L_0x0023
            r0.disable(r2)
        L_0x0023:
            if (r4 != 0) goto L_0x0032
            boolean r5 = r3.mShowing
            if (r5 == 0) goto L_0x002f
            boolean r5 = r3.mOccluded
            if (r5 != 0) goto L_0x002f
            r5 = 1
            goto L_0x0030
        L_0x002f:
            r5 = r2
        L_0x0030:
            if (r5 == 0) goto L_0x003f
        L_0x0032:
            boolean r5 = r3.mShowHomeOverLockscreen
            if (r5 == 0) goto L_0x003a
            boolean r5 = r3.mInGestureNavigationMode
            if (r5 != 0) goto L_0x003c
        L_0x003a:
            r2 = 2097152(0x200000, float:2.938736E-39)
        L_0x003c:
            r5 = 16777216(0x1000000, float:2.3509887E-38)
            r2 = r2 | r5
        L_0x003f:
            boolean r5 = DEBUG
            if (r5 == 0) goto L_0x007f
            java.lang.String r5 = "adjustStatusBarLocked: mShowing="
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            boolean r0 = r3.mShowing
            r5.append(r0)
            java.lang.String r0 = " mOccluded="
            r5.append(r0)
            boolean r0 = r3.mOccluded
            r5.append(r0)
            java.lang.String r0 = " isSecure="
            r5.append(r0)
            boolean r0 = r3.isSecure()
            r5.append(r0)
            java.lang.String r0 = " force="
            r5.append(r0)
            r5.append(r4)
            java.lang.String r4 = " --> flags=0x"
            r5.append(r4)
            java.lang.String r4 = java.lang.Integer.toHexString(r2)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.d(r1, r4)
        L_0x007f:
            android.app.StatusBarManager r3 = r3.mStatusBarManager
            r3.disable(r2)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.adjustStatusBarLocked(boolean, boolean):void");
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf(String str) {
        return InteractionJankMonitor.Configuration.Builder.withView(29, this.mKeyguardViewControllerLazy.get().getViewRootImpl().getView()).setTag(str);
    }

    public final void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
    }

    public final void doKeyguardForChildProfilesLocked() {
        for (int i : UserManager.get(this.mContext).getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                this.mTrustManager.setDeviceLockedForUser(i, true);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0059, code lost:
        if (r0.mDeviceProvisioned == false) goto L_0x005b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void doKeyguardLocked(android.os.Bundle r8) {
        /*
            r7 = this;
            boolean r0 = com.android.keyguard.KeyguardUpdateMonitor.CORE_APPS_ONLY
            java.lang.String r1 = "KeyguardViewMediator"
            if (r0 == 0) goto L_0x0010
            boolean r7 = DEBUG
            if (r7 == 0) goto L_0x000f
            java.lang.String r7 = "doKeyguard: not showing because booting to cryptkeeper"
            android.util.Log.d(r1, r7)
        L_0x000f:
            return
        L_0x0010:
            boolean r0 = r7.mExternallyEnabled
            r2 = 1
            if (r0 != 0) goto L_0x0021
            boolean r8 = DEBUG
            if (r8 == 0) goto L_0x001e
            java.lang.String r8 = "doKeyguard: not showing because externally disabled"
            android.util.Log.d(r1, r8)
        L_0x001e:
            r7.mNeedToReshowWhenReenabled = r2
            return
        L_0x0021:
            boolean r0 = r7.mShowing
            if (r0 == 0) goto L_0x0040
            dagger.Lazy<com.android.keyguard.KeyguardViewController> r0 = r7.mKeyguardViewControllerLazy
            java.lang.Object r0 = r0.get()
            com.android.keyguard.KeyguardViewController r0 = (com.android.keyguard.KeyguardViewController) r0
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x0040
            boolean r8 = DEBUG
            if (r8 == 0) goto L_0x003c
            java.lang.String r8 = "doKeyguard: not showing because it is already showing"
            android.util.Log.d(r1, r8)
        L_0x003c:
            r7.resetStateLocked()
            return
        L_0x0040:
            boolean r0 = android.os.UserManager.isSplitSystemUser()
            r3 = 0
            if (r0 == 0) goto L_0x004f
            int r0 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            if (r0 != 0) goto L_0x004f
            r0 = r2
            goto L_0x0050
        L_0x004f:
            r0 = r3
        L_0x0050:
            if (r0 == 0) goto L_0x005b
            com.android.keyguard.KeyguardUpdateMonitor r0 = r7.mUpdateMonitor
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mDeviceProvisioned
            if (r0 != 0) goto L_0x00dd
        L_0x005b:
            java.lang.String r0 = "keyguard.no_require_sim"
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r3)
            r0 = r0 ^ r2
            com.android.keyguard.KeyguardUpdateMonitor r4 = r7.mUpdateMonitor
            int r4 = r4.getNextSubIdForState(r2)
            boolean r4 = android.telephony.SubscriptionManager.isValidSubscriptionId(r4)
            com.android.keyguard.KeyguardUpdateMonitor r5 = r7.mUpdateMonitor
            r6 = 7
            int r5 = r5.getNextSubIdForState(r6)
            boolean r5 = android.telephony.SubscriptionManager.isValidSubscriptionId(r5)
            com.android.keyguard.KeyguardUpdateMonitor r6 = r7.mUpdateMonitor
            boolean r6 = r6.isSimPinSecure()
            if (r6 != 0) goto L_0x0088
            if (r4 != 0) goto L_0x0083
            if (r5 == 0) goto L_0x0086
        L_0x0083:
            if (r0 == 0) goto L_0x0086
            goto L_0x0088
        L_0x0086:
            r0 = r3
            goto L_0x0089
        L_0x0088:
            r0 = r2
        L_0x0089:
            if (r0 != 0) goto L_0x009b
            boolean r4 = r7.shouldWaitForProvisioning()
            if (r4 == 0) goto L_0x009b
            boolean r7 = DEBUG
            if (r7 == 0) goto L_0x009a
            java.lang.String r7 = "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing"
            android.util.Log.d(r1, r7)
        L_0x009a:
            return
        L_0x009b:
            if (r8 == 0) goto L_0x00a6
            java.lang.String r4 = "force_show"
            boolean r4 = r8.getBoolean(r4, r3)
            if (r4 == 0) goto L_0x00a6
            goto L_0x00a7
        L_0x00a6:
            r2 = r3
        L_0x00a7:
            com.android.internal.widget.LockPatternUtils r4 = r7.mLockPatternUtils
            int r5 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            boolean r4 = r4.isLockScreenDisabled(r5)
            if (r4 == 0) goto L_0x00c1
            if (r0 != 0) goto L_0x00c1
            if (r2 != 0) goto L_0x00c1
            boolean r7 = DEBUG
            if (r7 == 0) goto L_0x00c0
            java.lang.String r7 = "doKeyguard: not showing because lockscreen is off"
            android.util.Log.d(r1, r7)
        L_0x00c0:
            return
        L_0x00c1:
            com.android.internal.widget.LockPatternUtils r0 = r7.mLockPatternUtils
            int r2 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            boolean r0 = r0.checkVoldPassword(r2)
            if (r0 == 0) goto L_0x00dd
            boolean r8 = DEBUG
            if (r8 == 0) goto L_0x00d6
            java.lang.String r8 = "Not showing lock screen since just decrypted"
            android.util.Log.d(r1, r8)
        L_0x00d6:
            r7.setShowingLocked(r3, r3)
            r7.hideLocked()
            return
        L_0x00dd:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x00e6
            java.lang.String r0 = "doKeyguard: showing the lock screen"
            android.util.Log.d(r1, r0)
        L_0x00e6:
            r7.showLocked(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.doKeyguardLocked(android.os.Bundle):void");
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mSystemReady: ");
        printWriter.println(this.mSystemReady);
        printWriter.print("  mBootCompleted: ");
        printWriter.println(this.mBootCompleted);
        printWriter.print("  mBootSendUserPresent: ");
        printWriter.println(this.mBootSendUserPresent);
        printWriter.print("  mExternallyEnabled: ");
        printWriter.println(this.mExternallyEnabled);
        printWriter.print("  mShuttingDown: ");
        printWriter.println(this.mShuttingDown);
        printWriter.print("  mNeedToReshowWhenReenabled: ");
        printWriter.println(this.mNeedToReshowWhenReenabled);
        printWriter.print("  mShowing: ");
        printWriter.println(this.mShowing);
        printWriter.print("  mInputRestricted: ");
        printWriter.println(this.mInputRestricted);
        printWriter.print("  mOccluded: ");
        printWriter.println(this.mOccluded);
        printWriter.print("  mDelayedShowingSequence: ");
        printWriter.println(this.mDelayedShowingSequence);
        printWriter.print("  mExitSecureCallback: ");
        printWriter.println((Object) null);
        printWriter.print("  mDeviceInteractive: ");
        printWriter.println(this.mDeviceInteractive);
        printWriter.print("  mGoingToSleep: ");
        printWriter.println(this.mGoingToSleep);
        printWriter.print("  mHiding: ");
        printWriter.println(this.mHiding);
        printWriter.print("  mDozing: ");
        printWriter.println(this.mDozing);
        printWriter.print("  mAodShowing: ");
        printWriter.println(this.mAodShowing);
        printWriter.print("  mWaitingUntilKeyguardVisible: ");
        printWriter.println(this.mWaitingUntilKeyguardVisible);
        printWriter.print("  mKeyguardDonePending: ");
        printWriter.println(this.mKeyguardDonePending);
        printWriter.print("  mHideAnimationRun: ");
        printWriter.println(this.mHideAnimationRun);
        printWriter.print("  mPendingReset: ");
        printWriter.println(this.mPendingReset);
        printWriter.print("  mPendingLock: ");
        printWriter.println(this.mPendingLock);
        printWriter.print("  wakeAndUnlocking: ");
        ScreenOnCoordinator screenOnCoordinator = this.mScreenOnCoordinator;
        Objects.requireNonNull(screenOnCoordinator);
        printWriter.println(screenOnCoordinator.wakeAndUnlocking);
    }

    public final long getLockTimeout(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long j = (long) Settings.Secure.getInt(contentResolver, "lock_screen_lock_after_timeout", 5000);
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock((ComponentName) null, i);
        if (maximumTimeToLock <= 0) {
            return j;
        }
        return Math.max(Math.min(maximumTimeToLock - Math.max((long) Settings.System.getInt(contentResolver, "screen_off_timeout", 30000), 0), j), 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0070, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0073, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleHide() {
        /*
            r5 = this;
            java.lang.String r0 = "KeyguardViewMediator#handleHide"
            android.os.Trace.beginSection(r0)
            boolean r0 = r5.mAodShowing
            if (r0 != 0) goto L_0x000d
            boolean r0 = r5.mDreamOverlayShowing
            if (r0 == 0) goto L_0x0021
        L_0x000d:
            android.content.Context r0 = r5.mContext
            java.lang.Class<android.os.PowerManager> r1 = android.os.PowerManager.class
            java.lang.Object r0 = r0.getSystemService(r1)
            android.os.PowerManager r0 = (android.os.PowerManager) r0
            long r1 = android.os.SystemClock.uptimeMillis()
            r3 = 4
            java.lang.String r4 = "com.android.systemui:BOUNCER_DOZING"
            r0.wakeUp(r1, r3, r4)
        L_0x0021:
            monitor-enter(r5)
            boolean r0 = DEBUG     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x002d
            java.lang.String r1 = "KeyguardViewMediator"
            java.lang.String r2 = "handleHide"
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x003c }
        L_0x002d:
            boolean r1 = android.os.UserManager.isSplitSystemUser()     // Catch:{ all -> 0x003c }
            r2 = 1
            if (r1 == 0) goto L_0x003e
            int r1 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()     // Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x003e
            r1 = r2
            goto L_0x003f
        L_0x003c:
            r0 = move-exception
            goto L_0x0074
        L_0x003e:
            r1 = 0
        L_0x003f:
            if (r1 == 0) goto L_0x004f
            if (r0 == 0) goto L_0x004a
            java.lang.String r0 = "KeyguardViewMediator"
            java.lang.String r1 = "Split system user, quit unlocking."
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x003c }
        L_0x004a:
            r0 = 0
            r5.mKeyguardExitAnimationRunner = r0     // Catch:{ all -> 0x003c }
            monitor-exit(r5)     // Catch:{ all -> 0x003c }
            return
        L_0x004f:
            r5.mHiding = r2     // Catch:{ all -> 0x003c }
            boolean r0 = r5.mShowing     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x005f
            boolean r0 = r5.mOccluded     // Catch:{ all -> 0x003c }
            if (r0 != 0) goto L_0x005f
            com.android.systemui.keyguard.KeyguardViewMediator$10 r0 = r5.mKeyguardGoingAwayRunnable     // Catch:{ all -> 0x003c }
            r0.run()     // Catch:{ all -> 0x003c }
            goto L_0x006f
        L_0x005f:
            dagger.Lazy<com.android.systemui.statusbar.NotificationShadeWindowController> r0 = r5.mNotificationShadeWindowControllerLazy     // Catch:{ all -> 0x003c }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x003c }
            com.android.systemui.statusbar.NotificationShadeWindowController r0 = (com.android.systemui.statusbar.NotificationShadeWindowController) r0     // Catch:{ all -> 0x003c }
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4 r1 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4     // Catch:{ all -> 0x003c }
            r1.<init>(r5, r2)     // Catch:{ all -> 0x003c }
            r0.batchApplyWindowLayoutParams(r1)     // Catch:{ all -> 0x003c }
        L_0x006f:
            monitor-exit(r5)     // Catch:{ all -> 0x003c }
            android.os.Trace.endSection()
            return
        L_0x0074:
            monitor-exit(r5)     // Catch:{ all -> 0x003c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.handleHide():void");
    }

    public final void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda2(this, currentUser, 0));
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "handleKeyguardDone");
        }
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (this.mGoingToSleep) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            keyguardUpdateMonitor.clearBiometricRecognized(currentUser);
            Log.i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
            return;
        }
        handleHide();
        KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.mUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor2);
        keyguardUpdateMonitor2.clearBiometricRecognized(currentUser);
        Trace.endSection();
    }

    public final void hideLocked() {
        Trace.beginSection("KeyguardViewMediator#hideLocked");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "hideLocked");
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        Trace.endSection();
    }

    public final boolean isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe() {
        if (this.mSurfaceBehindRemoteAnimationRunning || this.mKeyguardStateController.isFlingingToDismissKeyguard()) {
            return true;
        }
        return false;
    }

    public final void maybeSendUserPresentBroadcast() {
        if (this.mSystemReady && this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
            sendUserPresentBroadcast();
        } else if (this.mSystemReady && shouldWaitForProvisioning()) {
            this.mLockPatternUtils.userPresent(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public final void notifyTrustedChangedLocked(boolean z) {
        for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
            try {
                this.mKeyguardStateCallbacks.get(size).onTrustedChanged(z);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(size);
                }
            }
        }
    }

    public final void onDozeAmountChanged(float f, float f2) {
        if (this.mAnimatingScreenOff && this.mDozing && f == 1.0f) {
            this.mAnimatingScreenOff = false;
            setShowingLocked(this.mShowing, true);
        }
    }

    public final void onKeyguardExitFinished() {
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState)) {
            playSound(this.mUnlockSoundId);
        }
        setShowingLocked(false, false);
        this.mScreenOnCoordinator.setWakeAndUnlocking(false);
        DismissCallbackRegistry dismissCallbackRegistry = this.mDismissCallbackRegistry;
        Objects.requireNonNull(dismissCallbackRegistry);
        int size = dismissCallbackRegistry.mDismissCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                DismissCallbackWrapper dismissCallbackWrapper = dismissCallbackRegistry.mDismissCallbacks.get(size);
                Executor executor = dismissCallbackRegistry.mUiBgExecutor;
                Objects.requireNonNull(dismissCallbackWrapper);
                executor.execute(new StatusBar$$ExternalSyntheticLambda19(dismissCallbackWrapper, 2));
            } else {
                dismissCallbackRegistry.mDismissCallbacks.clear();
                resetKeyguardDonePendingLocked();
                this.mHideAnimationRun = false;
                adjustStatusBarLocked(false, false);
                sendUserPresentBroadcast();
                return;
            }
        }
    }

    public final void onKeyguardExitRemoteAnimationFinished(boolean z) {
        if (this.mSurfaceBehindRemoteAnimationRunning || this.mSurfaceBehindRemoteAnimationRequested) {
            this.mKeyguardViewControllerLazy.get().blockPanelExpansionFromCurrentTouch();
            boolean z2 = this.mShowing;
            InteractionJankMonitor.getInstance().end(29);
            DejankUtils.postAfterTraversal(new KeyguardViewMediator$$ExternalSyntheticLambda6(this, z2, z));
            KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.mKeyguardUnlockAnimationControllerLazy.get();
            Objects.requireNonNull(keyguardUnlockAnimationController);
            keyguardUnlockAnimationController.handler.removeCallbacksAndMessages((Object) null);
            keyguardUnlockAnimationController.setSurfaceBehindAppearAmount(1.0f);
            ILauncherUnlockAnimationController iLauncherUnlockAnimationController = keyguardUnlockAnimationController.launcherUnlockController;
            if (iLauncherUnlockAnimationController != null) {
                iLauncherUnlockAnimationController.setUnlockAmount(1.0f);
            }
            keyguardUnlockAnimationController.smartspaceDestBounds.setEmpty();
            keyguardUnlockAnimationController.surfaceBehindRemoteAnimationTarget = null;
            keyguardUnlockAnimationController.surfaceBehindParams = null;
            keyguardUnlockAnimationController.playingCannedUnlockAnimation = false;
            keyguardUnlockAnimationController.unlockingToLauncherWithInWindowAnimations = false;
            keyguardUnlockAnimationController.unlockingWithSmartspaceTransition = false;
            keyguardUnlockAnimationController.smartspaceUnlockProgress = 0.0f;
            View view = keyguardUnlockAnimationController.lockscreenSmartspace;
            if (view != null) {
                view.post(new KeyguardUnlockAnimationController$resetSmartspaceTransition$1(keyguardUnlockAnimationController));
            }
            Iterator<KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener> it = keyguardUnlockAnimationController.listeners.iterator();
            while (it.hasNext()) {
                it.next().onUnlockAnimationFinished();
            }
        }
    }

    public final void onWakeAndUnlocking() {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        this.mScreenOnCoordinator.setWakeAndUnlocking(true);
        Trace.beginSection("KeyguardViewMediator#keyguardDone");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "keyguardDone()");
        }
        userActivity();
        EventLog.writeEvent(70000, 2);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7));
        Trace.endSection();
        Trace.endSection();
    }

    public final void playSound(int i) {
        if (i != 0 && Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled", 1) == 1) {
            this.mLockSounds.stop(this.mLockSoundStreamId);
            if (this.mAudioManager == null) {
                AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
                this.mAudioManager = audioManager;
                if (audioManager != null) {
                    this.mUiSoundsStreamType = audioManager.getUiSoundsStreamType();
                } else {
                    return;
                }
            }
            this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda3(this, i));
        }
    }

    public final void resetStateLocked() {
        if (DEBUG) {
            Log.e("KeyguardViewMediator", "resetStateLocked");
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final void setBlursDisabledForAppLaunch(boolean z) {
        boolean z2;
        NotificationShadeDepthController notificationShadeDepthController = this.mNotificationShadeDepthController.get();
        Objects.requireNonNull(notificationShadeDepthController);
        if (notificationShadeDepthController.blursDisabledForAppLaunch != z) {
            notificationShadeDepthController.blursDisabledForAppLaunch = z;
            notificationShadeDepthController.scheduleUpdate((View) null);
            boolean z3 = true;
            if (notificationShadeDepthController.shadeExpansion == 0.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                NotificationShadeDepthController.DepthAnimation depthAnimation = notificationShadeDepthController.shadeAnimation;
                Objects.requireNonNull(depthAnimation);
                if (depthAnimation.radius != 0.0f) {
                    z3 = false;
                }
                if (z3) {
                    return;
                }
            }
            if (z) {
                NotificationShadeDepthController.DepthAnimation.animateTo$default(notificationShadeDepthController.shadeAnimation, 0);
                NotificationShadeDepthController.DepthAnimation depthAnimation2 = notificationShadeDepthController.shadeAnimation;
                Objects.requireNonNull(depthAnimation2);
                SpringAnimation springAnimation = depthAnimation2.springAnimation;
                Objects.requireNonNull(springAnimation);
                if (springAnimation.mRunning) {
                    depthAnimation2.springAnimation.skipToEnd();
                }
            }
        }
    }

    public final void setOccluded(boolean z, boolean z2) {
        Trace.beginSection("KeyguardViewMediator#setOccluded");
        if (DEBUG) {
            ViewCompat$$ExternalSyntheticLambda0.m12m("setOccluded ", z, "KeyguardViewMediator");
        }
        this.mHandler.removeMessages(9);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, z ? 1 : 0, z2 ? 1 : 0));
        Trace.endSection();
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setShowingLocked(boolean r5, boolean r6) {
        /*
            r4 = this;
            boolean r0 = r4.mDozing
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0011
            com.android.keyguard.mediator.ScreenOnCoordinator r0 = r4.mScreenOnCoordinator
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.wakeAndUnlocking
            if (r0 != 0) goto L_0x0011
            r0 = r1
            goto L_0x0012
        L_0x0011:
            r0 = r2
        L_0x0012:
            boolean r3 = r4.mShowing
            if (r5 != r3) goto L_0x001e
            boolean r3 = r4.mAodShowing
            if (r0 != r3) goto L_0x001e
            if (r6 == 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r1 = r2
        L_0x001e:
            r4.mShowing = r5
            r4.mAodShowing = r0
            if (r1 == 0) goto L_0x0044
            com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5 r6 = new com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5
            r6.<init>(r4, r5)
            com.android.systemui.DejankUtils.whitelistIpcs((java.lang.Runnable) r6)
            r4.updateInputRestrictedLocked()
            java.util.concurrent.Executor r6 = r4.mUiBgExecutor
            com.android.wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0 r1 = new com.android.wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0
            r2 = 3
            r1.<init>(r4, r2)
            r6.execute(r1)
            java.util.concurrent.Executor r4 = r4.mUiBgExecutor
            com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda7 r6 = new com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda7
            r6.<init>(r5, r0)
            r4.execute(r6)
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.setShowingLocked(boolean, boolean):void");
    }

    public final void setupLocked() {
        boolean z;
        PowerManager.WakeLock newWakeLock = this.mPM.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock = newWakeLock;
        boolean z2 = false;
        newWakeLock.setReferenceCounted(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter2, "com.android.systemui.permission.SELF", (Handler) null, 2);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        int currentUser = ActivityManager.getCurrentUser();
        boolean z3 = KeyguardUpdateMonitor.DEBUG;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = currentUser;
        }
        try {
            z = this.mContext.getPackageManager().getServiceInfo(new ComponentName(this.mContext, KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            z = true;
        }
        if (z) {
            if (!shouldWaitForProvisioning() && !this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                z2 = true;
            }
            setShowingLocked(z2, true);
        } else {
            setShowingLocked(false, true);
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m9m("failed to load lock sound from ", string, "KeyguardViewMediator");
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m9m("failed to load unlock sound from ", string2, "KeyguardViewMediator");
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m9m("failed to load trusted sound from ", string3, "KeyguardViewMediator");
        }
        this.mLockSoundVolume = (float) Math.pow(10.0d, (double) (((float) this.mContext.getResources().getInteger(17694849)) / 20.0f));
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, 17432687);
        new WorkLockActivityController(this.mContext, TaskStackChangeListeners.INSTANCE, ActivityTaskManager.getService());
    }

    public final boolean shouldWaitForProvisioning() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        if (keyguardUpdateMonitor.mDeviceProvisioned || isSecure()) {
            return false;
        }
        return true;
    }

    public final void showLocked(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showLocked acquiring mShowKeyguardWakeLock");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "showLocked");
        }
        this.mShowKeyguardWakeLock.acquire();
        this.mHandler.sendMessageAtFrontOfQueue(this.mHandler.obtainMessage(1, bundle));
        Trace.endSection();
    }

    public final void tryKeyguardDone() {
        boolean z = DEBUG;
        if (z) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("tryKeyguardDone: pending - ");
            m.append(this.mKeyguardDonePending);
            m.append(", animRan - ");
            m.append(this.mHideAnimationRun);
            m.append(" animRunning - ");
            KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(m, this.mHideAnimationRunning, "KeyguardViewMediator");
        }
        if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
            handleKeyguardDone();
        } else if (!this.mHideAnimationRun) {
            if (z) {
                Log.d("KeyguardViewMediator", "tryKeyguardDone: starting pre-hide animation");
            }
            this.mHideAnimationRun = true;
            this.mHideAnimationRunning = true;
            this.mKeyguardViewControllerLazy.get().startPreHideAnimation(this.mHideAnimationFinishedRunnable);
        }
    }

    public final void updateInputRestrictedLocked() {
        boolean z;
        if (this.mShowing || this.mNeedToReshowWhenReenabled) {
            z = true;
        } else {
            z = false;
        }
        if (this.mInputRestricted != z) {
            this.mInputRestricted = z;
            for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = this.mKeyguardStateCallbacks.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(z);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        this.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                    }
                }
            }
        }
    }

    public final void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }

    /* renamed from: -$$Nest$mhandleKeyguardDoneDrawing  reason: not valid java name */
    public static void m202$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator keyguardViewMediator) {
        Objects.requireNonNull(keyguardViewMediator);
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (keyguardViewMediator) {
            boolean z = DEBUG;
            if (z) {
                Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing");
            }
            if (keyguardViewMediator.mWaitingUntilKeyguardVisible) {
                if (z) {
                    Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                }
                keyguardViewMediator.mWaitingUntilKeyguardVisible = false;
                keyguardViewMediator.notifyAll();
                keyguardViewMediator.mHandler.removeMessages(8);
            }
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleNotifyFinishedGoingToSleep  reason: not valid java name */
    public static void m203$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        Objects.requireNonNull(keyguardViewMediator);
        synchronized (keyguardViewMediator) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            }
            keyguardViewMediator.mKeyguardViewControllerLazy.get().onFinishedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedGoingToSleep  reason: not valid java name */
    public static void m204$$Nest$mhandleNotifyStartedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        Objects.requireNonNull(keyguardViewMediator);
        synchronized (keyguardViewMediator) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
            }
            keyguardViewMediator.mKeyguardViewControllerLazy.get().onStartedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp  reason: not valid java name */
    public static void m205$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator keyguardViewMediator) {
        Objects.requireNonNull(keyguardViewMediator);
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (keyguardViewMediator) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyWakingUp");
            }
            keyguardViewMediator.mKeyguardViewControllerLazy.get().onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleSetOccluded  reason: not valid java name */
    public static void m206$$Nest$mhandleSetOccluded(KeyguardViewMediator keyguardViewMediator, boolean z, boolean z2) {
        boolean z3;
        Objects.requireNonNull(keyguardViewMediator);
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        synchronized (keyguardViewMediator) {
            try {
                if (keyguardViewMediator.mHiding && z) {
                    keyguardViewMediator.startKeyguardExitAnimation(0, 0, 0, (RemoteAnimationTarget[]) null, (RemoteAnimationTarget[]) null, (RemoteAnimationTarget[]) null, (IRemoteAnimationFinishedCallback) null);
                }
                if (keyguardViewMediator.mOccluded != z) {
                    keyguardViewMediator.mOccluded = z;
                    KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
                    Objects.requireNonNull(keyguardUpdateMonitor);
                    keyguardUpdateMonitor.mKeyguardOccluded = z;
                    keyguardUpdateMonitor.updateBiometricListeningState(2);
                    KeyguardViewController keyguardViewController = keyguardViewMediator.mKeyguardViewControllerLazy.get();
                    if (!z2 || !keyguardViewMediator.mDeviceInteractive) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    keyguardViewController.setOccluded(z, z3);
                    keyguardViewMediator.adjustStatusBarLocked(false, false);
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleSystemReady  reason: not valid java name */
    public static void m207$$Nest$mhandleSystemReady(KeyguardViewMediator keyguardViewMediator) {
        Objects.requireNonNull(keyguardViewMediator);
        synchronized (keyguardViewMediator) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "onSystemReady");
            }
            keyguardViewMediator.mSystemReady = true;
            keyguardViewMediator.doKeyguardLocked((Bundle) null);
            keyguardViewMediator.mUpdateMonitor.registerCallback(keyguardViewMediator.mUpdateCallback);
            DreamOverlayStateController dreamOverlayStateController = keyguardViewMediator.mDreamOverlayStateController;
            C08542 r1 = keyguardViewMediator.mDreamOverlayStateCallback;
            Objects.requireNonNull(dreamOverlayStateController);
            dreamOverlayStateController.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda20(dreamOverlayStateController, r1, 2));
        }
        keyguardViewMediator.maybeSendUserPresentBroadcast();
    }

    public final void doKeyguardLaterLocked(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(268435456);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320));
        if (DEBUG) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("setting alarm to turn off keyguard, seq = "), this.mDelayedShowingSequence, "KeyguardViewMediator");
        }
        for (int i : UserManager.get(this.mContext).getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                long lockTimeout = getLockTimeout(i);
                if (lockTimeout == 0) {
                    doKeyguardForChildProfilesLocked();
                } else {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() + lockTimeout;
                    Intent intent2 = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                    intent2.putExtra("seq", this.mDelayedProfileShowingSequence);
                    intent2.putExtra("android.intent.extra.USER_ID", i);
                    intent2.addFlags(268435456);
                    this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime2, PendingIntent.getBroadcast(this.mContext, 0, intent2, 301989888));
                }
            }
        }
    }

    public final boolean isSecure() {
        if (this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser()) || this.mUpdateMonitor.isSimPinSecure()) {
            return true;
        }
        return false;
    }
}
