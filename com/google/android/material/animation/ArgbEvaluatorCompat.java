package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;

public final class ArgbEvaluatorCompat implements TypeEvaluator<Integer> {
    public static final ArgbEvaluatorCompat instance = new ArgbEvaluatorCompat();

    public final /* bridge */ /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
        return evaluate(f, (Integer) obj, (Integer) obj2);
    }

    public static Integer evaluate(float f, Integer num, Integer num2) {
        int intValue = num.intValue();
        float f2 = ((float) ((intValue >> 24) & 255)) / 255.0f;
        int intValue2 = num2.intValue();
        float pow = (float) Math.pow((double) (((float) ((intValue >> 16) & 255)) / 255.0f), 2.2d);
        float pow2 = (float) Math.pow((double) (((float) ((intValue >> 8) & 255)) / 255.0f), 2.2d);
        float pow3 = (float) Math.pow((double) (((float) (intValue & 255)) / 255.0f), 2.2d);
        float pow4 = (float) Math.pow((double) (((float) ((intValue2 >> 16) & 255)) / 255.0f), 2.2d);
        float m = MotionController$$ExternalSyntheticOutline0.m7m(((float) ((intValue2 >> 24) & 255)) / 255.0f, f2, f, f2);
        float m2 = MotionController$$ExternalSyntheticOutline0.m7m(pow4, pow, f, pow);
        float m3 = MotionController$$ExternalSyntheticOutline0.m7m((float) Math.pow((double) (((float) ((intValue2 >> 8) & 255)) / 255.0f), 2.2d), pow2, f, pow2);
        float m4 = MotionController$$ExternalSyntheticOutline0.m7m((float) Math.pow((double) (((float) (intValue2 & 255)) / 255.0f), 2.2d), pow3, f, pow3);
        int round = Math.round(((float) Math.pow((double) m2, 0.45454545454545453d)) * 255.0f) << 16;
        return Integer.valueOf(Math.round(((float) Math.pow((double) m4, 0.45454545454545453d)) * 255.0f) | round | (Math.round(m * 255.0f) << 24) | (Math.round(((float) Math.pow((double) m3, 0.45454545454545453d)) * 255.0f) << 8));
    }
}
