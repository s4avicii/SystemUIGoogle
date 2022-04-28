package com.android.systemui.biometrics;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.view.Display;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BiometricDisplayListener.kt */
public final class BiometricDisplayListener implements DisplayManager.DisplayListener {
    public final Context context;
    public final DisplayManager displayManager;
    public final Handler handler;
    public int lastRotation;
    public final Function0<Unit> onChanged;
    public final SensorType sensorType;

    public final void onDisplayAdded(int i) {
    }

    public final void onDisplayRemoved(int i) {
    }

    public final void disable() {
        this.displayManager.unregisterDisplayListener(this);
    }

    public final void enable() {
        int i;
        Display display = this.context.getDisplay();
        if (display == null) {
            i = 0;
        } else {
            i = display.getRotation();
        }
        this.lastRotation = i;
        this.displayManager.registerDisplayListener(this, this.handler);
    }

    public final void onDisplayChanged(int i) {
        Integer num;
        Display display = this.context.getDisplay();
        if (display == null) {
            num = null;
        } else {
            num = Integer.valueOf(display.getRotation());
        }
        boolean z = false;
        if (num != null) {
            int intValue = num.intValue();
            int i2 = this.lastRotation;
            this.lastRotation = intValue;
            if (i2 != intValue) {
                z = true;
            }
        }
        if (this.sensorType instanceof SensorType.SideFingerprint) {
            this.onChanged.invoke();
        } else if (z) {
            this.onChanged.invoke();
        }
    }

    public BiometricDisplayListener(Context context2, DisplayManager displayManager2, Handler handler2, SensorType sensorType2, Function0<Unit> function0) {
        this.context = context2;
        this.displayManager = displayManager2;
        this.handler = handler2;
        this.sensorType = sensorType2;
        this.onChanged = function0;
    }

    /* compiled from: BiometricDisplayListener.kt */
    public static abstract class SensorType {

        /* compiled from: BiometricDisplayListener.kt */
        public static final class Generic extends SensorType {
            public static final Generic INSTANCE = new Generic();

            public Generic() {
                super(0);
            }
        }

        /* compiled from: BiometricDisplayListener.kt */
        public static final class SideFingerprint extends SensorType {
            public final FingerprintSensorPropertiesInternal properties;

            public SideFingerprint(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
                super(0);
                this.properties = fingerprintSensorPropertiesInternal;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof SideFingerprint) && Intrinsics.areEqual(this.properties, ((SideFingerprint) obj).properties);
            }

            public final int hashCode() {
                return this.properties.hashCode();
            }

            public final String toString() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SideFingerprint(properties=");
                m.append(this.properties);
                m.append(')');
                return m.toString();
            }
        }

        /* compiled from: BiometricDisplayListener.kt */
        public static final class UnderDisplayFingerprint extends SensorType {
            public final FingerprintSensorPropertiesInternal properties;

            public UnderDisplayFingerprint(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
                super(0);
                this.properties = fingerprintSensorPropertiesInternal;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof UnderDisplayFingerprint) && Intrinsics.areEqual(this.properties, ((UnderDisplayFingerprint) obj).properties);
            }

            public final int hashCode() {
                return this.properties.hashCode();
            }

            public final String toString() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UnderDisplayFingerprint(properties=");
                m.append(this.properties);
                m.append(')');
                return m.toString();
            }
        }

        public SensorType(int i) {
        }
    }
}
