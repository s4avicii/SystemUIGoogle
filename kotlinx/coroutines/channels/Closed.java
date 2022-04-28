package kotlinx.coroutines.channels;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.lifecycle.runtime.R$id;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: AbstractChannel.kt */
public final class Closed<E> extends Send implements ReceiveOrClosed<E> {
    public final Throwable closeCause;

    public final void completeResumeReceive(E e) {
    }

    public final void completeResumeSend() {
    }

    public final Object getOfferResult() {
        return this;
    }

    public final Object getPollResult() {
        return this;
    }

    public final Throwable getReceiveException() {
        Throwable th = this.closeCause;
        if (th == null) {
            return new ClosedReceiveChannelException("Channel was closed");
        }
        return th;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Closed@");
        m.append(DebugStringsKt.getHexAddress(this));
        m.append('[');
        m.append(this.closeCause);
        m.append(']');
        return m.toString();
    }

    public Closed(Throwable th) {
        this.closeCause = th;
    }

    public final void resumeSendClosed(Closed<?> closed) {
        boolean z = DebugKt.DEBUG;
    }

    public final Symbol tryResumeReceive(Object obj) {
        return R$id.RESUME_TOKEN;
    }

    public final Symbol tryResumeSend() {
        return R$id.RESUME_TOKEN;
    }
}
