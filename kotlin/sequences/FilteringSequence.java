package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: Sequences.kt */
public final class FilteringSequence<T> implements Sequence<T> {
    public final Function1<T, Boolean> predicate;
    public final boolean sendWhen;
    public final Sequence<T> sequence;

    public final Iterator<T> iterator() {
        return new FilteringSequence$iterator$1(this);
    }

    public FilteringSequence(Sequence<? extends T> sequence2, boolean z, Function1<? super T, Boolean> function1) {
        this.sequence = sequence2;
        this.sendWhen = z;
        this.predicate = function1;
    }
}
