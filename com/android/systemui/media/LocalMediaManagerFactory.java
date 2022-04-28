package com.android.systemui.media;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: LocalMediaManagerFactory.kt */
public final class LocalMediaManagerFactory {
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context2, LocalBluetoothManager localBluetoothManager2) {
        this.context = context2;
        this.localBluetoothManager = localBluetoothManager2;
    }
}
