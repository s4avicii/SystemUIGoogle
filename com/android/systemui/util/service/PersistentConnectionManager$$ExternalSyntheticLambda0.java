package com.android.systemui.util.service;

import com.android.systemui.util.service.Observer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PersistentConnectionManager$$ExternalSyntheticLambda0 implements Observer.Callback {
    public final /* synthetic */ PersistentConnectionManager f$0;

    public /* synthetic */ PersistentConnectionManager$$ExternalSyntheticLambda0(PersistentConnectionManager persistentConnectionManager) {
        this.f$0 = persistentConnectionManager;
    }

    public final void onSourceChanged() {
        PersistentConnectionManager persistentConnectionManager = this.f$0;
        Objects.requireNonNull(persistentConnectionManager);
        persistentConnectionManager.mReconnectAttempts = 0;
        persistentConnectionManager.mConnection.bind();
    }
}
