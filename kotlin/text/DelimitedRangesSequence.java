package kotlin.text;

import java.util.Iterator;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;

/* compiled from: Strings.kt */
public final class DelimitedRangesSequence implements Sequence<IntRange> {
    public final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;
    public final CharSequence input;
    public final int limit;
    public final int startIndex;

    public final Iterator<IntRange> iterator() {
        return new DelimitedRangesSequence$iterator$1(this);
    }

    public DelimitedRangesSequence(CharSequence charSequence, int i, int i2, Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>> function2) {
        this.input = charSequence;
        this.startIndex = i;
        this.limit = i2;
        this.getNextMatch = function2;
    }
}
