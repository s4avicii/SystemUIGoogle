package com.android.systemui.clipboardoverlay;

import android.content.Intent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ ClipboardOverlayController f$0;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda3(ClipboardOverlayController clipboardOverlayController) {
        this.f$0 = clipboardOverlayController;
    }

    public final void onClick(View view) {
        ClipboardOverlayController clipboardOverlayController = this.f$0;
        Objects.requireNonNull(clipboardOverlayController);
        Intent intent = new Intent(clipboardOverlayController.mContext, EditTextActivity.class);
        intent.addFlags(268468224);
        clipboardOverlayController.mContext.startActivity(intent);
        clipboardOverlayController.animateOut();
    }
}
