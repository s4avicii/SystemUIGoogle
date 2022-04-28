package com.google.android.systemui.reversecharging;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtom;
import android.frameworks.stats.VendorAtomValue;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.Ringtone;
import android.nfc.IAppCallback;
import android.nfc.INfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Temperature;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.CallbackController;
import com.google.android.systemui.smartspace.SmartSpaceController$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import vendor.google.wireless_charger.V1_2.IWirelessCharger;

public final class ReverseChargingController extends BroadcastReceiver implements CallbackController<ReverseChargingChangeCallback> {
    public static final boolean DEBUG = Log.isLoggable("ReverseChargingControl", 3);
    public static final long DURATION_TO_ADVANCED_ACCESSORY_DEVICE_RECONNECTED_TIME_OUT;
    public static final long DURATION_TO_ADVANCED_PHONE_RECONNECTED_TIME_OUT;
    public static final long DURATION_TO_ADVANCED_PLUS_ACCESSORY_DEVICE_RECONNECTED_TIME_OUT;
    public static final long DURATION_TO_REVERSE_AC_TIME_OUT;
    public static final long DURATION_TO_REVERSE_RX_REMOVAL_TIME_OUT;
    public static final long DURATION_TO_REVERSE_TIME_OUT;
    public static final long DURATION_WAIT_NFC_SERVICE;
    public final ReverseChargingController$$ExternalSyntheticLambda3 mAccessoryDeviceRemovedTimeoutAlarmAction = new ReverseChargingController$$ExternalSyntheticLambda3(this);
    public final AlarmManager mAlarmManager;
    public final BatteryController.BatteryStateChangeCallback mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() {
        public final void onPowerSaveChanged(boolean z) {
            ReverseChargingController.this.mPowerSave = z;
        }

        public final void onWirelessChargingChanged(boolean z) {
            ReverseChargingController.this.mWirelessCharging = z;
        }
    };
    public final Executor mBgExecutor;
    public final BootCompleteCache mBootCompleteCache;
    public final ReverseChargingController$$ExternalSyntheticLambda5 mBootCompleteListener = new ReverseChargingController$$ExternalSyntheticLambda5(this);
    public boolean mBootCompleted;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mCacheIsReverseSupported;
    public final ArrayList<ReverseChargingChangeCallback> mChangeCallbacks = new ArrayList<>();
    public final ReverseChargingController$$ExternalSyntheticLambda4 mCheckNfcConflictWithUsbAudioAlarmAction = new ReverseChargingController$$ExternalSyntheticLambda4(this);
    public final Context mContext;
    public int mCurrentRtxMode = 0;
    public int mCurrentRtxReceiverType = 0;
    public final boolean mDoesNfcConflictWithUsbAudio;
    public final boolean mDoesNfcConflictWithWlc;
    public boolean mIsReverseSupported;
    public boolean mIsUsbPlugIn = false;
    public int mLevel;
    public final Executor mMainExecutor;
    public String mName;
    public final Binder mNfcInterfaceToken = new Binder();
    public final int[] mNfcUsbProductIds;
    public final int[] mNfcUsbVendorIds;
    public boolean mPluggedAc;
    public boolean mPowerSave;
    public boolean mProvidingBattery = false;
    public final ReverseChargingController$$ExternalSyntheticLambda2 mReconnectedTimeoutAlarmAction = new ReverseChargingController$$ExternalSyntheticLambda2(this);
    public boolean mRestoreUsbNfcPollingMode;
    public boolean mRestoreWlcNfcPollingMode;
    public boolean mReverse;
    public long mReverseStartTime = 0;
    public final Optional<ReverseWirelessCharger> mRtxChargerManagerOptional;
    public final SmartSpaceController$$ExternalSyntheticLambda0 mRtxFinishAlarmAction = new SmartSpaceController$$ExternalSyntheticLambda0(this, 1);
    public final ReverseChargingController$$ExternalSyntheticLambda1 mRtxFinishRxFullAlarmAction = new ReverseChargingController$$ExternalSyntheticLambda1(this);
    public int mRtxLevel;
    public IThermalEventListener mSkinThermalEventListener;
    public boolean mStartReconnected;
    public boolean mStopReverseAtAcUnplug;
    public final IThermalService mThermalService;
    public final Optional<UsbManager> mUsbManagerOptional;
    public boolean mUseRxRemovalTimeOut;
    public boolean mWirelessCharging;

    public interface ReverseChargingChangeCallback {
        void onReverseChargingChanged(boolean z, int i, String str) {
        }
    }

