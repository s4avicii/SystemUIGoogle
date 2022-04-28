package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;
import java.util.Objects;

public final class HfpClientProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public BluetoothHeadsetClient mService;

    public final class HfpClientServiceListener implements BluetoothProfile.ServiceListener {
        public HfpClientServiceListener() {
        }

        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHeadsetClient bluetoothHeadsetClient = (BluetoothHeadsetClient) bluetoothProfile;
            HfpClientProfile.this.mService = bluetoothHeadsetClient;
            List connectedDevices = bluetoothHeadsetClient.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HfpClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("HfpClientProfile", "HfpClient profile found new device: " + bluetoothDevice);
                    findDevice = HfpClientProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                findDevice.onProfileStateChanged(HfpClientProfile.this, 2);
                findDevice.refresh();
            }
            Objects.requireNonNull(HfpClientProfile.this);
        }

        public final void onServiceDisconnected(int i) {
            Objects.requireNonNull(HfpClientProfile.this);
        }
    }

    public final boolean accessProfileEnabled() {
        return true;
    }

    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302330;
    }

    public final int getProfileId() {
        return 16;
    }

    public final String toString() {
        return "HEADSET_CLIENT";
    }

    static {
        ParcelUuid parcelUuid = BluetoothUuid.HSP_AG;
        ParcelUuid parcelUuid2 = BluetoothUuid.HFP_AG;
    }

    public final void finalize() {
        Log.d("HfpClientProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(16, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HfpClientProfile", "Error cleaning up HfpClient proxy", th);
            }
        }
    }

    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return 0;
        }
        return bluetoothHeadsetClient.getConnectionState(bluetoothDevice);
    }

    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mService;
        if (bluetoothHeadsetClient == null) {
            return false;
        }
        if (!z) {
            return bluetoothHeadsetClient.setConnectionPolicy(bluetoothDevice, 0);
        }
        if (bluetoothHeadsetClient.getConnectionPolicy(bluetoothDevice) < 100) {
            return this.mService.setConnectionPolicy(bluetoothDevice, 100);
        }
        return false;
    }

    public HfpClientProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HfpClientServiceListener(), 16);
    }
}
