package com.android.systemui.controls.dagger;

import android.database.ContentObserver;
import android.os.Handler;
import java.util.Objects;

/* compiled from: ControlsComponent.kt */
public final class ControlsComponent$showWhileLockedObserver$1 extends ContentObserver {
    public final /* synthetic */ ControlsComponent this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlsComponent$showWhileLockedObserver$1(ControlsComponent controlsComponent) {
        super((Handler) null);
        this.this$0 = controlsComponent;
    }

    public final void onChange(boolean z) {
        ControlsComponent controlsComponent = this.this$0;
        Objects.requireNonNull(controlsComponent);
        boolean z2 = false;
        if (controlsComponent.secureSettings.getInt("lockscreen_show_controls", 0) != 0) {
            z2 = true;
        }
        controlsComponent.canShowWhileLockedSetting = z2;
    }
}
