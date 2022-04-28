package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: Sequences.kt */
public final class MergingSequence<T1, T2, V> implements Sequence<V> {
    public final Sequence<T1> sequence1;
    public final Sequence<T2> sequence2;
    public final Function2<T1, T2, V> transform;

    public final Iterator<V> iterator() {
        return new MergingSequence$iterator$1(this);
    }

    public MergingSequence(Sequence<? extends T1> sequence, Sequence<? extends T2> sequence3, Function2<? super T1, ? super T2, ? extends V> function2) {
        this.sequence1 = sequence;
        this.sequence2 = sequence3;
        this.transform = function2;
    }
}
