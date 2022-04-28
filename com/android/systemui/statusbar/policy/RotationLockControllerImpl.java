package com.android.systemui.statusbar.policy;

import com.android.internal.view.RotationPolicy;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RotationLockControllerImpl implements RotationLockController {
    public final CopyOnWriteArrayList<RotationLockController.RotationLockControllerCallback> mCallbacks;
    public final RotationPolicyWrapper mRotationPolicy;
    public final C16381 mRotationPolicyListener;

    public final void addCallback(Object obj) {
        RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = (RotationLockController.RotationLockControllerCallback) obj;
        this.mCallbacks.add(rotationLockControllerCallback);
        boolean isRotationLocked = this.mRotationPolicy.isRotationLocked();
        this.mRotationPolicy.isRotationLockToggleVisible();
        rotationLockControllerCallback.onRotationLockStateChanged(isRotationLocked);
    }

    public final int getRotationLockOrientation() {
        return this.mRotationPolicy.getRotationLockOrientation();
    }

    public final boolean isCameraRotationEnabled() {
        return this.mRotationPolicy.isCameraRotationEnabled();
    }

    public final boolean isRotationLocked() {
        return this.mRotationPolicy.isRotationLocked();
    }

    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((RotationLockController.RotationLockControllerCallback) obj);
    }

    public final void setRotationLocked(boolean z) {
        this.mRotationPolicy.setRotationLock(z);
    }

    public RotationLockControllerImpl(RotationPolicyWrapper rotationPolicyWrapper, DeviceStateRotationLockSettingController deviceStateRotationLockSettingController, String[] strArr) {
        boolean z;
        CopyOnWriteArrayList<RotationLockController.RotationLockControllerCallback> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        this.mCallbacks = copyOnWriteArrayList;
        C16381 r1 = new RotationPolicy.RotationPolicyListener() {
            public final void onChange() {
                RotationLockControllerImpl rotationLockControllerImpl = RotationLockControllerImpl.this;
                Objects.requireNonNull(rotationLockControllerImpl);
                Iterator<RotationLockController.RotationLockControllerCallback> it = rotationLockControllerImpl.mCallbacks.iterator();
                while (it.hasNext()) {
                    boolean isRotationLocked = rotationLockControllerImpl.mRotationPolicy.isRotationLocked();
                    rotationLockControllerImpl.mRotationPolicy.isRotationLockToggleVisible();
                    it.next().onRotationLockStateChanged(isRotationLocked);
                }
            }
        };
        this.mRotationPolicyListener = r1;
        this.mRotationPolicy = rotationPolicyWrapper;
        if (strArr.length > 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            copyOnWriteArrayList.add(deviceStateRotationLockSettingController);
        }
        rotationPolicyWrapper.registerRotationPolicyListener(r1);
        if (z) {
            Objects.requireNonNull(deviceStateRotationLockSettingController);
            C1612x3b31e417 deviceStateRotationLockSettingController$$ExternalSyntheticLambda0 = new C1612x3b31e417(deviceStateRotationLockSettingController);
            deviceStateRotationLockSettingController.mDeviceStateCallback = deviceStateRotationLockSettingController$$ExternalSyntheticLambda0;
            deviceStateRotationLockSettingController.mDeviceStateManager.registerCallback(deviceStateRotationLockSettingController.mMainExecutor, deviceStateRotationLockSettingController$$ExternalSyntheticLambda0);
            C1613x3b31e418 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1 = new C1613x3b31e418(deviceStateRotationLockSettingController);
            deviceStateRotationLockSettingController.mDeviceStateRotationLockSettingsListener = deviceStateRotationLockSettingController$$ExternalSyntheticLambda1;
            DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager = deviceStateRotationLockSettingController.mDeviceStateRotationLockSettingsManager;
            Objects.requireNonNull(deviceStateRotationLockSettingsManager);
            deviceStateRotationLockSettingsManager.mListeners.add(deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);
        }
    }
}
