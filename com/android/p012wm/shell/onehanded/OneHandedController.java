package com.android.p012wm.shell.onehanded;

import android.content.ContentResolver;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.accessibility.AccessibilityManager;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.onehanded.IOneHanded;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda2;
import com.android.systemui.p006qs.QSFgsManagerFooter$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6;
import com.android.systemui.wmshell.WMShell;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda1;
import com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* renamed from: com.android.wm.shell.onehanded.OneHandedController */
public final class OneHandedController implements RemoteCallable<OneHandedController>, DisplayChangeController.OnDisplayChangingListener {
    public final AccessibilityManager mAccessibilityManager;
    public C18772 mAccessibilityStateChangeListener;
    public final C18805 mActivatedObserver;
    public Context mContext;
    public OneHandedDisplayAreaOrganizer mDisplayAreaOrganizer;
    public final DisplayController mDisplayController;
    public final C18761 mDisplaysChangedListener;
    public final C18805 mEnabledObserver;
    public OneHandedEventCallback mEventCallback;
    public final OneHandedImpl mImpl = new OneHandedImpl();
    public volatile boolean mIsOneHandedEnabled;
    public boolean mIsShortcutEnabled;
    public volatile boolean mIsSwipeToNotificationEnabled;
    public boolean mKeyguardShowing;
    public boolean mLockedDisabled;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public float mOffSetFraction;
    public final OneHandedAccessibilityUtil mOneHandedAccessibilityUtil;
    public final OneHandedSettingsUtil mOneHandedSettingsUtil;
    public OneHandedUiEventLogger mOneHandedUiEventLogger;
    public final C18805 mShortcutEnabledObserver;
    public final OneHandedState mState;
    public final C18805 mSwipeToNotificationEnabledObserver;
    public boolean mTaskChangeToExit;
    public final TaskStackListenerImpl mTaskStackListener;
    public final C18794 mTaskStackListenerCallback;
    public final OneHandedTimeoutHandler mTimeoutHandler;
    public final OneHandedTouchHandler mTouchHandler;
    public final C18783 mTransitionCallBack;
    public final OneHandedTutorialHandler mTutorialHandler;
    public int mUserId;

    /* renamed from: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl */
    public class OneHandedImpl implements OneHanded {
        public static final /* synthetic */ int $r8$clinit = 0;
        public IOneHandedImpl mIOneHanded;

        public final void stopOneHanded() {
            OneHandedController.this.mMainExecutor.execute(new StandardWifiEntry$$ExternalSyntheticLambda0(this, 10));
        }

        public OneHandedImpl() {
        }

        public final IOneHanded createExternalInterface() {
            IOneHandedImpl iOneHandedImpl = this.mIOneHanded;
            if (iOneHandedImpl != null) {
                Objects.requireNonNull(iOneHandedImpl);
                iOneHandedImpl.mController = null;
            }
            IOneHandedImpl iOneHandedImpl2 = new IOneHandedImpl(OneHandedController.this);
            this.mIOneHanded = iOneHandedImpl2;
            return iOneHandedImpl2;
        }

        public final void onConfigChanged(Configuration configuration) {
            OneHandedController.this.mMainExecutor.execute(new ScreenDecorations$2$$ExternalSyntheticLambda0(this, configuration, 3));
        }

        public final void onKeyguardVisibilityChanged(boolean z) {
            OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1(this, z));
        }

