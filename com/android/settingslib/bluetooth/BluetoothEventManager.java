package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.util.ArrayUtils;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.keyboard.KeyboardUI;
import com.android.systemui.plugins.FalsingManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BluetoothEventManager {
    public static final boolean DEBUG = Log.isLoggable("BluetoothEventManager", 3);
    public final IntentFilter mAdapterIntentFilter;
    public final BluetoothBroadcastReceiver mBroadcastReceiver = new BluetoothBroadcastReceiver();
    public final CopyOnWriteArrayList mCallbacks = new CopyOnWriteArrayList();
    public final Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final HashMap mHandlerMap;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final BluetoothBroadcastReceiver mProfileBroadcastReceiver = new BluetoothBroadcastReceiver();
    public final IntentFilter mProfileIntentFilter;
    public final android.os.Handler mReceiverHandler;
    public final UserHandle mUserHandle;

    public class AclStateChangedHandler implements Handler {
        public AclStateChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            boolean z;
            if (bluetoothDevice == null) {
                Log.w("BluetoothEventManager", "AclStateChangedHandler: device is null");
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            Objects.requireNonNull(cachedBluetoothDeviceManager);
            synchronized (cachedBluetoothDeviceManager) {
                Iterator<CachedBluetoothDevice> it = cachedBluetoothDeviceManager.mCachedDevices.iterator();
                loop0:
                while (true) {
                    i = 0;
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    CachedBluetoothDevice next = it.next();
                    Objects.requireNonNull(next);
                    if (!next.mDevice.equals(bluetoothDevice)) {
                        HashSet<CachedBluetoothDevice> hashSet = next.mMemberDevices;
                        z = true;
                        if (hashSet.isEmpty()) {
                            CachedBluetoothDevice cachedBluetoothDevice = next.mSubDevice;
                            if (cachedBluetoothDevice != null && cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                                break;
                            }
                        } else {
                            for (CachedBluetoothDevice cachedBluetoothDevice2 : hashSet) {
                                Objects.requireNonNull(cachedBluetoothDevice2);
                                if (cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                                    break loop0;
                                }
                            }
                            continue;
                        }
                    }
                }
            }
            if (!z) {
                String action = intent.getAction();
                if (action == null) {
                    Log.w("BluetoothEventManager", "AclStateChangedHandler: action is null");
                    return;
                }
                CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("BluetoothEventManager", "AclStateChangedHandler: activeDevice is null");
                    return;
                }
                if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    i = 2;
                } else if (!action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    MotionLayout$$ExternalSyntheticOutline0.m9m("ActiveDeviceChangedHandler: unknown action ", action, "BluetoothEventManager");
                    return;
                }
                BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
                Objects.requireNonNull(bluetoothEventManager);
                Iterator it2 = bluetoothEventManager.mCallbacks.iterator();
                while (it2.hasNext()) {
                    ((BluetoothCallback) it2.next()).onAclConnectionStateChanged(findDevice, i);
                }
            }
        }
    }

    public class ActiveDeviceChangedHandler implements Handler {
        public ActiveDeviceChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            String action = intent.getAction();
            if (action == null) {
                Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: action is null");
                return;
            }
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 2;
            } else if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 1;
            } else if (action.equals("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 21;
            } else if (action.equals("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED")) {
                i = 22;
            } else {
                MotionLayout$$ExternalSyntheticOutline0.m9m("ActiveDeviceChangedHandler: unknown action ", action, "BluetoothEventManager");
                return;
            }
            BluetoothEventManager.this.dispatchActiveDeviceChanged(findDevice, i);
        }
    }

    public class AdapterStateChangedHandler implements Handler {
        public AdapterStateChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            BluetoothEventManager.this.mLocalAdapter.setBluetoothStateInt(intExtra);
            Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onBluetoothStateChanged(intExtra);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            Objects.requireNonNull(cachedBluetoothDeviceManager);
            synchronized (cachedBluetoothDeviceManager) {
                if (intExtra == 13) {
                    int size = cachedBluetoothDeviceManager.mCachedDevices.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                        Objects.requireNonNull(cachedBluetoothDevice);
                        HashSet<CachedBluetoothDevice> hashSet = cachedBluetoothDevice.mMemberDevices;
                        if (!hashSet.isEmpty()) {
                            for (CachedBluetoothDevice cachedBluetoothDevice2 : hashSet) {
                                if (cachedBluetoothDevice2.getBondState() != 12) {
                                    cachedBluetoothDevice.mMemberDevices.remove(cachedBluetoothDevice2);
                                }
                            }
                        } else {
                            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
                            if (!(cachedBluetoothDevice3 == null || cachedBluetoothDevice3.getBondState() == 12)) {
                                cachedBluetoothDevice.mSubDevice = null;
                            }
                        }
                        if (cachedBluetoothDevice.getBondState() != 12) {
                            if (cachedBluetoothDevice.mJustDiscovered) {
                                cachedBluetoothDevice.mJustDiscovered = false;
                                cachedBluetoothDevice.dispatchAttributesChanged();
                            }
                            cachedBluetoothDeviceManager.mCachedDevices.remove(size);
                        }
                    }
                }
            }
        }
    }

    public class AudioModeChangedHandler implements Handler {
        public AudioModeChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "AudioModeChangedHandler() action is null");
                return;
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            Objects.requireNonNull(bluetoothEventManager);
            Iterator it = bluetoothEventManager.mDeviceManager.getCachedDevicesCopy().iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                Objects.requireNonNull(cachedBluetoothDevice);
                cachedBluetoothDevice.dispatchAttributesChanged();
            }
            Iterator it2 = bluetoothEventManager.mCallbacks.iterator();
            while (it2.hasNext()) {
                Objects.requireNonNull((BluetoothCallback) it2.next());
            }
        }
    }

    public class BatteryLevelChangedHandler implements Handler {
        public BatteryLevelChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }
    }

    public class BluetoothBroadcastReceiver extends BroadcastReceiver {
        public BluetoothBroadcastReceiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Handler handler = (Handler) BluetoothEventManager.this.mHandlerMap.get(action);
            if (handler != null) {
                handler.onReceive(context, intent, bluetoothDevice);
            }
        }
    }

    public class BondStateChangedHandler implements Handler {
        public BondStateChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            Objects.requireNonNull(cachedBluetoothDeviceManager);
            synchronized (cachedBluetoothDeviceManager) {
            }
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                Log.w("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                findDevice = BluetoothEventManager.this.mDeviceManager.addDevice(bluetoothDevice);
            }
            Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onDeviceBondStateChanged(findDevice, intExtra);
            }
            if (intExtra == 10) {
                synchronized (findDevice.mProfileLock) {
                    findDevice.mProfiles.clear();
                }
                findDevice.mDevice.setPhonebookAccessPermission(0);
                findDevice.mDevice.setMessageAccessPermission(0);
                findDevice.mDevice.setSimAccessPermission(0);
            }
            findDevice.refresh();
            if (intExtra == 12 && findDevice.mDevice.isBondingInitiatedLocally()) {
                findDevice.connect$1();
            }
            if (intExtra == 10) {
                if (!(findDevice.mGroupId == -1 && findDevice.mHiSyncId == 0)) {
                    CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 = BluetoothEventManager.this.mDeviceManager;
                    Objects.requireNonNull(cachedBluetoothDeviceManager2);
                    synchronized (cachedBluetoothDeviceManager2) {
                        CachedBluetoothDevice findMainDevice = cachedBluetoothDeviceManager2.mCsipDeviceManager.findMainDevice(findDevice);
                        HashSet<CachedBluetoothDevice> hashSet = findDevice.mMemberDevices;
                        if (!hashSet.isEmpty()) {
                            for (CachedBluetoothDevice cachedBluetoothDevice : hashSet) {
                                cachedBluetoothDevice.unpair();
                                findDevice.mMemberDevices.remove(cachedBluetoothDevice);
                            }
                        } else if (findMainDevice != null) {
                            findMainDevice.unpair();
                        }
                        CachedBluetoothDevice findMainDevice2 = cachedBluetoothDeviceManager2.mHearingAidDeviceManager.findMainDevice(findDevice);
                        CachedBluetoothDevice cachedBluetoothDevice2 = findDevice.mSubDevice;
                        if (cachedBluetoothDevice2 != null) {
                            cachedBluetoothDevice2.unpair();
                            findDevice.mSubDevice = null;
                        } else if (findMainDevice2 != null) {
                            findMainDevice2.unpair();
                            findMainDevice2.mSubDevice = null;
                        }
                    }
                }
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE);
                String name = findDevice.getName();
                if (BluetoothEventManager.DEBUG) {
                    Log.d("BluetoothEventManager", "showUnbondMessage() name : " + name + ", reason : " + intExtra2);
                }
                switch (intExtra2) {
                    case 1:
                        i = C1777R.string.bluetooth_pairing_pin_error_message;
                        break;
                    case 2:
                        i = C1777R.string.bluetooth_pairing_rejected_error_message;
                        break;
                    case 4:
                        i = C1777R.string.bluetooth_pairing_device_down_error_message;
                        break;
                    case 5:
                    case FalsingManager.VERSION /*6*/:
                    case 7:
                    case 8:
                        i = C1777R.string.bluetooth_pairing_error_message;
                        break;
                    default:
                        GridLayoutManager$$ExternalSyntheticOutline1.m20m("showUnbondMessage: Not displaying any message for reason: ", intExtra2, "BluetoothEventManager");
                        return;
                }
                BluetoothUtils.ErrorListener errorListener = BluetoothUtils.sErrorListener;
                if (errorListener != null) {
                    KeyboardUI.this.mHandler.obtainMessage(11, i, 0, new Pair(context, name)).sendToTarget();
                }
            }
        }
    }

    public class ClassChangedHandler implements Handler {
        public ClassChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }
    }

    public class ConnectionStateChangedHandler implements Handler {
        public ConnectionStateChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE);
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            Objects.requireNonNull(bluetoothEventManager);
            Iterator it = bluetoothEventManager.mCallbacks.iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onConnectionStateChanged(findDevice, intExtra);
            }
        }
    }

    public class DeviceFoundHandler implements Handler {
        public DeviceFoundHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MIN_VALUE);
            intent.getStringExtra("android.bluetooth.device.extra.NAME");
            intent.getBooleanExtra("android.bluetooth.extra.IS_COORDINATED_SET_MEMBER", false);
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                findDevice = BluetoothEventManager.this.mDeviceManager.addDevice(bluetoothDevice);
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DeviceFoundHandler created new CachedBluetoothDevice ");
                m.append(findDevice.mDevice.getAnonymizedAddress());
                Log.d("BluetoothEventManager", m.toString());
            } else if (findDevice.getBondState() == 12 && !findDevice.mDevice.isConnected()) {
                BluetoothEventManager.this.dispatchDeviceAdded(findDevice);
            }
            if (findDevice.mRssi != shortExtra) {
                findDevice.mRssi = shortExtra;
                findDevice.dispatchAttributesChanged();
            }
            if (!findDevice.mJustDiscovered) {
                findDevice.mJustDiscovered = true;
                findDevice.dispatchAttributesChanged();
            }
        }
    }

    public interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    public class NameChangedHandler implements Handler {
        public NameChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            Objects.requireNonNull(cachedBluetoothDeviceManager);
            CachedBluetoothDevice findDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Device name: ");
                m.append(findDevice.getName());
                Log.d("CachedBluetoothDevice", m.toString());
                findDevice.dispatchAttributesChanged();
            }
        }
    }

    public class ScanningStateChangedHandler implements Handler {
        public final boolean mStarted;

        public ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Iterator it = BluetoothEventManager.this.mCallbacks.iterator();
            while (it.hasNext()) {
                Objects.requireNonNull((BluetoothCallback) it.next());
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            boolean z = this.mStarted;
            Objects.requireNonNull(cachedBluetoothDeviceManager);
            synchronized (cachedBluetoothDeviceManager) {
                if (z) {
                    int size = cachedBluetoothDeviceManager.mCachedDevices.size();
                    while (true) {
                        size--;
                        if (size >= 0) {
                            CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                            Objects.requireNonNull(cachedBluetoothDevice);
                            if (cachedBluetoothDevice.mJustDiscovered) {
                                cachedBluetoothDevice.mJustDiscovered = false;
                                cachedBluetoothDevice.dispatchAttributesChanged();
                            }
                            HashSet<CachedBluetoothDevice> hashSet = cachedBluetoothDevice.mMemberDevices;
                            if (!hashSet.isEmpty()) {
                                for (CachedBluetoothDevice cachedBluetoothDevice2 : hashSet) {
                                    Objects.requireNonNull(cachedBluetoothDevice2);
                                    if (cachedBluetoothDevice2.mJustDiscovered) {
                                        cachedBluetoothDevice2.mJustDiscovered = false;
                                        cachedBluetoothDevice2.dispatchAttributesChanged();
                                    }
                                }
                                return;
                            }
                            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
                            if (cachedBluetoothDevice3 != null && cachedBluetoothDevice3.mJustDiscovered) {
                                cachedBluetoothDevice3.mJustDiscovered = false;
                                cachedBluetoothDevice3.dispatchAttributesChanged();
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public class UuidChangedHandler implements Handler {
        public UuidChangedHandler() {
        }

        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.updateProfiles();
                ParcelUuid[] uuids = findDevice.mDevice.getUuids();
                long j = 30000;
                if (!ArrayUtils.contains(uuids, BluetoothUuid.HOGP)) {
                    if (ArrayUtils.contains(uuids, BluetoothUuid.HEARING_AID)) {
                        j = WifiTracker.MAX_SCAN_RESULT_AGE_MILLIS;
                    } else if (!ArrayUtils.contains(uuids, BluetoothUuid.LE_AUDIO)) {
                        j = 5000;
                    }
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onUuidChanged: Time since last connect=");
                m.append(SystemClock.elapsedRealtime() - findDevice.mConnectAttempted);
                Log.d("CachedBluetoothDevice", m.toString());
                if (findDevice.mConnectAttempted + j > SystemClock.elapsedRealtime()) {
                    Log.d("CachedBluetoothDevice", "onUuidChanged: triggering connectDevice");
                    findDevice.connectDevice();
                }
                findDevice.dispatchAttributesChanged();
            }
        }
    }

    public void addHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    public void addProfileHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x000a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatchActiveDeviceChanged(com.android.settingslib.bluetooth.CachedBluetoothDevice r7, int r8) {
        /*
            r6 = this;
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r0 = r6.mDeviceManager
            java.util.ArrayList r0 = r0.getCachedDevicesCopy()
            java.util.Iterator r0 = r0.iterator()
        L_0x000a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0075
            java.lang.Object r1 = r0.next()
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r1
            boolean r2 = java.util.Objects.equals(r1, r7)
            r3 = 1
            r4 = 0
            if (r8 == r3) goto L_0x0066
            r5 = 2
            if (r8 == r5) goto L_0x005d
            r5 = 21
            if (r8 == r5) goto L_0x0054
            r5 = 22
            if (r8 == r5) goto L_0x004b
            java.util.Objects.requireNonNull(r1)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "onActiveDeviceChanged: unknown profile "
            r3.append(r5)
            r3.append(r8)
            java.lang.String r5 = " isActive "
            r3.append(r5)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r3 = "CachedBluetoothDevice"
            android.util.Log.w(r3, r2)
            goto L_0x006f
        L_0x004b:
            boolean r5 = r1.mIsActiveDeviceLeAudio
            if (r5 == r2) goto L_0x0050
            goto L_0x0051
        L_0x0050:
            r3 = r4
        L_0x0051:
            r1.mIsActiveDeviceLeAudio = r2
            goto L_0x006e
        L_0x0054:
            boolean r5 = r1.mIsActiveDeviceHearingAid
            if (r5 == r2) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            r3 = r4
        L_0x005a:
            r1.mIsActiveDeviceHearingAid = r2
            goto L_0x006e
        L_0x005d:
            boolean r5 = r1.mIsActiveDeviceA2dp
            if (r5 == r2) goto L_0x0062
            goto L_0x0063
        L_0x0062:
            r3 = r4
        L_0x0063:
            r1.mIsActiveDeviceA2dp = r2
            goto L_0x006e
        L_0x0066:
            boolean r5 = r1.mIsActiveDeviceHeadset
            if (r5 == r2) goto L_0x006b
            goto L_0x006c
        L_0x006b:
            r3 = r4
        L_0x006c:
            r1.mIsActiveDeviceHeadset = r2
        L_0x006e:
            r4 = r3
        L_0x006f:
            if (r4 == 0) goto L_0x000a
            r1.dispatchAttributesChanged()
            goto L_0x000a
        L_0x0075:
            java.util.concurrent.CopyOnWriteArrayList r6 = r6.mCallbacks
            java.util.Iterator r6 = r6.iterator()
        L_0x007b:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x008b
            java.lang.Object r0 = r6.next()
            com.android.settingslib.bluetooth.BluetoothCallback r0 = (com.android.settingslib.bluetooth.BluetoothCallback) r0
            r0.onActiveDeviceChanged(r7, r8)
            goto L_0x007b
        L_0x008b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothEventManager.dispatchActiveDeviceChanged(com.android.settingslib.bluetooth.CachedBluetoothDevice, int):void");
    }

    public final void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onDeviceAdded(cachedBluetoothDevice);
        }
    }

    public final void dispatchDeviceRemoved(CachedBluetoothDevice cachedBluetoothDevice) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onDeviceDeleted(cachedBluetoothDevice);
        }
    }

    public final boolean readPairedDevices() {
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        Objects.requireNonNull(localBluetoothAdapter);
        Set<BluetoothDevice> bondedDevices = localBluetoothAdapter.mAdapter.getBondedDevices();
        boolean z = false;
        if (bondedDevices == null) {
            return false;
        }
        for (BluetoothDevice next : bondedDevices) {
            if (this.mDeviceManager.findDevice(next) == null) {
                this.mDeviceManager.addDevice(next);
                z = true;
            }
        }
        return z;
    }

    public void registerAdapterIntentReceiver() {
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = this.mBroadcastReceiver;
        IntentFilter intentFilter = this.mAdapterIntentFilter;
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            this.mContext.registerReceiver(bluetoothBroadcastReceiver, intentFilter, (String) null, this.mReceiverHandler, 2);
        } else {
            this.mContext.registerReceiverAsUser(bluetoothBroadcastReceiver, userHandle, intentFilter, (String) null, this.mReceiverHandler, 2);
        }
    }

    public void registerProfileIntentReceiver() {
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = this.mProfileBroadcastReceiver;
        IntentFilter intentFilter = this.mProfileIntentFilter;
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            this.mContext.registerReceiver(bluetoothBroadcastReceiver, intentFilter, (String) null, this.mReceiverHandler, 2);
        } else {
            this.mContext.registerReceiverAsUser(bluetoothBroadcastReceiver, userHandle, intentFilter, (String) null, this.mReceiverHandler, 2);
        }
    }

    public BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, Context context, android.os.Handler handler, UserHandle userHandle) {
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mAdapterIntentFilter = new IntentFilter();
        this.mProfileIntentFilter = new IntentFilter();
        this.mHandlerMap = new HashMap();
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mReceiverHandler = handler;
        addHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED", new ConnectionStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.DISCOVERY_STARTED", new ScanningStateChangedHandler(true));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceFoundHandler());
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.ALIAS_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.BOND_STATE_CHANGED", new BondStateChangedHandler());
        addHandler("android.bluetooth.device.action.CLASS_CHANGED", new ClassChangedHandler());
        addHandler("android.bluetooth.device.action.UUID", new UuidChangedHandler());
        addHandler("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED", new BatteryLevelChangedHandler());
        addHandler("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED", new AudioModeChangedHandler());
        addHandler("android.intent.action.PHONE_STATE", new AudioModeChangedHandler());
        addHandler("android.bluetooth.device.action.ACL_CONNECTED", new AclStateChangedHandler());
        addHandler("android.bluetooth.device.action.ACL_DISCONNECTED", new AclStateChangedHandler());
        registerAdapterIntentReceiver();
    }
}
