package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

public final class OppProfile implements LocalBluetoothProfile {
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        return 0;
    }

    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    public final int getProfileId() {
        return 20;
    }

    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        return false;
    }

    public final String toString() {
        return "OPP";
    }
}
