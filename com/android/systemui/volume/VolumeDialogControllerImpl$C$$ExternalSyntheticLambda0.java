package com.android.systemui.volume;

import com.android.systemui.plugins.VolumeDialogController;
import java.util.Map;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Map.Entry f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Boolean f$2;

    public /* synthetic */ VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(Map.Entry entry, boolean z, Boolean bool) {
        this.f$0 = entry;
        this.f$1 = z;
        this.f$2 = bool;
    }

    public final void run() {
        Map.Entry entry = this.f$0;
        boolean z = this.f$1;
        ((VolumeDialogController.Callbacks) entry.getKey()).onCaptionComponentStateChanged(Boolean.valueOf(z), this.f$2);
    }
}
