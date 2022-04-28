package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.actions.Action;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility$actionListener$1 implements Action.Listener {
    public final /* synthetic */ CameraVisibility this$0;

    public CameraVisibility$actionListener$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void onActionAvailabilityChanged(Action action) {
        T t;
        boolean z;
        CameraVisibility cameraVisibility = this.this$0;
        Iterator<T> it = cameraVisibility.exceptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            Action action2 = (Action) t;
            Objects.requireNonNull(action2);
            if (action2.isAvailable) {
                break;
            }
        }
        boolean z2 = true;
        if (t != null) {
            z = true;
        } else {
            z = false;
        }
        cameraVisibility.exceptionActive = z;
        CameraVisibility cameraVisibility2 = this.this$0;
        Objects.requireNonNull(cameraVisibility2);
        if (cameraVisibility2.exceptionActive || !cameraVisibility2.cameraShowing) {
            z2 = false;
        }
        cameraVisibility2.setBlocking(z2);
    }
}
