package com.google.android.systemui.columbus.gates;

import java.util.Objects;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility$taskStackListener$1$onTaskStackChanged$1 implements Runnable {
    public final /* synthetic */ CameraVisibility this$0;

    public CameraVisibility$taskStackListener$1$onTaskStackChanged$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void run() {
        boolean z;
        CameraVisibility cameraVisibility = this.this$0;
        Objects.requireNonNull(cameraVisibility);
        boolean isCameraShowing = cameraVisibility.isCameraShowing();
        cameraVisibility.cameraShowing = isCameraShowing;
        if (cameraVisibility.exceptionActive || !isCameraShowing) {
            z = false;
        } else {
            z = true;
        }
        cameraVisibility.setBlocking(z);
    }
}