        public final void onUserSwitch(int i) {
            OneHandedController.this.mMainExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda2(this, i, 1));
        }

        public final void registerEventCallback(WMShell.C17718 r4) {
            OneHandedController.this.mMainExecutor.execute(new ScreenshotController$$ExternalSyntheticLambda6(this, r4, 3));
        }

        public final void registerTransitionCallback(OneHandedTransitionCallback oneHandedTransitionCallback) {
            OneHandedController.this.mMainExecutor.execute(new ScreenRecordTile$$ExternalSyntheticLambda1(this, oneHandedTransitionCallback, 4));
        }

        public final void setLockedDisabled(boolean z) {
            OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(this, z));
        }

        public final void stopOneHanded(int i) {
            OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(this, i));
        }
    }

    public OneHandedController(Context context, DisplayController displayController, OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer, OneHandedTouchHandler oneHandedTouchHandler, OneHandedTutorialHandler oneHandedTutorialHandler, OneHandedSettingsUtil oneHandedSettingsUtil, OneHandedAccessibilityUtil oneHandedAccessibilityUtil, OneHandedTimeoutHandler oneHandedTimeoutHandler, OneHandedState oneHandedState, InteractionJankMonitor interactionJankMonitor, OneHandedUiEventLogger oneHandedUiEventLogger, IOverlayManager iOverlayManager, TaskStackListenerImpl taskStackListenerImpl, ShellExecutor shellExecutor, Handler handler) {
        DisplayController displayController2 = displayController;
        OneHandedTouchHandler oneHandedTouchHandler2 = oneHandedTouchHandler;
        OneHandedTutorialHandler oneHandedTutorialHandler2 = oneHandedTutorialHandler;
        OneHandedTimeoutHandler oneHandedTimeoutHandler2 = oneHandedTimeoutHandler;
        OneHandedState oneHandedState2 = oneHandedState;
        TaskStackListenerImpl taskStackListenerImpl2 = taskStackListenerImpl;
        Handler handler2 = handler;
        C18761 r8 = new DisplayController.OnDisplaysChangedListener() {
            public final void onDisplayAdded(int i) {
                if (i == 0 && OneHandedController.this.isInitialized()) {
                    OneHandedController.this.updateDisplayLayout(i);
                }
            }

            public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                if (i == 0 && OneHandedController.this.isInitialized()) {
                    OneHandedController.this.updateDisplayLayout(i);
                }
            }
        };
        this.mDisplaysChangedListener = r8;
        this.mAccessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() {
            public final void onAccessibilityStateChanged(boolean z) {
                if (OneHandedController.this.isInitialized()) {
                    if (z) {
                        OneHandedController oneHandedController = OneHandedController.this;
                        OneHandedSettingsUtil oneHandedSettingsUtil = oneHandedController.mOneHandedSettingsUtil;
                        ContentResolver contentResolver = oneHandedController.mContext.getContentResolver();
                        int i = OneHandedController.this.mUserId;
                        Objects.requireNonNull(oneHandedSettingsUtil);
                        int recommendedTimeoutMillis = OneHandedController.this.mAccessibilityManager.getRecommendedTimeoutMillis(Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_timeout", 8, i) * 1000, 4);
                        OneHandedTimeoutHandler oneHandedTimeoutHandler = OneHandedController.this.mTimeoutHandler;
                        int i2 = recommendedTimeoutMillis / 1000;
                        Objects.requireNonNull(oneHandedTimeoutHandler);
                        oneHandedTimeoutHandler.mTimeout = i2;
                        oneHandedTimeoutHandler.mTimeoutMs = TimeUnit.SECONDS.toMillis((long) i2);
                        oneHandedTimeoutHandler.resetTimer();
                        return;
                    }
                    OneHandedController oneHandedController2 = OneHandedController.this;
                    OneHandedTimeoutHandler oneHandedTimeoutHandler2 = oneHandedController2.mTimeoutHandler;
                    OneHandedSettingsUtil oneHandedSettingsUtil2 = oneHandedController2.mOneHandedSettingsUtil;
                    ContentResolver contentResolver2 = oneHandedController2.mContext.getContentResolver();
                    int i3 = OneHandedController.this.mUserId;
                    Objects.requireNonNull(oneHandedSettingsUtil2);
                    int intForUser = Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_timeout", 8, i3);
                    Objects.requireNonNull(oneHandedTimeoutHandler2);
                    oneHandedTimeoutHandler2.mTimeout = intForUser;
                    oneHandedTimeoutHandler2.mTimeoutMs = TimeUnit.SECONDS.toMillis((long) intForUser);
                    oneHandedTimeoutHandler2.resetTimer();
                }
            }
        };
        C18783 r9 = new OneHandedTransitionCallback() {
            public final void onStartFinished(Rect rect) {
                OneHandedController.this.mState.setState(2);
                OneHandedController.this.notifyShortcutStateChanged(2);
            }

            public final void onStopFinished(Rect rect) {
                OneHandedController.this.mState.setState(0);
                OneHandedController.this.notifyShortcutStateChanged(0);
            }
        };
        this.mTransitionCallBack = r9;
        C18794 r10 = new TaskStackListenerCallback() {
            public final void onTaskCreated() {
                OneHandedController.this.stopOneHanded(5);
            }

            public final void onTaskMovedToFront(int i) {
                OneHandedController.this.stopOneHanded(5);
            }
        };
        this.mTaskStackListenerCallback = r10;
        this.mContext = context;
        this.mOneHandedSettingsUtil = oneHandedSettingsUtil;
        this.mOneHandedAccessibilityUtil = oneHandedAccessibilityUtil;
        this.mDisplayAreaOrganizer = oneHandedDisplayAreaOrganizer;
        this.mDisplayController = displayController2;
        this.mTouchHandler = oneHandedTouchHandler2;
        this.mState = oneHandedState2;
        this.mTutorialHandler = oneHandedTutorialHandler2;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler2;
        this.mOneHandedUiEventLogger = oneHandedUiEventLogger;
        this.mTaskStackListener = taskStackListenerImpl2;
        displayController2.addDisplayWindowListener(r8);
        int i = SystemProperties.getInt("persist.debug.one_handed_offset_percentage", Math.round(context.getResources().getFraction(C1777R.fraction.config_one_handed_offset, 1, 1) * 100.0f));
        this.mUserId = UserHandle.myUserId();
        this.mOffSetFraction = ((float) i) / 100.0f;
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil);
        this.mIsOneHandedEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i2);
        this.mIsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(context.getContentResolver(), this.mUserId);
        this.mTimeoutHandler = oneHandedTimeoutHandler2;
        final OneHandedController$$ExternalSyntheticLambda1 oneHandedController$$ExternalSyntheticLambda1 = new OneHandedController$$ExternalSyntheticLambda1(this, 0);
        this.mActivatedObserver = new ContentObserver(handler2) {
            public final void onChange(boolean z) {
                r8.run();
            }
        };
        final BaseWifiTracker$$ExternalSyntheticLambda0 baseWifiTracker$$ExternalSyntheticLambda0 = new BaseWifiTracker$$ExternalSyntheticLambda0(this, 6);
        this.mEnabledObserver = new ContentObserver(handler2) {
            public final void onChange(boolean z) {
                r8.run();
            }
        };
        final BaseWifiTracker$$ExternalSyntheticLambda1 baseWifiTracker$$ExternalSyntheticLambda1 = new BaseWifiTracker$$ExternalSyntheticLambda1(this, 8);
        this.mSwipeToNotificationEnabledObserver = new ContentObserver(handler2) {
            public final void onChange(boolean z) {
                r8.run();
            }
        };
        final QSFgsManagerFooter$$ExternalSyntheticLambda0 qSFgsManagerFooter$$ExternalSyntheticLambda0 = new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 8);
        this.mShortcutEnabledObserver = new ContentObserver(handler2) {
            public final void onChange(boolean z) {
                qSFgsManagerFooter$$ExternalSyntheticLambda0.run();
            }
        };
        displayController2.addDisplayChangingController(this);
        ImageLoader$$ExternalSyntheticLambda0 imageLoader$$ExternalSyntheticLambda0 = new ImageLoader$$ExternalSyntheticLambda0(this);
        Objects.requireNonNull(oneHandedTouchHandler);
        oneHandedTouchHandler2.mTouchEventCallback = imageLoader$$ExternalSyntheticLambda0;
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer2 = this.mDisplayAreaOrganizer;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer2);
        oneHandedDisplayAreaOrganizer2.mTransitionCallbacks.add(oneHandedTouchHandler2);
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer3 = this.mDisplayAreaOrganizer;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer3);
        oneHandedDisplayAreaOrganizer3.mTransitionCallbacks.add(oneHandedTutorialHandler2);
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer4 = this.mDisplayAreaOrganizer;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer4);
        oneHandedDisplayAreaOrganizer4.mTransitionCallbacks.add(r9);
        if (this.mTaskChangeToExit) {
            taskStackListenerImpl2.addListener(r10);
        }
        registerSettingObservers(this.mUserId);
        OneHandedController$$ExternalSyntheticLambda0 oneHandedController$$ExternalSyntheticLambda0 = new OneHandedController$$ExternalSyntheticLambda0(this);
        Objects.requireNonNull(oneHandedTimeoutHandler);
        oneHandedTimeoutHandler2.mListeners.add(oneHandedController$$ExternalSyntheticLambda0);
        updateSettings();
        updateDisplayLayout(this.mContext.getDisplayId());
        AccessibilityManager instance = AccessibilityManager.getInstance(context);
        this.mAccessibilityManager = instance;
        instance.addAccessibilityStateChangeListener(this.mAccessibilityStateChangeListener);
        Objects.requireNonNull(oneHandedState);
        oneHandedState2.mStateChangeListeners.add(oneHandedTutorialHandler2);
    }

    public void stopOneHanded() {
        stopOneHanded(1);
    }

    /* renamed from: com.android.wm.shell.onehanded.OneHandedController$IOneHandedImpl */
    public static class IOneHandedImpl extends IOneHanded.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public OneHandedController mController;

        public IOneHandedImpl(OneHandedController oneHandedController) {
            this.mController = oneHandedController;
        }
    }

    public final boolean isInitialized() {
        if (this.mDisplayAreaOrganizer != null && this.mDisplayController != null && this.mOneHandedSettingsUtil != null) {
            return true;
        }
        Slog.w("OneHandedController", "Components may not initialized yet!");
        return false;
    }

    public void notifyExpandNotification() {
        if (this.mEventCallback != null) {
            this.mMainExecutor.execute(new QSTileImpl$$ExternalSyntheticLambda0(this, 7));
        }
    }

    public void onEnabledSettingChanged() {
        int i;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil);
        boolean settingsOneHandedModeEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i2);
        OneHandedUiEventLogger oneHandedUiEventLogger = this.mOneHandedUiEventLogger;
        if (settingsOneHandedModeEnabled) {
            i = 8;
        } else {
            i = 9;
        }
        oneHandedUiEventLogger.writeEvent(i);
        this.mIsOneHandedEnabled = settingsOneHandedModeEnabled;
        updateOneHandedEnabled();
    }

    public final void onShortcutEnabledChanged() {
        int i;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil);
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "accessibility_button_targets", i2);
        boolean z = true;
        if (TextUtils.isEmpty(stringForUser) || !stringForUser.contains(OneHandedSettingsUtil.ONE_HANDED_MODE_TARGET_NAME)) {
            String stringForUser2 = Settings.Secure.getStringForUser(contentResolver, "accessibility_shortcut_target_service", i2);
            if (TextUtils.isEmpty(stringForUser2) || !stringForUser2.contains(OneHandedSettingsUtil.ONE_HANDED_MODE_TARGET_NAME)) {
                z = false;
            }
        }
        this.mIsShortcutEnabled = z;
        OneHandedUiEventLogger oneHandedUiEventLogger = this.mOneHandedUiEventLogger;
        if (z) {
            i = 20;
        } else {
            i = 21;
        }
        oneHandedUiEventLogger.writeEvent(i);
    }

    public void onSwipeToNotificationEnabledChanged() {
        int i;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil);
        boolean settingsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver, i2);
        this.mIsSwipeToNotificationEnabled = settingsSwipeToNotificationEnabled;
        Objects.requireNonNull(this.mState);
        notifyShortcutStateChanged(OneHandedState.sCurrentState);
        OneHandedUiEventLogger oneHandedUiEventLogger = this.mOneHandedUiEventLogger;
        if (settingsSwipeToNotificationEnabled) {
            i = 18;
        } else {
            i = 19;
        }
        oneHandedUiEventLogger.writeEvent(i);
    }

    public final void registerSettingObservers(int i) {
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        C18805 r2 = this.mActivatedObserver;
        Objects.requireNonNull(oneHandedSettingsUtil);
        OneHandedSettingsUtil.registerSettingsKeyObserver("one_handed_mode_activated", contentResolver, r2, i);
        OneHandedSettingsUtil oneHandedSettingsUtil2 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        C18805 r22 = this.mEnabledObserver;
        Objects.requireNonNull(oneHandedSettingsUtil2);
        OneHandedSettingsUtil.registerSettingsKeyObserver("one_handed_mode_enabled", contentResolver2, r22, i);
        OneHandedSettingsUtil oneHandedSettingsUtil3 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver3 = this.mContext.getContentResolver();
        C18805 r23 = this.mSwipeToNotificationEnabledObserver;
        Objects.requireNonNull(oneHandedSettingsUtil3);
        OneHandedSettingsUtil.registerSettingsKeyObserver("swipe_bottom_to_notification_enabled", contentResolver3, r23, i);
        OneHandedSettingsUtil oneHandedSettingsUtil4 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver4 = this.mContext.getContentResolver();
        C18805 r24 = this.mShortcutEnabledObserver;
        Objects.requireNonNull(oneHandedSettingsUtil4);
        OneHandedSettingsUtil.registerSettingsKeyObserver("accessibility_button_targets", contentResolver4, r24, i);
        OneHandedSettingsUtil oneHandedSettingsUtil5 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver5 = this.mContext.getContentResolver();
        C18805 r3 = this.mShortcutEnabledObserver;
        Objects.requireNonNull(oneHandedSettingsUtil5);
        OneHandedSettingsUtil.registerSettingsKeyObserver("accessibility_shortcut_target_service", contentResolver5, r3, i);
    }

    public void setLockedDisabled(boolean z, boolean z2) {
        boolean z3;
        boolean z4 = false;
        if (this.mIsOneHandedEnabled || this.mIsSwipeToNotificationEnabled) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 != z3) {
            if (z && !z2) {
                z4 = true;
            }
            this.mLockedDisabled = z4;
        }
    }

    public final void stopOneHanded(int i) {
        Objects.requireNonNull(this.mState);
        int i2 = OneHandedState.sCurrentState;
        boolean z = true;
        if (!(i2 == 1 || i2 == 3)) {
            z = false;
        }
        if (!z) {
            Objects.requireNonNull(this.mState);
            if (OneHandedState.sCurrentState != 0) {
                this.mState.setState(3);
                OneHandedAccessibilityUtil oneHandedAccessibilityUtil = this.mOneHandedAccessibilityUtil;
                Objects.requireNonNull(oneHandedAccessibilityUtil);
                oneHandedAccessibilityUtil.announcementForScreenReader(oneHandedAccessibilityUtil.mStopOneHandedDescription);
                this.mDisplayAreaOrganizer.scheduleOffset(0);
                OneHandedTimeoutHandler oneHandedTimeoutHandler = this.mTimeoutHandler;
                Objects.requireNonNull(oneHandedTimeoutHandler);
                oneHandedTimeoutHandler.mMainExecutor.removeCallbacks(oneHandedTimeoutHandler.mTimeoutRunnable);
                this.mOneHandedUiEventLogger.writeEvent(i);
            }
        }
    }

    public void updateDisplayLayout(int i) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
        if (displayLayout == null) {
            Slog.w("OneHandedController", "Failed to get new DisplayLayout.");
            return;
        }
        this.mDisplayAreaOrganizer.setDisplayLayout(displayLayout);
        OneHandedTutorialHandler oneHandedTutorialHandler = this.mTutorialHandler;
        Objects.requireNonNull(oneHandedTutorialHandler);
        if (displayLayout.mHeight > displayLayout.mWidth) {
            oneHandedTutorialHandler.mDisplayBounds = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        } else {
            oneHandedTutorialHandler.mDisplayBounds = new Rect(0, 0, displayLayout.mHeight, displayLayout.mWidth);
        }
        int round = Math.round(((float) oneHandedTutorialHandler.mDisplayBounds.height()) * oneHandedTutorialHandler.mTutorialHeightRatio);
        oneHandedTutorialHandler.mTutorialAreaHeight = round;
        oneHandedTutorialHandler.mAlphaTransitionStart = ((float) round) * 0.6f;
        BackgroundWindowManager backgroundWindowManager = oneHandedTutorialHandler.mBackgroundWindowManager;
        Objects.requireNonNull(backgroundWindowManager);
        if (displayLayout.mHeight > displayLayout.mWidth) {
            backgroundWindowManager.mDisplayBounds = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        } else {
            backgroundWindowManager.mDisplayBounds = new Rect(0, 0, displayLayout.mHeight, displayLayout.mWidth);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if (com.android.p012wm.shell.onehanded.OneHandedState.sCurrentState == 2) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateOneHandedEnabled() {
        /*
            r3 = this;
            com.android.wm.shell.onehanded.OneHandedState r0 = r3.mState
            java.util.Objects.requireNonNull(r0)
            int r0 = com.android.p012wm.shell.onehanded.OneHandedState.sCurrentState
            r1 = 1
            if (r0 == r1) goto L_0x0014
            com.android.wm.shell.onehanded.OneHandedState r0 = r3.mState
            java.util.Objects.requireNonNull(r0)
            int r0 = com.android.p012wm.shell.onehanded.OneHandedState.sCurrentState
            r1 = 2
            if (r0 != r1) goto L_0x001f
        L_0x0014:
            com.android.wm.shell.common.ShellExecutor r0 = r3.mMainExecutor
            com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1 r1 = new com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1
            r2 = 4
            r1.<init>(r3, r2)
            r0.execute(r1)
        L_0x001f:
            boolean r0 = r3.isOneHandedEnabled()
            if (r0 == 0) goto L_0x0035
            boolean r0 = r3.isSwipeToNotificationEnabled()
            if (r0 != 0) goto L_0x0035
            com.android.wm.shell.onehanded.OneHandedState r0 = r3.mState
            java.util.Objects.requireNonNull(r0)
            int r0 = com.android.p012wm.shell.onehanded.OneHandedState.sCurrentState
            r3.notifyShortcutStateChanged(r0)
        L_0x0035:
            com.android.wm.shell.onehanded.OneHandedTouchHandler r0 = r3.mTouchHandler
            boolean r1 = r3.mIsOneHandedEnabled
            java.util.Objects.requireNonNull(r0)
            r0.mIsEnabled = r1
            r0.updateIsEnabled()
            boolean r0 = r3.mIsOneHandedEnabled
            if (r0 != 0) goto L_0x004b
            com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer r3 = r3.mDisplayAreaOrganizer
            r3.unregisterOrganizer()
            return
        L_0x004b:
            com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer r0 = r3.mDisplayAreaOrganizer
            android.util.ArrayMap r0 = r0.getDisplayAreaTokenMap()
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x005d
            com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer r3 = r3.mDisplayAreaOrganizer
            r0 = 3
            r3.registerOrganizer(r0)
        L_0x005d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.onehanded.OneHandedController.updateOneHandedEnabled():void");
    }

    public final void updateSettings() {
        boolean isEmpty;
        boolean isEmpty2;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil);
        this.mIsOneHandedEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i);
        updateOneHandedEnabled();
        OneHandedTimeoutHandler oneHandedTimeoutHandler = this.mTimeoutHandler;
        OneHandedSettingsUtil oneHandedSettingsUtil2 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil2);
        int intForUser = Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_timeout", 8, i2);
        Objects.requireNonNull(oneHandedTimeoutHandler);
        oneHandedTimeoutHandler.mTimeout = intForUser;
        oneHandedTimeoutHandler.mTimeoutMs = TimeUnit.SECONDS.toMillis((long) intForUser);
        oneHandedTimeoutHandler.resetTimer();
        OneHandedSettingsUtil oneHandedSettingsUtil3 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver3 = this.mContext.getContentResolver();
        int i3 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil3);
        boolean z = true;
        if (Settings.Secure.getIntForUser(contentResolver3, "taps_app_to_exit", 1, i3) != 1) {
            z = false;
        }
        if (z) {
            this.mTaskStackListener.addListener(this.mTaskStackListenerCallback);
        } else {
            TaskStackListenerImpl taskStackListenerImpl = this.mTaskStackListener;
            C18794 r1 = this.mTaskStackListenerCallback;
            Objects.requireNonNull(taskStackListenerImpl);
            synchronized (taskStackListenerImpl.mTaskStackListeners) {
                isEmpty = taskStackListenerImpl.mTaskStackListeners.isEmpty();
                taskStackListenerImpl.mTaskStackListeners.remove(r1);
                isEmpty2 = taskStackListenerImpl.mTaskStackListeners.isEmpty();
            }
            if (!isEmpty && isEmpty2) {
                try {
                    taskStackListenerImpl.mActivityTaskManager.unregisterTaskStackListener(taskStackListenerImpl);
                } catch (Exception e) {
                    Log.w("TaskStackListenerImpl", "Failed to call unregisterTaskStackListener", e);
                }
            }
        }
        this.mTaskChangeToExit = z;
        OneHandedSettingsUtil oneHandedSettingsUtil4 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver4 = this.mContext.getContentResolver();
        int i4 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil4);
        this.mIsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver4, i4);
        onShortcutEnabledChanged();
    }

    public void notifyShortcutStateChanged(int i) {
        int i2;
        if (isShortcutEnabled()) {
            OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
            ContentResolver contentResolver = this.mContext.getContentResolver();
            if (i == 2) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            int i3 = this.mUserId;
            Objects.requireNonNull(oneHandedSettingsUtil);
            Settings.Secure.putIntForUser(contentResolver, "one_handed_mode_activated", i2, i3);
        }
    }

    public void onActivatedActionChanged() {
        boolean z;
        if (!isShortcutEnabled()) {
            Slog.w("OneHandedController", "Shortcut not enabled, skip onActivatedActionChanged()");
            return;
        }
        boolean z2 = true;
        if (!isOneHandedEnabled()) {
            OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int i = this.mUserId;
            Objects.requireNonNull(oneHandedSettingsUtil);
            Slog.d("OneHandedController", "Auto enabled One-handed mode by shortcut trigger, success=" + Settings.Secure.putIntForUser(contentResolver, "one_handed_mode_enabled", 1, i));
        }
        if (isSwipeToNotificationEnabled()) {
            notifyExpandNotification();
            return;
        }
        Objects.requireNonNull(this.mState);
        if (OneHandedState.sCurrentState == 2) {
            z = true;
        } else {
            z = false;
        }
        OneHandedSettingsUtil oneHandedSettingsUtil2 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        Objects.requireNonNull(oneHandedSettingsUtil2);
        if (Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_activated", 0, i2) != 1) {
            z2 = false;
        }
        if (!(z ^ z2)) {
            return;
        }
        if (z2) {
            startOneHanded();
        } else {
            stopOneHanded();
        }
    }

    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        if (isInitialized()) {
            OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int i4 = this.mUserId;
            Objects.requireNonNull(oneHandedSettingsUtil);
            if (OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i4)) {
                OneHandedSettingsUtil oneHandedSettingsUtil2 = this.mOneHandedSettingsUtil;
                ContentResolver contentResolver2 = this.mContext.getContentResolver();
                int i5 = this.mUserId;
                Objects.requireNonNull(oneHandedSettingsUtil2);
                if (!OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver2, i5)) {
                    OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
                    Context context = this.mContext;
                    Objects.requireNonNull(oneHandedDisplayAreaOrganizer);
                    DisplayLayout displayLayout = oneHandedDisplayAreaOrganizer.mDisplayLayout;
                    Objects.requireNonNull(displayLayout);
                    if (displayLayout.mRotation != i3) {
                        oneHandedDisplayAreaOrganizer.mDisplayLayout.rotateTo(context.getResources(), i3);
                        oneHandedDisplayAreaOrganizer.updateDisplayBounds();
                        oneHandedDisplayAreaOrganizer.finishOffset(0, 2);
                    }
                    this.mOneHandedUiEventLogger.writeEvent(4);
                }
            }
        }
    }

    public void startOneHanded() {
        boolean z;
        boolean z2;
        if (isLockedDisabled() || this.mKeyguardShowing) {
            Slog.d("OneHandedController", "Temporary lock disabled");
            return;
        }
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer);
        if (!oneHandedDisplayAreaOrganizer.mIsReady) {
            this.mMainExecutor.executeDelayed(new DozeScreenState$$ExternalSyntheticLambda0(this, 7), 10);
            return;
        }
        Objects.requireNonNull(this.mState);
        int i = OneHandedState.sCurrentState;
        if (i == 1 || i == 3) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Objects.requireNonNull(this.mState);
            if (OneHandedState.sCurrentState == 2) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer2 = this.mDisplayAreaOrganizer;
                Objects.requireNonNull(oneHandedDisplayAreaOrganizer2);
                DisplayLayout displayLayout = oneHandedDisplayAreaOrganizer2.mDisplayLayout;
                Objects.requireNonNull(displayLayout);
                int i2 = displayLayout.mRotation;
                if (i2 == 0 || i2 == 2) {
                    this.mState.setState(1);
                    OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer3 = this.mDisplayAreaOrganizer;
                    Objects.requireNonNull(oneHandedDisplayAreaOrganizer3);
                    DisplayLayout displayLayout2 = oneHandedDisplayAreaOrganizer3.mDisplayLayout;
                    Objects.requireNonNull(displayLayout2);
                    int round = Math.round(((float) displayLayout2.mHeight) * this.mOffSetFraction);
                    OneHandedAccessibilityUtil oneHandedAccessibilityUtil = this.mOneHandedAccessibilityUtil;
                    Objects.requireNonNull(oneHandedAccessibilityUtil);
                    oneHandedAccessibilityUtil.announcementForScreenReader(oneHandedAccessibilityUtil.mStartOneHandedDescription);
                    this.mDisplayAreaOrganizer.scheduleOffset(round);
                    this.mTimeoutHandler.resetTimer();
                    this.mOneHandedUiEventLogger.writeEvent(0);
                    return;
                }
                Slog.w("OneHandedController", "One handed mode only support portrait mode");
            }
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public boolean isLockedDisabled() {
        return this.mLockedDisabled;
    }

    public boolean isOneHandedEnabled() {
        return this.mIsOneHandedEnabled;
    }

    public boolean isShortcutEnabled() {
        return this.mIsShortcutEnabled;
    }

    public boolean isSwipeToNotificationEnabled() {
        return this.mIsSwipeToNotificationEnabled;
    }
}
