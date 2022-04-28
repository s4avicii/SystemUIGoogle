package com.android.systemui.volume;

import android.content.DialogInterface;
import android.view.ViewGroup;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda3 implements DialogInterface.OnShowListener {
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda3(VolumeDialogImpl volumeDialogImpl) {
        this.f$0 = volumeDialogImpl;
    }

    public final void onShow(DialogInterface dialogInterface) {
        boolean z;
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        Objects.requireNonNull(volumeDialogImpl);
        volumeDialogImpl.mDialogView.getViewTreeObserver().addOnComputeInternalInsetsListener(volumeDialogImpl);
        if (volumeDialogImpl.mContext.getDisplay().getRotation() != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            ViewGroup viewGroup = volumeDialogImpl.mDialogView;
            viewGroup.setTranslationX(((float) viewGroup.getWidth()) / 2.0f);
        }
        volumeDialogImpl.mDialogView.setAlpha(0.0f);
        volumeDialogImpl.mDialogView.animate().alpha(1.0f).translationX(0.0f).setDuration((long) volumeDialogImpl.mDialogShowAnimationDurationMs).setInterpolator(new SystemUIInterpolators$LogDecelerateInterpolator()).withEndAction(new CreateUserActivity$$ExternalSyntheticLambda2(volumeDialogImpl, 5)).start();
    }
}
