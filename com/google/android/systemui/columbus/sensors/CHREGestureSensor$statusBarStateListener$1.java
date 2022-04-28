package com.google.android.systemui.columbus.sensors;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.Objects;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ CHREGestureSensor this$0;

    public CHREGestureSensor$statusBarStateListener$1(CHREGestureSensor cHREGestureSensor) {
        this.this$0 = cHREGestureSensor;
    }

    public final void onDozingChanged(boolean z) {
        CHREGestureSensor cHREGestureSensor = this.this$0;
        Objects.requireNonNull(cHREGestureSensor);
        if (cHREGestureSensor.isDozing != z) {
            cHREGestureSensor.isDozing = z;
            cHREGestureSensor.updateScreenState();
        }
    }
}
