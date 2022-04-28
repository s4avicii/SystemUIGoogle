package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;

@Deprecated
public final class LocalBluetoothAdapter {
    public static LocalBluetoothAdapter sInstance;
    public final BluetoothAdapter mAdapter;
    public LocalBluetoothProfileManager mProfileManager;
    public int mState = Integer.MIN_VALUE;

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        r1 = r1.mProfileManager;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        java.util.Objects.requireNonNull(r1);
        r1.updateLocalProfiles();
        r1.mEventManager.readPairedDevices();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000c, code lost:
        if (r2 != 12) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setBluetoothStateInt(int r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            int r0 = r1.mState     // Catch:{ all -> 0x001e }
            if (r0 != r2) goto L_0x0007
            monitor-exit(r1)     // Catch:{ all -> 0x001e }
            return
        L_0x0007:
            r1.mState = r2     // Catch:{ all -> 0x001e }
            monitor-exit(r1)     // Catch:{ all -> 0x001e }
            r0 = 12
            if (r2 != r0) goto L_0x001d
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r1.mProfileManager
            if (r1 == 0) goto L_0x001d
            java.util.Objects.requireNonNull(r1)
            r1.updateLocalProfiles()
            com.android.settingslib.bluetooth.BluetoothEventManager r1 = r1.mEventManager
            r1.readPairedDevices()
        L_0x001d:
            return
        L_0x001e:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001e }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.LocalBluetoothAdapter.setBluetoothStateInt(int):void");
    }

    public LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.mAdapter = bluetoothAdapter;
    }
}
