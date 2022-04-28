package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.CarrierConfigTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class NetworkControllerImpl_Factory implements Factory<NetworkControllerImpl> {
    public final Provider<AccessPointControllerImpl> accessPointControllerProvider;
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Looper> bgLooperProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<CallbackHandler> callbackHandlerProvider;
    public final Provider<CarrierConfigTracker> carrierConfigTrackerProvider;
    public final Provider<ConnectivityManager> connectivityManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DemoModeController> demoModeControllerProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<InternetDialogFactory> internetDialogFactoryProvider;
    public final Provider<NetworkScoreManager> networkScoreManagerProvider;
    public final Provider<SubscriptionManager> subscriptionManagerProvider;
    public final Provider<TelephonyListenerManager> telephonyListenerManagerProvider;
    public final Provider<TelephonyManager> telephonyManagerProvider;
    public final Provider<WifiManager> wifiManagerProvider;

    public NetworkControllerImpl_Factory(Provider<Context> provider, Provider<Looper> provider2, Provider<Executor> provider3, Provider<SubscriptionManager> provider4, Provider<CallbackHandler> provider5, Provider<DeviceProvisionedController> provider6, Provider<BroadcastDispatcher> provider7, Provider<ConnectivityManager> provider8, Provider<TelephonyManager> provider9, Provider<TelephonyListenerManager> provider10, Provider<WifiManager> provider11, Provider<NetworkScoreManager> provider12, Provider<AccessPointControllerImpl> provider13, Provider<DemoModeController> provider14, Provider<CarrierConfigTracker> provider15, Provider<Handler> provider16, Provider<InternetDialogFactory> provider17, Provider<FeatureFlags> provider18, Provider<DumpManager> provider19) {
        this.contextProvider = provider;
        this.bgLooperProvider = provider2;
        this.bgExecutorProvider = provider3;
        this.subscriptionManagerProvider = provider4;
        this.callbackHandlerProvider = provider5;
        this.deviceProvisionedControllerProvider = provider6;
        this.broadcastDispatcherProvider = provider7;
        this.connectivityManagerProvider = provider8;
        this.telephonyManagerProvider = provider9;
        this.telephonyListenerManagerProvider = provider10;
        this.wifiManagerProvider = provider11;
        this.networkScoreManagerProvider = provider12;
        this.accessPointControllerProvider = provider13;
        this.demoModeControllerProvider = provider14;
        this.carrierConfigTrackerProvider = provider15;
        this.handlerProvider = provider16;
        this.internetDialogFactoryProvider = provider17;
        this.featureFlagsProvider = provider18;
        this.dumpManagerProvider = provider19;
    }

    public static NetworkControllerImpl_Factory create(Provider<Context> provider, Provider<Looper> provider2, Provider<Executor> provider3, Provider<SubscriptionManager> provider4, Provider<CallbackHandler> provider5, Provider<DeviceProvisionedController> provider6, Provider<BroadcastDispatcher> provider7, Provider<ConnectivityManager> provider8, Provider<TelephonyManager> provider9, Provider<TelephonyListenerManager> provider10, Provider<WifiManager> provider11, Provider<NetworkScoreManager> provider12, Provider<AccessPointControllerImpl> provider13, Provider<DemoModeController> provider14, Provider<CarrierConfigTracker> provider15, Provider<Handler> provider16, Provider<InternetDialogFactory> provider17, Provider<FeatureFlags> provider18, Provider<DumpManager> provider19) {
        return new NetworkControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19);
    }

    public final Object get() {
        Context context = this.contextProvider.get();
        SubscriptionManager subscriptionManager = this.subscriptionManagerProvider.get();
        ConnectivityManager connectivityManager = this.connectivityManagerProvider.get();
        TelephonyManager telephonyManager = this.telephonyManagerProvider.get();
        TelephonyListenerManager telephonyListenerManager = this.telephonyListenerManagerProvider.get();
        WifiManager wifiManager = this.wifiManagerProvider.get();
        NetworkScoreManager networkScoreManager = this.networkScoreManagerProvider.get();
        AccessPointControllerImpl accessPointControllerImpl = this.accessPointControllerProvider.get();
        InternetDialogFactory internetDialogFactory = this.internetDialogFactoryProvider.get();
        MobileMappings.Config readConfig = MobileMappings.Config.readConfig(context);
        NetworkControllerImpl networkControllerImpl = r2;
        Handler handler = this.handlerProvider.get();
        DataUsageController dataUsageController = r0;
        DataUsageController dataUsageController2 = new DataUsageController(context);
        MobileStatusTracker.SubscriptionDefaults subscriptionDefaults = r0;
        MobileStatusTracker.SubscriptionDefaults subscriptionDefaults2 = new MobileStatusTracker.SubscriptionDefaults();
        NetworkControllerImpl networkControllerImpl2 = new NetworkControllerImpl(context, connectivityManager, telephonyManager, telephonyListenerManager, wifiManager, networkScoreManager, subscriptionManager, readConfig, this.bgLooperProvider.get(), this.bgExecutorProvider.get(), this.callbackHandlerProvider.get(), accessPointControllerImpl, dataUsageController, subscriptionDefaults, this.deviceProvisionedControllerProvider.get(), this.broadcastDispatcherProvider.get(), this.demoModeControllerProvider.get(), this.carrierConfigTrackerProvider.get(), this.featureFlagsProvider.get(), this.dumpManagerProvider.get());
        NetworkControllerImpl networkControllerImpl3 = networkControllerImpl;
        networkControllerImpl3.mReceiverHandler.post(networkControllerImpl3.mRegisterListeners);
        networkControllerImpl3.mMainHandler = handler;
        networkControllerImpl3.mInternetDialogFactory = internetDialogFactory;
        return networkControllerImpl3;
    }
}
