package kotlin.sequences;

import java.util.Iterator;

/* compiled from: Sequences.kt */
public final class TakeSequence<T> implements Sequence<T>, DropTakeSequence<T> {
    public final int count;
    public final Sequence<T> sequence;

    public final Iterator<T> iterator() {
        return new TakeSequence$iterator$1(this);
    }

    public final Sequence<T> take(int i) {
        if (i >= this.count) {
            return this;
        }
        return new TakeSequence(this.sequence, i);
    }

    public TakeSequence(Sequence<? extends T> sequence2, int i) {
        boolean z;
        this.sequence = sequence2;
        this.count = i;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(("count must be non-negative, but was " + i + '.').toString());
        }
    }
}
