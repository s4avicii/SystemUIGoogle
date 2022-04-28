package com.android.systemui.recents;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import androidx.leanback.R$color;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.ScreenshotHelper;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.transition.IShellTransitions;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda3;
import com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.ISystemUiProxy;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda30;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda31;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda32;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda2;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;

public final class OverviewProxyService extends CurrentUserTracker implements CallbackController<OverviewProxyListener>, NavigationModeController.ModeChangedListener, Dumpable {
    public Region mActiveNavBarRegion;
    public final Optional<BackAnimation> mBackAnimation;
    public boolean mBound;
    public final CommandQueue mCommandQueue;
    public int mConnectionBackoffAttempts;
    public final ArrayList mConnectionCallbacks = new ArrayList();
    public final CreateUserActivity$$ExternalSyntheticLambda2 mConnectionRunnable = new CreateUserActivity$$ExternalSyntheticLambda2(this, 2);
    public final Context mContext;
    public int mCurrentBoundedUserId = -1;
    public final DozeUi$$ExternalSyntheticLambda1 mDeferredConnectionCallback;
    public final Handler mHandler;
    public long mInputFocusTransferStartMillis;
    public float mInputFocusTransferStartY;
    public boolean mInputFocusTransferStarted;
    public boolean mIsEnabled;
    public final C10582 mLauncherStateChangedReceiver;
    public final Optional<LegacySplitScreen> mLegacySplitScreenOptional;
    public float mNavBarButtonAlpha;
    public final Lazy<NavigationBarController> mNavBarControllerLazy;
    public int mNavBarMode;
    public final Optional<OneHanded> mOneHandedOptional;
    public IOverviewProxy mOverviewProxy;
    public final C10593 mOverviewServiceConnection;
    public final OverviewProxyService$$ExternalSyntheticLambda0 mOverviewServiceDeathRcpt;
    public final Optional<Pip> mPipOptional;
    public final Intent mQuickStepIntent;
    public final Optional<RecentTasks> mRecentTasks;
    public final ComponentName mRecentsComponentName;
    public final ScreenshotHelper mScreenshotHelper;
    public final ShellTransitions mShellTransitions;
    public final OverviewProxyService$$ExternalSyntheticLambda3 mSplitScreenBoundsChangeListener;
    public final Optional<SplitScreen> mSplitScreenOptional;
    public final Optional<StartingSurface> mStartingSurface;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final NotificationShadeWindowController mStatusBarWinController;
    public final OverviewProxyService$$ExternalSyntheticLambda2 mStatusBarWindowCallback;
    public boolean mSupportsRoundedCornersOnWindows;
    @VisibleForTesting
    public ISystemUiProxy mSysUiProxy;
    public SysUiState mSysUiState;
    public final KeyguardUnlockAnimationController mSysuiUnlockAnimationController;
    public final UiEventLogger mUiEventLogger;
    public float mWindowCornerRadius;

    public interface OverviewProxyListener {
        void onAssistantGestureCompletion(float f) {
        }

        void onAssistantProgress(float f) {
        }

        void onConnectionChanged(boolean z) {
        }

        void onHomeRotationEnabled(boolean z) {
        }

        void onNavBarButtonAlphaChanged(float f, boolean z) {
        }

        void onOverviewShown() {
        }

        void onPrioritizedRotation(int i) {
        }

        void onSwipeUpGestureStarted() {
        }

        void onTaskbarAutohideSuspend(boolean z) {
        }

        void onTaskbarStatusUpdated(boolean z, boolean z2) {
        }

        void onToggleRecentApps() {
        }

