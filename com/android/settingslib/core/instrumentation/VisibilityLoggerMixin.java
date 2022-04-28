package com.android.settingslib.core.instrumentation;

import android.os.SystemClock;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnAttach;

public class VisibilityLoggerMixin implements LifecycleObserver, OnAttach {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    public final void onAttach$1() {
        SystemClock.elapsedRealtime();
    }
}
