package com.android.systemui.volume;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ VolumeDialogImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda12(VolumeDialogImpl volumeDialogImpl, boolean z) {
        this.f$0 = volumeDialogImpl;
        this.f$1 = z;
    }

    public final void run() {
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(volumeDialogImpl);
        CaptionsToggleImageButton captionsToggleImageButton = volumeDialogImpl.mODICaptionsIcon;
        Objects.requireNonNull(captionsToggleImageButton);
        captionsToggleImageButton.mOptedOut = z;
        captionsToggleImageButton.refreshDrawableState();
    }
}
