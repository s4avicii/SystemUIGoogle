package com.android.systemui.statusbar.policy.dagger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.UserManager;
import androidx.lifecycle.LifecycleRegistry;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.android.wifitrackerlib.WifiTrackerInjector;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class StatusBarPolicyModule_ProvideAccessPointControllerImplFactory implements Factory<AccessPointControllerImpl> {
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> wifiPickerTrackerFactoryProvider;

    /* JADX WARNING: type inference failed for: r12v0, types: [java.time.Clock, com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$WifiPickerTrackerFactory$1] */
    public final Object get() {
        WifiPickerTracker wifiPickerTracker;
        AccessPointControllerImpl accessPointControllerImpl;
        AccessPointControllerImpl accessPointControllerImpl2 = new AccessPointControllerImpl(this.userManagerProvider.get(), this.userTrackerProvider.get(), this.mainExecutorProvider.get(), this.wifiPickerTrackerFactoryProvider.get());
        if (accessPointControllerImpl2.mWifiPickerTracker != null) {
            return accessPointControllerImpl2;
        }
        AccessPointControllerImpl.WifiPickerTrackerFactory wifiPickerTrackerFactory = accessPointControllerImpl2.mWifiPickerTrackerFactory;
        LifecycleRegistry lifecycleRegistry = accessPointControllerImpl2.mLifecycle;
        Objects.requireNonNull(wifiPickerTrackerFactory);
        WifiManager wifiManager = wifiPickerTrackerFactory.mWifiManager;
        if (wifiManager == null) {
            wifiPickerTracker = null;
            accessPointControllerImpl = accessPointControllerImpl2;
        } else {
            Context context = wifiPickerTrackerFactory.mContext;
            ConnectivityManager connectivityManager = wifiPickerTrackerFactory.mConnectivityManager;
            Handler handler = wifiPickerTrackerFactory.mMainHandler;
            Handler handler2 = wifiPickerTrackerFactory.mWorkerHandler;
            ? r12 = wifiPickerTrackerFactory.mClock;
            accessPointControllerImpl = accessPointControllerImpl2;
            wifiPickerTracker = new WifiPickerTracker(new WifiTrackerInjector(context), lifecycleRegistry, context, wifiManager, connectivityManager, handler, handler2, r12, WifiTracker.MAX_SCAN_RESULT_AGE_MILLIS, 10000, accessPointControllerImpl);
        }
        accessPointControllerImpl.mWifiPickerTracker = wifiPickerTracker;
        return accessPointControllerImpl;
    }

    public StatusBarPolicyModule_ProvideAccessPointControllerImplFactory(Provider<UserManager> provider, Provider<UserTracker> provider2, Provider<Executor> provider3, Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> provider4) {
        this.userManagerProvider = provider;
        this.userTrackerProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.wifiPickerTrackerFactoryProvider = provider4;
    }
}
