package kotlinx.coroutines.internal;

import androidx.fragment.R$styleable;
import java.lang.reflect.Constructor;
import java.util.Comparator;

/* renamed from: kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1 */
/* compiled from: Comparisons.kt */
public final class C2506x9323e642<T> implements Comparator {
    public final int compare(T t, T t2) {
        return R$styleable.compareValues(Integer.valueOf(((Constructor) t2).getParameterTypes().length), Integer.valueOf(((Constructor) t).getParameterTypes().length));
    }
}
