package kotlin.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.DistinctIterator;

/* compiled from: AbstractIterator.kt */
public abstract class AbstractIterator<T> implements Iterator<T>, KMappedMarker {
    public T nextValue;
    public State state = State.NotReady;

    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void done() {
        this.state = State.Done;
    }

    public final boolean hasNext() {
        boolean z;
        State state2 = this.state;
        State state3 = State.Failed;
        if (state2 != state3) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            int ordinal = state2.ordinal();
            if (ordinal == 0) {
                return true;
            }
            if (ordinal != 2) {
                this.state = state3;
                DistinctIterator distinctIterator = (DistinctIterator) this;
                while (true) {
                    if (!distinctIterator.source.hasNext()) {
                        distinctIterator.done();
                        break;
                    }
                    T next = distinctIterator.source.next();
                    if (distinctIterator.observed.add(distinctIterator.keySelector.invoke(next))) {
                        distinctIterator.setNext(next);
                        break;
                    }
                }
                if (this.state == State.Ready) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public final void setNext(T t) {
        this.nextValue = t;
        this.state = State.Ready;
    }

    public final T next() {
        if (hasNext()) {
            this.state = State.NotReady;
            return this.nextValue;
        }
        throw new NoSuchElementException();
    }
}
