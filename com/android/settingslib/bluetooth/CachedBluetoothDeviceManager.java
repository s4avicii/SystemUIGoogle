package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public final class CachedBluetoothDeviceManager {
    public final LocalBluetoothManager mBtManager;
    @VisibleForTesting
    public final List<CachedBluetoothDevice> mCachedDevices;
    public Context mContext;
    @VisibleForTesting
    public CsipDeviceManager mCsipDeviceManager;
    @VisibleForTesting
    public HearingAidDeviceManager mHearingAidDeviceManager;

    public final synchronized CachedBluetoothDevice findDevice(BluetoothDevice bluetoothDevice) {
        for (CachedBluetoothDevice next : this.mCachedDevices) {
            Objects.requireNonNull(next);
            if (next.mDevice.equals(bluetoothDevice)) {
                return next;
            }
            HashSet<CachedBluetoothDevice> hashSet = next.mMemberDevices;
            if (!hashSet.isEmpty()) {
                for (CachedBluetoothDevice cachedBluetoothDevice : hashSet) {
                    Objects.requireNonNull(cachedBluetoothDevice);
                    if (cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                        return cachedBluetoothDevice;
                    }
                }
            }
            CachedBluetoothDevice cachedBluetoothDevice2 = next.mSubDevice;
            if (cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice2;
            }
        }
        return null;
    }

    public final synchronized ArrayList getCachedDevicesCopy() {
        return new ArrayList(this.mCachedDevices);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.settingslib.bluetooth.CachedBluetoothDevice addDevice(android.bluetooth.BluetoothDevice r10) {
        /*
            r9 = this;
            com.android.settingslib.bluetooth.LocalBluetoothManager r0 = r9.mBtManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r0.mProfileManager
            monitor-enter(r9)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = r9.findDevice(r10)     // Catch:{ all -> 0x0099 }
            if (r1 != 0) goto L_0x0097
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = new com.android.settingslib.bluetooth.CachedBluetoothDevice     // Catch:{ all -> 0x0099 }
            android.content.Context r2 = r9.mContext     // Catch:{ all -> 0x0099 }
            r1.<init>(r2, r0, r10)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.CsipDeviceManager r10 = r9.mCsipDeviceManager     // Catch:{ all -> 0x0099 }
            r10.initCsipDeviceIfNeeded(r1)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.HearingAidDeviceManager r10 = r9.mHearingAidDeviceManager     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0099 }
            android.bluetooth.BluetoothDevice r0 = r1.mDevice     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.LocalBluetoothManager r10 = r10.mBtManager     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r10 = r10.mProfileManager     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.HearingAidProfile r10 = r10.mHearingAidProfile     // Catch:{ all -> 0x0099 }
            r2 = 0
            if (r10 == 0) goto L_0x003d
            android.bluetooth.BluetoothHearingAid r10 = r10.mService     // Catch:{ all -> 0x0099 }
            if (r10 == 0) goto L_0x003d
            if (r0 != 0) goto L_0x0038
            goto L_0x003d
        L_0x0038:
            long r4 = r10.getHiSyncId(r0)     // Catch:{ all -> 0x0099 }
            goto L_0x003e
        L_0x003d:
            r4 = r2
        L_0x003e:
            int r10 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r0 = 1
            r6 = 0
            if (r10 == 0) goto L_0x0046
            r10 = r0
            goto L_0x0047
        L_0x0046:
            r10 = r6
        L_0x0047:
            if (r10 == 0) goto L_0x004b
            r1.mHiSyncId = r4     // Catch:{ all -> 0x0099 }
        L_0x004b:
            com.android.settingslib.bluetooth.CsipDeviceManager r10 = r9.mCsipDeviceManager     // Catch:{ all -> 0x0099 }
            boolean r10 = r10.setMemberDeviceIfNeeded(r1)     // Catch:{ all -> 0x0099 }
            if (r10 != 0) goto L_0x0097
            com.android.settingslib.bluetooth.HearingAidDeviceManager r10 = r9.mHearingAidDeviceManager     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0099 }
            long r4 = r1.mHiSyncId     // Catch:{ all -> 0x0099 }
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0060
            r2 = r0
            goto L_0x0061
        L_0x0060:
            r2 = r6
        L_0x0061:
            if (r2 == 0) goto L_0x0085
            java.util.List<com.android.settingslib.bluetooth.CachedBluetoothDevice> r2 = r10.mCachedDevices     // Catch:{ all -> 0x0099 }
            int r2 = r2.size()     // Catch:{ all -> 0x0099 }
        L_0x0069:
            int r2 = r2 + -1
            if (r2 < 0) goto L_0x007f
            java.util.List<com.android.settingslib.bluetooth.CachedBluetoothDevice> r3 = r10.mCachedDevices     // Catch:{ all -> 0x0099 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.CachedBluetoothDevice r3 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r3     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r3)     // Catch:{ all -> 0x0099 }
            long r7 = r3.mHiSyncId     // Catch:{ all -> 0x0099 }
            int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0069
            goto L_0x0080
        L_0x007f:
            r3 = 0
        L_0x0080:
            if (r3 == 0) goto L_0x0085
            r3.mSubDevice = r1     // Catch:{ all -> 0x0099 }
            goto L_0x0086
        L_0x0085:
            r0 = r6
        L_0x0086:
            if (r0 != 0) goto L_0x0097
            java.util.List<com.android.settingslib.bluetooth.CachedBluetoothDevice> r10 = r9.mCachedDevices     // Catch:{ all -> 0x0099 }
            r10.add(r1)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.LocalBluetoothManager r10 = r9.mBtManager     // Catch:{ all -> 0x0099 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0099 }
            com.android.settingslib.bluetooth.BluetoothEventManager r10 = r10.mEventManager     // Catch:{ all -> 0x0099 }
            r10.dispatchDeviceAdded(r1)     // Catch:{ all -> 0x0099 }
        L_0x0097:
            monitor-exit(r9)     // Catch:{ all -> 0x0099 }
            return r1
        L_0x0099:
            r10 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0099 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDeviceManager.addDevice(android.bluetooth.BluetoothDevice):com.android.settingslib.bluetooth.CachedBluetoothDevice");
    }

    public final void clearNonBondedSubDevices() {
        int size = this.mCachedDevices.size();
        while (true) {
            size--;
            if (size >= 0) {
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                Objects.requireNonNull(cachedBluetoothDevice);
                HashSet hashSet = cachedBluetoothDevice.mMemberDevices;
                if (!hashSet.isEmpty()) {
                    for (Object obj : hashSet.toArray()) {
                        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj;
                        Objects.requireNonNull(cachedBluetoothDevice2);
                        if (cachedBluetoothDevice2.mDevice.getBondState() == 10) {
                            cachedBluetoothDevice.mMemberDevices.remove(cachedBluetoothDevice2);
                        }
                    }
                    return;
                }
                CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
                if (cachedBluetoothDevice3 != null && cachedBluetoothDevice3.mDevice.getBondState() == 10) {
                    cachedBluetoothDevice.mSubDevice = null;
                }
            } else {
                return;
            }
        }
    }

    public CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        this.mCachedDevices = arrayList;
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mHearingAidDeviceManager = new HearingAidDeviceManager(localBluetoothManager, arrayList);
        this.mCsipDeviceManager = new CsipDeviceManager(localBluetoothManager, arrayList);
    }
}
