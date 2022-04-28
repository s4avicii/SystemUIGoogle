package com.android.p012wm.shell.splitscreen;

import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ SplitScreenController.ISplitScreenImpl f$0;
    public final /* synthetic */ ISplitScreenListener f$1;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6(SplitScreenController.ISplitScreenImpl iSplitScreenImpl, ISplitScreenListener iSplitScreenListener) {
        this.f$0 = iSplitScreenImpl;
        this.f$1 = iSplitScreenListener;
    }

    public final void accept(Object obj) {
        SplitScreenController.ISplitScreenImpl iSplitScreenImpl = this.f$0;
        ISplitScreenListener iSplitScreenListener = this.f$1;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(iSplitScreenImpl);
        iSplitScreenImpl.mListener.register(iSplitScreenListener);
    }
}
