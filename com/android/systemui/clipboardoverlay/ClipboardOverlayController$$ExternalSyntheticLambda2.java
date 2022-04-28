package com.android.systemui.clipboardoverlay;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ ClipboardOverlayController f$0;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda2(ClipboardOverlayController clipboardOverlayController) {
        this.f$0 = clipboardOverlayController;
    }

    public final void onClick(View view) {
        ClipboardOverlayController clipboardOverlayController = this.f$0;
        Objects.requireNonNull(clipboardOverlayController);
        clipboardOverlayController.animateOut();
    }
}
