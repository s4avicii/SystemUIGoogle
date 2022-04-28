package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.os.Looper;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainLooperFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BluetoothControllerImpl_Factory implements Factory<BluetoothControllerImpl> {
    public final Provider<Looper> bgLooperProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<LocalBluetoothManager> localBluetoothManagerProvider;
    public final Provider<Looper> mainLooperProvider;

    public BluetoothControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory = GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
        this.bgLooperProvider = provider3;
        this.mainLooperProvider = globalConcurrencyModule_ProvideMainLooperFactory;
        this.localBluetoothManagerProvider = provider4;
    }

    public final Object get() {
        return new BluetoothControllerImpl(this.contextProvider.get(), this.dumpManagerProvider.get(), this.bgLooperProvider.get(), this.mainLooperProvider.get(), this.localBluetoothManagerProvider.get());
    }
}
