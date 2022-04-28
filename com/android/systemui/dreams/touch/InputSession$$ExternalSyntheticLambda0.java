package com.android.systemui.dreams.touch;

import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.google.android.setupcompat.util.Logger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InputSession$$ExternalSyntheticLambda0 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ InputSession f$0;
    public final /* synthetic */ InputChannelCompat$InputEventListener f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ InputSession$$ExternalSyntheticLambda0(InputSession inputSession, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, boolean z) {
        this.f$0 = inputSession;
        this.f$1 = inputChannelCompat$InputEventListener;
        this.f$2 = z;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        InputSession inputSession = this.f$0;
        InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener = this.f$1;
        boolean z = this.f$2;
        Objects.requireNonNull(inputSession);
        inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
        if ((inputEvent instanceof MotionEvent) && inputSession.mGestureDetector.onTouchEvent((MotionEvent) inputEvent) && z) {
            Logger logger = inputSession.mInputMonitor;
            Objects.requireNonNull(logger);
            ((InputMonitor) logger.prefix).pilferPointers();
        }
    }
}
