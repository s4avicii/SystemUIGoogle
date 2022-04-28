package com.android.systemui.keyboard;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;
import androidx.exifinterface.media.C0155xe8491b12;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager$$ExternalSyntheticLambda0;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class KeyboardUI extends CoreStartable implements InputManager.OnTabletModeChangedListener {
    public boolean mBootCompleted;
    public long mBootCompletedTime;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public volatile Context mContext;
    public BluetoothDialog mDialog;
    public boolean mEnabled;
    public volatile KeyboardHandler mHandler;
    public int mInTabletMode = -1;
    public String mKeyboardName;
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public int mScanAttempt = 0;
    public KeyboardScanCallback mScanCallback;
    public int mState;
    public volatile KeyboardUIHandler mUIHandler;

    public final class BluetoothCallbackHandler implements BluetoothCallback {
        public BluetoothCallbackHandler() {
        }

        public final void onBluetoothStateChanged(int i) {
            KeyboardUI.this.mHandler.obtainMessage(4, i, 0).sendToTarget();
        }

        public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
            KeyboardUI.this.mHandler.obtainMessage(5, i, 0, cachedBluetoothDevice).sendToTarget();
        }
    }

    public final class BluetoothDialogClickListener implements DialogInterface.OnClickListener {
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2;
            if (-1 == i) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            KeyboardUI.this.mHandler.obtainMessage(3, i2, 0).sendToTarget();
            KeyboardUI.this.mDialog = null;
        }

        public BluetoothDialogClickListener() {
        }
    }

    public final class BluetoothDialogDismissListener implements DialogInterface.OnDismissListener {
        public BluetoothDialogDismissListener() {
        }

        public final void onDismiss(DialogInterface dialogInterface) {
            KeyboardUI.this.mDialog = null;
        }
    }

    public final class BluetoothErrorListener implements BluetoothUtils.ErrorListener {
        public BluetoothErrorListener() {
        }
    }

    public final class KeyboardHandler extends Handler {
        public KeyboardHandler(Looper looper) {
            super(looper, (Handler.Callback) null, true);
        }

        public final void handleMessage(Message message) {
            LocalBluetoothManager localBluetoothManager;
            boolean z = false;
            switch (message.what) {
                case 0:
                    KeyboardUI keyboardUI = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI);
                    Context context = keyboardUI.mContext;
                    String string = context.getString(17039995);
                    keyboardUI.mKeyboardName = string;
                    if (!TextUtils.isEmpty(string) && (localBluetoothManager = (LocalBluetoothManager) Dependency.get(LocalBluetoothManager.class)) != null) {
                        keyboardUI.mEnabled = true;
                        keyboardUI.mCachedDeviceManager = localBluetoothManager.mCachedDeviceManager;
                        keyboardUI.mLocalBluetoothAdapter = localBluetoothManager.mLocalAdapter;
                        BluetoothEventManager bluetoothEventManager = localBluetoothManager.mEventManager;
                        BluetoothCallbackHandler bluetoothCallbackHandler = new BluetoothCallbackHandler();
                        Objects.requireNonNull(bluetoothEventManager);
                        bluetoothEventManager.mCallbacks.add(bluetoothCallbackHandler);
                        BluetoothUtils.sErrorListener = new BluetoothErrorListener();
                        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
                        inputManager.registerOnTabletModeChangedListener(keyboardUI, keyboardUI.mHandler);
                        keyboardUI.mInTabletMode = inputManager.isInTabletMode();
                        keyboardUI.processKeyboardState();
                        keyboardUI.mUIHandler = new KeyboardUIHandler();
                        return;
                    }
                    return;
                case 1:
                    KeyboardUI keyboardUI2 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI2);
                    keyboardUI2.mBootCompleted = true;
                    keyboardUI2.mBootCompletedTime = SystemClock.uptimeMillis();
                    if (keyboardUI2.mState == 1) {
                        keyboardUI2.processKeyboardState();
                        return;
                    }
                    return;
                case 2:
                    KeyboardUI.this.processKeyboardState();
                    return;
                case 3:
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    if (z) {
                        LocalBluetoothAdapter localBluetoothAdapter = KeyboardUI.this.mLocalBluetoothAdapter;
                        Objects.requireNonNull(localBluetoothAdapter);
                        localBluetoothAdapter.mAdapter.enable();
                        return;
                    }
                    KeyboardUI.this.mState = 8;
                    return;
                case 4:
                    int i = message.arg1;
                    KeyboardUI keyboardUI3 = KeyboardUI.this;
                    if (i != 12) {
                        Objects.requireNonNull(keyboardUI3);
                        return;
                    } else if (keyboardUI3.mState == 4) {
                        keyboardUI3.processKeyboardState();
                        return;
                    } else {
                        return;
                    }
                case 5:
                    CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) message.obj;
                    int i2 = message.arg1;
                    KeyboardUI keyboardUI4 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI4);
                    if (keyboardUI4.mState == 5 && cachedBluetoothDevice.getName().equals(keyboardUI4.mKeyboardName)) {
                        if (i2 == 12) {
                            keyboardUI4.mState = 6;
                            return;
                        } else if (i2 == 10) {
                            keyboardUI4.mState = 7;
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case FalsingManager.VERSION /*6*/:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                    KeyboardUI keyboardUI5 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI5);
                    CachedBluetoothDevice findDevice = keyboardUI5.mCachedDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        findDevice = keyboardUI5.mCachedDeviceManager.addDevice(bluetoothDevice);
                    }
                    KeyboardUI keyboardUI6 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI6);
                    if (keyboardUI6.mState == 3 && findDevice.getName().equals(keyboardUI6.mKeyboardName)) {
                        keyboardUI6.stopScanning();
                        findDevice.startPairing();
                        keyboardUI6.mState = 5;
                        return;
                    }
                    return;
                case 7:
                    KeyboardUI keyboardUI7 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI7);
                    keyboardUI7.mScanCallback = null;
                    if (keyboardUI7.mState == 3) {
                        keyboardUI7.mState = 9;
                        return;
                    }
                    return;
                case 10:
                    int i3 = message.arg1;
                    KeyboardUI keyboardUI8 = KeyboardUI.this;
                    Objects.requireNonNull(keyboardUI8);
                    if (keyboardUI8.mState == 3 && i3 == keyboardUI8.mScanAttempt) {
                        keyboardUI8.stopScanning();
                        keyboardUI8.mState = 9;
                        return;
                    }
                    return;
                case QSTileImpl.C1034H.STALE /*11*/:
                    Pair pair = (Pair) message.obj;
                    KeyboardUI keyboardUI9 = KeyboardUI.this;
                    Context context2 = (Context) pair.first;
                    String str = (String) pair.second;
                    int i4 = message.arg1;
                    Objects.requireNonNull(keyboardUI9);
                    int i5 = keyboardUI9.mState;
                    if ((i5 == 5 || i5 == 7) && keyboardUI9.mKeyboardName.equals(str)) {
                        Toast.makeText(context2, context2.getString(i4, new Object[]{str}), 0).show();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public final class KeyboardScanCallback extends ScanCallback {
        public KeyboardScanCallback() {
        }

        public final void onScanFailed(int i) {
            KeyboardUI.this.mHandler.obtainMessage(7).sendToTarget();
        }

        public final void onBatchScanResults(List<ScanResult> list) {
            boolean z;
            BluetoothDevice bluetoothDevice = null;
            int i = Integer.MIN_VALUE;
            for (ScanResult next : list) {
                if ((next.getScanRecord().getAdvertiseFlags() & 3) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && next.getRssi() > i) {
                    bluetoothDevice = next.getDevice();
                    i = next.getRssi();
                }
            }
            if (bluetoothDevice != null) {
                KeyboardUI.this.mHandler.obtainMessage(6, bluetoothDevice).sendToTarget();
            }
        }

        public final void onScanResult(int i, ScanResult scanResult) {
            boolean z;
            if ((scanResult.getScanRecord().getAdvertiseFlags() & 3) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                KeyboardUI.this.mHandler.obtainMessage(6, scanResult.getDevice()).sendToTarget();
            }
        }
    }

    public final class KeyboardUIHandler extends Handler {
        public KeyboardUIHandler() {
            super(Looper.getMainLooper(), (Handler.Callback) null, true);
        }

        public final void handleMessage(Message message) {
            BluetoothDialog bluetoothDialog;
            int i = message.what;
            if (i == 8) {
                KeyboardUI keyboardUI = KeyboardUI.this;
                if (keyboardUI.mDialog == null) {
                    BluetoothDialogClickListener bluetoothDialogClickListener = new BluetoothDialogClickListener();
                    BluetoothDialogDismissListener bluetoothDialogDismissListener = new BluetoothDialogDismissListener();
                    keyboardUI.mDialog = new BluetoothDialog(KeyboardUI.this.mContext);
                    KeyboardUI.this.mDialog.setTitle(C1777R.string.enable_bluetooth_title);
                    KeyboardUI.this.mDialog.setMessage(C1777R.string.enable_bluetooth_message);
                    KeyboardUI.this.mDialog.setPositiveButton(C1777R.string.enable_bluetooth_confirmation_ok, bluetoothDialogClickListener);
                    KeyboardUI.this.mDialog.setNegativeButton(17039360, bluetoothDialogClickListener);
                    KeyboardUI.this.mDialog.setOnDismissListener(bluetoothDialogDismissListener);
                    KeyboardUI.this.mDialog.show();
                }
            } else if (i == 9 && (bluetoothDialog = KeyboardUI.this.mDialog) != null) {
                bluetoothDialog.dismiss();
            }
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "KeyboardUI:", "  mEnabled="), this.mEnabled, printWriter, "  mBootCompleted="), this.mEnabled, printWriter, "  mBootCompletedTime=");
        m.append(this.mBootCompletedTime);
        printWriter.println(m.toString());
        printWriter.println("  mKeyboardName=" + this.mKeyboardName);
        printWriter.println("  mInTabletMode=" + this.mInTabletMode);
        StringBuilder sb = new StringBuilder();
        sb.append("  mState=");
        int i = this.mState;
        switch (i) {
            case -1:
                str = "STATE_NOT_ENABLED";
                break;
            case 1:
                str = "STATE_WAITING_FOR_BOOT_COMPLETED";
                break;
            case 2:
                str = "STATE_WAITING_FOR_TABLET_MODE_EXIT";
                break;
            case 3:
                str = "STATE_WAITING_FOR_DEVICE_DISCOVERY";
                break;
            case 4:
                str = "STATE_WAITING_FOR_BLUETOOTH";
                break;
            case 5:
                str = "STATE_PAIRING";
                break;
            case FalsingManager.VERSION /*6*/:
                str = "STATE_PAIRED";
                break;
            case 7:
                str = "STATE_PAIRING_FAILED";
                break;
            case 8:
                str = "STATE_USER_CANCELLED";
                break;
            case 9:
                str = "STATE_DEVICE_NOT_FOUND";
                break;
            default:
                str = C0155xe8491b12.m16m("STATE_UNKNOWN (", i, ")");
                break;
        }
        sb.append(str);
        printWriter.println(sb.toString());
    }

    public final void onBootCompleted() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onTabletModeChanged(long j, boolean z) {
        if ((z && this.mInTabletMode != 1) || (!z && this.mInTabletMode != 0)) {
            this.mInTabletMode = z ? 1 : 0;
            processKeyboardState();
        }
    }

    public final void processKeyboardState() {
        CachedBluetoothDevice cachedBluetoothDevice;
        CachedBluetoothDevice cachedBluetoothDevice2;
        this.mHandler.removeMessages(2);
        if (!this.mEnabled) {
            this.mState = -1;
            return;
        }
        boolean z = true;
        if (!this.mBootCompleted) {
            this.mState = 1;
        } else if (this.mInTabletMode != 0) {
            int i = this.mState;
            if (i == 3) {
                stopScanning();
            } else if (i == 4) {
                this.mUIHandler.sendEmptyMessage(9);
            }
            this.mState = 2;
        } else {
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
            Objects.requireNonNull(localBluetoothAdapter);
            int state = localBluetoothAdapter.mAdapter.getState();
            if ((state == 11 || state == 12) && this.mState == 4) {
                this.mUIHandler.sendEmptyMessage(9);
            }
            if (state == 11) {
                this.mState = 4;
            } else if (state != 12) {
                this.mState = 4;
                if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) == 0) {
                    z = false;
                }
                if (z) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long j = this.mBootCompletedTime + 10000;
                    if (j < uptimeMillis) {
                        this.mUIHandler.sendEmptyMessage(8);
                    } else {
                        this.mHandler.sendEmptyMessageAtTime(2, j);
                    }
                } else {
                    LocalBluetoothAdapter localBluetoothAdapter2 = this.mLocalBluetoothAdapter;
                    Objects.requireNonNull(localBluetoothAdapter2);
                    localBluetoothAdapter2.mAdapter.enable();
                }
            } else {
                LocalBluetoothAdapter localBluetoothAdapter3 = this.mLocalBluetoothAdapter;
                Objects.requireNonNull(localBluetoothAdapter3);
                Iterator<BluetoothDevice> it = localBluetoothAdapter3.mAdapter.getBondedDevices().iterator();
                while (true) {
                    cachedBluetoothDevice = null;
                    if (!it.hasNext()) {
                        cachedBluetoothDevice2 = null;
                        break;
                    }
                    BluetoothDevice next = it.next();
                    if (this.mKeyboardName.equals(next.getName())) {
                        cachedBluetoothDevice2 = this.mCachedDeviceManager.findDevice(next);
                        if (cachedBluetoothDevice2 == null) {
                            cachedBluetoothDevice2 = this.mCachedDeviceManager.addDevice(next);
                        }
                    }
                }
                int i2 = this.mState;
                if (i2 == 2 || i2 == 4) {
                    if (cachedBluetoothDevice2 != null) {
                        this.mState = 6;
                        cachedBluetoothDevice2.connect$1();
                        return;
                    }
                    CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mCachedDeviceManager;
                    Objects.requireNonNull(cachedBluetoothDeviceManager);
                    synchronized (cachedBluetoothDeviceManager) {
                        cachedBluetoothDeviceManager.clearNonBondedSubDevices();
                        cachedBluetoothDeviceManager.mCachedDevices.removeIf(CachedBluetoothDeviceManager$$ExternalSyntheticLambda0.INSTANCE);
                    }
                }
                Iterator it2 = this.mCachedDeviceManager.getCachedDevicesCopy().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) it2.next();
                    if (cachedBluetoothDevice3.getName().equals(this.mKeyboardName)) {
                        cachedBluetoothDevice = cachedBluetoothDevice3;
                        break;
                    }
                }
                if (cachedBluetoothDevice != null) {
                    this.mState = 5;
                    cachedBluetoothDevice.startPairing();
                    return;
                }
                this.mState = 3;
                LocalBluetoothAdapter localBluetoothAdapter4 = this.mLocalBluetoothAdapter;
                Objects.requireNonNull(localBluetoothAdapter4);
                BluetoothLeScanner bluetoothLeScanner = localBluetoothAdapter4.mAdapter.getBluetoothLeScanner();
                ScanFilter build = new ScanFilter.Builder().setDeviceName(this.mKeyboardName).build();
                ScanSettings build2 = new ScanSettings.Builder().setCallbackType(1).setNumOfMatches(1).setScanMode(2).setReportDelay(0).build();
                this.mScanCallback = new KeyboardScanCallback();
                bluetoothLeScanner.startScan(Arrays.asList(new ScanFilter[]{build}), build2, this.mScanCallback);
                KeyboardHandler keyboardHandler = this.mHandler;
                int i3 = this.mScanAttempt + 1;
                this.mScanAttempt = i3;
                this.mHandler.sendMessageDelayed(keyboardHandler.obtainMessage(10, i3, 0), 30000);
            }
        }
    }

    public final void start() {
        this.mContext = this.mContext;
        HandlerThread handlerThread = new HandlerThread("Keyboard", 10);
        handlerThread.start();
        this.mHandler = new KeyboardHandler(handlerThread.getLooper());
        this.mHandler.sendEmptyMessage(0);
    }

    public final void stopScanning() {
        if (this.mScanCallback != null) {
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
            Objects.requireNonNull(localBluetoothAdapter);
            BluetoothLeScanner bluetoothLeScanner = localBluetoothAdapter.mAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.mScanCallback);
            }
            this.mScanCallback = null;
        }
    }

    public KeyboardUI(Context context) {
        super(context);
    }
}
