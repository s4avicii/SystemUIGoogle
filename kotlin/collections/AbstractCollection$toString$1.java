package kotlin.collections;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: AbstractCollection.kt */
public final class AbstractCollection$toString$1 extends Lambda implements Function1<E, CharSequence> {
    public final /* synthetic */ AbstractCollection<E> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractCollection$toString$1(AbstractCollection<? extends E> abstractCollection) {
        super(1);
        this.this$0 = abstractCollection;
    }

    public final Object invoke(Object obj) {
        if (obj == this.this$0) {
            return "(this Collection)";
        }
        return String.valueOf(obj);
    }
}
