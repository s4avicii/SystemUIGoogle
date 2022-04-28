package com.android.systemui.util.sensors;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda10;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProximitySensorImpl implements ProximitySensor {
    public static final boolean DEBUG;
    public final AtomicBoolean mAlerting = new AtomicBoolean();
    public Runnable mCancelSecondaryRunnable;
    public final DelayableExecutor mDelayableExecutor;
    public int mDevicePosture;
    public final Execution mExecution;
    public boolean mInitializedListeners = false;
    @VisibleForTesting
    public ThresholdSensorEvent mLastEvent;
    public ThresholdSensorEvent mLastPrimaryEvent;
    public final ArrayList mListeners = new ArrayList();
    @VisibleForTesting
    public boolean mPaused;
    public final ProximitySensorImpl$$ExternalSyntheticLambda0 mPrimaryEventListener = new ProximitySensorImpl$$ExternalSyntheticLambda0(this);
    public ThresholdSensor mPrimaryThresholdSensor;
    public boolean mRegistered;
    public final C17061 mSecondaryEventListener = new ThresholdSensor.Listener() {
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
            if (r6.mBelow == false) goto L_0x0018;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onThresholdCrossed(com.android.systemui.util.sensors.ThresholdSensorEvent r6) {
            /*
                r5 = this;
                com.android.systemui.util.sensors.ProximitySensorImpl r0 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                boolean r1 = r0.mSecondarySafe
                if (r1 != 0) goto L_0x004d
                com.android.systemui.util.sensors.ThresholdSensorEvent r0 = r0.mLastPrimaryEvent
                if (r0 == 0) goto L_0x0018
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mBelow
                if (r0 == 0) goto L_0x0018
                java.util.Objects.requireNonNull(r6)
                boolean r0 = r6.mBelow
                if (r0 != 0) goto L_0x004d
            L_0x0018:
                com.android.systemui.util.sensors.ProximitySensorImpl r0 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                r0.chooseSensor()
                com.android.systemui.util.sensors.ProximitySensorImpl r0 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                com.android.systemui.util.sensors.ThresholdSensorEvent r0 = r0.mLastPrimaryEvent
                if (r0 == 0) goto L_0x003e
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mBelow
                if (r0 != 0) goto L_0x002b
                goto L_0x003e
            L_0x002b:
                com.android.systemui.util.sensors.ProximitySensorImpl r0 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                com.android.systemui.util.concurrency.DelayableExecutor r1 = r0.mDelayableExecutor
                com.android.systemui.qs.QSFgsManagerFooter$$ExternalSyntheticLambda0 r2 = new com.android.systemui.qs.QSFgsManagerFooter$$ExternalSyntheticLambda0
                r3 = 6
                r2.<init>(r5, r3)
                r3 = 5000(0x1388, double:2.4703E-320)
                java.lang.Runnable r1 = r1.executeDelayed(r2, r3)
                r0.mCancelSecondaryRunnable = r1
                goto L_0x004d
            L_0x003e:
                com.android.systemui.util.sensors.ProximitySensorImpl r6 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                java.lang.Runnable r6 = r6.mCancelSecondaryRunnable
                if (r6 == 0) goto L_0x004c
                r6.run()
                com.android.systemui.util.sensors.ProximitySensorImpl r5 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                r6 = 0
                r5.mCancelSecondaryRunnable = r6
            L_0x004c:
                return
            L_0x004d:
                com.android.systemui.util.sensors.ProximitySensorImpl r0 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Secondary sensor event: "
                r1.append(r2)
                java.util.Objects.requireNonNull(r6)
                boolean r2 = r6.mBelow
                r1.append(r2)
                java.lang.String r2 = "."
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.logDebug(r1)
                com.android.systemui.util.sensors.ProximitySensorImpl r5 = com.android.systemui.util.sensors.ProximitySensorImpl.this
                boolean r0 = r5.mPaused
                if (r0 != 0) goto L_0x0076
                r5.onSensorEvent(r6)
            L_0x0076:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.sensors.ProximitySensorImpl.C17061.onThresholdCrossed(com.android.systemui.util.sensors.ThresholdSensorEvent):void");
        }
    };
    public boolean mSecondarySafe = false;
    public ThresholdSensor mSecondaryThresholdSensor;
    public String mTag = null;

    public String toString() {
        return String.format("{registered=%s, paused=%s, near=%s, posture=%s, primarySensor=%s, secondarySensor=%s secondarySafe=%s}", new Object[]{Boolean.valueOf(this.mRegistered), Boolean.valueOf(this.mPaused), isNear(), Integer.valueOf(this.mDevicePosture), this.mPrimaryThresholdSensor, this.mSecondaryThresholdSensor, Boolean.valueOf(this.mSecondarySafe)});
    }

    static {
        boolean z;
        if (Log.isLoggable("ProxSensor", 3) || Build.IS_DEBUGGABLE) {
            z = true;
        } else {
            z = false;
        }
        DEBUG = z;
    }

    public final void alertListeners() {
        this.mExecution.assertIsMainThread();
        if (!this.mAlerting.getAndSet(true)) {
            ThresholdSensorEvent thresholdSensorEvent = this.mLastEvent;
            if (thresholdSensorEvent != null) {
                new ArrayList(this.mListeners).forEach(new BubbleController$$ExternalSyntheticLambda10(thresholdSensorEvent, 2));
            }
            this.mAlerting.set(false);
        }
    }

    public final void chooseSensor() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered && !this.mPaused && !this.mListeners.isEmpty()) {
            if (this.mSecondarySafe) {
                this.mSecondaryThresholdSensor.resume();
                this.mPrimaryThresholdSensor.pause();
                return;
            }
            this.mPrimaryThresholdSensor.resume();
            this.mSecondaryThresholdSensor.pause();
        }
    }

    public final boolean isLoaded() {
        return this.mPrimaryThresholdSensor.isLoaded();
    }

    public final void logDebug(String str) {
        String str2;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            if (this.mTag != null) {
                str2 = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("["), this.mTag, "] ");
            } else {
                str2 = "";
            }
            sb.append(str2);
            sb.append(str);
            Log.d("ProxSensor", sb.toString());
        }
    }

    public final void onSensorEvent(ThresholdSensorEvent thresholdSensorEvent) {
        this.mExecution.assertIsMainThread();
        if (this.mLastEvent != null) {
            Objects.requireNonNull(thresholdSensorEvent);
            boolean z = thresholdSensorEvent.mBelow;
            ThresholdSensorEvent thresholdSensorEvent2 = this.mLastEvent;
            Objects.requireNonNull(thresholdSensorEvent2);
            if (z == thresholdSensorEvent2.mBelow) {
                return;
            }
        }
        if (!this.mSecondarySafe) {
            Objects.requireNonNull(thresholdSensorEvent);
            if (!thresholdSensorEvent.mBelow) {
                chooseSensor();
            }
        }
        this.mLastEvent = thresholdSensorEvent;
        alertListeners();
    }

    public final void pause() {
        this.mExecution.assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    public final void register(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        if (isLoaded()) {
            if (this.mListeners.contains(listener)) {
                logDebug("ProxListener registered multiple times: " + listener);
            } else {
                this.mListeners.add(listener);
            }
            registerInternal();
        }
    }

    public final void registerInternal() {
        this.mExecution.assertIsMainThread();
        if (!this.mRegistered && !this.mPaused && !this.mListeners.isEmpty()) {
            if (!this.mInitializedListeners) {
                this.mPrimaryThresholdSensor.pause();
                this.mSecondaryThresholdSensor.pause();
                this.mPrimaryThresholdSensor.register(this.mPrimaryEventListener);
                this.mSecondaryThresholdSensor.register(this.mSecondaryEventListener);
                this.mInitializedListeners = true;
            }
            this.mRegistered = true;
            chooseSensor();
        }
    }

    public final void resume() {
        this.mExecution.assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    public final void setDelay() {
        this.mExecution.assertIsMainThread();
        this.mPrimaryThresholdSensor.setDelay();
        this.mSecondaryThresholdSensor.setDelay();
    }

    public final void setSecondarySafe(boolean z) {
        boolean z2;
        if (!this.mSecondaryThresholdSensor.isLoaded() || !z) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mSecondarySafe = z2;
        chooseSensor();
    }

    public final void setTag(String str) {
        this.mTag = str;
        ThresholdSensor thresholdSensor = this.mPrimaryThresholdSensor;
        thresholdSensor.setTag(str + ":primary");
        ThresholdSensor thresholdSensor2 = this.mSecondaryThresholdSensor;
        thresholdSensor2.setTag(str + ":secondary");
    }

    public final void unregister(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        this.mListeners.remove(listener);
        if (this.mListeners.size() == 0) {
            unregisterInternal();
        }
    }

    public final void unregisterInternal() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered) {
            logDebug("unregistering sensor listener");
            this.mPrimaryThresholdSensor.pause();
            this.mSecondaryThresholdSensor.pause();
            Runnable runnable = this.mCancelSecondaryRunnable;
            if (runnable != null) {
                runnable.run();
                this.mCancelSecondaryRunnable = null;
            }
            this.mLastPrimaryEvent = null;
            this.mLastEvent = null;
            this.mRegistered = false;
        }
    }

    public ProximitySensorImpl(ThresholdSensor thresholdSensor, ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        this.mPrimaryThresholdSensor = thresholdSensor;
        this.mSecondaryThresholdSensor = thresholdSensor2;
        this.mDelayableExecutor = delayableExecutor;
        this.mExecution = execution;
    }

    public final Boolean isNear() {
        ThresholdSensorEvent thresholdSensorEvent;
        if (!isLoaded() || (thresholdSensorEvent = this.mLastEvent) == null) {
            return null;
        }
        Objects.requireNonNull(thresholdSensorEvent);
        return Boolean.valueOf(thresholdSensorEvent.mBelow);
    }

    public void destroy() {
        pause();
    }

    public final boolean isRegistered() {
        return this.mRegistered;
    }
}
