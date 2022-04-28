package com.android.systemui.volume;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda2 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda2(VolumeDialogImpl volumeDialogImpl) {
        this.f$0 = volumeDialogImpl;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        Objects.requireNonNull(volumeDialogImpl);
        volumeDialogImpl.mDialogView.getViewTreeObserver().removeOnComputeInternalInsetsListener(volumeDialogImpl);
    }
}
