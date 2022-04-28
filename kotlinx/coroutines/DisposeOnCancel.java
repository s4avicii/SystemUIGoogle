package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import kotlin.Unit;

/* compiled from: CancellableContinuation.kt */
public final class DisposeOnCancel extends CancelHandler {
    public final DisposableHandle handle;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.handle.dispose();
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DisposeOnCancel[");
        m.append(this.handle);
        m.append(']');
        return m.toString();
    }

    public DisposeOnCancel(DisposableHandle disposableHandle) {
        this.handle = disposableHandle;
    }
}
