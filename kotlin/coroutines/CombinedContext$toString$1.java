package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: CoroutineContextImpl.kt */
public final class CombinedContext$toString$1 extends Lambda implements Function2<String, CoroutineContext.Element, String> {
    public static final CombinedContext$toString$1 INSTANCE = new CombinedContext$toString$1();

    public CombinedContext$toString$1() {
        super(2);
    }

    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        String str = (String) obj;
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        if (str.length() == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return element.toString();
        }
        return str + ", " + element;
    }
}
