package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Objects;

public final class PbapServerProfile implements LocalBluetoothProfile {
    @VisibleForTesting
    public static final String NAME = "PBAP Server";
    public static final ParcelUuid[] PBAB_CLIENT_UUIDS = {BluetoothUuid.HSP, BluetoothUuid.HFP, BluetoothUuid.PBAP_PCE};
    public BluetoothPbap mService;

    public final class PbapServiceListener implements BluetoothProfile.ServiceListener {
        public PbapServiceListener() {
        }

        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PbapServerProfile pbapServerProfile = PbapServerProfile.this;
            pbapServerProfile.mService = (BluetoothPbap) bluetoothProfile;
            Objects.requireNonNull(pbapServerProfile);
        }

        public final void onServiceDisconnected(int i) {
            Objects.requireNonNull(PbapServerProfile.this);
        }
    }

    public final boolean accessProfileEnabled() {
        return true;
    }

    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302810;
    }

    public final int getProfileId() {
        return 6;
    }

    public final String toString() {
        return NAME;
    }

    public final void finalize() {
        Log.d("PbapServerProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(6, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PbapServerProfile", "Error cleaning up PBAP proxy", th);
            }
        }
    }

    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null) {
            return 0;
        }
        return bluetoothPbap.getConnectionState(bluetoothDevice);
    }

    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap != null && !z) {
            return bluetoothPbap.setConnectionPolicy(bluetoothDevice, 0);
        }
        return false;
    }

    public PbapServerProfile(Context context) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PbapServiceListener(), 6);
    }
}
