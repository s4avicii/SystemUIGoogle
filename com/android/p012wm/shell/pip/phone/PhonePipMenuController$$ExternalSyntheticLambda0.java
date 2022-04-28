package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhonePipMenuController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ PhonePipMenuController$$ExternalSyntheticLambda0(int i, boolean z, PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3) {
        this.f$0 = i;
        this.f$1 = z;
        this.f$2 = pipTaskOrganizer$$ExternalSyntheticLambda3;
    }

    public final void accept(Object obj) {
        ((PhonePipMenuController.Listener) obj).onPipMenuStateChangeStart(this.f$0, this.f$1, this.f$2);
    }
}
