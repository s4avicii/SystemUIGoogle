package kotlinx.coroutines.channels;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: ConflatedChannel.kt */
public final class ConflatedChannel<E> extends AbstractChannel<E> {
    public final ReentrantLock lock = new ReentrantLock();
    public Object value = AbstractChannelKt.EMPTY;

    public final boolean isBufferAlwaysEmpty() {
        return false;
    }

    public final boolean isBufferAlwaysFull() {
        return false;
    }

    public final boolean isBufferFull() {
        return false;
    }

    public final boolean enqueueReceiveInternal(Receive<? super E> receive) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueReceiveInternal(receive);
        } finally {
            reentrantLock.unlock();
        }
    }

    public final String getBufferDebugString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(value=");
        m.append(this.value);
        m.append(')');
        return m.toString();
    }

    public final boolean isBufferEmpty() {
        if (this.value == AbstractChannelKt.EMPTY) {
            return true;
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    public final Object offerInternal(E e) {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend == null) {
                if (this.value == AbstractChannelKt.EMPTY) {
                    do {
                        takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (takeFirstReceiveOrPeekClosed != null) {
                            if (takeFirstReceiveOrPeekClosed instanceof Closed) {
                                reentrantLock.unlock();
                                return takeFirstReceiveOrPeekClosed;
                            }
                        }
                    } while (takeFirstReceiveOrPeekClosed.tryResumeReceive(e) == null);
                    boolean z = DebugKt.DEBUG;
                    reentrantLock.unlock();
                    takeFirstReceiveOrPeekClosed.completeResumeReceive(e);
                    return takeFirstReceiveOrPeekClosed.getOfferResult();
                }
                Object obj = this.value;
                UndeliveredElementException undeliveredElementException = null;
                if (obj != AbstractChannelKt.EMPTY) {
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    if (function1 != null) {
                        undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, (UndeliveredElementException) null);
                    }
                }
                this.value = e;
                if (undeliveredElementException == null) {
                    Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
                    reentrantLock.unlock();
                    return symbol;
                }
                throw undeliveredElementException;
            }
            reentrantLock.unlock();
            return closedForSend;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void onCancelIdempotent(boolean z) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Symbol symbol = AbstractChannelKt.EMPTY;
            Object obj = this.value;
            UndeliveredElementException undeliveredElementException = null;
            if (obj != symbol) {
                Function1<E, Unit> function1 = this.onUndeliveredElement;
                if (function1 != null) {
                    undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, (UndeliveredElementException) null);
                }
            }
            this.value = symbol;
            reentrantLock.unlock();
            super.onCancelIdempotent(z);
            if (undeliveredElementException != null) {
                throw undeliveredElementException;
            }
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final Object pollInternal() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object obj = this.value;
            Symbol symbol = AbstractChannelKt.EMPTY;
            if (obj == symbol) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            this.value = symbol;
            reentrantLock.unlock();
            return obj;
        } finally {
            reentrantLock.unlock();
        }
    }

    public ConflatedChannel(Function1<? super E, Unit> function1) {
        super(function1);
    }
}
