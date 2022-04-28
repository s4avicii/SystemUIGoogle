package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.GameManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.view.InsetsVisibilities;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.leanback.R$color;
import com.android.internal.view.AppearanceRegion;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.pip.PipMediaController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda1;
import com.android.systemui.navigationbar.NavigationBarOverlayController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda32;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public final class EntryPointController extends NavigationBarOverlayController implements NavigationModeController.ModeChangedListener {
    public final AccessibilityManager mAccessibilityManager;
    public boolean mAlwaysOn;
    public final C22723 mAlwaysOnObserver;
    public int mCurrentUserId;
    public final FloatingEntryButton mEntryPoint;
    public final GameManager mGameManager;
    public final GameModeDndController mGameModeDndController;
    public String mGamePackageName;
    public ActivityManager.RunningTaskInfo mGameTaskInfo;
    public final boolean mHasGameOverlay;
    public ObjectAnimator mHideAnimator;
    public boolean mInSplitScreen;
    public final StatusBar$$ExternalSyntheticLambda32 mInSplitScreenCallback;
    public boolean mIsImmersive;
    public boolean mListenersRegistered = false;
    public int mNavBarMode;
    public final C22701 mOverviewProxyListener;
    public boolean mRecentsAnimationRunning;
    public final ShortcutBarController mShortcutBarController;
    public boolean mShouldShow;
    public final TaskStackListenerImpl mTaskStackListener;
    public final Optional<TaskSurfaceHelper> mTaskSurfaceHelper;
    public final ToastController mToast;
    public final int mTranslateDownAnimationDuration;
    public final int mTranslateUpAnimationDuration;
    public final GameDashboardUiEventLogger mUiEventLogger;
    public final C22712 mUserTracker;

    public class TaskStackListenerImpl extends TaskStackChangeListener {
        public TaskStackListenerImpl() {
        }

        public final void onTaskStackChanged() {
            EntryPointController.this.onRunningTaskChange();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public EntryPointController(Context context, AccessibilityManager accessibilityManager, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, GameModeDndController gameModeDndController, Handler handler, NavigationModeController navigationModeController, Optional<LegacySplitScreen> optional, OverviewProxyService overviewProxyService, PackageManager packageManager, ShortcutBarController shortcutBarController, ToastController toastController, GameDashboardUiEventLogger gameDashboardUiEventLogger, Optional<TaskSurfaceHelper> optional2) {
        super(context);
        boolean z;
        Context context2 = context;
        ShortcutBarController shortcutBarController2 = shortcutBarController;
        GameDashboardUiEventLogger gameDashboardUiEventLogger2 = gameDashboardUiEventLogger;
        C22701 r5 = new OverviewProxyService.OverviewProxyListener() {
            public final void onSwipeUpGestureStarted() {
                ShortcutBarController shortcutBarController = EntryPointController.this.mShortcutBarController;
                Objects.requireNonNull(shortcutBarController);
                shortcutBarController.hideUI();
                FpsController fpsController = shortcutBarController.mFpsController;
                Objects.requireNonNull(fpsController);
                fpsController.mWindowManager.unregisterTaskFpsCallback(fpsController.mListener);
                if (fpsController.mCallback != null) {
                    fpsController.mCallback = null;
                }
                EntryPointController.this.mGameTaskInfo = null;
            }
        };
        this.mOverviewProxyListener = r5;
        this.mInSplitScreenCallback = new StatusBar$$ExternalSyntheticLambda32(this, 4);
        this.mAccessibilityManager = accessibilityManager;
        this.mGameManager = (GameManager) context.getSystemService(GameManager.class);
        this.mGameModeDndController = gameModeDndController;
        this.mNavBarMode = navigationModeController.addListener(this);
        if (packageManager.hasSystemFeature("com.google.android.feature.GAME_OVERLAY") || Build.IS_DEBUGGABLE) {
            z = true;
        } else {
            z = false;
        }
        this.mHasGameOverlay = z;
        FloatingEntryButton floatingEntryButton = new FloatingEntryButton(context);
        this.mEntryPoint = floatingEntryButton;
        floatingEntryButton.mEntryPointButton.setOnClickListener(new NavigationBar$$ExternalSyntheticLambda0(this, 1));
        Resources resources = context.getResources();
        this.mTranslateUpAnimationDuration = resources.getInteger(C1777R.integer.game_toast_translate_up_animation_duration);
        this.mTranslateDownAnimationDuration = resources.getInteger(C1777R.integer.game_toast_translate_down_animation_duration);
        this.mTaskStackListener = new TaskStackListenerImpl();
        this.mShouldShow = false;
        this.mShortcutBarController = shortcutBarController2;
        Objects.requireNonNull(shortcutBarController);
        shortcutBarController2.mEntryPointController = this;
        GameDashboardUiEventLogger gameDashboardUiEventLogger3 = shortcutBarController2.mUiEventLogger;
        Objects.requireNonNull(gameDashboardUiEventLogger3);
        gameDashboardUiEventLogger3.mEntryPointController = this;
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) r5);
        this.mUiEventLogger = gameDashboardUiEventLogger2;
        Objects.requireNonNull(gameDashboardUiEventLogger);
        gameDashboardUiEventLogger2.mEntryPointController = this;
        this.mTaskSurfaceHelper = optional2;
        optional.ifPresent(new PipMediaController$$ExternalSyntheticLambda2(this, 3));
        this.mToast = toastController;
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        C22712 r3 = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                EntryPointController.this.mContext.getContentResolver().unregisterContentObserver(EntryPointController.this.mAlwaysOnObserver);
                EntryPointController entryPointController = EntryPointController.this;
                entryPointController.mCurrentUserId = i;
                ContentResolver contentResolver = entryPointController.mContext.getContentResolver();
                Uri uriFor = Settings.Secure.getUriFor("game_dashboard_always_on");
                EntryPointController entryPointController2 = EntryPointController.this;
                contentResolver.registerContentObserver(uriFor, false, entryPointController2.mAlwaysOnObserver, entryPointController2.mCurrentUserId);
                EntryPointController.this.checkAlwaysOn();
            }
        };
        this.mUserTracker = r3;
        r3.startTracking();
        this.mCurrentUserId = r3.getCurrentUserId();
        checkAlwaysOn();
        Handler handler2 = handler;
        C22723 r32 = new ContentObserver(handler) {
            public final void onChange(boolean z) {
                super.onChange(z);
                EntryPointController.this.checkAlwaysOn();
            }
        };
        this.mAlwaysOnObserver = r32;
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("game_dashboard_always_on"), false, r32, this.mCurrentUserId);
        NavigationBar$$ExternalSyntheticLambda1 navigationBar$$ExternalSyntheticLambda1 = new NavigationBar$$ExternalSyntheticLambda1(this, 1);
        ShortcutBarView shortcutBarView = shortcutBarController2.mView;
        Objects.requireNonNull(shortcutBarView);
        shortcutBarView.mEntryPointButton.setOnTouchListener(shortcutBarView.mOnTouchListener);
        shortcutBarView.mEntryPointButton.setOnClickListener(navigationBar$$ExternalSyntheticLambda1);
        CommandQueue commandQueue2 = commandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) new CommandQueue.Callbacks() {
            public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str) {
                boolean z2;
                boolean z3;
                boolean z4 = false;
                if (!insetsVisibilities.getVisibility(0) || !insetsVisibilities.getVisibility(1)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                EntryPointController entryPointController = EntryPointController.this;
                if (!z2 || i3 != 2) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                entryPointController.mIsImmersive = z3;
                if (!entryPointController.mAlwaysOn) {
                    ShortcutBarController shortcutBarController = entryPointController.mShortcutBarController;
                    if (z3 && !entryPointController.mInSplitScreen) {
                        z4 = true;
                    }
                    shortcutBarController.updateVisibility(z4);
                }
            }

            public final void onRecentsAnimationStateChanged(boolean z) {
                EntryPointController entryPointController = EntryPointController.this;
                entryPointController.mRecentsAnimationRunning = z;
                entryPointController.onRunningTaskChange();
            }
        });
        onRunningTaskChange();
    }

    public final void checkAlwaysOn() {
        boolean z;
        int i;
        boolean z2 = false;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "game_dashboard_always_on", 0, this.mCurrentUserId) == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mAlwaysOn = z;
        ShortcutBarController shortcutBarController = this.mShortcutBarController;
        boolean z3 = this.mShouldShow;
        Objects.requireNonNull(shortcutBarController);
        ShortcutBarView shortcutBarView = shortcutBarController.mView;
        Objects.requireNonNull(shortcutBarView);
        shortcutBarView.mIsEntryPointVisible = z;
        ShortcutBarButton shortcutBarButton = shortcutBarView.mEntryPointButton;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        shortcutBarButton.setVisibility(i);
        if (z && z3) {
            z2 = true;
        }
        shortcutBarController.onButtonVisibilityChange(z2);
    }

    public final View getCurrentView() {
        FloatingEntryButton floatingEntryButton = this.mEntryPoint;
        Objects.requireNonNull(floatingEntryButton);
        return floatingEntryButton.mFloatingView;
    }

    public final void init(Consumer<Boolean> consumer, Consumer<Rect> consumer2) {
        FloatingEntryButton floatingEntryButton = this.mEntryPoint;
        Objects.requireNonNull(floatingEntryButton);
        floatingEntryButton.mVisibilityChangedCallback = consumer;
        ShortcutBarController shortcutBarController = this.mShortcutBarController;
        Objects.requireNonNull(shortcutBarController);
        ShortcutBarView shortcutBarView = shortcutBarController.mView;
        Objects.requireNonNull(shortcutBarView);
        shortcutBarView.mExcludeBackRegionCallback = consumer2;
    }

    public final boolean isVisible() {
        FloatingEntryButton floatingEntryButton = this.mEntryPoint;
        Objects.requireNonNull(floatingEntryButton);
        if (floatingEntryButton.mFloatingView != null) {
            FloatingEntryButton floatingEntryButton2 = this.mEntryPoint;
            Objects.requireNonNull(floatingEntryButton2);
            if (floatingEntryButton2.mIsShowing) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0061, code lost:
        if (r2.mIsShowing == false) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0065, code lost:
        if (r0.mAlwaysOn != false) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0067, code lost:
        r0.mEntryPoint.hide();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006c, code lost:
        r2 = r1.topActivity.getClassName().equals(com.google.android.systemui.gamedashboard.GameMenuActivity.class.getName());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007e, code lost:
        if (r0.mShouldShow != false) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0080, code lost:
        if (r2 == false) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0083, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0085, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0086, code lost:
        r7 = r0.mGameModeDndController;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0088, code lost:
        if (r4 == false) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008c, code lost:
        if (r0.mHasGameOverlay == false) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008e, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0090, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0091, code lost:
        java.util.Objects.requireNonNull(r7);
        r7.mGameActive = r4;
        r7.updateRule();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009b, code lost:
        if (r0.mShouldShow == false) goto L_0x0198;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009d, code lost:
        r2 = r1.taskId;
        r4 = r0.mGameManager.getGameMode(r0.mGamePackageName);
        r7 = r0.mGameModeDndController.isGameModeDndOn();
        r0.mTaskSurfaceHelper.ifPresent(new com.google.android.systemui.gamedashboard.EntryPointController$$ExternalSyntheticLambda0(r2, r4));
        r8 = r0.mToast;
        java.util.Objects.requireNonNull(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00be, code lost:
        if (r8.mGameTaskId != r2) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c2, code lost:
        r8.mGameTaskId = r2;
        r8.mLaunchDndMessageView.setVisibility(8);
        r8.mLaunchGameModeMessageView.setVisibility(8);
        r2 = new java.lang.StringBuilder();
        r3 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d8, code lost:
        if (r7 == false) goto L_0x00f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00da, code lost:
        r8.mLaunchDndMessageView.setVisibility(0);
        r2.append(r8.mContext.getString(com.android.p012wm.shell.C1777R.string.game_launch_dnd_on));
        r8.mLaunchDndMessageView.setText(r2);
        r7 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f2, code lost:
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00f5, code lost:
        if (r4 != 2) goto L_0x0110;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f7, code lost:
        r7 = r7 + 1;
        r8.mLaunchGameModeMessageView.setVisibility(0);
        r3.append(r8.mContext.getString(com.android.p012wm.shell.C1777R.string.game_launch_performance));
        r8.mLaunchGameModeMessageView.setText(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0110, code lost:
        if (r4 != 3) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0112, code lost:
        r7 = r7 + 1;
        r8.mLaunchGameModeMessageView.setVisibility(0);
        r3.append(r8.mContext.getString(com.android.p012wm.shell.C1777R.string.game_launch_battery));
        r8.mLaunchGameModeMessageView.setText(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x012a, code lost:
        if (r7 <= 0) goto L_0x0170;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x012c, code lost:
        r4 = r8.getMargin();
        r8.mLaunchLayout.measure(0, 0);
        r8.removeViewImmediate();
        r11 = new android.view.WindowManager.LayoutParams(-2, r8.mLaunchLayout.getMeasuredHeight() + r4, 0, 0, 2024, 8, -3);
        r11.privateFlags |= 16;
        r11.layoutInDisplayCutoutMode = 3;
        r11.setTitle("ToastText");
        r11.setFitInsetsTypes(0);
        r11.gravity = 80;
        r8.show(r11, 1);
        r8.mLaunchDndMessageView.announceForAccessibility(r2);
        r8.mLaunchGameModeMessageView.announceForAccessibility(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0170, code lost:
        r2 = r0.mShortcutBarController;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0174, code lost:
        if (r0.mIsImmersive != false) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0178, code lost:
        if (r0.mAlwaysOn == false) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x017c, code lost:
        if (r0.mInSplitScreen != false) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x017e, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x017f, code lost:
        r2.updateVisibility(r5);
        r2 = r0.mShortcutBarController;
        java.util.Objects.requireNonNull(r2);
        r2 = r2.mView;
        java.util.Objects.requireNonNull(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x018e, code lost:
        if (r2.mIsFpsVisible == false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0190, code lost:
        r0.mShortcutBarController.registerFps(r1.taskId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0198, code lost:
        if (r2 == false) goto L_0x01a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x019a, code lost:
        r0.mShortcutBarController.updateVisibility(!r0.mInSplitScreen);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01a3, code lost:
        r2 = r0.mShortcutBarController;
        java.util.Objects.requireNonNull(r2);
        r2.hideUI();
        r2 = r2.mFpsController;
        java.util.Objects.requireNonNull(r2);
        r2.mWindowManager.unregisterTaskFpsCallback(r2.mListener);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01b9, code lost:
        if (r2.mCallback == null) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01bb, code lost:
        r2.mCallback = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01be, code lost:
        r0.mGameTaskInfo = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01c0, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onRunningTaskChange() {
        /*
            r19 = this;
            r0 = r19
            boolean r1 = r0.mRecentsAnimationRunning
            if (r1 == 0) goto L_0x0007
            return
        L_0x0007:
            com.google.android.systemui.gamedashboard.ToastController r1 = r0.mToast
            java.util.Objects.requireNonNull(r1)
            android.view.View r2 = r1.mLaunchLayout
            r3 = 8
            r2.setVisibility(r3)
            android.widget.TextView r2 = r1.mShortcutView
            r2.setVisibility(r3)
            android.widget.TextView r2 = r1.mRecordSaveView
            r2.setVisibility(r3)
            r1.removeViewImmediate()
            com.android.systemui.shared.system.ActivityManagerWrapper r1 = com.android.systemui.shared.system.ActivityManagerWrapper.sInstance
            android.app.ActivityManager$RunningTaskInfo r1 = r1.getRunningTask()
            if (r1 != 0) goto L_0x0029
            return
        L_0x0029:
            android.content.ComponentName r2 = r1.baseActivity
            java.lang.String r2 = r2.getPackageName()
            r0.mGamePackageName = r2
            android.content.pm.ActivityInfo r4 = r1.topActivityInfo
            android.content.pm.ApplicationInfo r4 = r4.applicationInfo
            int r4 = r4.category
            r5 = 0
            r6 = 1
            if (r4 != 0) goto L_0x0055
            java.lang.String r4 = "com.google.android.play.games"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0055
            android.content.ComponentName r2 = r1.topActivity
            java.lang.String r2 = r2.getPackageName()
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0055
            boolean r2 = r0.mInSplitScreen
            if (r2 != 0) goto L_0x0055
            r2 = r6
            goto L_0x0056
        L_0x0055:
            r2 = r5
        L_0x0056:
            r0.mShouldShow = r2
            if (r2 != 0) goto L_0x0063
            com.google.android.systemui.gamedashboard.FloatingEntryButton r2 = r0.mEntryPoint
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsShowing
            if (r2 != 0) goto L_0x0067
        L_0x0063:
            boolean r2 = r0.mAlwaysOn
            if (r2 == 0) goto L_0x006c
        L_0x0067:
            com.google.android.systemui.gamedashboard.FloatingEntryButton r2 = r0.mEntryPoint
            r2.hide()
        L_0x006c:
            android.content.ComponentName r2 = r1.topActivity
            java.lang.String r2 = r2.getClassName()
            java.lang.Class<com.google.android.systemui.gamedashboard.GameMenuActivity> r4 = com.google.android.systemui.gamedashboard.GameMenuActivity.class
            java.lang.String r4 = r4.getName()
            boolean r2 = r2.equals(r4)
            boolean r4 = r0.mShouldShow
            if (r4 != 0) goto L_0x0085
            if (r2 == 0) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            r4 = r5
            goto L_0x0086
        L_0x0085:
            r4 = r6
        L_0x0086:
            com.google.android.systemui.gamedashboard.GameModeDndController r7 = r0.mGameModeDndController
            if (r4 == 0) goto L_0x0090
            boolean r4 = r0.mHasGameOverlay
            if (r4 == 0) goto L_0x0090
            r4 = r6
            goto L_0x0091
        L_0x0090:
            r4 = r5
        L_0x0091:
            java.util.Objects.requireNonNull(r7)
            r7.mGameActive = r4
            r7.updateRule()
            boolean r4 = r0.mShouldShow
            if (r4 == 0) goto L_0x0198
            int r2 = r1.taskId
            android.app.GameManager r4 = r0.mGameManager
            java.lang.String r7 = r0.mGamePackageName
            int r4 = r4.getGameMode(r7)
            com.google.android.systemui.gamedashboard.GameModeDndController r7 = r0.mGameModeDndController
            boolean r7 = r7.isGameModeDndOn()
            java.util.Optional<com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelper> r8 = r0.mTaskSurfaceHelper
            com.google.android.systemui.gamedashboard.EntryPointController$$ExternalSyntheticLambda0 r9 = new com.google.android.systemui.gamedashboard.EntryPointController$$ExternalSyntheticLambda0
            r9.<init>(r2, r4)
            r8.ifPresent(r9)
            com.google.android.systemui.gamedashboard.ToastController r8 = r0.mToast
            java.util.Objects.requireNonNull(r8)
            int r9 = r8.mGameTaskId
            if (r9 != r2) goto L_0x00c2
            goto L_0x0170
        L_0x00c2:
            r8.mGameTaskId = r2
            android.widget.TextView r2 = r8.mLaunchDndMessageView
            r2.setVisibility(r3)
            android.widget.TextView r2 = r8.mLaunchGameModeMessageView
            r2.setVisibility(r3)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            if (r7 == 0) goto L_0x00f2
            android.widget.TextView r7 = r8.mLaunchDndMessageView
            r7.setVisibility(r5)
            android.content.Context r7 = r8.mContext
            r9 = 2131952381(0x7f1302fd, float:1.9541203E38)
            java.lang.String r7 = r7.getString(r9)
            r2.append(r7)
            android.widget.TextView r7 = r8.mLaunchDndMessageView
            r7.setText(r2)
            r7 = r6
            goto L_0x00f3
        L_0x00f2:
            r7 = r5
        L_0x00f3:
            r9 = 2
            r10 = 3
            if (r4 != r9) goto L_0x0110
            int r7 = r7 + 1
            android.widget.TextView r4 = r8.mLaunchGameModeMessageView
            r4.setVisibility(r5)
            android.content.Context r4 = r8.mContext
            r9 = 2131952382(0x7f1302fe, float:1.9541205E38)
            java.lang.String r4 = r4.getString(r9)
            r3.append(r4)
            android.widget.TextView r4 = r8.mLaunchGameModeMessageView
            r4.setText(r3)
            goto L_0x012a
        L_0x0110:
            if (r4 != r10) goto L_0x012a
            int r7 = r7 + 1
            android.widget.TextView r4 = r8.mLaunchGameModeMessageView
            r4.setVisibility(r5)
            android.content.Context r4 = r8.mContext
            r9 = 2131952379(0x7f1302fb, float:1.95412E38)
            java.lang.String r4 = r4.getString(r9)
            r3.append(r4)
            android.widget.TextView r4 = r8.mLaunchGameModeMessageView
            r4.setText(r3)
        L_0x012a:
            if (r7 <= 0) goto L_0x0170
            int r4 = r8.getMargin()
            android.view.View r7 = r8.mLaunchLayout
            r7.measure(r5, r5)
            android.view.View r7 = r8.mLaunchLayout
            int r7 = r7.getMeasuredHeight()
            int r13 = r7 + r4
            r8.removeViewImmediate()
            android.view.WindowManager$LayoutParams r4 = new android.view.WindowManager$LayoutParams
            r12 = -2
            r14 = 0
            r15 = 0
            r16 = 2024(0x7e8, float:2.836E-42)
            r17 = 8
            r18 = -3
            r11 = r4
            r11.<init>(r12, r13, r14, r15, r16, r17, r18)
            int r7 = r4.privateFlags
            r7 = r7 | 16
            r4.privateFlags = r7
            r4.layoutInDisplayCutoutMode = r10
            java.lang.String r7 = "ToastText"
            r4.setTitle(r7)
            r4.setFitInsetsTypes(r5)
            r7 = 80
            r4.gravity = r7
            r8.show(r4, r6)
            android.widget.TextView r4 = r8.mLaunchDndMessageView
            r4.announceForAccessibility(r2)
            android.widget.TextView r2 = r8.mLaunchGameModeMessageView
            r2.announceForAccessibility(r3)
        L_0x0170:
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r0.mShortcutBarController
            boolean r3 = r0.mIsImmersive
            if (r3 != 0) goto L_0x017a
            boolean r3 = r0.mAlwaysOn
            if (r3 == 0) goto L_0x017f
        L_0x017a:
            boolean r3 = r0.mInSplitScreen
            if (r3 != 0) goto L_0x017f
            r5 = r6
        L_0x017f:
            r2.updateVisibility(r5)
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r0.mShortcutBarController
            java.util.Objects.requireNonNull(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarView r2 = r2.mView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsFpsVisible
            if (r2 == 0) goto L_0x01be
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r0.mShortcutBarController
            int r3 = r1.taskId
            r2.registerFps(r3)
            goto L_0x01be
        L_0x0198:
            if (r2 == 0) goto L_0x01a3
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r0.mShortcutBarController
            boolean r3 = r0.mInSplitScreen
            r3 = r3 ^ r6
            r2.updateVisibility(r3)
            goto L_0x01be
        L_0x01a3:
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r0.mShortcutBarController
            java.util.Objects.requireNonNull(r2)
            r2.hideUI()
            com.google.android.systemui.gamedashboard.FpsController r2 = r2.mFpsController
            java.util.Objects.requireNonNull(r2)
            android.view.WindowManager r3 = r2.mWindowManager
            com.google.android.systemui.gamedashboard.FpsController$1 r4 = r2.mListener
            r3.unregisterTaskFpsCallback(r4)
            com.google.android.systemui.gamedashboard.FpsController$Callback r3 = r2.mCallback
            if (r3 == 0) goto L_0x01be
            r3 = 0
            r2.mCallback = r3
        L_0x01be:
            r0.mGameTaskInfo = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.gamedashboard.EntryPointController.onRunningTaskChange():void");
    }

    public final void registerListeners() {
        if (!this.mListenersRegistered) {
            this.mListenersRegistered = true;
            TaskStackChangeListeners.INSTANCE.registerTaskStackListener(this.mTaskStackListener);
        }
    }

    public final void setButtonState(boolean z, boolean z2) {
        ShortcutBarController shortcutBarController = this.mShortcutBarController;
        Objects.requireNonNull(shortcutBarController);
        ShortcutBarView shortcutBarView = shortcutBarController.mView;
        Objects.requireNonNull(shortcutBarView);
        if (shortcutBarView.mIsAttached && shortcutBarView.mShiftForTransientBar != 0) {
            if (z) {
                if (shortcutBarView.mRevealButton.getVisibility() == 0) {
                    RevealButton revealButton = shortcutBarView.mRevealButton;
                    Objects.requireNonNull(revealButton);
                    if (revealButton.mRightSide) {
                        shortcutBarView.autoUndock((float) shortcutBarView.mShiftForTransientBar);
                    }
                } else {
                    float width = (float) (((shortcutBarView.getWidth() - shortcutBarView.mBar.getWidth()) - shortcutBarView.mBarMarginEnd) - shortcutBarView.mShiftForTransientBar);
                    if (!shortcutBarView.mIsDragging && shortcutBarView.mBar.getTranslationX() >= width) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(shortcutBarView.mBar, FrameLayout.TRANSLATION_X, new float[]{width});
                        ofFloat.setDuration(100);
                        ofFloat.start();
                    }
                }
            } else if (shortcutBarView.mRevealButton.getVisibility() != 0 && !shortcutBarView.mIsDragging) {
                shortcutBarView.snapBarBackIfNecessary();
            }
        }
        if (!z) {
            FloatingEntryButton floatingEntryButton = this.mEntryPoint;
            Objects.requireNonNull(floatingEntryButton);
            if (!floatingEntryButton.mIsShowing) {
                return;
            }
        }
        if (this.mShouldShow && !this.mAlwaysOn) {
            FloatingEntryButton floatingEntryButton2 = this.mEntryPoint;
            Objects.requireNonNull(floatingEntryButton2);
            View view = floatingEntryButton2.mFloatingView;
            if (view != null) {
                float f = (float) (-view.getHeight());
                if (z) {
                    view.setTranslationY(f);
                    view.animate().translationY(0.0f).setDuration((long) this.mTranslateUpAnimationDuration);
                    FloatingEntryButton floatingEntryButton3 = this.mEntryPoint;
                    boolean isGesturalMode = R$color.isGesturalMode(this.mNavBarMode);
                    Objects.requireNonNull(floatingEntryButton3);
                    if (floatingEntryButton3.mCanShow && !floatingEntryButton3.mIsShowing) {
                        floatingEntryButton3.mIsShowing = true;
                        int i = floatingEntryButton3.mContext.getResources().getConfiguration().orientation;
                        int dimensionPixelSize = floatingEntryButton3.mContext.getResources().getDimensionPixelSize(C1777R.dimen.navigation_bar_height);
                        int dimensionPixelSize2 = floatingEntryButton3.mContext.getResources().getDimensionPixelSize(C1777R.dimen.status_bar_height);
                        int i2 = floatingEntryButton3.mMargin;
                        int i3 = dimensionPixelSize2 + i2;
                        if (i == 2 && !isGesturalMode) {
                            i2 += dimensionPixelSize;
                        }
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, floatingEntryButton3.mButtonHeight + i3, i2, 0, 2024, 8, -3);
                        layoutParams.privateFlags |= 16;
                        layoutParams.setTitle("FloatingEntryButton");
                        layoutParams.setFitInsetsTypes(0);
                        layoutParams.gravity = 8388661;
                        floatingEntryButton3.mWindowManager.addView(floatingEntryButton3.mFloatingView, layoutParams);
                        floatingEntryButton3.mFloatingView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            public final void onLayoutChange(
/*
Method generation error in method: com.google.android.systemui.gamedashboard.FloatingEntryButton.1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.google.android.systemui.gamedashboard.FloatingEntryButton.1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void, class status: UNLOADED
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
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
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
                        return;
                    }
                    return;
                }
                view.animate().cancel();
                if (z2) {
                    ObjectAnimator objectAnimator = this.mHideAnimator;
                    if (objectAnimator != null && objectAnimator.isRunning()) {
                        this.mHideAnimator.end();
                    }
                    this.mEntryPoint.hide();
                    return;
                }
                ObjectAnimator objectAnimator2 = this.mHideAnimator;
                if (objectAnimator2 == null || !objectAnimator2.isRunning()) {
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", new float[]{f});
                    if (this.mAccessibilityManager.isEnabled()) {
                        ofFloat2.setStartDelay((long) this.mAccessibilityManager.getRecommendedTimeoutMillis(this.mTranslateDownAnimationDuration, 1));
                    }
                    ofFloat2.setDuration((long) this.mTranslateDownAnimationDuration);
                    ofFloat2.setInterpolator(Interpolators.LINEAR);
                    ofFloat2.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            EntryPointController.this.mEntryPoint.hide();
                        }
                    });
                    this.mHideAnimator = ofFloat2;
                    ofFloat2.start();
                }
            }
        }
    }

    public final void setCanShow(boolean z) {
        FloatingEntryButton floatingEntryButton = this.mEntryPoint;
        Objects.requireNonNull(floatingEntryButton);
        floatingEntryButton.mCanShow = z;
        if (!z) {
            floatingEntryButton.hide();
        }
    }

    public final void unregisterListeners() {
        if (this.mListenersRegistered) {
            this.mListenersRegistered = false;
            TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(this.mTaskStackListener);
        }
    }

    public static void $r8$lambda$VcVcWE2qGRM_B0Sxv8eSSgrgnCU(EntryPointController entryPointController) {
        Objects.requireNonNull(entryPointController);
        entryPointController.mEntryPoint.hide();
        entryPointController.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_LAUNCH);
        Context context = entryPointController.mContext;
        IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
        Intent intent = new Intent(context, GameMenuActivity.class);
        ActivityOptions makeCustomTaskAnimation = ActivityOptions.makeCustomTaskAnimation(entryPointController.mContext, C1777R.anim.game_dashboard_fade_in, C1777R.anim.game_dashboard_fade_out, (Handler) null, (ActivityOptions.OnAnimationStartedListener) null, (ActivityOptions.OnAnimationFinishedListener) null);
        makeCustomTaskAnimation.setLaunchTaskId(ActivityManagerWrapper.sInstance.getRunningTask().taskId);
        entryPointController.mContext.startActivity(intent, makeCustomTaskAnimation.toBundle());
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public final boolean isNavigationBarOverlayEnabled() {
        return this.mHasGameOverlay;
    }
}
