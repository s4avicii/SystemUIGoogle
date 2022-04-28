package com.android.systemui.statusbar.connectivity;

import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CallbackHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ CallbackHandler f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ CallbackHandler$$ExternalSyntheticLambda1(CallbackHandler callbackHandler, boolean z, boolean z2, boolean z3) {
        this.f$0 = callbackHandler;
        this.f$1 = z;
        this.f$2 = z2;
        this.f$3 = z3;
    }

    public final void run() {
        CallbackHandler callbackHandler = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        boolean z3 = this.f$3;
        Objects.requireNonNull(callbackHandler);
        Iterator<SignalCallback> it = callbackHandler.mSignalCallbacks.iterator();
        while (it.hasNext()) {
            it.next().setConnectivityStatus(z, z2, z3);
        }
    }
}
