package kotlin.comparisons;

import androidx.fragment.R$styleable;
import java.util.Comparator;
import kotlin.jvm.functions.Function1;

/* compiled from: Comparisons.kt */
public final class ComparisonsKt__ComparisonsKt$compareBy$1<T> implements Comparator {
    public final /* synthetic */ Function1<T, Comparable<?>>[] $selectors;

    public ComparisonsKt__ComparisonsKt$compareBy$1(Function1<? super T, ? extends Comparable<?>>[] function1Arr) {
        this.$selectors = function1Arr;
    }

    public final int compare(T t, T t2) {
        Function1<T, Comparable<?>>[] function1Arr = this.$selectors;
        int length = function1Arr.length;
        int i = 0;
        while (i < length) {
            Function1<T, Comparable<?>> function1 = function1Arr[i];
            i++;
            int compareValues = R$styleable.compareValues(function1.invoke(t), function1.invoke(t2));
            if (compareValues != 0) {
                return compareValues;
            }
        }
        return 0;
    }
}
