package androidx.transition;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public final class TransitionUtils$MatrixEvaluator implements TypeEvaluator<Matrix> {
    public final float[] mTempEndValues = new float[9];
    public final Matrix mTempMatrix = new Matrix();
    public final float[] mTempStartValues = new float[9];

    public final Object evaluate(float f, Object obj, Object obj2) {
        ((Matrix) obj).getValues(this.mTempStartValues);
        ((Matrix) obj2).getValues(this.mTempEndValues);
        for (int i = 0; i < 9; i++) {
            float[] fArr = this.mTempEndValues;
            float f2 = fArr[i];
            float[] fArr2 = this.mTempStartValues;
            fArr[i] = ((f2 - fArr2[i]) * f) + fArr2[i];
        }
        this.mTempMatrix.setValues(this.mTempEndValues);
        return this.mTempMatrix;
    }
}
