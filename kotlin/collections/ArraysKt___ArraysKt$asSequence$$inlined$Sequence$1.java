package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.sequences.Sequence;

/* compiled from: Sequences.kt */
public final class ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1 implements Sequence<T> {
    public final /* synthetic */ Object[] $this_asSequence$inlined;

    public ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1(Object[] objArr) {
        this.$this_asSequence$inlined = objArr;
    }

    public final Iterator<T> iterator() {
        return new ArrayIterator(this.$this_asSequence$inlined);
    }
}
