package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class PanProfile implements LocalBluetoothProfile {
    public final HashMap<BluetoothDevice, Integer> mDeviceRoleMap = new HashMap<>();
    public BluetoothPan mService;

    public final class PanServiceListener implements BluetoothProfile.ServiceListener {
        public PanServiceListener() {
        }

        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PanProfile panProfile = PanProfile.this;
            panProfile.mService = (BluetoothPan) bluetoothProfile;
            Objects.requireNonNull(panProfile);
        }

        public final void onServiceDisconnected(int i) {
            Objects.requireNonNull(PanProfile.this);
        }
    }

    public final boolean accessProfileEnabled() {
        return true;
    }

    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302334;
    }

    public final int getProfileId() {
        return 5;
    }

    public final String toString() {
        return "PAN";
    }

    public final void finalize() {
        Log.d("PanProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(5, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PanProfile", "Error cleaning up PAN proxy", th);
            }
        }
    }

    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return 0;
        }
        return bluetoothPan.getConnectionState(bluetoothDevice);
    }

    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return false;
        }
        if (!z) {
            return bluetoothPan.setConnectionPolicy(bluetoothDevice, 0);
        }
        List<BluetoothDevice> connectedDevices = bluetoothPan.getConnectedDevices();
        if (connectedDevices != null) {
            for (BluetoothDevice connectionPolicy : connectedDevices) {
                this.mService.setConnectionPolicy(connectionPolicy, 0);
            }
        }
        return this.mService.setConnectionPolicy(bluetoothDevice, 100);
    }

    public PanProfile(Context context) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PanServiceListener(), 5);
    }
}
