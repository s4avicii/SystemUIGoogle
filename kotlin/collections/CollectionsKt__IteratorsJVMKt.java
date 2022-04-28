package kotlin.collections;

import java.util.Collection;

/* compiled from: IteratorsJVM.kt */
public class CollectionsKt__IteratorsJVMKt extends SetsKt__SetsKt {
    public static final int collectionSizeOrDefault(Iterable iterable, int i) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).size();
        }
        return i;
    }
}
