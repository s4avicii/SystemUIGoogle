package com.android.p012wm.shell.startingsurface;

import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.util.function.TriConsumer;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1932x795f7bd0 implements TriConsumer {
    public final /* synthetic */ StartingWindowController.IStartingWindowImpl f$0;

    public /* synthetic */ C1932x795f7bd0(StartingWindowController.IStartingWindowImpl iStartingWindowImpl) {
        this.f$0 = iStartingWindowImpl;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        StartingWindowController.IStartingWindowImpl iStartingWindowImpl = this.f$0;
        Integer num = (Integer) obj;
        Integer num2 = (Integer) obj2;
        Integer num3 = (Integer) obj3;
        Objects.requireNonNull(iStartingWindowImpl);
        SingleInstanceRemoteListener<StartingWindowController, IStartingWindowListener> singleInstanceRemoteListener = iStartingWindowImpl.mListener;
        Objects.requireNonNull(singleInstanceRemoteListener);
        L l = singleInstanceRemoteListener.mListener;
        if (l == null) {
            Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
            return;
        }
        try {
            ((IStartingWindowListener) l).onTaskLaunching(num.intValue(), num2.intValue(), num3.intValue());
        } catch (RemoteException e) {
            Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
        }
    }
}
