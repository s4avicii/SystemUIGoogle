package kotlin.jvm.internal;

import java.util.Objects;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;

public abstract class PropertyReference1 extends PropertyReference implements KProperty1 {
    public PropertyReference1() {
    }

    public PropertyReference1(Object obj, Class cls, String str, String str2, int i) {
        super(obj, cls, str, str2, i);
    }

    public final KCallable computeReflected() {
        Objects.requireNonNull(Reflection.factory);
        return this;
    }

    public final KProperty1.Getter getGetter() {
        return ((KProperty1) getReflected()).getGetter();
    }

    public final Object invoke(Object obj) {
        return get(obj);
    }
}