    public final class SkinThermalEventListener extends IThermalEventListener.Stub {
        public SkinThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            Log.i("ReverseChargingControl", "notifyThrottling(): thermal status=" + status);
            ReverseChargingController reverseChargingController = ReverseChargingController.this;
            if (reverseChargingController.mReverse && status >= 5) {
                reverseChargingController.setReverseStateInternal(false, 3);
            }
        }
    }

    public final void checkAndChangeNfcPollingAgainstUsbAudioDevice(boolean z, UsbDevice usbDevice) {
        boolean z2 = false;
        for (int i = 0; i < this.mNfcUsbVendorIds.length; i++) {
            if (usbDevice.getVendorId() == this.mNfcUsbVendorIds[i] && usbDevice.getProductId() == this.mNfcUsbProductIds[i]) {
                this.mRestoreUsbNfcPollingMode = !z;
                if (!this.mRestoreWlcNfcPollingMode && z) {
                    z2 = true;
                }
                enableNfcPollingMode(z2);
                return;
            }
        }
    }

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        DURATION_TO_REVERSE_TIME_OUT = timeUnit.toMillis(1);
        DURATION_TO_REVERSE_AC_TIME_OUT = timeUnit.toMillis(1);
        TimeUnit timeUnit2 = TimeUnit.SECONDS;
        DURATION_TO_REVERSE_RX_REMOVAL_TIME_OUT = timeUnit2.toMillis(30);
        DURATION_TO_ADVANCED_ACCESSORY_DEVICE_RECONNECTED_TIME_OUT = timeUnit2.toMillis(120);
        DURATION_TO_ADVANCED_PHONE_RECONNECTED_TIME_OUT = timeUnit2.toMillis(120);
        DURATION_TO_ADVANCED_PLUS_ACCESSORY_DEVICE_RECONNECTED_TIME_OUT = timeUnit2.toMillis(120);
        DURATION_WAIT_NFC_SERVICE = timeUnit2.toMillis(10);
    }

    public final void addCallback(Object obj) {
        ReverseChargingChangeCallback reverseChargingChangeCallback = (ReverseChargingChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(reverseChargingChangeCallback);
        }
        reverseChargingChangeCallback.onReverseChargingChanged(this.mReverse, this.mRtxLevel, this.mName);
    }

    public final void cancelRtxTimer(int i) {
        if (i == 0) {
            this.mAlarmManager.cancel(this.mRtxFinishAlarmAction);
        } else if (i == 1) {
            this.mAlarmManager.cancel(this.mRtxFinishRxFullAlarmAction);
        } else if (i == 3) {
            this.mAlarmManager.cancel(this.mReconnectedTimeoutAlarmAction);
        } else if (i == 4) {
            this.mAlarmManager.cancel(this.mAccessoryDeviceRemovedTimeoutAlarmAction);
        }
    }

    public final void enableNfcPollingMode(boolean z) {
        int i;
        if (z) {
            i = 0;
        } else {
            i = 4096;
        }
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("Change NFC reader mode to flags: ", i, "ReverseChargingControl");
        }
        try {
            INfcAdapter.Stub.asInterface(ServiceManager.getService("nfc")).setReaderMode(this.mNfcInterfaceToken, (IAppCallback) null, i, (Bundle) null);
        } catch (Exception e) {
            Log.e("ReverseChargingControl", "Could not change NFC reader mode, exception: " + e);
        }
    }

    public final void fireReverseChanged() {
        synchronized (this.mChangeCallbacks) {
            ArrayList arrayList = new ArrayList(this.mChangeCallbacks);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((ReverseChargingChangeCallback) arrayList.get(i)).onReverseChargingChanged(this.mReverse, this.mRtxLevel, this.mName);
            }
        }
    }

    public final void init(BatteryController batteryController) {
        batteryController.addCallback(this.mBatteryStateChangeCallback);
        this.mCacheIsReverseSupported = false;
        this.mReverse = false;
        this.mRtxLevel = -1;
        this.mName = null;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mBroadcastDispatcher.registerReceiver(this, intentFilter);
        this.mBootCompleteCache.addListener(this.mBootCompleteListener);
        if (this.mRtxChargerManagerOptional.isPresent()) {
            if (this.mRtxChargerManagerOptional.isPresent()) {
                this.mBgExecutor.execute(new ReverseChargingController$$ExternalSyntheticLambda8(this, false));
            } else {
                Log.i("ReverseChargingControl", "setRtxMode(): rtx not available");
            }
            ReverseWirelessCharger reverseWirelessCharger = this.mRtxChargerManagerOptional.get();
            ReverseChargingController$$ExternalSyntheticLambda0 reverseChargingController$$ExternalSyntheticLambda0 = new ReverseChargingController$$ExternalSyntheticLambda0(this);
            Objects.requireNonNull(reverseWirelessCharger);
            synchronized (reverseWirelessCharger.mLock) {
                reverseWirelessCharger.mIsDockPresentCallbacks.add(reverseChargingController$$ExternalSyntheticLambda0);
            }
            ReverseWirelessCharger reverseWirelessCharger2 = this.mRtxChargerManagerOptional.get();
            ReverseChargingController$$ExternalSyntheticLambda7 reverseChargingController$$ExternalSyntheticLambda7 = new ReverseChargingController$$ExternalSyntheticLambda7(this);
            Objects.requireNonNull(reverseWirelessCharger2);
            synchronized (reverseWirelessCharger2.mLock) {
                reverseWirelessCharger2.mRtxInformationCallbacks.add(reverseChargingController$$ExternalSyntheticLambda7);
            }
            ReverseWirelessCharger reverseWirelessCharger3 = this.mRtxChargerManagerOptional.get();
            ReverseChargingController$$ExternalSyntheticLambda6 reverseChargingController$$ExternalSyntheticLambda6 = new ReverseChargingController$$ExternalSyntheticLambda6(this);
            Objects.requireNonNull(reverseWirelessCharger3);
            synchronized (reverseWirelessCharger3.mLock) {
                reverseWirelessCharger3.mRtxStatusCallbacks.add(reverseChargingController$$ExternalSyntheticLambda6);
            }
            try {
                if (this.mSkinThermalEventListener == null) {
                    this.mSkinThermalEventListener = new SkinThermalEventListener();
                }
                this.mThermalService.registerThermalEventListenerWithType(this.mSkinThermalEventListener, 3);
            } catch (RemoteException e) {
                Log.e("ReverseChargingControl", "Could not register thermal event listener, exception: " + e);
            }
        }
    }

    public final boolean isLowBattery() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "advanced_battery_usage_amount", 2) * 5;
        if (this.mLevel > i) {
            return false;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("The battery is lower than threshold turn off reverse charging ! level : ");
        m.append(this.mLevel);
        m.append(", threshold : ");
        m.append(i);
        Log.w("ReverseChargingControl", m.toString());
        return true;
    }

    public final boolean isReverseSupported() {
        if (this.mCacheIsReverseSupported) {
            return this.mIsReverseSupported;
        }
        boolean z = false;
        if (this.mRtxChargerManagerOptional.isPresent()) {
            ReverseWirelessCharger reverseWirelessCharger = this.mRtxChargerManagerOptional.get();
            Objects.requireNonNull(reverseWirelessCharger);
            reverseWirelessCharger.initHALInterface();
            IWirelessCharger iWirelessCharger = reverseWirelessCharger.mWirelessCharger;
            if (iWirelessCharger != null) {
                try {
                    z = iWirelessCharger.isRtxSupported();
                } catch (Exception e) {
                    Log.i("ReverseWirelessCharger", "isRtxSupported fail: ", e);
                }
            }
            this.mIsReverseSupported = z;
            this.mCacheIsReverseSupported = true;
            return z;
        }
        if (DEBUG) {
            Log.d("ReverseChargingControl", "isReverseSupported(): mRtxChargerManagerOptional is not present!");
        }
        return false;
    }

    public final void logReverseStartEvent(int i) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("logReverseStartEvent: ", i, "ReverseChargingControl");
        }
        this.mReverseStartTime = SystemClock.uptimeMillis();
        int i2 = this.mLevel;
        VendorAtom createVendorAtom = ReverseChargingMetrics.createVendorAtom(2);
        createVendorAtom.atomId = 100037;
        createVendorAtom.values[0] = VendorAtomValue.intValue(i);
        createVendorAtom.values[1] = VendorAtomValue.intValue(i2);
        ReverseChargingMetrics.reportVendorAtom(createVendorAtom);
    }

    public final void logReverseStopEvent(int i) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("logReverseStopEvent: ", i, "ReverseChargingControl");
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int i2 = this.mLevel;
        VendorAtom createVendorAtom = ReverseChargingMetrics.createVendorAtom(3);
        createVendorAtom.atomId = 100038;
        createVendorAtom.values[0] = VendorAtomValue.intValue(i);
        createVendorAtom.values[1] = VendorAtomValue.intValue(i2);
        createVendorAtom.values[2] = new VendorAtomValue(1, Long.valueOf((uptimeMillis - this.mReverseStartTime) / 1000));
        ReverseChargingMetrics.reportVendorAtom(createVendorAtom);
    }

    public final void onAlarmRtxFinish(int i) {
        Log.i("ReverseChargingControl", "onAlarmRtxFinish(): rtx=0, reason: " + i);
        setReverseStateInternal(false, i);
    }

    public void onReverseStateChanged(Bundle bundle) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onReverseStateChanged(): rtx=");
        int i = 1;
        if (bundle.getInt("key_rtx_mode") != 1) {
            i = 0;
        }
        m.append(i);
        m.append(" bundle=");
        m.append(bundle.toString());
        m.append(" this=");
        m.append(this);
        Log.i("ReverseChargingControl", m.toString());
        this.mMainExecutor.execute(new ScreenshotController$$ExternalSyntheticLambda6(this, bundle, 6));
    }

    public void playSound(Ringtone ringtone) {
        if (ringtone != null) {
            ringtone.setStreamType(1);
            ringtone.play();
        }
    }

    public final void removeCallback(Object obj) {
        ReverseChargingChangeCallback reverseChargingChangeCallback = (ReverseChargingChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(reverseChargingChangeCallback);
        }
    }

    public final void setRtxTimer(int i, long j) {
        int i2 = i;
        if (i2 == 0) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, "ReverseChargingControl", this.mRtxFinishAlarmAction, (Handler) null);
        } else if (i2 == 1) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, "ReverseChargingControl", this.mRtxFinishRxFullAlarmAction, (Handler) null);
        } else if (i2 == 2) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, "ReverseChargingControl", this.mCheckNfcConflictWithUsbAudioAlarmAction, (Handler) null);
        } else if (i2 == 3) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, "ReverseChargingControl", this.mReconnectedTimeoutAlarmAction, (Handler) null);
        } else if (i2 == 4) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, "ReverseChargingControl", this.mAccessoryDeviceRemovedTimeoutAlarmAction, (Handler) null);
        }
    }

    public ReverseChargingController(Context context, BroadcastDispatcher broadcastDispatcher, Optional<ReverseWirelessCharger> optional, AlarmManager alarmManager, Optional<UsbManager> optional2, Executor executor, Executor executor2, BootCompleteCache bootCompleteCache, IThermalService iThermalService) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mRtxChargerManagerOptional = optional;
        this.mAlarmManager = alarmManager;
        this.mDoesNfcConflictWithWlc = context.getResources().getBoolean(C1777R.bool.config_nfc_conflict_with_wlc);
        this.mUsbManagerOptional = optional2;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mBootCompleteCache = bootCompleteCache;
        this.mThermalService = iThermalService;
        int[] intArray = context.getResources().getIntArray(C1777R.array.config_nfc_conflict_with_usb_audio_vendorid);
        this.mNfcUsbVendorIds = intArray;
        int[] intArray2 = context.getResources().getIntArray(C1777R.array.config_nfc_conflict_with_usb_audio_productid);
        this.mNfcUsbProductIds = intArray2;
        if (intArray.length == intArray2.length) {
            this.mDoesNfcConflictWithUsbAudio = context.getResources().getBoolean(C1777R.bool.config_nfc_conflict_with_usb_audio);
            return;
        }
        throw new IllegalStateException("VendorIds and ProductIds must be the same length");
    }

    public final void handleIntentForReverseCharging(Intent intent) {
        UsbDevice usbDevice;
        boolean z;
        boolean z2;
        boolean z3;
        if (isReverseSupported()) {
            String action = intent.getAction();
            boolean z4 = true;
            if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                boolean z5 = this.mPluggedAc;
                this.mLevel = (int) ((((float) intent.getIntExtra("level", 0)) * 100.0f) / ((float) intent.getIntExtra("scale", 100)));
                int intExtra = intent.getIntExtra("plugged", 0);
                if (intExtra == 1) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mPluggedAc = z3;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleIntentForReverseCharging(): rtx=");
                m.append(this.mReverse ? 1 : 0);
                m.append(" wlc=");
                m.append(this.mWirelessCharging ? 1 : 0);
                m.append(" plgac=");
                m.append(z5 ? 1 : 0);
                m.append(" ac=");
                m.append(this.mPluggedAc ? 1 : 0);
                m.append(" acrtx=");
                m.append(this.mStopReverseAtAcUnplug ? 1 : 0);
                m.append(" extra=");
                m.append(intExtra);
                m.append(" this=");
                m.append(this);
                Log.i("ReverseChargingControl", m.toString());
                boolean z6 = this.mReverse;
                if (z6 && this.mWirelessCharging) {
                    if (DEBUG) {
                        Log.d("ReverseChargingControl", "handleIntentForReverseCharging(): wireless charging, stop");
                    }
                    setReverseStateInternal(false, 102);
                } else if (z6 && z5 && !this.mPluggedAc && this.mStopReverseAtAcUnplug) {
                    if (DEBUG) {
                        Log.d("ReverseChargingControl", "handleIntentForReverseCharging(): wired charging, stop");
                    }
                    this.mStopReverseAtAcUnplug = false;
                    setReverseStateInternal(false, 106);
                } else if (z6 || z5 || !this.mPluggedAc) {
                    if (z6 && isLowBattery()) {
                        if (DEBUG) {
                            Log.d("ReverseChargingControl", "handleIntentForReverseCharging(): lower then battery threshold, stop");
                        }
                        setReverseStateInternal(false, 4);
                    }
                } else if (!this.mBootCompleted) {
                    Log.i("ReverseChargingControl", "skip auto turn on");
                } else {
                    if (DEBUG) {
                        Log.d("ReverseChargingControl", "handleIntentForReverseCharging(): wired charging, start");
                    }
                    this.mStopReverseAtAcUnplug = true;
                    setReverseStateInternal(true, 3);
                }
            } else if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                if (this.mReverse && this.mPowerSave) {
                    Log.i("ReverseChargingControl", "handleIntentForReverseCharging(): power save, stop");
                    setReverseStateInternal(false, 105);
                }
            } else if (TextUtils.equals(action, "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                if (usbDevice2 == null) {
                    Log.w("ReverseChargingControl", "handleIntentForReverseCharging() UsbDevice is null!");
                    this.mIsUsbPlugIn = false;
                    return;
                }
                if (this.mDoesNfcConflictWithUsbAudio) {
                    checkAndChangeNfcPollingAgainstUsbAudioDevice(false, usbDevice2);
                }
                int i = 0;
                while (true) {
                    if (i >= usbDevice2.getInterfaceCount()) {
                        z = false;
                        break;
                    } else if (usbDevice2.getInterface(i).getInterfaceClass() == 1) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= usbDevice2.getConfigurationCount()) {
                        z2 = false;
                        break;
                    } else if (usbDevice2.getConfiguration(i2).getMaxPower() < 100) {
                        z2 = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z && z2) {
                    z4 = false;
                }
                this.mIsUsbPlugIn = z4;
                if (this.mReverse && z4) {
                    setReverseStateInternal(false, 108);
                    Log.d("ReverseChargingControl", "handleIntentForReverseCharging(): stop reverse charging because USB-C plugin!");
                }
            } else if (TextUtils.equals(action, "android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                if (this.mDoesNfcConflictWithUsbAudio && (usbDevice = (UsbDevice) intent.getParcelableExtra("device")) != null) {
                    checkAndChangeNfcPollingAgainstUsbAudioDevice(true, usbDevice);
                }
                this.mIsUsbPlugIn = false;
            }
        }
    }

    public final void setReverseStateInternal(boolean z, int i) {
        if (isReverseSupported()) {
            Log.i("ReverseChargingControl", "setReverseStateInternal(): rtx=" + (z ? 1 : 0) + ",reason=" + i);
            if (!z || this.mReverse) {
                logReverseStopEvent(i);
            } else {
                logReverseStartEvent(i);
                if (this.mPowerSave) {
                    logReverseStopEvent(104);
                    return;
                } else if (isLowBattery()) {
                    logReverseStopEvent(100);
                    return;
                } else if (this.mIsUsbPlugIn) {
                    logReverseStopEvent(107);
                    return;
                }
            }
            if (z != this.mReverse) {
                if (z && this.mDoesNfcConflictWithWlc && !this.mRestoreWlcNfcPollingMode) {
                    enableNfcPollingMode(false);
                    this.mRestoreWlcNfcPollingMode = true;
                }
                this.mReverse = z;
                if (z) {
                    setRtxTimer(0, DURATION_TO_REVERSE_TIME_OUT);
                }
                if (this.mRtxChargerManagerOptional.isPresent()) {
                    this.mBgExecutor.execute(new ReverseChargingController$$ExternalSyntheticLambda8(this, z));
                } else {
                    Log.i("ReverseChargingControl", "setRtxMode(): rtx not available");
                }
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        handleIntentForReverseCharging(intent);
    }
}
