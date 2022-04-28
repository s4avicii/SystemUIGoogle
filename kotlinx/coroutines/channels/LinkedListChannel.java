package kotlinx.coroutines.channels;

import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.AbstractSendChannel;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: LinkedListChannel.kt */
public final class LinkedListChannel<E> extends AbstractChannel<E> {
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    public final boolean isBufferAlwaysFull() {
        return false;
    }

    public final boolean isBufferEmpty() {
        return true;
    }

    public final boolean isBufferFull() {
        return false;
    }

    /* renamed from: onCancelIdempotentList-w-w6eGU  reason: not valid java name */
    public final void m325onCancelIdempotentListww6eGU(Object obj, Closed<?> closed) {
        UndeliveredElementException undeliveredElementException = null;
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                Send send = (Send) obj;
                if (send instanceof AbstractSendChannel.SendBuffered) {
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    if (function1 != null) {
                        undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) send).element, (UndeliveredElementException) null);
                    }
                } else {
                    send.resumeSendClosed(closed);
                }
            } else {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size() - 1;
                if (size >= 0) {
                    UndeliveredElementException undeliveredElementException2 = null;
                    while (true) {
                        int i = size - 1;
                        Send send2 = (Send) arrayList.get(size);
                        if (send2 instanceof AbstractSendChannel.SendBuffered) {
                            Function1<E, Unit> function12 = this.onUndeliveredElement;
                            if (function12 == null) {
                                undeliveredElementException2 = null;
                            } else {
                                undeliveredElementException2 = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function12, ((AbstractSendChannel.SendBuffered) send2).element, undeliveredElementException2);
                            }
                        } else {
                            send2.resumeSendClosed(closed);
                        }
                        if (i < 0) {
                            break;
                        }
                        size = i;
                    }
                    undeliveredElementException = undeliveredElementException2;
                }
            }
        }
        if (undeliveredElementException != null) {
            throw undeliveredElementException;
        }
    }

    public final Object offerInternal(E e) {
        ReceiveOrClosed receiveOrClosed;
        do {
            Object offerInternal = super.offerInternal(e);
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            if (offerInternal == symbol) {
                return symbol;
            }
            if (offerInternal == AbstractChannelKt.OFFER_FAILED) {
                LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
                AbstractSendChannel.SendBuffered sendBuffered = new AbstractSendChannel.SendBuffered(e);
                while (true) {
                    LockFreeLinkedListNode prevNode = lockFreeLinkedListHead.getPrevNode();
                    if (!(prevNode instanceof ReceiveOrClosed)) {
                        if (prevNode.addNext(sendBuffered, lockFreeLinkedListHead)) {
                            receiveOrClosed = null;
                            break;
                        }
                    } else {
                        receiveOrClosed = (ReceiveOrClosed) prevNode;
                        break;
                    }
                }
                if (receiveOrClosed == null) {
                    return AbstractChannelKt.OFFER_SUCCESS;
                }
            } else if (offerInternal instanceof Closed) {
                return offerInternal;
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid offerInternal result ", offerInternal).toString());
            }
        } while (!(receiveOrClosed instanceof Closed));
        return receiveOrClosed;
    }

    public LinkedListChannel(Function1<? super E, Unit> function1) {
        super(function1);
    }
}
