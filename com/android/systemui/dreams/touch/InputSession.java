package com.android.systemui.dreams.touch;

import android.os.Looper;
import android.view.Choreographer;
import android.view.GestureDetector;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.google.android.setupcompat.util.Logger;

public final class InputSession {
    public final GestureDetector mGestureDetector;
    public final InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public final Logger mInputMonitor;

    public InputSession(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z) {
        Logger logger = new Logger(str, 0);
        this.mInputMonitor = logger;
        this.mGestureDetector = new GestureDetector(onGestureListener);
        this.mInputEventReceiver = logger.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputSession$$ExternalSyntheticLambda0(this, inputChannelCompat$InputEventListener, z));
    }
}
