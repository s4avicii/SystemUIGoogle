package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Sequences.kt */
public final class FilteringSequence$iterator$1 implements Iterator<T>, KMappedMarker {
    public final Iterator<T> iterator;
    public T nextItem;
    public int nextState = -1;
    public final /* synthetic */ FilteringSequence<T> this$0;

    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public FilteringSequence$iterator$1(FilteringSequence<T> filteringSequence) {
        this.this$0 = filteringSequence;
        this.iterator = filteringSequence.sequence.iterator();
    }

    public final void calcNext() {
        while (this.iterator.hasNext()) {
            T next = this.iterator.next();
            if (this.this$0.predicate.invoke(next).booleanValue() == this.this$0.sendWhen) {
                this.nextItem = next;
                this.nextState = 1;
                return;
            }
        }
        this.nextState = 0;
    }

    public final boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 1) {
            return true;
        }
        return false;
    }

    public final T next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState != 0) {
            T t = this.nextItem;
            this.nextItem = null;
            this.nextState = -1;
            return t;
        }
        throw new NoSuchElementException();
    }
}
