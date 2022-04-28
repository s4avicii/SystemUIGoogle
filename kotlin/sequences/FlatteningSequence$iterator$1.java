package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Sequences.kt */
public final class FlatteningSequence$iterator$1 implements Iterator<E>, KMappedMarker {
    public Iterator<? extends E> itemIterator;
    public final Iterator<T> iterator;
    public final /* synthetic */ FlatteningSequence<T, R, E> this$0;

    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public FlatteningSequence$iterator$1(FlatteningSequence<T, R, E> flatteningSequence) {
        this.this$0 = flatteningSequence;
        this.iterator = flatteningSequence.sequence.iterator();
    }

    public final boolean ensureItemIterator() {
        boolean z;
        Iterator<? extends E> it = this.itemIterator;
        if (it != null && !it.hasNext()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.itemIterator = null;
        }
        while (true) {
            if (this.itemIterator == null) {
                if (this.iterator.hasNext()) {
                    T next = this.iterator.next();
                    FlatteningSequence<T, R, E> flatteningSequence = this.this$0;
                    Iterator<? extends E> invoke = flatteningSequence.iterator.invoke(flatteningSequence.transformer.invoke(next));
                    if (invoke.hasNext()) {
                        this.itemIterator = invoke;
                        break;
                    }
                } else {
                    return false;
                }
            } else {
                break;
            }
        }
        return true;
    }

    public final boolean hasNext() {
        return ensureItemIterator();
    }

    public final E next() {
        if (ensureItemIterator()) {
            Iterator<? extends E> it = this.itemIterator;
            Intrinsics.checkNotNull(it);
            return it.next();
        }
        throw new NoSuchElementException();
    }
}
