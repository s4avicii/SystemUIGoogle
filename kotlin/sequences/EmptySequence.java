package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.EmptyIterator;

/* compiled from: Sequences.kt */
public final class EmptySequence implements Sequence, DropTakeSequence {
    public static final EmptySequence INSTANCE = new EmptySequence();

    public final /* bridge */ /* synthetic */ Sequence take(int i) {
        return INSTANCE;
    }

    public final Iterator iterator() {
        return EmptyIterator.INSTANCE;
    }
}
