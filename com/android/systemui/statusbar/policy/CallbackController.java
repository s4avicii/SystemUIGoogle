package com.android.systemui.statusbar.policy;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public interface CallbackController<T> {
    void addCallback(T t);

    T observe(LifecycleOwner lifecycleOwner, T t) {
        return observe(lifecycleOwner.getLifecycle(), t);
    }

    void removeCallback(T t);

    T observe(Lifecycle lifecycle, T t) {
        lifecycle.addObserver(new CallbackController$$ExternalSyntheticLambda0(this, t));
        return t;
    }
}