        void startAssistant(Bundle bundle) {
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OverviewProxyService(Context context, CommandQueue commandQueue, Lazy<NavigationBarController> lazy, Lazy<Optional<StatusBar>> lazy2, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, SysUiState sysUiState, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<OneHanded> optional4, Optional<RecentTasks> optional5, Optional<BackAnimation> optional6, Optional<StartingSurface> optional7, BroadcastDispatcher broadcastDispatcher, ShellTransitions shellTransitions, ScreenLifecycle screenLifecycle, UiEventLogger uiEventLogger, KeyguardUnlockAnimationController keyguardUnlockAnimationController, DumpManager dumpManager) {
        super(broadcastDispatcher);
        NotificationShadeWindowController notificationShadeWindowController2 = notificationShadeWindowController;
        SysUiState sysUiState2 = sysUiState;
        Optional<LegacySplitScreen> optional8 = optional2;
        boolean z = false;
        this.mNavBarMode = 0;
        this.mSysUiProxy = new ISystemUiProxy.Stub() {
            public static final /* synthetic */ int $r8$clinit = 0;

            public final void verifyCallerAndClearCallingIdentityPostMain(String str, Runnable runnable) {
                verifyCallerAndClearCallingIdentity(str, new OverviewProxyService$1$$ExternalSyntheticLambda19(this, runnable));
            }

            public final boolean sendEvent(int i) {
                long uptimeMillis = SystemClock.uptimeMillis();
                KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
                keyEvent.setDisplayId(OverviewProxyService.this.mContext.getDisplay().getDisplayId());
                return InputManager.getInstance().injectInputEvent(keyEvent, 0);
            }

            public final <T> T verifyCallerAndClearCallingIdentity(String str, Supplier<T> supplier) {
                boolean z;
                int identifier = Binder.getCallingUserHandle().getIdentifier();
                if (identifier != OverviewProxyService.this.mCurrentBoundedUserId) {
                    Log.w("OverviewProxyService", "Launcher called sysui with invalid user: " + identifier + ", reason: " + str);
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return supplier.get();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mDeferredConnectionCallback = new DozeUi$$ExternalSyntheticLambda1(this, 6);
        C10582 r7 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                boolean z;
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                Objects.requireNonNull(overviewProxyService);
                Objects.requireNonNull(ActivityManagerWrapper.sInstance);
                if (overviewProxyService.mContext.getPackageManager().resolveServiceAsUser(overviewProxyService.mQuickStepIntent, 1048576, ActivityManagerWrapper.getCurrentUserId()) != null) {
                    z = true;
                } else {
                    z = false;
                }
                overviewProxyService.mIsEnabled = z;
                OverviewProxyService.this.startConnectionToCurrentUser();
            }
        };
        this.mLauncherStateChangedReceiver = r7;
        this.mOverviewServiceConnection = new ServiceConnection() {
            public final void onBindingDied(ComponentName componentName) {
                Log.w("OverviewProxyService", "Binding died of '" + componentName + "', try reconnecting");
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mCurrentBoundedUserId = -1;
                overviewProxyService.retryConnectionWithBackoff();
            }

            public final void onNullBinding(ComponentName componentName) {
                Log.w("OverviewProxyService", "Null binding of '" + componentName + "', try reconnecting");
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mCurrentBoundedUserId = -1;
                overviewProxyService.retryConnectionWithBackoff();
            }

            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IOverviewProxy iOverviewProxy;
                Region region;
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mConnectionBackoffAttempts = 0;
                overviewProxyService.mHandler.removeCallbacks(overviewProxyService.mDeferredConnectionCallback);
                try {
                    iBinder.linkToDeath(OverviewProxyService.this.mOverviewServiceDeathRcpt, 0);
                    OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                    overviewProxyService2.mCurrentBoundedUserId = overviewProxyService2.getCurrentUserId();
                    OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                    int i = IOverviewProxy.Stub.$r8$clinit;
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof IOverviewProxy)) {
                        iOverviewProxy = new IOverviewProxy.Stub.Proxy(iBinder);
                    } else {
                        iOverviewProxy = (IOverviewProxy) queryLocalInterface;
                    }
                    overviewProxyService3.mOverviewProxy = iOverviewProxy;
                    Bundle bundle = new Bundle();
                    ISystemUiProxy.Stub stub = (ISystemUiProxy.Stub) OverviewProxyService.this.mSysUiProxy;
                    Objects.requireNonNull(stub);
                    bundle.putBinder("extra_sysui_proxy", stub);
                    bundle.putFloat("extra_window_corner_radius", OverviewProxyService.this.mWindowCornerRadius);
                    bundle.putBoolean("extra_supports_window_corners", OverviewProxyService.this.mSupportsRoundedCornersOnWindows);
                    OverviewProxyService.this.mPipOptional.ifPresent(new StatusBar$$ExternalSyntheticLambda30(bundle, 2));
                    OverviewProxyService.this.mSplitScreenOptional.ifPresent(new StatusBar$$ExternalSyntheticLambda31(bundle, 2));
                    OverviewProxyService.this.mOneHandedOptional.ifPresent(new Monitor$$ExternalSyntheticLambda2(bundle, 3));
                    IShellTransitions.Stub stub2 = (IShellTransitions.Stub) OverviewProxyService.this.mShellTransitions.createExternalInterface();
                    Objects.requireNonNull(stub2);
                    bundle.putBinder("extra_shell_shell_transitions", stub2);
                    OverviewProxyService.this.mStartingSurface.ifPresent(new DozeTriggers$$ExternalSyntheticLambda3(bundle, 4));
                    KeyguardUnlockAnimationController keyguardUnlockAnimationController = OverviewProxyService.this.mSysuiUnlockAnimationController;
                    Objects.requireNonNull(keyguardUnlockAnimationController);
                    bundle.putBinder("unlock_animation", keyguardUnlockAnimationController);
                    OverviewProxyService.this.mRecentTasks.ifPresent(new DozeTriggers$$ExternalSyntheticLambda2(bundle, 3));
                    OverviewProxyService.this.mBackAnimation.ifPresent(new StatusBar$$ExternalSyntheticLambda32(bundle, 1));
                    try {
                        OverviewProxyService.this.mOverviewProxy.onInitialize(bundle);
                    } catch (RemoteException e) {
                        OverviewProxyService.this.mCurrentBoundedUserId = -1;
                        Log.e("OverviewProxyService", "Failed to call onInitialize()", e);
                    }
                    OverviewProxyService overviewProxyService4 = OverviewProxyService.this;
                    Objects.requireNonNull(overviewProxyService4);
                    IOverviewProxy iOverviewProxy2 = overviewProxyService4.mOverviewProxy;
                    if (!(iOverviewProxy2 == null || (region = overviewProxyService4.mActiveNavBarRegion) == null)) {
                        try {
                            iOverviewProxy2.onActiveNavBarRegionChanges(region);
                        } catch (RemoteException e2) {
                            Log.e("OverviewProxyService", "Failed to call onActiveNavBarRegionChanges()", e2);
                        }
                    }
                    OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                    Objects.requireNonNull(overviewProxyService5);
                    NavigationBarController navigationBarController = overviewProxyService5.mNavBarControllerLazy.get();
                    Objects.requireNonNull(navigationBarController);
                    NavigationBar navigationBar = navigationBarController.mNavigationBars.get(0);
                    NavigationBarView navigationBarView = overviewProxyService5.mNavBarControllerLazy.get().getNavigationBarView(overviewProxyService5.mContext.getDisplayId());
                    StatusBar statusBar = (StatusBar) overviewProxyService5.mStatusBarOptionalLazy.get().get();
                    Objects.requireNonNull(statusBar);
                    NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
                    if (navigationBar != null) {
                        navigationBar.updateSystemUiStateFlags();
                    }
                    if (navigationBarView != null) {
                        navigationBarView.updateDisabledSystemUiStateFlags();
                    }
                    if (notificationPanelViewController != null) {
                        notificationPanelViewController.updateSystemUiStateFlags();
                    }
                    NotificationShadeWindowController notificationShadeWindowController = overviewProxyService5.mStatusBarWinController;
                    if (notificationShadeWindowController != null) {
                        notificationShadeWindowController.notifyStateChangedCallbacks();
                    }
                    OverviewProxyService overviewProxyService6 = OverviewProxyService.this;
                    SysUiState sysUiState = overviewProxyService6.mSysUiState;
                    Objects.requireNonNull(sysUiState);
                    int i2 = sysUiState.mFlags;
                    try {
                        IOverviewProxy iOverviewProxy3 = overviewProxyService6.mOverviewProxy;
                        if (iOverviewProxy3 != null) {
                            iOverviewProxy3.onSystemUiStateChanged(i2);
                        }
                    } catch (RemoteException e3) {
                        Log.e("OverviewProxyService", "Failed to notify sysui state change", e3);
                    }
                    OverviewProxyService.this.notifyConnectionChanged();
                } catch (RemoteException e4) {
                    Log.e("OverviewProxyService", "Lost connection to launcher service", e4);
                    OverviewProxyService.this.disconnectFromLauncherService();
                    OverviewProxyService.this.retryConnectionWithBackoff();
                }
            }

            public final void onServiceDisconnected(ComponentName componentName) {
                Log.w("OverviewProxyService", "Service disconnected");
                OverviewProxyService.this.mCurrentBoundedUserId = -1;
            }
        };
        OverviewProxyService$$ExternalSyntheticLambda2 overviewProxyService$$ExternalSyntheticLambda2 = new OverviewProxyService$$ExternalSyntheticLambda2(this);
        this.mStatusBarWindowCallback = overviewProxyService$$ExternalSyntheticLambda2;
        this.mSplitScreenBoundsChangeListener = new OverviewProxyService$$ExternalSyntheticLambda3(this);
        this.mOverviewServiceDeathRcpt = new OverviewProxyService$$ExternalSyntheticLambda0(this);
        this.mContext = context;
        this.mPipOptional = optional;
        this.mStatusBarOptionalLazy = lazy2;
        this.mHandler = new Handler();
        this.mNavBarControllerLazy = lazy;
        this.mStatusBarWinController = notificationShadeWindowController2;
        this.mConnectionBackoffAttempts = 0;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(17040010));
        this.mRecentsComponentName = unflattenFromString;
        Intent intent = new Intent("android.intent.action.QUICKSTEP_SERVICE").setPackage(unflattenFromString.getPackageName());
        this.mQuickStepIntent = intent;
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mSupportsRoundedCornersOnWindows = ScreenDecorationsUtils.supportsRoundedCornersOnWindows(context.getResources());
        this.mSysUiState = sysUiState2;
        OverviewProxyService$$ExternalSyntheticLambda1 overviewProxyService$$ExternalSyntheticLambda1 = new OverviewProxyService$$ExternalSyntheticLambda1(this);
        Objects.requireNonNull(sysUiState);
        sysUiState2.mCallbacks.add(overviewProxyService$$ExternalSyntheticLambda1);
        overviewProxyService$$ExternalSyntheticLambda1.onSystemUiStateChanged(sysUiState2.mFlags);
        this.mOneHandedOptional = optional4;
        this.mShellTransitions = shellTransitions;
        this.mRecentTasks = optional5;
        this.mBackAnimation = optional6;
        this.mUiEventLogger = uiEventLogger;
        this.mNavBarButtonAlpha = 1.0f;
        dumpManager.registerDumpable("OverviewProxyService", this);
        this.mNavBarMode = navigationModeController.addListener(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(unflattenFromString.getPackageName(), 0);
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        context.registerReceiver(r7, intentFilter);
        notificationShadeWindowController2.registerCallback(overviewProxyService$$ExternalSyntheticLambda2);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        commandQueue.addCallback((CommandQueue.Callbacks) new CommandQueue.Callbacks() {
            public final void onTracingStateChanged(boolean z) {
                SysUiState sysUiState = OverviewProxyService.this.mSysUiState;
                sysUiState.setFlag(4096, z);
                sysUiState.commitUpdate(OverviewProxyService.this.mContext.getDisplayId());
            }
        });
        this.mCommandQueue = commandQueue;
        this.mSplitScreenOptional = optional3;
        optional8.ifPresent(new OverviewProxyService$$ExternalSyntheticLambda4(this, 0));
        this.mLegacySplitScreenOptional = optional8;
        startTracking();
        C10615 r2 = new ScreenLifecycle.Observer() {
            public final void onScreenTurnedOn() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                Objects.requireNonNull(overviewProxyService);
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        iOverviewProxy.onScreenTurnedOn();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turned on event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call notifyScreenTurnedOn()", e);
                }
            }
        };
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle.mObservers.add(r2);
        Objects.requireNonNull(ActivityManagerWrapper.sInstance);
        this.mIsEnabled = context.getPackageManager().resolveServiceAsUser(intent, 1048576, ActivityManagerWrapper.getCurrentUserId()) != null ? true : z;
        startConnectionToCurrentUser();
        this.mStartingSurface = optional7;
        this.mSysuiUnlockAnimationController = keyguardUnlockAnimationController;
    }

    public final void onUserSwitched(int i) {
        this.mConnectionBackoffAttempts = 0;
        internalConnectToCurrentUser();
    }

    public final void addCallback(OverviewProxyListener overviewProxyListener) {
        if (!this.mConnectionCallbacks.contains(overviewProxyListener)) {
            this.mConnectionCallbacks.add(overviewProxyListener);
        }
        overviewProxyListener.onConnectionChanged(this.mOverviewProxy != null);
        overviewProxyListener.onNavBarButtonAlphaChanged(this.mNavBarButtonAlpha, false);
    }

    public final void disconnectFromLauncherService() {
        if (this.mBound) {
            this.mContext.unbindService(this.mOverviewServiceConnection);
            this.mBound = false;
        }
        IOverviewProxy iOverviewProxy = this.mOverviewProxy;
        if (iOverviewProxy != null) {
            iOverviewProxy.asBinder().unlinkToDeath(this.mOverviewServiceDeathRcpt, 0);
            this.mOverviewProxy = null;
            int size = this.mConnectionCallbacks.size();
            while (true) {
                size--;
                if (size >= 0) {
                    ((OverviewProxyListener) this.mConnectionCallbacks.get(size)).onNavBarButtonAlphaChanged(1.0f, false);
                } else {
                    notifyConnectionChanged();
                    return;
                }
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        String str22;
        String str23;
        String str24;
        printWriter.println("OverviewProxyService state:");
        printWriter.print("  isConnected=");
        boolean z2 = false;
        if (this.mOverviewProxy != null) {
            z = true;
        } else {
            z = false;
        }
        printWriter.println(z);
        printWriter.print("  mIsEnabled=");
        printWriter.println(this.mIsEnabled);
        printWriter.print("  mRecentsComponentName=");
        printWriter.println(this.mRecentsComponentName);
        printWriter.print("  mQuickStepIntent=");
        printWriter.println(this.mQuickStepIntent);
        printWriter.print("  mBound=");
        printWriter.println(this.mBound);
        printWriter.print("  mCurrentBoundedUserId=");
        printWriter.println(this.mCurrentBoundedUserId);
        printWriter.print("  mConnectionBackoffAttempts=");
        printWriter.println(this.mConnectionBackoffAttempts);
        printWriter.print("  mInputFocusTransferStarted=");
        printWriter.println(this.mInputFocusTransferStarted);
        printWriter.print("  mInputFocusTransferStartY=");
        printWriter.println(this.mInputFocusTransferStartY);
        printWriter.print("  mInputFocusTransferStartMillis=");
        printWriter.println(this.mInputFocusTransferStartMillis);
        printWriter.print("  mWindowCornerRadius=");
        printWriter.println(this.mWindowCornerRadius);
        printWriter.print("  mSupportsRoundedCornersOnWindows=");
        printWriter.println(this.mSupportsRoundedCornersOnWindows);
        printWriter.print("  mNavBarButtonAlpha=");
        printWriter.println(this.mNavBarButtonAlpha);
        printWriter.print("  mActiveNavBarRegion=");
        printWriter.println(this.mActiveNavBarRegion);
        printWriter.print("  mNavBarMode=");
        printWriter.println(this.mNavBarMode);
        SysUiState sysUiState = this.mSysUiState;
        Objects.requireNonNull(sysUiState);
        printWriter.println("SysUiState state:");
        printWriter.print("  mSysUiStateFlags=");
        printWriter.println(sysUiState.mFlags);
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        int i = sysUiState.mFlags;
        StringJoiner stringJoiner = new StringJoiner("|");
        String str25 = "";
        if ((i & 1) != 0) {
            str = "screen_pinned";
        } else {
            str = str25;
        }
        stringJoiner.add(str);
        if ((i & 128) != 0) {
            str2 = "overview_disabled";
        } else {
            str2 = str25;
        }
        stringJoiner.add(str2);
        if ((i & 256) != 0) {
            str3 = "home_disabled";
        } else {
            str3 = str25;
        }
        stringJoiner.add(str3);
        if ((i & 1024) != 0) {
            str4 = "search_disabled";
        } else {
            str4 = str25;
        }
        stringJoiner.add(str4);
        if ((i & 2) != 0) {
            str5 = "navbar_hidden";
        } else {
            str5 = str25;
        }
        stringJoiner.add(str5);
        if ((i & 4) != 0) {
            str6 = "notif_visible";
        } else {
            str6 = str25;
        }
        stringJoiner.add(str6);
        if ((i & 2048) != 0) {
            str7 = "qs_visible";
        } else {
            str7 = str25;
        }
        stringJoiner.add(str7);
        if ((i & 64) != 0) {
            str8 = "keygrd_visible";
        } else {
            str8 = str25;
        }
        stringJoiner.add(str8);
        if ((i & 512) != 0) {
            str9 = "keygrd_occluded";
        } else {
            str9 = str25;
        }
        stringJoiner.add(str9);
        if ((i & 8) != 0) {
            str10 = "bouncer_visible";
        } else {
            str10 = str25;
        }
        stringJoiner.add(str10);
        if ((32768 & i) != 0) {
            str11 = "global_actions";
        } else {
            str11 = str25;
        }
        stringJoiner.add(str11);
        if ((i & 16) != 0) {
            str12 = "a11y_click";
        } else {
            str12 = str25;
        }
        stringJoiner.add(str12);
        if ((i & 32) != 0) {
            str13 = "a11y_long_click";
        } else {
            str13 = str25;
        }
        stringJoiner.add(str13);
        if ((i & 4096) != 0) {
            str14 = "tracing";
        } else {
            str14 = str25;
        }
        stringJoiner.add(str14);
        if ((i & 8192) != 0) {
            str15 = "asst_gesture_constrain";
        } else {
            str15 = str25;
        }
        stringJoiner.add(str15);
        if ((i & 16384) != 0) {
            str16 = "bubbles_expanded";
        } else {
            str16 = str25;
        }
        stringJoiner.add(str16);
        if ((65536 & i) != 0) {
            str17 = "one_handed_active";
        } else {
            str17 = str25;
        }
        stringJoiner.add(str17);
        if ((i & 131072) != 0) {
            str18 = "allow_gesture";
        } else {
            str18 = str25;
        }
        stringJoiner.add(str18);
        if ((262144 & i) != 0) {
            str19 = "ime_visible";
        } else {
            str19 = str25;
        }
        stringJoiner.add(str19);
        if ((524288 & i) != 0) {
            str20 = "magnification_overlap";
        } else {
            str20 = str25;
        }
        stringJoiner.add(str20);
        if ((1048576 & i) != 0) {
            str21 = "ime_switcher_showing";
        } else {
            str21 = str25;
        }
        stringJoiner.add(str21);
        if ((2097152 & i) != 0) {
            str22 = "device_dozing";
        } else {
            str22 = str25;
        }
        stringJoiner.add(str22);
        if ((4194304 & i) != 0) {
            str23 = "back_disabled";
        } else {
            str23 = str25;
        }
        stringJoiner.add(str23);
        if ((8388608 & i) != 0) {
            str24 = "bubbles_mange_menu_expanded";
        } else {
            str24 = str25;
        }
        stringJoiner.add(str24);
        if ((i & 16777216) != 0) {
            str25 = "immersive_mode";
        }
        stringJoiner.add(str25);
        sb.append(stringJoiner.toString());
        printWriter.println(sb.toString());
        printWriter.print("    backGestureDisabled=");
        printWriter.println(R$color.isBackGestureDisabled(sysUiState.mFlags));
        printWriter.print("    assistantGestureDisabled=");
        int i2 = sysUiState.mFlags;
        if ((i2 & 131072) != 0) {
            i2 &= -3;
        }
        if ((i2 & 3083) != 0 || ((i2 & 4) != 0 && (i2 & 64) == 0)) {
            z2 = true;
        }
        printWriter.println(z2);
    }

    public final void notifyBackAction(boolean z, int i, int i2, boolean z2, boolean z3) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onBackAction(z, i, i2, z2, z3);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to notify back action", e);
        }
    }

    public final void notifyConnectionChanged() {
        boolean z;
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            OverviewProxyListener overviewProxyListener = (OverviewProxyListener) this.mConnectionCallbacks.get(size);
            if (this.mOverviewProxy != null) {
                z = true;
            } else {
                z = false;
            }
            overviewProxyListener.onConnectionChanged(z);
        }
    }

    public final void removeCallback(OverviewProxyListener overviewProxyListener) {
        this.mConnectionCallbacks.remove(overviewProxyListener);
    }

    public final void retryConnectionWithBackoff() {
        if (!this.mHandler.hasCallbacks(this.mConnectionRunnable)) {
            long min = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
            this.mHandler.postDelayed(this.mConnectionRunnable, min);
            this.mConnectionBackoffAttempts++;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Failed to connect on attempt ");
            m.append(this.mConnectionBackoffAttempts);
            m.append(" will try again in ");
            m.append(min);
            m.append("ms");
            Log.w("OverviewProxyService", m.toString());
        }
    }

    public final boolean shouldShowSwipeUpUI() {
        boolean z;
        if (this.mIsEnabled) {
            if (this.mNavBarMode == 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return true;
            }
        }
        return false;
    }

    public final void startConnectionToCurrentUser() {
        if (this.mHandler.getLooper() != Looper.myLooper()) {
            this.mHandler.post(this.mConnectionRunnable);
        } else {
            internalConnectToCurrentUser();
        }
    }

    public final void internalConnectToCurrentUser() {
        disconnectFromLauncherService();
        if (!this.mIsEnabled) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot attempt connection, is enabled ");
            m.append(this.mIsEnabled);
            Log.v("OverviewProxyService", m.toString());
            return;
        }
        this.mHandler.removeCallbacks(this.mConnectionRunnable);
        try {
            this.mBound = this.mContext.bindServiceAsUser(new Intent("android.intent.action.QUICKSTEP_SERVICE").setPackage(this.mRecentsComponentName.getPackageName()), this.mOverviewServiceConnection, 33554433, UserHandle.of(getCurrentUserId()));
        } catch (SecurityException e) {
            Log.e("OverviewProxyService", "Unable to bind because of security error", e);
        }
        if (this.mBound) {
            this.mHandler.postDelayed(this.mDeferredConnectionCallback, 5000);
        } else {
            retryConnectionWithBackoff();
        }
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }
}
