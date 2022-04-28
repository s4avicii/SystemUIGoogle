package com.google.android.setupcompat.util;

import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputMonitor;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;

public final class Logger {
    public final Object prefix;

    public /* synthetic */ Logger(String str, int i) {
        this.prefix = InputManager.getInstance().monitorGestureInput(str, 0);
    }

    public final void atInfo(String str) {
        if (Log.isLoggable("SetupLibrary", 4)) {
            Log.i("SetupLibrary", ((String) this.prefix).concat(str));
        }
    }

    /* renamed from: e */
    public final void mo18771e(String str) {
        Log.e("SetupLibrary", ((String) this.prefix).concat(str));
    }

    /* renamed from: e */
    public final void mo18772e(String str, Exception exc) {
        Log.e("SetupLibrary", ((String) this.prefix).concat(str), exc);
    }

    public final InputChannelCompat$InputEventReceiver getInputReceiver(Looper looper, Choreographer choreographer, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        return new InputChannelCompat$InputEventReceiver(((InputMonitor) this.prefix).getInputChannel(), looper, choreographer, inputChannelCompat$InputEventListener);
    }

    /* renamed from: w */
    public final void mo18774w(String str) {
        Log.w("SetupLibrary", ((String) this.prefix).concat(str));
    }

    public /* synthetic */ Logger(String str) {
        this.prefix = AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("[", str, "] ");
    }
}
