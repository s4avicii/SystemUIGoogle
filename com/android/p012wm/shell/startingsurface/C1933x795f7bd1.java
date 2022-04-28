package com.android.p012wm.shell.startingsurface;

import com.android.p012wm.shell.startingsurface.StartingWindowController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1933x795f7bd1 implements Consumer {
    public final /* synthetic */ StartingWindowController.IStartingWindowImpl f$0;
    public final /* synthetic */ IStartingWindowListener f$1;

    public /* synthetic */ C1933x795f7bd1(StartingWindowController.IStartingWindowImpl iStartingWindowImpl, IStartingWindowListener iStartingWindowListener) {
        this.f$0 = iStartingWindowImpl;
        this.f$1 = iStartingWindowListener;
    }

    public final void accept(Object obj) {
        StartingWindowController.IStartingWindowImpl iStartingWindowImpl = this.f$0;
        IStartingWindowListener iStartingWindowListener = this.f$1;
        StartingWindowController startingWindowController = (StartingWindowController) obj;
        Objects.requireNonNull(iStartingWindowImpl);
        if (iStartingWindowListener != null) {
            iStartingWindowImpl.mListener.register(iStartingWindowListener);
        } else {
            iStartingWindowImpl.mListener.unregister();
        }
    }
}
