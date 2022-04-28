package androidx.transition;

import android.animation.TypeEvaluator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;

public final class FloatArrayEvaluator implements TypeEvaluator<float[]> {
    public float[] mArray;

    public final Object evaluate(float f, Object obj, Object obj2) {
        float[] fArr = (float[]) obj;
        float[] fArr2 = (float[]) obj2;
        float[] fArr3 = this.mArray;
        if (fArr3 == null) {
            fArr3 = new float[fArr.length];
        }
        for (int i = 0; i < fArr3.length; i++) {
            float f2 = fArr[i];
            fArr3[i] = MotionController$$ExternalSyntheticOutline0.m7m(fArr2[i], f2, f, f2);
        }
        return fArr3;
    }

    public FloatArrayEvaluator(float[] fArr) {
        this.mArray = fArr;
    }
}
