package kotlin.jvm.internal;

public class MutablePropertyReference1Impl extends MutablePropertyReference1 {
    public MutablePropertyReference1Impl(Class cls, String str, String str2) {
        super(CallableReference.NO_RECEIVER, cls, str, str2);
    }

    public final Object get(Object obj) {
        return getGetter().call();
    }
}
