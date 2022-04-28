package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: Sequences.kt */
public final class TransformingSequence<T, R> implements Sequence<R> {
    public final Sequence<T> sequence;
    public final Function1<T, R> transformer;

    public final Iterator<R> iterator() {
        return new TransformingSequence$iterator$1(this);
    }

    public TransformingSequence(Sequence<? extends T> sequence2, Function1<? super T, ? extends R> function1) {
        this.sequence = sequence2;
        this.transformer = function1;
    }
}
