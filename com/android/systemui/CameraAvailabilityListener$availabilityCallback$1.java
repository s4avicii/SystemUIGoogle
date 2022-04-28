package com.android.systemui;

import android.hardware.camera2.CameraManager;
import com.android.systemui.CameraAvailabilityListener;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CameraAvailabilityListener.kt */
public final class CameraAvailabilityListener$availabilityCallback$1 extends CameraManager.AvailabilityCallback {
    public final /* synthetic */ CameraAvailabilityListener this$0;

    public CameraAvailabilityListener$availabilityCallback$1(CameraAvailabilityListener cameraAvailabilityListener) {
        this.this$0 = cameraAvailabilityListener;
    }

    public final void onCameraClosed(String str) {
        if (Intrinsics.areEqual(this.this$0.targetCameraId, str)) {
            CameraAvailabilityListener cameraAvailabilityListener = this.this$0;
            Objects.requireNonNull(cameraAvailabilityListener);
            Iterator it = cameraAvailabilityListener.listeners.iterator();
            while (it.hasNext()) {
                ((CameraAvailabilityListener.CameraTransitionCallback) it.next()).onHideCameraProtection();
            }
        }
    }

    public final void onCameraOpened(String str, String str2) {
        if (Intrinsics.areEqual(this.this$0.targetCameraId, str)) {
            CameraAvailabilityListener cameraAvailabilityListener = this.this$0;
            Objects.requireNonNull(cameraAvailabilityListener);
            if (!cameraAvailabilityListener.excludedPackageIds.contains(str2)) {
                CameraAvailabilityListener cameraAvailabilityListener2 = this.this$0;
                Objects.requireNonNull(cameraAvailabilityListener2);
                Iterator it = cameraAvailabilityListener2.listeners.iterator();
                while (it.hasNext()) {
                    ((CameraAvailabilityListener.CameraTransitionCallback) it.next()).onApplyCameraProtection(cameraAvailabilityListener2.cutoutProtectionPath, cameraAvailabilityListener2.cutoutBounds);
                }
            }
        }
    }
}
