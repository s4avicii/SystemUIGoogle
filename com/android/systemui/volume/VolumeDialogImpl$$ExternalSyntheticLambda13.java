package com.android.systemui.volume;

import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ VolumeDialogImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda13(VolumeDialogImpl volumeDialogImpl, int i, int i2) {
        this.f$0 = volumeDialogImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void accept(Object obj) {
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(volumeDialogImpl);
        BackgroundBlurDrawable backgroundBlurDrawable = volumeDialogImpl.mDialogRowsViewBackground;
        if (!((Boolean) obj).booleanValue()) {
            i = i2;
        }
        backgroundBlurDrawable.setColor(i);
        volumeDialogImpl.mDialogRowsView.invalidate();
    }
}
