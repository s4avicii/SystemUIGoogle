package com.google.android.systemui.columbus.gates;

import android.app.TaskStackListener;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility$taskStackListener$1 extends TaskStackListener {
    public final /* synthetic */ CameraVisibility this$0;

    public CameraVisibility$taskStackListener$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void onTaskStackChanged() {
        CameraVisibility cameraVisibility = this.this$0;
        cameraVisibility.updateHandler.post(new CameraVisibility$taskStackListener$1$onTaskStackChanged$1(cameraVisibility));
    }
}
