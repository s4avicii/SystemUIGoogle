package com.android.settingslib.media;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.MediaManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class LocalMediaManager implements BluetoothCallback {
    public static final Comparator<MediaDevice> COMPARATOR = Comparator.naturalOrder();
    @VisibleForTesting
    public BluetoothAdapter mBluetoothAdapter;
    public final CopyOnWriteArrayList mCallbacks = new CopyOnWriteArrayList();
    public Context mContext;
    @VisibleForTesting
    public MediaDevice mCurrentConnectedDevice;
    @VisibleForTesting
    public DeviceAttributeChangeCallback mDeviceAttributeChangeCallback = new DeviceAttributeChangeCallback();
    @VisibleForTesting
    public List<MediaDevice> mDisconnectedMediaDevices = new CopyOnWriteArrayList();
    public InfoMediaManager mInfoMediaManager;
    public LocalBluetoothManager mLocalBluetoothManager;
    @VisibleForTesting
    public final MediaDeviceCallback mMediaDeviceCallback = new MediaDeviceCallback();
    @VisibleForTesting
    public List<MediaDevice> mMediaDevices = new CopyOnWriteArrayList();
    public final Object mMediaDevicesLock = new Object();
    public MediaDevice mOnTransferBluetoothDevice;
    public String mPackageName;
    @VisibleForTesting
    public MediaDevice mPhoneDevice;

    @VisibleForTesting
    public class DeviceAttributeChangeCallback implements CachedBluetoothDevice.Callback {
        public DeviceAttributeChangeCallback() {
        }

        public final void onDeviceAttributesChanged() {
            MediaDevice mediaDevice = LocalMediaManager.this.mOnTransferBluetoothDevice;
            if (mediaDevice != null && !((BluetoothMediaDevice) mediaDevice).mCachedDevice.isBusy() && !LocalMediaManager.this.mOnTransferBluetoothDevice.isConnected()) {
                MediaDevice mediaDevice2 = LocalMediaManager.this.mOnTransferBluetoothDevice;
                Objects.requireNonNull(mediaDevice2);
                mediaDevice2.mState = 3;
                LocalMediaManager localMediaManager = LocalMediaManager.this;
                localMediaManager.mOnTransferBluetoothDevice = null;
                Iterator it = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DeviceCallback) it.next()).onRequestFailed(0);
                }
            }
            LocalMediaManager localMediaManager2 = LocalMediaManager.this;
            Objects.requireNonNull(localMediaManager2);
            Iterator it2 = new CopyOnWriteArrayList(localMediaManager2.mCallbacks).iterator();
            while (it2.hasNext()) {
                ((DeviceCallback) it2.next()).onDeviceAttributesChanged();
            }
        }
    }

    public interface DeviceCallback {
        void onAboutToConnectDeviceChanged(String str, Drawable drawable) {
        }

        void onDeviceAttributesChanged() {
        }

        void onDeviceListUpdate(ArrayList arrayList) {
        }

        void onRequestFailed(int i) {
        }

        void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        }
    }

    public class MediaDeviceCallback implements MediaManager.MediaDeviceCallback {
        public MediaDeviceCallback() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x007a  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x008e A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.util.ArrayList buildDisconnectedBluetoothDevice() {
            /*
                r11 = this;
                com.android.settingslib.media.LocalMediaManager r0 = com.android.settingslib.media.LocalMediaManager.this
                android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
                if (r0 != 0) goto L_0x0013
                java.lang.String r11 = "LocalMediaManager"
                java.lang.String r0 = "buildDisconnectedBluetoothDevice() BluetoothAdapter is null"
                android.util.Log.w(r11, r0)
                java.util.ArrayList r11 = new java.util.ArrayList
                r11.<init>()
                return r11
            L_0x0013:
                java.util.List r0 = r0.getMostRecentlyConnectedDevices()
                com.android.settingslib.media.LocalMediaManager r1 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.bluetooth.LocalBluetoothManager r1 = r1.mLocalBluetoothManager
                java.util.Objects.requireNonNull(r1)
                com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r1 = r1.mCachedDeviceManager
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.Iterator r0 = r0.iterator()
                r3 = 0
                r4 = r3
            L_0x002b:
                boolean r5 = r0.hasNext()
                if (r5 == 0) goto L_0x009d
                java.lang.Object r5 = r0.next()
                android.bluetooth.BluetoothDevice r5 = (android.bluetooth.BluetoothDevice) r5
                com.android.settingslib.bluetooth.CachedBluetoothDevice r5 = r1.findDevice(r5)
                if (r5 == 0) goto L_0x002b
                int r6 = r5.getBondState()
                r7 = 12
                if (r6 != r7) goto L_0x002b
                boolean r6 = r5.isConnected()
                if (r6 != 0) goto L_0x002b
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                java.lang.Object r7 = r5.mProfileLock
                monitor-enter(r7)
                java.util.concurrent.CopyOnWriteArrayList r8 = r5.mProfiles     // Catch:{ all -> 0x009a }
                java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x009a }
            L_0x0059:
                boolean r9 = r8.hasNext()     // Catch:{ all -> 0x009a }
                if (r9 == 0) goto L_0x006f
                java.lang.Object r9 = r8.next()     // Catch:{ all -> 0x009a }
                com.android.settingslib.bluetooth.LocalBluetoothProfile r9 = (com.android.settingslib.bluetooth.LocalBluetoothProfile) r9     // Catch:{ all -> 0x009a }
                boolean r10 = r9.accessProfileEnabled()     // Catch:{ all -> 0x009a }
                if (r10 == 0) goto L_0x0059
                r6.add(r9)     // Catch:{ all -> 0x009a }
                goto L_0x0059
            L_0x006f:
                monitor-exit(r7)     // Catch:{ all -> 0x009a }
                java.util.Iterator r6 = r6.iterator()
            L_0x0074:
                boolean r7 = r6.hasNext()
                if (r7 == 0) goto L_0x008e
                java.lang.Object r7 = r6.next()
                com.android.settingslib.bluetooth.LocalBluetoothProfile r7 = (com.android.settingslib.bluetooth.LocalBluetoothProfile) r7
                boolean r8 = r7 instanceof com.android.settingslib.bluetooth.A2dpProfile
                if (r8 != 0) goto L_0x008c
                boolean r8 = r7 instanceof com.android.settingslib.bluetooth.HearingAidProfile
                if (r8 != 0) goto L_0x008c
                boolean r7 = r7 instanceof com.android.settingslib.bluetooth.LeAudioProfile
                if (r7 == 0) goto L_0x0074
            L_0x008c:
                r6 = 1
                goto L_0x008f
            L_0x008e:
                r6 = r3
            L_0x008f:
                if (r6 == 0) goto L_0x002b
                int r4 = r4 + 1
                r2.add(r5)
                r5 = 5
                if (r4 < r5) goto L_0x002b
                goto L_0x009d
            L_0x009a:
                r11 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x009a }
                throw r11
            L_0x009d:
                com.android.settingslib.media.LocalMediaManager r0 = com.android.settingslib.media.LocalMediaManager.this
                r0.unRegisterDeviceAttributeChangeCallback()
                com.android.settingslib.media.LocalMediaManager r0 = com.android.settingslib.media.LocalMediaManager.this
                java.util.List<com.android.settingslib.media.MediaDevice> r0 = r0.mDisconnectedMediaDevices
                r0.clear()
                java.util.Iterator r0 = r2.iterator()
            L_0x00ad:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x00e6
                java.lang.Object r1 = r0.next()
                com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r1
                com.android.settingslib.media.BluetoothMediaDevice r8 = new com.android.settingslib.media.BluetoothMediaDevice
                com.android.settingslib.media.LocalMediaManager r2 = com.android.settingslib.media.LocalMediaManager.this
                android.content.Context r3 = r2.mContext
                r5 = 0
                r6 = 0
                java.lang.String r7 = r2.mPackageName
                r2 = r8
                r4 = r1
                r2.<init>(r3, r4, r5, r6, r7)
                com.android.settingslib.media.LocalMediaManager r2 = com.android.settingslib.media.LocalMediaManager.this
                java.util.List<com.android.settingslib.media.MediaDevice> r2 = r2.mMediaDevices
                boolean r2 = r2.contains(r8)
                if (r2 != 0) goto L_0x00ad
                com.android.settingslib.media.LocalMediaManager r2 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.LocalMediaManager$DeviceAttributeChangeCallback r2 = r2.mDeviceAttributeChangeCallback
                java.util.Objects.requireNonNull(r1)
                java.util.concurrent.CopyOnWriteArrayList r1 = r1.mCallbacks
                r1.add(r2)
                com.android.settingslib.media.LocalMediaManager r1 = com.android.settingslib.media.LocalMediaManager.this
                java.util.List<com.android.settingslib.media.MediaDevice> r1 = r1.mDisconnectedMediaDevices
                r1.add(r8)
                goto L_0x00ad
            L_0x00e6:
                java.util.ArrayList r0 = new java.util.ArrayList
                com.android.settingslib.media.LocalMediaManager r11 = com.android.settingslib.media.LocalMediaManager.this
                java.util.List<com.android.settingslib.media.MediaDevice> r11 = r11.mDisconnectedMediaDevices
                r0.<init>(r11)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.LocalMediaManager.MediaDeviceCallback.buildDisconnectedBluetoothDevice():java.util.ArrayList");
        }

        public final void onConnectedDeviceChanged(String str) {
            MediaDevice mediaDeviceById;
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                mediaDeviceById = LocalMediaManager.getMediaDeviceById(LocalMediaManager.this.mMediaDevices, str);
            }
            if (mediaDeviceById == null) {
                mediaDeviceById = LocalMediaManager.this.updateCurrentConnectedDevice();
            }
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = mediaDeviceById;
            if (mediaDeviceById != null) {
                mediaDeviceById.mState = 0;
                Iterator it = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DeviceCallback) it.next()).onSelectedDeviceStateChanged(mediaDeviceById);
                }
            }
        }

        public final void onDeviceAttributesChanged() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            Objects.requireNonNull(localMediaManager);
            Iterator it = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((DeviceCallback) it.next()).onDeviceAttributesChanged();
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:34:0x0041 A[EDGE_INSN: B:34:0x0041->B:13:0x0041 ?: BREAK  , SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:6:0x0022  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onDeviceListAdded(java.util.ArrayList r4) {
            /*
                r3 = this;
                com.android.settingslib.media.LocalMediaManager r0 = com.android.settingslib.media.LocalMediaManager.this
                java.lang.Object r0 = r0.mMediaDevicesLock
                monitor-enter(r0)
                java.util.Comparator<com.android.settingslib.media.MediaDevice> r1 = com.android.settingslib.media.LocalMediaManager.COMPARATOR     // Catch:{ all -> 0x00bf }
                java.util.Collections.sort(r4, r1)     // Catch:{ all -> 0x00bf }
                com.android.settingslib.media.LocalMediaManager r1 = com.android.settingslib.media.LocalMediaManager.this     // Catch:{ all -> 0x00bf }
                java.util.List<com.android.settingslib.media.MediaDevice> r1 = r1.mMediaDevices     // Catch:{ all -> 0x00bf }
                r1.clear()     // Catch:{ all -> 0x00bf }
                com.android.settingslib.media.LocalMediaManager r1 = com.android.settingslib.media.LocalMediaManager.this     // Catch:{ all -> 0x00bf }
                java.util.List<com.android.settingslib.media.MediaDevice> r1 = r1.mMediaDevices     // Catch:{ all -> 0x00bf }
                r1.addAll(r4)     // Catch:{ all -> 0x00bf }
                java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x00bf }
            L_0x001c:
                boolean r1 = r4.hasNext()     // Catch:{ all -> 0x00bf }
                if (r1 == 0) goto L_0x0041
                java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x00bf }
                com.android.settingslib.media.MediaDevice r1 = (com.android.settingslib.media.MediaDevice) r1     // Catch:{ all -> 0x00bf }
                java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00bf }
                int r1 = r1.mType     // Catch:{ all -> 0x00bf }
                r2 = 1
                if (r1 == r2) goto L_0x0036
                r2 = 2
                if (r1 == r2) goto L_0x0036
                r2 = 7
                if (r1 != r2) goto L_0x001c
            L_0x0036:
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this     // Catch:{ all -> 0x00bf }
                java.util.List<com.android.settingslib.media.MediaDevice> r4 = r4.mMediaDevices     // Catch:{ all -> 0x00bf }
                java.util.ArrayList r1 = r3.buildDisconnectedBluetoothDevice()     // Catch:{ all -> 0x00bf }
                r4.addAll(r1)     // Catch:{ all -> 0x00bf }
            L_0x0041:
                monitor-exit(r0)     // Catch:{ all -> 0x00bf }
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.InfoMediaManager r4 = r4.mInfoMediaManager
                java.util.Objects.requireNonNull(r4)
                com.android.settingslib.media.MediaDevice r4 = r4.mCurrentConnectedDevice
                com.android.settingslib.media.LocalMediaManager r0 = com.android.settingslib.media.LocalMediaManager.this
                if (r4 == 0) goto L_0x0050
                goto L_0x0054
            L_0x0050:
                com.android.settingslib.media.MediaDevice r4 = r0.updateCurrentConnectedDevice()
            L_0x0054:
                r0.mCurrentConnectedDevice = r4
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                java.util.Objects.requireNonNull(r4)
                java.util.ArrayList r0 = new java.util.ArrayList
                java.util.List<com.android.settingslib.media.MediaDevice> r1 = r4.mMediaDevices
                r0.<init>(r1)
                java.util.concurrent.CopyOnWriteArrayList r1 = new java.util.concurrent.CopyOnWriteArrayList
                java.util.concurrent.CopyOnWriteArrayList r4 = r4.mCallbacks
                r1.<init>(r4)
                java.util.Iterator r4 = r1.iterator()
            L_0x006d:
                boolean r1 = r4.hasNext()
                if (r1 == 0) goto L_0x007d
                java.lang.Object r1 = r4.next()
                com.android.settingslib.media.LocalMediaManager$DeviceCallback r1 = (com.android.settingslib.media.LocalMediaManager.DeviceCallback) r1
                r1.onDeviceListUpdate(r0)
                goto L_0x006d
            L_0x007d:
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.MediaDevice r4 = r4.mOnTransferBluetoothDevice
                if (r4 == 0) goto L_0x00be
                boolean r4 = r4.isConnected()
                if (r4 == 0) goto L_0x00be
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.MediaDevice r0 = r4.mOnTransferBluetoothDevice
                r4.connectDevice(r0)
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.MediaDevice r4 = r4.mOnTransferBluetoothDevice
                java.util.Objects.requireNonNull(r4)
                r0 = 0
                r4.mState = r0
                com.android.settingslib.media.LocalMediaManager r4 = com.android.settingslib.media.LocalMediaManager.this
                com.android.settingslib.media.MediaDevice r0 = r4.mOnTransferBluetoothDevice
                java.util.concurrent.CopyOnWriteArrayList r1 = new java.util.concurrent.CopyOnWriteArrayList
                java.util.concurrent.CopyOnWriteArrayList r4 = r4.mCallbacks
                r1.<init>(r4)
                java.util.Iterator r4 = r1.iterator()
            L_0x00a9:
                boolean r1 = r4.hasNext()
                if (r1 == 0) goto L_0x00b9
                java.lang.Object r1 = r4.next()
                com.android.settingslib.media.LocalMediaManager$DeviceCallback r1 = (com.android.settingslib.media.LocalMediaManager.DeviceCallback) r1
                r1.onSelectedDeviceStateChanged(r0)
                goto L_0x00a9
            L_0x00b9:
                com.android.settingslib.media.LocalMediaManager r3 = com.android.settingslib.media.LocalMediaManager.this
                r4 = 0
                r3.mOnTransferBluetoothDevice = r4
            L_0x00be:
                return
            L_0x00bf:
                r3 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00bf }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.LocalMediaManager.MediaDeviceCallback.onDeviceListAdded(java.util.ArrayList):void");
        }

        public final void onRequestFailed(int i) {
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                for (MediaDevice next : LocalMediaManager.this.mMediaDevices) {
                    Objects.requireNonNull(next);
                    if (next.mState == 1) {
                        next.mState = 3;
                    }
                }
            }
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            Objects.requireNonNull(localMediaManager);
            Iterator it = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((DeviceCallback) it.next()).onRequestFailed(i);
            }
        }
    }

    public final boolean connectDevice(MediaDevice mediaDevice) {
        MediaDevice mediaDeviceById;
        synchronized (this.mMediaDevicesLock) {
            mediaDeviceById = getMediaDeviceById(this.mMediaDevices, mediaDevice.getId());
        }
        if (mediaDeviceById == null) {
            Log.w("LocalMediaManager", "connectDevice() connectDevice not in the list!");
            return false;
        }
        if (mediaDeviceById instanceof BluetoothMediaDevice) {
            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothMediaDevice) mediaDeviceById).mCachedDevice;
            if (!cachedBluetoothDevice.isConnected() && !cachedBluetoothDevice.isBusy()) {
                this.mOnTransferBluetoothDevice = mediaDevice;
                mediaDeviceById.mState = 1;
                cachedBluetoothDevice.connect$1();
                return true;
            }
        }
        if (mediaDeviceById.equals(this.mCurrentConnectedDevice)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("connectDevice() this device is already connected! : ");
            m.append(mediaDeviceById.getName());
            Log.d("LocalMediaManager", m.toString());
            return false;
        }
        mediaDeviceById.mState = 1;
        if (TextUtils.isEmpty(this.mPackageName)) {
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            Objects.requireNonNull(infoMediaManager);
            RoutingSessionInfo systemRoutingSession = infoMediaManager.mRouterManager.getSystemRoutingSession((String) null);
            if (systemRoutingSession != null) {
                infoMediaManager.mRouterManager.transfer(systemRoutingSession, mediaDeviceById.mRouteInfo);
            }
        } else if (mediaDeviceById.mRouteInfo == null) {
            Log.w("MediaDevice", "Unable to connect. RouteInfo is empty");
        } else {
            mediaDeviceById.mConnectedRecord++;
            ConnectionRecordManager instance = ConnectionRecordManager.getInstance();
            Context context = mediaDeviceById.mContext;
            String id = mediaDeviceById.getId();
            int i = mediaDeviceById.mConnectedRecord;
            Objects.requireNonNull(instance);
            synchronized (instance) {
                SharedPreferences.Editor edit = context.getSharedPreferences("seamless_transfer_record", 0).edit();
                instance.mLastSelectedDevice = id;
                edit.putInt(id, i);
                edit.putString("last_selected_device", instance.mLastSelectedDevice);
                edit.apply();
            }
            mediaDeviceById.mRouterManager.selectRoute(mediaDeviceById.mPackageName, mediaDeviceById.mRouteInfo);
        }
        return true;
    }

    public final void dispatchAboutToConnectDeviceChanged(String str, Drawable drawable) {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((DeviceCallback) it.next()).onAboutToConnectDeviceChanged(str, drawable);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.bluetooth.BluetoothDevice} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008c A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isActiveDevice(com.android.settingslib.bluetooth.CachedBluetoothDevice r6) {
        /*
            r5 = this;
            com.android.settingslib.bluetooth.LocalBluetoothManager r0 = r5.mLocalBluetoothManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r0.mProfileManager
            java.util.Objects.requireNonNull(r0)
            com.android.settingslib.bluetooth.A2dpProfile r0 = r0.mA2dpProfile
            r1 = 0
            if (r0 == 0) goto L_0x0031
            java.util.Objects.requireNonNull(r6)
            android.bluetooth.BluetoothDevice r2 = r6.mDevice
            android.bluetooth.BluetoothAdapter r0 = r0.mBluetoothAdapter
            r3 = 0
            if (r0 != 0) goto L_0x001a
            goto L_0x002c
        L_0x001a:
            r4 = 2
            java.util.List r0 = r0.getActiveDevices(r4)
            int r4 = r0.size()
            if (r4 <= 0) goto L_0x002c
            java.lang.Object r0 = r0.get(r1)
            r3 = r0
            android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
        L_0x002c:
            boolean r0 = r2.equals(r3)
            goto L_0x0032
        L_0x0031:
            r0 = r1
        L_0x0032:
            if (r0 != 0) goto L_0x005c
            com.android.settingslib.bluetooth.LocalBluetoothManager r2 = r5.mLocalBluetoothManager
            java.util.Objects.requireNonNull(r2)
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r2 = r2.mProfileManager
            java.util.Objects.requireNonNull(r2)
            com.android.settingslib.bluetooth.HearingAidProfile r2 = r2.mHearingAidProfile
            if (r2 == 0) goto L_0x005c
            android.bluetooth.BluetoothAdapter r2 = r2.mBluetoothAdapter
            if (r2 != 0) goto L_0x004c
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            goto L_0x0052
        L_0x004c:
            r3 = 21
            java.util.List r2 = r2.getActiveDevices(r3)
        L_0x0052:
            java.util.Objects.requireNonNull(r6)
            android.bluetooth.BluetoothDevice r3 = r6.mDevice
            boolean r2 = r2.contains(r3)
            goto L_0x005d
        L_0x005c:
            r2 = r1
        L_0x005d:
            if (r0 != 0) goto L_0x0089
            if (r2 != 0) goto L_0x0089
            com.android.settingslib.bluetooth.LocalBluetoothManager r5 = r5.mLocalBluetoothManager
            java.util.Objects.requireNonNull(r5)
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r5 = r5.mProfileManager
            java.util.Objects.requireNonNull(r5)
            com.android.settingslib.bluetooth.LeAudioProfile r5 = r5.mLeAudioProfile
            if (r5 == 0) goto L_0x0089
            android.bluetooth.BluetoothAdapter r5 = r5.mBluetoothAdapter
            if (r5 != 0) goto L_0x0079
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            goto L_0x007f
        L_0x0079:
            r3 = 22
            java.util.List r5 = r5.getActiveDevices(r3)
        L_0x007f:
            java.util.Objects.requireNonNull(r6)
            android.bluetooth.BluetoothDevice r6 = r6.mDevice
            boolean r5 = r5.contains(r6)
            goto L_0x008a
        L_0x0089:
            r5 = r1
        L_0x008a:
            if (r0 != 0) goto L_0x0090
            if (r2 != 0) goto L_0x0090
            if (r5 == 0) goto L_0x0091
        L_0x0090:
            r1 = 1
        L_0x0091:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.LocalMediaManager.isActiveDevice(com.android.settingslib.bluetooth.CachedBluetoothDevice):boolean");
    }

    public final void startScan() {
        synchronized (this.mMediaDevicesLock) {
            this.mMediaDevices.clear();
        }
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
        Objects.requireNonNull(infoMediaManager);
        if (!infoMediaManager.mCallbacks.contains(mediaDeviceCallback)) {
            infoMediaManager.mCallbacks.add(mediaDeviceCallback);
        }
        InfoMediaManager infoMediaManager2 = this.mInfoMediaManager;
        Objects.requireNonNull(infoMediaManager2);
        infoMediaManager2.mMediaDevices.clear();
        infoMediaManager2.mRouterManager.registerCallback(infoMediaManager2.mExecutor, infoMediaManager2.mMediaRouterCallback);
        infoMediaManager2.mRouterManager.startScan();
        infoMediaManager2.refreshDevices();
    }

    public final void stopScan() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
        Objects.requireNonNull(infoMediaManager);
        if (infoMediaManager.mCallbacks.contains(mediaDeviceCallback)) {
            infoMediaManager.mCallbacks.remove(mediaDeviceCallback);
        }
        InfoMediaManager infoMediaManager2 = this.mInfoMediaManager;
        Objects.requireNonNull(infoMediaManager2);
        infoMediaManager2.mRouterManager.unregisterCallback(infoMediaManager2.mMediaRouterCallback);
        infoMediaManager2.mRouterManager.stopScan();
        unRegisterDeviceAttributeChangeCallback();
    }

    public final void unRegisterDeviceAttributeChangeCallback() {
        Iterator<MediaDevice> it = this.mDisconnectedMediaDevices.iterator();
        while (it.hasNext()) {
            BluetoothMediaDevice bluetoothMediaDevice = (BluetoothMediaDevice) it.next();
            Objects.requireNonNull(bluetoothMediaDevice);
            CachedBluetoothDevice cachedBluetoothDevice = bluetoothMediaDevice.mCachedDevice;
            DeviceAttributeChangeCallback deviceAttributeChangeCallback = this.mDeviceAttributeChangeCallback;
            Objects.requireNonNull(cachedBluetoothDevice);
            cachedBluetoothDevice.mCallbacks.remove(deviceAttributeChangeCallback);
        }
    }

    @VisibleForTesting
    public MediaDevice updateCurrentConnectedDevice() {
        synchronized (this.mMediaDevicesLock) {
            MediaDevice mediaDevice = null;
            for (MediaDevice next : this.mMediaDevices) {
                if (next instanceof BluetoothMediaDevice) {
                    BluetoothMediaDevice bluetoothMediaDevice = (BluetoothMediaDevice) next;
                    Objects.requireNonNull(bluetoothMediaDevice);
                    if (isActiveDevice(bluetoothMediaDevice.mCachedDevice) && next.isConnected()) {
                        return next;
                    }
                } else if (next instanceof PhoneMediaDevice) {
                    mediaDevice = next;
                }
            }
            return mediaDevice;
        }
    }

    public LocalMediaManager(Context context, LocalBluetoothManager localBluetoothManager, InfoMediaManager infoMediaManager, String str) {
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mInfoMediaManager = infoMediaManager;
        this.mPackageName = str;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static MediaDevice getMediaDeviceById(List list, String str) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaDevice mediaDevice = (MediaDevice) it.next();
            if (TextUtils.equals(mediaDevice.getId(), str)) {
                return mediaDevice;
            }
        }
        Log.i("LocalMediaManager", "getMediaDeviceById() can't found device");
        return null;
    }
}
