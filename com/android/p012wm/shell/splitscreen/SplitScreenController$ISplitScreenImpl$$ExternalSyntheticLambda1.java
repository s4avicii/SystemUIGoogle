package com.android.p012wm.shell.splitscreen;

import android.os.Bundle;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(int i, int i2, Bundle bundle) {
        this.f$0 = i;
        this.f$1 = i2;
        this.f$2 = bundle;
    }

    public final void accept(Object obj) {
        ((SplitScreenController) obj).startTask(this.f$0, this.f$1, this.f$2);
    }
}
