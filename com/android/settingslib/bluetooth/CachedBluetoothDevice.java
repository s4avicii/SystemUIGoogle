package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.SharedPreferences;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.LruCache;
import androidx.recyclerview.R$dimen;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CachedBluetoothDevice implements Comparable<CachedBluetoothDevice> {
    public final CopyOnWriteArrayList mCallbacks = new CopyOnWriteArrayList();
    public long mConnectAttempted;
    public final Context mContext;
    public BluetoothDevice mDevice;
    public LruCache<String, BitmapDrawable> mDrawableCache;
    public int mGroupId;
    public final C05921 mHandler = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Objects.requireNonNull(CachedBluetoothDevice.this);
            } else if (i == 2) {
                Objects.requireNonNull(CachedBluetoothDevice.this);
            } else if (i == 21) {
                Objects.requireNonNull(CachedBluetoothDevice.this);
            } else if (i != 22) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleMessage(): unknown message : ");
                m.append(message.what);
                Log.w("CachedBluetoothDevice", m.toString());
            } else {
                Objects.requireNonNull(CachedBluetoothDevice.this);
            }
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connect to profile : ");
            m2.append(message.what);
            m2.append(" timeout, show error message !");
            Log.w("CachedBluetoothDevice", m2.toString());
            CachedBluetoothDevice.this.refresh();
        }
    };
    public long mHiSyncId;
    public boolean mIsActiveDeviceA2dp = false;
    public boolean mIsActiveDeviceHeadset = false;
    public boolean mIsActiveDeviceHearingAid = false;
    public boolean mIsActiveDeviceLeAudio = false;
    public boolean mJustDiscovered;
    public final BluetoothAdapter mLocalAdapter;
    public boolean mLocalNapRoleConnected;
    public HashSet mMemberDevices = new HashSet();
    public final Object mProfileLock = new Object();
    public final LocalBluetoothProfileManager mProfileManager;
    public final CopyOnWriteArrayList mProfiles = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mRemovedProfiles = new CopyOnWriteArrayList();
    public short mRssi;
    public CachedBluetoothDevice mSubDevice;
    public boolean mUnpairing;

    public interface Callback {
        void onDeviceAttributesChanged();
    }

    public boolean isActiveDevice(int i) {
        if (i == 1) {
            return this.mIsActiveDeviceHeadset;
        }
        if (i == 2) {
            return this.mIsActiveDeviceA2dp;
        }
        if (i == 21) {
            return this.mIsActiveDeviceHearingAid;
        }
        if (i == 22) {
            return this.mIsActiveDeviceLeAudio;
        }
        GridLayoutManager$$ExternalSyntheticOutline1.m20m("getActiveDevice: unknown profile ", i, "CachedBluetoothDevice");
        return false;
    }

    public void setProfileConnectedStatus(int i, boolean z) {
        if (i != 1 && i != 2 && i != 21 && i != 22) {
            GridLayoutManager$$ExternalSyntheticOutline1.m20m("setProfileConnectedStatus(): unknown profile id : ", i, "CachedBluetoothDevice");
        }
    }

    public final int compareTo(Object obj) {
        int i;
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        int i2 = (cachedBluetoothDevice.isConnected() ? 1 : 0) - (isConnected() ? 1 : 0);
        if (i2 != 0) {
            return i2;
        }
        int i3 = 1;
        if (cachedBluetoothDevice.getBondState() == 12) {
            i = 1;
        } else {
            i = 0;
        }
        if (getBondState() != 12) {
            i3 = 0;
        }
        int i4 = i - i3;
        if (i4 != 0) {
            return i4;
        }
        int i5 = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
        if (i5 != 0) {
            return i5;
        }
        int i6 = cachedBluetoothDevice.mRssi - this.mRssi;
        if (i6 != 0) {
            return i6;
        }
        return getName().compareTo(cachedBluetoothDevice.getName());
    }

    public final void connectDevice() {
        synchronized (this.mProfileLock) {
            if (this.mProfiles.isEmpty()) {
                Log.d("CachedBluetoothDevice", "No profiles. Maybe we will connect later for device " + this.mDevice);
                return;
            }
            this.mDevice.connect();
        }
    }

    public final void dispatchAttributesChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothDevice)) {
            return false;
        }
        return this.mDevice.equals(((CachedBluetoothDevice) obj).mDevice);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: android.bluetooth.BluetoothDevice} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void fetchActiveDevices() {
        /*
            r5 = this;
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r5.mProfileManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.A2dpProfile r0 = r0.mA2dpProfile
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x002b
            android.bluetooth.BluetoothDevice r3 = r5.mDevice
            android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
            if (r0 != 0) goto L_0x0012
            goto L_0x0024
        L_0x0012:
            r4 = 2
            java.util.List r0 = r0.getActiveDevices(r4)
            int r4 = r0.size()
            if (r4 <= 0) goto L_0x0024
            java.lang.Object r0 = r0.get(r1)
            android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
            goto L_0x0025
        L_0x0024:
            r0 = r2
        L_0x0025:
            boolean r0 = r3.equals(r0)
            r5.mIsActiveDeviceA2dp = r0
        L_0x002b:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r5.mProfileManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.HeadsetProfile r0 = r0.mHeadsetProfile
            if (r0 == 0) goto L_0x0053
            android.bluetooth.BluetoothDevice r3 = r5.mDevice
            android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
            if (r0 != 0) goto L_0x003b
            goto L_0x004d
        L_0x003b:
            r4 = 1
            java.util.List r0 = r0.getActiveDevices(r4)
            int r4 = r0.size()
            if (r4 <= 0) goto L_0x004d
            java.lang.Object r0 = r0.get(r1)
            r2 = r0
            android.bluetooth.BluetoothDevice r2 = (android.bluetooth.BluetoothDevice) r2
        L_0x004d:
            boolean r0 = r3.equals(r2)
            r5.mIsActiveDeviceHeadset = r0
        L_0x0053:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r5.mProfileManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.HearingAidProfile r0 = r0.mHearingAidProfile
            if (r0 == 0) goto L_0x0074
            android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
            if (r0 != 0) goto L_0x0066
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x006c
        L_0x0066:
            r1 = 21
            java.util.List r0 = r0.getActiveDevices(r1)
        L_0x006c:
            android.bluetooth.BluetoothDevice r1 = r5.mDevice
            boolean r0 = r0.contains(r1)
            r5.mIsActiveDeviceHearingAid = r0
        L_0x0074:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r5.mProfileManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.LeAudioProfile r0 = r0.mLeAudioProfile
            if (r0 == 0) goto L_0x0095
            android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
            if (r0 != 0) goto L_0x0087
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x008d
        L_0x0087:
            r1 = 22
            java.util.List r0 = r0.getActiveDevices(r1)
        L_0x008d:
            android.bluetooth.BluetoothDevice r1 = r5.mDevice
            boolean r0 = r0.contains(r1)
            r5.mIsActiveDeviceLeAudio = r0
        L_0x0095:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.fetchActiveDevices():void");
    }

    public final String getAddress() {
        return this.mDevice.getAddress();
    }

    public final int getBondState() {
        return this.mDevice.getBondState();
    }

    public final String getName() {
        String alias = this.mDevice.getAlias();
        if (TextUtils.isEmpty(alias)) {
            return getAddress();
        }
        return alias;
    }

    public final int hashCode() {
        return this.mDevice.getAddress().hashCode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        return r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0026 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0011  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isBusy() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mProfileLock
            monitor-enter(r0)
            java.util.concurrent.CopyOnWriteArrayList r1 = r5.mProfiles     // Catch:{ all -> 0x0032 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0032 }
        L_0x0009:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0032 }
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0026
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0032 }
            com.android.settingslib.bluetooth.LocalBluetoothProfile r2 = (com.android.settingslib.bluetooth.LocalBluetoothProfile) r2     // Catch:{ all -> 0x0032 }
            if (r2 == 0) goto L_0x001f
            android.bluetooth.BluetoothDevice r4 = r5.mDevice     // Catch:{ all -> 0x0032 }
            int r4 = r2.getConnectionStatus(r4)     // Catch:{ all -> 0x0032 }
        L_0x001f:
            if (r4 == r3) goto L_0x0024
            r2 = 3
            if (r4 != r2) goto L_0x0009
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return r3
        L_0x0026:
            int r5 = r5.getBondState()     // Catch:{ all -> 0x0032 }
            r1 = 11
            if (r5 != r1) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            r3 = r4
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return r3
        L_0x0032:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.isBusy():boolean");
    }

    public final boolean isConnected() {
        int i;
        synchronized (this.mProfileLock) {
            Iterator it = this.mProfiles.iterator();
            do {
                i = 0;
                if (!it.hasNext()) {
                    return false;
                }
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                if (localBluetoothProfile != null) {
                    i = localBluetoothProfile.getConnectionStatus(this.mDevice);
                }
            } while (i != 2);
            return true;
        }
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        Log.d("CachedBluetoothDevice", "onProfileStateChanged: profile " + localBluetoothProfile + ", device " + this.mDevice.getAnonymizedAddress() + ", newProfileState " + i);
        if (this.mLocalAdapter.getState() == 13) {
            Log.d("CachedBluetoothDevice", " BT Turninig Off...Profile conn state change ignored...");
            return;
        }
        synchronized (this.mProfileLock) {
            try {
                boolean z = true;
                boolean z2 = false;
                if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HeadsetProfile) || (localBluetoothProfile instanceof HearingAidProfile)) {
                    setProfileConnectedStatus(localBluetoothProfile.getProfileId(), false);
                    if (i != 0) {
                        if (i == 1) {
                            this.mHandler.sendEmptyMessageDelayed(localBluetoothProfile.getProfileId(), 60000);
                        } else if (i == 2) {
                            this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                        } else if (i != 3) {
                            Log.w("CachedBluetoothDevice", "onProfileStateChanged(): unknown profile state : " + i);
                        } else if (this.mHandler.hasMessages(localBluetoothProfile.getProfileId())) {
                            this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                        }
                    } else if (this.mHandler.hasMessages(localBluetoothProfile.getProfileId())) {
                        this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                        setProfileConnectedStatus(localBluetoothProfile.getProfileId(), true);
                    }
                }
                if (i == 2) {
                    if (localBluetoothProfile instanceof MapProfile) {
                        localBluetoothProfile.setEnabled(this.mDevice, true);
                    }
                    if (!this.mProfiles.contains(localBluetoothProfile)) {
                        this.mRemovedProfiles.remove(localBluetoothProfile);
                        this.mProfiles.add(localBluetoothProfile);
                        if (localBluetoothProfile instanceof PanProfile) {
                            PanProfile panProfile = (PanProfile) localBluetoothProfile;
                            BluetoothDevice bluetoothDevice = this.mDevice;
                            Objects.requireNonNull(panProfile);
                            if (panProfile.mDeviceRoleMap.containsKey(bluetoothDevice) && panProfile.mDeviceRoleMap.get(bluetoothDevice).intValue() == 1) {
                                z2 = true;
                            }
                            if (z2) {
                                this.mLocalNapRoleConnected = true;
                            }
                        }
                    }
                } else if ((localBluetoothProfile instanceof MapProfile) && i == 0) {
                    localBluetoothProfile.setEnabled(this.mDevice, false);
                } else if (this.mLocalNapRoleConnected && (localBluetoothProfile instanceof PanProfile)) {
                    PanProfile panProfile2 = (PanProfile) localBluetoothProfile;
                    BluetoothDevice bluetoothDevice2 = this.mDevice;
                    Objects.requireNonNull(panProfile2);
                    if (!panProfile2.mDeviceRoleMap.containsKey(bluetoothDevice2) || panProfile2.mDeviceRoleMap.get(bluetoothDevice2).intValue() != 1) {
                        z = false;
                    }
                    if (z && i == 0) {
                        Log.d("CachedBluetoothDevice", "Removing PanProfile from device after NAP disconnect");
                        this.mProfiles.remove(localBluetoothProfile);
                        this.mRemovedProfiles.add(localBluetoothProfile);
                        this.mLocalNapRoleConnected = false;
                    }
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        fetchActiveDevices();
    }

    public final void refresh() {
        R$dimen.postOnBackgroundThread(new BubbleStackView$$ExternalSyntheticLambda16(this, 1));
    }

    public final boolean startPairing() {
        if (this.mLocalAdapter.isDiscovering()) {
            this.mLocalAdapter.cancelDiscovery();
        }
        if (!this.mDevice.createBond()) {
            return false;
        }
        return true;
    }

    public final void switchMemberDeviceContent(CachedBluetoothDevice cachedBluetoothDevice, CachedBluetoothDevice cachedBluetoothDevice2) {
        BluetoothDevice bluetoothDevice = this.mDevice;
        short s = this.mRssi;
        boolean z = this.mJustDiscovered;
        this.mDevice = cachedBluetoothDevice2.mDevice;
        this.mRssi = cachedBluetoothDevice2.mRssi;
        this.mJustDiscovered = cachedBluetoothDevice2.mJustDiscovered;
        this.mMemberDevices.add(cachedBluetoothDevice);
        this.mMemberDevices.remove(cachedBluetoothDevice2);
        cachedBluetoothDevice2.mDevice = bluetoothDevice;
        cachedBluetoothDevice2.mRssi = s;
        cachedBluetoothDevice2.mJustDiscovered = z;
        fetchActiveDevices();
    }

    public final void switchSubDeviceContent() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        short s = this.mRssi;
        boolean z = this.mJustDiscovered;
        CachedBluetoothDevice cachedBluetoothDevice = this.mSubDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mRssi = cachedBluetoothDevice.mRssi;
        this.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
        cachedBluetoothDevice.mDevice = bluetoothDevice;
        cachedBluetoothDevice.mRssi = s;
        cachedBluetoothDevice.mJustDiscovered = z;
        fetchActiveDevices();
    }

    public final String toString() {
        return this.mDevice.toString();
    }

    public final boolean updateProfiles() {
        ParcelUuid[] uuids;
        ParcelUuid[] uuids2 = this.mDevice.getUuids();
        if (uuids2 == null || (uuids = this.mLocalAdapter.getUuids()) == null) {
            return false;
        }
        if (this.mDevice.getBondState() == 12 && BluetoothUuid.containsAnyUuid(this.mDevice.getUuids(), PbapServerProfile.PBAB_CLIENT_UUIDS)) {
            BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
            if (this.mDevice.getPhonebookAccessPermission() == 0) {
                if (bluetoothClass != null && (bluetoothClass.getDeviceClass() == 1032 || bluetoothClass.getDeviceClass() == 1028)) {
                    EventLog.writeEvent(1397638484, new Object[]{"138529441", -1, ""});
                }
                this.mDevice.setPhonebookAccessPermission(2);
            }
        }
        synchronized (this.mProfileLock) {
            this.mProfileManager.updateProfiles(uuids2, uuids, this.mProfiles, this.mRemovedProfiles, this.mLocalNapRoleConnected, this.mDevice);
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("updating profiles for ");
        m.append(this.mDevice.getAnonymizedAddress());
        Log.d("CachedBluetoothDevice", m.toString());
        BluetoothClass bluetoothClass2 = this.mDevice.getBluetoothClass();
        if (bluetoothClass2 != null) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Class: ");
            m2.append(bluetoothClass2.toString());
            Log.v("CachedBluetoothDevice", m2.toString());
        }
        Log.v("CachedBluetoothDevice", "UUID:");
        for (ParcelUuid parcelUuid : uuids2) {
            Log.v("CachedBluetoothDevice", "  " + parcelUuid);
        }
        return true;
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        updateProfiles();
        fetchActiveDevices();
        SharedPreferences sharedPreferences = context.getSharedPreferences("bluetooth_phonebook_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getPhonebookAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setPhonebookAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setPhonebookAccessPermission(2);
                }
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(this.mDevice.getAddress());
            edit.commit();
        }
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("bluetooth_message_permission", 0);
        if (sharedPreferences2.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getMessageAccessPermission() == 0) {
                int i2 = sharedPreferences2.getInt(this.mDevice.getAddress(), 0);
                if (i2 == 1) {
                    this.mDevice.setMessageAccessPermission(1);
                } else if (i2 == 2) {
                    this.mDevice.setMessageAccessPermission(2);
                }
            }
            SharedPreferences.Editor edit2 = sharedPreferences2.edit();
            edit2.remove(this.mDevice.getAddress());
            edit2.commit();
        }
        dispatchAttributesChanged();
        this.mHiSyncId = 0;
        this.mGroupId = -1;
        this.mDrawableCache = new LruCache<String, BitmapDrawable>(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8) {
            public final int sizeOf(Object obj, Object obj2) {
                String str = (String) obj;
                return ((BitmapDrawable) obj2).getBitmap().getByteCount() / 1024;
            }
        };
        this.mUnpairing = false;
    }

    public final void connect$1() {
        boolean z;
        if (getBondState() == 10) {
            startPairing();
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mConnectAttempted = SystemClock.elapsedRealtime();
            connectDevice();
        }
    }

    public final void unpair() {
        BluetoothDevice bluetoothDevice;
        int bondState = getBondState();
        if (bondState == 11) {
            this.mDevice.cancelBondProcess();
        }
        if (bondState != 10 && (bluetoothDevice = this.mDevice) != null) {
            this.mUnpairing = true;
            if (bluetoothDevice.removeBond()) {
                this.mDrawableCache.evictAll();
                StringBuilder sb = new StringBuilder();
                sb.append("Command sent successfully:REMOVE_BOND ");
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Address:");
                m.append(this.mDevice);
                sb.append(m.toString());
                Log.d("CachedBluetoothDevice", sb.toString());
            }
        }
    }
}
