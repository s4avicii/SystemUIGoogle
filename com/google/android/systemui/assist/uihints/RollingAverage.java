package com.google.android.systemui.assist.uihints;

public final class RollingAverage {
    public int mIndex;
    public float[] mSamples;
    public float mTotal = 0.0f;

    public final void add(float f) {
        float f2 = this.mTotal;
        float[] fArr = this.mSamples;
        int i = this.mIndex;
        float f3 = f2 - fArr[i];
        this.mTotal = f3;
        fArr[i] = f;
        this.mTotal = f3 + f;
        int i2 = i + 1;
        this.mIndex = i2;
        if (i2 == 3) {
            this.mIndex = 0;
        }
    }

    public RollingAverage() {
        this.mIndex = 0;
        this.mSamples = new float[3];
        for (int i = 0; i < 3; i++) {
            this.mSamples[i] = 0.0f;
        }
    }
}
