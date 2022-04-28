package com.android.systemui.statusbar.policy;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CallbackController$$ExternalSyntheticLambda0 implements LifecycleEventObserver {
    public final /* synthetic */ CallbackController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CallbackController$$ExternalSyntheticLambda0(CallbackController callbackController, Object obj) {
        this.f$0 = callbackController;
        this.f$1 = obj;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        CallbackController callbackController = this.f$0;
        Object obj = this.f$1;
        Objects.requireNonNull(callbackController);
        if (event == Lifecycle.Event.ON_RESUME) {
            callbackController.addCallback(obj);
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            callbackController.removeCallback(obj);
        }
    }
}
