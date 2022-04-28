package com.android.systemui.controls.controller;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import java.util.Collection;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$settingObserver$1 extends ContentObserver {
    public final /* synthetic */ ControlsControllerImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlsControllerImpl$settingObserver$1(ControlsControllerImpl controlsControllerImpl) {
        super((Handler) null);
        this.this$0 = controlsControllerImpl;
    }

    public final void onChange(boolean z, Collection<? extends Uri> collection, int i, int i2) {
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        if (!controlsControllerImpl.userChanging && i2 == controlsControllerImpl.getCurrentUserId()) {
            this.this$0.resetFavorites();
        }
    }
}
