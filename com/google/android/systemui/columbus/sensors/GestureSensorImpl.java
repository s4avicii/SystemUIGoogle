package com.google.android.systemui.columbus.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.google.android.systemui.columbus.ColumbusEvent;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: GestureSensorImpl.kt */
public final class GestureSensorImpl extends GestureSensor {
    public final Sensor accelerometer;
    public final Sensor gyroscope;
    public final Handler handler;
    public boolean isListening;
    public final long samplingIntervalNs;
    public final GestureSensorEventListener sensorEventListener = new GestureSensorEventListener();
    public final SensorManager sensorManager;
    public final TapRT tap;
    public final UiEventLogger uiEventLogger;

    /* compiled from: GestureSensorImpl.kt */
    public final class GestureSensorEventListener implements SensorEventListener {
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
            r5 = r4.this$0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void setListening(boolean r5) {
            /*
                r4 = this;
                r0 = 0
                if (r5 == 0) goto L_0x002c
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r5 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                android.hardware.Sensor r1 = r5.accelerometer
                if (r1 == 0) goto L_0x002c
                android.hardware.Sensor r2 = r5.gyroscope
                if (r2 == 0) goto L_0x002c
                android.hardware.SensorManager r2 = r5.sensorManager
                com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener r3 = r5.sensorEventListener
                android.os.Handler r5 = r5.handler
                r2.registerListener(r3, r1, r0, r5)
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r5 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                android.hardware.SensorManager r1 = r5.sensorManager
                com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener r2 = r5.sensorEventListener
                android.hardware.Sensor r3 = r5.gyroscope
                android.os.Handler r5 = r5.handler
                r1.registerListener(r2, r3, r0, r5)
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r4 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                r5 = 1
                java.util.Objects.requireNonNull(r4)
                r4.isListening = r5
                goto L_0x003c
            L_0x002c:
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r5 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                android.hardware.SensorManager r1 = r5.sensorManager
                com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener r5 = r5.sensorEventListener
                r1.unregisterListener(r5)
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r4 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                java.util.Objects.requireNonNull(r4)
                r4.isListening = r0
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.sensors.GestureSensorImpl.GestureSensorEventListener.setListening(boolean):void");
        }

        public GestureSensorEventListener() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: java.lang.Object[]} */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0084, code lost:
            if (r2.mGotAcc == false) goto L_0x0165;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:100:0x04b3  */
        /* JADX WARNING: Removed duplicated region for block: B:103:0x04c2  */
        /* JADX WARNING: Removed duplicated region for block: B:86:0x045b  */
        /* JADX WARNING: Removed duplicated region for block: B:91:0x047a  */
        /* JADX WARNING: Removed duplicated region for block: B:92:0x047d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onSensorChanged(android.hardware.SensorEvent r20) {
            /*
                r19 = this;
                r0 = r20
                if (r0 != 0) goto L_0x0006
                goto L_0x04cc
            L_0x0006:
                r1 = r19
                com.google.android.systemui.columbus.sensors.GestureSensorImpl r1 = com.google.android.systemui.columbus.sensors.GestureSensorImpl.this
                com.google.android.systemui.columbus.sensors.TapRT r2 = r1.tap
                android.hardware.Sensor r3 = r0.sensor
                int r3 = r3.getType()
                float[] r4 = r0.values
                r5 = 0
                r12 = r4[r5]
                r13 = 1
                r14 = r4[r13]
                r15 = 2
                r4 = r4[r15]
                long r10 = r0.timestamp
                long r6 = r1.samplingIntervalNs
                java.util.Objects.requireNonNull(r2)
                r8 = 6
                r2.mResult = r8
                r16 = 1242725376(0x4a127c00, float:2400000.0)
                r8 = 0
                r15 = 4
                if (r3 != r13) goto L_0x005c
                r2.mGotAcc = r13
                r17 = r14
                long r13 = r2.mSyncTime
                int r13 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
                if (r13 != 0) goto L_0x0053
                com.google.android.systemui.columbus.sensors.Resample3C r13 = r2.mResampleAcc
                java.util.Objects.requireNonNull(r13)
                r13.mRawLastX = r12
                r13.mRawLastT = r10
                r13.mResampledThisX = r12
                r13.mResampledLastT = r10
                r13.mInterval = r6
                r14 = r17
                r13.mRawLastY = r14
                r13.mRawLastZ = r4
                r13.mResampledThisY = r14
                r13.mResampledThisZ = r4
                goto L_0x0055
            L_0x0053:
                r14 = r17
            L_0x0055:
                boolean r6 = r2.mGotGyro
                if (r6 != 0) goto L_0x0088
                r7 = r5
                goto L_0x0448
            L_0x005c:
                if (r3 != r15) goto L_0x0088
                r13 = 1
                r2.mGotGyro = r13
                r17 = r6
                long r5 = r2.mSyncTime
                int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x0082
                com.google.android.systemui.columbus.sensors.Resample3C r5 = r2.mResampleGyro
                java.util.Objects.requireNonNull(r5)
                r5.mRawLastX = r12
                r5.mRawLastT = r10
                r5.mResampledThisX = r12
                r5.mResampledLastT = r10
                r6 = r17
                r5.mInterval = r6
                r5.mRawLastY = r14
                r5.mRawLastZ = r4
                r5.mResampledThisY = r14
                r5.mResampledThisZ = r4
            L_0x0082:
                boolean r5 = r2.mGotAcc
                if (r5 != 0) goto L_0x0088
                goto L_0x0165
            L_0x0088:
                long r5 = r2.mSyncTime
                int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x0168
                r2.mSyncTime = r10
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleAcc
                java.util.Objects.requireNonNull(r3)
                r3.mResampledLastT = r10
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleGyro
                long r4 = r2.mSyncTime
                java.util.Objects.requireNonNull(r3)
                r3.mResampledLastT = r4
                com.google.android.systemui.columbus.sensors.Slope3C r3 = r2.mSlopeAcc
                com.google.android.systemui.columbus.sensors.Resample3C r4 = r2.mResampleAcc
                com.google.android.systemui.columbus.sensors.Sample3C r4 = r4.getResults()
                com.google.android.systemui.columbus.sensors.Point3f r4 = r4.mPoint
                java.util.Objects.requireNonNull(r3)
                com.google.android.systemui.columbus.sensors.Slope1C r5 = r3.mSlopeX
                float r6 = r4.f140x
                java.util.Objects.requireNonNull(r5)
                r5.mRawLastX = r6
                com.google.android.systemui.columbus.sensors.Slope1C r5 = r3.mSlopeY
                float r6 = r4.f141y
                java.util.Objects.requireNonNull(r5)
                r5.mRawLastX = r6
                com.google.android.systemui.columbus.sensors.Slope1C r3 = r3.mSlopeZ
                float r4 = r4.f142z
                java.util.Objects.requireNonNull(r3)
                r3.mRawLastX = r4
                com.google.android.systemui.columbus.sensors.Slope3C r3 = r2.mSlopeGyro
                com.google.android.systemui.columbus.sensors.Resample3C r4 = r2.mResampleGyro
                com.google.android.systemui.columbus.sensors.Sample3C r4 = r4.getResults()
                com.google.android.systemui.columbus.sensors.Point3f r4 = r4.mPoint
                java.util.Objects.requireNonNull(r3)
                com.google.android.systemui.columbus.sensors.Slope1C r5 = r3.mSlopeX
                float r6 = r4.f140x
                java.util.Objects.requireNonNull(r5)
                r5.mRawLastX = r6
                com.google.android.systemui.columbus.sensors.Slope1C r5 = r3.mSlopeY
                float r6 = r4.f141y
                java.util.Objects.requireNonNull(r5)
                r5.mRawLastX = r6
                com.google.android.systemui.columbus.sensors.Slope1C r3 = r3.mSlopeZ
                float r4 = r4.f142z
                java.util.Objects.requireNonNull(r3)
                r3.mRawLastX = r4
                com.google.android.systemui.columbus.sensors.Lowpass3C r3 = r2.mLowpassAcc
                java.util.Objects.requireNonNull(r3)
                com.google.android.systemui.columbus.sensors.Lowpass1C r4 = r3.mLowpassX
                java.util.Objects.requireNonNull(r4)
                r5 = 0
                r4.mLastX = r5
                com.google.android.systemui.columbus.sensors.Lowpass1C r4 = r3.mLowpassY
                java.util.Objects.requireNonNull(r4)
                r4.mLastX = r5
                com.google.android.systemui.columbus.sensors.Lowpass1C r3 = r3.mLowpassZ
                java.util.Objects.requireNonNull(r3)
                r3.mLastX = r5
                com.google.android.systemui.columbus.sensors.Lowpass3C r3 = r2.mLowpassGyro
                java.util.Objects.requireNonNull(r3)
                com.google.android.systemui.columbus.sensors.Lowpass1C r4 = r3.mLowpassX
                java.util.Objects.requireNonNull(r4)
                r4.mLastX = r5
                com.google.android.systemui.columbus.sensors.Lowpass1C r4 = r3.mLowpassY
                java.util.Objects.requireNonNull(r4)
                r4.mLastX = r5
                com.google.android.systemui.columbus.sensors.Lowpass1C r3 = r3.mLowpassZ
                java.util.Objects.requireNonNull(r3)
                r3.mLastX = r5
                com.google.android.systemui.columbus.sensors.Highpass3C r3 = r2.mHighpassAcc
                java.util.Objects.requireNonNull(r3)
                com.google.android.systemui.columbus.sensors.Highpass1C r4 = r3.mHighpassX
                java.util.Objects.requireNonNull(r4)
                r4.mLastX = r5
                r4.mLastY = r5
                com.google.android.systemui.columbus.sensors.Highpass1C r4 = r3.mHighpassY
                java.util.Objects.requireNonNull(r4)
                r4.mLastX = r5
                r4.mLastY = r5
                com.google.android.systemui.columbus.sensors.Highpass1C r3 = r3.mHighpassZ
                java.util.Objects.requireNonNull(r3)
                r3.mLastX = r5
                r3.mLastY = r5
                com.google.android.systemui.columbus.sensors.Highpass3C r2 = r2.mHighpassGyro
                java.util.Objects.requireNonNull(r2)
                com.google.android.systemui.columbus.sensors.Highpass1C r3 = r2.mHighpassX
                java.util.Objects.requireNonNull(r3)
                r3.mLastX = r5
                r3.mLastY = r5
                com.google.android.systemui.columbus.sensors.Highpass1C r3 = r2.mHighpassY
                java.util.Objects.requireNonNull(r3)
                r3.mLastX = r5
                r3.mLastY = r5
                com.google.android.systemui.columbus.sensors.Highpass1C r2 = r2.mHighpassZ
                java.util.Objects.requireNonNull(r2)
                r2.mLastX = r5
                r2.mLastY = r5
            L_0x0165:
                r7 = 0
                goto L_0x0448
            L_0x0168:
                r5 = 1
                if (r3 != r5) goto L_0x0217
            L_0x016b:
                com.google.android.systemui.columbus.sensors.Resample3C r6 = r2.mResampleAcc
                r7 = r12
                r8 = r14
                r9 = r4
                r17 = r10
                boolean r3 = r6.update(r7, r8, r9, r10)
                if (r3 == 0) goto L_0x0165
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleAcc
                com.google.android.systemui.columbus.sensors.Sample3C r3 = r3.getResults()
                com.google.android.systemui.columbus.sensors.Point3f r3 = r3.mPoint
                com.google.android.systemui.columbus.sensors.Resample3C r5 = r2.mResampleAcc
                java.util.Objects.requireNonNull(r5)
                long r5 = r5.mInterval
                float r5 = (float) r5
                float r5 = r16 / r5
                com.google.android.systemui.columbus.sensors.Slope3C r6 = r2.mSlopeAcc
                com.google.android.systemui.columbus.sensors.Point3f r3 = r6.update(r3, r5)
                com.google.android.systemui.columbus.sensors.Lowpass3C r5 = r2.mLowpassAcc
                com.google.android.systemui.columbus.sensors.Point3f r3 = r5.update(r3)
                com.google.android.systemui.columbus.sensors.Highpass3C r5 = r2.mHighpassAcc
                com.google.android.systemui.columbus.sensors.Point3f r3 = r5.update(r3)
                java.util.ArrayDeque r5 = r2.mAccXs
                float r6 = r3.f140x
                java.lang.Float r6 = java.lang.Float.valueOf(r6)
                r5.add(r6)
                java.util.ArrayDeque r5 = r2.mAccYs
                float r6 = r3.f141y
                java.lang.Float r6 = java.lang.Float.valueOf(r6)
                r5.add(r6)
                java.util.ArrayDeque r5 = r2.mAccZs
                float r3 = r3.f142z
                java.lang.Float r3 = java.lang.Float.valueOf(r3)
                r5.add(r3)
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleAcc
                java.util.Objects.requireNonNull(r3)
                long r5 = r3.mInterval
                long r7 = r2.mSizeWindowNs
                long r7 = r7 / r5
                int r3 = (int) r7
            L_0x01c8:
                java.util.ArrayDeque r5 = r2.mAccXs
                int r5 = r5.size()
                if (r5 <= r3) goto L_0x01e0
                java.util.ArrayDeque r5 = r2.mAccXs
                r5.removeFirst()
                java.util.ArrayDeque r5 = r2.mAccYs
                r5.removeFirst()
                java.util.ArrayDeque r5 = r2.mAccZs
                r5.removeFirst()
                goto L_0x01c8
            L_0x01e0:
                com.google.android.systemui.columbus.sensors.PeakDetector r3 = r2.mPeakDetector
                java.util.ArrayDeque r5 = r2.mAccZs
                java.lang.Object r5 = r5.getLast()
                java.lang.Float r5 = (java.lang.Float) r5
                float r5 = r5.floatValue()
                com.google.android.systemui.columbus.sensors.Resample3C r6 = r2.mResampleAcc
                com.google.android.systemui.columbus.sensors.Sample3C r6 = r6.getResults()
                long r6 = r6.f143mT
                r3.update(r5, r6)
                com.google.android.systemui.columbus.sensors.PeakDetector r3 = r2.mValleyDetector
                java.util.ArrayDeque r5 = r2.mAccZs
                java.lang.Object r5 = r5.getLast()
                java.lang.Float r5 = (java.lang.Float) r5
                float r5 = r5.floatValue()
                float r5 = -r5
                com.google.android.systemui.columbus.sensors.Resample3C r6 = r2.mResampleAcc
                com.google.android.systemui.columbus.sensors.Sample3C r6 = r6.getResults()
                long r6 = r6.f143mT
                r3.update(r5, r6)
                r10 = r17
                goto L_0x016b
            L_0x0217:
                r17 = r10
                if (r3 != r15) goto L_0x0165
            L_0x021b:
                com.google.android.systemui.columbus.sensors.Resample3C r6 = r2.mResampleGyro
                r7 = r12
                r8 = r14
                r9 = r4
                r10 = r17
                boolean r3 = r6.update(r7, r8, r9, r10)
                if (r3 == 0) goto L_0x0439
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleGyro
                com.google.android.systemui.columbus.sensors.Sample3C r3 = r3.getResults()
                com.google.android.systemui.columbus.sensors.Point3f r3 = r3.mPoint
                com.google.android.systemui.columbus.sensors.Resample3C r5 = r2.mResampleGyro
                java.util.Objects.requireNonNull(r5)
                long r5 = r5.mInterval
                float r5 = (float) r5
                float r5 = r16 / r5
                com.google.android.systemui.columbus.sensors.Slope3C r6 = r2.mSlopeGyro
                com.google.android.systemui.columbus.sensors.Point3f r3 = r6.update(r3, r5)
                com.google.android.systemui.columbus.sensors.Lowpass3C r5 = r2.mLowpassGyro
                com.google.android.systemui.columbus.sensors.Point3f r3 = r5.update(r3)
                com.google.android.systemui.columbus.sensors.Highpass3C r5 = r2.mHighpassGyro
                com.google.android.systemui.columbus.sensors.Point3f r3 = r5.update(r3)
                java.util.ArrayDeque r5 = r2.mGyroXs
                float r6 = r3.f140x
                java.lang.Float r6 = java.lang.Float.valueOf(r6)
                r5.add(r6)
                java.util.ArrayDeque r5 = r2.mGyroYs
                float r6 = r3.f141y
                java.lang.Float r6 = java.lang.Float.valueOf(r6)
                r5.add(r6)
                java.util.ArrayDeque r5 = r2.mGyroZs
                float r3 = r3.f142z
                java.lang.Float r3 = java.lang.Float.valueOf(r3)
                r5.add(r3)
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleGyro
                java.util.Objects.requireNonNull(r3)
                long r5 = r3.mInterval
                long r7 = r2.mSizeWindowNs
                long r7 = r7 / r5
                int r3 = (int) r7
            L_0x0278:
                java.util.ArrayDeque r5 = r2.mGyroXs
                int r5 = r5.size()
                if (r5 <= r3) goto L_0x0290
                java.util.ArrayDeque r5 = r2.mGyroXs
                r5.removeFirst()
                java.util.ArrayDeque r5 = r2.mGyroYs
                r5.removeFirst()
                java.util.ArrayDeque r5 = r2.mGyroZs
                r5.removeFirst()
                goto L_0x0278
            L_0x0290:
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleAcc
                java.util.Objects.requireNonNull(r3)
                long r5 = r3.mInterval
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleAcc
                com.google.android.systemui.columbus.sensors.Sample3C r3 = r3.getResults()
                long r7 = r3.f143mT
                com.google.android.systemui.columbus.sensors.Resample3C r3 = r2.mResampleGyro
                com.google.android.systemui.columbus.sensors.Sample3C r3 = r3.getResults()
                long r9 = r3.f143mT
                long r7 = r7 - r9
                long r7 = r7 / r5
                int r3 = (int) r7
                com.google.android.systemui.columbus.sensors.PeakDetector r5 = r2.mPeakDetector
                java.util.Objects.requireNonNull(r5)
                int r5 = r5.mPeakId
                r6 = 0
                int r5 = java.lang.Math.max(r6, r5)
                com.google.android.systemui.columbus.sensors.PeakDetector r7 = r2.mValleyDetector
                java.util.Objects.requireNonNull(r7)
                int r7 = r7.mPeakId
                int r7 = java.lang.Math.max(r6, r7)
                com.google.android.systemui.columbus.sensors.PeakDetector r6 = r2.mPeakDetector
                java.util.Objects.requireNonNull(r6)
                float r6 = r6.mAmplitude
                com.google.android.systemui.columbus.sensors.PeakDetector r8 = r2.mValleyDetector
                java.util.Objects.requireNonNull(r8)
                float r8 = r8.mAmplitude
                int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r6 <= 0) goto L_0x02d4
                goto L_0x02d5
            L_0x02d4:
                r5 = r7
            L_0x02d5:
                r6 = 12
                if (r5 <= r6) goto L_0x02dc
                r7 = 1
                r2.mWasPeakApproaching = r7
            L_0x02dc:
                int r7 = r5 + -6
                int r3 = r7 - r3
                java.util.ArrayDeque r8 = r2.mAccZs
                int r8 = r8.size()
                if (r7 < 0) goto L_0x0436
                if (r3 < 0) goto L_0x0436
                int r9 = r2.mSizeFeatureWindow
                int r10 = r7 + r9
                if (r10 > r8) goto L_0x0436
                int r9 = r9 + r3
                if (r9 <= r8) goto L_0x02f5
                goto L_0x0436
            L_0x02f5:
                boolean r8 = r2.mWasPeakApproaching
                if (r8 == 0) goto L_0x0436
                if (r5 > r6) goto L_0x0436
                r5 = 0
                r2.mWasPeakApproaching = r5
                com.google.android.systemui.columbus.sensors.PeakDetector r6 = r2.mPeakDetector
                java.util.Objects.requireNonNull(r6)
                com.google.android.systemui.columbus.sensors.PeakDetector r6 = r2.mValleyDetector
                java.util.Objects.requireNonNull(r6)
                java.util.ArrayDeque r6 = r2.mAccXs
                r2.addToFeatureVector(r6, r7, r5)
                java.util.ArrayDeque r5 = r2.mAccYs
                int r6 = r2.mSizeFeatureWindow
                r2.addToFeatureVector(r5, r7, r6)
                java.util.ArrayDeque r5 = r2.mAccZs
                int r6 = r2.mSizeFeatureWindow
                r8 = 2
                int r6 = r6 * r8
                r2.addToFeatureVector(r5, r7, r6)
                java.util.ArrayDeque r5 = r2.mGyroXs
                int r6 = r2.mSizeFeatureWindow
                r7 = 3
                int r6 = r6 * r7
                r2.addToFeatureVector(r5, r3, r6)
                java.util.ArrayDeque r5 = r2.mGyroYs
                int r6 = r2.mSizeFeatureWindow
                int r6 = r6 * r15
                r2.addToFeatureVector(r5, r3, r6)
                java.util.ArrayDeque r5 = r2.mGyroZs
                int r6 = r2.mSizeFeatureWindow
                int r6 = r6 * 5
                r2.addToFeatureVector(r5, r3, r6)
                java.util.ArrayList r3 = new java.util.ArrayList
                java.util.ArrayList<java.lang.Float> r5 = r2.mFeatureVector
                r6 = 100
                r8 = 150(0x96, float:2.1E-43)
                java.util.List r5 = r5.subList(r6, r8)
                r3.<init>(r5)
                java.util.ArrayList<java.lang.Float> r3 = r2.mFeatureVector
                r5 = 1092616192(0x41200000, float:10.0)
                int r6 = r3.size()
                r8 = 2
                int r6 = r6 / r8
            L_0x0350:
                int r8 = r3.size()
                if (r6 >= r8) goto L_0x036b
                java.lang.Object r8 = r3.get(r6)
                java.lang.Float r8 = (java.lang.Float) r8
                float r8 = r8.floatValue()
                float r8 = r8 * r5
                java.lang.Float r8 = java.lang.Float.valueOf(r8)
                r3.set(r6, r8)
                int r6 = r6 + 1
                goto L_0x0350
            L_0x036b:
                r2.mFeatureVector = r3
                com.google.android.systemui.columbus.sensors.TfClassifier r5 = r2.mClassifier
                java.util.Objects.requireNonNull(r5)
                java.lang.Class<float> r6 = float.class
                org.tensorflow.lite.Interpreter r8 = r5.mInterpreter
                if (r8 != 0) goto L_0x0380
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                r7 = 0
                goto L_0x03ff
            L_0x0380:
                int r8 = r3.size()
                int[] r9 = new int[r15]
                r10 = 1
                r9[r7] = r10
                r7 = 2
                r9[r7] = r10
                r9[r10] = r8
                r7 = 0
                r9[r7] = r10
                java.lang.Object r8 = java.lang.reflect.Array.newInstance(r6, r9)
                float[][][][] r8 = (float[][][][]) r8
                r9 = r7
            L_0x0398:
                int r10 = r3.size()
                if (r9 >= r10) goto L_0x03b3
                java.lang.Object r10 = r3.get(r9)
                java.lang.Float r10 = (java.lang.Float) r10
                float r10 = r10.floatValue()
                r11 = r8[r7]
                r11 = r11[r9]
                r11 = r11[r7]
                r11[r7] = r10
                int r9 = r9 + 1
                goto L_0x0398
            L_0x03b3:
                r9 = 1
                java.lang.Object[] r3 = new java.lang.Object[r9]
                r3[r7] = r8
                java.util.HashMap r8 = new java.util.HashMap
                r8.<init>()
                r9 = 2
                int[] r10 = new int[r9]
                r10 = {1, 7} // fill-array
                java.lang.Object r6 = java.lang.reflect.Array.newInstance(r6, r10)
                float[][] r6 = (float[][]) r6
                java.lang.Integer r9 = java.lang.Integer.valueOf(r7)
                r8.put(r9, r6)
                org.tensorflow.lite.Interpreter r5 = r5.mInterpreter
                r5.runForMultipleInputsOutputs(r3, r8)
                java.lang.Integer r3 = java.lang.Integer.valueOf(r7)
                java.lang.Object r3 = r8.get(r3)
                float[][] r3 = (float[][]) r3
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                r8 = 7
                r9 = r7
            L_0x03eb:
                if (r9 >= r8) goto L_0x03fb
                r10 = r3[r7]
                r10 = r10[r9]
                java.lang.Float r10 = java.lang.Float.valueOf(r10)
                r6.add(r10)
                int r9 = r9 + 1
                goto L_0x03eb
            L_0x03fb:
                r5.add(r6)
                r3 = r5
            L_0x03ff:
                boolean r5 = r3.isEmpty()
                if (r5 != 0) goto L_0x021b
                java.lang.Object r3 = r3.get(r7)
                java.util.ArrayList r3 = (java.util.ArrayList) r3
                r5 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
                r6 = r7
                r8 = r6
            L_0x0410:
                int r9 = r3.size()
                if (r6 >= r9) goto L_0x0432
                java.lang.Object r9 = r3.get(r6)
                java.lang.Float r9 = (java.lang.Float) r9
                float r9 = r9.floatValue()
                int r9 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                if (r9 >= 0) goto L_0x042f
                java.lang.Object r5 = r3.get(r6)
                java.lang.Float r5 = (java.lang.Float) r5
                float r5 = r5.floatValue()
                r8 = r6
            L_0x042f:
                int r6 = r6 + 1
                goto L_0x0410
            L_0x0432:
                r2.mResult = r8
                goto L_0x021b
            L_0x0436:
                r7 = 0
                goto L_0x021b
            L_0x0439:
                r7 = 0
                int r3 = r2.mResult
                r4 = 1
                if (r3 != r4) goto L_0x0448
                java.util.ArrayDeque r2 = r2.mTimestampsBackTap
                java.lang.Long r3 = java.lang.Long.valueOf(r17)
                r2.addLast(r3)
            L_0x0448:
                com.google.android.systemui.columbus.sensors.TapRT r2 = r1.tap
                long r3 = r0.timestamp
                java.util.Objects.requireNonNull(r2)
                java.util.ArrayDeque r0 = r2.mTimestampsBackTap
                java.util.Iterator r0 = r0.iterator()
            L_0x0455:
                boolean r5 = r0.hasNext()
                if (r5 == 0) goto L_0x0472
                java.lang.Object r5 = r0.next()
                java.lang.Long r5 = (java.lang.Long) r5
                long r5 = r5.longValue()
                long r5 = r3 - r5
                r8 = 500000000(0x1dcd6500, double:2.47032823E-315)
                int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
                if (r5 <= 0) goto L_0x0455
                r0.remove()
                goto L_0x0455
            L_0x0472:
                java.util.ArrayDeque r0 = r2.mTimestampsBackTap
                boolean r0 = r0.isEmpty()
                if (r0 == 0) goto L_0x047d
                r5 = r7
                r0 = 1
                goto L_0x04b1
            L_0x047d:
                java.util.ArrayDeque r0 = r2.mTimestampsBackTap
                java.util.Iterator r0 = r0.iterator()
            L_0x0483:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x04af
                java.util.ArrayDeque r3 = r2.mTimestampsBackTap
                java.lang.Object r3 = r3.getLast()
                java.lang.Long r3 = (java.lang.Long) r3
                long r3 = r3.longValue()
                java.lang.Object r5 = r0.next()
                java.lang.Long r5 = (java.lang.Long) r5
                long r5 = r5.longValue()
                long r3 = r3 - r5
                r5 = 100000000(0x5f5e100, double:4.94065646E-316)
                int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r3 <= 0) goto L_0x0483
                java.util.ArrayDeque r0 = r2.mTimestampsBackTap
                r0.clear()
                r0 = 1
                r5 = 2
                goto L_0x04b1
            L_0x04af:
                r0 = 1
                r5 = 1
            L_0x04b1:
                if (r5 == r0) goto L_0x04c2
                r0 = 2
                if (r5 == r0) goto L_0x04b7
                goto L_0x04cc
            L_0x04b7:
                android.os.Handler r0 = r1.handler
                com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$2 r2 = new com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$2
                r2.<init>(r1)
                r0.post(r2)
                goto L_0x04cc
            L_0x04c2:
                android.os.Handler r0 = r1.handler
                com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1 r2 = new com.google.android.systemui.columbus.sensors.GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1
                r2.<init>(r1)
                r0.post(r2)
            L_0x04cc:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.sensors.GestureSensorImpl.GestureSensorEventListener.onSensorChanged(android.hardware.SensorEvent):void");
        }
    }

    public final void startListening() {
        this.sensorEventListener.setListening(true);
        TapRT tapRT = this.tap;
        Objects.requireNonNull(tapRT);
        tapRT.mLowpassAcc.setPara(1.0f);
        TapRT tapRT2 = this.tap;
        Objects.requireNonNull(tapRT2);
        tapRT2.mLowpassGyro.setPara(1.0f);
        TapRT tapRT3 = this.tap;
        Objects.requireNonNull(tapRT3);
        tapRT3.mHighpassAcc.setPara();
        TapRT tapRT4 = this.tap;
        Objects.requireNonNull(tapRT4);
        tapRT4.mHighpassGyro.setPara();
        TapRT tapRT5 = this.tap;
        Objects.requireNonNull(tapRT5);
        PeakDetector peakDetector = tapRT5.mPeakDetector;
        Objects.requireNonNull(peakDetector);
        peakDetector.mMinNoiseTolerate = 0.03f;
        TapRT tapRT6 = this.tap;
        Objects.requireNonNull(tapRT6);
        PeakDetector peakDetector2 = tapRT6.mPeakDetector;
        Objects.requireNonNull(peakDetector2);
        peakDetector2.mWindowSize = 64;
        TapRT tapRT7 = this.tap;
        Objects.requireNonNull(tapRT7);
        PeakDetector peakDetector3 = tapRT7.mValleyDetector;
        Objects.requireNonNull(peakDetector3);
        peakDetector3.mMinNoiseTolerate = 0.015f;
        TapRT tapRT8 = this.tap;
        Objects.requireNonNull(tapRT8);
        PeakDetector peakDetector4 = tapRT8.mValleyDetector;
        Objects.requireNonNull(peakDetector4);
        peakDetector4.mWindowSize = 64;
        TapRT tapRT9 = this.tap;
        Objects.requireNonNull(tapRT9);
        tapRT9.mAccXs.clear();
        tapRT9.mAccYs.clear();
        tapRT9.mAccZs.clear();
        tapRT9.mGyroXs.clear();
        tapRT9.mGyroYs.clear();
        tapRT9.mGyroZs.clear();
        tapRT9.mGotAcc = false;
        tapRT9.mGotGyro = false;
        tapRT9.mSyncTime = 0;
        tapRT9.mFeatureVector = new ArrayList<>(tapRT9.mNumberFeature);
        for (int i = 0; i < tapRT9.mNumberFeature; i++) {
            tapRT9.mFeatureVector.add(Float.valueOf(0.0f));
        }
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_MODE_HIGH_POWER_ACTIVE);
    }

    public final void stopListening() {
        this.sensorEventListener.setListening(false);
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_MODE_INACTIVE);
    }

    public GestureSensorImpl(Context context, UiEventLogger uiEventLogger2, Handler handler2) {
        this.uiEventLogger = uiEventLogger2;
        this.handler = handler2;
        Object systemService = context.getSystemService("sensor");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
        SensorManager sensorManager2 = (SensorManager) systemService;
        this.sensorManager = sensorManager2;
        this.accelerometer = sensorManager2.getDefaultSensor(1);
        this.gyroscope = sensorManager2.getDefaultSensor(4);
        String str = Build.MODEL;
        this.samplingIntervalNs = 2400000;
        this.tap = new TapRT(context.getAssets());
    }

    public final boolean isListening() {
        return this.isListening;
    }
}
