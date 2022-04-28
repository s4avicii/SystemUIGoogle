package com.android.systemui.doze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

public final class DozeScreenBrightness extends BroadcastReceiver implements DozeMachine.Part, SensorEventListener {
    public final Context mContext;
    public int mDebugBrightnessBucket = -1;
    public int mDefaultDozeBrightness;
    public int mDevicePosture;
    public final C07761 mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public int mLastSensorValue = -1;
    public final Optional<Sensor>[] mLightSensorOptional;
    public boolean mPaused = false;
    public boolean mRegistered;
    public final int mScreenBrightnessDim;
    public final float mScreenBrightnessMinimumDimAmountFloat;
    public boolean mScreenOff = false;
    public final AsyncSensorManager mSensorManager;
    public final int[] mSensorToBrightness;
    public final int[] mSensorToScrimOpacity;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    public final void setLightSensorEnabled(boolean z) {
        Sensor sensor;
        boolean z2 = false;
        if (z && !this.mRegistered && isLightSensorPresent()) {
            AsyncSensorManager asyncSensorManager = this.mSensorManager;
            Optional<Sensor>[] optionalArr = this.mLightSensorOptional;
            if (optionalArr != null && this.mDevicePosture < optionalArr.length) {
                z2 = true;
            }
            if (!z2) {
                sensor = null;
            } else {
                sensor = optionalArr[this.mDevicePosture].get();
            }
            this.mRegistered = asyncSensorManager.registerListener(this, sensor, 3, this.mHandler);
            this.mLastSensorValue = -1;
        } else if (!z && this.mRegistered) {
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mLastSensorValue = -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0020  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateBrightnessAndReady(boolean r10) {
        /*
            r9 = this;
            r0 = -1
            if (r10 != 0) goto L_0x000b
            boolean r10 = r9.mRegistered
            if (r10 != 0) goto L_0x000b
            int r10 = r9.mDebugBrightnessBucket
            if (r10 == r0) goto L_0x0062
        L_0x000b:
            int r10 = r9.mDebugBrightnessBucket
            if (r10 != r0) goto L_0x0011
            int r10 = r9.mLastSensorValue
        L_0x0011:
            if (r10 < 0) goto L_0x001c
            int[] r1 = r9.mSensorToBrightness
            int r2 = r1.length
            if (r10 < r2) goto L_0x0019
            goto L_0x001c
        L_0x0019:
            r1 = r1[r10]
            goto L_0x001d
        L_0x001c:
            r1 = r0
        L_0x001d:
            r2 = 0
            if (r1 <= 0) goto L_0x0022
            r3 = 1
            goto L_0x0023
        L_0x0022:
            r3 = r2
        L_0x0023:
            if (r3 == 0) goto L_0x0043
            com.android.systemui.doze.DozeMachine$Service r4 = r9.mDozeService
            android.content.Context r5 = r9.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            r6 = 2147483647(0x7fffffff, float:NaN)
            r7 = -2
            java.lang.String r8 = "screen_brightness"
            int r5 = android.provider.Settings.System.getIntForUser(r5, r8, r6, r7)
            int r1 = java.lang.Math.min(r1, r5)
            int r1 = r9.clampToDimBrightnessForScreenOff(r1)
            r4.setDozeScreenBrightness(r1)
        L_0x0043:
            boolean r1 = r9.isLightSensorPresent()
            if (r1 != 0) goto L_0x004b
            r0 = r2
            goto L_0x0057
        L_0x004b:
            if (r3 == 0) goto L_0x0057
            if (r10 < 0) goto L_0x0057
            int[] r1 = r9.mSensorToScrimOpacity
            int r2 = r1.length
            if (r10 < r2) goto L_0x0055
            goto L_0x0057
        L_0x0055:
            r0 = r1[r10]
        L_0x0057:
            if (r0 < 0) goto L_0x0062
            com.android.systemui.doze.DozeHost r9 = r9.mDozeHost
            float r10 = (float) r0
            r0 = 1132396544(0x437f0000, float:255.0)
            float r10 = r10 / r0
            r9.setAodDimmingScrim(r10)
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.updateBrightnessAndReady(boolean):void");
    }

    static {
        SystemProperties.getBoolean("debug.aod_brightness", false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int clampToDimBrightnessForScreenOff(int r5) {
        /*
            r4 = this;
            com.android.systemui.statusbar.phone.DozeParameters r0 = r4.mDozeParameters
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r0 = r0.mScreenOffAnimationController
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList r0 = r0.animations
            boolean r1 = r0 instanceof java.util.Collection
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0019
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x0019
            goto L_0x0031
        L_0x0019:
            java.util.Iterator r0 = r0.iterator()
        L_0x001d:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0031
            java.lang.Object r1 = r0.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r1 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r1
            boolean r1 = r1.shouldPlayAnimation()
            if (r1 == 0) goto L_0x001d
            r0 = r2
            goto L_0x0032
        L_0x0031:
            r0 = r3
        L_0x0032:
            if (r0 != 0) goto L_0x0040
            com.android.systemui.keyguard.WakefulnessLifecycle r0 = r4.mWakefulnessLifecycle
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mWakefulness
            r1 = 3
            if (r0 != r1) goto L_0x003f
            goto L_0x0040
        L_0x003f:
            r2 = r3
        L_0x0040:
            if (r2 == 0) goto L_0x0063
            com.android.systemui.keyguard.WakefulnessLifecycle r0 = r4.mWakefulnessLifecycle
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mLastSleepReason
            r1 = 2
            if (r0 != r1) goto L_0x0063
            float r0 = r4.mScreenBrightnessMinimumDimAmountFloat
            r1 = 1132396544(0x437f0000, float:255.0)
            float r0 = r0 * r1
            double r0 = (double) r0
            double r0 = java.lang.Math.floor(r0)
            int r0 = (int) r0
            int r5 = r5 - r0
            int r4 = r4.mScreenBrightnessDim
            int r4 = java.lang.Math.min(r5, r4)
            int r4 = java.lang.Math.max(r3, r4)
            return r4
        L_0x0063:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.clampToDimBrightnessForScreenOff(int):int");
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("DozeScreenBrightness:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        StringBuilder m = DozeScreenBrightness$$ExternalSyntheticOutline0.m55m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("registered="), this.mRegistered, indentingPrintWriter, "posture=");
        m.append(DevicePostureController.devicePostureToString(this.mDevicePosture));
        indentingPrintWriter.println(m.toString());
    }

    public final boolean isLightSensorPresent() {
        boolean z;
        Optional<Sensor>[] optionalArr = this.mLightSensorOptional;
        if (optionalArr == null || this.mDevicePosture >= optionalArr.length) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            return optionalArr[this.mDevicePosture].isPresent();
        }
        if (optionalArr == null || !optionalArr[0].isPresent()) {
            return false;
        }
        return true;
    }

    public final void onReceive(Context context, Intent intent) {
        this.mDebugBrightnessBucket = intent.getIntExtra("brightness_bucket", -1);
        updateBrightnessAndReady(false);
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DozeScreenBrightness.onSensorChanged");
        m.append(sensorEvent.values[0]);
        Trace.beginSection(m.toString());
        try {
            if (this.mRegistered) {
                this.mLastSensorValue = (int) sensorEvent.values[0];
                updateBrightnessAndReady(false);
            }
        } finally {
            Trace.endSection();
        }
    }

    public DozeScreenBrightness(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog) {
        C07761 r0 = new DevicePostureController.Callback() {
            public final void onPostureChanged(int i) {
                DozeScreenBrightness dozeScreenBrightness = DozeScreenBrightness.this;
                int i2 = dozeScreenBrightness.mDevicePosture;
                if (i2 != i) {
                    Optional<Sensor>[] optionalArr = dozeScreenBrightness.mLightSensorOptional;
                    if (optionalArr.length >= 2 && i < optionalArr.length) {
                        Sensor sensor = optionalArr[i2].get();
                        Sensor sensor2 = DozeScreenBrightness.this.mLightSensorOptional[i].get();
                        if (Objects.equals(sensor, sensor2)) {
                            DozeScreenBrightness.this.mDevicePosture = i;
                            return;
                        }
                        DozeScreenBrightness dozeScreenBrightness2 = DozeScreenBrightness.this;
                        if (dozeScreenBrightness2.mRegistered) {
                            dozeScreenBrightness2.setLightSensorEnabled(false);
                            DozeScreenBrightness dozeScreenBrightness3 = DozeScreenBrightness.this;
                            dozeScreenBrightness3.mDevicePosture = i;
                            dozeScreenBrightness3.setLightSensorEnabled(true);
                        } else {
                            dozeScreenBrightness2.mDevicePosture = i;
                        }
                        DozeScreenBrightness dozeScreenBrightness4 = DozeScreenBrightness.this;
                        DozeLog dozeLog = dozeScreenBrightness4.mDozeLog;
                        int i3 = dozeScreenBrightness4.mDevicePosture;
                        dozeLog.tracePostureChanged(i3, "DozeScreenBrightness swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + DozeScreenBrightness.this.mRegistered);
                    }
                }
            }
        };
        this.mDevicePostureCallback = r0;
        this.mContext = context;
        this.mDozeService = service;
        this.mSensorManager = asyncSensorManager;
        this.mLightSensorOptional = optionalArr;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = devicePostureController.getDevicePosture();
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDozeParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mHandler = handler;
        this.mDozeLog = dozeLog;
        this.mScreenBrightnessMinimumDimAmountFloat = context.getResources().getFloat(17105102);
        this.mDefaultDozeBrightness = alwaysOnDisplayPolicy.defaultDozeBrightness;
        this.mScreenBrightnessDim = alwaysOnDisplayPolicy.dimBrightness;
        this.mSensorToBrightness = alwaysOnDisplayPolicy.screenBrightnessArray;
        this.mSensorToScrimOpacity = alwaysOnDisplayPolicy.dimmingScrimArray;
        devicePostureController.addCallback(r0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r9 != 11) goto L_0x0071;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r9, com.android.systemui.doze.DozeMachine.State r10) {
        /*
            r8 = this;
            int r9 = r10.ordinal()
            r0 = 0
            java.lang.String r1 = "screen_brightness"
            r2 = -2
            r3 = 2147483647(0x7fffffff, float:NaN)
            r4 = 0
            r5 = 1
            if (r9 == r5) goto L_0x0053
            r6 = 2
            if (r9 == r6) goto L_0x0031
            r0 = 3
            if (r9 == r0) goto L_0x002d
            r0 = 4
            if (r9 == r0) goto L_0x002d
            r0 = 8
            if (r9 == r0) goto L_0x0022
            r0 = 11
            if (r9 == r0) goto L_0x002d
            goto L_0x0071
        L_0x0022:
            r8.setLightSensorEnabled(r4)
            com.android.systemui.statusbar.policy.DevicePostureController r9 = r8.mDevicePostureController
            com.android.systemui.doze.DozeScreenBrightness$1 r0 = r8.mDevicePostureCallback
            r9.removeCallback(r0)
            goto L_0x0071
        L_0x002d:
            r8.setLightSensorEnabled(r5)
            goto L_0x0071
        L_0x0031:
            r8.setLightSensorEnabled(r4)
            com.android.systemui.doze.DozeMachine$Service r9 = r8.mDozeService
            int r6 = r8.mDefaultDozeBrightness
            android.content.Context r7 = r8.mContext
            android.content.ContentResolver r7 = r7.getContentResolver()
            int r1 = android.provider.Settings.System.getIntForUser(r7, r1, r3, r2)
            int r1 = java.lang.Math.min(r6, r1)
            int r1 = r8.clampToDimBrightnessForScreenOff(r1)
            r9.setDozeScreenBrightness(r1)
            com.android.systemui.doze.DozeHost r9 = r8.mDozeHost
            r9.setAodDimmingScrim(r0)
            goto L_0x0071
        L_0x0053:
            com.android.systemui.doze.DozeMachine$Service r9 = r8.mDozeService
            int r6 = r8.mDefaultDozeBrightness
            android.content.Context r7 = r8.mContext
            android.content.ContentResolver r7 = r7.getContentResolver()
            int r1 = android.provider.Settings.System.getIntForUser(r7, r1, r3, r2)
            int r1 = java.lang.Math.min(r6, r1)
            int r1 = r8.clampToDimBrightnessForScreenOff(r1)
            r9.setDozeScreenBrightness(r1)
            com.android.systemui.doze.DozeHost r9 = r8.mDozeHost
            r9.setAodDimmingScrim(r0)
        L_0x0071:
            com.android.systemui.doze.DozeMachine$State r9 = com.android.systemui.doze.DozeMachine.State.FINISH
            if (r10 == r9) goto L_0x0094
            com.android.systemui.doze.DozeMachine$State r9 = com.android.systemui.doze.DozeMachine.State.DOZE
            if (r10 != r9) goto L_0x007b
            r9 = r5
            goto L_0x007c
        L_0x007b:
            r9 = r4
        L_0x007c:
            boolean r0 = r8.mScreenOff
            if (r0 == r9) goto L_0x0085
            r8.mScreenOff = r9
            r8.updateBrightnessAndReady(r5)
        L_0x0085:
            com.android.systemui.doze.DozeMachine$State r9 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSED
            if (r10 != r9) goto L_0x008a
            goto L_0x008b
        L_0x008a:
            r5 = r4
        L_0x008b:
            boolean r9 = r8.mPaused
            if (r9 == r5) goto L_0x0094
            r8.mPaused = r5
            r8.updateBrightnessAndReady(r4)
        L_0x0094:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenBrightness.transitionTo(com.android.systemui.doze.DozeMachine$State, com.android.systemui.doze.DozeMachine$State):void");
    }
}
