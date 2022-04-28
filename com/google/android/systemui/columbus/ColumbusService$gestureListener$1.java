package com.google.android.systemui.columbus;

import android.os.PowerManager;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Objects;

/* compiled from: ColumbusService.kt */
public final class ColumbusService$gestureListener$1 implements GestureController.GestureListener {
    public final /* synthetic */ ColumbusService this$0;

    public ColumbusService$gestureListener$1(ColumbusService columbusService) {
        this.this$0 = columbusService;
    }

    public final void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        if (i != 0) {
            PowerManagerWrapper.WakeLockWrapper wakeLockWrapper = this.this$0.wakeLock;
            Objects.requireNonNull(wakeLockWrapper);
            PowerManager.WakeLock wakeLock = wakeLockWrapper.wakeLock;
            if (wakeLock != null) {
                wakeLock.acquire(2000);
            }
        }
        Action updateActiveAction = this.this$0.updateActiveAction();
        if (updateActiveAction != null) {
            ColumbusService columbusService = this.this$0;
            updateActiveAction.onGestureDetected(i, detectionProperties);
            for (FeedbackEffect onGestureDetected : columbusService.effects) {
                onGestureDetected.onGestureDetected(i, detectionProperties);
            }
        }
    }
}
