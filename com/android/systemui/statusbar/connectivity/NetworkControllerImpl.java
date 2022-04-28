package com.android.systemui.statusbar.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkScoreManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda1;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.DataUsageController;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.EncryptionHelper;
import com.android.systemui.telephony.TelephonyCallback;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import com.android.wifitrackerlib.WifiPickerTracker;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlinx.coroutines.YieldKt;

public final class NetworkControllerImpl extends BroadcastReceiver implements NetworkController, DemoMode, Dumpable {
    public static final boolean CHATTY = Log.isLoggable("NetworkControllerChat", 3);
    public static final boolean DEBUG = Log.isLoggable("NetworkController", 3);
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public final AccessPointControllerImpl mAccessPoints;
    public int mActiveMobileDataSubscription = -1;
    public boolean mAirplaneMode = false;
    public final Executor mBgExecutor;
    public final Looper mBgLooper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CallbackHandler mCallbackHandler;
    public final CarrierConfigTracker mCarrierConfigTracker;
    public final KeyguardDisplayManager$$ExternalSyntheticLambda1 mClearForceValidated = new KeyguardDisplayManager$$ExternalSyntheticLambda1(this, 4);
    public MobileMappings.Config mConfig;
    public final BitSet mConnectedTransports = new BitSet();
    public final Context mContext;
    public List<SubscriptionInfo> mCurrentSubscriptions = new ArrayList();
    public final DataSaverControllerImpl mDataSaverController;
    public final DataUsageController mDataUsageController;
    public MobileSignalController mDefaultSignalController;
    public boolean mDemoInetCondition;
    public final DemoModeController mDemoModeController;
    public WifiState mDemoWifiState;
    public int mEmergencySource;
    @VisibleForTesting
    public final EthernetSignalController mEthernetSignalController;
    public final FeatureFlags mFeatureFlags;
    public boolean mForceCellularValidated;
    public final boolean mHasMobileDataFeature;
    public boolean mHasNoSubs;
    public final String[] mHistory = new String[16];
    public int mHistoryIndex;
    public boolean mInetCondition;
    public InternetDialogFactory mInternetDialogFactory;
    public boolean mIsEmergency;
    public NetworkCapabilities mLastDefaultNetworkCapabilities;
    @VisibleForTesting
    public ServiceState mLastServiceState;
    @VisibleForTesting
    public boolean mListening;
    public Locale mLocale = null;
    public final Object mLock = new Object();
    public Handler mMainHandler;
    @VisibleForTesting
    public final SparseArray<MobileSignalController> mMobileSignalControllers = new SparseArray<>();
    public boolean mNoDefaultNetwork = false;
    public boolean mNoNetworksAvailable = true;
    public final TelephonyManager mPhone;
    public NetworkControllerImpl$$ExternalSyntheticLambda0 mPhoneStateListener;
    public final boolean mProviderModelBehavior;
    public final Handler mReceiverHandler;
    public final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 mRegisterListeners = new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 4);
    public boolean mSimDetected;
    public final MobileStatusTracker.SubscriptionDefaults mSubDefaults;
    public SubListener mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public boolean mUserSetup;
    public final C12023 mUserTracker;
    public final BitSet mValidatedTransports = new BitSet();
    public final WifiManager mWifiManager;
    @VisibleForTesting
    public final WifiSignalController mWifiSignalController;

    public class SubListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public SubListener(Looper looper) {
            super(looper);
        }

        public final void onSubscriptionsChanged() {
            NetworkControllerImpl.this.updateMobileControllers();
        }
    }

    public final void pushConnectivityToSignals() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).updateConnectivity(this.mConnectedTransports, this.mValidatedTransports);
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        BitSet bitSet = this.mValidatedTransports;
        Objects.requireNonNull(wifiSignalController);
        wifiSignalController.mCurrentState.inetCondition = bitSet.get(wifiSignalController.mTransportType) ? 1 : 0;
        wifiSignalController.notifyListenersIfNecessary();
        this.mEthernetSignalController.updateConnectivity(this.mConnectedTransports, this.mValidatedTransports);
    }

    @VisibleForTesting
    public void registerListeners() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).registerListener();
        }
        if (this.mSubscriptionListener == null) {
            this.mSubscriptionListener = new SubListener(this.mBgLooper);
        }
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        NetworkControllerImpl$$ExternalSyntheticLambda0 networkControllerImpl$$ExternalSyntheticLambda0 = this.mPhoneStateListener;
        Objects.requireNonNull(telephonyListenerManager);
        TelephonyCallback telephonyCallback = telephonyListenerManager.mTelephonyCallback;
        Objects.requireNonNull(telephonyCallback);
        telephonyCallback.mActiveDataSubscriptionIdListeners.add(networkControllerImpl$$ExternalSyntheticLambda0);
        telephonyListenerManager.updateListening();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.settings.panel.action.INTERNET_CONNECTIVITY");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter, this.mReceiverHandler);
        this.mListening = true;
        this.mReceiverHandler.post(new WifiEntry$$ExternalSyntheticLambda2(this, 5));
        Handler handler = this.mReceiverHandler;
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        Objects.requireNonNull(wifiSignalController);
        handler.post(new AccessPoint$$ExternalSyntheticLambda1(wifiSignalController, 4));
        this.mReceiverHandler.post(new AccessPoint$$ExternalSyntheticLambda0(this, 7));
        updateMobileControllers();
        this.mReceiverHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda10(this, 5));
    }

    @VisibleForTesting
    public NetworkControllerImpl(Context context, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WifiManager wifiManager, NetworkScoreManager networkScoreManager, SubscriptionManager subscriptionManager, MobileMappings.Config config, Looper looper, Executor executor, CallbackHandler callbackHandler, AccessPointControllerImpl accessPointControllerImpl, DataUsageController dataUsageController, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, DeviceProvisionedController deviceProvisionedController, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, CarrierConfigTracker carrierConfigTracker, FeatureFlags featureFlags, DumpManager dumpManager) {
        Context context2 = context;
        Looper looper2 = looper;
        CallbackHandler callbackHandler2 = callbackHandler;
        DataUsageController dataUsageController2 = dataUsageController;
        final DeviceProvisionedController deviceProvisionedController2 = deviceProvisionedController;
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        this.mContext = context2;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mConfig = config;
        Handler handler = new Handler(looper2);
        this.mReceiverHandler = handler;
        this.mBgLooper = looper2;
        this.mBgExecutor = executor;
        this.mCallbackHandler = callbackHandler2;
        this.mDataSaverController = new DataSaverControllerImpl(context2);
        this.mBroadcastDispatcher = broadcastDispatcher2;
        this.mSubscriptionManager = subscriptionManager;
        this.mSubDefaults = subscriptionDefaults;
        boolean isDataCapable = telephonyManager.isDataCapable();
        this.mHasMobileDataFeature = isDataCapable;
        this.mDemoModeController = demoModeController;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mFeatureFlags = featureFlags;
        this.mPhone = telephonyManager;
        this.mWifiManager = wifiManager;
        this.mLocale = context.getResources().getConfiguration().locale;
        this.mAccessPoints = accessPointControllerImpl;
        this.mDataUsageController = dataUsageController2;
        Objects.requireNonNull(dataUsageController);
        dataUsageController2.mCallback = new DataUsageController.Callback() {
        };
        WifiSignalController wifiSignalController = r0;
        Handler handler2 = handler;
        WifiSignalController wifiSignalController2 = new WifiSignalController(context, isDataCapable, callbackHandler, this, wifiManager, connectivityManager, networkScoreManager);
        this.mWifiSignalController = wifiSignalController;
        this.mEthernetSignalController = new EthernetSignalController(context2, callbackHandler2, this);
        updateAirplaneMode(true);
        C12023 r0 = new CurrentUserTracker(broadcastDispatcher2) {
            public final void onUserSwitched(int i) {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                boolean z = NetworkControllerImpl.DEBUG;
                Objects.requireNonNull(networkControllerImpl);
                AccessPointControllerImpl accessPointControllerImpl = networkControllerImpl.mAccessPoints;
                Objects.requireNonNull(accessPointControllerImpl);
                accessPointControllerImpl.mCurrentUser = i;
                networkControllerImpl.updateConnectivity();
            }
        };
        this.mUserTracker = r0;
        r0.startTracking();
        deviceProvisionedController2.addCallback(new DeviceProvisionedController.DeviceProvisionedListener() {
            public final void onUserSetupChanged() {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                boolean isCurrentUserSetup = deviceProvisionedController2.isCurrentUserSetup();
                Objects.requireNonNull(networkControllerImpl);
                networkControllerImpl.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(networkControllerImpl, isCurrentUserSetup));
            }
        });
        handler2.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, deviceProvisionedController.isCurrentUserSetup()));
        C12045 r02 = new WifiManager.ScanResultsCallback() {
            public final void onScanResultsAvailable() {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mNoNetworksAvailable = true;
                Iterator<ScanResult> it = networkControllerImpl.mWifiManager.getScanResults().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String str = it.next().SSID;
                    WifiSignalController wifiSignalController = NetworkControllerImpl.this.mWifiSignalController;
                    Objects.requireNonNull(wifiSignalController);
                    if (!str.equals(((WifiState) wifiSignalController.mCurrentState).ssid)) {
                        NetworkControllerImpl.this.mNoNetworksAvailable = false;
                        break;
                    }
                }
                NetworkControllerImpl networkControllerImpl2 = NetworkControllerImpl.this;
                boolean z = networkControllerImpl2.mNoDefaultNetwork;
                if (z) {
                    networkControllerImpl2.mCallbackHandler.setConnectivityStatus(z, true ^ networkControllerImpl2.mInetCondition, networkControllerImpl2.mNoNetworksAvailable);
                }
            }
        };
        WifiManager wifiManager2 = wifiManager;
        if (wifiManager2 != null) {
            wifiManager2.registerScanResultsCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler2), r02);
        }
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            public Network mLastNetwork;
            public NetworkCapabilities mLastNetworkCapabilities;

            public final void onLost(Network network) {
                this.mLastNetwork = null;
                this.mLastNetworkCapabilities = null;
                NetworkControllerImpl.this.mLastDefaultNetworkCapabilities = null;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                Objects.requireNonNull(networkControllerImpl);
                String[] strArr = networkControllerImpl.mHistory;
                int i = networkControllerImpl.mHistoryIndex;
                strArr[i] = NetworkControllerImpl.SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + "onLost: " + "network=" + network;
                networkControllerImpl.mHistoryIndex = (i + 1) % 16;
                NetworkControllerImpl.this.updateConnectivity();
            }

            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                boolean z;
                int[] iArr;
                NetworkCapabilities networkCapabilities2 = this.mLastNetworkCapabilities;
                if (networkCapabilities2 == null || !networkCapabilities2.hasCapability(16)) {
                    z = false;
                } else {
                    z = true;
                }
                boolean hasCapability = networkCapabilities.hasCapability(16);
                if (network.equals(this.mLastNetwork) && hasCapability == z) {
                    int[] r0 = NetworkControllerImpl.m229$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities);
                    Arrays.sort(r0);
                    NetworkCapabilities networkCapabilities3 = this.mLastNetworkCapabilities;
                    if (networkCapabilities3 != null) {
                        iArr = NetworkControllerImpl.m229$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities3);
                    } else {
                        iArr = null;
                    }
                    if (iArr != null) {
                        Arrays.sort(iArr);
                    }
                    if (Arrays.equals(r0, iArr)) {
                        return;
                    }
                }
                this.mLastNetwork = network;
                this.mLastNetworkCapabilities = networkCapabilities;
                NetworkControllerImpl.this.mLastDefaultNetworkCapabilities = networkCapabilities;
                String str = NetworkControllerImpl.SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + "onCapabilitiesChanged: " + "network=" + network + "," + "networkCapabilities=" + networkCapabilities;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                Objects.requireNonNull(networkControllerImpl);
                String[] strArr = networkControllerImpl.mHistory;
                int i = networkControllerImpl.mHistoryIndex;
                strArr[i] = str;
                networkControllerImpl.mHistoryIndex = (i + 1) % 16;
                NetworkControllerImpl.this.updateConnectivity();
            }
        }, handler2);
        this.mPhoneStateListener = new NetworkControllerImpl$$ExternalSyntheticLambda0(this);
        demoModeController.addCallback((DemoMode) this);
        this.mProviderModelBehavior = featureFlags.isEnabled(Flags.COMBINED_STATUS_BAR_SIGNAL_ICONS);
        dumpManager.registerDumpable("NetworkController", this);
    }

    public final void addCallback(Object obj) {
        SignalCallback signalCallback = (SignalCallback) obj;
        signalCallback.setSubs(this.mCurrentSubscriptions);
        boolean z = this.mAirplaneMode;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        signalCallback.setIsAirplaneMode(new IconState(z, C1777R.C1778drawable.stat_sys_airplane_mode, this.mContext.getString(C1777R.string.accessibility_airplane_mode)));
        signalCallback.setNoSims(this.mHasNoSubs, this.mSimDetected);
        signalCallback.setConnectivityStatus(this.mNoDefaultNetwork, !this.mInetCondition, this.mNoNetworksAvailable);
        this.mWifiSignalController.notifyListeners(signalCallback);
        this.mEthernetSignalController.notifyListeners(signalCallback);
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            valueAt.notifyListeners(signalCallback);
            if (this.mProviderModelBehavior) {
                valueAt.refreshCallIndicator(signalCallback);
            }
        }
        CallbackHandler callbackHandler = this.mCallbackHandler;
        Objects.requireNonNull(callbackHandler);
        callbackHandler.obtainMessage(7, 1, 0, signalCallback).sendToTarget();
    }

    public final SubscriptionInfo addSignalController(int i, int i2) {
        SubscriptionInfo subscriptionInfo = new SubscriptionInfo(i, "", i2, "", "", 0, 0, "", 0, (Bitmap) null, (String) null, (String) null, "", false, (UiccAccessRule[]) null, (String) null);
        MobileSignalController mobileSignalController = new MobileSignalController(this.mContext, this.mConfig, this.mHasMobileDataFeature, this.mPhone.createForSubscriptionId(subscriptionInfo.getSubscriptionId()), this.mCallbackHandler, this, subscriptionInfo, this.mSubDefaults, this.mReceiverHandler.getLooper(), this.mCarrierConfigTracker, this.mFeatureFlags);
        this.mMobileSignalControllers.put(i, mobileSignalController);
        ((MobileState) mobileSignalController.mCurrentState).userSetup = true;
        return subscriptionInfo;
    }

    public final List<String> demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("network");
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:190:0x037b  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x0392  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0119  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dispatchDemoCommand(java.lang.String r18, android.os.Bundle r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            com.android.systemui.demomode.DemoModeController r2 = r0.mDemoModeController
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.isInDemoMode
            if (r2 != 0) goto L_0x000e
            return
        L_0x000e:
            java.lang.String r2 = "airplane"
            java.lang.String r2 = r1.getString(r2)
            java.lang.String r3 = "show"
            if (r2 == 0) goto L_0x0035
            boolean r2 = r2.equals(r3)
            com.android.systemui.statusbar.connectivity.CallbackHandler r4 = r0.mCallbackHandler
            com.android.systemui.statusbar.connectivity.IconState r5 = new com.android.systemui.statusbar.connectivity.IconState
            com.android.settingslib.SignalIcon$MobileIconGroup r6 = com.android.settingslib.mobile.TelephonyIcons.CARRIER_NETWORK_CHANGE
            r6 = 2131232671(0x7f08079f, float:1.8081458E38)
            android.content.Context r7 = r0.mContext
            r8 = 2131951672(0x7f130038, float:1.9539765E38)
            java.lang.String r7 = r7.getString(r8)
            r5.<init>(r2, r6, r7)
            r4.setIsAirplaneMode(r5)
        L_0x0035:
            java.lang.String r2 = "fully"
            java.lang.String r2 = r1.getString(r2)
            if (r2 == 0) goto L_0x0085
            boolean r2 = java.lang.Boolean.parseBoolean(r2)
            r0.mDemoInetCondition = r2
            java.util.BitSet r2 = new java.util.BitSet
            r2.<init>()
            boolean r5 = r0.mDemoInetCondition
            if (r5 == 0) goto L_0x0053
            com.android.systemui.statusbar.connectivity.WifiSignalController r5 = r0.mWifiSignalController
            int r5 = r5.mTransportType
            r2.set(r5)
        L_0x0053:
            com.android.systemui.statusbar.connectivity.WifiSignalController r5 = r0.mWifiSignalController
            java.util.Objects.requireNonNull(r5)
            T r6 = r5.mCurrentState
            int r7 = r5.mTransportType
            boolean r7 = r2.get(r7)
            r6.inetCondition = r7
            r5.notifyListenersIfNecessary()
            r5 = 0
        L_0x0066:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r6 = r0.mMobileSignalControllers
            int r6 = r6.size()
            if (r5 >= r6) goto L_0x0085
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r6 = r0.mMobileSignalControllers
            java.lang.Object r6 = r6.valueAt(r5)
            com.android.systemui.statusbar.connectivity.MobileSignalController r6 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r6
            boolean r7 = r0.mDemoInetCondition
            if (r7 == 0) goto L_0x007f
            int r7 = r6.mTransportType
            r2.set(r7)
        L_0x007f:
            r6.updateConnectivity(r2, r2)
            int r5 = r5 + 1
            goto L_0x0066
        L_0x0085:
            java.lang.String r2 = "wifi"
            java.lang.String r2 = r1.getString(r2)
            java.lang.String r5 = "inout"
            java.lang.String r6 = "out"
            java.lang.String r7 = "in"
            java.lang.String r8 = "null"
            java.lang.String r9 = "activity"
            java.lang.String r10 = "level"
            r12 = 110414(0x1af4e, float:1.54723E-40)
            r13 = 3365(0xd25, float:4.715E-42)
            r15 = -1
            if (r2 == 0) goto L_0x013c
            boolean r2 = r2.equals(r3)
            java.lang.String r14 = r1.getString(r10)
            if (r14 == 0) goto L_0x00cd
            com.android.systemui.statusbar.connectivity.WifiState r4 = r0.mDemoWifiState
            boolean r16 = r14.equals(r8)
            if (r16 == 0) goto L_0x00b4
            r11 = r15
            goto L_0x00c0
        L_0x00b4:
            int r14 = java.lang.Integer.parseInt(r14)
            int r16 = com.android.systemui.statusbar.connectivity.WifiIcons.WIFI_LEVEL_COUNT
            int r11 = r16 + -1
            int r11 = java.lang.Math.min(r14, r11)
        L_0x00c0:
            r4.level = r11
            com.android.systemui.statusbar.connectivity.WifiState r4 = r0.mDemoWifiState
            int r11 = r4.level
            if (r11 < 0) goto L_0x00ca
            r11 = 1
            goto L_0x00cb
        L_0x00ca:
            r11 = 0
        L_0x00cb:
            r4.connected = r11
        L_0x00cd:
            java.lang.String r4 = r1.getString(r9)
            if (r4 == 0) goto L_0x0120
            int r11 = r4.hashCode()
            if (r11 == r13) goto L_0x00f3
            if (r11 == r12) goto L_0x00ea
            r14 = 100357129(0x5fb5409, float:2.3634796E-35)
            if (r11 == r14) goto L_0x00e1
            goto L_0x00f9
        L_0x00e1:
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00e8
            goto L_0x00f9
        L_0x00e8:
            r4 = 2
            goto L_0x00fc
        L_0x00ea:
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L_0x00f1
            goto L_0x00f9
        L_0x00f1:
            r4 = 1
            goto L_0x00fc
        L_0x00f3:
            boolean r4 = r4.equals(r7)
            if (r4 != 0) goto L_0x00fb
        L_0x00f9:
            r4 = r15
            goto L_0x00fc
        L_0x00fb:
            r4 = 0
        L_0x00fc:
            if (r4 == 0) goto L_0x0119
            r11 = 1
            if (r4 == r11) goto L_0x0112
            r11 = 2
            if (r4 == r11) goto L_0x010b
            com.android.systemui.statusbar.connectivity.WifiSignalController r4 = r0.mWifiSignalController
            r14 = 0
            r4.setActivity(r14)
            goto L_0x0126
        L_0x010b:
            com.android.systemui.statusbar.connectivity.WifiSignalController r4 = r0.mWifiSignalController
            r14 = 3
            r4.setActivity(r14)
            goto L_0x0126
        L_0x0112:
            r11 = 2
            com.android.systemui.statusbar.connectivity.WifiSignalController r4 = r0.mWifiSignalController
            r4.setActivity(r11)
            goto L_0x0126
        L_0x0119:
            com.android.systemui.statusbar.connectivity.WifiSignalController r4 = r0.mWifiSignalController
            r11 = 1
            r4.setActivity(r11)
            goto L_0x0126
        L_0x0120:
            com.android.systemui.statusbar.connectivity.WifiSignalController r4 = r0.mWifiSignalController
            r11 = 0
            r4.setActivity(r11)
        L_0x0126:
            java.lang.String r4 = "ssid"
            java.lang.String r4 = r1.getString(r4)
            if (r4 == 0) goto L_0x0133
            com.android.systemui.statusbar.connectivity.WifiState r11 = r0.mDemoWifiState
            r11.ssid = r4
        L_0x0133:
            com.android.systemui.statusbar.connectivity.WifiState r4 = r0.mDemoWifiState
            r4.enabled = r2
            com.android.systemui.statusbar.connectivity.WifiSignalController r2 = r0.mWifiSignalController
            r2.notifyListeners()
        L_0x013c:
            java.lang.String r2 = "sims"
            java.lang.String r2 = r1.getString(r2)
            r4 = 8
            if (r2 == 0) goto L_0x0199
            int r2 = java.lang.Integer.parseInt(r2)
            r11 = 1
            int r2 = android.util.MathUtils.constrain(r2, r11, r4)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r14 = r0.mMobileSignalControllers
            int r14 = r14.size()
            if (r2 == r14) goto L_0x0199
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r14 = r0.mMobileSignalControllers
            r14.clear()
            android.telephony.SubscriptionManager r14 = r0.mSubscriptionManager
            int r14 = r14.getActiveSubscriptionInfoCountMax()
            r15 = r14
        L_0x0169:
            int r12 = r14 + r2
            if (r15 >= r12) goto L_0x0177
            android.telephony.SubscriptionInfo r12 = r0.addSignalController(r15, r15)
            r11.add(r12)
            int r15 = r15 + 1
            goto L_0x0169
        L_0x0177:
            com.android.systemui.statusbar.connectivity.CallbackHandler r2 = r0.mCallbackHandler
            r2.setSubs(r11)
            r2 = 0
        L_0x017d:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r11 = r0.mMobileSignalControllers
            int r11 = r11.size()
            if (r2 >= r11) goto L_0x0199
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r11 = r0.mMobileSignalControllers
            int r11 = r11.keyAt(r2)
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r12 = r0.mMobileSignalControllers
            java.lang.Object r11 = r12.get(r11)
            com.android.systemui.statusbar.connectivity.MobileSignalController r11 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r11
            r11.notifyListeners()
            int r2 = r2 + 1
            goto L_0x017d
        L_0x0199:
            java.lang.String r2 = "nosim"
            java.lang.String r2 = r1.getString(r2)
            if (r2 == 0) goto L_0x01ae
            boolean r2 = r2.equals(r3)
            r0.mHasNoSubs = r2
            com.android.systemui.statusbar.connectivity.CallbackHandler r11 = r0.mCallbackHandler
            boolean r12 = r0.mSimDetected
            r11.setNoSims(r2, r12)
        L_0x01ae:
            java.lang.String r2 = "mobile"
            java.lang.String r2 = r1.getString(r2)
            if (r2 == 0) goto L_0x03a6
            boolean r2 = r2.equals(r3)
            java.lang.String r11 = "datatype"
            java.lang.String r11 = r1.getString(r11)
            java.lang.String r12 = "slot"
            java.lang.String r12 = r1.getString(r12)
            boolean r14 = android.text.TextUtils.isEmpty(r12)
            if (r14 == 0) goto L_0x01cf
            r12 = 0
            goto L_0x01d3
        L_0x01cf:
            int r12 = java.lang.Integer.parseInt(r12)
        L_0x01d3:
            r14 = 0
            int r4 = android.util.MathUtils.constrain(r12, r14, r4)
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
        L_0x01dd:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r14 = r0.mMobileSignalControllers
            int r14 = r14.size()
            if (r14 > r4) goto L_0x01f3
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r14 = r0.mMobileSignalControllers
            int r14 = r14.size()
            android.telephony.SubscriptionInfo r14 = r0.addSignalController(r14, r14)
            r12.add(r14)
            goto L_0x01dd
        L_0x01f3:
            boolean r14 = r12.isEmpty()
            if (r14 != 0) goto L_0x01fe
            com.android.systemui.statusbar.connectivity.CallbackHandler r14 = r0.mCallbackHandler
            r14.setSubs(r12)
        L_0x01fe:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r12 = r0.mMobileSignalControllers
            java.lang.Object r4 = r12.valueAt(r4)
            com.android.systemui.statusbar.connectivity.MobileSignalController r4 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r4
            java.util.Objects.requireNonNull(r4)
            T r12 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r12 = (com.android.systemui.statusbar.connectivity.MobileState) r12
            if (r11 == 0) goto L_0x0211
            r14 = 1
            goto L_0x0212
        L_0x0211:
            r14 = 0
        L_0x0212:
            r12.dataSim = r14
            if (r11 == 0) goto L_0x0218
            r14 = 1
            goto L_0x0219
        L_0x0218:
            r14 = 0
        L_0x0219:
            r12.isDefault = r14
            if (r11 == 0) goto L_0x021f
            r14 = 1
            goto L_0x0220
        L_0x021f:
            r14 = 0
        L_0x0220:
            r12.dataConnected = r14
            if (r11 == 0) goto L_0x02d3
            java.lang.String r14 = "1x"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0230
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.ONE_X
            goto L_0x02d1
        L_0x0230:
            java.lang.String r14 = "3g"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x023c
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.THREE_G
            goto L_0x02d1
        L_0x023c:
            java.lang.String r14 = "4g"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0248
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G
            goto L_0x02d1
        L_0x0248:
            java.lang.String r14 = "4g+"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0254
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G_PLUS
            goto L_0x02d1
        L_0x0254:
            java.lang.String r14 = "5g"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0260
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.NR_5G
            goto L_0x02d1
        L_0x0260:
            java.lang.String r14 = "5ge"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x026c
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.LTE_CA_5G_E
            goto L_0x02d1
        L_0x026c:
            java.lang.String r14 = "5g+"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0277
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.NR_5G_PLUS
            goto L_0x02d1
        L_0x0277:
            java.lang.String r14 = "e"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0282
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.f33E
            goto L_0x02d1
        L_0x0282:
            java.lang.String r14 = "g"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x028d
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.f34G
            goto L_0x02d1
        L_0x028d:
            java.lang.String r14 = "h"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x0298
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.f35H
            goto L_0x02d1
        L_0x0298:
            java.lang.String r14 = "h+"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x02a3
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.H_PLUS
            goto L_0x02d1
        L_0x02a3:
            java.lang.String r14 = "lte"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x02ae
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.LTE
            goto L_0x02d1
        L_0x02ae:
            java.lang.String r14 = "lte+"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x02b9
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.LTE_PLUS
            goto L_0x02d1
        L_0x02b9:
            java.lang.String r14 = "dis"
            boolean r14 = r11.equals(r14)
            if (r14 == 0) goto L_0x02c4
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED
            goto L_0x02d1
        L_0x02c4:
            java.lang.String r14 = "not"
            boolean r11 = r11.equals(r14)
            if (r11 == 0) goto L_0x02cf
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA
            goto L_0x02d1
        L_0x02cf:
            com.android.settingslib.SignalIcon$MobileIconGroup r11 = com.android.settingslib.mobile.TelephonyIcons.UNKNOWN
        L_0x02d1:
            r12.iconGroup = r11
        L_0x02d3:
            java.lang.String r11 = "roam"
            boolean r12 = r1.containsKey(r11)
            if (r12 == 0) goto L_0x02ea
            T r12 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r12 = (com.android.systemui.statusbar.connectivity.MobileState) r12
            java.lang.String r11 = r1.getString(r11)
            boolean r11 = r3.equals(r11)
            r12.roaming = r11
        L_0x02ea:
            java.lang.String r10 = r1.getString(r10)
            if (r10 == 0) goto L_0x0317
            T r11 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r11 = (com.android.systemui.statusbar.connectivity.MobileState) r11
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto L_0x02fc
            r8 = -1
            goto L_0x0308
        L_0x02fc:
            int r8 = java.lang.Integer.parseInt(r10)
            int r10 = android.telephony.CellSignalStrength.getNumSignalStrengthLevels()
            int r8 = java.lang.Math.min(r8, r10)
        L_0x0308:
            r11.level = r8
            T r8 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r8 = (com.android.systemui.statusbar.connectivity.MobileState) r8
            int r10 = r8.level
            if (r10 < 0) goto L_0x0314
            r10 = 1
            goto L_0x0315
        L_0x0314:
            r10 = 0
        L_0x0315:
            r8.connected = r10
        L_0x0317:
            java.lang.String r8 = "inflate"
            boolean r10 = r1.containsKey(r8)
            if (r10 == 0) goto L_0x0340
            r14 = 0
        L_0x0320:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r10 = r0.mMobileSignalControllers
            int r10 = r10.size()
            if (r14 >= r10) goto L_0x0340
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r10 = r0.mMobileSignalControllers
            java.lang.Object r10 = r10.valueAt(r14)
            com.android.systemui.statusbar.connectivity.MobileSignalController r10 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r10
            java.lang.String r11 = r1.getString(r8)
            java.lang.String r12 = "true"
            boolean r11 = r12.equals(r11)
            r10.mInflateSignalStrengths = r11
            int r14 = r14 + 1
            goto L_0x0320
        L_0x0340:
            java.lang.String r8 = r1.getString(r9)
            if (r8 == 0) goto L_0x0398
            T r9 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r9 = (com.android.systemui.statusbar.connectivity.MobileState) r9
            r10 = 1
            r9.dataConnected = r10
            int r9 = r8.hashCode()
            if (r9 == r13) goto L_0x0370
            r10 = 110414(0x1af4e, float:1.54723E-40)
            if (r9 == r10) goto L_0x0367
            r7 = 100357129(0x5fb5409, float:2.3634796E-35)
            if (r9 == r7) goto L_0x035e
            goto L_0x0376
        L_0x035e:
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L_0x0365
            goto L_0x0376
        L_0x0365:
            r15 = 2
            goto L_0x0379
        L_0x0367:
            boolean r5 = r8.equals(r6)
            if (r5 != 0) goto L_0x036e
            goto L_0x0376
        L_0x036e:
            r15 = 1
            goto L_0x0379
        L_0x0370:
            boolean r5 = r8.equals(r7)
            if (r5 != 0) goto L_0x0378
        L_0x0376:
            r15 = -1
            goto L_0x0379
        L_0x0378:
            r15 = 0
        L_0x0379:
            if (r15 == 0) goto L_0x0392
            r5 = 1
            if (r15 == r5) goto L_0x038c
            r5 = 2
            if (r15 == r5) goto L_0x0386
            r6 = 0
            r4.setActivity(r6)
            goto L_0x039c
        L_0x0386:
            r5 = 3
            r6 = 0
            r4.setActivity(r5)
            goto L_0x039c
        L_0x038c:
            r5 = 2
            r6 = 0
            r4.setActivity(r5)
            goto L_0x039c
        L_0x0392:
            r5 = 1
            r6 = 0
            r4.setActivity(r5)
            goto L_0x039c
        L_0x0398:
            r6 = 0
            r4.setActivity(r6)
        L_0x039c:
            T r5 = r4.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r5 = (com.android.systemui.statusbar.connectivity.MobileState) r5
            r5.enabled = r2
            r4.notifyListeners()
            goto L_0x03a7
        L_0x03a6:
            r6 = 0
        L_0x03a7:
            java.lang.String r2 = "carriernetworkchange"
            java.lang.String r1 = r1.getString(r2)
            if (r1 == 0) goto L_0x03d3
            boolean r1 = r1.equals(r3)
            r4 = r6
        L_0x03b4:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r2 = r0.mMobileSignalControllers
            int r2 = r2.size()
            if (r4 >= r2) goto L_0x03d3
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r2 = r0.mMobileSignalControllers
            java.lang.Object r2 = r2.valueAt(r4)
            com.android.systemui.statusbar.connectivity.MobileSignalController r2 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r2
            java.util.Objects.requireNonNull(r2)
            T r3 = r2.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r3 = (com.android.systemui.statusbar.connectivity.MobileState) r3
            r3.carrierNetworkChangeMode = r1
            r2.updateTelephony()
            int r4 = r4 + 1
            goto L_0x03b4
        L_0x03d3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.dispatchDemoCommand(java.lang.String, android.os.Bundle):void");
    }

    @VisibleForTesting
    public void doUpdateMobileControllers() {
        List completeActiveSubscriptionInfoList = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        if (completeActiveSubscriptionInfoList == null) {
            completeActiveSubscriptionInfoList = Collections.emptyList();
        }
        if (completeActiveSubscriptionInfoList.size() == 2) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) completeActiveSubscriptionInfoList.get(0);
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) completeActiveSubscriptionInfoList.get(1);
            if (subscriptionInfo.getGroupUuid() != null && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid()) && (subscriptionInfo.isOpportunistic() || subscriptionInfo2.isOpportunistic())) {
                if (CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean")) {
                    if (!subscriptionInfo.isOpportunistic()) {
                        subscriptionInfo = subscriptionInfo2;
                    }
                    completeActiveSubscriptionInfoList.remove(subscriptionInfo);
                } else {
                    if (subscriptionInfo.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                        subscriptionInfo = subscriptionInfo2;
                    }
                    completeActiveSubscriptionInfoList.remove(subscriptionInfo);
                }
            }
        }
        if (hasCorrectMobileControllers(completeActiveSubscriptionInfoList)) {
            updateNoSims();
            return;
        }
        synchronized (this.mLock) {
            setCurrentSubscriptionsLocked(completeActiveSubscriptionInfoList);
        }
        updateNoSims();
        recalculateEmergency();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        ArrayList arrayList;
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "NetworkController state:", "  mUserSetup=");
        m.append(this.mUserSetup);
        printWriter.println(m.toString());
        printWriter.println("  - telephony ------");
        printWriter.print("  hasVoiceCallingFeature()=");
        printWriter.println(hasVoiceCallingFeature());
        printWriter.println("  mListening=" + this.mListening);
        printWriter.println("  - connectivity ------");
        printWriter.print("  mConnectedTransports=");
        printWriter.println(this.mConnectedTransports);
        printWriter.print("  mValidatedTransports=");
        printWriter.println(this.mValidatedTransports);
        printWriter.print("  mInetCondition=");
        printWriter.println(this.mInetCondition);
        printWriter.print("  mAirplaneMode=");
        printWriter.println(this.mAirplaneMode);
        printWriter.print("  mLocale=");
        printWriter.println(this.mLocale);
        printWriter.print("  mLastServiceState=");
        printWriter.println(this.mLastServiceState);
        printWriter.print("  mIsEmergency=");
        printWriter.println(this.mIsEmergency);
        printWriter.print("  mEmergencySource=");
        int i = this.mEmergencySource;
        if (i > 300) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ASSUMED_VOICE_CONTROLLER(");
            m2.append(i - 200);
            m2.append(")");
            str = m2.toString();
        } else if (i > 300) {
            StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NO_SUB(");
            m3.append(i - 300);
            m3.append(")");
            str = m3.toString();
        } else if (i > 200) {
            StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("VOICE_CONTROLLER(");
            m4.append(i - 200);
            m4.append(")");
            str = m4.toString();
        } else if (i > 100) {
            StringBuilder m5 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("FIRST_CONTROLLER(");
            m5.append(i - 100);
            m5.append(")");
            str = m5.toString();
        } else if (i == 0) {
            str = "NO_CONTROLLERS";
        } else {
            str = "UNKNOWN_SOURCE";
        }
        printWriter.println(str);
        printWriter.println("  - DefaultNetworkCallback -----");
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            if (this.mHistory[i3] != null) {
                i2++;
            }
        }
        int i4 = this.mHistoryIndex + 16;
        while (true) {
            i4--;
            if (i4 < (this.mHistoryIndex + 16) - i2) {
                break;
            }
            StringBuilder m6 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Previous NetworkCallback(");
            m6.append((this.mHistoryIndex + 16) - i4);
            m6.append("): ");
            m6.append(this.mHistory[i4 & 15]);
            printWriter.println(m6.toString());
        }
        printWriter.println("  - config ------");
        for (int i5 = 0; i5 < this.mMobileSignalControllers.size(); i5++) {
            this.mMobileSignalControllers.valueAt(i5).dump(printWriter);
        }
        this.mWifiSignalController.dump(printWriter);
        this.mEthernetSignalController.dump(printWriter);
        AccessPointControllerImpl accessPointControllerImpl = this.mAccessPoints;
        Objects.requireNonNull(accessPointControllerImpl);
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("AccessPointControllerImpl:");
        indentingPrintWriter.increaseIndent();
        StringBuilder m7 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Callbacks: ");
        m7.append(Arrays.toString(accessPointControllerImpl.mCallbacks.toArray()));
        indentingPrintWriter.println(m7.toString());
        indentingPrintWriter.println("WifiPickerTracker: " + accessPointControllerImpl.mWifiPickerTracker.toString());
        if (accessPointControllerImpl.mWifiPickerTracker != null && !accessPointControllerImpl.mCallbacks.isEmpty()) {
            StringBuilder m8 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connected: ");
            WifiPickerTracker wifiPickerTracker = accessPointControllerImpl.mWifiPickerTracker;
            Objects.requireNonNull(wifiPickerTracker);
            m8.append(wifiPickerTracker.mConnectedWifiEntry);
            indentingPrintWriter.println(m8.toString());
            StringBuilder sb = new StringBuilder();
            sb.append("Other wifi entries: ");
            WifiPickerTracker wifiPickerTracker2 = accessPointControllerImpl.mWifiPickerTracker;
            Objects.requireNonNull(wifiPickerTracker2);
            synchronized (wifiPickerTracker2.mLock) {
                arrayList = new ArrayList(wifiPickerTracker2.mWifiEntries);
            }
            sb.append(Arrays.toString(arrayList.toArray()));
            indentingPrintWriter.println(sb.toString());
        } else if (accessPointControllerImpl.mWifiPickerTracker != null) {
            indentingPrintWriter.println("WifiPickerTracker not started, cannot get reliable entries");
        }
        indentingPrintWriter.decreaseIndent();
        CallbackHandler callbackHandler = this.mCallbackHandler;
        Objects.requireNonNull(callbackHandler);
        printWriter.println("  - CallbackHandler -----");
        int i6 = 0;
        for (int i7 = 0; i7 < 64; i7++) {
            if (callbackHandler.mHistory[i7] != null) {
                i6++;
            }
        }
        int i8 = callbackHandler.mHistoryIndex + 64;
        while (true) {
            i8--;
            if (i8 >= (callbackHandler.mHistoryIndex + 64) - i6) {
                StringBuilder m9 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Previous Callback(");
                m9.append((callbackHandler.mHistoryIndex + 64) - i8);
                m9.append("): ");
                m9.append(callbackHandler.mHistory[i8 & 63]);
                printWriter.println(m9.toString());
            } else {
                return;
            }
        }
    }

    public final String getMobileDataNetworkName() {
        Objects.requireNonNull(this.mSubDefaults);
        MobileSignalController controllerWithSubId = getControllerWithSubId(SubscriptionManager.getActiveDataSubscriptionId());
        if (controllerWithSubId != null) {
            return ((MobileState) controllerWithSubId.mCurrentState).networkNameData;
        }
        return "";
    }

    public final int getNumberSubscriptions() {
        return this.mMobileSignalControllers.size();
    }

    public final boolean hasVoiceCallingFeature() {
        if (this.mPhone.getPhoneType() != 0) {
            return true;
        }
        return false;
    }

    public final boolean isMobileDataNetworkInService() {
        Objects.requireNonNull(this.mSubDefaults);
        MobileSignalController controllerWithSubId = getControllerWithSubId(SubscriptionManager.getActiveDataSubscriptionId());
        if (controllerWithSubId == null || !((MobileState) controllerWithSubId.mCurrentState).isInService()) {
            return false;
        }
        return true;
    }

    public final boolean isRadioOn() {
        return !this.mAirplaneMode;
    }

    public final void notifyListeners() {
        CallbackHandler callbackHandler = this.mCallbackHandler;
        boolean z = this.mAirplaneMode;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        callbackHandler.setIsAirplaneMode(new IconState(z, C1777R.C1778drawable.stat_sys_airplane_mode, this.mContext.getString(C1777R.string.accessibility_airplane_mode)));
        this.mCallbackHandler.setNoSims(this.mHasNoSubs, this.mSimDetected);
    }

    public final void onDemoModeFinished() {
        if (DEBUG) {
            Log.d("NetworkController", "Exiting demo mode");
        }
        updateMobileControllers();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            Objects.requireNonNull(valueAt);
            valueAt.mCurrentState.copyFrom(valueAt.mLastState);
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        Objects.requireNonNull(wifiSignalController);
        wifiSignalController.mCurrentState.copyFrom(wifiSignalController.mLastState);
        this.mReceiverHandler.post(this.mRegisterListeners);
        notifyAllListeners();
    }

    public final void onDemoModeStarted() {
        if (DEBUG) {
            Log.d("NetworkController", "Entering demo mode");
        }
        this.mListening = false;
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            Objects.requireNonNull(valueAt);
            valueAt.mMobileStatusTracker.setListening(false);
            valueAt.mContext.getContentResolver().unregisterContentObserver(valueAt.mObserver);
            valueAt.mImsMmTelManager.unregisterImsRegistrationCallback(valueAt.mRegistrationCallback);
        }
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.mSubscriptionListener);
        this.mBroadcastDispatcher.unregisterReceiver(this);
        this.mDemoInetCondition = this.mInetCondition;
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        Objects.requireNonNull(wifiSignalController);
        WifiState wifiState = (WifiState) wifiSignalController.mCurrentState;
        this.mDemoWifiState = wifiState;
        wifiState.ssid = "DemoMode";
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onReceive(android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            boolean r6 = CHATTY
            if (r6 == 0) goto L_0x001a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "onReceive: intent="
            r6.append(r0)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r0 = "NetworkController"
            android.util.Log.d(r0, r6)
        L_0x001a:
            java.lang.String r6 = r7.getAction()
            java.util.Objects.requireNonNull(r6)
            int r0 = r6.hashCode()
            r1 = 3
            r2 = -1
            r3 = 4
            r4 = 0
            switch(r0) {
                case -2104353374: goto L_0x007c;
                case -1465084191: goto L_0x0071;
                case -1172645946: goto L_0x0066;
                case -1138588223: goto L_0x005b;
                case -1076576821: goto L_0x0050;
                case -229777127: goto L_0x0045;
                case -25388475: goto L_0x003a;
                case 464243859: goto L_0x002f;
                default: goto L_0x002c;
            }
        L_0x002c:
            r6 = r2
            goto L_0x0086
        L_0x002f:
            java.lang.String r0 = "android.settings.panel.action.INTERNET_CONNECTIVITY"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0038
            goto L_0x002c
        L_0x0038:
            r6 = 7
            goto L_0x0086
        L_0x003a:
            java.lang.String r0 = "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0043
            goto L_0x002c
        L_0x0043:
            r6 = 6
            goto L_0x0086
        L_0x0045:
            java.lang.String r0 = "android.intent.action.SIM_STATE_CHANGED"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x004e
            goto L_0x002c
        L_0x004e:
            r6 = 5
            goto L_0x0086
        L_0x0050:
            java.lang.String r0 = "android.intent.action.AIRPLANE_MODE"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0059
            goto L_0x002c
        L_0x0059:
            r6 = r3
            goto L_0x0086
        L_0x005b:
            java.lang.String r0 = "android.telephony.action.CARRIER_CONFIG_CHANGED"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0064
            goto L_0x002c
        L_0x0064:
            r6 = r1
            goto L_0x0086
        L_0x0066:
            java.lang.String r0 = "android.net.conn.CONNECTIVITY_CHANGE"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x006f
            goto L_0x002c
        L_0x006f:
            r6 = 2
            goto L_0x0086
        L_0x0071:
            java.lang.String r0 = "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x007a
            goto L_0x002c
        L_0x007a:
            r6 = 1
            goto L_0x0086
        L_0x007c:
            java.lang.String r0 = "android.intent.action.SERVICE_STATE"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0085
            goto L_0x002c
        L_0x0085:
            r6 = r4
        L_0x0086:
            switch(r6) {
                case 0: goto L_0x013a;
                case 1: goto L_0x0136;
                case 2: goto L_0x0132;
                case 3: goto L_0x011f;
                case 4: goto L_0x0118;
                case 5: goto L_0x010a;
                case 6: goto L_0x00e1;
                case 7: goto L_0x00d5;
                default: goto L_0x0089;
            }
        L_0x0089:
            java.lang.String r6 = "android.telephony.extra.SUBSCRIPTION_INDEX"
            int r6 = r7.getIntExtra(r6, r2)
            boolean r0 = android.telephony.SubscriptionManager.isValidSubscriptionId(r6)
            if (r0 == 0) goto L_0x00af
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r0 = r5.mMobileSignalControllers
            int r0 = r0.indexOfKey(r6)
            if (r0 < 0) goto L_0x00aa
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r5 = r5.mMobileSignalControllers
            java.lang.Object r5 = r5.get(r6)
            com.android.systemui.statusbar.connectivity.MobileSignalController r5 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r5
            r5.handleBroadcast(r7)
            goto L_0x014f
        L_0x00aa:
            r5.updateMobileControllers()
            goto L_0x014f
        L_0x00af:
            com.android.systemui.statusbar.connectivity.WifiSignalController r5 = r5.mWifiSignalController
            java.util.Objects.requireNonNull(r5)
            com.android.settingslib.wifi.WifiStatusTracker r6 = r5.mWifiTracker
            java.util.Objects.requireNonNull(r6)
            android.net.wifi.WifiManager r0 = r6.mWifiManager
            if (r0 != 0) goto L_0x00be
            goto L_0x00cd
        L_0x00be:
            java.lang.String r7 = r7.getAction()
            java.lang.String r0 = "android.net.wifi.WIFI_STATE_CHANGED"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x00cd
            r6.updateWifiState()
        L_0x00cd:
            r5.copyWifiStates()
            r5.notifyListenersIfNecessary()
            goto L_0x014f
        L_0x00d5:
            android.os.Handler r6 = r5.mMainHandler
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1 r7 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1
            r7.<init>(r5, r1)
            r6.post(r7)
            goto L_0x014f
        L_0x00e1:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r6 = r5.mMobileSignalControllers
            int r6 = r6.size()
            if (r4 >= r6) goto L_0x00f7
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r6 = r5.mMobileSignalControllers
            java.lang.Object r6 = r6.valueAt(r4)
            com.android.systemui.statusbar.connectivity.MobileSignalController r6 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r6
            r6.handleBroadcast(r7)
            int r4 = r4 + 1
            goto L_0x00e1
        L_0x00f7:
            android.content.Context r6 = r5.mContext
            com.android.settingslib.mobile.MobileMappings$Config r6 = com.android.settingslib.mobile.MobileMappings.Config.readConfig(r6)
            r5.mConfig = r6
            android.os.Handler r6 = r5.mReceiverHandler
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0 r7 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0
            r7.<init>(r5, r3)
            r6.post(r7)
            goto L_0x014f
        L_0x010a:
            java.lang.String r6 = "rebroadcastOnUnlock"
            boolean r6 = r7.getBooleanExtra(r6, r4)
            if (r6 == 0) goto L_0x0114
            goto L_0x014f
        L_0x0114:
            r5.updateMobileControllers()
            goto L_0x014f
        L_0x0118:
            r5.refreshLocale()
            r5.updateAirplaneMode(r4)
            goto L_0x014f
        L_0x011f:
            android.content.Context r6 = r5.mContext
            com.android.settingslib.mobile.MobileMappings$Config r6 = com.android.settingslib.mobile.MobileMappings.Config.readConfig(r6)
            r5.mConfig = r6
            android.os.Handler r6 = r5.mReceiverHandler
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2 r7 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2
            r7.<init>(r5, r3)
            r6.post(r7)
            goto L_0x014f
        L_0x0132:
            r5.updateConnectivity()
            goto L_0x014f
        L_0x0136:
            r5.recalculateEmergency()
            goto L_0x014f
        L_0x013a:
            android.os.Bundle r6 = r7.getExtras()
            android.telephony.ServiceState r6 = android.telephony.ServiceState.newFromBundle(r6)
            r5.mLastServiceState = r6
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r6 = r5.mMobileSignalControllers
            int r6 = r6.size()
            if (r6 != 0) goto L_0x014f
            r5.recalculateEmergency()
        L_0x014f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.onReceive(android.content.Context, android.content.Intent):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
        if (r0.isEmergencyOnly() != false) goto L_0x00d5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void recalculateEmergency() {
        /*
            r7 = this;
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r0 = r7.mMobileSignalControllers
            int r0 = r0.size()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0018
            r7.mEmergencySource = r1
            android.telephony.ServiceState r0 = r7.mLastServiceState
            if (r0 == 0) goto L_0x00d6
            boolean r0 = r0.isEmergencyOnly()
            if (r0 == 0) goto L_0x00d6
            goto L_0x00d5
        L_0x0018:
            com.android.settingslib.mobile.MobileStatusTracker$SubscriptionDefaults r0 = r7.mSubDefaults
            java.util.Objects.requireNonNull(r0)
            int r0 = android.telephony.SubscriptionManager.getDefaultVoiceSubscriptionId()
            boolean r3 = android.telephony.SubscriptionManager.isValidSubscriptionId(r0)
            java.lang.String r4 = "NetworkController"
            if (r3 != 0) goto L_0x0063
            r3 = r1
        L_0x002a:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r5 = r7.mMobileSignalControllers
            int r5 = r5.size()
            if (r3 >= r5) goto L_0x0063
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r5 = r7.mMobileSignalControllers
            java.lang.Object r5 = r5.valueAt(r3)
            com.android.systemui.statusbar.connectivity.MobileSignalController r5 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r5
            java.util.Objects.requireNonNull(r5)
            T r6 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r6 = (com.android.systemui.statusbar.connectivity.MobileState) r6
            boolean r6 = r6.isEmergency
            if (r6 != 0) goto L_0x0060
            android.telephony.SubscriptionInfo r0 = r5.mSubscriptionInfo
            int r0 = r0.getSubscriptionId()
            int r0 = r0 + 100
            r7.mEmergencySource = r0
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x00d6
            java.lang.String r0 = "Found emergency "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r2 = r5.mTag
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r0, r2, r4)
            goto L_0x00d6
        L_0x0060:
            int r3 = r3 + 1
            goto L_0x002a
        L_0x0063:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r3 = r7.mMobileSignalControllers
            int r3 = r3.indexOfKey(r0)
            if (r3 < 0) goto L_0x008a
            int r1 = r0 + 200
            r7.mEmergencySource = r1
            boolean r1 = DEBUG
            if (r1 == 0) goto L_0x0078
            java.lang.String r1 = "Getting emergency from "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r1, r0, r4)
        L_0x0078:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r1 = r7.mMobileSignalControllers
            java.lang.Object r0 = r1.get(r0)
            com.android.systemui.statusbar.connectivity.MobileSignalController r0 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r0
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r1 = r0.isEmergency
            goto L_0x00d6
        L_0x008a:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r3 = r7.mMobileSignalControllers
            int r3 = r3.size()
            if (r3 != r2) goto L_0x00c8
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r0 = r7.mMobileSignalControllers
            int r0 = r0.keyAt(r1)
            int r0 = r0 + 400
            r7.mEmergencySource = r0
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x00b6
            java.lang.String r0 = "Getting assumed emergency from "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r2 = r7.mMobileSignalControllers
            int r2 = r2.keyAt(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r4, r0)
        L_0x00b6:
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r0 = r7.mMobileSignalControllers
            java.lang.Object r0 = r0.valueAt(r1)
            com.android.systemui.statusbar.connectivity.MobileSignalController r0 = (com.android.systemui.statusbar.connectivity.MobileSignalController) r0
            java.util.Objects.requireNonNull(r0)
            T r0 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r1 = r0.isEmergency
            goto L_0x00d6
        L_0x00c8:
            boolean r1 = DEBUG
            if (r1 == 0) goto L_0x00d1
            java.lang.String r1 = "Cannot find controller for voice sub: "
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m(r1, r0, r4)
        L_0x00d1:
            int r0 = r0 + 300
            r7.mEmergencySource = r0
        L_0x00d5:
            r1 = r2
        L_0x00d6:
            r7.mIsEmergency = r1
            com.android.systemui.statusbar.connectivity.CallbackHandler r7 = r7.mCallbackHandler
            r7.setEmergencyCallsOnly(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.recalculateEmergency():void");
    }

    public final void refreshLocale() {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        if (!locale.equals(this.mLocale)) {
            this.mLocale = locale;
            WifiSignalController wifiSignalController = this.mWifiSignalController;
            Objects.requireNonNull(wifiSignalController);
            WifiStatusTracker wifiStatusTracker = wifiSignalController.mWifiTracker;
            Objects.requireNonNull(wifiStatusTracker);
            wifiStatusTracker.updateStatusLabel();
            wifiStatusTracker.mCallback.run();
            notifyAllListeners();
        }
    }

    public final void removeCallback(Object obj) {
        CallbackHandler callbackHandler = this.mCallbackHandler;
        Objects.requireNonNull(callbackHandler);
        callbackHandler.obtainMessage(7, 0, 0, (SignalCallback) obj).sendToTarget();
    }

    @GuardedBy({"mLock"})
    @VisibleForTesting
    public void setCurrentSubscriptionsLocked(List<SubscriptionInfo> list) {
        SparseArray sparseArray;
        int i;
        int i2;
        List<SubscriptionInfo> list2;
        List<SubscriptionInfo> list3 = list;
        Collections.sort(list3, new Comparator<SubscriptionInfo>() {
            public final int compare(Object obj, Object obj2) {
                int i;
                int i2;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj2;
                if (subscriptionInfo.getSimSlotIndex() == subscriptionInfo2.getSimSlotIndex()) {
                    i2 = subscriptionInfo.getSubscriptionId();
                    i = subscriptionInfo2.getSubscriptionId();
                } else {
                    i2 = subscriptionInfo.getSimSlotIndex();
                    i = subscriptionInfo2.getSimSlotIndex();
                }
                return i2 - i;
            }
        });
        this.mCurrentSubscriptions = list3;
        SparseArray sparseArray2 = new SparseArray();
        for (int i3 = 0; i3 < this.mMobileSignalControllers.size(); i3++) {
            sparseArray2.put(this.mMobileSignalControllers.keyAt(i3), this.mMobileSignalControllers.valueAt(i3));
        }
        this.mMobileSignalControllers.clear();
        int size = list.size();
        List<SubscriptionInfo> list4 = list3;
        int i4 = 0;
        while (i4 < size) {
            int subscriptionId = list4.get(i4).getSubscriptionId();
            if (sparseArray2.indexOfKey(subscriptionId) >= 0) {
                this.mMobileSignalControllers.put(subscriptionId, (MobileSignalController) sparseArray2.get(subscriptionId));
                sparseArray2.remove(subscriptionId);
                i2 = i4;
                i = size;
                list2 = list3;
                sparseArray = sparseArray2;
            } else {
                MobileStatusTracker.SubscriptionDefaults subscriptionDefaults = this.mSubDefaults;
                MobileStatusTracker.SubscriptionDefaults subscriptionDefaults2 = subscriptionDefaults;
                sparseArray = sparseArray2;
                MobileSignalController mobileSignalController = r0;
                i = size;
                MobileSignalController mobileSignalController2 = new MobileSignalController(this.mContext, this.mConfig, this.mHasMobileDataFeature, this.mPhone.createForSubscriptionId(subscriptionId), this.mCallbackHandler, this, list4.get(i4), subscriptionDefaults2, this.mReceiverHandler.getLooper(), this.mCarrierConfigTracker, this.mFeatureFlags);
                ((MobileState) mobileSignalController.mCurrentState).userSetup = this.mUserSetup;
                mobileSignalController.notifyListenersIfNecessary();
                this.mMobileSignalControllers.put(subscriptionId, mobileSignalController);
                list2 = list;
                i2 = i4;
                if (list2.get(i2).getSimSlotIndex() == 0) {
                    this.mDefaultSignalController = mobileSignalController;
                }
                if (this.mListening) {
                    mobileSignalController.registerListener();
                }
                list4 = list2;
            }
            i4 = i2 + 1;
            list3 = list2;
            size = i;
            sparseArray2 = sparseArray;
        }
        SparseArray sparseArray3 = sparseArray2;
        if (this.mListening) {
            int i5 = 0;
            while (i5 < sparseArray3.size()) {
                SparseArray sparseArray4 = sparseArray3;
                int keyAt = sparseArray4.keyAt(i5);
                if (sparseArray4.get(keyAt) == this.mDefaultSignalController) {
                    this.mDefaultSignalController = null;
                }
                MobileSignalController mobileSignalController3 = (MobileSignalController) sparseArray4.get(keyAt);
                Objects.requireNonNull(mobileSignalController3);
                mobileSignalController3.mMobileStatusTracker.setListening(false);
                mobileSignalController3.mContext.getContentResolver().unregisterContentObserver(mobileSignalController3.mObserver);
                mobileSignalController3.mImsMmTelManager.unregisterImsRegistrationCallback(mobileSignalController3.mRegistrationCallback);
                i5++;
                sparseArray3 = sparseArray4;
            }
        }
        this.mCallbackHandler.setSubs(list4);
        notifyAllListeners();
        pushConnectivityToSignals();
        updateAirplaneMode(true);
    }

    public final void setWifiEnabled(final boolean z) {
        new AsyncTask<Void, Void, Void>() {
            public final Object doInBackground(Object[] objArr) {
                Void[] voidArr = (Void[]) objArr;
                NetworkControllerImpl.this.mWifiManager.setWifiEnabled(z);
                return null;
            }
        }.execute(new Void[0]);
    }

    public final void updateAirplaneMode(boolean z) {
        boolean z2 = true;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) != 1) {
            z2 = false;
        }
        if (z2 != this.mAirplaneMode || z) {
            this.mAirplaneMode = z2;
            for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
                boolean z3 = this.mAirplaneMode;
                Objects.requireNonNull(valueAt);
                ((MobileState) valueAt.mCurrentState).airplaneMode = z3;
                valueAt.notifyListenersIfNecessary();
            }
            notifyListeners();
        }
    }

    public final void updateConnectivity() {
        boolean z;
        boolean z2;
        int i;
        boolean z3;
        this.mConnectedTransports.clear();
        this.mValidatedTransports.clear();
        NetworkCapabilities networkCapabilities = this.mLastDefaultNetworkCapabilities;
        boolean z4 = false;
        if (networkCapabilities != null) {
            for (int i2 : networkCapabilities.getTransportTypes()) {
                if (i2 == 0 || i2 == 1 || i2 == 3) {
                    if (i2 != 0 || Utils.tryGetWifiInfoForVcn(this.mLastDefaultNetworkCapabilities) == null) {
                        this.mConnectedTransports.set(i2);
                        if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                            this.mValidatedTransports.set(i2);
                        }
                    } else {
                        this.mConnectedTransports.set(1);
                        if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                            this.mValidatedTransports.set(1);
                        }
                    }
                }
            }
        }
        if (this.mForceCellularValidated) {
            this.mValidatedTransports.set(0);
        }
        if (CHATTY) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("updateConnectivity: mConnectedTransports=");
            m.append(this.mConnectedTransports);
            Log.d("NetworkController", m.toString());
            Log.d("NetworkController", "updateConnectivity: mValidatedTransports=" + this.mValidatedTransports);
        }
        if (this.mValidatedTransports.get(0) || this.mValidatedTransports.get(1) || this.mValidatedTransports.get(3)) {
            z = true;
        } else {
            z = false;
        }
        this.mInetCondition = z;
        pushConnectivityToSignals();
        if (this.mProviderModelBehavior) {
            if (this.mConnectedTransports.get(0) || this.mConnectedTransports.get(1) || this.mConnectedTransports.get(3)) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.mNoDefaultNetwork = z2;
            this.mCallbackHandler.setConnectivityStatus(z2, !this.mInetCondition, this.mNoNetworksAvailable);
            for (int i3 = 0; i3 < this.mMobileSignalControllers.size(); i3++) {
                MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i3);
                Objects.requireNonNull(valueAt);
                MobileState mobileState = (MobileState) valueAt.mCurrentState;
                Objects.requireNonNull(mobileState);
                ServiceState serviceState = mobileState.serviceState;
                if (serviceState == null) {
                    i = -1;
                } else {
                    i = serviceState.getState();
                }
                if (i != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                valueAt.notifyCallStateChange(new IconState(z3 & (!valueAt.hideNoCalling()), C1777R.C1778drawable.ic_qs_no_calling_sms, valueAt.getTextIfExists(C1777R.string.accessibility_no_calling).toString()), valueAt.mSubscriptionInfo.getSubscriptionId());
            }
            notifyAllListeners();
            return;
        }
        if (!this.mConnectedTransports.get(0) && !this.mConnectedTransports.get(1) && !this.mConnectedTransports.get(3)) {
            z4 = true;
        }
        this.mNoDefaultNetwork = z4;
        this.mCallbackHandler.setConnectivityStatus(z4, true ^ this.mInetCondition, this.mNoNetworksAvailable);
    }

    public final void updateMobileControllers() {
        if (this.mListening) {
            doUpdateMobileControllers();
        }
    }

    @VisibleForTesting
    public void updateNoSims() {
        boolean z;
        boolean z2 = false;
        if (!this.mHasMobileDataFeature || this.mMobileSignalControllers.size() != 0) {
            z = false;
        } else {
            z = true;
        }
        int activeModemCount = this.mPhone.getActiveModemCount();
        int i = 0;
        while (true) {
            if (i < activeModemCount) {
                int simState = this.mPhone.getSimState(i);
                if (simState != 1 && simState != 0) {
                    z2 = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (z != this.mHasNoSubs || z2 != this.mSimDetected) {
            this.mHasNoSubs = z;
            this.mSimDetected = z2;
            this.mCallbackHandler.setNoSims(z, z2);
        }
    }

    /* renamed from: -$$Nest$mgetProcessedTransportTypes  reason: not valid java name */
    public static int[] m229$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl networkControllerImpl, NetworkCapabilities networkCapabilities) {
        Objects.requireNonNull(networkControllerImpl);
        int[] transportTypes = networkCapabilities.getTransportTypes();
        int i = 0;
        while (true) {
            if (i < transportTypes.length) {
                if (transportTypes[i] == 0 && Utils.tryGetWifiInfoForVcn(networkCapabilities) != null) {
                    transportTypes[i] = 1;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return transportTypes;
    }

    public final MobileSignalController getControllerWithSubId(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            if (DEBUG) {
                Log.e("NetworkController", "No data sim selected");
            }
            return this.mDefaultSignalController;
        } else if (this.mMobileSignalControllers.indexOfKey(i) >= 0) {
            return this.mMobileSignalControllers.get(i);
        } else {
            if (DEBUG) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Cannot find controller for data sub: ", i, "NetworkController");
            }
            return this.mDefaultSignalController;
        }
    }

    @VisibleForTesting
    public void handleConfigurationChanged() {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
        updateMobileControllers();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            MobileMappings.Config config = this.mConfig;
            Objects.requireNonNull(valueAt);
            valueAt.mConfig = config;
            valueAt.mInflateSignalStrengths = YieldKt.shouldInflateSignalStrength(valueAt.mContext, valueAt.mSubscriptionInfo.getSubscriptionId());
            valueAt.mNetworkToIconLookup = MobileMappings.mapIconSets(valueAt.mConfig);
            if (!valueAt.mConfig.showAtLeast3G) {
                signalIcon$MobileIconGroup = TelephonyIcons.f34G;
            } else {
                signalIcon$MobileIconGroup = TelephonyIcons.THREE_G;
            }
            valueAt.mDefaultIcons = signalIcon$MobileIconGroup;
            valueAt.updateTelephony();
            if (this.mProviderModelBehavior) {
                valueAt.refreshCallIndicator(this.mCallbackHandler);
            }
        }
        refreshLocale();
    }

    @VisibleForTesting
    public boolean hasCorrectMobileControllers(List<SubscriptionInfo> list) {
        if (list.size() != this.mMobileSignalControllers.size()) {
            return false;
        }
        for (SubscriptionInfo subscriptionId : list) {
            if (this.mMobileSignalControllers.indexOfKey(subscriptionId.getSubscriptionId()) < 0) {
                return false;
            }
        }
        return true;
    }

    public final void notifyAllListeners() {
        notifyListeners();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).notifyListeners();
        }
        this.mWifiSignalController.notifyListeners();
        this.mEthernetSignalController.notifyListeners();
    }

    @VisibleForTesting
    public void setNoNetworksAvailable(boolean z) {
        this.mNoNetworksAvailable = z;
    }

    public final DataSaverControllerImpl getDataSaverController() {
        return this.mDataSaverController;
    }

    public final DataUsageController getMobileDataController() {
        return this.mDataUsageController;
    }

    public final boolean hasEmergencyCryptKeeperText() {
        return EncryptionHelper.IS_DATA_ENCRYPTED;
    }

    public final boolean hasMobileDataFeature() {
        return this.mHasMobileDataFeature;
    }

    @VisibleForTesting
    public boolean isUserSetup() {
        return this.mUserSetup;
    }
}
