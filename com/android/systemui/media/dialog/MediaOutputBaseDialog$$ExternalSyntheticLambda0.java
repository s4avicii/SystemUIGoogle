package com.android.systemui.media.dialog;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda0 implements ViewTreeObserver.OnGlobalLayoutListener {
    public final /* synthetic */ MediaOutputBaseDialog f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda0(MediaOutputBaseDialog mediaOutputBaseDialog) {
        this.f$0 = mediaOutputBaseDialog;
    }

    public final void onGlobalLayout() {
        MediaOutputBaseDialog mediaOutputBaseDialog = this.f$0;
        Objects.requireNonNull(mediaOutputBaseDialog);
        if (mediaOutputBaseDialog.mDeviceListLayout.getHeight() > mediaOutputBaseDialog.mListMaxHeight) {
            ViewGroup.LayoutParams layoutParams = mediaOutputBaseDialog.mDeviceListLayout.getLayoutParams();
            layoutParams.height = mediaOutputBaseDialog.mListMaxHeight;
            mediaOutputBaseDialog.mDeviceListLayout.setLayoutParams(layoutParams);
        }
    }
}
