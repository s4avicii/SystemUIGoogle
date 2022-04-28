package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator implements TypeEvaluator<Matrix> {
    public final float[] tempEndValues = new float[9];
    public final Matrix tempMatrix = new Matrix();
    public final float[] tempStartValues = new float[9];
}
