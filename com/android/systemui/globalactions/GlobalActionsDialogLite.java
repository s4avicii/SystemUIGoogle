package com.android.systemui.globalactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.sysprop.TelephonyProperties;
import android.telecom.TelecomManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.util.ArraySet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellInitImpl$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda5;
import com.android.systemui.MultiListLayout;
import com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.model.SysUiState;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda5;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.plugins.GlobalActionsPanelPlugin;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class GlobalActionsDialogLite implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener, ConfigurationController.ConfigurationListener, GlobalActionsPanelPlugin.Callbacks, LifecycleOwner {
    @VisibleForTesting
    public static final String GLOBAL_ACTION_KEY_POWER = "power";
    public MyAdapter mAdapter;
    public final C08157 mAirplaneModeObserver;
    public AirplaneModeAction mAirplaneModeOn;
    public ToggleState mAirplaneState = ToggleState.Off;
    public final AudioManager mAudioManager;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public C08135 mBroadcastReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                String stringExtra = intent.getStringExtra("reason");
                if (!"globalactions".equals(stringExtra)) {
                    C08168 r2 = GlobalActionsDialogLite.this.mHandler;
                    r2.sendMessage(r2.obtainMessage(0, stringExtra));
                }
            } else if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false)) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mIsWaitingForEcmExit) {
                    globalActionsDialogLite.mIsWaitingForEcmExit = false;
                    GlobalActionsDialogLite.m198$$Nest$mchangeAirplaneModeSystemSetting(globalActionsDialogLite, true);
                }
            }
        }
    };
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mDeviceProvisioned = false;
    @VisibleForTesting
    public ActionsDialogLite mDialog;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final SystemUIDialogManager mDialogManager;
    public int mDialogPressDelay = 850;
    public final IDreamManager mDreamManager;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public final GlobalSettings mGlobalSettings;
    public C08168 mHandler;
    public boolean mHasTelephony;
    public boolean mHasVibrator;
    public final IActivityManager mIActivityManager;
    public final IWindowManager mIWindowManager;
    public boolean mIsWaitingForEcmExit = false;
    @VisibleForTesting
    public final ArrayList<Action> mItems = new ArrayList<>();
    public boolean mKeyguardShowing = false;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this, true);
    public final LockPatternUtils mLockPatternUtils;
    public Handler mMainHandler;
    public final MetricsLogger mMetricsLogger;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public MyOverflowAdapter mOverflowAdapter;
    @VisibleForTesting
    public final ArrayList<Action> mOverflowItems = new ArrayList<>();
    public final C08146 mPhoneStateListener;
    public MyPowerOptionsAdapter mPowerAdapter;
    @VisibleForTesting
    public final ArrayList<Action> mPowerItems = new ArrayList<>();
    public final Resources mResources;
    public final RingerModeTracker mRingerModeTracker;
    public final ScreenshotHelper mScreenshotHelper;
    public final SecureSettings mSecureSettings;
    public final boolean mShowSilentToggle;
    public Action mSilentModeAction;
    public int mSmallestScreenWidthDp;
    public final Optional<StatusBar> mStatusBarOptional;
    public final IStatusBarService mStatusBarService;
    public final SysUiState mSysUiState;
    public final SysuiColorExtractor mSysuiColorExtractor;
    public final TelecomManager mTelecomManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TrustManager mTrustManager;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final GlobalActions.GlobalActionsManager mWindowManagerFuncs;

    public interface Action {
        View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater);

        Drawable getIcon(Context context);

        CharSequence getMessage();

        int getMessageResId();

        boolean isEnabled();

        void onPress();

        void shouldBeSeparated() {
        }

        boolean shouldShow() {
            return true;
        }

        boolean showBeforeProvisioning();

        void showDuringKeyguard();
    }

    @VisibleForTesting
    public static class ActionsDialogLite extends SystemUIDialog implements ColorExtractor.OnColorsChangedListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final MyAdapter mAdapter;
        public ScrimDrawable mBackgroundDrawable;
        public final SysuiColorExtractor mColorExtractor;
        public ViewGroup mContainer;
        public final Context mContext;
        public GestureDetector mGestureDetector;
        @VisibleForTesting
        public GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            public final boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (f2 <= 0.0f || Math.abs(f2) <= Math.abs(f) || motionEvent.getY() > ((float) ((Integer) ActionsDialogLite.this.mStatusBarOptional.map(PhoneStateMonitor$$ExternalSyntheticLambda1.INSTANCE$2).orElse(0)).intValue())) {
                    return false;
                }
                ActionsDialogLite.m199$$Nest$mopenShadeAndDismiss(ActionsDialogLite.this);
                return true;
            }

            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (f2 >= 0.0f || f2 <= f || motionEvent.getY() > ((float) ((Integer) ActionsDialogLite.this.mStatusBarOptional.map(C0833xc30ba73b.INSTANCE).orElse(0)).intValue())) {
                    return false;
                }
                ActionsDialogLite.m199$$Nest$mopenShadeAndDismiss(ActionsDialogLite.this);
                return true;
            }

            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                ActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
                ActionsDialogLite.this.cancel();
                return false;
            }
        };
        public MultiListLayout mGlobalActionsLayout;
        public boolean mKeyguardShowing;
        public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public LockPatternUtils mLockPatternUtils;
        public final NotificationShadeWindowController mNotificationShadeWindowController;
        public final Runnable mOnRefreshCallback;
        public final MyOverflowAdapter mOverflowAdapter;
        public GlobalActionsPopupMenu mOverflowPopup;
        public final MyPowerOptionsAdapter mPowerOptionsAdapter;
        public Dialog mPowerOptionsDialog;
        public Optional<StatusBar> mStatusBarOptional;
        public final SysUiState mSysUiState;
        public UiEventLogger mUiEventLogger;
        public float mWindowDimAmount;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ActionsDialogLite(Context context, MyAdapter myAdapter, MyOverflowAdapter myOverflowAdapter, SysuiColorExtractor sysuiColorExtractor, NotificationShadeWindowController notificationShadeWindowController, SysUiState sysUiState, TaskView$$ExternalSyntheticLambda4 taskView$$ExternalSyntheticLambda4, boolean z, MyPowerOptionsAdapter myPowerOptionsAdapter, UiEventLogger uiEventLogger, Optional optional, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, SystemUIDialogManager systemUIDialogManager) {
            super(context, 2132018187, false, systemUIDialogManager);
            new Binder();
            this.mContext = context;
            this.mAdapter = myAdapter;
            this.mOverflowAdapter = myOverflowAdapter;
            this.mPowerOptionsAdapter = myPowerOptionsAdapter;
            this.mColorExtractor = sysuiColorExtractor;
            this.mNotificationShadeWindowController = notificationShadeWindowController;
            this.mSysUiState = sysUiState;
            this.mOnRefreshCallback = taskView$$ExternalSyntheticLambda4;
            this.mKeyguardShowing = z;
            this.mUiEventLogger = uiEventLogger;
            this.mStatusBarOptional = optional;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
        }

        public final int getHeight() {
            return -1;
        }

        public final int getWidth() {
            return -1;
        }

        public final void startAnimation(boolean z, final C0829x58d1e4b4 globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6) {
            float f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            Resources resources = getContext().getResources();
            if (z) {
                f = resources.getDimension(17105457);
                ofFloat.setInterpolator(Interpolators.STANDARD);
                ofFloat.setDuration((long) resources.getInteger(17694730));
            } else {
                f = resources.getDimension(17105458);
                ofFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
                ofFloat.setDuration((long) resources.getInteger(17694731));
            }
            Window window = getWindow();
            ofFloat.addUpdateListener(new C0823x58d1e4ae(this, z, window, f, window.getWindowManager().getDefaultDisplay().getRotation()));
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public int mPreviousLayerType;

                public final void onAnimationEnd(Animator animator) {
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(this.mPreviousLayerType, (Paint) null);
                    Runnable runnable = globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6;
                    if (runnable != null) {
                        runnable.run();
                    }
                }

                public final void onAnimationStart(Animator animator, boolean z) {
                    this.mPreviousLayerType = ActionsDialogLite.this.mGlobalActionsLayout.getLayerType();
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(2, (Paint) null);
                }
            });
            ofFloat.start();
        }

        public final void dismiss() {
            GlobalActionsPopupMenu globalActionsPopupMenu = this.mOverflowPopup;
            if (globalActionsPopupMenu != null) {
                globalActionsPopupMenu.dismiss();
            }
            Dialog dialog = this.mPowerOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            this.mNotificationShadeWindowController.setRequestTopUi(false, "GlobalActionsDialogLite");
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(32768, false);
            sysUiState.commitUpdate(this.mContext.getDisplayId());
            super.dismiss();
        }

        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            if (this.mKeyguardShowing) {
                if ((i & 2) != 0) {
                    updateColors(colorExtractor.getColors(2), true);
                }
            } else if ((i & 1) != 0) {
                updateColors(colorExtractor.getColors(1), true);
            }
        }

        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (this.mGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent)) {
                return true;
            }
            return false;
        }

        public final void updateColors(ColorExtractor.GradientColors gradientColors, boolean z) {
            ScrimDrawable scrimDrawable = this.mBackgroundDrawable;
            if (scrimDrawable instanceof ScrimDrawable) {
                scrimDrawable.setColor(-16777216, z);
                View decorView = getWindow().getDecorView();
                if (gradientColors.supportsDarkText()) {
                    decorView.setSystemUiVisibility(8208);
                } else {
                    decorView.setSystemUiVisibility(0);
                }
            }
        }

        /* renamed from: -$$Nest$mopenShadeAndDismiss  reason: not valid java name */
        public static void m199$$Nest$mopenShadeAndDismiss(ActionsDialogLite actionsDialogLite) {
            Objects.requireNonNull(actionsDialogLite);
            actionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
            if (((Boolean) actionsDialogLite.mStatusBarOptional.map(WMShellBaseModule$$ExternalSyntheticLambda5.INSTANCE$1).orElse(Boolean.FALSE)).booleanValue()) {
                actionsDialogLite.mStatusBarOptional.ifPresent(C0832x58d1e4b7.INSTANCE);
            } else {
                actionsDialogLite.mStatusBarOptional.ifPresent(C0831x58d1e4b6.INSTANCE);
            }
            actionsDialogLite.dismiss();
        }

        public final void onBackPressed() {
            super.onBackPressed();
            this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_BACK);
        }

        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setContentView(C1777R.layout.global_actions_grid_lite);
            ViewGroup viewGroup = (ViewGroup) findViewById(16908290);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            viewGroup2.setClipChildren(false);
            viewGroup2.setClipToPadding(false);
            MultiListLayout multiListLayout = (MultiListLayout) findViewById(C1777R.C1779id.global_actions_view);
            this.mGlobalActionsLayout = multiListLayout;
            C08182 r1 = new View.AccessibilityDelegate() {
                public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    accessibilityEvent.getText().add(ActionsDialogLite.this.mContext.getString(17040396));
                    return true;
                }
            };
            Objects.requireNonNull(multiListLayout);
            multiListLayout.getListView().setAccessibilityDelegate(r1);
            MultiListLayout multiListLayout2 = this.mGlobalActionsLayout;
            C0828x58d1e4b3 globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5 = new C0828x58d1e4b3(this);
            Objects.requireNonNull(multiListLayout2);
            multiListLayout2.mRotationListener = globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5;
            MultiListLayout multiListLayout3 = this.mGlobalActionsLayout;
            MyAdapter myAdapter = this.mAdapter;
            Objects.requireNonNull(multiListLayout3);
            multiListLayout3.mAdapter = myAdapter;
            ViewGroup viewGroup3 = (ViewGroup) findViewById(C1777R.C1779id.global_actions_container);
            this.mContainer = viewGroup3;
            viewGroup3.setOnTouchListener(new C0825x58d1e4b0(this));
            View findViewById = findViewById(C1777R.C1779id.global_actions_overflow_button);
            if (findViewById != null) {
                if (this.mOverflowAdapter.getCount() > 0) {
                    findViewById.setOnClickListener(new C0824x58d1e4af(this));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams.setMarginEnd(0);
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams);
                } else {
                    findViewById.setVisibility(8);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams2.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.global_actions_side_margin));
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams2);
                }
            }
            if (this.mBackgroundDrawable == null) {
                this.mBackgroundDrawable = new ScrimDrawable();
            }
            boolean userHasTrust = this.mKeyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
            if (this.mKeyguardShowing && userHasTrust) {
                this.mLockPatternUtils.requireCredentialEntry(KeyguardUpdateMonitor.getCurrentUser());
                final View inflate = LayoutInflater.from(this.mContext).inflate(C1777R.layout.global_actions_toast, this.mContainer, false);
                final int recommendedTimeoutMillis = ((AccessibilityManager) getContext().getSystemService("accessibility")).getRecommendedTimeoutMillis(3500, 2);
                inflate.setVisibility(0);
                inflate.setAlpha(0.0f);
                this.mContainer.addView(inflate);
                inflate.animate().alpha(1.0f).setDuration(333).setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        inflate.animate().alpha(0.0f).setDuration(333).setStartDelay((long) recommendedTimeoutMillis).setListener((Animator.AnimatorListener) null);
                    }
                });
            }
            this.mWindowDimAmount = getWindow().getAttributes().dimAmount;
        }

        public final void onStart() {
            ColorExtractor.GradientColors gradientColors;
            super.onStart();
            MultiListLayout multiListLayout = this.mGlobalActionsLayout;
            Objects.requireNonNull(multiListLayout);
            if (multiListLayout.mAdapter != null) {
                multiListLayout.onUpdateList();
                if (this.mBackgroundDrawable instanceof ScrimDrawable) {
                    this.mColorExtractor.addOnColorsChangedListener(this);
                    SysuiColorExtractor sysuiColorExtractor = this.mColorExtractor;
                    Objects.requireNonNull(sysuiColorExtractor);
                    if (sysuiColorExtractor.mHasMediaArtwork) {
                        gradientColors = sysuiColorExtractor.mBackdropColors;
                    } else {
                        gradientColors = sysuiColorExtractor.mNeutralColorsLock;
                    }
                    updateColors(gradientColors, false);
                    return;
                }
                return;
            }
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }

        public final void onStop() {
            super.onStop();
            this.mColorExtractor.removeOnColorsChangedListener(this);
        }

        public final void show() {
            boolean z;
            super.show();
            this.mNotificationShadeWindowController.setRequestTopUi(true, "GlobalActionsDialogLite");
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(32768, true);
            sysUiState.commitUpdate(this.mContext.getDisplayId());
            if (getWindow().getAttributes().windowAnimations == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                startAnimation(true, (C0829x58d1e4b4) null);
                setDismissOverride(new C0830x58d1e4b5(this));
            }
        }
    }

    public class AirplaneModeAction extends ToggleAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public AirplaneModeAction() {
            super(17302485, 17302487, 17040398, 17040397);
        }

        public final void changeStateFromPress(boolean z) {
            ToggleState toggleState;
            if (GlobalActionsDialogLite.this.mHasTelephony && !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                if (z) {
                    toggleState = ToggleState.TurningOn;
                } else {
                    toggleState = ToggleState.TurningOff;
                }
                this.mState = toggleState;
                GlobalActionsDialogLite.this.mAirplaneState = toggleState;
            }
        }

        public final void onToggle(boolean z) {
            if (!GlobalActionsDialogLite.this.mHasTelephony || !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                GlobalActionsDialogLite.m198$$Nest$mchangeAirplaneModeSystemSetting(GlobalActionsDialogLite.this, z);
                return;
            }
            GlobalActionsDialogLite.this.mIsWaitingForEcmExit = true;
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(268435456);
            GlobalActionsDialogLite.this.mContext.startActivity(intent);
        }
    }

    @VisibleForTesting
    public class BugReportAction extends SinglePressAction implements LongPressAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public BugReportAction() {
            super(17302489, 17039811);
        }

        public final boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                GlobalActionsDialogLite.this.mMetricsLogger.action(293);
                GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_LONG_PRESS);
                GlobalActionsDialogLite.this.mIActivityManager.requestFullBugReport();
                GlobalActionsDialogLite.this.mStatusBarOptional.ifPresent(QSTileHost$$ExternalSyntheticLambda5.INSTANCE$2);
            } catch (RemoteException unused) {
            }
            return false;
        }

        public final void onPress() {
            if (!ActivityManager.isUserAMonkey()) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                globalActionsDialogLite.mHandler.postDelayed(new Runnable() {
                    public final void run() {
                        try {
                            GlobalActionsDialogLite.this.mMetricsLogger.action(292);
                            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_PRESS);
                            if (!GlobalActionsDialogLite.this.mIActivityManager.launchBugReportHandlerApp()) {
                                Log.w("GlobalActionsDialogLite", "Bugreport handler could not be launched");
                                GlobalActionsDialogLite.this.mIActivityManager.requestInteractiveBugReport();
                            }
                            GlobalActionsDialogLite.this.mStatusBarOptional.ifPresent(SystemActions$$ExternalSyntheticLambda2.INSTANCE$2);
                        } catch (RemoteException unused) {
                        }
                    }
                }, (long) globalActionsDialogLite.mDialogPressDelay);
            }
        }
    }

    @VisibleForTesting
    public abstract class EmergencyAction extends SinglePressAction {
        public final void shouldBeSeparated() {
        }

        public final boolean showBeforeProvisioning() {
            return true;
        }

        public final void showDuringKeyguard() {
        }

        public EmergencyAction(int i) {
            super(i, 17040383);
        }

        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View create = super.create(context, view, viewGroup, layoutInflater);
            Objects.requireNonNull(GlobalActionsDialogLite.this);
            int color = context.getResources().getColor(C1777R.color.global_actions_lite_text);
            Objects.requireNonNull(GlobalActionsDialogLite.this);
            int color2 = context.getResources().getColor(C1777R.color.global_actions_lite_emergency_icon);
            Objects.requireNonNull(GlobalActionsDialogLite.this);
            int color3 = context.getResources().getColor(C1777R.color.global_actions_lite_emergency_background);
            TextView textView = (TextView) create.findViewById(16908299);
            textView.setTextColor(color);
            textView.setSelected(true);
            ImageView imageView = (ImageView) create.findViewById(16908294);
            imageView.getDrawable().setTint(color2);
            imageView.setBackgroundTintList(ColorStateList.valueOf(color3));
            create.setBackgroundTintList(ColorStateList.valueOf(color3));
            return create;
        }
    }

    public class EmergencyAffordanceAction extends EmergencyAction {
        public EmergencyAffordanceAction() {
            super(17302208);
        }

        public final void onPress() {
            GlobalActionsDialogLite.this.mEmergencyAffordanceManager.performEmergencyCall();
        }
    }

    @VisibleForTesting
    public class EmergencyDialerAction extends EmergencyAction {
        public EmergencyDialerAction() {
            super(C1777R.C1778drawable.ic_emergency_star);
        }

        public final void onPress() {
            GlobalActionsDialogLite.this.mMetricsLogger.action(1569);
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_EMERGENCY_DIALER_PRESS);
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mTelecomManager != null) {
                globalActionsDialogLite.mStatusBarOptional.ifPresent(ShellInitImpl$$ExternalSyntheticLambda6.INSTANCE$1);
                Intent createLaunchEmergencyDialerIntent = GlobalActionsDialogLite.this.mTelecomManager.createLaunchEmergencyDialerIntent((String) null);
                createLaunchEmergencyDialerIntent.addFlags(343932928);
                createLaunchEmergencyDialerIntent.putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 2);
                GlobalActionsDialogLite.this.mContext.startActivityAsUser(createLaunchEmergencyDialerIntent, UserHandle.CURRENT);
            }
        }
    }

    @VisibleForTesting
    public class LockDownAction extends SinglePressAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public LockDownAction() {
            super(17302492, 17040385);
        }

        public final void onPress() {
            GlobalActionsDialogLite.this.mLockPatternUtils.requireStrongAuth(32, -1);
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_LOCKDOWN_PRESS);
            try {
                GlobalActionsDialogLite.this.mIWindowManager.lockNow((Bundle) null);
                GlobalActionsDialogLite.this.mBackgroundExecutor.execute(new GlobalActionsDialogLite$LockDownAction$$ExternalSyntheticLambda0(this));
            } catch (RemoteException e) {
                Log.e("GlobalActionsDialogLite", "Error while trying to lock device.", e);
            }
        }
    }

    public final class LogoutAction extends SinglePressAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public LogoutAction() {
            super(17302539, 17040386);
        }

        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mHandler.postDelayed(new StatusBar$$ExternalSyntheticLambda18(this, 3), (long) globalActionsDialogLite.mDialogPressDelay);
        }
    }

    public interface LongPressAction extends Action {
        boolean onLongPress();
    }

    public class MyAdapter extends MultiListLayout.MultiListAdapter {
        public final boolean areAllItemsEnabled() {
            return false;
        }

        public final int countItems(boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < GlobalActionsDialogLite.this.mItems.size(); i2++) {
                GlobalActionsDialogLite.this.mItems.get(i2).shouldBeSeparated();
                if (!z) {
                    i++;
                }
            }
            return i;
        }

        public final int getCount() {
            return countItems(false) + countItems(true);
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public MyAdapter() {
        }

        public final Action getItem(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < GlobalActionsDialogLite.this.mItems.size(); i3++) {
                Action action = GlobalActionsDialogLite.this.mItems.get(i3);
                if (GlobalActionsDialogLite.this.shouldShowAction(action)) {
                    if (i2 == i) {
                        return action;
                    }
                    i2++;
                }
            }
            StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("position ", i, " out of range of showable actions, filtered count=");
            m.append(getCount());
            m.append(", keyguardshowing=");
            m.append(GlobalActionsDialogLite.this.mKeyguardShowing);
            m.append(", provisioned=");
            m.append(GlobalActionsDialogLite.this.mDeviceProvisioned);
            throw new IllegalArgumentException(m.toString());
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            Context context = GlobalActionsDialogLite.this.mContext;
            View create = item.create(context, view, viewGroup, LayoutInflater.from(context));
            create.setOnClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(this, i));
            if (item instanceof LongPressAction) {
                create.setOnLongClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(this, i));
            }
            return create;
        }

        public final boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }
    }

    public class MyOverflowAdapter extends BaseAdapter {
        public final long getItemId(int i) {
            return (long) i;
        }

        public MyOverflowAdapter() {
        }

        public final int getCount() {
            return GlobalActionsDialogLite.this.mOverflowItems.size();
        }

        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mOverflowItems.get(i);
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i);
            if (action == null) {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("No overflow action found at position: ", i, "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(C1777R.layout.controls_more_item, viewGroup, false);
            }
            TextView textView = (TextView) view;
            if (action.getMessageResId() != 0) {
                textView.setText(action.getMessageResId());
            } else {
                textView.setText(action.getMessage());
            }
            return textView;
        }
    }

    public class MyPowerOptionsAdapter extends BaseAdapter {
        public final long getItemId(int i) {
            return (long) i;
        }

        public MyPowerOptionsAdapter() {
        }

        public final int getCount() {
            return GlobalActionsDialogLite.this.mPowerItems.size();
        }

        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mPowerItems.get(i);
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
            if (action == null) {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("No power options action found at position: ", i, "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(C1777R.layout.global_actions_power_item, viewGroup, false);
            }
            view.setOnClickListener(new C0834xb6603ee5(this, i));
            if (action instanceof LongPressAction) {
                view.setOnLongClickListener(new C0835xb6603ee6(this, i));
            }
            ImageView imageView = (ImageView) view.findViewById(16908294);
            TextView textView = (TextView) view.findViewById(16908299);
            textView.setSelected(true);
            imageView.setImageDrawable(action.getIcon(GlobalActionsDialogLite.this.mContext));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (action.getMessage() != null) {
                textView.setText(action.getMessage());
            } else {
                textView.setText(action.getMessageResId());
            }
            return view;
        }
    }

    @VisibleForTesting
    public final class PowerOptionsAction extends SinglePressAction {
        public final boolean showBeforeProvisioning() {
            return true;
        }

        public final void showDuringKeyguard() {
        }

        public PowerOptionsAction() {
            super((int) C1777R.C1778drawable.ic_settings_power, 17040388);
        }

        public final void onPress() {
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite != null) {
                Objects.requireNonNull(actionsDialogLite);
                Context context = actionsDialogLite.mContext;
                MyPowerOptionsAdapter myPowerOptionsAdapter = actionsDialogLite.mPowerOptionsAdapter;
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(C1777R.layout.global_actions_power_dialog, (ViewGroup) null);
                for (int i = 0; i < myPowerOptionsAdapter.getCount(); i++) {
                    viewGroup.addView(myPowerOptionsAdapter.getView(i, (View) null, viewGroup));
                }
                Resources resources = context.getResources();
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewGroup);
                Window window = dialog.getWindow();
                window.setType(2020);
                window.setTitle("");
                window.setBackgroundDrawable(resources.getDrawable(C1777R.C1778drawable.control_background, context.getTheme()));
                window.addFlags(131072);
                actionsDialogLite.mPowerOptionsDialog = dialog;
                dialog.show();
            }
        }
    }

    @VisibleForTesting
    public final class RestartAction extends SinglePressAction implements LongPressAction {
        public final boolean showBeforeProvisioning() {
            return true;
        }

        public final void showDuringKeyguard() {
        }

        public RestartAction() {
            super(17302830, 17040389);
        }

        public final boolean onLongPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_LONG_PRESS);
            if (GlobalActionsDialogLite.this.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            GlobalActionsDialogLite.this.mWindowManagerFuncs.reboot(true);
            return true;
        }

        public final void onPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_PRESS);
            GlobalActionsDialogLite.this.mWindowManagerFuncs.reboot(false);
        }
    }

    @VisibleForTesting
    public class ScreenshotAction extends SinglePressAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public ScreenshotAction() {
            super(17302832, 17040390);
        }

        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mHandler.postDelayed(new Runnable() {
                public final void run() {
                    GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                    globalActionsDialogLite.mScreenshotHelper.takeScreenshot(1, true, true, 0, globalActionsDialogLite.mHandler, (Consumer) null);
                    GlobalActionsDialogLite.this.mMetricsLogger.action(1282);
                    GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SCREENSHOT_PRESS);
                }
            }, (long) globalActionsDialogLite.mDialogPressDelay);
        }

        public final boolean shouldShow() {
            if (1 == GlobalActionsDialogLite.this.mContext.getResources().getInteger(17694878)) {
                return true;
            }
            return false;
        }
    }

    @VisibleForTesting
    public final class ShutDownAction extends SinglePressAction implements LongPressAction {
        public final boolean showBeforeProvisioning() {
            return true;
        }

        public final void showDuringKeyguard() {
        }

        public ShutDownAction() {
            super(17301552, 17040387);
        }

        public final boolean onLongPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_LONG_PRESS);
            if (GlobalActionsDialogLite.this.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            GlobalActionsDialogLite.this.mWindowManagerFuncs.reboot(true);
            return true;
        }

        public final void onPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_PRESS);
            GlobalActionsDialogLite.this.mWindowManagerFuncs.shutdown();
        }
    }

    public class SilentModeToggleAction extends ToggleAction {
        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public SilentModeToggleAction() {
            super(17302323, 17302322, 17040393, 17040392);
        }

        public final void onToggle(boolean z) {
            if (z) {
                GlobalActionsDialogLite.this.mAudioManager.setRingerMode(0);
            } else {
                GlobalActionsDialogLite.this.mAudioManager.setRingerMode(2);
            }
        }
    }

    public abstract class SinglePressAction implements Action {
        public final Drawable mIcon;
        public final int mIconResId;
        public final CharSequence mMessage;
        public final int mMessageResId;

        public SinglePressAction(int i, int i2) {
            this.mIconResId = i;
            this.mMessageResId = i2;
            this.mMessage = null;
            this.mIcon = null;
        }

        public final boolean isEnabled() {
            return true;
        }

        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            Objects.requireNonNull(GlobalActionsDialogLite.this);
            View inflate = layoutInflater.inflate(C1777R.layout.global_actions_grid_item_lite, viewGroup, false);
            inflate.setId(View.generateViewId());
            ImageView imageView = (ImageView) inflate.findViewById(16908294);
            TextView textView = (TextView) inflate.findViewById(16908299);
            textView.setSelected(true);
            imageView.setImageDrawable(getIcon(context));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CharSequence charSequence = this.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setText(this.mMessageResId);
            }
            return inflate;
        }

        public final Drawable getIcon(Context context) {
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                return drawable;
            }
            return context.getDrawable(this.mIconResId);
        }

        public SinglePressAction(Drawable drawable, String str) {
            this.mIconResId = 17302708;
            this.mMessageResId = 0;
            this.mMessage = str;
            this.mIcon = drawable;
        }

        public final CharSequence getMessage() {
            return this.mMessage;
        }

        public final int getMessageResId() {
            return this.mMessageResId;
        }
    }

    public abstract class ToggleAction implements Action {
        public int mDisabledIconResid;
        public int mDisabledStatusMessageResId;
        public int mEnabledIconResId;
        public int mEnabledStatusMessageResId;
        public ToggleState mState = ToggleState.Off;

        public final CharSequence getMessage() {
            return null;
        }

        public abstract void onToggle(boolean z);

        public void changeStateFromPress(boolean z) {
            ToggleState toggleState;
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            this.mState = toggleState;
        }

        public final Drawable getIcon(Context context) {
            boolean z;
            int i;
            ToggleState toggleState = this.mState;
            if (toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = this.mEnabledIconResId;
            } else {
                i = this.mDisabledIconResid;
            }
            return context.getDrawable(i);
        }

        public final int getMessageResId() {
            boolean z;
            ToggleState toggleState = this.mState;
            if (toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return this.mEnabledStatusMessageResId;
            }
            return this.mDisabledStatusMessageResId;
        }

        public final boolean isEnabled() {
            return !this.mState.inTransition();
        }

        public final void onPress() {
            boolean z;
            if (this.mState.inTransition()) {
                Log.w("GlobalActionsDialogLite", "shouldn't be able to toggle when in transition");
                return;
            }
            if (this.mState != ToggleState.On) {
                z = true;
            } else {
                z = false;
            }
            onToggle(z);
            changeStateFromPress(z);
        }

        public ToggleAction(int i, int i2, int i3, int i4) {
            this.mEnabledIconResId = i;
            this.mDisabledIconResid = i2;
            this.mEnabledStatusMessageResId = i3;
            this.mDisabledStatusMessageResId = i4;
        }

        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            int i;
            boolean z = false;
            View inflate = layoutInflater.inflate(C1777R.layout.global_actions_grid_item_v2, viewGroup, false);
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            layoutParams.width = -2;
            inflate.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) inflate.findViewById(16908294);
            TextView textView = (TextView) inflate.findViewById(16908299);
            boolean isEnabled = isEnabled();
            if (textView != null) {
                textView.setText(getMessageResId());
                textView.setEnabled(isEnabled);
                textView.setSelected(true);
            }
            if (imageView != null) {
                ToggleState toggleState = this.mState;
                if (toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) {
                    z = true;
                }
                if (z) {
                    i = this.mEnabledIconResId;
                } else {
                    i = this.mDisabledIconResid;
                }
                imageView.setImageDrawable(context.getDrawable(i));
                imageView.setEnabled(isEnabled);
            }
            inflate.setEnabled(isEnabled);
            return inflate;
        }
    }

    public GlobalActionsDialogLite(Context context, GlobalActions.GlobalActionsManager globalActionsManager, AudioManager audioManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, TelephonyListenerManager telephonyListenerManager, GlobalSettings globalSettings, SecureSettings secureSettings, VibratorHelper vibratorHelper, Resources resources, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UserManager userManager, TrustManager trustManager, IActivityManager iActivityManager, TelecomManager telecomManager, MetricsLogger metricsLogger, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, NotificationShadeWindowController notificationShadeWindowController, IWindowManager iWindowManager, Executor executor, UiEventLogger uiEventLogger, RingerModeTracker ringerModeTracker, SysUiState sysUiState, Handler handler, PackageManager packageManager, Optional<StatusBar> optional, KeyguardUpdateMonitor keyguardUpdateMonitor, DialogLaunchAnimator dialogLaunchAnimator, SystemUIDialogManager systemUIDialogManager) {
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        TelephonyListenerManager telephonyListenerManager2 = telephonyListenerManager;
        GlobalSettings globalSettings2 = globalSettings;
        Resources resources2 = resources;
        ConfigurationController configurationController2 = configurationController;
        C08146 r7 = new TelephonyCallback.ServiceStateListener() {
            public final void onServiceStateChanged(ServiceState serviceState) {
                boolean z;
                ToggleState toggleState;
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mHasTelephony) {
                    if (globalActionsDialogLite.mAirplaneModeOn == null) {
                        Log.d("GlobalActionsDialogLite", "Service changed before actions created");
                        return;
                    }
                    if (serviceState.getState() == 3) {
                        z = true;
                    } else {
                        z = false;
                    }
                    GlobalActionsDialogLite globalActionsDialogLite2 = GlobalActionsDialogLite.this;
                    if (z) {
                        toggleState = ToggleState.On;
                    } else {
                        toggleState = ToggleState.Off;
                    }
                    globalActionsDialogLite2.mAirplaneState = toggleState;
                    AirplaneModeAction airplaneModeAction = globalActionsDialogLite2.mAirplaneModeOn;
                    Objects.requireNonNull(airplaneModeAction);
                    airplaneModeAction.mState = toggleState;
                    GlobalActionsDialogLite.this.mAdapter.notifyDataSetChanged();
                    GlobalActionsDialogLite.this.mOverflowAdapter.notifyDataSetChanged();
                    GlobalActionsDialogLite.this.mPowerAdapter.notifyDataSetChanged();
                }
            }
        };
        this.mPhoneStateListener = r7;
        C08157 r9 = new ContentObserver(this.mMainHandler) {
            public final void onChange(boolean z) {
                GlobalActionsDialogLite.this.onAirplaneModeChanged();
            }
        };
        this.mAirplaneModeObserver = r9;
        this.mHandler = new Handler() {
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i != 0) {
                    if (i == 1) {
                        GlobalActionsDialogLite.this.refreshSilentMode();
                        GlobalActionsDialogLite.this.mAdapter.notifyDataSetChanged();
                    }
                } else if (GlobalActionsDialogLite.this.mDialog != null) {
                    if ("dream".equals(message.obj)) {
                        GlobalActionsDialogLite.this.mDialog.hide();
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    } else {
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    }
                    GlobalActionsDialogLite.this.mDialog = null;
                }
            }
        };
        this.mContext = context;
        this.mWindowManagerFuncs = globalActionsManager;
        this.mAudioManager = audioManager;
        this.mDreamManager = iDreamManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mTelephonyListenerManager = telephonyListenerManager2;
        this.mKeyguardStateController = keyguardStateController;
        this.mBroadcastDispatcher = broadcastDispatcher2;
        this.mGlobalSettings = globalSettings2;
        this.mSecureSettings = secureSettings;
        this.mResources = resources2;
        this.mConfigurationController = configurationController2;
        this.mUserManager = userManager;
        this.mTrustManager = trustManager;
        this.mIActivityManager = iActivityManager;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mSysuiColorExtractor = sysuiColorExtractor;
        this.mStatusBarService = iStatusBarService;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mIWindowManager = iWindowManager;
        this.mBackgroundExecutor = executor;
        this.mRingerModeTracker = ringerModeTracker;
        this.mSysUiState = sysUiState;
        this.mMainHandler = handler;
        this.mSmallestScreenWidthDp = resources.getConfiguration().smallestScreenWidthDp;
        this.mStatusBarOptional = optional;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mDialogManager = systemUIDialogManager;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        broadcastDispatcher2.registerReceiver(this.mBroadcastReceiver, intentFilter);
        this.mHasTelephony = packageManager.hasSystemFeature("android.hardware.telephony");
        Objects.requireNonNull(telephonyListenerManager);
        com.android.systemui.telephony.TelephonyCallback telephonyCallback = telephonyListenerManager2.mTelephonyCallback;
        Objects.requireNonNull(telephonyCallback);
        telephonyCallback.mServiceStateListeners.add(r7);
        telephonyListenerManager.updateListening();
        globalSettings2.registerContentObserver(Settings.Global.getUriFor("airplane_mode_on"), true, r9);
        this.mHasVibrator = vibratorHelper.hasVibrator();
        boolean z = !resources2.getBoolean(17891797);
        this.mShowSilentToggle = z;
        if (z) {
            ringerModeTracker.getRingerMode().observe(this, new GlobalActionsDialogLite$$ExternalSyntheticLambda0(this));
        }
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        configurationController2.addCallback(this);
    }

    @VisibleForTesting
    public void setZeroDialogPressDelayForTesting() {
        this.mDialogPressDelay = 0;
    }

    @VisibleForTesting
    public boolean shouldDisplayLockdown(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        int i = userInfo.id;
        if (!this.mKeyguardStateController.isMethodSecure()) {
            return false;
        }
        int strongAuthForUser = this.mLockPatternUtils.getStrongAuthForUser(i);
        return strongAuthForUser == 0 || strongAuthForUser == 4;
    }

    @VisibleForTesting
    public enum GlobalActionsEvent implements UiEventLogger.UiEventEnum {
        GA_POWER_MENU_OPEN(337),
        GA_POWER_MENU_CLOSE(471),
        GA_BUGREPORT_PRESS(344),
        GA_BUGREPORT_LONG_PRESS(345),
        GA_EMERGENCY_DIALER_PRESS(346),
        GA_SCREENSHOT_PRESS(347),
        GA_SHUTDOWN_PRESS(802),
        GA_SHUTDOWN_LONG_PRESS(803),
        GA_REBOOT_PRESS(349),
        GA_REBOOT_LONG_PRESS(804),
        GA_LOCKDOWN_PRESS(354),
        GA_OPEN_QS(805),
        GA_CLOSE_BACK(809),
        GA_CLOSE_TAP_OUTSIDE(810);
        
        private final int mId;

        /* access modifiers changed from: public */
        GlobalActionsEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public static class SilentModeTriStateAction implements Action, View.OnClickListener {
        public static final int[] ITEM_IDS = {16909302, 16909303, 16909304};
        public final AudioManager mAudioManager;
        public final Handler mHandler;

        public final Drawable getIcon(Context context) {
            return null;
        }

        public final CharSequence getMessage() {
            return null;
        }

        public final int getMessageResId() {
            return 0;
        }

        public final boolean isEnabled() {
            return true;
        }

        public final void onPress() {
        }

        public final boolean showBeforeProvisioning() {
            return false;
        }

        public final void showDuringKeyguard() {
        }

        public SilentModeTriStateAction(AudioManager audioManager, C08168 r2) {
            this.mAudioManager = audioManager;
            this.mHandler = r2;
        }

        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            boolean z;
            View inflate = layoutInflater.inflate(17367170, viewGroup, false);
            int ringerMode = this.mAudioManager.getRingerMode();
            for (int i = 0; i < 3; i++) {
                View findViewById = inflate.findViewById(ITEM_IDS[i]);
                if (ringerMode == i) {
                    z = true;
                } else {
                    z = false;
                }
                findViewById.setSelected(z);
                findViewById.setTag(Integer.valueOf(i));
                findViewById.setOnClickListener(this);
            }
            return inflate;
        }

        public final void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                this.mAudioManager.setRingerMode(((Integer) view.getTag()).intValue());
                this.mHandler.sendEmptyMessageDelayed(0, 300);
            }
        }
    }

    public enum ToggleState {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);
        
        private final boolean mInTransition;

        /* access modifiers changed from: public */
        ToggleState(boolean z) {
            this.mInTransition = z;
        }

        public final boolean inTransition() {
            return this.mInTransition;
        }
    }

    @VisibleForTesting
    public void createActionItems() {
        String[] strArr;
        boolean z;
        boolean z2;
        String[] strArr2;
        boolean z3;
        Drawable drawable;
        String str;
        if (!this.mHasVibrator) {
            this.mSilentModeAction = new SilentModeToggleAction();
        } else {
            this.mSilentModeAction = new SilentModeTriStateAction(this.mAudioManager, this.mHandler);
        }
        this.mAirplaneModeOn = new AirplaneModeAction();
        onAirplaneModeChanged();
        this.mItems.clear();
        this.mOverflowItems.clear();
        this.mPowerItems.clear();
        String[] defaultActions = getDefaultActions();
        ShutDownAction shutDownAction = new ShutDownAction();
        RestartAction restartAction = new RestartAction();
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList = new ArrayList();
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            addIfShouldShowAction(arrayList, new EmergencyAffordanceAction());
            arraySet.add("emergency");
        }
        boolean z4 = false;
        int i = 0;
        boolean z5 = false;
        UserInfo userInfo = null;
        while (i < defaultActions.length) {
            String str2 = defaultActions[i];
            if (arraySet.contains(str2)) {
                strArr = defaultActions;
            } else {
                if (GLOBAL_ACTION_KEY_POWER.equals(str2)) {
                    addIfShouldShowAction(arrayList, shutDownAction);
                } else if ("airplane".equals(str2)) {
                    addIfShouldShowAction(arrayList, this.mAirplaneModeOn);
                } else if ("bugreport".equals(str2)) {
                    if (!z5) {
                        userInfo = getCurrentUser();
                        z5 = true;
                    }
                    if (shouldDisplayBugReport(userInfo)) {
                        addIfShouldShowAction(arrayList, new BugReportAction());
                    }
                } else if (!"silent".equals(str2)) {
                    if (!"users".equals(str2)) {
                        strArr = defaultActions;
                        if ("settings".equals(str2)) {
                            addIfShouldShowAction(arrayList, new SinglePressAction() {
                                public final boolean showBeforeProvisioning() {
                                    return true;
                                }

                                public final void showDuringKeyguard() {
                                }

                                public final void onPress() {
                                    Intent intent = new Intent("android.settings.SETTINGS");
                                    intent.addFlags(335544320);
                                    GlobalActionsDialogLite.this.mContext.startActivity(intent);
                                }
                            });
                        } else if ("lockdown".equals(str2)) {
                            if (!z5) {
                                userInfo = getCurrentUser();
                                z5 = true;
                            }
                            if (shouldDisplayLockdown(userInfo)) {
                                addIfShouldShowAction(arrayList, new LockDownAction());
                            }
                        } else if ("voiceassist".equals(str2)) {
                            addIfShouldShowAction(arrayList, new SinglePressAction() {
                                public final boolean showBeforeProvisioning() {
                                    return true;
                                }

                                public final void showDuringKeyguard() {
                                }

                                public final void onPress() {
                                    Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
                                    intent.addFlags(335544320);
                                    GlobalActionsDialogLite.this.mContext.startActivity(intent);
                                }
                            });
                        } else if ("assist".equals(str2)) {
                            addIfShouldShowAction(arrayList, new SinglePressAction() {
                                public final boolean showBeforeProvisioning() {
                                    return true;
                                }

                                public final void showDuringKeyguard() {
                                }

                                public final void onPress() {
                                    Intent intent = new Intent("android.intent.action.ASSIST");
                                    intent.addFlags(335544320);
                                    GlobalActionsDialogLite.this.mContext.startActivity(intent);
                                }
                            });
                        } else if ("restart".equals(str2)) {
                            addIfShouldShowAction(arrayList, restartAction);
                        } else if ("screenshot".equals(str2)) {
                            addIfShouldShowAction(arrayList, new ScreenshotAction());
                        } else if ("logout".equals(str2)) {
                            if (this.mDevicePolicyManager.isLogoutEnabled()) {
                                if (!z5) {
                                    userInfo = getCurrentUser();
                                    z5 = true;
                                }
                                if (userInfo != null) {
                                    if (!z5) {
                                        userInfo = getCurrentUser();
                                        z = true;
                                    } else {
                                        z = z5;
                                    }
                                    if (userInfo.id != 0) {
                                        addIfShouldShowAction(arrayList, new LogoutAction());
                                    }
                                    z5 = z;
                                }
                            }
                        } else if ("emergency".equals(str2)) {
                            addIfShouldShowAction(arrayList, new EmergencyDialerAction());
                        } else {
                            Log.e("GlobalActionsDialogLite", "Invalid global action key " + str2);
                        }
                    } else if (SystemProperties.getBoolean("fw.power_user_switcher", z4)) {
                        if (!z5) {
                            userInfo = getCurrentUser();
                            z5 = true;
                        }
                        if (this.mUserManager.isUserSwitcherEnabled()) {
                            for (final UserInfo userInfo2 : this.mUserManager.getUsers()) {
                                if (userInfo2.supportsSwitchToByUser()) {
                                    if (userInfo != null ? userInfo.id != userInfo2.id : userInfo2.id != 0) {
                                        z3 = false;
                                    } else {
                                        z3 = true;
                                    }
                                    String str3 = userInfo2.iconPath;
                                    if (str3 != null) {
                                        drawable = Drawable.createFromPath(str3);
                                    } else {
                                        drawable = null;
                                    }
                                    strArr2 = defaultActions;
                                    StringBuilder sb = new StringBuilder();
                                    z2 = z5;
                                    String str4 = userInfo2.name;
                                    if (str4 == null) {
                                        str4 = "Primary";
                                    }
                                    sb.append(str4);
                                    if (z3) {
                                        str = " ✔";
                                    } else {
                                        str = "";
                                    }
                                    sb.append(str);
                                    addIfShouldShowAction(arrayList, new SinglePressAction(drawable, sb.toString()) {
                                        public final boolean showBeforeProvisioning() {
                                            return false;
                                        }

                                        public final void showDuringKeyguard() {
                                        }

                                        public final void onPress() {
                                            try {
                                                GlobalActionsDialogLite.this.mIActivityManager.switchUser(userInfo2.id);
                                            } catch (RemoteException e) {
                                                Log.e("GlobalActionsDialogLite", "Couldn't switch user " + e);
                                            }
                                        }
                                    });
                                } else {
                                    strArr2 = defaultActions;
                                    z2 = z5;
                                }
                                defaultActions = strArr2;
                                z5 = z2;
                            }
                        }
                        strArr = defaultActions;
                        z5 = z5;
                    }
                    arraySet.add(str2);
                } else if (this.mShowSilentToggle) {
                    addIfShouldShowAction(arrayList, this.mSilentModeAction);
                }
                strArr = defaultActions;
                arraySet.add(str2);
            }
            i++;
            defaultActions = strArr;
            z4 = false;
        }
        if (arrayList.contains(shutDownAction) && arrayList.contains(restartAction) && arrayList.size() > getMaxShownPowerItems()) {
            int min = Math.min(arrayList.indexOf(restartAction), arrayList.indexOf(shutDownAction));
            arrayList.remove(shutDownAction);
            arrayList.remove(restartAction);
            this.mPowerItems.add(shutDownAction);
            this.mPowerItems.add(restartAction);
            arrayList.add(min, new PowerOptionsAction());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if (this.mItems.size() < getMaxShownPowerItems()) {
                this.mItems.add(action);
            } else {
                this.mOverflowItems.add(action);
            }
        }
    }

    public final void dismissGlobalActionsMenu() {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    public final UserInfo getCurrentUser() {
        try {
            return this.mIActivityManager.getCurrentUser();
        } catch (RemoteException unused) {
            return null;
        }
    }

    @VisibleForTesting
    public String[] getDefaultActions() {
        return this.mResources.getStringArray(17236067);
    }

    @VisibleForTesting
    public int getMaxShownPowerItems() {
        return this.mResources.getInteger(C1777R.integer.power_menu_lite_max_rows) * this.mResources.getInteger(C1777R.integer.power_menu_lite_max_columns);
    }

    @VisibleForTesting
    public BugReportAction makeBugReportActionForTesting() {
        return new BugReportAction();
    }

    @VisibleForTesting
    public EmergencyDialerAction makeEmergencyDialerActionForTesting() {
        return new EmergencyDialerAction();
    }

    @VisibleForTesting
    public ScreenshotAction makeScreenshotActionForTesting() {
        return new ScreenshotAction();
    }

    public final void onAirplaneModeChanged() {
        ToggleState toggleState;
        if (!this.mHasTelephony && this.mAirplaneModeOn != null) {
            boolean z = false;
            if (this.mGlobalSettings.getInt("airplane_mode_on", 0) == 1) {
                z = true;
            }
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            this.mAirplaneState = toggleState;
            AirplaneModeAction airplaneModeAction = this.mAirplaneModeOn;
            Objects.requireNonNull(airplaneModeAction);
            airplaneModeAction.mState = toggleState;
        }
    }

    public final void onConfigChanged(Configuration configuration) {
        int i;
        ActionsDialogLite actionsDialogLite = this.mDialog;
        if (actionsDialogLite != null && actionsDialogLite.isShowing() && (i = configuration.smallestScreenWidthDp) != this.mSmallestScreenWidthDp) {
            this.mSmallestScreenWidthDp = i;
            ActionsDialogLite actionsDialogLite2 = this.mDialog;
            Objects.requireNonNull(actionsDialogLite2);
            actionsDialogLite2.mOnRefreshCallback.run();
            GlobalActionsPopupMenu globalActionsPopupMenu = actionsDialogLite2.mOverflowPopup;
            if (globalActionsPopupMenu != null) {
                globalActionsPopupMenu.dismiss();
            }
            Dialog dialog = actionsDialogLite2.mPowerOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            MultiListLayout multiListLayout = actionsDialogLite2.mGlobalActionsLayout;
            Objects.requireNonNull(multiListLayout);
            if (multiListLayout.mAdapter != null) {
                multiListLayout.onUpdateList();
                return;
            }
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mDialog == dialogInterface) {
            this.mDialog = null;
        }
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_CLOSE);
        this.mWindowManagerFuncs.onGlobalActionsHidden();
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    public final void onShow(DialogInterface dialogInterface) {
        this.mMetricsLogger.visible(1568);
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_OPEN);
    }

    public final void refreshSilentMode() {
        boolean z;
        ToggleState toggleState;
        if (!this.mHasVibrator) {
            RingerModeLiveData ringerMode = this.mRingerModeTracker.getRingerMode();
            Objects.requireNonNull(ringerMode);
            Integer value = ringerMode.getValue();
            if (value == null || value.intValue() == 2) {
                z = false;
            } else {
                z = true;
            }
            ToggleAction toggleAction = (ToggleAction) this.mSilentModeAction;
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            Objects.requireNonNull(toggleAction);
            toggleAction.mState = toggleState;
        }
    }

    @VisibleForTesting
    public boolean shouldDisplayBugReport(UserInfo userInfo) {
        if (this.mGlobalSettings.getInt("bugreport_in_power_menu", 0) == 0) {
            return false;
        }
        if (userInfo == null || userInfo.isPrimary()) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean shouldShowAction(Action action) {
        if (this.mKeyguardShowing) {
            action.showDuringKeyguard();
        }
        if (this.mDeviceProvisioned || action.showBeforeProvisioning()) {
            return action.shouldShow();
        }
        return false;
    }

    public final void showOrHideDialog(boolean z, boolean z2, View view) {
        View view2 = view;
        this.mKeyguardShowing = z;
        this.mDeviceProvisioned = z2;
        ActionsDialogLite actionsDialogLite = this.mDialog;
        if (actionsDialogLite == null || !actionsDialogLite.isShowing()) {
            IDreamManager iDreamManager = this.mDreamManager;
            if (iDreamManager != null) {
                try {
                    if (iDreamManager.isDreaming()) {
                        this.mDreamManager.awaken();
                    }
                } catch (RemoteException unused) {
                }
            }
            createActionItems();
            this.mAdapter = new MyAdapter();
            this.mOverflowAdapter = new MyOverflowAdapter();
            this.mPowerAdapter = new MyPowerOptionsAdapter();
            ActionsDialogLite actionsDialogLite2 = new ActionsDialogLite(this.mContext, this.mAdapter, this.mOverflowAdapter, this.mSysuiColorExtractor, this.mNotificationShadeWindowController, this.mSysUiState, new TaskView$$ExternalSyntheticLambda4(this, 6), this.mKeyguardShowing, this.mPowerAdapter, this.mUiEventLogger, this.mStatusBarOptional, this.mKeyguardUpdateMonitor, this.mLockPatternUtils, this.mDialogManager);
            actionsDialogLite2.setOnDismissListener(this);
            actionsDialogLite2.setOnShowListener(this);
            this.mDialog = actionsDialogLite2;
            refreshSilentMode();
            AirplaneModeAction airplaneModeAction = this.mAirplaneModeOn;
            ToggleState toggleState = this.mAirplaneState;
            Objects.requireNonNull(airplaneModeAction);
            airplaneModeAction.mState = toggleState;
            this.mAdapter.notifyDataSetChanged();
            this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
            WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
            attributes.setTitle("ActionsDialog");
            attributes.layoutInDisplayCutoutMode = 3;
            this.mDialog.getWindow().setAttributes(attributes);
            this.mDialog.getWindow().addFlags(131072);
            View view3 = view;
            if (view3 != null) {
                DialogLaunchAnimator dialogLaunchAnimator = this.mDialogLaunchAnimator;
                ActionsDialogLite actionsDialogLite3 = this.mDialog;
                Objects.requireNonNull(dialogLaunchAnimator);
                dialogLaunchAnimator.showFromView(actionsDialogLite3, view3, false);
            } else {
                this.mDialog.show();
            }
            this.mWindowManagerFuncs.onGlobalActionsShown();
            return;
        }
        this.mWindowManagerFuncs.onGlobalActionsShown();
        this.mDialog.dismiss();
        this.mDialog = null;
    }

    /* renamed from: -$$Nest$mchangeAirplaneModeSystemSetting  reason: not valid java name */
    public static void m198$$Nest$mchangeAirplaneModeSystemSetting(GlobalActionsDialogLite globalActionsDialogLite, boolean z) {
        ToggleState toggleState;
        Objects.requireNonNull(globalActionsDialogLite);
        globalActionsDialogLite.mGlobalSettings.putInt("airplane_mode_on", z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(536870912);
        intent.putExtra("state", z);
        globalActionsDialogLite.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (!globalActionsDialogLite.mHasTelephony) {
            if (z) {
                toggleState = ToggleState.On;
            } else {
                toggleState = ToggleState.Off;
            }
            globalActionsDialogLite.mAirplaneState = toggleState;
        }
    }

    public final void addIfShouldShowAction(ArrayList arrayList, Action action) {
        if (shouldShowAction(action)) {
            arrayList.add(action);
        }
    }

    public final Lifecycle getLifecycle() {
        return this.mLifecycle;
    }
}
