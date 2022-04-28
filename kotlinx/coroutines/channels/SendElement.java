package kotlinx.coroutines.channels;

import androidx.lifecycle.runtime.R$id;
import kotlin.Result;
import kotlin.Unit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: AbstractChannel.kt */
public class SendElement<E> extends Send {
    public final CancellableContinuation<Unit> cont;
    public final E pollResult;

    public final void completeResumeSend() {
        this.cont.completeResume();
    }

    public final void resumeSendClosed(Closed<?> closed) {
        CancellableContinuation<Unit> cancellableContinuation = this.cont;
        Throwable th = closed.closeCause;
        if (th == null) {
            th = new ClosedSendChannelException("Channel was closed");
        }
        cancellableContinuation.resumeWith(new Result.Failure(th));
    }

    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '(' + this.pollResult + ')';
    }

    public final Symbol tryResumeSend() {
        if (this.cont.tryResume((Object) Unit.INSTANCE, (Object) null) == null) {
            return null;
        }
        boolean z = DebugKt.DEBUG;
        return R$id.RESUME_TOKEN;
    }

    public SendElement(Object obj, CancellableContinuationImpl cancellableContinuationImpl) {
        this.pollResult = obj;
        this.cont = cancellableContinuationImpl;
    }

    public final E getPollResult() {
        return this.pollResult;
    }
}
