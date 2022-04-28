package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda4 implements AssistantPresenceHandler.SysUiIsNgaUiChangeListener {
    public final /* synthetic */ NgaUiController f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda4(NgaUiController ngaUiController) {
        this.f$0 = ngaUiController;
    }

    public final void onSysUiIsNgaUiChanged(boolean z) {
        NgaUiController ngaUiController = this.f$0;
        if (!z) {
            ngaUiController.hide();
        } else {
            Objects.requireNonNull(ngaUiController);
        }
    }
}
