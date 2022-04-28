package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import android.util.ArraySet;
import android.util.SparseBooleanArray;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;

public final class IndividualSensorPrivacyControllerImpl implements IndividualSensorPrivacyController {
    public static final int[] SENSORS = {2, 1};
    public final ArraySet mCallbacks = new ArraySet();
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final SparseBooleanArray mState = new SparseBooleanArray();

    public final void addCallback(Object obj) {
        this.mCallbacks.add((IndividualSensorPrivacyController.Callback) obj);
    }

    public final void init() {
        int[] iArr = SENSORS;
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            this.mSensorPrivacyManager.addSensorPrivacyListener(i2, new IndividualSensorPrivacyControllerImpl$$ExternalSyntheticLambda0(this, i2));
            this.mState.put(i2, this.mSensorPrivacyManager.isSensorPrivacyEnabled(i2));
        }
    }

    public final boolean isSensorBlocked(int i) {
        return this.mState.get(i, false);
    }

    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((IndividualSensorPrivacyController.Callback) obj);
    }

    public final void setSensorBlocked(int i, int i2, boolean z) {
        this.mSensorPrivacyManager.setSensorPrivacyForProfileGroup(i, i2, z);
    }

    public final boolean supportsSensorToggle(int i) {
        return this.mSensorPrivacyManager.supportsSensorToggle(i);
    }

    public final void suppressSensorPrivacyReminders(int i, boolean z) {
        this.mSensorPrivacyManager.suppressSensorPrivacyReminders(i, z);
    }

    public IndividualSensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }
}
