package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.exifinterface.media.C0155xe8491b12;
import com.android.internal.annotations.GuardedBy;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.BluetoothController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

public final class BluetoothControllerImpl implements BluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener {
    public static final boolean DEBUG = Log.isLoggable("BluetoothController", 3);
    public boolean mAudioProfileOnly;
    public final WeakHashMap<CachedBluetoothDevice, Object> mCachedState = new WeakHashMap<>();
    @GuardedBy({"mConnectedDevices"})
    public final ArrayList mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public final int mCurrentUser;
    public boolean mEnabled;
    public final C1601H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public int mState;
    public final UserManager mUserManager;

    /* renamed from: com.android.systemui.statusbar.policy.BluetoothControllerImpl$H */
    public final class C1601H extends Handler {
        public final ArrayList<BluetoothController.Callback> mCallbacks = new ArrayList<>();

        public C1601H(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Iterator<BluetoothController.Callback> it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().onBluetoothDevicesChanged();
                }
            } else if (i == 2) {
                Iterator<BluetoothController.Callback> it2 = this.mCallbacks.iterator();
                while (it2.hasNext()) {
                    boolean z = BluetoothControllerImpl.this.mEnabled;
                    it2.next().onBluetoothStateChange();
                }
            } else if (i == 3) {
                this.mCallbacks.add((BluetoothController.Callback) message.obj);
            } else if (i == 4) {
                this.mCallbacks.remove((BluetoothController.Callback) message.obj);
            }
        }
    }

    public final void onServiceDisconnected() {
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "DISCONNECTED";
        }
        if (i == 1) {
            return "CONNECTING";
        }
        if (i == 2) {
            return "CONNECTED";
        }
        if (i != 3) {
            return C0155xe8491b12.m16m("UNKNOWN(", i, ")");
        }
        return "DISCONNECTING";
    }

    public final void addCallback(Object obj) {
        this.mHandler.obtainMessage(3, (BluetoothController.Callback) obj).sendToTarget();
        this.mHandler.sendEmptyMessage(2);
    }

    public final boolean canConfigBluetooth() {
        if (this.mUserManager.hasUserRestriction("no_config_bluetooth", UserHandle.of(this.mCurrentUser)) || this.mUserManager.hasUserRestriction("no_bluetooth", UserHandle.of(this.mCurrentUser))) {
            return false;
        }
        return true;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("BluetoothController state:");
        printWriter.print("  mLocalBluetoothManager=");
        printWriter.println(this.mLocalBluetoothManager);
        if (this.mLocalBluetoothManager != null) {
            printWriter.print("  mEnabled=");
            printWriter.println(this.mEnabled);
            printWriter.print("  mConnectionState=");
            printWriter.println(stateToString(this.mConnectionState));
            printWriter.print("  mAudioProfileOnly=");
            printWriter.println(this.mAudioProfileOnly);
            printWriter.print("  mIsActive=");
            printWriter.println(this.mIsActive);
            printWriter.print("  mConnectedDevices=");
            printWriter.println(getConnectedDevices());
            printWriter.print("  mCallbacks.size=");
            printWriter.println(this.mHandler.mCallbacks.size());
            printWriter.println("  Bluetooth Devices:");
            for (CachedBluetoothDevice cachedBluetoothDevice : getDevices()) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    ");
                m.append(cachedBluetoothDevice.getName() + " " + cachedBluetoothDevice.getBondState() + " " + cachedBluetoothDevice.isConnected());
                printWriter.println(m.toString());
            }
        }
    }

    public final String getConnectedDeviceName() {
        synchronized (this.mConnectedDevices) {
            if (this.mConnectedDevices.size() != 1) {
                return null;
            }
            String name = ((CachedBluetoothDevice) this.mConnectedDevices.get(0)).getName();
            return name;
        }
    }

    public final ArrayList getConnectedDevices() {
        ArrayList arrayList;
        synchronized (this.mConnectedDevices) {
            arrayList = new ArrayList(this.mConnectedDevices);
        }
        return arrayList;
    }

    public final ArrayList getDevices() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            return localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy();
        }
        return null;
    }

    public final boolean isBluetoothConnected() {
        if (this.mConnectionState == 2) {
            return true;
        }
        return false;
    }

    public final boolean isBluetoothConnecting() {
        if (this.mConnectionState == 1) {
            return true;
        }
        return false;
    }

    public final boolean isBluetoothSupported() {
        if (this.mLocalBluetoothManager != null) {
            return true;
        }
        return false;
    }

    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ACLConnectionStateChanged=");
            m.append(cachedBluetoothDevice.getAddress());
            m.append(" ");
            m.append(stateToString(i));
            Log.d("BluetoothController", m.toString());
        }
        this.mCachedState.remove(cachedBluetoothDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ActiveDeviceChanged=");
            m.append(cachedBluetoothDevice.getAddress());
            m.append(" profileId=");
            m.append(i);
            Log.d("BluetoothController", m.toString());
        }
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : getDevices()) {
            boolean z2 = true;
            if (!cachedBluetoothDevice2.isActiveDevice(1) && !cachedBluetoothDevice2.isActiveDevice(2) && !cachedBluetoothDevice2.isActiveDevice(21)) {
                z2 = false;
            }
            z |= z2;
        }
        if (this.mIsActive != z) {
            this.mIsActive = z;
            this.mHandler.sendEmptyMessage(2);
        }
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onBluetoothStateChanged(int i) {
        boolean z;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BluetoothStateChanged=");
            m.append(stateToString(i));
            Log.d("BluetoothController", m.toString());
        }
        if (i == 12 || i == 11) {
            z = true;
        } else {
            z = false;
        }
        this.mEnabled = z;
        this.mState = i;
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ConnectionStateChanged=");
            m.append(cachedBluetoothDevice.getAddress());
            m.append(" ");
            m.append(stateToString(i));
            Log.d("BluetoothController", m.toString());
        }
        this.mCachedState.remove(cachedBluetoothDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DeviceAdded=");
            m.append(cachedBluetoothDevice.getAddress());
            Log.d("BluetoothController", m.toString());
        }
        cachedBluetoothDevice.mCallbacks.add(this);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onDeviceAttributesChanged() {
        if (DEBUG) {
            Log.d("BluetoothController", "DeviceAttributesChanged");
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DeviceBondStateChanged=");
            m.append(cachedBluetoothDevice.getAddress());
            Log.d("BluetoothController", m.toString());
        }
        this.mCachedState.remove(cachedBluetoothDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DeviceDeleted=");
            m.append(cachedBluetoothDevice.getAddress());
            Log.d("BluetoothController", m.toString());
        }
        this.mCachedState.remove(cachedBluetoothDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ProfileConnectionStateChanged=");
            m.append(cachedBluetoothDevice.getAddress());
            m.append(" ");
            m.append(stateToString(i));
            m.append(" profileId=");
            m.append(i2);
            Log.d("BluetoothController", m.toString());
        }
        this.mCachedState.remove(cachedBluetoothDevice);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void removeCallback(Object obj) {
        this.mHandler.obtainMessage(4, (BluetoothController.Callback) obj).sendToTarget();
    }

    public final void setBluetoothEnabled(boolean z) {
        boolean z2;
        int i;
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            Objects.requireNonNull(localBluetoothAdapter);
            if (z) {
                z2 = localBluetoothAdapter.mAdapter.enable();
            } else {
                z2 = localBluetoothAdapter.mAdapter.disable();
            }
            if (z2) {
                if (z) {
                    i = 11;
                } else {
                    i = 13;
                }
                localBluetoothAdapter.setBluetoothStateInt(i);
            } else if (localBluetoothAdapter.mAdapter.getState() != localBluetoothAdapter.mState) {
                localBluetoothAdapter.setBluetoothStateInt(localBluetoothAdapter.mAdapter.getState());
            }
        }
    }

    public final void updateConnected() {
        boolean z;
        boolean z2;
        int i;
        int i2;
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        Objects.requireNonNull(localBluetoothManager);
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        Objects.requireNonNull(localBluetoothAdapter);
        int connectionState = localBluetoothAdapter.mAdapter.getConnectionState();
        ArrayList arrayList = new ArrayList();
        Iterator it = getDevices().iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
            Objects.requireNonNull(cachedBluetoothDevice);
            synchronized (cachedBluetoothDevice.mProfileLock) {
                try {
                    Iterator it2 = new ArrayList(cachedBluetoothDevice.mProfiles).iterator();
                    i = 0;
                    while (it2.hasNext()) {
                        LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it2.next();
                        if (localBluetoothProfile != null) {
                            i2 = localBluetoothProfile.getConnectionStatus(cachedBluetoothDevice.mDevice);
                        } else {
                            i2 = 0;
                        }
                        if (i2 > i) {
                            i = i2;
                        }
                    }
                } finally {
                    while (true) {
                    }
                }
            }
            if (i > connectionState) {
                connectionState = i;
            }
            if (cachedBluetoothDevice.isConnected()) {
                arrayList.add(cachedBluetoothDevice);
            }
        }
        if (arrayList.isEmpty() && connectionState == 2) {
            connectionState = 0;
        }
        synchronized (this.mConnectedDevices) {
            this.mConnectedDevices.clear();
            this.mConnectedDevices.addAll(arrayList);
        }
        if (connectionState != this.mConnectionState) {
            this.mConnectionState = connectionState;
            this.mHandler.sendEmptyMessage(2);
        }
        boolean z3 = false;
        boolean z4 = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : getDevices()) {
            Objects.requireNonNull(cachedBluetoothDevice2);
            Iterator it3 = new ArrayList(cachedBluetoothDevice2.mProfiles).iterator();
            while (it3.hasNext()) {
                LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it3.next();
                int profileId = localBluetoothProfile2.getProfileId();
                if (localBluetoothProfile2.getConnectionStatus(cachedBluetoothDevice2.mDevice) == 2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (profileId == 1 || profileId == 2 || profileId == 21) {
                    z3 |= z2;
                } else {
                    z4 |= z2;
                }
            }
        }
        if (z3 && !z4) {
            z = true;
        }
        if (z != this.mAudioProfileOnly) {
            this.mAudioProfileOnly = z;
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public BluetoothControllerImpl(Context context, DumpManager dumpManager, Looper looper, Looper looper2, LocalBluetoothManager localBluetoothManager) {
        int i;
        this.mLocalBluetoothManager = localBluetoothManager;
        new Handler(looper);
        this.mHandler = new C1601H(looper2);
        if (localBluetoothManager != null) {
            BluetoothEventManager bluetoothEventManager = localBluetoothManager.mEventManager;
            Objects.requireNonNull(bluetoothEventManager);
            bluetoothEventManager.mCallbacks.add(this);
            LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
            Objects.requireNonNull(localBluetoothProfileManager);
            localBluetoothProfileManager.mServiceListeners.add(this);
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            Objects.requireNonNull(localBluetoothAdapter);
            synchronized (localBluetoothAdapter) {
                if (localBluetoothAdapter.mAdapter.getState() != localBluetoothAdapter.mState) {
                    localBluetoothAdapter.setBluetoothStateInt(localBluetoothAdapter.mAdapter.getState());
                }
                i = localBluetoothAdapter.mState;
            }
            onBluetoothStateChanged(i);
        }
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mCurrentUser = ActivityManager.getCurrentUser();
        dumpManager.registerDumpable("BluetoothController", this);
    }

    public final void onServiceConnected() {
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    public final int getBluetoothState() {
        return this.mState;
    }

    public final boolean isBluetoothAudioActive() {
        return this.mIsActive;
    }

    public final boolean isBluetoothAudioProfileOnly() {
        return this.mAudioProfileOnly;
    }

    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }
}
