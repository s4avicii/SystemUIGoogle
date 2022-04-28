package com.android.systemui.doze;

import android.app.ActivityManager;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda1;
import com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda2;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public final class DozeSensors {
    public static final boolean DEBUG = DozeService.DEBUG;
    public static final UiEventLoggerImpl UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public final C07792 mAuthControllerCallback;
    public final AmbientDisplayConfiguration mConfig;
    public long mDebounceFrom;
    public int mDevicePosture;
    public final DozeSensors$$ExternalSyntheticLambda0 mDevicePostureCallback = new DozeSensors$$ExternalSyntheticLambda0(this);
    public final DevicePostureController mDevicePostureController;
    public final DozeLog mDozeLog;
    public final Handler mHandler;
    public boolean mListening;
    public boolean mListeningProxSensors;
    public boolean mListeningTouchScreenSensors;
    public final Consumer<Boolean> mProxCallback;
    public final ProximitySensor mProximitySensor;
    public final boolean mScreenOffUdfpsEnabled;
    public final SecureSettings mSecureSettings;
    public boolean mSelectivelyRegisterProxSensors;
    public final Callback mSensorCallback;
    public final AsyncSensorManager mSensorManager;
    public boolean mSettingRegistered;
    public final C07781 mSettingsObserver;
    public TriggerSensor[] mTriggerSensors;
    public boolean mUdfpsEnrolled;
    public final WakeLock mWakeLock;

    public interface Callback {
    }

    public enum DozeSensorsUiEvent implements UiEventLogger.UiEventEnum {
        ;
        
        private final int mId;

        /* access modifiers changed from: public */
        DozeSensorsUiEvent() {
            this.mId = 459;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public class PluginSensor extends TriggerSensor implements SensorManagerPlugin.SensorEventListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public long mDebounce;
        public final SensorManagerPlugin.Sensor mPluginSensor;

        public PluginSensor(SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, long j) {
            super(DozeSensors.this, (Sensor) null, str, z, i, false, false);
            this.mPluginSensor = sensor;
            this.mDebounce = j;
        }

        public static String triggerEventToString(SensorManagerPlugin.SensorEvent sensorEvent) {
            if (sensorEvent == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder("PluginTriggerEvent[");
            sb.append(sensorEvent.getSensor());
            sb.append(',');
            sb.append(sensorEvent.getVendorType());
            if (sensorEvent.getValues() != null) {
                for (float append : sensorEvent.getValues()) {
                    sb.append(',');
                    sb.append(append);
                }
            }
            sb.append(']');
            return sb.toString();
        }

        public final void onSensorChanged(SensorManagerPlugin.SensorEvent sensorEvent) {
            DozeSensors.this.mDozeLog.traceSensor(this.mPulseReason);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new DozeSensors$PluginSensor$$ExternalSyntheticLambda0(this, sensorEvent)));
        }

        public final String toString() {
            return "{mRegistered=" + this.mRegistered + ", mRequested=" + this.mRequested + ", mDisabled=" + false + ", mConfigured=" + this.mConfigured + ", mIgnoresSetting=" + this.mIgnoresSetting + ", mSensor=" + this.mPluginSensor + "}";
        }

        public final void updateListening() {
            if (this.mConfigured) {
                AsyncSensorManager asyncSensorManager = DozeSensors.this.mSensorManager;
                if (this.mRequested && ((enabledBySetting() || this.mIgnoresSetting) && !this.mRegistered)) {
                    SensorManagerPlugin.Sensor sensor = this.mPluginSensor;
                    Objects.requireNonNull(asyncSensorManager);
                    if (asyncSensorManager.mPlugins.isEmpty()) {
                        Log.w("AsyncSensorManager", "No plugins registered");
                    } else {
                        asyncSensorManager.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda1(asyncSensorManager, sensor, this, 0));
                    }
                    this.mRegistered = true;
                    if (DozeSensors.DEBUG) {
                        Log.d("DozeSensors", "registerPluginListener");
                    }
                } else if (this.mRegistered) {
                    SensorManagerPlugin.Sensor sensor2 = this.mPluginSensor;
                    Objects.requireNonNull(asyncSensorManager);
                    asyncSensorManager.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda2(asyncSensorManager, sensor2, this, 0));
                    this.mRegistered = false;
                    if (DozeSensors.DEBUG) {
                        Log.d("DozeSensors", "unregisterPluginListener");
                    }
                }
            }
        }
    }

    public class TriggerSensor extends TriggerEventListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mConfigured;
        public boolean mIgnoresSetting;
        public int mPosture;
        public final int mPulseReason;
        public boolean mRegistered;
        public final boolean mReportsTouchCoordinates;
        public boolean mRequested;
        public final boolean mRequiresProx;
        public final boolean mRequiresTouchscreen;
        public final Sensor[] mSensors;
        public final String mSetting;
        public final boolean mSettingDefault;

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3) {
            this(dozeSensors, sensor, str, true, z, i, z2, z3, false);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5) {
            this(new Sensor[]{sensor}, str, z, z2, i, z3, z4, z5, 0);
        }

        public final boolean enabledBySetting() {
            if (!DozeSensors.this.mConfig.enabled(-2)) {
                return false;
            }
            if (TextUtils.isEmpty(this.mSetting)) {
                return true;
            }
            if (DozeSensors.this.mSecureSettings.getIntForUser(this.mSetting, this.mSettingDefault ? 1 : 0, -2) != 0) {
                return true;
            }
            return false;
        }

        public final void onTrigger(TriggerEvent triggerEvent) {
            Sensor sensor = this.mSensors[this.mPosture];
            DozeSensors.this.mDozeLog.traceSensor(this.mPulseReason);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new DozeSensors$TriggerSensor$$ExternalSyntheticLambda0(this, triggerEvent, sensor)));
        }

        public void updateListening() {
            Sensor sensor = this.mSensors[this.mPosture];
            if (this.mConfigured && sensor != null) {
                if (!this.mRequested || (!enabledBySetting() && !this.mIgnoresSetting)) {
                    if (this.mRegistered) {
                        boolean cancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(this, sensor);
                        if (DozeSensors.DEBUG) {
                            Log.d("DozeSensors", "cancelTriggerSensor[" + sensor + "] " + cancelTriggerSensor);
                        }
                        this.mRegistered = false;
                    }
                } else if (!this.mRegistered) {
                    this.mRegistered = DozeSensors.this.mSensorManager.requestTriggerSensor(this, sensor);
                    if (DozeSensors.DEBUG) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("requestTriggerSensor[");
                        sb.append(sensor);
                        sb.append("] ");
                        KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(sb, this.mRegistered, "DozeSensors");
                    }
                } else if (DozeSensors.DEBUG) {
                    Log.d("DozeSensors", "requestTriggerSensor[" + sensor + "] already registered");
                }
            }
        }

        public TriggerSensor(Sensor[] sensorArr, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, int i2) {
            this.mSensors = sensorArr;
            this.mSetting = str;
            this.mSettingDefault = z;
            this.mConfigured = z2;
            this.mPulseReason = i;
            this.mReportsTouchCoordinates = z3;
            this.mRequiresTouchscreen = z4;
            this.mIgnoresSetting = false;
            this.mRequiresProx = z5;
            this.mPosture = i2;
        }

        public String toString() {
            StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("{", "mRegistered=");
            m.append(this.mRegistered);
            m.append(", mRequested=");
            m.append(this.mRequested);
            m.append(", mDisabled=");
            m.append(false);
            m.append(", mConfigured=");
            m.append(this.mConfigured);
            m.append(", mIgnoresSetting=");
            m.append(this.mIgnoresSetting);
            m.append(", mSensors=");
            m.append(Arrays.toString(this.mSensors));
            if (this.mSensors.length > 2) {
                m.append(", mPosture=");
                m.append(DevicePostureController.devicePostureToString(DozeSensors.this.mDevicePosture));
            }
            m.append("}");
            return m.toString();
        }
    }

    public DozeSensors(AsyncSensorManager asyncSensorManager, DozeParameters dozeParameters, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, DozeTriggers$$ExternalSyntheticLambda0 dozeTriggers$$ExternalSyntheticLambda0, DozeTriggers$$ExternalSyntheticLambda4 dozeTriggers$$ExternalSyntheticLambda4, DozeLog dozeLog, ProximitySensor proximitySensor, SecureSettings secureSettings, AuthController authController, DevicePostureController devicePostureController) {
        boolean z;
        boolean z2;
        AsyncSensorManager asyncSensorManager2 = asyncSensorManager;
        DozeParameters dozeParameters2 = dozeParameters;
        AmbientDisplayConfiguration ambientDisplayConfiguration2 = ambientDisplayConfiguration;
        ProximitySensor proximitySensor2 = proximitySensor;
        AuthController authController2 = authController;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mSettingsObserver = new ContentObserver(handler) {
            public final void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                if (i2 == ActivityManager.getCurrentUser()) {
                    for (TriggerSensor updateListening : DozeSensors.this.mTriggerSensors) {
                        updateListening.updateListening();
                    }
                }
            }
        };
        C07792 r2 = new AuthController.Callback() {
            public final void updateUdfpsEnrolled() {
                DozeSensors dozeSensors = DozeSensors.this;
                dozeSensors.mUdfpsEnrolled = dozeSensors.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
                for (TriggerSensor triggerSensor : DozeSensors.this.mTriggerSensors) {
                    int i = triggerSensor.mPulseReason;
                    boolean z = true;
                    if (11 == i) {
                        DozeSensors dozeSensors2 = DozeSensors.this;
                        Objects.requireNonNull(dozeSensors2);
                        if (!dozeSensors2.mUdfpsEnrolled || !dozeSensors2.mConfig.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser())) {
                            z = false;
                        }
                        if (triggerSensor.mConfigured != z) {
                            triggerSensor.mConfigured = z;
                            triggerSensor.updateListening();
                        }
                    } else if (10 == i) {
                        DozeSensors dozeSensors3 = DozeSensors.this;
                        Objects.requireNonNull(dozeSensors3);
                        if (!dozeSensors3.mUdfpsEnrolled || (!dozeSensors3.mConfig.alwaysOnEnabled(-2) && !dozeSensors3.mScreenOffUdfpsEnabled)) {
                            z = false;
                        }
                        if (triggerSensor.mConfigured != z) {
                            triggerSensor.mConfigured = z;
                            triggerSensor.updateListening();
                        }
                    }
                }
            }

            public final void onAllAuthenticatorsRegistered() {
                updateUdfpsEnrolled();
            }

            public final void onEnrollmentsChanged() {
                updateUdfpsEnrolled();
            }
        };
        this.mAuthControllerCallback = r2;
        this.mSensorManager = asyncSensorManager2;
        this.mConfig = ambientDisplayConfiguration2;
        this.mWakeLock = wakeLock;
        this.mProxCallback = dozeTriggers$$ExternalSyntheticLambda4;
        this.mSecureSettings = secureSettings;
        this.mSensorCallback = dozeTriggers$$ExternalSyntheticLambda0;
        this.mDozeLog = dozeLog;
        this.mProximitySensor = proximitySensor2;
        proximitySensor2.setTag("DozeSensors");
        Objects.requireNonNull(dozeParameters);
        boolean z3 = SystemProperties.getBoolean("doze.prox.selectively_register", dozeParameters2.mResources.getBoolean(C1777R.bool.doze_selectively_register_prox));
        this.mSelectivelyRegisterProxSensors = z3;
        boolean z4 = true;
        this.mListeningProxSensors = !z3;
        this.mScreenOffUdfpsEnabled = ambientDisplayConfiguration2.screenOffUdfpsEnabled(KeyguardUpdateMonitor.getCurrentUser());
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = devicePostureController.getDevicePosture();
        this.mAuthController = authController2;
        this.mUdfpsEnrolled = authController2.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
        authController2.addCallback(r2);
        TriggerSensor[] triggerSensorArr = new TriggerSensor[9];
        triggerSensorArr[0] = new TriggerSensor(this, asyncSensorManager2.getDefaultSensor(17), (String) null, SystemProperties.getBoolean("doze.pulse.sigmotion", dozeParameters2.mResources.getBoolean(C1777R.bool.doze_pulse_on_significant_motion)), 2, false, false);
        triggerSensorArr[1] = new TriggerSensor(this, asyncSensorManager2.getDefaultSensor(25), "doze_pulse_on_pick_up", true, ambientDisplayConfiguration.dozePickupSensorAvailable(), 3, false, false, false);
        triggerSensorArr[2] = new TriggerSensor(this, findSensor(asyncSensorManager2, ambientDisplayConfiguration.doubleTapSensorType(), (String) null), "doze_pulse_on_double_tap", true, 4, dozeParameters2.mResources.getBoolean(C1777R.bool.doze_double_tap_reports_touch_coordinates), true);
        String[] tapSensorTypeMapping = ambientDisplayConfiguration.tapSensorTypeMapping();
        Sensor[] sensorArr = new Sensor[5];
        HashMap hashMap = new HashMap();
        for (int i = 0; i < tapSensorTypeMapping.length; i++) {
            String str = tapSensorTypeMapping[i];
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, findSensor(this.mSensorManager, str, (String) null));
            }
            sensorArr[i] = (Sensor) hashMap.get(str);
        }
        int i2 = this.mDevicePosture;
        int[] intArray = dozeParameters2.mResources.getIntArray(C1777R.array.doze_single_tap_uses_prox_posture_mapping);
        boolean z5 = dozeParameters2.mResources.getBoolean(C1777R.bool.doze_single_tap_uses_prox);
        if (i2 >= intArray.length) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unsupported doze posture ", i2, "DozeParameters");
            z4 = z5;
        } else if (intArray[i2] == 0) {
            z4 = false;
        }
        triggerSensorArr[3] = new TriggerSensor(sensorArr, "doze_tap_gesture", true, true, 9, false, true, z4, this.mDevicePosture);
        triggerSensorArr[4] = new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.longPressSensorType(), (String) null), "doze_pulse_on_long_press", false, true, 5, true, true, dozeParameters2.mResources.getBoolean(C1777R.bool.doze_long_press_uses_prox));
        Sensor findSensor = findSensor(this.mSensorManager, ambientDisplayConfiguration.udfpsLongPressSensorType(), (String) null);
        if (!this.mUdfpsEnrolled || (!this.mConfig.alwaysOnEnabled(-2) && !this.mScreenOffUdfpsEnabled)) {
            z = false;
        } else {
            z = true;
        }
        triggerSensorArr[5] = new TriggerSensor(this, findSensor, "doze_pulse_on_auth", true, z, 10, true, true, dozeParameters2.mResources.getBoolean(C1777R.bool.doze_long_press_uses_prox));
        SensorManagerPlugin.Sensor sensor = new SensorManagerPlugin.Sensor(2);
        if (!this.mConfig.wakeScreenGestureAvailable() || !this.mConfig.alwaysOnEnabled(-2)) {
            z2 = false;
        } else {
            z2 = true;
        }
        triggerSensorArr[6] = new PluginSensor(sensor, "doze_wake_display_gesture", z2, 7, 0);
        boolean z6 = true;
        triggerSensorArr[7] = new PluginSensor(new SensorManagerPlugin.Sensor(1), "doze_wake_screen_gesture", this.mConfig.wakeScreenGestureAvailable(), 8, this.mConfig.getWakeLockScreenDebounce());
        triggerSensorArr[8] = new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.quickPickupSensorType(), (String) null), "doze_quick_pickup_gesture", true, (!this.mUdfpsEnrolled || !this.mConfig.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser())) ? false : z6, 11, false, false, false);
        this.mTriggerSensors = triggerSensorArr;
        setProxListening(false);
        this.mProximitySensor.register(new DozeSensors$$ExternalSyntheticLambda1(this));
        this.mDevicePostureController.addCallback(this.mDevicePostureCallback);
    }

    public final void setProxListening(boolean z) {
        if (this.mProximitySensor.isRegistered() && z) {
            this.mProximitySensor.alertListeners();
        } else if (z) {
            this.mProximitySensor.resume();
        } else {
            this.mProximitySensor.pause();
        }
    }

    public final void updateListening() {
        boolean z;
        boolean z2 = false;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            if (!this.mListening || ((triggerSensor.mRequiresTouchscreen && !this.mListeningTouchScreenSensors) || (triggerSensor.mRequiresProx && !this.mListeningProxSensors))) {
                z = false;
            } else {
                z = true;
            }
            Objects.requireNonNull(triggerSensor);
            if (triggerSensor.mRequested != z) {
                triggerSensor.mRequested = z;
                triggerSensor.updateListening();
            }
            if (z) {
                z2 = true;
            }
        }
        if (!z2) {
            this.mSecureSettings.unregisterContentObserver(this.mSettingsObserver);
        } else if (!this.mSettingRegistered) {
            for (TriggerSensor triggerSensor2 : this.mTriggerSensors) {
                Objects.requireNonNull(triggerSensor2);
                if (triggerSensor2.mConfigured && !TextUtils.isEmpty(triggerSensor2.mSetting)) {
                    DozeSensors dozeSensors = DozeSensors.this;
                    dozeSensors.mSecureSettings.registerContentObserverForUser(triggerSensor2.mSetting, (ContentObserver) dozeSensors.mSettingsObserver, -1);
                }
            }
        }
        this.mSettingRegistered = z2;
    }

    public static Sensor findSensor(AsyncSensorManager asyncSensorManager, String str, String str2) {
        boolean z = !TextUtils.isEmpty(str2);
        boolean z2 = !TextUtils.isEmpty(str);
        if (!z && !z2) {
            return null;
        }
        for (Sensor next : asyncSensorManager.getSensorList(-1)) {
            if ((!z || str2.equals(next.getName())) && (!z2 || str.equals(next.getStringType()))) {
                return next;
            }
        }
        return null;
    }
}
