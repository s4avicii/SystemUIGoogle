package com.android.systemui.doze;

import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.format.Formatter;
import android.util.IndentingPrintWriter;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.p006qs.QSPanel$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public final class DozeTriggers implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public static boolean sWakeDisplaySensorState = true;
    public final boolean mAllowPulseTriggers;
    public DozeTriggers$$ExternalSyntheticLambda1 mAodInterruptRunnable;
    public final AuthController mAuthController;
    public final BatteryController mBatteryController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final TriggerReceiver mBroadcastReceiver = new TriggerReceiver();
    public final AmbientDisplayConfiguration mConfig;
    public final Context mContext;
    public final DevicePostureController mDevicePostureController;
    public final DockEventListener mDockEventListener = new DockEventListener();
    public final DockManager mDockManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeSensors mDozeSensors;
    public C07801 mHostCallback = new DozeHost.Callback() {
        public final void onDozeSuppressedChanged(boolean z) {
            DozeMachine.State state;
            if (!DozeTriggers.this.mConfig.alwaysOnEnabled(-2) || z) {
                state = DozeMachine.State.DOZE;
            } else {
                state = DozeMachine.State.DOZE_AOD;
            }
            DozeTriggers.this.mMachine.requestState(state);
        }

        public final void onNotificationAlerted(ScreenDecorations$2$$ExternalSyntheticLambda0 screenDecorations$2$$ExternalSyntheticLambda0) {
            DozeTriggers dozeTriggers = DozeTriggers.this;
            Objects.requireNonNull(dozeTriggers);
            if (DozeMachine.DEBUG) {
                Log.d("DozeTriggers", "requestNotificationPulse");
            }
            if (!DozeTriggers.sWakeDisplaySensorState) {
                Log.d("DozeTriggers", "Wake display false. Pulse denied.");
                screenDecorations$2$$ExternalSyntheticLambda0.run();
                dozeTriggers.mDozeLog.tracePulseDropped("wakeDisplaySensor");
                return;
            }
            dozeTriggers.mNotificationPulseTime = SystemClock.elapsedRealtime();
            if (!dozeTriggers.mConfig.pulseOnNotificationEnabled(-2)) {
                screenDecorations$2$$ExternalSyntheticLambda0.run();
                dozeTriggers.mDozeLog.tracePulseDropped("pulseOnNotificationsDisabled");
            } else if (dozeTriggers.mDozeHost.isDozeSuppressed()) {
                screenDecorations$2$$ExternalSyntheticLambda0.run();
                dozeTriggers.mDozeLog.tracePulseDropped("dozeSuppressed");
            } else {
                dozeTriggers.requestPulse(1, false, screenDecorations$2$$ExternalSyntheticLambda0);
                DozeLog dozeLog = dozeTriggers.mDozeLog;
                Objects.requireNonNull(dozeLog);
                DozeLogger dozeLogger = dozeLog.mLogger;
                Objects.requireNonNull(dozeLogger);
                LogBuffer logBuffer = dozeLogger.buffer;
                LogLevel logLevel = LogLevel.INFO;
                DozeLogger$logNotificationPulse$2 dozeLogger$logNotificationPulse$2 = DozeLogger$logNotificationPulse$2.INSTANCE;
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    logBuffer.push(logBuffer.obtain("DozeLog", logLevel, dozeLogger$logNotificationPulse$2));
                }
                DozeLog.SummaryStats summaryStats = dozeLog.mNotificationPulseStats;
                Objects.requireNonNull(summaryStats);
                summaryStats.mCount++;
            }
        }

        public final void onPowerSaveChanged() {
            DozeMachine.State state = DozeMachine.State.DOZE;
            if (DozeTriggers.this.mDozeHost.isPowerSaveActive()) {
                DozeTriggers.this.mMachine.requestState(state);
            } else if (DozeTriggers.this.mMachine.getState() == state && DozeTriggers.this.mConfig.alwaysOnEnabled(-2)) {
                DozeTriggers.this.mMachine.requestState(DozeMachine.State.DOZE_AOD);
            }
        }
    };
    public final KeyguardStateController mKeyguardStateController;
    public DozeMachine mMachine;
    public final DelayableExecutor mMainExecutor;
    public long mNotificationPulseTime;
    public final ProximityCheck mProxCheck;
    public boolean mPulsePending;
    public final UiEventLogger mUiEventLogger;
    public final UiModeManager mUiModeManager;
    public final WakeLock mWakeLock;
    public boolean mWantProxSensor;
    public boolean mWantSensors;
    public boolean mWantTouchScreenSensors;

    public class DockEventListener implements DockManager.DockEventListener {
        public DockEventListener() {
        }

        public final void onEvent(int i) {
            if (DozeTriggers.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("dock event = ", i, "DozeTriggers");
            }
            if (i == 0) {
                DozeSensors dozeSensors = DozeTriggers.this.mDozeSensors;
                Objects.requireNonNull(dozeSensors);
                for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
                    if (triggerSensor.mRequiresTouchscreen && triggerSensor.mIgnoresSetting) {
                        triggerSensor.mIgnoresSetting = false;
                        triggerSensor.updateListening();
                    }
                }
            } else if (i == 1 || i == 2) {
                DozeSensors dozeSensors2 = DozeTriggers.this.mDozeSensors;
                Objects.requireNonNull(dozeSensors2);
                for (DozeSensors.TriggerSensor triggerSensor2 : dozeSensors2.mTriggerSensors) {
                    if (triggerSensor2.mRequiresTouchscreen && !triggerSensor2.mIgnoresSetting) {
                        triggerSensor2.mIgnoresSetting = true;
                        triggerSensor2.updateListening();
                    }
                }
            }
        }
    }

    public class TriggerReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public TriggerReceiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "Received pulse intent");
                }
                DozeTriggers.this.requestPulse(0, false, (ScreenDecorations$2$$ExternalSyntheticLambda0) null);
            }
            if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(intent.getAction())) {
                DozeTriggers.this.mMachine.requestState(DozeMachine.State.FINISH);
            }
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                DozeSensors dozeSensors = DozeTriggers.this.mDozeSensors;
                Objects.requireNonNull(dozeSensors);
                for (DozeSensors.TriggerSensor updateListening : dozeSensors.mTriggerSensors) {
                    updateListening.updateListening();
                }
            }
        }
    }

    public DozeTriggers(Context context, DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeParameters dozeParameters, AsyncSensorManager asyncSensorManager, WakeLock wakeLock, DockManager dockManager, ProximitySensor proximitySensor, ProximityCheck proximityCheck, DozeLog dozeLog, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, AuthController authController, DelayableExecutor delayableExecutor, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, DevicePostureController devicePostureController, BatteryController batteryController) {
        Context context2 = context;
        this.mContext = context2;
        this.mDozeHost = dozeHost;
        AmbientDisplayConfiguration ambientDisplayConfiguration2 = ambientDisplayConfiguration;
        this.mConfig = ambientDisplayConfiguration2;
        DozeParameters dozeParameters2 = dozeParameters;
        this.mDozeParameters = dozeParameters2;
        WakeLock wakeLock2 = wakeLock;
        this.mWakeLock = wakeLock2;
        this.mAllowPulseTriggers = true;
        DevicePostureController devicePostureController2 = devicePostureController;
        this.mDevicePostureController = devicePostureController2;
        this.mDozeSensors = new DozeSensors(asyncSensorManager, dozeParameters2, ambientDisplayConfiguration2, wakeLock2, new DozeTriggers$$ExternalSyntheticLambda0(this), new DozeTriggers$$ExternalSyntheticLambda4(this, 0), dozeLog, proximitySensor, secureSettings, authController, devicePostureController2);
        this.mUiModeManager = (UiModeManager) context2.getSystemService(UiModeManager.class);
        this.mDockManager = dockManager;
        this.mProxCheck = proximityCheck;
        this.mDozeLog = dozeLog;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mAuthController = authController;
        this.mMainExecutor = delayableExecutor;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mBatteryController = batteryController;
    }

    @VisibleForTesting
    public enum DozingUpdateUiEvent implements UiEventLogger.UiEventEnum {
        DOZING_UPDATE_NOTIFICATION(433),
        DOZING_UPDATE_SIGMOTION(434),
        DOZING_UPDATE_SENSOR_PICKUP(435),
        DOZING_UPDATE_SENSOR_DOUBLE_TAP(436),
        DOZING_UPDATE_SENSOR_LONG_SQUEEZE(437),
        DOZING_UPDATE_DOCKING(438),
        DOZING_UPDATE_SENSOR_WAKEUP(439),
        DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN(440),
        DOZING_UPDATE_SENSOR_TAP(441),
        DOZING_UPDATE_AUTH_TRIGGERED(657),
        DOZING_UPDATE_QUICK_PICKUP(708),
        DOZING_UPDATE_WAKE_TIMEOUT(794);
        
        private final int mId;

        /* access modifiers changed from: public */
        DozingUpdateUiEvent(int i) {
            this.mId = i;
        }

        public static DozingUpdateUiEvent fromReason(int i) {
            switch (i) {
                case 1:
                    return DOZING_UPDATE_NOTIFICATION;
                case 2:
                    return DOZING_UPDATE_SIGMOTION;
                case 3:
                    return DOZING_UPDATE_SENSOR_PICKUP;
                case 4:
                    return DOZING_UPDATE_SENSOR_DOUBLE_TAP;
                case 5:
                    return DOZING_UPDATE_SENSOR_LONG_SQUEEZE;
                case FalsingManager.VERSION /*6*/:
                    return DOZING_UPDATE_DOCKING;
                case 7:
                    return DOZING_UPDATE_SENSOR_WAKEUP;
                case 8:
                    return DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN;
                case 9:
                    return DOZING_UPDATE_SENSOR_TAP;
                case 10:
                    return DOZING_UPDATE_AUTH_TRIGGERED;
                case QSTileImpl.C1034H.STALE /*11*/:
                    return DOZING_UPDATE_QUICK_PICKUP;
                default:
                    return null;
            }
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final boolean canPulse() {
        if (this.mMachine.getState() == DozeMachine.State.DOZE || this.mMachine.getState() == DozeMachine.State.DOZE_AOD || this.mMachine.getState() == DozeMachine.State.DOZE_AOD_DOCKED) {
            return true;
        }
        return false;
    }

    public final void destroy() {
        DozeSensors dozeSensors = this.mDozeSensors;
        Objects.requireNonNull(dozeSensors);
        for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
            Objects.requireNonNull(triggerSensor);
            if (triggerSensor.mRequested) {
                triggerSensor.mRequested = false;
                triggerSensor.updateListening();
            }
        }
        dozeSensors.mProximitySensor.destroy();
        dozeSensors.mDevicePostureController.removeCallback(dozeSensors.mDevicePostureCallback);
        AuthController authController = dozeSensors.mAuthController;
        DozeSensors.C07792 r0 = dozeSensors.mAuthControllerCallback;
        Objects.requireNonNull(authController);
        authController.mCallbacks.remove(r0);
        ProximityCheck proximityCheck = this.mProxCheck;
        Objects.requireNonNull(proximityCheck);
        proximityCheck.mSensor.destroy();
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" mAodInterruptRunnable=");
        m.append(this.mAodInterruptRunnable);
        printWriter.println(m.toString());
        printWriter.print(" notificationPulseTime=");
        printWriter.println(Formatter.formatShortElapsedTime(this.mContext, this.mNotificationPulseTime));
        printWriter.println(" pulsePending=" + this.mPulsePending);
        printWriter.println("DozeSensors:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        DozeSensors dozeSensors = this.mDozeSensors;
        Objects.requireNonNull(dozeSensors);
        StringBuilder sb = new StringBuilder();
        sb.append("mListening=");
        StringBuilder m2 = DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(sb, dozeSensors.mListening, indentingPrintWriter, "mDevicePosture=");
        m2.append(DevicePostureController.devicePostureToString(dozeSensors.mDevicePosture));
        indentingPrintWriter.println(m2.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mListeningTouchScreenSensors=");
        StringBuilder m3 = DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(sb2, dozeSensors.mListeningTouchScreenSensors, indentingPrintWriter, "mSelectivelyRegisterProxSensors="), dozeSensors.mSelectivelyRegisterProxSensors, indentingPrintWriter, "mListeningProxSensors="), dozeSensors.mListeningProxSensors, indentingPrintWriter, "mScreenOffUdfpsEnabled="), dozeSensors.mScreenOffUdfpsEnabled, indentingPrintWriter, "mUdfpsEnrolled=");
        m3.append(dozeSensors.mUdfpsEnrolled);
        indentingPrintWriter.println(m3.toString());
        IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(indentingPrintWriter);
        indentingPrintWriter2.increaseIndent();
        for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
            StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Sensor: ");
            m4.append(triggerSensor.toString());
            indentingPrintWriter2.println(m4.toString());
        }
        StringBuilder m5 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ProxSensor: ");
        m5.append(dozeSensors.mProximitySensor.toString());
        indentingPrintWriter2.println(m5.toString());
    }

    public final void onScreenState(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        DozeSensors dozeSensors = this.mDozeSensors;
        Objects.requireNonNull(dozeSensors);
        ProximitySensor proximitySensor = dozeSensors.mProximitySensor;
        boolean z4 = false;
        if (i == 3 || i == 4 || i == 1) {
            z = true;
        } else {
            z = false;
        }
        proximitySensor.setSecondarySafe(z);
        if (i == 3 || i == 4 || i == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        DozeSensors dozeSensors2 = this.mDozeSensors;
        if (!this.mWantProxSensor || !z2) {
            z3 = false;
        } else {
            z3 = true;
        }
        dozeSensors2.setProxListening(z3);
        DozeSensors dozeSensors3 = this.mDozeSensors;
        boolean z5 = this.mWantSensors;
        boolean z6 = this.mWantTouchScreenSensors;
        Objects.requireNonNull(dozeSensors3);
        if (!dozeSensors3.mSelectivelyRegisterProxSensors || z2) {
            z4 = true;
        }
        if (!(dozeSensors3.mListening == z5 && dozeSensors3.mListeningTouchScreenSensors == z6 && dozeSensors3.mListeningProxSensors == z4)) {
            dozeSensors3.mListening = z5;
            dozeSensors3.mListeningTouchScreenSensors = z6;
            dozeSensors3.mListeningProxSensors = z4;
            dozeSensors3.updateListening();
        }
        DozeTriggers$$ExternalSyntheticLambda1 dozeTriggers$$ExternalSyntheticLambda1 = this.mAodInterruptRunnable;
        if (dozeTriggers$$ExternalSyntheticLambda1 != null && i == 2) {
            dozeTriggers$$ExternalSyntheticLambda1.run();
            this.mAodInterruptRunnable = null;
        }
    }

    @VisibleForTesting
    public void onSensor(int i, float f, float f2, float[] fArr) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        DozeLog.SummaryStats summaryStats;
        int i2 = i;
        float[] fArr2 = fArr;
        boolean z11 = false;
        if (i2 == 4) {
            z = true;
        } else {
            z = false;
        }
        if (i2 == 9) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (i2 == 3) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (i2 == 5) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (i2 == 7) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (i2 == 8) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (i2 == 10) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (i2 == 11) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 || ((z5 || z6) && fArr2 != null && fArr2.length > 0 && fArr2[0] != 0.0f)) {
            z9 = true;
        } else {
            z9 = false;
        }
        DozeMachine.State state = null;
        if (z5) {
            if (!this.mMachine.isExecutingTransition()) {
                state = this.mMachine.getState();
            }
            onWakeScreen(z9, state, i2);
        } else if (z4) {
            requestPulse(i2, true, (ScreenDecorations$2$$ExternalSyntheticLambda0) null);
        } else if (!z6 && !z8) {
            proximityCheckThenCall(new DozeTriggers$$ExternalSyntheticLambda5(this, i, z, z2, f, f2, z3, z7, fArr), true, i2);
        } else if (z9) {
            requestPulse(i2, true, (ScreenDecorations$2$$ExternalSyntheticLambda0) null);
        }
        if (z3) {
            if (this.mKeyguardStateController.isOccluded() || this.mBatteryController.isPluggedInWireless()) {
                z10 = true;
            } else {
                z10 = false;
            }
            if (!z10) {
                long elapsedRealtime = SystemClock.elapsedRealtime() - this.mNotificationPulseTime;
                DozeParameters dozeParameters = this.mDozeParameters;
                Objects.requireNonNull(dozeParameters);
                if (elapsedRealtime < ((long) dozeParameters.getInt("doze.pickup.vibration.threshold", C1777R.integer.doze_pickup_vibration_threshold))) {
                    z11 = true;
                }
                DozeLog dozeLog = this.mDozeLog;
                Objects.requireNonNull(dozeLog);
                DozeLogger dozeLogger = dozeLog.mLogger;
                Objects.requireNonNull(dozeLogger);
                LogBuffer logBuffer = dozeLogger.buffer;
                LogLevel logLevel = LogLevel.DEBUG;
                DozeLogger$logPickupWakeup$2 dozeLogger$logPickupWakeup$2 = DozeLogger$logPickupWakeup$2.INSTANCE;
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPickupWakeup$2);
                    obtain.bool1 = z11;
                    logBuffer.push(obtain);
                }
                if (z11) {
                    summaryStats = dozeLog.mPickupPulseNearVibrationStats;
                } else {
                    summaryStats = dozeLog.mPickupPulseNotNearVibrationStats;
                }
                Objects.requireNonNull(summaryStats);
                summaryStats.mCount++;
            }
        }
    }

    public final void onWakeScreen(boolean z, DozeMachine.State state, int i) {
        boolean z2;
        DozeLog dozeLog = this.mDozeLog;
        Objects.requireNonNull(dozeLog);
        DozeLogger dozeLogger = dozeLog.mLogger;
        Objects.requireNonNull(dozeLogger);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$logWakeDisplay$2 dozeLogger$logWakeDisplay$2 = DozeLogger$logWakeDisplay$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logWakeDisplay$2);
            obtain.bool1 = z;
            obtain.int1 = i;
            logBuffer.push(obtain);
        }
        sWakeDisplaySensorState = z;
        boolean z3 = false;
        if (z) {
            proximityCheckThenCall(new DozeTriggers$$ExternalSyntheticLambda7(this, state, i), false, i);
            return;
        }
        if (state == DozeMachine.State.DOZE_AOD_PAUSED) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (state == DozeMachine.State.DOZE_AOD_PAUSING) {
            z3 = true;
        }
        if (!z3 && !z2) {
            this.mMachine.requestState(DozeMachine.State.DOZE);
            this.mUiEventLogger.log(DozingUpdateUiEvent.DOZING_UPDATE_WAKE_TIMEOUT);
        }
    }

    public final void proximityCheckThenCall(Consumer<Boolean> consumer, boolean z, int i) {
        DozeSensors dozeSensors = this.mDozeSensors;
        Objects.requireNonNull(dozeSensors);
        Boolean isNear = dozeSensors.mProximitySensor.isNear();
        if (z) {
            consumer.accept((Object) null);
        } else if (isNear != null) {
            consumer.accept(isNear);
        } else {
            long uptimeMillis = SystemClock.uptimeMillis();
            ProximityCheck proximityCheck = this.mProxCheck;
            DozeTriggers$$ExternalSyntheticLambda6 dozeTriggers$$ExternalSyntheticLambda6 = new DozeTriggers$$ExternalSyntheticLambda6(this, uptimeMillis, i, consumer);
            Objects.requireNonNull(proximityCheck);
            if (!proximityCheck.mSensor.isLoaded()) {
                dozeTriggers$$ExternalSyntheticLambda6.accept((Object) null);
            } else {
                proximityCheck.mCallbacks.add(dozeTriggers$$ExternalSyntheticLambda6);
                if (!proximityCheck.mRegistered.getAndSet(true)) {
                    proximityCheck.mSensor.register(proximityCheck.mListener);
                    proximityCheck.mDelayableExecutor.executeDelayed(proximityCheck, 500);
                }
            }
            this.mWakeLock.acquire("DozeTriggers");
        }
    }

    public final void gentleWakeUp(int i) {
        Optional ofNullable = Optional.ofNullable(DozingUpdateUiEvent.fromReason(i));
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        Objects.requireNonNull(uiEventLogger);
        ofNullable.ifPresent(new DozeTriggers$$ExternalSyntheticLambda2(uiEventLogger, 0));
        if (this.mDozeParameters.getDisplayNeedsBlanking()) {
            this.mDozeHost.setAodDimmingScrim(1.0f);
        }
        DozeMachine dozeMachine = this.mMachine;
        Objects.requireNonNull(dozeMachine);
        dozeMachine.mDozeService.requestWakeUp();
    }

    public final void requestPulse(int i, boolean z, ScreenDecorations$2$$ExternalSyntheticLambda0 screenDecorations$2$$ExternalSyntheticLambda0) {
        DozeMachine.State state;
        boolean z2;
        Assert.isMainThread();
        this.mDozeHost.extendPulse(i);
        if (this.mMachine.isExecutingTransition()) {
            state = null;
        } else {
            state = this.mMachine.getState();
        }
        if (state == DozeMachine.State.DOZE_PULSING && i == 8) {
            this.mMachine.requestState(DozeMachine.State.DOZE_PULSING_BRIGHT);
        } else if (this.mPulsePending || !this.mAllowPulseTriggers || !canPulse()) {
            if (this.mAllowPulseTriggers) {
                this.mDozeLog.tracePulseDropped(this.mPulsePending, state, this.mDozeHost.isPulsingBlocked());
            }
            if (screenDecorations$2$$ExternalSyntheticLambda0 != null) {
                screenDecorations$2$$ExternalSyntheticLambda0.run();
            }
        } else {
            this.mPulsePending = true;
            DozeTriggers$$ExternalSyntheticLambda8 dozeTriggers$$ExternalSyntheticLambda8 = new DozeTriggers$$ExternalSyntheticLambda8(this, screenDecorations$2$$ExternalSyntheticLambda0, i);
            DozeParameters dozeParameters = this.mDozeParameters;
            Objects.requireNonNull(dozeParameters);
            if (!SystemProperties.getBoolean("doze.pulse.proxcheck", dozeParameters.mResources.getBoolean(C1777R.bool.doze_proximity_check_before_pulse)) || z) {
                z2 = true;
            } else {
                z2 = false;
            }
            proximityCheckThenCall(dozeTriggers$$ExternalSyntheticLambda8, z2, i);
            Optional ofNullable = Optional.ofNullable(DozingUpdateUiEvent.fromReason(i));
            UiEventLogger uiEventLogger = this.mUiEventLogger;
            Objects.requireNonNull(uiEventLogger);
            ofNullable.ifPresent(new QSPanel$$ExternalSyntheticLambda1(uiEventLogger, 1));
        }
    }

    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        switch (state2.ordinal()) {
            case 1:
                this.mAodInterruptRunnable = null;
                sWakeDisplaySensorState = true;
                TriggerReceiver triggerReceiver = this.mBroadcastReceiver;
                BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
                Objects.requireNonNull(triggerReceiver);
                if (!triggerReceiver.mRegistered) {
                    IntentFilter intentFilter = new IntentFilter("com.android.systemui.doze.pulse");
                    intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
                    intentFilter.addAction("android.intent.action.USER_SWITCHED");
                    broadcastDispatcher.registerReceiver(triggerReceiver, intentFilter);
                    triggerReceiver.mRegistered = true;
                }
                this.mDozeHost.addCallback(this.mHostCallback);
                this.mDockManager.addListener(this.mDockEventListener);
                DozeSensors dozeSensors = this.mDozeSensors;
                Objects.requireNonNull(dozeSensors);
                dozeSensors.mDebounceFrom = SystemClock.uptimeMillis();
                if (this.mUiModeManager.getCurrentModeType() == 3 || this.mDozeHost.isBlockingDoze() || !this.mDozeHost.isProvisioned()) {
                    this.mMachine.requestState(DozeMachine.State.FINISH);
                    break;
                }
            case 2:
            case 3:
                this.mAodInterruptRunnable = null;
                if (state2 != DozeMachine.State.DOZE) {
                    z = true;
                } else {
                    z = false;
                }
                this.mWantProxSensor = z;
                this.mWantSensors = true;
                this.mWantTouchScreenSensors = true;
                if (state2 == DozeMachine.State.DOZE_AOD && !sWakeDisplaySensorState) {
                    onWakeScreen(false, state2, 7);
                    break;
                }
            case 5:
            case FalsingManager.VERSION /*6*/:
                this.mWantProxSensor = true;
                this.mWantTouchScreenSensors = false;
                break;
            case 7:
                DozeSensors dozeSensors2 = this.mDozeSensors;
                Objects.requireNonNull(dozeSensors2);
                dozeSensors2.mDebounceFrom = SystemClock.uptimeMillis();
                break;
            case 8:
                TriggerReceiver triggerReceiver2 = this.mBroadcastReceiver;
                BroadcastDispatcher broadcastDispatcher2 = this.mBroadcastDispatcher;
                Objects.requireNonNull(triggerReceiver2);
                if (triggerReceiver2.mRegistered) {
                    broadcastDispatcher2.unregisterReceiver(triggerReceiver2);
                    triggerReceiver2.mRegistered = false;
                }
                this.mDozeHost.removeCallback(this.mHostCallback);
                this.mDockManager.removeListener(this.mDockEventListener);
                DozeSensors dozeSensors3 = this.mDozeSensors;
                Objects.requireNonNull(dozeSensors3);
                if (dozeSensors3.mListening || dozeSensors3.mListeningTouchScreenSensors) {
                    dozeSensors3.mListening = false;
                    dozeSensors3.mListeningTouchScreenSensors = false;
                    dozeSensors3.updateListening();
                }
                this.mDozeSensors.setProxListening(false);
                this.mWantSensors = false;
                this.mWantProxSensor = false;
                this.mWantTouchScreenSensors = false;
                break;
            case 9:
            case 10:
                this.mWantProxSensor = true;
                break;
            case QSTileImpl.C1034H.STALE /*11*/:
                this.mWantProxSensor = false;
                this.mWantTouchScreenSensors = false;
                break;
        }
        DozeSensors dozeSensors4 = this.mDozeSensors;
        boolean z2 = this.mWantSensors;
        boolean z3 = this.mWantTouchScreenSensors;
        Objects.requireNonNull(dozeSensors4);
        if (dozeSensors4.mListening != z2 || dozeSensors4.mListeningTouchScreenSensors != z3) {
            dozeSensors4.mListening = z2;
            dozeSensors4.mListeningTouchScreenSensors = z3;
            dozeSensors4.updateListening();
        }
    }

    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }
}
