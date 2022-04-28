package com.google.android.systemui.assist.uihints;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ NgaUiController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda5(NgaUiController ngaUiController, boolean z) {
        this.f$0 = ngaUiController;
        this.f$1 = z;
    }

    public final void run() {
        NgaUiController ngaUiController = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(ngaUiController);
        ngaUiController.onDozingChanged(z);
    }
}
