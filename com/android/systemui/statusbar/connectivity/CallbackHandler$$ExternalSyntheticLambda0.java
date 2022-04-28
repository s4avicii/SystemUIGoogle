package com.android.systemui.statusbar.connectivity;

import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CallbackHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ CallbackHandler f$0;
    public final /* synthetic */ IconState f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CallbackHandler$$ExternalSyntheticLambda0(CallbackHandler callbackHandler, IconState iconState, int i) {
        this.f$0 = callbackHandler;
        this.f$1 = iconState;
        this.f$2 = i;
    }

    public final void run() {
        CallbackHandler callbackHandler = this.f$0;
        IconState iconState = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(callbackHandler);
        Iterator<SignalCallback> it = callbackHandler.mSignalCallbacks.iterator();
        while (it.hasNext()) {
            it.next().setCallIndicator(iconState, i);
        }
    }
}
