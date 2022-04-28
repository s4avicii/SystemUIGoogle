package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.util.SparseIntArray;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class DevicePostureControllerImpl implements DevicePostureController {
    public int mCurrentDevicePosture = 0;
    public final SparseIntArray mDeviceStateToPostureMap = new SparseIntArray();
    public final ArrayList mListeners = new ArrayList();

    public final void addCallback(Object obj) {
        Assert.isMainThread();
        this.mListeners.add((DevicePostureController.Callback) obj);
    }

    public final void removeCallback(Object obj) {
        Assert.isMainThread();
        this.mListeners.remove((DevicePostureController.Callback) obj);
    }

    public DevicePostureControllerImpl(Context context, DeviceStateManager deviceStateManager, Executor executor) {
        for (String split : context.getResources().getStringArray(17236026)) {
            String[] split2 = split.split(":");
            if (split2.length == 2) {
                try {
                    this.mDeviceStateToPostureMap.put(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                } catch (NumberFormatException unused) {
                }
            }
        }
        deviceStateManager.registerCallback(executor, new DevicePostureControllerImpl$$ExternalSyntheticLambda0(this));
    }

    public final int getDevicePosture() {
        return this.mCurrentDevicePosture;
    }
}
