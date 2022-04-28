package com.google.android.systemui.titan;

import android.animation.AnimationHandler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.INotificationManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.StatsManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.smartspace.SmartspaceManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.om.OverlayManager;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.NightDisplayListener;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.IAudioService;
import android.media.MediaRouter2Manager;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IThermalService;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.permission.PermissionManager;
import android.service.dreams.IDreamManager;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.CrossWindowBlurListeners;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.runtime.R$id;
import androidx.mediarouter.R$color;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IBatteryStats;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.AdminSecondaryLockScreenController_Factory_Factory;
import com.android.keyguard.CarrierText;
import com.android.keyguard.CarrierTextController;
import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.CarrierTextManager_Builder_Factory;
import com.android.keyguard.EmergencyButtonController_Factory_Factory;
import com.android.keyguard.KeyguardBiometricLockoutLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger_Factory;
import com.android.keyguard.KeyguardClockSwitchController;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardDisplayManager_Factory;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardHostViewController_Factory;
import com.android.keyguard.KeyguardInputViewController_Factory_Factory;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardMessageAreaController_Factory_Factory;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController_Factory_Factory;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityModel_Factory;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.KeyguardSecurityViewFlipperController_Factory;
import com.android.keyguard.KeyguardSliceViewController;
import com.android.keyguard.KeyguardSliceViewController_Factory;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUnfoldTransition_Factory;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor_Factory;
import com.android.keyguard.LiftToActivateListener_Factory;
import com.android.keyguard.LockIconView;
import com.android.keyguard.LockIconViewController;
import com.android.keyguard.LockIconViewController_Factory;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.clock.ClockManager;
import com.android.keyguard.clock.ClockManager_Factory;
import com.android.keyguard.clock.ClockOptionsProvider;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.keyguard.dagger.KeyguardQsUserSwitchComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.keyguard.dagger.KeyguardUserSwitcherComponent;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.keyguard.mediator.ScreenOnCoordinator_Factory;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.RootDisplayAreaOrganizer;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.ShellCommandHandler;
import com.android.p012wm.shell.ShellCommandHandlerImpl;
import com.android.p012wm.shell.ShellInit;
import com.android.p012wm.shell.ShellInitImpl;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskViewFactory;
import com.android.p012wm.shell.TaskViewFactoryController;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.p012wm.shell.animation.FlingAnimationUtils_Builder_Factory;
import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.back.BackAnimationController;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.p012wm.shell.dagger.TvPipModule_ProvideTvPipBoundsStateFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvidesTvPipMenuControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideBubblesFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideDisplayControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideDisplayLayoutFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideDragAndDropControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideFloatingContentCoordinatorFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideHideDisplayCutoutFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvidePipUiEventLoggerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideShellCommandHandlerImplFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideShellInitImplFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideShellTaskOrganizerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideSplitScreenFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideSystemWindowsFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideTaskViewFactoryControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideTransactionPoolFactory;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideMainHandlerFactory;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainExecutorFactory;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainHandlerFactory;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideAppPairsFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideBubbleControllerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideLegacySplitScreenFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideOneHandedControllerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipMotionHelperFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipSnapAlgorithmFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipTaskOrganizerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipTouchHandlerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipTransitionControllerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidePipTransitionStateFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideSplitScreenControllerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvideUnfoldBackgroundControllerFactory;
import com.android.p012wm.shell.dagger.WMShellModule_ProvidesPipBoundsAlgorithmFactory;
import com.android.p012wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.p012wm.shell.draganddrop.DragAndDrop;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.p012wm.shell.freeform.FreeformTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutout;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipTransitionState;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.pip.phone.PipAppOpsListener;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.p012wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.p012wm.shell.transition.Transitions;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.p012wm.shell.unfold.UnfoldBackgroundController;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.settingslib.dream.DreamBackend;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.ActivityIntentHelper_Factory;
import com.android.systemui.ActivityStarterDelegate;
import com.android.systemui.ActivityStarterDelegate_Factory;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.BootCompleteCacheImpl_Factory;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.Dependency_Factory;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.ForegroundServiceController_Factory;
import com.android.systemui.ForegroundServiceNotificationListener;
import com.android.systemui.ForegroundServiceNotificationListener_Factory;
import com.android.systemui.ForegroundServicesDialog;
import com.android.systemui.ForegroundServicesDialog_Factory;
import com.android.systemui.ImageWallpaper;
import com.android.systemui.ImageWallpaper_Factory;
import com.android.systemui.InitController;
import com.android.systemui.InitController_Factory;
import com.android.systemui.LatencyTester;
import com.android.systemui.LatencyTester_Factory;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.ScreenDecorations_Factory;
import com.android.systemui.SliceBroadcastRelayHandler;
import com.android.systemui.SliceBroadcastRelayHandler_Factory;
import com.android.systemui.SystemUIAppComponentFactory;
import com.android.systemui.SystemUIService;
import com.android.systemui.SystemUIService_Factory;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.UiOffloadThread_Factory;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.ModeSwitchesController;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.accessibility.SystemActions_Factory;
import com.android.systemui.accessibility.WindowMagnification;
import com.android.systemui.accessibility.WindowMagnification_Factory;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.appops.AppOpsControllerImpl_Factory;
import com.android.systemui.assist.AssistLogger_Factory;
import com.android.systemui.assist.AssistModule_ProvideAssistUtilsFactory;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.PhoneStateMonitor_Factory;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.android.systemui.assist.p003ui.DefaultUiController_Factory;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.battery.BatteryMeterViewController_Factory;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthController_Factory;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.AuthRippleController_Factory;
import com.android.systemui.biometrics.AuthRippleView;
import com.android.systemui.biometrics.SidefpsController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsController_Factory;
import com.android.systemui.biometrics.UdfpsHapticsSimulator;
import com.android.systemui.biometrics.UdfpsHapticsSimulator_Factory;
import com.android.systemui.biometrics.UdfpsHbmProvider;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.BrightLineFalsingManager_Factory;
import com.android.systemui.classifier.DiagonalClassifier_Factory;
import com.android.systemui.classifier.DistanceClassifier_Factory;
import com.android.systemui.classifier.DoubleTapClassifier;
import com.android.systemui.classifier.DoubleTapClassifier_Factory;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl_Factory;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.FalsingManagerProxy;
import com.android.systemui.classifier.FalsingManagerProxy_Factory;
import com.android.systemui.classifier.FalsingModule_ProvidesBrightLineGestureClassifiersFactory;
import com.android.systemui.classifier.FalsingModule_ProvidesDoubleTapTimeoutMsFactory;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.classifier.ProximityClassifier_Factory;
import com.android.systemui.classifier.SingleTapClassifier;
import com.android.systemui.classifier.SingleTapClassifier_Factory;
import com.android.systemui.classifier.TypeClassifier;
import com.android.systemui.classifier.TypeClassifier_Factory;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.clipboardoverlay.ClipboardListener_Factory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayControllerFactory;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.colorextraction.SysuiColorExtractor_Factory;
import com.android.systemui.communal.CommunalHostView;
import com.android.systemui.communal.CommunalHostViewController;
import com.android.systemui.communal.CommunalHostViewController_Factory;
import com.android.systemui.communal.CommunalSourceMonitor;
import com.android.systemui.communal.CommunalSourceMonitor_Factory;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.communal.CommunalStateController_Factory;
import com.android.systemui.communal.conditions.CommunalSettingCondition;
import com.android.systemui.communal.dagger.CommunalModule_ProvideCommunalSourceMonitorFactory;
import com.android.systemui.communal.dagger.CommunalViewComponent;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.ControlsMetricsLoggerImpl_Factory;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.CustomIconCache_Factory;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl_Factory;
import com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.dagger.ControlsComponent_Factory;
import com.android.systemui.controls.management.ControlsEditingActivity;
import com.android.systemui.controls.management.ControlsEditingActivity_Factory;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity_Factory;
import com.android.systemui.controls.management.ControlsRequestDialog;
import com.android.systemui.controls.p004ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.p004ui.ControlActionCoordinatorImpl_Factory;
import com.android.systemui.controls.p004ui.ControlsActivity;
import com.android.systemui.controls.p004ui.ControlsActivity_Factory;
import com.android.systemui.controls.p004ui.ControlsUiControllerImpl;
import com.android.systemui.dagger.C0768xb6bb24d6;
import com.android.systemui.dagger.C0769xb6bb24d7;
import com.android.systemui.dagger.C0770xb6bb24d8;
import com.android.systemui.dagger.C0771xb6bb24d9;
import com.android.systemui.dagger.C0772x82ed519d;
import com.android.systemui.dagger.C0773x82ed519e;
import com.android.systemui.dagger.C0774xb22f7179;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.ContextComponentResolver;
import com.android.systemui.dagger.ContextComponentResolver_Factory;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dagger.DependencyProvider_ProvideHandlerFactory;
import com.android.systemui.dagger.DependencyProvider_ProvideLeakDetectorFactory;
import com.android.systemui.dagger.DependencyProvider_ProviderLayoutInflaterFactory;
import com.android.systemui.dagger.DependencyProvider_ProvidesChoreographerFactory;
import com.android.systemui.dagger.DependencyProvider_ProvidesModeSwitchesControllerFactory;
import com.android.systemui.dagger.DependencyProvider_ProvidesViewMediatorCallbackFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideActivityTaskManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideFaceManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIActivityManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIAudioServiceFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIBatteryStatsFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIDreamManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIPackageManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIStatusBarServiceFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIWallPaperManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIWindowManagerFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideInteractionJankMonitorFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideOptionalVibratorFactory;
import com.android.systemui.dagger.FrameworkServicesModule_ProvidePackageManagerWrapperFactory;
import com.android.systemui.dagger.GlobalModule;
import com.android.systemui.dagger.GlobalModule_ProvideUiEventLoggerFactory;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.dagger.NightDisplayListenerModule;
import com.android.systemui.dagger.NightDisplayListenerModule_Builder_Factory;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.SystemUIModule_ProvideBubblesManagerFactory;
import com.android.systemui.dagger.SystemUIModule_ProvideLowLightClockControllerFactory;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.AlwaysOnDisplayPolicy;
import com.android.systemui.doze.DozeAuthRemover;
import com.android.systemui.doze.DozeAuthRemover_Factory;
import com.android.systemui.doze.DozeDockHandler;
import com.android.systemui.doze.DozeFalsingManagerAdapter;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeLog_Factory;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.doze.DozeLogger_Factory;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeMachine_Factory;
import com.android.systemui.doze.DozePauser;
import com.android.systemui.doze.DozePauser_Factory;
import com.android.systemui.doze.DozeScreenBrightness;
import com.android.systemui.doze.DozeScreenBrightness_Factory;
import com.android.systemui.doze.DozeScreenState;
import com.android.systemui.doze.DozeScreenState_Factory;
import com.android.systemui.doze.DozeService;
import com.android.systemui.doze.DozeService_Factory;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.doze.DozeTriggers_Factory;
import com.android.systemui.doze.DozeUi;
import com.android.systemui.doze.DozeUi_Factory;
import com.android.systemui.doze.DozeWallpaperState;
import com.android.systemui.doze.DozeWallpaperState_Factory;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.doze.dagger.DozeModule_ProvidesDozeMachinePartesFactory;
import com.android.systemui.doze.dagger.DozeModule_ProvidesDozeWakeLockFactory;
import com.android.systemui.doze.dagger.DozeModule_ProvidesWrappedServiceFactory;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.dreams.DreamOverlayContainerViewController_Factory;
import com.android.systemui.dreams.DreamOverlayRegistrant;
import com.android.systemui.dreams.DreamOverlayRegistrant_Factory;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayService_Factory;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController_Factory;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController_Factory;
import com.android.systemui.dreams.SmartSpaceComplication;
import com.android.systemui.dreams.SmartSpaceComplication_Registrant_Factory;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationCollectionLiveData;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.complication.ComplicationTypesUpdater;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.dreams.complication.ComplicationViewModelProvider;
import com.android.systemui.dreams.complication.ComplicationViewModelTransformer;
import com.android.systemui.dreams.complication.DreamClockDateComplication;
import com.android.systemui.dreams.complication.DreamClockDateComplication_Factory;
import com.android.systemui.dreams.complication.DreamClockDateComplication_Registrant_Factory;
import com.android.systemui.dreams.complication.DreamClockTimeComplication;
import com.android.systemui.dreams.complication.DreamClockTimeComplication_Factory;
import com.android.systemui.dreams.complication.DreamClockTimeComplication_Registrant_Factory;
import com.android.systemui.dreams.complication.DreamWeatherComplication;
import com.android.systemui.dreams.complication.DreamWeatherComplication_Factory;
import com.android.systemui.dreams.complication.DreamWeatherComplication_Registrant_Factory;
import com.android.systemui.dreams.complication.dagger.C0792x8a1d9ad2;
import com.android.systemui.dreams.complication.dagger.C0793x7dca8e24;
import com.android.systemui.dreams.complication.dagger.C0794x1653c7a9;
import com.android.systemui.dreams.complication.dagger.C0795x28793289;
import com.android.systemui.dreams.complication.dagger.C0796x871f559c;
import com.android.systemui.dreams.complication.dagger.C0797x2fbedb8b;
import com.android.systemui.dreams.complication.dagger.C0798x4215cf9e;
import com.android.systemui.dreams.complication.dagger.C0799x7019a799;
import com.android.systemui.dreams.complication.dagger.C0800x56e4d2ac;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewComponent;
import com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent;
import com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent$Factory;
import com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent$Factory;
import com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent$Factory;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesMaxBurnInOffsetFactory;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.touch.dagger.C0805x4f80e514;
import com.android.systemui.dreams.touch.dagger.C0806x6a8a7f11;
import com.android.systemui.dreams.touch.dagger.InputSessionComponent;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpHandler_Factory;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpManager_Factory;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.dump.LogBufferEulogizer_Factory;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.LogBufferFreezer_Factory;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.FeatureFlagsRelease_Factory;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.fragments.FragmentService_Factory;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.GlobalActionsComponent_Factory;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.globalactions.GlobalActionsDialogLite_Factory;
import com.android.systemui.globalactions.GlobalActionsImpl;
import com.android.systemui.globalactions.GlobalActionsImpl_Factory;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper;
import com.android.systemui.keyboard.KeyboardUI;
import com.android.systemui.keyboard.KeyboardUI_Factory;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.KeyguardLifecyclesDispatcher;
import com.android.systemui.keyguard.KeyguardLifecyclesDispatcher_Factory;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.ScreenLifecycle_Factory;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle_Factory;
import com.android.systemui.keyguard.WorkLockActivity;
import com.android.systemui.keyguard.dagger.KeyguardModule_NewKeyguardViewMediatorFactory;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.LogBufferFactory_Factory;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.SessionTracker_Factory;
import com.android.systemui.log.dagger.LogModule_ProvidePrivacyLogBufferFactory;
import com.android.systemui.lowlightclock.LowLightClockController;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.media.KeyguardMediaController_Factory;
import com.android.systemui.media.LocalMediaManagerFactory;
import com.android.systemui.media.MediaBrowserFactory;
import com.android.systemui.media.MediaBrowserFactory_Factory;
import com.android.systemui.media.MediaCarouselController;
import com.android.systemui.media.MediaControlPanel;
import com.android.systemui.media.MediaControlPanel_Factory;
import com.android.systemui.media.MediaControllerFactory;
import com.android.systemui.media.MediaControllerFactory_Factory;
import com.android.systemui.media.MediaDataFilter;
import com.android.systemui.media.MediaDataFilter_Factory;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaDataManager_Factory;
import com.android.systemui.media.MediaDeviceManager;
import com.android.systemui.media.MediaDeviceManager_Factory;
import com.android.systemui.media.MediaFeatureFlag;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.MediaFlags_Factory;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.media.MediaHierarchyManager_Factory;
import com.android.systemui.media.MediaHost;
import com.android.systemui.media.MediaHostStatesManager;
import com.android.systemui.media.MediaHostStatesManager_Factory;
import com.android.systemui.media.MediaResumeListener;
import com.android.systemui.media.MediaResumeListener_Factory;
import com.android.systemui.media.MediaSessionBasedFilter;
import com.android.systemui.media.MediaSessionBasedFilter_Factory;
import com.android.systemui.media.MediaTimeoutListener;
import com.android.systemui.media.MediaViewController;
import com.android.systemui.media.MediaViewController_Factory;
import com.android.systemui.media.ResumeMediaBrowserFactory;
import com.android.systemui.media.ResumeMediaBrowserFactory_Factory;
import com.android.systemui.media.RingtonePlayer;
import com.android.systemui.media.RingtonePlayer_Factory;
import com.android.systemui.media.SeekBarViewModel;
import com.android.systemui.media.SeekBarViewModel_Factory;
import com.android.systemui.media.dagger.MediaModule_ProvidesDreamMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesKeyguardMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesMediaTttChipControllerSenderFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQSMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQuickQSMediaHostFactory;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver_Factory;
import com.android.systemui.media.dream.MediaDreamComplication;
import com.android.systemui.media.dream.MediaDreamComplication_Factory;
import com.android.systemui.media.dream.MediaDreamSentinel;
import com.android.systemui.media.dream.MediaDreamSentinel_Factory;
import com.android.systemui.media.dream.dagger.C0904x5add62f3;
import com.android.systemui.media.dream.dagger.C0905xd5b79ace;
import com.android.systemui.media.dream.dagger.MediaComplicationComponent$Factory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver;
import com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver_Factory;
import com.android.systemui.media.taptotransfer.sender.MediaTttChipControllerSender;
import com.android.systemui.media.taptotransfer.sender.MediaTttChipControllerSender_Factory;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavBarHelper_Factory;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarController_Factory;
import com.android.systemui.navigationbar.NavigationBarOverlayController_Factory;
import com.android.systemui.navigationbar.NavigationBar_Factory_Factory;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.NavigationModeController_Factory;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.TaskbarDelegate_Factory;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler_Factory_Factory;
import com.android.systemui.p006qs.AutoAddTracker;
import com.android.systemui.p006qs.AutoAddTracker_Builder_Factory;
import com.android.systemui.p006qs.FgsManagerController;
import com.android.systemui.p006qs.FgsManagerController_Factory;
import com.android.systemui.p006qs.FooterActionsController;
import com.android.systemui.p006qs.FooterActionsController_Factory;
import com.android.systemui.p006qs.HeaderPrivacyIconsController_Factory;
import com.android.systemui.p006qs.QSAnimator;
import com.android.systemui.p006qs.QSAnimator_Factory;
import com.android.systemui.p006qs.QSContainerImplController;
import com.android.systemui.p006qs.QSContainerImplController_Factory;
import com.android.systemui.p006qs.QSExpansionPathInterpolator;
import com.android.systemui.p006qs.QSExpansionPathInterpolator_Factory;
import com.android.systemui.p006qs.QSFgsManagerFooter;
import com.android.systemui.p006qs.QSFgsManagerFooter_Factory;
import com.android.systemui.p006qs.QSFooter;
import com.android.systemui.p006qs.QSFooterViewController;
import com.android.systemui.p006qs.QSFooterViewController_Factory;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.p006qs.QSFragmentDisableFlagsLogger;
import com.android.systemui.p006qs.QSPanelController;
import com.android.systemui.p006qs.QSPanelController_Factory;
import com.android.systemui.p006qs.QSSecurityFooter_Factory;
import com.android.systemui.p006qs.QSSquishinessController;
import com.android.systemui.p006qs.QSSquishinessController_Factory;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.QSTileHost_Factory;
import com.android.systemui.p006qs.QSTileRevealController_Factory_Factory;
import com.android.systemui.p006qs.QuickQSPanelController;
import com.android.systemui.p006qs.QuickQSPanelController_Factory;
import com.android.systemui.p006qs.QuickStatusBarHeaderController_Factory;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.p006qs.carrier.C1010xf95dc14f;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController_Builder_Factory;
import com.android.systemui.p006qs.customize.QSCustomizer;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.p006qs.customize.QSCustomizerController_Factory;
import com.android.systemui.p006qs.customize.TileAdapter;
import com.android.systemui.p006qs.customize.TileAdapter_Factory;
import com.android.systemui.p006qs.customize.TileQueryHelper;
import com.android.systemui.p006qs.customize.TileQueryHelper_Factory;
import com.android.systemui.p006qs.dagger.QSFlagsModule_IsPMLiteEnabledFactory;
import com.android.systemui.p006qs.dagger.QSFragmentComponent;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvideQSPanelFactory;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvideRootViewFactory;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvideThemedContextFactory;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvidesQSFooterActionsViewFactory;
import com.android.systemui.p006qs.dagger.QSFragmentModule_ProvidesQuickQSPanelFactory;
import com.android.systemui.p006qs.external.C2507TileLifecycleManager_Factory;
import com.android.systemui.p006qs.external.CustomTile;
import com.android.systemui.p006qs.external.CustomTileStatePersister;
import com.android.systemui.p006qs.external.CustomTile_Builder_Factory;
import com.android.systemui.p006qs.external.PackageManagerAdapter;
import com.android.systemui.p006qs.external.PackageManagerAdapter_Factory;
import com.android.systemui.p006qs.external.TileLifecycleManager;
import com.android.systemui.p006qs.external.TileLifecycleManager_Factory_Impl;
import com.android.systemui.p006qs.external.TileServiceRequestController;
import com.android.systemui.p006qs.external.TileServiceRequestController_Builder_Factory;
import com.android.systemui.p006qs.external.TileServices;
import com.android.systemui.p006qs.external.TileServices_Factory;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.logging.QSLogger_Factory;
import com.android.systemui.p006qs.tiles.AirplaneModeTile;
import com.android.systemui.p006qs.tiles.AirplaneModeTile_Factory;
import com.android.systemui.p006qs.tiles.AlarmTile;
import com.android.systemui.p006qs.tiles.AlarmTile_Factory;
import com.android.systemui.p006qs.tiles.BluetoothTile;
import com.android.systemui.p006qs.tiles.BluetoothTile_Factory;
import com.android.systemui.p006qs.tiles.CameraToggleTile;
import com.android.systemui.p006qs.tiles.CameraToggleTile_Factory;
import com.android.systemui.p006qs.tiles.CastTile;
import com.android.systemui.p006qs.tiles.CastTile_Factory;
import com.android.systemui.p006qs.tiles.CellularTile;
import com.android.systemui.p006qs.tiles.CellularTile_Factory;
import com.android.systemui.p006qs.tiles.ColorCorrectionTile;
import com.android.systemui.p006qs.tiles.ColorCorrectionTile_Factory;
import com.android.systemui.p006qs.tiles.ColorInversionTile;
import com.android.systemui.p006qs.tiles.ColorInversionTile_Factory;
import com.android.systemui.p006qs.tiles.DataSaverTile;
import com.android.systemui.p006qs.tiles.DataSaverTile_Factory;
import com.android.systemui.p006qs.tiles.DeviceControlsTile;
import com.android.systemui.p006qs.tiles.DeviceControlsTile_Factory;
import com.android.systemui.p006qs.tiles.DndTile;
import com.android.systemui.p006qs.tiles.DndTile_Factory;
import com.android.systemui.p006qs.tiles.FlashlightTile;
import com.android.systemui.p006qs.tiles.FlashlightTile_Factory;
import com.android.systemui.p006qs.tiles.HotspotTile;
import com.android.systemui.p006qs.tiles.HotspotTile_Factory;
import com.android.systemui.p006qs.tiles.InternetTile;
import com.android.systemui.p006qs.tiles.InternetTile_Factory;
import com.android.systemui.p006qs.tiles.LocationTile;
import com.android.systemui.p006qs.tiles.LocationTile_Factory;
import com.android.systemui.p006qs.tiles.MicrophoneToggleTile;
import com.android.systemui.p006qs.tiles.MicrophoneToggleTile_Factory;
import com.android.systemui.p006qs.tiles.NfcTile;
import com.android.systemui.p006qs.tiles.NfcTile_Factory;
import com.android.systemui.p006qs.tiles.NightDisplayTile;
import com.android.systemui.p006qs.tiles.NightDisplayTile_Factory;
import com.android.systemui.p006qs.tiles.OneHandedModeTile;
import com.android.systemui.p006qs.tiles.OneHandedModeTile_Factory;
import com.android.systemui.p006qs.tiles.QRCodeScannerTile;
import com.android.systemui.p006qs.tiles.QRCodeScannerTile_Factory;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile_Factory;
import com.android.systemui.p006qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.p006qs.tiles.ReduceBrightColorsTile_Factory;
import com.android.systemui.p006qs.tiles.RotationLockTile_Factory;
import com.android.systemui.p006qs.tiles.ScreenRecordTile;
import com.android.systemui.p006qs.tiles.ScreenRecordTile_Factory;
import com.android.systemui.p006qs.tiles.UiModeNightTile;
import com.android.systemui.p006qs.tiles.UiModeNightTile_Factory;
import com.android.systemui.p006qs.tiles.UserDetailView;
import com.android.systemui.p006qs.tiles.UserDetailView_Adapter_Factory;
import com.android.systemui.p006qs.tiles.WifiTile;
import com.android.systemui.p006qs.tiles.WifiTile_Factory;
import com.android.systemui.p006qs.tiles.WorkModeTile;
import com.android.systemui.p006qs.tiles.WorkModeTile_Factory;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController_Factory;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogFactory_Factory;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.p006qs.user.UserSwitchDialogController_Factory;
import com.android.systemui.p008tv.TvSystemUIModule_ProvideBatteryControllerFactory;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.people.PeopleSpaceActivity;
import com.android.systemui.people.widget.LaunchConversationActivity;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager_Factory;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider_Factory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginDependencyProvider_Factory;
import com.android.systemui.plugins.PluginEnablerImpl;
import com.android.systemui.plugins.PluginEnablerImpl_Factory;
import com.android.systemui.plugins.PluginsModule_ProvidePluginInstanceManagerFactoryFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginDebugFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginExecutorFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginInstanceFactoryFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginManagerFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginPrefsFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPrivilegedPluginsFactory;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.power.PowerNotificationWarnings_Factory;
import com.android.systemui.power.PowerUI;
import com.android.systemui.power.PowerUI_Factory;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogController_Factory;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyItemController_Factory;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger_Factory;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.recents.OverviewProxyRecentsImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService_Factory;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.recents.ScreenPinningRequest_Factory;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.screenrecord.RecordingService_Factory;
import com.android.systemui.screenshot.ActionProxyReceiver;
import com.android.systemui.screenshot.ActionProxyReceiver_Factory;
import com.android.systemui.screenshot.DeleteScreenshotReceiver;
import com.android.systemui.screenshot.ImageExporter_Factory;
import com.android.systemui.screenshot.ImageTileSet_Factory;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.LongScreenshotData;
import com.android.systemui.screenshot.LongScreenshotData_Factory;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController_Factory;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotNotificationsController_Factory;
import com.android.systemui.screenshot.ScreenshotSmartActions;
import com.android.systemui.screenshot.ScreenshotSmartActions_Factory;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.android.systemui.screenshot.ScrollCaptureClient_Factory;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.screenshot.ScrollCaptureController_Factory;
import com.android.systemui.screenshot.SmartActionsReceiver;
import com.android.systemui.screenshot.SmartActionsReceiver_Factory;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.TakeScreenshotService_Factory;
import com.android.systemui.screenshot.TimeoutHandler;
import com.android.systemui.sensorprivacy.SensorUseStartedActivity;
import com.android.systemui.sensorprivacy.SensorUseStartedActivity_Factory;
import com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.brightness.BrightnessController_Factory_Factory;
import com.android.systemui.settings.brightness.BrightnessDialog;
import com.android.systemui.settings.brightness.BrightnessDialog_Factory;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderController_Factory_Factory;
import com.android.systemui.settings.dagger.SettingsModule_ProvideUserTrackerFactory;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.shortcut.ShortcutKeyDispatcher;
import com.android.systemui.shortcut.ShortcutKeyDispatcher_Factory;
import com.android.systemui.statusbar.ActionClickLogger;
import com.android.systemui.statusbar.ActionClickLogger_Factory;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.BlurUtils_Factory;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.DisableFlagsLogger;
import com.android.systemui.statusbar.DisableFlagsLogger_Factory;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController_Factory;
import com.android.systemui.statusbar.MediaArtworkProcessor;
import com.android.systemui.statusbar.MediaArtworkProcessor_Factory;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationListener_Factory;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeDepthController_Factory;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.OperatorNameViewController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.PulseExpansionHandler_Factory;
import com.android.systemui.statusbar.QsFrameTranslateImpl;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.VibratorHelper_Factory;
import com.android.systemui.statusbar.charging.WiredChargingRippleController;
import com.android.systemui.statusbar.charging.WiredChargingRippleController_Factory;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl_WifiPickerTrackerFactory_Factory;
import com.android.systemui.statusbar.connectivity.CallbackHandler;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl_Factory;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.core.StatusBarInitializer_Factory;
import com.android.systemui.statusbar.dagger.C1208x30c882de;
import com.android.systemui.statusbar.dagger.C1209xfa996c5e;
import com.android.systemui.statusbar.dagger.C1210x3f8faa0a;
import com.android.systemui.statusbar.dagger.StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory;
import com.android.systemui.statusbar.dagger.StatusBarDependenciesModule_ProvideOngoingCallControllerFactory;
import com.android.systemui.statusbar.dagger.StatusBarDependenciesModule_ProvideSmartReplyControllerFactory;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewController_Factory;
import com.android.systemui.statusbar.events.SystemEventChipAnimationController;
import com.android.systemui.statusbar.events.SystemEventChipAnimationController_Factory;
import com.android.systemui.statusbar.events.SystemEventCoordinator;
import com.android.systemui.statusbar.events.SystemEventCoordinator_Factory;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler_Factory;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler_Factory;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureLogger;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController_Factory;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.ConversationNotificationManager_Factory;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.DynamicChildBindController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.InstantAppNotifier;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationClickerLogger;
import com.android.systemui.statusbar.notification.NotificationClicker_Builder_Factory;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationEntryManagerLogger;
import com.android.systemui.statusbar.notification.NotificationFilter;
import com.android.systemui.statusbar.notification.NotificationFilter_Factory;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.SectionClassifier_Factory;
import com.android.systemui.statusbar.notification.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection_Factory;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotifPipeline_Factory;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.NotificationRankingManager;
import com.android.systemui.statusbar.notification.collection.NotificationRankingManager_Factory;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.CommunalCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.CommunalCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsModule_NotifCoordinatorsFactory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationPresenterExtensions;
import com.android.systemui.statusbar.notification.collection.legacy.LowPriorityInflationHelper;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy_Factory;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSource;
import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSourceModule_ProvideManagerFactory;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn_Factory;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager_Factory;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory_Impl;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager_Factory;
import com.android.systemui.statusbar.notification.collection.render.StatusBarNotifPanelEventSourceModule_BindStartableFactory;
import com.android.systemui.statusbar.notification.dagger.C1290x3fd4641;
import com.android.systemui.statusbar.notification.dagger.C1291xb614d321;
import com.android.systemui.statusbar.notification.dagger.C1292x39c1fe98;
import com.android.systemui.statusbar.notification.dagger.C1293x34a20792;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationEntryManagerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationGutsManagerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationPanelLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideVisualStabilityManagerFactory;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.notification.icon.IconBuilder;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.init.NotificationsControllerImpl;
import com.android.systemui.statusbar.notification.init.NotificationsControllerImpl_Factory;
import com.android.systemui.statusbar.notification.init.NotificationsControllerStub;
import com.android.systemui.statusbar.notification.interruption.HeadsUpController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpController_Factory;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationLogger_ExpansionStateLogger_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController_Factory;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager_Factory;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCache;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater_Factory;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowContentBindStageLogger;
import com.android.systemui.statusbar.notification.row.RowContentBindStage_Factory;
import com.android.systemui.statusbar.notification.row.dagger.C1334x3e2d0aca;
import com.android.systemui.statusbar.notification.row.dagger.C1335xdc9a80a2;
import com.android.systemui.statusbar.notification.row.dagger.C1336xc255c3ca;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.dagger.NotificationShelfComponent;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AmbientState_Factory;
import com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper_Builder_Factory;
import com.android.systemui.statusbar.p007tv.TvStatusBar_Factory;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationHandler;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationHandler_Factory;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationPanelActivity;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.AutoHideController_Factory_Factory;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.BiometricUnlockController_Factory;
import com.android.systemui.statusbar.phone.C2508LightBarController_Factory;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl_Factory;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeParameters_Factory;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost_Factory;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController_Factory;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.KeyguardBouncer_Factory_Factory;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil_Factory;
import com.android.systemui.statusbar.phone.KeyguardEnvironmentImpl;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarController_Factory_Factory;
import com.android.systemui.statusbar.phone.LightsOutNotifController;
import com.android.systemui.statusbar.phone.LightsOutNotifController_Factory;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.LockscreenWallpaper_Factory;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl_Factory;
import com.android.systemui.statusbar.phone.MultiUserSwitchController;
import com.android.systemui.statusbar.phone.MultiUserSwitchController_Factory_Factory;
import com.android.systemui.statusbar.phone.NotificationGroupAlertTransferHelper;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController_Factory;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.phone.NotificationPanelUnfoldAnimationController;
import com.android.systemui.statusbar.phone.NotificationPanelUnfoldAnimationController_Factory;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController_Factory;
import com.android.systemui.statusbar.phone.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.NotificationShadeWindowControllerImpl_Factory;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import com.android.systemui.statusbar.phone.NotificationTapHelper_Factory_Factory;
import com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy_Factory;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController_Factory_Factory;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimController_Factory;
import com.android.systemui.statusbar.phone.ShadeControllerImpl;
import com.android.systemui.statusbar.phone.ShadeControllerImpl_Factory;
import com.android.systemui.statusbar.phone.SplitShadeHeaderController;
import com.android.systemui.statusbar.phone.SplitShadeHeaderController_Factory;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarCommandQueueCallbacks;
import com.android.systemui.statusbar.phone.StatusBarCommandQueueCallbacks_Factory;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider_Factory;
import com.android.systemui.statusbar.phone.StatusBarDemoMode;
import com.android.systemui.statusbar.phone.StatusBarDemoMode_Factory;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl_Factory;
import com.android.systemui.statusbar.phone.StatusBarIconController_TintedIconManager_Factory_Factory;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager_Factory;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher_Factory;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter_Builder_Factory;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback_Factory;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy_Factory;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager_Factory;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager_Factory;
import com.android.systemui.statusbar.phone.TapAgainView;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController_Factory;
import com.android.systemui.statusbar.phone.dagger.StatusBarComponent;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetBatteryMeterViewControllerFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesStatusBarWindowViewFactory;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger;
import com.android.systemui.statusbar.phone.fragment.dagger.C1597xfe780fd7;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallFlags;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager_Factory;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserInfoTracker;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserInfoTracker_Factory;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherController;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherFeatureController;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl_Factory;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastControllerImpl_Factory;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceControlsControllerImpl;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl_Factory;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl_Factory;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.HotspotControllerImpl_Factory;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController_Factory;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController_Factory;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.LocationControllerImpl_Factory;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler_Factory;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockControllerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl_Factory;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SmartActionInflaterImpl;
import com.android.systemui.statusbar.policy.SmartActionInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.UserSwitcherController_Factory;
import com.android.systemui.statusbar.policy.VariableDateViewController_Factory_Factory;
import com.android.systemui.statusbar.policy.WalletControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.statusbar.policy.dagger.StatusBarPolicyModule_ProvideAccessPointControllerImplFactory;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowController_Factory;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.android.systemui.telephony.TelephonyCallback_Factory;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.telephony.TelephonyListenerManager_Factory;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.theme.ThemeOverlayController;
import com.android.systemui.theme.ThemeOverlayController_Factory;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.toast.ToastFactory_Factory;
import com.android.systemui.toast.ToastLogger;
import com.android.systemui.toast.ToastLogger_Factory;
import com.android.systemui.toast.ToastUI;
import com.android.systemui.toast.ToastUI_Factory;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.ProtoTracer_Factory;
import com.android.systemui.tuner.TunablePadding;
import com.android.systemui.tuner.TunablePadding_TunablePaddingService_Factory;
import com.android.systemui.tuner.TunerActivity;
import com.android.systemui.tuner.TunerActivity_Factory;
import com.android.systemui.tuner.TunerServiceImpl;
import com.android.systemui.tuner.TunerServiceImpl_Factory;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.FoldStateLogger;
import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.SysUIUnfoldModule;
import com.android.systemui.unfold.SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory;
import com.android.systemui.unfold.UnfoldLatencyTracker;
import com.android.systemui.unfold.UnfoldLatencyTracker_Factory;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation_Factory;
import com.android.systemui.unfold.UnfoldSharedModule;
import com.android.systemui.unfold.UnfoldSharedModule_HingeAngleProviderFactory;
import com.android.systemui.unfold.UnfoldSharedModule_UnfoldTransitionProgressProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider_Factory;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.unfold.util.C2509ScaleAwareTransitionProgressProvider_Factory;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider_Factory_Impl;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import com.android.systemui.usb.StorageNotification;
import com.android.systemui.usb.UsbDebuggingActivity;
import com.android.systemui.usb.UsbDebuggingActivity_Factory;
import com.android.systemui.usb.UsbDebuggingSecondaryUserActivity;
import com.android.systemui.usb.UsbDebuggingSecondaryUserActivity_Factory;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity_Factory;
import com.android.systemui.user.UserCreator;
import com.android.systemui.user.UserCreator_Factory;
import com.android.systemui.user.UserSwitcherActivity;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.CarrierConfigTracker_Factory;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceConfigProxy_Factory;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.RingerModeTrackerImpl_Factory;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.WallpaperController_Factory;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl_Factory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainLooperFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBgLooperFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideLongRunningLooperFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideUiBackgroundExecutorFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl_Factory;
import com.android.systemui.util.condition.Condition;
import com.android.systemui.util.condition.Monitor;
import com.android.systemui.util.condition.dagger.MonitorComponent;
import com.android.systemui.util.leak.GarbageMonitor;
import com.android.systemui.util.leak.GarbageMonitor_Factory;
import com.android.systemui.util.leak.GarbageMonitor_MemoryTile_Factory;
import com.android.systemui.util.leak.GarbageMonitor_Service_Factory;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.util.leak.LeakReporter_Factory;
import com.android.systemui.util.p010io.Files;
import com.android.systemui.util.p010io.Files_Factory;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.C1707xe478f0bc;
import com.android.systemui.util.sensors.PostureDependentProximitySensor_Factory;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.SensorModule_ProvidePostureToProximitySensorMappingFactory;
import com.android.systemui.util.sensors.SensorModule_ProvidePrimaryProximitySensorFactory;
import com.android.systemui.util.sensors.SensorModule_ProvideSecondaryProximitySensorFactory;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import com.android.systemui.util.sensors.ThresholdSensorImpl_BuilderFactory_Factory;
import com.android.systemui.util.sensors.ThresholdSensorImpl_Builder_Factory;
import com.android.systemui.util.service.ObservableServiceConnection;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.android.systemui.util.settings.SecureSettingsImpl_Factory;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.util.time.DateFormatUtil_Factory;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl_Factory;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.DelayedWakeLock_Builder_Factory;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.util.wakelock.WakeLock_Builder_Factory;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl_Factory;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.volume.VolumeDialogComponent_Factory;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import com.android.systemui.volume.VolumeDialogControllerImpl_Factory;
import com.android.systemui.volume.VolumeUI;
import com.android.systemui.volume.VolumeUI_Factory;
import com.android.systemui.volume.dagger.VolumeModule_ProvideVolumeDialogFactory;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController_Factory;
import com.android.systemui.wallet.p011ui.WalletActivity;
import com.android.systemui.wallet.p011ui.WalletActivity_Factory;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell_Factory;
import com.google.android.systemui.GoogleServices;
import com.google.android.systemui.GoogleServices_Factory;
import com.google.android.systemui.NotificationLockscreenUserManagerGoogle;
import com.google.android.systemui.NotificationLockscreenUserManagerGoogle_Factory;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.assist.AssistManagerGoogle_Factory;
import com.google.android.systemui.assist.GoogleAssistLogger;
import com.google.android.systemui.assist.GoogleAssistLogger_Factory;
import com.google.android.systemui.assist.OpaEnabledDispatcher;
import com.google.android.systemui.assist.OpaEnabledReceiver;
import com.google.android.systemui.assist.OpaEnabledReceiver_Factory;
import com.google.android.systemui.assist.OpaEnabledSettings;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.assist.uihints.AssistantUIHintsModule_BindEdgeLightsInfoListenersFactory;
import com.google.android.systemui.assist.uihints.AssistantUIHintsModule_ProvideActivityStarterFactory;
import com.google.android.systemui.assist.uihints.AssistantUIHintsModule_ProvideAudioInfoListenersFactory;
import com.google.android.systemui.assist.uihints.AssistantUIHintsModule_ProvideCardInfoListenersFactory;
import com.google.android.systemui.assist.uihints.AssistantUIHintsModule_ProvideConfigInfoListenersFactory;
import com.google.android.systemui.assist.uihints.AssistantWarmer;
import com.google.android.systemui.assist.uihints.AssistantWarmer_Factory;
import com.google.android.systemui.assist.uihints.ColorChangeHandler;
import com.google.android.systemui.assist.uihints.ColorChangeHandler_Factory;
import com.google.android.systemui.assist.uihints.ConfigurationHandler;
import com.google.android.systemui.assist.uihints.FlingVelocityWrapper_Factory;
import com.google.android.systemui.assist.uihints.GlowController;
import com.google.android.systemui.assist.uihints.GoBackHandler_Factory;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import com.google.android.systemui.assist.uihints.IconController;
import com.google.android.systemui.assist.uihints.IconController_Factory;
import com.google.android.systemui.assist.uihints.LightnessProvider_Factory;
import com.google.android.systemui.assist.uihints.NavBarFader;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.NgaMessageHandler_Factory;
import com.google.android.systemui.assist.uihints.NgaUiController;
import com.google.android.systemui.assist.uihints.NgaUiController_Factory;
import com.google.android.systemui.assist.uihints.OverlayUiHost_Factory;
import com.google.android.systemui.assist.uihints.SwipeHandler_Factory;
import com.google.android.systemui.assist.uihints.TaskStackNotifier_Factory;
import com.google.android.systemui.assist.uihints.TimeoutManager_Factory;
import com.google.android.systemui.assist.uihints.TouchInsideHandler;
import com.google.android.systemui.assist.uihints.TouchInsideHandler_Factory;
import com.google.android.systemui.assist.uihints.TouchOutsideHandler_Factory;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;
import com.google.android.systemui.assist.uihints.input.InputModule_ProvideTouchActionRegionsFactory;
import com.google.android.systemui.assist.uihints.input.NgaInputHandler;
import com.google.android.systemui.assist.uihints.input.NgaInputHandler_Factory;
import com.google.android.systemui.assist.uihints.input.TouchActionRegion;
import com.google.android.systemui.assist.uihints.input.TouchInsideRegion;
import com.google.android.systemui.autorotate.AutorotateDataService;
import com.google.android.systemui.autorotate.AutorotateDataService_Factory;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import com.google.android.systemui.columbus.ColumbusContentObserver_Factory_Factory;
import com.google.android.systemui.columbus.ColumbusModule_ProvideBlockingSystemKeysFactory;
import com.google.android.systemui.columbus.ColumbusModule_ProvideColumbusGatesFactory;
import com.google.android.systemui.columbus.ColumbusModule_ProvideTransientGateDurationFactory;
import com.google.android.systemui.columbus.ColumbusModule_ProvideUserSelectedActionsFactory;
import com.google.android.systemui.columbus.ColumbusService;
import com.google.android.systemui.columbus.ColumbusServiceWrapper;
import com.google.android.systemui.columbus.ColumbusServiceWrapper_Factory;
import com.google.android.systemui.columbus.ColumbusService_Factory;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.ColumbusSettings_Factory;
import com.google.android.systemui.columbus.ColumbusStructuredDataManager;
import com.google.android.systemui.columbus.ColumbusStructuredDataManager_Factory;
import com.google.android.systemui.columbus.ColumbusTargetRequestService;
import com.google.android.systemui.columbus.ColumbusTargetRequestService_Factory;
import com.google.android.systemui.columbus.ContentResolverWrapper;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.actions.DismissTimer;
import com.google.android.systemui.columbus.actions.DismissTimer_Factory;
import com.google.android.systemui.columbus.actions.LaunchApp;
import com.google.android.systemui.columbus.actions.LaunchApp_Factory;
import com.google.android.systemui.columbus.actions.LaunchOpa_Factory;
import com.google.android.systemui.columbus.actions.LaunchOverview;
import com.google.android.systemui.columbus.actions.LaunchOverview_Factory;
import com.google.android.systemui.columbus.actions.ManageMedia;
import com.google.android.systemui.columbus.actions.ManageMedia_Factory;
import com.google.android.systemui.columbus.actions.OpenNotificationShade;
import com.google.android.systemui.columbus.actions.OpenNotificationShade_Factory;
import com.google.android.systemui.columbus.actions.SettingsAction_Factory;
import com.google.android.systemui.columbus.actions.SilenceCall_Factory;
import com.google.android.systemui.columbus.actions.SnoozeAlarm;
import com.google.android.systemui.columbus.actions.SnoozeAlarm_Factory;
import com.google.android.systemui.columbus.actions.TakeScreenshot;
import com.google.android.systemui.columbus.actions.TakeScreenshot_Factory;
import com.google.android.systemui.columbus.actions.ToggleFlashlight;
import com.google.android.systemui.columbus.actions.ToggleFlashlight_Factory;
import com.google.android.systemui.columbus.actions.UserAction;
import com.google.android.systemui.columbus.actions.UserSelectedAction;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.feedback.HapticClick;
import com.google.android.systemui.columbus.feedback.UserActivity;
import com.google.android.systemui.columbus.gates.CameraVisibility;
import com.google.android.systemui.columbus.gates.CameraVisibility_Factory;
import com.google.android.systemui.columbus.gates.ChargingState;
import com.google.android.systemui.columbus.gates.ChargingState_Factory;
import com.google.android.systemui.columbus.gates.FlagEnabled;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.gates.KeyguardProximity;
import com.google.android.systemui.columbus.gates.KeyguardProximity_Factory;
import com.google.android.systemui.columbus.gates.KeyguardVisibility;
import com.google.android.systemui.columbus.gates.KeyguardVisibility_Factory;
import com.google.android.systemui.columbus.gates.PowerSaveState;
import com.google.android.systemui.columbus.gates.PowerSaveState_Factory;
import com.google.android.systemui.columbus.gates.PowerState;
import com.google.android.systemui.columbus.gates.Proximity;
import com.google.android.systemui.columbus.gates.ScreenTouch;
import com.google.android.systemui.columbus.gates.SetupWizard;
import com.google.android.systemui.columbus.gates.SetupWizard_Factory;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.gates.SystemKeyPress;
import com.google.android.systemui.columbus.gates.SystemKeyPress_Factory;
import com.google.android.systemui.columbus.gates.UsbState;
import com.google.android.systemui.columbus.gates.UsbState_Factory;
import com.google.android.systemui.columbus.gates.VrMode;
import com.google.android.systemui.columbus.gates.VrMode_Factory;
import com.google.android.systemui.columbus.sensors.CHREGestureSensor;
import com.google.android.systemui.columbus.sensors.CHREGestureSensor_Factory;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureController_Factory;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import com.google.android.systemui.columbus.sensors.GestureSensorImpl;
import com.google.android.systemui.columbus.sensors.GestureSensorImpl_Factory;
import com.google.android.systemui.columbus.sensors.config.Adjustment;
import com.google.android.systemui.columbus.sensors.config.GestureConfiguration;
import com.google.android.systemui.columbus.sensors.config.LowSensitivitySettingAdjustment;
import com.google.android.systemui.columbus.sensors.config.SensorConfiguration;
import com.google.android.systemui.communal.dock.DockEventSimulator;
import com.google.android.systemui.communal.dock.DockEventSimulator_DockingCondition_Factory;
import com.google.android.systemui.communal.dock.DockEventSimulator_Factory;
import com.google.android.systemui.communal.dock.DockMonitor;
import com.google.android.systemui.communal.dock.DockMonitor_Factory;
import com.google.android.systemui.communal.dock.callbacks.NudgeToSetupDreamCallback;
import com.google.android.systemui.communal.dock.callbacks.NudgeToSetupDreamCallback_Factory;
import com.google.android.systemui.communal.dock.callbacks.TimeoutToUserZeroCallback;
import com.google.android.systemui.communal.dock.callbacks.mediashell.MediaShellCallback;
import com.google.android.systemui.communal.dock.callbacks.mediashell.MediaShellCallback_Factory;
import com.google.android.systemui.communal.dock.callbacks.mediashell.MediaShellComplication;
import com.google.android.systemui.communal.dock.callbacks.mediashell.MediaShellComplication_Factory;
import com.google.android.systemui.communal.dock.callbacks.mediashell.dagger.MediaShellComponent$Factory;
import com.google.android.systemui.communal.dock.callbacks.mediashell.dagger.MediaShellModule_ProvidesMediaShellViewFactory;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroFeatureCondition;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroSettingCondition;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroSettingCondition_Factory;
import com.google.android.systemui.communal.dock.dagger.DockModule_ProvideConditionsMonitorFactory;
import com.google.android.systemui.communal.dock.dagger.DockModule_ProvideDockingCallbacksFactory;
import com.google.android.systemui.communal.dock.dagger.DockModule_ProvideTimeoutToUserZeroPreconditionsFactory;
import com.google.android.systemui.communal.dock.dagger.DockModule_ProvideTimeoutToUserZeroPreconditionsMonitorFactory;
import com.google.android.systemui.communal.dock.dagger.ServiceBinderCallbackComponent$Factory;
import com.google.android.systemui.communal.dreams.SetupDreamComplication;
import com.google.android.systemui.communal.dreams.SetupDreamComplication_Factory;
import com.google.android.systemui.communal.dreams.dagger.SetupDreamComponent$Factory;
import com.google.android.systemui.communal.dreams.dagger.SetupDreamModule_ProvidesDreamSettingFactory;
import com.google.android.systemui.communal.dreams.dagger.SetupDreamModule_ProvidesDreamSettingIntentFactory;
import com.google.android.systemui.controls.GoogleControlsTileResourceConfigurationImpl;
import com.google.android.systemui.dagger.SysUIGoogleSysUIComponent;
import com.google.android.systemui.dagger.SystemUIGoogleModule_ProvideAllowNotificationLongPressFactory;
import com.google.android.systemui.dagger.SystemUIGoogleModule_ProvideBcSmartspaceDataPluginFactory;
import com.google.android.systemui.dagger.SystemUIGoogleModule_ProvideHeadsUpManagerPhoneFactory;
import com.google.android.systemui.dagger.SystemUIGoogleModule_ProvideIThermalServiceFactory;
import com.google.android.systemui.dagger.SystemUIGoogleModule_ProvideLeakReportEmailFactory;
import com.google.android.systemui.elmyra.ServiceConfigurationGoogle;
import com.google.android.systemui.elmyra.ServiceConfigurationGoogle_Factory;
import com.google.android.systemui.elmyra.actions.CameraAction;
import com.google.android.systemui.elmyra.actions.CameraAction_Builder_Factory;
import com.google.android.systemui.elmyra.actions.LaunchOpa;
import com.google.android.systemui.elmyra.actions.LaunchOpa_Builder_Factory;
import com.google.android.systemui.elmyra.actions.SettingsAction;
import com.google.android.systemui.elmyra.actions.SettingsAction_Builder_Factory;
import com.google.android.systemui.elmyra.actions.SetupWizardAction;
import com.google.android.systemui.elmyra.actions.SetupWizardAction_Builder_Factory;
import com.google.android.systemui.elmyra.actions.SilenceCall;
import com.google.android.systemui.elmyra.actions.UnpinNotifications;
import com.google.android.systemui.elmyra.actions.UnpinNotifications_Factory;
import com.google.android.systemui.elmyra.feedback.AssistInvocationEffect;
import com.google.android.systemui.elmyra.feedback.OpaHomeButton;
import com.google.android.systemui.elmyra.feedback.OpaHomeButton_Factory;
import com.google.android.systemui.elmyra.feedback.OpaLockscreen;
import com.google.android.systemui.elmyra.feedback.OpaLockscreen_Factory;
import com.google.android.systemui.elmyra.feedback.SquishyNavigationButtons;
import com.google.android.systemui.elmyra.gates.TelephonyActivity;
import com.google.android.systemui.fingerprint.UdfpsHbmController;
import com.google.android.systemui.fingerprint.UdfpsHbmController_Factory;
import com.google.android.systemui.gamedashboard.EntryPointController;
import com.google.android.systemui.gamedashboard.EntryPointController_Factory;
import com.google.android.systemui.gamedashboard.FpsController;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger_Factory;
import com.google.android.systemui.gamedashboard.GameMenuActivity;
import com.google.android.systemui.gamedashboard.GameMenuActivity_Factory;
import com.google.android.systemui.gamedashboard.GameModeDndController;
import com.google.android.systemui.gamedashboard.ScreenRecordController;
import com.google.android.systemui.gamedashboard.ScreenRecordController_Factory;
import com.google.android.systemui.gamedashboard.ShortcutBarController;
import com.google.android.systemui.gamedashboard.ShortcutBarController_Factory;
import com.google.android.systemui.gamedashboard.ToastController;
import com.google.android.systemui.gamedashboard.ToastController_Factory;
import com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle;
import com.google.android.systemui.lowlightclock.AmbientLightModeMonitor;
import com.google.android.systemui.lowlightclock.AmbientLightModeMonitor_Factory;
import com.google.android.systemui.lowlightclock.LightSensorEventsDebounceAlgorithm;
import com.google.android.systemui.lowlightclock.LowLightClockControllerImpl;
import com.google.android.systemui.p016qs.dagger.QSModuleGoogle_ProvideAutoTileManagerFactory;
import com.google.android.systemui.p016qs.tileimpl.QSFactoryImplGoogle;
import com.google.android.systemui.p016qs.tileimpl.QSFactoryImplGoogle_Factory;
import com.google.android.systemui.p016qs.tiles.BatterySaverTileGoogle;
import com.google.android.systemui.p016qs.tiles.BatterySaverTileGoogle_Factory;
import com.google.android.systemui.p016qs.tiles.OverlayToggleTile;
import com.google.android.systemui.p016qs.tiles.OverlayToggleTile_Factory;
import com.google.android.systemui.p016qs.tiles.ReverseChargingTile;
import com.google.android.systemui.p016qs.tiles.RotationLockTileGoogle;
import com.google.android.systemui.p016qs.tiles.RotationLockTileGoogle_Factory;
import com.google.android.systemui.power.EnhancedEstimatesGoogleImpl;
import com.google.android.systemui.power.EnhancedEstimatesGoogleImpl_Factory;
import com.google.android.systemui.power.PowerModuleGoogle_ProvideWarningsUiFactory;
import com.google.android.systemui.reversecharging.ReverseChargingController;
import com.google.android.systemui.reversecharging.ReverseChargingViewController;
import com.google.android.systemui.reversecharging.ReverseChargingViewController_Factory;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;
import com.google.android.systemui.smartspace.KeyguardMediaViewController;
import com.google.android.systemui.smartspace.KeyguardSmartspaceController;
import com.google.android.systemui.smartspace.KeyguardSmartspaceController_Factory;
import com.google.android.systemui.smartspace.KeyguardZenAlarmViewController;
import com.google.android.systemui.smartspace.KeyguardZenAlarmViewController_Factory;
import com.google.android.systemui.smartspace.SmartSpaceController;
import com.google.android.systemui.smartspace.SmartSpaceController_Factory;
import com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle;
import com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle_Factory;
import com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService;
import com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService_Factory;
import com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient;
import com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient_Factory;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyClient;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController_Factory;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.phone.StatusBarGoogle;
import com.google.android.systemui.statusbar.phone.StatusBarGoogle_Factory;
import com.google.android.systemui.statusbar.phone.WallpaperNotifier;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.MapProviderFactory;
import dagger.internal.SetFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class DaggerTitanGlobalRootComponent implements GlobalRootComponent {
    public static final Provider ABSENT_JDK_OPTIONAL_PROVIDER = InstanceFactory.create(Optional.empty());
    public Provider<ATraceLoggerTransitionProgressListener> aTraceLoggerTransitionProgressListenerProvider;
    public final Context context;
    public Provider<Context> contextProvider;
    public Provider<DeviceFoldStateProvider> deviceFoldStateProvider;
    public Provider<DumpManager> dumpManagerProvider;
    public Provider<ScaleAwareTransitionProgressProvider.Factory> factoryProvider;
    public Provider<HingeAngleProvider> hingeAngleProvider;
    public Provider<LifecycleScreenStatusProvider> lifecycleScreenStatusProvider;
    public Provider<OpaEnabledSettings> opaEnabledSettingsProvider;
    public Provider<PluginDependencyProvider> pluginDependencyProvider;
    public Provider<PluginEnablerImpl> pluginEnablerImplProvider;
    public Provider<AccessibilityManager> provideAccessibilityManagerProvider;
    public Provider<ActivityManager> provideActivityManagerProvider;
    public Provider<ActivityTaskManager> provideActivityTaskManagerProvider;
    public Provider<AlarmManager> provideAlarmManagerProvider;
    public Provider<AudioManager> provideAudioManagerProvider;
    public Provider<ClipboardManager> provideClipboardManagerProvider;
    public Provider<ColorDisplayManager> provideColorDisplayManagerProvider;
    public Provider<ConnectivityManager> provideConnectivityManagagerProvider;
    public Provider<ContentResolver> provideContentResolverProvider;
    public Provider<CrossWindowBlurListeners> provideCrossWindowBlurListenersProvider;
    public Provider<DevicePolicyManager> provideDevicePolicyManagerProvider;
    public Provider<DeviceStateManager> provideDeviceStateManagerProvider;
    public Provider<Integer> provideDisplayIdProvider;
    public Provider<DisplayManager> provideDisplayManagerProvider;
    public Provider<DisplayMetrics> provideDisplayMetricsProvider;
    public Provider<Execution> provideExecutionProvider;
    public Provider<FaceManager> provideFaceManagerProvider;
    public Provider<FoldStateProvider> provideFoldStateProvider;
    public Provider<IActivityManager> provideIActivityManagerProvider;
    public Provider<IAudioService> provideIAudioServiceProvider;
    public Provider<IBatteryStats> provideIBatteryStatsProvider;
    public Provider<IDreamManager> provideIDreamManagerProvider;
    public Provider<IPackageManager> provideIPackageManagerProvider;
    public Provider<IStatusBarService> provideIStatusBarServiceProvider;
    public Provider<IWindowManager> provideIWindowManagerProvider;
    public Provider<InputMethodManager> provideInputMethodManagerProvider;
    public Provider<InteractionJankMonitor> provideInteractionJankMonitorProvider;
    public Provider<KeyguardManager> provideKeyguardManagerProvider;
    public Provider<LatencyTracker> provideLatencyTrackerProvider;
    public Provider<LauncherApps> provideLauncherAppsProvider;
    public Provider<DelayableExecutor> provideMainDelayableExecutorProvider;
    public Provider<Executor> provideMainExecutorProvider;
    public Provider<Handler> provideMainHandlerProvider;
    public Provider<MediaRouter2Manager> provideMediaRouter2ManagerProvider;
    public Provider<MediaSessionManager> provideMediaSessionManagerProvider;
    public Provider<Optional<NaturalRotationUnfoldProgressProvider>> provideNaturalRotationProgressProvider;
    public Provider<NetworkScoreManager> provideNetworkScoreManagerProvider;
    public Provider<NotificationManager> provideNotificationManagerProvider;
    public Provider<Optional<TelecomManager>> provideOptionalTelecomManagerProvider;
    public Provider<Optional<Vibrator>> provideOptionalVibratorProvider;
    public Provider<OverlayManager> provideOverlayManagerProvider;
    public Provider<PackageManager> providePackageManagerProvider;
    public Provider<PackageManagerWrapper> providePackageManagerWrapperProvider;
    public Provider<PermissionManager> providePermissionManagerProvider;
    public Provider<PluginActionManager.Factory> providePluginInstanceManagerFactoryProvider;
    public Provider<PowerManager> providePowerManagerProvider;
    public Provider<Resources> provideResourcesProvider;
    public Provider<SensorPrivacyManager> provideSensorPrivacyManagerProvider;
    public Provider<ShellUnfoldProgressProvider> provideShellProgressProvider;
    public Provider<ShortcutManager> provideShortcutManagerProvider;
    public Provider<SmartspaceManager> provideSmartspaceManagerProvider;
    public Provider<StatsManager> provideStatsManagerProvider;
    public Provider<Optional<ScopedUnfoldTransitionProgressProvider>> provideStatusBarScopedTransitionProvider;
    public Provider<SubscriptionManager> provideSubcriptionManagerProvider;
    public Provider<TelecomManager> provideTelecomManagerProvider;
    public Provider<TelephonyManager> provideTelephonyManagerProvider;
    public Provider<TrustManager> provideTrustManagerProvider;
    public Provider<UiEventLogger> provideUiEventLoggerProvider;
    public Provider<UnfoldTransitionConfig> provideUnfoldTransitionConfigProvider;
    public Provider<UserManager> provideUserManagerProvider;
    public Provider<Vibrator> provideVibratorProvider;
    public Provider<ViewConfiguration> provideViewConfigurationProvider;
    public Provider<WallpaperManager> provideWallpaperManagerProvider;
    public Provider<WifiManager> provideWifiManagerProvider;
    public Provider<WindowManager> provideWindowManagerProvider;
    public Provider<FingerprintManager> providesFingerprintManagerProvider;
    public Provider<Optional<FoldStateLogger>> providesFoldStateLoggerProvider;
    public Provider<Optional<FoldStateLoggingProvider>> providesFoldStateLoggingProvider;
    public Provider<Executor> providesPluginExecutorProvider;
    public Provider<PluginInstance.Factory> providesPluginInstanceFactoryProvider;
    public Provider<PluginManager> providesPluginManagerProvider;
    public Provider<PluginPrefs> providesPluginPrefsProvider;
    public Provider<List<String>> providesPrivilegedPluginsProvider;
    public Provider<SensorManager> providesSensorManagerProvider;
    public Provider<QSExpansionPathInterpolator> qSExpansionPathInterpolatorProvider;
    public C2509ScaleAwareTransitionProgressProvider_Factory scaleAwareTransitionProgressProvider;
    public Provider<ScreenLifecycle> screenLifecycleProvider;
    public Provider<ScreenStatusProvider> screenStatusProvider;
    public Provider<TaskbarDelegate> taskbarDelegateProvider;
    public Provider<String> tracingTagPrefixProvider;
    public Provider<Optional<UnfoldTransitionProgressProvider>> unfoldTransitionProgressProvider;

    public static final class Builder implements TitanGlobalRootComponent$Builder {
        public Context context;

        public final GlobalRootComponent build() {
            R$color.checkBuilderRequirement(this.context, Context.class);
            return new DaggerTitanGlobalRootComponent(new GlobalModule(), new UnfoldTransitionModule(), new UnfoldSharedModule(), this.context);
        }

        public final GlobalRootComponent.Builder context(Context context2) {
            Objects.requireNonNull(context2);
            this.context = context2;
            return this;
        }
    }

    public static final class PresentJdkOptionalInstanceProvider<T> implements Provider<Optional<T>> {
        public final Provider<T> delegate;

        public final Object get() {
            return Optional.of(this.delegate.get());
        }

        public PresentJdkOptionalInstanceProvider(Provider<T> provider) {
            Objects.requireNonNull(provider);
            this.delegate = provider;
        }
    }

    public final class TitanSysUIComponentBuilder implements TitanSysUIComponent$Builder {
        public Optional<Object> setAppPairs;
        public Optional<BackAnimation> setBackAnimation;
        public Optional<Bubbles> setBubbles;
        public Optional<CompatUI> setCompatUI;
        public Optional<DisplayAreaHelper> setDisplayAreaHelper;
        public Optional<DragAndDrop> setDragAndDrop;
        public Optional<HideDisplayCutout> setHideDisplayCutout;
        public Optional<LegacySplitScreen> setLegacySplitScreen;
        public Optional<OneHanded> setOneHanded;
        public Optional<Pip> setPip;
        public Optional<RecentTasks> setRecentTasks;
        public Optional<ShellCommandHandler> setShellCommandHandler;
        public Optional<SplitScreen> setSplitScreen;
        public Optional<StartingSurface> setStartingSurface;
        public Optional<TaskSurfaceHelper> setTaskSurfaceHelper;
        public Optional<TaskViewFactory> setTaskViewFactory;
        public ShellTransitions setTransitions;

        public TitanSysUIComponentBuilder() {
        }

        public final SysUIComponent build() {
            R$color.checkBuilderRequirement(this.setPip, Optional.class);
            R$color.checkBuilderRequirement(this.setLegacySplitScreen, Optional.class);
            R$color.checkBuilderRequirement(this.setSplitScreen, Optional.class);
            R$color.checkBuilderRequirement(this.setAppPairs, Optional.class);
            R$color.checkBuilderRequirement(this.setOneHanded, Optional.class);
            R$color.checkBuilderRequirement(this.setBubbles, Optional.class);
            R$color.checkBuilderRequirement(this.setTaskViewFactory, Optional.class);
            R$color.checkBuilderRequirement(this.setHideDisplayCutout, Optional.class);
            R$color.checkBuilderRequirement(this.setShellCommandHandler, Optional.class);
            R$color.checkBuilderRequirement(this.setTransitions, ShellTransitions.class);
            R$color.checkBuilderRequirement(this.setStartingSurface, Optional.class);
            R$color.checkBuilderRequirement(this.setDisplayAreaHelper, Optional.class);
            R$color.checkBuilderRequirement(this.setTaskSurfaceHelper, Optional.class);
            R$color.checkBuilderRequirement(this.setRecentTasks, Optional.class);
            R$color.checkBuilderRequirement(this.setCompatUI, Optional.class);
            R$color.checkBuilderRequirement(this.setDragAndDrop, Optional.class);
            R$color.checkBuilderRequirement(this.setBackAnimation, Optional.class);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = DaggerTitanGlobalRootComponent.this;
            DependencyProvider dependencyProvider = r2;
            DependencyProvider dependencyProvider2 = new DependencyProvider();
            NightDisplayListenerModule nightDisplayListenerModule = r2;
            NightDisplayListenerModule nightDisplayListenerModule2 = new NightDisplayListenerModule();
            SysUIUnfoldModule sysUIUnfoldModule = r2;
            SysUIUnfoldModule sysUIUnfoldModule2 = new SysUIUnfoldModule();
            return new TitanSysUIComponentImpl(daggerTitanGlobalRootComponent, dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, this.setPip, this.setLegacySplitScreen, this.setSplitScreen, this.setAppPairs, this.setOneHanded, this.setBubbles, this.setTaskViewFactory, this.setHideDisplayCutout, this.setShellCommandHandler, this.setTransitions, this.setStartingSurface, this.setDisplayAreaHelper, this.setTaskSurfaceHelper, this.setRecentTasks, this.setCompatUI, this.setDragAndDrop, this.setBackAnimation);
        }

        public final SysUIComponent.Builder setAppPairs(Optional optional) {
            Objects.requireNonNull(optional);
            this.setAppPairs = optional;
            return this;
        }

        public final SysUIComponent.Builder setBackAnimation(Optional optional) {
            Objects.requireNonNull(optional);
            this.setBackAnimation = optional;
            return this;
        }

        public final SysUIComponent.Builder setBubbles(Optional optional) {
            Objects.requireNonNull(optional);
            this.setBubbles = optional;
            return this;
        }

        public final SysUIComponent.Builder setCompatUI(Optional optional) {
            Objects.requireNonNull(optional);
            this.setCompatUI = optional;
            return this;
        }

        public final SysUIComponent.Builder setDisplayAreaHelper(Optional optional) {
            Objects.requireNonNull(optional);
            this.setDisplayAreaHelper = optional;
            return this;
        }

        public final SysUIComponent.Builder setDragAndDrop(Optional optional) {
            Objects.requireNonNull(optional);
            this.setDragAndDrop = optional;
            return this;
        }

        public final SysUIComponent.Builder setHideDisplayCutout(Optional optional) {
            Objects.requireNonNull(optional);
            this.setHideDisplayCutout = optional;
            return this;
        }

        public final SysUIComponent.Builder setLegacySplitScreen(Optional optional) {
            Objects.requireNonNull(optional);
            this.setLegacySplitScreen = optional;
            return this;
        }

        public final SysUIComponent.Builder setOneHanded(Optional optional) {
            Objects.requireNonNull(optional);
            this.setOneHanded = optional;
            return this;
        }

        public final SysUIComponent.Builder setPip(Optional optional) {
            Objects.requireNonNull(optional);
            this.setPip = optional;
            return this;
        }

        public final SysUIComponent.Builder setRecentTasks(Optional optional) {
            Objects.requireNonNull(optional);
            this.setRecentTasks = optional;
            return this;
        }

        public final SysUIComponent.Builder setShellCommandHandler(Optional optional) {
            Objects.requireNonNull(optional);
            this.setShellCommandHandler = optional;
            return this;
        }

        public final SysUIComponent.Builder setSplitScreen(Optional optional) {
            Objects.requireNonNull(optional);
            this.setSplitScreen = optional;
            return this;
        }

        public final SysUIComponent.Builder setStartingSurface(Optional optional) {
            Objects.requireNonNull(optional);
            this.setStartingSurface = optional;
            return this;
        }

        public final SysUIComponent.Builder setTaskSurfaceHelper(Optional optional) {
            Objects.requireNonNull(optional);
            this.setTaskSurfaceHelper = optional;
            return this;
        }

        public final SysUIComponent.Builder setTaskViewFactory(Optional optional) {
            Objects.requireNonNull(optional);
            this.setTaskViewFactory = optional;
            return this;
        }

        public final SysUIComponent.Builder setTransitions(ShellTransitions shellTransitions) {
            Objects.requireNonNull(shellTransitions);
            this.setTransitions = shellTransitions;
            return this;
        }
    }

    public final class TitanSysUIComponentImpl implements SysUIGoogleSysUIComponent {
        public Provider<AccessibilityButtonModeObserver> accessibilityButtonModeObserverProvider;
        public Provider<AccessibilityButtonTargetsObserver> accessibilityButtonTargetsObserverProvider;
        public Provider<AccessibilityController> accessibilityControllerProvider;
        public Provider<AccessibilityManagerWrapper> accessibilityManagerWrapperProvider;
        public Provider<ActionClickLogger> actionClickLoggerProvider;
        public Provider<ActionProxyReceiver> actionProxyReceiverProvider;
        public Provider<ActivityIntentHelper> activityIntentHelperProvider;
        public Provider<ActivityStarterDelegate> activityStarterDelegateProvider;
        public Provider<UserDetailView.Adapter> adapterProvider;
        public Provider<AirplaneModeTile> airplaneModeTileProvider;
        public Provider<AlarmTile> alarmTileProvider;
        public Provider<AmbientLightModeMonitor> ambientLightModeMonitorProvider;
        public Provider<AmbientState> ambientStateProvider;
        public Provider<AnimatedImageNotificationManager> animatedImageNotificationManagerProvider;
        public Provider<AppOpsControllerImpl> appOpsControllerImplProvider;
        public Provider<AssistInvocationEffect> assistInvocationEffectProvider;
        public Provider<com.google.android.systemui.columbus.feedback.AssistInvocationEffect> assistInvocationEffectProvider2;
        public Provider<AssistManagerGoogle> assistManagerGoogleProvider;
        public Provider<AssistantFeedbackController> assistantFeedbackControllerProvider;
        public Provider<AssistantPresenceHandler> assistantPresenceHandlerProvider;
        public Provider<AssistantWarmer> assistantWarmerProvider;
        public Provider<AsyncSensorManager> asyncSensorManagerProvider;
        public Provider<AuthController> authControllerProvider;
        public Provider<AutorotateDataService> autorotateDataServiceProvider;
        public Provider<BatterySaverTileGoogle> batterySaverTileGoogleProvider;
        public Provider<BatteryStateNotifier> batteryStateNotifierProvider;
        public Provider<Set<NgaMessageHandler.EdgeLightsInfoListener>> bindEdgeLightsInfoListenersProvider;
        public Provider<BindEventManagerImpl> bindEventManagerImplProvider;
        public Provider<NotifPanelEventSource> bindEventSourceProvider;
        public Provider<RotationPolicyWrapper> bindRotationPolicyWrapperProvider;
        public Provider<SystemClock> bindSystemClockProvider;
        public Provider<BiometricUnlockController> biometricUnlockControllerProvider;
        public Provider<BluetoothControllerImpl> bluetoothControllerImplProvider;
        public Provider<BluetoothTile> bluetoothTileProvider;
        public Provider<BlurUtils> blurUtilsProvider;
        public Provider<BootCompleteCacheImpl> bootCompleteCacheImplProvider;
        public Provider<BrightLineFalsingManager> brightLineFalsingManagerProvider;
        public Provider<BrightnessDialog> brightnessDialogProvider;
        public Provider<BroadcastDispatcherLogger> broadcastDispatcherLoggerProvider;
        public Provider<ThresholdSensorImpl.BuilderFactory> builderFactoryProvider;
        public Provider<DelayedWakeLock.Builder> builderProvider;
        public Provider<CustomTile.Builder> builderProvider10;
        public Provider<NightDisplayListenerModule.Builder> builderProvider11;
        public Provider<AutoAddTracker.Builder> builderProvider12;
        public Provider<TileServiceRequestController.Builder> builderProvider13;
        public Provider<FlingAnimationUtils.Builder> builderProvider14;
        public Provider<ThresholdSensorImpl.Builder> builderProvider2;
        public Provider<WakeLock.Builder> builderProvider3;
        public Provider<NotificationClicker.Builder> builderProvider4;
        public Provider<StatusBarNotificationActivityStarter.Builder> builderProvider5;
        public Provider<LaunchOpa.Builder> builderProvider6;
        public Provider<SettingsAction.Builder> builderProvider7;
        public Provider<CameraAction.Builder> builderProvider8;
        public Provider<SetupWizardAction.Builder> builderProvider9;
        public Provider<CHREGestureSensor> cHREGestureSensorProvider;
        public Provider<CallbackHandler> callbackHandlerProvider;
        public Provider<CameraToggleTile> cameraToggleTileProvider;
        public Provider<CameraVisibility> cameraVisibilityProvider;
        public Provider<CarrierConfigTracker> carrierConfigTrackerProvider;
        public Provider<CastControllerImpl> castControllerImplProvider;
        public Provider<CastTile> castTileProvider;
        public Provider<CellularTile> cellularTileProvider;
        public Provider<ChannelEditorDialogController> channelEditorDialogControllerProvider;
        public Provider<ChargingState> chargingStateProvider;
        public Provider<ClipboardListener> clipboardListenerProvider;
        public Provider<ClockManager> clockManagerProvider;
        public Provider<ColorChangeHandler> colorChangeHandlerProvider;
        public Provider<ColorCorrectionTile> colorCorrectionTileProvider;
        public Provider<ColorInversionTile> colorInversionTileProvider;
        public Provider<ColumbusService> columbusServiceProvider;
        public Provider<ColumbusServiceWrapper> columbusServiceWrapperProvider;
        public Provider<ColumbusSettings> columbusSettingsProvider;
        public Provider<ColumbusStructuredDataManager> columbusStructuredDataManagerProvider;
        public Provider<ColumbusTargetRequestService> columbusTargetRequestServiceProvider;
        public Provider<CommandRegistry> commandRegistryProvider;
        public Provider<CommunalSettingCondition> communalSettingConditionProvider;
        public Provider<CommunalSourceMonitor> communalSourceMonitorProvider;
        public Provider<CommunalStateController> communalStateControllerProvider;
        public Provider<CommunalViewComponent.Factory> communalViewComponentFactoryProvider;
        public Provider<ComplicationTypesUpdater> complicationTypesUpdaterProvider;
        public Provider<ConfigurationHandler> configurationHandlerProvider;
        public Provider<ContentResolverWrapper> contentResolverWrapperProvider;
        public Provider<ContextComponentResolver> contextComponentResolverProvider;
        public Provider<ControlActionCoordinatorImpl> controlActionCoordinatorImplProvider;
        public Provider<ControlsActivity> controlsActivityProvider;
        public Provider<ControlsBindingControllerImpl> controlsBindingControllerImplProvider;
        public Provider<ControlsComponent> controlsComponentProvider;
        public Provider<ControlsControllerImpl> controlsControllerImplProvider;
        public Provider<ControlsEditingActivity> controlsEditingActivityProvider;
        public Provider<ControlsFavoritingActivity> controlsFavoritingActivityProvider;
        public Provider<ControlsListingControllerImpl> controlsListingControllerImplProvider;
        public Provider<ControlsMetricsLoggerImpl> controlsMetricsLoggerImplProvider;
        public Provider<ControlsProviderSelectorActivity> controlsProviderSelectorActivityProvider;
        public Provider<ControlsRequestDialog> controlsRequestDialogProvider;
        public Provider<ControlsUiControllerImpl> controlsUiControllerImplProvider;
        public Provider<ConversationNotificationManager> conversationNotificationManagerProvider;
        public Provider<ConversationNotificationProcessor> conversationNotificationProcessorProvider;
        public Provider<CoordinatorsSubcomponent.Factory> coordinatorsSubcomponentFactoryProvider;
        public Provider<CreateUserActivity> createUserActivityProvider;
        public Provider<CustomIconCache> customIconCacheProvider;
        public Provider<CustomTileStatePersister> customTileStatePersisterProvider;
        public Provider<DarkIconDispatcherImpl> darkIconDispatcherImplProvider;
        public Provider dataLoggerProvider;
        public Provider<DataSaverTile> dataSaverTileProvider;
        public Provider<DateFormatUtil> dateFormatUtilProvider;
        public Provider<DebugModeFilterProvider> debugModeFilterProvider;
        public Provider<DebugNotificationVoiceReplyClient> debugNotificationVoiceReplyClientProvider;
        public Provider<DefaultUiController> defaultUiControllerProvider;
        public Provider<DeleteScreenshotReceiver> deleteScreenshotReceiverProvider;
        public Provider<Dependency> dependencyProvider2;
        public Provider<DeviceConfigProxy> deviceConfigProxyProvider;
        public Provider<DeviceControlsControllerImpl> deviceControlsControllerImplProvider;
        public Provider<DeviceControlsTile> deviceControlsTileProvider;
        public Provider<DevicePostureControllerImpl> devicePostureControllerImplProvider;
        public Provider<DeviceProvisionedControllerImpl> deviceProvisionedControllerImplProvider;
        public Provider<DeviceStateRotationLockSettingController> deviceStateRotationLockSettingControllerProvider;
        public Provider diagonalClassifierProvider;
        public Provider<DisableFlagsLogger> disableFlagsLoggerProvider;
        public Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
        public Provider<DismissTimer> dismissTimerProvider;
        public Provider distanceClassifierProvider;
        public Provider<DndTile> dndTileProvider;
        public Provider<DockEventSimulator> dockEventSimulatorProvider;
        public Provider<DockMonitor> dockMonitorProvider;
        public Provider<DockEventSimulator.DockingCondition> dockingConditionProvider;
        public Provider<DoubleTapClassifier> doubleTapClassifierProvider;
        public Provider<DozeComponent.Builder> dozeComponentBuilderProvider;
        public Provider<DozeLog> dozeLogProvider;
        public Provider<DozeLogger> dozeLoggerProvider;
        public Provider<DozeParameters> dozeParametersProvider;
        public Provider<DozeScrimController> dozeScrimControllerProvider;
        public Provider<DozeServiceHost> dozeServiceHostProvider;
        public Provider<DozeService> dozeServiceProvider;
        public Provider<DreamClockDateComplicationComponent$Factory> dreamClockDateComplicationComponentFactoryProvider;
        public Provider<DreamClockDateComplication> dreamClockDateComplicationProvider;
        public Provider<DreamClockTimeComplicationComponent$Factory> dreamClockTimeComplicationComponentFactoryProvider;
        public Provider<DreamClockTimeComplication> dreamClockTimeComplicationProvider;
        public Provider<DreamOverlayComponent.Factory> dreamOverlayComponentFactoryProvider;
        public Provider<DreamOverlayRegistrant> dreamOverlayRegistrantProvider;
        public Provider<DreamOverlayService> dreamOverlayServiceProvider;
        public Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
        public Provider<DreamWeatherComplicationComponent$Factory> dreamWeatherComplicationComponentFactoryProvider;
        public Provider<DreamWeatherComplication> dreamWeatherComplicationProvider;
        public Provider<DumpHandler> dumpHandlerProvider;
        public Provider<DynamicChildBindController> dynamicChildBindControllerProvider;
        public Provider<Optional<LowLightClockController>> dynamicOverrideOptionalOfLowLightClockControllerProvider;
        public Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
        public Provider<EdgeLightsController> edgeLightsControllerProvider;
        public Provider<EnhancedEstimatesGoogleImpl> enhancedEstimatesGoogleImplProvider;
        public Provider<EntryPointController> entryPointControllerProvider;
        public Provider<ExpandableNotificationRowComponent.Builder> expandableNotificationRowComponentBuilderProvider;
        public Provider<NotificationLogger.ExpansionStateLogger> expansionStateLoggerProvider;
        public Provider<ExtensionControllerImpl> extensionControllerImplProvider;
        public Provider<LightBarController.Factory> factoryProvider;
        public Provider<StatusBarIconController.TintedIconManager.Factory> factoryProvider10;
        public Provider<AutoHideController.Factory> factoryProvider2;
        public Provider<NavigationBar.Factory> factoryProvider3;
        public Provider<KeyguardBouncer.Factory> factoryProvider4;
        public Provider<KeyguardMessageAreaController.Factory> factoryProvider5;
        public Provider<BrightnessSliderController.Factory> factoryProvider6;
        public Provider<ColumbusContentObserver.Factory> factoryProvider7;
        public Provider<EdgeBackGestureHandler.Factory> factoryProvider8;
        public Provider<TileLifecycleManager.Factory> factoryProvider9;
        public Provider falsingCollectorImplProvider;
        public Provider<FalsingDataProvider> falsingDataProvider;
        public Provider<FalsingManagerProxy> falsingManagerProxyProvider;
        public Provider<FeatureFlagsRelease> featureFlagsReleaseProvider;
        public Provider<FgsManagerController> fgsManagerControllerProvider;
        public Provider<Files> filesProvider;
        public Provider<FlagEnabled> flagEnabledProvider;
        public Provider<FlashlightControllerImpl> flashlightControllerImplProvider;
        public Provider<FlashlightTile> flashlightTileProvider;
        public Provider flingVelocityWrapperProvider;
        public Provider<ForegroundServiceController> foregroundServiceControllerProvider;
        public Provider<ForegroundServiceDismissalFeatureController> foregroundServiceDismissalFeatureControllerProvider;
        public Provider<ForegroundServiceNotificationListener> foregroundServiceNotificationListenerProvider;
        public Provider<ForegroundServiceSectionController> foregroundServiceSectionControllerProvider;
        public Provider<ForegroundServicesDialog> foregroundServicesDialogProvider;
        public Provider<FpsController> fpsControllerProvider;
        public Provider<FragmentService.FragmentCreator.Factory> fragmentCreatorFactoryProvider;
        public Provider<FragmentService> fragmentServiceProvider;
        public Provider<GameDashboardUiEventLogger> gameDashboardUiEventLoggerProvider;
        public Provider<GameMenuActivity> gameMenuActivityProvider;
        public Provider<GameModeDndController> gameModeDndControllerProvider;
        public Provider<GarbageMonitor> garbageMonitorProvider;
        public Provider<GestureConfiguration> gestureConfigurationProvider;
        public Provider<GestureController> gestureControllerProvider;
        public Provider<GestureSensorImpl> gestureSensorImplProvider;
        public Provider<GlobalActionsComponent> globalActionsComponentProvider;
        public Provider<GlobalActionsDialogLite> globalActionsDialogLiteProvider;
        public Provider<GlobalActionsImpl> globalActionsImplProvider;
        public Provider globalSettingsImplProvider;
        public Provider<GlowController> glowControllerProvider;
        public Provider goBackHandlerProvider;
        public Provider<GoogleAssistLogger> googleAssistLoggerProvider;
        public Provider<GoogleControlsTileResourceConfigurationImpl> googleControlsTileResourceConfigurationImplProvider;
        public Provider<GoogleDefaultUiController> googleDefaultUiControllerProvider;
        public Provider<GoogleServices> googleServicesProvider;
        public Provider<GroupCoalescerLogger> groupCoalescerLoggerProvider;
        public Provider<GroupCoalescer> groupCoalescerProvider;
        public Provider<HapticClick> hapticClickProvider;
        public Provider<HdmiCecSetMenuLanguageActivity> hdmiCecSetMenuLanguageActivityProvider;
        public Provider<HdmiCecSetMenuLanguageHelper> hdmiCecSetMenuLanguageHelperProvider;
        public Provider<HeadsUpController> headsUpControllerProvider;
        public Provider<HeadsUpManagerLogger> headsUpManagerLoggerProvider;
        public Provider<HeadsUpViewBinderLogger> headsUpViewBinderLoggerProvider;
        public Provider<HeadsUpViewBinder> headsUpViewBinderProvider;
        public Provider<HighPriorityProvider> highPriorityProvider;
        public Provider<HistoryTracker> historyTrackerProvider;
        public Provider<HotspotControllerImpl> hotspotControllerImplProvider;
        public Provider<HotspotTile> hotspotTileProvider;
        public Provider<IconBuilder> iconBuilderProvider;
        public Provider<IconController> iconControllerProvider;
        public Provider<IconManager> iconManagerProvider;
        public Provider imageExporterProvider;
        public Provider imageTileSetProvider;
        public Provider<InitController> initControllerProvider;
        public Provider<InstantAppNotifier> instantAppNotifierProvider;
        public Provider<InternetDialogController> internetDialogControllerProvider;
        public Provider<InternetDialogFactory> internetDialogFactoryProvider;
        public Provider<InternetTile> internetTileProvider;
        public Provider<Boolean> isPMLiteEnabledProvider;
        public Provider<Boolean> isReduceBrightColorsAvailableProvider;
        public Provider keyboardMonitorProvider;
        public Provider<KeyboardUI> keyboardUIProvider;
        public Provider<KeyguardBiometricLockoutLogger> keyguardBiometricLockoutLoggerProvider;
        public Provider<KeyguardBouncerComponent.Factory> keyguardBouncerComponentFactoryProvider;
        public Provider<KeyguardBypassController> keyguardBypassControllerProvider;
        public Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
        public Provider<KeyguardDisplayManager> keyguardDisplayManagerProvider;
        public Provider<KeyguardEnvironmentImpl> keyguardEnvironmentImplProvider;
        public Provider<KeyguardIndicationControllerGoogle> keyguardIndicationControllerGoogleProvider;
        public Provider<KeyguardLifecyclesDispatcher> keyguardLifecyclesDispatcherProvider;
        public Provider<KeyguardMediaController> keyguardMediaControllerProvider;
        public Provider<KeyguardMediaViewController> keyguardMediaViewControllerProvider;
        public Provider<KeyguardProximity> keyguardProximityProvider;
        public Provider<KeyguardQsUserSwitchComponent.Factory> keyguardQsUserSwitchComponentFactoryProvider;
        public Provider<KeyguardSecurityModel> keyguardSecurityModelProvider;
        public Provider<KeyguardService> keyguardServiceProvider;
        public Provider<KeyguardSmartspaceController> keyguardSmartspaceControllerProvider;
        public Provider<KeyguardStateControllerImpl> keyguardStateControllerImplProvider;
        public Provider<KeyguardStatusBarViewComponent.Factory> keyguardStatusBarViewComponentFactoryProvider;
        public Provider<KeyguardStatusViewComponent.Factory> keyguardStatusViewComponentFactoryProvider;
        public Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
        public Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
        public Provider<KeyguardUserSwitcherComponent.Factory> keyguardUserSwitcherComponentFactoryProvider;
        public Provider<KeyguardVisibility> keyguardVisibilityProvider;
        public Provider<KeyguardZenAlarmViewController> keyguardZenAlarmViewControllerProvider;
        public Provider<LSShadeTransitionLogger> lSShadeTransitionLoggerProvider;
        public Provider<LatencyTester> latencyTesterProvider;
        public Provider<LaunchApp> launchAppProvider;
        public Provider<LaunchConversationActivity> launchConversationActivityProvider;
        public Provider<com.google.android.systemui.columbus.actions.LaunchOpa> launchOpaProvider;
        public Provider<LaunchOverview> launchOverviewProvider;
        public Provider<LeakReporter> leakReporterProvider;
        public Provider<LegacyNotificationPresenterExtensions> legacyNotificationPresenterExtensionsProvider;
        public Provider<LightBarController> lightBarControllerProvider;
        public Provider<LightSensorEventsDebounceAlgorithm> lightSensorEventsDebounceAlgorithmProvider;
        public Provider lightnessProvider;
        public Provider<LocalMediaManagerFactory> localMediaManagerFactoryProvider;
        public Provider<LocationControllerImpl> locationControllerImplProvider;
        public Provider<LocationTile> locationTileProvider;
        public Provider<LockscreenGestureLogger> lockscreenGestureLoggerProvider;
        public Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
        public Provider<LockscreenSmartspaceController> lockscreenSmartspaceControllerProvider;
        public Provider<LockscreenWallpaper> lockscreenWallpaperProvider;
        public Provider<LogBufferEulogizer> logBufferEulogizerProvider;
        public Provider<LogBufferFactory> logBufferFactoryProvider;
        public Provider<LogBufferFreezer> logBufferFreezerProvider;
        public Provider<LongScreenshotActivity> longScreenshotActivityProvider;
        public Provider<LongScreenshotData> longScreenshotDataProvider;
        public Provider<LowLightClockControllerImpl> lowLightClockControllerImplProvider;
        public Provider<LowPriorityInflationHelper> lowPriorityInflationHelperProvider;
        public Provider<LowSensitivitySettingAdjustment> lowSensitivitySettingAdjustmentProvider;
        public Provider<ManageMedia> manageMediaProvider;
        public Provider<ManagedProfileControllerImpl> managedProfileControllerImplProvider;
        public Provider<Map<Class<?>, Provider<Activity>>> mapOfClassOfAndProviderOfActivityProvider;
        public Provider<Map<Class<?>, Provider<BroadcastReceiver>>> mapOfClassOfAndProviderOfBroadcastReceiverProvider;
        public Provider<Map<Class<?>, Provider<CoreStartable>>> mapOfClassOfAndProviderOfCoreStartableProvider;
        public Provider<Map<Class<?>, Provider<RecentsImplementation>>> mapOfClassOfAndProviderOfRecentsImplementationProvider;
        public Provider<Map<Class<?>, Provider<Service>>> mapOfClassOfAndProviderOfServiceProvider;
        public Provider<MediaArtworkProcessor> mediaArtworkProcessorProvider;
        public Provider<MediaBrowserFactory> mediaBrowserFactoryProvider;
        public Provider<MediaCarouselController> mediaCarouselControllerProvider;
        public Provider<MediaComplicationComponent$Factory> mediaComplicationComponentFactoryProvider;
        public Provider<MediaContainerController> mediaContainerControllerProvider;
        public Provider<MediaControlPanel> mediaControlPanelProvider;
        public Provider<MediaControllerFactory> mediaControllerFactoryProvider;
        public Provider<MediaDataFilter> mediaDataFilterProvider;
        public Provider<MediaDataManager> mediaDataManagerProvider;
        public Provider<MediaDeviceManager> mediaDeviceManagerProvider;
        public Provider<MediaDreamComplication> mediaDreamComplicationProvider;
        public Provider<MediaDreamSentinel> mediaDreamSentinelProvider;
        public Provider<MediaFeatureFlag> mediaFeatureFlagProvider;
        public Provider<MediaFlags> mediaFlagsProvider;
        public Provider<MediaHierarchyManager> mediaHierarchyManagerProvider;
        public Provider<MediaHostStatesManager> mediaHostStatesManagerProvider;
        public Provider<MediaMuteAwaitConnectionCli> mediaMuteAwaitConnectionCliProvider;
        public Provider<MediaMuteAwaitConnectionManagerFactory> mediaMuteAwaitConnectionManagerFactoryProvider;
        public Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
        public Provider<MediaOutputDialogReceiver> mediaOutputDialogReceiverProvider;
        public Provider<MediaResumeListener> mediaResumeListenerProvider;
        public Provider<MediaSessionBasedFilter> mediaSessionBasedFilterProvider;
        public Provider<MediaShellCallback> mediaShellCallbackProvider;
        public Provider<MediaShellComplication> mediaShellComplicationProvider;
        public Provider<MediaShellComponent$Factory> mediaShellComponentFactoryProvider;
        public Provider<MediaTimeoutListener> mediaTimeoutListenerProvider;
        public Provider<MediaTttChipControllerReceiver> mediaTttChipControllerReceiverProvider;
        public Provider<MediaTttChipControllerSender> mediaTttChipControllerSenderProvider;
        public Provider<MediaTttCommandLineHelper> mediaTttCommandLineHelperProvider;
        public Provider<MediaTttFlags> mediaTttFlagsProvider;
        public Provider<MediaViewController> mediaViewControllerProvider;
        public Provider<GarbageMonitor.MemoryTile> memoryTileProvider;
        public Provider<MicrophoneToggleTile> microphoneToggleTileProvider;
        public Provider<MonitorComponent.Factory> monitorComponentFactoryProvider;
        public Provider<Set<Action>> namedSetOfActionProvider;
        public Provider<Set<Condition>> namedSetOfConditionProvider;
        public Provider<Set<FalsingClassifier>> namedSetOfFalsingClassifierProvider;
        public Provider<Set<FeedbackEffect>> namedSetOfFeedbackEffectProvider;
        public Provider<Set<FeedbackEffect>> namedSetOfFeedbackEffectProvider2;
        public Provider<Set<Gate>> namedSetOfGateProvider;
        public Provider<Set<Gate>> namedSetOfGateProvider2;
        public Provider<Set<Integer>> namedSetOfIntegerProvider;
        public Provider<NavBarFader> navBarFaderProvider;
        public Provider<NavBarHelper> navBarHelperProvider;
        public Provider<NavigationBarController> navigationBarControllerProvider;
        public Provider<NavigationModeController> navigationModeControllerProvider;
        public Provider<NearbyMediaDevicesManager> nearbyMediaDevicesManagerProvider;
        public Provider<NetworkControllerImpl> networkControllerImplProvider;
        public Provider<KeyguardViewMediator> newKeyguardViewMediatorProvider;
        public Provider<NextAlarmControllerImpl> nextAlarmControllerImplProvider;
        public Provider<NfcTile> nfcTileProvider;
        public Provider<NgaInputHandler> ngaInputHandlerProvider;
        public Provider<NgaMessageHandler> ngaMessageHandlerProvider;
        public Provider<NgaUiController> ngaUiControllerProvider;
        public Provider<NightDisplayTile> nightDisplayTileProvider;
        public Provider<NotifBindPipelineInitializer> notifBindPipelineInitializerProvider;
        public Provider<NotifBindPipelineLogger> notifBindPipelineLoggerProvider;
        public Provider<NotifBindPipeline> notifBindPipelineProvider;
        public Provider<NotifCollectionLogger> notifCollectionLoggerProvider;
        public Provider<NotifCollection> notifCollectionProvider;
        public Provider<NotifCoordinators> notifCoordinatorsProvider;
        public Provider<NotifInflaterImpl> notifInflaterImplProvider;
        public Provider<NotifInflationErrorManager> notifInflationErrorManagerProvider;
        public Provider<NotifLiveDataStoreImpl> notifLiveDataStoreImplProvider;
        public Provider notifPipelineChoreographerImplProvider;
        public Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
        public Provider<NotifPipelineInitializer> notifPipelineInitializerProvider;
        public Provider<NotifPipeline> notifPipelineProvider;
        public Provider<NotifRemoteViewCacheImpl> notifRemoteViewCacheImplProvider;
        public Provider<NotifUiAdjustmentProvider> notifUiAdjustmentProvider;
        public Provider<NotifViewBarn> notifViewBarnProvider;
        public Provider<NotificationChannels> notificationChannelsProvider;
        public Provider<NotificationClickNotifier> notificationClickNotifierProvider;
        public Provider<NotificationClickerLogger> notificationClickerLoggerProvider;
        public Provider<NotificationContentInflater> notificationContentInflaterProvider;
        public Provider<NotificationEntryManagerLogger> notificationEntryManagerLoggerProvider;
        public Provider<NotificationFilter> notificationFilterProvider;
        public Provider<NotificationGroupAlertTransferHelper> notificationGroupAlertTransferHelperProvider;
        public Provider<NotificationGroupManagerLegacy> notificationGroupManagerLegacyProvider;
        public Provider<NotificationIconAreaController> notificationIconAreaControllerProvider;
        public Provider<NotificationInteractionTracker> notificationInteractionTrackerProvider;
        public Provider<NotificationInterruptLogger> notificationInterruptLoggerProvider;
        public Provider<NotificationInterruptStateProviderImpl> notificationInterruptStateProviderImplProvider;
        public Provider<NotificationListener> notificationListenerProvider;
        public Provider<NotificationListenerWithPlugins> notificationListenerWithPluginsProvider;
        public Provider<NotificationLockscreenUserManagerGoogle> notificationLockscreenUserManagerGoogleProvider;
        public Provider<NotificationLockscreenUserManagerImpl> notificationLockscreenUserManagerImplProvider;
        public Provider<NotificationPersonExtractorPluginBoundary> notificationPersonExtractorPluginBoundaryProvider;
        public Provider<NotificationRankingManager> notificationRankingManagerProvider;
        public Provider<NotificationRoundnessManager> notificationRoundnessManagerProvider;
        public Provider<NotificationRowBinderImpl> notificationRowBinderImplProvider;
        public Provider<NotificationSectionsFeatureManager> notificationSectionsFeatureManagerProvider;
        public Provider<NotificationSectionsLogger> notificationSectionsLoggerProvider;
        public Provider<NotificationSectionsManager> notificationSectionsManagerProvider;
        public Provider<NotificationShadeDepthController> notificationShadeDepthControllerProvider;
        public Provider<NotificationShadeWindowControllerImpl> notificationShadeWindowControllerImplProvider;
        public Provider<NotificationShelfComponent.Builder> notificationShelfComponentBuilderProvider;
        public Provider<NotificationVisibilityProviderImpl> notificationVisibilityProviderImplProvider;
        public Provider<NotificationVoiceReplyController> notificationVoiceReplyControllerProvider;
        public Provider<NotificationVoiceReplyLogger> notificationVoiceReplyLoggerProvider;
        public Provider<NotificationVoiceReplyManagerService> notificationVoiceReplyManagerServiceProvider;
        public Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
        public Provider<NotificationsControllerImpl> notificationsControllerImplProvider;
        public Provider<NotificationsControllerStub> notificationsControllerStubProvider;
        public Provider<NudgeToSetupDreamCallback> nudgeToSetupDreamCallbackProvider;
        public Provider<OneHandedModeTile> oneHandedModeTileProvider;
        public Provider<OngoingCallFlags> ongoingCallFlagsProvider;
        public Provider<OngoingCallLogger> ongoingCallLoggerProvider;
        public Provider<OpaEnabledDispatcher> opaEnabledDispatcherProvider;
        public Provider<OpaEnabledReceiver> opaEnabledReceiverProvider;
        public Provider<OpaHomeButton> opaHomeButtonProvider;
        public Provider<OpaLockscreen> opaLockscreenProvider;
        public Provider<OpenNotificationShade> openNotificationShadeProvider;
        public Provider<Optional<BcSmartspaceDataPlugin>> optionalOfBcSmartspaceDataPluginProvider;
        public Provider<Optional<CommandQueue>> optionalOfCommandQueueProvider;
        public Provider<Optional<ControlsFavoritePersistenceWrapper>> optionalOfControlsFavoritePersistenceWrapperProvider;
        public Provider<Optional<ControlsTileResourceConfiguration>> optionalOfControlsTileResourceConfigurationProvider;
        public Provider<Optional<HeadsUpManager>> optionalOfHeadsUpManagerProvider;
        public Provider<Optional<Recents>> optionalOfRecentsProvider;
        public Provider<Optional<StatusBar>> optionalOfStatusBarProvider;
        public Provider<Optional<UdfpsHbmProvider>> optionalOfUdfpsHbmProvider;
        public Provider overlappedElementControllerProvider;
        public Provider<OverlayToggleTile> overlayToggleTileProvider;
        public Provider overlayUiHostProvider;
        public Provider<OverviewProxyRecentsImpl> overviewProxyRecentsImplProvider;
        public Provider<OverviewProxyService> overviewProxyServiceProvider;
        public Provider<PackageManagerAdapter> packageManagerAdapterProvider;
        public Provider<PanelExpansionStateManager> panelExpansionStateManagerProvider;
        public Provider<PeopleNotificationIdentifierImpl> peopleNotificationIdentifierImplProvider;
        public Provider<PeopleSpaceActivity> peopleSpaceActivityProvider;
        public Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;
        public Provider<PeopleSpaceWidgetPinnedReceiver> peopleSpaceWidgetPinnedReceiverProvider;
        public Provider<PeopleSpaceWidgetProvider> peopleSpaceWidgetProvider;
        public Provider<PhoneStateMonitor> phoneStateMonitorProvider;
        public Provider<PhoneStatusBarPolicy> phoneStatusBarPolicyProvider;
        public Provider pointerCountClassifierProvider;
        public Provider postureDependentProximitySensorProvider;
        public Provider<PowerManagerWrapper> powerManagerWrapperProvider;
        public Provider<PowerSaveState> powerSaveStateProvider;
        public Provider<PowerState> powerStateProvider;
        public Provider<PowerUI> powerUIProvider;
        public Provider<PrivacyDialogController> privacyDialogControllerProvider;
        public Provider<PrivacyDotDecorProviderFactory> privacyDotDecorProviderFactoryProvider;
        public Provider<PrivacyDotViewController> privacyDotViewControllerProvider;
        public Provider<PrivacyItemController> privacyItemControllerProvider;
        public Provider<PrivacyLogger> privacyLoggerProvider;
        public Provider<ProtoTracer> protoTracerProvider;
        public Provider<AccessPointControllerImpl> provideAccessPointControllerImplProvider;
        public Provider<AccessibilityFloatingMenuController> provideAccessibilityFloatingMenuControllerProvider;
        public Provider<ActivityLaunchAnimator> provideActivityLaunchAnimatorProvider;
        public Provider<ActivityManagerWrapper> provideActivityManagerWrapperProvider;
        public Provider<ActivityStarter> provideActivityStarterProvider;
        public Provider<NgaMessageHandler.StartActivityInfoListener> provideActivityStarterProvider2;
        public Provider<Boolean> provideAllowNotificationLongPressProvider;
        public Provider<AlwaysOnDisplayPolicy> provideAlwaysOnDisplayPolicyProvider;
        public Provider<AmbientDisplayConfiguration> provideAmbientDisplayConfigurationProvider;
        public Provider<AssistUtils> provideAssistUtilsProvider;
        public Provider<Set<NgaMessageHandler.AudioInfoListener>> provideAudioInfoListenersProvider;
        public Provider<AutoHideController> provideAutoHideControllerProvider;
        public Provider<DeviceStateRotationLockSettingsManager> provideAutoRotateSettingsManagerProvider;
        public Provider<AutoTileManager> provideAutoTileManagerProvider;
        public Provider<DelayableExecutor> provideBackgroundDelayableExecutorProvider;
        public Provider<Executor> provideBackgroundExecutorProvider;
        public Provider<RepeatableExecutor> provideBackgroundRepeatableExecutorProvider;
        public Provider<BatteryController> provideBatteryControllerProvider;
        public Provider<BcSmartspaceDataPlugin> provideBcSmartspaceDataPluginProvider;
        public Provider<Handler> provideBgHandlerProvider;
        public Provider<Looper> provideBgLooperProvider;
        public Provider<LogBuffer> provideBroadcastDispatcherLogBufferProvider;
        public Provider<Optional<BubblesManager>> provideBubblesManagerProvider;
        public Provider<Set<NgaMessageHandler.CardInfoListener>> provideCardInfoListenersProvider;
        public Provider<ClipboardOverlayControllerFactory> provideClipboardOverlayControllerFactoryProvider;
        public Provider provideClockInfoListProvider;
        public Provider<LogBuffer> provideCollapsedSbFragmentLogBufferProvider;
        public Provider<List<Action>> provideColumbusActionsProvider;
        public Provider<Set<FeedbackEffect>> provideColumbusEffectsProvider;
        public Provider<Set<Gate>> provideColumbusGatesProvider;
        public Provider<Set<Gate>> provideColumbusSoftGatesProvider;
        public Provider<CommandQueue> provideCommandQueueProvider;
        public Provider<CommonNotifCollection> provideCommonNotifCollectionProvider;
        public Provider<Set<Condition>> provideCommunalConditionsProvider;
        public Provider<Monitor> provideCommunalSourceMonitorProvider;
        public Provider<Monitor> provideConditionsMonitorProvider;
        public Provider<Set<NgaMessageHandler.ConfigInfoListener>> provideConfigInfoListenersProvider;
        public Provider<ConfigurationController> provideConfigurationControllerProvider;
        public Provider<DataSaverController> provideDataSaverControllerProvider;
        public Provider<DelayableExecutor> provideDelayableExecutorProvider;
        public Provider<DemoModeController> provideDemoModeControllerProvider;
        public Provider<DevicePolicyManagerWrapper> provideDevicePolicyManagerWrapperProvider;
        public Provider<DeviceProvisionedController> provideDeviceProvisionedControllerProvider;
        public Provider<DialogLaunchAnimator> provideDialogLaunchAnimatorProvider;
        public Provider<DockManager> provideDockManagerProvider;
        public Provider<Set<Monitor.Callback>> provideDockingCallbacksProvider;
        public Provider<Set<Condition>> provideDockingConditionsProvider;
        public Provider<LogBuffer> provideDozeLogBufferProvider;
        public Provider<Executor> provideExecutorProvider;
        public Provider<List<Action>> provideFullscreenActionsProvider;
        public Provider<List<Adjustment>> provideGestureAdjustmentsProvider;
        public Provider<GestureSensor> provideGestureSensorProvider;
        public Provider<GroupExpansionManager> provideGroupExpansionManagerProvider;
        public Provider<GroupMembershipManager> provideGroupMembershipManagerProvider;
        public Provider<Handler> provideHandlerProvider;
        public Provider<HeadsUpManagerPhone> provideHeadsUpManagerPhoneProvider;
        public Provider<INotificationManager> provideINotificationManagerProvider;
        public Provider<IThermalService> provideIThermalServiceProvider;
        public Provider<IndividualSensorPrivacyController> provideIndividualSensorPrivacyControllerProvider;
        public Provider<LogBuffer> provideLSShadeTransitionControllerBufferProvider;
        public Provider<LeakDetector> provideLeakDetectorProvider;
        public Provider<String> provideLeakReportEmailProvider;
        public Provider<LocalBluetoothManager> provideLocalBluetoothControllerProvider;
        public Provider<LockPatternUtils> provideLockPatternUtilsProvider;
        public Provider<LogcatEchoTracker> provideLogcatEchoTrackerProvider;
        public Provider<Executor> provideLongRunningExecutorProvider;
        public Provider<Looper> provideLongRunningLooperProvider;
        public Provider<Optional<LowLightClockController>> provideLowLightClockControllerProvider;
        public Provider<MetricsLogger> provideMetricsLoggerProvider;
        public Provider<NightDisplayListener> provideNightDisplayListenerProvider;
        public Provider<NotifGutsViewManager> provideNotifGutsViewManagerProvider;
        public Provider<LogBuffer> provideNotifInteractionLogBufferProvider;
        public Provider<NotifRemoteViewCache> provideNotifRemoteViewCacheProvider;
        public Provider<NotifShadeEventSource> provideNotifShadeEventSourceProvider;
        public Provider<LogBuffer> provideNotifVoiceReplyLogBufferProvider;
        public Provider<NotificationEntryManager> provideNotificationEntryManagerProvider;
        public Provider<NotificationGutsManager> provideNotificationGutsManagerProvider;
        public Provider<LogBuffer> provideNotificationHeadsUpLogBufferProvider;
        public Provider<NotificationLogger> provideNotificationLoggerProvider;
        public Provider<NotificationMediaManager> provideNotificationMediaManagerProvider;
        public Provider<NotificationMessagingUtil> provideNotificationMessagingUtilProvider;
        public Provider<NotificationPanelLogger> provideNotificationPanelLoggerProvider;
        public Provider<NotificationRemoteInputManager> provideNotificationRemoteInputManagerProvider;
        public Provider<LogBuffer> provideNotificationSectionLogBufferProvider;
        public Provider<NotificationViewHierarchyManager> provideNotificationViewHierarchyManagerProvider;
        public Provider<NotificationVisibilityProvider> provideNotificationVisibilityProvider;
        public Provider<Optional<NotificationVoiceReplyClient>> provideNotificationVoiceReplyClientProvider;
        public Provider<NotificationsController> provideNotificationsControllerProvider;
        public Provider<LogBuffer> provideNotificationsLogBufferProvider;
        public Provider<OnUserInteractionCallback> provideOnUserInteractionCallbackProvider;
        public Provider<OngoingCallController> provideOngoingCallControllerProvider;
        public Provider<ViewGroup> provideParentViewGroupProvider;
        public Provider<ThresholdSensor[]> providePostureToProximitySensorMappingProvider;
        public Provider<ThresholdSensor[]> providePostureToSecondaryProximitySensorMappingProvider;
        public Provider<ThresholdSensor> providePrimaryProximitySensorProvider;
        public Provider<LogBuffer> providePrivacyLogBufferProvider;
        public Provider<ProximityCheck> provideProximityCheckProvider;
        public Provider<ProximitySensor> provideProximitySensorProvider;
        public Provider<LogBuffer> provideQSFragmentDisableLogBufferProvider;
        public Provider<QuickAccessWalletClient> provideQuickAccessWalletClientProvider;
        public Provider<LogBuffer> provideQuickSettingsLogBufferProvider;
        public Provider<RecentsImplementation> provideRecentsImplProvider;
        public Provider<Recents> provideRecentsProvider;
        public Provider<ReduceBrightColorsController> provideReduceBrightColorsListenerProvider;
        public Provider<Optional<ReverseChargingViewController>> provideReverseChargingViewControllerOptionalProvider;
        public Provider<Optional<ReverseWirelessCharger>> provideReverseWirelessChargerProvider;
        public Provider<ThresholdSensor> provideSecondaryProximitySensorProvider;
        public Provider<SensorPrivacyController> provideSensorPrivacyControllerProvider;
        public Provider<SharedPreferences> provideSharePreferencesProvider;
        public Provider<SmartReplyController> provideSmartReplyControllerProvider;
        public Provider<LogBuffer> provideSwipeAwayGestureLogBufferProvider;
        public Provider<Optional<SysUIUnfoldComponent>> provideSysUIUnfoldComponentProvider;
        public Provider<SysUiState> provideSysUiStateProvider;
        public Provider<ThemeOverlayApplier> provideThemeOverlayManagerProvider;
        public Provider<Handler> provideTimeTickHandlerProvider;
        public Provider<Boolean> provideTimeoutToUserZeroFeatureEnabledProvider;
        public Provider<Monitor> provideTimeoutToUserZeroPreconditionsMonitorProvider;
        public Provider<Set<Condition>> provideTimeoutToUserZeroPreconditionsProvider;
        public Provider<Integer> provideTimeoutToUserZeroUserSettingDurationProvider;
        public Provider<LogBuffer> provideToastLogBufferProvider;
        public Provider<Set<TouchActionRegion>> provideTouchActionRegionsProvider;
        public Provider<Set<TouchInsideRegion>> provideTouchInsideRegionsProvider;
        public Provider<Executor> provideUiBackgroundExecutorProvider;
        public Provider<Optional<UsbManager>> provideUsbManagerProvider;
        public Provider<Integer> provideUserIdProvider;
        public Provider<Map<String, UserAction>> provideUserSelectedActionsProvider;
        public Provider<UserTracker> provideUserTrackerProvider;
        public Provider<VisualStabilityManager> provideVisualStabilityManagerProvider;
        public Provider<VolumeDialog> provideVolumeDialogProvider;
        public Provider<PowerUI.WarningsUI> provideWarningsUiProvider;
        public Provider<LayoutInflater> providerLayoutInflaterProvider;
        public Provider<SectionHeaderController> providesAlertingHeaderControllerProvider;
        public Provider<NodeController> providesAlertingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesAlertingHeaderSubcomponentProvider;
        public Provider<MessageRouter> providesBackgroundMessageRouterProvider;
        public Provider<Set<FalsingClassifier>> providesBrightLineGestureClassifiersProvider;
        public Provider<BroadcastDispatcher> providesBroadcastDispatcherProvider;
        public Provider<Choreographer> providesChoreographerProvider;
        public Provider<Boolean> providesControlsFeatureEnabledProvider;
        public Provider<String> providesDeviceNameProvider;
        public Provider<String[]> providesDeviceStateRotationLockDefaultsProvider;
        public Provider<Float> providesDoubleTapTouchSlopProvider;
        public Provider<DreamBackend> providesDreamBackendProvider;
        public Provider<MediaHost> providesDreamMediaHostProvider;
        public Provider<Boolean> providesDreamSelectedProvider;
        public Provider<ContentResolver> providesDreamSettingContentObserverProvider;
        public Provider<PendingIntent> providesDreamSettingPendingIntentProvider;
        public Provider<Uri> providesDreamsSettingUriProvider;
        public Provider<SectionHeaderController> providesIncomingHeaderControllerProvider;
        public Provider<NodeController> providesIncomingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesIncomingHeaderSubcomponentProvider;
        public Provider<MediaHost> providesKeyguardMediaHostProvider;
        public Provider<MessageRouter> providesMainMessageRouterProvider;
        public Provider<Optional<MediaMuteAwaitConnectionCli>> providesMediaMuteAwaitConnectionCliProvider;
        public Provider<View> providesMediaShellViewProvider;
        public Provider<Optional<MediaTttChipControllerReceiver>> providesMediaTttChipControllerReceiverProvider;
        public Provider<Optional<MediaTttChipControllerSender>> providesMediaTttChipControllerSenderProvider;
        public Provider<Optional<MediaTttCommandLineHelper>> providesMediaTttCommandLineHelperProvider;
        public Provider<ModeSwitchesController> providesModeSwitchesControllerProvider;
        public Provider<Optional<NearbyMediaDevicesManager>> providesNearbyMediaDevicesManagerProvider;
        public Provider<NotificationManager> providesNotificationManagerProvider;
        public Provider<SectionHeaderController> providesPeopleHeaderControllerProvider;
        public Provider<NodeController> providesPeopleHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesPeopleHeaderSubcomponentProvider;
        public Provider<Executor> providesPluginExecutorProvider;
        public Provider<MediaHost> providesQSMediaHostProvider;
        public Provider<MediaHost> providesQuickQSMediaHostProvider;
        public Provider<Notification> providesSetupDreamNotificationProvider;
        public Provider<SectionHeaderController> providesSilentHeaderControllerProvider;
        public Provider<NodeController> providesSilentHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesSilentHeaderSubcomponentProvider;
        public Provider<Float> providesSingleTapTouchSlopProvider;
        public Provider<StatusBarWindowView> providesStatusBarWindowViewProvider;
        public Provider<ViewMediatorCallback> providesViewMediatorCallbackProvider;
        public Provider proximityClassifierProvider;
        public Provider<Proximity> proximityProvider;
        public Provider proximitySensorImplProvider;
        public Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
        public Provider<QRCodeScannerController> qRCodeScannerControllerProvider;
        public Provider<QRCodeScannerTile> qRCodeScannerTileProvider;
        public Provider<QSFactoryImplGoogle> qSFactoryImplGoogleProvider;
        public Provider<QSLogger> qSLoggerProvider;
        public Provider<QSTileHost> qSTileHostProvider;
        public Provider<QsFrameTranslateImpl> qsFrameTranslateImplProvider;
        public Provider<QuickAccessWalletController> quickAccessWalletControllerProvider;
        public Provider<QuickAccessWalletTile> quickAccessWalletTileProvider;
        public Provider<RecordingController> recordingControllerProvider;
        public Provider<RecordingService> recordingServiceProvider;
        public Provider<ReduceBrightColorsTile> reduceBrightColorsTileProvider;
        public Provider<DreamClockTimeComplication.Registrant> registrantProvider;
        public Provider<DreamClockDateComplication.Registrant> registrantProvider2;
        public Provider<DreamWeatherComplication.Registrant> registrantProvider3;
        public Provider<SmartSpaceComplication.Registrant> registrantProvider4;
        public Provider<RemoteInputNotificationRebuilder> remoteInputNotificationRebuilderProvider;
        public Provider<RemoteInputQuickSettingsDisabler> remoteInputQuickSettingsDisablerProvider;
        public Provider<RemoteInputUriController> remoteInputUriControllerProvider;
        public Provider<RenderStageManager> renderStageManagerProvider;
        public Provider<ResumeMediaBrowserFactory> resumeMediaBrowserFactoryProvider;
        public Provider<ReverseChargingController> reverseChargingControllerProvider;
        public Provider<ReverseChargingTile> reverseChargingTileProvider;
        public Provider<ReverseChargingViewController> reverseChargingViewControllerProvider;
        public Provider<RingerModeTrackerImpl> ringerModeTrackerImplProvider;
        public Provider<RingtonePlayer> ringtonePlayerProvider;
        public Provider<RotationLockControllerImpl> rotationLockControllerImplProvider;
        public Provider<RotationLockTileGoogle> rotationLockTileGoogleProvider;
        public Provider<RotationPolicyWrapperImpl> rotationPolicyWrapperImplProvider;
        public Provider<RowContentBindStageLogger> rowContentBindStageLoggerProvider;
        public Provider<RowContentBindStage> rowContentBindStageProvider;
        public Provider<ScreenDecorations> screenDecorationsProvider;
        public Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
        public Provider<ScreenOnCoordinator> screenOnCoordinatorProvider;
        public Provider<ScreenPinningRequest> screenPinningRequestProvider;
        public Provider<ScreenRecordController> screenRecordControllerProvider;
        public Provider<ScreenRecordTile> screenRecordTileProvider;
        public Provider<ScreenTouch> screenTouchProvider;
        public Provider<ScreenshotController> screenshotControllerProvider;
        public Provider<ScreenshotNotificationsController> screenshotNotificationsControllerProvider;
        public Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;
        public Provider<ScrimController> scrimControllerProvider;
        public Provider<com.google.android.systemui.assist.uihints.ScrimController> scrimControllerProvider2;
        public Provider<ScrollCaptureClient> scrollCaptureClientProvider;
        public Provider<ScrollCaptureController> scrollCaptureControllerProvider;
        public Provider<SectionClassifier> sectionClassifierProvider;
        public Provider<SectionHeaderControllerSubcomponent.Builder> sectionHeaderControllerSubcomponentBuilderProvider;
        public Provider<SectionHeaderVisibilityProvider> sectionHeaderVisibilityProvider;
        public Provider secureSettingsImplProvider;
        public Provider<SecurityControllerImpl> securityControllerImplProvider;
        public Provider<SeekBarViewModel> seekBarViewModelProvider;
        public Provider<SensorConfiguration> sensorConfigurationProvider;
        public Provider<SensorUseStartedActivity> sensorUseStartedActivityProvider;
        public Provider<ServiceBinderCallbackComponent$Factory> serviceBinderCallbackComponentFactoryProvider;
        public Provider<ServiceConfigurationGoogle> serviceConfigurationGoogleProvider;
        public Provider<GarbageMonitor.Service> serviceProvider;
        public Provider<SessionTracker> sessionTrackerProvider;
        public Provider<Optional<BackAnimation>> setBackAnimationProvider;
        public Provider<Optional<Bubbles>> setBubblesProvider;
        public Provider<Optional<CompatUI>> setCompatUIProvider;
        public Provider<Optional<DisplayAreaHelper>> setDisplayAreaHelperProvider;
        public Provider<Optional<DragAndDrop>> setDragAndDropProvider;
        public Provider<Optional<HideDisplayCutout>> setHideDisplayCutoutProvider;
        public Provider<Optional<LegacySplitScreen>> setLegacySplitScreenProvider;
        public Provider<Set<NgaMessageHandler.AudioInfoListener>> setOfAudioInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.CardInfoListener>> setOfCardInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.ChipsInfoListener>> setOfChipsInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.ClearListener>> setOfClearListenerProvider;
        public Provider<Set<NgaMessageHandler.ConfigInfoListener>> setOfConfigInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.EdgeLightsInfoListener>> setOfEdgeLightsInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.GoBackListener>> setOfGoBackListenerProvider;
        public Provider<Set<NgaMessageHandler.GreetingInfoListener>> setOfGreetingInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.KeepAliveListener>> setOfKeepAliveListenerProvider;
        public Provider<Set<NgaMessageHandler.KeyboardInfoListener>> setOfKeyboardInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.NavBarVisibilityListener>> setOfNavBarVisibilityListenerProvider;
        public Provider<Set<NgaMessageHandler.StartActivityInfoListener>> setOfStartActivityInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.SwipeListener>> setOfSwipeListenerProvider;
        public Provider<Set<NgaMessageHandler.TakeScreenshotListener>> setOfTakeScreenshotListenerProvider;
        public Provider<Set<TouchActionRegion>> setOfTouchActionRegionProvider;
        public Provider<Set<TouchInsideRegion>> setOfTouchInsideRegionProvider;
        public Provider<Set<NgaMessageHandler.TranscriptionInfoListener>> setOfTranscriptionInfoListenerProvider;
        public Provider<Set<NgaMessageHandler.WarmingListener>> setOfWarmingListenerProvider;
        public Provider<Set<NgaMessageHandler.ZerostateInfoListener>> setOfZerostateInfoListenerProvider;
        public Provider<Optional<OneHanded>> setOneHandedProvider;
        public Provider<Optional<Pip>> setPipProvider;
        public Provider<Optional<RecentTasks>> setRecentTasksProvider;
        public Provider<Optional<ShellCommandHandler>> setShellCommandHandlerProvider;
        public Provider<Optional<SplitScreen>> setSplitScreenProvider;
        public Provider<Optional<StartingSurface>> setStartingSurfaceProvider;
        public Provider<Optional<TaskSurfaceHelper>> setTaskSurfaceHelperProvider;
        public Provider<Optional<TaskViewFactory>> setTaskViewFactoryProvider;
        public Provider<ShellTransitions> setTransitionsProvider;
        public Provider<com.google.android.systemui.columbus.actions.SettingsAction> settingsActionProvider;
        public Provider<SetupDreamComplication> setupDreamComplicationProvider;
        public Provider<SetupDreamComponent$Factory> setupDreamComponentFactoryProvider;
        public Provider<SetupWizard> setupWizardProvider;
        public Provider<ShadeControllerImpl> shadeControllerImplProvider;
        public Provider<ShadeEventCoordinatorLogger> shadeEventCoordinatorLoggerProvider;
        public Provider<ShadeEventCoordinator> shadeEventCoordinatorProvider;
        public Provider<ShadeListBuilderLogger> shadeListBuilderLoggerProvider;
        public Provider<ShadeListBuilder> shadeListBuilderProvider;
        public Provider<ShadeViewDifferLogger> shadeViewDifferLoggerProvider;
        public Provider<ShadeViewManagerFactory> shadeViewManagerFactoryProvider;
        public ShadeViewManager_Factory shadeViewManagerProvider;
        public Provider<ShortcutBarController> shortcutBarControllerProvider;
        public Provider<ShortcutKeyDispatcher> shortcutKeyDispatcherProvider;
        public Provider<SidefpsController> sidefpsControllerProvider;
        public Provider<SilenceAlertsDisabled> silenceAlertsDisabledProvider;
        public Provider<SilenceCall> silenceCallProvider;
        public Provider<com.google.android.systemui.columbus.actions.SilenceCall> silenceCallProvider2;
        public Provider<SingleTapClassifier> singleTapClassifierProvider;
        public Provider<SliceBroadcastRelayHandler> sliceBroadcastRelayHandlerProvider;
        public Provider<SmartActionInflaterImpl> smartActionInflaterImplProvider;
        public Provider<SmartActionsReceiver> smartActionsReceiverProvider;
        public Provider<SmartReplyConstants> smartReplyConstantsProvider;
        public Provider<SmartReplyInflaterImpl> smartReplyInflaterImplProvider;
        public Provider<SmartReplyStateInflaterImpl> smartReplyStateInflaterImplProvider;
        public Provider<SmartSpaceComplication> smartSpaceComplicationProvider;
        public Provider<SmartSpaceController> smartSpaceControllerProvider;
        public Provider<SnoozeAlarm> snoozeAlarmProvider;
        public Provider<SquishyNavigationButtons> squishyNavigationButtonsProvider;
        public Provider<StatusBarComponent.Factory> statusBarComponentFactoryProvider;
        public Provider<StatusBarContentInsetsProvider> statusBarContentInsetsProvider;
        public Provider<StatusBarGoogle> statusBarGoogleProvider;
        public Provider<StatusBarHideIconsForBouncerManager> statusBarHideIconsForBouncerManagerProvider;
        public Provider<StatusBarIconControllerImpl> statusBarIconControllerImplProvider;
        public Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
        public Provider<StatusBarLocationPublisher> statusBarLocationPublisherProvider;
        public Provider<StatusBarNotificationActivityStarterLogger> statusBarNotificationActivityStarterLoggerProvider;
        public Provider<StatusBarRemoteInputCallback> statusBarRemoteInputCallbackProvider;
        public Provider<StatusBarSignalPolicy> statusBarSignalPolicyProvider;
        public Provider<StatusBarStateControllerImpl> statusBarStateControllerImplProvider;
        public Provider<StatusBarTouchableRegionManager> statusBarTouchableRegionManagerProvider;
        public Provider<StatusBarUserInfoTracker> statusBarUserInfoTrackerProvider;
        public Provider<StatusBarUserSwitcherFeatureController> statusBarUserSwitcherFeatureControllerProvider;
        public Provider<StatusBarWindowController> statusBarWindowControllerProvider;
        public Provider<StatusBarWindowStateController> statusBarWindowStateControllerProvider;
        public Provider<StorageNotification> storageNotificationProvider;
        public Provider<QSCarrierGroupController.SubscriptionManagerSlotIndexResolver> subscriptionManagerSlotIndexResolverProvider;
        public Provider swipeHandlerProvider;
        public Provider<SwipeStatusBarAwayGestureHandler> swipeStatusBarAwayGestureHandlerProvider;
        public Provider<SwipeStatusBarAwayGestureLogger> swipeStatusBarAwayGestureLoggerProvider;
        public Provider<SysUIUnfoldComponent.Factory> sysUIUnfoldComponentFactoryProvider;
        public Provider<SystemActions> systemActionsProvider;
        public Provider<SystemEventChipAnimationController> systemEventChipAnimationControllerProvider;
        public Provider<SystemEventCoordinator> systemEventCoordinatorProvider;
        public Provider<SystemKeyPress> systemKeyPressProvider;
        public Provider<SystemStatusAnimationScheduler> systemStatusAnimationSchedulerProvider;
        public Provider<SystemUIAuxiliaryDumpService> systemUIAuxiliaryDumpServiceProvider;
        public Provider<SystemUIDialogManager> systemUIDialogManagerProvider;
        public Provider<SystemUIService> systemUIServiceProvider;
        public Provider<SysuiColorExtractor> sysuiColorExtractorProvider;
        public Provider takeScreenshotHandlerProvider;
        public Provider<TakeScreenshot> takeScreenshotProvider;
        public Provider<TakeScreenshotService> takeScreenshotServiceProvider;
        public Provider<TargetSdkResolver> targetSdkResolverProvider;
        public Provider taskStackNotifierProvider;
        public Provider<TelephonyActivity> telephonyActivityProvider;
        public Provider<com.google.android.systemui.columbus.gates.TelephonyActivity> telephonyActivityProvider2;
        public Provider<TelephonyListenerManager> telephonyListenerManagerProvider;
        public Provider<ThemeOverlayController> themeOverlayControllerProvider;
        public final /* synthetic */ DaggerTitanGlobalRootComponent this$0;
        public C2507TileLifecycleManager_Factory tileLifecycleManagerProvider;
        public Provider<TileServices> tileServicesProvider;
        public Provider<TimeoutHandler> timeoutHandlerProvider;
        public Provider timeoutManagerProvider;
        public Provider<TimeoutToUserZeroCallback> timeoutToUserZeroCallbackProvider;
        public Provider<TimeoutToUserZeroFeatureCondition> timeoutToUserZeroFeatureConditionProvider;
        public Provider<TimeoutToUserZeroSettingCondition> timeoutToUserZeroSettingConditionProvider;
        public Provider<ToastController> toastControllerProvider;
        public Provider<ToastFactory> toastFactoryProvider;
        public Provider<ToastLogger> toastLoggerProvider;
        public Provider<ToastUI> toastUIProvider;
        public Provider<ToggleFlashlight> toggleFlashlightProvider;
        public Provider<TouchInsideHandler> touchInsideHandlerProvider;
        public Provider touchOutsideHandlerProvider;
        public Provider<TranscriptionController> transcriptionControllerProvider;
        public Provider<TunablePadding.TunablePaddingService> tunablePaddingServiceProvider;
        public Provider<TunerActivity> tunerActivityProvider;
        public Provider<TunerServiceImpl> tunerServiceImplProvider;
        public Provider<TvNotificationHandler> tvNotificationHandlerProvider;
        public Provider<TvNotificationPanelActivity> tvNotificationPanelActivityProvider;
        public Provider<TvUnblockSensorActivity> tvUnblockSensorActivityProvider;
        public Provider<TypeClassifier> typeClassifierProvider;
        public Provider<UdfpsController> udfpsControllerProvider;
        public Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
        public Provider<UdfpsHbmController> udfpsHbmControllerProvider;
        public Provider<UiModeNightTile> uiModeNightTileProvider;
        public Provider<UiOffloadThread> uiOffloadThreadProvider;
        public Provider<UnfoldLatencyTracker> unfoldLatencyTrackerProvider;
        public Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
        public Provider<UnpinNotifications> unpinNotificationsProvider;
        public Provider<com.google.android.systemui.columbus.actions.UnpinNotifications> unpinNotificationsProvider2;
        public Provider<UsbDebuggingActivity> usbDebuggingActivityProvider;
        public Provider<UsbDebuggingSecondaryUserActivity> usbDebuggingSecondaryUserActivityProvider;
        public Provider<UsbState> usbStateProvider;
        public Provider<UserActivity> userActivityProvider;
        public Provider<UserCreator> userCreatorProvider;
        public Provider<UserInfoControllerImpl> userInfoControllerImplProvider;
        public Provider<UserSelectedAction> userSelectedActionProvider;
        public Provider<UserSwitchDialogController> userSwitchDialogControllerProvider;
        public Provider<UserSwitcherActivity> userSwitcherActivityProvider;
        public Provider<UserSwitcherController> userSwitcherControllerProvider;
        public Provider<VibratorHelper> vibratorHelperProvider;
        public Provider<VisualStabilityCoordinator> visualStabilityCoordinatorProvider;
        public Provider<VisualStabilityProvider> visualStabilityProvider;
        public Provider<VolumeDialogComponent> volumeDialogComponentProvider;
        public Provider<VolumeDialogControllerImpl> volumeDialogControllerImplProvider;
        public Provider<VolumeUI> volumeUIProvider;
        public Provider<VrMode> vrModeProvider;
        public Provider<WMShell> wMShellProvider;
        public Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
        public Provider<WalletActivity> walletActivityProvider;
        public Provider<WalletControllerImpl> walletControllerImplProvider;
        public Provider<WallpaperController> wallpaperControllerProvider;
        public Provider<WallpaperNotifier> wallpaperNotifierProvider;
        public Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> wifiPickerTrackerFactoryProvider;
        public Provider<WifiTile> wifiTileProvider;
        public Provider<WindowMagnification> windowMagnificationProvider;
        public Provider<WiredChargingRippleController> wiredChargingRippleControllerProvider;
        public Provider<WorkLockActivity> workLockActivityProvider;
        public Provider<WorkModeTile> workModeTileProvider;
        public Provider<ZenModeControllerImpl> zenModeControllerImplProvider;
        public Provider zigZagClassifierProvider;

        public final class CommunalViewComponentFactory implements CommunalViewComponent.Factory {
            public CommunalViewComponentFactory() {
            }

            public final CommunalViewComponent build(CommunalHostView communalHostView) {
                Objects.requireNonNull(communalHostView);
                return new CommunalViewComponentImpl(communalHostView);
            }
        }

        public final class CommunalViewComponentImpl implements CommunalViewComponent {
            public final CommunalHostView arg0;

            public CommunalViewComponentImpl(CommunalHostView communalHostView) {
                this.arg0 = communalHostView;
            }

            public final CommunalHostViewController getCommunalHostViewController() {
                DozeParameters dozeParameters = TitanSysUIComponentImpl.this.dozeParametersProvider.get();
                return CommunalHostViewController_Factory.newInstance(TitanSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TitanSysUIComponentImpl.this.communalStateControllerProvider.get(), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TitanSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.screenOffAnimationControllerProvider.get(), TitanSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), this.arg0);
            }
        }

        public final class CoordinatorsSubcomponentFactory implements CoordinatorsSubcomponent.Factory {
            public CoordinatorsSubcomponentFactory() {
            }

            public final CoordinatorsSubcomponent create() {
                return new CoordinatorsSubcomponentImpl(TitanSysUIComponentImpl.this);
            }
        }

        public final class CoordinatorsSubcomponentImpl implements CoordinatorsSubcomponent {
            public Provider<AppOpsCoordinator> appOpsCoordinatorProvider;
            public Provider<BubbleCoordinator> bubbleCoordinatorProvider;
            public Provider<CommunalCoordinator> communalCoordinatorProvider;
            public Provider<ConversationCoordinator> conversationCoordinatorProvider;
            public Provider<DataStoreCoordinator> dataStoreCoordinatorProvider;
            public Provider<DebugModeCoordinator> debugModeCoordinatorProvider;
            public Provider<DeviceProvisionedCoordinator> deviceProvisionedCoordinatorProvider;
            public Provider<GroupCountCoordinator> groupCountCoordinatorProvider;
            public RingtonePlayer_Factory gutsCoordinatorLoggerProvider;
            public Provider<GutsCoordinator> gutsCoordinatorProvider;
            public MediaBrowserFactory_Factory headsUpCoordinatorLoggerProvider;
            public Provider<HeadsUpCoordinator> headsUpCoordinatorProvider;
            public Provider<HideLocallyDismissedNotifsCoordinator> hideLocallyDismissedNotifsCoordinatorProvider = DoubleCheck.provider(HideLocallyDismissedNotifsCoordinator_Factory.InstanceHolder.INSTANCE);
            public Provider<HideNotifsForOtherUsersCoordinator> hideNotifsForOtherUsersCoordinatorProvider;
            public Provider<KeyguardCoordinator> keyguardCoordinatorProvider;
            public Provider<MediaCoordinator> mediaCoordinatorProvider;
            public Provider<NotifCoordinatorsImpl> notifCoordinatorsImplProvider;
            public TypeClassifier_Factory preparationCoordinatorLoggerProvider;
            public Provider<PreparationCoordinator> preparationCoordinatorProvider;
            public Provider<RankingCoordinator> rankingCoordinatorProvider;
            public Provider<RemoteInputCoordinator> remoteInputCoordinatorProvider;
            public Provider<RowAppearanceCoordinator> rowAppearanceCoordinatorProvider;
            public Provider sensitiveContentCoordinatorImplProvider;
            public WMShellBaseModule_ProvideHideDisplayCutoutFactory sharedCoordinatorLoggerProvider;
            public Provider<SmartspaceDedupingCoordinator> smartspaceDedupingCoordinatorProvider;
            public Provider<StackCoordinator> stackCoordinatorProvider;
            public Provider<ViewConfigCoordinator> viewConfigCoordinatorProvider;

            public CoordinatorsSubcomponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                this.dataStoreCoordinatorProvider = DoubleCheck.provider(new KeyboardUI_Factory(titanSysUIComponentImpl2.notifLiveDataStoreImplProvider, 7));
                WMShellBaseModule_ProvideHideDisplayCutoutFactory wMShellBaseModule_ProvideHideDisplayCutoutFactory = new WMShellBaseModule_ProvideHideDisplayCutoutFactory(titanSysUIComponentImpl2.provideNotificationsLogBufferProvider, 3);
                this.sharedCoordinatorLoggerProvider = wMShellBaseModule_ProvideHideDisplayCutoutFactory;
                this.hideNotifsForOtherUsersCoordinatorProvider = DoubleCheck.provider(new ScreenPinningRequest_Factory(titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, wMShellBaseModule_ProvideHideDisplayCutoutFactory, 3));
                this.keyguardCoordinatorProvider = DoubleCheck.provider(ControlsControllerImpl_Factory.create$1(titanSysUIComponentImpl2.this$0.contextProvider, titanSysUIComponentImpl2.provideHandlerProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.providesBroadcastDispatcherProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.highPriorityProvider, titanSysUIComponentImpl2.sectionHeaderVisibilityProvider));
                this.rankingCoordinatorProvider = DoubleCheck.provider(RankingCoordinator_Factory.create(titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.highPriorityProvider, titanSysUIComponentImpl2.sectionClassifierProvider, titanSysUIComponentImpl2.providesAlertingHeaderNodeControllerProvider, titanSysUIComponentImpl2.providesSilentHeaderControllerProvider, titanSysUIComponentImpl2.providesSilentHeaderNodeControllerProvider));
                this.appOpsCoordinatorProvider = DoubleCheck.provider(new MediaViewController_Factory(titanSysUIComponentImpl2.foregroundServiceControllerProvider, titanSysUIComponentImpl2.appOpsControllerImplProvider, titanSysUIComponentImpl2.this$0.provideMainDelayableExecutorProvider, 1));
                this.deviceProvisionedCoordinatorProvider = DoubleCheck.provider(new DeviceProvisionedCoordinator_Factory(titanSysUIComponentImpl2.provideDeviceProvisionedControllerProvider, titanSysUIComponentImpl2.this$0.provideIPackageManagerProvider));
                this.bubbleCoordinatorProvider = DoubleCheck.provider(new RowContentBindStage_Factory(titanSysUIComponentImpl2.provideBubblesManagerProvider, titanSysUIComponentImpl2.setBubblesProvider, titanSysUIComponentImpl2.notifCollectionProvider, 1));
                MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(titanSysUIComponentImpl2.provideNotificationHeadsUpLogBufferProvider, 4);
                this.headsUpCoordinatorLoggerProvider = mediaBrowserFactory_Factory;
                this.headsUpCoordinatorProvider = DoubleCheck.provider(HeadsUpCoordinator_Factory.create(mediaBrowserFactory_Factory, titanSysUIComponentImpl2.bindSystemClockProvider, titanSysUIComponentImpl2.provideHeadsUpManagerPhoneProvider, titanSysUIComponentImpl2.headsUpViewBinderProvider, titanSysUIComponentImpl2.notificationInterruptStateProviderImplProvider, titanSysUIComponentImpl2.provideNotificationRemoteInputManagerProvider, titanSysUIComponentImpl2.providesIncomingHeaderNodeControllerProvider, titanSysUIComponentImpl2.this$0.provideMainDelayableExecutorProvider));
                RingtonePlayer_Factory ringtonePlayer_Factory = new RingtonePlayer_Factory(titanSysUIComponentImpl2.provideNotificationsLogBufferProvider, 3);
                this.gutsCoordinatorLoggerProvider = ringtonePlayer_Factory;
                this.gutsCoordinatorProvider = DoubleCheck.provider(new OpaHomeButton_Factory(titanSysUIComponentImpl2.provideNotifGutsViewManagerProvider, ringtonePlayer_Factory, titanSysUIComponentImpl2.this$0.dumpManagerProvider, 1));
                this.communalCoordinatorProvider = DoubleCheck.provider(new CommunalCoordinator_Factory(titanSysUIComponentImpl2.this$0.provideMainExecutorProvider, titanSysUIComponentImpl2.provideNotificationEntryManagerProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.communalStateControllerProvider));
                this.conversationCoordinatorProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(titanSysUIComponentImpl2.peopleNotificationIdentifierImplProvider, titanSysUIComponentImpl2.providesPeopleHeaderNodeControllerProvider, 1));
                this.debugModeCoordinatorProvider = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(titanSysUIComponentImpl2.debugModeFilterProvider, 4));
                this.groupCountCoordinatorProvider = DoubleCheck.provider(GroupCountCoordinator_Factory.InstanceHolder.INSTANCE);
                this.mediaCoordinatorProvider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(titanSysUIComponentImpl2.mediaFeatureFlagProvider, 2));
                TypeClassifier_Factory typeClassifier_Factory = new TypeClassifier_Factory(titanSysUIComponentImpl2.provideNotificationsLogBufferProvider, 4);
                this.preparationCoordinatorLoggerProvider = typeClassifier_Factory;
                this.preparationCoordinatorProvider = DoubleCheck.provider(PreparationCoordinator_Factory.create(typeClassifier_Factory, titanSysUIComponentImpl2.notifInflaterImplProvider, titanSysUIComponentImpl2.notifInflationErrorManagerProvider, titanSysUIComponentImpl2.notifViewBarnProvider, titanSysUIComponentImpl2.notifUiAdjustmentProvider, titanSysUIComponentImpl2.this$0.provideIStatusBarServiceProvider, titanSysUIComponentImpl2.bindEventManagerImplProvider));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                this.remoteInputCoordinatorProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDragAndDropControllerFactory.create(daggerTitanGlobalRootComponent.dumpManagerProvider, titanSysUIComponentImpl2.remoteInputNotificationRebuilderProvider, titanSysUIComponentImpl2.provideNotificationRemoteInputManagerProvider, daggerTitanGlobalRootComponent.provideMainHandlerProvider, titanSysUIComponentImpl2.provideSmartReplyControllerProvider));
                this.rowAppearanceCoordinatorProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(titanSysUIComponentImpl2.this$0.contextProvider, titanSysUIComponentImpl2.assistantFeedbackControllerProvider, titanSysUIComponentImpl2.sectionClassifierProvider, 2));
                this.stackCoordinatorProvider = DoubleCheck.provider(new DozeLogger_Factory(titanSysUIComponentImpl2.notificationIconAreaControllerProvider, 3));
                this.smartspaceDedupingCoordinatorProvider = DoubleCheck.provider(SmartspaceDedupingCoordinator_Factory.create(titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.lockscreenSmartspaceControllerProvider, titanSysUIComponentImpl2.provideNotificationEntryManagerProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.notifPipelineProvider, titanSysUIComponentImpl2.this$0.provideMainDelayableExecutorProvider, titanSysUIComponentImpl2.bindSystemClockProvider));
                this.viewConfigCoordinatorProvider = DoubleCheck.provider(new ViewConfigCoordinator_Factory(titanSysUIComponentImpl2.provideConfigurationControllerProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerImplProvider, titanSysUIComponentImpl2.provideNotificationGutsManagerProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider));
                Provider provider = DoubleCheck.provider(MediaDataFilter_Factory.create$1(titanSysUIComponentImpl2.dynamicPrivacyControllerProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider));
                this.sensitiveContentCoordinatorImplProvider = provider;
                this.notifCoordinatorsImplProvider = DoubleCheck.provider(NotifCoordinatorsImpl_Factory.create(titanSysUIComponentImpl2.this$0.dumpManagerProvider, titanSysUIComponentImpl2.notifPipelineFlagsProvider, this.dataStoreCoordinatorProvider, this.hideLocallyDismissedNotifsCoordinatorProvider, this.hideNotifsForOtherUsersCoordinatorProvider, this.keyguardCoordinatorProvider, this.rankingCoordinatorProvider, this.appOpsCoordinatorProvider, this.deviceProvisionedCoordinatorProvider, this.bubbleCoordinatorProvider, this.headsUpCoordinatorProvider, this.gutsCoordinatorProvider, this.communalCoordinatorProvider, this.conversationCoordinatorProvider, this.debugModeCoordinatorProvider, this.groupCountCoordinatorProvider, this.mediaCoordinatorProvider, this.preparationCoordinatorProvider, this.remoteInputCoordinatorProvider, this.rowAppearanceCoordinatorProvider, this.stackCoordinatorProvider, titanSysUIComponentImpl2.shadeEventCoordinatorProvider, this.smartspaceDedupingCoordinatorProvider, this.viewConfigCoordinatorProvider, titanSysUIComponentImpl2.visualStabilityCoordinatorProvider, provider));
            }

            public final NotifCoordinators getNotifCoordinators() {
                return this.notifCoordinatorsImplProvider.get();
            }
        }

        public final class DozeComponentFactory implements DozeComponent.Builder {
            public DozeComponentFactory() {
            }

            public final DozeComponent build(DozeMachine.Service service) {
                Objects.requireNonNull(service);
                return new DozeComponentImpl(TitanSysUIComponentImpl.this, service);
            }
        }

        public final class DozeComponentImpl implements DozeComponent {
            public InstanceFactory arg0Provider;
            public Provider<DozeAuthRemover> dozeAuthRemoverProvider;
            public Provider<DozeDockHandler> dozeDockHandlerProvider;
            public Provider<DozeFalsingManagerAdapter> dozeFalsingManagerAdapterProvider;
            public Provider<DozeMachine> dozeMachineProvider;
            public Provider<DozePauser> dozePauserProvider;
            public Provider<DozeScreenBrightness> dozeScreenBrightnessProvider;
            public Provider<DozeScreenState> dozeScreenStateProvider;
            public Provider<DozeTriggers> dozeTriggersProvider;
            public Provider<DozeUi> dozeUiProvider;
            public Provider<DozeWallpaperState> dozeWallpaperStateProvider;
            public LatencyTester_Factory providesBrightnessSensorsProvider;
            public DozeModule_ProvidesDozeMachinePartesFactory providesDozeMachinePartesProvider;
            public Provider<WakeLock> providesDozeWakeLockProvider;
            public Provider<DozeMachine.Service> providesWrappedServiceProvider;

            public DozeComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, DozeMachine.Service service) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                InstanceFactory create = InstanceFactory.create(service);
                this.arg0Provider = create;
                this.providesWrappedServiceProvider = DoubleCheck.provider(new DozeModule_ProvidesWrappedServiceFactory(create, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.dozeParametersProvider));
                this.providesDozeWakeLockProvider = DoubleCheck.provider(new DozeModule_ProvidesDozeWakeLockFactory(titanSysUIComponentImpl2.builderProvider, titanSysUIComponentImpl2.this$0.provideMainHandlerProvider));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                this.dozePauserProvider = DoubleCheck.provider(new DozePauser_Factory(daggerTitanGlobalRootComponent.provideMainHandlerProvider, daggerTitanGlobalRootComponent.provideAlarmManagerProvider, titanSysUIComponentImpl2.provideAlwaysOnDisplayPolicyProvider));
                this.dozeFalsingManagerAdapterProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(titanSysUIComponentImpl2.falsingCollectorImplProvider, 3));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = titanSysUIComponentImpl2.this$0;
                this.dozeTriggersProvider = DoubleCheck.provider(DozeTriggers_Factory.create(daggerTitanGlobalRootComponent2.contextProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.provideAmbientDisplayConfigurationProvider, titanSysUIComponentImpl2.dozeParametersProvider, titanSysUIComponentImpl2.asyncSensorManagerProvider, this.providesDozeWakeLockProvider, titanSysUIComponentImpl2.provideDockManagerProvider, titanSysUIComponentImpl2.provideProximitySensorProvider, titanSysUIComponentImpl2.provideProximityCheckProvider, titanSysUIComponentImpl2.dozeLogProvider, titanSysUIComponentImpl2.providesBroadcastDispatcherProvider, titanSysUIComponentImpl2.secureSettingsImplProvider, titanSysUIComponentImpl2.authControllerProvider, daggerTitanGlobalRootComponent2.provideMainDelayableExecutorProvider, daggerTitanGlobalRootComponent2.provideUiEventLoggerProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.devicePostureControllerImplProvider, titanSysUIComponentImpl2.provideBatteryControllerProvider));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = titanSysUIComponentImpl2.this$0;
                this.dozeUiProvider = DoubleCheck.provider(DozeUi_Factory.create(daggerTitanGlobalRootComponent3.contextProvider, daggerTitanGlobalRootComponent3.provideAlarmManagerProvider, this.providesDozeWakeLockProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, daggerTitanGlobalRootComponent3.provideMainHandlerProvider, titanSysUIComponentImpl2.dozeParametersProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.dozeLogProvider));
                Provider<AsyncSensorManager> provider = titanSysUIComponentImpl2.asyncSensorManagerProvider;
                Provider<Context> provider2 = titanSysUIComponentImpl2.this$0.contextProvider;
                Provider<DozeParameters> provider3 = titanSysUIComponentImpl2.dozeParametersProvider;
                LatencyTester_Factory latencyTester_Factory = new LatencyTester_Factory(provider, provider2, provider3, 1);
                this.providesBrightnessSensorsProvider = latencyTester_Factory;
                Provider<DozeScreenBrightness> provider4 = DoubleCheck.provider(DozeScreenBrightness_Factory.create(provider2, this.providesWrappedServiceProvider, provider, latencyTester_Factory, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.provideHandlerProvider, titanSysUIComponentImpl2.provideAlwaysOnDisplayPolicyProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, provider3, titanSysUIComponentImpl2.devicePostureControllerImplProvider, titanSysUIComponentImpl2.dozeLogProvider));
                this.dozeScreenBrightnessProvider = provider4;
                this.dozeScreenStateProvider = DoubleCheck.provider(DozeScreenState_Factory.create(this.providesWrappedServiceProvider, titanSysUIComponentImpl2.this$0.provideMainHandlerProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.dozeParametersProvider, this.providesDozeWakeLockProvider, titanSysUIComponentImpl2.authControllerProvider, titanSysUIComponentImpl2.udfpsControllerProvider, titanSysUIComponentImpl2.dozeLogProvider, provider4));
                this.dozeWallpaperStateProvider = DoubleCheck.provider(new DozeWallpaperState_Factory(FrameworkServicesModule_ProvideIWallPaperManagerFactory.InstanceHolder.INSTANCE, titanSysUIComponentImpl2.biometricUnlockControllerProvider, titanSysUIComponentImpl2.dozeParametersProvider, 0));
                this.dozeDockHandlerProvider = DoubleCheck.provider(new ProtoTracer_Factory(titanSysUIComponentImpl2.provideAmbientDisplayConfigurationProvider, titanSysUIComponentImpl2.provideDockManagerProvider, 1));
                Provider<DozeAuthRemover> provider5 = DoubleCheck.provider(new DozeAuthRemover_Factory(titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, 0));
                this.dozeAuthRemoverProvider = provider5;
                DozeModule_ProvidesDozeMachinePartesFactory create2 = DozeModule_ProvidesDozeMachinePartesFactory.create(this.dozePauserProvider, this.dozeFalsingManagerAdapterProvider, this.dozeTriggersProvider, this.dozeUiProvider, this.dozeScreenStateProvider, this.dozeScreenBrightnessProvider, this.dozeWallpaperStateProvider, this.dozeDockHandlerProvider, provider5);
                this.providesDozeMachinePartesProvider = create2;
                this.dozeMachineProvider = DoubleCheck.provider(DozeMachine_Factory.create(this.providesWrappedServiceProvider, titanSysUIComponentImpl2.provideAmbientDisplayConfigurationProvider, this.providesDozeWakeLockProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, titanSysUIComponentImpl2.provideBatteryControllerProvider, titanSysUIComponentImpl2.dozeLogProvider, titanSysUIComponentImpl2.provideDockManagerProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, create2));
            }

            public final DozeMachine getDozeMachine() {
                return this.dozeMachineProvider.get();
            }
        }

        public final class DreamClockDateComplicationComponentFactory implements DreamClockDateComplicationComponent$Factory {
            public DreamClockDateComplicationComponentFactory() {
            }

            public final DreamClockDateComplicationComponentImpl create() {
                return new DreamClockDateComplicationComponentImpl(TitanSysUIComponentImpl.this);
            }
        }

        public final class DreamClockTimeComplicationComponentFactory implements DreamClockTimeComplicationComponent$Factory {
            public DreamClockTimeComplicationComponentFactory() {
            }

            public final DreamClockTimeComplicationComponentImpl create() {
                return new DreamClockTimeComplicationComponentImpl(TitanSysUIComponentImpl.this);
            }
        }

        public final class DreamOverlayComponentFactory implements DreamOverlayComponent.Factory {
            public DreamOverlayComponentFactory() {
            }

            public final DreamOverlayComponent create(ViewModelStore viewModelStore, Complication.Host host) {
                Objects.requireNonNull(viewModelStore);
                return new DreamOverlayComponentImpl(viewModelStore, (DreamOverlayService.C07851) host);
            }
        }

        public final class DreamOverlayComponentImpl implements DreamOverlayComponent {
            public final ViewModelStore arg0;
            public final Complication.Host arg1;
            public C24601 complicationHostViewComponentFactoryProvider = new Provider<ComplicationHostViewComponent.Factory>() {
                public final Object get() {
                    return new ComplicationHostViewComponentFactory();
                }
            };
            public Provider<DreamOverlayContainerViewController> dreamOverlayContainerViewControllerProvider;
            public Provider<DreamOverlayStatusBarViewController> dreamOverlayStatusBarViewControllerProvider;
            public Provider<BatteryMeterViewController> providesBatteryMeterViewControllerProvider;
            public Provider<BatteryMeterView> providesBatteryMeterViewProvider;
            public MediaBrowserFactory_Factory providesBurnInProtectionUpdateIntervalProvider;
            public Provider<DreamOverlayContainerView> providesDreamOverlayContainerViewProvider;
            public Provider<ViewGroup> providesDreamOverlayContentViewProvider;
            public Provider<DreamOverlayStatusBarView> providesDreamOverlayStatusBarViewProvider;
            public Provider<LifecycleOwner> providesLifecycleOwnerProvider;
            public Provider<Lifecycle> providesLifecycleProvider;
            public DelegateFactory providesLifecycleRegistryProvider;
            public Provider<Integer> providesMaxBurnInOffsetProvider;

            public final class ComplicationHostViewComponentFactory implements ComplicationHostViewComponent.Factory {
                public ComplicationHostViewComponentFactory() {
                }

                public final ComplicationHostViewComponent create() {
                    return new ComplicationHostViewComponentI();
                }
            }

            public final class ComplicationHostViewComponentI implements ComplicationHostViewComponent {
                public Provider<ConstraintLayout> providesComplicationHostViewProvider;
                public Provider<Integer> providesComplicationPaddingProvider;

                public ComplicationHostViewComponentI() {
                    this.providesComplicationHostViewProvider = DoubleCheck.provider(new C0792x8a1d9ad2(TitanSysUIComponentImpl.this.providerLayoutInflaterProvider));
                    this.providesComplicationPaddingProvider = DoubleCheck.provider(new C0793x7dca8e24(TitanSysUIComponentImpl.this.this$0.provideResourcesProvider));
                }

                public final ComplicationHostViewController getController() {
                    DreamOverlayComponentImpl dreamOverlayComponentImpl = DreamOverlayComponentImpl.this;
                    Objects.requireNonNull(dreamOverlayComponentImpl);
                    return new ComplicationHostViewController(this.providesComplicationHostViewProvider.get(), new ComplicationLayoutEngine(this.providesComplicationHostViewProvider.get(), this.providesComplicationPaddingProvider.get().intValue()), DreamOverlayComponentImpl.this.providesLifecycleOwnerProvider.get(), C0794x1653c7a9.providesComplicationCollectionViewModel(dreamOverlayComponentImpl.arg0, new ComplicationCollectionViewModel(new ComplicationCollectionLiveData(TitanSysUIComponentImpl.this.dreamOverlayStateControllerProvider.get()), new ComplicationViewModelTransformer(new ComplicationViewModelComponentFactory()))));
                }
            }

            public final class ComplicationViewModelComponentFactory implements ComplicationViewModelComponent.Factory {
                public ComplicationViewModelComponentFactory() {
                }

                public final ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId) {
                    Objects.requireNonNull(complication);
                    Objects.requireNonNull(complicationId);
                    return new ComplicationViewModelComponentI(complication, complicationId);
                }
            }

            public final class ComplicationViewModelComponentI implements ComplicationViewModelComponent {
                public final Complication arg0;
                public final ComplicationId arg1;

                public ComplicationViewModelComponentI(Complication complication, ComplicationId complicationId) {
                    this.arg0 = complication;
                    this.arg1 = complicationId;
                }

                public final ComplicationViewModelProvider getViewModelProvider() {
                    DreamOverlayComponentImpl dreamOverlayComponentImpl = DreamOverlayComponentImpl.this;
                    return new ComplicationViewModelProvider(dreamOverlayComponentImpl.arg0, new ComplicationViewModel(this.arg0, this.arg1, dreamOverlayComponentImpl.arg1));
                }
            }

            public DreamOverlayComponentImpl(ViewModelStore viewModelStore, DreamOverlayService.C07851 r12) {
                this.arg0 = viewModelStore;
                this.arg1 = r12;
                Provider<DreamOverlayContainerView> provider = DoubleCheck.provider(new QSFragmentModule_ProvidesQuickQSPanelFactory(TitanSysUIComponentImpl.this.providerLayoutInflaterProvider, 3));
                this.providesDreamOverlayContainerViewProvider = provider;
                this.providesDreamOverlayContentViewProvider = DoubleCheck.provider(new DateFormatUtil_Factory(provider, 1));
                this.providesDreamOverlayStatusBarViewProvider = DoubleCheck.provider(new ScreenLifecycle_Factory(this.providesDreamOverlayContainerViewProvider, 1));
                Provider<BatteryMeterView> provider2 = DoubleCheck.provider(new PrivacyLogger_Factory(this.providesDreamOverlayContainerViewProvider, 1));
                this.providesBatteryMeterViewProvider = provider2;
                Provider<ConfigurationController> provider3 = TitanSysUIComponentImpl.this.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider4 = TitanSysUIComponentImpl.this.tunerServiceImplProvider;
                Provider<BroadcastDispatcher> provider5 = TitanSysUIComponentImpl.this.providesBroadcastDispatcherProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = TitanSysUIComponentImpl.this.this$0;
                Provider<BatteryMeterViewController> provider6 = DoubleCheck.provider(AppOpsControllerImpl_Factory.create$1(provider2, provider3, provider4, provider5, daggerTitanGlobalRootComponent.provideMainHandlerProvider, daggerTitanGlobalRootComponent.provideContentResolverProvider, TitanSysUIComponentImpl.this.provideBatteryControllerProvider));
                this.providesBatteryMeterViewControllerProvider = provider6;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = TitanSysUIComponentImpl.this.this$0;
                this.dreamOverlayStatusBarViewControllerProvider = DoubleCheck.provider(new DreamOverlayStatusBarViewController_Factory(daggerTitanGlobalRootComponent2.contextProvider, this.providesDreamOverlayStatusBarViewProvider, TitanSysUIComponentImpl.this.provideBatteryControllerProvider, provider6, daggerTitanGlobalRootComponent2.provideConnectivityManagagerProvider));
                Provider<Integer> provider7 = DoubleCheck.provider(new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(TitanSysUIComponentImpl.this.this$0.provideResourcesProvider, 0));
                this.providesMaxBurnInOffsetProvider = provider7;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = TitanSysUIComponentImpl.this.this$0;
                MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(daggerTitanGlobalRootComponent3.provideResourcesProvider, 2);
                this.providesBurnInProtectionUpdateIntervalProvider = mediaBrowserFactory_Factory;
                this.dreamOverlayContainerViewControllerProvider = DoubleCheck.provider(DreamOverlayContainerViewController_Factory.create(this.providesDreamOverlayContainerViewProvider, this.complicationHostViewComponentFactoryProvider, this.providesDreamOverlayContentViewProvider, this.dreamOverlayStatusBarViewControllerProvider, daggerTitanGlobalRootComponent3.provideMainHandlerProvider, provider7, mediaBrowserFactory_Factory));
                DelegateFactory delegateFactory = new DelegateFactory();
                this.providesLifecycleRegistryProvider = delegateFactory;
                Provider<LifecycleOwner> provider8 = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(delegateFactory, 1));
                this.providesLifecycleOwnerProvider = provider8;
                DelegateFactory.setDelegate(this.providesLifecycleRegistryProvider, DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(provider8, 1)));
                this.providesLifecycleProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.providesLifecycleOwnerProvider, 2));
            }

            public final DreamOverlayContainerViewController getDreamOverlayContainerViewController() {
                return this.dreamOverlayContainerViewControllerProvider.get();
            }

            public final DreamOverlayTouchMonitor getDreamOverlayTouchMonitor() {
                TitanSysUIComponentImpl titanSysUIComponentImpl = TitanSysUIComponentImpl.this;
                return new DreamOverlayTouchMonitor(TitanSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), this.providesLifecycleProvider.get(), new InputSessionComponentFactory(), Collections.singleton(titanSysUIComponentImpl.providesBouncerSwipeTouchHandler()));
            }

            public final LifecycleRegistry getLifecycleRegistry() {
                return (LifecycleRegistry) this.providesLifecycleRegistryProvider.get();
            }
        }

        public final class DreamWeatherComplicationComponentFactory implements DreamWeatherComplicationComponent$Factory {
            public DreamWeatherComplicationComponentFactory() {
            }

            public final DreamWeatherComplicationComponentImpl create() {
                return new DreamWeatherComplicationComponentImpl();
            }
        }

        public final class DreamWeatherComplicationComponentImpl {
            public Provider<TextView> provideComplicationViewProvider;
            public Provider<ComplicationLayoutParams> provideLayoutParamsProvider = DoubleCheck.provider(C0800x56e4d2ac.InstanceHolder.INSTANCE);

            public DreamWeatherComplicationComponentImpl() {
                this.provideComplicationViewProvider = DoubleCheck.provider(new C0799x7019a799(TitanSysUIComponentImpl.this.providerLayoutInflaterProvider));
            }
        }

        public final class ExpandableNotificationRowComponentBuilder implements ExpandableNotificationRowComponent.Builder {
            public ExpandableNotificationRow expandableNotificationRow;
            public NotificationListContainer listContainer;
            public NotificationEntry notificationEntry;
            public ExpandableNotificationRow.OnExpandClickListener onExpandClickListener;

            public ExpandableNotificationRowComponentBuilder() {
            }

            public final ExpandableNotificationRowComponent build() {
                R$color.checkBuilderRequirement(this.expandableNotificationRow, ExpandableNotificationRow.class);
                R$color.checkBuilderRequirement(this.notificationEntry, NotificationEntry.class);
                R$color.checkBuilderRequirement(this.onExpandClickListener, ExpandableNotificationRow.OnExpandClickListener.class);
                R$color.checkBuilderRequirement(this.listContainer, NotificationListContainer.class);
                return new ExpandableNotificationRowComponentImpl(TitanSysUIComponentImpl.this, this.expandableNotificationRow, this.notificationEntry, this.onExpandClickListener, this.listContainer);
            }

            public final ExpandableNotificationRowComponent.Builder expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow2) {
                Objects.requireNonNull(expandableNotificationRow2);
                this.expandableNotificationRow = expandableNotificationRow2;
                return this;
            }

            public final ExpandableNotificationRowComponent.Builder listContainer(NotificationListContainer notificationListContainer) {
                Objects.requireNonNull(notificationListContainer);
                this.listContainer = notificationListContainer;
                return this;
            }

            public final ExpandableNotificationRowComponent.Builder notificationEntry(NotificationEntry notificationEntry2) {
                Objects.requireNonNull(notificationEntry2);
                this.notificationEntry = notificationEntry2;
                return this;
            }

            public final ExpandableNotificationRowComponent.Builder onExpandClickListener(NotificationPresenter notificationPresenter) {
                Objects.requireNonNull(notificationPresenter);
                this.onExpandClickListener = notificationPresenter;
                return this;
            }
        }

        public final class ExpandableNotificationRowComponentImpl implements ExpandableNotificationRowComponent {
            public ActivatableNotificationViewController_Factory activatableNotificationViewControllerProvider;
            public Provider<ExpandableNotificationRowController> expandableNotificationRowControllerProvider;
            public ControlsActivity_Factory expandableNotificationRowDragControllerProvider;
            public InstanceFactory expandableNotificationRowProvider;
            public UnpinNotifications_Factory expandableOutlineViewControllerProvider;
            public KeyboardUI_Factory expandableViewControllerProvider;
            public NotificationTapHelper_Factory_Factory factoryProvider;
            public InstanceFactory listContainerProvider;
            public final NotificationEntry notificationEntry;
            public InstanceFactory notificationEntryProvider;
            public InstanceFactory onExpandClickListenerProvider;
            public C1334x3e2d0aca provideAppNameProvider;
            public C1335xdc9a80a2 provideNotificationKeyProvider;
            public C1336xc255c3ca provideStatusBarNotificationProvider;
            public C24611 remoteInputViewSubcomponentFactoryProvider = new Provider<RemoteInputViewSubcomponent.Factory>() {
                public final Object get() {
                    return new RemoteInputViewSubcomponentFactory();
                }
            };
            public final /* synthetic */ TitanSysUIComponentImpl this$1;

            public final class RemoteInputViewSubcomponentFactory implements RemoteInputViewSubcomponent.Factory {
                public RemoteInputViewSubcomponentFactory() {
                }

                public final RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
                    Objects.requireNonNull(remoteInputController);
                    return new RemoteInputViewSubcomponentI(remoteInputView, remoteInputController);
                }
            }

            public final class RemoteInputViewSubcomponentI implements RemoteInputViewSubcomponent {
                public final RemoteInputView arg0;
                public final RemoteInputController arg1;

                public RemoteInputViewSubcomponentI(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
                    this.arg0 = remoteInputView;
                    this.arg1 = remoteInputController;
                }

                public final RemoteInputViewController getController() {
                    RemoteInputView remoteInputView = this.arg0;
                    ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl = ExpandableNotificationRowComponentImpl.this;
                    return new RemoteInputViewControllerImpl(remoteInputView, expandableNotificationRowComponentImpl.notificationEntry, expandableNotificationRowComponentImpl.this$1.remoteInputQuickSettingsDisablerProvider.get(), this.arg1, ExpandableNotificationRowComponentImpl.this.this$1.this$0.provideShortcutManagerProvider.get(), ExpandableNotificationRowComponentImpl.this.this$1.this$0.provideUiEventLoggerProvider.get());
                }
            }

            public ExpandableNotificationRowComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry2, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, NotificationListContainer notificationListContainer) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                this.this$1 = titanSysUIComponentImpl2;
                this.notificationEntry = notificationEntry2;
                this.expandableNotificationRowProvider = InstanceFactory.create(expandableNotificationRow);
                this.listContainerProvider = InstanceFactory.create(notificationListContainer);
                this.factoryProvider = new NotificationTapHelper_Factory_Factory(titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.this$0.provideMainDelayableExecutorProvider);
                KeyboardUI_Factory create$8 = KeyboardUI_Factory.create$8(this.expandableNotificationRowProvider);
                this.expandableViewControllerProvider = create$8;
                UnpinNotifications_Factory create = UnpinNotifications_Factory.create(this.expandableNotificationRowProvider, create$8);
                this.expandableOutlineViewControllerProvider = create;
                this.activatableNotificationViewControllerProvider = ActivatableNotificationViewController_Factory.create(this.expandableNotificationRowProvider, this.factoryProvider, create, titanSysUIComponentImpl2.this$0.provideAccessibilityManagerProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.falsingCollectorImplProvider);
                InstanceFactory create2 = InstanceFactory.create(notificationEntry2);
                this.notificationEntryProvider = create2;
                C1336xc255c3ca expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory = new C1336xc255c3ca(create2);
                this.provideStatusBarNotificationProvider = expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory;
                this.provideAppNameProvider = new C1334x3e2d0aca(titanSysUIComponentImpl2.this$0.contextProvider, expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory);
                this.provideNotificationKeyProvider = new C1335xdc9a80a2(expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory);
                InstanceFactory create3 = InstanceFactory.create(onExpandClickListener);
                this.onExpandClickListenerProvider = create3;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                Provider<Context> provider = daggerTitanGlobalRootComponent.contextProvider;
                Provider<HeadsUpManagerPhone> provider2 = titanSysUIComponentImpl2.provideHeadsUpManagerPhoneProvider;
                ControlsActivity_Factory controlsActivity_Factory = new ControlsActivity_Factory(provider, provider2, 2);
                this.expandableNotificationRowDragControllerProvider = controlsActivity_Factory;
                Provider<HeadsUpManagerPhone> provider3 = provider2;
                InstanceFactory instanceFactory = create3;
                this.expandableNotificationRowControllerProvider = DoubleCheck.provider(ExpandableNotificationRowController_Factory.create(this.expandableNotificationRowProvider, this.listContainerProvider, this.remoteInputViewSubcomponentFactoryProvider, this.activatableNotificationViewControllerProvider, titanSysUIComponentImpl2.provideNotificationMediaManagerProvider, daggerTitanGlobalRootComponent.providesPluginManagerProvider, titanSysUIComponentImpl2.bindSystemClockProvider, this.provideAppNameProvider, this.provideNotificationKeyProvider, titanSysUIComponentImpl2.keyguardBypassControllerProvider, titanSysUIComponentImpl2.provideGroupMembershipManagerProvider, titanSysUIComponentImpl2.provideGroupExpansionManagerProvider, titanSysUIComponentImpl2.rowContentBindStageProvider, titanSysUIComponentImpl2.provideNotificationLoggerProvider, provider3, instanceFactory, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.provideNotificationGutsManagerProvider, titanSysUIComponentImpl2.provideAllowNotificationLongPressProvider, titanSysUIComponentImpl2.provideOnUserInteractionCallbackProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.falsingCollectorImplProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, titanSysUIComponentImpl2.peopleNotificationIdentifierImplProvider, titanSysUIComponentImpl2.provideBubblesManagerProvider, controlsActivity_Factory));
            }

            public final ExpandableNotificationRowController getExpandableNotificationRowController() {
                return this.expandableNotificationRowControllerProvider.get();
            }
        }

        public final class FragmentCreatorFactory implements FragmentService.FragmentCreator.Factory {
            public FragmentCreatorFactory() {
            }

            public final FragmentService.FragmentCreator build() {
                return new FragmentCreatorImpl();
            }
        }

        public final class FragmentCreatorImpl implements FragmentService.FragmentCreator {
            public FragmentCreatorImpl() {
            }

            public final QSFragment createQSFragment() {
                QSTileHost qSTileHost = TitanSysUIComponentImpl.this.qSTileHostProvider.get();
                TitanSysUIComponentImpl titanSysUIComponentImpl = TitanSysUIComponentImpl.this;
                return new QSFragment(TitanSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider.get(), TitanSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.provideCommandQueueProvider.get(), TitanSysUIComponentImpl.this.providesQSMediaHostProvider.get(), TitanSysUIComponentImpl.this.providesQuickQSMediaHostProvider.get(), TitanSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), new QSFragmentComponentFactory(), new QSFragmentDisableFlagsLogger(titanSysUIComponentImpl.provideQSFragmentDisableLogBufferProvider.get(), TitanSysUIComponentImpl.this.disableFlagsLoggerProvider.get()), TitanSysUIComponentImpl.this.falsingManagerProxyProvider.get(), TitanSysUIComponentImpl.this.this$0.dumpManagerProvider.get());
            }
        }

        public final class InputSessionComponentFactory implements InputSessionComponent.Factory {
            public InputSessionComponentFactory() {
            }

            public final InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z) {
                Objects.requireNonNull(inputChannelCompat$InputEventListener);
                Objects.requireNonNull(onGestureListener);
                Boolean bool = Boolean.TRUE;
                Objects.requireNonNull(bool);
                return new InputSessionComponentImpl((DreamOverlayTouchMonitor.C08032) inputChannelCompat$InputEventListener, (DreamOverlayTouchMonitor.C08043) onGestureListener, bool);
            }
        }

        public final class InputSessionComponentImpl implements InputSessionComponent {
            public final String arg0 = "dreamOverlay";
            public final InputChannelCompat$InputEventListener arg1;
            public final GestureDetector.OnGestureListener arg2;
            public final Boolean arg3;

            public final InputSession getInputSession() {
                return new InputSession(this.arg0, this.arg1, this.arg2, this.arg3.booleanValue());
            }

            public InputSessionComponentImpl(DreamOverlayTouchMonitor.C08032 r2, DreamOverlayTouchMonitor.C08043 r3, Boolean bool) {
                this.arg1 = r2;
                this.arg2 = r3;
                this.arg3 = bool;
            }
        }

        public final class KeyguardBouncerComponentFactory implements KeyguardBouncerComponent.Factory {
            public KeyguardBouncerComponentFactory() {
            }

            public final KeyguardBouncerComponent create(ViewGroup viewGroup) {
                Objects.requireNonNull(viewGroup);
                return new KeyguardBouncerComponentImpl(TitanSysUIComponentImpl.this, viewGroup);
            }
        }

        public final class KeyguardBouncerComponentImpl implements KeyguardBouncerComponent {
            public InstanceFactory arg0Provider;
            public Provider<AdminSecondaryLockScreenController.Factory> factoryProvider;
            public EmergencyButtonController_Factory_Factory factoryProvider2;
            public KeyguardInputViewController_Factory_Factory factoryProvider3;
            public KeyguardSecurityContainerController_Factory_Factory factoryProvider4;
            public Provider<KeyguardHostViewController> keyguardHostViewControllerProvider;
            public Provider<KeyguardSecurityViewFlipperController> keyguardSecurityViewFlipperControllerProvider;
            public LiftToActivateListener_Factory liftToActivateListenerProvider;
            public Provider<KeyguardHostView> providesKeyguardHostViewProvider;
            public Provider<KeyguardSecurityContainer> providesKeyguardSecurityContainerProvider;
            public Provider<KeyguardSecurityViewFlipper> providesKeyguardSecurityViewFlipperProvider = C0772x82ed519d.m53m(this.providesKeyguardSecurityContainerProvider, 1);

            public KeyguardBouncerComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, ViewGroup viewGroup) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                InstanceFactory create = InstanceFactory.create(viewGroup);
                this.arg0Provider = create;
                Provider<KeyguardHostView> provider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(create, titanSysUIComponentImpl2.providerLayoutInflaterProvider, 1));
                this.providesKeyguardHostViewProvider = provider;
                Provider<KeyguardSecurityContainer> m = C0773x82ed519e.m54m(provider, 1);
                this.providesKeyguardSecurityContainerProvider = m;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                this.factoryProvider = DoubleCheck.provider(new AdminSecondaryLockScreenController_Factory_Factory(daggerTitanGlobalRootComponent.contextProvider, m, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent.provideMainHandlerProvider));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = titanSysUIComponentImpl2.this$0;
                this.liftToActivateListenerProvider = new LiftToActivateListener_Factory(daggerTitanGlobalRootComponent2.provideAccessibilityManagerProvider, 0);
                EmergencyButtonController_Factory_Factory create2 = EmergencyButtonController_Factory_Factory.create(titanSysUIComponentImpl2.provideConfigurationControllerProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent2.provideTelephonyManagerProvider, daggerTitanGlobalRootComponent2.providePowerManagerProvider, daggerTitanGlobalRootComponent2.provideActivityTaskManagerProvider, titanSysUIComponentImpl2.shadeControllerImplProvider, daggerTitanGlobalRootComponent2.provideTelecomManagerProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider);
                this.factoryProvider2 = create2;
                Provider<KeyguardUpdateMonitor> provider2 = titanSysUIComponentImpl2.keyguardUpdateMonitorProvider;
                Provider<LockPatternUtils> provider3 = titanSysUIComponentImpl2.provideLockPatternUtilsProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = titanSysUIComponentImpl2.this$0;
                KeyguardInputViewController_Factory_Factory create3 = KeyguardInputViewController_Factory_Factory.create(provider2, provider3, daggerTitanGlobalRootComponent3.provideLatencyTrackerProvider, titanSysUIComponentImpl2.factoryProvider5, daggerTitanGlobalRootComponent3.provideInputMethodManagerProvider, daggerTitanGlobalRootComponent3.provideMainDelayableExecutorProvider, daggerTitanGlobalRootComponent3.provideResourcesProvider, this.liftToActivateListenerProvider, daggerTitanGlobalRootComponent3.provideTelephonyManagerProvider, titanSysUIComponentImpl2.falsingCollectorImplProvider, create2, titanSysUIComponentImpl2.devicePostureControllerImplProvider);
                this.factoryProvider3 = create3;
                Provider<KeyguardSecurityViewFlipperController> provider4 = DoubleCheck.provider(new KeyguardSecurityViewFlipperController_Factory(this.providesKeyguardSecurityViewFlipperProvider, titanSysUIComponentImpl2.providerLayoutInflaterProvider, create3, this.factoryProvider2));
                this.keyguardSecurityViewFlipperControllerProvider = provider4;
                Provider<KeyguardSecurityContainer> provider5 = this.providesKeyguardSecurityContainerProvider;
                Provider<AdminSecondaryLockScreenController.Factory> provider6 = this.factoryProvider;
                Provider<LockPatternUtils> provider7 = titanSysUIComponentImpl2.provideLockPatternUtilsProvider;
                Provider<KeyguardUpdateMonitor> provider8 = titanSysUIComponentImpl2.keyguardUpdateMonitorProvider;
                Provider<KeyguardSecurityModel> provider9 = titanSysUIComponentImpl2.keyguardSecurityModelProvider;
                Provider<MetricsLogger> provider10 = titanSysUIComponentImpl2.provideMetricsLoggerProvider;
                Provider<UiEventLogger> provider11 = titanSysUIComponentImpl2.this$0.provideUiEventLoggerProvider;
                Provider<KeyguardStateControllerImpl> provider12 = titanSysUIComponentImpl2.keyguardStateControllerImplProvider;
                Provider<ConfigurationController> provider13 = titanSysUIComponentImpl2.provideConfigurationControllerProvider;
                Provider provider14 = titanSysUIComponentImpl2.falsingCollectorImplProvider;
                Provider<FalsingManagerProxy> provider15 = titanSysUIComponentImpl2.falsingManagerProxyProvider;
                Provider<UserSwitcherController> provider16 = titanSysUIComponentImpl2.userSwitcherControllerProvider;
                KeyguardSecurityContainerController_Factory_Factory create4 = KeyguardSecurityContainerController_Factory_Factory.create(provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider4, provider13, provider14, provider15, provider16, titanSysUIComponentImpl2.featureFlagsReleaseProvider, titanSysUIComponentImpl2.globalSettingsImplProvider, titanSysUIComponentImpl2.sessionTrackerProvider);
                this.factoryProvider4 = create4;
                Provider<KeyguardHostView> provider17 = this.providesKeyguardHostViewProvider;
                Provider<KeyguardUpdateMonitor> provider18 = titanSysUIComponentImpl2.keyguardUpdateMonitorProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = titanSysUIComponentImpl2.this$0;
                this.keyguardHostViewControllerProvider = DoubleCheck.provider(KeyguardHostViewController_Factory.create(provider17, provider18, daggerTitanGlobalRootComponent4.provideAudioManagerProvider, daggerTitanGlobalRootComponent4.provideTelephonyManagerProvider, titanSysUIComponentImpl2.providesViewMediatorCallbackProvider, create4));
            }

            public final KeyguardHostViewController getKeyguardHostViewController() {
                return this.keyguardHostViewControllerProvider.get();
            }
        }

        public final class KeyguardQsUserSwitchComponentFactory implements KeyguardQsUserSwitchComponent.Factory {
            public KeyguardQsUserSwitchComponentFactory() {
            }

            public final KeyguardQsUserSwitchComponent build(FrameLayout frameLayout) {
                Objects.requireNonNull(frameLayout);
                return new KeyguardQsUserSwitchComponentImpl(TitanSysUIComponentImpl.this, frameLayout);
            }
        }

        public final class KeyguardQsUserSwitchComponentImpl implements KeyguardQsUserSwitchComponent {
            public InstanceFactory arg0Provider;
            public Provider<KeyguardQsUserSwitchController> keyguardQsUserSwitchControllerProvider;

            public KeyguardQsUserSwitchComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, FrameLayout frameLayout) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                InstanceFactory create = InstanceFactory.create(frameLayout);
                this.arg0Provider = create;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                this.keyguardQsUserSwitchControllerProvider = DoubleCheck.provider(KeyguardQsUserSwitchController_Factory.create(create, daggerTitanGlobalRootComponent.contextProvider, daggerTitanGlobalRootComponent.provideResourcesProvider, daggerTitanGlobalRootComponent.screenLifecycleProvider, titanSysUIComponentImpl2.userSwitcherControllerProvider, titanSysUIComponentImpl2.communalStateControllerProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.dozeParametersProvider, titanSysUIComponentImpl2.screenOffAnimationControllerProvider, titanSysUIComponentImpl2.userSwitchDialogControllerProvider, daggerTitanGlobalRootComponent.provideUiEventLoggerProvider));
            }

            public final KeyguardQsUserSwitchController getKeyguardQsUserSwitchController() {
                return this.keyguardQsUserSwitchControllerProvider.get();
            }
        }

        public final class KeyguardStatusBarViewComponentFactory implements KeyguardStatusBarViewComponent.Factory {
            public KeyguardStatusBarViewComponentFactory() {
            }

            public final KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                Objects.requireNonNull(keyguardStatusBarView);
                Objects.requireNonNull(notificationPanelViewStateProvider);
                return new KeyguardStatusBarViewComponentImpl(keyguardStatusBarView, notificationPanelViewStateProvider);
            }
        }

        public final class KeyguardStatusBarViewComponentImpl implements KeyguardStatusBarViewComponent {
            public final KeyguardStatusBarView arg0;
            public InstanceFactory arg0Provider;
            public final NotificationPanelViewController.NotificationPanelViewStateProvider arg1;
            public Provider<StatusBarUserSwitcherController> bindStatusBarUserSwitcherControllerProvider;
            public Provider<BatteryMeterView> getBatteryMeterViewProvider = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.arg0Provider, 1));
            public Provider<CarrierText> getCarrierTextProvider;
            public Provider<StatusBarUserSwitcherContainer> getUserSwitcherContainerProvider;
            public ClockManager_Factory statusBarUserSwitcherControllerImplProvider;

            public KeyguardStatusBarViewComponentImpl(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                this.arg0 = keyguardStatusBarView;
                this.arg1 = notificationPanelViewStateProvider;
                InstanceFactory create = InstanceFactory.create(keyguardStatusBarView);
                this.arg0Provider = create;
                this.getCarrierTextProvider = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(create, 1));
                Provider<StatusBarUserSwitcherContainer> provider = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.arg0Provider, 1));
                this.getUserSwitcherContainerProvider = provider;
                ClockManager_Factory create$1 = ClockManager_Factory.create$1(provider, TitanSysUIComponentImpl.this.statusBarUserInfoTrackerProvider, TitanSysUIComponentImpl.this.statusBarUserSwitcherFeatureControllerProvider, TitanSysUIComponentImpl.this.userSwitchDialogControllerProvider, TitanSysUIComponentImpl.this.featureFlagsReleaseProvider, TitanSysUIComponentImpl.this.provideActivityStarterProvider);
                this.statusBarUserSwitcherControllerImplProvider = create$1;
                this.bindStatusBarUserSwitcherControllerProvider = DoubleCheck.provider(create$1);
            }

            public final KeyguardStatusBarViewController getKeyguardStatusBarViewController() {
                KeyguardStatusBarView keyguardStatusBarView = this.arg0;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = TitanSysUIComponentImpl.this.this$0;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = TitanSysUIComponentImpl.this.this$0;
                Provider provider = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
                return new KeyguardStatusBarViewController(keyguardStatusBarView, new CarrierTextController(this.getCarrierTextProvider.get(), new CarrierTextManager.Builder(daggerTitanGlobalRootComponent.context, daggerTitanGlobalRootComponent.mainResources(), TitanSysUIComponentImpl.this.this$0.provideWifiManagerProvider.get(), TitanSysUIComponentImpl.this.this$0.provideTelephonyManagerProvider.get(), TitanSysUIComponentImpl.this.telephonyListenerManagerProvider.get(), TitanSysUIComponentImpl.this.wakefulnessLifecycleProvider.get(), TitanSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TitanSysUIComponentImpl.this.provideBackgroundExecutorProvider.get(), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get()), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get()), TitanSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TitanSysUIComponentImpl.this.systemStatusAnimationSchedulerProvider.get(), TitanSysUIComponentImpl.this.provideBatteryControllerProvider.get(), TitanSysUIComponentImpl.this.userInfoControllerImplProvider.get(), TitanSysUIComponentImpl.this.statusBarIconControllerImplProvider.get(), TitanSysUIComponentImpl.this.factoryProvider10.get(), new BatteryMeterViewController(this.getBatteryMeterViewProvider.get(), TitanSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TitanSysUIComponentImpl.this.tunerServiceImplProvider.get(), TitanSysUIComponentImpl.this.providesBroadcastDispatcherProvider.get(), daggerTitanGlobalRootComponent2.mainHandler(), TitanSysUIComponentImpl.this.this$0.provideContentResolverProvider.get(), TitanSysUIComponentImpl.this.provideBatteryControllerProvider.get()), this.arg1, TitanSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TitanSysUIComponentImpl.this.biometricUnlockControllerProvider.get(), TitanSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.statusBarContentInsetsProvider.get(), TitanSysUIComponentImpl.this.this$0.provideUserManagerProvider.get(), TitanSysUIComponentImpl.this.statusBarUserSwitcherFeatureControllerProvider.get(), this.bindStatusBarUserSwitcherControllerProvider.get(), TitanSysUIComponentImpl.this.statusBarUserInfoTrackerProvider.get());
            }
        }

        public final class KeyguardStatusViewComponentFactory implements KeyguardStatusViewComponent.Factory {
            public KeyguardStatusViewComponentFactory() {
            }

            public final KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView) {
                Objects.requireNonNull(keyguardStatusView);
                return new KeyguardStatusViewComponentImpl(keyguardStatusView);
            }
        }

        public final class KeyguardStatusViewComponentImpl implements KeyguardStatusViewComponent {
            public final KeyguardStatusView arg0;
            public InstanceFactory arg0Provider;
            public VrMode_Factory getKeyguardClockSwitchProvider;
            public KeyboardUI_Factory getKeyguardSliceViewProvider;
            public Provider<KeyguardSliceViewController> keyguardSliceViewControllerProvider;

            public KeyguardStatusViewComponentImpl(KeyguardStatusView keyguardStatusView) {
                this.arg0 = keyguardStatusView;
                InstanceFactory create = InstanceFactory.create(keyguardStatusView);
                this.arg0Provider = create;
                VrMode_Factory vrMode_Factory = new VrMode_Factory(create, 1);
                this.getKeyguardClockSwitchProvider = vrMode_Factory;
                KeyboardUI_Factory keyboardUI_Factory = new KeyboardUI_Factory(vrMode_Factory, 1);
                this.getKeyguardSliceViewProvider = keyboardUI_Factory;
                this.keyguardSliceViewControllerProvider = DoubleCheck.provider(KeyguardSliceViewController_Factory.create(keyboardUI_Factory, TitanSysUIComponentImpl.this.provideActivityStarterProvider, TitanSysUIComponentImpl.this.provideConfigurationControllerProvider, TitanSysUIComponentImpl.this.tunerServiceImplProvider, TitanSysUIComponentImpl.this.this$0.dumpManagerProvider));
            }

            public final KeyguardClockSwitchController getKeyguardClockSwitchController() {
                KeyguardBypassController keyguardBypassController = TitanSysUIComponentImpl.this.keyguardBypassControllerProvider.get();
                return new KeyguardClockSwitchController(VrMode_Factory.getKeyguardClockSwitch(this.arg0), TitanSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.sysuiColorExtractorProvider.get(), TitanSysUIComponentImpl.this.clockManagerProvider.get(), this.keyguardSliceViewControllerProvider.get(), TitanSysUIComponentImpl.this.notificationIconAreaControllerProvider.get(), TitanSysUIComponentImpl.this.providesBroadcastDispatcherProvider.get(), TitanSysUIComponentImpl.this.provideBatteryControllerProvider.get(), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TitanSysUIComponentImpl.this.lockscreenSmartspaceControllerProvider.get(), TitanSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider.get(), (SecureSettings) TitanSysUIComponentImpl.this.secureSettingsImpl(), TitanSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TitanSysUIComponentImpl.this.this$0.mainResources());
            }

            public final KeyguardStatusViewController getKeyguardStatusViewController() {
                DozeParameters dozeParameters = TitanSysUIComponentImpl.this.dozeParametersProvider.get();
                return new KeyguardStatusViewController(this.arg0, this.keyguardSliceViewControllerProvider.get(), getKeyguardClockSwitchController(), TitanSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TitanSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TitanSysUIComponentImpl.this.communalStateControllerProvider.get(), TitanSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TitanSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider.get(), TitanSysUIComponentImpl.this.screenOffAnimationControllerProvider.get());
            }
        }

        public final class KeyguardUserSwitcherComponentFactory implements KeyguardUserSwitcherComponent.Factory {
            public KeyguardUserSwitcherComponentFactory() {
            }

            public final KeyguardUserSwitcherComponent build(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                Objects.requireNonNull(keyguardUserSwitcherView);
                return new KeyguardUserSwitcherComponentImpl(TitanSysUIComponentImpl.this, keyguardUserSwitcherView);
            }
        }

        public final class KeyguardUserSwitcherComponentImpl implements KeyguardUserSwitcherComponent {
            public InstanceFactory arg0Provider;
            public Provider<KeyguardUserSwitcherController> keyguardUserSwitcherControllerProvider;

            public final KeyguardUserSwitcherController getKeyguardUserSwitcherController() {
                return this.keyguardUserSwitcherControllerProvider.get();
            }

            public KeyguardUserSwitcherComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, KeyguardUserSwitcherView keyguardUserSwitcherView) {
                InstanceFactory create = InstanceFactory.create(keyguardUserSwitcherView);
                this.arg0Provider = create;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl.this$0;
                this.keyguardUserSwitcherControllerProvider = DoubleCheck.provider(KeyguardUserSwitcherController_Factory.create(create, daggerTitanGlobalRootComponent.contextProvider, daggerTitanGlobalRootComponent.provideResourcesProvider, titanSysUIComponentImpl.providerLayoutInflaterProvider, daggerTitanGlobalRootComponent.screenLifecycleProvider, titanSysUIComponentImpl.userSwitcherControllerProvider, titanSysUIComponentImpl.communalStateControllerProvider, titanSysUIComponentImpl.keyguardStateControllerImplProvider, titanSysUIComponentImpl.statusBarStateControllerImplProvider, titanSysUIComponentImpl.keyguardUpdateMonitorProvider, titanSysUIComponentImpl.dozeParametersProvider, titanSysUIComponentImpl.screenOffAnimationControllerProvider));
            }
        }

        public final class MediaComplicationComponentFactory implements MediaComplicationComponent$Factory {
            public MediaComplicationComponentFactory() {
            }

            public final MediaComplicationComponentImpl create() {
                return new MediaComplicationComponentImpl();
            }
        }

        public final class MediaComplicationComponentImpl {
            public Provider<FrameLayout> provideComplicationContainerProvider;
            public Provider<ComplicationLayoutParams> provideLayoutParamsProvider = DoubleCheck.provider(C0905xd5b79ace.InstanceHolder.INSTANCE);

            public MediaComplicationComponentImpl() {
                this.provideComplicationContainerProvider = DoubleCheck.provider(new C0904x5add62f3(TitanSysUIComponentImpl.this.this$0.contextProvider));
            }
        }

        public final class MediaShellComponentFactory implements MediaShellComponent$Factory {
            public MediaShellComponentFactory() {
            }

            public final MediaShellComponentImpl create(MediaShellCallback.C22031 r2) {
                return new MediaShellComponentImpl(r2);
            }
        }

        public final class MediaShellComponentImpl {
            public final ObservableServiceConnection.Callback<IBinder> callback;

            public MediaShellComponentImpl(MediaShellCallback.C22031 r2) {
                this.callback = r2;
            }
        }

        public final class MonitorComponentFactory implements MonitorComponent.Factory {
            public MonitorComponentFactory() {
            }

            public final MonitorComponent create(Set<Condition> set, Set<Monitor.Callback> set2) {
                Objects.requireNonNull(set);
                Objects.requireNonNull(set2);
                return new MonitorComponentImpl(set, set2);
            }
        }

        public final class MonitorComponentImpl implements MonitorComponent {
            public final Set<Condition> arg0;
            public final Set<Monitor.Callback> arg1;

            public MonitorComponentImpl(Set set, Set set2) {
                this.arg0 = set;
                this.arg1 = set2;
            }

            public final Monitor getMonitor() {
                return new Monitor(TitanSysUIComponentImpl.this.provideExecutorProvider.get(), this.arg0, this.arg1);
            }
        }

        public final class NotificationShelfComponentBuilder implements NotificationShelfComponent.Builder {
            public NotificationShelf notificationShelf;

            public NotificationShelfComponentBuilder() {
            }

            public final NotificationShelfComponent build() {
                R$color.checkBuilderRequirement(this.notificationShelf, NotificationShelf.class);
                return new NotificationShelfComponentImpl(TitanSysUIComponentImpl.this, this.notificationShelf);
            }

            public final NotificationShelfComponent.Builder notificationShelf(NotificationShelf notificationShelf2) {
                Objects.requireNonNull(notificationShelf2);
                this.notificationShelf = notificationShelf2;
                return this;
            }
        }

        public final class NotificationShelfComponentImpl implements NotificationShelfComponent {
            public ActivatableNotificationViewController_Factory activatableNotificationViewControllerProvider;
            public UnpinNotifications_Factory expandableOutlineViewControllerProvider;
            public KeyboardUI_Factory expandableViewControllerProvider;
            public NotificationTapHelper_Factory_Factory factoryProvider;
            public Provider<NotificationShelfController> notificationShelfControllerProvider;
            public InstanceFactory notificationShelfProvider;

            public final NotificationShelfController getNotificationShelfController() {
                return this.notificationShelfControllerProvider.get();
            }

            public NotificationShelfComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, NotificationShelf notificationShelf) {
                InstanceFactory create = InstanceFactory.create(notificationShelf);
                this.notificationShelfProvider = create;
                this.factoryProvider = new NotificationTapHelper_Factory_Factory(titanSysUIComponentImpl.falsingManagerProxyProvider, titanSysUIComponentImpl.this$0.provideMainDelayableExecutorProvider);
                KeyboardUI_Factory create$8 = KeyboardUI_Factory.create$8(create);
                this.expandableViewControllerProvider = create$8;
                UnpinNotifications_Factory create2 = UnpinNotifications_Factory.create(this.notificationShelfProvider, create$8);
                this.expandableOutlineViewControllerProvider = create2;
                ActivatableNotificationViewController_Factory create3 = ActivatableNotificationViewController_Factory.create(this.notificationShelfProvider, this.factoryProvider, create2, titanSysUIComponentImpl.this$0.provideAccessibilityManagerProvider, titanSysUIComponentImpl.falsingManagerProxyProvider, titanSysUIComponentImpl.falsingCollectorImplProvider);
                this.activatableNotificationViewControllerProvider = create3;
                this.notificationShelfControllerProvider = DoubleCheck.provider(new LogBufferEulogizer_Factory(this.notificationShelfProvider, create3, titanSysUIComponentImpl.keyguardBypassControllerProvider, titanSysUIComponentImpl.statusBarStateControllerImplProvider, 1));
            }
        }

        public final class QSFragmentComponentFactory implements QSFragmentComponent.Factory {
            public QSFragmentComponentFactory() {
            }

            public final QSFragmentComponent create(QSFragment qSFragment) {
                Objects.requireNonNull(qSFragment);
                return new QSFragmentComponentImpl(TitanSysUIComponentImpl.this, qSFragment);
            }
        }

        public final class QSFragmentComponentImpl implements QSFragmentComponent {
            public InstanceFactory arg0Provider;
            public BatteryMeterViewController_Factory batteryMeterViewControllerProvider;
            public CarrierTextManager_Builder_Factory builderProvider;
            public QSCarrierGroupController_Builder_Factory builderProvider2;
            public Provider factoryProvider;
            public BrightnessController_Factory_Factory factoryProvider2;
            public VariableDateViewController_Factory_Factory factoryProvider3;
            public Provider<MultiUserSwitchController.Factory> factoryProvider4;
            public Provider<FooterActionsController> footerActionsControllerProvider;
            public HeaderPrivacyIconsController_Factory headerPrivacyIconsControllerProvider;
            public QSFragmentModule_ProvideQSPanelFactory provideQSPanelProvider;
            public QSFragmentModule_ProvideRootViewFactory provideRootViewProvider;
            public QSFragmentModule_ProvideThemedContextFactory provideThemedContextProvider;
            public LogModule_ProvidePrivacyLogBufferFactory provideThemedLayoutInflaterProvider;
            public VrMode_Factory providesBatteryMeterViewProvider;
            public Provider<OngoingPrivacyChip> providesPrivacyChipProvider;
            public DependencyProvider_ProvideHandlerFactory providesQSContainerImplProvider;
            public Provider<QSCustomizer> providesQSCutomizerProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.provideRootViewProvider, 4));
            public Provider<View> providesQSFgsManagerFooterViewProvider;
            public QSFragmentModule_ProvidesQSFooterActionsViewFactory providesQSFooterActionsViewProvider;
            public Provider<QSFooter> providesQSFooterProvider;
            public SecureSettingsImpl_Factory providesQSFooterViewProvider;
            public Provider<View> providesQSSecurityFooterViewProvider;
            public PrivacyLogger_Factory providesQSUsingCollapsedLandscapeMediaProvider;
            public MediaBrowserFactory_Factory providesQSUsingMediaPlayerProvider;
            public QSFragmentModule_ProvidesQuickQSPanelFactory providesQuickQSPanelProvider;
            public DateFormatUtil_Factory providesQuickStatusBarHeaderProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<QSAnimator> qSAnimatorProvider;
            public Provider<QSContainerImplController> qSContainerImplControllerProvider;
            public Provider<QSCustomizerController> qSCustomizerControllerProvider;
            public Provider<QSFgsManagerFooter> qSFgsManagerFooterProvider;
            public Provider<QSFooterViewController> qSFooterViewControllerProvider;
            public Provider<QSPanelController> qSPanelControllerProvider;
            public Provider qSSecurityFooterProvider;
            public Provider<QSSquishinessController> qSSquishinessControllerProvider;
            public Provider<QuickQSPanelController> quickQSPanelControllerProvider;
            public Provider quickStatusBarHeaderControllerProvider;
            public Provider<TileAdapter> tileAdapterProvider;
            public Provider<TileQueryHelper> tileQueryHelperProvider;

            public QSFragmentComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, QSFragment qSFragment) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                InstanceFactory create = InstanceFactory.create(qSFragment);
                this.arg0Provider = create;
                QSFragmentModule_ProvideRootViewFactory qSFragmentModule_ProvideRootViewFactory = new QSFragmentModule_ProvideRootViewFactory(create, 0);
                this.provideRootViewProvider = qSFragmentModule_ProvideRootViewFactory;
                QSFragmentModule_ProvideQSPanelFactory qSFragmentModule_ProvideQSPanelFactory = new QSFragmentModule_ProvideQSPanelFactory(qSFragmentModule_ProvideRootViewFactory, 0);
                this.provideQSPanelProvider = qSFragmentModule_ProvideQSPanelFactory;
                QSFragmentModule_ProvideThemedContextFactory qSFragmentModule_ProvideThemedContextFactory = new QSFragmentModule_ProvideThemedContextFactory(qSFragmentModule_ProvideRootViewFactory, 0);
                this.provideThemedContextProvider = qSFragmentModule_ProvideThemedContextFactory;
                LogModule_ProvidePrivacyLogBufferFactory logModule_ProvidePrivacyLogBufferFactory = new LogModule_ProvidePrivacyLogBufferFactory(qSFragmentModule_ProvideThemedContextFactory, 3);
                this.provideThemedLayoutInflaterProvider = logModule_ProvidePrivacyLogBufferFactory;
                Provider<View> provider = DoubleCheck.provider(new DependencyProvider_ProvideLeakDetectorFactory(logModule_ProvidePrivacyLogBufferFactory, (Provider) qSFragmentModule_ProvideQSPanelFactory));
                this.providesQSFgsManagerFooterViewProvider = provider;
                this.qSFgsManagerFooterProvider = DoubleCheck.provider(new QSFgsManagerFooter_Factory(provider, titanSysUIComponentImpl2.this$0.provideMainExecutorProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider, titanSysUIComponentImpl2.fgsManagerControllerProvider, 0));
                Provider<View> provider2 = DoubleCheck.provider(new SliceBroadcastRelayHandler_Factory(this.provideThemedLayoutInflaterProvider, this.provideQSPanelProvider, 1));
                this.providesQSSecurityFooterViewProvider = provider2;
                this.qSSecurityFooterProvider = DoubleCheck.provider(QSSecurityFooter_Factory.create(provider2, titanSysUIComponentImpl2.provideUserTrackerProvider, titanSysUIComponentImpl2.this$0.provideMainHandlerProvider, titanSysUIComponentImpl2.provideActivityStarterProvider, titanSysUIComponentImpl2.securityControllerImplProvider, titanSysUIComponentImpl2.provideDialogLaunchAnimatorProvider, titanSysUIComponentImpl2.provideBgLooperProvider));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                this.tileQueryHelperProvider = DoubleCheck.provider(new TileQueryHelper_Factory(daggerTitanGlobalRootComponent.contextProvider, titanSysUIComponentImpl2.provideUserTrackerProvider, daggerTitanGlobalRootComponent.provideMainExecutorProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider));
                Provider<TileAdapter> provider3 = DoubleCheck.provider(new TileAdapter_Factory(this.provideThemedContextProvider, titanSysUIComponentImpl2.qSTileHostProvider, titanSysUIComponentImpl2.this$0.provideUiEventLoggerProvider, 0));
                this.tileAdapterProvider = provider3;
                Provider<QSCustomizer> provider4 = this.providesQSCutomizerProvider;
                Provider<TileQueryHelper> provider5 = this.tileQueryHelperProvider;
                Provider<QSTileHost> provider6 = titanSysUIComponentImpl2.qSTileHostProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = titanSysUIComponentImpl2.this$0;
                Provider<QSCustomizerController> provider7 = DoubleCheck.provider(QSCustomizerController_Factory.create$1(provider4, provider5, provider6, provider3, daggerTitanGlobalRootComponent2.screenLifecycleProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.lightBarControllerProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent2.provideUiEventLoggerProvider));
                this.qSCustomizerControllerProvider = provider7;
                Provider<Context> provider8 = titanSysUIComponentImpl2.this$0.contextProvider;
                this.providesQSUsingMediaPlayerProvider = new MediaBrowserFactory_Factory(provider8, 3);
                Provider provider9 = DoubleCheck.provider(new QSTileRevealController_Factory_Factory(provider8, provider7));
                Provider provider10 = provider9;
                this.factoryProvider = provider9;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = titanSysUIComponentImpl2.this$0;
                BrightnessController_Factory_Factory brightnessController_Factory_Factory = r9;
                BrightnessController_Factory_Factory brightnessController_Factory_Factory2 = new BrightnessController_Factory_Factory(daggerTitanGlobalRootComponent3.contextProvider, titanSysUIComponentImpl2.providesBroadcastDispatcherProvider, titanSysUIComponentImpl2.provideBgHandlerProvider);
                this.factoryProvider2 = brightnessController_Factory_Factory2;
                this.qSPanelControllerProvider = DoubleCheck.provider(QSPanelController_Factory.create(this.provideQSPanelProvider, this.qSFgsManagerFooterProvider, this.qSSecurityFooterProvider, titanSysUIComponentImpl2.tunerServiceImplProvider, titanSysUIComponentImpl2.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, titanSysUIComponentImpl2.providesQSMediaHostProvider, provider10, daggerTitanGlobalRootComponent3.dumpManagerProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, titanSysUIComponentImpl2.qSLoggerProvider, brightnessController_Factory_Factory, titanSysUIComponentImpl2.factoryProvider6, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider));
                DateFormatUtil_Factory dateFormatUtil_Factory = new DateFormatUtil_Factory(this.provideRootViewProvider, 2);
                this.providesQuickStatusBarHeaderProvider = dateFormatUtil_Factory;
                QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = new QSFragmentModule_ProvidesQuickQSPanelFactory(dateFormatUtil_Factory, 0);
                this.providesQuickQSPanelProvider = qSFragmentModule_ProvidesQuickQSPanelFactory;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = titanSysUIComponentImpl2.this$0;
                PrivacyLogger_Factory privacyLogger_Factory = new PrivacyLogger_Factory(daggerTitanGlobalRootComponent4.contextProvider, 2);
                this.providesQSUsingCollapsedLandscapeMediaProvider = privacyLogger_Factory;
                Provider<QuickQSPanelController> provider11 = DoubleCheck.provider(QuickQSPanelController_Factory.create(qSFragmentModule_ProvidesQuickQSPanelFactory, titanSysUIComponentImpl2.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, titanSysUIComponentImpl2.providesQuickQSMediaHostProvider, privacyLogger_Factory, titanSysUIComponentImpl2.mediaFlagsProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, daggerTitanGlobalRootComponent4.provideUiEventLoggerProvider, titanSysUIComponentImpl2.qSLoggerProvider, daggerTitanGlobalRootComponent4.dumpManagerProvider));
                this.quickQSPanelControllerProvider = provider11;
                InstanceFactory instanceFactory = this.arg0Provider;
                QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory2 = this.providesQuickQSPanelProvider;
                DateFormatUtil_Factory dateFormatUtil_Factory2 = this.providesQuickStatusBarHeaderProvider;
                Provider<QSPanelController> provider12 = this.qSPanelControllerProvider;
                Provider<QSTileHost> provider13 = titanSysUIComponentImpl2.qSTileHostProvider;
                Provider<QSFgsManagerFooter> provider14 = this.qSFgsManagerFooterProvider;
                Provider provider15 = this.qSSecurityFooterProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = titanSysUIComponentImpl2.this$0;
                this.qSAnimatorProvider = DoubleCheck.provider(QSAnimator_Factory.create(instanceFactory, qSFragmentModule_ProvidesQuickQSPanelFactory2, dateFormatUtil_Factory2, provider12, provider11, provider13, provider14, provider15, daggerTitanGlobalRootComponent5.provideMainExecutorProvider, titanSysUIComponentImpl2.tunerServiceImplProvider, daggerTitanGlobalRootComponent5.qSExpansionPathInterpolatorProvider));
                this.providesQSContainerImplProvider = new DependencyProvider_ProvideHandlerFactory(this.provideRootViewProvider, 3);
                this.providesPrivacyChipProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.providesQuickStatusBarHeaderProvider, 4));
                Provider<StatusIconContainer> provider16 = DoubleCheck.provider(new ScreenLifecycle_Factory(this.providesQuickStatusBarHeaderProvider, 3));
                this.providesStatusIconContainerProvider = provider16;
                Provider<PrivacyItemController> provider17 = titanSysUIComponentImpl2.privacyItemControllerProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = titanSysUIComponentImpl2.this$0;
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(provider17, daggerTitanGlobalRootComponent6.provideUiEventLoggerProvider, this.providesPrivacyChipProvider, titanSysUIComponentImpl2.privacyDialogControllerProvider, titanSysUIComponentImpl2.privacyLoggerProvider, provider16, daggerTitanGlobalRootComponent6.providePermissionManagerProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent6.provideMainExecutorProvider, titanSysUIComponentImpl2.provideActivityStarterProvider, titanSysUIComponentImpl2.appOpsControllerImplProvider, titanSysUIComponentImpl2.deviceConfigProxyProvider);
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = titanSysUIComponentImpl2.this$0;
                CarrierTextManager_Builder_Factory create2 = CarrierTextManager_Builder_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, daggerTitanGlobalRootComponent7.provideResourcesProvider, daggerTitanGlobalRootComponent7.provideWifiManagerProvider, daggerTitanGlobalRootComponent7.provideTelephonyManagerProvider, titanSysUIComponentImpl2.telephonyListenerManagerProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, daggerTitanGlobalRootComponent7.provideMainExecutorProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider);
                this.builderProvider = create2;
                this.builderProvider2 = QSCarrierGroupController_Builder_Factory.create(titanSysUIComponentImpl2.provideActivityStarterProvider, titanSysUIComponentImpl2.provideBgHandlerProvider, titanSysUIComponentImpl2.networkControllerImplProvider, create2, titanSysUIComponentImpl2.this$0.contextProvider, titanSysUIComponentImpl2.carrierConfigTrackerProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, titanSysUIComponentImpl2.subscriptionManagerSlotIndexResolverProvider);
                Provider<SystemClock> provider18 = titanSysUIComponentImpl2.bindSystemClockProvider;
                Provider<BroadcastDispatcher> provider19 = titanSysUIComponentImpl2.providesBroadcastDispatcherProvider;
                this.factoryProvider3 = new VariableDateViewController_Factory_Factory(provider18, provider19, titanSysUIComponentImpl2.provideTimeTickHandlerProvider);
                VrMode_Factory vrMode_Factory = new VrMode_Factory(this.providesQuickStatusBarHeaderProvider, 4);
                this.providesBatteryMeterViewProvider = vrMode_Factory;
                Provider<ConfigurationController> provider20 = titanSysUIComponentImpl2.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider21 = titanSysUIComponentImpl2.tunerServiceImplProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = titanSysUIComponentImpl2.this$0;
                BatteryMeterViewController_Factory create3 = BatteryMeterViewController_Factory.create(vrMode_Factory, provider20, provider21, provider19, daggerTitanGlobalRootComponent8.provideMainHandlerProvider, daggerTitanGlobalRootComponent8.provideContentResolverProvider, titanSysUIComponentImpl2.provideBatteryControllerProvider);
                this.batteryMeterViewControllerProvider = create3;
                Provider provider22 = DoubleCheck.provider(QuickStatusBarHeaderController_Factory.create(this.providesQuickStatusBarHeaderProvider, this.headerPrivacyIconsControllerProvider, titanSysUIComponentImpl2.statusBarIconControllerImplProvider, titanSysUIComponentImpl2.provideDemoModeControllerProvider, this.quickQSPanelControllerProvider, this.builderProvider2, titanSysUIComponentImpl2.sysuiColorExtractorProvider, titanSysUIComponentImpl2.this$0.qSExpansionPathInterpolatorProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, this.factoryProvider3, create3, titanSysUIComponentImpl2.statusBarContentInsetsProvider));
                this.quickStatusBarHeaderControllerProvider = provider22;
                this.qSContainerImplControllerProvider = DoubleCheck.provider(new QSContainerImplController_Factory(this.providesQSContainerImplProvider, this.qSPanelControllerProvider, provider22, titanSysUIComponentImpl2.provideConfigurationControllerProvider));
                SecureSettingsImpl_Factory secureSettingsImpl_Factory = new SecureSettingsImpl_Factory(this.provideRootViewProvider, 2);
                this.providesQSFooterViewProvider = secureSettingsImpl_Factory;
                Provider<QSFooterViewController> provider23 = DoubleCheck.provider(new QSFooterViewController_Factory(secureSettingsImpl_Factory, titanSysUIComponentImpl2.provideUserTrackerProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.provideActivityStarterProvider, this.qSPanelControllerProvider));
                this.qSFooterViewControllerProvider = provider23;
                this.providesQSFooterProvider = DoubleCheck.provider(new TvPipModule_ProvideTvPipBoundsStateFactory(provider23, 3));
                this.qSSquishinessControllerProvider = DoubleCheck.provider(new QSSquishinessController_Factory(this.qSAnimatorProvider, this.qSPanelControllerProvider, this.quickQSPanelControllerProvider, 0));
                QSFragmentModule_ProvideRootViewFactory qSFragmentModule_ProvideRootViewFactory2 = this.provideRootViewProvider;
                Provider<FeatureFlagsRelease> provider24 = titanSysUIComponentImpl2.featureFlagsReleaseProvider;
                this.providesQSFooterActionsViewProvider = new QSFragmentModule_ProvidesQSFooterActionsViewFactory(qSFragmentModule_ProvideRootViewFactory2, provider24, 0);
                Provider<MultiUserSwitchController.Factory> provider25 = DoubleCheck.provider(MultiUserSwitchController_Factory_Factory.create(titanSysUIComponentImpl2.this$0.provideUserManagerProvider, titanSysUIComponentImpl2.userSwitcherControllerProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.userSwitchDialogControllerProvider, provider24, titanSysUIComponentImpl2.provideActivityStarterProvider));
                Provider<MultiUserSwitchController.Factory> provider26 = provider25;
                this.factoryProvider4 = provider25;
                QSFragmentModule_ProvidesQSFooterActionsViewFactory qSFragmentModule_ProvidesQSFooterActionsViewFactory = this.providesQSFooterActionsViewProvider;
                Provider<ActivityStarter> provider27 = titanSysUIComponentImpl2.provideActivityStarterProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = titanSysUIComponentImpl2.this$0;
                this.footerActionsControllerProvider = DoubleCheck.provider(FooterActionsController_Factory.create(qSFragmentModule_ProvidesQSFooterActionsViewFactory, provider26, provider27, daggerTitanGlobalRootComponent9.provideUserManagerProvider, titanSysUIComponentImpl2.provideUserTrackerProvider, titanSysUIComponentImpl2.userInfoControllerImplProvider, titanSysUIComponentImpl2.provideDeviceProvisionedControllerProvider, this.qSSecurityFooterProvider, this.qSFgsManagerFooterProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, titanSysUIComponentImpl2.globalActionsDialogLiteProvider, daggerTitanGlobalRootComponent9.provideUiEventLoggerProvider, titanSysUIComponentImpl2.isPMLiteEnabledProvider, titanSysUIComponentImpl2.globalSettingsImplProvider, titanSysUIComponentImpl2.provideHandlerProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider));
            }

            public final QSAnimator getQSAnimator() {
                return this.qSAnimatorProvider.get();
            }

            public final QSContainerImplController getQSContainerImplController() {
                return this.qSContainerImplControllerProvider.get();
            }

            public final QSCustomizerController getQSCustomizerController() {
                return this.qSCustomizerControllerProvider.get();
            }

            public final QSFooter getQSFooter() {
                return this.providesQSFooterProvider.get();
            }

            public final FooterActionsController getQSFooterActionController() {
                return this.footerActionsControllerProvider.get();
            }

            public final QSPanelController getQSPanelController() {
                return this.qSPanelControllerProvider.get();
            }

            public final QSSquishinessController getQSSquishinessController() {
                return this.qSSquishinessControllerProvider.get();
            }

            public final QuickQSPanelController getQuickQSPanelController() {
                return this.quickQSPanelControllerProvider.get();
            }
        }

        public final class SectionHeaderControllerSubcomponentBuilder implements SectionHeaderControllerSubcomponent.Builder {
            public String clickIntentAction;
            public Integer headerText;
            public String nodeLabel;

            public SectionHeaderControllerSubcomponentBuilder() {
            }

            public final SectionHeaderControllerSubcomponent build() {
                Class<String> cls = String.class;
                R$color.checkBuilderRequirement(this.nodeLabel, cls);
                R$color.checkBuilderRequirement(this.headerText, Integer.class);
                R$color.checkBuilderRequirement(this.clickIntentAction, cls);
                return new SectionHeaderControllerSubcomponentImpl(TitanSysUIComponentImpl.this, this.nodeLabel, this.headerText, this.clickIntentAction);
            }

            public final SectionHeaderControllerSubcomponent.Builder headerText(int i) {
                Integer valueOf = Integer.valueOf(i);
                Objects.requireNonNull(valueOf);
                this.headerText = valueOf;
                return this;
            }

            public final SectionHeaderControllerSubcomponent.Builder clickIntentAction(String str) {
                this.clickIntentAction = str;
                return this;
            }

            public final SectionHeaderControllerSubcomponent.Builder nodeLabel(String str) {
                this.nodeLabel = str;
                return this;
            }
        }

        public final class SectionHeaderControllerSubcomponentImpl implements SectionHeaderControllerSubcomponent {
            public InstanceFactory clickIntentActionProvider;
            public InstanceFactory headerTextProvider;
            public InstanceFactory nodeLabelProvider;
            public Provider<SectionHeaderNodeControllerImpl> sectionHeaderNodeControllerImplProvider;

            public final SectionHeaderController getHeaderController() {
                return this.sectionHeaderNodeControllerImplProvider.get();
            }

            public final NodeController getNodeController() {
                return this.sectionHeaderNodeControllerImplProvider.get();
            }

            public SectionHeaderControllerSubcomponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, String str, Integer num, String str2) {
                this.nodeLabelProvider = InstanceFactory.create(str);
                this.headerTextProvider = InstanceFactory.create(num);
                InstanceFactory create = InstanceFactory.create(str2);
                this.clickIntentActionProvider = create;
                this.sectionHeaderNodeControllerImplProvider = DoubleCheck.provider(new SectionHeaderNodeControllerImpl_Factory(this.nodeLabelProvider, titanSysUIComponentImpl.providerLayoutInflaterProvider, this.headerTextProvider, titanSysUIComponentImpl.provideActivityStarterProvider, create));
            }
        }

        public final class ServiceBinderCallbackComponentFactory implements ServiceBinderCallbackComponent$Factory {
            public ServiceBinderCallbackComponentFactory() {
            }

            public final ServiceBinderCallbackComponentImpl create(Intent intent) {
                return new ServiceBinderCallbackComponentImpl(intent);
            }
        }

        public final class ServiceBinderCallbackComponentImpl {
            public final Intent intent;

            public ServiceBinderCallbackComponentImpl(Intent intent2) {
                this.intent = intent2;
            }
        }

        public final class SetupDreamComponentFactory implements SetupDreamComponent$Factory {
            public SetupDreamComponentFactory() {
            }

            public final SetupDreamComponentImpl create(ComplicationViewModel complicationViewModel) {
                Objects.requireNonNull(complicationViewModel);
                return new SetupDreamComponentImpl(complicationViewModel);
            }
        }

        public final class SetupDreamComponentImpl {
            public final ComplicationViewModel model;

            public SetupDreamComponentImpl(ComplicationViewModel complicationViewModel) {
                this.model = complicationViewModel;
            }
        }

        public final class StatusBarComponentFactory implements StatusBarComponent.Factory {
            public StatusBarComponentFactory() {
            }

            public final StatusBarComponent create() {
                return new StatusBarComponentImpl(TitanSysUIComponentImpl.this);
            }
        }

        public final class StatusBarComponentImpl implements StatusBarComponent {
            public Provider<AuthRippleController> authRippleControllerProvider;
            public Provider<StatusBarComponent.Startable> bindStartableProvider;
            public NotificationSwipeHelper_Builder_Factory builderProvider;
            public FlingAnimationUtils_Builder_Factory builderProvider2;
            public CarrierTextManager_Builder_Factory builderProvider3;
            public QSCarrierGroupController_Builder_Factory builderProvider4;
            public Provider<AuthRippleView> getAuthRippleViewProvider;
            public Provider<BatteryMeterViewController> getBatteryMeterViewControllerProvider;
            public Provider<BatteryMeterView> getBatteryMeterViewProvider;
            public Provider<LockIconView> getLockIconViewProvider;
            public Provider<NotificationPanelView> getNotificationPanelViewProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.providesNotificationShadeWindowViewProvider, 4));
            public Provider<NotificationsQuickSettingsContainer> getNotificationsQuickSettingsContainerProvider;
            public Provider<OngoingPrivacyChip> getSplitShadeOngoingPrivacyChipProvider;
            public Provider<View> getSplitShadeStatusBarViewProvider;
            public Provider<TapAgainView> getTapAgainViewProvider;
            public HeaderPrivacyIconsController_Factory headerPrivacyIconsControllerProvider;
            public Provider<LockIconViewController> lockIconViewControllerProvider;
            public Provider<NotificationPanelViewController> notificationPanelViewControllerProvider;
            public Provider<NotificationStackScrollLayoutController> notificationStackScrollLayoutControllerProvider;
            public TvPipModule_ProvideTvPipBoundsStateFactory notificationStackScrollLoggerProvider;
            public MediaSessionBasedFilter_Factory notificationsQSContainerControllerProvider;
            public Provider<NotificationShadeWindowView> providesNotificationShadeWindowViewProvider;
            public Provider<NotificationShelf> providesNotificationShelfProvider;
            public Provider<NotificationStackScrollLayout> providesNotificationStackScrollLayoutProvider;
            public Provider<NotificationShelfController> providesStatusBarWindowViewProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<SplitShadeHeaderController> splitShadeHeaderControllerProvider;
            public ScreenLifecycle_Factory stackStateLoggerProvider;
            public Provider<StatusBarCommandQueueCallbacks> statusBarCommandQueueCallbacksProvider;
            public Provider<StatusBarHeadsUpChangeListener> statusBarHeadsUpChangeListenerProvider;
            public Provider<StatusBarInitializer> statusBarInitializerProvider;
            public Provider<TapAgainViewController> tapAgainViewControllerProvider;
            public final /* synthetic */ TitanSysUIComponentImpl this$1;

            public final class StatusBarFragmentComponentFactory implements StatusBarFragmentComponent.Factory {
                public StatusBarFragmentComponentFactory() {
                }

                public final StatusBarFragmentComponent create(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    Objects.requireNonNull(collapsedStatusBarFragment);
                    return new StatusBarFragmentComponentI(StatusBarComponentImpl.this, collapsedStatusBarFragment);
                }
            }

            public final class StatusBarFragmentComponentI implements StatusBarFragmentComponent {
                public InstanceFactory arg0Provider;
                public Provider<StatusBarUserSwitcherController> bindStatusBarUserSwitcherControllerProvider;
                public PhoneStatusBarViewController_Factory_Factory factoryProvider;
                public Provider<HeadsUpAppearanceController> headsUpAppearanceControllerProvider;
                public Provider<LightsOutNotifController> lightsOutNotifControllerProvider;
                public Provider<BatteryMeterView> provideBatteryMeterViewProvider;
                public Provider<Clock> provideClockProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.providePhoneStatusBarViewProvider, 5));
                public Provider<View> provideLightsOutNotifViewProvider;
                public Provider<Optional<View>> provideOperatorFrameNameViewProvider;
                public Provider<View> provideOperatorNameViewProvider;
                public Provider<PhoneStatusBarTransitions> providePhoneStatusBarTransitionsProvider;
                public Provider<PhoneStatusBarViewController> providePhoneStatusBarViewControllerProvider;
                public Provider<PhoneStatusBarView> providePhoneStatusBarViewProvider;
                public Provider<StatusBarUserSwitcherContainer> provideStatusBarUserSwitcherContainerProvider;
                public Provider<HeadsUpStatusBarView> providesHeasdUpStatusBarViewProvider = DoubleCheck.provider(new DozeLogger_Factory(this.providePhoneStatusBarViewProvider, 5));
                public Provider<StatusBarDemoMode> statusBarDemoModeProvider;
                public ClockManager_Factory statusBarUserSwitcherControllerImplProvider;
                public final /* synthetic */ StatusBarComponentImpl this$2;

                public StatusBarFragmentComponentI(StatusBarComponentImpl statusBarComponentImpl, CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    StatusBarComponentImpl statusBarComponentImpl2 = statusBarComponentImpl;
                    this.this$2 = statusBarComponentImpl2;
                    InstanceFactory create = InstanceFactory.create(collapsedStatusBarFragment);
                    this.arg0Provider = create;
                    Provider<PhoneStatusBarView> provider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(create, 4));
                    this.providePhoneStatusBarViewProvider = provider;
                    this.provideBatteryMeterViewProvider = DoubleCheck.provider(new TimeoutManager_Factory(provider, 3));
                    Provider<StatusBarUserSwitcherContainer> provider2 = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.providePhoneStatusBarViewProvider, 2));
                    this.provideStatusBarUserSwitcherContainerProvider = provider2;
                    TitanSysUIComponentImpl titanSysUIComponentImpl = statusBarComponentImpl2.this$1;
                    ClockManager_Factory create$1 = ClockManager_Factory.create$1(provider2, titanSysUIComponentImpl.statusBarUserInfoTrackerProvider, titanSysUIComponentImpl.statusBarUserSwitcherFeatureControllerProvider, titanSysUIComponentImpl.userSwitchDialogControllerProvider, titanSysUIComponentImpl.featureFlagsReleaseProvider, titanSysUIComponentImpl.provideActivityStarterProvider);
                    this.statusBarUserSwitcherControllerImplProvider = create$1;
                    Provider<StatusBarUserSwitcherController> provider3 = DoubleCheck.provider(create$1);
                    this.bindStatusBarUserSwitcherControllerProvider = provider3;
                    TitanSysUIComponentImpl titanSysUIComponentImpl2 = statusBarComponentImpl2.this$1;
                    PhoneStatusBarViewController_Factory_Factory phoneStatusBarViewController_Factory_Factory = new PhoneStatusBarViewController_Factory_Factory(titanSysUIComponentImpl2.provideSysUIUnfoldComponentProvider, titanSysUIComponentImpl2.this$0.provideStatusBarScopedTransitionProvider, provider3, titanSysUIComponentImpl2.provideConfigurationControllerProvider);
                    this.factoryProvider = phoneStatusBarViewController_Factory_Factory;
                    this.providePhoneStatusBarViewControllerProvider = DoubleCheck.provider(new C1597xfe780fd7(phoneStatusBarViewController_Factory_Factory, this.providePhoneStatusBarViewProvider, statusBarComponentImpl2.notificationPanelViewControllerProvider));
                    Provider<Optional<View>> provider4 = DoubleCheck.provider(new FrameworkServicesModule_ProvideFaceManagerFactory(this.providePhoneStatusBarViewProvider, 2));
                    this.provideOperatorFrameNameViewProvider = provider4;
                    TitanSysUIComponentImpl titanSysUIComponentImpl3 = statusBarComponentImpl2.this$1;
                    this.headsUpAppearanceControllerProvider = DoubleCheck.provider(HeadsUpAppearanceController_Factory.create(titanSysUIComponentImpl3.notificationIconAreaControllerProvider, titanSysUIComponentImpl3.provideHeadsUpManagerPhoneProvider, titanSysUIComponentImpl3.statusBarStateControllerImplProvider, titanSysUIComponentImpl3.keyguardBypassControllerProvider, titanSysUIComponentImpl3.notificationWakeUpCoordinatorProvider, titanSysUIComponentImpl3.darkIconDispatcherImplProvider, titanSysUIComponentImpl3.keyguardStateControllerImplProvider, titanSysUIComponentImpl3.provideCommandQueueProvider, statusBarComponentImpl2.notificationStackScrollLayoutControllerProvider, statusBarComponentImpl2.notificationPanelViewControllerProvider, this.providesHeasdUpStatusBarViewProvider, this.provideClockProvider, provider4));
                    Provider<View> provider5 = DoubleCheck.provider(new MediaControllerFactory_Factory(this.providePhoneStatusBarViewProvider, 5));
                    this.provideLightsOutNotifViewProvider = provider5;
                    TitanSysUIComponentImpl titanSysUIComponentImpl4 = statusBarComponentImpl2.this$1;
                    this.lightsOutNotifControllerProvider = DoubleCheck.provider(new LightsOutNotifController_Factory(provider5, titanSysUIComponentImpl4.this$0.provideWindowManagerProvider, titanSysUIComponentImpl4.notifLiveDataStoreImplProvider, titanSysUIComponentImpl4.provideCommandQueueProvider));
                    this.provideOperatorNameViewProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(this.providePhoneStatusBarViewProvider, 4));
                    Provider<PhoneStatusBarTransitions> provider6 = DoubleCheck.provider(new ShadeEventCoordinator_Factory(this.providePhoneStatusBarViewProvider, statusBarComponentImpl2.this$1.statusBarWindowControllerProvider, 1));
                    this.providePhoneStatusBarTransitionsProvider = provider6;
                    Provider<Clock> provider7 = this.provideClockProvider;
                    Provider<View> provider8 = this.provideOperatorNameViewProvider;
                    TitanSysUIComponentImpl titanSysUIComponentImpl5 = statusBarComponentImpl2.this$1;
                    this.statusBarDemoModeProvider = DoubleCheck.provider(StatusBarDemoMode_Factory.create$1(provider7, provider8, titanSysUIComponentImpl5.provideDemoModeControllerProvider, provider6, titanSysUIComponentImpl5.navigationBarControllerProvider, titanSysUIComponentImpl5.this$0.provideDisplayIdProvider));
                }

                public final BatteryMeterViewController getBatteryMeterViewController() {
                    DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$2.this$1.this$0;
                    Provider provider = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
                    return new BatteryMeterViewController(this.provideBatteryMeterViewProvider.get(), this.this$2.this$1.provideConfigurationControllerProvider.get(), this.this$2.this$1.tunerServiceImplProvider.get(), this.this$2.this$1.providesBroadcastDispatcherProvider.get(), daggerTitanGlobalRootComponent.mainHandler(), this.this$2.this$1.this$0.provideContentResolverProvider.get(), this.this$2.this$1.provideBatteryControllerProvider.get());
                }

                public final HeadsUpAppearanceController getHeadsUpAppearanceController() {
                    return this.headsUpAppearanceControllerProvider.get();
                }

                public final LightsOutNotifController getLightsOutNotifController() {
                    return this.lightsOutNotifControllerProvider.get();
                }

                public final PhoneStatusBarTransitions getPhoneStatusBarTransitions() {
                    return this.providePhoneStatusBarTransitionsProvider.get();
                }

                public final PhoneStatusBarView getPhoneStatusBarView() {
                    return this.providePhoneStatusBarViewProvider.get();
                }

                public final PhoneStatusBarViewController getPhoneStatusBarViewController() {
                    return this.providePhoneStatusBarViewControllerProvider.get();
                }

                public final StatusBarDemoMode getStatusBarDemoMode() {
                    return this.statusBarDemoModeProvider.get();
                }
            }

            public StatusBarComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl) {
                TitanSysUIComponentImpl titanSysUIComponentImpl2 = titanSysUIComponentImpl;
                this.this$1 = titanSysUIComponentImpl2;
                Provider<NotificationShadeWindowView> provider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(titanSysUIComponentImpl2.providerLayoutInflaterProvider, 3));
                this.providesNotificationShadeWindowViewProvider = provider;
                Provider<NotificationStackScrollLayout> provider2 = DoubleCheck.provider(new LiftToActivateListener_Factory(provider, 5));
                this.providesNotificationStackScrollLayoutProvider = provider2;
                Provider<NotificationShelf> provider3 = DoubleCheck.provider(new DreamOverlayRegistrant_Factory(titanSysUIComponentImpl2.providerLayoutInflaterProvider, provider2, 1));
                this.providesNotificationShelfProvider = provider3;
                this.providesStatusBarWindowViewProvider = DoubleCheck.provider(new StatusBarViewModule_ProvidesStatusBarWindowViewFactory(titanSysUIComponentImpl2.notificationShelfComponentBuilderProvider, provider3));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl2.this$0;
                Provider<Resources> provider4 = daggerTitanGlobalRootComponent.provideResourcesProvider;
                Provider<ViewConfiguration> provider5 = daggerTitanGlobalRootComponent.provideViewConfigurationProvider;
                Provider<FalsingManagerProxy> provider6 = titanSysUIComponentImpl2.falsingManagerProxyProvider;
                NotificationSwipeHelper_Builder_Factory notificationSwipeHelper_Builder_Factory = new NotificationSwipeHelper_Builder_Factory(provider4, provider5, provider6, titanSysUIComponentImpl2.featureFlagsReleaseProvider);
                this.builderProvider = notificationSwipeHelper_Builder_Factory;
                Provider<LogBuffer> provider7 = titanSysUIComponentImpl2.provideNotificationHeadsUpLogBufferProvider;
                ScreenLifecycle_Factory screenLifecycle_Factory = new ScreenLifecycle_Factory(provider7, 4);
                this.stackStateLoggerProvider = screenLifecycle_Factory;
                TvPipModule_ProvideTvPipBoundsStateFactory tvPipModule_ProvideTvPipBoundsStateFactory = new TvPipModule_ProvideTvPipBoundsStateFactory(provider7, 4);
                this.notificationStackScrollLoggerProvider = tvPipModule_ProvideTvPipBoundsStateFactory;
                ScreenLifecycle_Factory screenLifecycle_Factory2 = screenLifecycle_Factory;
                NotificationSwipeHelper_Builder_Factory notificationSwipeHelper_Builder_Factory2 = notificationSwipeHelper_Builder_Factory;
                Provider<FalsingManagerProxy> provider8 = provider6;
                Provider<Resources> provider9 = provider4;
                this.notificationStackScrollLayoutControllerProvider = DoubleCheck.provider(NotificationStackScrollLayoutController_Factory.create(titanSysUIComponentImpl2.provideAllowNotificationLongPressProvider, titanSysUIComponentImpl2.provideNotificationGutsManagerProvider, titanSysUIComponentImpl2.provideNotificationVisibilityProvider, titanSysUIComponentImpl2.provideHeadsUpManagerPhoneProvider, titanSysUIComponentImpl2.notificationRoundnessManagerProvider, titanSysUIComponentImpl2.tunerServiceImplProvider, titanSysUIComponentImpl2.provideDeviceProvisionedControllerProvider, titanSysUIComponentImpl2.dynamicPrivacyControllerProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.keyguardMediaControllerProvider, titanSysUIComponentImpl2.keyguardBypassControllerProvider, titanSysUIComponentImpl2.zenModeControllerImplProvider, titanSysUIComponentImpl2.sysuiColorExtractorProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, titanSysUIComponentImpl2.falsingCollectorImplProvider, provider8, provider9, notificationSwipeHelper_Builder_Factory2, titanSysUIComponentImpl2.statusBarGoogleProvider, titanSysUIComponentImpl2.scrimControllerProvider, titanSysUIComponentImpl2.notificationGroupManagerLegacyProvider, titanSysUIComponentImpl2.provideGroupExpansionManagerProvider, titanSysUIComponentImpl2.providesSilentHeaderControllerProvider, titanSysUIComponentImpl2.notifPipelineFlagsProvider, titanSysUIComponentImpl2.notifPipelineProvider, titanSysUIComponentImpl2.notifCollectionProvider, titanSysUIComponentImpl2.provideNotificationEntryManagerProvider, titanSysUIComponentImpl2.lockscreenShadeTransitionControllerProvider, daggerTitanGlobalRootComponent.provideIStatusBarServiceProvider, daggerTitanGlobalRootComponent.provideUiEventLoggerProvider, titanSysUIComponentImpl2.foregroundServiceDismissalFeatureControllerProvider, titanSysUIComponentImpl2.foregroundServiceSectionControllerProvider, titanSysUIComponentImpl2.providerLayoutInflaterProvider, titanSysUIComponentImpl2.provideNotificationRemoteInputManagerProvider, titanSysUIComponentImpl2.provideVisualStabilityManagerProvider, titanSysUIComponentImpl2.shadeControllerImplProvider, daggerTitanGlobalRootComponent.provideInteractionJankMonitorProvider, screenLifecycle_Factory2, tvPipModule_ProvideTvPipBoundsStateFactory));
                this.builderProvider2 = new FlingAnimationUtils_Builder_Factory(titanSysUIComponentImpl2.this$0.provideDisplayMetricsProvider);
                Provider<NotificationsQuickSettingsContainer> provider10 = DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(this.providesNotificationShadeWindowViewProvider, 2));
                this.getNotificationsQuickSettingsContainerProvider = provider10;
                this.notificationsQSContainerControllerProvider = new MediaSessionBasedFilter_Factory(provider10, titanSysUIComponentImpl2.navigationModeControllerProvider, titanSysUIComponentImpl2.overviewProxyServiceProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, 1);
                this.getLockIconViewProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.providesNotificationShadeWindowViewProvider, 5));
                Provider<AuthRippleView> provider11 = DoubleCheck.provider(new QSFragmentModule_ProvidesQuickQSPanelFactory(this.providesNotificationShadeWindowViewProvider, 5));
                this.getAuthRippleViewProvider = provider11;
                Provider<AuthRippleController> provider12 = DoubleCheck.provider(AuthRippleController_Factory.create(titanSysUIComponentImpl2.statusBarGoogleProvider, titanSysUIComponentImpl2.this$0.contextProvider, titanSysUIComponentImpl2.authControllerProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, titanSysUIComponentImpl2.commandRegistryProvider, titanSysUIComponentImpl2.notificationShadeWindowControllerImplProvider, titanSysUIComponentImpl2.keyguardBypassControllerProvider, titanSysUIComponentImpl2.biometricUnlockControllerProvider, titanSysUIComponentImpl2.udfpsControllerProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, provider11));
                this.authRippleControllerProvider = provider12;
                Provider<LockIconView> provider13 = this.getLockIconViewProvider;
                Provider<StatusBarStateControllerImpl> provider14 = titanSysUIComponentImpl2.statusBarStateControllerImplProvider;
                Provider<KeyguardUpdateMonitor> provider15 = titanSysUIComponentImpl2.keyguardUpdateMonitorProvider;
                Provider<StatusBarKeyguardViewManager> provider16 = titanSysUIComponentImpl2.statusBarKeyguardViewManagerProvider;
                Provider<KeyguardStateControllerImpl> provider17 = titanSysUIComponentImpl2.keyguardStateControllerImplProvider;
                Provider<FalsingManagerProxy> provider18 = titanSysUIComponentImpl2.falsingManagerProxyProvider;
                Provider<AuthController> provider19 = titanSysUIComponentImpl2.authControllerProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = titanSysUIComponentImpl2.this$0;
                this.lockIconViewControllerProvider = DoubleCheck.provider(LockIconViewController_Factory.create(provider13, provider14, provider15, provider16, provider17, provider18, provider19, daggerTitanGlobalRootComponent2.dumpManagerProvider, daggerTitanGlobalRootComponent2.provideAccessibilityManagerProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent2.provideMainDelayableExecutorProvider, titanSysUIComponentImpl2.vibratorHelperProvider, provider12, daggerTitanGlobalRootComponent2.provideResourcesProvider));
                Provider<TapAgainView> provider20 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.getNotificationPanelViewProvider, 4));
                this.getTapAgainViewProvider = provider20;
                this.tapAgainViewControllerProvider = DoubleCheck.provider(new AssistLogger_Factory(provider20, titanSysUIComponentImpl2.this$0.provideMainDelayableExecutorProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, FalsingModule_ProvidesDoubleTapTimeoutMsFactory.InstanceHolder.INSTANCE, 1));
                Provider<View> provider21 = DoubleCheck.provider(new DependencyProvider_ProvidesViewMediatorCallbackFactory(this.providesNotificationShadeWindowViewProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, 2));
                this.getSplitShadeStatusBarViewProvider = provider21;
                this.getSplitShadeOngoingPrivacyChipProvider = DoubleCheck.provider(new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(provider21, 2));
                Provider<StatusIconContainer> provider22 = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.getSplitShadeStatusBarViewProvider, 3));
                this.providesStatusIconContainerProvider = provider22;
                Provider<PrivacyItemController> provider23 = titanSysUIComponentImpl2.privacyItemControllerProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = titanSysUIComponentImpl2.this$0;
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(provider23, daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, this.getSplitShadeOngoingPrivacyChipProvider, titanSysUIComponentImpl2.privacyDialogControllerProvider, titanSysUIComponentImpl2.privacyLoggerProvider, provider22, daggerTitanGlobalRootComponent3.providePermissionManagerProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent3.provideMainExecutorProvider, titanSysUIComponentImpl2.provideActivityStarterProvider, titanSysUIComponentImpl2.appOpsControllerImplProvider, titanSysUIComponentImpl2.deviceConfigProxyProvider);
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = titanSysUIComponentImpl2.this$0;
                CarrierTextManager_Builder_Factory create = CarrierTextManager_Builder_Factory.create(daggerTitanGlobalRootComponent4.contextProvider, daggerTitanGlobalRootComponent4.provideResourcesProvider, daggerTitanGlobalRootComponent4.provideWifiManagerProvider, daggerTitanGlobalRootComponent4.provideTelephonyManagerProvider, titanSysUIComponentImpl2.telephonyListenerManagerProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, daggerTitanGlobalRootComponent4.provideMainExecutorProvider, titanSysUIComponentImpl2.provideBackgroundExecutorProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider);
                this.builderProvider3 = create;
                this.builderProvider4 = QSCarrierGroupController_Builder_Factory.create(titanSysUIComponentImpl2.provideActivityStarterProvider, titanSysUIComponentImpl2.provideBgHandlerProvider, titanSysUIComponentImpl2.networkControllerImplProvider, create, titanSysUIComponentImpl2.this$0.contextProvider, titanSysUIComponentImpl2.carrierConfigTrackerProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, titanSysUIComponentImpl2.subscriptionManagerSlotIndexResolverProvider);
                Provider<BatteryMeterView> provider24 = DoubleCheck.provider(new ScreenLifecycle_Factory(this.getSplitShadeStatusBarViewProvider, 5));
                this.getBatteryMeterViewProvider = provider24;
                Provider<ConfigurationController> provider25 = titanSysUIComponentImpl2.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider26 = titanSysUIComponentImpl2.tunerServiceImplProvider;
                Provider<BroadcastDispatcher> provider27 = titanSysUIComponentImpl2.providesBroadcastDispatcherProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = titanSysUIComponentImpl2.this$0;
                Provider<BatteryMeterViewController> provider28 = DoubleCheck.provider(StatusBarViewModule_GetBatteryMeterViewControllerFactory.create(provider24, provider25, provider26, provider27, daggerTitanGlobalRootComponent5.provideMainHandlerProvider, daggerTitanGlobalRootComponent5.provideContentResolverProvider, titanSysUIComponentImpl2.provideBatteryControllerProvider));
                this.getBatteryMeterViewControllerProvider = provider28;
                Provider<SplitShadeHeaderController> provider29 = DoubleCheck.provider(SplitShadeHeaderController_Factory.create(this.getSplitShadeStatusBarViewProvider, titanSysUIComponentImpl2.statusBarIconControllerImplProvider, this.headerPrivacyIconsControllerProvider, this.builderProvider4, titanSysUIComponentImpl2.featureFlagsReleaseProvider, provider28, titanSysUIComponentImpl2.this$0.dumpManagerProvider));
                Provider<SplitShadeHeaderController> provider30 = provider29;
                this.splitShadeHeaderControllerProvider = provider29;
                Provider<NotificationPanelView> provider31 = this.getNotificationPanelViewProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = titanSysUIComponentImpl2.this$0;
                Provider<NotificationPanelViewController> provider32 = DoubleCheck.provider(NotificationPanelViewController_Factory.create(provider31, daggerTitanGlobalRootComponent6.provideResourcesProvider, daggerTitanGlobalRootComponent6.provideMainHandlerProvider, titanSysUIComponentImpl2.providerLayoutInflaterProvider, titanSysUIComponentImpl2.featureFlagsReleaseProvider, titanSysUIComponentImpl2.notificationWakeUpCoordinatorProvider, titanSysUIComponentImpl2.pulseExpansionHandlerProvider, titanSysUIComponentImpl2.dynamicPrivacyControllerProvider, titanSysUIComponentImpl2.keyguardBypassControllerProvider, titanSysUIComponentImpl2.falsingManagerProxyProvider, titanSysUIComponentImpl2.falsingCollectorImplProvider, titanSysUIComponentImpl2.notificationLockscreenUserManagerGoogleProvider, titanSysUIComponentImpl2.provideNotificationEntryManagerProvider, titanSysUIComponentImpl2.communalStateControllerProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.statusBarWindowStateControllerProvider, titanSysUIComponentImpl2.notificationShadeWindowControllerImplProvider, titanSysUIComponentImpl2.dozeLogProvider, titanSysUIComponentImpl2.dozeParametersProvider, titanSysUIComponentImpl2.provideCommandQueueProvider, titanSysUIComponentImpl2.vibratorHelperProvider, daggerTitanGlobalRootComponent6.provideLatencyTrackerProvider, daggerTitanGlobalRootComponent6.providePowerManagerProvider, daggerTitanGlobalRootComponent6.provideAccessibilityManagerProvider, daggerTitanGlobalRootComponent6.provideDisplayIdProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.communalSourceMonitorProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, daggerTitanGlobalRootComponent6.provideActivityManagerProvider, titanSysUIComponentImpl2.provideConfigurationControllerProvider, this.builderProvider2, titanSysUIComponentImpl2.statusBarTouchableRegionManagerProvider, titanSysUIComponentImpl2.conversationNotificationManagerProvider, titanSysUIComponentImpl2.mediaHierarchyManagerProvider, titanSysUIComponentImpl2.statusBarKeyguardViewManagerProvider, this.notificationsQSContainerControllerProvider, this.notificationStackScrollLayoutControllerProvider, titanSysUIComponentImpl2.keyguardStatusViewComponentFactoryProvider, titanSysUIComponentImpl2.keyguardQsUserSwitchComponentFactoryProvider, titanSysUIComponentImpl2.keyguardUserSwitcherComponentFactoryProvider, titanSysUIComponentImpl2.keyguardStatusBarViewComponentFactoryProvider, titanSysUIComponentImpl2.communalViewComponentFactoryProvider, titanSysUIComponentImpl2.lockscreenShadeTransitionControllerProvider, titanSysUIComponentImpl2.notificationGroupManagerLegacyProvider, titanSysUIComponentImpl2.notificationIconAreaControllerProvider, titanSysUIComponentImpl2.authControllerProvider, titanSysUIComponentImpl2.scrimControllerProvider, daggerTitanGlobalRootComponent6.provideUserManagerProvider, titanSysUIComponentImpl2.mediaDataManagerProvider, titanSysUIComponentImpl2.notificationShadeDepthControllerProvider, titanSysUIComponentImpl2.ambientStateProvider, this.lockIconViewControllerProvider, titanSysUIComponentImpl2.keyguardMediaControllerProvider, titanSysUIComponentImpl2.privacyDotViewControllerProvider, this.tapAgainViewControllerProvider, titanSysUIComponentImpl2.navigationModeControllerProvider, titanSysUIComponentImpl2.fragmentServiceProvider, daggerTitanGlobalRootComponent6.provideContentResolverProvider, titanSysUIComponentImpl2.quickAccessWalletControllerProvider, titanSysUIComponentImpl2.qRCodeScannerControllerProvider, titanSysUIComponentImpl2.recordingControllerProvider, daggerTitanGlobalRootComponent6.provideMainExecutorProvider, titanSysUIComponentImpl2.secureSettingsImplProvider, provider30, titanSysUIComponentImpl2.screenOffAnimationControllerProvider, titanSysUIComponentImpl2.lockscreenGestureLoggerProvider, titanSysUIComponentImpl2.panelExpansionStateManagerProvider, titanSysUIComponentImpl2.provideNotificationRemoteInputManagerProvider, titanSysUIComponentImpl2.provideSysUIUnfoldComponentProvider, titanSysUIComponentImpl2.controlsComponentProvider, daggerTitanGlobalRootComponent6.provideInteractionJankMonitorProvider, titanSysUIComponentImpl2.qsFrameTranslateImplProvider, titanSysUIComponentImpl2.provideSysUiStateProvider, titanSysUIComponentImpl2.keyguardUnlockAnimationControllerProvider));
                this.notificationPanelViewControllerProvider = provider32;
                this.statusBarHeadsUpChangeListenerProvider = DoubleCheck.provider(MediaHierarchyManager_Factory.create$1(titanSysUIComponentImpl2.notificationShadeWindowControllerImplProvider, titanSysUIComponentImpl2.statusBarWindowControllerProvider, provider32, titanSysUIComponentImpl2.keyguardBypassControllerProvider, titanSysUIComponentImpl2.provideHeadsUpManagerPhoneProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, titanSysUIComponentImpl2.provideNotificationRemoteInputManagerProvider, titanSysUIComponentImpl2.provideNotificationsControllerProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.dozeScrimControllerProvider));
                Provider<StatusBarGoogle> provider33 = titanSysUIComponentImpl2.statusBarGoogleProvider;
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = titanSysUIComponentImpl2.this$0;
                this.statusBarCommandQueueCallbacksProvider = DoubleCheck.provider(StatusBarCommandQueueCallbacks_Factory.create(provider33, daggerTitanGlobalRootComponent7.contextProvider, daggerTitanGlobalRootComponent7.provideResourcesProvider, titanSysUIComponentImpl2.shadeControllerImplProvider, titanSysUIComponentImpl2.provideCommandQueueProvider, this.notificationPanelViewControllerProvider, titanSysUIComponentImpl2.setLegacySplitScreenProvider, titanSysUIComponentImpl2.remoteInputQuickSettingsDisablerProvider, titanSysUIComponentImpl2.provideMetricsLoggerProvider, titanSysUIComponentImpl2.keyguardUpdateMonitorProvider, titanSysUIComponentImpl2.keyguardStateControllerImplProvider, titanSysUIComponentImpl2.provideHeadsUpManagerPhoneProvider, titanSysUIComponentImpl2.wakefulnessLifecycleProvider, titanSysUIComponentImpl2.provideDeviceProvisionedControllerProvider, titanSysUIComponentImpl2.statusBarKeyguardViewManagerProvider, titanSysUIComponentImpl2.assistManagerGoogleProvider, titanSysUIComponentImpl2.dozeServiceHostProvider, titanSysUIComponentImpl2.statusBarStateControllerImplProvider, this.providesNotificationShadeWindowViewProvider, this.notificationStackScrollLayoutControllerProvider, titanSysUIComponentImpl2.statusBarHideIconsForBouncerManagerProvider, daggerTitanGlobalRootComponent7.providePowerManagerProvider, titanSysUIComponentImpl2.vibratorHelperProvider, daggerTitanGlobalRootComponent7.provideOptionalVibratorProvider, titanSysUIComponentImpl2.lightBarControllerProvider, titanSysUIComponentImpl2.disableFlagsLoggerProvider, daggerTitanGlobalRootComponent7.provideDisplayIdProvider));
                this.statusBarInitializerProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(titanSysUIComponentImpl2.statusBarWindowControllerProvider, 0));
                this.bindStartableProvider = DoubleCheck.provider(new StatusBarNotifPanelEventSourceModule_BindStartableFactory(this.notificationPanelViewControllerProvider));
            }

            public final CollapsedStatusBarFragment createCollapsedStatusBarFragment() {
                StatusBarFragmentComponentFactory statusBarFragmentComponentFactory = new StatusBarFragmentComponentFactory();
                CommandQueue commandQueue = this.this$1.provideCommandQueueProvider.get();
                return StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory.createCollapsedStatusBarFragment(statusBarFragmentComponentFactory, this.this$1.provideOngoingCallControllerProvider.get(), this.this$1.systemStatusAnimationSchedulerProvider.get(), this.this$1.statusBarLocationPublisherProvider.get(), this.this$1.notificationIconAreaControllerProvider.get(), this.this$1.panelExpansionStateManagerProvider.get(), this.this$1.featureFlagsReleaseProvider.get(), this.this$1.statusBarIconControllerImplProvider.get(), this.this$1.statusBarHideIconsForBouncerManagerProvider.get(), this.this$1.keyguardStateControllerImplProvider.get(), this.notificationPanelViewControllerProvider.get(), this.this$1.networkControllerImplProvider.get(), this.this$1.statusBarStateControllerImplProvider.get(), commandQueue, new CollapsedStatusBarFragmentLogger(this.this$1.provideCollapsedSbFragmentLogBufferProvider.get(), this.this$1.disableFlagsLoggerProvider.get()), new OperatorNameViewController.Factory(this.this$1.darkIconDispatcherImplProvider.get(), this.this$1.networkControllerImplProvider.get(), this.this$1.tunerServiceImplProvider.get(), this.this$1.this$0.provideTelephonyManagerProvider.get(), this.this$1.keyguardUpdateMonitorProvider.get()));
            }

            public final AuthRippleController getAuthRippleController() {
                return this.authRippleControllerProvider.get();
            }

            public final LockIconViewController getLockIconViewController() {
                return this.lockIconViewControllerProvider.get();
            }

            public final NotificationPanelViewController getNotificationPanelViewController() {
                return this.notificationPanelViewControllerProvider.get();
            }

            public final NotificationShadeWindowView getNotificationShadeWindowView() {
                return this.providesNotificationShadeWindowViewProvider.get();
            }

            public final NotificationShadeWindowViewController getNotificationShadeWindowViewController() {
                return new NotificationShadeWindowViewController(this.this$1.lockscreenShadeTransitionControllerProvider.get(), (FalsingCollector) this.this$1.falsingCollectorImplProvider.get(), this.this$1.tunerServiceImplProvider.get(), this.this$1.statusBarStateControllerImplProvider.get(), this.this$1.provideDockManagerProvider.get(), this.this$1.notificationShadeDepthControllerProvider.get(), this.providesNotificationShadeWindowViewProvider.get(), this.notificationPanelViewControllerProvider.get(), this.this$1.panelExpansionStateManagerProvider.get(), this.notificationStackScrollLayoutControllerProvider.get(), this.this$1.statusBarKeyguardViewManagerProvider.get(), this.this$1.statusBarWindowStateControllerProvider.get(), this.lockIconViewControllerProvider.get(), this.this$1.provideLowLightClockControllerProvider.get());
            }

            public final NotificationShelfController getNotificationShelfController() {
                return this.providesStatusBarWindowViewProvider.get();
            }

            public final NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
                return this.notificationStackScrollLayoutControllerProvider.get();
            }

            public final Set<StatusBarComponent.Startable> getStartables() {
                return Collections.singleton(this.bindStartableProvider.get());
            }

            public final StatusBarCommandQueueCallbacks getStatusBarCommandQueueCallbacks() {
                return this.statusBarCommandQueueCallbacksProvider.get();
            }

            public final StatusBarHeadsUpChangeListener getStatusBarHeadsUpChangeListener() {
                return this.statusBarHeadsUpChangeListenerProvider.get();
            }

            public final StatusBarInitializer getStatusBarInitializer() {
                return this.statusBarInitializerProvider.get();
            }
        }

        public final class SysUIUnfoldComponentFactory implements SysUIUnfoldComponent.Factory {
            public SysUIUnfoldComponentFactory() {
            }

            public final SysUIUnfoldComponent create(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                return new SysUIUnfoldComponentImpl(TitanSysUIComponentImpl.this, unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider);
            }
        }

        public final class SysUIUnfoldComponentImpl implements SysUIUnfoldComponent {
            public InstanceFactory arg0Provider;
            public InstanceFactory arg1Provider;
            public InstanceFactory arg2Provider;
            public Provider<FoldAodAnimationController> foldAodAnimationControllerProvider;
            public Provider<KeyguardUnfoldTransition> keyguardUnfoldTransitionProvider;
            public Provider<NotificationPanelUnfoldAnimationController> notificationPanelUnfoldAnimationControllerProvider;
            public Provider<StatusBarMoveFromCenterAnimationController> statusBarMoveFromCenterAnimationControllerProvider;
            public Provider<UnfoldLightRevealOverlayAnimation> unfoldLightRevealOverlayAnimationProvider;
            public Provider<UnfoldTransitionWallpaperController> unfoldTransitionWallpaperControllerProvider;

            public final FoldAodAnimationController getFoldAodAnimationController() {
                return this.foldAodAnimationControllerProvider.get();
            }

            public final KeyguardUnfoldTransition getKeyguardUnfoldTransition() {
                return this.keyguardUnfoldTransitionProvider.get();
            }

            public final NotificationPanelUnfoldAnimationController getNotificationPanelUnfoldAnimationController() {
                return this.notificationPanelUnfoldAnimationControllerProvider.get();
            }

            public final StatusBarMoveFromCenterAnimationController getStatusBarMoveFromCenterAnimationController() {
                return this.statusBarMoveFromCenterAnimationControllerProvider.get();
            }

            public final UnfoldLightRevealOverlayAnimation getUnfoldLightRevealOverlayAnimation() {
                return this.unfoldLightRevealOverlayAnimationProvider.get();
            }

            public final UnfoldTransitionWallpaperController getUnfoldTransitionWallpaperController() {
                return this.unfoldTransitionWallpaperControllerProvider.get();
            }

            public SysUIUnfoldComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                InstanceFactory create = InstanceFactory.create(naturalRotationUnfoldProgressProvider);
                this.arg1Provider = create;
                this.keyguardUnfoldTransitionProvider = DoubleCheck.provider(new KeyguardUnfoldTransition_Factory(titanSysUIComponentImpl.this$0.contextProvider, create, 0));
                InstanceFactory create2 = InstanceFactory.create(scopedUnfoldTransitionProgressProvider);
                this.arg2Provider = create2;
                this.statusBarMoveFromCenterAnimationControllerProvider = DoubleCheck.provider(new QSFragmentModule_ProvidesQSFooterActionsViewFactory(create2, titanSysUIComponentImpl.this$0.provideWindowManagerProvider, 2));
                this.notificationPanelUnfoldAnimationControllerProvider = DoubleCheck.provider(new NotificationPanelUnfoldAnimationController_Factory((Provider) titanSysUIComponentImpl.this$0.contextProvider, (Provider) this.arg1Provider));
                this.foldAodAnimationControllerProvider = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(titanSysUIComponentImpl.this$0.provideMainHandlerProvider, titanSysUIComponentImpl.wakefulnessLifecycleProvider, titanSysUIComponentImpl.globalSettingsImplProvider, 2));
                InstanceFactory create3 = InstanceFactory.create(unfoldTransitionProgressProvider);
                this.arg0Provider = create3;
                this.unfoldTransitionWallpaperControllerProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(create3, titanSysUIComponentImpl.wallpaperControllerProvider, 2));
                DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = titanSysUIComponentImpl.this$0;
                this.unfoldLightRevealOverlayAnimationProvider = DoubleCheck.provider(UnfoldLightRevealOverlayAnimation_Factory.create(daggerTitanGlobalRootComponent.contextProvider, daggerTitanGlobalRootComponent.provideDeviceStateManagerProvider, daggerTitanGlobalRootComponent.provideDisplayManagerProvider, this.arg0Provider, titanSysUIComponentImpl.setDisplayAreaHelperProvider, daggerTitanGlobalRootComponent.provideMainExecutorProvider, titanSysUIComponentImpl.provideUiBackgroundExecutorProvider, daggerTitanGlobalRootComponent.provideIWindowManagerProvider));
            }
        }

        public final /* bridge */ /* synthetic */ void init() {
            super.init();
        }

        public final void inject(SystemUIAppComponentFactory systemUIAppComponentFactory) {
            injectSystemUIAppComponentFactory(systemUIAppComponentFactory);
        }

        public final class DreamClockDateComplicationComponentImpl {
            public Provider<View> provideComplicationViewProvider;
            public Provider<ComplicationLayoutParams> provideLayoutParamsProvider = DoubleCheck.provider(C0796x871f559c.InstanceHolder.INSTANCE);

            public DreamClockDateComplicationComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl) {
                this.provideComplicationViewProvider = DoubleCheck.provider(new C0795x28793289(titanSysUIComponentImpl.providerLayoutInflaterProvider));
            }
        }

        public final class DreamClockTimeComplicationComponentImpl {
            public Provider<View> provideComplicationViewProvider;
            public Provider<ComplicationLayoutParams> provideLayoutParamsProvider = DoubleCheck.provider(C0798x4215cf9e.InstanceHolder.INSTANCE);

            public DreamClockTimeComplicationComponentImpl(TitanSysUIComponentImpl titanSysUIComponentImpl) {
                this.provideComplicationViewProvider = DoubleCheck.provider(new C0797x2fbedb8b(titanSysUIComponentImpl.providerLayoutInflaterProvider));
            }
        }

        public TitanSysUIComponentImpl(DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent, DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            this.this$0 = daggerTitanGlobalRootComponent;
            initialize(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize2(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize3(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize4(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize5(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize6(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize7(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize8(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize9(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
        }

        public final ActivityStarter activityStarter() {
            ActivityStarterDelegate activityStarterDelegate = this.activityStarterDelegateProvider.get();
            this.this$0.pluginDependencyProvider.get().allowPluginDependency(ActivityStarter.class, activityStarterDelegate);
            Objects.requireNonNull(activityStarterDelegate, "Cannot return null from a non-@Nullable @Provides method");
            return activityStarterDelegate;
        }

        public final BouncerSwipeTouchHandler bouncerSwipeTouchHandler() {
            return new BouncerSwipeTouchHandler(this.statusBarKeyguardViewManagerProvider.get(), this.statusBarGoogleProvider.get(), this.notificationShadeWindowControllerImplProvider.get(), namedFlingAnimationUtils(), namedFlingAnimationUtils2(), namedFloat());
        }

        public final Dependency createDependency() {
            return this.dependencyProvider2.get();
        }

        public final DumpManager createDumpManager() {
            return this.this$0.dumpManagerProvider.get();
        }

        public final KeyguardSmartspaceController createKeyguardSmartspaceController() {
            return this.keyguardSmartspaceControllerProvider.get();
        }

        public final ConfigurationController getConfigurationController() {
            return this.provideConfigurationControllerProvider.get();
        }

        public final ContextComponentHelper getContextComponentHelper() {
            return this.contextComponentResolverProvider.get();
        }

        public final Optional<FoldStateLogger> getFoldStateLogger() {
            return this.this$0.providesFoldStateLoggerProvider.get();
        }

        public final Optional<FoldStateLoggingProvider> getFoldStateLoggingProvider() {
            return this.this$0.providesFoldStateLoggingProvider.get();
        }

        public final InitController getInitController() {
            return this.initControllerProvider.get();
        }

        public final Optional<MediaMuteAwaitConnectionCli> getMediaMuteAwaitConnectionCli() {
            return this.providesMediaMuteAwaitConnectionCliProvider.get();
        }

        public final Optional<MediaTttChipControllerReceiver> getMediaTttChipControllerReceiver() {
            return this.providesMediaTttChipControllerReceiverProvider.get();
        }

        public final Optional<MediaTttChipControllerSender> getMediaTttChipControllerSender() {
            return this.providesMediaTttChipControllerSenderProvider.get();
        }

        public final Optional<MediaTttCommandLineHelper> getMediaTttCommandLineHelper() {
            return this.providesMediaTttCommandLineHelperProvider.get();
        }

        public final Optional<NaturalRotationUnfoldProgressProvider> getNaturalRotationUnfoldProgressProvider() {
            return this.this$0.provideNaturalRotationProgressProvider.get();
        }

        public final Optional<NearbyMediaDevicesManager> getNearbyMediaDevicesManager() {
            return this.providesNearbyMediaDevicesManagerProvider.get();
        }

        public final Map<Class<?>, Provider<CoreStartable>> getPerUserStartables() {
            return Collections.singletonMap(NotificationChannels.class, this.notificationChannelsProvider);
        }

        public final Map<Class<?>, Provider<CoreStartable>> getStartables() {
            LinkedHashMap newLinkedHashMapWithExpectedSize = R$id.newLinkedHashMapWithExpectedSize(34);
            newLinkedHashMapWithExpectedSize.put(AuthController.class, this.authControllerProvider);
            newLinkedHashMapWithExpectedSize.put(ClipboardListener.class, this.clipboardListenerProvider);
            newLinkedHashMapWithExpectedSize.put(GarbageMonitor.class, this.serviceProvider);
            newLinkedHashMapWithExpectedSize.put(GlobalActionsComponent.class, this.globalActionsComponentProvider);
            newLinkedHashMapWithExpectedSize.put(InstantAppNotifier.class, this.instantAppNotifierProvider);
            newLinkedHashMapWithExpectedSize.put(KeyboardUI.class, this.keyboardUIProvider);
            newLinkedHashMapWithExpectedSize.put(KeyguardBiometricLockoutLogger.class, this.keyguardBiometricLockoutLoggerProvider);
            newLinkedHashMapWithExpectedSize.put(KeyguardViewMediator.class, this.newKeyguardViewMediatorProvider);
            newLinkedHashMapWithExpectedSize.put(LatencyTester.class, this.latencyTesterProvider);
            newLinkedHashMapWithExpectedSize.put(PowerUI.class, this.powerUIProvider);
            newLinkedHashMapWithExpectedSize.put(Recents.class, this.provideRecentsProvider);
            newLinkedHashMapWithExpectedSize.put(RingtonePlayer.class, this.ringtonePlayerProvider);
            newLinkedHashMapWithExpectedSize.put(ScreenDecorations.class, this.screenDecorationsProvider);
            newLinkedHashMapWithExpectedSize.put(SessionTracker.class, this.sessionTrackerProvider);
            newLinkedHashMapWithExpectedSize.put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider);
            newLinkedHashMapWithExpectedSize.put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider);
            newLinkedHashMapWithExpectedSize.put(StorageNotification.class, this.storageNotificationProvider);
            newLinkedHashMapWithExpectedSize.put(SystemActions.class, this.systemActionsProvider);
            newLinkedHashMapWithExpectedSize.put(ThemeOverlayController.class, this.themeOverlayControllerProvider);
            newLinkedHashMapWithExpectedSize.put(ToastUI.class, this.toastUIProvider);
            newLinkedHashMapWithExpectedSize.put(VolumeUI.class, this.volumeUIProvider);
            newLinkedHashMapWithExpectedSize.put(WindowMagnification.class, this.windowMagnificationProvider);
            newLinkedHashMapWithExpectedSize.put(WMShell.class, this.wMShellProvider);
            newLinkedHashMapWithExpectedSize.put(GoogleServices.class, this.googleServicesProvider);
            newLinkedHashMapWithExpectedSize.put(StatusBar.class, this.statusBarGoogleProvider);
            newLinkedHashMapWithExpectedSize.put(DockMonitor.class, this.dockMonitorProvider);
            newLinkedHashMapWithExpectedSize.put(DockEventSimulator.class, this.dockEventSimulatorProvider);
            newLinkedHashMapWithExpectedSize.put(DreamClockTimeComplication.Registrant.class, this.registrantProvider);
            newLinkedHashMapWithExpectedSize.put(DreamClockDateComplication.Registrant.class, this.registrantProvider2);
            newLinkedHashMapWithExpectedSize.put(DreamOverlayRegistrant.class, this.dreamOverlayRegistrantProvider);
            newLinkedHashMapWithExpectedSize.put(DreamWeatherComplication.Registrant.class, this.registrantProvider3);
            newLinkedHashMapWithExpectedSize.put(MediaDreamSentinel.class, this.mediaDreamSentinelProvider);
            newLinkedHashMapWithExpectedSize.put(SmartSpaceComplication.Registrant.class, this.registrantProvider4);
            newLinkedHashMapWithExpectedSize.put(ComplicationTypesUpdater.class, this.complicationTypesUpdaterProvider);
            if (newLinkedHashMapWithExpectedSize.isEmpty()) {
                return Collections.emptyMap();
            }
            return Collections.unmodifiableMap(newLinkedHashMapWithExpectedSize);
        }

        public final Optional<SysUIUnfoldComponent> getSysUIUnfoldComponent() {
            return this.provideSysUIUnfoldComponentProvider.get();
        }

        public final UnfoldLatencyTracker getUnfoldLatencyTracker() {
            return this.unfoldLatencyTrackerProvider.get();
        }

        public final void initialize(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            this.bootCompleteCacheImplProvider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(this.this$0.dumpManagerProvider, 0));
            this.provideConfigurationControllerProvider = DoubleCheck.provider(new ResumeMediaBrowserFactory_Factory(dependencyProvider3, this.this$0.contextProvider));
            Provider<Looper> provider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBgLooperFactory.InstanceHolder.INSTANCE);
            this.provideBgLooperProvider = provider;
            this.provideBackgroundExecutorProvider = DoubleCheck.provider(new PowerSaveState_Factory(provider, 4));
            this.provideExecutorProvider = C0773x82ed519e.m54m(this.provideBgLooperProvider, 6);
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(this.provideBgLooperProvider, 6);
            this.provideBgHandlerProvider = smartActionsReceiver_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            Provider<UserTracker> provider2 = DoubleCheck.provider(new SettingsModule_ProvideUserTrackerFactory(daggerTitanGlobalRootComponent.contextProvider, daggerTitanGlobalRootComponent.provideUserManagerProvider, daggerTitanGlobalRootComponent.dumpManagerProvider, smartActionsReceiver_Factory));
            this.provideUserTrackerProvider = provider2;
            this.controlsListingControllerImplProvider = DoubleCheck.provider(new TouchInsideHandler_Factory(this.this$0.contextProvider, this.provideExecutorProvider, provider2, 1));
            this.provideBackgroundDelayableExecutorProvider = DoubleCheck.provider(new ForegroundServicesDialog_Factory(this.provideBgLooperProvider, 5));
            this.controlsControllerImplProvider = new DelegateFactory();
            this.provideSharePreferencesProvider = new KeyguardLifecyclesDispatcher_Factory(dependencyProvider3, this.this$0.contextProvider);
            this.provideDelayableExecutorProvider = DoubleCheck.provider(new ActionClickLogger_Factory(this.provideBgLooperProvider, 3));
            Provider<ContentResolver> provider3 = this.this$0.provideContentResolverProvider;
            GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory = GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE;
            Provider<LogcatEchoTracker> provider4 = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(provider3, globalConcurrencyModule_ProvideMainLooperFactory, 2));
            this.provideLogcatEchoTrackerProvider = provider4;
            Provider<LogBufferFactory> provider5 = DoubleCheck.provider(new LogBufferFactory_Factory(this.this$0.dumpManagerProvider, provider4, 0));
            this.logBufferFactoryProvider = provider5;
            Provider<LogBuffer> provider6 = DoubleCheck.provider(new ForegroundServicesDialog_Factory(provider5, 3));
            this.provideBroadcastDispatcherLogBufferProvider = provider6;
            WallpaperController_Factory wallpaperController_Factory = new WallpaperController_Factory(provider6, 1);
            this.broadcastDispatcherLoggerProvider = wallpaperController_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            this.providesBroadcastDispatcherProvider = DoubleCheck.provider(TvSystemUIModule_ProvideBatteryControllerFactory.create(dependencyProvider, daggerTitanGlobalRootComponent2.contextProvider, this.provideBgLooperProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent2.dumpManagerProvider, wallpaperController_Factory, this.provideUserTrackerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.statusBarStateControllerImplProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, daggerTitanGlobalRootComponent3.dumpManagerProvider, daggerTitanGlobalRootComponent3.provideInteractionJankMonitorProvider, 1));
            this.provideLockPatternUtilsProvider = DoubleCheck.provider(new OpaLockscreen_Factory(dependencyProvider3, (Provider) this.this$0.contextProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            this.protoTracerProvider = DoubleCheck.provider(new ProtoTracer_Factory(daggerTitanGlobalRootComponent4.contextProvider, daggerTitanGlobalRootComponent4.dumpManagerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            Provider<CommandRegistry> provider7 = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(daggerTitanGlobalRootComponent5.contextProvider, daggerTitanGlobalRootComponent5.provideMainExecutorProvider, 1));
            this.commandRegistryProvider = provider7;
            this.provideCommandQueueProvider = DoubleCheck.provider(new DozeLog_Factory(this.this$0.contextProvider, this.protoTracerProvider, provider7, 1));
            this.providerLayoutInflaterProvider = DoubleCheck.provider(new DependencyProvider_ProviderLayoutInflaterFactory(dependencyProvider3, this.this$0.contextProvider));
            this.panelExpansionStateManagerProvider = DoubleCheck.provider(PanelExpansionStateManager_Factory.InstanceHolder.INSTANCE);
            this.enhancedEstimatesGoogleImplProvider = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.this$0.contextProvider, 0));
            ActivityIntentHelper_Factory activityIntentHelper_Factory = new ActivityIntentHelper_Factory(this.this$0.provideContentResolverProvider, 5);
            this.globalSettingsImplProvider = activityIntentHelper_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            this.provideDemoModeControllerProvider = DoubleCheck.provider(new LaunchOverview_Factory(daggerTitanGlobalRootComponent6.contextProvider, daggerTitanGlobalRootComponent6.dumpManagerProvider, activityIntentHelper_Factory, 1));
            this.provideReverseWirelessChargerProvider = C0771xb6bb24d9.m52m(this.this$0.contextProvider, 9);
            this.provideUsbManagerProvider = DoubleCheck.provider(new ActionClickLogger_Factory(this.this$0.contextProvider, 4));
            Provider<IThermalService> provider8 = DoubleCheck.provider(SystemUIGoogleModule_ProvideIThermalServiceFactory.InstanceHolder.INSTANCE);
            this.provideIThermalServiceProvider = provider8;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            Provider<ReverseChargingController> provider9 = DoubleCheck.provider(new BluetoothTile_Factory(daggerTitanGlobalRootComponent7.contextProvider, this.providesBroadcastDispatcherProvider, this.provideReverseWirelessChargerProvider, daggerTitanGlobalRootComponent7.provideAlarmManagerProvider, this.provideUsbManagerProvider, daggerTitanGlobalRootComponent7.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.bootCompleteCacheImplProvider, provider8, 1));
            this.reverseChargingControllerProvider = provider9;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            this.provideBatteryControllerProvider = DoubleCheck.provider(new OverlayToggleTile_Factory(daggerTitanGlobalRootComponent8.contextProvider, this.enhancedEstimatesGoogleImplProvider, daggerTitanGlobalRootComponent8.providePowerManagerProvider, this.providesBroadcastDispatcherProvider, this.provideDemoModeControllerProvider, daggerTitanGlobalRootComponent8.provideMainHandlerProvider, this.provideBgHandlerProvider, this.provideUserTrackerProvider, provider9, 2));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            Provider<Context> provider10 = daggerTitanGlobalRootComponent9.contextProvider;
            this.provideAmbientDisplayConfigurationProvider = new DistanceClassifier_Factory(dependencyProvider3, provider10);
            this.debugModeFilterProvider = DoubleCheck.provider(new MediaModule_ProvidesMediaTttChipControllerSenderFactory(provider10, daggerTitanGlobalRootComponent9.dumpManagerProvider, 1));
            this.notifLiveDataStoreImplProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.this$0.provideMainExecutorProvider, 2));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            Provider<FeatureFlagsRelease> provider11 = DoubleCheck.provider(new FeatureFlagsRelease_Factory(daggerTitanGlobalRootComponent10.provideResourcesProvider, daggerTitanGlobalRootComponent10.dumpManagerProvider, 0));
            this.featureFlagsReleaseProvider = provider11;
            this.notifPipelineFlagsProvider = new VolumeUI_Factory(this.this$0.contextProvider, provider11, 1);
            this.bindSystemClockProvider = DoubleCheck.provider(SystemClockImpl_Factory.InstanceHolder.INSTANCE);
            Provider<LogBuffer> provider12 = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(this.logBufferFactoryProvider, 3));
            this.provideNotificationsLogBufferProvider = provider12;
            this.notifCollectionLoggerProvider = new PrivacyLogger_Factory(provider12, 4);
            Provider<Files> provider13 = DoubleCheck.provider(Files_Factory.InstanceHolder.INSTANCE);
            this.filesProvider = provider13;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            Provider<LogBufferEulogizer> provider14 = DoubleCheck.provider(new LogBufferEulogizer_Factory(daggerTitanGlobalRootComponent11.contextProvider, daggerTitanGlobalRootComponent11.dumpManagerProvider, this.bindSystemClockProvider, provider13, 0));
            this.logBufferEulogizerProvider = provider14;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            this.notifCollectionProvider = DoubleCheck.provider(NotifCollection_Factory.create(daggerTitanGlobalRootComponent12.provideIStatusBarServiceProvider, this.bindSystemClockProvider, this.notifPipelineFlagsProvider, this.notifCollectionLoggerProvider, daggerTitanGlobalRootComponent12.provideMainHandlerProvider, provider14, daggerTitanGlobalRootComponent12.dumpManagerProvider));
            Provider<Choreographer> provider15 = DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(dependencyProvider3, 0));
            this.providesChoreographerProvider = provider15;
            this.notifPipelineChoreographerImplProvider = DoubleCheck.provider(new RotationPolicyWrapperImpl_Factory(provider15, this.this$0.provideMainDelayableExecutorProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            this.notificationClickNotifierProvider = DoubleCheck.provider(new LogBufferFactory_Factory(daggerTitanGlobalRootComponent13.provideIStatusBarServiceProvider, daggerTitanGlobalRootComponent13.provideMainExecutorProvider, 1));
            this.notificationEntryManagerLoggerProvider = new WMShellBaseModule_ProvideRecentTasksFactory(this.provideNotificationsLogBufferProvider, 2);
            Provider<LeakDetector> provider16 = DoubleCheck.provider(new DependencyProvider_ProvideLeakDetectorFactory(dependencyProvider3, (Provider) this.this$0.dumpManagerProvider));
            this.provideLeakDetectorProvider = provider16;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            Provider<TunerServiceImpl> provider17 = DoubleCheck.provider(new TunerServiceImpl_Factory(daggerTitanGlobalRootComponent14.contextProvider, daggerTitanGlobalRootComponent14.provideMainHandlerProvider, provider16, this.provideDemoModeControllerProvider, this.provideUserTrackerProvider));
            this.tunerServiceImplProvider = provider17;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            Provider<ExtensionControllerImpl> provider18 = DoubleCheck.provider(new ExtensionControllerImpl_Factory(daggerTitanGlobalRootComponent15.contextProvider, this.provideLeakDetectorProvider, daggerTitanGlobalRootComponent15.providesPluginManagerProvider, provider17, this.provideConfigurationControllerProvider));
            this.extensionControllerImplProvider = provider18;
            this.notificationPersonExtractorPluginBoundaryProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(provider18, 3));
            DelegateFactory delegateFactory = new DelegateFactory();
            this.notificationGroupManagerLegacyProvider = delegateFactory;
            Provider<GroupMembershipManager> provider19 = DoubleCheck.provider(new WMShellModule_ProvideUnfoldBackgroundControllerFactory(this.notifPipelineFlagsProvider, delegateFactory, 1));
            this.provideGroupMembershipManagerProvider = provider19;
            this.peopleNotificationIdentifierImplProvider = DoubleCheck.provider(new RotationPolicyWrapperImpl_Factory(this.notificationPersonExtractorPluginBoundaryProvider, provider19, 3));
            InstanceFactory create = InstanceFactory.create(optional6);
            this.setBubblesProvider = create;
            DelegateFactory.setDelegate(this.notificationGroupManagerLegacyProvider, DoubleCheck.provider(new NotificationGroupManagerLegacy_Factory(this.statusBarStateControllerImplProvider, this.peopleNotificationIdentifierImplProvider, create, this.this$0.dumpManagerProvider)));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            this.provideNotificationMessagingUtilProvider = new SliceBroadcastRelayHandler_Factory(dependencyProvider3, daggerTitanGlobalRootComponent16.contextProvider);
            this.notificationLockscreenUserManagerGoogleProvider = new DelegateFactory();
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.provideNotificationVisibilityProvider = delegateFactory2;
            this.provideSmartReplyControllerProvider = DoubleCheck.provider(new StatusBarDependenciesModule_ProvideSmartReplyControllerFactory(daggerTitanGlobalRootComponent16.dumpManagerProvider, delegateFactory2, daggerTitanGlobalRootComponent16.provideIStatusBarServiceProvider, this.notificationClickNotifierProvider));
            this.provideNotificationEntryManagerProvider = new DelegateFactory();
            this.remoteInputNotificationRebuilderProvider = DoubleCheck.provider(new PrivacyLogger_Factory(this.this$0.contextProvider, 3));
            this.optionalOfStatusBarProvider = new DelegateFactory();
            this.provideHandlerProvider = new DependencyProvider_ProvideHandlerFactory(dependencyProvider3, 0);
            this.remoteInputUriControllerProvider = DoubleCheck.provider(new TimeoutManager_Factory(this.this$0.provideIStatusBarServiceProvider, 4));
            Provider<LogBuffer> m = C0773x82ed519e.m54m(this.logBufferFactoryProvider, 3);
            this.provideNotifInteractionLogBufferProvider = m;
            this.actionClickLoggerProvider = new ActionClickLogger_Factory(m, 0);
            this.provideNotificationRemoteInputManagerProvider = DoubleCheck.provider(C1209xfa996c5e.create(this.this$0.contextProvider, this.notifPipelineFlagsProvider, this.notificationLockscreenUserManagerGoogleProvider, this.provideSmartReplyControllerProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.remoteInputNotificationRebuilderProvider, this.optionalOfStatusBarProvider, this.statusBarStateControllerImplProvider, this.provideHandlerProvider, this.remoteInputUriControllerProvider, this.notificationClickNotifierProvider, this.actionClickLoggerProvider, this.this$0.dumpManagerProvider));
            this.provideCommonNotifCollectionProvider = new DelegateFactory();
            DateFormatUtil_Factory dateFormatUtil_Factory = new DateFormatUtil_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.notifBindPipelineLoggerProvider = dateFormatUtil_Factory;
            this.notifBindPipelineProvider = DoubleCheck.provider(new DozeWallpaperState_Factory(this.provideCommonNotifCollectionProvider, dateFormatUtil_Factory, globalConcurrencyModule_ProvideMainLooperFactory, 1));
            PeopleSpaceWidgetProvider_Factory peopleSpaceWidgetProvider_Factory = new PeopleSpaceWidgetProvider_Factory(this.provideCommonNotifCollectionProvider, 4);
            this.notifRemoteViewCacheImplProvider = peopleSpaceWidgetProvider_Factory;
            this.provideNotifRemoteViewCacheProvider = DoubleCheck.provider(peopleSpaceWidgetProvider_Factory);
            Provider<BindEventManagerImpl> provider20 = DoubleCheck.provider(BindEventManagerImpl_Factory.InstanceHolder.INSTANCE);
            this.bindEventManagerImplProvider = provider20;
            Provider<NotificationGroupManagerLegacy> provider21 = this.notificationGroupManagerLegacyProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            Provider<ConversationNotificationManager> provider22 = DoubleCheck.provider(ConversationNotificationManager_Factory.create(provider20, provider21, daggerTitanGlobalRootComponent17.contextProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineFlagsProvider, daggerTitanGlobalRootComponent17.provideMainHandlerProvider));
            this.conversationNotificationManagerProvider = provider22;
            this.conversationNotificationProcessorProvider = new KeyguardLifecyclesDispatcher_Factory(this.this$0.provideLauncherAppsProvider, provider22, 3);
            this.mediaFeatureFlagProvider = new QSLogger_Factory(this.this$0.contextProvider, 2);
            Provider<DeviceConfigProxy> provider23 = DoubleCheck.provider(DeviceConfigProxy_Factory.InstanceHolder.INSTANCE);
            this.deviceConfigProxyProvider = provider23;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            this.smartReplyConstantsProvider = DoubleCheck.provider(new LeakReporter_Factory(daggerTitanGlobalRootComponent18.provideMainHandlerProvider, daggerTitanGlobalRootComponent18.contextProvider, provider23, 1));
            DependencyProvider dependencyProvider4 = dependencyProvider;
            this.provideActivityManagerWrapperProvider = DoubleCheck.provider(new ActionClickLogger_Factory(dependencyProvider4, 5));
            this.provideDevicePolicyManagerWrapperProvider = DoubleCheck.provider(new KeyboardUI_Factory(dependencyProvider4, 9));
            Provider<KeyguardDismissUtil> provider24 = DoubleCheck.provider(KeyguardDismissUtil_Factory.InstanceHolder.INSTANCE);
            this.keyguardDismissUtilProvider = provider24;
            this.smartReplyInflaterImplProvider = DefaultUiController_Factory.create$2(this.smartReplyConstantsProvider, provider24, this.provideNotificationRemoteInputManagerProvider, this.provideSmartReplyControllerProvider, this.this$0.contextProvider);
            this.provideActivityStarterProvider = new DelegateFactory();
            Provider<LogBuffer> m2 = C0772x82ed519d.m53m(this.logBufferFactoryProvider, 3);
            this.provideNotificationHeadsUpLogBufferProvider = m2;
            this.headsUpManagerLoggerProvider = new ImageExporter_Factory(m2, 5);
            this.keyguardUpdateMonitorProvider = new DelegateFactory();
            this.keyguardStateControllerImplProvider = new DelegateFactory();
            this.newKeyguardViewMediatorProvider = new DelegateFactory();
            this.statusBarKeyguardViewManagerProvider = new DelegateFactory();
            this.provideAlwaysOnDisplayPolicyProvider = DoubleCheck.provider(new DiagonalClassifier_Factory(dependencyProvider4, this.this$0.contextProvider));
            this.sysUIUnfoldComponentFactoryProvider = new Provider<SysUIUnfoldComponent.Factory>() {
                public final Object get() {
                    return new SysUIUnfoldComponentFactory();
                }
            };
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            this.provideSysUIUnfoldComponentProvider = DoubleCheck.provider(new SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(sysUIUnfoldModule, daggerTitanGlobalRootComponent19.unfoldTransitionProgressProvider, daggerTitanGlobalRootComponent19.provideNaturalRotationProgressProvider, daggerTitanGlobalRootComponent19.provideStatusBarScopedTransitionProvider, this.sysUIUnfoldComponentFactoryProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            this.wakefulnessLifecycleProvider = DoubleCheck.provider(new WakefulnessLifecycle_Factory(daggerTitanGlobalRootComponent20.contextProvider, daggerTitanGlobalRootComponent20.dumpManagerProvider));
            this.dozeParametersProvider = new DelegateFactory();
            Provider<Context> provider25 = this.this$0.contextProvider;
            Provider<WakefulnessLifecycle> provider26 = this.wakefulnessLifecycleProvider;
            Provider<StatusBarStateControllerImpl> provider27 = this.statusBarStateControllerImplProvider;
            Provider<KeyguardViewMediator> provider28 = this.newKeyguardViewMediatorProvider;
            Provider<KeyguardStateControllerImpl> provider29 = this.keyguardStateControllerImplProvider;
            Provider<DozeParameters> provider30 = this.dozeParametersProvider;
            Provider provider31 = this.globalSettingsImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            Provider<UnlockedScreenOffAnimationController> provider32 = DoubleCheck.provider(UnlockedScreenOffAnimationController_Factory.create(provider25, provider26, provider27, provider28, provider29, provider30, provider31, daggerTitanGlobalRootComponent21.provideInteractionJankMonitorProvider, daggerTitanGlobalRootComponent21.providePowerManagerProvider, this.provideHandlerProvider));
            this.unlockedScreenOffAnimationControllerProvider = provider32;
            this.screenOffAnimationControllerProvider = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(this.provideSysUIUnfoldComponentProvider, provider32, this.wakefulnessLifecycleProvider, 1));
            DelegateFactory.setDelegate(this.dozeParametersProvider, DoubleCheck.provider(DozeParameters_Factory.create(this.this$0.provideResourcesProvider, this.provideAmbientDisplayConfigurationProvider, this.provideAlwaysOnDisplayPolicyProvider, this.this$0.providePowerManagerProvider, this.provideBatteryControllerProvider, this.tunerServiceImplProvider, this.this$0.dumpManagerProvider, this.featureFlagsReleaseProvider, this.screenOffAnimationControllerProvider, this.provideSysUIUnfoldComponentProvider, this.unlockedScreenOffAnimationControllerProvider, this.keyguardUpdateMonitorProvider, this.provideConfigurationControllerProvider, this.statusBarStateControllerImplProvider)));
            Provider<LogBuffer> m3 = C0771xb6bb24d9.m52m(this.logBufferFactoryProvider, 3);
            this.provideDozeLogBufferProvider = m3;
            this.dozeLoggerProvider = new DozeLogger_Factory(m3, 0);
        }

        public final void initialize2(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            Provider<DozeLog> provider = DoubleCheck.provider(new DozeLog_Factory(this.keyguardUpdateMonitorProvider, this.this$0.dumpManagerProvider, this.dozeLoggerProvider, 0));
            this.dozeLogProvider = provider;
            this.dozeScrimControllerProvider = DoubleCheck.provider(new SetupWizard_Factory(this.dozeParametersProvider, provider, this.statusBarStateControllerImplProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            this.darkIconDispatcherImplProvider = DoubleCheck.provider(new DarkIconDispatcherImpl_Factory(daggerTitanGlobalRootComponent.contextProvider, this.provideCommandQueueProvider, daggerTitanGlobalRootComponent.dumpManagerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            SecureSettingsImpl_Factory secureSettingsImpl_Factory = new SecureSettingsImpl_Factory(daggerTitanGlobalRootComponent2.provideContentResolverProvider, 0);
            this.secureSettingsImplProvider = secureSettingsImpl_Factory;
            Provider<DeviceProvisionedControllerImpl> provider2 = DoubleCheck.provider(DeviceProvisionedControllerImpl_Factory.create(secureSettingsImpl_Factory, this.globalSettingsImplProvider, this.provideUserTrackerProvider, daggerTitanGlobalRootComponent2.dumpManagerProvider, this.provideBgHandlerProvider, daggerTitanGlobalRootComponent2.provideMainExecutorProvider));
            this.deviceProvisionedControllerImplProvider = provider2;
            this.provideDeviceProvisionedControllerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksFactory(provider2, 5));
            Provider<Executor> provider3 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideUiBackgroundExecutorFactory.InstanceHolder.INSTANCE);
            this.provideUiBackgroundExecutorProvider = provider3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            Provider<NavigationModeController> provider4 = DoubleCheck.provider(NavigationModeController_Factory.create$1(daggerTitanGlobalRootComponent3.contextProvider, this.provideDeviceProvisionedControllerProvider, this.provideConfigurationControllerProvider, provider3, daggerTitanGlobalRootComponent3.dumpManagerProvider));
            this.navigationModeControllerProvider = provider4;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            Provider<LightBarController> provider5 = DoubleCheck.provider(new C2508LightBarController_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.darkIconDispatcherImplProvider, this.provideBatteryControllerProvider, provider4, daggerTitanGlobalRootComponent4.dumpManagerProvider));
            this.lightBarControllerProvider = provider5;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            DelayedWakeLock_Builder_Factory delayedWakeLock_Builder_Factory = new DelayedWakeLock_Builder_Factory(daggerTitanGlobalRootComponent5.contextProvider);
            this.builderProvider = delayedWakeLock_Builder_Factory;
            DelegateFactory delegateFactory = new DelegateFactory();
            this.provideDockManagerProvider = delegateFactory;
            this.scrimControllerProvider = DoubleCheck.provider(ScrimController_Factory.create(provider5, this.dozeParametersProvider, daggerTitanGlobalRootComponent5.provideAlarmManagerProvider, this.keyguardStateControllerImplProvider, delayedWakeLock_Builder_Factory, this.provideHandlerProvider, this.keyguardUpdateMonitorProvider, delegateFactory, this.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent5.provideMainExecutorProvider, this.screenOffAnimationControllerProvider, this.panelExpansionStateManagerProvider));
            this.keyguardBypassControllerProvider = new DelegateFactory();
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            Provider<SysuiColorExtractor> provider6 = DoubleCheck.provider(new SysuiColorExtractor_Factory(daggerTitanGlobalRootComponent6.contextProvider, this.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent6.dumpManagerProvider, 0));
            this.sysuiColorExtractorProvider = provider6;
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.authControllerProvider = delegateFactory2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            this.notificationShadeWindowControllerImplProvider = DoubleCheck.provider(NotificationShadeWindowControllerImpl_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, daggerTitanGlobalRootComponent7.provideWindowManagerProvider, daggerTitanGlobalRootComponent7.provideIActivityManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.newKeyguardViewMediatorProvider, this.keyguardBypassControllerProvider, provider6, daggerTitanGlobalRootComponent7.dumpManagerProvider, this.keyguardStateControllerImplProvider, this.screenOffAnimationControllerProvider, delegateFactory2));
            this.provideAssistUtilsProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.this$0.contextProvider, 0));
            DelegateFactory delegateFactory3 = new DelegateFactory();
            this.assistManagerGoogleProvider = delegateFactory3;
            this.timeoutManagerProvider = DoubleCheck.provider(new TimeoutManager_Factory(delegateFactory3, 0));
            this.assistantPresenceHandlerProvider = DoubleCheck.provider(new SingleTapClassifier_Factory(this.this$0.contextProvider, this.provideAssistUtilsProvider, 2));
            Provider<PhoneStateMonitor> provider7 = DoubleCheck.provider(new PhoneStateMonitor_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, this.optionalOfStatusBarProvider, this.bootCompleteCacheImplProvider, this.statusBarStateControllerImplProvider));
            this.phoneStateMonitorProvider = provider7;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            Provider<GoogleAssistLogger> provider8 = DoubleCheck.provider(new GoogleAssistLogger_Factory(daggerTitanGlobalRootComponent8.contextProvider, daggerTitanGlobalRootComponent8.provideUiEventLoggerProvider, this.provideAssistUtilsProvider, provider7, this.assistantPresenceHandlerProvider, 0));
            this.googleAssistLoggerProvider = provider8;
            this.touchInsideHandlerProvider = DoubleCheck.provider(new TouchInsideHandler_Factory(this.assistManagerGoogleProvider, this.navigationModeControllerProvider, provider8, 0));
            this.colorChangeHandlerProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.this$0.contextProvider, 0));
            Provider provider9 = DoubleCheck.provider(TouchOutsideHandler_Factory.InstanceHolder.INSTANCE);
            this.touchOutsideHandlerProvider = provider9;
            Provider provider10 = DoubleCheck.provider(new OverlayUiHost_Factory(this.this$0.contextProvider, provider9, 0));
            this.overlayUiHostProvider = provider10;
            Provider<ViewGroup> provider11 = DoubleCheck.provider(new TypeClassifier_Factory(provider10, 6));
            this.provideParentViewGroupProvider = provider11;
            this.edgeLightsControllerProvider = DoubleCheck.provider(new IconController_Factory(this.this$0.contextProvider, provider11, this.googleAssistLoggerProvider, 2));
            this.glowControllerProvider = DoubleCheck.provider(new ActionProxyReceiver_Factory(this.this$0.contextProvider, this.provideParentViewGroupProvider, this.touchInsideHandlerProvider, 2));
            DelegateFactory delegateFactory4 = new DelegateFactory();
            this.statusBarGoogleProvider = delegateFactory4;
            this.overlappedElementControllerProvider = DoubleCheck.provider(new GameDashboardUiEventLogger_Factory(delegateFactory4, 2));
            Provider provider12 = DoubleCheck.provider(LightnessProvider_Factory.InstanceHolder.INSTANCE);
            this.lightnessProvider = provider12;
            this.scrimControllerProvider2 = DoubleCheck.provider(new com.google.android.systemui.assist.uihints.ScrimController_Factory(this.provideParentViewGroupProvider, this.overlappedElementControllerProvider, provider12, this.touchInsideHandlerProvider));
            Provider provider13 = DoubleCheck.provider(FlingVelocityWrapper_Factory.InstanceHolder.INSTANCE);
            this.flingVelocityWrapperProvider = provider13;
            this.transcriptionControllerProvider = DoubleCheck.provider(new QSFgsManagerFooter_Factory(this.provideParentViewGroupProvider, this.touchInsideHandlerProvider, provider13, this.provideConfigurationControllerProvider, 2));
            this.iconControllerProvider = DoubleCheck.provider(new IconController_Factory(this.providerLayoutInflaterProvider, this.provideParentViewGroupProvider, this.provideConfigurationControllerProvider, 0));
            this.assistantWarmerProvider = DoubleCheck.provider(new AssistantWarmer_Factory(this.this$0.contextProvider, 0));
            this.navigationBarControllerProvider = new DelegateFactory();
            this.provideSysUiStateProvider = C0769xb6bb24d7.m50m(this.this$0.dumpManagerProvider, 2);
            this.setPipProvider = InstanceFactory.create(optional);
            this.setLegacySplitScreenProvider = InstanceFactory.create(optional2);
            this.setSplitScreenProvider = InstanceFactory.create(optional3);
            this.setOneHandedProvider = InstanceFactory.create(optional5);
            this.setRecentTasksProvider = InstanceFactory.create(optional13);
            this.setBackAnimationProvider = InstanceFactory.create(optional16);
            this.setStartingSurfaceProvider = InstanceFactory.create(optional10);
            InstanceFactory create = InstanceFactory.create(shellTransitions);
            InstanceFactory instanceFactory = create;
            this.setTransitionsProvider = create;
            DelegateFactory delegateFactory5 = r2;
            DelegateFactory delegateFactory6 = new DelegateFactory();
            this.keyguardUnlockAnimationControllerProvider = delegateFactory6;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            this.overviewProxyServiceProvider = DoubleCheck.provider(OverviewProxyService_Factory.create(daggerTitanGlobalRootComponent9.contextProvider, this.provideCommandQueueProvider, this.navigationBarControllerProvider, this.optionalOfStatusBarProvider, this.navigationModeControllerProvider, this.notificationShadeWindowControllerImplProvider, this.provideSysUiStateProvider, this.setPipProvider, this.setLegacySplitScreenProvider, this.setSplitScreenProvider, this.setOneHandedProvider, this.setRecentTasksProvider, this.setBackAnimationProvider, this.setStartingSurfaceProvider, this.providesBroadcastDispatcherProvider, instanceFactory, daggerTitanGlobalRootComponent9.screenLifecycleProvider, daggerTitanGlobalRootComponent9.provideUiEventLoggerProvider, delegateFactory5, daggerTitanGlobalRootComponent9.dumpManagerProvider));
            this.accessibilityButtonModeObserverProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.this$0.contextProvider, 1));
            this.accessibilityButtonTargetsObserverProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(this.this$0.contextProvider, 1));
            DelegateFactory delegateFactory7 = new DelegateFactory();
            this.contextComponentResolverProvider = delegateFactory7;
            CommunalSourceMonitor_Factory communalSourceMonitor_Factory = new CommunalSourceMonitor_Factory(this.this$0.contextProvider, delegateFactory7, 3);
            this.provideRecentsImplProvider = communalSourceMonitor_Factory;
            Provider<Recents> provider14 = DoubleCheck.provider(new IconController_Factory(this.this$0.contextProvider, communalSourceMonitor_Factory, this.provideCommandQueueProvider, 3));
            this.provideRecentsProvider = provider14;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider = new PresentJdkOptionalInstanceProvider(provider14);
            this.optionalOfRecentsProvider = presentJdkOptionalInstanceProvider;
            Provider<SystemActions> provider15 = DoubleCheck.provider(new SystemActions_Factory(this.this$0.contextProvider, this.notificationShadeWindowControllerImplProvider, this.optionalOfStatusBarProvider, presentJdkOptionalInstanceProvider));
            this.systemActionsProvider = provider15;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.navBarHelperProvider = DoubleCheck.provider(NavBarHelper_Factory.create(daggerTitanGlobalRootComponent10.contextProvider, daggerTitanGlobalRootComponent10.provideAccessibilityManagerProvider, this.accessibilityButtonModeObserverProvider, this.accessibilityButtonTargetsObserverProvider, provider15, this.overviewProxyServiceProvider, this.assistManagerGoogleProvider, this.optionalOfStatusBarProvider, this.navigationModeControllerProvider, this.provideUserTrackerProvider, daggerTitanGlobalRootComponent10.dumpManagerProvider));
            this.provideMetricsLoggerProvider = DoubleCheck.provider(new SecureSettingsImpl_Factory(dependencyProvider3, 5));
            this.shadeControllerImplProvider = new DelegateFactory();
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            this.blurUtilsProvider = DoubleCheck.provider(new BlurUtils_Factory(daggerTitanGlobalRootComponent11.provideResourcesProvider, daggerTitanGlobalRootComponent11.provideCrossWindowBlurListenersProvider, daggerTitanGlobalRootComponent11.dumpManagerProvider, 0));
            this.biometricUnlockControllerProvider = new DelegateFactory();
            Provider<WallpaperController> provider16 = DoubleCheck.provider(new WallpaperController_Factory(this.this$0.provideWallpaperManagerProvider, 0));
            this.wallpaperControllerProvider = provider16;
            this.notificationShadeDepthControllerProvider = DoubleCheck.provider(NotificationShadeDepthController_Factory.create(this.statusBarStateControllerImplProvider, this.blurUtilsProvider, this.biometricUnlockControllerProvider, this.keyguardStateControllerImplProvider, this.providesChoreographerProvider, provider16, this.notificationShadeWindowControllerImplProvider, this.dozeParametersProvider, this.this$0.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            this.gameModeDndControllerProvider = DoubleCheck.provider(new TileAdapter_Factory(daggerTitanGlobalRootComponent12.contextProvider, daggerTitanGlobalRootComponent12.provideNotificationManagerProvider, this.providesBroadcastDispatcherProvider, 3));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            this.fpsControllerProvider = DoubleCheck.provider(new UserCreator_Factory(daggerTitanGlobalRootComponent13.provideMainExecutorProvider, daggerTitanGlobalRootComponent13.provideWindowManagerProvider, 3));
            this.recordingControllerProvider = DoubleCheck.provider(new UserCreator_Factory(this.providesBroadcastDispatcherProvider, this.provideUserTrackerProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            Provider<ToastController> provider17 = DoubleCheck.provider(new ToastController_Factory(daggerTitanGlobalRootComponent14.contextProvider, this.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent14.provideWindowManagerProvider, daggerTitanGlobalRootComponent14.provideUiEventLoggerProvider, this.navigationModeControllerProvider, 0));
            this.toastControllerProvider = provider17;
            this.screenRecordControllerProvider = DoubleCheck.provider(new ScreenRecordController_Factory(this.recordingControllerProvider, this.provideHandlerProvider, this.keyguardDismissUtilProvider, this.this$0.contextProvider, provider17, 0));
            this.setTaskSurfaceHelperProvider = InstanceFactory.create(optional12);
            GameDashboardUiEventLogger_Factory gameDashboardUiEventLogger_Factory = new GameDashboardUiEventLogger_Factory(this.this$0.provideUiEventLoggerProvider, 0);
            this.gameDashboardUiEventLoggerProvider = gameDashboardUiEventLogger_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            Provider<ShortcutBarController> provider18 = DoubleCheck.provider(new ShortcutBarController_Factory(daggerTitanGlobalRootComponent15.contextProvider, daggerTitanGlobalRootComponent15.provideWindowManagerProvider, this.fpsControllerProvider, this.provideConfigurationControllerProvider, this.provideHandlerProvider, this.screenRecordControllerProvider, this.setTaskSurfaceHelperProvider, gameDashboardUiEventLogger_Factory, this.toastControllerProvider));
            this.shortcutBarControllerProvider = provider18;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            this.entryPointControllerProvider = DoubleCheck.provider(EntryPointController_Factory.create(daggerTitanGlobalRootComponent16.contextProvider, daggerTitanGlobalRootComponent16.provideAccessibilityManagerProvider, this.providesBroadcastDispatcherProvider, this.provideCommandQueueProvider, this.gameModeDndControllerProvider, daggerTitanGlobalRootComponent16.provideMainHandlerProvider, this.navigationModeControllerProvider, this.setLegacySplitScreenProvider, this.overviewProxyServiceProvider, daggerTitanGlobalRootComponent16.providePackageManagerProvider, provider18, this.toastControllerProvider, this.gameDashboardUiEventLoggerProvider, this.setTaskSurfaceHelperProvider));
            Provider<DarkIconDispatcherImpl> provider19 = this.darkIconDispatcherImplProvider;
            Provider<BatteryController> provider20 = this.provideBatteryControllerProvider;
            Provider<NavigationModeController> provider21 = this.navigationModeControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            this.factoryProvider = new LightBarController_Factory_Factory(provider19, provider20, provider21, daggerTitanGlobalRootComponent17.dumpManagerProvider);
            this.provideAutoHideControllerProvider = DoubleCheck.provider(new DoubleTapClassifier_Factory(dependencyProvider3, daggerTitanGlobalRootComponent17.contextProvider, daggerTitanGlobalRootComponent17.provideMainHandlerProvider, daggerTitanGlobalRootComponent17.provideIWindowManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            Provider<Handler> provider22 = daggerTitanGlobalRootComponent18.provideMainHandlerProvider;
            Provider<Handler> provider23 = provider22;
            AutoHideController_Factory_Factory autoHideController_Factory_Factory = r4;
            AutoHideController_Factory_Factory autoHideController_Factory_Factory2 = new AutoHideController_Factory_Factory(provider22, daggerTitanGlobalRootComponent18.provideIWindowManagerProvider);
            this.factoryProvider2 = autoHideController_Factory_Factory2;
            Provider<UiEventLogger> provider24 = daggerTitanGlobalRootComponent18.provideUiEventLoggerProvider;
            Provider<NavBarHelper> provider25 = this.navBarHelperProvider;
            Provider<LightBarController> provider26 = this.lightBarControllerProvider;
            Provider<LightBarController.Factory> provider27 = this.factoryProvider;
            Provider<AutoHideController> provider28 = this.provideAutoHideControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            NavigationBar_Factory_Factory create2 = NavigationBar_Factory_Factory.create(this.assistManagerGoogleProvider, daggerTitanGlobalRootComponent18.provideAccessibilityManagerProvider, this.provideDeviceProvisionedControllerProvider, this.provideMetricsLoggerProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.accessibilityButtonModeObserverProvider, this.statusBarStateControllerImplProvider, this.provideSysUiStateProvider, this.providesBroadcastDispatcherProvider, this.provideCommandQueueProvider, this.setPipProvider, this.setLegacySplitScreenProvider, this.optionalOfRecentsProvider, this.optionalOfStatusBarProvider, this.shadeControllerImplProvider, this.provideNotificationRemoteInputManagerProvider, this.notificationShadeDepthControllerProvider, provider23, this.entryPointControllerProvider, provider24, provider25, provider26, provider27, provider28, autoHideController_Factory_Factory, daggerTitanGlobalRootComponent19.provideOptionalTelecomManagerProvider, daggerTitanGlobalRootComponent19.provideInputMethodManagerProvider, this.setBackAnimationProvider);
            this.factoryProvider3 = create2;
            Provider<NavigationBarController> provider29 = this.navigationBarControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            Provider<NavigationBarController> provider30 = provider29;
            DelegateFactory.setDelegate(provider30, DoubleCheck.provider(NavigationBarController_Factory.create(daggerTitanGlobalRootComponent20.contextProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.provideSysUiStateProvider, this.provideCommandQueueProvider, daggerTitanGlobalRootComponent20.provideMainHandlerProvider, this.provideConfigurationControllerProvider, this.navBarHelperProvider, daggerTitanGlobalRootComponent20.taskbarDelegateProvider, create2, this.statusBarKeyguardViewManagerProvider, daggerTitanGlobalRootComponent20.dumpManagerProvider, this.provideAutoHideControllerProvider, this.lightBarControllerProvider, this.setPipProvider, this.setBackAnimationProvider)));
            Provider<NavBarFader> provider31 = DoubleCheck.provider(new DistanceClassifier_Factory(this.navigationBarControllerProvider, this.this$0.provideMainHandlerProvider, 1));
            this.navBarFaderProvider = provider31;
            this.ngaUiControllerProvider = DoubleCheck.provider(NgaUiController_Factory.create(this.this$0.contextProvider, this.timeoutManagerProvider, this.assistantPresenceHandlerProvider, this.touchInsideHandlerProvider, this.colorChangeHandlerProvider, this.overlayUiHostProvider, this.edgeLightsControllerProvider, this.glowControllerProvider, this.scrimControllerProvider2, this.transcriptionControllerProvider, this.iconControllerProvider, this.lightnessProvider, this.statusBarStateControllerImplProvider, this.assistManagerGoogleProvider, this.flingVelocityWrapperProvider, this.assistantWarmerProvider, provider31, this.googleAssistLoggerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            this.opaEnabledReceiverProvider = DoubleCheck.provider(new OpaEnabledReceiver_Factory(daggerTitanGlobalRootComponent21.contextProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent21.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent21.opaEnabledSettingsProvider, 0));
            this.opaEnabledDispatcherProvider = new SeekBarViewModel_Factory(this.statusBarGoogleProvider, 2);
            int i = SetFactory.$r8$clinit;
            ArrayList arrayList = new ArrayList(1);
            this.setOfKeepAliveListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList, this.timeoutManagerProvider, arrayList, Collections.emptyList());
            this.provideAudioInfoListenersProvider = new AssistantUIHintsModule_ProvideAudioInfoListenersFactory(this.edgeLightsControllerProvider, this.glowControllerProvider);
            List emptyList = Collections.emptyList();
            ArrayList arrayList2 = new ArrayList(1);
            this.setOfAudioInfoListenerProvider = C0768xb6bb24d6.m49m(arrayList2, this.provideAudioInfoListenersProvider, emptyList, arrayList2);
            this.provideCardInfoListenersProvider = new AssistantUIHintsModule_ProvideCardInfoListenersFactory(this.glowControllerProvider, this.scrimControllerProvider2, this.transcriptionControllerProvider, this.lightnessProvider);
            List emptyList2 = Collections.emptyList();
            ArrayList arrayList3 = new ArrayList(1);
            this.setOfCardInfoListenerProvider = C0768xb6bb24d6.m49m(arrayList3, this.provideCardInfoListenersProvider, emptyList2, arrayList3);
            this.taskStackNotifierProvider = DoubleCheck.provider(TaskStackNotifier_Factory.InstanceHolder.INSTANCE);
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider2 = new PresentJdkOptionalInstanceProvider(this.provideCommandQueueProvider);
            this.optionalOfCommandQueueProvider = presentJdkOptionalInstanceProvider2;
            this.keyboardMonitorProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(this.this$0.contextProvider, presentJdkOptionalInstanceProvider2, 3));
            Provider<ConfigurationHandler> provider32 = DoubleCheck.provider(new MediaControllerFactory_Factory(this.this$0.contextProvider, 6));
            this.configurationHandlerProvider = provider32;
            this.provideConfigInfoListenersProvider = new AssistantUIHintsModule_ProvideConfigInfoListenersFactory(this.assistantPresenceHandlerProvider, this.touchInsideHandlerProvider, this.touchOutsideHandlerProvider, this.taskStackNotifierProvider, this.keyboardMonitorProvider, this.colorChangeHandlerProvider, provider32);
            List emptyList3 = Collections.emptyList();
            ArrayList arrayList4 = new ArrayList(1);
            this.setOfConfigInfoListenerProvider = C0768xb6bb24d6.m49m(arrayList4, this.provideConfigInfoListenersProvider, emptyList3, arrayList4);
            this.provideTouchActionRegionsProvider = new InputModule_ProvideTouchActionRegionsFactory(this.iconControllerProvider, this.transcriptionControllerProvider);
            List emptyList4 = Collections.emptyList();
            ArrayList arrayList5 = new ArrayList(1);
            this.setOfTouchActionRegionProvider = C0768xb6bb24d6.m49m(arrayList5, this.provideTouchActionRegionsProvider, emptyList4, arrayList5);
            this.provideTouchInsideRegionsProvider = new BlurUtils_Factory(this.glowControllerProvider, this.scrimControllerProvider2, this.transcriptionControllerProvider, 1);
            List emptyList5 = Collections.emptyList();
            ArrayList arrayList6 = new ArrayList(1);
            SetFactory m = C0768xb6bb24d6.m49m(arrayList6, this.provideTouchInsideRegionsProvider, emptyList5, arrayList6);
            this.setOfTouchInsideRegionProvider = m;
            Provider<NgaInputHandler> provider33 = DoubleCheck.provider(new NgaInputHandler_Factory(this.touchInsideHandlerProvider, this.setOfTouchActionRegionProvider, m));
            this.ngaInputHandlerProvider = provider33;
            this.bindEdgeLightsInfoListenersProvider = new AssistantUIHintsModule_BindEdgeLightsInfoListenersFactory(this.edgeLightsControllerProvider, provider33);
            List emptyList6 = Collections.emptyList();
            ArrayList arrayList7 = new ArrayList(1);
            this.setOfEdgeLightsInfoListenerProvider = C0768xb6bb24d6.m49m(arrayList7, this.bindEdgeLightsInfoListenersProvider, emptyList6, arrayList7);
            ArrayList arrayList8 = new ArrayList(1);
            this.setOfTranscriptionInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList8, this.transcriptionControllerProvider, arrayList8, Collections.emptyList());
        }

        public final void initialize3(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            int i = SetFactory.$r8$clinit;
            ArrayList arrayList = new ArrayList(1);
            this.setOfGreetingInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList, this.transcriptionControllerProvider, arrayList, Collections.emptyList());
            ArrayList arrayList2 = new ArrayList(1);
            this.setOfChipsInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList2, this.transcriptionControllerProvider, arrayList2, Collections.emptyList());
            ArrayList arrayList3 = new ArrayList(1);
            this.setOfClearListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList3, this.transcriptionControllerProvider, arrayList3, Collections.emptyList());
            this.provideActivityStarterProvider2 = new AssistantUIHintsModule_ProvideActivityStarterFactory(this.statusBarGoogleProvider);
            ArrayList arrayList4 = new ArrayList(1);
            this.setOfStartActivityInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList4, this.provideActivityStarterProvider2, arrayList4, Collections.emptyList());
            ArrayList arrayList5 = new ArrayList(1);
            this.setOfKeyboardInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList5, this.iconControllerProvider, arrayList5, Collections.emptyList());
            ArrayList arrayList6 = new ArrayList(1);
            this.setOfZerostateInfoListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList6, this.iconControllerProvider, arrayList6, Collections.emptyList());
            this.goBackHandlerProvider = DoubleCheck.provider(GoBackHandler_Factory.InstanceHolder.INSTANCE);
            ArrayList arrayList7 = new ArrayList(1);
            this.setOfGoBackListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList7, this.goBackHandlerProvider, arrayList7, Collections.emptyList());
            this.swipeHandlerProvider = DoubleCheck.provider(SwipeHandler_Factory.InstanceHolder.INSTANCE);
            ArrayList arrayList8 = new ArrayList(1);
            this.setOfSwipeListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList8, this.swipeHandlerProvider, arrayList8, Collections.emptyList());
            this.takeScreenshotHandlerProvider = DoubleCheck.provider(new AssistantWarmer_Factory(this.this$0.contextProvider, 4));
            ArrayList arrayList9 = new ArrayList(1);
            this.setOfTakeScreenshotListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList9, this.takeScreenshotHandlerProvider, arrayList9, Collections.emptyList());
            ArrayList arrayList10 = new ArrayList(1);
            this.setOfWarmingListenerProvider = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList10, this.assistantWarmerProvider, arrayList10, Collections.emptyList());
            ArrayList arrayList11 = new ArrayList(1);
            SetFactory m = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList11, this.navBarFaderProvider, arrayList11, Collections.emptyList());
            this.setOfNavBarVisibilityListenerProvider = m;
            this.ngaMessageHandlerProvider = DoubleCheck.provider(NgaMessageHandler_Factory.create(this.ngaUiControllerProvider, this.assistantPresenceHandlerProvider, this.navigationModeControllerProvider, this.setOfKeepAliveListenerProvider, this.setOfAudioInfoListenerProvider, this.setOfCardInfoListenerProvider, this.setOfConfigInfoListenerProvider, this.setOfEdgeLightsInfoListenerProvider, this.setOfTranscriptionInfoListenerProvider, this.setOfGreetingInfoListenerProvider, this.setOfChipsInfoListenerProvider, this.setOfClearListenerProvider, this.setOfStartActivityInfoListenerProvider, this.setOfKeyboardInfoListenerProvider, this.setOfZerostateInfoListenerProvider, this.setOfGoBackListenerProvider, this.setOfSwipeListenerProvider, this.setOfTakeScreenshotListenerProvider, this.setOfWarmingListenerProvider, m, this.this$0.provideMainHandlerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            this.defaultUiControllerProvider = DoubleCheck.provider(DefaultUiController_Factory.create(daggerTitanGlobalRootComponent.contextProvider, this.googleAssistLoggerProvider, daggerTitanGlobalRootComponent.provideWindowManagerProvider, this.provideMetricsLoggerProvider, this.assistManagerGoogleProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            Provider<GoogleDefaultUiController> provider = DoubleCheck.provider(new PrivacyDotViewController_Factory(daggerTitanGlobalRootComponent2.contextProvider, this.googleAssistLoggerProvider, daggerTitanGlobalRootComponent2.provideWindowManagerProvider, this.provideMetricsLoggerProvider, this.assistManagerGoogleProvider, 1));
            Provider<GoogleDefaultUiController> provider2 = provider;
            this.googleDefaultUiControllerProvider = provider;
            Provider<AssistManagerGoogle> provider3 = this.assistManagerGoogleProvider;
            Provider<DeviceProvisionedController> provider4 = this.provideDeviceProvisionedControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            DelegateFactory.setDelegate(provider3, DoubleCheck.provider(AssistManagerGoogle_Factory.create(provider4, daggerTitanGlobalRootComponent3.contextProvider, this.provideAssistUtilsProvider, this.ngaUiControllerProvider, this.provideCommandQueueProvider, this.opaEnabledReceiverProvider, this.phoneStateMonitorProvider, this.overviewProxyServiceProvider, this.opaEnabledDispatcherProvider, this.keyguardUpdateMonitorProvider, this.navigationModeControllerProvider, this.assistantPresenceHandlerProvider, this.ngaMessageHandlerProvider, this.provideSysUiStateProvider, daggerTitanGlobalRootComponent3.provideMainHandlerProvider, this.defaultUiControllerProvider, provider2, daggerTitanGlobalRootComponent3.provideIWindowManagerProvider, this.googleAssistLoggerProvider)));
            DelegateFactory.setDelegate(this.shadeControllerImplProvider, DoubleCheck.provider(ShadeControllerImpl_Factory.create(this.provideCommandQueueProvider, this.statusBarStateControllerImplProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.this$0.provideWindowManagerProvider, this.optionalOfStatusBarProvider, this.assistManagerGoogleProvider, this.setBubblesProvider)));
            this.mediaArtworkProcessorProvider = DoubleCheck.provider(MediaArtworkProcessor_Factory.InstanceHolder.INSTANCE);
            this.notifPipelineProvider = new DelegateFactory();
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            MediaControllerFactory_Factory mediaControllerFactory_Factory = new MediaControllerFactory_Factory(daggerTitanGlobalRootComponent4.contextProvider, 0);
            this.mediaControllerFactoryProvider = mediaControllerFactory_Factory;
            this.mediaTimeoutListenerProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(mediaControllerFactory_Factory, daggerTitanGlobalRootComponent4.provideMainDelayableExecutorProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            Provider<Context> provider5 = daggerTitanGlobalRootComponent5.contextProvider;
            MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(provider5, 0);
            this.mediaBrowserFactoryProvider = mediaBrowserFactory_Factory;
            ResumeMediaBrowserFactory_Factory resumeMediaBrowserFactory_Factory = new ResumeMediaBrowserFactory_Factory(provider5, mediaBrowserFactory_Factory, 0);
            this.resumeMediaBrowserFactoryProvider = resumeMediaBrowserFactory_Factory;
            this.mediaResumeListenerProvider = DoubleCheck.provider(MediaResumeListener_Factory.create(provider5, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, this.tunerServiceImplProvider, resumeMediaBrowserFactory_Factory, daggerTitanGlobalRootComponent5.dumpManagerProvider, this.bindSystemClockProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            Provider<Context> provider6 = daggerTitanGlobalRootComponent6.contextProvider;
            this.mediaSessionBasedFilterProvider = new MediaSessionBasedFilter_Factory(provider6, daggerTitanGlobalRootComponent6.provideMediaSessionManagerProvider, daggerTitanGlobalRootComponent6.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, 0);
            Provider<LocalBluetoothManager> provider7 = DoubleCheck.provider(new QSFragmentModule_ProvidesQSFooterActionsViewFactory(provider6, this.provideBgHandlerProvider, 1));
            this.provideLocalBluetoothControllerProvider = provider7;
            this.localMediaManagerFactoryProvider = new CommunalSourceMonitor_Factory(this.this$0.contextProvider, provider7, 2);
            Provider<MediaFlags> provider8 = DoubleCheck.provider(new MediaFlags_Factory(this.featureFlagsReleaseProvider, 0));
            this.mediaFlagsProvider = provider8;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            Provider<MediaMuteAwaitConnectionManagerFactory> provider9 = DoubleCheck.provider(new ColumbusStructuredDataManager_Factory(provider8, daggerTitanGlobalRootComponent7.contextProvider, daggerTitanGlobalRootComponent7.provideMainExecutorProvider, 1));
            this.mediaMuteAwaitConnectionManagerFactoryProvider = provider9;
            Provider<MediaControllerFactory> provider10 = this.mediaControllerFactoryProvider;
            Provider<LocalMediaManagerFactory> provider11 = this.localMediaManagerFactoryProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            this.mediaDeviceManagerProvider = MediaDeviceManager_Factory.create(provider10, provider11, daggerTitanGlobalRootComponent8.provideMediaRouter2ManagerProvider, provider9, daggerTitanGlobalRootComponent8.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent8.dumpManagerProvider);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            MediaDataFilter_Factory create = MediaDataFilter_Factory.create(daggerTitanGlobalRootComponent9.contextProvider, this.providesBroadcastDispatcherProvider, this.notificationLockscreenUserManagerGoogleProvider, daggerTitanGlobalRootComponent9.provideMainExecutorProvider, this.bindSystemClockProvider);
            this.mediaDataFilterProvider = create;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            Provider<MediaDataManager> provider12 = DoubleCheck.provider(MediaDataManager_Factory.create(daggerTitanGlobalRootComponent10.contextProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent10.provideMainDelayableExecutorProvider, this.mediaControllerFactoryProvider, daggerTitanGlobalRootComponent10.dumpManagerProvider, this.providesBroadcastDispatcherProvider, this.mediaTimeoutListenerProvider, this.mediaResumeListenerProvider, this.mediaSessionBasedFilterProvider, this.mediaDeviceManagerProvider, create, this.provideActivityStarterProvider, this.bindSystemClockProvider, this.tunerServiceImplProvider, this.mediaFlagsProvider));
            this.mediaDataManagerProvider = provider12;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            this.provideNotificationMediaManagerProvider = DoubleCheck.provider(C1208x30c882de.create(daggerTitanGlobalRootComponent11.contextProvider, this.optionalOfStatusBarProvider, this.notificationShadeWindowControllerImplProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.mediaArtworkProcessorProvider, this.keyguardBypassControllerProvider, this.notifPipelineProvider, this.notifCollectionProvider, this.notifPipelineFlagsProvider, daggerTitanGlobalRootComponent11.provideMainDelayableExecutorProvider, provider12, daggerTitanGlobalRootComponent11.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            Provider<SessionTracker> provider13 = DoubleCheck.provider(new SessionTracker_Factory(daggerTitanGlobalRootComponent12.contextProvider, daggerTitanGlobalRootComponent12.provideIStatusBarServiceProvider, this.authControllerProvider, this.keyguardUpdateMonitorProvider, this.keyguardStateControllerImplProvider));
            Provider<SessionTracker> provider14 = provider13;
            this.sessionTrackerProvider = provider13;
            Provider<BiometricUnlockController> provider15 = this.biometricUnlockControllerProvider;
            Provider<DozeScrimController> provider16 = this.dozeScrimControllerProvider;
            Provider<KeyguardViewMediator> provider17 = this.newKeyguardViewMediatorProvider;
            Provider<ScrimController> provider18 = this.scrimControllerProvider;
            Provider<ShadeControllerImpl> provider19 = this.shadeControllerImplProvider;
            Provider<NotificationShadeWindowControllerImpl> provider20 = this.notificationShadeWindowControllerImplProvider;
            Provider<KeyguardStateControllerImpl> provider21 = this.keyguardStateControllerImplProvider;
            Provider<Handler> provider22 = this.provideHandlerProvider;
            Provider<KeyguardUpdateMonitor> provider23 = this.keyguardUpdateMonitorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            DelegateFactory.setDelegate(provider15, DoubleCheck.provider(BiometricUnlockController_Factory.create(provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, daggerTitanGlobalRootComponent13.provideResourcesProvider, this.keyguardBypassControllerProvider, this.dozeParametersProvider, this.provideMetricsLoggerProvider, daggerTitanGlobalRootComponent13.dumpManagerProvider, daggerTitanGlobalRootComponent13.providePowerManagerProvider, this.provideNotificationMediaManagerProvider, this.wakefulnessLifecycleProvider, daggerTitanGlobalRootComponent13.screenLifecycleProvider, this.authControllerProvider, this.statusBarStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, provider14, daggerTitanGlobalRootComponent13.provideLatencyTrackerProvider)));
            DelegateFactory.setDelegate(this.keyguardUnlockAnimationControllerProvider, DoubleCheck.provider(WMShellModule_ProvideAppPairsFactory.create(this.this$0.contextProvider, this.keyguardStateControllerImplProvider, this.newKeyguardViewMediatorProvider, this.statusBarKeyguardViewManagerProvider, this.featureFlagsReleaseProvider, this.biometricUnlockControllerProvider)));
            Provider<KeyguardStateControllerImpl> provider24 = this.keyguardStateControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            DelegateFactory.setDelegate(provider24, DoubleCheck.provider(GoogleAssistLogger_Factory.create$1(daggerTitanGlobalRootComponent14.contextProvider, this.keyguardUpdateMonitorProvider, this.provideLockPatternUtilsProvider, this.keyguardUnlockAnimationControllerProvider, daggerTitanGlobalRootComponent14.dumpManagerProvider)));
            Provider<KeyguardBypassController> provider25 = this.keyguardBypassControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            DelegateFactory.setDelegate(provider25, DoubleCheck.provider(StatusBarDemoMode_Factory.create(daggerTitanGlobalRootComponent15.contextProvider, this.tunerServiceImplProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerGoogleProvider, this.keyguardStateControllerImplProvider, daggerTitanGlobalRootComponent15.dumpManagerProvider)));
            Provider<VisualStabilityProvider> provider26 = DoubleCheck.provider(VisualStabilityProvider_Factory.InstanceHolder.INSTANCE);
            this.visualStabilityProvider = provider26;
            Provider<HeadsUpManagerPhone> provider27 = DoubleCheck.provider(new SystemUIGoogleModule_ProvideHeadsUpManagerPhoneFactory(this.this$0.contextProvider, this.headsUpManagerLoggerProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.provideGroupMembershipManagerProvider, provider26, this.provideConfigurationControllerProvider));
            this.provideHeadsUpManagerPhoneProvider = provider27;
            Provider<SmartReplyConstants> provider28 = this.smartReplyConstantsProvider;
            SmartActionInflaterImpl_Factory smartActionInflaterImpl_Factory = new SmartActionInflaterImpl_Factory(provider28, this.provideActivityStarterProvider, this.provideSmartReplyControllerProvider, provider27);
            this.smartActionInflaterImplProvider = smartActionInflaterImpl_Factory;
            SmartReplyStateInflaterImpl_Factory create2 = SmartReplyStateInflaterImpl_Factory.create(provider28, this.provideActivityManagerWrapperProvider, this.this$0.providePackageManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, this.smartReplyInflaterImplProvider, smartActionInflaterImpl_Factory);
            this.smartReplyStateInflaterImplProvider = create2;
            this.notificationContentInflaterProvider = DoubleCheck.provider(NotificationContentInflater_Factory.create(this.provideNotifRemoteViewCacheProvider, this.provideNotificationRemoteInputManagerProvider, this.conversationNotificationProcessorProvider, this.mediaFeatureFlagProvider, this.provideBackgroundExecutorProvider, create2));
            this.notifInflationErrorManagerProvider = DoubleCheck.provider(NotifInflationErrorManager_Factory.InstanceHolder.INSTANCE);
            WMShellBaseModule_ProvideSplitScreenFactory wMShellBaseModule_ProvideSplitScreenFactory = new WMShellBaseModule_ProvideSplitScreenFactory(this.provideNotificationsLogBufferProvider, 5);
            this.rowContentBindStageLoggerProvider = wMShellBaseModule_ProvideSplitScreenFactory;
            this.rowContentBindStageProvider = DoubleCheck.provider(new RowContentBindStage_Factory(this.notificationContentInflaterProvider, this.notifInflationErrorManagerProvider, wMShellBaseModule_ProvideSplitScreenFactory, 0));
            this.expandableNotificationRowComponentBuilderProvider = new Provider<ExpandableNotificationRowComponent.Builder>() {
                public final Object get() {
                    return new ExpandableNotificationRowComponentBuilder();
                }
            };
            UsbDebuggingActivity_Factory usbDebuggingActivity_Factory = new UsbDebuggingActivity_Factory(this.this$0.contextProvider, 2);
            this.iconBuilderProvider = usbDebuggingActivity_Factory;
            this.iconManagerProvider = new DarkIconDispatcherImpl_Factory(this.provideCommonNotifCollectionProvider, this.this$0.provideLauncherAppsProvider, usbDebuggingActivity_Factory, 1);
            Provider<LowPriorityInflationHelper> provider29 = DoubleCheck.provider(new ColumbusStructuredDataManager_Factory(this.notificationGroupManagerLegacyProvider, this.rowContentBindStageProvider, this.notifPipelineFlagsProvider, 2));
            this.lowPriorityInflationHelperProvider = provider29;
            this.notificationRowBinderImplProvider = DoubleCheck.provider(NotificationRowBinderImpl_Factory.create(this.this$0.contextProvider, this.provideNotificationMessagingUtilProvider, this.provideNotificationRemoteInputManagerProvider, this.notificationLockscreenUserManagerGoogleProvider, this.notifBindPipelineProvider, this.rowContentBindStageProvider, this.expandableNotificationRowComponentBuilderProvider, this.iconManagerProvider, provider29, this.notifPipelineFlagsProvider));
            Provider<ForegroundServiceDismissalFeatureController> provider30 = DoubleCheck.provider(new ScreenPinningRequest_Factory(this.deviceConfigProxyProvider, this.this$0.contextProvider, 2));
            this.foregroundServiceDismissalFeatureControllerProvider = provider30;
            Provider<NotificationEntryManager> provider31 = this.provideNotificationEntryManagerProvider;
            Provider<NotificationEntryManagerLogger> provider32 = this.notificationEntryManagerLoggerProvider;
            Provider<NotificationGroupManagerLegacy> provider33 = this.notificationGroupManagerLegacyProvider;
            Provider<NotifPipelineFlags> provider34 = this.notifPipelineFlagsProvider;
            Provider<NotificationRowBinderImpl> provider35 = this.notificationRowBinderImplProvider;
            Provider<NotificationRemoteInputManager> provider36 = this.provideNotificationRemoteInputManagerProvider;
            Provider<LeakDetector> provider37 = this.provideLeakDetectorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            DelegateFactory.setDelegate(provider31, DoubleCheck.provider(NotificationsModule_ProvideNotificationEntryManagerFactory.create(provider32, provider33, provider34, provider35, provider36, provider37, provider30, daggerTitanGlobalRootComponent16.provideIStatusBarServiceProvider, this.notifLiveDataStoreImplProvider, daggerTitanGlobalRootComponent16.dumpManagerProvider)));
            this.notificationInteractionTrackerProvider = DoubleCheck.provider(new DiagonalClassifier_Factory(this.notificationClickNotifierProvider, this.provideNotificationEntryManagerProvider, 1));
            SecureSettingsImpl_Factory secureSettingsImpl_Factory = new SecureSettingsImpl_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.shadeListBuilderLoggerProvider = secureSettingsImpl_Factory;
            this.shadeListBuilderProvider = DoubleCheck.provider(GlobalActionsImpl_Factory.create$1(this.this$0.dumpManagerProvider, this.notifPipelineChoreographerImplProvider, this.notifPipelineFlagsProvider, this.notificationInteractionTrackerProvider, secureSettingsImpl_Factory, this.bindSystemClockProvider));
            Provider<RenderStageManager> provider38 = DoubleCheck.provider(RenderStageManager_Factory.InstanceHolder.INSTANCE);
            this.renderStageManagerProvider = provider38;
            DelegateFactory.setDelegate(this.notifPipelineProvider, DoubleCheck.provider(new NotifPipeline_Factory(this.notifPipelineFlagsProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, provider38)));
            DelegateFactory.setDelegate(this.provideCommonNotifCollectionProvider, DoubleCheck.provider(new DozeLog_Factory(this.notifPipelineFlagsProvider, this.notifPipelineProvider, this.provideNotificationEntryManagerProvider, 2)));
            RotationPolicyWrapperImpl_Factory rotationPolicyWrapperImpl_Factory = new RotationPolicyWrapperImpl_Factory(this.notifLiveDataStoreImplProvider, this.provideCommonNotifCollectionProvider, 2);
            this.notificationVisibilityProviderImplProvider = rotationPolicyWrapperImpl_Factory;
            DelegateFactory.setDelegate(this.provideNotificationVisibilityProvider, DoubleCheck.provider(rotationPolicyWrapperImpl_Factory));
            DelegateFactory delegateFactory = new DelegateFactory();
            this.smartSpaceControllerProvider = delegateFactory;
            Provider<NotificationLockscreenUserManagerGoogle> provider39 = this.notificationLockscreenUserManagerGoogleProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            Provider<NotificationLockscreenUserManagerGoogle> provider40 = provider39;
            Provider<NotificationLockscreenUserManagerGoogle> provider41 = provider40;
            DelegateFactory.setDelegate(provider41, DoubleCheck.provider(NotificationLockscreenUserManagerGoogle_Factory.create(daggerTitanGlobalRootComponent17.contextProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent17.provideDevicePolicyManagerProvider, daggerTitanGlobalRootComponent17.provideUserManagerProvider, this.provideNotificationVisibilityProvider, this.provideCommonNotifCollectionProvider, this.notificationClickNotifierProvider, daggerTitanGlobalRootComponent17.provideKeyguardManagerProvider, this.statusBarStateControllerImplProvider, daggerTitanGlobalRootComponent17.provideMainHandlerProvider, this.provideDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.keyguardBypassControllerProvider, delegateFactory, daggerTitanGlobalRootComponent17.dumpManagerProvider)));
            this.keyguardEnvironmentImplProvider = DoubleCheck.provider(new VibratorHelper_Factory(this.notificationLockscreenUserManagerGoogleProvider, this.provideDeviceProvisionedControllerProvider, 3));
            Provider<IndividualSensorPrivacyController> provider42 = DoubleCheck.provider(new UsbDebuggingSecondaryUserActivity_Factory(this.this$0.provideSensorPrivacyManagerProvider, 4));
            this.provideIndividualSensorPrivacyControllerProvider = provider42;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            Provider<AppOpsControllerImpl> provider43 = DoubleCheck.provider(AppOpsControllerImpl_Factory.create(daggerTitanGlobalRootComponent18.contextProvider, this.provideBgLooperProvider, daggerTitanGlobalRootComponent18.dumpManagerProvider, daggerTitanGlobalRootComponent18.provideAudioManagerProvider, provider42, this.providesBroadcastDispatcherProvider, this.bindSystemClockProvider));
            this.appOpsControllerImplProvider = provider43;
            Provider<ForegroundServiceController> provider44 = DoubleCheck.provider(new ForegroundServiceController_Factory(provider43, this.this$0.provideMainHandlerProvider, 0));
            this.foregroundServiceControllerProvider = provider44;
            this.notificationFilterProvider = DoubleCheck.provider(NotificationFilter_Factory.create(this.debugModeFilterProvider, this.statusBarStateControllerImplProvider, this.keyguardEnvironmentImplProvider, provider44, this.notificationLockscreenUserManagerGoogleProvider, this.mediaFeatureFlagProvider));
            WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory = new WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory(this.provideNotificationsLogBufferProvider, this.provideNotificationHeadsUpLogBufferProvider, 1);
            this.notificationInterruptLoggerProvider = wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            this.notificationInterruptStateProviderImplProvider = DoubleCheck.provider(NotificationInterruptStateProviderImpl_Factory.create(daggerTitanGlobalRootComponent19.provideContentResolverProvider, daggerTitanGlobalRootComponent19.providePowerManagerProvider, daggerTitanGlobalRootComponent19.provideIDreamManagerProvider, this.provideAmbientDisplayConfigurationProvider, this.notificationFilterProvider, this.provideBatteryControllerProvider, this.statusBarStateControllerImplProvider, this.provideHeadsUpManagerPhoneProvider, wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory, daggerTitanGlobalRootComponent19.provideMainHandlerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            this.lightSensorEventsDebounceAlgorithmProvider = new DependencyProvider_ProvidesModeSwitchesControllerFactory((Provider) daggerTitanGlobalRootComponent20.provideMainDelayableExecutorProvider, (Provider) daggerTitanGlobalRootComponent20.provideResourcesProvider);
            Provider<AsyncSensorManager> provider45 = DoubleCheck.provider(new DozeLog_Factory(daggerTitanGlobalRootComponent20.providesSensorManagerProvider, ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE, daggerTitanGlobalRootComponent20.providesPluginManagerProvider, 3));
            this.asyncSensorManagerProvider = provider45;
            AmbientLightModeMonitor_Factory ambientLightModeMonitor_Factory = new AmbientLightModeMonitor_Factory(this.lightSensorEventsDebounceAlgorithmProvider, provider45);
            this.ambientLightModeMonitorProvider = ambientLightModeMonitor_Factory;
            Provider<DockManager> provider46 = this.provideDockManagerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            DelegateFactory.setDelegate(provider46, DoubleCheck.provider(new WiredChargingRippleController_Factory(daggerTitanGlobalRootComponent21.contextProvider, this.providesBroadcastDispatcherProvider, this.statusBarStateControllerImplProvider, this.notificationInterruptStateProviderImplProvider, this.provideConfigurationControllerProvider, ambientLightModeMonitor_Factory, daggerTitanGlobalRootComponent21.provideMainDelayableExecutorProvider, daggerTitanGlobalRootComponent21.providePowerManagerProvider, 1)));
            Provider<FalsingDataProvider> provider47 = DoubleCheck.provider(new QSSquishinessController_Factory(this.this$0.provideDisplayMetricsProvider, this.provideBatteryControllerProvider, this.provideDockManagerProvider, 1));
            this.falsingDataProvider = provider47;
            DistanceClassifier_Factory distanceClassifier_Factory = new DistanceClassifier_Factory(provider47, this.deviceConfigProxyProvider, 0);
            this.distanceClassifierProvider = distanceClassifier_Factory;
            this.proximityClassifierProvider = new ProximityClassifier_Factory(distanceClassifier_Factory, this.falsingDataProvider, this.deviceConfigProxyProvider, 0);
            this.pointerCountClassifierProvider = new BootCompleteCacheImpl_Factory(this.falsingDataProvider, 1);
            this.typeClassifierProvider = new TypeClassifier_Factory(this.falsingDataProvider, 0);
            this.diagonalClassifierProvider = new DiagonalClassifier_Factory(this.falsingDataProvider, this.deviceConfigProxyProvider, 0);
            KeyguardUnfoldTransition_Factory keyguardUnfoldTransition_Factory = new KeyguardUnfoldTransition_Factory(this.falsingDataProvider, this.deviceConfigProxyProvider, 1);
            this.zigZagClassifierProvider = keyguardUnfoldTransition_Factory;
            this.providesBrightLineGestureClassifiersProvider = FalsingModule_ProvidesBrightLineGestureClassifiersFactory.create(this.distanceClassifierProvider, this.proximityClassifierProvider, this.pointerCountClassifierProvider, this.typeClassifierProvider, this.diagonalClassifierProvider, keyguardUnfoldTransition_Factory);
            List emptyList = Collections.emptyList();
            ArrayList arrayList12 = new ArrayList(1);
            this.namedSetOfFalsingClassifierProvider = C0768xb6bb24d6.m49m(arrayList12, this.providesBrightLineGestureClassifiersProvider, emptyList, arrayList12);
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = new QSFragmentModule_ProvidesQuickQSPanelFactory(this.this$0.provideViewConfigurationProvider, 1);
            this.providesSingleTapTouchSlopProvider = qSFragmentModule_ProvidesQuickQSPanelFactory;
            this.singleTapClassifierProvider = new SingleTapClassifier_Factory(this.falsingDataProvider, qSFragmentModule_ProvidesQuickQSPanelFactory, 0);
            MediaBrowserFactory_Factory mediaBrowserFactory_Factory2 = new MediaBrowserFactory_Factory(this.this$0.provideResourcesProvider, 1);
            this.providesDoubleTapTouchSlopProvider = mediaBrowserFactory_Factory2;
            this.doubleTapClassifierProvider = new DoubleTapClassifier_Factory(this.falsingDataProvider, this.singleTapClassifierProvider, mediaBrowserFactory_Factory2);
            Provider<HistoryTracker> provider48 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.bindSystemClockProvider, 1));
            this.historyTrackerProvider = provider48;
            BrightLineFalsingManager_Factory create3 = BrightLineFalsingManager_Factory.create(this.falsingDataProvider, this.provideMetricsLoggerProvider, this.namedSetOfFalsingClassifierProvider, this.singleTapClassifierProvider, this.doubleTapClassifierProvider, provider48, this.keyguardStateControllerImplProvider, this.this$0.provideAccessibilityManagerProvider);
            this.brightLineFalsingManagerProvider = create3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent22 = this.this$0;
            this.falsingManagerProxyProvider = DoubleCheck.provider(new FalsingManagerProxy_Factory(daggerTitanGlobalRootComponent22.providesPluginManagerProvider, daggerTitanGlobalRootComponent22.provideMainExecutorProvider, this.deviceConfigProxyProvider, daggerTitanGlobalRootComponent22.dumpManagerProvider, create3));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent23 = this.this$0;
            Provider<Resources> provider49 = daggerTitanGlobalRootComponent23.provideResourcesProvider;
            ThresholdSensorImpl_BuilderFactory_Factory thresholdSensorImpl_BuilderFactory_Factory = new ThresholdSensorImpl_BuilderFactory_Factory(provider49, this.asyncSensorManagerProvider, daggerTitanGlobalRootComponent23.provideExecutionProvider);
            this.builderFactoryProvider = thresholdSensorImpl_BuilderFactory_Factory;
            this.providePostureToProximitySensorMappingProvider = new SensorModule_ProvidePostureToProximitySensorMappingFactory(thresholdSensorImpl_BuilderFactory_Factory, provider49);
            this.providePostureToSecondaryProximitySensorMappingProvider = new C1707xe478f0bc(thresholdSensorImpl_BuilderFactory_Factory, provider49);
            Provider<DevicePostureControllerImpl> provider50 = DoubleCheck.provider(new TouchInsideHandler_Factory(daggerTitanGlobalRootComponent23.contextProvider, daggerTitanGlobalRootComponent23.provideDeviceStateManagerProvider, daggerTitanGlobalRootComponent23.provideMainExecutorProvider, 2));
            this.devicePostureControllerImplProvider = provider50;
            Provider<ThresholdSensor[]> provider51 = this.providePostureToProximitySensorMappingProvider;
            Provider<ThresholdSensor[]> provider52 = this.providePostureToSecondaryProximitySensorMappingProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent24 = this.this$0;
            this.postureDependentProximitySensorProvider = PostureDependentProximitySensor_Factory.create(provider51, provider52, daggerTitanGlobalRootComponent24.provideMainDelayableExecutorProvider, daggerTitanGlobalRootComponent24.provideExecutionProvider, provider50);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent25 = this.this$0;
            ThresholdSensorImpl_Builder_Factory thresholdSensorImpl_Builder_Factory = new ThresholdSensorImpl_Builder_Factory(daggerTitanGlobalRootComponent25.provideResourcesProvider, this.asyncSensorManagerProvider, daggerTitanGlobalRootComponent25.provideExecutionProvider);
            this.builderProvider2 = thresholdSensorImpl_Builder_Factory;
            this.providePrimaryProximitySensorProvider = new SensorModule_ProvidePrimaryProximitySensorFactory(daggerTitanGlobalRootComponent25.providesSensorManagerProvider, thresholdSensorImpl_Builder_Factory);
        }

        public final void initialize4(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            SensorModule_ProvideSecondaryProximitySensorFactory sensorModule_ProvideSecondaryProximitySensorFactory = new SensorModule_ProvideSecondaryProximitySensorFactory(this.builderProvider2);
            this.provideSecondaryProximitySensorProvider = sensorModule_ProvideSecondaryProximitySensorFactory;
            Provider<ThresholdSensor> provider = this.providePrimaryProximitySensorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            Provider<DelayableExecutor> provider2 = daggerTitanGlobalRootComponent.provideMainDelayableExecutorProvider;
            MediaDreamSentinel_Factory mediaDreamSentinel_Factory = new MediaDreamSentinel_Factory(provider, sensorModule_ProvideSecondaryProximitySensorFactory, provider2, daggerTitanGlobalRootComponent.provideExecutionProvider, 1);
            this.proximitySensorImplProvider = mediaDreamSentinel_Factory;
            TileAdapter_Factory tileAdapter_Factory = new TileAdapter_Factory(daggerTitanGlobalRootComponent.provideResourcesProvider, this.postureDependentProximitySensorProvider, mediaDreamSentinel_Factory, 1);
            this.provideProximitySensorProvider = tileAdapter_Factory;
            this.falsingCollectorImplProvider = DoubleCheck.provider(FalsingCollectorImpl_Factory.create(this.falsingDataProvider, this.falsingManagerProxyProvider, this.keyguardUpdateMonitorProvider, this.historyTrackerProvider, tileAdapter_Factory, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideBatteryControllerProvider, this.provideDockManagerProvider, provider2, this.bindSystemClockProvider));
            this.dismissCallbackRegistryProvider = C0770xb6bb24d8.m51m(this.provideUiBackgroundExecutorProvider, 2);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            this.telephonyListenerManagerProvider = DoubleCheck.provider(new TelephonyListenerManager_Factory(daggerTitanGlobalRootComponent2.provideTelephonyManagerProvider, daggerTitanGlobalRootComponent2.provideMainExecutorProvider, TelephonyCallback_Factory.InstanceHolder.INSTANCE, 0));
            Provider<DialogLaunchAnimator> provider3 = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.this$0.provideIDreamManagerProvider, 2));
            Provider<DialogLaunchAnimator> provider4 = provider3;
            this.provideDialogLaunchAnimatorProvider = provider3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.userSwitcherControllerProvider = DoubleCheck.provider(UserSwitcherController_Factory.create(daggerTitanGlobalRootComponent3.contextProvider, daggerTitanGlobalRootComponent3.provideIActivityManagerProvider, daggerTitanGlobalRootComponent3.provideUserManagerProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider, this.provideDeviceProvisionedControllerProvider, daggerTitanGlobalRootComponent3.provideDevicePolicyManagerProvider, daggerTitanGlobalRootComponent3.provideMainHandlerProvider, this.provideActivityStarterProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, this.falsingManagerProxyProvider, this.telephonyListenerManagerProvider, this.secureSettingsImplProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent3.provideMainExecutorProvider, daggerTitanGlobalRootComponent3.provideInteractionJankMonitorProvider, daggerTitanGlobalRootComponent3.provideLatencyTrackerProvider, daggerTitanGlobalRootComponent3.dumpManagerProvider, this.shadeControllerImplProvider, provider4));
            C24533 r1 = new Provider<KeyguardStatusViewComponent.Factory>() {
                public final Object get() {
                    return new KeyguardStatusViewComponentFactory();
                }
            };
            this.keyguardStatusViewComponentFactoryProvider = r1;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            this.keyguardDisplayManagerProvider = new KeyguardDisplayManager_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.navigationBarControllerProvider, r1, this.provideUiBackgroundExecutorProvider);
            this.screenOnCoordinatorProvider = DoubleCheck.provider(new ScreenOnCoordinator_Factory(daggerTitanGlobalRootComponent4.screenLifecycleProvider, this.provideSysUIUnfoldComponentProvider, daggerTitanGlobalRootComponent4.provideExecutionProvider, 0));
            this.dreamOverlayStateControllerProvider = DoubleCheck.provider(new DreamOverlayStateController_Factory(this.this$0.provideMainExecutorProvider, 0));
            Provider<ActivityLaunchAnimator> provider5 = DoubleCheck.provider(StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory.InstanceHolder.INSTANCE);
            Provider<ActivityLaunchAnimator> provider6 = provider5;
            this.provideActivityLaunchAnimatorProvider = provider5;
            Provider<KeyguardViewMediator> provider7 = this.newKeyguardViewMediatorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            DelegateFactory.setDelegate(provider7, DoubleCheck.provider(KeyguardModule_NewKeyguardViewMediatorFactory.create(daggerTitanGlobalRootComponent5.contextProvider, this.falsingCollectorImplProvider, this.provideLockPatternUtilsProvider, this.providesBroadcastDispatcherProvider, this.statusBarKeyguardViewManagerProvider, this.dismissCallbackRegistryProvider, this.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent5.dumpManagerProvider, daggerTitanGlobalRootComponent5.providePowerManagerProvider, daggerTitanGlobalRootComponent5.provideTrustManagerProvider, this.userSwitcherControllerProvider, this.provideUiBackgroundExecutorProvider, this.deviceConfigProxyProvider, this.navigationModeControllerProvider, this.keyguardDisplayManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, this.screenOffAnimationControllerProvider, this.notificationShadeDepthControllerProvider, this.screenOnCoordinatorProvider, daggerTitanGlobalRootComponent5.provideInteractionJankMonitorProvider, this.dreamOverlayStateControllerProvider, this.notificationShadeWindowControllerImplProvider, provider6)));
            this.providesViewMediatorCallbackProvider = new DependencyProvider_ProvidesViewMediatorCallbackFactory(dependencyProvider, this.newKeyguardViewMediatorProvider);
            Provider<KeyguardSecurityModel> provider8 = DoubleCheck.provider(new KeyguardSecurityModel_Factory(this.this$0.provideResourcesProvider, this.provideLockPatternUtilsProvider, this.keyguardUpdateMonitorProvider, 0));
            this.keyguardSecurityModelProvider = provider8;
            C24544 r3 = new Provider<KeyguardBouncerComponent.Factory>() {
                public final Object get() {
                    return new KeyguardBouncerComponentFactory();
                }
            };
            this.keyguardBouncerComponentFactoryProvider = r3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            KeyguardBouncer_Factory_Factory create = KeyguardBouncer_Factory_Factory.create(daggerTitanGlobalRootComponent6.contextProvider, this.providesViewMediatorCallbackProvider, this.dismissCallbackRegistryProvider, this.falsingCollectorImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.keyguardBypassControllerProvider, daggerTitanGlobalRootComponent6.provideMainHandlerProvider, provider8, r3);
            KeyguardBouncer_Factory_Factory keyguardBouncer_Factory_Factory = create;
            this.factoryProvider4 = create;
            Provider<KeyguardUpdateMonitor> provider9 = this.keyguardUpdateMonitorProvider;
            Provider<KeyguardUpdateMonitor> provider10 = provider9;
            Provider<ConfigurationController> provider11 = this.provideConfigurationControllerProvider;
            Provider<ConfigurationController> provider12 = provider11;
            KeyguardMessageAreaController_Factory_Factory keyguardMessageAreaController_Factory_Factory = r4;
            KeyguardMessageAreaController_Factory_Factory keyguardMessageAreaController_Factory_Factory2 = new KeyguardMessageAreaController_Factory_Factory(provider9, provider11);
            this.factoryProvider5 = keyguardMessageAreaController_Factory_Factory2;
            Provider<StatusBarKeyguardViewManager> provider13 = this.statusBarKeyguardViewManagerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            DelegateFactory.setDelegate(provider13, DoubleCheck.provider(StatusBarKeyguardViewManager_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, this.providesViewMediatorCallbackProvider, this.provideLockPatternUtilsProvider, this.statusBarStateControllerImplProvider, provider12, provider10, this.dreamOverlayStateControllerProvider, this.navigationModeControllerProvider, this.provideDockManagerProvider, this.notificationShadeWindowControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideNotificationMediaManagerProvider, keyguardBouncer_Factory_Factory, keyguardMessageAreaController_Factory_Factory, this.provideSysUIUnfoldComponentProvider, this.shadeControllerImplProvider, daggerTitanGlobalRootComponent7.provideLatencyTrackerProvider)));
            this.provideLSShadeTransitionControllerBufferProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.logBufferFactoryProvider, 2));
            Provider<LockscreenGestureLogger> m = C0770xb6bb24d8.m51m(this.provideMetricsLoggerProvider, 4);
            this.lockscreenGestureLoggerProvider = m;
            this.lSShadeTransitionLoggerProvider = new IconController_Factory(this.provideLSShadeTransitionControllerBufferProvider, m, this.this$0.provideDisplayMetricsProvider, 1);
            Provider<MediaHostStatesManager> provider14 = DoubleCheck.provider(MediaHostStatesManager_Factory.InstanceHolder.INSTANCE);
            this.mediaHostStatesManagerProvider = provider14;
            this.mediaViewControllerProvider = new MediaViewController_Factory(this.this$0.contextProvider, this.provideConfigurationControllerProvider, provider14, 0);
            Provider<RepeatableExecutor> m2 = C0771xb6bb24d9.m52m(this.provideBackgroundDelayableExecutorProvider, 6);
            this.provideBackgroundRepeatableExecutorProvider = m2;
            this.seekBarViewModelProvider = new SeekBarViewModel_Factory(m2, 0);
            Provider<SystemUIDialogManager> provider15 = DoubleCheck.provider(new SystemUIDialogManager_Factory(this.this$0.dumpManagerProvider, this.statusBarKeyguardViewManagerProvider, 0));
            this.systemUIDialogManagerProvider = provider15;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            QSCustomizerController_Factory create2 = QSCustomizerController_Factory.create(daggerTitanGlobalRootComponent8.contextProvider, daggerTitanGlobalRootComponent8.provideMediaSessionManagerProvider, this.provideLocalBluetoothControllerProvider, this.shadeControllerImplProvider, this.provideActivityStarterProvider, this.provideCommonNotifCollectionProvider, daggerTitanGlobalRootComponent8.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, provider15);
            this.mediaOutputDialogFactoryProvider = create2;
            DelegateFactory delegateFactory = new DelegateFactory();
            this.mediaCarouselControllerProvider = delegateFactory;
            MediaControlPanel_Factory create3 = MediaControlPanel_Factory.create(this.this$0.contextProvider, this.provideBackgroundExecutorProvider, this.provideActivityStarterProvider, this.mediaViewControllerProvider, this.seekBarViewModelProvider, this.mediaDataManagerProvider, create2, delegateFactory, this.falsingManagerProxyProvider, this.mediaFlagsProvider, this.bindSystemClockProvider);
            this.mediaControlPanelProvider = create3;
            Provider<MediaCarouselController> provider16 = this.mediaCarouselControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            DelegateFactory.setDelegate(provider16, DoubleCheck.provider(CastTile_Factory.create(daggerTitanGlobalRootComponent9.contextProvider, create3, this.visualStabilityProvider, this.mediaHostStatesManagerProvider, this.provideActivityStarterProvider, this.bindSystemClockProvider, daggerTitanGlobalRootComponent9.provideMainDelayableExecutorProvider, this.mediaDataManagerProvider, this.provideConfigurationControllerProvider, this.falsingCollectorImplProvider, this.falsingManagerProxyProvider, daggerTitanGlobalRootComponent9.dumpManagerProvider, this.mediaFlagsProvider)));
            Provider<MediaHierarchyManager> provider17 = DoubleCheck.provider(MediaHierarchyManager_Factory.create(this.this$0.contextProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardBypassControllerProvider, this.mediaCarouselControllerProvider, this.notificationLockscreenUserManagerGoogleProvider, this.provideConfigurationControllerProvider, this.wakefulnessLifecycleProvider, this.statusBarKeyguardViewManagerProvider, this.dreamOverlayStateControllerProvider));
            this.mediaHierarchyManagerProvider = provider17;
            Provider<MediaHost> provider18 = DoubleCheck.provider(new MediaModule_ProvidesKeyguardMediaHostFactory(provider17, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesKeyguardMediaHostProvider = provider18;
            this.keyguardMediaControllerProvider = DoubleCheck.provider(KeyguardMediaController_Factory.create(provider18, this.keyguardBypassControllerProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerGoogleProvider, this.this$0.contextProvider, this.provideConfigurationControllerProvider, this.mediaFlagsProvider));
            this.notificationSectionsFeatureManagerProvider = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(this.deviceConfigProxyProvider, this.this$0.contextProvider, 2));
            Provider<LogBuffer> provider19 = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.logBufferFactoryProvider, 3));
            this.provideNotificationSectionLogBufferProvider = provider19;
            this.notificationSectionsLoggerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(provider19, 2));
            this.mediaContainerControllerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(this.providerLayoutInflaterProvider, 2));
            C24555 r12 = new Provider<SectionHeaderControllerSubcomponent.Builder>() {
                public final Object get() {
                    return new SectionHeaderControllerSubcomponentBuilder();
                }
            };
            this.sectionHeaderControllerSubcomponentBuilderProvider = r12;
            Provider<SectionHeaderControllerSubcomponent> provider20 = DoubleCheck.provider(new C1291xb614d321(r12));
            this.providesIncomingHeaderSubcomponentProvider = provider20;
            this.providesIncomingHeaderControllerProvider = new DozeAuthRemover_Factory(provider20, 2);
            Provider<SectionHeaderControllerSubcomponent> provider21 = DoubleCheck.provider(new C1292x39c1fe98(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesPeopleHeaderSubcomponentProvider = provider21;
            this.providesPeopleHeaderControllerProvider = new ColorChangeHandler_Factory(provider21, 3);
            Provider<SectionHeaderControllerSubcomponent> provider22 = DoubleCheck.provider(new C1290x3fd4641(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesAlertingHeaderSubcomponentProvider = provider22;
            this.providesAlertingHeaderControllerProvider = new TypeClassifier_Factory(provider22, 5);
            Provider<SectionHeaderControllerSubcomponent> provider23 = DoubleCheck.provider(new C1293x34a20792(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesSilentHeaderSubcomponentProvider = provider23;
            FrameworkServicesModule_ProvideFaceManagerFactory frameworkServicesModule_ProvideFaceManagerFactory = new FrameworkServicesModule_ProvideFaceManagerFactory(provider23, 1);
            this.providesSilentHeaderControllerProvider = frameworkServicesModule_ProvideFaceManagerFactory;
            QuickQSPanelController_Factory create$1 = QuickQSPanelController_Factory.create$1(this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.keyguardMediaControllerProvider, this.notificationSectionsFeatureManagerProvider, this.notificationSectionsLoggerProvider, this.notifPipelineFlagsProvider, this.mediaContainerControllerProvider, this.providesIncomingHeaderControllerProvider, this.providesPeopleHeaderControllerProvider, this.providesAlertingHeaderControllerProvider, frameworkServicesModule_ProvideFaceManagerFactory);
            this.notificationSectionsManagerProvider = create$1;
            Provider<AmbientState> provider24 = DoubleCheck.provider(new AmbientState_Factory(this.this$0.contextProvider, create$1, this.keyguardBypassControllerProvider));
            this.ambientStateProvider = provider24;
            Provider<StatusBarStateControllerImpl> provider25 = this.statusBarStateControllerImplProvider;
            Provider<LSShadeTransitionLogger> provider26 = this.lSShadeTransitionLoggerProvider;
            Provider<KeyguardBypassController> provider27 = this.keyguardBypassControllerProvider;
            Provider<NotificationLockscreenUserManagerGoogle> provider28 = this.notificationLockscreenUserManagerGoogleProvider;
            Provider provider29 = this.falsingCollectorImplProvider;
            Provider<MediaHierarchyManager> provider30 = this.mediaHierarchyManagerProvider;
            Provider<ScrimController> provider31 = this.scrimControllerProvider;
            Provider<NotificationShadeDepthController> provider32 = this.notificationShadeDepthControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.lockscreenShadeTransitionControllerProvider = DoubleCheck.provider(LockscreenShadeTransitionController_Factory.create(provider25, provider26, provider27, provider28, provider29, provider24, provider30, provider31, provider32, daggerTitanGlobalRootComponent10.contextProvider, this.wakefulnessLifecycleProvider, this.provideConfigurationControllerProvider, this.falsingManagerProxyProvider, daggerTitanGlobalRootComponent10.dumpManagerProvider));
            Provider<VibratorHelper> provider33 = DoubleCheck.provider(new VibratorHelper_Factory(this.this$0.provideVibratorProvider, this.provideBackgroundExecutorProvider, 0));
            this.vibratorHelperProvider = provider33;
            this.udfpsHapticsSimulatorProvider = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(this.commandRegistryProvider, provider33, this.keyguardUpdateMonitorProvider, 0));
            Provider<Executor> provider34 = DoubleCheck.provider(new TypeClassifier_Factory(ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE, 1));
            this.providesPluginExecutorProvider = provider34;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            Provider<UdfpsHbmController> provider35 = DoubleCheck.provider(new UdfpsHbmController_Factory(daggerTitanGlobalRootComponent11.contextProvider, daggerTitanGlobalRootComponent11.provideExecutionProvider, daggerTitanGlobalRootComponent11.provideMainHandlerProvider, provider34, this.authControllerProvider, daggerTitanGlobalRootComponent11.provideDisplayManagerProvider));
            this.udfpsHbmControllerProvider = provider35;
            this.optionalOfUdfpsHbmProvider = new PresentJdkOptionalInstanceProvider(provider35);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            Provider<ScreenLifecycle> provider36 = daggerTitanGlobalRootComponent12.screenLifecycleProvider;
            Provider<VibratorHelper> provider37 = this.vibratorHelperProvider;
            Provider<UdfpsHapticsSimulator> provider38 = this.udfpsHapticsSimulatorProvider;
            Provider<Optional<UdfpsHbmProvider>> provider39 = this.optionalOfUdfpsHbmProvider;
            Provider<KeyguardStateControllerImpl> provider40 = this.keyguardStateControllerImplProvider;
            Provider<KeyguardBypassController> provider41 = this.keyguardBypassControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            this.udfpsControllerProvider = DoubleCheck.provider(UdfpsController_Factory.create(daggerTitanGlobalRootComponent12.contextProvider, daggerTitanGlobalRootComponent12.provideExecutionProvider, this.providerLayoutInflaterProvider, daggerTitanGlobalRootComponent12.providesFingerprintManagerProvider, daggerTitanGlobalRootComponent12.provideWindowManagerProvider, this.statusBarStateControllerImplProvider, daggerTitanGlobalRootComponent12.provideMainDelayableExecutorProvider, this.panelExpansionStateManagerProvider, this.statusBarKeyguardViewManagerProvider, daggerTitanGlobalRootComponent12.dumpManagerProvider, this.keyguardUpdateMonitorProvider, this.falsingManagerProxyProvider, daggerTitanGlobalRootComponent12.providePowerManagerProvider, daggerTitanGlobalRootComponent12.provideAccessibilityManagerProvider, this.lockscreenShadeTransitionControllerProvider, provider36, provider37, provider38, provider39, provider40, provider41, daggerTitanGlobalRootComponent13.provideDisplayManagerProvider, daggerTitanGlobalRootComponent13.provideMainHandlerProvider, this.provideConfigurationControllerProvider, this.bindSystemClockProvider, this.unlockedScreenOffAnimationControllerProvider, this.systemUIDialogManagerProvider, daggerTitanGlobalRootComponent13.provideLatencyTrackerProvider, this.provideActivityLaunchAnimatorProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            Provider<SidefpsController> provider42 = DoubleCheck.provider(NfcTile_Factory.create(daggerTitanGlobalRootComponent14.contextProvider, this.providerLayoutInflaterProvider, daggerTitanGlobalRootComponent14.providesFingerprintManagerProvider, daggerTitanGlobalRootComponent14.provideWindowManagerProvider, daggerTitanGlobalRootComponent14.provideActivityTaskManagerProvider, this.overviewProxyServiceProvider, daggerTitanGlobalRootComponent14.provideDisplayManagerProvider, daggerTitanGlobalRootComponent14.provideMainDelayableExecutorProvider, daggerTitanGlobalRootComponent14.provideMainHandlerProvider));
            this.sidefpsControllerProvider = provider42;
            Provider<AuthController> provider43 = this.authControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            DelegateFactory.setDelegate(provider43, DoubleCheck.provider(AuthController_Factory.create(daggerTitanGlobalRootComponent15.contextProvider, daggerTitanGlobalRootComponent15.provideExecutionProvider, this.provideCommandQueueProvider, daggerTitanGlobalRootComponent15.provideActivityTaskManagerProvider, daggerTitanGlobalRootComponent15.provideWindowManagerProvider, daggerTitanGlobalRootComponent15.providesFingerprintManagerProvider, daggerTitanGlobalRootComponent15.provideFaceManagerProvider, this.udfpsControllerProvider, provider42, daggerTitanGlobalRootComponent15.provideDisplayManagerProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, daggerTitanGlobalRootComponent15.provideMainHandlerProvider)));
            Provider<KeyguardUpdateMonitor> provider44 = this.keyguardUpdateMonitorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            DelegateFactory.setDelegate(provider44, DoubleCheck.provider(KeyguardUpdateMonitor_Factory.create(daggerTitanGlobalRootComponent16.contextProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent16.dumpManagerProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent16.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, this.provideLockPatternUtilsProvider, this.authControllerProvider, this.telephonyListenerManagerProvider, daggerTitanGlobalRootComponent16.provideInteractionJankMonitorProvider, daggerTitanGlobalRootComponent16.provideLatencyTrackerProvider)));
            Provider<SmartSpaceController> provider45 = this.smartSpaceControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            DelegateFactory.setDelegate(provider45, DoubleCheck.provider(new SmartSpaceController_Factory(daggerTitanGlobalRootComponent17.contextProvider, this.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent17.provideMainHandlerProvider, daggerTitanGlobalRootComponent17.provideAlarmManagerProvider, daggerTitanGlobalRootComponent17.dumpManagerProvider)));
            this.wallpaperNotifierProvider = new KeyguardSecurityModel_Factory(this.this$0.contextProvider, this.provideCommonNotifCollectionProvider, this.providesBroadcastDispatcherProvider, 1);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            this.statusBarIconControllerImplProvider = DoubleCheck.provider(StatusBarIconControllerImpl_Factory.create(daggerTitanGlobalRootComponent18.contextProvider, this.provideCommandQueueProvider, this.provideDemoModeControllerProvider, this.provideConfigurationControllerProvider, this.tunerServiceImplProvider, daggerTitanGlobalRootComponent18.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            Provider<Context> provider46 = daggerTitanGlobalRootComponent19.contextProvider;
            WakeLock_Builder_Factory wakeLock_Builder_Factory = r5;
            WakeLock_Builder_Factory wakeLock_Builder_Factory2 = new WakeLock_Builder_Factory(provider46);
            this.builderProvider3 = wakeLock_Builder_Factory2;
            Provider<KeyguardIndicationControllerGoogle> provider47 = DoubleCheck.provider(KeyguardIndicationControllerGoogle_Factory.create(provider46, wakeLock_Builder_Factory, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.provideDockManagerProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent19.provideDevicePolicyManagerProvider, daggerTitanGlobalRootComponent19.provideIBatteryStatsProvider, daggerTitanGlobalRootComponent19.provideUserManagerProvider, this.tunerServiceImplProvider, this.deviceConfigProxyProvider, daggerTitanGlobalRootComponent19.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.falsingManagerProxyProvider, this.provideLockPatternUtilsProvider, daggerTitanGlobalRootComponent19.screenLifecycleProvider, this.this$0.provideIActivityManagerProvider, this.keyguardBypassControllerProvider));
            this.keyguardIndicationControllerGoogleProvider = provider47;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            Provider<ReverseChargingViewController> provider48 = DoubleCheck.provider(new ReverseChargingViewController_Factory(daggerTitanGlobalRootComponent20.contextProvider, this.provideBatteryControllerProvider, this.statusBarGoogleProvider, this.statusBarIconControllerImplProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent20.provideMainExecutorProvider, provider47));
            this.reverseChargingViewControllerProvider = provider48;
            this.provideReverseChargingViewControllerOptionalProvider = DoubleCheck.provider(new DependencyProvider_ProviderLayoutInflaterFactory(this.provideBatteryControllerProvider, provider48, 2));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            this.notificationListenerProvider = DoubleCheck.provider(new NotificationListener_Factory(daggerTitanGlobalRootComponent21.contextProvider, daggerTitanGlobalRootComponent21.provideNotificationManagerProvider, this.bindSystemClockProvider, daggerTitanGlobalRootComponent21.provideMainExecutorProvider, daggerTitanGlobalRootComponent21.providesPluginManagerProvider));
            Provider<HighPriorityProvider> provider49 = DoubleCheck.provider(new UserCreator_Factory(this.peopleNotificationIdentifierImplProvider, this.provideGroupMembershipManagerProvider, 2));
            this.highPriorityProvider = provider49;
            this.notificationRankingManagerProvider = NotificationRankingManager_Factory.create(this.provideNotificationMediaManagerProvider, this.notificationGroupManagerLegacyProvider, this.provideHeadsUpManagerPhoneProvider, this.notificationFilterProvider, this.notificationEntryManagerLoggerProvider, this.notificationSectionsFeatureManagerProvider, this.peopleNotificationIdentifierImplProvider, provider49, this.keyguardEnvironmentImplProvider);
            this.targetSdkResolverProvider = DoubleCheck.provider(new MediaFlags_Factory(this.this$0.contextProvider, 2));
            ImageExporter_Factory imageExporter_Factory = new ImageExporter_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.groupCoalescerLoggerProvider = imageExporter_Factory;
            this.groupCoalescerProvider = new SystemEventChipAnimationController_Factory(this.this$0.provideMainDelayableExecutorProvider, this.bindSystemClockProvider, imageExporter_Factory, 1);
            C24566 r13 = new Provider<CoordinatorsSubcomponent.Factory>() {
                public final Object get() {
                    return new CoordinatorsSubcomponentFactory();
                }
            };
            this.coordinatorsSubcomponentFactoryProvider = r13;
            this.notifCoordinatorsProvider = DoubleCheck.provider(new CoordinatorsModule_NotifCoordinatorsFactory(r13));
            this.notifInflaterImplProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.notifInflationErrorManagerProvider, 3));
            this.sectionHeaderVisibilityProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.this$0.contextProvider, 3));
            this.shadeViewDifferLoggerProvider = new ToastLogger_Factory(this.provideNotificationsLogBufferProvider, 2);
            Provider<NotifViewBarn> provider50 = DoubleCheck.provider(NotifViewBarn_Factory.InstanceHolder.INSTANCE);
            this.notifViewBarnProvider = provider50;
            ShadeViewManager_Factory create4 = ShadeViewManager_Factory.create(this.this$0.contextProvider, this.mediaContainerControllerProvider, this.notificationSectionsFeatureManagerProvider, this.sectionHeaderVisibilityProvider, this.shadeViewDifferLoggerProvider, provider50);
            this.shadeViewManagerProvider = create4;
            InstanceFactory create5 = InstanceFactory.create(new ShadeViewManagerFactory_Impl(create4));
            this.shadeViewManagerFactoryProvider = create5;
            this.notifPipelineInitializerProvider = DoubleCheck.provider(NotifPipelineInitializer_Factory.create(this.notifPipelineProvider, this.groupCoalescerProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, this.renderStageManagerProvider, this.notifCoordinatorsProvider, this.notifInflaterImplProvider, this.this$0.dumpManagerProvider, create5, this.notifPipelineFlagsProvider));
            this.notifBindPipelineInitializerProvider = new NotifBindPipelineInitializer_Factory(this.notifBindPipelineProvider, this.rowContentBindStageProvider, 0);
            this.notificationGroupAlertTransferHelperProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(this.rowContentBindStageProvider, this.statusBarStateControllerImplProvider, this.notificationGroupManagerLegacyProvider, 3));
            LogModule_ProvidePrivacyLogBufferFactory logModule_ProvidePrivacyLogBufferFactory = new LogModule_ProvidePrivacyLogBufferFactory(this.provideNotificationHeadsUpLogBufferProvider, 4);
            this.headsUpViewBinderLoggerProvider = logModule_ProvidePrivacyLogBufferFactory;
            this.headsUpViewBinderProvider = DoubleCheck.provider(new SysuiColorExtractor_Factory(this.provideNotificationMessagingUtilProvider, this.rowContentBindStageProvider, logModule_ProvidePrivacyLogBufferFactory, 1));
            Provider<NotificationEntryManager> provider51 = this.provideNotificationEntryManagerProvider;
            Provider<VisualStabilityProvider> provider52 = this.visualStabilityProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent22 = this.this$0;
            Provider<VisualStabilityManager> provider53 = DoubleCheck.provider(NotificationsModule_ProvideVisualStabilityManagerFactory.create(provider51, provider52, daggerTitanGlobalRootComponent22.provideMainHandlerProvider, this.statusBarStateControllerImplProvider, this.wakefulnessLifecycleProvider, daggerTitanGlobalRootComponent22.dumpManagerProvider));
            this.provideVisualStabilityManagerProvider = provider53;
            this.headsUpControllerProvider = DoubleCheck.provider(HeadsUpController_Factory.create(this.headsUpViewBinderProvider, this.notificationInterruptStateProviderImplProvider, this.provideHeadsUpManagerPhoneProvider, this.provideNotificationRemoteInputManagerProvider, this.statusBarStateControllerImplProvider, provider53, this.notificationListenerProvider));
            QSLogger_Factory qSLogger_Factory = new QSLogger_Factory(this.provideNotifInteractionLogBufferProvider, 3);
            this.notificationClickerLoggerProvider = qSLogger_Factory;
            this.builderProvider4 = new NotificationClicker_Builder_Factory(qSLogger_Factory);
            this.animatedImageNotificationManagerProvider = DoubleCheck.provider(new TakeScreenshotService_Factory(this.provideCommonNotifCollectionProvider, this.bindEventManagerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent23 = this.this$0;
            this.peopleSpaceWidgetManagerProvider = DoubleCheck.provider(PeopleSpaceWidgetManager_Factory.create(daggerTitanGlobalRootComponent23.contextProvider, daggerTitanGlobalRootComponent23.provideLauncherAppsProvider, this.provideCommonNotifCollectionProvider, daggerTitanGlobalRootComponent23.providePackageManagerProvider, this.setBubblesProvider, daggerTitanGlobalRootComponent23.provideUserManagerProvider, daggerTitanGlobalRootComponent23.provideNotificationManagerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider));
            this.notificationsControllerImplProvider = DoubleCheck.provider(NotificationsControllerImpl_Factory.create(this.notifPipelineFlagsProvider, this.notificationListenerProvider, this.provideNotificationEntryManagerProvider, this.debugModeFilterProvider, this.notificationRankingManagerProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineProvider, this.notifLiveDataStoreImplProvider, this.targetSdkResolverProvider, this.notifPipelineInitializerProvider, this.notifBindPipelineInitializerProvider, this.provideDeviceProvisionedControllerProvider, this.notificationRowBinderImplProvider, this.bindEventManagerImplProvider, this.remoteInputUriControllerProvider, this.notificationGroupManagerLegacyProvider, this.notificationGroupAlertTransferHelperProvider, this.provideHeadsUpManagerPhoneProvider, this.headsUpControllerProvider, this.headsUpViewBinderProvider, this.builderProvider4, this.animatedImageNotificationManagerProvider, this.peopleSpaceWidgetManagerProvider));
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(this.notificationListenerProvider, 4);
            this.notificationsControllerStubProvider = smartActionsReceiver_Factory;
            this.provideNotificationsControllerProvider = DoubleCheck.provider(new ScrollCaptureClient_Factory(this.this$0.contextProvider, this.notificationsControllerImplProvider, smartActionsReceiver_Factory, 1));
            C24577 r14 = new Provider<FragmentService.FragmentCreator.Factory>() {
                public final Object get() {
                    return new FragmentCreatorFactory();
                }
            };
            this.fragmentCreatorFactoryProvider = r14;
            this.fragmentServiceProvider = DoubleCheck.provider(new FragmentService_Factory(r14, this.provideConfigurationControllerProvider, this.this$0.dumpManagerProvider));
            this.providesStatusBarWindowViewProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.providerLayoutInflaterProvider, 5));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent24 = this.this$0;
            Provider<StatusBarContentInsetsProvider> provider54 = DoubleCheck.provider(new StatusBarContentInsetsProvider_Factory(daggerTitanGlobalRootComponent24.contextProvider, this.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent24.dumpManagerProvider, 0));
            this.statusBarContentInsetsProvider = provider54;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent25 = this.this$0;
            this.statusBarWindowControllerProvider = DoubleCheck.provider(StatusBarWindowController_Factory.create(daggerTitanGlobalRootComponent25.contextProvider, this.providesStatusBarWindowViewProvider, daggerTitanGlobalRootComponent25.provideWindowManagerProvider, daggerTitanGlobalRootComponent25.provideIWindowManagerProvider, provider54, daggerTitanGlobalRootComponent25.provideResourcesProvider, daggerTitanGlobalRootComponent25.unfoldTransitionProgressProvider));
        }

        public final void initialize5(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            this.statusBarWindowStateControllerProvider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(this.this$0.provideDisplayIdProvider, this.provideCommandQueueProvider, 3));
            this.carrierConfigTrackerProvider = DoubleCheck.provider(new CarrierConfigTracker_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider));
            this.callbackHandlerProvider = new KeyboardUI_Factory(GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE, 6);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> provider = DoubleCheck.provider(new AccessPointControllerImpl_WifiPickerTrackerFactory_Factory(daggerTitanGlobalRootComponent.contextProvider, daggerTitanGlobalRootComponent.provideWifiManagerProvider, daggerTitanGlobalRootComponent.provideConnectivityManagagerProvider, daggerTitanGlobalRootComponent.provideMainHandlerProvider, this.provideBgHandlerProvider));
            this.wifiPickerTrackerFactoryProvider = provider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            this.provideAccessPointControllerImplProvider = DoubleCheck.provider(new StatusBarPolicyModule_ProvideAccessPointControllerImplFactory(daggerTitanGlobalRootComponent2.provideUserManagerProvider, this.provideUserTrackerProvider, daggerTitanGlobalRootComponent2.provideMainExecutorProvider, provider));
            Provider<LayoutInflater> provider2 = this.providerLayoutInflaterProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.toastFactoryProvider = DoubleCheck.provider(new ToastFactory_Factory(provider2, daggerTitanGlobalRootComponent3.providesPluginManagerProvider, daggerTitanGlobalRootComponent3.dumpManagerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            Provider<LocationControllerImpl> provider3 = DoubleCheck.provider(LocationControllerImpl_Factory.create(daggerTitanGlobalRootComponent4.contextProvider, this.appOpsControllerImplProvider, this.deviceConfigProxyProvider, this.provideBgHandlerProvider, this.providesBroadcastDispatcherProvider, this.bootCompleteCacheImplProvider, this.provideUserTrackerProvider, daggerTitanGlobalRootComponent4.providePackageManagerProvider, daggerTitanGlobalRootComponent4.provideUiEventLoggerProvider, this.secureSettingsImplProvider));
            Provider<LocationControllerImpl> provider4 = provider3;
            this.locationControllerImplProvider = provider3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            InternetDialogController_Factory create = InternetDialogController_Factory.create(daggerTitanGlobalRootComponent5.contextProvider, daggerTitanGlobalRootComponent5.provideUiEventLoggerProvider, this.provideActivityStarterProvider, this.provideAccessPointControllerImplProvider, daggerTitanGlobalRootComponent5.provideSubcriptionManagerProvider, daggerTitanGlobalRootComponent5.provideTelephonyManagerProvider, daggerTitanGlobalRootComponent5.provideWifiManagerProvider, daggerTitanGlobalRootComponent5.provideConnectivityManagagerProvider, daggerTitanGlobalRootComponent5.provideMainHandlerProvider, daggerTitanGlobalRootComponent5.provideMainExecutorProvider, this.providesBroadcastDispatcherProvider, this.keyguardUpdateMonitorProvider, this.globalSettingsImplProvider, this.keyguardStateControllerImplProvider, daggerTitanGlobalRootComponent5.provideWindowManagerProvider, this.toastFactoryProvider, this.provideBgHandlerProvider, this.carrierConfigTrackerProvider, provider4, this.provideDialogLaunchAnimatorProvider);
            this.internetDialogControllerProvider = create;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            Provider<InternetDialogFactory> provider5 = DoubleCheck.provider(InternetDialogFactory_Factory.create(daggerTitanGlobalRootComponent6.provideMainHandlerProvider, this.provideBackgroundExecutorProvider, create, daggerTitanGlobalRootComponent6.contextProvider, daggerTitanGlobalRootComponent6.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.keyguardStateControllerImplProvider));
            Provider<InternetDialogFactory> provider6 = provider5;
            this.internetDialogFactoryProvider = provider5;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            this.networkControllerImplProvider = DoubleCheck.provider(NetworkControllerImpl_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, this.provideBgLooperProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent7.provideSubcriptionManagerProvider, this.callbackHandlerProvider, this.provideDeviceProvisionedControllerProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent7.provideConnectivityManagagerProvider, daggerTitanGlobalRootComponent7.provideTelephonyManagerProvider, this.telephonyListenerManagerProvider, daggerTitanGlobalRootComponent7.provideWifiManagerProvider, daggerTitanGlobalRootComponent7.provideNetworkScoreManagerProvider, this.provideAccessPointControllerImplProvider, this.provideDemoModeControllerProvider, this.carrierConfigTrackerProvider, daggerTitanGlobalRootComponent7.provideMainHandlerProvider, provider6, this.featureFlagsReleaseProvider, daggerTitanGlobalRootComponent7.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            Provider<SecurityControllerImpl> provider7 = DoubleCheck.provider(new SecurityControllerImpl_Factory(daggerTitanGlobalRootComponent8.contextProvider, this.provideBgHandlerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent8.dumpManagerProvider));
            this.securityControllerImplProvider = provider7;
            this.statusBarSignalPolicyProvider = DoubleCheck.provider(StatusBarSignalPolicy_Factory.create(this.this$0.contextProvider, this.statusBarIconControllerImplProvider, this.carrierConfigTrackerProvider, this.networkControllerImplProvider, provider7, this.tunerServiceImplProvider, this.featureFlagsReleaseProvider));
            this.notificationWakeUpCoordinatorProvider = DoubleCheck.provider(ForegroundServiceNotificationListener_Factory.create$1(this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.dozeParametersProvider, this.screenOffAnimationControllerProvider));
            Provider<NotificationRoundnessManager> m = C0769xb6bb24d7.m50m(this.notificationSectionsFeatureManagerProvider, 4);
            this.notificationRoundnessManagerProvider = m;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            this.pulseExpansionHandlerProvider = DoubleCheck.provider(PulseExpansionHandler_Factory.create(daggerTitanGlobalRootComponent9.contextProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideHeadsUpManagerPhoneProvider, m, this.provideConfigurationControllerProvider, this.statusBarStateControllerImplProvider, this.falsingManagerProxyProvider, this.lockscreenShadeTransitionControllerProvider, this.falsingCollectorImplProvider, daggerTitanGlobalRootComponent9.dumpManagerProvider));
            this.dynamicPrivacyControllerProvider = DoubleCheck.provider(new DismissTimer_Factory(this.notificationLockscreenUserManagerGoogleProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, 1));
            StatusBarInitializer_Factory statusBarInitializer_Factory = new StatusBarInitializer_Factory(this.provideNotificationsLogBufferProvider, 2);
            this.shadeEventCoordinatorLoggerProvider = statusBarInitializer_Factory;
            Provider<ShadeEventCoordinator> provider8 = DoubleCheck.provider(new ShadeEventCoordinator_Factory(this.this$0.provideMainExecutorProvider, statusBarInitializer_Factory, 0));
            this.shadeEventCoordinatorProvider = provider8;
            ForegroundServicesDialog_Factory foregroundServicesDialog_Factory = new ForegroundServicesDialog_Factory(this.provideNotificationEntryManagerProvider, 4);
            this.legacyNotificationPresenterExtensionsProvider = foregroundServicesDialog_Factory;
            this.provideNotifShadeEventSourceProvider = DoubleCheck.provider(new StatusBarContentInsetsProvider_Factory(this.notifPipelineFlagsProvider, provider8, foregroundServicesDialog_Factory, 1));
            Provider<INotificationManager> provider9 = DoubleCheck.provider(new ActivityIntentHelper_Factory(dependencyProvider3, 6));
            this.provideINotificationManagerProvider = provider9;
            this.channelEditorDialogControllerProvider = DoubleCheck.provider(new ChannelEditorDialogController_Factory(this.this$0.contextProvider, provider9));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.assistantFeedbackControllerProvider = DoubleCheck.provider(new ScreenOnCoordinator_Factory(daggerTitanGlobalRootComponent10.provideMainHandlerProvider, daggerTitanGlobalRootComponent10.contextProvider, this.deviceConfigProxyProvider, 2));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            Provider<ZenModeControllerImpl> provider10 = DoubleCheck.provider(MediaDataFilter_Factory.create$2(daggerTitanGlobalRootComponent11.contextProvider, daggerTitanGlobalRootComponent11.provideMainHandlerProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent11.dumpManagerProvider, this.globalSettingsImplProvider));
            Provider<ZenModeControllerImpl> provider11 = provider10;
            this.zenModeControllerImplProvider = provider10;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            this.provideBubblesManagerProvider = DoubleCheck.provider(SystemUIModule_ProvideBubblesManagerFactory.create(daggerTitanGlobalRootComponent12.contextProvider, this.setBubblesProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarStateControllerImplProvider, this.shadeControllerImplProvider, this.provideConfigurationControllerProvider, daggerTitanGlobalRootComponent12.provideIStatusBarServiceProvider, this.provideINotificationManagerProvider, this.provideNotificationVisibilityProvider, this.notificationInterruptStateProviderImplProvider, provider11, this.notificationLockscreenUserManagerGoogleProvider, this.notificationGroupManagerLegacyProvider, this.provideNotificationEntryManagerProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineProvider, this.provideSysUiStateProvider, this.notifPipelineFlagsProvider, daggerTitanGlobalRootComponent12.dumpManagerProvider, daggerTitanGlobalRootComponent12.provideMainExecutorProvider));
            Provider<NotifPanelEventSource> provider12 = DoubleCheck.provider(NotifPanelEventSourceModule_ProvideManagerFactory.InstanceHolder.INSTANCE);
            this.bindEventSourceProvider = provider12;
            Provider<VisualStabilityCoordinator> provider13 = DoubleCheck.provider(VisualStabilityCoordinator_Factory.create(this.provideDelayableExecutorProvider, this.this$0.dumpManagerProvider, this.provideHeadsUpManagerPhoneProvider, provider12, this.statusBarStateControllerImplProvider, this.visualStabilityProvider, this.wakefulnessLifecycleProvider));
            this.visualStabilityCoordinatorProvider = provider13;
            Provider<OnUserInteractionCallback> provider14 = DoubleCheck.provider(OverlayToggleTile_Factory.create(this.notifPipelineFlagsProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, this.notifCollectionProvider, this.provideNotificationVisibilityProvider, provider13, this.provideNotificationEntryManagerProvider, this.provideVisualStabilityManagerProvider, this.provideGroupMembershipManagerProvider));
            Provider<OnUserInteractionCallback> provider15 = provider14;
            this.provideOnUserInteractionCallbackProvider = provider14;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            this.provideNotificationGutsManagerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationGutsManagerFactory.create(daggerTitanGlobalRootComponent13.contextProvider, this.optionalOfStatusBarProvider, daggerTitanGlobalRootComponent13.provideMainHandlerProvider, this.provideBgHandlerProvider, daggerTitanGlobalRootComponent13.provideAccessibilityManagerProvider, this.highPriorityProvider, this.provideINotificationManagerProvider, this.provideNotificationEntryManagerProvider, this.peopleSpaceWidgetManagerProvider, daggerTitanGlobalRootComponent13.provideLauncherAppsProvider, daggerTitanGlobalRootComponent13.provideShortcutManagerProvider, this.channelEditorDialogControllerProvider, this.provideUserTrackerProvider, this.assistantFeedbackControllerProvider, this.provideBubblesManagerProvider, daggerTitanGlobalRootComponent13.provideUiEventLoggerProvider, provider15, this.shadeControllerImplProvider, daggerTitanGlobalRootComponent13.dumpManagerProvider));
            this.expansionStateLoggerProvider = new NotificationLogger_ExpansionStateLogger_Factory(this.provideUiBackgroundExecutorProvider);
            Provider<NotificationPanelLogger> provider16 = DoubleCheck.provider(NotificationsModule_ProvideNotificationPanelLoggerFactory.InstanceHolder.INSTANCE);
            this.provideNotificationPanelLoggerProvider = provider16;
            this.provideNotificationLoggerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationLoggerFactory.create(this.notificationListenerProvider, this.provideUiBackgroundExecutorProvider, this.notifPipelineFlagsProvider, this.notifLiveDataStoreImplProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.statusBarStateControllerImplProvider, this.expansionStateLoggerProvider, provider16));
            this.foregroundServiceSectionControllerProvider = DoubleCheck.provider(new DependencyProvider_ProvidesViewMediatorCallbackFactory(this.provideNotificationEntryManagerProvider, this.foregroundServiceDismissalFeatureControllerProvider, 1));
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = r4;
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory2 = new QSFragmentModule_ProvidesQuickQSPanelFactory(this.rowContentBindStageProvider, 4);
            this.dynamicChildBindControllerProvider = qSFragmentModule_ProvidesQuickQSPanelFactory2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            this.provideNotificationViewHierarchyManagerProvider = DoubleCheck.provider(C1210x3f8faa0a.create(daggerTitanGlobalRootComponent14.contextProvider, daggerTitanGlobalRootComponent14.provideMainHandlerProvider, this.featureFlagsReleaseProvider, this.notificationLockscreenUserManagerGoogleProvider, this.notificationGroupManagerLegacyProvider, this.provideVisualStabilityManagerProvider, this.statusBarStateControllerImplProvider, this.provideNotificationEntryManagerProvider, this.keyguardBypassControllerProvider, this.setBubblesProvider, this.dynamicPrivacyControllerProvider, this.foregroundServiceSectionControllerProvider, qSFragmentModule_ProvidesQuickQSPanelFactory, this.lowPriorityInflationHelperProvider, this.assistantFeedbackControllerProvider, this.notifPipelineFlagsProvider, this.keyguardUpdateMonitorProvider, this.keyguardStateControllerImplProvider));
            this.provideAccessibilityFloatingMenuControllerProvider = DoubleCheck.provider(new C0774xb22f7179(dependencyProvider, this.this$0.contextProvider, this.accessibilityButtonTargetsObserverProvider, this.accessibilityButtonModeObserverProvider, this.keyguardUpdateMonitorProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            this.lockscreenWallpaperProvider = DoubleCheck.provider(LockscreenWallpaper_Factory.create(daggerTitanGlobalRootComponent15.provideWallpaperManagerProvider, this.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent15.dumpManagerProvider, this.provideNotificationMediaManagerProvider, daggerTitanGlobalRootComponent15.provideMainHandlerProvider));
            Provider<NotificationIconAreaController> provider17 = DoubleCheck.provider(NotificationIconAreaController_Factory.create(this.this$0.contextProvider, this.statusBarStateControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideNotificationMediaManagerProvider, this.notificationListenerProvider, this.dozeParametersProvider, this.setBubblesProvider, this.provideDemoModeControllerProvider, this.darkIconDispatcherImplProvider, this.statusBarWindowControllerProvider, this.screenOffAnimationControllerProvider));
            this.notificationIconAreaControllerProvider = provider17;
            this.dozeServiceHostProvider = DoubleCheck.provider(DozeServiceHost_Factory.create(this.dozeLogProvider, this.this$0.providePowerManagerProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, this.provideDeviceProvisionedControllerProvider, this.provideHeadsUpManagerPhoneProvider, this.provideBatteryControllerProvider, this.scrimControllerProvider, this.biometricUnlockControllerProvider, this.newKeyguardViewMediatorProvider, this.assistManagerGoogleProvider, this.dozeScrimControllerProvider, this.keyguardUpdateMonitorProvider, this.pulseExpansionHandlerProvider, this.notificationShadeWindowControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.authControllerProvider, provider17));
            this.screenPinningRequestProvider = new ScreenPinningRequest_Factory(this.this$0.contextProvider, this.optionalOfStatusBarProvider, 0);
            Provider<RingerModeTrackerImpl> provider18 = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(this.this$0.provideAudioManagerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, 0));
            this.ringerModeTrackerImplProvider = provider18;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            this.volumeDialogControllerImplProvider = DoubleCheck.provider(VolumeDialogControllerImpl_Factory.create(daggerTitanGlobalRootComponent16.contextProvider, this.providesBroadcastDispatcherProvider, provider18, daggerTitanGlobalRootComponent16.provideAudioManagerProvider, daggerTitanGlobalRootComponent16.provideNotificationManagerProvider, this.vibratorHelperProvider, daggerTitanGlobalRootComponent16.provideIAudioServiceProvider, daggerTitanGlobalRootComponent16.provideAccessibilityManagerProvider, daggerTitanGlobalRootComponent16.providePackageManagerProvider, this.wakefulnessLifecycleProvider));
            Provider<AccessibilityManagerWrapper> m2 = C0771xb6bb24d9.m52m(this.this$0.provideAccessibilityManagerProvider, 5);
            this.accessibilityManagerWrapperProvider = m2;
            VolumeModule_ProvideVolumeDialogFactory create2 = VolumeModule_ProvideVolumeDialogFactory.create(this.this$0.contextProvider, this.volumeDialogControllerImplProvider, m2, this.provideDeviceProvisionedControllerProvider, this.provideConfigurationControllerProvider, this.mediaOutputDialogFactoryProvider, this.provideActivityStarterProvider);
            this.provideVolumeDialogProvider = create2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            this.volumeDialogComponentProvider = DoubleCheck.provider(VolumeDialogComponent_Factory.create(daggerTitanGlobalRootComponent17.contextProvider, this.newKeyguardViewMediatorProvider, this.provideActivityStarterProvider, this.volumeDialogControllerImplProvider, this.provideDemoModeControllerProvider, daggerTitanGlobalRootComponent17.pluginDependencyProvider, this.extensionControllerImplProvider, this.tunerServiceImplProvider, create2));
            this.statusBarComponentFactoryProvider = new Provider<StatusBarComponent.Factory>() {
                public final Object get() {
                    return new StatusBarComponentFactory();
                }
            };
            Provider<GroupExpansionManager> provider19 = DoubleCheck.provider(new TvStatusBar_Factory(this.notifPipelineFlagsProvider, this.provideGroupMembershipManagerProvider, this.notificationGroupManagerLegacyProvider, 1));
            this.provideGroupExpansionManagerProvider = provider19;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            this.statusBarRemoteInputCallbackProvider = DoubleCheck.provider(StatusBarRemoteInputCallback_Factory.create(daggerTitanGlobalRootComponent18.contextProvider, provider19, this.notificationLockscreenUserManagerGoogleProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.provideActivityStarterProvider, this.shadeControllerImplProvider, this.provideCommandQueueProvider, this.actionClickLoggerProvider, daggerTitanGlobalRootComponent18.provideMainExecutorProvider));
            this.activityIntentHelperProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.this$0.contextProvider, 0));
            ColorChangeHandler_Factory colorChangeHandler_Factory = r4;
            ColorChangeHandler_Factory colorChangeHandler_Factory2 = new ColorChangeHandler_Factory(this.provideNotifInteractionLogBufferProvider, 4);
            this.statusBarNotificationActivityStarterLoggerProvider = colorChangeHandler_Factory2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            this.builderProvider5 = DoubleCheck.provider(StatusBarNotificationActivityStarter_Builder_Factory.create(daggerTitanGlobalRootComponent19.contextProvider, this.provideCommandQueueProvider, daggerTitanGlobalRootComponent19.provideMainHandlerProvider, this.provideUiBackgroundExecutorProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.provideNotificationVisibilityProvider, this.provideHeadsUpManagerPhoneProvider, this.provideActivityStarterProvider, this.notificationClickNotifierProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, daggerTitanGlobalRootComponent19.provideKeyguardManagerProvider, daggerTitanGlobalRootComponent19.provideIDreamManagerProvider, this.provideBubblesManagerProvider, this.assistManagerGoogleProvider, this.provideNotificationRemoteInputManagerProvider, this.provideGroupMembershipManagerProvider, this.notificationLockscreenUserManagerGoogleProvider, this.shadeControllerImplProvider, this.keyguardStateControllerImplProvider, this.notificationInterruptStateProviderImplProvider, this.provideLockPatternUtilsProvider, this.statusBarRemoteInputCallbackProvider, this.activityIntentHelperProvider, this.notifPipelineFlagsProvider, this.provideMetricsLoggerProvider, colorChangeHandler_Factory, this.provideOnUserInteractionCallbackProvider));
            this.initControllerProvider = DoubleCheck.provider(InitController_Factory.InstanceHolder.INSTANCE);
            this.provideTimeTickHandlerProvider = DoubleCheck.provider(new ScreenLifecycle_Factory(dependencyProvider3, 7));
            this.userInfoControllerImplProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.this$0.contextProvider, 3));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            this.castControllerImplProvider = DoubleCheck.provider(new CastControllerImpl_Factory(daggerTitanGlobalRootComponent20.contextProvider, daggerTitanGlobalRootComponent20.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            this.hotspotControllerImplProvider = DoubleCheck.provider(new HotspotControllerImpl_Factory(daggerTitanGlobalRootComponent21.contextProvider, daggerTitanGlobalRootComponent21.provideMainHandlerProvider, this.provideBgHandlerProvider, daggerTitanGlobalRootComponent21.dumpManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent22 = this.this$0;
            this.bluetoothControllerImplProvider = DoubleCheck.provider(new BluetoothControllerImpl_Factory(daggerTitanGlobalRootComponent22.contextProvider, daggerTitanGlobalRootComponent22.dumpManagerProvider, this.provideBgLooperProvider, this.provideLocalBluetoothControllerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent23 = this.this$0;
            this.nextAlarmControllerImplProvider = DoubleCheck.provider(new ToastFactory_Factory(daggerTitanGlobalRootComponent23.provideAlarmManagerProvider, this.providesBroadcastDispatcherProvider, daggerTitanGlobalRootComponent23.dumpManagerProvider, 1));
            RotationPolicyWrapperImpl_Factory rotationPolicyWrapperImpl_Factory = new RotationPolicyWrapperImpl_Factory(this.this$0.contextProvider, this.secureSettingsImplProvider, 0);
            this.rotationPolicyWrapperImplProvider = rotationPolicyWrapperImpl_Factory;
            this.bindRotationPolicyWrapperProvider = DoubleCheck.provider(rotationPolicyWrapperImpl_Factory);
            Provider<DeviceStateRotationLockSettingsManager> provider20 = DoubleCheck.provider(new StatusBarInitializer_Factory(this.this$0.contextProvider, 5));
            this.provideAutoRotateSettingsManagerProvider = provider20;
            Provider<RotationPolicyWrapper> provider21 = this.bindRotationPolicyWrapperProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent24 = this.this$0;
            this.deviceStateRotationLockSettingControllerProvider = DoubleCheck.provider(new KeyguardSmartspaceController_Factory(provider21, daggerTitanGlobalRootComponent24.provideDeviceStateManagerProvider, daggerTitanGlobalRootComponent24.provideMainExecutorProvider, provider20, 1));
            WallpaperController_Factory wallpaperController_Factory = new WallpaperController_Factory(this.this$0.provideResourcesProvider, 3);
            this.providesDeviceStateRotationLockDefaultsProvider = wallpaperController_Factory;
            this.rotationLockControllerImplProvider = DoubleCheck.provider(new SetupWizard_Factory(this.bindRotationPolicyWrapperProvider, this.deviceStateRotationLockSettingControllerProvider, wallpaperController_Factory, 2));
            this.provideDataSaverControllerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(dependencyProvider3, this.networkControllerImplProvider));
            this.provideSensorPrivacyControllerProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.this$0.provideSensorPrivacyManagerProvider, 7));
            this.dateFormatUtilProvider = new DateFormatUtil_Factory(this.this$0.contextProvider, 0);
            Provider<LogBuffer> provider22 = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.logBufferFactoryProvider, 0));
            this.providePrivacyLogBufferProvider = provider22;
            PrivacyLogger_Factory privacyLogger_Factory = new PrivacyLogger_Factory(provider22, 0);
            this.privacyLoggerProvider = privacyLogger_Factory;
            Provider<AppOpsControllerImpl> provider23 = this.appOpsControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent25 = this.this$0;
            this.privacyItemControllerProvider = DoubleCheck.provider(PrivacyItemController_Factory.create(provider23, daggerTitanGlobalRootComponent25.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.deviceConfigProxyProvider, this.provideUserTrackerProvider, privacyLogger_Factory, this.bindSystemClockProvider, daggerTitanGlobalRootComponent25.dumpManagerProvider));
            Provider<StatusBarIconControllerImpl> provider24 = this.statusBarIconControllerImplProvider;
            Provider<CommandQueue> provider25 = this.provideCommandQueueProvider;
            Provider<BroadcastDispatcher> provider26 = this.providesBroadcastDispatcherProvider;
            Provider<Executor> provider27 = this.provideUiBackgroundExecutorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent26 = this.this$0;
            this.phoneStatusBarPolicyProvider = PhoneStatusBarPolicy_Factory.create(provider24, provider25, provider26, provider27, daggerTitanGlobalRootComponent26.provideResourcesProvider, this.castControllerImplProvider, this.hotspotControllerImplProvider, this.bluetoothControllerImplProvider, this.nextAlarmControllerImplProvider, this.userInfoControllerImplProvider, this.rotationLockControllerImplProvider, this.provideDataSaverControllerProvider, this.zenModeControllerImplProvider, this.provideDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.locationControllerImplProvider, this.provideSensorPrivacyControllerProvider, daggerTitanGlobalRootComponent26.provideIActivityManagerProvider, daggerTitanGlobalRootComponent26.provideAlarmManagerProvider, daggerTitanGlobalRootComponent26.provideUserManagerProvider, daggerTitanGlobalRootComponent26.provideDevicePolicyManagerProvider, this.recordingControllerProvider, daggerTitanGlobalRootComponent26.provideTelecomManagerProvider, daggerTitanGlobalRootComponent26.provideDisplayIdProvider, this.provideSharePreferencesProvider, this.dateFormatUtilProvider, this.ringerModeTrackerImplProvider, this.privacyItemControllerProvider, this.privacyLoggerProvider);
            this.statusBarTouchableRegionManagerProvider = DoubleCheck.provider(new StatusBarTouchableRegionManager_Factory(this.this$0.contextProvider, this.notificationShadeWindowControllerImplProvider, this.provideConfigurationControllerProvider, this.provideHeadsUpManagerPhoneProvider));
            this.factoryProvider6 = new BrightnessSliderController_Factory_Factory(this.falsingManagerProxyProvider);
            this.ongoingCallLoggerProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(this.this$0.provideUiEventLoggerProvider, 2));
            Provider<LogBuffer> provider28 = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(this.logBufferFactoryProvider, 2));
            this.provideSwipeAwayGestureLogBufferProvider = provider28;
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(provider28, 3);
            this.swipeStatusBarAwayGestureLoggerProvider = smartActionsReceiver_Factory;
            this.swipeStatusBarAwayGestureHandlerProvider = DoubleCheck.provider(new SwipeStatusBarAwayGestureHandler_Factory(this.this$0.contextProvider, this.statusBarWindowControllerProvider, smartActionsReceiver_Factory));
            Provider<OngoingCallFlags> provider29 = DoubleCheck.provider(new MediaFlags_Factory(this.featureFlagsReleaseProvider, 3));
            this.ongoingCallFlagsProvider = provider29;
            Provider<CommonNotifCollection> provider30 = this.provideCommonNotifCollectionProvider;
            Provider<SystemClock> provider31 = this.bindSystemClockProvider;
            Provider<ActivityStarter> provider32 = this.provideActivityStarterProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent27 = this.this$0;
            this.provideOngoingCallControllerProvider = DoubleCheck.provider(StatusBarDependenciesModule_ProvideOngoingCallControllerFactory.create(provider30, provider31, provider32, daggerTitanGlobalRootComponent27.provideMainExecutorProvider, daggerTitanGlobalRootComponent27.provideIActivityManagerProvider, this.ongoingCallLoggerProvider, daggerTitanGlobalRootComponent27.dumpManagerProvider, this.statusBarWindowControllerProvider, this.swipeStatusBarAwayGestureHandlerProvider, this.statusBarStateControllerImplProvider, provider29));
            Provider<CommandQueue> provider33 = this.provideCommandQueueProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent28 = this.this$0;
            this.statusBarHideIconsForBouncerManagerProvider = DoubleCheck.provider(new QSFgsManagerFooter_Factory(provider33, daggerTitanGlobalRootComponent28.provideMainDelayableExecutorProvider, this.statusBarWindowStateControllerProvider, daggerTitanGlobalRootComponent28.dumpManagerProvider, 1));
            Provider<LogBuffer> provider34 = DoubleCheck.provider(new ForegroundServicesDialog_Factory(this.logBufferFactoryProvider, 8));
            this.provideNotifVoiceReplyLogBufferProvider = provider34;
            Provider<NotificationVoiceReplyLogger> provider35 = DoubleCheck.provider(new SystemUIDialogManager_Factory(provider34, this.this$0.provideUiEventLoggerProvider, 1));
            this.notificationVoiceReplyLoggerProvider = provider35;
            Provider<CommonNotifCollection> provider36 = this.provideCommonNotifCollectionProvider;
            Provider<BindEventManagerImpl> provider37 = this.bindEventManagerImplProvider;
            Provider<NotifLiveDataStoreImpl> provider38 = this.notifLiveDataStoreImplProvider;
            Provider<NotificationLockscreenUserManagerGoogle> provider39 = this.notificationLockscreenUserManagerGoogleProvider;
            Provider<NotificationRemoteInputManager> provider40 = this.provideNotificationRemoteInputManagerProvider;
            Provider<LockscreenShadeTransitionController> provider41 = this.lockscreenShadeTransitionControllerProvider;
            Provider<NotificationShadeWindowControllerImpl> provider42 = this.notificationShadeWindowControllerImplProvider;
            Provider<StatusBarKeyguardViewManager> provider43 = this.statusBarKeyguardViewManagerProvider;
            Provider<StatusBarGoogle> provider44 = this.statusBarGoogleProvider;
            Provider<StatusBarStateControllerImpl> provider45 = this.statusBarStateControllerImplProvider;
            Provider<HeadsUpManagerPhone> provider46 = this.provideHeadsUpManagerPhoneProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent29 = this.this$0;
            Provider<NotificationVoiceReplyController> provider47 = DoubleCheck.provider(NotificationVoiceReplyController_Factory.create(provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, daggerTitanGlobalRootComponent29.providePowerManagerProvider, daggerTitanGlobalRootComponent29.contextProvider, provider35));
            this.notificationVoiceReplyControllerProvider = provider47;
            Provider<DebugNotificationVoiceReplyClient> provider48 = DoubleCheck.provider(new DebugNotificationVoiceReplyClient_Factory(this.providesBroadcastDispatcherProvider, this.notificationLockscreenUserManagerGoogleProvider, provider47));
            this.debugNotificationVoiceReplyClientProvider = provider48;
            this.provideNotificationVoiceReplyClientProvider = DoubleCheck.provider(new PrivacyLogger_Factory(provider48, 6));
            this.providesMainMessageRouterProvider = new QSFragmentModule_ProvideThemedContextFactory(this.this$0.provideMainDelayableExecutorProvider, 4);
            Provider<CommandRegistry> provider49 = this.commandRegistryProvider;
            Provider<BatteryController> provider50 = this.provideBatteryControllerProvider;
            Provider<ConfigurationController> provider51 = this.provideConfigurationControllerProvider;
            Provider<FeatureFlagsRelease> provider52 = this.featureFlagsReleaseProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent30 = this.this$0;
            this.wiredChargingRippleControllerProvider = DoubleCheck.provider(WiredChargingRippleController_Factory.create(provider49, provider50, provider51, provider52, daggerTitanGlobalRootComponent30.contextProvider, daggerTitanGlobalRootComponent30.provideWindowManagerProvider, this.bindSystemClockProvider, daggerTitanGlobalRootComponent30.provideUiEventLoggerProvider));
            Provider<StatusBarGoogle> provider53 = this.statusBarGoogleProvider;
            Provider<SmartSpaceController> provider54 = this.smartSpaceControllerProvider;
            Provider<WallpaperNotifier> provider55 = this.wallpaperNotifierProvider;
            Provider<Optional<ReverseChargingViewController>> provider56 = this.provideReverseChargingViewControllerOptionalProvider;
            Provider<Context> provider57 = this.this$0.contextProvider;
            Provider<NotificationsController> provider58 = this.provideNotificationsControllerProvider;
            Provider<FragmentService> provider59 = this.fragmentServiceProvider;
            Provider<LightBarController> provider60 = this.lightBarControllerProvider;
            Provider<AutoHideController> provider61 = this.provideAutoHideControllerProvider;
            Provider<StatusBarWindowController> provider62 = this.statusBarWindowControllerProvider;
            Provider<StatusBarWindowStateController> provider63 = this.statusBarWindowStateControllerProvider;
            Provider<KeyguardUpdateMonitor> provider64 = this.keyguardUpdateMonitorProvider;
            Provider<StatusBarSignalPolicy> provider65 = this.statusBarSignalPolicyProvider;
            Provider<PulseExpansionHandler> provider66 = this.pulseExpansionHandlerProvider;
            Provider<NotificationWakeUpCoordinator> provider67 = this.notificationWakeUpCoordinatorProvider;
            Provider<KeyguardBypassController> provider68 = this.keyguardBypassControllerProvider;
            Provider<KeyguardStateControllerImpl> provider69 = this.keyguardStateControllerImplProvider;
            Provider<HeadsUpManagerPhone> provider70 = this.provideHeadsUpManagerPhoneProvider;
            Provider<DynamicPrivacyController> provider71 = this.dynamicPrivacyControllerProvider;
            Provider<FalsingManagerProxy> provider72 = this.falsingManagerProxyProvider;
            Provider provider73 = this.falsingCollectorImplProvider;
            Provider<BroadcastDispatcher> provider74 = this.providesBroadcastDispatcherProvider;
            Provider<NotifShadeEventSource> provider75 = this.provideNotifShadeEventSourceProvider;
            Provider<NotificationEntryManager> provider76 = this.provideNotificationEntryManagerProvider;
            Provider<NotificationGutsManager> provider77 = this.provideNotificationGutsManagerProvider;
            Provider<NotificationLogger> provider78 = this.provideNotificationLoggerProvider;
            Provider<NotificationInterruptStateProviderImpl> provider79 = this.notificationInterruptStateProviderImplProvider;
            Provider<NotificationViewHierarchyManager> provider80 = this.provideNotificationViewHierarchyManagerProvider;
            Provider<PanelExpansionStateManager> provider81 = this.panelExpansionStateManagerProvider;
            Provider<KeyguardViewMediator> provider82 = this.newKeyguardViewMediatorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent31 = this.this$0;
            Provider<ScreenLifecycle> provider83 = daggerTitanGlobalRootComponent31.screenLifecycleProvider;
            Provider<WakefulnessLifecycle> provider84 = this.wakefulnessLifecycleProvider;
            Provider<StatusBarStateControllerImpl> provider85 = this.statusBarStateControllerImplProvider;
            Provider<Optional<BubblesManager>> provider86 = this.provideBubblesManagerProvider;
            Provider<Optional<Bubbles>> provider87 = this.setBubblesProvider;
            Provider<VisualStabilityManager> provider88 = this.provideVisualStabilityManagerProvider;
            Provider<DeviceProvisionedController> provider89 = this.provideDeviceProvisionedControllerProvider;
            Provider<NavigationBarController> provider90 = this.navigationBarControllerProvider;
            Provider<AccessibilityFloatingMenuController> provider91 = this.provideAccessibilityFloatingMenuControllerProvider;
            Provider<AssistManagerGoogle> provider92 = this.assistManagerGoogleProvider;
            Provider<ConfigurationController> provider93 = this.provideConfigurationControllerProvider;
            Provider<NotificationShadeWindowControllerImpl> provider94 = this.notificationShadeWindowControllerImplProvider;
            Provider<DozeParameters> provider95 = this.dozeParametersProvider;
            Provider<ScrimController> provider96 = this.scrimControllerProvider;
            Provider<LockscreenWallpaper> provider97 = this.lockscreenWallpaperProvider;
            Provider<LockscreenGestureLogger> provider98 = this.lockscreenGestureLoggerProvider;
            Provider<BiometricUnlockController> provider99 = this.biometricUnlockControllerProvider;
            Provider<NotificationShadeDepthController> provider100 = this.notificationShadeDepthControllerProvider;
            Provider<DozeServiceHost> provider101 = this.dozeServiceHostProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent32 = this.this$0;
            Provider<PluginManager> provider102 = daggerTitanGlobalRootComponent32.providesPluginManagerProvider;
            Provider<Optional<LegacySplitScreen>> provider103 = this.setLegacySplitScreenProvider;
            Provider<StatusBarNotificationActivityStarter.Builder> provider104 = this.builderProvider5;
            Provider<ShadeControllerImpl> provider105 = this.shadeControllerImplProvider;
            Provider<StatusBarKeyguardViewManager> provider106 = this.statusBarKeyguardViewManagerProvider;
            Provider<ViewMediatorCallback> provider107 = this.providesViewMediatorCallbackProvider;
            Provider<InitController> provider108 = this.initControllerProvider;
            Provider<Handler> provider109 = this.provideTimeTickHandlerProvider;
            Provider<PluginDependencyProvider> provider110 = this.this$0.pluginDependencyProvider;
            Provider<KeyguardDismissUtil> provider111 = this.keyguardDismissUtilProvider;
            Provider<ExtensionControllerImpl> provider112 = this.extensionControllerImplProvider;
            Provider<UserInfoControllerImpl> provider113 = this.userInfoControllerImplProvider;
            Provider<PhoneStatusBarPolicy> provider114 = this.phoneStatusBarPolicyProvider;
            Provider<KeyguardIndicationControllerGoogle> provider115 = this.keyguardIndicationControllerGoogleProvider;
            Provider<DemoModeController> provider116 = this.provideDemoModeControllerProvider;
            Provider<StatusBarTouchableRegionManager> provider117 = this.statusBarTouchableRegionManagerProvider;
            Provider<NotificationIconAreaController> provider118 = this.notificationIconAreaControllerProvider;
            Provider<BrightnessSliderController.Factory> provider119 = this.factoryProvider6;
            Provider<ScreenOffAnimationController> provider120 = this.screenOffAnimationControllerProvider;
            Provider<WallpaperController> provider121 = this.wallpaperControllerProvider;
            Provider<OngoingCallController> provider122 = this.provideOngoingCallControllerProvider;
            Provider<StatusBarHideIconsForBouncerManager> provider123 = this.statusBarHideIconsForBouncerManagerProvider;
            Provider<LockscreenShadeTransitionController> provider124 = this.lockscreenShadeTransitionControllerProvider;
            Provider<FeatureFlagsRelease> provider125 = this.featureFlagsReleaseProvider;
            Provider<Optional<NotificationVoiceReplyClient>> provider126 = this.provideNotificationVoiceReplyClientProvider;
            Provider<KeyguardUnlockAnimationController> provider127 = this.keyguardUnlockAnimationControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent33 = this.this$0;
            DelegateFactory.setDelegate(provider53, DoubleCheck.provider(StatusBarGoogle_Factory.create(provider54, provider55, provider56, provider57, provider58, provider59, provider60, provider61, provider62, provider63, provider64, provider65, provider66, provider67, provider68, provider69, provider70, provider71, provider72, provider73, provider74, provider75, provider76, provider77, provider78, provider79, provider80, provider81, provider82, daggerTitanGlobalRootComponent31.provideDisplayMetricsProvider, this.provideMetricsLoggerProvider, this.provideUiBackgroundExecutorProvider, this.provideNotificationMediaManagerProvider, this.notificationLockscreenUserManagerGoogleProvider, this.provideNotificationRemoteInputManagerProvider, this.userSwitcherControllerProvider, this.networkControllerImplProvider, this.provideBatteryControllerProvider, this.sysuiColorExtractorProvider, provider83, provider84, provider85, provider86, provider87, provider88, provider89, provider90, provider91, provider92, provider93, provider94, provider95, provider96, provider97, provider98, provider99, provider100, provider101, daggerTitanGlobalRootComponent32.providePowerManagerProvider, this.screenPinningRequestProvider, this.dozeScrimControllerProvider, this.volumeDialogComponentProvider, this.provideCommandQueueProvider, this.statusBarComponentFactoryProvider, provider102, provider103, provider104, provider105, provider106, provider107, provider108, provider109, provider110, provider111, provider112, provider113, provider114, provider115, provider116, provider117, provider118, provider119, provider120, provider121, provider122, provider123, provider124, provider125, provider126, provider127, daggerTitanGlobalRootComponent33.provideMainHandlerProvider, daggerTitanGlobalRootComponent33.provideMainDelayableExecutorProvider, this.providesMainMessageRouterProvider, daggerTitanGlobalRootComponent33.provideWallpaperManagerProvider, this.setStartingSurfaceProvider, this.provideActivityLaunchAnimatorProvider, daggerTitanGlobalRootComponent33.provideAlarmManagerProvider, this.notifPipelineFlagsProvider, daggerTitanGlobalRootComponent33.provideInteractionJankMonitorProvider, daggerTitanGlobalRootComponent33.provideDeviceStateManagerProvider, this.dreamOverlayStateControllerProvider, this.wiredChargingRippleControllerProvider)));
            DelegateFactory.setDelegate(this.optionalOfStatusBarProvider, new PresentJdkOptionalInstanceProvider(this.statusBarGoogleProvider));
            Provider<ActivityStarterDelegate> provider128 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.optionalOfStatusBarProvider, 0));
            this.activityStarterDelegateProvider = provider128;
            DelegateFactory.setDelegate(this.provideActivityStarterProvider, new CommunalSourceMonitor_Factory(provider128, this.this$0.pluginDependencyProvider, 1));
            this.setTaskViewFactoryProvider = InstanceFactory.create(optional7);
            Provider<ControlsMetricsLoggerImpl> provider129 = DoubleCheck.provider(ControlsMetricsLoggerImpl_Factory.InstanceHolder.INSTANCE);
            this.controlsMetricsLoggerImplProvider = provider129;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent34 = this.this$0;
            this.controlActionCoordinatorImplProvider = DoubleCheck.provider(ControlActionCoordinatorImpl_Factory.create(daggerTitanGlobalRootComponent34.contextProvider, this.provideDelayableExecutorProvider, daggerTitanGlobalRootComponent34.provideMainDelayableExecutorProvider, this.provideActivityStarterProvider, this.keyguardStateControllerImplProvider, this.setTaskViewFactoryProvider, provider129, this.vibratorHelperProvider));
            this.customIconCacheProvider = DoubleCheck.provider(CustomIconCache_Factory.InstanceHolder.INSTANCE);
            Provider<ControlsControllerImpl> provider130 = this.controlsControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent35 = this.this$0;
            this.controlsUiControllerImplProvider = DoubleCheck.provider(RotationLockTile_Factory.create(provider130, daggerTitanGlobalRootComponent35.contextProvider, daggerTitanGlobalRootComponent35.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsListingControllerImplProvider, this.provideSharePreferencesProvider, this.controlActionCoordinatorImplProvider, this.provideActivityStarterProvider, this.shadeControllerImplProvider, this.customIconCacheProvider, this.controlsMetricsLoggerImplProvider, this.keyguardStateControllerImplProvider));
            Provider<ControlsBindingControllerImpl> provider131 = DoubleCheck.provider(new KeyguardBiometricLockoutLogger_Factory(this.this$0.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider, 1));
            this.controlsBindingControllerImplProvider = provider131;
            Provider<Optional<ControlsFavoritePersistenceWrapper>> provider132 = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.optionalOfControlsFavoritePersistenceWrapperProvider = provider132;
            Provider<ControlsControllerImpl> provider133 = this.controlsControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent36 = this.this$0;
            DelegateFactory.setDelegate(provider133, DoubleCheck.provider(ControlsControllerImpl_Factory.create(daggerTitanGlobalRootComponent36.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsUiControllerImplProvider, provider131, this.controlsListingControllerImplProvider, this.providesBroadcastDispatcherProvider, provider132, daggerTitanGlobalRootComponent36.dumpManagerProvider, this.provideUserTrackerProvider)));
            this.controlsProviderSelectorActivityProvider = ControlsProviderSelectorActivity_Factory.create(this.this$0.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.controlsListingControllerImplProvider, this.controlsControllerImplProvider, this.providesBroadcastDispatcherProvider, this.controlsUiControllerImplProvider);
        }

        public final void initialize6(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            this.controlsFavoritingActivityProvider = SystemUIService_Factory.create$1(this.this$0.provideMainExecutorProvider, this.controlsControllerImplProvider, this.controlsListingControllerImplProvider, this.providesBroadcastDispatcherProvider, this.controlsUiControllerImplProvider);
            Provider<ControlsControllerImpl> provider = this.controlsControllerImplProvider;
            Provider<BroadcastDispatcher> provider2 = this.providesBroadcastDispatcherProvider;
            Provider<CustomIconCache> provider3 = this.customIconCacheProvider;
            Provider<ControlsUiControllerImpl> provider4 = this.controlsUiControllerImplProvider;
            this.controlsEditingActivityProvider = new ControlsEditingActivity_Factory(provider, provider2, provider3, provider4, 0);
            this.controlsRequestDialogProvider = new ActionProxyReceiver_Factory(provider, provider2, this.controlsListingControllerImplProvider, 1);
            this.controlsActivityProvider = new ControlsActivity_Factory(provider4, provider2, 0);
            this.userSwitcherActivityProvider = GarbageMonitor_Factory.create(this.userSwitcherControllerProvider, provider2, this.providerLayoutInflaterProvider, this.falsingManagerProxyProvider, this.this$0.provideUserManagerProvider, this.shadeControllerImplProvider);
            Provider<KeyguardStateControllerImpl> provider5 = this.keyguardStateControllerImplProvider;
            Provider<KeyguardDismissUtil> provider6 = this.keyguardDismissUtilProvider;
            Provider<ActivityStarter> provider7 = this.provideActivityStarterProvider;
            Provider<Executor> provider8 = this.provideBackgroundExecutorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            this.walletActivityProvider = WalletActivity_Factory.create(provider5, provider6, provider7, provider8, daggerTitanGlobalRootComponent.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.falsingCollectorImplProvider, this.provideUserTrackerProvider, this.keyguardUpdateMonitorProvider, this.statusBarKeyguardViewManagerProvider, daggerTitanGlobalRootComponent.provideUiEventLoggerProvider);
            this.tunerActivityProvider = new TunerActivity_Factory(this.provideDemoModeControllerProvider, this.tunerServiceImplProvider);
            this.foregroundServicesDialogProvider = new ForegroundServicesDialog_Factory(this.provideMetricsLoggerProvider, 0);
            Provider<BroadcastDispatcher> provider9 = this.providesBroadcastDispatcherProvider;
            this.workLockActivityProvider = new DozeLogger_Factory(provider9, 2);
            this.brightnessDialogProvider = new BrightnessDialog_Factory(provider9, this.factoryProvider6, this.provideBgHandlerProvider);
            this.usbDebuggingActivityProvider = new UsbDebuggingActivity_Factory(provider9, 0);
            this.usbDebuggingSecondaryUserActivityProvider = new UsbDebuggingSecondaryUserActivity_Factory(provider9, 0);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            Provider<Context> provider10 = daggerTitanGlobalRootComponent2.contextProvider;
            UserCreator_Factory userCreator_Factory = new UserCreator_Factory(provider10, daggerTitanGlobalRootComponent2.provideUserManagerProvider, 0);
            this.userCreatorProvider = userCreator_Factory;
            this.createUserActivityProvider = new CreateUserActivity_Factory(userCreator_Factory, daggerTitanGlobalRootComponent2.provideIActivityManagerProvider);
            TvNotificationHandler_Factory tvNotificationHandler_Factory = new TvNotificationHandler_Factory(provider10, this.notificationListenerProvider);
            this.tvNotificationHandlerProvider = tvNotificationHandler_Factory;
            this.tvNotificationPanelActivityProvider = new FrameworkServicesModule_ProvideOptionalVibratorFactory(tvNotificationHandler_Factory, 2);
            this.peopleSpaceActivityProvider = new ImageTileSet_Factory(this.peopleSpaceWidgetManagerProvider, 4);
            this.imageExporterProvider = new ImageExporter_Factory(daggerTitanGlobalRootComponent2.provideContentResolverProvider, 0);
            Provider<LongScreenshotData> provider11 = DoubleCheck.provider(LongScreenshotData_Factory.InstanceHolder.INSTANCE);
            this.longScreenshotDataProvider = provider11;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.longScreenshotActivityProvider = GoogleAssistLogger_Factory.create(daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, this.imageExporterProvider, daggerTitanGlobalRootComponent3.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, provider11);
            this.launchConversationActivityProvider = OpaEnabledReceiver_Factory.create(this.provideNotificationVisibilityProvider, this.provideCommonNotifCollectionProvider, this.provideBubblesManagerProvider, this.this$0.provideUserManagerProvider, this.provideCommandQueueProvider);
            Provider<IndividualSensorPrivacyController> provider12 = this.provideIndividualSensorPrivacyControllerProvider;
            this.sensorUseStartedActivityProvider = new SensorUseStartedActivity_Factory(provider12, this.keyguardStateControllerImplProvider, this.keyguardDismissUtilProvider, this.provideBgHandlerProvider);
            this.tvUnblockSensorActivityProvider = new ActivityStarterDelegate_Factory(provider12, 3);
            Provider<HdmiCecSetMenuLanguageHelper> provider13 = DoubleCheck.provider(new ControlsActivity_Factory(this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, 1));
            this.hdmiCecSetMenuLanguageHelperProvider = provider13;
            this.hdmiCecSetMenuLanguageActivityProvider = new ForegroundServicesDialog_Factory(provider13, 2);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            this.gameMenuActivityProvider = new GameMenuActivity_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.entryPointControllerProvider, this.provideActivityStarterProvider, this.shortcutBarControllerProvider, this.gameModeDndControllerProvider, this.providerLayoutInflaterProvider, daggerTitanGlobalRootComponent4.provideMainHandlerProvider, this.gameDashboardUiEventLoggerProvider);
            MapProviderFactory.Builder builder = new MapProviderFactory.Builder(22);
            builder.put(ControlsProviderSelectorActivity.class, this.controlsProviderSelectorActivityProvider);
            builder.put(ControlsFavoritingActivity.class, this.controlsFavoritingActivityProvider);
            builder.put(ControlsEditingActivity.class, this.controlsEditingActivityProvider);
            builder.put(ControlsRequestDialog.class, this.controlsRequestDialogProvider);
            builder.put(ControlsActivity.class, this.controlsActivityProvider);
            builder.put(UserSwitcherActivity.class, this.userSwitcherActivityProvider);
            builder.put(WalletActivity.class, this.walletActivityProvider);
            builder.put(TunerActivity.class, this.tunerActivityProvider);
            builder.put(ForegroundServicesDialog.class, this.foregroundServicesDialogProvider);
            builder.put(WorkLockActivity.class, this.workLockActivityProvider);
            builder.put(BrightnessDialog.class, this.brightnessDialogProvider);
            builder.put(UsbDebuggingActivity.class, this.usbDebuggingActivityProvider);
            builder.put(UsbDebuggingSecondaryUserActivity.class, this.usbDebuggingSecondaryUserActivityProvider);
            builder.put(CreateUserActivity.class, this.createUserActivityProvider);
            builder.put(TvNotificationPanelActivity.class, this.tvNotificationPanelActivityProvider);
            builder.put(PeopleSpaceActivity.class, this.peopleSpaceActivityProvider);
            builder.put(LongScreenshotActivity.class, this.longScreenshotActivityProvider);
            builder.put(LaunchConversationActivity.class, this.launchConversationActivityProvider);
            builder.put(SensorUseStartedActivity.class, this.sensorUseStartedActivityProvider);
            builder.put(TvUnblockSensorActivity.class, this.tvUnblockSensorActivityProvider);
            builder.put(HdmiCecSetMenuLanguageActivity.class, this.hdmiCecSetMenuLanguageActivityProvider);
            builder.put(GameMenuActivity.class, this.gameMenuActivityProvider);
            this.mapOfClassOfAndProviderOfActivityProvider = builder.build();
            this.screenshotSmartActionsProvider = DoubleCheck.provider(ScreenshotSmartActions_Factory.InstanceHolder.INSTANCE);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            Provider<Context> provider14 = daggerTitanGlobalRootComponent5.contextProvider;
            this.screenshotNotificationsControllerProvider = new ScreenshotNotificationsController_Factory(provider14, daggerTitanGlobalRootComponent5.provideWindowManagerProvider);
            this.scrollCaptureClientProvider = new ScrollCaptureClient_Factory(daggerTitanGlobalRootComponent5.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, provider14, 0);
            ImageTileSet_Factory imageTileSet_Factory = new ImageTileSet_Factory(this.provideHandlerProvider, 0);
            this.imageTileSetProvider = imageTileSet_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            this.scrollCaptureControllerProvider = new ScrollCaptureController_Factory(daggerTitanGlobalRootComponent6.contextProvider, this.provideBackgroundExecutorProvider, this.scrollCaptureClientProvider, imageTileSet_Factory, daggerTitanGlobalRootComponent6.provideUiEventLoggerProvider);
            KeyboardUI_Factory keyboardUI_Factory = new KeyboardUI_Factory(this.this$0.contextProvider, 5);
            this.timeoutHandlerProvider = keyboardUI_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            ScreenshotController_Factory create = ScreenshotController_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, this.screenshotSmartActionsProvider, this.screenshotNotificationsControllerProvider, this.scrollCaptureClientProvider, daggerTitanGlobalRootComponent7.provideUiEventLoggerProvider, this.imageExporterProvider, daggerTitanGlobalRootComponent7.provideMainExecutorProvider, this.scrollCaptureControllerProvider, this.longScreenshotDataProvider, daggerTitanGlobalRootComponent7.provideActivityManagerProvider, keyboardUI_Factory);
            this.screenshotControllerProvider = create;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            this.takeScreenshotServiceProvider = new TakeScreenshotService_Factory(create, daggerTitanGlobalRootComponent8.provideUserManagerProvider, daggerTitanGlobalRootComponent8.provideUiEventLoggerProvider, this.screenshotNotificationsControllerProvider, 0);
            C24599 r2 = new Provider<DozeComponent.Builder>() {
                public final Object get() {
                    return new DozeComponentFactory();
                }
            };
            this.dozeComponentBuilderProvider = r2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            this.dozeServiceProvider = new DozeService_Factory(r2, daggerTitanGlobalRootComponent9.providesPluginManagerProvider);
            Provider<KeyguardLifecyclesDispatcher> provider15 = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(daggerTitanGlobalRootComponent9.screenLifecycleProvider, this.wakefulnessLifecycleProvider, 0));
            this.keyguardLifecyclesDispatcherProvider = provider15;
            this.keyguardServiceProvider = new LatencyTester_Factory(this.newKeyguardViewMediatorProvider, provider15, this.setTransitionsProvider, 2);
            C243810 r22 = new Provider<DreamOverlayComponent.Factory>() {
                public final Object get() {
                    return new DreamOverlayComponentFactory();
                }
            };
            this.dreamOverlayComponentFactoryProvider = r22;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.dreamOverlayServiceProvider = new DreamOverlayService_Factory(daggerTitanGlobalRootComponent10.contextProvider, daggerTitanGlobalRootComponent10.provideMainExecutorProvider, r22, this.dreamOverlayStateControllerProvider, this.keyguardUpdateMonitorProvider);
            this.notificationListenerWithPluginsProvider = new WMShellBaseModule_ProvideRecentTasksFactory(this.this$0.providesPluginManagerProvider, 3);
            Provider<ClipboardOverlayControllerFactory> provider16 = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(dependencyProvider3, 6));
            this.provideClipboardOverlayControllerFactoryProvider = provider16;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            this.clipboardListenerProvider = DoubleCheck.provider(new ClipboardListener_Factory(daggerTitanGlobalRootComponent11.contextProvider, this.deviceConfigProxyProvider, provider16, daggerTitanGlobalRootComponent11.provideClipboardManagerProvider, 0));
            this.providesBackgroundMessageRouterProvider = new QSFragmentModule_ProvideRootViewFactory(this.provideBackgroundDelayableExecutorProvider, 4);
            Provider<String> provider17 = DoubleCheck.provider(SystemUIGoogleModule_ProvideLeakReportEmailFactory.InstanceHolder.INSTANCE);
            this.provideLeakReportEmailProvider = provider17;
            Provider<LeakReporter> provider18 = DoubleCheck.provider(new LeakReporter_Factory(this.this$0.contextProvider, this.provideLeakDetectorProvider, provider17, 0));
            this.leakReporterProvider = provider18;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            Provider<GarbageMonitor> provider19 = DoubleCheck.provider(GarbageMonitor_Factory.create$1(daggerTitanGlobalRootComponent12.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.providesBackgroundMessageRouterProvider, this.provideLeakDetectorProvider, provider18, daggerTitanGlobalRootComponent12.dumpManagerProvider));
            this.garbageMonitorProvider = provider19;
            this.serviceProvider = DoubleCheck.provider(new GarbageMonitor_Service_Factory(this.this$0.contextProvider, provider19));
            DelegateFactory delegateFactory = r2;
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.globalActionsComponentProvider = delegateFactory2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            Provider<UiEventLogger> provider20 = daggerTitanGlobalRootComponent13.provideUiEventLoggerProvider;
            Provider<RingerModeTrackerImpl> provider21 = this.ringerModeTrackerImplProvider;
            Provider<SysUiState> provider22 = this.provideSysUiStateProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            GlobalActionsDialogLite_Factory create2 = GlobalActionsDialogLite_Factory.create(daggerTitanGlobalRootComponent13.contextProvider, delegateFactory, daggerTitanGlobalRootComponent13.provideAudioManagerProvider, daggerTitanGlobalRootComponent13.provideIDreamManagerProvider, daggerTitanGlobalRootComponent13.provideDevicePolicyManagerProvider, this.provideLockPatternUtilsProvider, this.providesBroadcastDispatcherProvider, this.telephonyListenerManagerProvider, this.globalSettingsImplProvider, this.secureSettingsImplProvider, this.vibratorHelperProvider, daggerTitanGlobalRootComponent13.provideResourcesProvider, this.provideConfigurationControllerProvider, this.keyguardStateControllerImplProvider, daggerTitanGlobalRootComponent13.provideUserManagerProvider, daggerTitanGlobalRootComponent13.provideTrustManagerProvider, daggerTitanGlobalRootComponent13.provideIActivityManagerProvider, daggerTitanGlobalRootComponent13.provideTelecomManagerProvider, this.provideMetricsLoggerProvider, this.sysuiColorExtractorProvider, daggerTitanGlobalRootComponent13.provideIStatusBarServiceProvider, this.notificationShadeWindowControllerImplProvider, daggerTitanGlobalRootComponent13.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, provider20, provider21, provider22, daggerTitanGlobalRootComponent14.provideMainHandlerProvider, daggerTitanGlobalRootComponent14.providePackageManagerProvider, this.optionalOfStatusBarProvider, this.keyguardUpdateMonitorProvider, this.provideDialogLaunchAnimatorProvider, this.systemUIDialogManagerProvider);
            this.globalActionsDialogLiteProvider = create2;
            GlobalActionsImpl_Factory create3 = GlobalActionsImpl_Factory.create(this.this$0.contextProvider, this.provideCommandQueueProvider, create2, this.blurUtilsProvider, this.keyguardStateControllerImplProvider, this.provideDeviceProvisionedControllerProvider);
            this.globalActionsImplProvider = create3;
            DelegateFactory.setDelegate(this.globalActionsComponentProvider, DoubleCheck.provider(new GlobalActionsComponent_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.extensionControllerImplProvider, create3, this.statusBarKeyguardViewManagerProvider)));
            this.instantAppNotifierProvider = DoubleCheck.provider(new ClipboardListener_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.provideUiBackgroundExecutorProvider, this.setLegacySplitScreenProvider, 1));
            this.keyboardUIProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.this$0.contextProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            this.keyguardBiometricLockoutLoggerProvider = DoubleCheck.provider(new KeyguardBiometricLockoutLogger_Factory(daggerTitanGlobalRootComponent15.contextProvider, daggerTitanGlobalRootComponent15.provideUiEventLoggerProvider, this.keyguardUpdateMonitorProvider, this.sessionTrackerProvider, 0));
            this.latencyTesterProvider = DoubleCheck.provider(new LatencyTester_Factory(this.this$0.contextProvider, this.biometricUnlockControllerProvider, this.providesBroadcastDispatcherProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            Provider<Context> provider23 = daggerTitanGlobalRootComponent16.contextProvider;
            Provider<ActivityStarter> provider24 = this.provideActivityStarterProvider;
            Provider<BroadcastDispatcher> provider25 = this.providesBroadcastDispatcherProvider;
            PowerModuleGoogle_ProvideWarningsUiFactory powerModuleGoogle_ProvideWarningsUiFactory = new PowerModuleGoogle_ProvideWarningsUiFactory(provider23, provider24, provider25, daggerTitanGlobalRootComponent16.provideUiEventLoggerProvider);
            this.provideWarningsUiProvider = powerModuleGoogle_ProvideWarningsUiFactory;
            this.powerUIProvider = DoubleCheck.provider(PowerUI_Factory.create(provider23, provider25, this.provideCommandQueueProvider, this.optionalOfStatusBarProvider, powerModuleGoogle_ProvideWarningsUiFactory, this.enhancedEstimatesGoogleImplProvider, daggerTitanGlobalRootComponent16.providePowerManagerProvider));
            this.ringtonePlayerProvider = C0770xb6bb24d8.m51m(this.this$0.contextProvider, 0);
            this.systemEventCoordinatorProvider = DoubleCheck.provider(new SystemEventCoordinator_Factory(this.bindSystemClockProvider, this.provideBatteryControllerProvider, this.privacyItemControllerProvider, this.this$0.contextProvider));
            Provider<StatusBarLocationPublisher> provider26 = DoubleCheck.provider(StatusBarLocationPublisher_Factory.InstanceHolder.INSTANCE);
            this.statusBarLocationPublisherProvider = provider26;
            SystemEventChipAnimationController_Factory systemEventChipAnimationController_Factory = new SystemEventChipAnimationController_Factory(this.this$0.contextProvider, this.statusBarWindowControllerProvider, provider26, 0);
            this.systemEventChipAnimationControllerProvider = systemEventChipAnimationController_Factory;
            Provider<SystemEventCoordinator> provider27 = this.systemEventCoordinatorProvider;
            Provider<StatusBarWindowController> provider28 = this.statusBarWindowControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            Provider<SystemStatusAnimationScheduler> provider29 = DoubleCheck.provider(SystemStatusAnimationScheduler_Factory.create(provider27, systemEventChipAnimationController_Factory, provider28, daggerTitanGlobalRootComponent17.dumpManagerProvider, this.bindSystemClockProvider, daggerTitanGlobalRootComponent17.provideMainDelayableExecutorProvider));
            this.systemStatusAnimationSchedulerProvider = provider29;
            this.privacyDotViewControllerProvider = DoubleCheck.provider(PrivacyDotViewController_Factory.create(this.this$0.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.statusBarContentInsetsProvider, provider29));
            Provider<PrivacyDotDecorProviderFactory> provider30 = DoubleCheck.provider(new PackageManagerAdapter_Factory(this.this$0.provideResourcesProvider, 2));
            this.privacyDotDecorProviderFactoryProvider = provider30;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            this.screenDecorationsProvider = DoubleCheck.provider(ScreenDecorations_Factory.create(daggerTitanGlobalRootComponent18.contextProvider, daggerTitanGlobalRootComponent18.provideMainExecutorProvider, this.secureSettingsImplProvider, this.providesBroadcastDispatcherProvider, this.tunerServiceImplProvider, this.provideUserTrackerProvider, this.privacyDotViewControllerProvider, provider30));
            this.shortcutKeyDispatcherProvider = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(this.this$0.contextProvider, this.setLegacySplitScreenProvider, 0));
            this.sliceBroadcastRelayHandlerProvider = DoubleCheck.provider(new SliceBroadcastRelayHandler_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, 0));
            this.storageNotificationProvider = DoubleCheck.provider(new QSLogger_Factory(this.this$0.contextProvider, 4));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            Provider<ThemeOverlayApplier> provider31 = DoubleCheck.provider(new ToastController_Factory(daggerTitanGlobalRootComponent19.contextProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent19.provideMainExecutorProvider, daggerTitanGlobalRootComponent19.provideOverlayManagerProvider, daggerTitanGlobalRootComponent19.dumpManagerProvider, 1));
            this.provideThemeOverlayManagerProvider = provider31;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            this.themeOverlayControllerProvider = DoubleCheck.provider(ThemeOverlayController_Factory.create(daggerTitanGlobalRootComponent20.contextProvider, this.providesBroadcastDispatcherProvider, this.provideBgHandlerProvider, daggerTitanGlobalRootComponent20.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, provider31, this.secureSettingsImplProvider, daggerTitanGlobalRootComponent20.provideWallpaperManagerProvider, daggerTitanGlobalRootComponent20.provideUserManagerProvider, this.provideDeviceProvisionedControllerProvider, this.provideUserTrackerProvider, daggerTitanGlobalRootComponent20.dumpManagerProvider, this.featureFlagsReleaseProvider, this.wakefulnessLifecycleProvider));
            Provider<LogBuffer> provider32 = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.logBufferFactoryProvider, 2));
            this.provideToastLogBufferProvider = provider32;
            ToastLogger_Factory toastLogger_Factory = new ToastLogger_Factory(provider32, 0);
            this.toastLoggerProvider = toastLogger_Factory;
            this.toastUIProvider = DoubleCheck.provider(new ToastUI_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.toastFactoryProvider, toastLogger_Factory));
            this.volumeUIProvider = DoubleCheck.provider(new VolumeUI_Factory(this.this$0.contextProvider, this.volumeDialogComponentProvider, 0));
            Provider<ModeSwitchesController> provider33 = DoubleCheck.provider(new DependencyProvider_ProvidesModeSwitchesControllerFactory(dependencyProvider3, (Provider) this.this$0.contextProvider));
            this.providesModeSwitchesControllerProvider = provider33;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            this.windowMagnificationProvider = DoubleCheck.provider(WindowMagnification_Factory.create(daggerTitanGlobalRootComponent21.contextProvider, daggerTitanGlobalRootComponent21.provideMainHandlerProvider, this.provideCommandQueueProvider, provider33, this.provideSysUiStateProvider, this.overviewProxyServiceProvider));
            this.setHideDisplayCutoutProvider = InstanceFactory.create(optional8);
            this.setShellCommandHandlerProvider = InstanceFactory.create(optional9);
            this.setCompatUIProvider = InstanceFactory.create(optional14);
            this.setDragAndDropProvider = InstanceFactory.create(optional15);
            Provider<Context> provider34 = this.this$0.contextProvider;
            Provider<Optional<Pip>> provider35 = this.setPipProvider;
            Provider<Optional<LegacySplitScreen>> provider36 = this.setLegacySplitScreenProvider;
            Provider<Optional<SplitScreen>> provider37 = this.setSplitScreenProvider;
            Provider<Optional<OneHanded>> provider38 = this.setOneHandedProvider;
            Provider<Optional<HideDisplayCutout>> provider39 = this.setHideDisplayCutoutProvider;
            Provider<Optional<ShellCommandHandler>> provider40 = this.setShellCommandHandlerProvider;
            Provider<Optional<CompatUI>> provider41 = this.setCompatUIProvider;
            Provider<Optional<DragAndDrop>> provider42 = this.setDragAndDropProvider;
            Provider<CommandQueue> provider43 = this.provideCommandQueueProvider;
            Provider<ConfigurationController> provider44 = this.provideConfigurationControllerProvider;
            Provider<KeyguardUpdateMonitor> provider45 = this.keyguardUpdateMonitorProvider;
            Provider<NavigationModeController> provider46 = this.navigationModeControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent22 = this.this$0;
            this.wMShellProvider = DoubleCheck.provider(WMShell_Factory.create(provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, daggerTitanGlobalRootComponent22.screenLifecycleProvider, this.provideSysUiStateProvider, this.protoTracerProvider, this.wakefulnessLifecycleProvider, this.userInfoControllerImplProvider, daggerTitanGlobalRootComponent22.provideMainExecutorProvider));
            this.opaHomeButtonProvider = new OpaHomeButton_Factory(this.newKeyguardViewMediatorProvider, this.statusBarGoogleProvider, this.navigationModeControllerProvider, 0);
            this.opaLockscreenProvider = new OpaLockscreen_Factory((Provider) this.statusBarGoogleProvider, (Provider) this.keyguardStateControllerImplProvider);
            this.assistInvocationEffectProvider = new TelephonyListenerManager_Factory(this.assistManagerGoogleProvider, this.opaHomeButtonProvider, this.opaLockscreenProvider, 2);
            this.builderProvider6 = new LaunchOpa_Builder_Factory(this.this$0.contextProvider, this.statusBarGoogleProvider);
            this.builderProvider7 = new SettingsAction_Builder_Factory(this.this$0.contextProvider, this.statusBarGoogleProvider);
            this.builderProvider8 = new CameraAction_Builder_Factory(this.this$0.contextProvider, this.statusBarGoogleProvider);
            this.builderProvider9 = new SetupWizardAction_Builder_Factory(this.this$0.contextProvider, this.statusBarGoogleProvider);
            this.squishyNavigationButtonsProvider = new GestureController_Factory(this.this$0.contextProvider, this.newKeyguardViewMediatorProvider, this.statusBarGoogleProvider, this.navigationModeControllerProvider, 1);
            this.optionalOfHeadsUpManagerProvider = new PresentJdkOptionalInstanceProvider(this.provideHeadsUpManagerPhoneProvider);
            this.unpinNotificationsProvider = new UnpinNotifications_Factory(this.this$0.contextProvider, this.optionalOfHeadsUpManagerProvider, 0);
            this.silenceCallProvider = new FeatureFlagsRelease_Factory(this.this$0.contextProvider, this.telephonyListenerManagerProvider, 5);
            this.telephonyActivityProvider = new OverlayUiHost_Factory(this.this$0.contextProvider, this.telephonyListenerManagerProvider, 1);
            this.serviceConfigurationGoogleProvider = new ServiceConfigurationGoogle_Factory(this.this$0.contextProvider, this.assistInvocationEffectProvider, this.builderProvider6, this.builderProvider7, this.builderProvider8, this.builderProvider9, this.squishyNavigationButtonsProvider, this.unpinNotificationsProvider, this.silenceCallProvider, this.telephonyActivityProvider);
            Provider<ContentResolverWrapper> m = C0770xb6bb24d8.m51m(this.this$0.contextProvider, 6);
            this.contentResolverWrapperProvider = m;
            this.factoryProvider7 = DoubleCheck.provider(new ColumbusContentObserver_Factory_Factory(m, this.provideUserTrackerProvider, this.this$0.provideMainHandlerProvider, this.this$0.provideMainExecutorProvider));
            this.columbusSettingsProvider = DoubleCheck.provider(new ColumbusSettings_Factory(this.this$0.contextProvider, this.provideUserTrackerProvider, this.factoryProvider7));
            this.silenceAlertsDisabledProvider = DoubleCheck.provider(new DistanceClassifier_Factory(this.this$0.contextProvider, this.columbusSettingsProvider, 2));
        }

        public final void initialize7(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            this.dismissTimerProvider = DoubleCheck.provider(new DismissTimer_Factory(daggerTitanGlobalRootComponent.contextProvider, this.silenceAlertsDisabledProvider, daggerTitanGlobalRootComponent.provideIActivityManagerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            this.snoozeAlarmProvider = DoubleCheck.provider(new SnoozeAlarm_Factory(daggerTitanGlobalRootComponent2.contextProvider, this.silenceAlertsDisabledProvider, daggerTitanGlobalRootComponent2.provideIActivityManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.silenceCallProvider2 = DoubleCheck.provider(new SilenceCall_Factory(daggerTitanGlobalRootComponent3.contextProvider, this.silenceAlertsDisabledProvider, daggerTitanGlobalRootComponent3.provideTelecomManagerProvider, daggerTitanGlobalRootComponent3.provideTelephonyManagerProvider, this.telephonyListenerManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            Provider<com.google.android.systemui.columbus.actions.SettingsAction> provider = DoubleCheck.provider(new SettingsAction_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.statusBarGoogleProvider, daggerTitanGlobalRootComponent4.provideUiEventLoggerProvider));
            this.settingsActionProvider = provider;
            this.provideFullscreenActionsProvider = DoubleCheck.provider(new OpenNotificationShade_Factory(this.dismissTimerProvider, this.snoozeAlarmProvider, this.silenceCallProvider2, provider, 1));
            this.unpinNotificationsProvider2 = DoubleCheck.provider(new DozeLog_Factory(this.this$0.contextProvider, this.silenceAlertsDisabledProvider, this.optionalOfHeadsUpManagerProvider, 5));
            this.assistInvocationEffectProvider2 = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.assistManagerGoogleProvider, 4));
            int i = SetFactory.$r8$clinit;
            ArrayList arrayList = new ArrayList(1);
            SetFactory m = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList, this.assistInvocationEffectProvider2, arrayList, Collections.emptyList());
            this.namedSetOfFeedbackEffectProvider = m;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            this.launchOpaProvider = DoubleCheck.provider(new LaunchOpa_Factory(daggerTitanGlobalRootComponent5.contextProvider, this.statusBarGoogleProvider, m, this.assistManagerGoogleProvider, daggerTitanGlobalRootComponent5.provideKeyguardManagerProvider, this.tunerServiceImplProvider, this.factoryProvider7, daggerTitanGlobalRootComponent5.provideUiEventLoggerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            this.manageMediaProvider = DoubleCheck.provider(new ManageMedia_Factory(daggerTitanGlobalRootComponent6.contextProvider, daggerTitanGlobalRootComponent6.provideAudioManagerProvider, daggerTitanGlobalRootComponent6.provideUiEventLoggerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            this.takeScreenshotProvider = DoubleCheck.provider(new TakeScreenshot_Factory(daggerTitanGlobalRootComponent7.contextProvider, daggerTitanGlobalRootComponent7.provideMainHandlerProvider, daggerTitanGlobalRootComponent7.provideUiEventLoggerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            this.launchOverviewProvider = DoubleCheck.provider(new LaunchOverview_Factory(daggerTitanGlobalRootComponent8.contextProvider, this.provideRecentsProvider, daggerTitanGlobalRootComponent8.provideUiEventLoggerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            this.openNotificationShadeProvider = DoubleCheck.provider(new OpenNotificationShade_Factory(daggerTitanGlobalRootComponent9.contextProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarGoogleProvider, daggerTitanGlobalRootComponent9.provideUiEventLoggerProvider, 0));
            Provider<KeyguardVisibility> provider2 = DoubleCheck.provider(new KeyguardVisibility_Factory(this.this$0.contextProvider, this.keyguardStateControllerImplProvider, 0));
            this.keyguardVisibilityProvider = provider2;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.launchAppProvider = DoubleCheck.provider(LaunchApp_Factory.create(daggerTitanGlobalRootComponent10.contextProvider, daggerTitanGlobalRootComponent10.provideLauncherAppsProvider, this.provideActivityStarterProvider, this.statusBarKeyguardViewManagerProvider, daggerTitanGlobalRootComponent10.provideIActivityManagerProvider, daggerTitanGlobalRootComponent10.provideUserManagerProvider, this.columbusSettingsProvider, provider2, this.keyguardUpdateMonitorProvider, daggerTitanGlobalRootComponent10.provideMainHandlerProvider, this.provideBgHandlerProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent10.provideUiEventLoggerProvider, this.provideUserTrackerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            Provider<FlashlightControllerImpl> provider3 = DoubleCheck.provider(new LogBufferFactory_Factory(daggerTitanGlobalRootComponent11.contextProvider, daggerTitanGlobalRootComponent11.dumpManagerProvider, 2));
            this.flashlightControllerImplProvider = provider3;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            Provider<ToggleFlashlight> provider4 = DoubleCheck.provider(new ToggleFlashlight_Factory(daggerTitanGlobalRootComponent12.contextProvider, provider3, daggerTitanGlobalRootComponent12.provideMainHandlerProvider, daggerTitanGlobalRootComponent12.provideUiEventLoggerProvider));
            this.toggleFlashlightProvider = provider4;
            this.provideUserSelectedActionsProvider = DoubleCheck.provider(new ColumbusModule_ProvideUserSelectedActionsFactory(this.launchOpaProvider, this.manageMediaProvider, this.takeScreenshotProvider, this.launchOverviewProvider, this.openNotificationShadeProvider, this.launchAppProvider, provider4));
            Provider<PowerManagerWrapper> provider5 = DoubleCheck.provider(new SeekBarViewModel_Factory(this.this$0.contextProvider, 3));
            this.powerManagerWrapperProvider = provider5;
            Provider<UserSelectedAction> provider6 = DoubleCheck.provider(new BatteryMeterViewController_Factory(this.this$0.contextProvider, this.columbusSettingsProvider, this.provideUserSelectedActionsProvider, this.takeScreenshotProvider, this.keyguardStateControllerImplProvider, provider5, this.wakefulnessLifecycleProvider, 1));
            this.userSelectedActionProvider = provider6;
            this.provideColumbusActionsProvider = DoubleCheck.provider(new ProximityClassifier_Factory(this.provideFullscreenActionsProvider, this.unpinNotificationsProvider2, provider6, 1));
            this.hapticClickProvider = DoubleCheck.provider(new DreamOverlayStateController_Factory(this.this$0.provideVibratorProvider, 3));
            Provider<UserActivity> provider7 = DoubleCheck.provider(new MediaFlags_Factory(this.this$0.providePowerManagerProvider, 4));
            this.userActivityProvider = provider7;
            this.provideColumbusEffectsProvider = new SingleTapClassifier_Factory(this.hapticClickProvider, provider7, 3);
            List emptyList = Collections.emptyList();
            ArrayList arrayList2 = new ArrayList(1);
            this.namedSetOfFeedbackEffectProvider2 = C0768xb6bb24d6.m49m(arrayList2, this.provideColumbusEffectsProvider, emptyList, arrayList2);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            this.flagEnabledProvider = DoubleCheck.provider(new TileAdapter_Factory(daggerTitanGlobalRootComponent13.contextProvider, this.columbusSettingsProvider, daggerTitanGlobalRootComponent13.provideMainHandlerProvider, 2));
            Provider<Proximity> provider8 = DoubleCheck.provider(new FeatureFlagsRelease_Factory(this.this$0.contextProvider, this.provideProximitySensorProvider, 4));
            this.proximityProvider = provider8;
            this.keyguardProximityProvider = DoubleCheck.provider(new KeyguardProximity_Factory(this.this$0.contextProvider, this.keyguardVisibilityProvider, provider8));
            ArrayList arrayList3 = new ArrayList(1);
            SetFactory m2 = BouncerSwipeModule$$ExternalSyntheticLambda0.m56m(arrayList3, this.settingsActionProvider, arrayList3, Collections.emptyList());
            this.namedSetOfActionProvider = m2;
            this.setupWizardProvider = DoubleCheck.provider(new SetupWizard_Factory(this.this$0.contextProvider, m2, this.provideDeviceProvisionedControllerProvider, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            this.telephonyActivityProvider2 = DoubleCheck.provider(new TelephonyListenerManager_Factory(daggerTitanGlobalRootComponent14.contextProvider, daggerTitanGlobalRootComponent14.provideTelephonyManagerProvider, this.telephonyListenerManagerProvider, 1));
            this.vrModeProvider = DoubleCheck.provider(new VrMode_Factory(this.this$0.contextProvider, 0));
            Provider<PowerState> provider9 = DoubleCheck.provider(new ControlsActivity_Factory(this.this$0.contextProvider, this.wakefulnessLifecycleProvider, 3));
            this.powerStateProvider = provider9;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent15 = this.this$0;
            this.cameraVisibilityProvider = DoubleCheck.provider(new CameraVisibility_Factory(daggerTitanGlobalRootComponent15.contextProvider, this.provideFullscreenActionsProvider, this.keyguardVisibilityProvider, provider9, daggerTitanGlobalRootComponent15.provideIActivityManagerProvider, daggerTitanGlobalRootComponent15.provideMainHandlerProvider));
            Provider<PowerSaveState> provider10 = DoubleCheck.provider(new PowerSaveState_Factory(this.this$0.contextProvider, 0));
            this.powerSaveStateProvider = provider10;
            this.provideColumbusGatesProvider = new ColumbusModule_ProvideColumbusGatesFactory(this.flagEnabledProvider, this.keyguardProximityProvider, this.setupWizardProvider, this.telephonyActivityProvider2, this.vrModeProvider, this.cameraVisibilityProvider, provider10, this.powerStateProvider);
            List emptyList2 = Collections.emptyList();
            ArrayList arrayList4 = new ArrayList(1);
            this.namedSetOfGateProvider = C0768xb6bb24d6.m49m(arrayList4, this.provideColumbusGatesProvider, emptyList2, arrayList4);
            Provider<SensorConfiguration> m3 = C0769xb6bb24d7.m50m(this.this$0.contextProvider, 8);
            this.sensorConfigurationProvider = m3;
            Provider<LowSensitivitySettingAdjustment> provider11 = DoubleCheck.provider(new LeakReporter_Factory(this.this$0.contextProvider, this.columbusSettingsProvider, m3, 2));
            this.lowSensitivitySettingAdjustmentProvider = provider11;
            Provider<List<Adjustment>> provider12 = DoubleCheck.provider(new TimeoutManager_Factory(provider11, 5));
            this.provideGestureAdjustmentsProvider = provider12;
            Provider<GestureConfiguration> provider13 = DoubleCheck.provider(new ManagedProfileControllerImpl_Factory(provider12, this.sensorConfigurationProvider, 2));
            this.gestureConfigurationProvider = provider13;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent16 = this.this$0;
            this.cHREGestureSensorProvider = DoubleCheck.provider(new CHREGestureSensor_Factory(daggerTitanGlobalRootComponent16.contextProvider, daggerTitanGlobalRootComponent16.provideUiEventLoggerProvider, provider13, this.statusBarStateControllerImplProvider, this.wakefulnessLifecycleProvider, this.provideBgHandlerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent17 = this.this$0;
            Provider<GestureSensorImpl> provider14 = DoubleCheck.provider(new GestureSensorImpl_Factory((Provider) daggerTitanGlobalRootComponent17.contextProvider, (Provider) daggerTitanGlobalRootComponent17.provideUiEventLoggerProvider, (Provider) daggerTitanGlobalRootComponent17.provideMainHandlerProvider));
            this.gestureSensorImplProvider = provider14;
            this.provideGestureSensorProvider = DoubleCheck.provider(new ClipboardListener_Factory(this.this$0.contextProvider, this.columbusSettingsProvider, this.cHREGestureSensorProvider, provider14, 3));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent18 = this.this$0;
            Provider<Context> provider15 = daggerTitanGlobalRootComponent18.contextProvider;
            Provider<Handler> provider16 = daggerTitanGlobalRootComponent18.provideMainHandlerProvider;
            ColumbusModule_ProvideTransientGateDurationFactory columbusModule_ProvideTransientGateDurationFactory = ColumbusModule_ProvideTransientGateDurationFactory.InstanceHolder.INSTANCE;
            this.chargingStateProvider = DoubleCheck.provider(new ChargingState_Factory(provider15, provider16));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent19 = this.this$0;
            this.usbStateProvider = DoubleCheck.provider(new UsbState_Factory(daggerTitanGlobalRootComponent19.contextProvider, daggerTitanGlobalRootComponent19.provideMainHandlerProvider));
            List emptyList3 = Collections.emptyList();
            ArrayList arrayList5 = new ArrayList(1);
            arrayList5.add(ColumbusModule_ProvideBlockingSystemKeysFactory.InstanceHolder.INSTANCE);
            SetFactory setFactory = new SetFactory(emptyList3, arrayList5);
            this.namedSetOfIntegerProvider = setFactory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent20 = this.this$0;
            this.systemKeyPressProvider = DoubleCheck.provider(new SystemKeyPress_Factory(daggerTitanGlobalRootComponent20.contextProvider, daggerTitanGlobalRootComponent20.provideMainHandlerProvider, this.provideCommandQueueProvider, columbusModule_ProvideTransientGateDurationFactory, setFactory, 0));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent21 = this.this$0;
            Provider<ScreenTouch> provider17 = DoubleCheck.provider(new MediaViewController_Factory(daggerTitanGlobalRootComponent21.contextProvider, this.powerStateProvider, daggerTitanGlobalRootComponent21.provideMainHandlerProvider, 2));
            this.screenTouchProvider = provider17;
            this.provideColumbusSoftGatesProvider = new ControlsEditingActivity_Factory(this.chargingStateProvider, this.usbStateProvider, this.systemKeyPressProvider, provider17, 1);
            List emptyList4 = Collections.emptyList();
            ArrayList arrayList6 = new ArrayList(1);
            SetFactory m4 = C0768xb6bb24d6.m49m(arrayList6, this.provideColumbusSoftGatesProvider, emptyList4, arrayList6);
            this.namedSetOfGateProvider2 = m4;
            Provider<GestureController> provider18 = DoubleCheck.provider(new GestureController_Factory(this.provideGestureSensorProvider, m4, this.commandRegistryProvider, this.this$0.provideUiEventLoggerProvider, 0));
            this.gestureControllerProvider = provider18;
            this.columbusServiceProvider = DoubleCheck.provider(new ColumbusService_Factory(this.provideColumbusActionsProvider, this.namedSetOfFeedbackEffectProvider2, this.namedSetOfGateProvider, provider18, this.powerManagerWrapperProvider));
            Provider<ColumbusStructuredDataManager> provider19 = DoubleCheck.provider(new ColumbusStructuredDataManager_Factory(this.this$0.contextProvider, this.provideUserTrackerProvider, this.provideBackgroundExecutorProvider, 0));
            this.columbusStructuredDataManagerProvider = provider19;
            this.columbusServiceWrapperProvider = DoubleCheck.provider(new ColumbusServiceWrapper_Factory(this.columbusSettingsProvider, this.columbusServiceProvider, this.settingsActionProvider, provider19, 0));
            ScreenLifecycle_Factory screenLifecycle_Factory = new ScreenLifecycle_Factory(this.this$0.provideStatsManagerProvider, 6);
            this.dataLoggerProvider = screenLifecycle_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent22 = this.this$0;
            AutorotateDataService_Factory autorotateDataService_Factory = new AutorotateDataService_Factory(daggerTitanGlobalRootComponent22.contextProvider, daggerTitanGlobalRootComponent22.providesSensorManagerProvider, screenLifecycle_Factory, this.providesBroadcastDispatcherProvider, this.deviceConfigProxyProvider, daggerTitanGlobalRootComponent22.provideMainDelayableExecutorProvider);
            this.autorotateDataServiceProvider = autorotateDataService_Factory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent23 = this.this$0;
            this.googleServicesProvider = DoubleCheck.provider(new GoogleServices_Factory(daggerTitanGlobalRootComponent23.contextProvider, this.serviceConfigurationGoogleProvider, daggerTitanGlobalRootComponent23.provideUiEventLoggerProvider, this.columbusServiceWrapperProvider, autorotateDataService_Factory));
            this.monitorComponentFactoryProvider = new Provider<MonitorComponent.Factory>() {
                public final Object get() {
                    return new MonitorComponentFactory();
                }
            };
            Provider<Context> provider20 = this.this$0.contextProvider;
            this.provideDockingConditionsProvider = new DreamOverlayStateController_Factory(provider20, 4);
            this.serviceBinderCallbackComponentFactoryProvider = new Provider<ServiceBinderCallbackComponent$Factory>() {
                public final Object get() {
                    return new ServiceBinderCallbackComponentFactory();
                }
            };
            C244113 r6 = new Provider<SetupDreamComponent$Factory>() {
                public final Object get() {
                    return new SetupDreamComponentFactory();
                }
            };
            this.setupDreamComponentFactoryProvider = r6;
            this.setupDreamComplicationProvider = new SetupDreamComplication_Factory(r6);
            SetupDreamModule_ProvidesDreamSettingFactory setupDreamModule_ProvidesDreamSettingFactory = SetupDreamModule_ProvidesDreamSettingFactory.InstanceHolder.INSTANCE;
            this.providesDreamSelectedProvider = new ForegroundServiceController_Factory(provider20, setupDreamModule_ProvidesDreamSettingFactory, 3);
            Provider<Context> provider21 = this.this$0.contextProvider;
            this.providesNotificationManagerProvider = new ForegroundServicesDialog_Factory(provider21, 7);
            this.providesDreamSettingPendingIntentProvider = new ShortcutKeyDispatcher_Factory(provider21, SetupDreamModule_ProvidesDreamSettingIntentFactory.InstanceHolder.INSTANCE, 3);
            this.providesSetupDreamNotificationProvider = new PowerNotificationWarnings_Factory(this.this$0.contextProvider, this.providesDreamSettingPendingIntentProvider, 1);
            this.providesDreamSettingContentObserverProvider = new UsbDebuggingActivity_Factory(this.this$0.contextProvider, 5);
            this.providesDreamsSettingUriProvider = new UsbDebuggingSecondaryUserActivity_Factory(setupDreamModule_ProvidesDreamSettingFactory, 3);
            this.nudgeToSetupDreamCallbackProvider = new NudgeToSetupDreamCallback_Factory(this.setupDreamComplicationProvider, this.dreamOverlayStateControllerProvider, this.providesDreamSelectedProvider, this.providesNotificationManagerProvider, this.providesSetupDreamNotificationProvider, this.providesDreamSettingContentObserverProvider, this.providesDreamsSettingUriProvider);
            this.mediaShellComponentFactoryProvider = new Provider<MediaShellComponent$Factory>() {
                public final Object get() {
                    return new MediaShellComponentFactory();
                }
            };
            this.providesDeviceNameProvider = new MediaControllerFactory_Factory(this.this$0.contextProvider, 7);
            this.providesMediaShellViewProvider = new MediaShellModule_ProvidesMediaShellViewFactory(this.this$0.contextProvider, this.providesDeviceNameProvider);
            this.mediaShellComplicationProvider = new MediaShellComplication_Factory(this.providesMediaShellViewProvider);
            this.mediaShellCallbackProvider = new MediaShellCallback_Factory(this.mediaShellComponentFactoryProvider, this.dreamOverlayStateControllerProvider, this.mediaShellComplicationProvider);
            this.provideTimeoutToUserZeroFeatureEnabledProvider = new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.this$0.provideResourcesProvider, 5);
            this.timeoutToUserZeroFeatureConditionProvider = new StatusBarInitializer_Factory(this.provideTimeoutToUserZeroFeatureEnabledProvider, 6);
            this.provideUserIdProvider = new DozeLogger_Factory(this.provideUserTrackerProvider, 8);
            this.provideTimeoutToUserZeroUserSettingDurationProvider = new WMShellModule_ProvideUnfoldBackgroundControllerFactory(this.secureSettingsImplProvider, this.provideUserIdProvider, 2);
            this.timeoutToUserZeroSettingConditionProvider = new TimeoutToUserZeroSettingCondition_Factory(this.provideHandlerProvider, this.secureSettingsImplProvider, this.provideTimeoutToUserZeroUserSettingDurationProvider, this.provideUserIdProvider);
            this.provideTimeoutToUserZeroPreconditionsProvider = new DockModule_ProvideTimeoutToUserZeroPreconditionsFactory(this.timeoutToUserZeroFeatureConditionProvider, this.timeoutToUserZeroSettingConditionProvider);
            this.provideTimeoutToUserZeroPreconditionsMonitorProvider = new DockModule_ProvideTimeoutToUserZeroPreconditionsMonitorFactory(this.monitorComponentFactoryProvider, this.provideTimeoutToUserZeroPreconditionsProvider);
            this.timeoutToUserZeroCallbackProvider = new SystemUIService_Factory(this.provideDelayableExecutorProvider, this.provideTimeoutToUserZeroPreconditionsMonitorProvider, this.provideTimeoutToUserZeroUserSettingDurationProvider, this.userSwitcherControllerProvider, this.provideUserTrackerProvider, 2);
            this.provideDockingCallbacksProvider = new DockModule_ProvideDockingCallbacksFactory(this.this$0.provideResourcesProvider, this.serviceBinderCallbackComponentFactoryProvider, this.nudgeToSetupDreamCallbackProvider, this.mediaShellCallbackProvider, this.timeoutToUserZeroCallbackProvider);
            this.provideConditionsMonitorProvider = DoubleCheck.provider(new DockModule_ProvideConditionsMonitorFactory(this.monitorComponentFactoryProvider, this.provideDockingConditionsProvider, this.provideDockingCallbacksProvider));
            this.dockMonitorProvider = DoubleCheck.provider(new DockMonitor_Factory(this.this$0.contextProvider, this.provideConditionsMonitorProvider));
            this.dockingConditionProvider = new DockEventSimulator_DockingCondition_Factory(this.this$0.contextProvider);
            this.dockEventSimulatorProvider = new DockEventSimulator_Factory(this.this$0.contextProvider, this.featureFlagsReleaseProvider, this.provideConditionsMonitorProvider, this.dockingConditionProvider);
            this.dreamClockTimeComplicationComponentFactoryProvider = new Provider<DreamClockTimeComplicationComponent$Factory>() {
                public final Object get() {
                    return new DreamClockTimeComplicationComponentFactory();
                }
            };
            this.dreamClockTimeComplicationProvider = new DreamClockTimeComplication_Factory(this.dreamClockTimeComplicationComponentFactoryProvider);
            this.registrantProvider = new DreamClockTimeComplication_Registrant_Factory(this.this$0.contextProvider, this.dreamOverlayStateControllerProvider, this.dreamClockTimeComplicationProvider);
            this.dreamClockDateComplicationComponentFactoryProvider = new Provider<DreamClockDateComplicationComponent$Factory>() {
                public final Object get() {
                    return new DreamClockDateComplicationComponentFactory();
                }
            };
            this.dreamClockDateComplicationProvider = new DreamClockDateComplication_Factory(this.dreamClockDateComplicationComponentFactoryProvider);
            this.registrantProvider2 = new DreamClockDateComplication_Registrant_Factory(this.this$0.contextProvider, this.dreamOverlayStateControllerProvider, this.dreamClockDateComplicationProvider);
            this.dreamOverlayRegistrantProvider = new DreamOverlayRegistrant_Factory(this.this$0.contextProvider, this.this$0.provideResourcesProvider, 0);
            Provider<BcSmartspaceDataPlugin> provider22 = DoubleCheck.provider(SystemUIGoogleModule_ProvideBcSmartspaceDataPluginFactory.InstanceHolder.INSTANCE);
            this.provideBcSmartspaceDataPluginProvider = provider22;
            this.optionalOfBcSmartspaceDataPluginProvider = new PresentJdkOptionalInstanceProvider(provider22);
            Provider<Context> provider23 = this.this$0.contextProvider;
            Provider<FeatureFlagsRelease> provider24 = this.featureFlagsReleaseProvider;
            Provider<SmartspaceManager> provider25 = this.this$0.provideSmartspaceManagerProvider;
            Provider<ActivityStarter> provider26 = this.provideActivityStarterProvider;
            Provider<FalsingManagerProxy> provider27 = this.falsingManagerProxyProvider;
            Provider provider28 = this.secureSettingsImplProvider;
            Provider<UserTracker> provider29 = this.provideUserTrackerProvider;
            Provider<ContentResolver> provider30 = this.this$0.provideContentResolverProvider;
            Provider<ConfigurationController> provider31 = this.provideConfigurationControllerProvider;
            Provider<StatusBarStateControllerImpl> provider32 = this.statusBarStateControllerImplProvider;
            Provider<DeviceProvisionedController> provider33 = this.provideDeviceProvisionedControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent24 = this.this$0;
            this.lockscreenSmartspaceControllerProvider = DoubleCheck.provider(LockscreenSmartspaceController_Factory.create(provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, daggerTitanGlobalRootComponent24.provideExecutionProvider, daggerTitanGlobalRootComponent24.provideMainExecutorProvider, this.this$0.provideMainHandlerProvider, this.optionalOfBcSmartspaceDataPluginProvider));
            this.dreamWeatherComplicationComponentFactoryProvider = new Provider<DreamWeatherComplicationComponent$Factory>() {
                public final Object get() {
                    return new DreamWeatherComplicationComponentFactory();
                }
            };
            this.dreamWeatherComplicationProvider = new DreamWeatherComplication_Factory(this.dreamWeatherComplicationComponentFactoryProvider);
        }

        public final void initialize8(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            Provider<Context> provider = daggerTitanGlobalRootComponent.contextProvider;
            Provider<LockscreenSmartspaceController> provider2 = this.lockscreenSmartspaceControllerProvider;
            Provider<DreamOverlayStateController> provider3 = this.dreamOverlayStateControllerProvider;
            this.registrantProvider3 = new DreamWeatherComplication_Registrant_Factory(provider, provider2, provider3, this.dreamWeatherComplicationProvider);
            C244618 r5 = new Provider<MediaComplicationComponent$Factory>() {
                public final Object get() {
                    return new MediaComplicationComponentFactory();
                }
            };
            this.mediaComplicationComponentFactoryProvider = r5;
            MediaDreamComplication_Factory mediaDreamComplication_Factory = new MediaDreamComplication_Factory(r5);
            this.mediaDreamComplicationProvider = mediaDreamComplication_Factory;
            Provider<Context> provider4 = provider;
            this.mediaDreamSentinelProvider = new MediaDreamSentinel_Factory(provider4, this.mediaDataManagerProvider, provider3, mediaDreamComplication_Factory, 0);
            VibratorHelper_Factory vibratorHelper_Factory = new VibratorHelper_Factory(provider, provider2, 1);
            this.smartSpaceComplicationProvider = vibratorHelper_Factory;
            this.registrantProvider4 = new SmartSpaceComplication_Registrant_Factory(provider, provider3, vibratorHelper_Factory, provider2);
            TvPipModule_ProvideTvPipBoundsStateFactory tvPipModule_ProvideTvPipBoundsStateFactory = new TvPipModule_ProvideTvPipBoundsStateFactory(provider, 2);
            this.providesDreamBackendProvider = tvPipModule_ProvideTvPipBoundsStateFactory;
            this.complicationTypesUpdaterProvider = DoubleCheck.provider(new NavigationModeController_Factory(provider4, tvPipModule_ProvideTvPipBoundsStateFactory, daggerTitanGlobalRootComponent.provideMainExecutorProvider, this.secureSettingsImplProvider, provider3, 1));
            MapProviderFactory.Builder builder = new MapProviderFactory.Builder(34);
            builder.put(AuthController.class, this.authControllerProvider);
            builder.put(ClipboardListener.class, this.clipboardListenerProvider);
            builder.put(GarbageMonitor.class, this.serviceProvider);
            builder.put(GlobalActionsComponent.class, this.globalActionsComponentProvider);
            builder.put(InstantAppNotifier.class, this.instantAppNotifierProvider);
            builder.put(KeyboardUI.class, this.keyboardUIProvider);
            builder.put(KeyguardBiometricLockoutLogger.class, this.keyguardBiometricLockoutLoggerProvider);
            builder.put(KeyguardViewMediator.class, this.newKeyguardViewMediatorProvider);
            builder.put(LatencyTester.class, this.latencyTesterProvider);
            builder.put(PowerUI.class, this.powerUIProvider);
            builder.put(Recents.class, this.provideRecentsProvider);
            builder.put(RingtonePlayer.class, this.ringtonePlayerProvider);
            builder.put(ScreenDecorations.class, this.screenDecorationsProvider);
            builder.put(SessionTracker.class, this.sessionTrackerProvider);
            builder.put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider);
            builder.put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider);
            builder.put(StorageNotification.class, this.storageNotificationProvider);
            builder.put(SystemActions.class, this.systemActionsProvider);
            builder.put(ThemeOverlayController.class, this.themeOverlayControllerProvider);
            builder.put(ToastUI.class, this.toastUIProvider);
            builder.put(VolumeUI.class, this.volumeUIProvider);
            builder.put(WindowMagnification.class, this.windowMagnificationProvider);
            builder.put(WMShell.class, this.wMShellProvider);
            builder.put(GoogleServices.class, this.googleServicesProvider);
            builder.put(StatusBar.class, this.statusBarGoogleProvider);
            builder.put(DockMonitor.class, this.dockMonitorProvider);
            builder.put(DockEventSimulator.class, this.dockEventSimulatorProvider);
            builder.put(DreamClockTimeComplication.Registrant.class, this.registrantProvider);
            builder.put(DreamClockDateComplication.Registrant.class, this.registrantProvider2);
            builder.put(DreamOverlayRegistrant.class, this.dreamOverlayRegistrantProvider);
            builder.put(DreamWeatherComplication.Registrant.class, this.registrantProvider3);
            builder.put(MediaDreamSentinel.class, this.mediaDreamSentinelProvider);
            builder.put(SmartSpaceComplication.Registrant.class, this.registrantProvider4);
            builder.put(ComplicationTypesUpdater.class, this.complicationTypesUpdaterProvider);
            MapProviderFactory build = builder.build();
            this.mapOfClassOfAndProviderOfCoreStartableProvider = build;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            Provider<Context> provider5 = daggerTitanGlobalRootComponent2.contextProvider;
            Provider<DumpManager> provider6 = daggerTitanGlobalRootComponent2.dumpManagerProvider;
            DumpHandler_Factory dumpHandler_Factory = new DumpHandler_Factory(provider5, provider6, this.logBufferEulogizerProvider, build);
            this.dumpHandlerProvider = dumpHandler_Factory;
            LogBufferFreezer_Factory logBufferFreezer_Factory = new LogBufferFreezer_Factory(provider6, daggerTitanGlobalRootComponent2.provideMainDelayableExecutorProvider);
            this.logBufferFreezerProvider = logBufferFreezer_Factory;
            TakeScreenshotService_Factory takeScreenshotService_Factory = new TakeScreenshotService_Factory(this.provideBatteryControllerProvider, daggerTitanGlobalRootComponent2.provideNotificationManagerProvider, this.provideDelayableExecutorProvider, provider5, 2);
            this.batteryStateNotifierProvider = takeScreenshotService_Factory;
            this.systemUIServiceProvider = SystemUIService_Factory.create(daggerTitanGlobalRootComponent2.provideMainHandlerProvider, dumpHandler_Factory, this.providesBroadcastDispatcherProvider, logBufferFreezer_Factory, takeScreenshotService_Factory);
            this.systemUIAuxiliaryDumpServiceProvider = new ImageTileSet_Factory(this.dumpHandlerProvider, 2);
            Provider<Looper> provider7 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideLongRunningLooperFactory.InstanceHolder.INSTANCE);
            this.provideLongRunningLooperProvider = provider7;
            Provider<Executor> m = C0772x82ed519d.m53m(provider7, 5);
            this.provideLongRunningExecutorProvider = m;
            Provider<RecordingController> provider8 = this.recordingControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.recordingServiceProvider = RecordingService_Factory.create(provider8, m, daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, daggerTitanGlobalRootComponent3.provideNotificationManagerProvider, this.provideUserTrackerProvider, this.keyguardDismissUtilProvider);
            this.notificationVoiceReplyManagerServiceProvider = new NotificationVoiceReplyManagerService_Factory(this.notificationVoiceReplyControllerProvider, this.notificationVoiceReplyLoggerProvider);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            this.columbusTargetRequestServiceProvider = new ColumbusTargetRequestService_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.provideUserTrackerProvider, this.columbusSettingsProvider, this.columbusStructuredDataManagerProvider, daggerTitanGlobalRootComponent4.provideUiEventLoggerProvider, daggerTitanGlobalRootComponent4.provideMainHandlerProvider, this.provideBgLooperProvider);
            MapProviderFactory.Builder builder2 = new MapProviderFactory.Builder(11);
            builder2.put(TakeScreenshotService.class, this.takeScreenshotServiceProvider);
            builder2.put(DozeService.class, this.dozeServiceProvider);
            builder2.put(ImageWallpaper.class, ImageWallpaper_Factory.InstanceHolder.INSTANCE);
            builder2.put(KeyguardService.class, this.keyguardServiceProvider);
            builder2.put(DreamOverlayService.class, this.dreamOverlayServiceProvider);
            builder2.put(NotificationListenerWithPlugins.class, this.notificationListenerWithPluginsProvider);
            builder2.put(SystemUIService.class, this.systemUIServiceProvider);
            builder2.put(SystemUIAuxiliaryDumpService.class, this.systemUIAuxiliaryDumpServiceProvider);
            builder2.put(RecordingService.class, this.recordingServiceProvider);
            builder2.put(NotificationVoiceReplyManagerService.class, this.notificationVoiceReplyManagerServiceProvider);
            builder2.put(ColumbusTargetRequestService.class, this.columbusTargetRequestServiceProvider);
            this.mapOfClassOfAndProviderOfServiceProvider = builder2.build();
            this.overviewProxyRecentsImplProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.optionalOfStatusBarProvider, 2));
            LinkedHashMap newLinkedHashMapWithExpectedSize = R$id.newLinkedHashMapWithExpectedSize(1);
            Provider<OverviewProxyRecentsImpl> provider9 = this.overviewProxyRecentsImplProvider;
            Objects.requireNonNull(provider9, "provider");
            newLinkedHashMapWithExpectedSize.put(OverviewProxyRecentsImpl.class, provider9);
            this.mapOfClassOfAndProviderOfRecentsImplementationProvider = new MapProviderFactory(newLinkedHashMapWithExpectedSize);
            Provider<Optional<StatusBar>> provider10 = this.optionalOfStatusBarProvider;
            Provider<ActivityManagerWrapper> provider11 = this.provideActivityManagerWrapperProvider;
            Provider<ScreenshotSmartActions> provider12 = this.screenshotSmartActionsProvider;
            this.actionProxyReceiverProvider = new ActionProxyReceiver_Factory(provider10, provider11, provider12, 0);
            this.deleteScreenshotReceiverProvider = new VibratorHelper_Factory(provider12, this.provideBackgroundExecutorProvider, 2);
            this.smartActionsReceiverProvider = new SmartActionsReceiver_Factory(provider12, 0);
            this.mediaOutputDialogReceiverProvider = new MediaOutputDialogReceiver_Factory(this.mediaOutputDialogFactoryProvider, 0);
            this.peopleSpaceWidgetPinnedReceiverProvider = new ScreenLifecycle_Factory(this.peopleSpaceWidgetManagerProvider, 2);
            this.peopleSpaceWidgetProvider = new PeopleSpaceWidgetProvider_Factory(this.peopleSpaceWidgetManagerProvider, 0);
            LinkedHashMap newLinkedHashMapWithExpectedSize2 = R$id.newLinkedHashMapWithExpectedSize(6);
            Provider<ActionProxyReceiver> provider13 = this.actionProxyReceiverProvider;
            Objects.requireNonNull(provider13, "provider");
            newLinkedHashMapWithExpectedSize2.put(ActionProxyReceiver.class, provider13);
            Provider<DeleteScreenshotReceiver> provider14 = this.deleteScreenshotReceiverProvider;
            Objects.requireNonNull(provider14, "provider");
            newLinkedHashMapWithExpectedSize2.put(DeleteScreenshotReceiver.class, provider14);
            Provider<SmartActionsReceiver> provider15 = this.smartActionsReceiverProvider;
            Objects.requireNonNull(provider15, "provider");
            newLinkedHashMapWithExpectedSize2.put(SmartActionsReceiver.class, provider15);
            Provider<MediaOutputDialogReceiver> provider16 = this.mediaOutputDialogReceiverProvider;
            Objects.requireNonNull(provider16, "provider");
            newLinkedHashMapWithExpectedSize2.put(MediaOutputDialogReceiver.class, provider16);
            Provider<PeopleSpaceWidgetPinnedReceiver> provider17 = this.peopleSpaceWidgetPinnedReceiverProvider;
            Objects.requireNonNull(provider17, "provider");
            newLinkedHashMapWithExpectedSize2.put(PeopleSpaceWidgetPinnedReceiver.class, provider17);
            Provider<PeopleSpaceWidgetProvider> provider18 = this.peopleSpaceWidgetProvider;
            Objects.requireNonNull(provider18, "provider");
            newLinkedHashMapWithExpectedSize2.put(PeopleSpaceWidgetProvider.class, provider18);
            MapProviderFactory mapProviderFactory = new MapProviderFactory(newLinkedHashMapWithExpectedSize2);
            this.mapOfClassOfAndProviderOfBroadcastReceiverProvider = mapProviderFactory;
            DelegateFactory.setDelegate(this.contextComponentResolverProvider, DoubleCheck.provider(new ContextComponentResolver_Factory(this.mapOfClassOfAndProviderOfActivityProvider, this.mapOfClassOfAndProviderOfServiceProvider, this.mapOfClassOfAndProviderOfRecentsImplementationProvider, mapProviderFactory)));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            this.unfoldLatencyTrackerProvider = DoubleCheck.provider(new UnfoldLatencyTracker_Factory(daggerTitanGlobalRootComponent5.provideLatencyTrackerProvider, daggerTitanGlobalRootComponent5.provideDeviceStateManagerProvider, this.provideUiBackgroundExecutorProvider, daggerTitanGlobalRootComponent5.contextProvider, daggerTitanGlobalRootComponent5.screenLifecycleProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            Provider<Context> provider19 = daggerTitanGlobalRootComponent6.contextProvider;
            Provider<Handler> provider20 = this.provideBgHandlerProvider;
            this.provideNightDisplayListenerProvider = new GestureSensorImpl_Factory(nightDisplayListenerModule, (Provider) provider19, (Provider) provider20);
            this.provideReduceBrightColorsListenerProvider = DoubleCheck.provider(new TvPipModule_ProvidesTvPipMenuControllerFactory(dependencyProvider, (Provider) provider20, (Provider) this.provideUserTrackerProvider, (Provider) daggerTitanGlobalRootComponent6.provideColorDisplayManagerProvider, this.secureSettingsImplProvider));
            this.managedProfileControllerImplProvider = DoubleCheck.provider(new ManagedProfileControllerImpl_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, 0));
            this.accessibilityControllerProvider = DoubleCheck.provider(new UsbDebuggingSecondaryUserActivity_Factory(this.this$0.contextProvider, 2));
            this.tunablePaddingServiceProvider = DoubleCheck.provider(new TunablePadding_TunablePaddingService_Factory(this.tunerServiceImplProvider));
            this.uiOffloadThreadProvider = DoubleCheck.provider(UiOffloadThread_Factory.InstanceHolder.INSTANCE);
            this.remoteInputQuickSettingsDisablerProvider = DoubleCheck.provider(new RemoteInputQuickSettingsDisabler_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.provideConfigurationControllerProvider, 0));
            this.foregroundServiceNotificationListenerProvider = DoubleCheck.provider(ForegroundServiceNotificationListener_Factory.create(this.this$0.contextProvider, this.foregroundServiceControllerProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.bindSystemClockProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            this.clockManagerProvider = DoubleCheck.provider(ClockManager_Factory.create(daggerTitanGlobalRootComponent7.contextProvider, this.providerLayoutInflaterProvider, daggerTitanGlobalRootComponent7.providesPluginManagerProvider, this.sysuiColorExtractorProvider, this.provideDockManagerProvider, this.providesBroadcastDispatcherProvider));
            Provider<OverviewProxyService> provider21 = this.overviewProxyServiceProvider;
            Provider<SysUiState> provider22 = this.provideSysUiStateProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            Provider<PluginManager> provider23 = daggerTitanGlobalRootComponent8.providesPluginManagerProvider;
            Provider<Executor> provider24 = daggerTitanGlobalRootComponent8.provideMainExecutorProvider;
            Provider<BroadcastDispatcher> provider25 = this.providesBroadcastDispatcherProvider;
            Provider<ProtoTracer> provider26 = this.protoTracerProvider;
            Provider<NavigationModeController> provider27 = this.navigationModeControllerProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            this.factoryProvider8 = EdgeBackGestureHandler_Factory_Factory.create(provider21, provider22, provider23, provider24, provider25, provider26, provider27, daggerTitanGlobalRootComponent9.provideViewConfigurationProvider, daggerTitanGlobalRootComponent9.provideWindowManagerProvider, daggerTitanGlobalRootComponent9.provideIWindowManagerProvider, this.falsingManagerProxyProvider);
            Provider<DumpManager> provider28 = this.this$0.dumpManagerProvider;
            Provider<ActivityStarter> provider29 = this.provideActivityStarterProvider;
            Provider<BroadcastDispatcher> provider30 = this.providesBroadcastDispatcherProvider;
            Provider<AsyncSensorManager> provider31 = this.asyncSensorManagerProvider;
            Provider<BluetoothControllerImpl> provider32 = this.bluetoothControllerImplProvider;
            Provider<LocationControllerImpl> provider33 = this.locationControllerImplProvider;
            Provider<RotationLockControllerImpl> provider34 = this.rotationLockControllerImplProvider;
            Provider<ZenModeControllerImpl> provider35 = this.zenModeControllerImplProvider;
            Provider<HdmiCecSetMenuLanguageHelper> provider36 = this.hdmiCecSetMenuLanguageHelperProvider;
            Provider<HotspotControllerImpl> provider37 = this.hotspotControllerImplProvider;
            Provider<CastControllerImpl> provider38 = this.castControllerImplProvider;
            Provider<FlashlightControllerImpl> provider39 = this.flashlightControllerImplProvider;
            Provider<UserSwitcherController> provider40 = this.userSwitcherControllerProvider;
            Provider<UserInfoControllerImpl> provider41 = this.userInfoControllerImplProvider;
            Provider<KeyguardStateControllerImpl> provider42 = this.keyguardStateControllerImplProvider;
            Provider<KeyguardUpdateMonitor> provider43 = this.keyguardUpdateMonitorProvider;
            Provider<BatteryController> provider44 = this.provideBatteryControllerProvider;
            Provider<NightDisplayListener> provider45 = this.provideNightDisplayListenerProvider;
            Provider<ReduceBrightColorsController> provider46 = this.provideReduceBrightColorsListenerProvider;
            Provider<ManagedProfileControllerImpl> provider47 = this.managedProfileControllerImplProvider;
            Provider<NextAlarmControllerImpl> provider48 = this.nextAlarmControllerImplProvider;
            Provider<DataSaverController> provider49 = this.provideDataSaverControllerProvider;
            Provider<AccessibilityController> provider50 = this.accessibilityControllerProvider;
            Provider<DeviceProvisionedController> provider51 = this.provideDeviceProvisionedControllerProvider;
            Provider<PluginManager> provider52 = this.this$0.providesPluginManagerProvider;
            Provider<AssistManagerGoogle> provider53 = this.assistManagerGoogleProvider;
            Provider<SecurityControllerImpl> provider54 = this.securityControllerImplProvider;
            Provider<LeakDetector> provider55 = this.provideLeakDetectorProvider;
            Provider<LeakReporter> provider56 = this.leakReporterProvider;
            Provider<GarbageMonitor> provider57 = this.garbageMonitorProvider;
            Provider<TunerServiceImpl> provider58 = this.tunerServiceImplProvider;
            Provider<NotificationShadeWindowControllerImpl> provider59 = this.notificationShadeWindowControllerImplProvider;
            Provider<StatusBarWindowController> provider60 = this.statusBarWindowControllerProvider;
            Provider<DarkIconDispatcherImpl> provider61 = this.darkIconDispatcherImplProvider;
            Provider<ConfigurationController> provider62 = this.provideConfigurationControllerProvider;
            Provider<StatusBarIconControllerImpl> provider63 = this.statusBarIconControllerImplProvider;
            Provider<ScreenLifecycle> provider64 = this.this$0.screenLifecycleProvider;
            Provider<WakefulnessLifecycle> provider65 = this.wakefulnessLifecycleProvider;
            Provider<FragmentService> provider66 = this.fragmentServiceProvider;
            Provider<ExtensionControllerImpl> provider67 = this.extensionControllerImplProvider;
            Provider<PluginDependencyProvider> provider68 = this.this$0.pluginDependencyProvider;
            Provider<LocalBluetoothManager> provider69 = this.provideLocalBluetoothControllerProvider;
            Provider<VolumeDialogControllerImpl> provider70 = this.volumeDialogControllerImplProvider;
            Provider<MetricsLogger> provider71 = this.provideMetricsLoggerProvider;
            Provider<AccessibilityManagerWrapper> provider72 = this.accessibilityManagerWrapperProvider;
            Provider<SysuiColorExtractor> provider73 = this.sysuiColorExtractorProvider;
            Provider<TunablePadding.TunablePaddingService> provider74 = this.tunablePaddingServiceProvider;
            Provider<ForegroundServiceController> provider75 = this.foregroundServiceControllerProvider;
            Provider<UiOffloadThread> provider76 = this.uiOffloadThreadProvider;
            Provider<PowerUI.WarningsUI> provider77 = this.provideWarningsUiProvider;
            Provider<LightBarController> provider78 = this.lightBarControllerProvider;
            Provider<IWindowManager> provider79 = this.this$0.provideIWindowManagerProvider;
            Provider<OverviewProxyService> provider80 = this.overviewProxyServiceProvider;
            Provider<NavigationModeController> provider81 = this.navigationModeControllerProvider;
            Provider<AccessibilityButtonModeObserver> provider82 = this.accessibilityButtonModeObserverProvider;
            Provider<AccessibilityButtonTargetsObserver> provider83 = this.accessibilityButtonTargetsObserverProvider;
            Provider<EnhancedEstimatesGoogleImpl> provider84 = this.enhancedEstimatesGoogleImplProvider;
            Provider<VibratorHelper> provider85 = this.vibratorHelperProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.dependencyProvider2 = DoubleCheck.provider(Dependency_Factory.create(provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47, provider48, provider49, provider50, provider51, provider52, provider53, provider54, provider55, provider56, provider57, provider58, provider59, provider60, provider61, provider62, provider63, provider64, provider65, provider66, provider67, provider68, provider69, provider70, provider71, provider72, provider73, provider74, provider75, provider76, provider77, provider78, provider79, provider80, provider81, provider82, provider83, provider84, provider85, daggerTitanGlobalRootComponent10.provideIStatusBarServiceProvider, daggerTitanGlobalRootComponent10.provideDisplayMetricsProvider, this.lockscreenGestureLoggerProvider, this.keyguardEnvironmentImplProvider, this.shadeControllerImplProvider, this.statusBarRemoteInputCallbackProvider, this.appOpsControllerImplProvider, this.navigationBarControllerProvider, this.provideAccessibilityFloatingMenuControllerProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerGoogleProvider, this.notificationGroupAlertTransferHelperProvider, this.notificationGroupManagerLegacyProvider, this.provideVisualStabilityManagerProvider, this.provideNotificationGutsManagerProvider, this.provideNotificationMediaManagerProvider, this.provideNotificationRemoteInputManagerProvider, this.smartReplyConstantsProvider, this.notificationListenerProvider, this.provideNotificationLoggerProvider, this.provideNotificationViewHierarchyManagerProvider, this.notificationFilterProvider, this.keyguardDismissUtilProvider, this.provideSmartReplyControllerProvider, this.remoteInputQuickSettingsDisablerProvider, this.provideNotificationEntryManagerProvider, this.this$0.provideSensorPrivacyManagerProvider, this.provideAutoHideControllerProvider, this.foregroundServiceNotificationListenerProvider, this.privacyItemControllerProvider, this.provideBgLooperProvider, this.provideBgHandlerProvider, this.this$0.provideMainHandlerProvider, this.provideTimeTickHandlerProvider, this.provideLeakReportEmailProvider, this.this$0.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.clockManagerProvider, this.provideActivityManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, this.this$0.providePackageManagerWrapperProvider, this.provideSensorPrivacyControllerProvider, this.provideDockManagerProvider, this.provideINotificationManagerProvider, this.provideSysUiStateProvider, this.this$0.provideAlarmManagerProvider, this.keyguardSecurityModelProvider, this.dozeParametersProvider, this.provideCommandQueueProvider, this.recordingControllerProvider, this.protoTracerProvider, this.mediaOutputDialogFactoryProvider, this.deviceConfigProxyProvider, this.entryPointControllerProvider, this.telephonyListenerManagerProvider, this.systemStatusAnimationSchedulerProvider, this.privacyDotViewControllerProvider, this.factoryProvider8, this.this$0.provideUiEventLoggerProvider, this.statusBarContentInsetsProvider, this.internetDialogFactoryProvider, this.featureFlagsReleaseProvider, this.notificationSectionsManagerProvider, this.screenOffAnimationControllerProvider, this.ambientStateProvider, this.provideGroupMembershipManagerProvider, this.provideGroupExpansionManagerProvider));
            this.mediaTttFlagsProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.featureFlagsReleaseProvider, 3));
            Provider<CommandQueue> provider86 = this.provideCommandQueueProvider;
            Provider<Context> provider87 = this.this$0.contextProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            this.mediaTttChipControllerSenderProvider = DoubleCheck.provider(new MediaTttChipControllerSender_Factory(provider86, provider87, daggerTitanGlobalRootComponent11.provideWindowManagerProvider, daggerTitanGlobalRootComponent11.provideMainDelayableExecutorProvider));
            this.providesMediaTttChipControllerSenderProvider = DoubleCheck.provider(new MediaModule_ProvidesMediaTttChipControllerSenderFactory(this.mediaTttFlagsProvider, this.mediaTttChipControllerSenderProvider, 0));
            this.mediaTttChipControllerReceiverProvider = DoubleCheck.provider(new MediaTttChipControllerReceiver_Factory(this.provideCommandQueueProvider, this.this$0.contextProvider, this.this$0.provideWindowManagerProvider, this.provideDelayableExecutorProvider, this.this$0.provideMainHandlerProvider));
            this.providesMediaTttChipControllerReceiverProvider = DoubleCheck.provider(new NotifBindPipelineInitializer_Factory(this.mediaTttFlagsProvider, this.mediaTttChipControllerReceiverProvider, 1));
            this.mediaTttCommandLineHelperProvider = DoubleCheck.provider(new ScreenOnCoordinator_Factory(this.commandRegistryProvider, this.this$0.contextProvider, this.this$0.provideMainExecutorProvider, 1));
            this.providesMediaTttCommandLineHelperProvider = DoubleCheck.provider(new ScreenPinningRequest_Factory(this.mediaTttFlagsProvider, this.mediaTttCommandLineHelperProvider, 1));
            this.mediaMuteAwaitConnectionCliProvider = DoubleCheck.provider(new KeyguardVisibility_Factory(this.commandRegistryProvider, this.this$0.contextProvider, 1));
            this.providesMediaMuteAwaitConnectionCliProvider = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(this.mediaFlagsProvider, this.mediaMuteAwaitConnectionCliProvider, 2));
            this.nearbyMediaDevicesManagerProvider = DoubleCheck.provider(new ActionClickLogger_Factory(this.provideCommandQueueProvider, 2));
            this.providesNearbyMediaDevicesManagerProvider = DoubleCheck.provider(new DependencyProvider_ProviderLayoutInflaterFactory(this.mediaFlagsProvider, this.nearbyMediaDevicesManagerProvider, 1));
            this.notificationChannelsProvider = new DozeAuthRemover_Factory(this.this$0.contextProvider, 3);
            this.provideClockInfoListProvider = new ImageExporter_Factory(this.clockManagerProvider, 1);
            Provider<Context> provider88 = this.this$0.contextProvider;
            Provider<BcSmartspaceDataPlugin> provider89 = this.provideBcSmartspaceDataPluginProvider;
            Provider<ZenModeControllerImpl> provider90 = this.zenModeControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent12 = this.this$0;
            this.keyguardZenAlarmViewControllerProvider = new KeyguardZenAlarmViewController_Factory(provider88, provider89, provider90, daggerTitanGlobalRootComponent12.provideAlarmManagerProvider, this.nextAlarmControllerImplProvider, daggerTitanGlobalRootComponent12.provideMainHandlerProvider);
            this.keyguardMediaViewControllerProvider = new PostureDependentProximitySensor_Factory(this.this$0.contextProvider, this.provideBcSmartspaceDataPluginProvider, this.this$0.provideMainDelayableExecutorProvider, this.provideNotificationMediaManagerProvider, this.providesBroadcastDispatcherProvider, 1);
            this.keyguardSmartspaceControllerProvider = DoubleCheck.provider(new KeyguardSmartspaceController_Factory(this.this$0.contextProvider, this.featureFlagsReleaseProvider, this.keyguardZenAlarmViewControllerProvider, this.keyguardMediaViewControllerProvider, 0));
            this.setDisplayAreaHelperProvider = InstanceFactory.create(optional11);
            this.provideAllowNotificationLongPressProvider = DoubleCheck.provider(SystemUIGoogleModule_ProvideAllowNotificationLongPressFactory.InstanceHolder.INSTANCE);
            this.communalStateControllerProvider = DoubleCheck.provider(CommunalStateController_Factory.InstanceHolder.INSTANCE);
            this.sectionClassifierProvider = DoubleCheck.provider(SectionClassifier_Factory.InstanceHolder.INSTANCE);
            this.providesAlertingHeaderNodeControllerProvider = new AssistantWarmer_Factory(this.providesAlertingHeaderSubcomponentProvider, 2);
            this.providesSilentHeaderNodeControllerProvider = new StatusBarInitializer_Factory(this.providesSilentHeaderSubcomponentProvider, 3);
            this.providesIncomingHeaderNodeControllerProvider = new TimeoutManager_Factory(this.providesIncomingHeaderSubcomponentProvider, 2);
            this.provideNotifGutsViewManagerProvider = DoubleCheck.provider(new DozeLogger_Factory(this.provideNotificationGutsManagerProvider, 4));
            this.providesPeopleHeaderNodeControllerProvider = new MediaControllerFactory_Factory(this.providesPeopleHeaderSubcomponentProvider, 4);
            this.notifUiAdjustmentProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideOptionalVibratorFactory(this.sectionClassifierProvider, 1));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent13 = this.this$0;
            Provider<Context> provider91 = daggerTitanGlobalRootComponent13.contextProvider;
            Provider<BroadcastDispatcher> provider92 = this.providesBroadcastDispatcherProvider;
            Provider<DevicePolicyManager> provider93 = daggerTitanGlobalRootComponent13.provideDevicePolicyManagerProvider;
            Provider<UserManager> provider94 = daggerTitanGlobalRootComponent13.provideUserManagerProvider;
            Provider<NotificationVisibilityProvider> provider95 = this.provideNotificationVisibilityProvider;
            Provider<CommonNotifCollection> provider96 = this.provideCommonNotifCollectionProvider;
            Provider<NotificationClickNotifier> provider97 = this.notificationClickNotifierProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent14 = this.this$0;
            this.notificationLockscreenUserManagerImplProvider = DoubleCheck.provider(AuthController_Factory.create$1(provider91, provider92, provider93, provider94, provider95, provider96, provider97, daggerTitanGlobalRootComponent14.provideKeyguardManagerProvider, this.statusBarStateControllerImplProvider, daggerTitanGlobalRootComponent14.provideMainHandlerProvider, this.provideDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.this$0.dumpManagerProvider));
            this.qSTileHostProvider = new DelegateFactory();
            Provider<LogBuffer> provider98 = DoubleCheck.provider(new KeyboardUI_Factory(this.logBufferFactoryProvider, 3));
            this.provideQuickSettingsLogBufferProvider = provider98;
            this.qSLoggerProvider = new QSLogger_Factory(provider98, 0);
            this.customTileStatePersisterProvider = new LiftToActivateListener_Factory(this.this$0.contextProvider, 3);
            this.tileServicesProvider = new TileServices_Factory(this.qSTileHostProvider, this.providesBroadcastDispatcherProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider);
            this.builderProvider10 = CustomTile_Builder_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.customTileStatePersisterProvider, this.tileServicesProvider);
            this.wifiTileProvider = WifiTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider);
            this.internetTileProvider = InternetTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider, this.internetDialogFactoryProvider);
            this.bluetoothTileProvider = BluetoothTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.bluetoothControllerImplProvider);
            this.cellularTileProvider = CellularTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.dndTileProvider = DndTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.zenModeControllerImplProvider, this.provideSharePreferencesProvider, this.secureSettingsImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.colorInversionTileProvider = ColorInversionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.airplaneModeTileProvider = AirplaneModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.providesBroadcastDispatcherProvider, this.this$0.provideConnectivityManagagerProvider, this.globalSettingsImplProvider);
            this.workModeTileProvider = WorkModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.managedProfileControllerImplProvider);
            this.rotationLockTileGoogleProvider = RotationLockTileGoogle_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.rotationLockControllerImplProvider, this.this$0.provideSensorPrivacyManagerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider, this.providesDeviceStateRotationLockDefaultsProvider, this.devicePostureControllerImplProvider);
            this.flashlightTileProvider = FlashlightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.flashlightControllerImplProvider);
            this.locationTileProvider = LocationTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.castTileProvider = CastTile_Factory.create$1(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.castControllerImplProvider, this.keyguardStateControllerImplProvider, this.networkControllerImplProvider, this.hotspotControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.hotspotTileProvider = HotspotTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider);
            this.batterySaverTileGoogleProvider = new BatterySaverTileGoogle_Factory(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider);
            this.dataSaverTileProvider = DataSaverTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideDataSaverControllerProvider, this.provideDialogLaunchAnimatorProvider);
            this.builderProvider11 = new NightDisplayListenerModule_Builder_Factory(this.this$0.contextProvider, this.provideBgHandlerProvider);
            this.nightDisplayTileProvider = NightDisplayTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, this.this$0.provideColorDisplayManagerProvider, this.builderProvider11);
            this.nfcTileProvider = NfcTile_Factory.create$1(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.providesBroadcastDispatcherProvider);
            this.memoryTileProvider = GarbageMonitor_MemoryTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.garbageMonitorProvider);
            this.uiModeNightTileProvider = UiModeNightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideConfigurationControllerProvider, this.provideBatteryControllerProvider, this.locationControllerImplProvider);
            this.screenRecordTileProvider = ScreenRecordTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.recordingControllerProvider, this.keyguardDismissUtilProvider, this.keyguardStateControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.reverseChargingTileProvider = new OneHandedModeTile_Factory(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideBatteryControllerProvider, this.provideIThermalServiceProvider, 1);
            Provider<Boolean> m2 = C0773x82ed519e.m54m(this.this$0.contextProvider, 4);
            this.isReduceBrightColorsAvailableProvider = m2;
            this.reduceBrightColorsTileProvider = ReduceBrightColorsTile_Factory.create(m2, this.provideReduceBrightColorsListenerProvider, this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.cameraToggleTileProvider = CameraToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
        }

        public final void initialize9(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            this.microphoneToggleTileProvider = MicrophoneToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
            this.providesControlsFeatureEnabledProvider = DoubleCheck.provider(new TypeClassifier_Factory(this.this$0.providePackageManagerProvider, 2));
            Provider<GoogleControlsTileResourceConfigurationImpl> m = C0771xb6bb24d9.m52m(this.controlsControllerImplProvider, 8);
            this.googleControlsTileResourceConfigurationImplProvider = m;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider = new PresentJdkOptionalInstanceProvider(m);
            this.optionalOfControlsTileResourceConfigurationProvider = presentJdkOptionalInstanceProvider;
            Provider<ControlsComponent> provider = DoubleCheck.provider(ControlsComponent_Factory.create(this.providesControlsFeatureEnabledProvider, this.this$0.contextProvider, this.controlsControllerImplProvider, this.controlsUiControllerImplProvider, this.controlsListingControllerImplProvider, this.provideLockPatternUtilsProvider, this.keyguardStateControllerImplProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, presentJdkOptionalInstanceProvider));
            this.controlsComponentProvider = provider;
            this.deviceControlsTileProvider = DeviceControlsTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, provider, this.keyguardStateControllerImplProvider);
            this.alarmTileProvider = AlarmTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.nextAlarmControllerImplProvider);
            Provider<QSTileHost> provider2 = this.qSTileHostProvider;
            Provider<Looper> provider3 = this.provideBgLooperProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            this.overlayToggleTileProvider = new OverlayToggleTile_Factory(provider2, provider3, daggerTitanGlobalRootComponent.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, daggerTitanGlobalRootComponent.provideOverlayManagerProvider, 0);
            Provider<QuickAccessWalletClient> provider4 = DoubleCheck.provider(new DozeLogger_Factory(daggerTitanGlobalRootComponent.contextProvider, 6));
            this.provideQuickAccessWalletClientProvider = provider4;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = this.this$0;
            Provider<QuickAccessWalletController> provider5 = DoubleCheck.provider(QuickAccessWalletController_Factory.create(daggerTitanGlobalRootComponent2.contextProvider, daggerTitanGlobalRootComponent2.provideMainExecutorProvider, this.provideExecutorProvider, this.secureSettingsImplProvider, provider4, this.bindSystemClockProvider));
            this.quickAccessWalletControllerProvider = provider5;
            Provider<QSTileHost> provider6 = this.qSTileHostProvider;
            Provider<Looper> provider7 = this.provideBgLooperProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = this.this$0;
            this.quickAccessWalletTileProvider = QuickAccessWalletTile_Factory.create(provider6, provider7, daggerTitanGlobalRootComponent3.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.keyguardStateControllerImplProvider, daggerTitanGlobalRootComponent3.providePackageManagerProvider, this.secureSettingsImplProvider, provider5);
            Provider<QRCodeScannerController> provider8 = DoubleCheck.provider(DefaultUiController_Factory.create$1(this.this$0.contextProvider, this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, this.deviceConfigProxyProvider, this.provideUserTrackerProvider));
            this.qRCodeScannerControllerProvider = provider8;
            this.qRCodeScannerTileProvider = QRCodeScannerTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, provider8);
            this.oneHandedModeTileProvider = OneHandedModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            ColorCorrectionTile_Factory create = ColorCorrectionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.colorCorrectionTileProvider = create;
            this.qSFactoryImplGoogleProvider = DoubleCheck.provider(QSFactoryImplGoogle_Factory.create(this.qSTileHostProvider, this.builderProvider10, this.wifiTileProvider, this.internetTileProvider, this.bluetoothTileProvider, this.cellularTileProvider, this.dndTileProvider, this.colorInversionTileProvider, this.airplaneModeTileProvider, this.workModeTileProvider, this.rotationLockTileGoogleProvider, this.flashlightTileProvider, this.locationTileProvider, this.castTileProvider, this.hotspotTileProvider, this.batterySaverTileGoogleProvider, this.dataSaverTileProvider, this.nightDisplayTileProvider, this.nfcTileProvider, this.memoryTileProvider, this.uiModeNightTileProvider, this.screenRecordTileProvider, this.reverseChargingTileProvider, this.reduceBrightColorsTileProvider, this.cameraToggleTileProvider, this.microphoneToggleTileProvider, this.deviceControlsTileProvider, this.alarmTileProvider, this.overlayToggleTileProvider, this.quickAccessWalletTileProvider, this.qRCodeScannerTileProvider, this.oneHandedModeTileProvider, create));
            Provider provider9 = this.secureSettingsImplProvider;
            Provider<BroadcastDispatcher> provider10 = this.providesBroadcastDispatcherProvider;
            Provider<QSTileHost> provider11 = this.qSTileHostProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = this.this$0;
            this.builderProvider12 = DoubleCheck.provider(AutoAddTracker_Builder_Factory.create(provider9, provider10, provider11, daggerTitanGlobalRootComponent4.dumpManagerProvider, daggerTitanGlobalRootComponent4.provideMainHandlerProvider, this.provideBackgroundExecutorProvider));
            this.deviceControlsControllerImplProvider = DoubleCheck.provider(new ClipboardListener_Factory(this.this$0.contextProvider, this.controlsComponentProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, 2));
            Provider<WalletControllerImpl> provider12 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.provideQuickAccessWalletClientProvider, 5));
            this.walletControllerImplProvider = provider12;
            this.provideAutoTileManagerProvider = QSModuleGoogle_ProvideAutoTileManagerFactory.create(this.this$0.contextProvider, this.builderProvider12, this.qSTileHostProvider, this.provideBgHandlerProvider, this.secureSettingsImplProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider, this.managedProfileControllerImplProvider, this.provideNightDisplayListenerProvider, this.castControllerImplProvider, this.provideBatteryControllerProvider, this.provideReduceBrightColorsListenerProvider, this.deviceControlsControllerImplProvider, provider12, this.isReduceBrightColorsAvailableProvider);
            this.builderProvider13 = DoubleCheck.provider(new TileServiceRequestController_Builder_Factory(this.provideCommandQueueProvider, this.commandRegistryProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = this.this$0;
            Provider<Context> provider13 = daggerTitanGlobalRootComponent5.contextProvider;
            PackageManagerAdapter_Factory packageManagerAdapter_Factory = new PackageManagerAdapter_Factory(provider13, 0);
            this.packageManagerAdapterProvider = packageManagerAdapter_Factory;
            C2507TileLifecycleManager_Factory tileLifecycleManager_Factory = new C2507TileLifecycleManager_Factory(daggerTitanGlobalRootComponent5.provideMainHandlerProvider, provider13, this.tileServicesProvider, packageManagerAdapter_Factory, this.providesBroadcastDispatcherProvider);
            this.tileLifecycleManagerProvider = tileLifecycleManager_Factory;
            InstanceFactory create2 = InstanceFactory.create(new TileLifecycleManager_Factory_Impl(tileLifecycleManager_Factory));
            InstanceFactory instanceFactory = create2;
            this.factoryProvider9 = create2;
            Provider<QSTileHost> provider14 = this.qSTileHostProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent6 = this.this$0;
            DelegateFactory.setDelegate(provider14, DoubleCheck.provider(QSTileHost_Factory.create(daggerTitanGlobalRootComponent6.contextProvider, this.statusBarIconControllerImplProvider, this.qSFactoryImplGoogleProvider, daggerTitanGlobalRootComponent6.provideMainHandlerProvider, this.provideBgLooperProvider, daggerTitanGlobalRootComponent6.providesPluginManagerProvider, this.tunerServiceImplProvider, this.provideAutoTileManagerProvider, daggerTitanGlobalRootComponent6.dumpManagerProvider, this.providesBroadcastDispatcherProvider, this.optionalOfStatusBarProvider, this.qSLoggerProvider, daggerTitanGlobalRootComponent6.provideUiEventLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, this.customTileStatePersisterProvider, this.builderProvider13, instanceFactory)));
            this.providesQSMediaHostProvider = DoubleCheck.provider(new MediaModule_ProvidesQSMediaHostFactory(this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesQuickQSMediaHostProvider = DoubleCheck.provider(new MediaModule_ProvidesQuickQSMediaHostFactory(this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.provideQSFragmentDisableLogBufferProvider = DoubleCheck.provider(new VrMode_Factory(this.logBufferFactoryProvider, 3));
            this.disableFlagsLoggerProvider = DoubleCheck.provider(DisableFlagsLogger_Factory.InstanceHolder.INSTANCE);
            this.notificationShelfComponentBuilderProvider = new Provider<NotificationShelfComponent.Builder>() {
                public final Object get() {
                    return new NotificationShelfComponentBuilder();
                }
            };
            KeyguardLifecyclesDispatcher_Factory keyguardLifecyclesDispatcher_Factory = new KeyguardLifecyclesDispatcher_Factory(this.provideHandlerProvider, this.secureSettingsImplProvider, 1);
            this.communalSettingConditionProvider = keyguardLifecyclesDispatcher_Factory;
            this.provideCommunalConditionsProvider = new QSFragmentModule_ProvidesQuickQSPanelFactory(keyguardLifecyclesDispatcher_Factory, 2);
            int i = SetFactory.$r8$clinit;
            List emptyList = Collections.emptyList();
            ArrayList arrayList = new ArrayList(1);
            SetFactory m2 = C0768xb6bb24d6.m49m(arrayList, this.provideCommunalConditionsProvider, emptyList, arrayList);
            this.namedSetOfConditionProvider = m2;
            CommunalModule_ProvideCommunalSourceMonitorFactory communalModule_ProvideCommunalSourceMonitorFactory = new CommunalModule_ProvideCommunalSourceMonitorFactory(m2, this.monitorComponentFactoryProvider);
            this.provideCommunalSourceMonitorProvider = communalModule_ProvideCommunalSourceMonitorFactory;
            this.communalSourceMonitorProvider = DoubleCheck.provider(new CommunalSourceMonitor_Factory(this.this$0.provideMainExecutorProvider, communalModule_ProvideCommunalSourceMonitorFactory, 0));
            this.keyguardQsUserSwitchComponentFactoryProvider = new Provider<KeyguardQsUserSwitchComponent.Factory>() {
                public final Object get() {
                    return new KeyguardQsUserSwitchComponentFactory();
                }
            };
            this.keyguardUserSwitcherComponentFactoryProvider = new Provider<KeyguardUserSwitcherComponent.Factory>() {
                public final Object get() {
                    return new KeyguardUserSwitcherComponentFactory();
                }
            };
            this.keyguardStatusBarViewComponentFactoryProvider = new Provider<KeyguardStatusBarViewComponent.Factory>() {
                public final Object get() {
                    return new KeyguardStatusBarViewComponentFactory();
                }
            };
            this.communalViewComponentFactoryProvider = new Provider<CommunalViewComponent.Factory>() {
                public final Object get() {
                    return new CommunalViewComponentFactory();
                }
            };
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent7 = this.this$0;
            this.privacyDialogControllerProvider = DoubleCheck.provider(PrivacyDialogController_Factory.create(daggerTitanGlobalRootComponent7.providePermissionManagerProvider, daggerTitanGlobalRootComponent7.providePackageManagerProvider, this.privacyItemControllerProvider, this.provideUserTrackerProvider, this.provideActivityStarterProvider, this.provideBackgroundExecutorProvider, daggerTitanGlobalRootComponent7.provideMainExecutorProvider, this.privacyLoggerProvider, this.keyguardStateControllerImplProvider, this.appOpsControllerImplProvider, daggerTitanGlobalRootComponent7.provideUiEventLoggerProvider));
            this.subscriptionManagerSlotIndexResolverProvider = DoubleCheck.provider(C1010xf95dc14f.InstanceHolder.INSTANCE);
            this.qsFrameTranslateImplProvider = DoubleCheck.provider(new VrMode_Factory(this.statusBarGoogleProvider, 5));
            Provider<LowLightClockControllerImpl> provider15 = DoubleCheck.provider(new ManagedProfileControllerImpl_Factory(this.this$0.provideResourcesProvider, this.providerLayoutInflaterProvider, 3));
            this.lowLightClockControllerImplProvider = provider15;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider2 = new PresentJdkOptionalInstanceProvider(provider15);
            this.dynamicOverrideOptionalOfLowLightClockControllerProvider = presentJdkOptionalInstanceProvider2;
            this.provideLowLightClockControllerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(presentJdkOptionalInstanceProvider2, 0));
            this.provideCollapsedSbFragmentLogBufferProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.logBufferFactoryProvider, 2));
            Provider<UserInfoControllerImpl> provider16 = this.userInfoControllerImplProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent8 = this.this$0;
            this.statusBarUserInfoTrackerProvider = DoubleCheck.provider(new StatusBarUserInfoTracker_Factory(provider16, daggerTitanGlobalRootComponent8.provideUserManagerProvider, daggerTitanGlobalRootComponent8.dumpManagerProvider, daggerTitanGlobalRootComponent8.provideMainExecutorProvider, this.provideBackgroundExecutorProvider));
            this.statusBarUserSwitcherFeatureControllerProvider = DoubleCheck.provider(new ImageExporter_Factory(this.featureFlagsReleaseProvider, 4));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent9 = this.this$0;
            Provider<Context> provider17 = daggerTitanGlobalRootComponent9.contextProvider;
            Provider<UserSwitcherController> provider18 = this.userSwitcherControllerProvider;
            Provider<UiEventLogger> provider19 = daggerTitanGlobalRootComponent9.provideUiEventLoggerProvider;
            Provider<FalsingManagerProxy> provider20 = this.falsingManagerProxyProvider;
            UserDetailView_Adapter_Factory userDetailView_Adapter_Factory = new UserDetailView_Adapter_Factory(provider17, provider18, provider19, provider20);
            this.adapterProvider = userDetailView_Adapter_Factory;
            this.userSwitchDialogControllerProvider = DoubleCheck.provider(new UserSwitchDialogController_Factory(userDetailView_Adapter_Factory, this.provideActivityStarterProvider, provider20, this.provideDialogLaunchAnimatorProvider, provider19));
            Provider<ProximitySensor> provider21 = this.provideProximitySensorProvider;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent10 = this.this$0;
            this.provideProximityCheckProvider = new ForegroundServiceController_Factory(provider21, daggerTitanGlobalRootComponent10.provideMainDelayableExecutorProvider, 2);
            this.builderProvider14 = new FlingAnimationUtils_Builder_Factory(daggerTitanGlobalRootComponent10.provideDisplayMetricsProvider);
            this.providesDreamMediaHostProvider = DoubleCheck.provider(new MediaModule_ProvidesDreamMediaHostFactory(this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent11 = this.this$0;
            this.fgsManagerControllerProvider = DoubleCheck.provider(FgsManagerController_Factory.create(daggerTitanGlobalRootComponent11.contextProvider, daggerTitanGlobalRootComponent11.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.bindSystemClockProvider, daggerTitanGlobalRootComponent11.provideIActivityManagerProvider, daggerTitanGlobalRootComponent11.providePackageManagerProvider, this.deviceConfigProxyProvider, this.provideDialogLaunchAnimatorProvider, daggerTitanGlobalRootComponent11.dumpManagerProvider));
            this.isPMLiteEnabledProvider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(this.featureFlagsReleaseProvider, this.globalSettingsImplProvider, 0));
            this.factoryProvider10 = DoubleCheck.provider(new StatusBarIconController_TintedIconManager_Factory_Factory(this.featureFlagsReleaseProvider));
        }

        public final void inject(KeyguardSliceProvider keyguardSliceProvider) {
            injectKeyguardSliceProvider(keyguardSliceProvider);
        }

        public final ClockOptionsProvider injectClockOptionsProvider(ClockOptionsProvider clockOptionsProvider) {
            clockOptionsProvider.mClockInfosProvider = this.provideClockInfoListProvider;
            return clockOptionsProvider;
        }

        public final KeyguardSliceProvider injectKeyguardSliceProvider(KeyguardSliceProvider keyguardSliceProvider) {
            keyguardSliceProvider.mDozeParameters = this.dozeParametersProvider.get();
            keyguardSliceProvider.mZenModeController = this.zenModeControllerImplProvider.get();
            keyguardSliceProvider.mNextAlarmController = this.nextAlarmControllerImplProvider.get();
            keyguardSliceProvider.mAlarmManager = this.this$0.provideAlarmManagerProvider.get();
            keyguardSliceProvider.mContentResolver = this.this$0.provideContentResolverProvider.get();
            keyguardSliceProvider.mMediaManager = this.provideNotificationMediaManagerProvider.get();
            keyguardSliceProvider.mStatusBarStateController = this.statusBarStateControllerImplProvider.get();
            keyguardSliceProvider.mKeyguardBypassController = this.keyguardBypassControllerProvider.get();
            keyguardSliceProvider.mKeyguardUpdateMonitor = this.keyguardUpdateMonitorProvider.get();
            return keyguardSliceProvider;
        }

        public final KeyguardSliceProviderGoogle injectKeyguardSliceProviderGoogle(KeyguardSliceProviderGoogle keyguardSliceProviderGoogle) {
            keyguardSliceProviderGoogle.mDozeParameters = this.dozeParametersProvider.get();
            keyguardSliceProviderGoogle.mZenModeController = this.zenModeControllerImplProvider.get();
            keyguardSliceProviderGoogle.mNextAlarmController = this.nextAlarmControllerImplProvider.get();
            keyguardSliceProviderGoogle.mAlarmManager = this.this$0.provideAlarmManagerProvider.get();
            keyguardSliceProviderGoogle.mContentResolver = this.this$0.provideContentResolverProvider.get();
            keyguardSliceProviderGoogle.mMediaManager = this.provideNotificationMediaManagerProvider.get();
            keyguardSliceProviderGoogle.mStatusBarStateController = this.statusBarStateControllerImplProvider.get();
            keyguardSliceProviderGoogle.mKeyguardBypassController = this.keyguardBypassControllerProvider.get();
            keyguardSliceProviderGoogle.mKeyguardUpdateMonitor = this.keyguardUpdateMonitorProvider.get();
            keyguardSliceProviderGoogle.mSmartSpaceController = this.smartSpaceControllerProvider.get();
            return keyguardSliceProviderGoogle;
        }

        public final PeopleProvider injectPeopleProvider(PeopleProvider peopleProvider) {
            peopleProvider.mPeopleSpaceWidgetManager = this.peopleSpaceWidgetManagerProvider.get();
            return peopleProvider;
        }

        public final SystemUIAppComponentFactory injectSystemUIAppComponentFactory(SystemUIAppComponentFactory systemUIAppComponentFactory) {
            systemUIAppComponentFactory.mComponentHelper = this.contextComponentResolverProvider.get();
            return systemUIAppComponentFactory;
        }

        public final FlingAnimationUtils namedFlingAnimationUtils() {
            return C0805x4f80e514.providesSwipeToBouncerFlingAnimationUtilsClosing(this.builderProvider14);
        }

        public final FlingAnimationUtils namedFlingAnimationUtils2() {
            return C0806x6a8a7f11.providesSwipeToBouncerFlingAnimationUtilsOpening(this.builderProvider14);
        }

        public final float namedFloat() {
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = this.this$0;
            Provider provider = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            return BouncerSwipeModule.providesSwipeToBouncerStartRegion(daggerTitanGlobalRootComponent.mainResources());
        }

        public final View namedView() {
            View inflate = LayoutInflater.from(this.this$0.context).inflate(C1777R.layout.setup_dream_complication_layout, (ViewGroup) null);
            Objects.requireNonNull(inflate, "Cannot return null from a non-@Nullable @Provides method");
            return inflate;
        }

        public final BootCompleteCacheImpl provideBootCacheImpl() {
            return this.bootCompleteCacheImplProvider.get();
        }

        public final Object secureSettingsImpl() {
            return new SecureSettingsImpl(this.this$0.provideContentResolverProvider.get());
        }

        public final void inject(PeopleProvider peopleProvider) {
            injectPeopleProvider(peopleProvider);
        }

        public final DreamTouchHandler providesBouncerSwipeTouchHandler() {
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler = bouncerSwipeTouchHandler();
            Objects.requireNonNull(bouncerSwipeTouchHandler, "Cannot return null from a non-@Nullable @Provides method");
            return bouncerSwipeTouchHandler;
        }

        public final void inject(KeyguardSliceProviderGoogle keyguardSliceProviderGoogle) {
            injectKeyguardSliceProviderGoogle(keyguardSliceProviderGoogle);
        }

        public final void inject(ClockOptionsProvider clockOptionsProvider) {
            clockOptionsProvider.mClockInfosProvider = this.provideClockInfoListProvider;
        }
    }

    public final class WMComponentBuilder implements WMComponent.Builder {
        public HandlerThread setShellMainThread;

        public WMComponentBuilder() {
        }

        public final WMComponent build() {
            return new WMComponentImpl(DaggerTitanGlobalRootComponent.this, this.setShellMainThread);
        }

        public final WMComponent.Builder setShellMainThread(HandlerThread handlerThread) {
            this.setShellMainThread = handlerThread;
            return this;
        }
    }

    public final class WMComponentImpl implements WMComponent {
        public Provider<Optional<DisplayImeController>> dynamicOverrideOptionalOfDisplayImeControllerProvider;
        public PresentJdkOptionalInstanceProvider dynamicOverrideOptionalOfFreeformTaskListenerProvider;
        public Provider<Optional<FullscreenTaskListener>> dynamicOverrideOptionalOfFullscreenTaskListenerProvider;
        public PresentJdkOptionalInstanceProvider dynamicOverrideOptionalOfFullscreenUnfoldControllerProvider;
        public PresentJdkOptionalInstanceProvider dynamicOverrideOptionalOfOneHandedControllerProvider;
        public PresentJdkOptionalInstanceProvider dynamicOverrideOptionalOfSplitScreenControllerProvider;
        public Provider<Optional<StartingWindowTypeAlgorithm>> dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider;
        public PresentJdkOptionalInstanceProvider optionalOfAppPairsControllerProvider;
        public PresentJdkOptionalInstanceProvider optionalOfBubbleControllerProvider;
        public PresentJdkOptionalInstanceProvider optionalOfLegacySplitScreenControllerProvider;
        public PresentJdkOptionalInstanceProvider optionalOfPipTouchHandlerProvider;
        public PresentJdkOptionalInstanceProvider optionalOfShellUnfoldProgressProvider;
        public Provider<AppPairsController> provideAppPairsProvider;
        public Provider<Optional<Object>> provideAppPairsProvider2;
        public Provider<Optional<BackAnimationController>> provideBackAnimationControllerProvider;
        public Provider<Optional<BackAnimation>> provideBackAnimationProvider;
        public Provider<BubbleController> provideBubbleControllerProvider;
        public Provider<Optional<Bubbles>> provideBubblesProvider;
        public Provider<CompatUIController> provideCompatUIControllerProvider;
        public Provider<Optional<CompatUI>> provideCompatUIProvider;
        public Provider<Optional<DisplayAreaHelper>> provideDisplayAreaHelperProvider;
        public Provider<DisplayController> provideDisplayControllerProvider;
        public Provider<DisplayImeController> provideDisplayImeControllerProvider;
        public Provider<DisplayInsetsController> provideDisplayInsetsControllerProvider;
        public Provider<DisplayLayout> provideDisplayLayoutProvider;
        public Provider<DragAndDropController> provideDragAndDropControllerProvider;
        public Provider<Optional<DragAndDrop>> provideDragAndDropProvider;
        public Provider<FloatingContentCoordinator> provideFloatingContentCoordinatorProvider;
        public Provider<FreeformTaskListener> provideFreeformTaskListenerProvider;
        public Provider<Optional<FreeformTaskListener>> provideFreeformTaskListenerProvider2;
        public Provider<FullscreenTaskListener> provideFullscreenTaskListenerProvider;
        public Provider<FullscreenUnfoldController> provideFullscreenUnfoldControllerProvider;
        public Provider<Optional<FullscreenUnfoldController>> provideFullscreenUnfoldControllerProvider2;
        public Provider<Optional<HideDisplayCutoutController>> provideHideDisplayCutoutControllerProvider;
        public Provider<Optional<HideDisplayCutout>> provideHideDisplayCutoutProvider;
        public Provider<IconProvider> provideIconProvider;
        public Provider<LegacySplitScreenController> provideLegacySplitScreenProvider;
        public Provider<Optional<LegacySplitScreen>> provideLegacySplitScreenProvider2;
        public Provider<OneHandedController> provideOneHandedControllerProvider;
        public Provider<Optional<OneHanded>> provideOneHandedProvider;
        public Provider<PipAnimationController> providePipAnimationControllerProvider;
        public Provider<PipAppOpsListener> providePipAppOpsListenerProvider;
        public Provider<PipBoundsState> providePipBoundsStateProvider;
        public Provider<PipMediaController> providePipMediaControllerProvider;
        public Provider<PipMotionHelper> providePipMotionHelperProvider;
        public Provider<Optional<Pip>> providePipProvider;
        public Provider<PipSnapAlgorithm> providePipSnapAlgorithmProvider;
        public Provider<PipSurfaceTransactionHelper> providePipSurfaceTransactionHelperProvider;
        public Provider<PipTaskOrganizer> providePipTaskOrganizerProvider;
        public Provider<PipTouchHandler> providePipTouchHandlerProvider;
        public Provider<PipTransitionController> providePipTransitionControllerProvider;
        public Provider<PipTransitionState> providePipTransitionStateProvider;
        public Provider<PipUiEventLogger> providePipUiEventLoggerProvider;
        public Provider<Optional<RecentTasksController>> provideRecentTasksControllerProvider;
        public Provider<Optional<RecentTasks>> provideRecentTasksProvider;
        public Provider<ShellTransitions> provideRemoteTransitionsProvider;
        public Provider<RootDisplayAreaOrganizer> provideRootDisplayAreaOrganizerProvider;
        public Provider<RootTaskDisplayAreaOrganizer> provideRootTaskDisplayAreaOrganizerProvider;
        public Provider<ShellExecutor> provideShellAnimationExecutorProvider;
        public Provider<ShellCommandHandlerImpl> provideShellCommandHandlerImplProvider;
        public Provider<Optional<ShellCommandHandler>> provideShellCommandHandlerProvider;
        public Provider<ShellInitImpl> provideShellInitImplProvider;
        public Provider<ShellInit> provideShellInitProvider;
        public Provider<ShellExecutor> provideShellMainExecutorProvider;
        public Provider<AnimationHandler> provideShellMainExecutorSfVsyncAnimationHandlerProvider;
        public Provider<Handler> provideShellMainHandlerProvider;
        public Provider<ShellTaskOrganizer> provideShellTaskOrganizerProvider;
        public Provider<ShellExecutor> provideSplashScreenExecutorProvider;
        public Provider<SplitScreenController> provideSplitScreenControllerProvider;
        public Provider<Optional<SplitScreen>> provideSplitScreenProvider;
        public ControlsProviderSelectorActivity_Factory provideStageTaskUnfoldControllerProvider;
        public Provider<Optional<StartingSurface>> provideStartingSurfaceProvider;
        public Provider<StartingWindowController> provideStartingWindowControllerProvider;
        public Provider<StartingWindowTypeAlgorithm> provideStartingWindowTypeAlgorithmProvider;
        public Provider<SyncTransactionQueue> provideSyncTransactionQueueProvider;
        public Provider<ShellExecutor> provideSysUIMainExecutorProvider;
        public Provider<SystemWindows> provideSystemWindowsProvider;
        public WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory provideTaskSurfaceHelperControllerProvider;
        public Provider<Optional<TaskSurfaceHelper>> provideTaskSurfaceHelperProvider;
        public Provider<TaskViewFactoryController> provideTaskViewFactoryControllerProvider;
        public Provider<Optional<TaskViewFactory>> provideTaskViewFactoryProvider;
        public Provider<TaskViewTransitions> provideTaskViewTransitionsProvider;
        public Provider<TransactionPool> provideTransactionPoolProvider;
        public Provider<Transitions> provideTransitionsProvider;
        public Provider<UnfoldBackgroundController> provideUnfoldBackgroundControllerProvider;
        public Provider<WindowManagerShellWrapper> provideWindowManagerShellWrapperProvider;
        public Provider<TaskStackListenerImpl> providerTaskStackListenerImplProvider;
        public Provider<Optional<OneHandedController>> providesOneHandedControllerProvider;
        public Provider<PipBoundsAlgorithm> providesPipBoundsAlgorithmProvider;
        public Provider<PhonePipMenuController> providesPipPhoneMenuControllerProvider;
        public Provider<Optional<SplitScreenController>> providesSplitScreenControllerProvider;
        public InstanceFactory setShellMainThreadProvider;

        public WMComponentImpl(DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent, HandlerThread handlerThread) {
            InstanceFactory<Object> instanceFactory;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent2 = daggerTitanGlobalRootComponent;
            HandlerThread handlerThread2 = handlerThread;
            if (handlerThread2 == null) {
                instanceFactory = InstanceFactory.NULL_INSTANCE_FACTORY;
            } else {
                instanceFactory = new InstanceFactory<>(handlerThread2);
            }
            this.setShellMainThreadProvider = instanceFactory;
            Provider<Context> provider = daggerTitanGlobalRootComponent2.contextProvider;
            WMShellConcurrencyModule_ProvideMainHandlerFactory wMShellConcurrencyModule_ProvideMainHandlerFactory = WMShellConcurrencyModule_ProvideMainHandlerFactory.InstanceHolder.INSTANCE;
            this.provideShellMainHandlerProvider = DoubleCheck.provider(new WMShellConcurrencyModule_ProvideShellMainHandlerFactory(provider, instanceFactory));
            Provider<ShellExecutor> provider2 = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(wMShellConcurrencyModule_ProvideMainHandlerFactory, 5));
            this.provideSysUIMainExecutorProvider = provider2;
            Provider<ShellExecutor> provider3 = DoubleCheck.provider(new WMShellConcurrencyModule_ProvideShellMainExecutorFactory(daggerTitanGlobalRootComponent2.contextProvider, this.provideShellMainHandlerProvider, provider2));
            this.provideShellMainExecutorProvider = provider3;
            Provider<DisplayController> provider4 = DoubleCheck.provider(new WMShellBaseModule_ProvideDisplayControllerFactory(daggerTitanGlobalRootComponent2.contextProvider, daggerTitanGlobalRootComponent2.provideIWindowManagerProvider, provider3));
            this.provideDisplayControllerProvider = provider4;
            Provider<Optional<FullscreenTaskListener>> provider5 = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.dynamicOverrideOptionalOfDisplayImeControllerProvider = provider5;
            this.provideDisplayInsetsControllerProvider = DoubleCheck.provider(new RemoteInputQuickSettingsDisabler_Factory(daggerTitanGlobalRootComponent2.provideIWindowManagerProvider, provider4, this.provideShellMainExecutorProvider, 1));
            Provider<TransactionPool> provider6 = DoubleCheck.provider(WMShellBaseModule_ProvideTransactionPoolFactory.InstanceHolder.INSTANCE);
            this.provideTransactionPoolProvider = provider6;
            this.provideDisplayImeControllerProvider = DoubleCheck.provider(RecordingService_Factory.create$1(this.dynamicOverrideOptionalOfDisplayImeControllerProvider, daggerTitanGlobalRootComponent2.provideIWindowManagerProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideShellMainExecutorProvider, provider6));
            Provider<IconProvider> provider7 = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(daggerTitanGlobalRootComponent2.contextProvider, 3));
            this.provideIconProvider = provider7;
            this.provideDragAndDropControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDragAndDropControllerFactory.create$1(daggerTitanGlobalRootComponent2.contextProvider, this.provideDisplayControllerProvider, daggerTitanGlobalRootComponent2.provideUiEventLoggerProvider, provider7, this.provideShellMainExecutorProvider));
            Provider<SyncTransactionQueue> provider8 = DoubleCheck.provider(new ResumeMediaBrowserFactory_Factory(this.provideTransactionPoolProvider, this.provideShellMainExecutorProvider, 1));
            this.provideSyncTransactionQueueProvider = provider8;
            this.provideCompatUIControllerProvider = DoubleCheck.provider(SmartReplyStateInflaterImpl_Factory.create$1(daggerTitanGlobalRootComponent2.contextProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDisplayImeControllerProvider, provider8, this.provideShellMainExecutorProvider));
            Provider<TaskStackListenerImpl> provider9 = DoubleCheck.provider(new SeekBarViewModel_Factory(this.provideShellMainHandlerProvider, 1));
            this.providerTaskStackListenerImplProvider = provider9;
            Provider<Optional<RecentTasksController>> provider10 = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksControllerFactory(daggerTitanGlobalRootComponent2.contextProvider, provider9, this.provideShellMainExecutorProvider));
            this.provideRecentTasksControllerProvider = provider10;
            this.provideShellTaskOrganizerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideShellTaskOrganizerFactory(this.provideShellMainExecutorProvider, daggerTitanGlobalRootComponent2.contextProvider, this.provideCompatUIControllerProvider, provider10));
            this.provideFloatingContentCoordinatorProvider = DoubleCheck.provider(WMShellBaseModule_ProvideFloatingContentCoordinatorFactory.InstanceHolder.INSTANCE);
            this.provideWindowManagerShellWrapperProvider = DoubleCheck.provider(new SecureSettingsImpl_Factory(this.provideShellMainExecutorProvider, 4));
            Provider<DisplayLayout> provider11 = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayLayoutFactory.InstanceHolder.INSTANCE);
            this.provideDisplayLayoutProvider = provider11;
            Provider<OneHandedController> provider12 = DoubleCheck.provider(WMShellModule_ProvideOneHandedControllerFactory.create(daggerTitanGlobalRootComponent2.contextProvider, daggerTitanGlobalRootComponent2.provideWindowManagerProvider, this.provideDisplayControllerProvider, provider11, this.providerTaskStackListenerImplProvider, daggerTitanGlobalRootComponent2.provideUiEventLoggerProvider, daggerTitanGlobalRootComponent2.provideInteractionJankMonitorProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider));
            this.provideOneHandedControllerProvider = provider12;
            this.dynamicOverrideOptionalOfOneHandedControllerProvider = new PresentJdkOptionalInstanceProvider(provider12);
            Provider<ShellExecutor> provider13 = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory.InstanceHolder.INSTANCE);
            this.provideShellAnimationExecutorProvider = provider13;
            Provider<Transitions> provider14 = DoubleCheck.provider(KeyguardMediaController_Factory.create$1(this.provideShellTaskOrganizerProvider, this.provideTransactionPoolProvider, this.provideDisplayControllerProvider, daggerTitanGlobalRootComponent2.contextProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider, provider13));
            this.provideTransitionsProvider = provider14;
            Provider<TaskViewTransitions> provider15 = DoubleCheck.provider(new TaskbarDelegate_Factory(provider14, 3));
            this.provideTaskViewTransitionsProvider = provider15;
            Provider<Optional<FullscreenTaskListener>> provider16 = provider5;
            Provider<BubbleController> provider17 = DoubleCheck.provider(WMShellModule_ProvideBubbleControllerFactory.create(daggerTitanGlobalRootComponent2.contextProvider, this.provideFloatingContentCoordinatorProvider, daggerTitanGlobalRootComponent2.provideIStatusBarServiceProvider, daggerTitanGlobalRootComponent2.provideWindowManagerProvider, this.provideWindowManagerShellWrapperProvider, daggerTitanGlobalRootComponent2.provideLauncherAppsProvider, this.providerTaskStackListenerImplProvider, daggerTitanGlobalRootComponent2.provideUiEventLoggerProvider, this.provideShellTaskOrganizerProvider, this.provideDisplayControllerProvider, this.dynamicOverrideOptionalOfOneHandedControllerProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider, provider15, this.provideSyncTransactionQueueProvider));
            this.provideBubbleControllerProvider = provider17;
            this.optionalOfBubbleControllerProvider = new PresentJdkOptionalInstanceProvider(provider17);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent3 = daggerTitanGlobalRootComponent;
            this.provideRootTaskDisplayAreaOrganizerProvider = DoubleCheck.provider(new KeyguardVisibility_Factory(this.provideShellMainExecutorProvider, daggerTitanGlobalRootComponent3.contextProvider, 2));
            this.optionalOfShellUnfoldProgressProvider = new PresentJdkOptionalInstanceProvider(daggerTitanGlobalRootComponent3.provideShellProgressProvider);
            Provider<UnfoldBackgroundController> provider18 = DoubleCheck.provider(new WMShellModule_ProvideUnfoldBackgroundControllerFactory(this.provideRootTaskDisplayAreaOrganizerProvider, daggerTitanGlobalRootComponent3.contextProvider, 0));
            this.provideUnfoldBackgroundControllerProvider = provider18;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider = this.optionalOfShellUnfoldProgressProvider;
            Provider<Context> provider19 = daggerTitanGlobalRootComponent3.contextProvider;
            Provider<TransactionPool> provider20 = this.provideTransactionPoolProvider;
            Provider<DisplayInsetsController> provider21 = this.provideDisplayInsetsControllerProvider;
            Provider<ShellExecutor> provider22 = this.provideShellMainExecutorProvider;
            ControlsProviderSelectorActivity_Factory controlsProviderSelectorActivity_Factory = new ControlsProviderSelectorActivity_Factory(presentJdkOptionalInstanceProvider, provider19, provider20, provider18, provider21, provider22, 1);
            this.provideStageTaskUnfoldControllerProvider = controlsProviderSelectorActivity_Factory;
            Provider<SplitScreenController> provider23 = DoubleCheck.provider(WMShellModule_ProvideSplitScreenControllerFactory.create(this.provideShellTaskOrganizerProvider, this.provideSyncTransactionQueueProvider, provider19, this.provideRootTaskDisplayAreaOrganizerProvider, provider22, this.provideDisplayControllerProvider, this.provideDisplayImeControllerProvider, provider21, this.provideTransitionsProvider, provider20, this.provideIconProvider, this.provideRecentTasksControllerProvider, controlsProviderSelectorActivity_Factory));
            this.provideSplitScreenControllerProvider = provider23;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider2 = new PresentJdkOptionalInstanceProvider(provider23);
            this.dynamicOverrideOptionalOfSplitScreenControllerProvider = presentJdkOptionalInstanceProvider2;
            this.providesSplitScreenControllerProvider = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(presentJdkOptionalInstanceProvider2, daggerTitanGlobalRootComponent3.contextProvider, 4));
            Provider<AppPairsController> provider24 = DoubleCheck.provider(new WMShellModule_ProvideAppPairsFactory(this.provideShellTaskOrganizerProvider, this.provideSyncTransactionQueueProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider, this.provideDisplayImeControllerProvider, this.provideDisplayInsetsControllerProvider, 0));
            this.provideAppPairsProvider = provider24;
            this.optionalOfAppPairsControllerProvider = new PresentJdkOptionalInstanceProvider(provider24);
            this.providePipBoundsStateProvider = DoubleCheck.provider(new DozeAuthRemover_Factory(daggerTitanGlobalRootComponent3.contextProvider, 4));
            this.providePipMediaControllerProvider = DoubleCheck.provider(new VibratorHelper_Factory(daggerTitanGlobalRootComponent3.contextProvider, this.provideShellMainHandlerProvider, 4));
            this.provideSystemWindowsProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(this.provideDisplayControllerProvider, daggerTitanGlobalRootComponent3.provideIWindowManagerProvider, 0));
            Provider<PipUiEventLogger> provider25 = DoubleCheck.provider(new WMShellBaseModule_ProvidePipUiEventLoggerFactory(daggerTitanGlobalRootComponent3.provideUiEventLoggerProvider, daggerTitanGlobalRootComponent3.providePackageManagerProvider));
            this.providePipUiEventLoggerProvider = provider25;
            this.providesPipPhoneMenuControllerProvider = DoubleCheck.provider(UnfoldLightRevealOverlayAnimation_Factory.create$1(daggerTitanGlobalRootComponent3.contextProvider, this.providePipBoundsStateProvider, this.providePipMediaControllerProvider, this.provideSystemWindowsProvider, this.providesSplitScreenControllerProvider, provider25, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider));
            Provider<PipSnapAlgorithm> provider26 = DoubleCheck.provider(WMShellModule_ProvidePipSnapAlgorithmFactory.InstanceHolder.INSTANCE);
            this.providePipSnapAlgorithmProvider = provider26;
            this.providesPipBoundsAlgorithmProvider = DoubleCheck.provider(new WMShellModule_ProvidesPipBoundsAlgorithmFactory(daggerTitanGlobalRootComponent3.contextProvider, this.providePipBoundsStateProvider, provider26));
            this.providePipTransitionStateProvider = DoubleCheck.provider(WMShellModule_ProvidePipTransitionStateFactory.InstanceHolder.INSTANCE);
            Provider<PipSurfaceTransactionHelper> provider27 = DoubleCheck.provider(WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory.InstanceHolder.INSTANCE);
            this.providePipSurfaceTransactionHelperProvider = provider27;
            Provider<PipAnimationController> provider28 = DoubleCheck.provider(new AssistantWarmer_Factory(provider27, 3));
            this.providePipAnimationControllerProvider = provider28;
            this.providePipTransitionControllerProvider = DoubleCheck.provider(WMShellModule_ProvidePipTransitionControllerFactory.create(daggerTitanGlobalRootComponent3.contextProvider, this.provideTransitionsProvider, this.provideShellTaskOrganizerProvider, provider28, this.providesPipBoundsAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipTransitionStateProvider, this.providesPipPhoneMenuControllerProvider, this.providePipSurfaceTransactionHelperProvider, this.providesSplitScreenControllerProvider));
            Provider<AnimationHandler> provider29 = DoubleCheck.provider(new GameDashboardUiEventLogger_Factory(this.provideShellMainExecutorProvider, 1));
            this.provideShellMainExecutorSfVsyncAnimationHandlerProvider = provider29;
            Provider<LegacySplitScreenController> provider30 = DoubleCheck.provider(WMShellModule_ProvideLegacySplitScreenFactory.create(daggerTitanGlobalRootComponent3.contextProvider, this.provideDisplayControllerProvider, this.provideSystemWindowsProvider, this.provideDisplayImeControllerProvider, this.provideTransactionPoolProvider, this.provideShellTaskOrganizerProvider, this.provideSyncTransactionQueueProvider, this.providerTaskStackListenerImplProvider, this.provideTransitionsProvider, this.provideShellMainExecutorProvider, provider29));
            this.provideLegacySplitScreenProvider = provider30;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider3 = new PresentJdkOptionalInstanceProvider(provider30);
            this.optionalOfLegacySplitScreenControllerProvider = presentJdkOptionalInstanceProvider3;
            Provider<PipTaskOrganizer> provider31 = DoubleCheck.provider(WMShellModule_ProvidePipTaskOrganizerFactory.create(daggerTitanGlobalRootComponent3.contextProvider, this.provideSyncTransactionQueueProvider, this.providePipTransitionStateProvider, this.providePipBoundsStateProvider, this.providesPipBoundsAlgorithmProvider, this.providesPipPhoneMenuControllerProvider, this.providePipAnimationControllerProvider, this.providePipSurfaceTransactionHelperProvider, this.providePipTransitionControllerProvider, presentJdkOptionalInstanceProvider3, this.providesSplitScreenControllerProvider, this.provideDisplayControllerProvider, this.providePipUiEventLoggerProvider, this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider));
            this.providePipTaskOrganizerProvider = provider31;
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent4 = daggerTitanGlobalRootComponent;
            Provider<PipMotionHelper> provider32 = DoubleCheck.provider(new WMShellModule_ProvidePipMotionHelperFactory(daggerTitanGlobalRootComponent4.contextProvider, this.providePipBoundsStateProvider, provider31, this.providesPipPhoneMenuControllerProvider, this.providePipSnapAlgorithmProvider, this.providePipTransitionControllerProvider, this.provideFloatingContentCoordinatorProvider));
            this.providePipMotionHelperProvider = provider32;
            Provider<PipTouchHandler> provider33 = DoubleCheck.provider(WMShellModule_ProvidePipTouchHandlerFactory.create(daggerTitanGlobalRootComponent4.contextProvider, this.providesPipPhoneMenuControllerProvider, this.providesPipBoundsAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipTaskOrganizerProvider, provider32, this.provideFloatingContentCoordinatorProvider, this.providePipUiEventLoggerProvider, this.provideShellMainExecutorProvider));
            this.providePipTouchHandlerProvider = provider33;
            this.optionalOfPipTouchHandlerProvider = new PresentJdkOptionalInstanceProvider(provider33);
            Provider<Optional<FullscreenTaskListener>> provider34 = provider16;
            this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider = provider34;
            Provider<FullscreenUnfoldController> provider35 = DoubleCheck.provider(new KeyguardSliceViewController_Factory(daggerTitanGlobalRootComponent4.contextProvider, this.optionalOfShellUnfoldProgressProvider, this.provideUnfoldBackgroundControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideShellMainExecutorProvider, 1));
            this.provideFullscreenUnfoldControllerProvider = provider35;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider4 = new PresentJdkOptionalInstanceProvider(provider35);
            this.dynamicOverrideOptionalOfFullscreenUnfoldControllerProvider = presentJdkOptionalInstanceProvider4;
            Provider<Optional<FullscreenUnfoldController>> provider36 = DoubleCheck.provider(new WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory(presentJdkOptionalInstanceProvider4, this.optionalOfShellUnfoldProgressProvider));
            this.provideFullscreenUnfoldControllerProvider2 = provider36;
            this.provideFullscreenTaskListenerProvider = DoubleCheck.provider(new ColumbusServiceWrapper_Factory(this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider, this.provideSyncTransactionQueueProvider, provider36, this.provideRecentTasksControllerProvider, 1));
            Provider<FreeformTaskListener> provider37 = DoubleCheck.provider(new PackageManagerAdapter_Factory(this.provideSyncTransactionQueueProvider, 3));
            this.provideFreeformTaskListenerProvider = provider37;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider5 = new PresentJdkOptionalInstanceProvider(provider37);
            this.dynamicOverrideOptionalOfFreeformTaskListenerProvider = presentJdkOptionalInstanceProvider5;
            this.provideFreeformTaskListenerProvider2 = DoubleCheck.provider(new VolumeUI_Factory(presentJdkOptionalInstanceProvider5, daggerTitanGlobalRootComponent4.contextProvider, 2));
            this.provideSplashScreenExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory.InstanceHolder.INSTANCE);
            this.dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider = provider34;
            Provider<StartingWindowTypeAlgorithm> provider38 = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(provider34, 5));
            this.provideStartingWindowTypeAlgorithmProvider = provider38;
            Provider<StartingWindowController> provider39 = DoubleCheck.provider(SystemKeyPress_Factory.create(daggerTitanGlobalRootComponent4.contextProvider, this.provideSplashScreenExecutorProvider, provider38, this.provideIconProvider, this.provideTransactionPoolProvider));
            this.provideStartingWindowControllerProvider = provider39;
            Provider<ShellInitImpl> provider40 = DoubleCheck.provider(WMShellBaseModule_ProvideShellInitImplFactory.create(this.provideDisplayControllerProvider, this.provideDisplayImeControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDragAndDropControllerProvider, this.provideShellTaskOrganizerProvider, this.optionalOfBubbleControllerProvider, this.providesSplitScreenControllerProvider, this.optionalOfAppPairsControllerProvider, this.optionalOfPipTouchHandlerProvider, this.provideFullscreenTaskListenerProvider, this.provideFullscreenUnfoldControllerProvider2, this.provideFreeformTaskListenerProvider2, this.provideRecentTasksControllerProvider, this.provideTransitionsProvider, provider39, this.provideShellMainExecutorProvider));
            this.provideShellInitImplProvider = provider40;
            this.provideShellInitProvider = C0771xb6bb24d9.m52m(provider40, 7);
            DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent5 = daggerTitanGlobalRootComponent;
            this.providePipAppOpsListenerProvider = DoubleCheck.provider(new UnfoldSharedModule_HingeAngleProviderFactory((Provider) daggerTitanGlobalRootComponent5.contextProvider, (Provider) this.providePipTouchHandlerProvider, (Provider) this.provideShellMainExecutorProvider));
            Provider<Optional<OneHandedController>> provider41 = DoubleCheck.provider(new PrivacyLogger_Factory(this.dynamicOverrideOptionalOfOneHandedControllerProvider, 5));
            this.providesOneHandedControllerProvider = provider41;
            this.providePipProvider = DoubleCheck.provider(WMShellModule_ProvidePipFactory.create(daggerTitanGlobalRootComponent5.contextProvider, this.provideDisplayControllerProvider, this.providePipAppOpsListenerProvider, this.providesPipBoundsAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipMediaControllerProvider, this.providesPipPhoneMenuControllerProvider, this.providePipTaskOrganizerProvider, this.providePipTouchHandlerProvider, this.providePipTransitionControllerProvider, this.provideWindowManagerShellWrapperProvider, this.providerTaskStackListenerImplProvider, provider41, this.provideShellMainExecutorProvider));
            Provider<Optional<HideDisplayCutoutController>> provider42 = DoubleCheck.provider(new DozeLog_Factory(daggerTitanGlobalRootComponent5.contextProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider, 4));
            this.provideHideDisplayCutoutControllerProvider = provider42;
            Provider<ShellCommandHandlerImpl> provider43 = DoubleCheck.provider(WMShellBaseModule_ProvideShellCommandHandlerImplFactory.create(this.provideShellTaskOrganizerProvider, this.optionalOfLegacySplitScreenControllerProvider, this.providesSplitScreenControllerProvider, this.providePipProvider, this.providesOneHandedControllerProvider, provider42, this.optionalOfAppPairsControllerProvider, this.provideRecentTasksControllerProvider, this.provideShellMainExecutorProvider));
            this.provideShellCommandHandlerImplProvider = provider43;
            this.provideShellCommandHandlerProvider = DoubleCheck.provider(new ForegroundServicesDialog_Factory(provider43, 6));
            this.provideOneHandedProvider = DoubleCheck.provider(new DreamOverlayStateController_Factory(this.providesOneHandedControllerProvider, 2));
            this.provideLegacySplitScreenProvider2 = DoubleCheck.provider(new DozeLogger_Factory(this.optionalOfLegacySplitScreenControllerProvider, 7));
            this.provideSplitScreenProvider = C0773x82ed519e.m54m(this.providesSplitScreenControllerProvider, 0);
            this.provideAppPairsProvider2 = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(this.optionalOfAppPairsControllerProvider, 4));
            this.provideBubblesProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.optionalOfBubbleControllerProvider, 0));
            this.provideHideDisplayCutoutProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.provideHideDisplayCutoutControllerProvider, 0));
            Provider<TaskViewFactoryController> provider44 = DoubleCheck.provider(new WMShellBaseModule_ProvideTaskViewFactoryControllerFactory(this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider, this.provideSyncTransactionQueueProvider, this.provideTaskViewTransitionsProvider));
            this.provideTaskViewFactoryControllerProvider = provider44;
            this.provideTaskViewFactoryProvider = C0770xb6bb24d8.m51m(provider44, 5);
            this.provideRemoteTransitionsProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.provideTransitionsProvider, 4));
            this.provideStartingSurfaceProvider = C0772x82ed519d.m53m(this.provideStartingWindowControllerProvider, 6);
            Provider<RootDisplayAreaOrganizer> provider45 = DoubleCheck.provider(new ImageExporter_Factory(this.provideShellMainExecutorProvider, 6));
            this.provideRootDisplayAreaOrganizerProvider = provider45;
            this.provideDisplayAreaHelperProvider = DoubleCheck.provider(new SingleTapClassifier_Factory(this.provideShellMainExecutorProvider, provider45, 1));
            WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory = new WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory(this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider, 0);
            this.provideTaskSurfaceHelperControllerProvider = wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory;
            this.provideTaskSurfaceHelperProvider = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory, 6));
            this.provideRecentTasksProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksFactory(this.provideRecentTasksControllerProvider, 0));
            this.provideCompatUIProvider = DoubleCheck.provider(new ToastLogger_Factory(this.provideCompatUIControllerProvider, 3));
            this.provideDragAndDropProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.provideDragAndDropControllerProvider, 6));
            Provider<Optional<BackAnimationController>> provider46 = DoubleCheck.provider(new DreamOverlayRegistrant_Factory(daggerTitanGlobalRootComponent5.contextProvider, this.provideShellMainExecutorProvider, 2));
            this.provideBackAnimationControllerProvider = provider46;
            this.provideBackAnimationProvider = C0769xb6bb24d7.m50m(provider46, 6);
        }

        public final Optional<Object> getAppPairs() {
            return this.provideAppPairsProvider2.get();
        }

        public final Optional<BackAnimation> getBackAnimation() {
            return this.provideBackAnimationProvider.get();
        }

        public final Optional<Bubbles> getBubbles() {
            return this.provideBubblesProvider.get();
        }

        public final Optional<CompatUI> getCompatUI() {
            return this.provideCompatUIProvider.get();
        }

        public final Optional<DisplayAreaHelper> getDisplayAreaHelper() {
            return this.provideDisplayAreaHelperProvider.get();
        }

        public final Optional<DragAndDrop> getDragAndDrop() {
            return this.provideDragAndDropProvider.get();
        }

        public final Optional<HideDisplayCutout> getHideDisplayCutout() {
            return this.provideHideDisplayCutoutProvider.get();
        }

        public final Optional<LegacySplitScreen> getLegacySplitScreen() {
            return this.provideLegacySplitScreenProvider2.get();
        }

        public final Optional<OneHanded> getOneHanded() {
            return this.provideOneHandedProvider.get();
        }

        public final Optional<Pip> getPip() {
            return this.providePipProvider.get();
        }

        public final Optional<RecentTasks> getRecentTasks() {
            return this.provideRecentTasksProvider.get();
        }

        public final Optional<ShellCommandHandler> getShellCommandHandler() {
            return this.provideShellCommandHandlerProvider.get();
        }

        public final ShellInit getShellInit() {
            return this.provideShellInitProvider.get();
        }

        public final Optional<SplitScreen> getSplitScreen() {
            return this.provideSplitScreenProvider.get();
        }

        public final Optional<StartingSurface> getStartingSurface() {
            return this.provideStartingSurfaceProvider.get();
        }

        public final Optional<TaskSurfaceHelper> getTaskSurfaceHelper() {
            return this.provideTaskSurfaceHelperProvider.get();
        }

        public final Optional<TaskViewFactory> getTaskViewFactory() {
            return this.provideTaskViewFactoryProvider.get();
        }

        public final ShellTransitions getTransitions() {
            return this.provideRemoteTransitionsProvider.get();
        }
    }

    public static TitanGlobalRootComponent$Builder builder() {
        return new Builder();
    }

    public final TitanSysUIComponent$Builder getSysUIComponent() {
        return new TitanSysUIComponentBuilder();
    }

    public final WMComponent.Builder getWMComponentBuilder() {
        return new WMComponentBuilder();
    }

    public final void initialize(GlobalModule globalModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context2) {
        UnfoldTransitionModule unfoldTransitionModule2 = unfoldTransitionModule;
        UnfoldSharedModule unfoldSharedModule2 = unfoldSharedModule;
        this.contextProvider = InstanceFactory.create(context2);
        this.provideIWindowManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIWindowManagerFactory.InstanceHolder.INSTANCE);
        this.provideUiEventLoggerProvider = DoubleCheck.provider(GlobalModule_ProvideUiEventLoggerFactory.InstanceHolder.INSTANCE);
        this.provideIStatusBarServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIStatusBarServiceFactory.InstanceHolder.INSTANCE);
        this.provideWindowManagerProvider = C0770xb6bb24d8.m51m(this.contextProvider, 1);
        this.provideLauncherAppsProvider = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.contextProvider, 1));
        this.provideInteractionJankMonitorProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideInteractionJankMonitorFactory.InstanceHolder.INSTANCE);
        this.provideUnfoldTransitionConfigProvider = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(unfoldTransitionModule2, this.contextProvider));
        Provider<ContentResolver> provider = DoubleCheck.provider(new AssistantWarmer_Factory(this.contextProvider, 1));
        this.provideContentResolverProvider = provider;
        C2509ScaleAwareTransitionProgressProvider_Factory scaleAwareTransitionProgressProvider_Factory = new C2509ScaleAwareTransitionProgressProvider_Factory(provider);
        this.scaleAwareTransitionProgressProvider = scaleAwareTransitionProgressProvider_Factory;
        this.factoryProvider = InstanceFactory.create(new ScaleAwareTransitionProgressProvider_Factory_Impl(scaleAwareTransitionProgressProvider_Factory));
        ImageTileSet_Factory imageTileSet_Factory = new ImageTileSet_Factory(unfoldTransitionModule2, 10);
        this.tracingTagPrefixProvider = imageTileSet_Factory;
        this.aTraceLoggerTransitionProgressListenerProvider = new QSFragmentModule_ProvideQSPanelFactory(imageTileSet_Factory, 4);
        Provider<SensorManager> provider2 = DoubleCheck.provider(new TvPipModule_ProvideTvPipBoundsStateFactory(this.contextProvider, 1));
        this.providesSensorManagerProvider = provider2;
        this.hingeAngleProvider = new UnfoldSharedModule_HingeAngleProviderFactory(unfoldSharedModule2, (Provider) this.provideUnfoldTransitionConfigProvider, (Provider) provider2);
        Provider<DumpManager> provider3 = DoubleCheck.provider(DumpManager_Factory.InstanceHolder.INSTANCE);
        this.dumpManagerProvider = provider3;
        Provider<ScreenLifecycle> provider4 = DoubleCheck.provider(new ScreenLifecycle_Factory(provider3, 0));
        this.screenLifecycleProvider = provider4;
        Provider<LifecycleScreenStatusProvider> provider5 = DoubleCheck.provider(new SecureSettingsImpl_Factory(provider4, 1));
        this.lifecycleScreenStatusProvider = provider5;
        this.screenStatusProvider = new PowerNotificationWarnings_Factory(unfoldTransitionModule2, provider5);
        this.provideDeviceStateManagerProvider = DoubleCheck.provider(new TimeoutManager_Factory(this.contextProvider, 1));
        Provider<Executor> provider6 = DoubleCheck.provider(new FrameworkServicesModule_ProvideOptionalVibratorFactory(this.contextProvider, 3));
        this.provideMainExecutorProvider = provider6;
        GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory = GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE;
        WMShellBaseModule_ProvideRecentTasksFactory wMShellBaseModule_ProvideRecentTasksFactory = new WMShellBaseModule_ProvideRecentTasksFactory(globalConcurrencyModule_ProvideMainLooperFactory, 4);
        this.provideMainHandlerProvider = wMShellBaseModule_ProvideRecentTasksFactory;
        DeviceFoldStateProvider_Factory create = DeviceFoldStateProvider_Factory.create(this.contextProvider, this.hingeAngleProvider, this.screenStatusProvider, this.provideDeviceStateManagerProvider, provider6, wMShellBaseModule_ProvideRecentTasksFactory);
        this.deviceFoldStateProvider = create;
        Provider<FoldStateProvider> provider7 = DoubleCheck.provider(new VibratorHelper_Factory(unfoldSharedModule2, create));
        this.provideFoldStateProvider = provider7;
        Provider<Optional<UnfoldTransitionProgressProvider>> provider8 = DoubleCheck.provider(new UnfoldSharedModule_UnfoldTransitionProgressProviderFactory(unfoldSharedModule2, this.provideUnfoldTransitionConfigProvider, this.factoryProvider, this.aTraceLoggerTransitionProgressListenerProvider, provider7));
        this.unfoldTransitionProgressProvider = provider8;
        this.provideShellProgressProvider = DoubleCheck.provider(new ChargingState_Factory(unfoldTransitionModule2, this.provideUnfoldTransitionConfigProvider, provider8));
        this.providePackageManagerProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.contextProvider, 1));
        this.provideUserManagerProvider = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.contextProvider, 2));
        this.provideMainDelayableExecutorProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(globalConcurrencyModule_ProvideMainLooperFactory, 3));
        this.provideExecutionProvider = DoubleCheck.provider(ExecutionImpl_Factory.InstanceHolder.INSTANCE);
        this.provideActivityTaskManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideActivityTaskManagerFactory.InstanceHolder.INSTANCE);
        this.providesFingerprintManagerProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.contextProvider, 1));
        this.provideFaceManagerProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideFaceManagerFactory(this.contextProvider, 0));
        Provider<Context> provider9 = this.contextProvider;
        this.provideDisplayMetricsProvider = new SystemUIDialogManager_Factory(globalModule, provider9);
        this.providePowerManagerProvider = DoubleCheck.provider(new UsbDebuggingSecondaryUserActivity_Factory(provider9, 1));
        this.provideAlarmManagerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.contextProvider, 1));
        this.provideIDreamManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIDreamManagerFactory.InstanceHolder.INSTANCE);
        this.provideDevicePolicyManagerProvider = DoubleCheck.provider(new DozeAuthRemover_Factory(this.contextProvider, 1));
        this.provideResourcesProvider = new ForegroundServicesDialog_Factory(this.contextProvider, 1);
        this.providesPluginExecutorProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginExecutorFactory.create(ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE));
        this.provideNotificationManagerProvider = DoubleCheck.provider(new MediaFlags_Factory(this.contextProvider, 1));
        this.pluginEnablerImplProvider = DoubleCheck.provider(PluginEnablerImpl_Factory.create(this.contextProvider, this.providePackageManagerProvider));
        PluginsModule_ProvidesPrivilegedPluginsFactory create2 = PluginsModule_ProvidesPrivilegedPluginsFactory.create(this.contextProvider);
        this.providesPrivilegedPluginsProvider = create2;
        Provider<PluginInstance.Factory> provider10 = DoubleCheck.provider(PluginsModule_ProvidesPluginInstanceFactoryFactory.create(create2, PluginsModule_ProvidesPluginDebugFactory.create()));
        this.providesPluginInstanceFactoryProvider = provider10;
        this.providePluginInstanceManagerFactoryProvider = DoubleCheck.provider(PluginsModule_ProvidePluginInstanceManagerFactoryFactory.create(this.contextProvider, this.providePackageManagerProvider, this.provideMainExecutorProvider, this.providesPluginExecutorProvider, this.provideNotificationManagerProvider, this.pluginEnablerImplProvider, this.providesPrivilegedPluginsProvider, provider10));
        this.providesPluginPrefsProvider = PluginsModule_ProvidesPluginPrefsFactory.create(this.contextProvider);
        this.providesPluginManagerProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginManagerFactory.create(this.contextProvider, this.providePluginInstanceManagerFactoryProvider, PluginsModule_ProvidesPluginDebugFactory.create(), GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory.InstanceHolder.INSTANCE, this.pluginEnablerImplProvider, this.providesPluginPrefsProvider, this.providesPrivilegedPluginsProvider));
        this.providePackageManagerWrapperProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePackageManagerWrapperFactory.InstanceHolder.INSTANCE);
        Provider<Optional<NaturalRotationUnfoldProgressProvider>> provider11 = DoubleCheck.provider(new MediaDreamSentinel_Factory(unfoldTransitionModule2, this.contextProvider, this.provideIWindowManagerProvider, this.unfoldTransitionProgressProvider));
        this.provideNaturalRotationProgressProvider = provider11;
        this.provideStatusBarScopedTransitionProvider = DoubleCheck.provider(new NotificationPanelUnfoldAnimationController_Factory(unfoldTransitionModule2, (Provider) provider11));
        this.provideIActivityManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIActivityManagerFactory.InstanceHolder.INSTANCE);
        this.provideAccessibilityManagerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(this.contextProvider, 1));
        this.taskbarDelegateProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.contextProvider, 0));
        this.provideCrossWindowBlurListenersProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory.InstanceHolder.INSTANCE);
        this.provideWallpaperManagerProvider = new DependencyProvider_ProvideHandlerFactory(this.contextProvider, 1);
        this.provideOptionalTelecomManagerProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(this.contextProvider, 1));
        this.provideInputMethodManagerProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(this.contextProvider, 1));
        this.opaEnabledSettingsProvider = C0769xb6bb24d7.m50m(this.contextProvider, 7);
        this.provideMediaSessionManagerProvider = new DreamOverlayStateController_Factory(this.contextProvider, 1);
        this.provideMediaRouter2ManagerProvider = new DozeLogger_Factory(this.contextProvider, 1);
        this.provideLatencyTrackerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.contextProvider, 1));
        this.provideKeyguardManagerProvider = DoubleCheck.provider(new WallpaperController_Factory(this.contextProvider, 2));
        this.provideAudioManagerProvider = DoubleCheck.provider(new PackageManagerAdapter_Factory(this.contextProvider, 1));
        this.provideSensorPrivacyManagerProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.contextProvider, 1));
        this.provideViewConfigurationProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.contextProvider, 2));
        this.provideTrustManagerProvider = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(this.contextProvider, 2));
        this.provideTelephonyManagerProvider = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.contextProvider, 2));
        this.provideVibratorProvider = DoubleCheck.provider(new VrMode_Factory(this.contextProvider, 2));
        this.provideDisplayManagerProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(this.contextProvider, 2));
        this.provideIBatteryStatsProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIBatteryStatsFactory.InstanceHolder.INSTANCE);
        this.provideDisplayIdProvider = new ColorChangeHandler_Factory(this.contextProvider, 2);
        this.provideSubcriptionManagerProvider = C0773x82ed519e.m54m(this.contextProvider, 2);
        this.provideConnectivityManagagerProvider = DoubleCheck.provider(new TypeClassifier_Factory(this.contextProvider, 3));
        this.provideWifiManagerProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.contextProvider, 1));
        this.provideNetworkScoreManagerProvider = DoubleCheck.provider(new QSLogger_Factory(this.contextProvider, 1));
        this.provideShortcutManagerProvider = C0771xb6bb24d9.m52m(this.contextProvider, 1);
        this.provideIAudioServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIAudioServiceFactory.InstanceHolder.INSTANCE);
        this.pluginDependencyProvider = DoubleCheck.provider(PluginDependencyProvider_Factory.create(this.providesPluginManagerProvider));
        this.provideTelecomManagerProvider = C0772x82ed519d.m53m(this.contextProvider, 2);
        this.provideActivityManagerProvider = C0769xb6bb24d7.m50m(this.contextProvider, 1);
        this.provideClipboardManagerProvider = DoubleCheck.provider(new ToastLogger_Factory(this.contextProvider, 1));
        this.provideOverlayManagerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksFactory(this.contextProvider, 1));
        this.provideStatsManagerProvider = DoubleCheck.provider(new ActionClickLogger_Factory(this.contextProvider, 1));
        this.provideSmartspaceManagerProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.contextProvider, 1));
        Provider<Optional<FoldStateLoggingProvider>> provider12 = DoubleCheck.provider(new DarkIconDispatcherImpl_Factory(unfoldTransitionModule2, this.provideUnfoldTransitionConfigProvider, this.provideFoldStateProvider));
        this.providesFoldStateLoggingProvider = provider12;
        this.providesFoldStateLoggerProvider = DoubleCheck.provider(new KeyguardVisibility_Factory(unfoldTransitionModule2, provider12));
        this.provideColorDisplayManagerProvider = DoubleCheck.provider(new NavigationBarOverlayController_Factory(this.contextProvider, 1));
        this.provideIPackageManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIPackageManagerFactory.InstanceHolder.INSTANCE);
        this.providePermissionManagerProvider = DoubleCheck.provider(new ImageExporter_Factory(this.contextProvider, 2));
        this.provideOptionalVibratorProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideOptionalVibratorFactory(this.contextProvider, 0));
        this.qSExpansionPathInterpolatorProvider = DoubleCheck.provider(QSExpansionPathInterpolator_Factory.InstanceHolder.INSTANCE);
    }

    public final Resources mainResources() {
        Resources resources = this.context.getResources();
        Objects.requireNonNull(resources, "Cannot return null from a non-@Nullable @Provides method");
        return resources;
    }

    public DaggerTitanGlobalRootComponent(GlobalModule globalModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context2) {
        this.context = context2;
        initialize(globalModule, unfoldTransitionModule, unfoldSharedModule, context2);
    }

    public final Handler mainHandler() {
        Looper mainLooper = Looper.getMainLooper();
        Objects.requireNonNull(mainLooper, "Cannot return null from a non-@Nullable @Provides method");
        return new Handler(mainLooper);
    }

    public static <T> Provider<Optional<T>> absentJdkOptionalProvider() {
        return ABSENT_JDK_OPTIONAL_PROVIDER;
    }
}
