package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: Sequences.kt */
public final class TransformingIndexedSequence<T, R> implements Sequence<R> {
    public final Sequence<T> sequence;
    public final Function2<Integer, T, R> transformer;

    public final Iterator<R> iterator() {
        return new TransformingIndexedSequence$iterator$1(this);
    }

    public TransformingIndexedSequence(Sequence<? extends T> sequence2, Function2<? super Integer, ? super T, ? extends R> function2) {
        this.sequence = sequence2;
        this.transformer = function2;
    }
}
