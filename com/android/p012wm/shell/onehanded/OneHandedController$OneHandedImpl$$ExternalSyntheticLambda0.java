package com.android.p012wm.shell.onehanded;

import com.android.p012wm.shell.onehanded.OneHandedController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(OneHandedController.OneHandedImpl oneHandedImpl, int i) {
        this.f$0 = oneHandedImpl;
        this.f$1 = i;
    }

    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(oneHandedImpl);
        OneHandedController.this.stopOneHanded(i);
    }
}
