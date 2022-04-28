package com.android.p012wm.shell.onehanded;

import com.android.p012wm.shell.onehanded.OneHandedTimeoutHandler;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedController$$ExternalSyntheticLambda0 implements OneHandedTimeoutHandler.TimeoutListener {
    public final /* synthetic */ OneHandedController f$0;

    public /* synthetic */ OneHandedController$$ExternalSyntheticLambda0(OneHandedController oneHandedController) {
        this.f$0 = oneHandedController;
    }

    public final void onTimeout() {
        OneHandedController oneHandedController = this.f$0;
        Objects.requireNonNull(oneHandedController);
        oneHandedController.stopOneHanded(6);
    }
}
