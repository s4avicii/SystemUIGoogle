package com.google.android.systemui.elmyra.feedback;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

public final class HapticClick implements FeedbackEffect {
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(18).build();
    public int mLastGestureStage;
    public final VibrationEffect mProgressVibrationEffect;
    public final VibrationEffect mResolveVibrationEffect = VibrationEffect.get(0);
    public final Vibrator mVibrator;

    public final void onRelease() {
    }

    public final void onProgress(float f, int i) {
        Vibrator vibrator;
        if (!(this.mLastGestureStage == 2 || i != 2 || (vibrator = this.mVibrator) == null)) {
            vibrator.vibrate(this.mProgressVibrationEffect, TOUCH_VIBRATION_ATTRIBUTES);
        }
        this.mLastGestureStage = i;
    }

    public final void onResolve(GestureSensor.DetectionProperties detectionProperties) {
        Vibrator vibrator;
        if ((detectionProperties == null || !detectionProperties.mHapticConsumed) && (vibrator = this.mVibrator) != null) {
            vibrator.vibrate(this.mResolveVibrationEffect, TOUCH_VIBRATION_ATTRIBUTES);
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x002e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HapticClick(android.content.Context r5) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.String r0 = "vibrator"
            java.lang.Object r0 = r5.getSystemService(r0)
            android.os.Vibrator r0 = (android.os.Vibrator) r0
            r4.mVibrator = r0
            r1 = 0
            android.os.VibrationEffect r1 = android.os.VibrationEffect.get(r1)
            r4.mResolveVibrationEffect = r1
            r1 = 5
            android.os.VibrationEffect r1 = android.os.VibrationEffect.get(r1)
            r4.mProgressVibrationEffect = r1
            if (r0 == 0) goto L_0x0042
            android.content.res.Resources r2 = r5.getResources()     // Catch:{ NotFoundException -> 0x002e }
            r3 = 2131492925(0x7f0c003d, float:1.8609316E38)
            int r2 = r2.getInteger(r3)     // Catch:{ NotFoundException -> 0x002e }
            android.os.VibrationAttributes r3 = TOUCH_VIBRATION_ATTRIBUTES     // Catch:{ NotFoundException -> 0x002e }
            r0.setAlwaysOnEffect(r2, r1, r3)     // Catch:{ NotFoundException -> 0x002e }
        L_0x002e:
            android.content.res.Resources r5 = r5.getResources()     // Catch:{ NotFoundException -> 0x0042 }
            r0 = 2131492926(0x7f0c003e, float:1.8609318E38)
            int r5 = r5.getInteger(r0)     // Catch:{ NotFoundException -> 0x0042 }
            android.os.Vibrator r0 = r4.mVibrator     // Catch:{ NotFoundException -> 0x0042 }
            android.os.VibrationEffect r4 = r4.mResolveVibrationEffect     // Catch:{ NotFoundException -> 0x0042 }
            android.os.VibrationAttributes r1 = TOUCH_VIBRATION_ATTRIBUTES     // Catch:{ NotFoundException -> 0x0042 }
            r0.setAlwaysOnEffect(r5, r4, r1)     // Catch:{ NotFoundException -> 0x0042 }
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.elmyra.feedback.HapticClick.<init>(android.content.Context):void");
    }
}
