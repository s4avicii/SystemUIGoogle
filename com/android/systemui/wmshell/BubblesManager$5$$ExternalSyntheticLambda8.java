package com.android.systemui.wmshell;

import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ BubblesManager.C17525 f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ String f$2 = "Bubbles";

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda8(BubblesManager.C17525 r1, boolean z) {
        this.f$0 = r1;
        this.f$1 = z;
    }

    public final void run() {
        BubblesManager.C17525 r0 = this.f$0;
        boolean z = this.f$1;
        String str = this.f$2;
        Objects.requireNonNull(r0);
        BubblesManager.this.mNotificationShadeWindowController.setRequestTopUi(z, str);
    }
}
