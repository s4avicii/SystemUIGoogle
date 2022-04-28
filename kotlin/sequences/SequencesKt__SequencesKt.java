package kotlin.sequences;

import androidx.slice.view.R$id;
import java.util.Iterator;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: Sequences.kt */
public class SequencesKt__SequencesKt extends R$id {
    public static final <T> Sequence<T> sequenceOf(T... tArr) {
        boolean z;
        if (tArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return EmptySequence.INSTANCE;
        }
        return ArraysKt___ArraysKt.asSequence(tArr);
    }

    public static final <T> Sequence<T> asSequence(Iterator<? extends T> it) {
        SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1 sequencesKt__SequencesKt$asSequence$$inlined$Sequence$1 = new SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1(it);
        if (sequencesKt__SequencesKt$asSequence$$inlined$Sequence$1 instanceof ConstrainedOnceSequence) {
            return sequencesKt__SequencesKt$asSequence$$inlined$Sequence$1;
        }
        return new ConstrainedOnceSequence(sequencesKt__SequencesKt$asSequence$$inlined$Sequence$1);
    }
}
