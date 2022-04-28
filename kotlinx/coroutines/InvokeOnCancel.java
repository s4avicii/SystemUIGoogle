package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: CancellableContinuationImpl.kt */
public final class InvokeOnCancel extends CancelHandler {
    public final Function1<Throwable, Unit> handler;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.handler.invoke(th);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("InvokeOnCancel[");
        m.append(DebugStringsKt.getClassSimpleName(this.handler));
        m.append('@');
        m.append(DebugStringsKt.getHexAddress(this));
        m.append(']');
        return m.toString();
    }

    public InvokeOnCancel(Function1<? super Throwable, Unit> function1) {
        this.handler = function1;
    }
}
