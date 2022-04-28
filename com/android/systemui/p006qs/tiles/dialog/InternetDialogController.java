package com.android.systemui.p006qs.tiles.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda24;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.toast.SystemUIToast;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda11;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController */
public final class InternetDialogController implements AccessPointController.AccessPointCallback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialogController", 3);
    public static final ColorDrawable EMPTY_DRAWABLE = new ColorDrawable(0);
    public static final long SHORT_DURATION_TIMEOUT = 4000;
    public static final int SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE = C1777R.string.all_network_unavailable;
    public static final int SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE = C1777R.string.non_carrier_network_unavailable;
    public static final int SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS = C1777R.string.wifi_empty_list_wifi_on;
    public static final int SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT = C1777R.string.tap_a_network_to_connect;
    public static final int SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS = C1777R.string.unlock_to_view_networks;
    public static final int SUBTITLE_TEXT_WIFI_IS_OFF = C1777R.string.wifi_is_off;
    public static final float TOAST_PARAMS_HORIZONTAL_WEIGHT = 1.0f;
    public static final float TOAST_PARAMS_VERTICAL_WEIGHT = 1.0f;
    public AccessPointController mAccessPointController;
    public ActivityStarter mActivityStarter;
    public BroadcastDispatcher mBroadcastDispatcher;
    public InternetDialogCallback mCallback;
    public boolean mCanConfigWifi;
    public CarrierConfigTracker mCarrierConfigTracker;
    public MobileMappings.Config mConfig = null;
    public ConnectedWifiInternetMonitor mConnectedWifiInternetMonitor;
    public IntentFilter mConnectionStateFilter;
    public final C10532 mConnectionStateReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                if (InternetDialogController.DEBUG) {
                    Log.d("InternetDialogController", "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                }
                InternetDialogController.this.mConfig = MobileMappings.Config.readConfig(context);
                InternetDialogController.m218$$Nest$mupdateListener(InternetDialogController.this);
            } else if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                InternetDialogController.m218$$Nest$mupdateListener(InternetDialogController.this);
            }
        }
    };
    public ConnectivityManager mConnectivityManager;
    public DataConnectivityListener mConnectivityManagerNetworkCallback;
    public Context mContext;
    public int mDefaultDataSubId = -1;
    public DialogLaunchAnimator mDialogLaunchAnimator;
    public Executor mExecutor;
    public GlobalSettings mGlobalSettings;
    public Handler mHandler;
    public boolean mHasEthernet = false;
    public boolean mHasWifiEntries;
    public InternetTelephonyCallback mInternetTelephonyCallback;
    public KeyguardStateController mKeyguardStateController;
    public final C10521 mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() {
        public final void onRefreshCarrierInfo() {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new BubbleStackView$$ExternalSyntheticLambda15(internetDialog, 4));
        }

        public final void onSimStateChanged(int i, int i2, int i3) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new SuggestController$$ExternalSyntheticLambda1(internetDialog, 2));
        }
    };
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LocationController mLocationController;
    public SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangedListener;
    public SignalDrawable mSignalDrawable;
    public SubscriptionManager mSubscriptionManager;
    public TelephonyDisplayInfo mTelephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
    public TelephonyManager mTelephonyManager;
    public ToastFactory mToastFactory;
    public WifiUtils.InternetIconInjector mWifiIconInjector;
    public WifiManager mWifiManager;
    public WindowManager mWindowManager;
    public Handler mWorkerHandler;

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$ConnectedWifiInternetMonitor */
    public class ConnectedWifiInternetMonitor implements WifiEntry.WifiEntryCallback {
        public WifiEntry mWifiEntry;

        public ConnectedWifiInternetMonitor() {
        }

        public final void onUpdated() {
            WifiEntry wifiEntry = this.mWifiEntry;
            if (wifiEntry != null) {
                if (wifiEntry.getConnectedState() != 2) {
                    WifiEntry wifiEntry2 = this.mWifiEntry;
                    if (wifiEntry2 != null) {
                        synchronized (wifiEntry2) {
                            wifiEntry2.mListener = null;
                        }
                        this.mWifiEntry = null;
                    }
                } else if (wifiEntry.mIsDefaultNetwork && wifiEntry.mIsValidated) {
                    WifiEntry wifiEntry3 = this.mWifiEntry;
                    if (wifiEntry3 != null) {
                        synchronized (wifiEntry3) {
                            wifiEntry3.mListener = null;
                        }
                        this.mWifiEntry = null;
                    }
                    InternetDialogController internetDialogController = InternetDialogController.this;
                    ColorDrawable colorDrawable = InternetDialogController.EMPTY_DRAWABLE;
                    Objects.requireNonNull(internetDialogController);
                    if (internetDialogController.mCanConfigWifi) {
                        internetDialogController.mAccessPointController.scanForAccessPoints();
                    }
                }
            }
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$DataConnectivityListener */
    public class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
        public DataConnectivityListener() {
        }

        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            InternetDialogController.this.mHasEthernet = networkCapabilities.hasTransport(3);
            InternetDialogController internetDialogController = InternetDialogController.this;
            if (internetDialogController.mCanConfigWifi && (internetDialogController.mHasEthernet || networkCapabilities.hasTransport(1))) {
                InternetDialogController internetDialogController2 = InternetDialogController.this;
                Objects.requireNonNull(internetDialogController2);
                if (internetDialogController2.mCanConfigWifi) {
                    internetDialogController2.mAccessPointController.scanForAccessPoints();
                }
            }
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda11(internetDialog, 1));
        }

        public final void onLost(Network network) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mHasEthernet = false;
            InternetDialog internetDialog = (InternetDialog) internetDialogController.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(internetDialog, 2));
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$InternetDialogCallback */
    public interface InternetDialogCallback {
    }

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$InternetOnSubscriptionChangedListener */
    public class InternetOnSubscriptionChangedListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public InternetOnSubscriptionChangedListener() {
        }

        public final void onSubscriptionsChanged() {
            InternetDialogController.m218$$Nest$mupdateListener(InternetDialogController.this);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$InternetTelephonyCallback */
    public class InternetTelephonyCallback extends TelephonyCallback implements TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.UserMobileDataStateListener {
        public InternetTelephonyCallback() {
        }

        public final void onDataConnectionStateChanged(int i, int i2) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new StatusBar$$ExternalSyntheticLambda18(internetDialog, 5));
        }

        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mTelephonyDisplayInfo = telephonyDisplayInfo;
            InternetDialog internetDialog = (InternetDialog) internetDialogController.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new KeyguardStatusView$$ExternalSyntheticLambda0(internetDialog, 2));
        }

        public final void onServiceStateChanged(ServiceState serviceState) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new LockIconViewController$$ExternalSyntheticLambda1(internetDialog, 2));
        }

        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new LockIconViewController$$ExternalSyntheticLambda0(internetDialog, 2));
        }

        public final void onUserMobileDataStateChanged(boolean z) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            Objects.requireNonNull(internetDialog);
            internetDialog.mHandler.post(new BubbleStackView$$ExternalSyntheticLambda18(internetDialog, 2));
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$WifiEntryConnectCallback */
    public static class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final ActivityStarter mActivityStarter;
        public final InternetDialogController mInternetDialogController;
        public final WifiEntry mWifiEntry;

        public final void onConnectResult(int i) {
            boolean z = InternetDialogController.DEBUG;
            if (z) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("onConnectResult ", i, "InternetDialogController");
            }
            if (i == 1) {
                Intent putExtra = new Intent("com.android.settings.WIFI_DIALOG").putExtra("key_chosen_wifientry_key", this.mWifiEntry.getKey());
                putExtra.addFlags(268435456);
                this.mActivityStarter.startActivity(putExtra, false);
            } else if (i == 2) {
                this.mInternetDialogController.makeOverlayToast(C1777R.string.wifi_failed_connect_message);
            } else if (z) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("connect failure reason=", i, "InternetDialogController");
            }
        }

        public WifiEntryConnectCallback(ActivityStarter activityStarter, WifiEntry wifiEntry, InternetDialogController internetDialogController) {
            this.mActivityStarter = activityStarter;
            this.mWifiEntry = wifiEntry;
            this.mInternetDialogController = internetDialogController;
        }
    }

    public InternetDialogController(Context context, ActivityStarter activityStarter, AccessPointController accessPointController, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Executor executor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, WindowManager windowManager, ToastFactory toastFactory, Handler handler2, CarrierConfigTracker carrierConfigTracker, LocationController locationController, DialogLaunchAnimator dialogLaunchAnimator) {
        if (DEBUG) {
            Log.d("InternetDialogController", "Init InternetDialogController");
        }
        this.mHandler = handler;
        this.mWorkerHandler = handler2;
        this.mExecutor = executor;
        this.mContext = context;
        this.mGlobalSettings = globalSettings;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mConnectivityManager = connectivityManager;
        this.mSubscriptionManager = subscriptionManager;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        IntentFilter intentFilter = new IntentFilter();
        this.mConnectionStateFilter = intentFilter;
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mConnectionStateFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        this.mActivityStarter = activityStarter;
        this.mAccessPointController = accessPointController;
        this.mWifiIconInjector = new WifiUtils.InternetIconInjector(this.mContext);
        this.mConnectivityManagerNetworkCallback = new DataConnectivityListener();
        this.mWindowManager = windowManager;
        this.mToastFactory = toastFactory;
        this.mSignalDrawable = new SignalDrawable(this.mContext);
        this.mLocationController = locationController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mConnectedWifiInternetMonitor = new ConnectedWifiInternetMonitor();
    }

    public final boolean activeNetworkIsCellular() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "ConnectivityManager is null, can not check active network.");
            }
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    public Intent getSettingsIntent() {
        return new Intent("android.settings.NETWORK_PROVIDER_SETTINGS").addFlags(268435456);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        r0 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
        r3 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        r8 = r7.mContext;
        r2 = !isMobileDataEnabled();
        r5 = r7.mSignalDrawable;
        r6 = com.android.settingslib.graph.SignalDrawable.$r8$clinit;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        if (r2 == false) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0036, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        r5.setLevel(r0 | ((r2 << 16) | (r3 << 8)));
        r2 = new android.graphics.drawable.Drawable[]{EMPTY_DRAWABLE, r7.mSignalDrawable};
        r7 = r8.getResources().getDimensionPixelSize(com.android.p012wm.shell.C1777R.dimen.signal_strength_icon_size);
        r0 = new android.graphics.drawable.LayerDrawable(r2);
        r0.setLayerGravity(0, 51);
        r0.setLayerGravity(1, 85);
        r0.setLayerSize(1, r7, r7);
        r0.setTintList(com.android.settingslib.Utils.getColorAttr(r8, 16843282));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0073, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (kotlinx.coroutines.YieldKt.shouldInflateSignalStrength(r7.mContext, r7.mDefaultDataSubId) == false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        if (r8 != false) goto L_0x0020;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        if (r8 == false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.drawable.LayerDrawable getSignalStrengthDrawableWithLevel(boolean r8) {
        /*
            r7 = this;
            android.telephony.TelephonyManager r0 = r7.mTelephonyManager
            android.telephony.SignalStrength r0 = r0.getSignalStrength()
            r1 = 0
            if (r0 != 0) goto L_0x000b
            r0 = r1
            goto L_0x000f
        L_0x000b:
            int r0 = r0.getLevel()
        L_0x000f:
            android.telephony.SubscriptionManager r2 = r7.mSubscriptionManager
            r3 = 5
            if (r2 == 0) goto L_0x001e
            int r2 = r7.mDefaultDataSubId
            android.content.Context r4 = r7.mContext
            boolean r2 = kotlinx.coroutines.YieldKt.shouldInflateSignalStrength(r4, r2)
            if (r2 != 0) goto L_0x0020
        L_0x001e:
            if (r8 == 0) goto L_0x0027
        L_0x0020:
            if (r8 == 0) goto L_0x0024
            r0 = r3
            goto L_0x0026
        L_0x0024:
            int r0 = r0 + 1
        L_0x0026:
            r3 = 6
        L_0x0027:
            android.content.Context r8 = r7.mContext
            boolean r2 = r7.isMobileDataEnabled()
            r4 = 1
            r2 = r2 ^ r4
            com.android.settingslib.graph.SignalDrawable r5 = r7.mSignalDrawable
            int r6 = com.android.settingslib.graph.SignalDrawable.$r8$clinit
            r6 = 2
            if (r2 == 0) goto L_0x0038
            r2 = r6
            goto L_0x0039
        L_0x0038:
            r2 = r1
        L_0x0039:
            int r2 = r2 << 16
            int r3 = r3 << 8
            r2 = r2 | r3
            r0 = r0 | r2
            r5.setLevel(r0)
            android.graphics.drawable.ColorDrawable r0 = EMPTY_DRAWABLE
            android.graphics.drawable.Drawable[] r2 = new android.graphics.drawable.Drawable[r6]
            r2[r1] = r0
            com.android.settingslib.graph.SignalDrawable r7 = r7.mSignalDrawable
            r2[r4] = r7
            android.content.res.Resources r7 = r8.getResources()
            r0 = 2131167018(0x7f07072a, float:1.7948298E38)
            int r7 = r7.getDimensionPixelSize(r0)
            android.graphics.drawable.LayerDrawable r0 = new android.graphics.drawable.LayerDrawable
            r0.<init>(r2)
            r2 = 51
            r0.setLayerGravity(r1, r2)
            r1 = 85
            r0.setLayerGravity(r4, r1)
            r0.setLayerSize(r4, r7, r7)
            r7 = 16843282(0x1010212, float:2.3695043E-38)
            android.content.res.ColorStateList r7 = com.android.settingslib.Utils.getColorAttr(r8, r7)
            r0.setTintList(r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.dialog.InternetDialogController.getSignalStrengthDrawableWithLevel(boolean):android.graphics.drawable.LayerDrawable");
    }

    public final boolean hasActiveSubId() {
        if (this.mSubscriptionManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "SubscriptionManager is null, can not check carrier.");
            }
            return false;
        } else if (isAirplaneModeEnabled() || this.mTelephonyManager == null || this.mSubscriptionManager.getActiveSubscriptionIdList().length <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isAirplaneModeEnabled() {
        if (this.mGlobalSettings.getInt("airplane_mode_on", 0) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        if (mergedCarrierEntry == null || !mergedCarrierEntry.mIsDefaultNetwork) {
            return false;
        }
        return true;
    }

    public final boolean isDataStateInService() {
        NetworkRegistrationInfo networkRegistrationInfo;
        ServiceState serviceState = this.mTelephonyManager.getServiceState();
        if (serviceState == null) {
            networkRegistrationInfo = null;
        } else {
            networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
        }
        if (networkRegistrationInfo == null) {
            return false;
        }
        return networkRegistrationInfo.isRegistered();
    }

    public final boolean isMobileDataEnabled() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null || !telephonyManager.isDataEnabled()) {
            return false;
        }
        return true;
    }

    public final boolean isVoiceStateInService() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not detect voice state.");
            }
            return false;
        }
        ServiceState serviceState = telephonyManager.getServiceState();
        if (serviceState == null || serviceState.getState() != 0) {
            return false;
        }
        return true;
    }

    public final void makeOverlayToast(int i) {
        Resources resources = this.mContext.getResources();
        final SystemUIToast createToast = this.mToastFactory.createToast(this.mContext, resources.getString(i), this.mContext.getPackageName(), UserHandle.myUserId(), resources.getConfiguration().orientation);
        final View view = createToast.mToastView;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.type = 2017;
        layoutParams.flags = 152;
        layoutParams.y = createToast.getYOffset().intValue();
        int absoluteGravity = Gravity.getAbsoluteGravity(createToast.getGravity().intValue(), resources.getConfiguration().getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        this.mWindowManager.addView(view, layoutParams);
        Animator animator = createToast.mInAnimator;
        if (animator != null) {
            animator.start();
        }
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                SystemUIToast systemUIToast = createToast;
                Objects.requireNonNull(systemUIToast);
                Animator animator = systemUIToast.mOutAnimator;
                if (animator != null) {
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            C10543 r0 = C10543.this;
                            InternetDialogController.this.mWindowManager.removeViewImmediate(view);
                        }
                    });
                }
            }
        }, 4000);
    }

    public final void onAccessPointsChanged(List<WifiEntry> list) {
        int i;
        boolean z;
        ArrayList arrayList;
        WifiEntry wifiEntry;
        boolean z2;
        if (this.mCanConfigWifi) {
            if (list == null) {
                i = 0;
            } else {
                i = list.size();
            }
            if (i > 3) {
                z = true;
            } else {
                z = false;
            }
            WifiEntry wifiEntry2 = null;
            if (i > 0) {
                ArrayList arrayList2 = new ArrayList();
                if (z) {
                    i = 3;
                }
                ConnectedWifiInternetMonitor connectedWifiInternetMonitor = this.mConnectedWifiInternetMonitor;
                Objects.requireNonNull(connectedWifiInternetMonitor);
                WifiEntry wifiEntry3 = connectedWifiInternetMonitor.mWifiEntry;
                if (wifiEntry3 != null) {
                    synchronized (wifiEntry3) {
                        wifiEntry3.mListener = null;
                    }
                    connectedWifiInternetMonitor.mWifiEntry = null;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    WifiEntry wifiEntry4 = list.get(i2);
                    ConnectedWifiInternetMonitor connectedWifiInternetMonitor2 = this.mConnectedWifiInternetMonitor;
                    if (wifiEntry4 == null) {
                        Objects.requireNonNull(connectedWifiInternetMonitor2);
                    } else if (connectedWifiInternetMonitor2.mWifiEntry == null && wifiEntry4.getConnectedState() == 2 && (!wifiEntry4.mIsDefaultNetwork || !wifiEntry4.mIsValidated)) {
                        connectedWifiInternetMonitor2.mWifiEntry = wifiEntry4;
                        synchronized (wifiEntry4) {
                            wifiEntry4.mListener = connectedWifiInternetMonitor2;
                        }
                    }
                    if (wifiEntry2 == null) {
                        Objects.requireNonNull(wifiEntry4);
                        if (wifiEntry4.mIsDefaultNetwork && wifiEntry4.mIsValidated) {
                            wifiEntry2 = wifiEntry4;
                        }
                    }
                    arrayList2.add(wifiEntry4);
                }
                this.mHasWifiEntries = true;
                wifiEntry = wifiEntry2;
                arrayList = arrayList2;
            } else {
                this.mHasWifiEntries = false;
                wifiEntry = null;
                arrayList = null;
            }
            InternetDialog internetDialog = (InternetDialog) this.mCallback;
            Objects.requireNonNull(internetDialog);
            if (internetDialog.mMobileNetworkLayout.getVisibility() != 0 || !internetDialog.mInternetDialogController.isAirplaneModeEnabled()) {
                z2 = false;
            } else {
                z2 = true;
            }
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda11(internetDialog, wifiEntry, arrayList, z, z2));
        }
    }

    public final void setMobileDataEnabled(Context context, int i, boolean z) {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
            }
        } else if (this.mSubscriptionManager != null) {
            telephonyManager.setDataEnabled(z);
            this.mWorkerHandler.post(new StatusBar$$ExternalSyntheticLambda24(this, i, z));
        } else if (DEBUG) {
            Log.d("InternetDialogController", "SubscriptionManager is null, can not set mobile data.");
        }
    }

    public final void startActivity(Intent intent, View view) {
        DialogLaunchAnimator dialogLaunchAnimator = this.mDialogLaunchAnimator;
        Objects.requireNonNull(dialogLaunchAnimator);
        DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view);
        if (createActivityLaunchController$default == null) {
            InternetDialog internetDialog = (InternetDialog) this.mCallback;
            if (InternetDialog.DEBUG) {
                Objects.requireNonNull(internetDialog);
                Log.d("InternetDialog", "dismissDialog");
            }
            Objects.requireNonNull(internetDialog.mInternetDialogFactory);
            if (InternetDialogFactoryKt.DEBUG) {
                Log.d("InternetDialogFactory", "destroyDialog");
            }
            InternetDialogFactory.internetDialog = null;
            internetDialog.dismiss();
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, createActivityLaunchController$default);
    }

    /* renamed from: -$$Nest$mupdateListener  reason: not valid java name */
    public static void m218$$Nest$mupdateListener(InternetDialogController internetDialogController) {
        Objects.requireNonNull(internetDialogController);
        int defaultDataSubscriptionId = internetDialogController.getDefaultDataSubscriptionId();
        if (internetDialogController.mDefaultDataSubId != internetDialogController.getDefaultDataSubscriptionId()) {
            internetDialogController.mDefaultDataSubId = defaultDataSubscriptionId;
            if (DEBUG) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("DDS: defaultDataSubId:"), internetDialogController.mDefaultDataSubId, "InternetDialogController");
            }
            if (SubscriptionManager.isUsableSubscriptionId(internetDialogController.mDefaultDataSubId)) {
                internetDialogController.mTelephonyManager.unregisterTelephonyCallback(internetDialogController.mInternetTelephonyCallback);
                TelephonyManager createForSubscriptionId = internetDialogController.mTelephonyManager.createForSubscriptionId(internetDialogController.mDefaultDataSubId);
                internetDialogController.mTelephonyManager = createForSubscriptionId;
                Handler handler = internetDialogController.mHandler;
                Objects.requireNonNull(handler);
                createForSubscriptionId.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), internetDialogController.mInternetTelephonyCallback);
                InternetDialogCallback internetDialogCallback = internetDialogController.mCallback;
                int i = internetDialogController.mDefaultDataSubId;
                InternetDialog internetDialog = (InternetDialog) internetDialogCallback;
                Objects.requireNonNull(internetDialog);
                internetDialog.mDefaultDataSubId = i;
                internetDialog.mTelephonyManager = internetDialog.mTelephonyManager.createForSubscriptionId(i);
                internetDialog.mHandler.post(new LockIconViewController$$ExternalSyntheticLambda2(internetDialog, 3));
            }
        } else if (DEBUG) {
            Log.d("InternetDialogController", "DDS: no change");
        }
    }

    public int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    public final void launchWifiNetworkDetailsSetting(String str, View view) {
        Intent intent;
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                Log.d("InternetDialogController", "connected entry's key is empty");
            }
            intent = null;
        } else {
            Intent intent2 = new Intent("android.settings.WIFI_DETAILS_SETTINGS");
            Bundle bundle = new Bundle();
            bundle.putString("key_chosen_wifientry_key", str);
            intent2.putExtra(":settings:show_fragment_args", bundle);
            intent = intent2;
        }
        if (intent != null) {
            startActivity(intent, view);
        }
    }
}
