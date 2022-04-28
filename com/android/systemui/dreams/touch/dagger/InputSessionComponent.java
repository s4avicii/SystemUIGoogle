package com.android.systemui.dreams.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

public interface InputSessionComponent {

    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }

    InputSession getInputSession();
}
