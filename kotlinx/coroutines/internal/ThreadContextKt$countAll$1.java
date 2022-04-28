package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: ThreadContext.kt */
public final class ThreadContextKt$countAll$1 extends Lambda implements Function2<Object, CoroutineContext.Element, Object> {
    public static final ThreadContextKt$countAll$1 INSTANCE = new ThreadContextKt$countAll$1();

    public ThreadContextKt$countAll$1() {
        super(2);
    }

    public final Object invoke(Object obj, Object obj2) {
        Integer num;
        int i;
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        if (!(element instanceof ThreadContextElement)) {
            return obj;
        }
        if (obj instanceof Integer) {
            num = (Integer) obj;
        } else {
            num = null;
        }
        if (num == null) {
            i = 1;
        } else {
            i = num.intValue();
        }
        if (i == 0) {
            return element;
        }
        return Integer.valueOf(i + 1);
    }
}
