package com.android.systemui.controls.controller;

import android.util.Log;
import java.util.Objects;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1 implements Runnable {
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1(ControlsControllerImpl controlsControllerImpl) {
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        Log.d("ControlsControllerImpl", "Restore finished, storing auxiliary favorites");
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        Objects.requireNonNull(controlsControllerImpl);
        controlsControllerImpl.auxiliaryPersistenceWrapper.initialize();
        ControlsControllerImpl controlsControllerImpl2 = this.this$0;
        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl2.persistenceWrapper;
        Objects.requireNonNull(controlsControllerImpl2);
        AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper = controlsControllerImpl2.auxiliaryPersistenceWrapper;
        Objects.requireNonNull(auxiliaryPersistenceWrapper);
        controlsFavoritePersistenceWrapper.storeFavorites(auxiliaryPersistenceWrapper.favorites);
        this.this$0.resetFavorites();
    }
}
