package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import java.util.ArrayList;
import java.util.Iterator;

public final class SensorPrivacyControllerImpl implements SensorPrivacyController, SensorPrivacyManager.OnAllSensorPrivacyChangedListener {
    public final ArrayList mListeners = new ArrayList(1);
    public Object mLock = new Object();
    public boolean mSensorPrivacyEnabled;
    public SensorPrivacyManager mSensorPrivacyManager;

    public final void addCallback(Object obj) {
        SensorPrivacyController.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (SensorPrivacyController.OnSensorPrivacyChangedListener) obj;
        synchronized (this.mLock) {
            this.mListeners.add(onSensorPrivacyChangedListener);
            onSensorPrivacyChangedListener.onSensorPrivacyChanged(this.mSensorPrivacyEnabled);
        }
    }

    public final boolean isSensorPrivacyEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSensorPrivacyEnabled;
        }
        return z;
    }

    public final void onAllSensorPrivacyChanged(boolean z) {
        synchronized (this.mLock) {
            this.mSensorPrivacyEnabled = z;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((SensorPrivacyController.OnSensorPrivacyChangedListener) it.next()).onSensorPrivacyChanged(this.mSensorPrivacyEnabled);
            }
        }
    }

    public final void removeCallback(Object obj) {
        SensorPrivacyController.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (SensorPrivacyController.OnSensorPrivacyChangedListener) obj;
        synchronized (this.mLock) {
            this.mListeners.remove(onSensorPrivacyChangedListener);
        }
    }

    public SensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }
}
