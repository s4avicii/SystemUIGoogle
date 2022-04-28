package kotlinx.coroutines.channels;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import androidx.lifecycle.runtime.R$id;
import androidx.preference.R$color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.RemoveOnCancel;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Removed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: AbstractChannel.kt */
public abstract class AbstractSendChannel<E> implements SendChannel<E> {
    public final AtomicRef<Object> onCloseHandler = AtomicFU.atomic((Object) null);
    public final Function1<E, Unit> onUndeliveredElement;
    public final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();

    /* compiled from: AbstractChannel.kt */
    public static final class SendBuffered<E> extends Send {
        public final E element;

        public final void completeResumeSend() {
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SendBuffered@");
            m.append(DebugStringsKt.getHexAddress(this));
            m.append('(');
            m.append(this.element);
            m.append(')');
            return m.toString();
        }

        public SendBuffered(E e) {
            this.element = e;
        }

        public final void resumeSendClosed(Closed<?> closed) {
            boolean z = DebugKt.DEBUG;
        }

        public final Object getPollResult() {
            return this.element;
        }

        public final Symbol tryResumeSend() {
            return R$id.RESUME_TOKEN;
        }
    }

    public static void helpClose(Closed closed) {
        Receive receive;
        Object obj = null;
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            if (prevNode instanceof Receive) {
                receive = (Receive) prevNode;
            } else {
                receive = null;
            }
            if (receive == null) {
                break;
            } else if (!receive.remove()) {
                ((Removed) receive.getNext()).ref.helpRemovePrev();
            } else {
                obj = InlineList.m329plusFjFbRPM(obj, receive);
            }
        }
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                ((Receive) obj).resumeReceiveClosed(closed);
                return;
            }
            ArrayList arrayList = (ArrayList) obj;
            int size = arrayList.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    ((Receive) arrayList.get(size)).resumeReceiveClosed(closed);
                    if (i >= 0) {
                        size = i;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public String getBufferDebugString() {
        return "";
    }

    public abstract boolean isBufferAlwaysFull();

    public abstract boolean isBufferFull();

    public final boolean offer(Boolean bool) {
        UndeliveredElementException callUndeliveredElementCatchingException;
        ChannelResult.Closed closed;
        Throwable th;
        try {
            Object r1 = m324trySendJP2dKIU(bool);
            if (!(r1 instanceof ChannelResult.Failed)) {
                return true;
            }
            if (r1 instanceof ChannelResult.Closed) {
                closed = (ChannelResult.Closed) r1;
            } else {
                closed = null;
            }
            if (closed == null) {
                th = null;
            } else {
                th = closed.cause;
            }
            if (th == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(th);
        } catch (Throwable th2) {
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 == null || (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, bool, (UndeliveredElementException) null)) == null) {
                throw th2;
            }
            ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException, th2);
            throw callUndeliveredElementCatchingException;
        }
    }

    public abstract ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed();

    public final boolean close(Throwable th) {
        boolean z;
        T t;
        Closed closed = new Closed(th);
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            LockFreeLinkedListNode prevNode = lockFreeLinkedListHead.getPrevNode();
            if (!(prevNode instanceof Closed)) {
                if (prevNode.addNext(closed, lockFreeLinkedListHead)) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            closed = (Closed) this.queue.getPrevNode();
        }
        helpClose(closed);
        if (z) {
            AtomicRef<Object> atomicRef = this.onCloseHandler;
            Objects.requireNonNull(atomicRef);
            T t2 = atomicRef.value;
            if (!(t2 == null || t2 == (t = AbstractChannelKt.HANDLER_INVOKED) || !this.onCloseHandler.compareAndSet(t2, t))) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(t2, 1);
                ((Function1) t2).invoke(th);
            }
        }
        return z;
    }

    public final Closed<?> getClosedForSend() {
        Closed<?> closed;
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        if (prevNode instanceof Closed) {
            closed = (Closed) prevNode;
        } else {
            closed = null;
        }
        if (closed == null) {
            return null;
        }
        helpClose(closed);
        return closed;
    }

    public final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        LockFreeLinkedListNode removeOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            if (lockFreeLinkedListNode != lockFreeLinkedListHead && (lockFreeLinkedListNode instanceof Send)) {
                if (((((Send) lockFreeLinkedListNode) instanceof Closed) && !lockFreeLinkedListNode.isRemoved()) || (removeOrNext = lockFreeLinkedListNode.removeOrNext()) == null) {
                    break;
                }
                removeOrNext.helpRemovePrev();
            }
        }
        lockFreeLinkedListNode = null;
        return (Send) lockFreeLinkedListNode;
    }

    public final String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(DebugStringsKt.getClassSimpleName(this));
        sb.append('@');
        sb.append(DebugStringsKt.getHexAddress(this));
        sb.append('{');
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        if (nextNode == this.queue) {
            str = "EmptyQueue";
        } else {
            if (nextNode instanceof Closed) {
                str2 = nextNode.toString();
            } else if (nextNode instanceof Receive) {
                str2 = "ReceiveQueued";
            } else if (nextNode instanceof Send) {
                str2 = "SendQueued";
            } else {
                str2 = Intrinsics.stringPlus("UNEXPECTED:", nextNode);
            }
            LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
            if (prevNode != nextNode) {
                StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str2, ",queueSize=");
                LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
                int i = 0;
                for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, lockFreeLinkedListHead); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
                    if (lockFreeLinkedListNode instanceof LockFreeLinkedListNode) {
                        i++;
                    }
                }
                m.append(i);
                str = m.toString();
                if (prevNode instanceof Closed) {
                    str = str + ",closedForSend=" + prevNode;
                }
            } else {
                str = str2;
            }
        }
        sb.append(str);
        sb.append('}');
        sb.append(getBufferDebugString());
        return sb.toString();
    }

    public AbstractSendChannel(Function1<? super E, Unit> function1) {
        this.onUndeliveredElement = function1;
    }

    public static final void access$helpCloseAndResumeWithSendException(AbstractSendChannel abstractSendChannel, CancellableContinuationImpl cancellableContinuationImpl, Object obj, Closed closed) {
        UndeliveredElementException callUndeliveredElementCatchingException;
        Objects.requireNonNull(abstractSendChannel);
        helpClose(closed);
        Throwable th = closed.closeCause;
        if (th == null) {
            th = new ClosedSendChannelException("Channel was closed");
        }
        Function1<E, Unit> function1 = abstractSendChannel.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, (UndeliveredElementException) null)) == null) {
            cancellableContinuationImpl.resumeWith(new Result.Failure(th));
            return;
        }
        ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException, th);
        cancellableContinuationImpl.resumeWith(new Result.Failure(callUndeliveredElementCatchingException));
    }

    public Object enqueueSend(SendElement sendElement) {
        boolean z;
        LockFreeLinkedListNode prevNode;
        if (isBufferAlwaysFull()) {
            LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
            do {
                prevNode = lockFreeLinkedListHead.getPrevNode();
                if (prevNode instanceof ReceiveOrClosed) {
                    return prevNode;
                }
            } while (!prevNode.addNext(sendElement, lockFreeLinkedListHead));
            return null;
        }
        LockFreeLinkedListHead lockFreeLinkedListHead2 = this.queue;
        AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 abstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 = new AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1(sendElement, this);
        while (true) {
            LockFreeLinkedListNode prevNode2 = lockFreeLinkedListHead2.getPrevNode();
            if (!(prevNode2 instanceof ReceiveOrClosed)) {
                int tryCondAddNext = prevNode2.tryCondAddNext(sendElement, lockFreeLinkedListHead2, abstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1);
                z = true;
                if (tryCondAddNext != 1) {
                    if (tryCondAddNext == 2) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            } else {
                return prevNode2;
            }
        }
        if (!z) {
            return AbstractChannelKt.ENQUEUE_FAILED;
        }
        return null;
    }

    public Object offerInternal(E e) {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed;
        do {
            takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
            if (takeFirstReceiveOrPeekClosed == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
        } while (takeFirstReceiveOrPeekClosed.tryResumeReceive(e) == null);
        boolean z = DebugKt.DEBUG;
        takeFirstReceiveOrPeekClosed.completeResumeReceive(e);
        return takeFirstReceiveOrPeekClosed.getOfferResult();
    }

    public final Object send(E e, Continuation<? super Unit> continuation) {
        boolean z;
        SendElement sendElement;
        if (offerInternal(e) == AbstractChannelKt.OFFER_SUCCESS) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(R$color.intercepted(continuation));
        while (true) {
            if ((this.queue.getNextNode() instanceof ReceiveOrClosed) || !isBufferFull()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Function1<E, Unit> function1 = this.onUndeliveredElement;
                if (function1 == null) {
                    sendElement = new SendElement(e, orCreateCancellableContinuation);
                } else {
                    sendElement = new SendElementWithUndeliveredHandler(e, orCreateCancellableContinuation, function1);
                }
                Object enqueueSend = enqueueSend(sendElement);
                if (enqueueSend == null) {
                    orCreateCancellableContinuation.invokeOnCancellation(new RemoveOnCancel(sendElement));
                    break;
                } else if (enqueueSend instanceof Closed) {
                    access$helpCloseAndResumeWithSendException(this, orCreateCancellableContinuation, e, (Closed) enqueueSend);
                    break;
                } else if (enqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueSend instanceof Receive)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("enqueueSend returned ", enqueueSend).toString());
                }
            }
            Object offerInternal = offerInternal(e);
            if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
                orCreateCancellableContinuation.resumeWith(Unit.INSTANCE);
                break;
            } else if (offerInternal != AbstractChannelKt.OFFER_FAILED) {
                if (offerInternal instanceof Closed) {
                    access$helpCloseAndResumeWithSendException(this, orCreateCancellableContinuation, e, (Closed) offerInternal);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("offerInternal returned ", offerInternal).toString());
                }
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        if (result == coroutineSingletons) {
            return result;
        }
        return Unit.INSTANCE;
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public final Object m324trySendJP2dKIU(Serializable serializable) {
        ChannelResult.Closed closed;
        Object offerInternal = offerInternal(serializable);
        if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
            return Unit.INSTANCE;
        }
        if (offerInternal == AbstractChannelKt.OFFER_FAILED) {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend == null) {
                return ChannelResult.failed;
            }
            helpClose(closedForSend);
            Throwable th = closedForSend.closeCause;
            if (th == null) {
                th = new ClosedSendChannelException("Channel was closed");
            }
            closed = new ChannelResult.Closed(th);
        } else if (offerInternal instanceof Closed) {
            Closed closed2 = (Closed) offerInternal;
            helpClose(closed2);
            Throwable th2 = closed2.closeCause;
            if (th2 == null) {
                th2 = new ClosedSendChannelException("Channel was closed");
            }
            closed = new ChannelResult.Closed(th2);
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("trySend returned ", offerInternal).toString());
        }
        return closed;
    }
}
