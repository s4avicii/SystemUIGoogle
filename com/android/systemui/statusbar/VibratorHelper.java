package com.android.systemui.statusbar;

import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda5;
import java.util.concurrent.Executor;

public final class VibratorHelper {
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public final Executor mExecutor;
    public final Vibrator mVibrator;

    public final void vibrate(int i) {
        if (hasVibrator()) {
            this.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(this, i, 0));
        }
    }

    public final boolean hasVibrator() {
        Vibrator vibrator = this.mVibrator;
        if (vibrator == null || !vibrator.hasVibrator()) {
            return false;
        }
        return true;
    }

    public VibratorHelper(Vibrator vibrator, Executor executor) {
        this.mExecutor = executor;
        this.mVibrator = vibrator;
    }

    public final void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes) {
        if (hasVibrator()) {
            this.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda2(this, i, str, vibrationEffect, str2, vibrationAttributes));
        }
    }

    public final void vibrate(VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        if (hasVibrator()) {
            this.mExecutor.execute(new PipTaskOrganizer$$ExternalSyntheticLambda5(this, vibrationEffect, audioAttributes, 3));
        }
    }
}
