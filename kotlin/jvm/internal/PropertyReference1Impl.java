package kotlin.jvm.internal;

public class PropertyReference1Impl extends PropertyReference1 {
    public PropertyReference1Impl(Class cls, String str, String str2, int i) {
        super(CallableReference.NO_RECEIVER, cls, str, str2, i);
    }

    public Object get(Object obj) {
        return getGetter().call();
    }
}
