package com.google.android.systemui.columbus.sensors;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class EventIMURT {
    public ArrayDeque mAccXs = new ArrayDeque();
    public ArrayDeque mAccYs = new ArrayDeque();
    public ArrayDeque mAccZs = new ArrayDeque();
    public TfClassifier mClassifier = null;
    public ArrayList<Float> mFeatureVector = new ArrayList<>();
    public boolean mGotAcc = false;
    public boolean mGotGyro = false;
    public ArrayDeque mGyroXs = new ArrayDeque();
    public ArrayDeque mGyroYs = new ArrayDeque();
    public ArrayDeque mGyroZs = new ArrayDeque();
    public Highpass3C mHighpassAcc = new Highpass3C();
    public Highpass3C mHighpassGyro = new Highpass3C();
    public Lowpass3C mLowpassAcc = new Lowpass3C();
    public Lowpass3C mLowpassGyro = new Lowpass3C();
    public int mNumberFeature;
    public Resample3C mResampleAcc = new Resample3C();
    public Resample3C mResampleGyro = new Resample3C();
    public int mSizeFeatureWindow;
    public long mSizeWindowNs;
    public Slope3C mSlopeAcc = new Slope3C();
    public Slope3C mSlopeGyro = new Slope3C();
    public long mSyncTime = 0;
}
