package com.android.systemui.p008tv;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.INotificationManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.smartspace.SmartspaceManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.NightDisplayListener;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioManager;
import android.media.IAudioService;
import android.media.MediaRouter2Manager;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.permission.PermissionManager;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
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
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.EmergencyButtonController_Factory_Factory;
import com.android.keyguard.KeyguardBiometricLockoutLogger_Factory;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardClockSwitchController;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardDisplayManager_Factory;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardHostViewController_Factory;
import com.android.keyguard.KeyguardInputViewController;
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
import com.android.keyguard.KeyguardSliceView;
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
import com.android.p012wm.shell.RootDisplayAreaOrganizer;
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
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.p012wm.shell.dagger.TvPipModule_ProvidePipSnapAlgorithmFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvidePipTaskOrganizerFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvidePipTransitionStateFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvideTvPipBoundsStateFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvideTvPipNotificationControllerFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvideTvPipTransitionFactory;
import com.android.p012wm.shell.dagger.TvPipModule_ProvidesTvPipMenuControllerFactory;
import com.android.p012wm.shell.dagger.TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideBubblesFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideDisplayControllerFactory;
import com.android.p012wm.shell.dagger.WMShellBaseModule_ProvideDragAndDropControllerFactory;
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
import com.android.p012wm.shell.dagger.WMShellModule_ProvideUnfoldBackgroundControllerFactory;
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
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipTransitionState;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipNotificationController;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.p012wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.p012wm.shell.transition.Transitions;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
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
import com.android.systemui.LatencyTester_Factory;
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
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistLogger_Factory;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistManager_Factory;
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
import com.android.systemui.clipboardoverlay.ClipboardListener_Factory;
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
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dock.DockManagerImpl;
import com.android.systemui.dock.DockManagerImpl_Factory;
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
import com.android.systemui.dreams.DreamOverlayRegistrant_Factory;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayService_Factory;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController_Factory;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController_Factory;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationCollectionLiveData;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.dreams.complication.ComplicationViewModelProvider;
import com.android.systemui.dreams.complication.ComplicationViewModelTransformer;
import com.android.systemui.dreams.complication.dagger.C0792x8a1d9ad2;
import com.android.systemui.dreams.complication.dagger.C0793x7dca8e24;
import com.android.systemui.dreams.complication.dagger.C0794x1653c7a9;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewComponent;
import com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesMaxBurnInOffsetFactory;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule;
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
import com.android.systemui.media.dagger.MediaModule_ProvidesKeyguardMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesMediaTttChipControllerSenderFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQSMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQuickQSMediaHostFactory;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver_Factory;
import com.android.systemui.media.dream.MediaDreamSentinel_Factory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.media.systemsounds.HomeSoundEffectController;
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
import com.android.systemui.navigationbar.NavigationBarOverlayController;
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
import com.android.systemui.p006qs.FooterActionsView;
import com.android.systemui.p006qs.HeaderPrivacyIconsController;
import com.android.systemui.p006qs.HeaderPrivacyIconsController_Factory;
import com.android.systemui.p006qs.QSAnimator;
import com.android.systemui.p006qs.QSAnimator_Factory;
import com.android.systemui.p006qs.QSContainerImpl;
import com.android.systemui.p006qs.QSContainerImplController;
import com.android.systemui.p006qs.QSContainerImplController_Factory;
import com.android.systemui.p006qs.QSExpansionPathInterpolator;
import com.android.systemui.p006qs.QSExpansionPathInterpolator_Factory;
import com.android.systemui.p006qs.QSFgsManagerFooter;
import com.android.systemui.p006qs.QSFgsManagerFooter_Factory;
import com.android.systemui.p006qs.QSFooter;
import com.android.systemui.p006qs.QSFooterView;
import com.android.systemui.p006qs.QSFooterViewController;
import com.android.systemui.p006qs.QSFooterViewController_Factory;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.p006qs.QSFragmentDisableFlagsLogger;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelController;
import com.android.systemui.p006qs.QSPanelController_Factory;
import com.android.systemui.p006qs.QSSecurityFooter_Factory;
import com.android.systemui.p006qs.QSSquishinessController;
import com.android.systemui.p006qs.QSSquishinessController_Factory;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.QSTileHost_Factory;
import com.android.systemui.p006qs.QSTileRevealController_Factory_Factory;
import com.android.systemui.p006qs.QuickQSPanel;
import com.android.systemui.p006qs.QuickQSPanelController;
import com.android.systemui.p006qs.QuickQSPanelController_Factory;
import com.android.systemui.p006qs.QuickStatusBarHeader;
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
import com.android.systemui.p006qs.dagger.QSModule_ProvideAutoTileManagerFactory;
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
import com.android.systemui.p006qs.tileimpl.QSFactoryImpl;
import com.android.systemui.p006qs.tileimpl.QSFactoryImpl_Factory;
import com.android.systemui.p006qs.tiles.AirplaneModeTile;
import com.android.systemui.p006qs.tiles.AirplaneModeTile_Factory;
import com.android.systemui.p006qs.tiles.AlarmTile;
import com.android.systemui.p006qs.tiles.AlarmTile_Factory;
import com.android.systemui.p006qs.tiles.BatterySaverTile;
import com.android.systemui.p006qs.tiles.BatterySaverTile_Factory;
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
import com.android.systemui.p006qs.tiles.RotationLockTile;
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
import com.android.systemui.p008tv.TvGlobalRootComponent;
import com.android.systemui.p008tv.TvSysUIComponent;
import com.android.systemui.p008tv.TvSystemUIModule_ProvideAllowNotificationLongPressFactory;
import com.android.systemui.p008tv.TvSystemUIModule_ProvideLeakReportEmailFactory;
import com.android.systemui.p008tv.TvWMComponent;
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
import com.android.systemui.power.EnhancedEstimatesImpl;
import com.android.systemui.power.EnhancedEstimatesImpl_Factory;
import com.android.systemui.power.PowerNotificationWarnings;
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
import com.android.systemui.privacy.television.TvOngoingPrivacyChip;
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
import com.android.systemui.settings.brightness.BrightnessController;
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
import com.android.systemui.shared.system.TaskStackChangeListeners;
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
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardIndicationController_Factory;
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
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger;
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
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.SharedCoordinatorLogger;
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
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController_Factory;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineViewController;
import com.android.systemui.statusbar.notification.row.ExpandableViewController;
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
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper_Builder_Factory;
import com.android.systemui.statusbar.notification.stack.StackStateLogger;
import com.android.systemui.statusbar.p007tv.TvStatusBar;
import com.android.systemui.statusbar.p007tv.TvStatusBar_Factory;
import com.android.systemui.statusbar.p007tv.VpnStatusObserver;
import com.android.systemui.statusbar.p007tv.VpnStatusObserver_Factory;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationHandler;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationPanel;
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
import com.android.systemui.statusbar.phone.NotificationTapHelper;
import com.android.systemui.statusbar.phone.NotificationTapHelper_Factory_Factory;
import com.android.systemui.statusbar.phone.NotificationsQSContainerController;
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
import com.android.systemui.statusbar.phone.dagger.StatusBarPhoneModule_ProvideStatusBarFactory;
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
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherControllerImpl;
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
import com.android.systemui.statusbar.policy.VariableDateViewController;
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
import com.google.android.systemui.assist.GoogleAssistLogger_Factory;
import com.google.android.systemui.assist.OpaEnabledReceiver_Factory;
import com.google.android.systemui.assist.uihints.AssistantWarmer_Factory;
import com.google.android.systemui.assist.uihints.ColorChangeHandler_Factory;
import com.google.android.systemui.assist.uihints.IconController_Factory;
import com.google.android.systemui.assist.uihints.TimeoutManager_Factory;
import com.google.android.systemui.assist.uihints.TouchInsideHandler_Factory;
import com.google.android.systemui.columbus.ColumbusServiceWrapper_Factory;
import com.google.android.systemui.columbus.ColumbusStructuredDataManager_Factory;
import com.google.android.systemui.columbus.actions.DismissTimer_Factory;
import com.google.android.systemui.columbus.actions.LaunchOverview_Factory;
import com.google.android.systemui.columbus.gates.ChargingState_Factory;
import com.google.android.systemui.columbus.gates.KeyguardVisibility_Factory;
import com.google.android.systemui.columbus.gates.PowerSaveState_Factory;
import com.google.android.systemui.columbus.gates.SetupWizard_Factory;
import com.google.android.systemui.columbus.gates.SystemKeyPress_Factory;
import com.google.android.systemui.columbus.gates.VrMode_Factory;
import com.google.android.systemui.columbus.sensors.GestureSensorImpl_Factory;
import com.google.android.systemui.elmyra.actions.UnpinNotifications_Factory;
import com.google.android.systemui.elmyra.feedback.OpaHomeButton_Factory;
import com.google.android.systemui.elmyra.feedback.OpaLockscreen_Factory;
import com.google.android.systemui.gamedashboard.ScreenRecordController_Factory;
import com.google.android.systemui.p016qs.tiles.OverlayToggleTile_Factory;
import com.google.android.systemui.power.EnhancedEstimatesGoogleImpl_Factory;
import com.google.android.systemui.smartspace.KeyguardSmartspaceController_Factory;
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

/* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent */
public final class DaggerTvGlobalRootComponent implements TvGlobalRootComponent {
    public static final Provider ABSENT_JDK_OPTIONAL_PROVIDER = InstanceFactory.create(Optional.empty());
    public Provider<ATraceLoggerTransitionProgressListener> aTraceLoggerTransitionProgressListenerProvider;
    public final Context context;
    public Provider<Context> contextProvider;
    public Provider<DeviceFoldStateProvider> deviceFoldStateProvider;
    public Provider<DumpManager> dumpManagerProvider;
    public Provider<ScaleAwareTransitionProgressProvider.Factory> factoryProvider;
    public Provider<HingeAngleProvider> hingeAngleProvider;
    public Provider<LifecycleScreenStatusProvider> lifecycleScreenStatusProvider;
    public Provider<PluginDependencyProvider> pluginDependencyProvider;
    public Provider<PluginEnablerImpl> pluginEnablerImplProvider;
    public Provider<AccessibilityManager> provideAccessibilityManagerProvider;
    public Provider<ActivityManager> provideActivityManagerProvider;
    public Provider<ActivityTaskManager> provideActivityTaskManagerProvider;
    public Provider<AlarmManager> provideAlarmManagerProvider;
    public Provider<AudioManager> provideAudioManagerProvider;
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

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$Builder */
    public static final class Builder implements TvGlobalRootComponent.Builder {
        public Context context;

        public Builder() {
        }

        public final GlobalRootComponent.Builder context(Context context2) {
            Objects.requireNonNull(context2);
            this.context = context2;
            return this;
        }

        public Builder(int i) {
        }

        public final TvGlobalRootComponent build() {
            R$color.checkBuilderRequirement(this.context, Context.class);
            return new DaggerTvGlobalRootComponent(new GlobalModule(), new UnfoldTransitionModule(), new UnfoldSharedModule(), this.context);
        }

        /* renamed from: context  reason: collision with other method in class */
        public final Builder m258context(Context context2) {
            Objects.requireNonNull(context2);
            this.context = context2;
            return this;
        }
    }

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$PresentJdkOptionalInstanceProvider */
    public static final class PresentJdkOptionalInstanceProvider<T> implements Provider<Optional<T>> {
        public final Provider<T> delegate;

        /* renamed from: of */
        public static <T> Provider<Optional<T>> m76of(Provider<T> provider) {
            return new PresentJdkOptionalInstanceProvider(provider);
        }

        public final Optional<T> get() {
            return Optional.of(this.delegate.get());
        }

        public PresentJdkOptionalInstanceProvider(Provider<T> provider) {
            Objects.requireNonNull(provider);
            this.delegate = provider;
        }
    }

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentBuilder */
    public final class TvSysUIComponentBuilder implements TvSysUIComponent.Builder {
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

        public TvSysUIComponentBuilder() {
        }

        public final TvSysUIComponent build() {
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
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = DaggerTvGlobalRootComponent.this;
            DependencyProvider dependencyProvider = new DependencyProvider();
            NightDisplayListenerModule nightDisplayListenerModule = new NightDisplayListenerModule();
            SysUIUnfoldModule sysUIUnfoldModule = new SysUIUnfoldModule();
            Optional<Pip> optional = this.setPip;
            Optional<LegacySplitScreen> optional2 = this.setLegacySplitScreen;
            Optional<SplitScreen> optional3 = this.setSplitScreen;
            Optional<Object> optional4 = this.setAppPairs;
            Optional<OneHanded> optional5 = this.setOneHanded;
            Optional<Bubbles> optional6 = this.setBubbles;
            Optional<TaskViewFactory> optional7 = this.setTaskViewFactory;
            Optional<HideDisplayCutout> optional8 = this.setHideDisplayCutout;
            Optional<ShellCommandHandler> optional9 = this.setShellCommandHandler;
            return new TvSysUIComponentImpl(daggerTvGlobalRootComponent, dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, this.setTransitions, this.setStartingSurface, this.setDisplayAreaHelper, this.setTaskSurfaceHelper, this.setRecentTasks, this.setCompatUI, this.setDragAndDrop, this.setBackAnimation);
        }

        /* renamed from: setAppPairs  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m259setAppPairs(Optional<Object> optional) {
            Objects.requireNonNull(optional);
            this.setAppPairs = optional;
            return this;
        }

        /* renamed from: setBackAnimation  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m260setBackAnimation(Optional<BackAnimation> optional) {
            Objects.requireNonNull(optional);
            this.setBackAnimation = optional;
            return this;
        }

        /* renamed from: setBubbles  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m261setBubbles(Optional<Bubbles> optional) {
            Objects.requireNonNull(optional);
            this.setBubbles = optional;
            return this;
        }

        /* renamed from: setCompatUI  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m262setCompatUI(Optional<CompatUI> optional) {
            Objects.requireNonNull(optional);
            this.setCompatUI = optional;
            return this;
        }

        /* renamed from: setDisplayAreaHelper  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m263setDisplayAreaHelper(Optional<DisplayAreaHelper> optional) {
            Objects.requireNonNull(optional);
            this.setDisplayAreaHelper = optional;
            return this;
        }

        /* renamed from: setDragAndDrop  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m264setDragAndDrop(Optional<DragAndDrop> optional) {
            Objects.requireNonNull(optional);
            this.setDragAndDrop = optional;
            return this;
        }

        /* renamed from: setHideDisplayCutout  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m265setHideDisplayCutout(Optional<HideDisplayCutout> optional) {
            Objects.requireNonNull(optional);
            this.setHideDisplayCutout = optional;
            return this;
        }

        /* renamed from: setLegacySplitScreen  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m266setLegacySplitScreen(Optional<LegacySplitScreen> optional) {
            Objects.requireNonNull(optional);
            this.setLegacySplitScreen = optional;
            return this;
        }

        /* renamed from: setOneHanded  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m267setOneHanded(Optional<OneHanded> optional) {
            Objects.requireNonNull(optional);
            this.setOneHanded = optional;
            return this;
        }

        /* renamed from: setPip  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m268setPip(Optional<Pip> optional) {
            Objects.requireNonNull(optional);
            this.setPip = optional;
            return this;
        }

        /* renamed from: setRecentTasks  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m269setRecentTasks(Optional<RecentTasks> optional) {
            Objects.requireNonNull(optional);
            this.setRecentTasks = optional;
            return this;
        }

        /* renamed from: setShellCommandHandler  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m270setShellCommandHandler(Optional<ShellCommandHandler> optional) {
            Objects.requireNonNull(optional);
            this.setShellCommandHandler = optional;
            return this;
        }

        /* renamed from: setSplitScreen  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m271setSplitScreen(Optional<SplitScreen> optional) {
            Objects.requireNonNull(optional);
            this.setSplitScreen = optional;
            return this;
        }

        /* renamed from: setStartingSurface  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m272setStartingSurface(Optional<StartingSurface> optional) {
            Objects.requireNonNull(optional);
            this.setStartingSurface = optional;
            return this;
        }

        /* renamed from: setTaskSurfaceHelper  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m273setTaskSurfaceHelper(Optional<TaskSurfaceHelper> optional) {
            Objects.requireNonNull(optional);
            this.setTaskSurfaceHelper = optional;
            return this;
        }

        /* renamed from: setTaskViewFactory  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m274setTaskViewFactory(Optional<TaskViewFactory> optional) {
            Objects.requireNonNull(optional);
            this.setTaskViewFactory = optional;
            return this;
        }

        /* renamed from: setTransitions  reason: collision with other method in class */
        public final TvSysUIComponentBuilder m275setTransitions(ShellTransitions shellTransitions) {
            Objects.requireNonNull(shellTransitions);
            this.setTransitions = shellTransitions;
            return this;
        }
    }

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl */
    public final class TvSysUIComponentImpl implements TvSysUIComponent {
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
        public Provider<AmbientState> ambientStateProvider;
        public Provider<AnimatedImageNotificationManager> animatedImageNotificationManagerProvider;
        public Provider<AppOpsControllerImpl> appOpsControllerImplProvider;
        public Provider<AssistLogger> assistLoggerProvider;
        public Provider<AssistManager> assistManagerProvider;
        public Provider<AssistantFeedbackController> assistantFeedbackControllerProvider;
        public Provider<AsyncSensorManager> asyncSensorManagerProvider;
        public Provider<AuthController> authControllerProvider;
        public Provider<BatterySaverTile> batterySaverTileProvider;
        public Provider<BatteryStateNotifier> batteryStateNotifierProvider;
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
        public Provider<ThresholdSensorImpl.Builder> builderProvider;
        public Provider<FlingAnimationUtils.Builder> builderProvider10;
        public Provider<NotificationClicker.Builder> builderProvider2;
        public Provider<DelayedWakeLock.Builder> builderProvider3;
        public Provider<StatusBarNotificationActivityStarter.Builder> builderProvider4;
        public Provider<WakeLock.Builder> builderProvider5;
        public Provider<CustomTile.Builder> builderProvider6;
        public Provider<NightDisplayListenerModule.Builder> builderProvider7;
        public Provider<AutoAddTracker.Builder> builderProvider8;
        public Provider<TileServiceRequestController.Builder> builderProvider9;
        public Provider<CallbackHandler> callbackHandlerProvider;
        public Provider<CameraToggleTile> cameraToggleTileProvider;
        public Provider<CarrierConfigTracker> carrierConfigTrackerProvider;
        public Provider<CastControllerImpl> castControllerImplProvider;
        public Provider<CastTile> castTileProvider;
        public Provider<CellularTile> cellularTileProvider;
        public Provider<ChannelEditorDialogController> channelEditorDialogControllerProvider;
        public Provider<ClockManager> clockManagerProvider;
        public Provider<ColorCorrectionTile> colorCorrectionTileProvider;
        public Provider<ColorInversionTile> colorInversionTileProvider;
        public Provider<CommandRegistry> commandRegistryProvider;
        public Provider<CommunalSettingCondition> communalSettingConditionProvider;
        public Provider<CommunalSourceMonitor> communalSourceMonitorProvider;
        public Provider<CommunalStateController> communalStateControllerProvider;
        public Provider<CommunalViewComponent.Factory> communalViewComponentFactoryProvider;
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
        public Provider<DataSaverTile> dataSaverTileProvider;
        public Provider<DateFormatUtil> dateFormatUtilProvider;
        public Provider<DebugModeFilterProvider> debugModeFilterProvider;
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
        public Provider distanceClassifierProvider;
        public Provider<DndTile> dndTileProvider;
        public Provider<DockManagerImpl> dockManagerImplProvider;
        public Provider<DoubleTapClassifier> doubleTapClassifierProvider;
        public Provider<DozeComponent.Builder> dozeComponentBuilderProvider;
        public Provider<DozeLog> dozeLogProvider;
        public Provider<DozeLogger> dozeLoggerProvider;
        public Provider<DozeParameters> dozeParametersProvider;
        public Provider<DozeScrimController> dozeScrimControllerProvider;
        public Provider<DozeServiceHost> dozeServiceHostProvider;
        public Provider<DozeService> dozeServiceProvider;
        public Provider<DreamOverlayComponent.Factory> dreamOverlayComponentFactoryProvider;
        public Provider<DreamOverlayService> dreamOverlayServiceProvider;
        public Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
        public Provider<DumpHandler> dumpHandlerProvider;
        public Provider<DynamicChildBindController> dynamicChildBindControllerProvider;
        public Provider<Optional<LowLightClockController>> dynamicOverrideOptionalOfLowLightClockControllerProvider;
        public Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
        public Provider<EnhancedEstimatesImpl> enhancedEstimatesImplProvider;
        public Provider<ExpandableNotificationRowComponent.Builder> expandableNotificationRowComponentBuilderProvider;
        public Provider<NotificationLogger.ExpansionStateLogger> expansionStateLoggerProvider;
        public Provider<ExtensionControllerImpl> extensionControllerImplProvider;
        public Provider<LightBarController.Factory> factoryProvider;
        public Provider<AutoHideController.Factory> factoryProvider2;
        public Provider<NavigationBar.Factory> factoryProvider3;
        public Provider<BrightnessSliderController.Factory> factoryProvider4;
        public Provider<KeyguardBouncer.Factory> factoryProvider5;
        public Provider<KeyguardMessageAreaController.Factory> factoryProvider6;
        public Provider<EdgeBackGestureHandler.Factory> factoryProvider7;
        public Provider<TileLifecycleManager.Factory> factoryProvider8;
        public Provider<StatusBarIconController.TintedIconManager.Factory> factoryProvider9;
        public Provider falsingCollectorImplProvider;
        public Provider<FalsingDataProvider> falsingDataProvider;
        public Provider<FalsingManagerProxy> falsingManagerProxyProvider;
        public Provider<FeatureFlagsRelease> featureFlagsReleaseProvider;
        public Provider<FgsManagerController> fgsManagerControllerProvider;
        public Provider<Files> filesProvider;
        public Provider<FlashlightControllerImpl> flashlightControllerImplProvider;
        public Provider<FlashlightTile> flashlightTileProvider;
        public Provider<ForegroundServiceController> foregroundServiceControllerProvider;
        public Provider<ForegroundServiceDismissalFeatureController> foregroundServiceDismissalFeatureControllerProvider;
        public Provider<ForegroundServiceNotificationListener> foregroundServiceNotificationListenerProvider;
        public Provider<ForegroundServiceSectionController> foregroundServiceSectionControllerProvider;
        public Provider<ForegroundServicesDialog> foregroundServicesDialogProvider;
        public Provider<FragmentService.FragmentCreator.Factory> fragmentCreatorFactoryProvider;
        public Provider<FragmentService> fragmentServiceProvider;
        public Provider<GarbageMonitor> garbageMonitorProvider;
        public Provider<GlobalActionsComponent> globalActionsComponentProvider;
        public Provider<GlobalActionsDialogLite> globalActionsDialogLiteProvider;
        public Provider<GlobalActionsImpl> globalActionsImplProvider;
        public Provider globalSettingsImplProvider;
        public Provider<GroupCoalescerLogger> groupCoalescerLoggerProvider;
        public Provider<GroupCoalescer> groupCoalescerProvider;
        public Provider<HdmiCecSetMenuLanguageActivity> hdmiCecSetMenuLanguageActivityProvider;
        public Provider<HdmiCecSetMenuLanguageHelper> hdmiCecSetMenuLanguageHelperProvider;
        public Provider<HeadsUpController> headsUpControllerProvider;
        public Provider<HeadsUpManagerLogger> headsUpManagerLoggerProvider;
        public Provider<HeadsUpViewBinderLogger> headsUpViewBinderLoggerProvider;
        public Provider<HeadsUpViewBinder> headsUpViewBinderProvider;
        public Provider<HighPriorityProvider> highPriorityProvider;
        public Provider<HistoryTracker> historyTrackerProvider;
        public Provider<HomeSoundEffectController> homeSoundEffectControllerProvider;
        public Provider<HotspotControllerImpl> hotspotControllerImplProvider;
        public Provider<HotspotTile> hotspotTileProvider;
        public Provider<IconBuilder> iconBuilderProvider;
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
        public Provider<KeyboardUI> keyboardUIProvider;
        public Provider<KeyguardBouncerComponent.Factory> keyguardBouncerComponentFactoryProvider;
        public Provider<KeyguardBypassController> keyguardBypassControllerProvider;
        public Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
        public Provider<KeyguardDisplayManager> keyguardDisplayManagerProvider;
        public Provider<KeyguardEnvironmentImpl> keyguardEnvironmentImplProvider;
        public Provider<KeyguardIndicationController> keyguardIndicationControllerProvider;
        public Provider<KeyguardLifecyclesDispatcher> keyguardLifecyclesDispatcherProvider;
        public Provider<KeyguardMediaController> keyguardMediaControllerProvider;
        public Provider<KeyguardQsUserSwitchComponent.Factory> keyguardQsUserSwitchComponentFactoryProvider;
        public Provider<KeyguardSecurityModel> keyguardSecurityModelProvider;
        public Provider<KeyguardService> keyguardServiceProvider;
        public Provider<KeyguardStateControllerImpl> keyguardStateControllerImplProvider;
        public Provider<KeyguardStatusBarViewComponent.Factory> keyguardStatusBarViewComponentFactoryProvider;
        public Provider<KeyguardStatusViewComponent.Factory> keyguardStatusViewComponentFactoryProvider;
        public Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
        public Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
        public Provider<KeyguardUserSwitcherComponent.Factory> keyguardUserSwitcherComponentFactoryProvider;
        public Provider<LSShadeTransitionLogger> lSShadeTransitionLoggerProvider;
        public Provider<LaunchConversationActivity> launchConversationActivityProvider;
        public Provider<LeakReporter> leakReporterProvider;
        public Provider<LegacyNotificationPresenterExtensions> legacyNotificationPresenterExtensionsProvider;
        public Provider<LightBarController> lightBarControllerProvider;
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
        public Provider<LowPriorityInflationHelper> lowPriorityInflationHelperProvider;
        public Provider<ManagedProfileControllerImpl> managedProfileControllerImplProvider;
        public Provider<Map<Class<?>, Provider<Activity>>> mapOfClassOfAndProviderOfActivityProvider;
        public Provider<Map<Class<?>, Provider<BroadcastReceiver>>> mapOfClassOfAndProviderOfBroadcastReceiverProvider;
        public Provider<Map<Class<?>, Provider<CoreStartable>>> mapOfClassOfAndProviderOfCoreStartableProvider;
        public Provider<Map<Class<?>, Provider<RecentsImplementation>>> mapOfClassOfAndProviderOfRecentsImplementationProvider;
        public Provider<Map<Class<?>, Provider<Service>>> mapOfClassOfAndProviderOfServiceProvider;
        public Provider<MediaArtworkProcessor> mediaArtworkProcessorProvider;
        public Provider<MediaBrowserFactory> mediaBrowserFactoryProvider;
        public Provider<MediaCarouselController> mediaCarouselControllerProvider;
        public Provider<MediaContainerController> mediaContainerControllerProvider;
        public Provider<MediaControlPanel> mediaControlPanelProvider;
        public Provider<MediaControllerFactory> mediaControllerFactoryProvider;
        public Provider<MediaDataFilter> mediaDataFilterProvider;
        public Provider<MediaDataManager> mediaDataManagerProvider;
        public Provider<MediaDeviceManager> mediaDeviceManagerProvider;
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
        public Provider<MediaTimeoutListener> mediaTimeoutListenerProvider;
        public Provider<MediaTttChipControllerReceiver> mediaTttChipControllerReceiverProvider;
        public Provider<MediaTttChipControllerSender> mediaTttChipControllerSenderProvider;
        public Provider<MediaTttCommandLineHelper> mediaTttCommandLineHelperProvider;
        public Provider<MediaTttFlags> mediaTttFlagsProvider;
        public Provider<MediaViewController> mediaViewControllerProvider;
        public Provider<GarbageMonitor.MemoryTile> memoryTileProvider;
        public Provider<MicrophoneToggleTile> microphoneToggleTileProvider;
        public Provider<MonitorComponent.Factory> monitorComponentFactoryProvider;
        public Provider<Set<Condition>> namedSetOfConditionProvider;
        public Provider<Set<FalsingClassifier>> namedSetOfFalsingClassifierProvider;
        public Provider<NavBarHelper> navBarHelperProvider;
        public Provider<NavigationBarController> navigationBarControllerProvider;
        public Provider<NavigationBarOverlayController> navigationBarOverlayControllerProvider;
        public Provider<NavigationModeController> navigationModeControllerProvider;
        public Provider<NearbyMediaDevicesManager> nearbyMediaDevicesManagerProvider;
        public Provider<NetworkControllerImpl> networkControllerImplProvider;
        public Provider<KeyguardViewMediator> newKeyguardViewMediatorProvider;
        public Provider<NextAlarmControllerImpl> nextAlarmControllerImplProvider;
        public Provider<NfcTile> nfcTileProvider;
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
        public Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
        public Provider<NotificationsControllerImpl> notificationsControllerImplProvider;
        public Provider<NotificationsControllerStub> notificationsControllerStubProvider;
        public Provider<OneHandedModeTile> oneHandedModeTileProvider;
        public Provider<OngoingCallFlags> ongoingCallFlagsProvider;
        public Provider<OngoingCallLogger> ongoingCallLoggerProvider;
        public Provider<Optional<BcSmartspaceDataPlugin>> optionalOfBcSmartspaceDataPluginProvider;
        public Provider<Optional<ControlsFavoritePersistenceWrapper>> optionalOfControlsFavoritePersistenceWrapperProvider;
        public Provider<Optional<ControlsTileResourceConfiguration>> optionalOfControlsTileResourceConfigurationProvider;
        public Provider<Optional<Recents>> optionalOfRecentsProvider;
        public Provider<Optional<StatusBar>> optionalOfStatusBarProvider;
        public Provider<Optional<UdfpsHbmProvider>> optionalOfUdfpsHbmProvider;
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
        public Provider<PowerNotificationWarnings> powerNotificationWarningsProvider;
        public Provider<PowerUI> powerUIProvider;
        public Provider<PrivacyDialogController> privacyDialogControllerProvider;
        public Provider<PrivacyDotViewController> privacyDotViewControllerProvider;
        public Provider<PrivacyItemController> privacyItemControllerProvider;
        public Provider<PrivacyLogger> privacyLoggerProvider;
        public Provider<ProtoTracer> protoTracerProvider;
        public Provider<AccessPointControllerImpl> provideAccessPointControllerImplProvider;
        public Provider<AccessibilityFloatingMenuController> provideAccessibilityFloatingMenuControllerProvider;
        public Provider<ActivityLaunchAnimator> provideActivityLaunchAnimatorProvider;
        public Provider<ActivityManagerWrapper> provideActivityManagerWrapperProvider;
        public Provider<ActivityStarter> provideActivityStarterProvider;
        public Provider<Boolean> provideAllowNotificationLongPressProvider;
        public Provider<AlwaysOnDisplayPolicy> provideAlwaysOnDisplayPolicyProvider;
        public Provider<AmbientDisplayConfiguration> provideAmbientDisplayConfigurationProvider;
        public Provider<AssistUtils> provideAssistUtilsProvider;
        public Provider<AutoHideController> provideAutoHideControllerProvider;
        public Provider<DeviceStateRotationLockSettingsManager> provideAutoRotateSettingsManagerProvider;
        public Provider<AutoTileManager> provideAutoTileManagerProvider;
        public Provider<DelayableExecutor> provideBackgroundDelayableExecutorProvider;
        public Provider<Executor> provideBackgroundExecutorProvider;
        public Provider<RepeatableExecutor> provideBackgroundRepeatableExecutorProvider;
        public Provider<BatteryController> provideBatteryControllerProvider;
        public Provider<Handler> provideBgHandlerProvider;
        public Provider<Looper> provideBgLooperProvider;
        public Provider<LogBuffer> provideBroadcastDispatcherLogBufferProvider;
        public Provider<Optional<BubblesManager>> provideBubblesManagerProvider;
        public Provider provideClockInfoListProvider;
        public Provider<LogBuffer> provideCollapsedSbFragmentLogBufferProvider;
        public Provider<CommandQueue> provideCommandQueueProvider;
        public Provider<CommonNotifCollection> provideCommonNotifCollectionProvider;
        public Provider<Set<Condition>> provideCommunalConditionsProvider;
        public Provider<Monitor> provideCommunalSourceMonitorProvider;
        public Provider<ConfigurationController> provideConfigurationControllerProvider;
        public Provider<DataSaverController> provideDataSaverControllerProvider;
        public Provider<DelayableExecutor> provideDelayableExecutorProvider;
        public Provider<DemoModeController> provideDemoModeControllerProvider;
        public Provider<DevicePolicyManagerWrapper> provideDevicePolicyManagerWrapperProvider;
        public Provider<DialogLaunchAnimator> provideDialogLaunchAnimatorProvider;
        public Provider<LogBuffer> provideDozeLogBufferProvider;
        public Provider<Executor> provideExecutorProvider;
        public Provider<GroupExpansionManager> provideGroupExpansionManagerProvider;
        public Provider<GroupMembershipManager> provideGroupMembershipManagerProvider;
        public Provider<Handler> provideHandlerProvider;
        public Provider<HeadsUpManagerPhone> provideHeadsUpManagerPhoneProvider;
        public Provider<INotificationManager> provideINotificationManagerProvider;
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
        public Provider<NotificationsController> provideNotificationsControllerProvider;
        public Provider<LogBuffer> provideNotificationsLogBufferProvider;
        public Provider<OnUserInteractionCallback> provideOnUserInteractionCallbackProvider;
        public Provider<OngoingCallController> provideOngoingCallControllerProvider;
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
        public Provider<ThresholdSensor> provideSecondaryProximitySensorProvider;
        public Provider<SensorPrivacyController> provideSensorPrivacyControllerProvider;
        public Provider<SharedPreferences> provideSharePreferencesProvider;
        public Provider<SmartReplyController> provideSmartReplyControllerProvider;
        public Provider<StatusBar> provideStatusBarProvider;
        public Provider<LogBuffer> provideSwipeAwayGestureLogBufferProvider;
        public Provider<Optional<SysUIUnfoldComponent>> provideSysUIUnfoldComponentProvider;
        public Provider<SysUiState> provideSysUiStateProvider;
        public Provider<TaskStackChangeListeners> provideTaskStackChangeListenersProvider;
        public Provider<Handler> provideTimeTickHandlerProvider;
        public Provider<LogBuffer> provideToastLogBufferProvider;
        public Provider<TvNotificationHandler> provideTvNotificationHandlerProvider;
        public Provider<Executor> provideUiBackgroundExecutorProvider;
        public Provider<UserTracker> provideUserTrackerProvider;
        public Provider<VisualStabilityManager> provideVisualStabilityManagerProvider;
        public Provider<VolumeDialog> provideVolumeDialogProvider;
        public Provider<LayoutInflater> providerLayoutInflaterProvider;
        public Provider<SectionHeaderController> providesAlertingHeaderControllerProvider;
        public Provider<NodeController> providesAlertingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesAlertingHeaderSubcomponentProvider;
        public Provider<MessageRouter> providesBackgroundMessageRouterProvider;
        public Provider<Set<FalsingClassifier>> providesBrightLineGestureClassifiersProvider;
        public Provider<BroadcastDispatcher> providesBroadcastDispatcherProvider;
        public Provider<Choreographer> providesChoreographerProvider;
        public Provider<Boolean> providesControlsFeatureEnabledProvider;
        public Provider<DeviceProvisionedController> providesDeviceProvisionedControllerProvider;
        public Provider<String[]> providesDeviceStateRotationLockDefaultsProvider;
        public Provider<Float> providesDoubleTapTouchSlopProvider;
        public Provider<SectionHeaderController> providesIncomingHeaderControllerProvider;
        public Provider<NodeController> providesIncomingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesIncomingHeaderSubcomponentProvider;
        public Provider<MediaHost> providesKeyguardMediaHostProvider;
        public Provider<MessageRouter> providesMainMessageRouterProvider;
        public Provider<Optional<MediaMuteAwaitConnectionCli>> providesMediaMuteAwaitConnectionCliProvider;
        public Provider<Optional<MediaTttChipControllerReceiver>> providesMediaTttChipControllerReceiverProvider;
        public Provider<Optional<MediaTttChipControllerSender>> providesMediaTttChipControllerSenderProvider;
        public Provider<Optional<MediaTttCommandLineHelper>> providesMediaTttCommandLineHelperProvider;
        public Provider<ModeSwitchesController> providesModeSwitchesControllerProvider;
        public Provider<Optional<NearbyMediaDevicesManager>> providesNearbyMediaDevicesManagerProvider;
        public Provider<SectionHeaderController> providesPeopleHeaderControllerProvider;
        public Provider<NodeController> providesPeopleHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesPeopleHeaderSubcomponentProvider;
        public Provider<MediaHost> providesQSMediaHostProvider;
        public Provider<MediaHost> providesQuickQSMediaHostProvider;
        public Provider<SectionHeaderController> providesSilentHeaderControllerProvider;
        public Provider<NodeController> providesSilentHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesSilentHeaderSubcomponentProvider;
        public Provider<Float> providesSingleTapTouchSlopProvider;
        public Provider<StatusBarWindowView> providesStatusBarWindowViewProvider;
        public Provider<ViewMediatorCallback> providesViewMediatorCallbackProvider;
        public Provider proximityClassifierProvider;
        public Provider proximitySensorImplProvider;
        public Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
        public Provider<QRCodeScannerController> qRCodeScannerControllerProvider;
        public Provider<QRCodeScannerTile> qRCodeScannerTileProvider;
        public Provider<QSFactoryImpl> qSFactoryImplProvider;
        public Provider<QSLogger> qSLoggerProvider;
        public Provider<QSTileHost> qSTileHostProvider;
        public Provider<QsFrameTranslateImpl> qsFrameTranslateImplProvider;
        public Provider<QuickAccessWalletController> quickAccessWalletControllerProvider;
        public Provider<QuickAccessWalletTile> quickAccessWalletTileProvider;
        public Provider<RecordingController> recordingControllerProvider;
        public Provider<RecordingService> recordingServiceProvider;
        public Provider<ReduceBrightColorsTile> reduceBrightColorsTileProvider;
        public Provider<RemoteInputNotificationRebuilder> remoteInputNotificationRebuilderProvider;
        public Provider<RemoteInputQuickSettingsDisabler> remoteInputQuickSettingsDisablerProvider;
        public Provider<RemoteInputUriController> remoteInputUriControllerProvider;
        public Provider<RenderStageManager> renderStageManagerProvider;
        public Provider<ResumeMediaBrowserFactory> resumeMediaBrowserFactoryProvider;
        public Provider<RingerModeTrackerImpl> ringerModeTrackerImplProvider;
        public Provider<RingtonePlayer> ringtonePlayerProvider;
        public Provider<RotationLockControllerImpl> rotationLockControllerImplProvider;
        public Provider<RotationLockTile> rotationLockTileProvider;
        public Provider<RotationPolicyWrapperImpl> rotationPolicyWrapperImplProvider;
        public Provider<RowContentBindStageLogger> rowContentBindStageLoggerProvider;
        public Provider<RowContentBindStage> rowContentBindStageProvider;
        public Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
        public Provider<ScreenOnCoordinator> screenOnCoordinatorProvider;
        public Provider<ScreenPinningRequest> screenPinningRequestProvider;
        public Provider<ScreenRecordTile> screenRecordTileProvider;
        public Provider<ScreenshotController> screenshotControllerProvider;
        public Provider<ScreenshotNotificationsController> screenshotNotificationsControllerProvider;
        public Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;
        public Provider<ScrimController> scrimControllerProvider;
        public Provider<ScrollCaptureClient> scrollCaptureClientProvider;
        public Provider<ScrollCaptureController> scrollCaptureControllerProvider;
        public Provider<SectionClassifier> sectionClassifierProvider;
        public Provider<SectionHeaderControllerSubcomponent.Builder> sectionHeaderControllerSubcomponentBuilderProvider;
        public Provider<SectionHeaderVisibilityProvider> sectionHeaderVisibilityProvider;
        public Provider secureSettingsImplProvider;
        public Provider<SecurityControllerImpl> securityControllerImplProvider;
        public Provider<SeekBarViewModel> seekBarViewModelProvider;
        public Provider<SensorUseStartedActivity> sensorUseStartedActivityProvider;
        public Provider<SessionTracker> sessionTrackerProvider;
        public Provider<Optional<BackAnimation>> setBackAnimationProvider;
        public Provider<Optional<Bubbles>> setBubblesProvider;
        public Provider<Optional<CompatUI>> setCompatUIProvider;
        public Provider<Optional<DisplayAreaHelper>> setDisplayAreaHelperProvider;
        public Provider<Optional<DragAndDrop>> setDragAndDropProvider;
        public Provider<Optional<HideDisplayCutout>> setHideDisplayCutoutProvider;
        public Provider<Optional<LegacySplitScreen>> setLegacySplitScreenProvider;
        public Provider<Optional<OneHanded>> setOneHandedProvider;
        public Provider<Optional<Pip>> setPipProvider;
        public Provider<Optional<RecentTasks>> setRecentTasksProvider;
        public Provider<Optional<ShellCommandHandler>> setShellCommandHandlerProvider;
        public Provider<Optional<SplitScreen>> setSplitScreenProvider;
        public Provider<Optional<StartingSurface>> setStartingSurfaceProvider;
        public Provider<Optional<TaskViewFactory>> setTaskViewFactoryProvider;
        public Provider<ShellTransitions> setTransitionsProvider;
        public Provider<ShadeControllerImpl> shadeControllerImplProvider;
        public Provider<ShadeEventCoordinatorLogger> shadeEventCoordinatorLoggerProvider;
        public Provider<ShadeEventCoordinator> shadeEventCoordinatorProvider;
        public Provider<ShadeListBuilderLogger> shadeListBuilderLoggerProvider;
        public Provider<ShadeListBuilder> shadeListBuilderProvider;
        public Provider<ShadeViewDifferLogger> shadeViewDifferLoggerProvider;
        public Provider<ShadeViewManagerFactory> shadeViewManagerFactoryProvider;
        public ShadeViewManager_Factory shadeViewManagerProvider;
        public Provider<ShortcutKeyDispatcher> shortcutKeyDispatcherProvider;
        public Provider<SidefpsController> sidefpsControllerProvider;
        public Provider<SingleTapClassifier> singleTapClassifierProvider;
        public Provider<SliceBroadcastRelayHandler> sliceBroadcastRelayHandlerProvider;
        public Provider<SmartActionInflaterImpl> smartActionInflaterImplProvider;
        public Provider<SmartActionsReceiver> smartActionsReceiverProvider;
        public Provider<SmartReplyConstants> smartReplyConstantsProvider;
        public Provider<SmartReplyInflaterImpl> smartReplyInflaterImplProvider;
        public Provider<SmartReplyStateInflaterImpl> smartReplyStateInflaterImplProvider;
        public Provider<StatusBarComponent.Factory> statusBarComponentFactoryProvider;
        public Provider<StatusBarContentInsetsProvider> statusBarContentInsetsProvider;
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
        public Provider<SwipeStatusBarAwayGestureHandler> swipeStatusBarAwayGestureHandlerProvider;
        public Provider<SwipeStatusBarAwayGestureLogger> swipeStatusBarAwayGestureLoggerProvider;
        public Provider<SysUIUnfoldComponent.Factory> sysUIUnfoldComponentFactoryProvider;
        public Provider<SystemActions> systemActionsProvider;
        public Provider<SystemEventChipAnimationController> systemEventChipAnimationControllerProvider;
        public Provider<SystemEventCoordinator> systemEventCoordinatorProvider;
        public Provider<SystemStatusAnimationScheduler> systemStatusAnimationSchedulerProvider;
        public Provider<SystemUIAuxiliaryDumpService> systemUIAuxiliaryDumpServiceProvider;
        public Provider<SystemUIDialogManager> systemUIDialogManagerProvider;
        public Provider<SystemUIService> systemUIServiceProvider;
        public Provider<SysuiColorExtractor> sysuiColorExtractorProvider;
        public Provider<TakeScreenshotService> takeScreenshotServiceProvider;
        public Provider<TargetSdkResolver> targetSdkResolverProvider;
        public Provider<TelephonyListenerManager> telephonyListenerManagerProvider;
        public final /* synthetic */ DaggerTvGlobalRootComponent this$0;
        public C2507TileLifecycleManager_Factory tileLifecycleManagerProvider;
        public Provider<TileServices> tileServicesProvider;
        public Provider<TimeoutHandler> timeoutHandlerProvider;
        public Provider<ToastFactory> toastFactoryProvider;
        public Provider<ToastLogger> toastLoggerProvider;
        public Provider<ToastUI> toastUIProvider;
        public Provider<TunablePadding.TunablePaddingService> tunablePaddingServiceProvider;
        public Provider<TunerActivity> tunerActivityProvider;
        public Provider<TunerServiceImpl> tunerServiceImplProvider;
        public Provider<TvNotificationPanelActivity> tvNotificationPanelActivityProvider;
        public Provider<TvNotificationPanel> tvNotificationPanelProvider;
        public Provider<TvOngoingPrivacyChip> tvOngoingPrivacyChipProvider;
        public Provider<TvStatusBar> tvStatusBarProvider;
        public Provider<TvUnblockSensorActivity> tvUnblockSensorActivityProvider;
        public Provider<TypeClassifier> typeClassifierProvider;
        public Provider<UdfpsController> udfpsControllerProvider;
        public Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
        public Provider<UiModeNightTile> uiModeNightTileProvider;
        public Provider<UiOffloadThread> uiOffloadThreadProvider;
        public Provider<UnfoldLatencyTracker> unfoldLatencyTrackerProvider;
        public Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
        public Provider<UsbDebuggingActivity> usbDebuggingActivityProvider;
        public Provider<UsbDebuggingSecondaryUserActivity> usbDebuggingSecondaryUserActivityProvider;
        public Provider<UserCreator> userCreatorProvider;
        public Provider<UserInfoControllerImpl> userInfoControllerImplProvider;
        public Provider<UserSwitchDialogController> userSwitchDialogControllerProvider;
        public Provider<UserSwitcherActivity> userSwitcherActivityProvider;
        public Provider<UserSwitcherController> userSwitcherControllerProvider;
        public Provider<VibratorHelper> vibratorHelperProvider;
        public Provider<VisualStabilityCoordinator> visualStabilityCoordinatorProvider;
        public Provider<VisualStabilityProvider> visualStabilityProvider;
        public Provider<VolumeDialogComponent> volumeDialogComponentProvider;
        public Provider<VolumeDialogControllerImpl> volumeDialogControllerImplProvider;
        public Provider<VolumeUI> volumeUIProvider;
        public Provider<VpnStatusObserver> vpnStatusObserverProvider;
        public Provider<WMShell> wMShellProvider;
        public Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
        public Provider<WalletActivity> walletActivityProvider;
        public Provider<WalletControllerImpl> walletControllerImplProvider;
        public Provider<WallpaperController> wallpaperControllerProvider;
        public Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> wifiPickerTrackerFactoryProvider;
        public Provider<WifiTile> wifiTileProvider;
        public Provider<WindowMagnification> windowMagnificationProvider;
        public Provider<WiredChargingRippleController> wiredChargingRippleControllerProvider;
        public Provider<WorkLockActivity> workLockActivityProvider;
        public Provider<WorkModeTile> workModeTileProvider;
        public Provider<ZenModeControllerImpl> zenModeControllerImplProvider;
        public Provider zigZagClassifierProvider;

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$CommunalViewComponentFactory */
        public final class CommunalViewComponentFactory implements CommunalViewComponent.Factory {
            public CommunalViewComponentFactory() {
            }

            public final CommunalViewComponent build(CommunalHostView communalHostView) {
                Objects.requireNonNull(communalHostView);
                return new CommunalViewComponentImpl(communalHostView);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$CommunalViewComponentImpl */
        public final class CommunalViewComponentImpl implements CommunalViewComponent {
            public final CommunalHostView view;

            public CommunalViewComponentImpl(CommunalHostView communalHostView) {
                this.view = communalHostView;
            }

            public final CommunalHostViewController getCommunalHostViewController() {
                DozeParameters dozeParameters = TvSysUIComponentImpl.this.dozeParametersProvider.get();
                return CommunalHostViewController_Factory.newInstance(TvSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TvSysUIComponentImpl.this.communalStateControllerProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TvSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TvSysUIComponentImpl.this.screenOffAnimationControllerProvider.get(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), this.view);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$CoordinatorsSubcomponentFactory */
        public final class CoordinatorsSubcomponentFactory implements CoordinatorsSubcomponent.Factory {
            public CoordinatorsSubcomponentFactory() {
            }

            public final CoordinatorsSubcomponent create() {
                return new CoordinatorsSubcomponentImpl();
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$CoordinatorsSubcomponentImpl */
        public final class CoordinatorsSubcomponentImpl implements CoordinatorsSubcomponent {
            public Provider<AppOpsCoordinator> appOpsCoordinatorProvider;
            public Provider<BubbleCoordinator> bubbleCoordinatorProvider;
            public Provider<CommunalCoordinator> communalCoordinatorProvider;
            public Provider<ConversationCoordinator> conversationCoordinatorProvider;
            public Provider<DataStoreCoordinator> dataStoreCoordinatorProvider;
            public Provider<DebugModeCoordinator> debugModeCoordinatorProvider;
            public Provider<DeviceProvisionedCoordinator> deviceProvisionedCoordinatorProvider;
            public Provider<GroupCountCoordinator> groupCountCoordinatorProvider;
            public Provider<GutsCoordinatorLogger> gutsCoordinatorLoggerProvider;
            public Provider<GutsCoordinator> gutsCoordinatorProvider;
            public Provider<HeadsUpCoordinatorLogger> headsUpCoordinatorLoggerProvider;
            public Provider<HeadsUpCoordinator> headsUpCoordinatorProvider;
            public Provider<HideLocallyDismissedNotifsCoordinator> hideLocallyDismissedNotifsCoordinatorProvider;
            public Provider<HideNotifsForOtherUsersCoordinator> hideNotifsForOtherUsersCoordinatorProvider;
            public Provider<KeyguardCoordinator> keyguardCoordinatorProvider;
            public Provider<MediaCoordinator> mediaCoordinatorProvider;
            public Provider<NotifCoordinatorsImpl> notifCoordinatorsImplProvider;
            public Provider<PreparationCoordinatorLogger> preparationCoordinatorLoggerProvider;
            public Provider<PreparationCoordinator> preparationCoordinatorProvider;
            public Provider<RankingCoordinator> rankingCoordinatorProvider;
            public Provider<RemoteInputCoordinator> remoteInputCoordinatorProvider;
            public Provider<RowAppearanceCoordinator> rowAppearanceCoordinatorProvider;
            public Provider sensitiveContentCoordinatorImplProvider;
            public Provider<SharedCoordinatorLogger> sharedCoordinatorLoggerProvider;
            public Provider<SmartspaceDedupingCoordinator> smartspaceDedupingCoordinatorProvider;
            public Provider<StackCoordinator> stackCoordinatorProvider;
            public Provider<ViewConfigCoordinator> viewConfigCoordinatorProvider;

            public CoordinatorsSubcomponentImpl() {
                initialize();
            }

            public final NotifCoordinators getNotifCoordinators() {
                return this.notifCoordinatorsImplProvider.get();
            }

            public final void initialize() {
                this.dataStoreCoordinatorProvider = DoubleCheck.provider(new KeyboardUI_Factory(TvSysUIComponentImpl.this.notifLiveDataStoreImplProvider, 7));
                this.hideLocallyDismissedNotifsCoordinatorProvider = DoubleCheck.provider(HideLocallyDismissedNotifsCoordinator_Factory.InstanceHolder.INSTANCE);
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                WMShellBaseModule_ProvideHideDisplayCutoutFactory wMShellBaseModule_ProvideHideDisplayCutoutFactory = new WMShellBaseModule_ProvideHideDisplayCutoutFactory(tvSysUIComponentImpl.provideNotificationsLogBufferProvider, 3);
                this.sharedCoordinatorLoggerProvider = wMShellBaseModule_ProvideHideDisplayCutoutFactory;
                this.hideNotifsForOtherUsersCoordinatorProvider = DoubleCheck.provider(new ScreenPinningRequest_Factory(tvSysUIComponentImpl.notificationLockscreenUserManagerImplProvider, wMShellBaseModule_ProvideHideDisplayCutoutFactory, 3));
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                this.keyguardCoordinatorProvider = DoubleCheck.provider(ControlsControllerImpl_Factory.create$1(tvSysUIComponentImpl2.this$0.contextProvider, tvSysUIComponentImpl2.provideHandlerProvider, tvSysUIComponentImpl2.keyguardStateControllerImplProvider, tvSysUIComponentImpl2.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl2.providesBroadcastDispatcherProvider, tvSysUIComponentImpl2.statusBarStateControllerImplProvider, tvSysUIComponentImpl2.keyguardUpdateMonitorProvider, tvSysUIComponentImpl2.highPriorityProvider, tvSysUIComponentImpl2.sectionHeaderVisibilityProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                this.rankingCoordinatorProvider = DoubleCheck.provider(RankingCoordinator_Factory.create(tvSysUIComponentImpl3.statusBarStateControllerImplProvider, tvSysUIComponentImpl3.highPriorityProvider, tvSysUIComponentImpl3.sectionClassifierProvider, tvSysUIComponentImpl3.providesAlertingHeaderNodeControllerProvider, tvSysUIComponentImpl3.providesSilentHeaderControllerProvider, tvSysUIComponentImpl3.providesSilentHeaderNodeControllerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                this.appOpsCoordinatorProvider = DoubleCheck.provider(new MediaViewController_Factory(tvSysUIComponentImpl4.foregroundServiceControllerProvider, tvSysUIComponentImpl4.appOpsControllerImplProvider, tvSysUIComponentImpl4.this$0.provideMainDelayableExecutorProvider, 1));
                TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                this.deviceProvisionedCoordinatorProvider = DoubleCheck.provider(new DeviceProvisionedCoordinator_Factory(tvSysUIComponentImpl5.providesDeviceProvisionedControllerProvider, tvSysUIComponentImpl5.this$0.provideIPackageManagerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl6 = TvSysUIComponentImpl.this;
                this.bubbleCoordinatorProvider = DoubleCheck.provider(new RowContentBindStage_Factory(tvSysUIComponentImpl6.provideBubblesManagerProvider, tvSysUIComponentImpl6.setBubblesProvider, tvSysUIComponentImpl6.notifCollectionProvider, 1));
                TvSysUIComponentImpl tvSysUIComponentImpl7 = TvSysUIComponentImpl.this;
                MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(tvSysUIComponentImpl7.provideNotificationHeadsUpLogBufferProvider, 4);
                this.headsUpCoordinatorLoggerProvider = mediaBrowserFactory_Factory;
                this.headsUpCoordinatorProvider = DoubleCheck.provider(HeadsUpCoordinator_Factory.create(mediaBrowserFactory_Factory, tvSysUIComponentImpl7.bindSystemClockProvider, tvSysUIComponentImpl7.provideHeadsUpManagerPhoneProvider, tvSysUIComponentImpl7.headsUpViewBinderProvider, tvSysUIComponentImpl7.notificationInterruptStateProviderImplProvider, tvSysUIComponentImpl7.provideNotificationRemoteInputManagerProvider, tvSysUIComponentImpl7.providesIncomingHeaderNodeControllerProvider, tvSysUIComponentImpl7.this$0.provideMainDelayableExecutorProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl8 = TvSysUIComponentImpl.this;
                RingtonePlayer_Factory ringtonePlayer_Factory = new RingtonePlayer_Factory(tvSysUIComponentImpl8.provideNotificationsLogBufferProvider, 3);
                this.gutsCoordinatorLoggerProvider = ringtonePlayer_Factory;
                this.gutsCoordinatorProvider = DoubleCheck.provider(new OpaHomeButton_Factory(tvSysUIComponentImpl8.provideNotifGutsViewManagerProvider, ringtonePlayer_Factory, tvSysUIComponentImpl8.this$0.dumpManagerProvider, 1));
                TvSysUIComponentImpl tvSysUIComponentImpl9 = TvSysUIComponentImpl.this;
                this.communalCoordinatorProvider = DoubleCheck.provider(new CommunalCoordinator_Factory(tvSysUIComponentImpl9.this$0.provideMainExecutorProvider, tvSysUIComponentImpl9.provideNotificationEntryManagerProvider, tvSysUIComponentImpl9.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl9.communalStateControllerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl10 = TvSysUIComponentImpl.this;
                this.conversationCoordinatorProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(tvSysUIComponentImpl10.peopleNotificationIdentifierImplProvider, tvSysUIComponentImpl10.providesPeopleHeaderNodeControllerProvider, 1));
                this.debugModeCoordinatorProvider = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(TvSysUIComponentImpl.this.debugModeFilterProvider, 4));
                this.groupCountCoordinatorProvider = DoubleCheck.provider(GroupCountCoordinator_Factory.InstanceHolder.INSTANCE);
                this.mediaCoordinatorProvider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(TvSysUIComponentImpl.this.mediaFeatureFlagProvider, 2));
                TvSysUIComponentImpl tvSysUIComponentImpl11 = TvSysUIComponentImpl.this;
                TypeClassifier_Factory typeClassifier_Factory = new TypeClassifier_Factory(tvSysUIComponentImpl11.provideNotificationsLogBufferProvider, 4);
                this.preparationCoordinatorLoggerProvider = typeClassifier_Factory;
                this.preparationCoordinatorProvider = DoubleCheck.provider(PreparationCoordinator_Factory.create(typeClassifier_Factory, tvSysUIComponentImpl11.notifInflaterImplProvider, tvSysUIComponentImpl11.notifInflationErrorManagerProvider, tvSysUIComponentImpl11.notifViewBarnProvider, tvSysUIComponentImpl11.notifUiAdjustmentProvider, tvSysUIComponentImpl11.this$0.provideIStatusBarServiceProvider, tvSysUIComponentImpl11.bindEventManagerImplProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl12 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl12.this$0;
                this.remoteInputCoordinatorProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDragAndDropControllerFactory.create(daggerTvGlobalRootComponent.dumpManagerProvider, tvSysUIComponentImpl12.remoteInputNotificationRebuilderProvider, tvSysUIComponentImpl12.provideNotificationRemoteInputManagerProvider, daggerTvGlobalRootComponent.provideMainHandlerProvider, tvSysUIComponentImpl12.provideSmartReplyControllerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl13 = TvSysUIComponentImpl.this;
                this.rowAppearanceCoordinatorProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(tvSysUIComponentImpl13.this$0.contextProvider, tvSysUIComponentImpl13.assistantFeedbackControllerProvider, tvSysUIComponentImpl13.sectionClassifierProvider, 2));
                this.stackCoordinatorProvider = DoubleCheck.provider(new DozeLogger_Factory(TvSysUIComponentImpl.this.notificationIconAreaControllerProvider, 3));
                TvSysUIComponentImpl tvSysUIComponentImpl14 = TvSysUIComponentImpl.this;
                this.smartspaceDedupingCoordinatorProvider = DoubleCheck.provider(SmartspaceDedupingCoordinator_Factory.create(tvSysUIComponentImpl14.statusBarStateControllerImplProvider, tvSysUIComponentImpl14.lockscreenSmartspaceControllerProvider, tvSysUIComponentImpl14.provideNotificationEntryManagerProvider, tvSysUIComponentImpl14.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl14.notifPipelineProvider, tvSysUIComponentImpl14.this$0.provideMainDelayableExecutorProvider, tvSysUIComponentImpl14.bindSystemClockProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl15 = TvSysUIComponentImpl.this;
                this.viewConfigCoordinatorProvider = DoubleCheck.provider(new ViewConfigCoordinator_Factory(tvSysUIComponentImpl15.provideConfigurationControllerProvider, tvSysUIComponentImpl15.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl15.provideNotificationGutsManagerProvider, tvSysUIComponentImpl15.keyguardUpdateMonitorProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl16 = TvSysUIComponentImpl.this;
                Provider provider = DoubleCheck.provider(MediaDataFilter_Factory.create$1(tvSysUIComponentImpl16.dynamicPrivacyControllerProvider, tvSysUIComponentImpl16.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl16.keyguardUpdateMonitorProvider, tvSysUIComponentImpl16.statusBarStateControllerImplProvider, tvSysUIComponentImpl16.keyguardStateControllerImplProvider));
                Provider provider2 = provider;
                this.sensitiveContentCoordinatorImplProvider = provider;
                TvSysUIComponentImpl tvSysUIComponentImpl17 = TvSysUIComponentImpl.this;
                this.notifCoordinatorsImplProvider = DoubleCheck.provider(NotifCoordinatorsImpl_Factory.create(tvSysUIComponentImpl17.this$0.dumpManagerProvider, tvSysUIComponentImpl17.notifPipelineFlagsProvider, this.dataStoreCoordinatorProvider, this.hideLocallyDismissedNotifsCoordinatorProvider, this.hideNotifsForOtherUsersCoordinatorProvider, this.keyguardCoordinatorProvider, this.rankingCoordinatorProvider, this.appOpsCoordinatorProvider, this.deviceProvisionedCoordinatorProvider, this.bubbleCoordinatorProvider, this.headsUpCoordinatorProvider, this.gutsCoordinatorProvider, this.communalCoordinatorProvider, this.conversationCoordinatorProvider, this.debugModeCoordinatorProvider, this.groupCountCoordinatorProvider, this.mediaCoordinatorProvider, this.preparationCoordinatorProvider, this.remoteInputCoordinatorProvider, this.rowAppearanceCoordinatorProvider, this.stackCoordinatorProvider, tvSysUIComponentImpl17.shadeEventCoordinatorProvider, this.smartspaceDedupingCoordinatorProvider, this.viewConfigCoordinatorProvider, tvSysUIComponentImpl17.visualStabilityCoordinatorProvider, provider2));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DozeComponentFactory */
        public final class DozeComponentFactory implements DozeComponent.Builder {
            public DozeComponentFactory() {
            }

            public final DozeComponent build(DozeMachine.Service service) {
                Objects.requireNonNull(service);
                return new DozeComponentImpl(service);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DozeComponentImpl */
        public final class DozeComponentImpl implements DozeComponent {
            public Provider<DozeAuthRemover> dozeAuthRemoverProvider;
            public Provider<DozeDockHandler> dozeDockHandlerProvider;
            public Provider<DozeFalsingManagerAdapter> dozeFalsingManagerAdapterProvider;
            public Provider<DozeMachine> dozeMachineProvider;
            public Provider<DozeMachine.Service> dozeMachineServiceProvider;
            public Provider<DozePauser> dozePauserProvider;
            public Provider<DozeScreenBrightness> dozeScreenBrightnessProvider;
            public Provider<DozeScreenState> dozeScreenStateProvider;
            public Provider<DozeTriggers> dozeTriggersProvider;
            public Provider<DozeUi> dozeUiProvider;
            public Provider<DozeWallpaperState> dozeWallpaperStateProvider;
            public Provider<Optional<Sensor>[]> providesBrightnessSensorsProvider;
            public Provider<DozeMachine.Part[]> providesDozeMachinePartesProvider;
            public Provider<WakeLock> providesDozeWakeLockProvider;
            public Provider<DozeMachine.Service> providesWrappedServiceProvider;

            public DozeComponentImpl(DozeMachine.Service service) {
                initialize(service);
            }

            public final DozeMachine getDozeMachine() {
                return this.dozeMachineProvider.get();
            }

            public final void initialize(DozeMachine.Service service) {
                InstanceFactory create = InstanceFactory.create(service);
                this.dozeMachineServiceProvider = create;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.providesWrappedServiceProvider = DoubleCheck.provider(new DozeModule_ProvidesWrappedServiceFactory(create, tvSysUIComponentImpl.dozeServiceHostProvider, tvSysUIComponentImpl.dozeParametersProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                this.providesDozeWakeLockProvider = DoubleCheck.provider(new DozeModule_ProvidesDozeWakeLockFactory(tvSysUIComponentImpl2.builderProvider3, tvSysUIComponentImpl2.this$0.provideMainHandlerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl3.this$0;
                this.dozePauserProvider = DoubleCheck.provider(new DozePauser_Factory(daggerTvGlobalRootComponent.provideMainHandlerProvider, daggerTvGlobalRootComponent.provideAlarmManagerProvider, tvSysUIComponentImpl3.provideAlwaysOnDisplayPolicyProvider));
                this.dozeFalsingManagerAdapterProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(TvSysUIComponentImpl.this.falsingCollectorImplProvider, 3));
                TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = tvSysUIComponentImpl4.this$0;
                this.dozeTriggersProvider = DoubleCheck.provider(DozeTriggers_Factory.create(daggerTvGlobalRootComponent2.contextProvider, tvSysUIComponentImpl4.dozeServiceHostProvider, tvSysUIComponentImpl4.provideAmbientDisplayConfigurationProvider, tvSysUIComponentImpl4.dozeParametersProvider, tvSysUIComponentImpl4.asyncSensorManagerProvider, this.providesDozeWakeLockProvider, tvSysUIComponentImpl4.dockManagerImplProvider, tvSysUIComponentImpl4.provideProximitySensorProvider, tvSysUIComponentImpl4.provideProximityCheckProvider, tvSysUIComponentImpl4.dozeLogProvider, tvSysUIComponentImpl4.providesBroadcastDispatcherProvider, tvSysUIComponentImpl4.secureSettingsImplProvider, tvSysUIComponentImpl4.authControllerProvider, daggerTvGlobalRootComponent2.provideMainDelayableExecutorProvider, daggerTvGlobalRootComponent2.provideUiEventLoggerProvider, tvSysUIComponentImpl4.keyguardStateControllerImplProvider, tvSysUIComponentImpl4.devicePostureControllerImplProvider, tvSysUIComponentImpl4.provideBatteryControllerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = tvSysUIComponentImpl5.this$0;
                this.dozeUiProvider = DoubleCheck.provider(DozeUi_Factory.create(daggerTvGlobalRootComponent3.contextProvider, daggerTvGlobalRootComponent3.provideAlarmManagerProvider, this.providesDozeWakeLockProvider, tvSysUIComponentImpl5.dozeServiceHostProvider, daggerTvGlobalRootComponent3.provideMainHandlerProvider, tvSysUIComponentImpl5.dozeParametersProvider, tvSysUIComponentImpl5.keyguardUpdateMonitorProvider, tvSysUIComponentImpl5.statusBarStateControllerImplProvider, tvSysUIComponentImpl5.dozeLogProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl6 = TvSysUIComponentImpl.this;
                Provider<AsyncSensorManager> provider = tvSysUIComponentImpl6.asyncSensorManagerProvider;
                Provider<Context> provider2 = tvSysUIComponentImpl6.this$0.contextProvider;
                Provider<DozeParameters> provider3 = tvSysUIComponentImpl6.dozeParametersProvider;
                LatencyTester_Factory latencyTester_Factory = new LatencyTester_Factory(provider, provider2, provider3, 1);
                this.providesBrightnessSensorsProvider = latencyTester_Factory;
                Provider<DozeScreenBrightness> provider4 = DoubleCheck.provider(DozeScreenBrightness_Factory.create(provider2, this.providesWrappedServiceProvider, provider, latencyTester_Factory, tvSysUIComponentImpl6.dozeServiceHostProvider, tvSysUIComponentImpl6.provideHandlerProvider, tvSysUIComponentImpl6.provideAlwaysOnDisplayPolicyProvider, tvSysUIComponentImpl6.wakefulnessLifecycleProvider, provider3, tvSysUIComponentImpl6.devicePostureControllerImplProvider, tvSysUIComponentImpl6.dozeLogProvider));
                this.dozeScreenBrightnessProvider = provider4;
                Provider<DozeMachine.Service> provider5 = this.providesWrappedServiceProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl7 = TvSysUIComponentImpl.this;
                this.dozeScreenStateProvider = DoubleCheck.provider(DozeScreenState_Factory.create(provider5, tvSysUIComponentImpl7.this$0.provideMainHandlerProvider, tvSysUIComponentImpl7.dozeServiceHostProvider, tvSysUIComponentImpl7.dozeParametersProvider, this.providesDozeWakeLockProvider, tvSysUIComponentImpl7.authControllerProvider, tvSysUIComponentImpl7.udfpsControllerProvider, tvSysUIComponentImpl7.dozeLogProvider, provider4));
                FrameworkServicesModule_ProvideIWallPaperManagerFactory frameworkServicesModule_ProvideIWallPaperManagerFactory = FrameworkServicesModule_ProvideIWallPaperManagerFactory.InstanceHolder.INSTANCE;
                TvSysUIComponentImpl tvSysUIComponentImpl8 = TvSysUIComponentImpl.this;
                this.dozeWallpaperStateProvider = DoubleCheck.provider(new DozeWallpaperState_Factory(frameworkServicesModule_ProvideIWallPaperManagerFactory, tvSysUIComponentImpl8.biometricUnlockControllerProvider, tvSysUIComponentImpl8.dozeParametersProvider, 0));
                TvSysUIComponentImpl tvSysUIComponentImpl9 = TvSysUIComponentImpl.this;
                this.dozeDockHandlerProvider = DoubleCheck.provider(new ProtoTracer_Factory(tvSysUIComponentImpl9.provideAmbientDisplayConfigurationProvider, tvSysUIComponentImpl9.dockManagerImplProvider, 1));
                Provider<DozeAuthRemover> provider6 = DoubleCheck.provider(new DozeAuthRemover_Factory(TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider, 0));
                this.dozeAuthRemoverProvider = provider6;
                DozeModule_ProvidesDozeMachinePartesFactory create2 = DozeModule_ProvidesDozeMachinePartesFactory.create(this.dozePauserProvider, this.dozeFalsingManagerAdapterProvider, this.dozeTriggersProvider, this.dozeUiProvider, this.dozeScreenStateProvider, this.dozeScreenBrightnessProvider, this.dozeWallpaperStateProvider, this.dozeDockHandlerProvider, provider6);
                this.providesDozeMachinePartesProvider = create2;
                Provider<DozeMachine.Service> provider7 = this.providesWrappedServiceProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl10 = TvSysUIComponentImpl.this;
                this.dozeMachineProvider = DoubleCheck.provider(DozeMachine_Factory.create(provider7, tvSysUIComponentImpl10.provideAmbientDisplayConfigurationProvider, this.providesDozeWakeLockProvider, tvSysUIComponentImpl10.wakefulnessLifecycleProvider, tvSysUIComponentImpl10.provideBatteryControllerProvider, tvSysUIComponentImpl10.dozeLogProvider, tvSysUIComponentImpl10.dockManagerImplProvider, tvSysUIComponentImpl10.dozeServiceHostProvider, create2));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentFactory */
        public final class DreamOverlayComponentFactory implements DreamOverlayComponent.Factory {
            public DreamOverlayComponentFactory() {
            }

            public final DreamOverlayComponent create(ViewModelStore viewModelStore, Complication.Host host) {
                Objects.requireNonNull(viewModelStore);
                Objects.requireNonNull(host);
                return new DreamOverlayComponentImpl(viewModelStore, host);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentImpl */
        public final class DreamOverlayComponentImpl implements DreamOverlayComponent {
            public Provider<ComplicationHostViewComponent.Factory> complicationHostViewComponentFactoryProvider;
            public Provider<DreamOverlayContainerViewController> dreamOverlayContainerViewControllerProvider;
            public Provider<DreamOverlayStatusBarViewController> dreamOverlayStatusBarViewControllerProvider;
            public final Complication.Host host;
            public Provider<BatteryMeterViewController> providesBatteryMeterViewControllerProvider;
            public Provider<BatteryMeterView> providesBatteryMeterViewProvider;
            public Provider<Long> providesBurnInProtectionUpdateIntervalProvider;
            public Provider<DreamOverlayContainerView> providesDreamOverlayContainerViewProvider;
            public Provider<ViewGroup> providesDreamOverlayContentViewProvider;
            public Provider<DreamOverlayStatusBarView> providesDreamOverlayStatusBarViewProvider;
            public Provider<LifecycleOwner> providesLifecycleOwnerProvider;
            public Provider<Lifecycle> providesLifecycleProvider;
            public Provider<LifecycleRegistry> providesLifecycleRegistryProvider;
            public Provider<Integer> providesMaxBurnInOffsetProvider;
            public final ViewModelStore store;

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationHostViewComponentFactory */
            public final class ComplicationHostViewComponentFactory implements ComplicationHostViewComponent.Factory {
                public ComplicationHostViewComponentFactory() {
                }

                public final ComplicationHostViewComponent create() {
                    return new ComplicationHostViewComponentI();
                }
            }

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationHostViewComponentI */
            public final class ComplicationHostViewComponentI implements ComplicationHostViewComponent {
                public Provider<ConstraintLayout> providesComplicationHostViewProvider;
                public Provider<Integer> providesComplicationPaddingProvider;

                public ComplicationHostViewComponentI() {
                    initialize();
                }

                public final ComplicationLayoutEngine complicationLayoutEngine() {
                    return new ComplicationLayoutEngine(this.providesComplicationHostViewProvider.get(), this.providesComplicationPaddingProvider.get().intValue());
                }

                public final ComplicationHostViewController getController() {
                    return new ComplicationHostViewController(this.providesComplicationHostViewProvider.get(), complicationLayoutEngine(), DreamOverlayComponentImpl.this.providesLifecycleOwnerProvider.get(), DreamOverlayComponentImpl.this.namedComplicationCollectionViewModel());
                }

                public final void initialize() {
                    this.providesComplicationHostViewProvider = DoubleCheck.provider(new C0792x8a1d9ad2(TvSysUIComponentImpl.this.providerLayoutInflaterProvider));
                    this.providesComplicationPaddingProvider = DoubleCheck.provider(new C0793x7dca8e24(TvSysUIComponentImpl.this.this$0.provideResourcesProvider));
                }
            }

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationViewModelComponentFactory */
            public final class ComplicationViewModelComponentFactory implements ComplicationViewModelComponent.Factory {
                public ComplicationViewModelComponentFactory() {
                }

                public final ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId) {
                    Objects.requireNonNull(complication);
                    Objects.requireNonNull(complicationId);
                    return new ComplicationViewModelComponentI(complication, complicationId);
                }
            }

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationViewModelComponentI */
            public final class ComplicationViewModelComponentI implements ComplicationViewModelComponent {
                public final Complication complication;

                /* renamed from: id */
                public final ComplicationId f83id;

                public ComplicationViewModelComponentI(Complication complication2, ComplicationId complicationId) {
                    this.complication = complication2;
                    this.f83id = complicationId;
                }

                public final ComplicationViewModel complicationViewModel() {
                    return new ComplicationViewModel(this.complication, this.f83id, DreamOverlayComponentImpl.this.host);
                }

                public final ComplicationViewModelProvider getViewModelProvider() {
                    return new ComplicationViewModelProvider(DreamOverlayComponentImpl.this.store, complicationViewModel());
                }
            }

            public DreamOverlayComponentImpl(ViewModelStore viewModelStore, Complication.Host host2) {
                this.store = viewModelStore;
                this.host = host2;
                initialize(viewModelStore, host2);
            }

            public final ComplicationCollectionLiveData complicationCollectionLiveData() {
                return new ComplicationCollectionLiveData(TvSysUIComponentImpl.this.dreamOverlayStateControllerProvider.get());
            }

            public final ComplicationCollectionViewModel complicationCollectionViewModel() {
                return new ComplicationCollectionViewModel(complicationCollectionLiveData(), complicationViewModelTransformer());
            }

            public final ComplicationViewModelTransformer complicationViewModelTransformer() {
                return new ComplicationViewModelTransformer(new ComplicationViewModelComponentFactory());
            }

            public final DreamOverlayContainerViewController getDreamOverlayContainerViewController() {
                return this.dreamOverlayContainerViewControllerProvider.get();
            }

            public final DreamOverlayTouchMonitor getDreamOverlayTouchMonitor() {
                return new DreamOverlayTouchMonitor(TvSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), this.providesLifecycleProvider.get(), new InputSessionComponentFactory(), setOfDreamTouchHandler());
            }

            public final LifecycleOwner getLifecycleOwner() {
                return this.providesLifecycleOwnerProvider.get();
            }

            public final LifecycleRegistry getLifecycleRegistry() {
                return this.providesLifecycleRegistryProvider.get();
            }

            public final void initialize(ViewModelStore viewModelStore, Complication.Host host2) {
                this.providesDreamOverlayContainerViewProvider = DoubleCheck.provider(new QSFragmentModule_ProvidesQuickQSPanelFactory(TvSysUIComponentImpl.this.providerLayoutInflaterProvider, 3));
                this.complicationHostViewComponentFactoryProvider = new Provider<ComplicationHostViewComponent.Factory>() {
                    public final ComplicationHostViewComponent.Factory get() {
                        return new ComplicationHostViewComponentFactory();
                    }
                };
                this.providesDreamOverlayContentViewProvider = DoubleCheck.provider(new DateFormatUtil_Factory(this.providesDreamOverlayContainerViewProvider, 1));
                this.providesDreamOverlayStatusBarViewProvider = DoubleCheck.provider(new ScreenLifecycle_Factory(this.providesDreamOverlayContainerViewProvider, 1));
                Provider<BatteryMeterView> provider = DoubleCheck.provider(new PrivacyLogger_Factory(this.providesDreamOverlayContainerViewProvider, 1));
                this.providesBatteryMeterViewProvider = provider;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                Provider<ConfigurationController> provider2 = tvSysUIComponentImpl.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider3 = tvSysUIComponentImpl.tunerServiceImplProvider;
                Provider<BroadcastDispatcher> provider4 = tvSysUIComponentImpl.providesBroadcastDispatcherProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl.this$0;
                Provider<BatteryMeterViewController> provider5 = DoubleCheck.provider(AppOpsControllerImpl_Factory.create$1(provider, provider2, provider3, provider4, daggerTvGlobalRootComponent.provideMainHandlerProvider, daggerTvGlobalRootComponent.provideContentResolverProvider, tvSysUIComponentImpl.provideBatteryControllerProvider));
                this.providesBatteryMeterViewControllerProvider = provider5;
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = tvSysUIComponentImpl2.this$0;
                this.dreamOverlayStatusBarViewControllerProvider = DoubleCheck.provider(new DreamOverlayStatusBarViewController_Factory(daggerTvGlobalRootComponent2.contextProvider, this.providesDreamOverlayStatusBarViewProvider, tvSysUIComponentImpl2.provideBatteryControllerProvider, provider5, daggerTvGlobalRootComponent2.provideConnectivityManagagerProvider));
                Provider<Integer> provider6 = DoubleCheck.provider(new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(TvSysUIComponentImpl.this.this$0.provideResourcesProvider, 0));
                this.providesMaxBurnInOffsetProvider = provider6;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = TvSysUIComponentImpl.this.this$0;
                MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(daggerTvGlobalRootComponent3.provideResourcesProvider, 2);
                this.providesBurnInProtectionUpdateIntervalProvider = mediaBrowserFactory_Factory;
                this.dreamOverlayContainerViewControllerProvider = DoubleCheck.provider(DreamOverlayContainerViewController_Factory.create(this.providesDreamOverlayContainerViewProvider, this.complicationHostViewComponentFactoryProvider, this.providesDreamOverlayContentViewProvider, this.dreamOverlayStatusBarViewControllerProvider, daggerTvGlobalRootComponent3.provideMainHandlerProvider, provider6, mediaBrowserFactory_Factory));
                DelegateFactory delegateFactory = new DelegateFactory();
                this.providesLifecycleRegistryProvider = delegateFactory;
                Provider<LifecycleOwner> provider7 = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(delegateFactory, 1));
                this.providesLifecycleOwnerProvider = provider7;
                DelegateFactory.setDelegate(this.providesLifecycleRegistryProvider, DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(provider7, 1)));
                this.providesLifecycleProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.providesLifecycleOwnerProvider, 2));
            }

            public final ComplicationCollectionViewModel namedComplicationCollectionViewModel() {
                return C0794x1653c7a9.providesComplicationCollectionViewModel(this.store, complicationCollectionViewModel());
            }

            public final Set<DreamTouchHandler> setOfDreamTouchHandler() {
                return Collections.singleton(TvSysUIComponentImpl.this.providesBouncerSwipeTouchHandler());
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$ExpandableNotificationRowComponentBuilder */
        public final class ExpandableNotificationRowComponentBuilder implements ExpandableNotificationRowComponent.Builder {
            public ExpandableNotificationRow expandableNotificationRow;
            public NotificationListContainer listContainer;
            public NotificationEntry notificationEntry;
            public ExpandableNotificationRow.OnExpandClickListener onExpandClickListener;

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

            public ExpandableNotificationRowComponentBuilder() {
            }

            public final ExpandableNotificationRowComponent build() {
                R$color.checkBuilderRequirement(this.expandableNotificationRow, ExpandableNotificationRow.class);
                R$color.checkBuilderRequirement(this.notificationEntry, NotificationEntry.class);
                R$color.checkBuilderRequirement(this.onExpandClickListener, ExpandableNotificationRow.OnExpandClickListener.class);
                R$color.checkBuilderRequirement(this.listContainer, NotificationListContainer.class);
                return new ExpandableNotificationRowComponentImpl(this.expandableNotificationRow, this.notificationEntry, this.onExpandClickListener, this.listContainer);
            }

            /* renamed from: expandableNotificationRow  reason: collision with other method in class */
            public final ExpandableNotificationRowComponentBuilder m276expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow2) {
                Objects.requireNonNull(expandableNotificationRow2);
                this.expandableNotificationRow = expandableNotificationRow2;
                return this;
            }

            /* renamed from: listContainer  reason: collision with other method in class */
            public final ExpandableNotificationRowComponentBuilder m277listContainer(NotificationListContainer notificationListContainer) {
                Objects.requireNonNull(notificationListContainer);
                this.listContainer = notificationListContainer;
                return this;
            }

            /* renamed from: notificationEntry  reason: collision with other method in class */
            public final ExpandableNotificationRowComponentBuilder m278notificationEntry(NotificationEntry notificationEntry2) {
                Objects.requireNonNull(notificationEntry2);
                this.notificationEntry = notificationEntry2;
                return this;
            }

            public final ExpandableNotificationRowComponentBuilder onExpandClickListener(ExpandableNotificationRow.OnExpandClickListener onExpandClickListener2) {
                Objects.requireNonNull(onExpandClickListener2);
                this.onExpandClickListener = onExpandClickListener2;
                return this;
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$ExpandableNotificationRowComponentImpl */
        public final class ExpandableNotificationRowComponentImpl implements ExpandableNotificationRowComponent {
            public Provider<ActivatableNotificationViewController> activatableNotificationViewControllerProvider;
            public Provider<ExpandableNotificationRowController> expandableNotificationRowControllerProvider;
            public Provider<ExpandableNotificationRowDragController> expandableNotificationRowDragControllerProvider;
            public Provider<ExpandableNotificationRow> expandableNotificationRowProvider;
            public Provider<ExpandableOutlineViewController> expandableOutlineViewControllerProvider;
            public Provider<ExpandableViewController> expandableViewControllerProvider;
            public Provider<NotificationTapHelper.Factory> factoryProvider;
            public Provider<NotificationListContainer> listContainerProvider;
            public final NotificationEntry notificationEntry;
            public Provider<NotificationEntry> notificationEntryProvider;
            public Provider<ExpandableNotificationRow.OnExpandClickListener> onExpandClickListenerProvider;
            public Provider<String> provideAppNameProvider;
            public Provider<String> provideNotificationKeyProvider;
            public Provider<StatusBarNotification> provideStatusBarNotificationProvider;
            public Provider<RemoteInputViewSubcomponent.Factory> remoteInputViewSubcomponentFactoryProvider;

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$ExpandableNotificationRowComponentImpl$RemoteInputViewSubcomponentFactory */
            public final class RemoteInputViewSubcomponentFactory implements RemoteInputViewSubcomponent.Factory {
                public RemoteInputViewSubcomponentFactory() {
                }

                public final RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
                    Objects.requireNonNull(remoteInputView);
                    Objects.requireNonNull(remoteInputController);
                    return new RemoteInputViewSubcomponentI(remoteInputView, remoteInputController);
                }
            }

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$ExpandableNotificationRowComponentImpl$RemoteInputViewSubcomponentI */
            public final class RemoteInputViewSubcomponentI implements RemoteInputViewSubcomponent {
                public final RemoteInputController remoteInputController;
                public final RemoteInputView view;

                public RemoteInputViewSubcomponentI(RemoteInputView remoteInputView, RemoteInputController remoteInputController2) {
                    this.view = remoteInputView;
                    this.remoteInputController = remoteInputController2;
                }

                public final RemoteInputViewControllerImpl remoteInputViewControllerImpl() {
                    RemoteInputView remoteInputView = this.view;
                    ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl = ExpandableNotificationRowComponentImpl.this;
                    return new RemoteInputViewControllerImpl(remoteInputView, expandableNotificationRowComponentImpl.notificationEntry, TvSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider.get(), this.remoteInputController, TvSysUIComponentImpl.this.this$0.provideShortcutManagerProvider.get(), TvSysUIComponentImpl.this.this$0.provideUiEventLoggerProvider.get());
                }

                public final RemoteInputViewController getController() {
                    return remoteInputViewControllerImpl();
                }
            }

            public ExpandableNotificationRowComponentImpl(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry2, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, NotificationListContainer notificationListContainer) {
                this.notificationEntry = notificationEntry2;
                initialize(expandableNotificationRow, notificationEntry2, onExpandClickListener, notificationListContainer);
            }

            public final ExpandableNotificationRowController getExpandableNotificationRowController() {
                return this.expandableNotificationRowControllerProvider.get();
            }

            public final void initialize(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry2, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, NotificationListContainer notificationListContainer) {
                this.expandableNotificationRowProvider = InstanceFactory.create(expandableNotificationRow);
                this.listContainerProvider = InstanceFactory.create(notificationListContainer);
                this.remoteInputViewSubcomponentFactoryProvider = new Provider<RemoteInputViewSubcomponent.Factory>() {
                    public final RemoteInputViewSubcomponent.Factory get() {
                        return new RemoteInputViewSubcomponentFactory();
                    }
                };
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.factoryProvider = new NotificationTapHelper_Factory_Factory(tvSysUIComponentImpl.falsingManagerProxyProvider, tvSysUIComponentImpl.this$0.provideMainDelayableExecutorProvider);
                KeyboardUI_Factory create$8 = KeyboardUI_Factory.create$8(this.expandableNotificationRowProvider);
                this.expandableViewControllerProvider = create$8;
                UnpinNotifications_Factory create = UnpinNotifications_Factory.create(this.expandableNotificationRowProvider, create$8);
                this.expandableOutlineViewControllerProvider = create;
                Provider<ExpandableNotificationRow> provider = this.expandableNotificationRowProvider;
                Provider<NotificationTapHelper.Factory> provider2 = this.factoryProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                this.activatableNotificationViewControllerProvider = ActivatableNotificationViewController_Factory.create(provider, provider2, create, tvSysUIComponentImpl2.this$0.provideAccessibilityManagerProvider, tvSysUIComponentImpl2.falsingManagerProxyProvider, tvSysUIComponentImpl2.falsingCollectorImplProvider);
                InstanceFactory create2 = InstanceFactory.create(notificationEntry2);
                this.notificationEntryProvider = create2;
                C1336xc255c3ca expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory = new C1336xc255c3ca(create2);
                this.provideStatusBarNotificationProvider = expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory;
                this.provideAppNameProvider = new C1334x3e2d0aca(TvSysUIComponentImpl.this.this$0.contextProvider, expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory);
                this.provideNotificationKeyProvider = new C1335xdc9a80a2(expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory);
                InstanceFactory create3 = InstanceFactory.create(onExpandClickListener);
                this.onExpandClickListenerProvider = create3;
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl3.this$0;
                Provider<Context> provider3 = daggerTvGlobalRootComponent.contextProvider;
                Provider<HeadsUpManagerPhone> provider4 = tvSysUIComponentImpl3.provideHeadsUpManagerPhoneProvider;
                ControlsActivity_Factory controlsActivity_Factory = new ControlsActivity_Factory(provider3, provider4, 2);
                this.expandableNotificationRowDragControllerProvider = controlsActivity_Factory;
                Provider<HeadsUpManagerPhone> provider5 = provider4;
                Provider<RowContentBindStage> provider6 = tvSysUIComponentImpl3.rowContentBindStageProvider;
                Provider<HeadsUpManagerPhone> provider7 = provider5;
                Provider<NotificationLogger> provider8 = tvSysUIComponentImpl3.provideNotificationLoggerProvider;
                Provider<StatusBarStateControllerImpl> provider9 = tvSysUIComponentImpl3.statusBarStateControllerImplProvider;
                Provider<NotificationGutsManager> provider10 = tvSysUIComponentImpl3.provideNotificationGutsManagerProvider;
                Provider<Boolean> provider11 = tvSysUIComponentImpl3.provideAllowNotificationLongPressProvider;
                Provider<OnUserInteractionCallback> provider12 = tvSysUIComponentImpl3.provideOnUserInteractionCallbackProvider;
                Provider<FalsingManagerProxy> provider13 = tvSysUIComponentImpl3.falsingManagerProxyProvider;
                Provider provider14 = tvSysUIComponentImpl3.falsingCollectorImplProvider;
                Provider<FeatureFlagsRelease> provider15 = tvSysUIComponentImpl3.featureFlagsReleaseProvider;
                Provider<PeopleNotificationIdentifierImpl> provider16 = tvSysUIComponentImpl3.peopleNotificationIdentifierImplProvider;
                Provider<Optional<BubblesManager>> provider17 = tvSysUIComponentImpl3.provideBubblesManagerProvider;
                InstanceFactory instanceFactory = create3;
                this.expandableNotificationRowControllerProvider = DoubleCheck.provider(ExpandableNotificationRowController_Factory.create(this.expandableNotificationRowProvider, this.listContainerProvider, this.remoteInputViewSubcomponentFactoryProvider, this.activatableNotificationViewControllerProvider, tvSysUIComponentImpl3.provideNotificationMediaManagerProvider, daggerTvGlobalRootComponent.providesPluginManagerProvider, tvSysUIComponentImpl3.bindSystemClockProvider, this.provideAppNameProvider, this.provideNotificationKeyProvider, tvSysUIComponentImpl3.keyguardBypassControllerProvider, tvSysUIComponentImpl3.provideGroupMembershipManagerProvider, tvSysUIComponentImpl3.provideGroupExpansionManagerProvider, provider6, provider8, provider7, instanceFactory, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, controlsActivity_Factory));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$FragmentCreatorFactory */
        public final class FragmentCreatorFactory implements FragmentService.FragmentCreator.Factory {
            public FragmentCreatorFactory() {
            }

            public final FragmentService.FragmentCreator build() {
                return new FragmentCreatorImpl();
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$FragmentCreatorImpl */
        public final class FragmentCreatorImpl implements FragmentService.FragmentCreator {
            public FragmentCreatorImpl() {
            }

            public final QSFragment createQSFragment() {
                QSTileHost qSTileHost = TvSysUIComponentImpl.this.qSTileHostProvider.get();
                return new QSFragment(TvSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider.get(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TvSysUIComponentImpl.this.provideCommandQueueProvider.get(), TvSysUIComponentImpl.this.providesQSMediaHostProvider.get(), TvSysUIComponentImpl.this.providesQuickQSMediaHostProvider.get(), TvSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), new QSFragmentComponentFactory(), qSFragmentDisableFlagsLogger(), TvSysUIComponentImpl.this.falsingManagerProxyProvider.get(), TvSysUIComponentImpl.this.this$0.dumpManagerProvider.get());
            }

            public final QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger() {
                return new QSFragmentDisableFlagsLogger(TvSysUIComponentImpl.this.provideQSFragmentDisableLogBufferProvider.get(), TvSysUIComponentImpl.this.disableFlagsLoggerProvider.get());
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$InputSessionComponentFactory */
        public final class InputSessionComponentFactory implements InputSessionComponent.Factory {
            public InputSessionComponentFactory() {
            }

            public final InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z) {
                Objects.requireNonNull(str);
                Objects.requireNonNull(inputChannelCompat$InputEventListener);
                Objects.requireNonNull(onGestureListener);
                Objects.requireNonNull(Boolean.valueOf(z));
                return new InputSessionComponentImpl(TvSysUIComponentImpl.this, str, inputChannelCompat$InputEventListener, onGestureListener, Boolean.valueOf(z));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$InputSessionComponentImpl */
        public final class InputSessionComponentImpl implements InputSessionComponent {
            public final GestureDetector.OnGestureListener gestureListener;
            public final InputChannelCompat$InputEventListener inputEventListener;
            public final String name;
            public final Boolean pilferOnGestureConsume;

            public final InputSession getInputSession() {
                return new InputSession(this.name, this.inputEventListener, this.gestureListener, this.pilferOnGestureConsume.booleanValue());
            }

            public InputSessionComponentImpl(TvSysUIComponentImpl tvSysUIComponentImpl, String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, Boolean bool) {
                this.name = str;
                this.inputEventListener = inputChannelCompat$InputEventListener;
                this.gestureListener = onGestureListener;
                this.pilferOnGestureConsume = bool;
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardBouncerComponentFactory */
        public final class KeyguardBouncerComponentFactory implements KeyguardBouncerComponent.Factory {
            public KeyguardBouncerComponentFactory() {
            }

            public final KeyguardBouncerComponent create(ViewGroup viewGroup) {
                Objects.requireNonNull(viewGroup);
                return new KeyguardBouncerComponentImpl(viewGroup);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardBouncerComponentImpl */
        public final class KeyguardBouncerComponentImpl implements KeyguardBouncerComponent {
            public Provider<ViewGroup> bouncerContainerProvider;
            public Provider<AdminSecondaryLockScreenController.Factory> factoryProvider;
            public Provider<EmergencyButtonController.Factory> factoryProvider2;
            public Provider<KeyguardInputViewController.Factory> factoryProvider3;
            public Provider factoryProvider4;
            public Provider<KeyguardHostViewController> keyguardHostViewControllerProvider;
            public Provider<KeyguardSecurityViewFlipperController> keyguardSecurityViewFlipperControllerProvider;
            public Provider liftToActivateListenerProvider;
            public Provider<KeyguardHostView> providesKeyguardHostViewProvider;
            public Provider<KeyguardSecurityContainer> providesKeyguardSecurityContainerProvider;
            public Provider<KeyguardSecurityViewFlipper> providesKeyguardSecurityViewFlipperProvider;

            public KeyguardBouncerComponentImpl(ViewGroup viewGroup) {
                initialize(viewGroup);
            }

            public final KeyguardHostViewController getKeyguardHostViewController() {
                return this.keyguardHostViewControllerProvider.get();
            }

            public final void initialize(ViewGroup viewGroup) {
                InstanceFactory create = InstanceFactory.create(viewGroup);
                this.bouncerContainerProvider = create;
                Provider<KeyguardHostView> provider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(create, TvSysUIComponentImpl.this.providerLayoutInflaterProvider, 1));
                this.providesKeyguardHostViewProvider = provider;
                Provider<KeyguardSecurityContainer> m = C0773x82ed519e.m54m(provider, 1);
                this.providesKeyguardSecurityContainerProvider = m;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl.this$0;
                this.factoryProvider = DoubleCheck.provider(new AdminSecondaryLockScreenController_Factory_Factory(daggerTvGlobalRootComponent.contextProvider, m, tvSysUIComponentImpl.keyguardUpdateMonitorProvider, daggerTvGlobalRootComponent.provideMainHandlerProvider));
                this.providesKeyguardSecurityViewFlipperProvider = C0772x82ed519d.m53m(this.providesKeyguardSecurityContainerProvider, 1);
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = tvSysUIComponentImpl2.this$0;
                this.liftToActivateListenerProvider = new LiftToActivateListener_Factory(daggerTvGlobalRootComponent2.provideAccessibilityManagerProvider, 0);
                EmergencyButtonController_Factory_Factory create2 = EmergencyButtonController_Factory_Factory.create(tvSysUIComponentImpl2.provideConfigurationControllerProvider, tvSysUIComponentImpl2.keyguardUpdateMonitorProvider, daggerTvGlobalRootComponent2.provideTelephonyManagerProvider, daggerTvGlobalRootComponent2.providePowerManagerProvider, daggerTvGlobalRootComponent2.provideActivityTaskManagerProvider, tvSysUIComponentImpl2.shadeControllerImplProvider, daggerTvGlobalRootComponent2.provideTelecomManagerProvider, tvSysUIComponentImpl2.provideMetricsLoggerProvider);
                this.factoryProvider2 = create2;
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                Provider<KeyguardUpdateMonitor> provider2 = tvSysUIComponentImpl3.keyguardUpdateMonitorProvider;
                Provider<LockPatternUtils> provider3 = tvSysUIComponentImpl3.provideLockPatternUtilsProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = tvSysUIComponentImpl3.this$0;
                KeyguardInputViewController_Factory_Factory create3 = KeyguardInputViewController_Factory_Factory.create(provider2, provider3, daggerTvGlobalRootComponent3.provideLatencyTrackerProvider, tvSysUIComponentImpl3.factoryProvider6, daggerTvGlobalRootComponent3.provideInputMethodManagerProvider, daggerTvGlobalRootComponent3.provideMainDelayableExecutorProvider, daggerTvGlobalRootComponent3.provideResourcesProvider, this.liftToActivateListenerProvider, daggerTvGlobalRootComponent3.provideTelephonyManagerProvider, tvSysUIComponentImpl3.falsingCollectorImplProvider, create2, tvSysUIComponentImpl3.devicePostureControllerImplProvider);
                this.factoryProvider3 = create3;
                Provider<KeyguardSecurityViewFlipperController> provider4 = DoubleCheck.provider(new KeyguardSecurityViewFlipperController_Factory(this.providesKeyguardSecurityViewFlipperProvider, TvSysUIComponentImpl.this.providerLayoutInflaterProvider, create3, this.factoryProvider2));
                this.keyguardSecurityViewFlipperControllerProvider = provider4;
                Provider<KeyguardSecurityContainer> provider5 = this.providesKeyguardSecurityContainerProvider;
                Provider<AdminSecondaryLockScreenController.Factory> provider6 = this.factoryProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                KeyguardSecurityContainerController_Factory_Factory create4 = KeyguardSecurityContainerController_Factory_Factory.create(provider5, provider6, tvSysUIComponentImpl4.provideLockPatternUtilsProvider, tvSysUIComponentImpl4.keyguardUpdateMonitorProvider, tvSysUIComponentImpl4.keyguardSecurityModelProvider, tvSysUIComponentImpl4.provideMetricsLoggerProvider, tvSysUIComponentImpl4.this$0.provideUiEventLoggerProvider, tvSysUIComponentImpl4.keyguardStateControllerImplProvider, provider4, tvSysUIComponentImpl4.provideConfigurationControllerProvider, tvSysUIComponentImpl4.falsingCollectorImplProvider, tvSysUIComponentImpl4.falsingManagerProxyProvider, tvSysUIComponentImpl4.userSwitcherControllerProvider, tvSysUIComponentImpl4.featureFlagsReleaseProvider, tvSysUIComponentImpl4.globalSettingsImplProvider, tvSysUIComponentImpl4.sessionTrackerProvider);
                this.factoryProvider4 = create4;
                Provider<KeyguardHostView> provider7 = this.providesKeyguardHostViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                Provider<KeyguardUpdateMonitor> provider8 = tvSysUIComponentImpl5.keyguardUpdateMonitorProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = tvSysUIComponentImpl5.this$0;
                this.keyguardHostViewControllerProvider = DoubleCheck.provider(KeyguardHostViewController_Factory.create(provider7, provider8, daggerTvGlobalRootComponent4.provideAudioManagerProvider, daggerTvGlobalRootComponent4.provideTelephonyManagerProvider, tvSysUIComponentImpl5.providesViewMediatorCallbackProvider, create4));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardQsUserSwitchComponentFactory */
        public final class KeyguardQsUserSwitchComponentFactory implements KeyguardQsUserSwitchComponent.Factory {
            public KeyguardQsUserSwitchComponentFactory() {
            }

            public final KeyguardQsUserSwitchComponent build(FrameLayout frameLayout) {
                Objects.requireNonNull(frameLayout);
                return new KeyguardQsUserSwitchComponentImpl(frameLayout);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardQsUserSwitchComponentImpl */
        public final class KeyguardQsUserSwitchComponentImpl implements KeyguardQsUserSwitchComponent {
            public Provider<KeyguardQsUserSwitchController> keyguardQsUserSwitchControllerProvider;
            public Provider<FrameLayout> userAvatarContainerProvider;

            public KeyguardQsUserSwitchComponentImpl(FrameLayout frameLayout) {
                initialize(frameLayout);
            }

            public final KeyguardQsUserSwitchController getKeyguardQsUserSwitchController() {
                return this.keyguardQsUserSwitchControllerProvider.get();
            }

            public final void initialize(FrameLayout frameLayout) {
                InstanceFactory create = InstanceFactory.create(frameLayout);
                this.userAvatarContainerProvider = create;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl.this$0;
                this.keyguardQsUserSwitchControllerProvider = DoubleCheck.provider(KeyguardQsUserSwitchController_Factory.create(create, daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.provideResourcesProvider, daggerTvGlobalRootComponent.screenLifecycleProvider, tvSysUIComponentImpl.userSwitcherControllerProvider, tvSysUIComponentImpl.communalStateControllerProvider, tvSysUIComponentImpl.keyguardStateControllerImplProvider, tvSysUIComponentImpl.falsingManagerProxyProvider, tvSysUIComponentImpl.provideConfigurationControllerProvider, tvSysUIComponentImpl.statusBarStateControllerImplProvider, tvSysUIComponentImpl.dozeParametersProvider, tvSysUIComponentImpl.screenOffAnimationControllerProvider, tvSysUIComponentImpl.userSwitchDialogControllerProvider, daggerTvGlobalRootComponent.provideUiEventLoggerProvider));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardStatusBarViewComponentFactory */
        public final class KeyguardStatusBarViewComponentFactory implements KeyguardStatusBarViewComponent.Factory {
            public KeyguardStatusBarViewComponentFactory() {
            }

            public final KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                Objects.requireNonNull(keyguardStatusBarView);
                Objects.requireNonNull(notificationPanelViewStateProvider);
                return new KeyguardStatusBarViewComponentImpl(keyguardStatusBarView, notificationPanelViewStateProvider);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardStatusBarViewComponentImpl */
        public final class KeyguardStatusBarViewComponentImpl implements KeyguardStatusBarViewComponent {
            public Provider<StatusBarUserSwitcherController> bindStatusBarUserSwitcherControllerProvider;
            public Provider<BatteryMeterView> getBatteryMeterViewProvider;
            public Provider<CarrierText> getCarrierTextProvider;
            public Provider<StatusBarUserSwitcherContainer> getUserSwitcherContainerProvider;
            public final NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider;
            public Provider<StatusBarUserSwitcherControllerImpl> statusBarUserSwitcherControllerImplProvider;
            public final KeyguardStatusBarView view;
            public Provider<KeyguardStatusBarView> viewProvider;

            public KeyguardStatusBarViewComponentImpl(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider2) {
                this.view = keyguardStatusBarView;
                this.notificationPanelViewStateProvider = notificationPanelViewStateProvider2;
                initialize(keyguardStatusBarView, notificationPanelViewStateProvider2);
            }

            public final BatteryMeterViewController batteryMeterViewController() {
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = TvSysUIComponentImpl.this.this$0;
                Provider provider = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
                return new BatteryMeterViewController(this.getBatteryMeterViewProvider.get(), TvSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TvSysUIComponentImpl.this.tunerServiceImplProvider.get(), TvSysUIComponentImpl.this.providesBroadcastDispatcherProvider.get(), daggerTvGlobalRootComponent.mainHandler(), TvSysUIComponentImpl.this.this$0.provideContentResolverProvider.get(), TvSysUIComponentImpl.this.provideBatteryControllerProvider.get());
            }

            public final CarrierTextController carrierTextController() {
                return new CarrierTextController(this.getCarrierTextProvider.get(), carrierTextManagerBuilder(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get());
            }

            public final CarrierTextManager.Builder carrierTextManagerBuilder() {
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = TvSysUIComponentImpl.this.this$0;
                return new CarrierTextManager.Builder(daggerTvGlobalRootComponent.context, daggerTvGlobalRootComponent.mainResources(), TvSysUIComponentImpl.this.this$0.provideWifiManagerProvider.get(), TvSysUIComponentImpl.this.this$0.provideTelephonyManagerProvider.get(), TvSysUIComponentImpl.this.telephonyListenerManagerProvider.get(), TvSysUIComponentImpl.this.wakefulnessLifecycleProvider.get(), TvSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TvSysUIComponentImpl.this.provideBackgroundExecutorProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get());
            }

            public final KeyguardStatusBarViewController getKeyguardStatusBarViewController() {
                return new KeyguardStatusBarViewController(this.view, carrierTextController(), TvSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TvSysUIComponentImpl.this.systemStatusAnimationSchedulerProvider.get(), TvSysUIComponentImpl.this.provideBatteryControllerProvider.get(), TvSysUIComponentImpl.this.userInfoControllerImplProvider.get(), TvSysUIComponentImpl.this.statusBarIconControllerImplProvider.get(), TvSysUIComponentImpl.this.factoryProvider9.get(), batteryMeterViewController(), this.notificationPanelViewStateProvider, TvSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TvSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TvSysUIComponentImpl.this.biometricUnlockControllerProvider.get(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TvSysUIComponentImpl.this.statusBarContentInsetsProvider.get(), TvSysUIComponentImpl.this.this$0.provideUserManagerProvider.get(), TvSysUIComponentImpl.this.statusBarUserSwitcherFeatureControllerProvider.get(), this.bindStatusBarUserSwitcherControllerProvider.get(), TvSysUIComponentImpl.this.statusBarUserInfoTrackerProvider.get());
            }

            public final void initialize(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider2) {
                InstanceFactory create = InstanceFactory.create(keyguardStatusBarView);
                this.viewProvider = create;
                this.getCarrierTextProvider = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(create, 1));
                this.getBatteryMeterViewProvider = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.viewProvider, 1));
                Provider<StatusBarUserSwitcherContainer> provider = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.viewProvider, 1));
                this.getUserSwitcherContainerProvider = provider;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                ClockManager_Factory create$1 = ClockManager_Factory.create$1(provider, tvSysUIComponentImpl.statusBarUserInfoTrackerProvider, tvSysUIComponentImpl.statusBarUserSwitcherFeatureControllerProvider, tvSysUIComponentImpl.userSwitchDialogControllerProvider, tvSysUIComponentImpl.featureFlagsReleaseProvider, tvSysUIComponentImpl.provideActivityStarterProvider);
                this.statusBarUserSwitcherControllerImplProvider = create$1;
                this.bindStatusBarUserSwitcherControllerProvider = DoubleCheck.provider(create$1);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardStatusViewComponentFactory */
        public final class KeyguardStatusViewComponentFactory implements KeyguardStatusViewComponent.Factory {
            public KeyguardStatusViewComponentFactory() {
            }

            public final KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView) {
                Objects.requireNonNull(keyguardStatusView);
                return new KeyguardStatusViewComponentImpl(keyguardStatusView);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardStatusViewComponentImpl */
        public final class KeyguardStatusViewComponentImpl implements KeyguardStatusViewComponent {
            public Provider<KeyguardClockSwitch> getKeyguardClockSwitchProvider;
            public Provider<KeyguardSliceView> getKeyguardSliceViewProvider;
            public Provider<KeyguardSliceViewController> keyguardSliceViewControllerProvider;
            public final KeyguardStatusView presentation;
            public Provider<KeyguardStatusView> presentationProvider;

            public KeyguardStatusViewComponentImpl(KeyguardStatusView keyguardStatusView) {
                this.presentation = keyguardStatusView;
                initialize(keyguardStatusView);
            }

            public final KeyguardClockSwitchController getKeyguardClockSwitchController() {
                KeyguardBypassController keyguardBypassController = TvSysUIComponentImpl.this.keyguardBypassControllerProvider.get();
                return new KeyguardClockSwitchController(keyguardClockSwitch(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TvSysUIComponentImpl.this.sysuiColorExtractorProvider.get(), TvSysUIComponentImpl.this.clockManagerProvider.get(), this.keyguardSliceViewControllerProvider.get(), TvSysUIComponentImpl.this.notificationIconAreaControllerProvider.get(), TvSysUIComponentImpl.this.providesBroadcastDispatcherProvider.get(), TvSysUIComponentImpl.this.provideBatteryControllerProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TvSysUIComponentImpl.this.lockscreenSmartspaceControllerProvider.get(), TvSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider.get(), (SecureSettings) TvSysUIComponentImpl.this.secureSettingsImpl(), TvSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get(), TvSysUIComponentImpl.this.this$0.mainResources());
            }

            public final KeyguardStatusViewController getKeyguardStatusViewController() {
                DozeParameters dozeParameters = TvSysUIComponentImpl.this.dozeParametersProvider.get();
                return new KeyguardStatusViewController(this.presentation, this.keyguardSliceViewControllerProvider.get(), getKeyguardClockSwitchController(), TvSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), TvSysUIComponentImpl.this.communalStateControllerProvider.get(), TvSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TvSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider.get(), TvSysUIComponentImpl.this.screenOffAnimationControllerProvider.get());
            }

            public final KeyguardClockSwitch keyguardClockSwitch() {
                return VrMode_Factory.getKeyguardClockSwitch(this.presentation);
            }

            public final void initialize(KeyguardStatusView keyguardStatusView) {
                InstanceFactory create = InstanceFactory.create(keyguardStatusView);
                this.presentationProvider = create;
                VrMode_Factory vrMode_Factory = new VrMode_Factory(create, 1);
                this.getKeyguardClockSwitchProvider = vrMode_Factory;
                KeyboardUI_Factory keyboardUI_Factory = new KeyboardUI_Factory(vrMode_Factory, 1);
                this.getKeyguardSliceViewProvider = keyboardUI_Factory;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.keyguardSliceViewControllerProvider = DoubleCheck.provider(KeyguardSliceViewController_Factory.create(keyboardUI_Factory, tvSysUIComponentImpl.provideActivityStarterProvider, tvSysUIComponentImpl.provideConfigurationControllerProvider, tvSysUIComponentImpl.tunerServiceImplProvider, tvSysUIComponentImpl.this$0.dumpManagerProvider));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardUserSwitcherComponentFactory */
        public final class KeyguardUserSwitcherComponentFactory implements KeyguardUserSwitcherComponent.Factory {
            public KeyguardUserSwitcherComponentFactory() {
            }

            public final KeyguardUserSwitcherComponent build(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                Objects.requireNonNull(keyguardUserSwitcherView);
                return new KeyguardUserSwitcherComponentImpl(keyguardUserSwitcherView);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$KeyguardUserSwitcherComponentImpl */
        public final class KeyguardUserSwitcherComponentImpl implements KeyguardUserSwitcherComponent {
            public Provider<KeyguardUserSwitcherController> keyguardUserSwitcherControllerProvider;
            public Provider<KeyguardUserSwitcherView> keyguardUserSwitcherViewProvider;

            public KeyguardUserSwitcherComponentImpl(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                initialize(keyguardUserSwitcherView);
            }

            public final KeyguardUserSwitcherController getKeyguardUserSwitcherController() {
                return this.keyguardUserSwitcherControllerProvider.get();
            }

            public final void initialize(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                InstanceFactory create = InstanceFactory.create(keyguardUserSwitcherView);
                this.keyguardUserSwitcherViewProvider = create;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl.this$0;
                this.keyguardUserSwitcherControllerProvider = DoubleCheck.provider(KeyguardUserSwitcherController_Factory.create(create, daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.provideResourcesProvider, tvSysUIComponentImpl.providerLayoutInflaterProvider, daggerTvGlobalRootComponent.screenLifecycleProvider, tvSysUIComponentImpl.userSwitcherControllerProvider, tvSysUIComponentImpl.communalStateControllerProvider, tvSysUIComponentImpl.keyguardStateControllerImplProvider, tvSysUIComponentImpl.statusBarStateControllerImplProvider, tvSysUIComponentImpl.keyguardUpdateMonitorProvider, tvSysUIComponentImpl.dozeParametersProvider, tvSysUIComponentImpl.screenOffAnimationControllerProvider));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$MonitorComponentFactory */
        public final class MonitorComponentFactory implements MonitorComponent.Factory {
            public MonitorComponentFactory() {
            }

            public final MonitorComponent create(Set<Condition> set, Set<Monitor.Callback> set2) {
                Objects.requireNonNull(set);
                Objects.requireNonNull(set2);
                return new MonitorComponentImpl(set, set2);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$MonitorComponentImpl */
        public final class MonitorComponentImpl implements MonitorComponent {
            public final Set<Monitor.Callback> callbacks;
            public final Set<Condition> conditions;

            public MonitorComponentImpl(Set<Condition> set, Set<Monitor.Callback> set2) {
                this.conditions = set;
                this.callbacks = set2;
            }

            public final Monitor getMonitor() {
                return new Monitor(TvSysUIComponentImpl.this.provideExecutorProvider.get(), this.conditions, this.callbacks);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$NotificationShelfComponentBuilder */
        public final class NotificationShelfComponentBuilder implements NotificationShelfComponent.Builder {
            public NotificationShelf notificationShelf;

            public final NotificationShelfComponent.Builder notificationShelf(NotificationShelf notificationShelf2) {
                Objects.requireNonNull(notificationShelf2);
                this.notificationShelf = notificationShelf2;
                return this;
            }

            public NotificationShelfComponentBuilder() {
            }

            public final NotificationShelfComponent build() {
                R$color.checkBuilderRequirement(this.notificationShelf, NotificationShelf.class);
                return new NotificationShelfComponentImpl(this.notificationShelf);
            }

            /* renamed from: notificationShelf  reason: collision with other method in class */
            public final NotificationShelfComponentBuilder m279notificationShelf(NotificationShelf notificationShelf2) {
                Objects.requireNonNull(notificationShelf2);
                this.notificationShelf = notificationShelf2;
                return this;
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$NotificationShelfComponentImpl */
        public final class NotificationShelfComponentImpl implements NotificationShelfComponent {
            public Provider<ActivatableNotificationViewController> activatableNotificationViewControllerProvider;
            public Provider<ExpandableOutlineViewController> expandableOutlineViewControllerProvider;
            public Provider<ExpandableViewController> expandableViewControllerProvider;
            public Provider<NotificationTapHelper.Factory> factoryProvider;
            public Provider<NotificationShelfController> notificationShelfControllerProvider;
            public Provider<NotificationShelf> notificationShelfProvider;

            public NotificationShelfComponentImpl(NotificationShelf notificationShelf) {
                initialize(notificationShelf);
            }

            public final NotificationShelfController getNotificationShelfController() {
                return this.notificationShelfControllerProvider.get();
            }

            public final void initialize(NotificationShelf notificationShelf) {
                InstanceFactory create = InstanceFactory.create(notificationShelf);
                this.notificationShelfProvider = create;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.factoryProvider = new NotificationTapHelper_Factory_Factory(tvSysUIComponentImpl.falsingManagerProxyProvider, tvSysUIComponentImpl.this$0.provideMainDelayableExecutorProvider);
                KeyboardUI_Factory create$8 = KeyboardUI_Factory.create$8(create);
                this.expandableViewControllerProvider = create$8;
                UnpinNotifications_Factory create2 = UnpinNotifications_Factory.create(this.notificationShelfProvider, create$8);
                this.expandableOutlineViewControllerProvider = create2;
                Provider<NotificationShelf> provider = this.notificationShelfProvider;
                Provider<NotificationTapHelper.Factory> provider2 = this.factoryProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                ActivatableNotificationViewController_Factory create3 = ActivatableNotificationViewController_Factory.create(provider, provider2, create2, tvSysUIComponentImpl2.this$0.provideAccessibilityManagerProvider, tvSysUIComponentImpl2.falsingManagerProxyProvider, tvSysUIComponentImpl2.falsingCollectorImplProvider);
                this.activatableNotificationViewControllerProvider = create3;
                Provider<NotificationShelf> provider3 = this.notificationShelfProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                this.notificationShelfControllerProvider = DoubleCheck.provider(new LogBufferEulogizer_Factory(provider3, create3, tvSysUIComponentImpl3.keyguardBypassControllerProvider, tvSysUIComponentImpl3.statusBarStateControllerImplProvider, 1));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$QSFragmentComponentFactory */
        public final class QSFragmentComponentFactory implements QSFragmentComponent.Factory {
            public QSFragmentComponentFactory() {
            }

            public final QSFragmentComponent create(QSFragment qSFragment) {
                Objects.requireNonNull(qSFragment);
                return new QSFragmentComponentImpl(qSFragment);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$QSFragmentComponentImpl */
        public final class QSFragmentComponentImpl implements QSFragmentComponent {
            public Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
            public Provider<CarrierTextManager.Builder> builderProvider;
            public Provider<QSCarrierGroupController.Builder> builderProvider2;
            public Provider factoryProvider;
            public Provider<BrightnessController.Factory> factoryProvider2;
            public Provider<VariableDateViewController.Factory> factoryProvider3;
            public Provider<MultiUserSwitchController.Factory> factoryProvider4;
            public Provider<FooterActionsController> footerActionsControllerProvider;
            public Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
            public Provider<QSPanel> provideQSPanelProvider;
            public Provider<View> provideRootViewProvider;
            public Provider<Context> provideThemedContextProvider;
            public Provider<LayoutInflater> provideThemedLayoutInflaterProvider;
            public Provider<BatteryMeterView> providesBatteryMeterViewProvider;
            public Provider<OngoingPrivacyChip> providesPrivacyChipProvider;
            public Provider<QSContainerImpl> providesQSContainerImplProvider;
            public Provider<QSCustomizer> providesQSCutomizerProvider;
            public Provider<View> providesQSFgsManagerFooterViewProvider;
            public Provider<FooterActionsView> providesQSFooterActionsViewProvider;
            public Provider<QSFooter> providesQSFooterProvider;
            public Provider<QSFooterView> providesQSFooterViewProvider;
            public Provider<View> providesQSSecurityFooterViewProvider;
            public Provider<Boolean> providesQSUsingCollapsedLandscapeMediaProvider;
            public Provider<Boolean> providesQSUsingMediaPlayerProvider;
            public Provider<QuickQSPanel> providesQuickQSPanelProvider;
            public Provider<QuickStatusBarHeader> providesQuickStatusBarHeaderProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<QSAnimator> qSAnimatorProvider;
            public Provider<QSContainerImplController> qSContainerImplControllerProvider;
            public Provider<QSCustomizerController> qSCustomizerControllerProvider;
            public Provider<QSFgsManagerFooter> qSFgsManagerFooterProvider;
            public Provider<QSFooterViewController> qSFooterViewControllerProvider;
            public Provider<QSPanelController> qSPanelControllerProvider;
            public Provider qSSecurityFooterProvider;
            public Provider<QSSquishinessController> qSSquishinessControllerProvider;
            public Provider<QSFragment> qsFragmentProvider;
            public Provider<QuickQSPanelController> quickQSPanelControllerProvider;
            public Provider quickStatusBarHeaderControllerProvider;
            public Provider<TileAdapter> tileAdapterProvider;
            public Provider<TileQueryHelper> tileQueryHelperProvider;

            public QSFragmentComponentImpl(QSFragment qSFragment) {
                initialize(qSFragment);
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

            public final void initialize(QSFragment qSFragment) {
                InstanceFactory create = InstanceFactory.create(qSFragment);
                this.qsFragmentProvider = create;
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
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.qSFgsManagerFooterProvider = DoubleCheck.provider(new QSFgsManagerFooter_Factory(provider, tvSysUIComponentImpl.this$0.provideMainExecutorProvider, tvSysUIComponentImpl.provideBackgroundExecutorProvider, tvSysUIComponentImpl.fgsManagerControllerProvider, 0));
                Provider<View> provider2 = DoubleCheck.provider(new SliceBroadcastRelayHandler_Factory(this.provideThemedLayoutInflaterProvider, this.provideQSPanelProvider, 1));
                this.providesQSSecurityFooterViewProvider = provider2;
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                this.qSSecurityFooterProvider = DoubleCheck.provider(QSSecurityFooter_Factory.create(provider2, tvSysUIComponentImpl2.provideUserTrackerProvider, tvSysUIComponentImpl2.this$0.provideMainHandlerProvider, tvSysUIComponentImpl2.provideActivityStarterProvider, tvSysUIComponentImpl2.securityControllerImplProvider, tvSysUIComponentImpl2.provideDialogLaunchAnimatorProvider, tvSysUIComponentImpl2.provideBgLooperProvider));
                this.providesQSCutomizerProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.provideRootViewProvider, 4));
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl3.this$0;
                this.tileQueryHelperProvider = DoubleCheck.provider(new TileQueryHelper_Factory(daggerTvGlobalRootComponent.contextProvider, tvSysUIComponentImpl3.provideUserTrackerProvider, daggerTvGlobalRootComponent.provideMainExecutorProvider, tvSysUIComponentImpl3.provideBackgroundExecutorProvider));
                Provider<Context> provider3 = this.provideThemedContextProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                Provider<TileAdapter> provider4 = DoubleCheck.provider(new TileAdapter_Factory(provider3, tvSysUIComponentImpl4.qSTileHostProvider, tvSysUIComponentImpl4.this$0.provideUiEventLoggerProvider, 0));
                this.tileAdapterProvider = provider4;
                Provider<QSCustomizer> provider5 = this.providesQSCutomizerProvider;
                Provider<TileQueryHelper> provider6 = this.tileQueryHelperProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                Provider<QSTileHost> provider7 = tvSysUIComponentImpl5.qSTileHostProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = tvSysUIComponentImpl5.this$0;
                Provider<QSCustomizerController> provider8 = DoubleCheck.provider(QSCustomizerController_Factory.create$1(provider5, provider6, provider7, provider4, daggerTvGlobalRootComponent2.screenLifecycleProvider, tvSysUIComponentImpl5.keyguardStateControllerImplProvider, tvSysUIComponentImpl5.lightBarControllerProvider, tvSysUIComponentImpl5.provideConfigurationControllerProvider, daggerTvGlobalRootComponent2.provideUiEventLoggerProvider));
                this.qSCustomizerControllerProvider = provider8;
                Provider<Context> provider9 = TvSysUIComponentImpl.this.this$0.contextProvider;
                this.providesQSUsingMediaPlayerProvider = new MediaBrowserFactory_Factory(provider9, 3);
                Provider provider10 = DoubleCheck.provider(new QSTileRevealController_Factory_Factory(provider9, provider8));
                Provider provider11 = provider10;
                this.factoryProvider = provider10;
                TvSysUIComponentImpl tvSysUIComponentImpl6 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = tvSysUIComponentImpl6.this$0;
                BrightnessController_Factory_Factory brightnessController_Factory_Factory = r9;
                BrightnessController_Factory_Factory brightnessController_Factory_Factory2 = new BrightnessController_Factory_Factory(daggerTvGlobalRootComponent3.contextProvider, tvSysUIComponentImpl6.providesBroadcastDispatcherProvider, tvSysUIComponentImpl6.provideBgHandlerProvider);
                this.factoryProvider2 = brightnessController_Factory_Factory2;
                this.qSPanelControllerProvider = DoubleCheck.provider(QSPanelController_Factory.create(this.provideQSPanelProvider, this.qSFgsManagerFooterProvider, this.qSSecurityFooterProvider, tvSysUIComponentImpl6.tunerServiceImplProvider, tvSysUIComponentImpl6.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, tvSysUIComponentImpl6.providesQSMediaHostProvider, provider11, daggerTvGlobalRootComponent3.dumpManagerProvider, tvSysUIComponentImpl6.provideMetricsLoggerProvider, daggerTvGlobalRootComponent3.provideUiEventLoggerProvider, tvSysUIComponentImpl6.qSLoggerProvider, brightnessController_Factory_Factory, tvSysUIComponentImpl6.factoryProvider4, tvSysUIComponentImpl6.falsingManagerProxyProvider, tvSysUIComponentImpl6.featureFlagsReleaseProvider));
                DateFormatUtil_Factory dateFormatUtil_Factory = new DateFormatUtil_Factory(this.provideRootViewProvider, 2);
                this.providesQuickStatusBarHeaderProvider = dateFormatUtil_Factory;
                QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = new QSFragmentModule_ProvidesQuickQSPanelFactory(dateFormatUtil_Factory, 0);
                this.providesQuickQSPanelProvider = qSFragmentModule_ProvidesQuickQSPanelFactory;
                TvSysUIComponentImpl tvSysUIComponentImpl7 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = tvSysUIComponentImpl7.this$0;
                PrivacyLogger_Factory privacyLogger_Factory = new PrivacyLogger_Factory(daggerTvGlobalRootComponent4.contextProvider, 2);
                this.providesQSUsingCollapsedLandscapeMediaProvider = privacyLogger_Factory;
                Provider<QuickQSPanelController> provider12 = DoubleCheck.provider(QuickQSPanelController_Factory.create(qSFragmentModule_ProvidesQuickQSPanelFactory, tvSysUIComponentImpl7.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, tvSysUIComponentImpl7.providesQuickQSMediaHostProvider, privacyLogger_Factory, tvSysUIComponentImpl7.mediaFlagsProvider, tvSysUIComponentImpl7.provideMetricsLoggerProvider, daggerTvGlobalRootComponent4.provideUiEventLoggerProvider, tvSysUIComponentImpl7.qSLoggerProvider, daggerTvGlobalRootComponent4.dumpManagerProvider));
                this.quickQSPanelControllerProvider = provider12;
                Provider<QSFragment> provider13 = this.qsFragmentProvider;
                Provider<QuickQSPanel> provider14 = this.providesQuickQSPanelProvider;
                Provider<QuickStatusBarHeader> provider15 = this.providesQuickStatusBarHeaderProvider;
                Provider<QSPanelController> provider16 = this.qSPanelControllerProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl8 = TvSysUIComponentImpl.this;
                Provider<QSTileHost> provider17 = tvSysUIComponentImpl8.qSTileHostProvider;
                Provider<QSFgsManagerFooter> provider18 = this.qSFgsManagerFooterProvider;
                Provider provider19 = this.qSSecurityFooterProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = tvSysUIComponentImpl8.this$0;
                this.qSAnimatorProvider = DoubleCheck.provider(QSAnimator_Factory.create(provider13, provider14, provider15, provider16, provider12, provider17, provider18, provider19, daggerTvGlobalRootComponent5.provideMainExecutorProvider, tvSysUIComponentImpl8.tunerServiceImplProvider, daggerTvGlobalRootComponent5.qSExpansionPathInterpolatorProvider));
                this.providesQSContainerImplProvider = new DependencyProvider_ProvideHandlerFactory(this.provideRootViewProvider, 3);
                this.providesPrivacyChipProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.providesQuickStatusBarHeaderProvider, 4));
                Provider<StatusIconContainer> provider20 = DoubleCheck.provider(new ScreenLifecycle_Factory(this.providesQuickStatusBarHeaderProvider, 3));
                this.providesStatusIconContainerProvider = provider20;
                TvSysUIComponentImpl tvSysUIComponentImpl9 = TvSysUIComponentImpl.this;
                Provider<PrivacyItemController> provider21 = tvSysUIComponentImpl9.privacyItemControllerProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = tvSysUIComponentImpl9.this$0;
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(provider21, daggerTvGlobalRootComponent6.provideUiEventLoggerProvider, this.providesPrivacyChipProvider, tvSysUIComponentImpl9.privacyDialogControllerProvider, tvSysUIComponentImpl9.privacyLoggerProvider, provider20, daggerTvGlobalRootComponent6.providePermissionManagerProvider, tvSysUIComponentImpl9.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent6.provideMainExecutorProvider, tvSysUIComponentImpl9.provideActivityStarterProvider, tvSysUIComponentImpl9.appOpsControllerImplProvider, tvSysUIComponentImpl9.deviceConfigProxyProvider);
                TvSysUIComponentImpl tvSysUIComponentImpl10 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = tvSysUIComponentImpl10.this$0;
                CarrierTextManager_Builder_Factory create2 = CarrierTextManager_Builder_Factory.create(daggerTvGlobalRootComponent7.contextProvider, daggerTvGlobalRootComponent7.provideResourcesProvider, daggerTvGlobalRootComponent7.provideWifiManagerProvider, daggerTvGlobalRootComponent7.provideTelephonyManagerProvider, tvSysUIComponentImpl10.telephonyListenerManagerProvider, tvSysUIComponentImpl10.wakefulnessLifecycleProvider, daggerTvGlobalRootComponent7.provideMainExecutorProvider, tvSysUIComponentImpl10.provideBackgroundExecutorProvider, tvSysUIComponentImpl10.keyguardUpdateMonitorProvider);
                this.builderProvider = create2;
                TvSysUIComponentImpl tvSysUIComponentImpl11 = TvSysUIComponentImpl.this;
                this.builderProvider2 = QSCarrierGroupController_Builder_Factory.create(tvSysUIComponentImpl11.provideActivityStarterProvider, tvSysUIComponentImpl11.provideBgHandlerProvider, tvSysUIComponentImpl11.networkControllerImplProvider, create2, tvSysUIComponentImpl11.this$0.contextProvider, tvSysUIComponentImpl11.carrierConfigTrackerProvider, tvSysUIComponentImpl11.featureFlagsReleaseProvider, tvSysUIComponentImpl11.subscriptionManagerSlotIndexResolverProvider);
                TvSysUIComponentImpl tvSysUIComponentImpl12 = TvSysUIComponentImpl.this;
                Provider<SystemClock> provider22 = tvSysUIComponentImpl12.bindSystemClockProvider;
                Provider<BroadcastDispatcher> provider23 = tvSysUIComponentImpl12.providesBroadcastDispatcherProvider;
                this.factoryProvider3 = new VariableDateViewController_Factory_Factory(provider22, provider23, tvSysUIComponentImpl12.provideTimeTickHandlerProvider);
                VrMode_Factory vrMode_Factory = new VrMode_Factory(this.providesQuickStatusBarHeaderProvider, 4);
                this.providesBatteryMeterViewProvider = vrMode_Factory;
                Provider<ConfigurationController> provider24 = tvSysUIComponentImpl12.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider25 = tvSysUIComponentImpl12.tunerServiceImplProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = tvSysUIComponentImpl12.this$0;
                BatteryMeterViewController_Factory create3 = BatteryMeterViewController_Factory.create(vrMode_Factory, provider24, provider25, provider23, daggerTvGlobalRootComponent8.provideMainHandlerProvider, daggerTvGlobalRootComponent8.provideContentResolverProvider, tvSysUIComponentImpl12.provideBatteryControllerProvider);
                this.batteryMeterViewControllerProvider = create3;
                Provider<QuickStatusBarHeader> provider26 = this.providesQuickStatusBarHeaderProvider;
                Provider<HeaderPrivacyIconsController> provider27 = this.headerPrivacyIconsControllerProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl13 = TvSysUIComponentImpl.this;
                Provider provider28 = DoubleCheck.provider(QuickStatusBarHeaderController_Factory.create(provider26, provider27, tvSysUIComponentImpl13.statusBarIconControllerImplProvider, tvSysUIComponentImpl13.provideDemoModeControllerProvider, this.quickQSPanelControllerProvider, this.builderProvider2, tvSysUIComponentImpl13.sysuiColorExtractorProvider, tvSysUIComponentImpl13.this$0.qSExpansionPathInterpolatorProvider, tvSysUIComponentImpl13.featureFlagsReleaseProvider, this.factoryProvider3, create3, tvSysUIComponentImpl13.statusBarContentInsetsProvider));
                this.quickStatusBarHeaderControllerProvider = provider28;
                this.qSContainerImplControllerProvider = DoubleCheck.provider(new QSContainerImplController_Factory(this.providesQSContainerImplProvider, this.qSPanelControllerProvider, provider28, TvSysUIComponentImpl.this.provideConfigurationControllerProvider));
                SecureSettingsImpl_Factory secureSettingsImpl_Factory = new SecureSettingsImpl_Factory(this.provideRootViewProvider, 2);
                this.providesQSFooterViewProvider = secureSettingsImpl_Factory;
                TvSysUIComponentImpl tvSysUIComponentImpl14 = TvSysUIComponentImpl.this;
                Provider<QSFooterViewController> provider29 = DoubleCheck.provider(new QSFooterViewController_Factory(secureSettingsImpl_Factory, tvSysUIComponentImpl14.provideUserTrackerProvider, tvSysUIComponentImpl14.falsingManagerProxyProvider, tvSysUIComponentImpl14.provideActivityStarterProvider, this.qSPanelControllerProvider));
                this.qSFooterViewControllerProvider = provider29;
                this.providesQSFooterProvider = DoubleCheck.provider(new TvPipModule_ProvideTvPipBoundsStateFactory(provider29, 3));
                this.qSSquishinessControllerProvider = DoubleCheck.provider(new QSSquishinessController_Factory(this.qSAnimatorProvider, this.qSPanelControllerProvider, this.quickQSPanelControllerProvider, 0));
                Provider<View> provider30 = this.provideRootViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl15 = TvSysUIComponentImpl.this;
                Provider<FeatureFlagsRelease> provider31 = tvSysUIComponentImpl15.featureFlagsReleaseProvider;
                this.providesQSFooterActionsViewProvider = new QSFragmentModule_ProvidesQSFooterActionsViewFactory(provider30, provider31, 0);
                Provider<MultiUserSwitchController.Factory> provider32 = DoubleCheck.provider(MultiUserSwitchController_Factory_Factory.create(tvSysUIComponentImpl15.this$0.provideUserManagerProvider, tvSysUIComponentImpl15.userSwitcherControllerProvider, tvSysUIComponentImpl15.falsingManagerProxyProvider, tvSysUIComponentImpl15.userSwitchDialogControllerProvider, provider31, tvSysUIComponentImpl15.provideActivityStarterProvider));
                Provider<MultiUserSwitchController.Factory> provider33 = provider32;
                this.factoryProvider4 = provider32;
                Provider<FooterActionsView> provider34 = this.providesQSFooterActionsViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl16 = TvSysUIComponentImpl.this;
                Provider<ActivityStarter> provider35 = tvSysUIComponentImpl16.provideActivityStarterProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = tvSysUIComponentImpl16.this$0;
                this.footerActionsControllerProvider = DoubleCheck.provider(FooterActionsController_Factory.create(provider34, provider33, provider35, daggerTvGlobalRootComponent9.provideUserManagerProvider, tvSysUIComponentImpl16.provideUserTrackerProvider, tvSysUIComponentImpl16.userInfoControllerImplProvider, tvSysUIComponentImpl16.providesDeviceProvisionedControllerProvider, this.qSSecurityFooterProvider, this.qSFgsManagerFooterProvider, tvSysUIComponentImpl16.falsingManagerProxyProvider, tvSysUIComponentImpl16.provideMetricsLoggerProvider, tvSysUIComponentImpl16.globalActionsDialogLiteProvider, daggerTvGlobalRootComponent9.provideUiEventLoggerProvider, tvSysUIComponentImpl16.isPMLiteEnabledProvider, tvSysUIComponentImpl16.globalSettingsImplProvider, tvSysUIComponentImpl16.provideHandlerProvider, tvSysUIComponentImpl16.featureFlagsReleaseProvider));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$SectionHeaderControllerSubcomponentBuilder */
        public final class SectionHeaderControllerSubcomponentBuilder implements SectionHeaderControllerSubcomponent.Builder {
            public String clickIntentAction;
            public Integer headerText;
            public String nodeLabel;

            /* renamed from: clickIntentAction  reason: collision with other method in class */
            public final SectionHeaderControllerSubcomponentBuilder m280clickIntentAction(String str) {
                Objects.requireNonNull(str);
                this.clickIntentAction = str;
                return this;
            }

            /* renamed from: nodeLabel  reason: collision with other method in class */
            public final SectionHeaderControllerSubcomponentBuilder m281nodeLabel(String str) {
                Objects.requireNonNull(str);
                this.nodeLabel = str;
                return this;
            }

            public SectionHeaderControllerSubcomponentBuilder() {
            }

            public final SectionHeaderControllerSubcomponent build() {
                Class<String> cls = String.class;
                R$color.checkBuilderRequirement(this.nodeLabel, cls);
                R$color.checkBuilderRequirement(this.headerText, Integer.class);
                R$color.checkBuilderRequirement(this.clickIntentAction, cls);
                return new SectionHeaderControllerSubcomponentImpl(this.nodeLabel, this.headerText, this.clickIntentAction);
            }

            public final SectionHeaderControllerSubcomponentBuilder headerText(int i) {
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

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$SectionHeaderControllerSubcomponentImpl */
        public final class SectionHeaderControllerSubcomponentImpl implements SectionHeaderControllerSubcomponent {
            public Provider<String> clickIntentActionProvider;
            public Provider<Integer> headerTextProvider;
            public Provider<String> nodeLabelProvider;
            public Provider<SectionHeaderNodeControllerImpl> sectionHeaderNodeControllerImplProvider;

            public SectionHeaderControllerSubcomponentImpl(String str, Integer num, String str2) {
                initialize(str, num, str2);
            }

            public final SectionHeaderController getHeaderController() {
                return this.sectionHeaderNodeControllerImplProvider.get();
            }

            public final NodeController getNodeController() {
                return this.sectionHeaderNodeControllerImplProvider.get();
            }

            public final void initialize(String str, Integer num, String str2) {
                this.nodeLabelProvider = InstanceFactory.create(str);
                this.headerTextProvider = InstanceFactory.create(num);
                InstanceFactory create = InstanceFactory.create(str2);
                this.clickIntentActionProvider = create;
                Provider<String> provider = this.nodeLabelProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.sectionHeaderNodeControllerImplProvider = DoubleCheck.provider(new SectionHeaderNodeControllerImpl_Factory(provider, tvSysUIComponentImpl.providerLayoutInflaterProvider, this.headerTextProvider, tvSysUIComponentImpl.provideActivityStarterProvider, create));
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$StatusBarComponentFactory */
        public final class StatusBarComponentFactory implements StatusBarComponent.Factory {
            public StatusBarComponentFactory() {
            }

            public final StatusBarComponent create() {
                return new StatusBarComponentImpl();
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$StatusBarComponentImpl */
        public final class StatusBarComponentImpl implements StatusBarComponent {
            public Provider<AuthRippleController> authRippleControllerProvider;
            public Provider<StatusBarComponent.Startable> bindStartableProvider;
            public Provider builderProvider;
            public Provider<FlingAnimationUtils.Builder> builderProvider2;
            public Provider<CarrierTextManager.Builder> builderProvider3;
            public Provider<QSCarrierGroupController.Builder> builderProvider4;
            public Provider<AuthRippleView> getAuthRippleViewProvider;
            public Provider<BatteryMeterViewController> getBatteryMeterViewControllerProvider;
            public Provider<BatteryMeterView> getBatteryMeterViewProvider;
            public Provider<LockIconView> getLockIconViewProvider;
            public Provider<NotificationPanelView> getNotificationPanelViewProvider;
            public Provider<NotificationsQuickSettingsContainer> getNotificationsQuickSettingsContainerProvider;
            public Provider<OngoingPrivacyChip> getSplitShadeOngoingPrivacyChipProvider;
            public Provider<View> getSplitShadeStatusBarViewProvider;
            public Provider<TapAgainView> getTapAgainViewProvider;
            public Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
            public Provider<LockIconViewController> lockIconViewControllerProvider;
            public Provider<NotificationPanelViewController> notificationPanelViewControllerProvider;
            public Provider<NotificationStackScrollLayoutController> notificationStackScrollLayoutControllerProvider;
            public Provider<NotificationStackScrollLogger> notificationStackScrollLoggerProvider;
            public Provider<NotificationsQSContainerController> notificationsQSContainerControllerProvider;
            public Provider<NotificationShadeWindowView> providesNotificationShadeWindowViewProvider;
            public Provider<NotificationShelf> providesNotificationShelfProvider;
            public Provider<NotificationStackScrollLayout> providesNotificationStackScrollLayoutProvider;
            public Provider<NotificationShelfController> providesStatusBarWindowViewProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<SplitShadeHeaderController> splitShadeHeaderControllerProvider;
            public Provider<StackStateLogger> stackStateLoggerProvider;
            public Provider<StatusBarCommandQueueCallbacks> statusBarCommandQueueCallbacksProvider;
            public Provider<StatusBarHeadsUpChangeListener> statusBarHeadsUpChangeListenerProvider;
            public Provider<StatusBarInitializer> statusBarInitializerProvider;
            public Provider<TapAgainViewController> tapAgainViewControllerProvider;

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$StatusBarComponentImpl$StatusBarFragmentComponentFactory */
            public final class StatusBarFragmentComponentFactory implements StatusBarFragmentComponent.Factory {
                public StatusBarFragmentComponentFactory() {
                }

                public final StatusBarFragmentComponent create(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    Objects.requireNonNull(collapsedStatusBarFragment);
                    return new StatusBarFragmentComponentI(collapsedStatusBarFragment);
                }
            }

            /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$StatusBarComponentImpl$StatusBarFragmentComponentI */
            public final class StatusBarFragmentComponentI implements StatusBarFragmentComponent {
                public Provider<StatusBarUserSwitcherController> bindStatusBarUserSwitcherControllerProvider;
                public Provider<CollapsedStatusBarFragment> collapsedStatusBarFragmentProvider;
                public Provider<PhoneStatusBarViewController.Factory> factoryProvider;
                public Provider<HeadsUpAppearanceController> headsUpAppearanceControllerProvider;
                public Provider<LightsOutNotifController> lightsOutNotifControllerProvider;
                public Provider<BatteryMeterView> provideBatteryMeterViewProvider;
                public Provider<Clock> provideClockProvider;
                public Provider<View> provideLightsOutNotifViewProvider;
                public Provider<Optional<View>> provideOperatorFrameNameViewProvider;
                public Provider<View> provideOperatorNameViewProvider;
                public Provider<PhoneStatusBarTransitions> providePhoneStatusBarTransitionsProvider;
                public Provider<PhoneStatusBarViewController> providePhoneStatusBarViewControllerProvider;
                public Provider<PhoneStatusBarView> providePhoneStatusBarViewProvider;
                public Provider<StatusBarUserSwitcherContainer> provideStatusBarUserSwitcherContainerProvider;
                public Provider<HeadsUpStatusBarView> providesHeasdUpStatusBarViewProvider;
                public Provider<StatusBarDemoMode> statusBarDemoModeProvider;
                public Provider<StatusBarUserSwitcherControllerImpl> statusBarUserSwitcherControllerImplProvider;

                public final /* bridge */ /* synthetic */ void init() {
                    super.init();
                }

                public StatusBarFragmentComponentI(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    initialize(collapsedStatusBarFragment);
                }

                public final BatteryMeterViewController getBatteryMeterViewController() {
                    DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = TvSysUIComponentImpl.this.this$0;
                    Provider provider = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
                    return new BatteryMeterViewController(this.provideBatteryMeterViewProvider.get(), TvSysUIComponentImpl.this.provideConfigurationControllerProvider.get(), TvSysUIComponentImpl.this.tunerServiceImplProvider.get(), TvSysUIComponentImpl.this.providesBroadcastDispatcherProvider.get(), daggerTvGlobalRootComponent.mainHandler(), TvSysUIComponentImpl.this.this$0.provideContentResolverProvider.get(), TvSysUIComponentImpl.this.provideBatteryControllerProvider.get());
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

                public final void initialize(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    InstanceFactory create = InstanceFactory.create(collapsedStatusBarFragment);
                    this.collapsedStatusBarFragmentProvider = create;
                    Provider<PhoneStatusBarView> provider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(create, 4));
                    this.providePhoneStatusBarViewProvider = provider;
                    this.provideBatteryMeterViewProvider = DoubleCheck.provider(new TimeoutManager_Factory(provider, 3));
                    Provider<StatusBarUserSwitcherContainer> provider2 = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.providePhoneStatusBarViewProvider, 2));
                    this.provideStatusBarUserSwitcherContainerProvider = provider2;
                    TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                    ClockManager_Factory create$1 = ClockManager_Factory.create$1(provider2, tvSysUIComponentImpl.statusBarUserInfoTrackerProvider, tvSysUIComponentImpl.statusBarUserSwitcherFeatureControllerProvider, tvSysUIComponentImpl.userSwitchDialogControllerProvider, tvSysUIComponentImpl.featureFlagsReleaseProvider, tvSysUIComponentImpl.provideActivityStarterProvider);
                    this.statusBarUserSwitcherControllerImplProvider = create$1;
                    Provider<StatusBarUserSwitcherController> provider3 = DoubleCheck.provider(create$1);
                    this.bindStatusBarUserSwitcherControllerProvider = provider3;
                    StatusBarComponentImpl statusBarComponentImpl = StatusBarComponentImpl.this;
                    TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                    PhoneStatusBarViewController_Factory_Factory phoneStatusBarViewController_Factory_Factory = new PhoneStatusBarViewController_Factory_Factory(tvSysUIComponentImpl2.provideSysUIUnfoldComponentProvider, tvSysUIComponentImpl2.this$0.provideStatusBarScopedTransitionProvider, provider3, tvSysUIComponentImpl2.provideConfigurationControllerProvider);
                    this.factoryProvider = phoneStatusBarViewController_Factory_Factory;
                    this.providePhoneStatusBarViewControllerProvider = DoubleCheck.provider(new C1597xfe780fd7(phoneStatusBarViewController_Factory_Factory, this.providePhoneStatusBarViewProvider, statusBarComponentImpl.notificationPanelViewControllerProvider));
                    this.providesHeasdUpStatusBarViewProvider = DoubleCheck.provider(new DozeLogger_Factory(this.providePhoneStatusBarViewProvider, 5));
                    this.provideClockProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.providePhoneStatusBarViewProvider, 5));
                    Provider<Optional<View>> provider4 = DoubleCheck.provider(new FrameworkServicesModule_ProvideFaceManagerFactory(this.providePhoneStatusBarViewProvider, 2));
                    this.provideOperatorFrameNameViewProvider = provider4;
                    StatusBarComponentImpl statusBarComponentImpl2 = StatusBarComponentImpl.this;
                    TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                    this.headsUpAppearanceControllerProvider = DoubleCheck.provider(HeadsUpAppearanceController_Factory.create(tvSysUIComponentImpl3.notificationIconAreaControllerProvider, tvSysUIComponentImpl3.provideHeadsUpManagerPhoneProvider, tvSysUIComponentImpl3.statusBarStateControllerImplProvider, tvSysUIComponentImpl3.keyguardBypassControllerProvider, tvSysUIComponentImpl3.notificationWakeUpCoordinatorProvider, tvSysUIComponentImpl3.darkIconDispatcherImplProvider, tvSysUIComponentImpl3.keyguardStateControllerImplProvider, tvSysUIComponentImpl3.provideCommandQueueProvider, statusBarComponentImpl2.notificationStackScrollLayoutControllerProvider, statusBarComponentImpl2.notificationPanelViewControllerProvider, this.providesHeasdUpStatusBarViewProvider, this.provideClockProvider, provider4));
                    Provider<View> provider5 = DoubleCheck.provider(new MediaControllerFactory_Factory(this.providePhoneStatusBarViewProvider, 5));
                    this.provideLightsOutNotifViewProvider = provider5;
                    TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                    this.lightsOutNotifControllerProvider = DoubleCheck.provider(new LightsOutNotifController_Factory(provider5, tvSysUIComponentImpl4.this$0.provideWindowManagerProvider, tvSysUIComponentImpl4.notifLiveDataStoreImplProvider, tvSysUIComponentImpl4.provideCommandQueueProvider));
                    this.provideOperatorNameViewProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(this.providePhoneStatusBarViewProvider, 4));
                    Provider<PhoneStatusBarTransitions> provider6 = DoubleCheck.provider(new ShadeEventCoordinator_Factory(this.providePhoneStatusBarViewProvider, TvSysUIComponentImpl.this.statusBarWindowControllerProvider, 1));
                    this.providePhoneStatusBarTransitionsProvider = provider6;
                    Provider<Clock> provider7 = this.provideClockProvider;
                    Provider<View> provider8 = this.provideOperatorNameViewProvider;
                    TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                    this.statusBarDemoModeProvider = DoubleCheck.provider(StatusBarDemoMode_Factory.create$1(provider7, provider8, tvSysUIComponentImpl5.provideDemoModeControllerProvider, provider6, tvSysUIComponentImpl5.navigationBarControllerProvider, tvSysUIComponentImpl5.this$0.provideDisplayIdProvider));
                }
            }

            public StatusBarComponentImpl() {
                initialize();
            }

            public final CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger() {
                return new CollapsedStatusBarFragmentLogger(TvSysUIComponentImpl.this.provideCollapsedSbFragmentLogBufferProvider.get(), TvSysUIComponentImpl.this.disableFlagsLoggerProvider.get());
            }

            public final CollapsedStatusBarFragment createCollapsedStatusBarFragment() {
                return StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory.createCollapsedStatusBarFragment(new StatusBarFragmentComponentFactory(), TvSysUIComponentImpl.this.provideOngoingCallControllerProvider.get(), TvSysUIComponentImpl.this.systemStatusAnimationSchedulerProvider.get(), TvSysUIComponentImpl.this.statusBarLocationPublisherProvider.get(), TvSysUIComponentImpl.this.notificationIconAreaControllerProvider.get(), TvSysUIComponentImpl.this.panelExpansionStateManagerProvider.get(), TvSysUIComponentImpl.this.featureFlagsReleaseProvider.get(), TvSysUIComponentImpl.this.statusBarIconControllerImplProvider.get(), TvSysUIComponentImpl.this.statusBarHideIconsForBouncerManagerProvider.get(), TvSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), this.notificationPanelViewControllerProvider.get(), TvSysUIComponentImpl.this.networkControllerImplProvider.get(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TvSysUIComponentImpl.this.provideCommandQueueProvider.get(), collapsedStatusBarFragmentLogger(), operatorNameViewControllerFactory());
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
                return new NotificationShadeWindowViewController(TvSysUIComponentImpl.this.lockscreenShadeTransitionControllerProvider.get(), (FalsingCollector) TvSysUIComponentImpl.this.falsingCollectorImplProvider.get(), TvSysUIComponentImpl.this.tunerServiceImplProvider.get(), TvSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), TvSysUIComponentImpl.this.dockManagerImplProvider.get(), TvSysUIComponentImpl.this.notificationShadeDepthControllerProvider.get(), this.providesNotificationShadeWindowViewProvider.get(), this.notificationPanelViewControllerProvider.get(), TvSysUIComponentImpl.this.panelExpansionStateManagerProvider.get(), this.notificationStackScrollLayoutControllerProvider.get(), TvSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider.get(), TvSysUIComponentImpl.this.statusBarWindowStateControllerProvider.get(), this.lockIconViewControllerProvider.get(), TvSysUIComponentImpl.this.provideLowLightClockControllerProvider.get());
            }

            public final NotificationShelfController getNotificationShelfController() {
                return this.providesStatusBarWindowViewProvider.get();
            }

            public final NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
                return this.notificationStackScrollLayoutControllerProvider.get();
            }

            public final SplitShadeHeaderController getSplitShadeHeaderController() {
                return this.splitShadeHeaderControllerProvider.get();
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

            public final void initialize() {
                Provider<NotificationShadeWindowView> provider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(TvSysUIComponentImpl.this.providerLayoutInflaterProvider, 3));
                this.providesNotificationShadeWindowViewProvider = provider;
                Provider<NotificationStackScrollLayout> provider2 = DoubleCheck.provider(new LiftToActivateListener_Factory(provider, 5));
                this.providesNotificationStackScrollLayoutProvider = provider2;
                Provider<NotificationShelf> provider3 = DoubleCheck.provider(new DreamOverlayRegistrant_Factory(TvSysUIComponentImpl.this.providerLayoutInflaterProvider, provider2, 1));
                this.providesNotificationShelfProvider = provider3;
                this.providesStatusBarWindowViewProvider = DoubleCheck.provider(new StatusBarViewModule_ProvidesStatusBarWindowViewFactory(TvSysUIComponentImpl.this.notificationShelfComponentBuilderProvider, provider3));
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl.this$0;
                Provider<Resources> provider4 = daggerTvGlobalRootComponent.provideResourcesProvider;
                Provider<ViewConfiguration> provider5 = daggerTvGlobalRootComponent.provideViewConfigurationProvider;
                Provider<FalsingManagerProxy> provider6 = tvSysUIComponentImpl.falsingManagerProxyProvider;
                NotificationSwipeHelper_Builder_Factory notificationSwipeHelper_Builder_Factory = new NotificationSwipeHelper_Builder_Factory(provider4, provider5, provider6, tvSysUIComponentImpl.featureFlagsReleaseProvider);
                this.builderProvider = notificationSwipeHelper_Builder_Factory;
                Provider<LogBuffer> provider7 = tvSysUIComponentImpl.provideNotificationHeadsUpLogBufferProvider;
                ScreenLifecycle_Factory screenLifecycle_Factory = new ScreenLifecycle_Factory(provider7, 4);
                this.stackStateLoggerProvider = screenLifecycle_Factory;
                TvPipModule_ProvideTvPipBoundsStateFactory tvPipModule_ProvideTvPipBoundsStateFactory = new TvPipModule_ProvideTvPipBoundsStateFactory(provider7, 4);
                this.notificationStackScrollLoggerProvider = tvPipModule_ProvideTvPipBoundsStateFactory;
                ScreenLifecycle_Factory screenLifecycle_Factory2 = screenLifecycle_Factory;
                NotificationSwipeHelper_Builder_Factory notificationSwipeHelper_Builder_Factory2 = notificationSwipeHelper_Builder_Factory;
                Provider<FalsingManagerProxy> provider8 = provider6;
                Provider<Resources> provider9 = provider4;
                this.notificationStackScrollLayoutControllerProvider = DoubleCheck.provider(NotificationStackScrollLayoutController_Factory.create(tvSysUIComponentImpl.provideAllowNotificationLongPressProvider, tvSysUIComponentImpl.provideNotificationGutsManagerProvider, tvSysUIComponentImpl.provideNotificationVisibilityProvider, tvSysUIComponentImpl.provideHeadsUpManagerPhoneProvider, tvSysUIComponentImpl.notificationRoundnessManagerProvider, tvSysUIComponentImpl.tunerServiceImplProvider, tvSysUIComponentImpl.providesDeviceProvisionedControllerProvider, tvSysUIComponentImpl.dynamicPrivacyControllerProvider, tvSysUIComponentImpl.provideConfigurationControllerProvider, tvSysUIComponentImpl.statusBarStateControllerImplProvider, tvSysUIComponentImpl.keyguardMediaControllerProvider, tvSysUIComponentImpl.keyguardBypassControllerProvider, tvSysUIComponentImpl.zenModeControllerImplProvider, tvSysUIComponentImpl.sysuiColorExtractorProvider, tvSysUIComponentImpl.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl.provideMetricsLoggerProvider, tvSysUIComponentImpl.falsingCollectorImplProvider, provider8, provider9, notificationSwipeHelper_Builder_Factory2, tvSysUIComponentImpl.provideStatusBarProvider, tvSysUIComponentImpl.scrimControllerProvider, tvSysUIComponentImpl.notificationGroupManagerLegacyProvider, tvSysUIComponentImpl.provideGroupExpansionManagerProvider, tvSysUIComponentImpl.providesSilentHeaderControllerProvider, tvSysUIComponentImpl.notifPipelineFlagsProvider, tvSysUIComponentImpl.notifPipelineProvider, tvSysUIComponentImpl.notifCollectionProvider, tvSysUIComponentImpl.provideNotificationEntryManagerProvider, tvSysUIComponentImpl.lockscreenShadeTransitionControllerProvider, daggerTvGlobalRootComponent.provideIStatusBarServiceProvider, daggerTvGlobalRootComponent.provideUiEventLoggerProvider, tvSysUIComponentImpl.foregroundServiceDismissalFeatureControllerProvider, tvSysUIComponentImpl.foregroundServiceSectionControllerProvider, tvSysUIComponentImpl.providerLayoutInflaterProvider, tvSysUIComponentImpl.provideNotificationRemoteInputManagerProvider, tvSysUIComponentImpl.provideVisualStabilityManagerProvider, tvSysUIComponentImpl.shadeControllerImplProvider, daggerTvGlobalRootComponent.provideInteractionJankMonitorProvider, screenLifecycle_Factory2, tvPipModule_ProvideTvPipBoundsStateFactory));
                this.getNotificationPanelViewProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.providesNotificationShadeWindowViewProvider, 4));
                this.builderProvider2 = new FlingAnimationUtils_Builder_Factory(TvSysUIComponentImpl.this.this$0.provideDisplayMetricsProvider);
                Provider<NotificationsQuickSettingsContainer> provider10 = DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(this.providesNotificationShadeWindowViewProvider, 2));
                this.getNotificationsQuickSettingsContainerProvider = provider10;
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                this.notificationsQSContainerControllerProvider = new MediaSessionBasedFilter_Factory(provider10, tvSysUIComponentImpl2.navigationModeControllerProvider, tvSysUIComponentImpl2.overviewProxyServiceProvider, tvSysUIComponentImpl2.featureFlagsReleaseProvider, 1);
                this.getLockIconViewProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.providesNotificationShadeWindowViewProvider, 5));
                Provider<AuthRippleView> provider11 = DoubleCheck.provider(new QSFragmentModule_ProvidesQuickQSPanelFactory(this.providesNotificationShadeWindowViewProvider, 5));
                this.getAuthRippleViewProvider = provider11;
                TvSysUIComponentImpl tvSysUIComponentImpl3 = TvSysUIComponentImpl.this;
                Provider<AuthRippleController> provider12 = DoubleCheck.provider(AuthRippleController_Factory.create(tvSysUIComponentImpl3.provideStatusBarProvider, tvSysUIComponentImpl3.this$0.contextProvider, tvSysUIComponentImpl3.authControllerProvider, tvSysUIComponentImpl3.provideConfigurationControllerProvider, tvSysUIComponentImpl3.keyguardUpdateMonitorProvider, tvSysUIComponentImpl3.keyguardStateControllerImplProvider, tvSysUIComponentImpl3.wakefulnessLifecycleProvider, tvSysUIComponentImpl3.commandRegistryProvider, tvSysUIComponentImpl3.notificationShadeWindowControllerImplProvider, tvSysUIComponentImpl3.keyguardBypassControllerProvider, tvSysUIComponentImpl3.biometricUnlockControllerProvider, tvSysUIComponentImpl3.udfpsControllerProvider, tvSysUIComponentImpl3.statusBarStateControllerImplProvider, provider11));
                this.authRippleControllerProvider = provider12;
                Provider<LockIconView> provider13 = this.getLockIconViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl4 = TvSysUIComponentImpl.this;
                Provider<StatusBarStateControllerImpl> provider14 = tvSysUIComponentImpl4.statusBarStateControllerImplProvider;
                Provider<KeyguardUpdateMonitor> provider15 = tvSysUIComponentImpl4.keyguardUpdateMonitorProvider;
                Provider<StatusBarKeyguardViewManager> provider16 = tvSysUIComponentImpl4.statusBarKeyguardViewManagerProvider;
                Provider<KeyguardStateControllerImpl> provider17 = tvSysUIComponentImpl4.keyguardStateControllerImplProvider;
                Provider<FalsingManagerProxy> provider18 = tvSysUIComponentImpl4.falsingManagerProxyProvider;
                Provider<AuthController> provider19 = tvSysUIComponentImpl4.authControllerProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = tvSysUIComponentImpl4.this$0;
                this.lockIconViewControllerProvider = DoubleCheck.provider(LockIconViewController_Factory.create(provider13, provider14, provider15, provider16, provider17, provider18, provider19, daggerTvGlobalRootComponent2.dumpManagerProvider, daggerTvGlobalRootComponent2.provideAccessibilityManagerProvider, tvSysUIComponentImpl4.provideConfigurationControllerProvider, daggerTvGlobalRootComponent2.provideMainDelayableExecutorProvider, tvSysUIComponentImpl4.vibratorHelperProvider, provider12, daggerTvGlobalRootComponent2.provideResourcesProvider));
                Provider<TapAgainView> provider20 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.getNotificationPanelViewProvider, 4));
                this.getTapAgainViewProvider = provider20;
                TvSysUIComponentImpl tvSysUIComponentImpl5 = TvSysUIComponentImpl.this;
                this.tapAgainViewControllerProvider = DoubleCheck.provider(new AssistLogger_Factory(provider20, tvSysUIComponentImpl5.this$0.provideMainDelayableExecutorProvider, tvSysUIComponentImpl5.provideConfigurationControllerProvider, FalsingModule_ProvidesDoubleTapTimeoutMsFactory.InstanceHolder.INSTANCE, 1));
                Provider<View> provider21 = DoubleCheck.provider(new DependencyProvider_ProvidesViewMediatorCallbackFactory(this.providesNotificationShadeWindowViewProvider, TvSysUIComponentImpl.this.featureFlagsReleaseProvider, 2));
                this.getSplitShadeStatusBarViewProvider = provider21;
                this.getSplitShadeOngoingPrivacyChipProvider = DoubleCheck.provider(new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(provider21, 2));
                Provider<StatusIconContainer> provider22 = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.getSplitShadeStatusBarViewProvider, 3));
                this.providesStatusIconContainerProvider = provider22;
                TvSysUIComponentImpl tvSysUIComponentImpl6 = TvSysUIComponentImpl.this;
                Provider<PrivacyItemController> provider23 = tvSysUIComponentImpl6.privacyItemControllerProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = tvSysUIComponentImpl6.this$0;
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(provider23, daggerTvGlobalRootComponent3.provideUiEventLoggerProvider, this.getSplitShadeOngoingPrivacyChipProvider, tvSysUIComponentImpl6.privacyDialogControllerProvider, tvSysUIComponentImpl6.privacyLoggerProvider, provider22, daggerTvGlobalRootComponent3.providePermissionManagerProvider, tvSysUIComponentImpl6.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent3.provideMainExecutorProvider, tvSysUIComponentImpl6.provideActivityStarterProvider, tvSysUIComponentImpl6.appOpsControllerImplProvider, tvSysUIComponentImpl6.deviceConfigProxyProvider);
                TvSysUIComponentImpl tvSysUIComponentImpl7 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = tvSysUIComponentImpl7.this$0;
                CarrierTextManager_Builder_Factory create = CarrierTextManager_Builder_Factory.create(daggerTvGlobalRootComponent4.contextProvider, daggerTvGlobalRootComponent4.provideResourcesProvider, daggerTvGlobalRootComponent4.provideWifiManagerProvider, daggerTvGlobalRootComponent4.provideTelephonyManagerProvider, tvSysUIComponentImpl7.telephonyListenerManagerProvider, tvSysUIComponentImpl7.wakefulnessLifecycleProvider, daggerTvGlobalRootComponent4.provideMainExecutorProvider, tvSysUIComponentImpl7.provideBackgroundExecutorProvider, tvSysUIComponentImpl7.keyguardUpdateMonitorProvider);
                this.builderProvider3 = create;
                TvSysUIComponentImpl tvSysUIComponentImpl8 = TvSysUIComponentImpl.this;
                this.builderProvider4 = QSCarrierGroupController_Builder_Factory.create(tvSysUIComponentImpl8.provideActivityStarterProvider, tvSysUIComponentImpl8.provideBgHandlerProvider, tvSysUIComponentImpl8.networkControllerImplProvider, create, tvSysUIComponentImpl8.this$0.contextProvider, tvSysUIComponentImpl8.carrierConfigTrackerProvider, tvSysUIComponentImpl8.featureFlagsReleaseProvider, tvSysUIComponentImpl8.subscriptionManagerSlotIndexResolverProvider);
                Provider<BatteryMeterView> provider24 = DoubleCheck.provider(new ScreenLifecycle_Factory(this.getSplitShadeStatusBarViewProvider, 5));
                this.getBatteryMeterViewProvider = provider24;
                TvSysUIComponentImpl tvSysUIComponentImpl9 = TvSysUIComponentImpl.this;
                Provider<ConfigurationController> provider25 = tvSysUIComponentImpl9.provideConfigurationControllerProvider;
                Provider<TunerServiceImpl> provider26 = tvSysUIComponentImpl9.tunerServiceImplProvider;
                Provider<BroadcastDispatcher> provider27 = tvSysUIComponentImpl9.providesBroadcastDispatcherProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = tvSysUIComponentImpl9.this$0;
                Provider<BatteryMeterViewController> provider28 = DoubleCheck.provider(StatusBarViewModule_GetBatteryMeterViewControllerFactory.create(provider24, provider25, provider26, provider27, daggerTvGlobalRootComponent5.provideMainHandlerProvider, daggerTvGlobalRootComponent5.provideContentResolverProvider, tvSysUIComponentImpl9.provideBatteryControllerProvider));
                this.getBatteryMeterViewControllerProvider = provider28;
                Provider<View> provider29 = this.getSplitShadeStatusBarViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl10 = TvSysUIComponentImpl.this;
                Provider<SplitShadeHeaderController> provider30 = DoubleCheck.provider(SplitShadeHeaderController_Factory.create(provider29, tvSysUIComponentImpl10.statusBarIconControllerImplProvider, this.headerPrivacyIconsControllerProvider, this.builderProvider4, tvSysUIComponentImpl10.featureFlagsReleaseProvider, provider28, tvSysUIComponentImpl10.this$0.dumpManagerProvider));
                Provider<SplitShadeHeaderController> provider31 = provider30;
                this.splitShadeHeaderControllerProvider = provider30;
                Provider<NotificationPanelView> provider32 = this.getNotificationPanelViewProvider;
                TvSysUIComponentImpl tvSysUIComponentImpl11 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = tvSysUIComponentImpl11.this$0;
                Provider<NotificationPanelView> provider33 = provider32;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = daggerTvGlobalRootComponent6;
                Provider<NotificationPanelViewController> provider34 = DoubleCheck.provider(NotificationPanelViewController_Factory.create(provider33, daggerTvGlobalRootComponent6.provideResourcesProvider, daggerTvGlobalRootComponent6.provideMainHandlerProvider, tvSysUIComponentImpl11.providerLayoutInflaterProvider, tvSysUIComponentImpl11.featureFlagsReleaseProvider, tvSysUIComponentImpl11.notificationWakeUpCoordinatorProvider, tvSysUIComponentImpl11.pulseExpansionHandlerProvider, tvSysUIComponentImpl11.dynamicPrivacyControllerProvider, tvSysUIComponentImpl11.keyguardBypassControllerProvider, tvSysUIComponentImpl11.falsingManagerProxyProvider, tvSysUIComponentImpl11.falsingCollectorImplProvider, tvSysUIComponentImpl11.notificationLockscreenUserManagerImplProvider, tvSysUIComponentImpl11.provideNotificationEntryManagerProvider, tvSysUIComponentImpl11.communalStateControllerProvider, tvSysUIComponentImpl11.keyguardStateControllerImplProvider, tvSysUIComponentImpl11.statusBarStateControllerImplProvider, tvSysUIComponentImpl11.statusBarWindowStateControllerProvider, tvSysUIComponentImpl11.notificationShadeWindowControllerImplProvider, tvSysUIComponentImpl11.dozeLogProvider, tvSysUIComponentImpl11.dozeParametersProvider, tvSysUIComponentImpl11.provideCommandQueueProvider, tvSysUIComponentImpl11.vibratorHelperProvider, daggerTvGlobalRootComponent7.provideLatencyTrackerProvider, daggerTvGlobalRootComponent7.providePowerManagerProvider, daggerTvGlobalRootComponent7.provideAccessibilityManagerProvider, daggerTvGlobalRootComponent7.provideDisplayIdProvider, tvSysUIComponentImpl11.keyguardUpdateMonitorProvider, tvSysUIComponentImpl11.communalSourceMonitorProvider, tvSysUIComponentImpl11.provideMetricsLoggerProvider, daggerTvGlobalRootComponent7.provideActivityManagerProvider, tvSysUIComponentImpl11.provideConfigurationControllerProvider, this.builderProvider2, tvSysUIComponentImpl11.statusBarTouchableRegionManagerProvider, tvSysUIComponentImpl11.conversationNotificationManagerProvider, tvSysUIComponentImpl11.mediaHierarchyManagerProvider, tvSysUIComponentImpl11.statusBarKeyguardViewManagerProvider, this.notificationsQSContainerControllerProvider, this.notificationStackScrollLayoutControllerProvider, tvSysUIComponentImpl11.keyguardStatusViewComponentFactoryProvider, tvSysUIComponentImpl11.keyguardQsUserSwitchComponentFactoryProvider, tvSysUIComponentImpl11.keyguardUserSwitcherComponentFactoryProvider, tvSysUIComponentImpl11.keyguardStatusBarViewComponentFactoryProvider, tvSysUIComponentImpl11.communalViewComponentFactoryProvider, tvSysUIComponentImpl11.lockscreenShadeTransitionControllerProvider, tvSysUIComponentImpl11.notificationGroupManagerLegacyProvider, tvSysUIComponentImpl11.notificationIconAreaControllerProvider, tvSysUIComponentImpl11.authControllerProvider, tvSysUIComponentImpl11.scrimControllerProvider, daggerTvGlobalRootComponent7.provideUserManagerProvider, tvSysUIComponentImpl11.mediaDataManagerProvider, tvSysUIComponentImpl11.notificationShadeDepthControllerProvider, tvSysUIComponentImpl11.ambientStateProvider, this.lockIconViewControllerProvider, tvSysUIComponentImpl11.keyguardMediaControllerProvider, tvSysUIComponentImpl11.privacyDotViewControllerProvider, this.tapAgainViewControllerProvider, tvSysUIComponentImpl11.navigationModeControllerProvider, tvSysUIComponentImpl11.fragmentServiceProvider, daggerTvGlobalRootComponent7.provideContentResolverProvider, tvSysUIComponentImpl11.quickAccessWalletControllerProvider, tvSysUIComponentImpl11.qRCodeScannerControllerProvider, tvSysUIComponentImpl11.recordingControllerProvider, daggerTvGlobalRootComponent7.provideMainExecutorProvider, tvSysUIComponentImpl11.secureSettingsImplProvider, provider31, tvSysUIComponentImpl11.screenOffAnimationControllerProvider, tvSysUIComponentImpl11.lockscreenGestureLoggerProvider, tvSysUIComponentImpl11.panelExpansionStateManagerProvider, tvSysUIComponentImpl11.provideNotificationRemoteInputManagerProvider, tvSysUIComponentImpl11.provideSysUIUnfoldComponentProvider, tvSysUIComponentImpl11.controlsComponentProvider, daggerTvGlobalRootComponent7.provideInteractionJankMonitorProvider, tvSysUIComponentImpl11.qsFrameTranslateImplProvider, tvSysUIComponentImpl11.provideSysUiStateProvider, tvSysUIComponentImpl11.keyguardUnlockAnimationControllerProvider));
                this.notificationPanelViewControllerProvider = provider34;
                TvSysUIComponentImpl tvSysUIComponentImpl12 = TvSysUIComponentImpl.this;
                this.statusBarHeadsUpChangeListenerProvider = DoubleCheck.provider(MediaHierarchyManager_Factory.create$1(tvSysUIComponentImpl12.notificationShadeWindowControllerImplProvider, tvSysUIComponentImpl12.statusBarWindowControllerProvider, provider34, tvSysUIComponentImpl12.keyguardBypassControllerProvider, tvSysUIComponentImpl12.provideHeadsUpManagerPhoneProvider, tvSysUIComponentImpl12.statusBarStateControllerImplProvider, tvSysUIComponentImpl12.provideNotificationRemoteInputManagerProvider, tvSysUIComponentImpl12.provideNotificationsControllerProvider, tvSysUIComponentImpl12.dozeServiceHostProvider, tvSysUIComponentImpl12.dozeScrimControllerProvider));
                TvSysUIComponentImpl tvSysUIComponentImpl13 = TvSysUIComponentImpl.this;
                Provider<StatusBar> provider35 = tvSysUIComponentImpl13.provideStatusBarProvider;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = tvSysUIComponentImpl13.this$0;
                Provider<StatusBar> provider36 = provider35;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = daggerTvGlobalRootComponent8;
                this.statusBarCommandQueueCallbacksProvider = DoubleCheck.provider(StatusBarCommandQueueCallbacks_Factory.create(provider36, daggerTvGlobalRootComponent8.contextProvider, daggerTvGlobalRootComponent8.provideResourcesProvider, tvSysUIComponentImpl13.shadeControllerImplProvider, tvSysUIComponentImpl13.provideCommandQueueProvider, this.notificationPanelViewControllerProvider, tvSysUIComponentImpl13.setLegacySplitScreenProvider, tvSysUIComponentImpl13.remoteInputQuickSettingsDisablerProvider, tvSysUIComponentImpl13.provideMetricsLoggerProvider, tvSysUIComponentImpl13.keyguardUpdateMonitorProvider, tvSysUIComponentImpl13.keyguardStateControllerImplProvider, tvSysUIComponentImpl13.provideHeadsUpManagerPhoneProvider, tvSysUIComponentImpl13.wakefulnessLifecycleProvider, tvSysUIComponentImpl13.providesDeviceProvisionedControllerProvider, tvSysUIComponentImpl13.statusBarKeyguardViewManagerProvider, tvSysUIComponentImpl13.assistManagerProvider, tvSysUIComponentImpl13.dozeServiceHostProvider, tvSysUIComponentImpl13.statusBarStateControllerImplProvider, this.providesNotificationShadeWindowViewProvider, this.notificationStackScrollLayoutControllerProvider, tvSysUIComponentImpl13.statusBarHideIconsForBouncerManagerProvider, daggerTvGlobalRootComponent9.providePowerManagerProvider, tvSysUIComponentImpl13.vibratorHelperProvider, daggerTvGlobalRootComponent9.provideOptionalVibratorProvider, tvSysUIComponentImpl13.lightBarControllerProvider, tvSysUIComponentImpl13.disableFlagsLoggerProvider, daggerTvGlobalRootComponent9.provideDisplayIdProvider));
                this.statusBarInitializerProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(TvSysUIComponentImpl.this.statusBarWindowControllerProvider, 0));
                this.bindStartableProvider = DoubleCheck.provider(new StatusBarNotifPanelEventSourceModule_BindStartableFactory(this.notificationPanelViewControllerProvider));
            }

            public final OperatorNameViewController.Factory operatorNameViewControllerFactory() {
                return new OperatorNameViewController.Factory(TvSysUIComponentImpl.this.darkIconDispatcherImplProvider.get(), TvSysUIComponentImpl.this.networkControllerImplProvider.get(), TvSysUIComponentImpl.this.tunerServiceImplProvider.get(), TvSysUIComponentImpl.this.this$0.provideTelephonyManagerProvider.get(), TvSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get());
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$SysUIUnfoldComponentFactory */
        public final class SysUIUnfoldComponentFactory implements SysUIUnfoldComponent.Factory {
            public SysUIUnfoldComponentFactory() {
            }

            public final SysUIUnfoldComponent create(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                Objects.requireNonNull(unfoldTransitionProgressProvider);
                Objects.requireNonNull(naturalRotationUnfoldProgressProvider);
                Objects.requireNonNull(scopedUnfoldTransitionProgressProvider);
                return new SysUIUnfoldComponentImpl(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider);
            }
        }

        /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvSysUIComponentImpl$SysUIUnfoldComponentImpl */
        public final class SysUIUnfoldComponentImpl implements SysUIUnfoldComponent {
            public Provider<FoldAodAnimationController> foldAodAnimationControllerProvider;
            public Provider<KeyguardUnfoldTransition> keyguardUnfoldTransitionProvider;
            public Provider<NotificationPanelUnfoldAnimationController> notificationPanelUnfoldAnimationControllerProvider;
            public Provider<UnfoldTransitionProgressProvider> p1Provider;
            public Provider<NaturalRotationUnfoldProgressProvider> p2Provider;
            public Provider<ScopedUnfoldTransitionProgressProvider> p3Provider;
            public Provider<StatusBarMoveFromCenterAnimationController> statusBarMoveFromCenterAnimationControllerProvider;
            public Provider<UnfoldLightRevealOverlayAnimation> unfoldLightRevealOverlayAnimationProvider;
            public Provider<UnfoldTransitionWallpaperController> unfoldTransitionWallpaperControllerProvider;

            public SysUIUnfoldComponentImpl(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                initialize(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider);
            }

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

            public final void initialize(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                InstanceFactory create = InstanceFactory.create(naturalRotationUnfoldProgressProvider);
                this.p2Provider = create;
                this.keyguardUnfoldTransitionProvider = DoubleCheck.provider(new KeyguardUnfoldTransition_Factory(TvSysUIComponentImpl.this.this$0.contextProvider, create, 0));
                InstanceFactory create2 = InstanceFactory.create(scopedUnfoldTransitionProgressProvider);
                this.p3Provider = create2;
                this.statusBarMoveFromCenterAnimationControllerProvider = DoubleCheck.provider(new QSFragmentModule_ProvidesQSFooterActionsViewFactory(create2, TvSysUIComponentImpl.this.this$0.provideWindowManagerProvider, 2));
                this.notificationPanelUnfoldAnimationControllerProvider = DoubleCheck.provider(new NotificationPanelUnfoldAnimationController_Factory((Provider) TvSysUIComponentImpl.this.this$0.contextProvider, (Provider) this.p2Provider));
                TvSysUIComponentImpl tvSysUIComponentImpl = TvSysUIComponentImpl.this;
                this.foldAodAnimationControllerProvider = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(tvSysUIComponentImpl.this$0.provideMainHandlerProvider, tvSysUIComponentImpl.wakefulnessLifecycleProvider, tvSysUIComponentImpl.globalSettingsImplProvider, 2));
                InstanceFactory create3 = InstanceFactory.create(unfoldTransitionProgressProvider);
                this.p1Provider = create3;
                this.unfoldTransitionWallpaperControllerProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(create3, TvSysUIComponentImpl.this.wallpaperControllerProvider, 2));
                TvSysUIComponentImpl tvSysUIComponentImpl2 = TvSysUIComponentImpl.this;
                DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = tvSysUIComponentImpl2.this$0;
                this.unfoldLightRevealOverlayAnimationProvider = DoubleCheck.provider(UnfoldLightRevealOverlayAnimation_Factory.create(daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.provideDeviceStateManagerProvider, daggerTvGlobalRootComponent.provideDisplayManagerProvider, this.p1Provider, tvSysUIComponentImpl2.setDisplayAreaHelperProvider, daggerTvGlobalRootComponent.provideMainExecutorProvider, tvSysUIComponentImpl2.provideUiBackgroundExecutorProvider, daggerTvGlobalRootComponent.provideIWindowManagerProvider));
            }
        }

        public final /* bridge */ /* synthetic */ void init() {
            super.init();
        }

        public final void initialize7(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            this.builderProvider9 = DoubleCheck.provider(new TileServiceRequestController_Builder_Factory(this.provideCommandQueueProvider, this.commandRegistryProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            Provider<Context> provider = daggerTvGlobalRootComponent.contextProvider;
            PackageManagerAdapter_Factory packageManagerAdapter_Factory = new PackageManagerAdapter_Factory(provider, 0);
            this.packageManagerAdapterProvider = packageManagerAdapter_Factory;
            C2507TileLifecycleManager_Factory tileLifecycleManager_Factory = new C2507TileLifecycleManager_Factory(daggerTvGlobalRootComponent.provideMainHandlerProvider, provider, this.tileServicesProvider, packageManagerAdapter_Factory, this.providesBroadcastDispatcherProvider);
            this.tileLifecycleManagerProvider = tileLifecycleManager_Factory;
            InstanceFactory create = InstanceFactory.create(new TileLifecycleManager_Factory_Impl(tileLifecycleManager_Factory));
            InstanceFactory instanceFactory = create;
            this.factoryProvider8 = create;
            Provider<QSTileHost> provider2 = this.qSTileHostProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            DelegateFactory.setDelegate(provider2, DoubleCheck.provider(QSTileHost_Factory.create(daggerTvGlobalRootComponent2.contextProvider, this.statusBarIconControllerImplProvider, this.qSFactoryImplProvider, daggerTvGlobalRootComponent2.provideMainHandlerProvider, this.provideBgLooperProvider, daggerTvGlobalRootComponent2.providesPluginManagerProvider, this.tunerServiceImplProvider, this.provideAutoTileManagerProvider, daggerTvGlobalRootComponent2.dumpManagerProvider, this.providesBroadcastDispatcherProvider, this.optionalOfStatusBarProvider, this.qSLoggerProvider, daggerTvGlobalRootComponent2.provideUiEventLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, this.customTileStatePersisterProvider, this.builderProvider9, instanceFactory)));
            this.providesQSMediaHostProvider = DoubleCheck.provider(new MediaModule_ProvidesQSMediaHostFactory(this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesQuickQSMediaHostProvider = DoubleCheck.provider(new MediaModule_ProvidesQuickQSMediaHostFactory(this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.provideQSFragmentDisableLogBufferProvider = DoubleCheck.provider(new VrMode_Factory(this.logBufferFactoryProvider, 3));
            this.disableFlagsLoggerProvider = DoubleCheck.provider(DisableFlagsLogger_Factory.InstanceHolder.INSTANCE);
            this.notificationShelfComponentBuilderProvider = new Provider<NotificationShelfComponent.Builder>() {
                public final NotificationShelfComponent.Builder get() {
                    return new NotificationShelfComponentBuilder();
                }
            };
            KeyguardLifecyclesDispatcher_Factory keyguardLifecyclesDispatcher_Factory = new KeyguardLifecyclesDispatcher_Factory(this.provideHandlerProvider, this.secureSettingsImplProvider, 1);
            this.communalSettingConditionProvider = keyguardLifecyclesDispatcher_Factory;
            this.provideCommunalConditionsProvider = new QSFragmentModule_ProvidesQuickQSPanelFactory(keyguardLifecyclesDispatcher_Factory, 2);
            int i = SetFactory.$r8$clinit;
            List emptyList = Collections.emptyList();
            ArrayList arrayList = new ArrayList(1);
            this.namedSetOfConditionProvider = C0768xb6bb24d6.m49m(arrayList, this.provideCommunalConditionsProvider, emptyList, arrayList);
            C167612 r1 = new Provider<MonitorComponent.Factory>() {
                public final MonitorComponent.Factory get() {
                    return new MonitorComponentFactory();
                }
            };
            this.monitorComponentFactoryProvider = r1;
            CommunalModule_ProvideCommunalSourceMonitorFactory communalModule_ProvideCommunalSourceMonitorFactory = new CommunalModule_ProvideCommunalSourceMonitorFactory(this.namedSetOfConditionProvider, r1);
            this.provideCommunalSourceMonitorProvider = communalModule_ProvideCommunalSourceMonitorFactory;
            this.communalSourceMonitorProvider = DoubleCheck.provider(new CommunalSourceMonitor_Factory(this.this$0.provideMainExecutorProvider, communalModule_ProvideCommunalSourceMonitorFactory, 0));
            this.keyguardQsUserSwitchComponentFactoryProvider = new Provider<KeyguardQsUserSwitchComponent.Factory>() {
                public final KeyguardQsUserSwitchComponent.Factory get() {
                    return new KeyguardQsUserSwitchComponentFactory();
                }
            };
            this.keyguardUserSwitcherComponentFactoryProvider = new Provider<KeyguardUserSwitcherComponent.Factory>() {
                public final KeyguardUserSwitcherComponent.Factory get() {
                    return new KeyguardUserSwitcherComponentFactory();
                }
            };
            this.keyguardStatusBarViewComponentFactoryProvider = new Provider<KeyguardStatusBarViewComponent.Factory>() {
                public final KeyguardStatusBarViewComponent.Factory get() {
                    return new KeyguardStatusBarViewComponentFactory();
                }
            };
            this.communalViewComponentFactoryProvider = new Provider<CommunalViewComponent.Factory>() {
                public final CommunalViewComponent.Factory get() {
                    return new CommunalViewComponentFactory();
                }
            };
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            this.privacyDialogControllerProvider = DoubleCheck.provider(PrivacyDialogController_Factory.create(daggerTvGlobalRootComponent3.providePermissionManagerProvider, daggerTvGlobalRootComponent3.providePackageManagerProvider, this.privacyItemControllerProvider, this.provideUserTrackerProvider, this.provideActivityStarterProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent3.provideMainExecutorProvider, this.privacyLoggerProvider, this.keyguardStateControllerImplProvider, this.appOpsControllerImplProvider, daggerTvGlobalRootComponent3.provideUiEventLoggerProvider));
            this.subscriptionManagerSlotIndexResolverProvider = DoubleCheck.provider(C1010xf95dc14f.InstanceHolder.INSTANCE);
            this.qsFrameTranslateImplProvider = DoubleCheck.provider(new VrMode_Factory(this.provideStatusBarProvider, 5));
            Provider<Optional<LowLightClockController>> provider3 = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.dynamicOverrideOptionalOfLowLightClockControllerProvider = provider3;
            this.provideLowLightClockControllerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(provider3, 0));
            this.provideCollapsedSbFragmentLogBufferProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.logBufferFactoryProvider, 2));
            Provider<UserInfoControllerImpl> provider4 = this.userInfoControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            this.statusBarUserInfoTrackerProvider = DoubleCheck.provider(new StatusBarUserInfoTracker_Factory(provider4, daggerTvGlobalRootComponent4.provideUserManagerProvider, daggerTvGlobalRootComponent4.dumpManagerProvider, daggerTvGlobalRootComponent4.provideMainExecutorProvider, this.provideBackgroundExecutorProvider));
            this.statusBarUserSwitcherFeatureControllerProvider = DoubleCheck.provider(new ImageExporter_Factory(this.featureFlagsReleaseProvider, 4));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            Provider<Context> provider5 = daggerTvGlobalRootComponent5.contextProvider;
            Provider<UserSwitcherController> provider6 = this.userSwitcherControllerProvider;
            Provider<UiEventLogger> provider7 = daggerTvGlobalRootComponent5.provideUiEventLoggerProvider;
            Provider<FalsingManagerProxy> provider8 = this.falsingManagerProxyProvider;
            UserDetailView_Adapter_Factory userDetailView_Adapter_Factory = new UserDetailView_Adapter_Factory(provider5, provider6, provider7, provider8);
            this.adapterProvider = userDetailView_Adapter_Factory;
            this.userSwitchDialogControllerProvider = DoubleCheck.provider(new UserSwitchDialogController_Factory(userDetailView_Adapter_Factory, this.provideActivityStarterProvider, provider8, this.provideDialogLaunchAnimatorProvider, provider7));
            Provider<ProximitySensor> provider9 = this.provideProximitySensorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            this.provideProximityCheckProvider = new ForegroundServiceController_Factory(provider9, daggerTvGlobalRootComponent6.provideMainDelayableExecutorProvider, 2);
            this.builderProvider10 = new FlingAnimationUtils_Builder_Factory(daggerTvGlobalRootComponent6.provideDisplayMetricsProvider);
            this.fgsManagerControllerProvider = DoubleCheck.provider(FgsManagerController_Factory.create(daggerTvGlobalRootComponent6.contextProvider, daggerTvGlobalRootComponent6.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.bindSystemClockProvider, daggerTvGlobalRootComponent6.provideIActivityManagerProvider, daggerTvGlobalRootComponent6.providePackageManagerProvider, this.deviceConfigProxyProvider, this.provideDialogLaunchAnimatorProvider, daggerTvGlobalRootComponent6.dumpManagerProvider));
            this.isPMLiteEnabledProvider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(this.featureFlagsReleaseProvider, this.globalSettingsImplProvider, 0));
            this.factoryProvider9 = DoubleCheck.provider(new StatusBarIconController_TintedIconManager_Factory_Factory(this.featureFlagsReleaseProvider));
        }

        public final void inject(SystemUIAppComponentFactory systemUIAppComponentFactory) {
            injectSystemUIAppComponentFactory(systemUIAppComponentFactory);
        }

        public TvSysUIComponentImpl(DaggerTvGlobalRootComponent daggerTvGlobalRootComponent, DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            this.this$0 = daggerTvGlobalRootComponent;
            initialize(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize2(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize3(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize4(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize5(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize6(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
            initialize7(dependencyProvider, nightDisplayListenerModule, sysUIUnfoldModule, optional, optional2, optional3, optional4, optional5, optional6, optional7, optional8, optional9, shellTransitions, optional10, optional11, optional12, optional13, optional14, optional15, optional16);
        }

        public final BouncerSwipeTouchHandler bouncerSwipeTouchHandler() {
            return new BouncerSwipeTouchHandler(this.statusBarKeyguardViewManagerProvider.get(), this.provideStatusBarProvider.get(), this.notificationShadeWindowControllerImplProvider.get(), namedFlingAnimationUtils(), namedFlingAnimationUtils2(), namedFloat());
        }

        public final Dependency createDependency() {
            return this.dependencyProvider2.get();
        }

        public final DumpManager createDumpManager() {
            return this.this$0.dumpManagerProvider.get();
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
            LinkedHashMap newLinkedHashMapWithExpectedSize = R$id.newLinkedHashMapWithExpectedSize(18);
            newLinkedHashMapWithExpectedSize.put(GlobalActionsComponent.class, this.globalActionsComponentProvider);
            newLinkedHashMapWithExpectedSize.put(HomeSoundEffectController.class, this.homeSoundEffectControllerProvider);
            newLinkedHashMapWithExpectedSize.put(InstantAppNotifier.class, this.instantAppNotifierProvider);
            newLinkedHashMapWithExpectedSize.put(KeyboardUI.class, this.keyboardUIProvider);
            newLinkedHashMapWithExpectedSize.put(PowerUI.class, this.powerUIProvider);
            newLinkedHashMapWithExpectedSize.put(RingtonePlayer.class, this.ringtonePlayerProvider);
            newLinkedHashMapWithExpectedSize.put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider);
            newLinkedHashMapWithExpectedSize.put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider);
            newLinkedHashMapWithExpectedSize.put(StorageNotification.class, this.storageNotificationProvider);
            newLinkedHashMapWithExpectedSize.put(ToastUI.class, this.toastUIProvider);
            newLinkedHashMapWithExpectedSize.put(TvNotificationHandler.class, this.provideTvNotificationHandlerProvider);
            newLinkedHashMapWithExpectedSize.put(TvNotificationPanel.class, this.tvNotificationPanelProvider);
            newLinkedHashMapWithExpectedSize.put(TvOngoingPrivacyChip.class, this.tvOngoingPrivacyChipProvider);
            newLinkedHashMapWithExpectedSize.put(TvStatusBar.class, this.tvStatusBarProvider);
            newLinkedHashMapWithExpectedSize.put(VolumeUI.class, this.volumeUIProvider);
            newLinkedHashMapWithExpectedSize.put(VpnStatusObserver.class, this.vpnStatusObserverProvider);
            newLinkedHashMapWithExpectedSize.put(WindowMagnification.class, this.windowMagnificationProvider);
            newLinkedHashMapWithExpectedSize.put(WMShell.class, this.wMShellProvider);
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
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            ActivityIntentHelper_Factory activityIntentHelper_Factory = new ActivityIntentHelper_Factory(daggerTvGlobalRootComponent.provideContentResolverProvider, 5);
            this.globalSettingsImplProvider = activityIntentHelper_Factory;
            this.provideDemoModeControllerProvider = DoubleCheck.provider(new LaunchOverview_Factory(daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.dumpManagerProvider, activityIntentHelper_Factory, 1));
            this.provideLeakDetectorProvider = DoubleCheck.provider(new DependencyProvider_ProvideLeakDetectorFactory(dependencyProvider3, (Provider) this.this$0.dumpManagerProvider));
            Provider<Looper> provider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBgLooperFactory.InstanceHolder.INSTANCE);
            this.provideBgLooperProvider = provider;
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(provider, 6);
            this.provideBgHandlerProvider = smartActionsReceiver_Factory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            Provider<UserTracker> provider2 = DoubleCheck.provider(new SettingsModule_ProvideUserTrackerFactory(daggerTvGlobalRootComponent2.contextProvider, daggerTvGlobalRootComponent2.provideUserManagerProvider, daggerTvGlobalRootComponent2.dumpManagerProvider, smartActionsReceiver_Factory));
            this.provideUserTrackerProvider = provider2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            Provider<TunerServiceImpl> provider3 = DoubleCheck.provider(new TunerServiceImpl_Factory(daggerTvGlobalRootComponent3.contextProvider, daggerTvGlobalRootComponent3.provideMainHandlerProvider, this.provideLeakDetectorProvider, this.provideDemoModeControllerProvider, provider2));
            this.tunerServiceImplProvider = provider3;
            this.tunerActivityProvider = new TunerActivity_Factory(this.provideDemoModeControllerProvider, provider3);
            Provider<MetricsLogger> provider4 = DoubleCheck.provider(new SecureSettingsImpl_Factory(dependencyProvider3, 5));
            this.provideMetricsLoggerProvider = provider4;
            this.foregroundServicesDialogProvider = new ForegroundServicesDialog_Factory(provider4, 0);
            this.provideBackgroundExecutorProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.provideBgLooperProvider, 4));
            Provider<LogcatEchoTracker> provider5 = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(this.this$0.provideContentResolverProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE, 2));
            this.provideLogcatEchoTrackerProvider = provider5;
            Provider<LogBufferFactory> provider6 = DoubleCheck.provider(new LogBufferFactory_Factory(this.this$0.dumpManagerProvider, provider5, 0));
            this.logBufferFactoryProvider = provider6;
            Provider<LogBuffer> provider7 = DoubleCheck.provider(new ForegroundServicesDialog_Factory(provider6, 3));
            this.provideBroadcastDispatcherLogBufferProvider = provider7;
            WallpaperController_Factory wallpaperController_Factory = new WallpaperController_Factory(provider7, 1);
            this.broadcastDispatcherLoggerProvider = wallpaperController_Factory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            Provider<BroadcastDispatcher> provider8 = DoubleCheck.provider(TvSystemUIModule_ProvideBatteryControllerFactory.create(dependencyProvider, daggerTvGlobalRootComponent4.contextProvider, this.provideBgLooperProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent4.dumpManagerProvider, wallpaperController_Factory, this.provideUserTrackerProvider));
            this.providesBroadcastDispatcherProvider = provider8;
            this.workLockActivityProvider = new DozeLogger_Factory(provider8, 2);
            this.deviceConfigProxyProvider = DoubleCheck.provider(DeviceConfigProxy_Factory.InstanceHolder.INSTANCE);
            Provider<EnhancedEstimatesImpl> provider9 = DoubleCheck.provider(EnhancedEstimatesImpl_Factory.InstanceHolder.INSTANCE);
            this.enhancedEstimatesImplProvider = provider9;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            this.provideBatteryControllerProvider = DoubleCheck.provider(new TvSystemUIModule_ProvideBatteryControllerFactory(daggerTvGlobalRootComponent5.contextProvider, provider9, daggerTvGlobalRootComponent5.providePowerManagerProvider, this.providesBroadcastDispatcherProvider, this.provideDemoModeControllerProvider, daggerTvGlobalRootComponent5.provideMainHandlerProvider, this.provideBgHandlerProvider, 0));
            Provider<DockManagerImpl> provider10 = DoubleCheck.provider(DockManagerImpl_Factory.InstanceHolder.INSTANCE);
            this.dockManagerImplProvider = provider10;
            Provider<FalsingDataProvider> provider11 = DoubleCheck.provider(new QSSquishinessController_Factory(this.this$0.provideDisplayMetricsProvider, this.provideBatteryControllerProvider, provider10, 1));
            this.falsingDataProvider = provider11;
            DistanceClassifier_Factory distanceClassifier_Factory = new DistanceClassifier_Factory(provider11, this.deviceConfigProxyProvider, 0);
            this.distanceClassifierProvider = distanceClassifier_Factory;
            this.proximityClassifierProvider = new ProximityClassifier_Factory(distanceClassifier_Factory, this.falsingDataProvider, this.deviceConfigProxyProvider, 0);
            this.pointerCountClassifierProvider = new BootCompleteCacheImpl_Factory(this.falsingDataProvider, 1);
            this.typeClassifierProvider = new TypeClassifier_Factory(this.falsingDataProvider, 0);
            this.diagonalClassifierProvider = new DiagonalClassifier_Factory(this.falsingDataProvider, this.deviceConfigProxyProvider, 0);
            KeyguardUnfoldTransition_Factory keyguardUnfoldTransition_Factory = new KeyguardUnfoldTransition_Factory(this.falsingDataProvider, this.deviceConfigProxyProvider, 1);
            this.zigZagClassifierProvider = keyguardUnfoldTransition_Factory;
            this.providesBrightLineGestureClassifiersProvider = FalsingModule_ProvidesBrightLineGestureClassifiersFactory.create(this.distanceClassifierProvider, this.proximityClassifierProvider, this.pointerCountClassifierProvider, this.typeClassifierProvider, this.diagonalClassifierProvider, keyguardUnfoldTransition_Factory);
            int i = SetFactory.$r8$clinit;
            List emptyList = Collections.emptyList();
            ArrayList arrayList = new ArrayList(1);
            this.namedSetOfFalsingClassifierProvider = C0768xb6bb24d6.m49m(arrayList, this.providesBrightLineGestureClassifiersProvider, emptyList, arrayList);
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = new QSFragmentModule_ProvidesQuickQSPanelFactory(this.this$0.provideViewConfigurationProvider, 1);
            this.providesSingleTapTouchSlopProvider = qSFragmentModule_ProvidesQuickQSPanelFactory;
            this.singleTapClassifierProvider = new SingleTapClassifier_Factory(this.falsingDataProvider, qSFragmentModule_ProvidesQuickQSPanelFactory, 0);
            MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(this.this$0.provideResourcesProvider, 1);
            this.providesDoubleTapTouchSlopProvider = mediaBrowserFactory_Factory;
            this.doubleTapClassifierProvider = new DoubleTapClassifier_Factory(this.falsingDataProvider, this.singleTapClassifierProvider, mediaBrowserFactory_Factory);
            Provider<SystemClock> provider12 = DoubleCheck.provider(SystemClockImpl_Factory.InstanceHolder.INSTANCE);
            this.bindSystemClockProvider = provider12;
            this.historyTrackerProvider = DoubleCheck.provider(new ActivityStarterDelegate_Factory(provider12, 1));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            this.statusBarStateControllerImplProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(daggerTvGlobalRootComponent6.provideUiEventLoggerProvider, daggerTvGlobalRootComponent6.dumpManagerProvider, daggerTvGlobalRootComponent6.provideInteractionJankMonitorProvider, 1));
            this.provideLockPatternUtilsProvider = DoubleCheck.provider(new OpaLockscreen_Factory(dependencyProvider3, (Provider) this.this$0.contextProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.protoTracerProvider = DoubleCheck.provider(new ProtoTracer_Factory(daggerTvGlobalRootComponent7.contextProvider, daggerTvGlobalRootComponent7.dumpManagerProvider, 0));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            Provider<CommandRegistry> provider13 = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(daggerTvGlobalRootComponent8.contextProvider, daggerTvGlobalRootComponent8.provideMainExecutorProvider, 1));
            this.commandRegistryProvider = provider13;
            this.provideCommandQueueProvider = DoubleCheck.provider(new DozeLog_Factory(this.this$0.contextProvider, this.protoTracerProvider, provider13, 1));
            this.providerLayoutInflaterProvider = DoubleCheck.provider(new DependencyProvider_ProviderLayoutInflaterFactory(dependencyProvider3, this.this$0.contextProvider));
            this.panelExpansionStateManagerProvider = DoubleCheck.provider(PanelExpansionStateManager_Factory.InstanceHolder.INSTANCE);
            this.falsingManagerProxyProvider = new DelegateFactory();
            this.keyguardUpdateMonitorProvider = new DelegateFactory();
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            Provider<AsyncSensorManager> provider14 = DoubleCheck.provider(new DozeLog_Factory(daggerTvGlobalRootComponent9.providesSensorManagerProvider, ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE, daggerTvGlobalRootComponent9.providesPluginManagerProvider, 3));
            this.asyncSensorManagerProvider = provider14;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            Provider<Resources> provider15 = daggerTvGlobalRootComponent10.provideResourcesProvider;
            ThresholdSensorImpl_BuilderFactory_Factory thresholdSensorImpl_BuilderFactory_Factory = new ThresholdSensorImpl_BuilderFactory_Factory(provider15, provider14, daggerTvGlobalRootComponent10.provideExecutionProvider);
            this.builderFactoryProvider = thresholdSensorImpl_BuilderFactory_Factory;
            this.providePostureToProximitySensorMappingProvider = new SensorModule_ProvidePostureToProximitySensorMappingFactory(thresholdSensorImpl_BuilderFactory_Factory, provider15);
            this.providePostureToSecondaryProximitySensorMappingProvider = new C1707xe478f0bc(thresholdSensorImpl_BuilderFactory_Factory, this.this$0.provideResourcesProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            this.devicePostureControllerImplProvider = DoubleCheck.provider(new TouchInsideHandler_Factory(daggerTvGlobalRootComponent11.contextProvider, daggerTvGlobalRootComponent11.provideDeviceStateManagerProvider, daggerTvGlobalRootComponent11.provideMainExecutorProvider, 2));
            Provider<ThresholdSensor[]> provider16 = this.providePostureToProximitySensorMappingProvider;
            Provider<ThresholdSensor[]> provider17 = this.providePostureToSecondaryProximitySensorMappingProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            this.postureDependentProximitySensorProvider = PostureDependentProximitySensor_Factory.create(provider16, provider17, daggerTvGlobalRootComponent12.provideMainDelayableExecutorProvider, daggerTvGlobalRootComponent12.provideExecutionProvider, this.devicePostureControllerImplProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            this.builderProvider = new ThresholdSensorImpl_Builder_Factory(daggerTvGlobalRootComponent13.provideResourcesProvider, this.asyncSensorManagerProvider, daggerTvGlobalRootComponent13.provideExecutionProvider);
            this.providePrimaryProximitySensorProvider = new SensorModule_ProvidePrimaryProximitySensorFactory(this.this$0.providesSensorManagerProvider, this.builderProvider);
            this.provideSecondaryProximitySensorProvider = new SensorModule_ProvideSecondaryProximitySensorFactory(this.builderProvider);
            Provider<ThresholdSensor> provider18 = this.providePrimaryProximitySensorProvider;
            Provider<ThresholdSensor> provider19 = this.provideSecondaryProximitySensorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            this.proximitySensorImplProvider = new MediaDreamSentinel_Factory(provider18, provider19, daggerTvGlobalRootComponent14.provideMainDelayableExecutorProvider, daggerTvGlobalRootComponent14.provideExecutionProvider, 1);
            this.provideProximitySensorProvider = new TileAdapter_Factory(this.this$0.provideResourcesProvider, this.postureDependentProximitySensorProvider, this.proximitySensorImplProvider, 1);
            this.keyguardStateControllerImplProvider = new DelegateFactory();
            this.falsingCollectorImplProvider = DoubleCheck.provider(FalsingCollectorImpl_Factory.create(this.falsingDataProvider, this.falsingManagerProxyProvider, this.keyguardUpdateMonitorProvider, this.historyTrackerProvider, this.provideProximitySensorProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideBatteryControllerProvider, this.dockManagerImplProvider, this.this$0.provideMainDelayableExecutorProvider, this.bindSystemClockProvider));
            this.statusBarKeyguardViewManagerProvider = new DelegateFactory();
            Provider<Executor> provider20 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideUiBackgroundExecutorFactory.InstanceHolder.INSTANCE);
            this.provideUiBackgroundExecutorProvider = provider20;
            this.dismissCallbackRegistryProvider = C0770xb6bb24d8.m51m(provider20, 2);
            SecureSettingsImpl_Factory secureSettingsImpl_Factory = new SecureSettingsImpl_Factory(this.this$0.provideContentResolverProvider, 0);
            this.secureSettingsImplProvider = secureSettingsImpl_Factory;
            Provider provider21 = this.globalSettingsImplProvider;
            Provider<UserTracker> provider22 = this.provideUserTrackerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            Provider<DeviceProvisionedControllerImpl> provider23 = DoubleCheck.provider(DeviceProvisionedControllerImpl_Factory.create(secureSettingsImpl_Factory, provider21, provider22, daggerTvGlobalRootComponent15.dumpManagerProvider, this.provideBgHandlerProvider, daggerTvGlobalRootComponent15.provideMainExecutorProvider));
            this.deviceProvisionedControllerImplProvider = provider23;
            this.providesDeviceProvisionedControllerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(provider23, 3));
            this.featureFlagsReleaseProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(this.this$0.provideResourcesProvider, this.this$0.dumpManagerProvider, 0));
            this.notifPipelineFlagsProvider = new VolumeUI_Factory(this.this$0.contextProvider, this.featureFlagsReleaseProvider, 1);
            Provider<Context> provider24 = this.this$0.contextProvider;
            Provider<NotificationManager> provider25 = this.this$0.provideNotificationManagerProvider;
            Provider<SystemClock> provider26 = this.bindSystemClockProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            this.notificationListenerProvider = DoubleCheck.provider(new NotificationListener_Factory(provider24, provider25, provider26, daggerTvGlobalRootComponent16.provideMainExecutorProvider, daggerTvGlobalRootComponent16.providesPluginManagerProvider));
            Provider<LogBuffer> provider27 = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(this.logBufferFactoryProvider, 3));
            this.provideNotificationsLogBufferProvider = provider27;
            this.notificationEntryManagerLoggerProvider = new WMShellBaseModule_ProvideRecentTasksFactory(provider27, 2);
            Provider<ExtensionControllerImpl> provider28 = DoubleCheck.provider(new ExtensionControllerImpl_Factory(this.this$0.contextProvider, this.provideLeakDetectorProvider, this.this$0.providesPluginManagerProvider, this.tunerServiceImplProvider, this.provideConfigurationControllerProvider));
            this.extensionControllerImplProvider = provider28;
            this.notificationPersonExtractorPluginBoundaryProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(provider28, 3));
            this.notificationGroupManagerLegacyProvider = new DelegateFactory();
            Provider<GroupMembershipManager> provider29 = DoubleCheck.provider(new WMShellModule_ProvideUnfoldBackgroundControllerFactory(this.notifPipelineFlagsProvider, this.notificationGroupManagerLegacyProvider, 1));
            this.provideGroupMembershipManagerProvider = provider29;
            this.peopleNotificationIdentifierImplProvider = DoubleCheck.provider(new RotationPolicyWrapperImpl_Factory(this.notificationPersonExtractorPluginBoundaryProvider, provider29, 3));
            this.setBubblesProvider = InstanceFactory.create(optional6);
            DelegateFactory.setDelegate(this.notificationGroupManagerLegacyProvider, DoubleCheck.provider(new NotificationGroupManagerLegacy_Factory(this.statusBarStateControllerImplProvider, this.peopleNotificationIdentifierImplProvider, this.setBubblesProvider, this.this$0.dumpManagerProvider)));
            this.provideNotificationMessagingUtilProvider = new SliceBroadcastRelayHandler_Factory(dependencyProvider3, this.this$0.contextProvider);
            this.notifLiveDataStoreImplProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.this$0.provideMainExecutorProvider, 2));
            this.notifCollectionLoggerProvider = new PrivacyLogger_Factory(this.provideNotificationsLogBufferProvider, 4);
            this.filesProvider = DoubleCheck.provider(Files_Factory.InstanceHolder.INSTANCE);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            this.logBufferEulogizerProvider = DoubleCheck.provider(new LogBufferEulogizer_Factory(daggerTvGlobalRootComponent17.contextProvider, daggerTvGlobalRootComponent17.dumpManagerProvider, this.bindSystemClockProvider, this.filesProvider, 0));
            this.notifCollectionProvider = DoubleCheck.provider(NotifCollection_Factory.create(this.this$0.provideIStatusBarServiceProvider, this.bindSystemClockProvider, this.notifPipelineFlagsProvider, this.notifCollectionLoggerProvider, this.this$0.provideMainHandlerProvider, this.logBufferEulogizerProvider, this.this$0.dumpManagerProvider));
            Provider<Choreographer> provider30 = DoubleCheck.provider(new DependencyProvider_ProvidesChoreographerFactory(dependencyProvider3, 0));
            this.providesChoreographerProvider = provider30;
            this.notifPipelineChoreographerImplProvider = DoubleCheck.provider(new RotationPolicyWrapperImpl_Factory(provider30, this.this$0.provideMainDelayableExecutorProvider, 1));
            this.notificationClickNotifierProvider = DoubleCheck.provider(new LogBufferFactory_Factory(this.this$0.provideIStatusBarServiceProvider, this.this$0.provideMainExecutorProvider, 1));
            this.provideNotificationEntryManagerProvider = new DelegateFactory();
            this.notificationInteractionTrackerProvider = DoubleCheck.provider(new DiagonalClassifier_Factory(this.notificationClickNotifierProvider, this.provideNotificationEntryManagerProvider, 1));
            this.shadeListBuilderLoggerProvider = new SecureSettingsImpl_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.shadeListBuilderProvider = DoubleCheck.provider(GlobalActionsImpl_Factory.create$1(this.this$0.dumpManagerProvider, this.notifPipelineChoreographerImplProvider, this.notifPipelineFlagsProvider, this.notificationInteractionTrackerProvider, this.shadeListBuilderLoggerProvider, this.bindSystemClockProvider));
            this.renderStageManagerProvider = DoubleCheck.provider(RenderStageManager_Factory.InstanceHolder.INSTANCE);
            this.notifPipelineProvider = DoubleCheck.provider(new NotifPipeline_Factory(this.notifPipelineFlagsProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, this.renderStageManagerProvider));
            this.provideCommonNotifCollectionProvider = DoubleCheck.provider(new DozeLog_Factory(this.notifPipelineFlagsProvider, this.notifPipelineProvider, this.provideNotificationEntryManagerProvider, 2));
            RotationPolicyWrapperImpl_Factory rotationPolicyWrapperImpl_Factory = new RotationPolicyWrapperImpl_Factory(this.notifLiveDataStoreImplProvider, this.provideCommonNotifCollectionProvider, 2);
            this.notificationVisibilityProviderImplProvider = rotationPolicyWrapperImpl_Factory;
            this.provideNotificationVisibilityProvider = DoubleCheck.provider(rotationPolicyWrapperImpl_Factory);
            Provider<Context> provider31 = this.this$0.contextProvider;
            Provider<BroadcastDispatcher> provider32 = this.providesBroadcastDispatcherProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent18 = this.this$0;
            this.notificationLockscreenUserManagerImplProvider = DoubleCheck.provider(AuthController_Factory.create$1(provider31, provider32, daggerTvGlobalRootComponent18.provideDevicePolicyManagerProvider, daggerTvGlobalRootComponent18.provideUserManagerProvider, this.provideNotificationVisibilityProvider, this.provideCommonNotifCollectionProvider, this.notificationClickNotifierProvider, this.this$0.provideKeyguardManagerProvider, this.statusBarStateControllerImplProvider, this.this$0.provideMainHandlerProvider, this.providesDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.this$0.dumpManagerProvider));
            this.provideSmartReplyControllerProvider = DoubleCheck.provider(new StatusBarDependenciesModule_ProvideSmartReplyControllerFactory(this.this$0.dumpManagerProvider, this.provideNotificationVisibilityProvider, this.this$0.provideIStatusBarServiceProvider, this.notificationClickNotifierProvider));
            this.remoteInputNotificationRebuilderProvider = DoubleCheck.provider(new PrivacyLogger_Factory(this.this$0.contextProvider, 3));
            this.optionalOfStatusBarProvider = new DelegateFactory();
        }

        public final void initialize2(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            this.provideHandlerProvider = new DependencyProvider_ProvideHandlerFactory(dependencyProvider3, 0);
            this.remoteInputUriControllerProvider = DoubleCheck.provider(new TimeoutManager_Factory(this.this$0.provideIStatusBarServiceProvider, 4));
            Provider<LogBuffer> m = C0773x82ed519e.m54m(this.logBufferFactoryProvider, 3);
            this.provideNotifInteractionLogBufferProvider = m;
            ActionClickLogger_Factory actionClickLogger_Factory = new ActionClickLogger_Factory(m, 0);
            this.actionClickLoggerProvider = actionClickLogger_Factory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            this.provideNotificationRemoteInputManagerProvider = DoubleCheck.provider(C1209xfa996c5e.create(daggerTvGlobalRootComponent.contextProvider, this.notifPipelineFlagsProvider, this.notificationLockscreenUserManagerImplProvider, this.provideSmartReplyControllerProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.remoteInputNotificationRebuilderProvider, this.optionalOfStatusBarProvider, this.statusBarStateControllerImplProvider, this.provideHandlerProvider, this.remoteInputUriControllerProvider, this.notificationClickNotifierProvider, actionClickLogger_Factory, daggerTvGlobalRootComponent.dumpManagerProvider));
            DateFormatUtil_Factory dateFormatUtil_Factory = new DateFormatUtil_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.notifBindPipelineLoggerProvider = dateFormatUtil_Factory;
            this.notifBindPipelineProvider = DoubleCheck.provider(new DozeWallpaperState_Factory(this.provideCommonNotifCollectionProvider, dateFormatUtil_Factory, GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE, 1));
            PeopleSpaceWidgetProvider_Factory peopleSpaceWidgetProvider_Factory = new PeopleSpaceWidgetProvider_Factory(this.provideCommonNotifCollectionProvider, 4);
            this.notifRemoteViewCacheImplProvider = peopleSpaceWidgetProvider_Factory;
            this.provideNotifRemoteViewCacheProvider = DoubleCheck.provider(peopleSpaceWidgetProvider_Factory);
            Provider<BindEventManagerImpl> provider = DoubleCheck.provider(BindEventManagerImpl_Factory.InstanceHolder.INSTANCE);
            this.bindEventManagerImplProvider = provider;
            Provider<NotificationGroupManagerLegacy> provider2 = this.notificationGroupManagerLegacyProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            Provider<ConversationNotificationManager> provider3 = DoubleCheck.provider(ConversationNotificationManager_Factory.create(provider, provider2, daggerTvGlobalRootComponent2.contextProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineFlagsProvider, daggerTvGlobalRootComponent2.provideMainHandlerProvider));
            this.conversationNotificationManagerProvider = provider3;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            this.conversationNotificationProcessorProvider = new KeyguardLifecyclesDispatcher_Factory(daggerTvGlobalRootComponent3.provideLauncherAppsProvider, provider3, 3);
            Provider<Context> provider4 = daggerTvGlobalRootComponent3.contextProvider;
            this.mediaFeatureFlagProvider = new QSLogger_Factory(provider4, 2);
            this.smartReplyConstantsProvider = DoubleCheck.provider(new LeakReporter_Factory(daggerTvGlobalRootComponent3.provideMainHandlerProvider, provider4, this.deviceConfigProxyProvider, 1));
            this.provideActivityManagerWrapperProvider = DoubleCheck.provider(new ActionClickLogger_Factory(dependencyProvider3, 5));
            this.provideDevicePolicyManagerWrapperProvider = DoubleCheck.provider(new KeyboardUI_Factory(dependencyProvider3, 9));
            Provider<KeyguardDismissUtil> provider5 = DoubleCheck.provider(KeyguardDismissUtil_Factory.InstanceHolder.INSTANCE);
            this.keyguardDismissUtilProvider = provider5;
            this.smartReplyInflaterImplProvider = DefaultUiController_Factory.create$2(this.smartReplyConstantsProvider, provider5, this.provideNotificationRemoteInputManagerProvider, this.provideSmartReplyControllerProvider, this.this$0.contextProvider);
            this.provideActivityStarterProvider = new DelegateFactory();
            Provider<LogBuffer> m2 = C0772x82ed519d.m53m(this.logBufferFactoryProvider, 3);
            this.provideNotificationHeadsUpLogBufferProvider = m2;
            this.headsUpManagerLoggerProvider = new ImageExporter_Factory(m2, 5);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            this.keyguardBypassControllerProvider = DoubleCheck.provider(StatusBarDemoMode_Factory.create(daggerTvGlobalRootComponent4.contextProvider, this.tunerServiceImplProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, daggerTvGlobalRootComponent4.dumpManagerProvider));
            Provider<VisualStabilityProvider> provider6 = DoubleCheck.provider(VisualStabilityProvider_Factory.InstanceHolder.INSTANCE);
            this.visualStabilityProvider = provider6;
            Provider<HeadsUpManagerPhone> provider7 = DoubleCheck.provider(new TvSystemUIModule_ProvideHeadsUpManagerPhoneFactory(this.this$0.contextProvider, this.headsUpManagerLoggerProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.provideGroupMembershipManagerProvider, provider6, this.provideConfigurationControllerProvider, 0));
            this.provideHeadsUpManagerPhoneProvider = provider7;
            Provider<SmartReplyConstants> provider8 = this.smartReplyConstantsProvider;
            SmartActionInflaterImpl_Factory smartActionInflaterImpl_Factory = new SmartActionInflaterImpl_Factory(provider8, this.provideActivityStarterProvider, this.provideSmartReplyControllerProvider, provider7);
            this.smartActionInflaterImplProvider = smartActionInflaterImpl_Factory;
            SmartReplyStateInflaterImpl_Factory create = SmartReplyStateInflaterImpl_Factory.create(provider8, this.provideActivityManagerWrapperProvider, this.this$0.providePackageManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, this.smartReplyInflaterImplProvider, smartActionInflaterImpl_Factory);
            this.smartReplyStateInflaterImplProvider = create;
            this.notificationContentInflaterProvider = DoubleCheck.provider(NotificationContentInflater_Factory.create(this.provideNotifRemoteViewCacheProvider, this.provideNotificationRemoteInputManagerProvider, this.conversationNotificationProcessorProvider, this.mediaFeatureFlagProvider, this.provideBackgroundExecutorProvider, create));
            Provider<NotifInflationErrorManager> provider9 = DoubleCheck.provider(NotifInflationErrorManager_Factory.InstanceHolder.INSTANCE);
            this.notifInflationErrorManagerProvider = provider9;
            WMShellBaseModule_ProvideSplitScreenFactory wMShellBaseModule_ProvideSplitScreenFactory = new WMShellBaseModule_ProvideSplitScreenFactory(this.provideNotificationsLogBufferProvider, 5);
            this.rowContentBindStageLoggerProvider = wMShellBaseModule_ProvideSplitScreenFactory;
            this.rowContentBindStageProvider = DoubleCheck.provider(new RowContentBindStage_Factory(this.notificationContentInflaterProvider, provider9, wMShellBaseModule_ProvideSplitScreenFactory, 0));
            this.expandableNotificationRowComponentBuilderProvider = new Provider<ExpandableNotificationRowComponent.Builder>() {
                public final ExpandableNotificationRowComponent.Builder get() {
                    return new ExpandableNotificationRowComponentBuilder();
                }
            };
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            UsbDebuggingActivity_Factory usbDebuggingActivity_Factory = new UsbDebuggingActivity_Factory(daggerTvGlobalRootComponent5.contextProvider, 2);
            this.iconBuilderProvider = usbDebuggingActivity_Factory;
            this.iconManagerProvider = new DarkIconDispatcherImpl_Factory(this.provideCommonNotifCollectionProvider, daggerTvGlobalRootComponent5.provideLauncherAppsProvider, usbDebuggingActivity_Factory, 1);
            Provider<LowPriorityInflationHelper> provider10 = DoubleCheck.provider(new ColumbusStructuredDataManager_Factory(this.notificationGroupManagerLegacyProvider, this.rowContentBindStageProvider, this.notifPipelineFlagsProvider, 2));
            this.lowPriorityInflationHelperProvider = provider10;
            this.notificationRowBinderImplProvider = DoubleCheck.provider(NotificationRowBinderImpl_Factory.create(this.this$0.contextProvider, this.provideNotificationMessagingUtilProvider, this.provideNotificationRemoteInputManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.notifBindPipelineProvider, this.rowContentBindStageProvider, this.expandableNotificationRowComponentBuilderProvider, this.iconManagerProvider, provider10, this.notifPipelineFlagsProvider));
            Provider<ForegroundServiceDismissalFeatureController> provider11 = DoubleCheck.provider(new ScreenPinningRequest_Factory(this.deviceConfigProxyProvider, this.this$0.contextProvider, 2));
            this.foregroundServiceDismissalFeatureControllerProvider = provider11;
            Provider<NotificationEntryManager> provider12 = this.provideNotificationEntryManagerProvider;
            Provider<NotificationEntryManagerLogger> provider13 = this.notificationEntryManagerLoggerProvider;
            Provider<NotificationGroupManagerLegacy> provider14 = this.notificationGroupManagerLegacyProvider;
            Provider<NotifPipelineFlags> provider15 = this.notifPipelineFlagsProvider;
            Provider<NotificationRowBinderImpl> provider16 = this.notificationRowBinderImplProvider;
            Provider<NotificationRemoteInputManager> provider17 = this.provideNotificationRemoteInputManagerProvider;
            Provider<LeakDetector> provider18 = this.provideLeakDetectorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            DelegateFactory.setDelegate(provider12, DoubleCheck.provider(NotificationsModule_ProvideNotificationEntryManagerFactory.create(provider13, provider14, provider15, provider16, provider17, provider18, provider11, daggerTvGlobalRootComponent6.provideIStatusBarServiceProvider, this.notifLiveDataStoreImplProvider, daggerTvGlobalRootComponent6.dumpManagerProvider)));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.debugModeFilterProvider = DoubleCheck.provider(new MediaModule_ProvidesMediaTttChipControllerSenderFactory(daggerTvGlobalRootComponent7.contextProvider, daggerTvGlobalRootComponent7.dumpManagerProvider, 1));
            Provider<Context> provider19 = this.this$0.contextProvider;
            this.provideAmbientDisplayConfigurationProvider = new DistanceClassifier_Factory(dependencyProvider3, provider19);
            this.provideAlwaysOnDisplayPolicyProvider = DoubleCheck.provider(new DiagonalClassifier_Factory(dependencyProvider3, provider19));
            C16812 r1 = new Provider<SysUIUnfoldComponent.Factory>() {
                public final SysUIUnfoldComponent.Factory get() {
                    return new SysUIUnfoldComponentFactory();
                }
            };
            this.sysUIUnfoldComponentFactoryProvider = r1;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            this.provideSysUIUnfoldComponentProvider = DoubleCheck.provider(new SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(sysUIUnfoldModule, daggerTvGlobalRootComponent8.unfoldTransitionProgressProvider, daggerTvGlobalRootComponent8.provideNaturalRotationProgressProvider, daggerTvGlobalRootComponent8.provideStatusBarScopedTransitionProvider, r1));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            Provider<WakefulnessLifecycle> provider20 = DoubleCheck.provider(new WakefulnessLifecycle_Factory(daggerTvGlobalRootComponent9.contextProvider, daggerTvGlobalRootComponent9.dumpManagerProvider));
            this.wakefulnessLifecycleProvider = provider20;
            DelegateFactory delegateFactory = new DelegateFactory();
            this.newKeyguardViewMediatorProvider = delegateFactory;
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.dozeParametersProvider = delegateFactory2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            Provider<UnlockedScreenOffAnimationController> provider21 = DoubleCheck.provider(UnlockedScreenOffAnimationController_Factory.create(daggerTvGlobalRootComponent10.contextProvider, provider20, this.statusBarStateControllerImplProvider, delegateFactory, this.keyguardStateControllerImplProvider, delegateFactory2, this.globalSettingsImplProvider, daggerTvGlobalRootComponent10.provideInteractionJankMonitorProvider, daggerTvGlobalRootComponent10.providePowerManagerProvider, this.provideHandlerProvider));
            this.unlockedScreenOffAnimationControllerProvider = provider21;
            Provider<ScreenOffAnimationController> provider22 = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(this.provideSysUIUnfoldComponentProvider, provider21, this.wakefulnessLifecycleProvider, 1));
            this.screenOffAnimationControllerProvider = provider22;
            Provider<DozeParameters> provider23 = this.dozeParametersProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            DelegateFactory.setDelegate(provider23, DoubleCheck.provider(DozeParameters_Factory.create(daggerTvGlobalRootComponent11.provideResourcesProvider, this.provideAmbientDisplayConfigurationProvider, this.provideAlwaysOnDisplayPolicyProvider, daggerTvGlobalRootComponent11.providePowerManagerProvider, this.provideBatteryControllerProvider, this.tunerServiceImplProvider, daggerTvGlobalRootComponent11.dumpManagerProvider, this.featureFlagsReleaseProvider, provider22, this.provideSysUIUnfoldComponentProvider, this.unlockedScreenOffAnimationControllerProvider, this.keyguardUpdateMonitorProvider, this.provideConfigurationControllerProvider, this.statusBarStateControllerImplProvider)));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            Provider<SysuiColorExtractor> provider24 = DoubleCheck.provider(new SysuiColorExtractor_Factory(daggerTvGlobalRootComponent12.contextProvider, this.provideConfigurationControllerProvider, daggerTvGlobalRootComponent12.dumpManagerProvider, 0));
            this.sysuiColorExtractorProvider = provider24;
            DelegateFactory delegateFactory3 = new DelegateFactory();
            this.authControllerProvider = delegateFactory3;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            this.notificationShadeWindowControllerImplProvider = DoubleCheck.provider(NotificationShadeWindowControllerImpl_Factory.create(daggerTvGlobalRootComponent13.contextProvider, daggerTvGlobalRootComponent13.provideWindowManagerProvider, daggerTvGlobalRootComponent13.provideIActivityManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.newKeyguardViewMediatorProvider, this.keyguardBypassControllerProvider, provider24, daggerTvGlobalRootComponent13.dumpManagerProvider, this.keyguardStateControllerImplProvider, this.screenOffAnimationControllerProvider, delegateFactory3));
            this.mediaArtworkProcessorProvider = DoubleCheck.provider(MediaArtworkProcessor_Factory.InstanceHolder.INSTANCE);
            MediaControllerFactory_Factory mediaControllerFactory_Factory = new MediaControllerFactory_Factory(this.this$0.contextProvider, 0);
            this.mediaControllerFactoryProvider = mediaControllerFactory_Factory;
            this.mediaTimeoutListenerProvider = DoubleCheck.provider(new FeatureFlagsRelease_Factory(mediaControllerFactory_Factory, this.this$0.provideMainDelayableExecutorProvider, 1));
            MediaBrowserFactory_Factory mediaBrowserFactory_Factory = new MediaBrowserFactory_Factory(this.this$0.contextProvider, 0);
            this.mediaBrowserFactoryProvider = mediaBrowserFactory_Factory;
            ResumeMediaBrowserFactory_Factory resumeMediaBrowserFactory_Factory = new ResumeMediaBrowserFactory_Factory(this.this$0.contextProvider, mediaBrowserFactory_Factory, 0);
            this.resumeMediaBrowserFactoryProvider = resumeMediaBrowserFactory_Factory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            this.mediaResumeListenerProvider = DoubleCheck.provider(MediaResumeListener_Factory.create(daggerTvGlobalRootComponent14.contextProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, this.tunerServiceImplProvider, resumeMediaBrowserFactory_Factory, daggerTvGlobalRootComponent14.dumpManagerProvider, this.bindSystemClockProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            this.mediaSessionBasedFilterProvider = new MediaSessionBasedFilter_Factory(daggerTvGlobalRootComponent15.contextProvider, daggerTvGlobalRootComponent15.provideMediaSessionManagerProvider, daggerTvGlobalRootComponent15.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, 0);
            Provider<LocalBluetoothManager> provider25 = DoubleCheck.provider(new QSFragmentModule_ProvidesQSFooterActionsViewFactory(this.this$0.contextProvider, this.provideBgHandlerProvider, 1));
            this.provideLocalBluetoothControllerProvider = provider25;
            this.localMediaManagerFactoryProvider = new CommunalSourceMonitor_Factory(this.this$0.contextProvider, provider25, 2);
            Provider<MediaFlags> provider26 = DoubleCheck.provider(new MediaFlags_Factory(this.featureFlagsReleaseProvider, 0));
            this.mediaFlagsProvider = provider26;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            Provider<MediaMuteAwaitConnectionManagerFactory> provider27 = DoubleCheck.provider(new ColumbusStructuredDataManager_Factory(provider26, daggerTvGlobalRootComponent16.contextProvider, daggerTvGlobalRootComponent16.provideMainExecutorProvider, 1));
            this.mediaMuteAwaitConnectionManagerFactoryProvider = provider27;
            Provider<MediaControllerFactory> provider28 = this.mediaControllerFactoryProvider;
            Provider<LocalMediaManagerFactory> provider29 = this.localMediaManagerFactoryProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            this.mediaDeviceManagerProvider = MediaDeviceManager_Factory.create(provider28, provider29, daggerTvGlobalRootComponent17.provideMediaRouter2ManagerProvider, provider27, daggerTvGlobalRootComponent17.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent17.dumpManagerProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent18 = this.this$0;
            MediaDataFilter_Factory create2 = MediaDataFilter_Factory.create(daggerTvGlobalRootComponent18.contextProvider, this.providesBroadcastDispatcherProvider, this.notificationLockscreenUserManagerImplProvider, daggerTvGlobalRootComponent18.provideMainExecutorProvider, this.bindSystemClockProvider);
            this.mediaDataFilterProvider = create2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent19 = this.this$0;
            Provider<MediaDataManager> provider30 = DoubleCheck.provider(MediaDataManager_Factory.create(daggerTvGlobalRootComponent19.contextProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent19.provideMainDelayableExecutorProvider, this.mediaControllerFactoryProvider, daggerTvGlobalRootComponent19.dumpManagerProvider, this.providesBroadcastDispatcherProvider, this.mediaTimeoutListenerProvider, this.mediaResumeListenerProvider, this.mediaSessionBasedFilterProvider, this.mediaDeviceManagerProvider, create2, this.provideActivityStarterProvider, this.bindSystemClockProvider, this.tunerServiceImplProvider, this.mediaFlagsProvider));
            this.mediaDataManagerProvider = provider30;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent20 = this.this$0;
            this.provideNotificationMediaManagerProvider = DoubleCheck.provider(C1208x30c882de.create(daggerTvGlobalRootComponent20.contextProvider, this.optionalOfStatusBarProvider, this.notificationShadeWindowControllerImplProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.mediaArtworkProcessorProvider, this.keyguardBypassControllerProvider, this.notifPipelineProvider, this.notifCollectionProvider, this.notifPipelineFlagsProvider, daggerTvGlobalRootComponent20.provideMainDelayableExecutorProvider, provider30, daggerTvGlobalRootComponent20.dumpManagerProvider));
            this.keyguardEnvironmentImplProvider = DoubleCheck.provider(new VibratorHelper_Factory(this.notificationLockscreenUserManagerImplProvider, this.providesDeviceProvisionedControllerProvider, 3));
            Provider<IndividualSensorPrivacyController> provider31 = DoubleCheck.provider(new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(this.this$0.provideSensorPrivacyManagerProvider, 3));
            this.provideIndividualSensorPrivacyControllerProvider = provider31;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent21 = this.this$0;
            Provider<AppOpsControllerImpl> provider32 = DoubleCheck.provider(AppOpsControllerImpl_Factory.create(daggerTvGlobalRootComponent21.contextProvider, this.provideBgLooperProvider, daggerTvGlobalRootComponent21.dumpManagerProvider, daggerTvGlobalRootComponent21.provideAudioManagerProvider, provider31, this.providesBroadcastDispatcherProvider, this.bindSystemClockProvider));
            this.appOpsControllerImplProvider = provider32;
            Provider<ForegroundServiceController> provider33 = DoubleCheck.provider(new ForegroundServiceController_Factory(provider32, this.this$0.provideMainHandlerProvider, 0));
            this.foregroundServiceControllerProvider = provider33;
            this.notificationFilterProvider = DoubleCheck.provider(NotificationFilter_Factory.create(this.debugModeFilterProvider, this.statusBarStateControllerImplProvider, this.keyguardEnvironmentImplProvider, provider33, this.notificationLockscreenUserManagerImplProvider, this.mediaFeatureFlagProvider));
            this.notificationSectionsFeatureManagerProvider = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(this.deviceConfigProxyProvider, this.this$0.contextProvider, 2));
            Provider<HighPriorityProvider> provider34 = DoubleCheck.provider(new UserCreator_Factory(this.peopleNotificationIdentifierImplProvider, this.provideGroupMembershipManagerProvider, 2));
            this.highPriorityProvider = provider34;
            this.notificationRankingManagerProvider = NotificationRankingManager_Factory.create(this.provideNotificationMediaManagerProvider, this.notificationGroupManagerLegacyProvider, this.provideHeadsUpManagerPhoneProvider, this.notificationFilterProvider, this.notificationEntryManagerLoggerProvider, this.notificationSectionsFeatureManagerProvider, this.peopleNotificationIdentifierImplProvider, provider34, this.keyguardEnvironmentImplProvider);
            this.targetSdkResolverProvider = DoubleCheck.provider(new MediaFlags_Factory(this.this$0.contextProvider, 2));
            ImageExporter_Factory imageExporter_Factory = new ImageExporter_Factory(this.provideNotificationsLogBufferProvider, 3);
            this.groupCoalescerLoggerProvider = imageExporter_Factory;
            this.groupCoalescerProvider = new SystemEventChipAnimationController_Factory(this.this$0.provideMainDelayableExecutorProvider, this.bindSystemClockProvider, imageExporter_Factory, 1);
            C16823 r12 = new Provider<CoordinatorsSubcomponent.Factory>() {
                public final CoordinatorsSubcomponent.Factory get() {
                    return new CoordinatorsSubcomponentFactory();
                }
            };
            this.coordinatorsSubcomponentFactoryProvider = r12;
            this.notifCoordinatorsProvider = DoubleCheck.provider(new CoordinatorsModule_NotifCoordinatorsFactory(r12));
            this.notifInflaterImplProvider = DoubleCheck.provider(new PeopleSpaceWidgetProvider_Factory(this.notifInflationErrorManagerProvider, 3));
            this.mediaContainerControllerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(this.providerLayoutInflaterProvider, 2));
            this.sectionHeaderVisibilityProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.this$0.contextProvider, 3));
            this.shadeViewDifferLoggerProvider = new ToastLogger_Factory(this.provideNotificationsLogBufferProvider, 2);
            Provider<NotifViewBarn> provider35 = DoubleCheck.provider(NotifViewBarn_Factory.InstanceHolder.INSTANCE);
            this.notifViewBarnProvider = provider35;
            ShadeViewManager_Factory create3 = ShadeViewManager_Factory.create(this.this$0.contextProvider, this.mediaContainerControllerProvider, this.notificationSectionsFeatureManagerProvider, this.sectionHeaderVisibilityProvider, this.shadeViewDifferLoggerProvider, provider35);
            this.shadeViewManagerProvider = create3;
            InstanceFactory create4 = InstanceFactory.create(new ShadeViewManagerFactory_Impl(create3));
            this.shadeViewManagerFactoryProvider = create4;
            this.notifPipelineInitializerProvider = DoubleCheck.provider(NotifPipelineInitializer_Factory.create(this.notifPipelineProvider, this.groupCoalescerProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, this.renderStageManagerProvider, this.notifCoordinatorsProvider, this.notifInflaterImplProvider, this.this$0.dumpManagerProvider, create4, this.notifPipelineFlagsProvider));
            this.notifBindPipelineInitializerProvider = new NotifBindPipelineInitializer_Factory(this.notifBindPipelineProvider, this.rowContentBindStageProvider, 0);
            this.notificationGroupAlertTransferHelperProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(this.rowContentBindStageProvider, this.statusBarStateControllerImplProvider, this.notificationGroupManagerLegacyProvider, 3));
            LogModule_ProvidePrivacyLogBufferFactory logModule_ProvidePrivacyLogBufferFactory = new LogModule_ProvidePrivacyLogBufferFactory(this.provideNotificationHeadsUpLogBufferProvider, 4);
            this.headsUpViewBinderLoggerProvider = logModule_ProvidePrivacyLogBufferFactory;
            this.headsUpViewBinderProvider = DoubleCheck.provider(new SysuiColorExtractor_Factory(this.provideNotificationMessagingUtilProvider, this.rowContentBindStageProvider, logModule_ProvidePrivacyLogBufferFactory, 1));
            WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory = new WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory(this.provideNotificationsLogBufferProvider, this.provideNotificationHeadsUpLogBufferProvider, 1);
            this.notificationInterruptLoggerProvider = wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent22 = this.this$0;
            this.notificationInterruptStateProviderImplProvider = DoubleCheck.provider(NotificationInterruptStateProviderImpl_Factory.create(daggerTvGlobalRootComponent22.provideContentResolverProvider, daggerTvGlobalRootComponent22.providePowerManagerProvider, daggerTvGlobalRootComponent22.provideIDreamManagerProvider, this.provideAmbientDisplayConfigurationProvider, this.notificationFilterProvider, this.provideBatteryControllerProvider, this.statusBarStateControllerImplProvider, this.provideHeadsUpManagerPhoneProvider, wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory, daggerTvGlobalRootComponent22.provideMainHandlerProvider));
            Provider<NotificationEntryManager> provider36 = this.provideNotificationEntryManagerProvider;
            Provider<VisualStabilityProvider> provider37 = this.visualStabilityProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent23 = this.this$0;
            Provider<VisualStabilityManager> provider38 = DoubleCheck.provider(NotificationsModule_ProvideVisualStabilityManagerFactory.create(provider36, provider37, daggerTvGlobalRootComponent23.provideMainHandlerProvider, this.statusBarStateControllerImplProvider, this.wakefulnessLifecycleProvider, daggerTvGlobalRootComponent23.dumpManagerProvider));
            this.provideVisualStabilityManagerProvider = provider38;
            this.headsUpControllerProvider = DoubleCheck.provider(HeadsUpController_Factory.create(this.headsUpViewBinderProvider, this.notificationInterruptStateProviderImplProvider, this.provideHeadsUpManagerPhoneProvider, this.provideNotificationRemoteInputManagerProvider, this.statusBarStateControllerImplProvider, provider38, this.notificationListenerProvider));
            QSLogger_Factory qSLogger_Factory = new QSLogger_Factory(this.provideNotifInteractionLogBufferProvider, 3);
            this.notificationClickerLoggerProvider = qSLogger_Factory;
            this.builderProvider2 = new NotificationClicker_Builder_Factory(qSLogger_Factory);
            this.animatedImageNotificationManagerProvider = DoubleCheck.provider(new TakeScreenshotService_Factory(this.provideCommonNotifCollectionProvider, this.bindEventManagerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, 1));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent24 = this.this$0;
            this.peopleSpaceWidgetManagerProvider = DoubleCheck.provider(PeopleSpaceWidgetManager_Factory.create(daggerTvGlobalRootComponent24.contextProvider, daggerTvGlobalRootComponent24.provideLauncherAppsProvider, this.provideCommonNotifCollectionProvider, daggerTvGlobalRootComponent24.providePackageManagerProvider, this.setBubblesProvider, daggerTvGlobalRootComponent24.provideUserManagerProvider, daggerTvGlobalRootComponent24.provideNotificationManagerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider));
            this.notificationsControllerImplProvider = DoubleCheck.provider(NotificationsControllerImpl_Factory.create(this.notifPipelineFlagsProvider, this.notificationListenerProvider, this.provideNotificationEntryManagerProvider, this.debugModeFilterProvider, this.notificationRankingManagerProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineProvider, this.notifLiveDataStoreImplProvider, this.targetSdkResolverProvider, this.notifPipelineInitializerProvider, this.notifBindPipelineInitializerProvider, this.providesDeviceProvisionedControllerProvider, this.notificationRowBinderImplProvider, this.bindEventManagerImplProvider, this.remoteInputUriControllerProvider, this.notificationGroupManagerLegacyProvider, this.notificationGroupAlertTransferHelperProvider, this.provideHeadsUpManagerPhoneProvider, this.headsUpControllerProvider, this.headsUpViewBinderProvider, this.builderProvider2, this.animatedImageNotificationManagerProvider, this.peopleSpaceWidgetManagerProvider));
        }

        public final void initialize3(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(this.notificationListenerProvider, 4);
            this.notificationsControllerStubProvider = smartActionsReceiver_Factory;
            this.provideNotificationsControllerProvider = DoubleCheck.provider(new ScrollCaptureClient_Factory(this.this$0.contextProvider, this.notificationsControllerImplProvider, smartActionsReceiver_Factory, 1));
            C16834 r1 = new Provider<FragmentService.FragmentCreator.Factory>() {
                public final FragmentService.FragmentCreator.Factory get() {
                    return new FragmentCreatorFactory();
                }
            };
            this.fragmentCreatorFactoryProvider = r1;
            this.fragmentServiceProvider = DoubleCheck.provider(new FragmentService_Factory(r1, this.provideConfigurationControllerProvider, this.this$0.dumpManagerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            this.darkIconDispatcherImplProvider = DoubleCheck.provider(new DarkIconDispatcherImpl_Factory(daggerTvGlobalRootComponent.contextProvider, this.provideCommandQueueProvider, daggerTvGlobalRootComponent.dumpManagerProvider, 0));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            Provider<NavigationModeController> provider = DoubleCheck.provider(NavigationModeController_Factory.create$1(daggerTvGlobalRootComponent2.contextProvider, this.providesDeviceProvisionedControllerProvider, this.provideConfigurationControllerProvider, this.provideUiBackgroundExecutorProvider, daggerTvGlobalRootComponent2.dumpManagerProvider));
            this.navigationModeControllerProvider = provider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            this.lightBarControllerProvider = DoubleCheck.provider(new C2508LightBarController_Factory(daggerTvGlobalRootComponent3.contextProvider, this.darkIconDispatcherImplProvider, this.provideBatteryControllerProvider, provider, daggerTvGlobalRootComponent3.dumpManagerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            this.provideAutoHideControllerProvider = DoubleCheck.provider(new DoubleTapClassifier_Factory(dependencyProvider, daggerTvGlobalRootComponent4.contextProvider, daggerTvGlobalRootComponent4.provideMainHandlerProvider, daggerTvGlobalRootComponent4.provideIWindowManagerProvider));
            this.providesStatusBarWindowViewProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.providerLayoutInflaterProvider, 5));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            Provider<StatusBarContentInsetsProvider> provider2 = DoubleCheck.provider(new StatusBarContentInsetsProvider_Factory(daggerTvGlobalRootComponent5.contextProvider, this.provideConfigurationControllerProvider, daggerTvGlobalRootComponent5.dumpManagerProvider, 0));
            this.statusBarContentInsetsProvider = provider2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            this.statusBarWindowControllerProvider = DoubleCheck.provider(StatusBarWindowController_Factory.create(daggerTvGlobalRootComponent6.contextProvider, this.providesStatusBarWindowViewProvider, daggerTvGlobalRootComponent6.provideWindowManagerProvider, daggerTvGlobalRootComponent6.provideIWindowManagerProvider, provider2, daggerTvGlobalRootComponent6.provideResourcesProvider, daggerTvGlobalRootComponent6.unfoldTransitionProgressProvider));
            this.statusBarWindowStateControllerProvider = DoubleCheck.provider(new QSFlagsModule_IsPMLiteEnabledFactory(this.this$0.provideDisplayIdProvider, this.provideCommandQueueProvider, 3));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.statusBarIconControllerImplProvider = DoubleCheck.provider(StatusBarIconControllerImpl_Factory.create(daggerTvGlobalRootComponent7.contextProvider, this.provideCommandQueueProvider, this.provideDemoModeControllerProvider, this.provideConfigurationControllerProvider, this.tunerServiceImplProvider, daggerTvGlobalRootComponent7.dumpManagerProvider));
            this.carrierConfigTrackerProvider = DoubleCheck.provider(new CarrierConfigTracker_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider));
            this.callbackHandlerProvider = new KeyboardUI_Factory(GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE, 6);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            this.telephonyListenerManagerProvider = DoubleCheck.provider(new TelephonyListenerManager_Factory(daggerTvGlobalRootComponent8.provideTelephonyManagerProvider, daggerTvGlobalRootComponent8.provideMainExecutorProvider, TelephonyCallback_Factory.InstanceHolder.INSTANCE, 0));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> provider3 = DoubleCheck.provider(new AccessPointControllerImpl_WifiPickerTrackerFactory_Factory(daggerTvGlobalRootComponent9.contextProvider, daggerTvGlobalRootComponent9.provideWifiManagerProvider, daggerTvGlobalRootComponent9.provideConnectivityManagagerProvider, daggerTvGlobalRootComponent9.provideMainHandlerProvider, this.provideBgHandlerProvider));
            this.wifiPickerTrackerFactoryProvider = provider3;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            this.provideAccessPointControllerImplProvider = DoubleCheck.provider(new StatusBarPolicyModule_ProvideAccessPointControllerImplFactory(daggerTvGlobalRootComponent10.provideUserManagerProvider, this.provideUserTrackerProvider, daggerTvGlobalRootComponent10.provideMainExecutorProvider, provider3));
            Provider<LayoutInflater> provider4 = this.providerLayoutInflaterProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            this.toastFactoryProvider = DoubleCheck.provider(new ToastFactory_Factory(provider4, daggerTvGlobalRootComponent11.providesPluginManagerProvider, daggerTvGlobalRootComponent11.dumpManagerProvider, 0));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            this.locationControllerImplProvider = DoubleCheck.provider(LocationControllerImpl_Factory.create(daggerTvGlobalRootComponent12.contextProvider, this.appOpsControllerImplProvider, this.deviceConfigProxyProvider, this.provideBgHandlerProvider, this.providesBroadcastDispatcherProvider, this.bootCompleteCacheImplProvider, this.provideUserTrackerProvider, daggerTvGlobalRootComponent12.providePackageManagerProvider, daggerTvGlobalRootComponent12.provideUiEventLoggerProvider, this.secureSettingsImplProvider));
            Provider<DialogLaunchAnimator> provider5 = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.this$0.provideIDreamManagerProvider, 2));
            Provider<DialogLaunchAnimator> provider6 = provider5;
            this.provideDialogLaunchAnimatorProvider = provider5;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            InternetDialogController_Factory create = InternetDialogController_Factory.create(daggerTvGlobalRootComponent13.contextProvider, daggerTvGlobalRootComponent13.provideUiEventLoggerProvider, this.provideActivityStarterProvider, this.provideAccessPointControllerImplProvider, daggerTvGlobalRootComponent13.provideSubcriptionManagerProvider, daggerTvGlobalRootComponent13.provideTelephonyManagerProvider, daggerTvGlobalRootComponent13.provideWifiManagerProvider, daggerTvGlobalRootComponent13.provideConnectivityManagagerProvider, daggerTvGlobalRootComponent13.provideMainHandlerProvider, daggerTvGlobalRootComponent13.provideMainExecutorProvider, this.providesBroadcastDispatcherProvider, this.keyguardUpdateMonitorProvider, this.globalSettingsImplProvider, this.keyguardStateControllerImplProvider, daggerTvGlobalRootComponent13.provideWindowManagerProvider, this.toastFactoryProvider, this.provideBgHandlerProvider, this.carrierConfigTrackerProvider, this.locationControllerImplProvider, provider6);
            this.internetDialogControllerProvider = create;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            Provider<InternetDialogFactory> provider7 = DoubleCheck.provider(InternetDialogFactory_Factory.create(daggerTvGlobalRootComponent14.provideMainHandlerProvider, this.provideBackgroundExecutorProvider, create, daggerTvGlobalRootComponent14.contextProvider, daggerTvGlobalRootComponent14.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.keyguardStateControllerImplProvider));
            Provider<InternetDialogFactory> provider8 = provider7;
            this.internetDialogFactoryProvider = provider7;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            this.networkControllerImplProvider = DoubleCheck.provider(NetworkControllerImpl_Factory.create(daggerTvGlobalRootComponent15.contextProvider, this.provideBgLooperProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent15.provideSubcriptionManagerProvider, this.callbackHandlerProvider, this.providesDeviceProvisionedControllerProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent15.provideConnectivityManagagerProvider, daggerTvGlobalRootComponent15.provideTelephonyManagerProvider, this.telephonyListenerManagerProvider, daggerTvGlobalRootComponent15.provideWifiManagerProvider, daggerTvGlobalRootComponent15.provideNetworkScoreManagerProvider, this.provideAccessPointControllerImplProvider, this.provideDemoModeControllerProvider, this.carrierConfigTrackerProvider, daggerTvGlobalRootComponent15.provideMainHandlerProvider, provider8, this.featureFlagsReleaseProvider, daggerTvGlobalRootComponent15.dumpManagerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            Provider<SecurityControllerImpl> provider9 = DoubleCheck.provider(new SecurityControllerImpl_Factory(daggerTvGlobalRootComponent16.contextProvider, this.provideBgHandlerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent16.dumpManagerProvider));
            this.securityControllerImplProvider = provider9;
            this.statusBarSignalPolicyProvider = DoubleCheck.provider(StatusBarSignalPolicy_Factory.create(this.this$0.contextProvider, this.statusBarIconControllerImplProvider, this.carrierConfigTrackerProvider, this.networkControllerImplProvider, provider9, this.tunerServiceImplProvider, this.featureFlagsReleaseProvider));
            this.notificationWakeUpCoordinatorProvider = DoubleCheck.provider(ForegroundServiceNotificationListener_Factory.create$1(this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.dozeParametersProvider, this.screenOffAnimationControllerProvider));
            this.notificationRoundnessManagerProvider = C0769xb6bb24d7.m50m(this.notificationSectionsFeatureManagerProvider, 4);
            this.provideLSShadeTransitionControllerBufferProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.logBufferFactoryProvider, 2));
            Provider<LockscreenGestureLogger> m = C0770xb6bb24d8.m51m(this.provideMetricsLoggerProvider, 4);
            this.lockscreenGestureLoggerProvider = m;
            this.lSShadeTransitionLoggerProvider = new IconController_Factory(this.provideLSShadeTransitionControllerBufferProvider, m, this.this$0.provideDisplayMetricsProvider, 1);
            Provider<MediaHostStatesManager> provider10 = DoubleCheck.provider(MediaHostStatesManager_Factory.InstanceHolder.INSTANCE);
            this.mediaHostStatesManagerProvider = provider10;
            this.mediaViewControllerProvider = new MediaViewController_Factory(this.this$0.contextProvider, this.provideConfigurationControllerProvider, provider10, 0);
            Provider<DelayableExecutor> provider11 = DoubleCheck.provider(new ForegroundServicesDialog_Factory(this.provideBgLooperProvider, 5));
            this.provideBackgroundDelayableExecutorProvider = provider11;
            Provider<RepeatableExecutor> m2 = C0771xb6bb24d9.m52m(provider11, 6);
            this.provideBackgroundRepeatableExecutorProvider = m2;
            this.seekBarViewModelProvider = new SeekBarViewModel_Factory(m2, 0);
            this.provideAssistUtilsProvider = DoubleCheck.provider(new AssistModule_ProvideAssistUtilsFactory(this.this$0.contextProvider, 0));
            this.phoneStateMonitorProvider = DoubleCheck.provider(new PhoneStateMonitor_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, this.optionalOfStatusBarProvider, this.bootCompleteCacheImplProvider, this.statusBarStateControllerImplProvider));
            this.overviewProxyServiceProvider = new DelegateFactory();
            this.provideSysUiStateProvider = C0769xb6bb24d7.m50m(this.this$0.dumpManagerProvider, 2);
            this.accessibilityButtonModeObserverProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.this$0.contextProvider, 1));
            this.accessibilityButtonTargetsObserverProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(this.this$0.contextProvider, 1));
            DelegateFactory delegateFactory = new DelegateFactory();
            this.contextComponentResolverProvider = delegateFactory;
            CommunalSourceMonitor_Factory communalSourceMonitor_Factory = new CommunalSourceMonitor_Factory(this.this$0.contextProvider, delegateFactory, 3);
            this.provideRecentsImplProvider = communalSourceMonitor_Factory;
            Provider<Recents> provider12 = DoubleCheck.provider(new TvSystemUIModule_ProvideRecentsFactory(this.this$0.contextProvider, communalSourceMonitor_Factory, this.provideCommandQueueProvider, 0));
            this.provideRecentsProvider = provider12;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider = new PresentJdkOptionalInstanceProvider(provider12);
            this.optionalOfRecentsProvider = presentJdkOptionalInstanceProvider;
            Provider<SystemActions> provider13 = DoubleCheck.provider(new SystemActions_Factory(this.this$0.contextProvider, this.notificationShadeWindowControllerImplProvider, this.optionalOfStatusBarProvider, presentJdkOptionalInstanceProvider));
            this.systemActionsProvider = provider13;
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.assistManagerProvider = delegateFactory2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            this.navBarHelperProvider = DoubleCheck.provider(NavBarHelper_Factory.create(daggerTvGlobalRootComponent17.contextProvider, daggerTvGlobalRootComponent17.provideAccessibilityManagerProvider, this.accessibilityButtonModeObserverProvider, this.accessibilityButtonTargetsObserverProvider, provider13, this.overviewProxyServiceProvider, delegateFactory2, this.optionalOfStatusBarProvider, this.navigationModeControllerProvider, this.provideUserTrackerProvider, daggerTvGlobalRootComponent17.dumpManagerProvider));
            this.setPipProvider = InstanceFactory.create(optional);
            this.setLegacySplitScreenProvider = InstanceFactory.create(optional2);
            this.shadeControllerImplProvider = new DelegateFactory();
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent18 = this.this$0;
            this.blurUtilsProvider = DoubleCheck.provider(new BlurUtils_Factory(daggerTvGlobalRootComponent18.provideResourcesProvider, daggerTvGlobalRootComponent18.provideCrossWindowBlurListenersProvider, daggerTvGlobalRootComponent18.dumpManagerProvider, 0));
            Provider<LogBuffer> m3 = C0771xb6bb24d9.m52m(this.logBufferFactoryProvider, 3);
            this.provideDozeLogBufferProvider = m3;
            DozeLogger_Factory dozeLogger_Factory = new DozeLogger_Factory(m3, 0);
            this.dozeLoggerProvider = dozeLogger_Factory;
            Provider<DozeLog> provider14 = DoubleCheck.provider(new DozeLog_Factory(this.keyguardUpdateMonitorProvider, this.this$0.dumpManagerProvider, dozeLogger_Factory, 0));
            this.dozeLogProvider = provider14;
            this.dozeScrimControllerProvider = DoubleCheck.provider(new SetupWizard_Factory(this.dozeParametersProvider, provider14, this.statusBarStateControllerImplProvider, 1));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent19 = this.this$0;
            DelayedWakeLock_Builder_Factory delayedWakeLock_Builder_Factory = new DelayedWakeLock_Builder_Factory(daggerTvGlobalRootComponent19.contextProvider);
            this.builderProvider3 = delayedWakeLock_Builder_Factory;
            this.scrimControllerProvider = DoubleCheck.provider(ScrimController_Factory.create(this.lightBarControllerProvider, this.dozeParametersProvider, daggerTvGlobalRootComponent19.provideAlarmManagerProvider, this.keyguardStateControllerImplProvider, delayedWakeLock_Builder_Factory, this.provideHandlerProvider, this.keyguardUpdateMonitorProvider, this.dockManagerImplProvider, this.provideConfigurationControllerProvider, daggerTvGlobalRootComponent19.provideMainExecutorProvider, this.screenOffAnimationControllerProvider, this.panelExpansionStateManagerProvider));
            DelegateFactory delegateFactory3 = new DelegateFactory();
            this.biometricUnlockControllerProvider = delegateFactory3;
            this.keyguardUnlockAnimationControllerProvider = DoubleCheck.provider(WMShellModule_ProvideAppPairsFactory.create(this.this$0.contextProvider, this.keyguardStateControllerImplProvider, this.newKeyguardViewMediatorProvider, this.statusBarKeyguardViewManagerProvider, this.featureFlagsReleaseProvider, delegateFactory3));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent20 = this.this$0;
            Provider<SessionTracker> provider15 = DoubleCheck.provider(new SessionTracker_Factory(daggerTvGlobalRootComponent20.contextProvider, daggerTvGlobalRootComponent20.provideIStatusBarServiceProvider, this.authControllerProvider, this.keyguardUpdateMonitorProvider, this.keyguardStateControllerImplProvider));
            Provider<SessionTracker> provider16 = provider15;
            this.sessionTrackerProvider = provider15;
            Provider<BiometricUnlockController> provider17 = this.biometricUnlockControllerProvider;
            Provider<DozeScrimController> provider18 = this.dozeScrimControllerProvider;
            Provider<KeyguardViewMediator> provider19 = this.newKeyguardViewMediatorProvider;
            Provider<ScrimController> provider20 = this.scrimControllerProvider;
            Provider<ShadeControllerImpl> provider21 = this.shadeControllerImplProvider;
            Provider<NotificationShadeWindowControllerImpl> provider22 = this.notificationShadeWindowControllerImplProvider;
            Provider<KeyguardStateControllerImpl> provider23 = this.keyguardStateControllerImplProvider;
            Provider<Handler> provider24 = this.provideHandlerProvider;
            Provider<KeyguardUpdateMonitor> provider25 = this.keyguardUpdateMonitorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent21 = this.this$0;
            DelegateFactory.setDelegate(provider17, DoubleCheck.provider(BiometricUnlockController_Factory.create(provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, daggerTvGlobalRootComponent21.provideResourcesProvider, this.keyguardBypassControllerProvider, this.dozeParametersProvider, this.provideMetricsLoggerProvider, daggerTvGlobalRootComponent21.dumpManagerProvider, daggerTvGlobalRootComponent21.providePowerManagerProvider, this.provideNotificationMediaManagerProvider, this.wakefulnessLifecycleProvider, daggerTvGlobalRootComponent21.screenLifecycleProvider, this.authControllerProvider, this.statusBarStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, provider16, daggerTvGlobalRootComponent21.provideLatencyTrackerProvider)));
            Provider<WallpaperController> provider26 = DoubleCheck.provider(new WallpaperController_Factory(this.this$0.provideWallpaperManagerProvider, 0));
            this.wallpaperControllerProvider = provider26;
            this.notificationShadeDepthControllerProvider = DoubleCheck.provider(NotificationShadeDepthController_Factory.create(this.statusBarStateControllerImplProvider, this.blurUtilsProvider, this.biometricUnlockControllerProvider, this.keyguardStateControllerImplProvider, this.providesChoreographerProvider, provider26, this.notificationShadeWindowControllerImplProvider, this.dozeParametersProvider, this.this$0.dumpManagerProvider));
            this.navigationBarOverlayControllerProvider = DoubleCheck.provider(new NavigationBarOverlayController_Factory(this.this$0.contextProvider, 0));
            Provider<DarkIconDispatcherImpl> provider27 = this.darkIconDispatcherImplProvider;
            Provider<BatteryController> provider28 = this.provideBatteryControllerProvider;
            Provider<NavigationModeController> provider29 = this.navigationModeControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent22 = this.this$0;
            this.factoryProvider = new LightBarController_Factory_Factory(provider27, provider28, provider29, daggerTvGlobalRootComponent22.dumpManagerProvider);
            this.factoryProvider2 = new AutoHideController_Factory_Factory(daggerTvGlobalRootComponent22.provideMainHandlerProvider, daggerTvGlobalRootComponent22.provideIWindowManagerProvider);
            this.setBackAnimationProvider = InstanceFactory.create(optional16);
            Provider<AssistManager> provider30 = this.assistManagerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent23 = this.this$0;
            Provider<Handler> provider31 = daggerTvGlobalRootComponent23.provideMainHandlerProvider;
            Provider<NavigationBarOverlayController> provider32 = this.navigationBarOverlayControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent24 = this.this$0;
            NavigationBar_Factory_Factory create2 = NavigationBar_Factory_Factory.create(provider30, daggerTvGlobalRootComponent23.provideAccessibilityManagerProvider, this.providesDeviceProvisionedControllerProvider, this.provideMetricsLoggerProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.accessibilityButtonModeObserverProvider, this.statusBarStateControllerImplProvider, this.provideSysUiStateProvider, this.providesBroadcastDispatcherProvider, this.provideCommandQueueProvider, this.setPipProvider, this.setLegacySplitScreenProvider, this.optionalOfRecentsProvider, this.optionalOfStatusBarProvider, this.shadeControllerImplProvider, this.provideNotificationRemoteInputManagerProvider, this.notificationShadeDepthControllerProvider, provider31, provider32, daggerTvGlobalRootComponent24.provideUiEventLoggerProvider, this.navBarHelperProvider, this.lightBarControllerProvider, this.factoryProvider, this.provideAutoHideControllerProvider, this.factoryProvider2, daggerTvGlobalRootComponent24.provideOptionalTelecomManagerProvider, daggerTvGlobalRootComponent24.provideInputMethodManagerProvider, this.setBackAnimationProvider);
            this.factoryProvider3 = create2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent25 = this.this$0;
            this.navigationBarControllerProvider = DoubleCheck.provider(NavigationBarController_Factory.create(daggerTvGlobalRootComponent25.contextProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.provideSysUiStateProvider, this.provideCommandQueueProvider, daggerTvGlobalRootComponent25.provideMainHandlerProvider, this.provideConfigurationControllerProvider, this.navBarHelperProvider, daggerTvGlobalRootComponent25.taskbarDelegateProvider, create2, this.statusBarKeyguardViewManagerProvider, daggerTvGlobalRootComponent25.dumpManagerProvider, this.provideAutoHideControllerProvider, this.lightBarControllerProvider, this.setPipProvider, this.setBackAnimationProvider));
            this.setSplitScreenProvider = InstanceFactory.create(optional3);
            this.setOneHandedProvider = InstanceFactory.create(optional5);
            this.setRecentTasksProvider = InstanceFactory.create(optional13);
            this.setStartingSurfaceProvider = InstanceFactory.create(optional10);
            InstanceFactory create3 = InstanceFactory.create(shellTransitions);
            InstanceFactory instanceFactory = create3;
            this.setTransitionsProvider = create3;
            Provider<OverviewProxyService> provider33 = this.overviewProxyServiceProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent26 = this.this$0;
            DelegateFactory.setDelegate(provider33, DoubleCheck.provider(OverviewProxyService_Factory.create(daggerTvGlobalRootComponent26.contextProvider, this.provideCommandQueueProvider, this.navigationBarControllerProvider, this.optionalOfStatusBarProvider, this.navigationModeControllerProvider, this.notificationShadeWindowControllerImplProvider, this.provideSysUiStateProvider, this.setPipProvider, this.setLegacySplitScreenProvider, this.setSplitScreenProvider, this.setOneHandedProvider, this.setRecentTasksProvider, this.setBackAnimationProvider, this.setStartingSurfaceProvider, this.providesBroadcastDispatcherProvider, instanceFactory, daggerTvGlobalRootComponent26.screenLifecycleProvider, daggerTvGlobalRootComponent26.provideUiEventLoggerProvider, this.keyguardUnlockAnimationControllerProvider, this.this$0.dumpManagerProvider)));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent27 = this.this$0;
            Provider<AssistLogger> provider34 = DoubleCheck.provider(new AssistLogger_Factory(daggerTvGlobalRootComponent27.contextProvider, daggerTvGlobalRootComponent27.provideUiEventLoggerProvider, this.provideAssistUtilsProvider, this.phoneStateMonitorProvider, 0));
            this.assistLoggerProvider = provider34;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent28 = this.this$0;
            Provider<DefaultUiController> provider35 = DoubleCheck.provider(DefaultUiController_Factory.create(daggerTvGlobalRootComponent28.contextProvider, provider34, daggerTvGlobalRootComponent28.provideWindowManagerProvider, this.provideMetricsLoggerProvider, this.assistManagerProvider));
            this.defaultUiControllerProvider = provider35;
            Provider<AssistManager> provider36 = this.assistManagerProvider;
            Provider<DeviceProvisionedController> provider37 = this.providesDeviceProvisionedControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent29 = this.this$0;
            DelegateFactory.setDelegate(provider36, DoubleCheck.provider(new AssistManager_Factory(provider37, daggerTvGlobalRootComponent29.contextProvider, this.provideAssistUtilsProvider, this.provideCommandQueueProvider, this.phoneStateMonitorProvider, this.overviewProxyServiceProvider, this.provideSysUiStateProvider, provider35, this.assistLoggerProvider, daggerTvGlobalRootComponent29.provideMainHandlerProvider)));
            DelegateFactory.setDelegate(this.shadeControllerImplProvider, DoubleCheck.provider(ShadeControllerImpl_Factory.create(this.provideCommandQueueProvider, this.statusBarStateControllerImplProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.this$0.provideWindowManagerProvider, this.optionalOfStatusBarProvider, this.assistManagerProvider, this.setBubblesProvider)));
            Provider<SystemUIDialogManager> provider38 = DoubleCheck.provider(new SystemUIDialogManager_Factory(this.this$0.dumpManagerProvider, this.statusBarKeyguardViewManagerProvider, 0));
            this.systemUIDialogManagerProvider = provider38;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent30 = this.this$0;
            QSCustomizerController_Factory create4 = QSCustomizerController_Factory.create(daggerTvGlobalRootComponent30.contextProvider, daggerTvGlobalRootComponent30.provideMediaSessionManagerProvider, this.provideLocalBluetoothControllerProvider, this.shadeControllerImplProvider, this.provideActivityStarterProvider, this.provideCommonNotifCollectionProvider, daggerTvGlobalRootComponent30.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, provider38);
            this.mediaOutputDialogFactoryProvider = create4;
            DelegateFactory delegateFactory4 = new DelegateFactory();
            this.mediaCarouselControllerProvider = delegateFactory4;
            MediaControlPanel_Factory create5 = MediaControlPanel_Factory.create(this.this$0.contextProvider, this.provideBackgroundExecutorProvider, this.provideActivityStarterProvider, this.mediaViewControllerProvider, this.seekBarViewModelProvider, this.mediaDataManagerProvider, create4, delegateFactory4, this.falsingManagerProxyProvider, this.mediaFlagsProvider, this.bindSystemClockProvider);
            this.mediaControlPanelProvider = create5;
            Provider<MediaCarouselController> provider39 = this.mediaCarouselControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent31 = this.this$0;
            DelegateFactory.setDelegate(provider39, DoubleCheck.provider(CastTile_Factory.create(daggerTvGlobalRootComponent31.contextProvider, create5, this.visualStabilityProvider, this.mediaHostStatesManagerProvider, this.provideActivityStarterProvider, this.bindSystemClockProvider, daggerTvGlobalRootComponent31.provideMainDelayableExecutorProvider, this.mediaDataManagerProvider, this.provideConfigurationControllerProvider, this.falsingCollectorImplProvider, this.falsingManagerProxyProvider, daggerTvGlobalRootComponent31.dumpManagerProvider, this.mediaFlagsProvider)));
            Provider<DreamOverlayStateController> provider40 = DoubleCheck.provider(new DreamOverlayStateController_Factory(this.this$0.provideMainExecutorProvider, 0));
            this.dreamOverlayStateControllerProvider = provider40;
            Provider<MediaHierarchyManager> provider41 = DoubleCheck.provider(MediaHierarchyManager_Factory.create(this.this$0.contextProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardBypassControllerProvider, this.mediaCarouselControllerProvider, this.notificationLockscreenUserManagerImplProvider, this.provideConfigurationControllerProvider, this.wakefulnessLifecycleProvider, this.statusBarKeyguardViewManagerProvider, provider40));
            this.mediaHierarchyManagerProvider = provider41;
            Provider<MediaHost> provider42 = DoubleCheck.provider(new MediaModule_ProvidesKeyguardMediaHostFactory(provider41, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesKeyguardMediaHostProvider = provider42;
            this.keyguardMediaControllerProvider = DoubleCheck.provider(KeyguardMediaController_Factory.create(provider42, this.keyguardBypassControllerProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.this$0.contextProvider, this.provideConfigurationControllerProvider, this.mediaFlagsProvider));
            Provider<LogBuffer> provider43 = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.logBufferFactoryProvider, 3));
            this.provideNotificationSectionLogBufferProvider = provider43;
            this.notificationSectionsLoggerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(provider43, 2));
            C16845 r12 = new Provider<SectionHeaderControllerSubcomponent.Builder>() {
                public final SectionHeaderControllerSubcomponent.Builder get() {
                    return new SectionHeaderControllerSubcomponentBuilder();
                }
            };
            this.sectionHeaderControllerSubcomponentBuilderProvider = r12;
            Provider<SectionHeaderControllerSubcomponent> provider44 = DoubleCheck.provider(new C1291xb614d321(r12));
            this.providesIncomingHeaderSubcomponentProvider = provider44;
            this.providesIncomingHeaderControllerProvider = new DozeAuthRemover_Factory(provider44, 2);
            Provider<SectionHeaderControllerSubcomponent> provider45 = DoubleCheck.provider(new C1292x39c1fe98(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesPeopleHeaderSubcomponentProvider = provider45;
            this.providesPeopleHeaderControllerProvider = new ColorChangeHandler_Factory(provider45, 3);
            Provider<SectionHeaderControllerSubcomponent> provider46 = DoubleCheck.provider(new C1290x3fd4641(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesAlertingHeaderSubcomponentProvider = provider46;
            this.providesAlertingHeaderControllerProvider = new TypeClassifier_Factory(provider46, 5);
            this.providesSilentHeaderSubcomponentProvider = DoubleCheck.provider(new C1293x34a20792(this.sectionHeaderControllerSubcomponentBuilderProvider));
        }

        public final void initialize4(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            FrameworkServicesModule_ProvideFaceManagerFactory frameworkServicesModule_ProvideFaceManagerFactory = new FrameworkServicesModule_ProvideFaceManagerFactory(this.providesSilentHeaderSubcomponentProvider, 1);
            this.providesSilentHeaderControllerProvider = frameworkServicesModule_ProvideFaceManagerFactory;
            QuickQSPanelController_Factory create$1 = QuickQSPanelController_Factory.create$1(this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.keyguardMediaControllerProvider, this.notificationSectionsFeatureManagerProvider, this.notificationSectionsLoggerProvider, this.notifPipelineFlagsProvider, this.mediaContainerControllerProvider, this.providesIncomingHeaderControllerProvider, this.providesPeopleHeaderControllerProvider, this.providesAlertingHeaderControllerProvider, frameworkServicesModule_ProvideFaceManagerFactory);
            this.notificationSectionsManagerProvider = create$1;
            Provider<AmbientState> provider = DoubleCheck.provider(new AmbientState_Factory(this.this$0.contextProvider, create$1, this.keyguardBypassControllerProvider));
            this.ambientStateProvider = provider;
            Provider<StatusBarStateControllerImpl> provider2 = this.statusBarStateControllerImplProvider;
            Provider<LSShadeTransitionLogger> provider3 = this.lSShadeTransitionLoggerProvider;
            Provider<KeyguardBypassController> provider4 = this.keyguardBypassControllerProvider;
            Provider<NotificationLockscreenUserManagerImpl> provider5 = this.notificationLockscreenUserManagerImplProvider;
            Provider provider6 = this.falsingCollectorImplProvider;
            Provider<MediaHierarchyManager> provider7 = this.mediaHierarchyManagerProvider;
            Provider<ScrimController> provider8 = this.scrimControllerProvider;
            Provider<NotificationShadeDepthController> provider9 = this.notificationShadeDepthControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            Provider<LockscreenShadeTransitionController> provider10 = DoubleCheck.provider(LockscreenShadeTransitionController_Factory.create(provider2, provider3, provider4, provider5, provider6, provider, provider7, provider8, provider9, daggerTvGlobalRootComponent.contextProvider, this.wakefulnessLifecycleProvider, this.provideConfigurationControllerProvider, this.falsingManagerProxyProvider, daggerTvGlobalRootComponent.dumpManagerProvider));
            this.lockscreenShadeTransitionControllerProvider = provider10;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            this.pulseExpansionHandlerProvider = DoubleCheck.provider(PulseExpansionHandler_Factory.create(daggerTvGlobalRootComponent2.contextProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideHeadsUpManagerPhoneProvider, this.notificationRoundnessManagerProvider, this.provideConfigurationControllerProvider, this.statusBarStateControllerImplProvider, this.falsingManagerProxyProvider, provider10, this.falsingCollectorImplProvider, daggerTvGlobalRootComponent2.dumpManagerProvider));
            this.dynamicPrivacyControllerProvider = DoubleCheck.provider(new DismissTimer_Factory(this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, 1));
            StatusBarInitializer_Factory statusBarInitializer_Factory = new StatusBarInitializer_Factory(this.provideNotificationsLogBufferProvider, 2);
            this.shadeEventCoordinatorLoggerProvider = statusBarInitializer_Factory;
            Provider<ShadeEventCoordinator> provider11 = DoubleCheck.provider(new ShadeEventCoordinator_Factory(this.this$0.provideMainExecutorProvider, statusBarInitializer_Factory, 0));
            this.shadeEventCoordinatorProvider = provider11;
            ForegroundServicesDialog_Factory foregroundServicesDialog_Factory = new ForegroundServicesDialog_Factory(this.provideNotificationEntryManagerProvider, 4);
            this.legacyNotificationPresenterExtensionsProvider = foregroundServicesDialog_Factory;
            this.provideNotifShadeEventSourceProvider = DoubleCheck.provider(new StatusBarContentInsetsProvider_Factory(this.notifPipelineFlagsProvider, provider11, foregroundServicesDialog_Factory, 1));
            Provider<INotificationManager> provider12 = DoubleCheck.provider(new ActivityIntentHelper_Factory(dependencyProvider3, 6));
            this.provideINotificationManagerProvider = provider12;
            this.channelEditorDialogControllerProvider = DoubleCheck.provider(new ChannelEditorDialogController_Factory(this.this$0.contextProvider, provider12));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            this.assistantFeedbackControllerProvider = DoubleCheck.provider(new ScreenOnCoordinator_Factory(daggerTvGlobalRootComponent3.provideMainHandlerProvider, daggerTvGlobalRootComponent3.contextProvider, this.deviceConfigProxyProvider, 2));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            Provider<ZenModeControllerImpl> provider13 = DoubleCheck.provider(MediaDataFilter_Factory.create$2(daggerTvGlobalRootComponent4.contextProvider, daggerTvGlobalRootComponent4.provideMainHandlerProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent4.dumpManagerProvider, this.globalSettingsImplProvider));
            Provider<ZenModeControllerImpl> provider14 = provider13;
            this.zenModeControllerImplProvider = provider13;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            this.provideBubblesManagerProvider = DoubleCheck.provider(SystemUIModule_ProvideBubblesManagerFactory.create(daggerTvGlobalRootComponent5.contextProvider, this.setBubblesProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarStateControllerImplProvider, this.shadeControllerImplProvider, this.provideConfigurationControllerProvider, daggerTvGlobalRootComponent5.provideIStatusBarServiceProvider, this.provideINotificationManagerProvider, this.provideNotificationVisibilityProvider, this.notificationInterruptStateProviderImplProvider, provider14, this.notificationLockscreenUserManagerImplProvider, this.notificationGroupManagerLegacyProvider, this.provideNotificationEntryManagerProvider, this.provideCommonNotifCollectionProvider, this.notifPipelineProvider, this.provideSysUiStateProvider, this.notifPipelineFlagsProvider, daggerTvGlobalRootComponent5.dumpManagerProvider, daggerTvGlobalRootComponent5.provideMainExecutorProvider));
            this.provideDelayableExecutorProvider = DoubleCheck.provider(new ActionClickLogger_Factory(this.provideBgLooperProvider, 3));
            Provider<NotifPanelEventSource> provider15 = DoubleCheck.provider(NotifPanelEventSourceModule_ProvideManagerFactory.InstanceHolder.INSTANCE);
            this.bindEventSourceProvider = provider15;
            Provider<VisualStabilityCoordinator> provider16 = DoubleCheck.provider(VisualStabilityCoordinator_Factory.create(this.provideDelayableExecutorProvider, this.this$0.dumpManagerProvider, this.provideHeadsUpManagerPhoneProvider, provider15, this.statusBarStateControllerImplProvider, this.visualStabilityProvider, this.wakefulnessLifecycleProvider));
            this.visualStabilityCoordinatorProvider = provider16;
            Provider<OnUserInteractionCallback> provider17 = DoubleCheck.provider(OverlayToggleTile_Factory.create(this.notifPipelineFlagsProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, this.notifCollectionProvider, this.provideNotificationVisibilityProvider, provider16, this.provideNotificationEntryManagerProvider, this.provideVisualStabilityManagerProvider, this.provideGroupMembershipManagerProvider));
            Provider<OnUserInteractionCallback> provider18 = provider17;
            this.provideOnUserInteractionCallbackProvider = provider17;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            this.provideNotificationGutsManagerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationGutsManagerFactory.create(daggerTvGlobalRootComponent6.contextProvider, this.optionalOfStatusBarProvider, daggerTvGlobalRootComponent6.provideMainHandlerProvider, this.provideBgHandlerProvider, daggerTvGlobalRootComponent6.provideAccessibilityManagerProvider, this.highPriorityProvider, this.provideINotificationManagerProvider, this.provideNotificationEntryManagerProvider, this.peopleSpaceWidgetManagerProvider, daggerTvGlobalRootComponent6.provideLauncherAppsProvider, daggerTvGlobalRootComponent6.provideShortcutManagerProvider, this.channelEditorDialogControllerProvider, this.provideUserTrackerProvider, this.assistantFeedbackControllerProvider, this.provideBubblesManagerProvider, daggerTvGlobalRootComponent6.provideUiEventLoggerProvider, provider18, this.shadeControllerImplProvider, daggerTvGlobalRootComponent6.dumpManagerProvider));
            this.expansionStateLoggerProvider = new NotificationLogger_ExpansionStateLogger_Factory(this.provideUiBackgroundExecutorProvider);
            Provider<NotificationPanelLogger> provider19 = DoubleCheck.provider(NotificationsModule_ProvideNotificationPanelLoggerFactory.InstanceHolder.INSTANCE);
            this.provideNotificationPanelLoggerProvider = provider19;
            this.provideNotificationLoggerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationLoggerFactory.create(this.notificationListenerProvider, this.provideUiBackgroundExecutorProvider, this.notifPipelineFlagsProvider, this.notifLiveDataStoreImplProvider, this.provideNotificationVisibilityProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.statusBarStateControllerImplProvider, this.expansionStateLoggerProvider, provider19));
            Provider<ForegroundServiceSectionController> provider20 = DoubleCheck.provider(new DependencyProvider_ProvidesViewMediatorCallbackFactory(this.provideNotificationEntryManagerProvider, this.foregroundServiceDismissalFeatureControllerProvider, 1));
            Provider<ForegroundServiceSectionController> provider21 = provider20;
            this.foregroundServiceSectionControllerProvider = provider20;
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory = r3;
            QSFragmentModule_ProvidesQuickQSPanelFactory qSFragmentModule_ProvidesQuickQSPanelFactory2 = new QSFragmentModule_ProvidesQuickQSPanelFactory(this.rowContentBindStageProvider, 4);
            this.dynamicChildBindControllerProvider = qSFragmentModule_ProvidesQuickQSPanelFactory2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.provideNotificationViewHierarchyManagerProvider = DoubleCheck.provider(C1210x3f8faa0a.create(daggerTvGlobalRootComponent7.contextProvider, daggerTvGlobalRootComponent7.provideMainHandlerProvider, this.featureFlagsReleaseProvider, this.notificationLockscreenUserManagerImplProvider, this.notificationGroupManagerLegacyProvider, this.provideVisualStabilityManagerProvider, this.statusBarStateControllerImplProvider, this.provideNotificationEntryManagerProvider, this.keyguardBypassControllerProvider, this.setBubblesProvider, this.dynamicPrivacyControllerProvider, provider21, qSFragmentModule_ProvidesQuickQSPanelFactory, this.lowPriorityInflationHelperProvider, this.assistantFeedbackControllerProvider, this.notifPipelineFlagsProvider, this.keyguardUpdateMonitorProvider, this.keyguardStateControllerImplProvider));
            this.userSwitcherControllerProvider = new DelegateFactory();
            this.provideAccessibilityFloatingMenuControllerProvider = DoubleCheck.provider(new C0774xb22f7179(dependencyProvider, this.this$0.contextProvider, this.accessibilityButtonTargetsObserverProvider, this.accessibilityButtonModeObserverProvider, this.keyguardUpdateMonitorProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            this.lockscreenWallpaperProvider = DoubleCheck.provider(LockscreenWallpaper_Factory.create(daggerTvGlobalRootComponent8.provideWallpaperManagerProvider, this.keyguardUpdateMonitorProvider, daggerTvGlobalRootComponent8.dumpManagerProvider, this.provideNotificationMediaManagerProvider, daggerTvGlobalRootComponent8.provideMainHandlerProvider));
            Provider<NotificationIconAreaController> provider22 = DoubleCheck.provider(NotificationIconAreaController_Factory.create(this.this$0.contextProvider, this.statusBarStateControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideNotificationMediaManagerProvider, this.notificationListenerProvider, this.dozeParametersProvider, this.setBubblesProvider, this.provideDemoModeControllerProvider, this.darkIconDispatcherImplProvider, this.statusBarWindowControllerProvider, this.screenOffAnimationControllerProvider));
            this.notificationIconAreaControllerProvider = provider22;
            this.dozeServiceHostProvider = DoubleCheck.provider(DozeServiceHost_Factory.create(this.dozeLogProvider, this.this$0.providePowerManagerProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, this.providesDeviceProvisionedControllerProvider, this.provideHeadsUpManagerPhoneProvider, this.provideBatteryControllerProvider, this.scrimControllerProvider, this.biometricUnlockControllerProvider, this.newKeyguardViewMediatorProvider, this.assistManagerProvider, this.dozeScrimControllerProvider, this.keyguardUpdateMonitorProvider, this.pulseExpansionHandlerProvider, this.notificationShadeWindowControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.authControllerProvider, provider22));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            this.screenPinningRequestProvider = new ScreenPinningRequest_Factory(daggerTvGlobalRootComponent9.contextProvider, this.optionalOfStatusBarProvider, 0);
            this.ringerModeTrackerImplProvider = DoubleCheck.provider(new RingerModeTrackerImpl_Factory(daggerTvGlobalRootComponent9.provideAudioManagerProvider, this.providesBroadcastDispatcherProvider, this.provideBackgroundExecutorProvider, 0));
            Provider<VibratorHelper> provider23 = DoubleCheck.provider(new VibratorHelper_Factory(this.this$0.provideVibratorProvider, this.provideBackgroundExecutorProvider, 0));
            this.vibratorHelperProvider = provider23;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            this.volumeDialogControllerImplProvider = DoubleCheck.provider(VolumeDialogControllerImpl_Factory.create(daggerTvGlobalRootComponent10.contextProvider, this.providesBroadcastDispatcherProvider, this.ringerModeTrackerImplProvider, daggerTvGlobalRootComponent10.provideAudioManagerProvider, daggerTvGlobalRootComponent10.provideNotificationManagerProvider, provider23, daggerTvGlobalRootComponent10.provideIAudioServiceProvider, daggerTvGlobalRootComponent10.provideAccessibilityManagerProvider, daggerTvGlobalRootComponent10.providePackageManagerProvider, this.wakefulnessLifecycleProvider));
            Provider<AccessibilityManagerWrapper> m = C0771xb6bb24d9.m52m(this.this$0.provideAccessibilityManagerProvider, 5);
            this.accessibilityManagerWrapperProvider = m;
            VolumeModule_ProvideVolumeDialogFactory create = VolumeModule_ProvideVolumeDialogFactory.create(this.this$0.contextProvider, this.volumeDialogControllerImplProvider, m, this.providesDeviceProvisionedControllerProvider, this.provideConfigurationControllerProvider, this.mediaOutputDialogFactoryProvider, this.provideActivityStarterProvider);
            this.provideVolumeDialogProvider = create;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            this.volumeDialogComponentProvider = DoubleCheck.provider(VolumeDialogComponent_Factory.create(daggerTvGlobalRootComponent11.contextProvider, this.newKeyguardViewMediatorProvider, this.provideActivityStarterProvider, this.volumeDialogControllerImplProvider, this.provideDemoModeControllerProvider, daggerTvGlobalRootComponent11.pluginDependencyProvider, this.extensionControllerImplProvider, this.tunerServiceImplProvider, create));
            this.statusBarComponentFactoryProvider = new Provider<StatusBarComponent.Factory>() {
                public final StatusBarComponent.Factory get() {
                    return new StatusBarComponentFactory();
                }
            };
            Provider<GroupExpansionManager> provider24 = DoubleCheck.provider(new TvStatusBar_Factory(this.notifPipelineFlagsProvider, this.provideGroupMembershipManagerProvider, this.notificationGroupManagerLegacyProvider, 1));
            this.provideGroupExpansionManagerProvider = provider24;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            this.statusBarRemoteInputCallbackProvider = DoubleCheck.provider(StatusBarRemoteInputCallback_Factory.create(daggerTvGlobalRootComponent12.contextProvider, provider24, this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.provideActivityStarterProvider, this.shadeControllerImplProvider, this.provideCommandQueueProvider, this.actionClickLoggerProvider, daggerTvGlobalRootComponent12.provideMainExecutorProvider));
            this.activityIntentHelperProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.this$0.contextProvider, 0));
            ColorChangeHandler_Factory colorChangeHandler_Factory = r4;
            ColorChangeHandler_Factory colorChangeHandler_Factory2 = new ColorChangeHandler_Factory(this.provideNotifInteractionLogBufferProvider, 4);
            this.statusBarNotificationActivityStarterLoggerProvider = colorChangeHandler_Factory2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            this.builderProvider4 = DoubleCheck.provider(StatusBarNotificationActivityStarter_Builder_Factory.create(daggerTvGlobalRootComponent13.contextProvider, this.provideCommandQueueProvider, daggerTvGlobalRootComponent13.provideMainHandlerProvider, this.provideUiBackgroundExecutorProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.provideNotificationVisibilityProvider, this.provideHeadsUpManagerPhoneProvider, this.provideActivityStarterProvider, this.notificationClickNotifierProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, daggerTvGlobalRootComponent13.provideKeyguardManagerProvider, daggerTvGlobalRootComponent13.provideIDreamManagerProvider, this.provideBubblesManagerProvider, this.assistManagerProvider, this.provideNotificationRemoteInputManagerProvider, this.provideGroupMembershipManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.shadeControllerImplProvider, this.keyguardStateControllerImplProvider, this.notificationInterruptStateProviderImplProvider, this.provideLockPatternUtilsProvider, this.statusBarRemoteInputCallbackProvider, this.activityIntentHelperProvider, this.notifPipelineFlagsProvider, this.provideMetricsLoggerProvider, colorChangeHandler_Factory, this.provideOnUserInteractionCallbackProvider));
            this.providesViewMediatorCallbackProvider = new DelegateFactory();
            this.initControllerProvider = DoubleCheck.provider(InitController_Factory.InstanceHolder.INSTANCE);
            this.provideTimeTickHandlerProvider = DoubleCheck.provider(new ScreenLifecycle_Factory(dependencyProvider3, 7));
            this.userInfoControllerImplProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.this$0.contextProvider, 3));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            this.castControllerImplProvider = DoubleCheck.provider(new CastControllerImpl_Factory(daggerTvGlobalRootComponent14.contextProvider, daggerTvGlobalRootComponent14.dumpManagerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            this.hotspotControllerImplProvider = DoubleCheck.provider(new HotspotControllerImpl_Factory(daggerTvGlobalRootComponent15.contextProvider, daggerTvGlobalRootComponent15.provideMainHandlerProvider, this.provideBgHandlerProvider, daggerTvGlobalRootComponent15.dumpManagerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            this.bluetoothControllerImplProvider = DoubleCheck.provider(new BluetoothControllerImpl_Factory(daggerTvGlobalRootComponent16.contextProvider, daggerTvGlobalRootComponent16.dumpManagerProvider, this.provideBgLooperProvider, this.provideLocalBluetoothControllerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            this.nextAlarmControllerImplProvider = DoubleCheck.provider(new ToastFactory_Factory(daggerTvGlobalRootComponent17.provideAlarmManagerProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent17.dumpManagerProvider, 1));
            RotationPolicyWrapperImpl_Factory rotationPolicyWrapperImpl_Factory = new RotationPolicyWrapperImpl_Factory(this.this$0.contextProvider, this.secureSettingsImplProvider, 0);
            this.rotationPolicyWrapperImplProvider = rotationPolicyWrapperImpl_Factory;
            this.bindRotationPolicyWrapperProvider = DoubleCheck.provider(rotationPolicyWrapperImpl_Factory);
            Provider<DeviceStateRotationLockSettingsManager> provider25 = DoubleCheck.provider(new StatusBarInitializer_Factory(this.this$0.contextProvider, 5));
            this.provideAutoRotateSettingsManagerProvider = provider25;
            Provider<RotationPolicyWrapper> provider26 = this.bindRotationPolicyWrapperProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent18 = this.this$0;
            this.deviceStateRotationLockSettingControllerProvider = DoubleCheck.provider(new KeyguardSmartspaceController_Factory(provider26, daggerTvGlobalRootComponent18.provideDeviceStateManagerProvider, daggerTvGlobalRootComponent18.provideMainExecutorProvider, provider25, 1));
            WallpaperController_Factory wallpaperController_Factory = new WallpaperController_Factory(this.this$0.provideResourcesProvider, 3);
            this.providesDeviceStateRotationLockDefaultsProvider = wallpaperController_Factory;
            this.rotationLockControllerImplProvider = DoubleCheck.provider(new SetupWizard_Factory(this.bindRotationPolicyWrapperProvider, this.deviceStateRotationLockSettingControllerProvider, wallpaperController_Factory, 2));
            this.provideDataSaverControllerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(dependencyProvider3, this.networkControllerImplProvider));
            this.provideSensorPrivacyControllerProvider = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.this$0.provideSensorPrivacyManagerProvider, 6));
            this.recordingControllerProvider = DoubleCheck.provider(new UserCreator_Factory(this.providesBroadcastDispatcherProvider, this.provideUserTrackerProvider, 1));
            Provider<Context> provider27 = this.this$0.contextProvider;
            this.provideSharePreferencesProvider = new KeyguardLifecyclesDispatcher_Factory(dependencyProvider3, provider27);
            this.dateFormatUtilProvider = new DateFormatUtil_Factory(provider27, 0);
            Provider<LogBuffer> provider28 = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.logBufferFactoryProvider, 0));
            this.providePrivacyLogBufferProvider = provider28;
            PrivacyLogger_Factory privacyLogger_Factory = new PrivacyLogger_Factory(provider28, 0);
            this.privacyLoggerProvider = privacyLogger_Factory;
            Provider<AppOpsControllerImpl> provider29 = this.appOpsControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent19 = this.this$0;
            this.privacyItemControllerProvider = DoubleCheck.provider(PrivacyItemController_Factory.create(provider29, daggerTvGlobalRootComponent19.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.deviceConfigProxyProvider, this.provideUserTrackerProvider, privacyLogger_Factory, this.bindSystemClockProvider, daggerTvGlobalRootComponent19.dumpManagerProvider));
            Provider<StatusBarIconControllerImpl> provider30 = this.statusBarIconControllerImplProvider;
            Provider<CommandQueue> provider31 = this.provideCommandQueueProvider;
            Provider<BroadcastDispatcher> provider32 = this.providesBroadcastDispatcherProvider;
            Provider<Executor> provider33 = this.provideUiBackgroundExecutorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent20 = this.this$0;
            this.phoneStatusBarPolicyProvider = PhoneStatusBarPolicy_Factory.create(provider30, provider31, provider32, provider33, daggerTvGlobalRootComponent20.provideResourcesProvider, this.castControllerImplProvider, this.hotspotControllerImplProvider, this.bluetoothControllerImplProvider, this.nextAlarmControllerImplProvider, this.userInfoControllerImplProvider, this.rotationLockControllerImplProvider, this.provideDataSaverControllerProvider, this.zenModeControllerImplProvider, this.providesDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.locationControllerImplProvider, this.provideSensorPrivacyControllerProvider, daggerTvGlobalRootComponent20.provideIActivityManagerProvider, daggerTvGlobalRootComponent20.provideAlarmManagerProvider, daggerTvGlobalRootComponent20.provideUserManagerProvider, daggerTvGlobalRootComponent20.provideDevicePolicyManagerProvider, this.recordingControllerProvider, daggerTvGlobalRootComponent20.provideTelecomManagerProvider, daggerTvGlobalRootComponent20.provideDisplayIdProvider, this.provideSharePreferencesProvider, this.dateFormatUtilProvider, this.ringerModeTrackerImplProvider, this.privacyItemControllerProvider, this.privacyLoggerProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent21 = this.this$0;
            Provider<Context> provider34 = daggerTvGlobalRootComponent21.contextProvider;
            WakeLock_Builder_Factory wakeLock_Builder_Factory = r4;
            WakeLock_Builder_Factory wakeLock_Builder_Factory2 = new WakeLock_Builder_Factory(provider34);
            this.builderProvider5 = wakeLock_Builder_Factory2;
            this.keyguardIndicationControllerProvider = DoubleCheck.provider(KeyguardIndicationController_Factory.create(provider34, wakeLock_Builder_Factory, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.dockManagerImplProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent21.provideDevicePolicyManagerProvider, daggerTvGlobalRootComponent21.provideIBatteryStatsProvider, daggerTvGlobalRootComponent21.provideUserManagerProvider, daggerTvGlobalRootComponent21.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.falsingManagerProxyProvider, this.provideLockPatternUtilsProvider, daggerTvGlobalRootComponent21.screenLifecycleProvider, daggerTvGlobalRootComponent21.provideIActivityManagerProvider, this.keyguardBypassControllerProvider));
            this.statusBarTouchableRegionManagerProvider = DoubleCheck.provider(new StatusBarTouchableRegionManager_Factory(this.this$0.contextProvider, this.notificationShadeWindowControllerImplProvider, this.provideConfigurationControllerProvider, this.provideHeadsUpManagerPhoneProvider));
            this.factoryProvider4 = new DelegateFactory();
            this.ongoingCallLoggerProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(this.this$0.provideUiEventLoggerProvider, 2));
            Provider<LogBuffer> provider35 = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(this.logBufferFactoryProvider, 2));
            this.provideSwipeAwayGestureLogBufferProvider = provider35;
            SmartActionsReceiver_Factory smartActionsReceiver_Factory = new SmartActionsReceiver_Factory(provider35, 3);
            this.swipeStatusBarAwayGestureLoggerProvider = smartActionsReceiver_Factory;
            this.swipeStatusBarAwayGestureHandlerProvider = DoubleCheck.provider(new SwipeStatusBarAwayGestureHandler_Factory(this.this$0.contextProvider, this.statusBarWindowControllerProvider, smartActionsReceiver_Factory));
            Provider<OngoingCallFlags> provider36 = DoubleCheck.provider(new MediaFlags_Factory(this.featureFlagsReleaseProvider, 3));
            this.ongoingCallFlagsProvider = provider36;
            Provider<CommonNotifCollection> provider37 = this.provideCommonNotifCollectionProvider;
            Provider<SystemClock> provider38 = this.bindSystemClockProvider;
            Provider<ActivityStarter> provider39 = this.provideActivityStarterProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent22 = this.this$0;
            this.provideOngoingCallControllerProvider = DoubleCheck.provider(StatusBarDependenciesModule_ProvideOngoingCallControllerFactory.create(provider37, provider38, provider39, daggerTvGlobalRootComponent22.provideMainExecutorProvider, daggerTvGlobalRootComponent22.provideIActivityManagerProvider, this.ongoingCallLoggerProvider, daggerTvGlobalRootComponent22.dumpManagerProvider, this.statusBarWindowControllerProvider, this.swipeStatusBarAwayGestureHandlerProvider, this.statusBarStateControllerImplProvider, provider36));
            Provider<CommandQueue> provider40 = this.provideCommandQueueProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent23 = this.this$0;
            this.statusBarHideIconsForBouncerManagerProvider = DoubleCheck.provider(new QSFgsManagerFooter_Factory(provider40, daggerTvGlobalRootComponent23.provideMainDelayableExecutorProvider, this.statusBarWindowStateControllerProvider, daggerTvGlobalRootComponent23.dumpManagerProvider, 1));
            this.providesMainMessageRouterProvider = new QSFragmentModule_ProvideThemedContextFactory(this.this$0.provideMainDelayableExecutorProvider, 4);
            this.provideActivityLaunchAnimatorProvider = DoubleCheck.provider(StatusBarDependenciesModule_ProvideActivityLaunchAnimatorFactory.InstanceHolder.INSTANCE);
            Provider<CommandRegistry> provider41 = this.commandRegistryProvider;
            Provider<BatteryController> provider42 = this.provideBatteryControllerProvider;
            Provider<ConfigurationController> provider43 = this.provideConfigurationControllerProvider;
            Provider<FeatureFlagsRelease> provider44 = this.featureFlagsReleaseProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent24 = this.this$0;
            this.wiredChargingRippleControllerProvider = DoubleCheck.provider(WiredChargingRippleController_Factory.create(provider41, provider42, provider43, provider44, daggerTvGlobalRootComponent24.contextProvider, daggerTvGlobalRootComponent24.provideWindowManagerProvider, this.bindSystemClockProvider, daggerTvGlobalRootComponent24.provideUiEventLoggerProvider));
            Provider<Context> provider45 = this.this$0.contextProvider;
            Provider<NotificationsController> provider46 = this.provideNotificationsControllerProvider;
            Provider<FragmentService> provider47 = this.fragmentServiceProvider;
            Provider<LightBarController> provider48 = this.lightBarControllerProvider;
            Provider<AutoHideController> provider49 = this.provideAutoHideControllerProvider;
            Provider<StatusBarWindowController> provider50 = this.statusBarWindowControllerProvider;
            Provider<StatusBarWindowStateController> provider51 = this.statusBarWindowStateControllerProvider;
            Provider<KeyguardUpdateMonitor> provider52 = this.keyguardUpdateMonitorProvider;
            Provider<StatusBarSignalPolicy> provider53 = this.statusBarSignalPolicyProvider;
            Provider<PulseExpansionHandler> provider54 = this.pulseExpansionHandlerProvider;
            Provider<NotificationWakeUpCoordinator> provider55 = this.notificationWakeUpCoordinatorProvider;
            Provider<KeyguardBypassController> provider56 = this.keyguardBypassControllerProvider;
            Provider<KeyguardStateControllerImpl> provider57 = this.keyguardStateControllerImplProvider;
            Provider<HeadsUpManagerPhone> provider58 = this.provideHeadsUpManagerPhoneProvider;
            Provider<DynamicPrivacyController> provider59 = this.dynamicPrivacyControllerProvider;
            Provider<FalsingManagerProxy> provider60 = this.falsingManagerProxyProvider;
            Provider provider61 = this.falsingCollectorImplProvider;
            Provider<BroadcastDispatcher> provider62 = this.providesBroadcastDispatcherProvider;
            Provider<NotifShadeEventSource> provider63 = this.provideNotifShadeEventSourceProvider;
            Provider<NotificationEntryManager> provider64 = this.provideNotificationEntryManagerProvider;
            Provider<NotificationGutsManager> provider65 = this.provideNotificationGutsManagerProvider;
            Provider<NotificationLogger> provider66 = this.provideNotificationLoggerProvider;
            Provider<NotificationInterruptStateProviderImpl> provider67 = this.notificationInterruptStateProviderImplProvider;
            Provider<NotificationViewHierarchyManager> provider68 = this.provideNotificationViewHierarchyManagerProvider;
            Provider<PanelExpansionStateManager> provider69 = this.panelExpansionStateManagerProvider;
            Provider<KeyguardViewMediator> provider70 = this.newKeyguardViewMediatorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent25 = this.this$0;
            Provider<ScreenLifecycle> provider71 = daggerTvGlobalRootComponent25.screenLifecycleProvider;
            Provider<WakefulnessLifecycle> provider72 = this.wakefulnessLifecycleProvider;
            Provider<StatusBarStateControllerImpl> provider73 = this.statusBarStateControllerImplProvider;
            Provider<Optional<BubblesManager>> provider74 = this.provideBubblesManagerProvider;
            Provider<Optional<Bubbles>> provider75 = this.setBubblesProvider;
            Provider<VisualStabilityManager> provider76 = this.provideVisualStabilityManagerProvider;
            Provider<DeviceProvisionedController> provider77 = this.providesDeviceProvisionedControllerProvider;
            Provider<NavigationBarController> provider78 = this.navigationBarControllerProvider;
            Provider<AccessibilityFloatingMenuController> provider79 = this.provideAccessibilityFloatingMenuControllerProvider;
            Provider<AssistManager> provider80 = this.assistManagerProvider;
            Provider<ConfigurationController> provider81 = this.provideConfigurationControllerProvider;
            Provider<NotificationShadeWindowControllerImpl> provider82 = this.notificationShadeWindowControllerImplProvider;
            Provider<DozeParameters> provider83 = this.dozeParametersProvider;
            Provider<ScrimController> provider84 = this.scrimControllerProvider;
            Provider<LockscreenWallpaper> provider85 = this.lockscreenWallpaperProvider;
            Provider<LockscreenGestureLogger> provider86 = this.lockscreenGestureLoggerProvider;
            Provider<BiometricUnlockController> provider87 = this.biometricUnlockControllerProvider;
            Provider<DozeServiceHost> provider88 = this.dozeServiceHostProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent26 = this.this$0;
            Provider<PluginManager> provider89 = daggerTvGlobalRootComponent26.providesPluginManagerProvider;
            Provider<Optional<LegacySplitScreen>> provider90 = this.setLegacySplitScreenProvider;
            Provider<StatusBarNotificationActivityStarter.Builder> provider91 = this.builderProvider4;
            Provider<ShadeControllerImpl> provider92 = this.shadeControllerImplProvider;
            Provider<StatusBarKeyguardViewManager> provider93 = this.statusBarKeyguardViewManagerProvider;
            Provider<ViewMediatorCallback> provider94 = this.providesViewMediatorCallbackProvider;
            Provider<InitController> provider95 = this.initControllerProvider;
            Provider<Handler> provider96 = this.provideTimeTickHandlerProvider;
            Provider<PluginDependencyProvider> provider97 = this.this$0.pluginDependencyProvider;
            Provider<KeyguardDismissUtil> provider98 = this.keyguardDismissUtilProvider;
            Provider<ExtensionControllerImpl> provider99 = this.extensionControllerImplProvider;
            Provider<UserInfoControllerImpl> provider100 = this.userInfoControllerImplProvider;
            Provider<PhoneStatusBarPolicy> provider101 = this.phoneStatusBarPolicyProvider;
            Provider<KeyguardIndicationController> provider102 = this.keyguardIndicationControllerProvider;
            Provider<DemoModeController> provider103 = this.provideDemoModeControllerProvider;
            Provider<NotificationShadeDepthController> provider104 = this.notificationShadeDepthControllerProvider;
            Provider<StatusBarTouchableRegionManager> provider105 = this.statusBarTouchableRegionManagerProvider;
            Provider<NotificationIconAreaController> provider106 = this.notificationIconAreaControllerProvider;
            Provider<BrightnessSliderController.Factory> provider107 = this.factoryProvider4;
            Provider<ScreenOffAnimationController> provider108 = this.screenOffAnimationControllerProvider;
            Provider<WallpaperController> provider109 = this.wallpaperControllerProvider;
            Provider<OngoingCallController> provider110 = this.provideOngoingCallControllerProvider;
            Provider<StatusBarHideIconsForBouncerManager> provider111 = this.statusBarHideIconsForBouncerManagerProvider;
            Provider<LockscreenShadeTransitionController> provider112 = this.lockscreenShadeTransitionControllerProvider;
            Provider<FeatureFlagsRelease> provider113 = this.featureFlagsReleaseProvider;
            Provider<KeyguardUnlockAnimationController> provider114 = this.keyguardUnlockAnimationControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent27 = this.this$0;
            Provider<StatusBar> provider115 = DoubleCheck.provider(StatusBarPhoneModule_ProvideStatusBarFactory.create(provider45, provider46, provider47, provider48, provider49, provider50, provider51, provider52, provider53, provider54, provider55, provider56, provider57, provider58, provider59, provider60, provider61, provider62, provider63, provider64, provider65, provider66, provider67, provider68, provider69, provider70, daggerTvGlobalRootComponent25.provideDisplayMetricsProvider, this.provideMetricsLoggerProvider, this.provideUiBackgroundExecutorProvider, this.provideNotificationMediaManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.provideNotificationRemoteInputManagerProvider, this.userSwitcherControllerProvider, this.networkControllerImplProvider, this.provideBatteryControllerProvider, this.sysuiColorExtractorProvider, provider71, provider72, provider73, provider74, provider75, provider76, provider77, provider78, provider79, provider80, provider81, provider82, provider83, provider84, provider85, provider86, provider87, provider88, daggerTvGlobalRootComponent26.providePowerManagerProvider, this.screenPinningRequestProvider, this.dozeScrimControllerProvider, this.volumeDialogComponentProvider, this.provideCommandQueueProvider, this.statusBarComponentFactoryProvider, provider89, provider90, provider91, provider92, provider93, provider94, provider95, provider96, provider97, provider98, provider99, provider100, provider101, provider102, provider103, provider104, provider105, provider106, provider107, provider108, provider109, provider110, provider111, provider112, provider113, provider114, daggerTvGlobalRootComponent27.provideMainHandlerProvider, daggerTvGlobalRootComponent27.provideMainDelayableExecutorProvider, this.providesMainMessageRouterProvider, daggerTvGlobalRootComponent27.provideWallpaperManagerProvider, this.setStartingSurfaceProvider, this.provideActivityLaunchAnimatorProvider, this.notifPipelineFlagsProvider, daggerTvGlobalRootComponent27.provideInteractionJankMonitorProvider, daggerTvGlobalRootComponent27.provideDeviceStateManagerProvider, this.dreamOverlayStateControllerProvider, this.wiredChargingRippleControllerProvider));
            this.provideStatusBarProvider = provider115;
            DelegateFactory.setDelegate(this.optionalOfStatusBarProvider, new PresentJdkOptionalInstanceProvider(provider115));
            Provider<ActivityStarterDelegate> provider116 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.optionalOfStatusBarProvider, 0));
            this.activityStarterDelegateProvider = provider116;
            DelegateFactory.setDelegate(this.provideActivityStarterProvider, new CommunalSourceMonitor_Factory(provider116, this.this$0.pluginDependencyProvider, 1));
            Provider<UserSwitcherController> provider117 = this.userSwitcherControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent28 = this.this$0;
            Provider<UiEventLogger> provider118 = daggerTvGlobalRootComponent28.provideUiEventLoggerProvider;
            Provider<FalsingManagerProxy> provider119 = this.falsingManagerProxyProvider;
            Provider<TelephonyListenerManager> provider120 = this.telephonyListenerManagerProvider;
            Provider provider121 = this.secureSettingsImplProvider;
            Provider<Executor> provider122 = this.provideBackgroundExecutorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent29 = this.this$0;
            DelegateFactory.setDelegate(provider117, DoubleCheck.provider(UserSwitcherController_Factory.create(daggerTvGlobalRootComponent28.contextProvider, daggerTvGlobalRootComponent28.provideIActivityManagerProvider, daggerTvGlobalRootComponent28.provideUserManagerProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider, this.providesDeviceProvisionedControllerProvider, daggerTvGlobalRootComponent28.provideDevicePolicyManagerProvider, daggerTvGlobalRootComponent28.provideMainHandlerProvider, this.provideActivityStarterProvider, this.providesBroadcastDispatcherProvider, provider118, provider119, provider120, provider121, provider122, daggerTvGlobalRootComponent29.provideMainExecutorProvider, daggerTvGlobalRootComponent29.provideInteractionJankMonitorProvider, daggerTvGlobalRootComponent29.provideLatencyTrackerProvider, daggerTvGlobalRootComponent29.dumpManagerProvider, this.shadeControllerImplProvider, this.provideDialogLaunchAnimatorProvider)));
            C16867 r2 = new Provider<KeyguardStatusViewComponent.Factory>() {
                public final KeyguardStatusViewComponent.Factory get() {
                    return new KeyguardStatusViewComponentFactory();
                }
            };
            this.keyguardStatusViewComponentFactoryProvider = r2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent30 = this.this$0;
            this.keyguardDisplayManagerProvider = new KeyguardDisplayManager_Factory(daggerTvGlobalRootComponent30.contextProvider, this.navigationBarControllerProvider, r2, this.provideUiBackgroundExecutorProvider);
            this.screenOnCoordinatorProvider = DoubleCheck.provider(new ScreenOnCoordinator_Factory(daggerTvGlobalRootComponent30.screenLifecycleProvider, this.provideSysUIUnfoldComponentProvider, daggerTvGlobalRootComponent30.provideExecutionProvider, 0));
            Provider<KeyguardViewMediator> provider123 = this.newKeyguardViewMediatorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent31 = this.this$0;
            DelegateFactory.setDelegate(provider123, DoubleCheck.provider(KeyguardModule_NewKeyguardViewMediatorFactory.create(daggerTvGlobalRootComponent31.contextProvider, this.falsingCollectorImplProvider, this.provideLockPatternUtilsProvider, this.providesBroadcastDispatcherProvider, this.statusBarKeyguardViewManagerProvider, this.dismissCallbackRegistryProvider, this.keyguardUpdateMonitorProvider, daggerTvGlobalRootComponent31.dumpManagerProvider, daggerTvGlobalRootComponent31.providePowerManagerProvider, this.this$0.provideTrustManagerProvider, this.userSwitcherControllerProvider, this.provideUiBackgroundExecutorProvider, this.deviceConfigProxyProvider, this.navigationModeControllerProvider, this.keyguardDisplayManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, this.screenOffAnimationControllerProvider, this.notificationShadeDepthControllerProvider, this.screenOnCoordinatorProvider, this.this$0.provideInteractionJankMonitorProvider, this.dreamOverlayStateControllerProvider, this.notificationShadeWindowControllerImplProvider, this.provideActivityLaunchAnimatorProvider)));
            DelegateFactory.setDelegate(this.providesViewMediatorCallbackProvider, new DependencyProvider_ProvidesViewMediatorCallbackFactory(dependencyProvider3, this.newKeyguardViewMediatorProvider));
            this.keyguardSecurityModelProvider = DoubleCheck.provider(new KeyguardSecurityModel_Factory(this.this$0.provideResourcesProvider, this.provideLockPatternUtilsProvider, this.keyguardUpdateMonitorProvider, 0));
            this.keyguardBouncerComponentFactoryProvider = new Provider<KeyguardBouncerComponent.Factory>() {
                public final KeyguardBouncerComponent.Factory get() {
                    return new KeyguardBouncerComponentFactory();
                }
            };
            this.factoryProvider5 = KeyguardBouncer_Factory_Factory.create(this.this$0.contextProvider, this.providesViewMediatorCallbackProvider, this.dismissCallbackRegistryProvider, this.falsingCollectorImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.keyguardBypassControllerProvider, this.this$0.provideMainHandlerProvider, this.keyguardSecurityModelProvider, this.keyguardBouncerComponentFactoryProvider);
            this.factoryProvider6 = new KeyguardMessageAreaController_Factory_Factory(this.keyguardUpdateMonitorProvider, this.provideConfigurationControllerProvider);
            DelegateFactory.setDelegate(this.statusBarKeyguardViewManagerProvider, DoubleCheck.provider(StatusBarKeyguardViewManager_Factory.create(this.this$0.contextProvider, this.providesViewMediatorCallbackProvider, this.provideLockPatternUtilsProvider, this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.keyguardUpdateMonitorProvider, this.dreamOverlayStateControllerProvider, this.navigationModeControllerProvider, this.dockManagerImplProvider, this.notificationShadeWindowControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideNotificationMediaManagerProvider, this.factoryProvider5, this.factoryProvider6, this.provideSysUIUnfoldComponentProvider, this.shadeControllerImplProvider, this.this$0.provideLatencyTrackerProvider)));
            this.udfpsHapticsSimulatorProvider = DoubleCheck.provider(new UdfpsHapticsSimulator_Factory(this.commandRegistryProvider, this.vibratorHelperProvider, this.keyguardUpdateMonitorProvider, 0));
            this.optionalOfUdfpsHbmProvider = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent32 = this.this$0;
            Provider<Context> provider124 = daggerTvGlobalRootComponent32.contextProvider;
            Provider<Execution> provider125 = daggerTvGlobalRootComponent32.provideExecutionProvider;
            Provider<LayoutInflater> provider126 = this.providerLayoutInflaterProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent33 = this.this$0;
            Provider<FingerprintManager> provider127 = daggerTvGlobalRootComponent33.providesFingerprintManagerProvider;
            Provider<WindowManager> provider128 = daggerTvGlobalRootComponent33.provideWindowManagerProvider;
            Provider<StatusBarStateControllerImpl> provider129 = this.statusBarStateControllerImplProvider;
            Provider<DelayableExecutor> provider130 = this.this$0.provideMainDelayableExecutorProvider;
            Provider<PanelExpansionStateManager> provider131 = this.panelExpansionStateManagerProvider;
            Provider<StatusBarKeyguardViewManager> provider132 = this.statusBarKeyguardViewManagerProvider;
            Provider<DumpManager> provider133 = this.this$0.dumpManagerProvider;
            Provider<KeyguardUpdateMonitor> provider134 = this.keyguardUpdateMonitorProvider;
            Provider<FalsingManagerProxy> provider135 = this.falsingManagerProxyProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent34 = this.this$0;
            Provider<PowerManager> provider136 = daggerTvGlobalRootComponent34.providePowerManagerProvider;
            Provider<AccessibilityManager> provider137 = daggerTvGlobalRootComponent34.provideAccessibilityManagerProvider;
            Provider<LockscreenShadeTransitionController> provider138 = this.lockscreenShadeTransitionControllerProvider;
            Provider<ScreenLifecycle> provider139 = this.this$0.screenLifecycleProvider;
            Provider<VibratorHelper> provider140 = this.vibratorHelperProvider;
            Provider<UdfpsHapticsSimulator> provider141 = this.udfpsHapticsSimulatorProvider;
            Provider<Optional<UdfpsHbmProvider>> provider142 = this.optionalOfUdfpsHbmProvider;
            Provider<KeyguardStateControllerImpl> provider143 = this.keyguardStateControllerImplProvider;
            Provider<KeyguardBypassController> provider144 = this.keyguardBypassControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent35 = this.this$0;
            this.udfpsControllerProvider = DoubleCheck.provider(UdfpsController_Factory.create(provider124, provider125, provider126, provider127, provider128, provider129, provider130, provider131, provider132, provider133, provider134, provider135, provider136, provider137, provider138, provider139, provider140, provider141, provider142, provider143, provider144, daggerTvGlobalRootComponent35.provideDisplayManagerProvider, daggerTvGlobalRootComponent35.provideMainHandlerProvider, this.provideConfigurationControllerProvider, this.bindSystemClockProvider, this.unlockedScreenOffAnimationControllerProvider, this.systemUIDialogManagerProvider, this.this$0.provideLatencyTrackerProvider, this.provideActivityLaunchAnimatorProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent36 = this.this$0;
            Provider<Context> provider145 = daggerTvGlobalRootComponent36.contextProvider;
            Provider<LayoutInflater> provider146 = this.providerLayoutInflaterProvider;
            Provider<FingerprintManager> provider147 = daggerTvGlobalRootComponent36.providesFingerprintManagerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent37 = this.this$0;
            Provider<WindowManager> provider148 = daggerTvGlobalRootComponent37.provideWindowManagerProvider;
            Provider<ActivityTaskManager> provider149 = daggerTvGlobalRootComponent37.provideActivityTaskManagerProvider;
            Provider<OverviewProxyService> provider150 = this.overviewProxyServiceProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent38 = this.this$0;
            this.sidefpsControllerProvider = DoubleCheck.provider(NfcTile_Factory.create(provider145, provider146, provider147, provider148, provider149, provider150, daggerTvGlobalRootComponent38.provideDisplayManagerProvider, daggerTvGlobalRootComponent38.provideMainDelayableExecutorProvider, daggerTvGlobalRootComponent38.provideMainHandlerProvider));
        }

        public final void initialize5(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            DependencyProvider dependencyProvider3 = dependencyProvider;
            Provider<AuthController> provider = this.authControllerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            DelegateFactory.setDelegate(provider, DoubleCheck.provider(AuthController_Factory.create(daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.provideExecutionProvider, this.provideCommandQueueProvider, daggerTvGlobalRootComponent.provideActivityTaskManagerProvider, daggerTvGlobalRootComponent.provideWindowManagerProvider, daggerTvGlobalRootComponent.providesFingerprintManagerProvider, daggerTvGlobalRootComponent.provideFaceManagerProvider, this.udfpsControllerProvider, this.sidefpsControllerProvider, daggerTvGlobalRootComponent.provideDisplayManagerProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, daggerTvGlobalRootComponent.provideMainHandlerProvider)));
            Provider<KeyguardUpdateMonitor> provider2 = this.keyguardUpdateMonitorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            DelegateFactory.setDelegate(provider2, DoubleCheck.provider(KeyguardUpdateMonitor_Factory.create(daggerTvGlobalRootComponent2.contextProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent2.dumpManagerProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent2.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, this.provideLockPatternUtilsProvider, this.authControllerProvider, this.telephonyListenerManagerProvider, daggerTvGlobalRootComponent2.provideInteractionJankMonitorProvider, daggerTvGlobalRootComponent2.provideLatencyTrackerProvider)));
            Provider<KeyguardStateControllerImpl> provider3 = this.keyguardStateControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            DelegateFactory.setDelegate(provider3, DoubleCheck.provider(GoogleAssistLogger_Factory.create$1(daggerTvGlobalRootComponent3.contextProvider, this.keyguardUpdateMonitorProvider, this.provideLockPatternUtilsProvider, this.keyguardUnlockAnimationControllerProvider, daggerTvGlobalRootComponent3.dumpManagerProvider)));
            BrightLineFalsingManager_Factory create = BrightLineFalsingManager_Factory.create(this.falsingDataProvider, this.provideMetricsLoggerProvider, this.namedSetOfFalsingClassifierProvider, this.singleTapClassifierProvider, this.doubleTapClassifierProvider, this.historyTrackerProvider, this.keyguardStateControllerImplProvider, this.this$0.provideAccessibilityManagerProvider);
            this.brightLineFalsingManagerProvider = create;
            Provider<FalsingManagerProxy> provider4 = this.falsingManagerProxyProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            DelegateFactory.setDelegate(provider4, DoubleCheck.provider(new FalsingManagerProxy_Factory(daggerTvGlobalRootComponent4.providesPluginManagerProvider, daggerTvGlobalRootComponent4.provideMainExecutorProvider, this.deviceConfigProxyProvider, daggerTvGlobalRootComponent4.dumpManagerProvider, create)));
            DelegateFactory.setDelegate(this.factoryProvider4, new BrightnessSliderController_Factory_Factory(this.falsingManagerProxyProvider));
            Provider<BroadcastDispatcher> provider5 = this.providesBroadcastDispatcherProvider;
            this.brightnessDialogProvider = new BrightnessDialog_Factory(provider5, this.factoryProvider4, this.provideBgHandlerProvider);
            this.usbDebuggingActivityProvider = new UsbDebuggingActivity_Factory(provider5, 0);
            this.usbDebuggingSecondaryUserActivityProvider = new UsbDebuggingSecondaryUserActivity_Factory(provider5, 0);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            Provider<Context> provider6 = daggerTvGlobalRootComponent5.contextProvider;
            UserCreator_Factory userCreator_Factory = new UserCreator_Factory(provider6, daggerTvGlobalRootComponent5.provideUserManagerProvider, 0);
            this.userCreatorProvider = userCreator_Factory;
            this.createUserActivityProvider = new CreateUserActivity_Factory(userCreator_Factory, daggerTvGlobalRootComponent5.provideIActivityManagerProvider);
            Provider<TvNotificationHandler> provider7 = DoubleCheck.provider(new ManagedProfileControllerImpl_Factory(provider6, this.notificationListenerProvider, 1));
            this.provideTvNotificationHandlerProvider = provider7;
            this.tvNotificationPanelActivityProvider = new FrameworkServicesModule_ProvideOptionalVibratorFactory(provider7, 2);
            this.peopleSpaceActivityProvider = new ImageTileSet_Factory(this.peopleSpaceWidgetManagerProvider, 4);
            this.imageExporterProvider = new ImageExporter_Factory(this.this$0.provideContentResolverProvider, 0);
            Provider<LongScreenshotData> provider8 = DoubleCheck.provider(LongScreenshotData_Factory.InstanceHolder.INSTANCE);
            this.longScreenshotDataProvider = provider8;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            this.longScreenshotActivityProvider = GoogleAssistLogger_Factory.create(daggerTvGlobalRootComponent6.provideUiEventLoggerProvider, this.imageExporterProvider, daggerTvGlobalRootComponent6.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, provider8);
            this.launchConversationActivityProvider = OpaEnabledReceiver_Factory.create(this.provideNotificationVisibilityProvider, this.provideCommonNotifCollectionProvider, this.provideBubblesManagerProvider, this.this$0.provideUserManagerProvider, this.provideCommandQueueProvider);
            Provider<IndividualSensorPrivacyController> provider9 = this.provideIndividualSensorPrivacyControllerProvider;
            this.sensorUseStartedActivityProvider = new SensorUseStartedActivity_Factory(provider9, this.keyguardStateControllerImplProvider, this.keyguardDismissUtilProvider, this.provideBgHandlerProvider);
            this.tvUnblockSensorActivityProvider = new ActivityStarterDelegate_Factory(provider9, 3);
            Provider<HdmiCecSetMenuLanguageHelper> provider10 = DoubleCheck.provider(new ControlsActivity_Factory(this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, 1));
            this.hdmiCecSetMenuLanguageHelperProvider = provider10;
            this.hdmiCecSetMenuLanguageActivityProvider = new ForegroundServicesDialog_Factory(provider10, 2);
            Provider<Executor> m = C0773x82ed519e.m54m(this.provideBgLooperProvider, 6);
            this.provideExecutorProvider = m;
            this.controlsListingControllerImplProvider = DoubleCheck.provider(new TouchInsideHandler_Factory(this.this$0.contextProvider, m, this.provideUserTrackerProvider, 1));
            this.controlsControllerImplProvider = new DelegateFactory();
            this.setTaskViewFactoryProvider = InstanceFactory.create(optional7);
            Provider<ControlsMetricsLoggerImpl> provider11 = DoubleCheck.provider(ControlsMetricsLoggerImpl_Factory.InstanceHolder.INSTANCE);
            this.controlsMetricsLoggerImplProvider = provider11;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.controlActionCoordinatorImplProvider = DoubleCheck.provider(ControlActionCoordinatorImpl_Factory.create(daggerTvGlobalRootComponent7.contextProvider, this.provideDelayableExecutorProvider, daggerTvGlobalRootComponent7.provideMainDelayableExecutorProvider, this.provideActivityStarterProvider, this.keyguardStateControllerImplProvider, this.setTaskViewFactoryProvider, provider11, this.vibratorHelperProvider));
            Provider<CustomIconCache> provider12 = DoubleCheck.provider(CustomIconCache_Factory.InstanceHolder.INSTANCE);
            this.customIconCacheProvider = provider12;
            Provider<ControlsControllerImpl> provider13 = this.controlsControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            this.controlsUiControllerImplProvider = DoubleCheck.provider(RotationLockTile_Factory.create(provider13, daggerTvGlobalRootComponent8.contextProvider, daggerTvGlobalRootComponent8.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsListingControllerImplProvider, this.provideSharePreferencesProvider, this.controlActionCoordinatorImplProvider, this.provideActivityStarterProvider, this.shadeControllerImplProvider, provider12, this.controlsMetricsLoggerImplProvider, this.keyguardStateControllerImplProvider));
            Provider<ControlsBindingControllerImpl> provider14 = DoubleCheck.provider(new KeyguardBiometricLockoutLogger_Factory(this.this$0.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider, 1));
            this.controlsBindingControllerImplProvider = provider14;
            Provider<Optional<ControlsFavoritePersistenceWrapper>> provider15 = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.optionalOfControlsFavoritePersistenceWrapperProvider = provider15;
            Provider<ControlsControllerImpl> provider16 = this.controlsControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            DelegateFactory.setDelegate(provider16, DoubleCheck.provider(ControlsControllerImpl_Factory.create(daggerTvGlobalRootComponent9.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsUiControllerImplProvider, provider14, this.controlsListingControllerImplProvider, this.providesBroadcastDispatcherProvider, provider15, daggerTvGlobalRootComponent9.dumpManagerProvider, this.provideUserTrackerProvider)));
            this.controlsProviderSelectorActivityProvider = ControlsProviderSelectorActivity_Factory.create(this.this$0.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.controlsListingControllerImplProvider, this.controlsControllerImplProvider, this.providesBroadcastDispatcherProvider, this.controlsUiControllerImplProvider);
            this.controlsFavoritingActivityProvider = SystemUIService_Factory.create$1(this.this$0.provideMainExecutorProvider, this.controlsControllerImplProvider, this.controlsListingControllerImplProvider, this.providesBroadcastDispatcherProvider, this.controlsUiControllerImplProvider);
            Provider<ControlsControllerImpl> provider17 = this.controlsControllerImplProvider;
            Provider<BroadcastDispatcher> provider18 = this.providesBroadcastDispatcherProvider;
            this.controlsEditingActivityProvider = new ControlsEditingActivity_Factory(provider17, provider18, this.customIconCacheProvider, this.controlsUiControllerImplProvider, 0);
            this.controlsRequestDialogProvider = new ActionProxyReceiver_Factory(provider17, provider18, this.controlsListingControllerImplProvider, 1);
            this.controlsActivityProvider = new ControlsActivity_Factory(this.controlsUiControllerImplProvider, this.providesBroadcastDispatcherProvider, 0);
            this.userSwitcherActivityProvider = GarbageMonitor_Factory.create(this.userSwitcherControllerProvider, this.providesBroadcastDispatcherProvider, this.providerLayoutInflaterProvider, this.falsingManagerProxyProvider, this.this$0.provideUserManagerProvider, this.shadeControllerImplProvider);
            Provider<KeyguardStateControllerImpl> provider19 = this.keyguardStateControllerImplProvider;
            Provider<KeyguardDismissUtil> provider20 = this.keyguardDismissUtilProvider;
            Provider<ActivityStarter> provider21 = this.provideActivityStarterProvider;
            Provider<Executor> provider22 = this.provideBackgroundExecutorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            this.walletActivityProvider = WalletActivity_Factory.create(provider19, provider20, provider21, provider22, daggerTvGlobalRootComponent10.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.falsingCollectorImplProvider, this.provideUserTrackerProvider, this.keyguardUpdateMonitorProvider, this.statusBarKeyguardViewManagerProvider, daggerTvGlobalRootComponent10.provideUiEventLoggerProvider);
            MapProviderFactory.Builder builder = new MapProviderFactory.Builder(21);
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
            builder.put(ControlsProviderSelectorActivity.class, this.controlsProviderSelectorActivityProvider);
            builder.put(ControlsFavoritingActivity.class, this.controlsFavoritingActivityProvider);
            builder.put(ControlsEditingActivity.class, this.controlsEditingActivityProvider);
            builder.put(ControlsRequestDialog.class, this.controlsRequestDialogProvider);
            builder.put(ControlsActivity.class, this.controlsActivityProvider);
            builder.put(UserSwitcherActivity.class, this.userSwitcherActivityProvider);
            builder.put(WalletActivity.class, this.walletActivityProvider);
            this.mapOfClassOfAndProviderOfActivityProvider = builder.build();
            C16889 r2 = new Provider<DozeComponent.Builder>() {
                public final DozeComponent.Builder get() {
                    return new DozeComponentFactory();
                }
            };
            this.dozeComponentBuilderProvider = r2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            this.dozeServiceProvider = new DozeService_Factory(r2, daggerTvGlobalRootComponent11.providesPluginManagerProvider);
            Provider<KeyguardLifecyclesDispatcher> provider23 = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(daggerTvGlobalRootComponent11.screenLifecycleProvider, this.wakefulnessLifecycleProvider, 0));
            this.keyguardLifecyclesDispatcherProvider = provider23;
            this.keyguardServiceProvider = new LatencyTester_Factory(this.newKeyguardViewMediatorProvider, provider23, this.setTransitionsProvider, 2);
            C167410 r22 = new Provider<DreamOverlayComponent.Factory>() {
                public final DreamOverlayComponent.Factory get() {
                    return new DreamOverlayComponentFactory();
                }
            };
            this.dreamOverlayComponentFactoryProvider = r22;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            this.dreamOverlayServiceProvider = new DreamOverlayService_Factory(daggerTvGlobalRootComponent12.contextProvider, daggerTvGlobalRootComponent12.provideMainExecutorProvider, r22, this.dreamOverlayStateControllerProvider, this.keyguardUpdateMonitorProvider);
            this.notificationListenerWithPluginsProvider = new WMShellBaseModule_ProvideRecentTasksFactory(this.this$0.providesPluginManagerProvider, 3);
            DelegateFactory delegateFactory = r2;
            DelegateFactory delegateFactory2 = new DelegateFactory();
            this.globalActionsComponentProvider = delegateFactory2;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            GlobalActionsDialogLite_Factory create2 = GlobalActionsDialogLite_Factory.create(daggerTvGlobalRootComponent13.contextProvider, delegateFactory, daggerTvGlobalRootComponent13.provideAudioManagerProvider, daggerTvGlobalRootComponent13.provideIDreamManagerProvider, daggerTvGlobalRootComponent13.provideDevicePolicyManagerProvider, this.provideLockPatternUtilsProvider, this.providesBroadcastDispatcherProvider, this.telephonyListenerManagerProvider, this.globalSettingsImplProvider, this.secureSettingsImplProvider, this.vibratorHelperProvider, daggerTvGlobalRootComponent13.provideResourcesProvider, this.provideConfigurationControllerProvider, this.keyguardStateControllerImplProvider, daggerTvGlobalRootComponent13.provideUserManagerProvider, daggerTvGlobalRootComponent13.provideTrustManagerProvider, daggerTvGlobalRootComponent13.provideIActivityManagerProvider, daggerTvGlobalRootComponent13.provideTelecomManagerProvider, this.provideMetricsLoggerProvider, this.sysuiColorExtractorProvider, daggerTvGlobalRootComponent13.provideIStatusBarServiceProvider, this.notificationShadeWindowControllerImplProvider, daggerTvGlobalRootComponent13.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, daggerTvGlobalRootComponent13.provideUiEventLoggerProvider, this.ringerModeTrackerImplProvider, this.provideSysUiStateProvider, daggerTvGlobalRootComponent13.provideMainHandlerProvider, this.this$0.providePackageManagerProvider, this.optionalOfStatusBarProvider, this.keyguardUpdateMonitorProvider, this.provideDialogLaunchAnimatorProvider, this.systemUIDialogManagerProvider);
            this.globalActionsDialogLiteProvider = create2;
            GlobalActionsImpl_Factory create3 = GlobalActionsImpl_Factory.create(this.this$0.contextProvider, this.provideCommandQueueProvider, create2, this.blurUtilsProvider, this.keyguardStateControllerImplProvider, this.providesDeviceProvisionedControllerProvider);
            this.globalActionsImplProvider = create3;
            DelegateFactory.setDelegate(this.globalActionsComponentProvider, DoubleCheck.provider(new GlobalActionsComponent_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.extensionControllerImplProvider, create3, this.statusBarKeyguardViewManagerProvider)));
            Provider<TaskStackChangeListeners> provider24 = DoubleCheck.provider(new QSFragmentModule_ProvidesQuickQSPanelFactory(dependencyProvider3, 6));
            this.provideTaskStackChangeListenersProvider = provider24;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            this.homeSoundEffectControllerProvider = DoubleCheck.provider(new ScreenRecordController_Factory(daggerTvGlobalRootComponent14.contextProvider, daggerTvGlobalRootComponent14.provideAudioManagerProvider, provider24, this.provideActivityManagerWrapperProvider, daggerTvGlobalRootComponent14.providePackageManagerProvider, 1));
            this.instantAppNotifierProvider = DoubleCheck.provider(new ClipboardListener_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.provideUiBackgroundExecutorProvider, this.setLegacySplitScreenProvider, 1));
            this.keyboardUIProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.this$0.contextProvider, 0));
            Provider<PowerNotificationWarnings> provider25 = DoubleCheck.provider(new PowerNotificationWarnings_Factory(this.this$0.contextProvider, this.provideActivityStarterProvider, 0));
            this.powerNotificationWarningsProvider = provider25;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            this.powerUIProvider = DoubleCheck.provider(PowerUI_Factory.create(daggerTvGlobalRootComponent15.contextProvider, this.providesBroadcastDispatcherProvider, this.provideCommandQueueProvider, this.optionalOfStatusBarProvider, provider25, this.enhancedEstimatesImplProvider, daggerTvGlobalRootComponent15.providePowerManagerProvider));
            this.ringtonePlayerProvider = C0770xb6bb24d8.m51m(this.this$0.contextProvider, 0);
            this.shortcutKeyDispatcherProvider = DoubleCheck.provider(new ShortcutKeyDispatcher_Factory(this.this$0.contextProvider, this.setLegacySplitScreenProvider, 0));
            this.sliceBroadcastRelayHandlerProvider = DoubleCheck.provider(new SliceBroadcastRelayHandler_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, 0));
            this.storageNotificationProvider = DoubleCheck.provider(new QSLogger_Factory(this.this$0.contextProvider, 4));
            Provider<LogBuffer> provider26 = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.logBufferFactoryProvider, 2));
            this.provideToastLogBufferProvider = provider26;
            ToastLogger_Factory toastLogger_Factory = new ToastLogger_Factory(provider26, 0);
            this.toastLoggerProvider = toastLogger_Factory;
            this.toastUIProvider = DoubleCheck.provider(new ToastUI_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.toastFactoryProvider, toastLogger_Factory));
            this.tvNotificationPanelProvider = DoubleCheck.provider(new ForegroundServiceController_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, 1));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            this.tvOngoingPrivacyChipProvider = DoubleCheck.provider(new LatencyTester_Factory(daggerTvGlobalRootComponent16.contextProvider, this.privacyItemControllerProvider, daggerTvGlobalRootComponent16.provideIWindowManagerProvider, 3));
            this.tvStatusBarProvider = DoubleCheck.provider(new TvStatusBar_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.assistManagerProvider, 0));
            this.volumeUIProvider = DoubleCheck.provider(new VolumeUI_Factory(this.this$0.contextProvider, this.volumeDialogComponentProvider, 0));
            this.vpnStatusObserverProvider = DoubleCheck.provider(new VpnStatusObserver_Factory(this.this$0.contextProvider, this.securityControllerImplProvider));
            this.providesModeSwitchesControllerProvider = DoubleCheck.provider(new DependencyProvider_ProvidesModeSwitchesControllerFactory(dependencyProvider3, (Provider) this.this$0.contextProvider));
            this.windowMagnificationProvider = DoubleCheck.provider(WindowMagnification_Factory.create(this.this$0.contextProvider, this.this$0.provideMainHandlerProvider, this.provideCommandQueueProvider, this.providesModeSwitchesControllerProvider, this.provideSysUiStateProvider, this.overviewProxyServiceProvider));
            this.setHideDisplayCutoutProvider = InstanceFactory.create(optional8);
            this.setShellCommandHandlerProvider = InstanceFactory.create(optional9);
            this.setCompatUIProvider = InstanceFactory.create(optional14);
            this.setDragAndDropProvider = InstanceFactory.create(optional15);
            this.wMShellProvider = DoubleCheck.provider(WMShell_Factory.create(this.this$0.contextProvider, this.setPipProvider, this.setLegacySplitScreenProvider, this.setSplitScreenProvider, this.setOneHandedProvider, this.setHideDisplayCutoutProvider, this.setShellCommandHandlerProvider, this.setCompatUIProvider, this.setDragAndDropProvider, this.provideCommandQueueProvider, this.provideConfigurationControllerProvider, this.keyguardUpdateMonitorProvider, this.navigationModeControllerProvider, this.this$0.screenLifecycleProvider, this.provideSysUiStateProvider, this.protoTracerProvider, this.wakefulnessLifecycleProvider, this.userInfoControllerImplProvider, this.this$0.provideMainExecutorProvider));
            MapProviderFactory.Builder builder2 = new MapProviderFactory.Builder(18);
            builder2.put(GlobalActionsComponent.class, this.globalActionsComponentProvider);
            builder2.put(HomeSoundEffectController.class, this.homeSoundEffectControllerProvider);
            builder2.put(InstantAppNotifier.class, this.instantAppNotifierProvider);
            builder2.put(KeyboardUI.class, this.keyboardUIProvider);
            builder2.put(PowerUI.class, this.powerUIProvider);
            builder2.put(RingtonePlayer.class, this.ringtonePlayerProvider);
            builder2.put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider);
            builder2.put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider);
            builder2.put(StorageNotification.class, this.storageNotificationProvider);
            builder2.put(ToastUI.class, this.toastUIProvider);
            builder2.put(TvNotificationHandler.class, this.provideTvNotificationHandlerProvider);
            builder2.put(TvNotificationPanel.class, this.tvNotificationPanelProvider);
            builder2.put(TvOngoingPrivacyChip.class, this.tvOngoingPrivacyChipProvider);
            builder2.put(TvStatusBar.class, this.tvStatusBarProvider);
            builder2.put(VolumeUI.class, this.volumeUIProvider);
            builder2.put(VpnStatusObserver.class, this.vpnStatusObserverProvider);
            builder2.put(WindowMagnification.class, this.windowMagnificationProvider);
            builder2.put(WMShell.class, this.wMShellProvider);
            this.mapOfClassOfAndProviderOfCoreStartableProvider = builder2.build();
            this.dumpHandlerProvider = new DumpHandler_Factory(this.this$0.contextProvider, this.this$0.dumpManagerProvider, this.logBufferEulogizerProvider, this.mapOfClassOfAndProviderOfCoreStartableProvider);
            this.logBufferFreezerProvider = new LogBufferFreezer_Factory(this.this$0.dumpManagerProvider, this.this$0.provideMainDelayableExecutorProvider);
            this.batteryStateNotifierProvider = new TakeScreenshotService_Factory(this.provideBatteryControllerProvider, this.this$0.provideNotificationManagerProvider, this.provideDelayableExecutorProvider, this.this$0.contextProvider, 2);
            this.systemUIServiceProvider = SystemUIService_Factory.create(this.this$0.provideMainHandlerProvider, this.dumpHandlerProvider, this.providesBroadcastDispatcherProvider, this.logBufferFreezerProvider, this.batteryStateNotifierProvider);
            this.systemUIAuxiliaryDumpServiceProvider = new ImageTileSet_Factory(this.dumpHandlerProvider, 2);
            Provider<Looper> provider27 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideLongRunningLooperFactory.InstanceHolder.INSTANCE);
            this.provideLongRunningLooperProvider = provider27;
            this.provideLongRunningExecutorProvider = C0772x82ed519d.m53m(provider27, 5);
            Provider<RecordingController> provider28 = this.recordingControllerProvider;
            Provider<Executor> provider29 = this.provideLongRunningExecutorProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            this.recordingServiceProvider = RecordingService_Factory.create(provider28, provider29, daggerTvGlobalRootComponent17.provideUiEventLoggerProvider, daggerTvGlobalRootComponent17.provideNotificationManagerProvider, this.provideUserTrackerProvider, this.keyguardDismissUtilProvider);
            this.screenshotSmartActionsProvider = DoubleCheck.provider(ScreenshotSmartActions_Factory.InstanceHolder.INSTANCE);
            this.screenshotNotificationsControllerProvider = new ScreenshotNotificationsController_Factory(this.this$0.contextProvider, this.this$0.provideWindowManagerProvider);
            this.scrollCaptureClientProvider = new ScrollCaptureClient_Factory(this.this$0.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, this.this$0.contextProvider, 0);
            this.imageTileSetProvider = new ImageTileSet_Factory(this.provideHandlerProvider, 0);
            this.scrollCaptureControllerProvider = new ScrollCaptureController_Factory(this.this$0.contextProvider, this.provideBackgroundExecutorProvider, this.scrollCaptureClientProvider, this.imageTileSetProvider, this.this$0.provideUiEventLoggerProvider);
            this.timeoutHandlerProvider = new KeyboardUI_Factory(this.this$0.contextProvider, 5);
            ScreenshotController_Factory create4 = ScreenshotController_Factory.create(this.this$0.contextProvider, this.screenshotSmartActionsProvider, this.screenshotNotificationsControllerProvider, this.scrollCaptureClientProvider, this.this$0.provideUiEventLoggerProvider, this.imageExporterProvider, this.this$0.provideMainExecutorProvider, this.scrollCaptureControllerProvider, this.longScreenshotDataProvider, this.this$0.provideActivityManagerProvider, this.timeoutHandlerProvider);
            this.screenshotControllerProvider = create4;
            this.takeScreenshotServiceProvider = new TakeScreenshotService_Factory(create4, this.this$0.provideUserManagerProvider, this.this$0.provideUiEventLoggerProvider, this.screenshotNotificationsControllerProvider, 0);
            MapProviderFactory.Builder builder3 = new MapProviderFactory.Builder(9);
            builder3.put(DozeService.class, this.dozeServiceProvider);
            builder3.put(ImageWallpaper.class, ImageWallpaper_Factory.InstanceHolder.INSTANCE);
            builder3.put(KeyguardService.class, this.keyguardServiceProvider);
            builder3.put(DreamOverlayService.class, this.dreamOverlayServiceProvider);
            builder3.put(NotificationListenerWithPlugins.class, this.notificationListenerWithPluginsProvider);
            builder3.put(SystemUIService.class, this.systemUIServiceProvider);
            builder3.put(SystemUIAuxiliaryDumpService.class, this.systemUIAuxiliaryDumpServiceProvider);
            builder3.put(RecordingService.class, this.recordingServiceProvider);
            builder3.put(TakeScreenshotService.class, this.takeScreenshotServiceProvider);
            this.mapOfClassOfAndProviderOfServiceProvider = builder3.build();
            this.overviewProxyRecentsImplProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.optionalOfStatusBarProvider, 2));
            LinkedHashMap newLinkedHashMapWithExpectedSize = R$id.newLinkedHashMapWithExpectedSize(1);
            Provider<OverviewProxyRecentsImpl> provider30 = this.overviewProxyRecentsImplProvider;
            Objects.requireNonNull(provider30, "provider");
            newLinkedHashMapWithExpectedSize.put(OverviewProxyRecentsImpl.class, provider30);
            this.mapOfClassOfAndProviderOfRecentsImplementationProvider = new MapProviderFactory(newLinkedHashMapWithExpectedSize);
            this.actionProxyReceiverProvider = new ActionProxyReceiver_Factory(this.optionalOfStatusBarProvider, this.provideActivityManagerWrapperProvider, this.screenshotSmartActionsProvider, 0);
            this.deleteScreenshotReceiverProvider = new VibratorHelper_Factory(this.screenshotSmartActionsProvider, this.provideBackgroundExecutorProvider, 2);
            this.smartActionsReceiverProvider = new SmartActionsReceiver_Factory(this.screenshotSmartActionsProvider, 0);
        }

        public final void initialize6(DependencyProvider dependencyProvider, NightDisplayListenerModule nightDisplayListenerModule, SysUIUnfoldModule sysUIUnfoldModule, Optional<Pip> optional, Optional<LegacySplitScreen> optional2, Optional<SplitScreen> optional3, Optional<Object> optional4, Optional<OneHanded> optional5, Optional<Bubbles> optional6, Optional<TaskViewFactory> optional7, Optional<HideDisplayCutout> optional8, Optional<ShellCommandHandler> optional9, ShellTransitions shellTransitions, Optional<StartingSurface> optional10, Optional<DisplayAreaHelper> optional11, Optional<TaskSurfaceHelper> optional12, Optional<RecentTasks> optional13, Optional<CompatUI> optional14, Optional<DragAndDrop> optional15, Optional<BackAnimation> optional16) {
            this.mediaOutputDialogReceiverProvider = new MediaOutputDialogReceiver_Factory(this.mediaOutputDialogFactoryProvider, 0);
            Provider<PeopleSpaceWidgetManager> provider = this.peopleSpaceWidgetManagerProvider;
            this.peopleSpaceWidgetPinnedReceiverProvider = new ScreenLifecycle_Factory(provider, 2);
            this.peopleSpaceWidgetProvider = new PeopleSpaceWidgetProvider_Factory(provider, 0);
            LinkedHashMap newLinkedHashMapWithExpectedSize = R$id.newLinkedHashMapWithExpectedSize(6);
            Provider<ActionProxyReceiver> provider2 = this.actionProxyReceiverProvider;
            Objects.requireNonNull(provider2, "provider");
            newLinkedHashMapWithExpectedSize.put(ActionProxyReceiver.class, provider2);
            Provider<DeleteScreenshotReceiver> provider3 = this.deleteScreenshotReceiverProvider;
            Objects.requireNonNull(provider3, "provider");
            newLinkedHashMapWithExpectedSize.put(DeleteScreenshotReceiver.class, provider3);
            Provider<SmartActionsReceiver> provider4 = this.smartActionsReceiverProvider;
            Objects.requireNonNull(provider4, "provider");
            newLinkedHashMapWithExpectedSize.put(SmartActionsReceiver.class, provider4);
            Provider<MediaOutputDialogReceiver> provider5 = this.mediaOutputDialogReceiverProvider;
            Objects.requireNonNull(provider5, "provider");
            newLinkedHashMapWithExpectedSize.put(MediaOutputDialogReceiver.class, provider5);
            Provider<PeopleSpaceWidgetPinnedReceiver> provider6 = this.peopleSpaceWidgetPinnedReceiverProvider;
            Objects.requireNonNull(provider6, "provider");
            newLinkedHashMapWithExpectedSize.put(PeopleSpaceWidgetPinnedReceiver.class, provider6);
            Provider<PeopleSpaceWidgetProvider> provider7 = this.peopleSpaceWidgetProvider;
            Objects.requireNonNull(provider7, "provider");
            newLinkedHashMapWithExpectedSize.put(PeopleSpaceWidgetProvider.class, provider7);
            MapProviderFactory mapProviderFactory = new MapProviderFactory(newLinkedHashMapWithExpectedSize);
            this.mapOfClassOfAndProviderOfBroadcastReceiverProvider = mapProviderFactory;
            DelegateFactory.setDelegate(this.contextComponentResolverProvider, DoubleCheck.provider(new ContextComponentResolver_Factory(this.mapOfClassOfAndProviderOfActivityProvider, this.mapOfClassOfAndProviderOfServiceProvider, this.mapOfClassOfAndProviderOfRecentsImplementationProvider, mapProviderFactory)));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            this.unfoldLatencyTrackerProvider = DoubleCheck.provider(new UnfoldLatencyTracker_Factory(daggerTvGlobalRootComponent.provideLatencyTrackerProvider, daggerTvGlobalRootComponent.provideDeviceStateManagerProvider, this.provideUiBackgroundExecutorProvider, daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.screenLifecycleProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = this.this$0;
            this.flashlightControllerImplProvider = DoubleCheck.provider(new LogBufferFactory_Factory(daggerTvGlobalRootComponent2.contextProvider, daggerTvGlobalRootComponent2.dumpManagerProvider, 2));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = this.this$0;
            Provider<Context> provider8 = daggerTvGlobalRootComponent3.contextProvider;
            Provider<Handler> provider9 = this.provideBgHandlerProvider;
            this.provideNightDisplayListenerProvider = new GestureSensorImpl_Factory(nightDisplayListenerModule, (Provider) provider8, (Provider) provider9);
            this.provideReduceBrightColorsListenerProvider = DoubleCheck.provider(new TvPipModule_ProvidesTvPipMenuControllerFactory(dependencyProvider, (Provider) provider9, (Provider) this.provideUserTrackerProvider, (Provider) daggerTvGlobalRootComponent3.provideColorDisplayManagerProvider, this.secureSettingsImplProvider));
            this.managedProfileControllerImplProvider = DoubleCheck.provider(new ManagedProfileControllerImpl_Factory(this.this$0.contextProvider, this.providesBroadcastDispatcherProvider, 0));
            this.accessibilityControllerProvider = DoubleCheck.provider(new UsbDebuggingSecondaryUserActivity_Factory(this.this$0.contextProvider, 2));
            Provider<String> provider10 = DoubleCheck.provider(TvSystemUIModule_ProvideLeakReportEmailFactory.InstanceHolder.INSTANCE);
            this.provideLeakReportEmailProvider = provider10;
            Provider<LeakReporter> provider11 = DoubleCheck.provider(new LeakReporter_Factory(this.this$0.contextProvider, this.provideLeakDetectorProvider, provider10, 0));
            this.leakReporterProvider = provider11;
            Provider<DelayableExecutor> provider12 = this.provideBackgroundDelayableExecutorProvider;
            QSFragmentModule_ProvideRootViewFactory qSFragmentModule_ProvideRootViewFactory = new QSFragmentModule_ProvideRootViewFactory(provider12, 4);
            this.providesBackgroundMessageRouterProvider = qSFragmentModule_ProvideRootViewFactory;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent4 = this.this$0;
            this.garbageMonitorProvider = DoubleCheck.provider(GarbageMonitor_Factory.create$1(daggerTvGlobalRootComponent4.contextProvider, provider12, qSFragmentModule_ProvideRootViewFactory, this.provideLeakDetectorProvider, provider11, daggerTvGlobalRootComponent4.dumpManagerProvider));
            this.tunablePaddingServiceProvider = DoubleCheck.provider(new TunablePadding_TunablePaddingService_Factory(this.tunerServiceImplProvider));
            this.uiOffloadThreadProvider = DoubleCheck.provider(UiOffloadThread_Factory.InstanceHolder.INSTANCE);
            this.remoteInputQuickSettingsDisablerProvider = DoubleCheck.provider(new RemoteInputQuickSettingsDisabler_Factory(this.this$0.contextProvider, this.provideCommandQueueProvider, this.provideConfigurationControllerProvider, 0));
            this.foregroundServiceNotificationListenerProvider = DoubleCheck.provider(ForegroundServiceNotificationListener_Factory.create(this.this$0.contextProvider, this.foregroundServiceControllerProvider, this.provideNotificationEntryManagerProvider, this.notifPipelineProvider, this.bindSystemClockProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent5 = this.this$0;
            this.clockManagerProvider = DoubleCheck.provider(ClockManager_Factory.create(daggerTvGlobalRootComponent5.contextProvider, this.providerLayoutInflaterProvider, daggerTvGlobalRootComponent5.providesPluginManagerProvider, this.sysuiColorExtractorProvider, this.dockManagerImplProvider, this.providesBroadcastDispatcherProvider));
            this.systemEventCoordinatorProvider = DoubleCheck.provider(new SystemEventCoordinator_Factory(this.bindSystemClockProvider, this.provideBatteryControllerProvider, this.privacyItemControllerProvider, this.this$0.contextProvider));
            Provider<StatusBarLocationPublisher> provider13 = DoubleCheck.provider(StatusBarLocationPublisher_Factory.InstanceHolder.INSTANCE);
            this.statusBarLocationPublisherProvider = provider13;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent6 = this.this$0;
            Provider<Context> provider14 = daggerTvGlobalRootComponent6.contextProvider;
            Provider<StatusBarWindowController> provider15 = this.statusBarWindowControllerProvider;
            SystemEventChipAnimationController_Factory systemEventChipAnimationController_Factory = new SystemEventChipAnimationController_Factory(provider14, provider15, provider13, 0);
            this.systemEventChipAnimationControllerProvider = systemEventChipAnimationController_Factory;
            Provider<SystemStatusAnimationScheduler> provider16 = DoubleCheck.provider(SystemStatusAnimationScheduler_Factory.create(this.systemEventCoordinatorProvider, systemEventChipAnimationController_Factory, provider15, daggerTvGlobalRootComponent6.dumpManagerProvider, this.bindSystemClockProvider, daggerTvGlobalRootComponent6.provideMainDelayableExecutorProvider));
            this.systemStatusAnimationSchedulerProvider = provider16;
            this.privacyDotViewControllerProvider = DoubleCheck.provider(PrivacyDotViewController_Factory.create(this.this$0.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, this.provideConfigurationControllerProvider, this.statusBarContentInsetsProvider, provider16));
            Provider<OverviewProxyService> provider17 = this.overviewProxyServiceProvider;
            Provider<SysUiState> provider18 = this.provideSysUiStateProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent7 = this.this$0;
            this.factoryProvider7 = EdgeBackGestureHandler_Factory_Factory.create(provider17, provider18, daggerTvGlobalRootComponent7.providesPluginManagerProvider, daggerTvGlobalRootComponent7.provideMainExecutorProvider, this.providesBroadcastDispatcherProvider, this.protoTracerProvider, this.navigationModeControllerProvider, daggerTvGlobalRootComponent7.provideViewConfigurationProvider, daggerTvGlobalRootComponent7.provideWindowManagerProvider, daggerTvGlobalRootComponent7.provideIWindowManagerProvider, this.falsingManagerProxyProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent8 = this.this$0;
            Provider<PluginManager> provider19 = daggerTvGlobalRootComponent8.providesPluginManagerProvider;
            Provider<AssistManager> provider20 = this.assistManagerProvider;
            Provider<SecurityControllerImpl> provider21 = this.securityControllerImplProvider;
            Provider<LeakDetector> provider22 = this.provideLeakDetectorProvider;
            Provider<LeakReporter> provider23 = this.leakReporterProvider;
            Provider<GarbageMonitor> provider24 = this.garbageMonitorProvider;
            Provider<TunerServiceImpl> provider25 = this.tunerServiceImplProvider;
            Provider<NotificationShadeWindowControllerImpl> provider26 = this.notificationShadeWindowControllerImplProvider;
            Provider<StatusBarWindowController> provider27 = this.statusBarWindowControllerProvider;
            Provider<DarkIconDispatcherImpl> provider28 = this.darkIconDispatcherImplProvider;
            Provider<ConfigurationController> provider29 = this.provideConfigurationControllerProvider;
            Provider<StatusBarIconControllerImpl> provider30 = this.statusBarIconControllerImplProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent9 = this.this$0;
            Provider<DisplayMetrics> provider31 = daggerTvGlobalRootComponent9.provideDisplayMetricsProvider;
            Provider<LockscreenGestureLogger> provider32 = this.lockscreenGestureLoggerProvider;
            Provider<KeyguardEnvironmentImpl> provider33 = this.keyguardEnvironmentImplProvider;
            Provider<ShadeControllerImpl> provider34 = this.shadeControllerImplProvider;
            Provider<StatusBarRemoteInputCallback> provider35 = this.statusBarRemoteInputCallbackProvider;
            Provider<AppOpsControllerImpl> provider36 = this.appOpsControllerImplProvider;
            Provider<NavigationBarController> provider37 = this.navigationBarControllerProvider;
            Provider<AccessibilityFloatingMenuController> provider38 = this.provideAccessibilityFloatingMenuControllerProvider;
            Provider<StatusBarStateControllerImpl> provider39 = this.statusBarStateControllerImplProvider;
            Provider<NotificationLockscreenUserManagerImpl> provider40 = this.notificationLockscreenUserManagerImplProvider;
            Provider<NotificationGroupAlertTransferHelper> provider41 = this.notificationGroupAlertTransferHelperProvider;
            Provider<NotificationGroupManagerLegacy> provider42 = this.notificationGroupManagerLegacyProvider;
            Provider<VisualStabilityManager> provider43 = this.provideVisualStabilityManagerProvider;
            Provider<NotificationGutsManager> provider44 = this.provideNotificationGutsManagerProvider;
            Provider<NotificationMediaManager> provider45 = this.provideNotificationMediaManagerProvider;
            Provider<NotificationRemoteInputManager> provider46 = this.provideNotificationRemoteInputManagerProvider;
            Provider<SmartReplyConstants> provider47 = this.smartReplyConstantsProvider;
            Provider<NotificationListener> provider48 = this.notificationListenerProvider;
            Provider<NotificationLogger> provider49 = this.provideNotificationLoggerProvider;
            Provider<NotificationViewHierarchyManager> provider50 = this.provideNotificationViewHierarchyManagerProvider;
            Provider<NotificationFilter> provider51 = this.notificationFilterProvider;
            Provider<KeyguardDismissUtil> provider52 = this.keyguardDismissUtilProvider;
            Provider<SmartReplyController> provider53 = this.provideSmartReplyControllerProvider;
            Provider<RemoteInputQuickSettingsDisabler> provider54 = this.remoteInputQuickSettingsDisablerProvider;
            Provider<NotificationEntryManager> provider55 = this.provideNotificationEntryManagerProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent10 = this.this$0;
            this.dependencyProvider2 = DoubleCheck.provider(Dependency_Factory.create(daggerTvGlobalRootComponent8.dumpManagerProvider, this.provideActivityStarterProvider, this.providesBroadcastDispatcherProvider, this.asyncSensorManagerProvider, this.bluetoothControllerImplProvider, this.locationControllerImplProvider, this.rotationLockControllerImplProvider, this.zenModeControllerImplProvider, this.hdmiCecSetMenuLanguageHelperProvider, this.hotspotControllerImplProvider, this.castControllerImplProvider, this.flashlightControllerImplProvider, this.userSwitcherControllerProvider, this.userInfoControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.provideBatteryControllerProvider, this.provideNightDisplayListenerProvider, this.provideReduceBrightColorsListenerProvider, this.managedProfileControllerImplProvider, this.nextAlarmControllerImplProvider, this.provideDataSaverControllerProvider, this.accessibilityControllerProvider, this.providesDeviceProvisionedControllerProvider, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, daggerTvGlobalRootComponent9.screenLifecycleProvider, this.wakefulnessLifecycleProvider, this.fragmentServiceProvider, this.extensionControllerImplProvider, daggerTvGlobalRootComponent9.pluginDependencyProvider, this.provideLocalBluetoothControllerProvider, this.volumeDialogControllerImplProvider, this.provideMetricsLoggerProvider, this.accessibilityManagerWrapperProvider, this.sysuiColorExtractorProvider, this.tunablePaddingServiceProvider, this.foregroundServiceControllerProvider, this.uiOffloadThreadProvider, this.powerNotificationWarningsProvider, this.lightBarControllerProvider, daggerTvGlobalRootComponent9.provideIWindowManagerProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.accessibilityButtonModeObserverProvider, this.accessibilityButtonTargetsObserverProvider, this.enhancedEstimatesImplProvider, this.vibratorHelperProvider, daggerTvGlobalRootComponent9.provideIStatusBarServiceProvider, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47, provider48, provider49, provider50, provider51, provider52, provider53, provider54, provider55, daggerTvGlobalRootComponent10.provideSensorPrivacyManagerProvider, this.provideAutoHideControllerProvider, this.foregroundServiceNotificationListenerProvider, this.privacyItemControllerProvider, this.provideBgLooperProvider, this.provideBgHandlerProvider, daggerTvGlobalRootComponent10.provideMainHandlerProvider, this.provideTimeTickHandlerProvider, this.provideLeakReportEmailProvider, daggerTvGlobalRootComponent10.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.clockManagerProvider, this.provideActivityManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, daggerTvGlobalRootComponent10.providePackageManagerWrapperProvider, this.provideSensorPrivacyControllerProvider, this.dockManagerImplProvider, this.provideINotificationManagerProvider, this.provideSysUiStateProvider, daggerTvGlobalRootComponent10.provideAlarmManagerProvider, this.keyguardSecurityModelProvider, this.dozeParametersProvider, this.provideCommandQueueProvider, this.recordingControllerProvider, this.protoTracerProvider, this.mediaOutputDialogFactoryProvider, this.deviceConfigProxyProvider, this.navigationBarOverlayControllerProvider, this.telephonyListenerManagerProvider, this.systemStatusAnimationSchedulerProvider, this.privacyDotViewControllerProvider, this.factoryProvider7, this.this$0.provideUiEventLoggerProvider, this.statusBarContentInsetsProvider, this.internetDialogFactoryProvider, this.featureFlagsReleaseProvider, this.notificationSectionsManagerProvider, this.screenOffAnimationControllerProvider, this.ambientStateProvider, this.provideGroupMembershipManagerProvider, this.provideGroupExpansionManagerProvider));
            this.mediaTttFlagsProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.featureFlagsReleaseProvider, 3));
            Provider<CommandQueue> provider56 = this.provideCommandQueueProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent11 = this.this$0;
            Provider<MediaTttChipControllerSender> provider57 = DoubleCheck.provider(new MediaTttChipControllerSender_Factory(provider56, daggerTvGlobalRootComponent11.contextProvider, daggerTvGlobalRootComponent11.provideWindowManagerProvider, daggerTvGlobalRootComponent11.provideMainDelayableExecutorProvider));
            this.mediaTttChipControllerSenderProvider = provider57;
            this.providesMediaTttChipControllerSenderProvider = DoubleCheck.provider(new MediaModule_ProvidesMediaTttChipControllerSenderFactory(this.mediaTttFlagsProvider, provider57, 0));
            Provider<CommandQueue> provider58 = this.provideCommandQueueProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent12 = this.this$0;
            Provider<MediaTttChipControllerReceiver> provider59 = DoubleCheck.provider(new MediaTttChipControllerReceiver_Factory(provider58, daggerTvGlobalRootComponent12.contextProvider, daggerTvGlobalRootComponent12.provideWindowManagerProvider, this.provideDelayableExecutorProvider, daggerTvGlobalRootComponent12.provideMainHandlerProvider));
            this.mediaTttChipControllerReceiverProvider = provider59;
            this.providesMediaTttChipControllerReceiverProvider = DoubleCheck.provider(new NotifBindPipelineInitializer_Factory(this.mediaTttFlagsProvider, provider59, 1));
            Provider<CommandRegistry> provider60 = this.commandRegistryProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent13 = this.this$0;
            Provider<MediaTttCommandLineHelper> provider61 = DoubleCheck.provider(new ScreenOnCoordinator_Factory(provider60, daggerTvGlobalRootComponent13.contextProvider, daggerTvGlobalRootComponent13.provideMainExecutorProvider, 1));
            this.mediaTttCommandLineHelperProvider = provider61;
            this.providesMediaTttCommandLineHelperProvider = DoubleCheck.provider(new ScreenPinningRequest_Factory(this.mediaTttFlagsProvider, provider61, 1));
            Provider<MediaMuteAwaitConnectionCli> provider62 = DoubleCheck.provider(new KeyguardVisibility_Factory(this.commandRegistryProvider, this.this$0.contextProvider, 1));
            this.mediaMuteAwaitConnectionCliProvider = provider62;
            this.providesMediaMuteAwaitConnectionCliProvider = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(this.mediaFlagsProvider, provider62, 2));
            Provider<NearbyMediaDevicesManager> provider63 = DoubleCheck.provider(new ActionClickLogger_Factory(this.provideCommandQueueProvider, 2));
            this.nearbyMediaDevicesManagerProvider = provider63;
            this.providesNearbyMediaDevicesManagerProvider = DoubleCheck.provider(new DependencyProvider_ProviderLayoutInflaterFactory(this.mediaFlagsProvider, provider63, 1));
            this.notificationChannelsProvider = new DozeAuthRemover_Factory(this.this$0.contextProvider, 3);
            this.provideClockInfoListProvider = new ImageExporter_Factory(this.clockManagerProvider, 1);
            this.provideAllowNotificationLongPressProvider = DoubleCheck.provider(TvSystemUIModule_ProvideAllowNotificationLongPressFactory.InstanceHolder.INSTANCE);
            this.setDisplayAreaHelperProvider = InstanceFactory.create(optional11);
            this.sectionClassifierProvider = DoubleCheck.provider(SectionClassifier_Factory.InstanceHolder.INSTANCE);
            this.providesAlertingHeaderNodeControllerProvider = new AssistantWarmer_Factory(this.providesAlertingHeaderSubcomponentProvider, 2);
            this.providesSilentHeaderNodeControllerProvider = new StatusBarInitializer_Factory(this.providesSilentHeaderSubcomponentProvider, 3);
            this.providesIncomingHeaderNodeControllerProvider = new TimeoutManager_Factory(this.providesIncomingHeaderSubcomponentProvider, 2);
            this.provideNotifGutsViewManagerProvider = DoubleCheck.provider(new DozeLogger_Factory(this.provideNotificationGutsManagerProvider, 4));
            this.communalStateControllerProvider = DoubleCheck.provider(CommunalStateController_Factory.InstanceHolder.INSTANCE);
            this.providesPeopleHeaderNodeControllerProvider = new MediaControllerFactory_Factory(this.providesPeopleHeaderSubcomponentProvider, 4);
            this.notifUiAdjustmentProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideOptionalVibratorFactory(this.sectionClassifierProvider, 1));
            Provider<Optional<BcSmartspaceDataPlugin>> provider64 = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.optionalOfBcSmartspaceDataPluginProvider = provider64;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent14 = this.this$0;
            this.lockscreenSmartspaceControllerProvider = DoubleCheck.provider(LockscreenSmartspaceController_Factory.create(daggerTvGlobalRootComponent14.contextProvider, this.featureFlagsReleaseProvider, daggerTvGlobalRootComponent14.provideSmartspaceManagerProvider, this.provideActivityStarterProvider, this.falsingManagerProxyProvider, this.secureSettingsImplProvider, this.provideUserTrackerProvider, daggerTvGlobalRootComponent14.provideContentResolverProvider, this.provideConfigurationControllerProvider, this.statusBarStateControllerImplProvider, this.providesDeviceProvisionedControllerProvider, daggerTvGlobalRootComponent14.provideExecutionProvider, daggerTvGlobalRootComponent14.provideMainExecutorProvider, daggerTvGlobalRootComponent14.provideMainHandlerProvider, provider64));
            this.qSTileHostProvider = new DelegateFactory();
            Provider<LogBuffer> provider65 = DoubleCheck.provider(new KeyboardUI_Factory(this.logBufferFactoryProvider, 3));
            this.provideQuickSettingsLogBufferProvider = provider65;
            this.qSLoggerProvider = new QSLogger_Factory(provider65, 0);
            this.customTileStatePersisterProvider = new LiftToActivateListener_Factory(this.this$0.contextProvider, 3);
            TileServices_Factory tileServices_Factory = new TileServices_Factory(this.qSTileHostProvider, this.providesBroadcastDispatcherProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider);
            this.tileServicesProvider = tileServices_Factory;
            this.builderProvider6 = CustomTile_Builder_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.customTileStatePersisterProvider, tileServices_Factory);
            this.wifiTileProvider = WifiTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider);
            this.internetTileProvider = InternetTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider, this.internetDialogFactoryProvider);
            this.bluetoothTileProvider = BluetoothTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.bluetoothControllerImplProvider);
            this.cellularTileProvider = CellularTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.dndTileProvider = DndTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.zenModeControllerImplProvider, this.provideSharePreferencesProvider, this.secureSettingsImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.colorInversionTileProvider = ColorInversionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            Provider<QSTileHost> provider66 = this.qSTileHostProvider;
            Provider<Looper> provider67 = this.provideBgLooperProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent15 = this.this$0;
            this.airplaneModeTileProvider = AirplaneModeTile_Factory.create(provider66, provider67, daggerTvGlobalRootComponent15.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.providesBroadcastDispatcherProvider, daggerTvGlobalRootComponent15.provideConnectivityManagagerProvider, this.globalSettingsImplProvider);
            this.workModeTileProvider = WorkModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.managedProfileControllerImplProvider);
            Provider<QSTileHost> provider68 = this.qSTileHostProvider;
            Provider<Looper> provider69 = this.provideBgLooperProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent16 = this.this$0;
            this.rotationLockTileProvider = RotationLockTile_Factory.create$1(provider68, provider69, daggerTvGlobalRootComponent16.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.rotationLockControllerImplProvider, daggerTvGlobalRootComponent16.provideSensorPrivacyManagerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider);
            this.flashlightTileProvider = FlashlightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.flashlightControllerImplProvider);
            this.locationTileProvider = LocationTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.castTileProvider = CastTile_Factory.create$1(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.castControllerImplProvider, this.keyguardStateControllerImplProvider, this.networkControllerImplProvider, this.hotspotControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.hotspotTileProvider = HotspotTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider);
            this.batterySaverTileProvider = new BatterySaverTile_Factory(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider);
            this.dataSaverTileProvider = DataSaverTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideDataSaverControllerProvider, this.provideDialogLaunchAnimatorProvider);
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent17 = this.this$0;
            NightDisplayListenerModule_Builder_Factory nightDisplayListenerModule_Builder_Factory = new NightDisplayListenerModule_Builder_Factory(daggerTvGlobalRootComponent17.contextProvider, this.provideBgHandlerProvider);
            this.builderProvider7 = nightDisplayListenerModule_Builder_Factory;
            this.nightDisplayTileProvider = NightDisplayTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, daggerTvGlobalRootComponent17.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, daggerTvGlobalRootComponent17.provideColorDisplayManagerProvider, nightDisplayListenerModule_Builder_Factory);
            this.nfcTileProvider = NfcTile_Factory.create$1(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.providesBroadcastDispatcherProvider);
            this.memoryTileProvider = GarbageMonitor_MemoryTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.garbageMonitorProvider);
            this.uiModeNightTileProvider = UiModeNightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideConfigurationControllerProvider, this.provideBatteryControllerProvider, this.locationControllerImplProvider);
            this.screenRecordTileProvider = ScreenRecordTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.recordingControllerProvider, this.keyguardDismissUtilProvider, this.keyguardStateControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            Provider<Boolean> m = C0773x82ed519e.m54m(this.this$0.contextProvider, 4);
            this.isReduceBrightColorsAvailableProvider = m;
            this.reduceBrightColorsTileProvider = ReduceBrightColorsTile_Factory.create(m, this.provideReduceBrightColorsListenerProvider, this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.cameraToggleTileProvider = CameraToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
            this.microphoneToggleTileProvider = MicrophoneToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
            Provider<Boolean> provider70 = DoubleCheck.provider(new TypeClassifier_Factory(this.this$0.providePackageManagerProvider, 2));
            this.providesControlsFeatureEnabledProvider = provider70;
            this.optionalOfControlsTileResourceConfigurationProvider = provider64;
            Provider<ControlsComponent> provider71 = DoubleCheck.provider(ControlsComponent_Factory.create(provider70, this.this$0.contextProvider, this.controlsControllerImplProvider, this.controlsUiControllerImplProvider, this.controlsListingControllerImplProvider, this.provideLockPatternUtilsProvider, this.keyguardStateControllerImplProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, provider64));
            this.controlsComponentProvider = provider71;
            this.deviceControlsTileProvider = DeviceControlsTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, provider71, this.keyguardStateControllerImplProvider);
            this.alarmTileProvider = AlarmTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.nextAlarmControllerImplProvider);
            Provider<QuickAccessWalletClient> provider72 = DoubleCheck.provider(new DozeLogger_Factory(this.this$0.contextProvider, 6));
            this.provideQuickAccessWalletClientProvider = provider72;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent18 = this.this$0;
            Provider<QuickAccessWalletController> provider73 = DoubleCheck.provider(QuickAccessWalletController_Factory.create(daggerTvGlobalRootComponent18.contextProvider, daggerTvGlobalRootComponent18.provideMainExecutorProvider, this.provideExecutorProvider, this.secureSettingsImplProvider, provider72, this.bindSystemClockProvider));
            this.quickAccessWalletControllerProvider = provider73;
            Provider<QSTileHost> provider74 = this.qSTileHostProvider;
            Provider<Looper> provider75 = this.provideBgLooperProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent19 = this.this$0;
            this.quickAccessWalletTileProvider = QuickAccessWalletTile_Factory.create(provider74, provider75, daggerTvGlobalRootComponent19.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.keyguardStateControllerImplProvider, daggerTvGlobalRootComponent19.providePackageManagerProvider, this.secureSettingsImplProvider, provider73);
            Provider<QRCodeScannerController> provider76 = DoubleCheck.provider(DefaultUiController_Factory.create$1(this.this$0.contextProvider, this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, this.deviceConfigProxyProvider, this.provideUserTrackerProvider));
            this.qRCodeScannerControllerProvider = provider76;
            this.qRCodeScannerTileProvider = QRCodeScannerTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, provider76);
            this.oneHandedModeTileProvider = OneHandedModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.colorCorrectionTileProvider = ColorCorrectionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, this.this$0.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.qSFactoryImplProvider = DoubleCheck.provider(QSFactoryImpl_Factory.create(this.qSTileHostProvider, this.builderProvider6, this.wifiTileProvider, this.internetTileProvider, this.bluetoothTileProvider, this.cellularTileProvider, this.dndTileProvider, this.colorInversionTileProvider, this.airplaneModeTileProvider, this.workModeTileProvider, this.rotationLockTileProvider, this.flashlightTileProvider, this.locationTileProvider, this.castTileProvider, this.hotspotTileProvider, this.batterySaverTileProvider, this.dataSaverTileProvider, this.nightDisplayTileProvider, this.nfcTileProvider, this.memoryTileProvider, this.uiModeNightTileProvider, this.screenRecordTileProvider, this.reduceBrightColorsTileProvider, this.cameraToggleTileProvider, this.microphoneToggleTileProvider, this.deviceControlsTileProvider, this.alarmTileProvider, this.quickAccessWalletTileProvider, this.qRCodeScannerTileProvider, this.oneHandedModeTileProvider, this.colorCorrectionTileProvider));
            Provider provider77 = this.secureSettingsImplProvider;
            Provider<BroadcastDispatcher> provider78 = this.providesBroadcastDispatcherProvider;
            Provider<QSTileHost> provider79 = this.qSTileHostProvider;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent20 = this.this$0;
            this.builderProvider8 = DoubleCheck.provider(AutoAddTracker_Builder_Factory.create(provider77, provider78, provider79, daggerTvGlobalRootComponent20.dumpManagerProvider, daggerTvGlobalRootComponent20.provideMainHandlerProvider, this.provideBackgroundExecutorProvider));
            this.deviceControlsControllerImplProvider = DoubleCheck.provider(new ClipboardListener_Factory(this.this$0.contextProvider, this.controlsComponentProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, 2));
            Provider<WalletControllerImpl> provider80 = DoubleCheck.provider(new ActivityStarterDelegate_Factory(this.provideQuickAccessWalletClientProvider, 5));
            this.walletControllerImplProvider = provider80;
            this.provideAutoTileManagerProvider = QSModule_ProvideAutoTileManagerFactory.create(this.this$0.contextProvider, this.builderProvider8, this.qSTileHostProvider, this.provideBgHandlerProvider, this.secureSettingsImplProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider, this.managedProfileControllerImplProvider, this.provideNightDisplayListenerProvider, this.castControllerImplProvider, this.provideReduceBrightColorsListenerProvider, this.deviceControlsControllerImplProvider, provider80, this.isReduceBrightColorsAvailableProvider);
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

        public final PeopleProvider injectPeopleProvider(PeopleProvider peopleProvider) {
            peopleProvider.mPeopleSpaceWidgetManager = this.peopleSpaceWidgetManagerProvider.get();
            return peopleProvider;
        }

        public final SystemUIAppComponentFactory injectSystemUIAppComponentFactory(SystemUIAppComponentFactory systemUIAppComponentFactory) {
            systemUIAppComponentFactory.mComponentHelper = this.contextComponentResolverProvider.get();
            return systemUIAppComponentFactory;
        }

        public final FlingAnimationUtils namedFlingAnimationUtils() {
            return C0805x4f80e514.providesSwipeToBouncerFlingAnimationUtilsClosing(this.builderProvider10);
        }

        public final FlingAnimationUtils namedFlingAnimationUtils2() {
            return C0806x6a8a7f11.providesSwipeToBouncerFlingAnimationUtilsOpening(this.builderProvider10);
        }

        public final float namedFloat() {
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = this.this$0;
            Provider provider = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            return BouncerSwipeModule.providesSwipeToBouncerStartRegion(daggerTvGlobalRootComponent.mainResources());
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

        public final void inject(ClockOptionsProvider clockOptionsProvider) {
            clockOptionsProvider.mClockInfosProvider = this.provideClockInfoListProvider;
        }
    }

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvWMComponentBuilder */
    public final class TvWMComponentBuilder implements TvWMComponent.Builder {
        public HandlerThread setShellMainThread;

        public final WMComponent.Builder setShellMainThread(HandlerThread handlerThread) {
            this.setShellMainThread = handlerThread;
            return this;
        }

        public TvWMComponentBuilder() {
        }

        public final TvWMComponent build() {
            return new TvWMComponentImpl(this.setShellMainThread);
        }

        /* renamed from: setShellMainThread  reason: collision with other method in class */
        public final TvWMComponentBuilder m282setShellMainThread(HandlerThread handlerThread) {
            this.setShellMainThread = handlerThread;
            return this;
        }
    }

    /* renamed from: com.android.systemui.tv.DaggerTvGlobalRootComponent$TvWMComponentImpl */
    public final class TvWMComponentImpl implements TvWMComponent {
        public Provider<Optional<DisplayImeController>> dynamicOverrideOptionalOfDisplayImeControllerProvider;
        public Provider<Optional<FreeformTaskListener>> dynamicOverrideOptionalOfFreeformTaskListenerProvider;
        public Provider<Optional<FullscreenTaskListener>> dynamicOverrideOptionalOfFullscreenTaskListenerProvider;
        public Provider<Optional<FullscreenUnfoldController>> dynamicOverrideOptionalOfFullscreenUnfoldControllerProvider;
        public Provider<Optional<OneHandedController>> dynamicOverrideOptionalOfOneHandedControllerProvider;
        public Provider<Optional<SplitScreenController>> dynamicOverrideOptionalOfSplitScreenControllerProvider;
        public Provider<Optional<StartingWindowTypeAlgorithm>> dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider;
        public Provider<Optional<AppPairsController>> optionalOfAppPairsControllerProvider;
        public Provider<Optional<BubbleController>> optionalOfBubbleControllerProvider;
        public Provider<Optional<LegacySplitScreenController>> optionalOfLegacySplitScreenControllerProvider;
        public Provider<Optional<PipTouchHandler>> optionalOfPipTouchHandlerProvider;
        public Provider<Optional<ShellUnfoldProgressProvider>> optionalOfShellUnfoldProgressProvider;
        public Provider<Optional<Object>> provideAppPairsProvider;
        public Provider<Optional<BackAnimationController>> provideBackAnimationControllerProvider;
        public Provider<Optional<BackAnimation>> provideBackAnimationProvider;
        public Provider<Optional<Bubbles>> provideBubblesProvider;
        public Provider<CompatUIController> provideCompatUIControllerProvider;
        public Provider<Optional<CompatUI>> provideCompatUIProvider;
        public Provider<Optional<DisplayAreaHelper>> provideDisplayAreaHelperProvider;
        public Provider<DisplayController> provideDisplayControllerProvider;
        public Provider<DisplayImeController> provideDisplayImeControllerProvider;
        public Provider<DisplayInsetsController> provideDisplayInsetsControllerProvider;
        public Provider<DragAndDropController> provideDragAndDropControllerProvider;
        public Provider<Optional<DragAndDrop>> provideDragAndDropProvider;
        public Provider<Optional<FreeformTaskListener>> provideFreeformTaskListenerProvider;
        public Provider<FullscreenTaskListener> provideFullscreenTaskListenerProvider;
        public Provider<Optional<FullscreenUnfoldController>> provideFullscreenUnfoldControllerProvider;
        public Provider<Optional<HideDisplayCutoutController>> provideHideDisplayCutoutControllerProvider;
        public Provider<Optional<HideDisplayCutout>> provideHideDisplayCutoutProvider;
        public Provider<IconProvider> provideIconProvider;
        public Provider<Optional<LegacySplitScreen>> provideLegacySplitScreenProvider;
        public Provider<Optional<OneHanded>> provideOneHandedProvider;
        public Provider<PipAnimationController> providePipAnimationControllerProvider;
        public Provider<PipMediaController> providePipMediaControllerProvider;
        public Provider<Optional<Pip>> providePipProvider;
        public Provider<PipSnapAlgorithm> providePipSnapAlgorithmProvider;
        public Provider<PipSurfaceTransactionHelper> providePipSurfaceTransactionHelperProvider;
        public Provider<PipTaskOrganizer> providePipTaskOrganizerProvider;
        public Provider<PipTransitionState> providePipTransitionStateProvider;
        public Provider<PipUiEventLogger> providePipUiEventLoggerProvider;
        public Provider<Optional<RecentTasksController>> provideRecentTasksControllerProvider;
        public Provider<Optional<RecentTasks>> provideRecentTasksProvider;
        public Provider<ShellTransitions> provideRemoteTransitionsProvider;
        public Provider<RootDisplayAreaOrganizer> provideRootDisplayAreaOrganizerProvider;
        public Provider<ShellExecutor> provideShellAnimationExecutorProvider;
        public Provider<ShellCommandHandlerImpl> provideShellCommandHandlerImplProvider;
        public Provider<Optional<ShellCommandHandler>> provideShellCommandHandlerProvider;
        public Provider<ShellInitImpl> provideShellInitImplProvider;
        public Provider<ShellInit> provideShellInitProvider;
        public Provider<ShellExecutor> provideShellMainExecutorProvider;
        public Provider<Handler> provideShellMainHandlerProvider;
        public Provider<ShellTaskOrganizer> provideShellTaskOrganizerProvider;
        public Provider<ShellExecutor> provideSplashScreenExecutorProvider;
        public Provider<Optional<SplitScreen>> provideSplitScreenProvider;
        public Provider<Optional<StartingSurface>> provideStartingSurfaceProvider;
        public Provider<StartingWindowController> provideStartingWindowControllerProvider;
        public Provider<StartingWindowTypeAlgorithm> provideStartingWindowTypeAlgorithmProvider;
        public Provider<StartingWindowTypeAlgorithm> provideStartingWindowTypeAlgorithmProvider2;
        public Provider<SyncTransactionQueue> provideSyncTransactionQueueProvider;
        public Provider<ShellExecutor> provideSysUIMainExecutorProvider;
        public Provider<SystemWindows> provideSystemWindowsProvider;
        public Provider<Optional<TaskSurfaceHelperController>> provideTaskSurfaceHelperControllerProvider;
        public Provider<Optional<TaskSurfaceHelper>> provideTaskSurfaceHelperProvider;
        public Provider<TaskViewFactoryController> provideTaskViewFactoryControllerProvider;
        public Provider<Optional<TaskViewFactory>> provideTaskViewFactoryProvider;
        public Provider<TaskViewTransitions> provideTaskViewTransitionsProvider;
        public Provider<TransactionPool> provideTransactionPoolProvider;
        public Provider<Transitions> provideTransitionsProvider;
        public Provider<TvPipBoundsAlgorithm> provideTvPipBoundsAlgorithmProvider;
        public Provider<TvPipBoundsState> provideTvPipBoundsStateProvider;
        public Provider<TvPipNotificationController> provideTvPipNotificationControllerProvider;
        public Provider<PipTransitionController> provideTvPipTransitionProvider;
        public Provider<WindowManagerShellWrapper> provideWindowManagerShellWrapperProvider;
        public Provider<TaskStackListenerImpl> providerTaskStackListenerImplProvider;
        public Provider<Optional<OneHandedController>> providesOneHandedControllerProvider;
        public Provider<Optional<SplitScreenController>> providesSplitScreenControllerProvider;
        public Provider<TvPipMenuController> providesTvPipMenuControllerProvider;
        public Provider<HandlerThread> setShellMainThreadProvider;

        public final /* bridge */ /* synthetic */ void init() {
            super.init();
        }

        public TvWMComponentImpl(HandlerThread handlerThread) {
            initialize(handlerThread);
        }

        public final Optional<Object> getAppPairs() {
            return this.provideAppPairsProvider.get();
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
            return this.provideLegacySplitScreenProvider.get();
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

        public final void initialize(HandlerThread handlerThread) {
            InstanceFactory<Object> instanceFactory;
            HandlerThread handlerThread2 = handlerThread;
            if (handlerThread2 == null) {
                instanceFactory = InstanceFactory.NULL_INSTANCE_FACTORY;
            } else {
                instanceFactory = new InstanceFactory<>(handlerThread2);
            }
            this.setShellMainThreadProvider = instanceFactory;
            Provider<Context> provider = DaggerTvGlobalRootComponent.this.contextProvider;
            WMShellConcurrencyModule_ProvideMainHandlerFactory wMShellConcurrencyModule_ProvideMainHandlerFactory = WMShellConcurrencyModule_ProvideMainHandlerFactory.InstanceHolder.INSTANCE;
            this.provideShellMainHandlerProvider = DoubleCheck.provider(new WMShellConcurrencyModule_ProvideShellMainHandlerFactory(provider, instanceFactory));
            Provider<ShellExecutor> provider2 = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(wMShellConcurrencyModule_ProvideMainHandlerFactory, 5));
            this.provideSysUIMainExecutorProvider = provider2;
            Provider<ShellExecutor> provider3 = DoubleCheck.provider(new WMShellConcurrencyModule_ProvideShellMainExecutorFactory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideShellMainHandlerProvider, provider2));
            this.provideShellMainExecutorProvider = provider3;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent = DaggerTvGlobalRootComponent.this;
            Provider<DisplayController> provider4 = DoubleCheck.provider(new WMShellBaseModule_ProvideDisplayControllerFactory(daggerTvGlobalRootComponent.contextProvider, daggerTvGlobalRootComponent.provideIWindowManagerProvider, provider3));
            this.provideDisplayControllerProvider = provider4;
            Provider<Optional<OneHandedController>> provider5 = DaggerTvGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
            this.dynamicOverrideOptionalOfDisplayImeControllerProvider = provider5;
            this.provideDisplayInsetsControllerProvider = DoubleCheck.provider(new RemoteInputQuickSettingsDisabler_Factory(DaggerTvGlobalRootComponent.this.provideIWindowManagerProvider, provider4, this.provideShellMainExecutorProvider, 1));
            Provider<TransactionPool> provider6 = DoubleCheck.provider(WMShellBaseModule_ProvideTransactionPoolFactory.InstanceHolder.INSTANCE);
            this.provideTransactionPoolProvider = provider6;
            this.provideDisplayImeControllerProvider = DoubleCheck.provider(RecordingService_Factory.create$1(this.dynamicOverrideOptionalOfDisplayImeControllerProvider, DaggerTvGlobalRootComponent.this.provideIWindowManagerProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideShellMainExecutorProvider, provider6));
            Provider<IconProvider> provider7 = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(DaggerTvGlobalRootComponent.this.contextProvider, 3));
            this.provideIconProvider = provider7;
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent2 = DaggerTvGlobalRootComponent.this;
            this.provideDragAndDropControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDragAndDropControllerFactory.create$1(daggerTvGlobalRootComponent2.contextProvider, this.provideDisplayControllerProvider, daggerTvGlobalRootComponent2.provideUiEventLoggerProvider, provider7, this.provideShellMainExecutorProvider));
            Provider<SyncTransactionQueue> provider8 = DoubleCheck.provider(new ResumeMediaBrowserFactory_Factory(this.provideTransactionPoolProvider, this.provideShellMainExecutorProvider, 1));
            this.provideSyncTransactionQueueProvider = provider8;
            this.provideCompatUIControllerProvider = DoubleCheck.provider(SmartReplyStateInflaterImpl_Factory.create$1(DaggerTvGlobalRootComponent.this.contextProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDisplayImeControllerProvider, provider8, this.provideShellMainExecutorProvider));
            Provider<TaskStackListenerImpl> provider9 = DoubleCheck.provider(new SeekBarViewModel_Factory(this.provideShellMainHandlerProvider, 1));
            this.providerTaskStackListenerImplProvider = provider9;
            Provider<Optional<RecentTasksController>> provider10 = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksControllerFactory(DaggerTvGlobalRootComponent.this.contextProvider, provider9, this.provideShellMainExecutorProvider));
            this.provideRecentTasksControllerProvider = provider10;
            this.provideShellTaskOrganizerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideShellTaskOrganizerFactory(this.provideShellMainExecutorProvider, DaggerTvGlobalRootComponent.this.contextProvider, this.provideCompatUIControllerProvider, provider10));
            this.optionalOfBubbleControllerProvider = provider5;
            this.dynamicOverrideOptionalOfSplitScreenControllerProvider = provider5;
            this.providesSplitScreenControllerProvider = DoubleCheck.provider(new KeyguardLifecyclesDispatcher_Factory(provider5, DaggerTvGlobalRootComponent.this.contextProvider, 4));
            this.optionalOfAppPairsControllerProvider = provider5;
            this.optionalOfPipTouchHandlerProvider = provider5;
            this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider = provider5;
            this.dynamicOverrideOptionalOfFullscreenUnfoldControllerProvider = provider5;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider = new PresentJdkOptionalInstanceProvider(DaggerTvGlobalRootComponent.this.provideShellProgressProvider);
            this.optionalOfShellUnfoldProgressProvider = presentJdkOptionalInstanceProvider;
            Provider<Optional<FullscreenUnfoldController>> provider11 = DoubleCheck.provider(new WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory(this.dynamicOverrideOptionalOfFullscreenUnfoldControllerProvider, presentJdkOptionalInstanceProvider));
            this.provideFullscreenUnfoldControllerProvider = provider11;
            this.provideFullscreenTaskListenerProvider = DoubleCheck.provider(new ColumbusServiceWrapper_Factory(this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider, this.provideSyncTransactionQueueProvider, provider11, this.provideRecentTasksControllerProvider, 1));
            this.dynamicOverrideOptionalOfFreeformTaskListenerProvider = provider5;
            this.provideFreeformTaskListenerProvider = DoubleCheck.provider(new VolumeUI_Factory(provider5, DaggerTvGlobalRootComponent.this.contextProvider, 2));
            Provider<ShellExecutor> provider12 = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory.InstanceHolder.INSTANCE);
            this.provideShellAnimationExecutorProvider = provider12;
            this.provideTransitionsProvider = DoubleCheck.provider(KeyguardMediaController_Factory.create$1(this.provideShellTaskOrganizerProvider, this.provideTransactionPoolProvider, this.provideDisplayControllerProvider, DaggerTvGlobalRootComponent.this.contextProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider, provider12));
            this.provideSplashScreenExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory.InstanceHolder.INSTANCE);
            Provider<StartingWindowTypeAlgorithm> provider13 = DoubleCheck.provider(TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory.InstanceHolder.INSTANCE);
            this.provideStartingWindowTypeAlgorithmProvider = provider13;
            PresentJdkOptionalInstanceProvider presentJdkOptionalInstanceProvider2 = new PresentJdkOptionalInstanceProvider(provider13);
            this.dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider = presentJdkOptionalInstanceProvider2;
            Provider<StartingWindowTypeAlgorithm> provider14 = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(presentJdkOptionalInstanceProvider2, 5));
            this.provideStartingWindowTypeAlgorithmProvider2 = provider14;
            Provider<StartingWindowController> provider15 = DoubleCheck.provider(SystemKeyPress_Factory.create(DaggerTvGlobalRootComponent.this.contextProvider, this.provideSplashScreenExecutorProvider, provider14, this.provideIconProvider, this.provideTransactionPoolProvider));
            this.provideStartingWindowControllerProvider = provider15;
            Provider<DisplayController> provider16 = this.provideDisplayControllerProvider;
            Provider<DisplayImeController> provider17 = this.provideDisplayImeControllerProvider;
            Provider<DisplayInsetsController> provider18 = this.provideDisplayInsetsControllerProvider;
            Provider<DragAndDropController> provider19 = this.provideDragAndDropControllerProvider;
            Provider<ShellTaskOrganizer> provider20 = this.provideShellTaskOrganizerProvider;
            Provider<Optional<BubbleController>> provider21 = this.optionalOfBubbleControllerProvider;
            Provider<Optional<SplitScreenController>> provider22 = this.providesSplitScreenControllerProvider;
            Provider<Optional<AppPairsController>> provider23 = this.optionalOfAppPairsControllerProvider;
            Provider<Optional<PipTouchHandler>> provider24 = this.optionalOfPipTouchHandlerProvider;
            Provider<FullscreenTaskListener> provider25 = this.provideFullscreenTaskListenerProvider;
            Provider<Optional<FullscreenUnfoldController>> provider26 = this.provideFullscreenUnfoldControllerProvider;
            Provider<Optional<FreeformTaskListener>> provider27 = this.provideFreeformTaskListenerProvider;
            Provider<Optional<RecentTasksController>> provider28 = this.provideRecentTasksControllerProvider;
            Provider<Optional<OneHandedController>> provider29 = provider5;
            Provider<ShellInitImpl> provider30 = DoubleCheck.provider(WMShellBaseModule_ProvideShellInitImplFactory.create(provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, this.provideTransitionsProvider, provider15, this.provideShellMainExecutorProvider));
            this.provideShellInitImplProvider = provider30;
            this.provideShellInitProvider = C0771xb6bb24d9.m52m(provider30, 7);
            this.optionalOfLegacySplitScreenControllerProvider = provider29;
            this.provideTvPipBoundsStateProvider = DoubleCheck.provider(new TvPipModule_ProvideTvPipBoundsStateFactory(DaggerTvGlobalRootComponent.this.contextProvider, 0));
            Provider<PipSnapAlgorithm> provider31 = DoubleCheck.provider(TvPipModule_ProvidePipSnapAlgorithmFactory.InstanceHolder.INSTANCE);
            this.providePipSnapAlgorithmProvider = provider31;
            this.provideTvPipBoundsAlgorithmProvider = DoubleCheck.provider(new OpaHomeButton_Factory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideTvPipBoundsStateProvider, provider31, 2));
            this.provideSystemWindowsProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideSystemWindowsFactory(this.provideDisplayControllerProvider, DaggerTvGlobalRootComponent.this.provideIWindowManagerProvider, 0));
            Provider<PipMediaController> provider32 = DoubleCheck.provider(new VibratorHelper_Factory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideShellMainHandlerProvider, 4));
            this.providePipMediaControllerProvider = provider32;
            this.providesTvPipMenuControllerProvider = DoubleCheck.provider(new TvPipModule_ProvidesTvPipMenuControllerFactory((Provider) DaggerTvGlobalRootComponent.this.contextProvider, (Provider) this.provideTvPipBoundsStateProvider, (Provider) this.provideSystemWindowsProvider, (Provider) provider32, (Provider) this.provideShellMainHandlerProvider));
            this.providePipTransitionStateProvider = DoubleCheck.provider(TvPipModule_ProvidePipTransitionStateFactory.InstanceHolder.INSTANCE);
            Provider<PipSurfaceTransactionHelper> provider33 = DoubleCheck.provider(WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory.InstanceHolder.INSTANCE);
            this.providePipSurfaceTransactionHelperProvider = provider33;
            Provider<PipAnimationController> provider34 = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(provider33, 5));
            this.providePipAnimationControllerProvider = provider34;
            this.provideTvPipTransitionProvider = DoubleCheck.provider(new TvPipModule_ProvideTvPipTransitionFactory(this.provideTransitionsProvider, this.provideShellTaskOrganizerProvider, provider34, this.provideTvPipBoundsAlgorithmProvider, this.provideTvPipBoundsStateProvider, this.providesTvPipMenuControllerProvider));
            DaggerTvGlobalRootComponent daggerTvGlobalRootComponent3 = DaggerTvGlobalRootComponent.this;
            this.providePipUiEventLoggerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvidePipUiEventLoggerFactory(daggerTvGlobalRootComponent3.provideUiEventLoggerProvider, daggerTvGlobalRootComponent3.providePackageManagerProvider));
            Provider<Context> provider35 = DaggerTvGlobalRootComponent.this.contextProvider;
            Provider<TvPipMenuController> provider36 = this.providesTvPipMenuControllerProvider;
            Provider<SyncTransactionQueue> provider37 = this.provideSyncTransactionQueueProvider;
            Provider<TvPipBoundsState> provider38 = this.provideTvPipBoundsStateProvider;
            Provider<PipTransitionState> provider39 = this.providePipTransitionStateProvider;
            Provider<TvPipBoundsAlgorithm> provider40 = this.provideTvPipBoundsAlgorithmProvider;
            Provider<PipAnimationController> provider41 = this.providePipAnimationControllerProvider;
            Provider<PipTransitionController> provider42 = this.provideTvPipTransitionProvider;
            Provider<PipSurfaceTransactionHelper> provider43 = this.providePipSurfaceTransactionHelperProvider;
            Provider<Optional<LegacySplitScreenController>> provider44 = this.optionalOfLegacySplitScreenControllerProvider;
            Provider<Optional<SplitScreenController>> provider45 = this.providesSplitScreenControllerProvider;
            Provider<DisplayController> provider46 = this.provideDisplayControllerProvider;
            Provider<PipUiEventLogger> provider47 = this.providePipUiEventLoggerProvider;
            Provider<PipUiEventLogger> provider48 = provider47;
            this.providePipTaskOrganizerProvider = DoubleCheck.provider(new TvPipModule_ProvidePipTaskOrganizerFactory(provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider48, this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider));
            this.provideTvPipNotificationControllerProvider = DoubleCheck.provider(new TvPipModule_ProvideTvPipNotificationControllerFactory(DaggerTvGlobalRootComponent.this.contextProvider, this.providePipMediaControllerProvider, this.provideShellMainHandlerProvider));
            this.provideWindowManagerShellWrapperProvider = DoubleCheck.provider(new SecureSettingsImpl_Factory(this.provideShellMainExecutorProvider, 4));
            this.providePipProvider = DoubleCheck.provider(new UiModeNightTile_Factory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideTvPipBoundsStateProvider, this.provideTvPipBoundsAlgorithmProvider, this.providePipTaskOrganizerProvider, this.providesTvPipMenuControllerProvider, this.providePipMediaControllerProvider, this.provideTvPipTransitionProvider, this.provideTvPipNotificationControllerProvider, this.providerTaskStackListenerImplProvider, this.provideWindowManagerShellWrapperProvider, this.provideShellMainExecutorProvider, 1));
            this.dynamicOverrideOptionalOfOneHandedControllerProvider = provider29;
            this.providesOneHandedControllerProvider = DoubleCheck.provider(new PrivacyLogger_Factory(this.dynamicOverrideOptionalOfOneHandedControllerProvider, 5));
            this.provideHideDisplayCutoutControllerProvider = DoubleCheck.provider(new DozeLog_Factory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider, 4));
            Provider<ShellCommandHandlerImpl> provider49 = DoubleCheck.provider(WMShellBaseModule_ProvideShellCommandHandlerImplFactory.create(this.provideShellTaskOrganizerProvider, this.optionalOfLegacySplitScreenControllerProvider, this.providesSplitScreenControllerProvider, this.providePipProvider, this.providesOneHandedControllerProvider, this.provideHideDisplayCutoutControllerProvider, this.optionalOfAppPairsControllerProvider, this.provideRecentTasksControllerProvider, this.provideShellMainExecutorProvider));
            this.provideShellCommandHandlerImplProvider = provider49;
            this.provideShellCommandHandlerProvider = DoubleCheck.provider(new ForegroundServicesDialog_Factory(provider49, 6));
            this.provideOneHandedProvider = DoubleCheck.provider(new DreamOverlayStateController_Factory(this.providesOneHandedControllerProvider, 2));
            this.provideLegacySplitScreenProvider = DoubleCheck.provider(new DozeLogger_Factory(this.optionalOfLegacySplitScreenControllerProvider, 7));
            this.provideSplitScreenProvider = C0773x82ed519e.m54m(this.providesSplitScreenControllerProvider, 0);
            this.provideAppPairsProvider = DoubleCheck.provider(new BootCompleteCacheImpl_Factory(this.optionalOfAppPairsControllerProvider, 4));
            this.provideBubblesProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.optionalOfBubbleControllerProvider, 0));
            this.provideHideDisplayCutoutProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.provideHideDisplayCutoutControllerProvider, 0));
            this.provideTaskViewTransitionsProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.provideTransitionsProvider, 3));
            this.provideTaskViewFactoryControllerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideTaskViewFactoryControllerFactory(this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider, this.provideSyncTransactionQueueProvider, this.provideTaskViewTransitionsProvider));
            this.provideTaskViewFactoryProvider = C0770xb6bb24d8.m51m(this.provideTaskViewFactoryControllerProvider, 5);
            this.provideRemoteTransitionsProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.provideTransitionsProvider, 4));
            this.provideStartingSurfaceProvider = C0772x82ed519d.m53m(this.provideStartingWindowControllerProvider, 6);
            this.provideRootDisplayAreaOrganizerProvider = DoubleCheck.provider(new ImageExporter_Factory(this.provideShellMainExecutorProvider, 6));
            this.provideDisplayAreaHelperProvider = DoubleCheck.provider(new SingleTapClassifier_Factory(this.provideShellMainExecutorProvider, this.provideRootDisplayAreaOrganizerProvider, 1));
            WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory = new WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory(this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider, 0);
            this.provideTaskSurfaceHelperControllerProvider = wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory;
            this.provideTaskSurfaceHelperProvider = DoubleCheck.provider(new DependencyProvider_ProvideHandlerFactory(wMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory, 6));
            this.provideRecentTasksProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideRecentTasksFactory(this.provideRecentTasksControllerProvider, 0));
            this.provideCompatUIProvider = DoubleCheck.provider(new ToastLogger_Factory(this.provideCompatUIControllerProvider, 3));
            this.provideDragAndDropProvider = DoubleCheck.provider(new ColorChangeHandler_Factory(this.provideDragAndDropControllerProvider, 6));
            Provider<Optional<BackAnimationController>> provider50 = DoubleCheck.provider(new DreamOverlayRegistrant_Factory(DaggerTvGlobalRootComponent.this.contextProvider, this.provideShellMainExecutorProvider, 2));
            this.provideBackAnimationControllerProvider = provider50;
            this.provideBackAnimationProvider = C0769xb6bb24d7.m50m(provider50, 6);
        }
    }

    public final SysUIComponent.Builder getSysUIComponent() {
        return new TvSysUIComponentBuilder();
    }

    public final WMComponent.Builder getWMComponentBuilder() {
        return new TvWMComponentBuilder();
    }

    public static TvGlobalRootComponent.Builder builder() {
        return new Builder(0);
    }

    /* renamed from: getSysUIComponent  reason: collision with other method in class */
    public final TvSysUIComponent.Builder m256getSysUIComponent() {
        return new TvSysUIComponentBuilder();
    }

    /* renamed from: getWMComponentBuilder  reason: collision with other method in class */
    public final TvWMComponent.Builder m257getWMComponentBuilder() {
        return new TvWMComponentBuilder();
    }

    public final void initialize(GlobalModule globalModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context2) {
        UnfoldTransitionModule unfoldTransitionModule2 = unfoldTransitionModule;
        UnfoldSharedModule unfoldSharedModule2 = unfoldSharedModule;
        this.contextProvider = InstanceFactory.create(context2);
        this.provideIWindowManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIWindowManagerFactory.InstanceHolder.INSTANCE);
        this.provideUiEventLoggerProvider = DoubleCheck.provider(GlobalModule_ProvideUiEventLoggerFactory.InstanceHolder.INSTANCE);
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
        GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory2 = globalConcurrencyModule_ProvideMainLooperFactory;
        Provider<Optional<UnfoldTransitionProgressProvider>> provider8 = DoubleCheck.provider(new UnfoldSharedModule_UnfoldTransitionProgressProviderFactory(unfoldSharedModule2, this.provideUnfoldTransitionConfigProvider, this.factoryProvider, this.aTraceLoggerTransitionProgressListenerProvider, provider7));
        this.unfoldTransitionProgressProvider = provider8;
        this.provideShellProgressProvider = DoubleCheck.provider(new ChargingState_Factory(unfoldTransitionModule2, this.provideUnfoldTransitionConfigProvider, provider8));
        this.providePackageManagerProvider = DoubleCheck.provider(new UsbDebuggingActivity_Factory(this.contextProvider, 1));
        this.provideUserManagerProvider = DoubleCheck.provider(new LogModule_ProvidePrivacyLogBufferFactory(this.contextProvider, 2));
        this.providesPluginExecutorProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginExecutorFactory.create(ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE));
        this.provideNotificationManagerProvider = DoubleCheck.provider(new MediaFlags_Factory(this.contextProvider, 1));
        this.pluginEnablerImplProvider = DoubleCheck.provider(PluginEnablerImpl_Factory.create(this.contextProvider, this.providePackageManagerProvider));
        PluginsModule_ProvidesPrivilegedPluginsFactory create2 = PluginsModule_ProvidesPrivilegedPluginsFactory.create(this.contextProvider);
        this.providesPrivilegedPluginsProvider = create2;
        Provider<PluginInstance.Factory> provider9 = DoubleCheck.provider(PluginsModule_ProvidesPluginInstanceFactoryFactory.create(create2, PluginsModule_ProvidesPluginDebugFactory.create()));
        this.providesPluginInstanceFactoryProvider = provider9;
        this.providePluginInstanceManagerFactoryProvider = DoubleCheck.provider(PluginsModule_ProvidePluginInstanceManagerFactoryFactory.create(this.contextProvider, this.providePackageManagerProvider, this.provideMainExecutorProvider, this.providesPluginExecutorProvider, this.provideNotificationManagerProvider, this.pluginEnablerImplProvider, this.providesPrivilegedPluginsProvider, provider9));
        this.providesPluginPrefsProvider = PluginsModule_ProvidesPluginPrefsFactory.create(this.contextProvider);
        this.providesPluginManagerProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginManagerFactory.create(this.contextProvider, this.providePluginInstanceManagerFactoryProvider, PluginsModule_ProvidesPluginDebugFactory.create(), GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory.InstanceHolder.INSTANCE, this.pluginEnablerImplProvider, this.providesPluginPrefsProvider, this.providesPrivilegedPluginsProvider));
        Provider<Context> provider10 = this.contextProvider;
        this.provideDisplayMetricsProvider = new SystemUIDialogManager_Factory(globalModule, provider10);
        this.providePowerManagerProvider = DoubleCheck.provider(new UsbDebuggingSecondaryUserActivity_Factory(provider10, 1));
        this.provideViewConfigurationProvider = DoubleCheck.provider(new KeyboardUI_Factory(this.contextProvider, 2));
        this.provideResourcesProvider = new ForegroundServicesDialog_Factory(this.contextProvider, 1);
        this.provideInteractionJankMonitorProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideInteractionJankMonitorFactory.InstanceHolder.INSTANCE);
        this.provideExecutionProvider = DoubleCheck.provider(ExecutionImpl_Factory.InstanceHolder.INSTANCE);
        this.provideActivityTaskManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideActivityTaskManagerFactory.InstanceHolder.INSTANCE);
        this.provideWindowManagerProvider = C0770xb6bb24d8.m51m(this.contextProvider, 1);
        this.providesFingerprintManagerProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.contextProvider, 1));
        this.provideFaceManagerProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideFaceManagerFactory(this.contextProvider, 0));
        this.provideMainDelayableExecutorProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(globalConcurrencyModule_ProvideMainLooperFactory2, 3));
        this.provideTrustManagerProvider = DoubleCheck.provider(new QSFragmentModule_ProvideThemedContextFactory(this.contextProvider, 2));
        this.provideIActivityManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIActivityManagerFactory.InstanceHolder.INSTANCE);
        this.provideDevicePolicyManagerProvider = DoubleCheck.provider(new DozeAuthRemover_Factory(this.contextProvider, 1));
        this.provideIStatusBarServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIStatusBarServiceFactory.InstanceHolder.INSTANCE);
        this.provideKeyguardManagerProvider = DoubleCheck.provider(new WallpaperController_Factory(this.contextProvider, 2));
        this.provideLauncherAppsProvider = DoubleCheck.provider(new EnhancedEstimatesGoogleImpl_Factory(this.contextProvider, 1));
        this.providePackageManagerWrapperProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePackageManagerWrapperFactory.InstanceHolder.INSTANCE);
        Provider<Optional<NaturalRotationUnfoldProgressProvider>> provider11 = DoubleCheck.provider(new MediaDreamSentinel_Factory(unfoldTransitionModule2, this.contextProvider, this.provideIWindowManagerProvider, this.unfoldTransitionProgressProvider));
        this.provideNaturalRotationProgressProvider = provider11;
        this.provideStatusBarScopedTransitionProvider = DoubleCheck.provider(new NotificationPanelUnfoldAnimationController_Factory(unfoldTransitionModule2, (Provider) provider11));
        this.provideMediaSessionManagerProvider = new DreamOverlayStateController_Factory(this.contextProvider, 1);
        this.provideMediaRouter2ManagerProvider = new DozeLogger_Factory(this.contextProvider, 1);
        this.provideAudioManagerProvider = DoubleCheck.provider(new PackageManagerAdapter_Factory(this.contextProvider, 1));
        this.provideSensorPrivacyManagerProvider = DoubleCheck.provider(new PowerSaveState_Factory(this.contextProvider, 1));
        this.provideIDreamManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIDreamManagerFactory.InstanceHolder.INSTANCE);
        this.provideDisplayIdProvider = new ColorChangeHandler_Factory(this.contextProvider, 2);
        this.provideSubcriptionManagerProvider = C0773x82ed519e.m54m(this.contextProvider, 2);
        this.provideConnectivityManagagerProvider = DoubleCheck.provider(new TypeClassifier_Factory(this.contextProvider, 3));
        this.provideTelephonyManagerProvider = DoubleCheck.provider(new QSFragmentModule_ProvideRootViewFactory(this.contextProvider, 2));
        this.provideWifiManagerProvider = DoubleCheck.provider(new ActivityIntentHelper_Factory(this.contextProvider, 1));
        this.provideNetworkScoreManagerProvider = DoubleCheck.provider(new QSLogger_Factory(this.contextProvider, 1));
        this.provideAccessibilityManagerProvider = DoubleCheck.provider(new SystemUIModule_ProvideLowLightClockControllerFactory(this.contextProvider, 1));
        this.taskbarDelegateProvider = DoubleCheck.provider(new TaskbarDelegate_Factory(this.contextProvider, 0));
        this.provideCrossWindowBlurListenersProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory.InstanceHolder.INSTANCE);
        this.provideAlarmManagerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideBubblesFactory(this.contextProvider, 1));
        this.provideLatencyTrackerProvider = DoubleCheck.provider(new WMShellBaseModule_ProvideHideDisplayCutoutFactory(this.contextProvider, 1));
        this.provideWallpaperManagerProvider = new DependencyProvider_ProvideHandlerFactory(this.contextProvider, 1);
        this.provideOptionalTelecomManagerProvider = DoubleCheck.provider(new MediaOutputDialogReceiver_Factory(this.contextProvider, 1));
        this.provideInputMethodManagerProvider = DoubleCheck.provider(new StatusBarInitializer_Factory(this.contextProvider, 1));
        this.provideShortcutManagerProvider = C0771xb6bb24d9.m52m(this.contextProvider, 1);
        this.provideVibratorProvider = DoubleCheck.provider(new VrMode_Factory(this.contextProvider, 2));
        this.provideIAudioServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIAudioServiceFactory.InstanceHolder.INSTANCE);
        this.pluginDependencyProvider = DoubleCheck.provider(PluginDependencyProvider_Factory.create(this.providesPluginManagerProvider));
        this.provideTelecomManagerProvider = C0772x82ed519d.m53m(this.contextProvider, 2);
        this.provideIBatteryStatsProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIBatteryStatsFactory.InstanceHolder.INSTANCE);
        this.provideDisplayManagerProvider = DoubleCheck.provider(new MediaControllerFactory_Factory(this.contextProvider, 2));
        this.provideActivityManagerProvider = C0769xb6bb24d7.m50m(this.contextProvider, 1);
        Provider<Optional<FoldStateLoggingProvider>> provider12 = DoubleCheck.provider(new DarkIconDispatcherImpl_Factory(unfoldTransitionModule2, this.provideUnfoldTransitionConfigProvider, this.provideFoldStateProvider));
        this.providesFoldStateLoggingProvider = provider12;
        this.providesFoldStateLoggerProvider = DoubleCheck.provider(new KeyguardVisibility_Factory(unfoldTransitionModule2, provider12));
        this.provideColorDisplayManagerProvider = DoubleCheck.provider(new NavigationBarOverlayController_Factory(this.contextProvider, 1));
        this.provideIPackageManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIPackageManagerFactory.InstanceHolder.INSTANCE);
        this.provideSmartspaceManagerProvider = DoubleCheck.provider(new SmartActionsReceiver_Factory(this.contextProvider, 1));
        this.providePermissionManagerProvider = DoubleCheck.provider(new ImageExporter_Factory(this.contextProvider, 2));
        this.provideOptionalVibratorProvider = DoubleCheck.provider(new FrameworkServicesModule_ProvideOptionalVibratorFactory(this.contextProvider, 0));
        this.qSExpansionPathInterpolatorProvider = DoubleCheck.provider(QSExpansionPathInterpolator_Factory.InstanceHolder.INSTANCE);
    }

    public final Resources mainResources() {
        Resources resources = this.context.getResources();
        Objects.requireNonNull(resources, "Cannot return null from a non-@Nullable @Provides method");
        return resources;
    }

    public DaggerTvGlobalRootComponent(GlobalModule globalModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context2) {
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
