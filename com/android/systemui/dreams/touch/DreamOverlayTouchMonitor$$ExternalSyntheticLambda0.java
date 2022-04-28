package com.android.systemui.dreams.touch;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda23;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayTouchMonitor$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ DreamOverlayTouchMonitor f$0;
    public final /* synthetic */ DreamOverlayTouchMonitor.TouchSessionImpl f$1;

    public /* synthetic */ DreamOverlayTouchMonitor$$ExternalSyntheticLambda0(DreamOverlayTouchMonitor dreamOverlayTouchMonitor, DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl) {
        this.f$0 = dreamOverlayTouchMonitor;
        this.f$1 = touchSessionImpl;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        DreamOverlayTouchMonitor dreamOverlayTouchMonitor = this.f$0;
        DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = this.f$1;
        Objects.requireNonNull(dreamOverlayTouchMonitor);
        dreamOverlayTouchMonitor.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda23(dreamOverlayTouchMonitor, touchSessionImpl, completer, 1));
        return "DreamOverlayTouchMonitor::pop";
    }
}
